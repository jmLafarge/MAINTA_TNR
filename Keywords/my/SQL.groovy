package my

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.sql.Sql
import internal.GlobalVariable
import my.Log as MYLOG
import my.InfoBDD as INFOBDD
import my.JDDKW as MYJDDKW

public class SQL {
	/**
	 * 
	 */


	private static sqlInstance = Sql.newInstance(GlobalVariable.BDD_URL, GlobalVariable.BDD_USER, GlobalVariable.BDD_MDP)



	static executeSQL(String query) {
		try {
			sqlInstance.execute(query)
		}
		catch(Exception ex) {
			MYLOG.addERROR("Erreur d'execution de execute($query) : ")
			MYLOG.addDETAIL("executeSQL()")
			MYLOG.addDETAIL(ex.getMessage())
		}
	}


	static getFirstRow(String query) {
		try {
			return sqlInstance.firstRow(query)
		} catch(Exception ex) {
			MYLOG.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			MYLOG.addDETAIL("getFirstRow()")
			MYLOG.addDETAIL(ex.getMessage())
			return null
		}
	}


	/**
	 * Vérification à la fin des insertions, donc dans le cas de 2 insertions, il faut boucler sur le nombre d'insertions
	 *
	 * @param
	 * @return
	 *
	 *
	 *
	 */
	static checkJDDWithBD(JDD myJDD,Map specificValueMap=[:],String sql =''){

		KW.delay(1)

		boolean pass = true
		MYLOG.addSTEP("Début de la vérification des valeurs en Base de Données")
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()


		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				MYLOG.addSUBSTEP("Contrôle cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}

			def rows
			def row

			String query =''
			if (sql) {
				query = sql
				try {
					row = sqlInstance.firstRow(query)
				} catch(Exception ex) {
					MYLOG.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
					MYLOG.addDETAIL("checkJDDWithBD()")
					MYLOG.addDETAIL(ex.getMessage())
				}
			}else {
				query = "SELECT * FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD,casDeTestNum)
				MYLOG.addDEBUG("query =  $query")
				try {
					rows = sqlInstance.rows(query)
					if (rows.size() == 0) {
						MYLOG.addERROR("Pas de résultat pour la requête : $query")
						pass = false
					}else if (rows.size() > 1){
						MYLOG.addERROR(rows.size() + "résultats pour la requête : $query")
						pass = false
					}else {
						row=rows[0]
					}
				}
				catch(Exception ex) {
					pass=false
					MYLOG.addERROR("Erreur d'execution de sql.rows($query) : ")
					MYLOG.addDETAIL("checkJDDWithBD()")
					MYLOG.addDETAIL(ex.getMessage())
				}
			}



			if (pass) {

				row.each{fieldName,val ->

					MYLOG.addDEBUG("fieldName = $fieldName , val = $val , JDD value = " + myJDD.getData(fieldName))

					pass = this.checkValue(myJDD,fieldName, val,pass,specificValueMap)
				}//row

			}//pass
		}//for
		if (pass) {
			MYLOG.addSTEPPASS("Fin de la vérification des valeurs en Base de Données")
		}else {
			MYLOG.addSTEPFAIL("Fin de la  vérification des valeurs en Base de Données")
		}
	}





	private static boolean checkValue(my.JDD myJDD, String fieldName, val,boolean pass,Map specificValueMap) {

		boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)

		if (!specificValue && myJDD.getParamForThisName('FOREIGNKEY', fieldName)!=null) {

			if (!this.checkForeignKey(myJDD, fieldName, val)) {
				pass = false
			}

		}else {

			switch (myJDD.getData(fieldName)) {

				case MYJDDKW.getKW_NU() :

					MYLOG.addDEBUG("NU : Pas de contrôle pour $fieldName : la valeur en BD est  : $val" )
					break

				case MYJDDKW.getKW_VIDE() :
				case MYJDDKW.getKW_NULL():
					if (val == null || val =='') {

						MYLOG.addDEBUG("Contrôle de la valeur de $fieldName OK : la valeur attendue est VIDE ou null et la valeur en BD est  : $val" )
					}else {
						MYLOG.addDETAIL("Contrôle de la valeur de $fieldName KO : la valeur attendue est VIDE ou null et la valeur en BD est  : $val")
						pass = false
					}

					break

				case MYJDDKW.getKW_DATE() :

					MYLOG.addDETAIL("Contrôle de la valeur DATE de $fieldName KO : ******* reste à faire ******* la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
					pass = false
					break

				case MYJDDKW.getKW_DATETIME() :

					if (val instanceof java.sql.Timestamp) {
						MYLOG.addDEBUG("Contrôle de la valeur DATETIME de $fieldName OK : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val " )
					}else {
						MYLOG.addDETAIL("Contrôle de la valeur DATETIME de $fieldName KO : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
						pass = false
					}
					break

				case MYJDDKW.getKW_SEQUENCEID() :

					MYLOG.addDETAIL("Contrôle IDINTERNE valeur $fieldName KO : ******* reste à faire la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
				//il faut peut etre testé si la valeur est num et unique ? ******

					MYLOG.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' dans le JDD : ' + myJDD.getData(fieldName).getClass())
					if ( val == myJDD.getData(fieldName)) {
						MYLOG.addDEBUG("Contrôle de la valeur SEQUENCEID de $fieldName OK : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val " )
					}else {
						MYLOG.addDETAIL("Contrôle de la valeur SEQUENCEID de $fieldName KO : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
						pass = false
					}

					break

				case MYJDDKW.getKW_ORDRE() :

					MYLOG.addDETAIL("Contrôle de la valeur ORDRE de $fieldName KO : ******* reste à faire la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
				//voir aussi le NU_NIV *******
					pass = false
					break

				default:

					if (specificValue) {
						MYLOG.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' la valeur spécifique est  : ' + specificValueMap[fieldName].getClass())
						if ( val == INFOBDD.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
							MYLOG.addDEBUG("Contrôle de la valeur spécifique de $fieldName OK : la valeur attendue est : " + specificValueMap[fieldName] + " et la valeur en BD est : $val " )
						}else {
							MYLOG.addDETAIL("Contrôle de la valeur spécifique de $fieldName KO : la valeur attendue est : " + specificValueMap[fieldName] + " et la valeur en BD est : $val")
							pass = false
						}
					}else {
						MYLOG.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' dans le JDD : ' + myJDD.getData(fieldName).getClass())
						if ( val == INFOBDD.castJDDVal(myJDD.getDBTableName(), fieldName, myJDD.getData(fieldName))) {
							MYLOG.addDEBUG("Contrôle de la valeur de $fieldName OK : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val " )
						}else {
							MYLOG.addDETAIL("Contrôle de la valeur de $fieldName KO : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
							pass = false
						}
					}
					break
			}//case
		}
		return pass
	}







	public static String getMaintaVersion() {

		String query = "SELECT ST_VAL FROM VER WHERE ID_CODINF = 'CURR_VERS'"

		try {
			def frow = sqlInstance.firstRow(query)
			if (frow ) {
				return frow.getAt(0).toString()
			}else {
				MYLOG.addDEBUG("getMaintaVersion() est null")
				return null
			}
		}catch(Exception ex) {
			MYLOG.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			MYLOG.addDETAIL("getMaintaVersion()")
			MYLOG.addDETAIL(ex.getMessage())
		}
	}






	private static boolean checkForeignKey(JDD myJDD, String fieldName, def val) {
		boolean pass = false
		String query = myJDD.getSqlForForeignKey(fieldName)
		try {
			def frow = sqlInstance.firstRow(query)
			if (frow == null) {
				MYLOG.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO, pas de résultat pour la query : $query")
			}else if (val != frow.getAt(0)) {
				MYLOG.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO : ** la valeur du JDD attendue est : " + myJDD.getData(fieldName) + ' et la valeur est BD est : ' +  frow.getAt(0))
			}else {
				MYLOG.addDEBUG("Contrôle de la valeur de $fieldName (FK) OK : la valeur attendue est : " + frow.getAt(0) + " et la valeur en BD est : $val")
				pass = true
			}
		}catch(Exception ex) {
			MYLOG.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			MYLOG.addDETAIL("checkForeignKey()")
			MYLOG.addDETAIL(ex.getMessage())
		}
		return pass
	}




	/**
	 * Dans le cas d'une vérif 	à la fin des insertions, donc dans le cas de 2 insertions, il faut boucler sur le nombre d'insertions
	 * @param myJDD
	 * 
	 * @return
	 */

	static checkIDNotInBD(JDD myJDD){

		KW.delay(1)
		boolean pass = true
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()

		MYLOG.addSTEP("Début de la vérification de la suppression des valeurs en Base de Données")
		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				MYLOG.addSUBSTEP("Contrôle de la suppression du cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}
			String query = "SELECT count(*) FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD,casDeTestNum)

			MYLOG.addDEBUG("query =  $query")

			def row

			try {
				row = sqlInstance.firstRow(query)

			}catch(Exception ex) {
				pass = false
				MYLOG.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
				MYLOG.addDETAIL("checkIDNotInBD()")
				MYLOG.addDETAIL(ex.getMessage())
			}

			if (row[0]>0) {
				MYLOG.addDETAIL("Supression KO")
				pass = false
			}
		}

		if (pass) {
			MYLOG.addSTEPPASS("Fin de la vérification de la suppression des valeurs en Base de Données")
			//KeywordUtil.markPassed("Supression OK")
		}else {
			MYLOG.addSTEPFAIL("Fin de la  vérification de la suppression des valeurs en Base de Données")
			//KeywordUtil.markFailed("Supression KO")
		}
	}



	/**
	 * 
	 * @param myJDD
	 * @param casDeTestNum
	 * @return
	 */
	private static String getWhereWithAllPK(JDD myJDD,int casDeTestNum) {
		List PKList = INFOBDD.getPK(myJDD.getDBTableName())
		if (PKList) {
			String query = ' WHERE '
			PKList.each {
				query = query + it + "='" + myJDD.getData(it,casDeTestNum) + "' and "
			}
			return query.substring(0,query.length()-5)
		}
		return ''
	}





	static insertSQL(String req) {
		try {
			def nbRowInserted = sqlInstance.executeInsert req
			if (nbRowInserted >= 0) {
				MYLOG.addDEBUG("insertSQL($req) OK, nombre de ligne inséré : ${nbRowInserted}")
			}else {
				MYLOG.addERROR("Erreur de insertSQL()")
				MYLOG.addDETAIL(req)
			}
			return nbRowInserted
		}
		catch(Exception ex) {
			MYLOG.addERROR("Erreur d'execution de insertSQL() : $req ")
			MYLOG.addDETAIL(ex.getMessage())
			return -1
		}

	}




	static int getMaxFromTable(String fieldName, String tableName) {

		MYLOG.addDEBUG("getMaxFromTable(String fieldName, String tableName) '$fieldName' , '$tableName'")

		String req = "SELECT MAX($fieldName) as num FROM $tableName"

		try {
			int num = sqlInstance.firstRow(req).num
			MYLOG.addDETAIL("get Max '$fieldName From Table '$tableName' = $num")
			return num
		} catch(Exception ex) {
			MYLOG.addERROR("Erreur d'execution de firstRow($req) : ")
			MYLOG.addDETAIL("getMaxFromTable()")
			MYLOG.addDETAIL(ex.getMessage())
		}
		return null
	}


	static getNumEcran(String table) {

		def query = "SELECT ID_NUMECR as num FROM OBJ where ST_NOMOBJ=$table"

		try {
			def res = sqlInstance.firstRow(query)
			def ret = (res) ? res.num : null
			MYLOG.addDETAIL("getNumEcran($table) = $ret")
			return ret
		} catch(Exception ex) {
			MYLOG.addERROR("Erreur d'execution de firstRow($query) : ")
			MYLOG.addDETAIL("getNumEcran()")
			MYLOG.addDETAIL(ex.getMessage())
			return null
		}
	}

	static Map getLibelle(String table, numEcran) {

		MYLOG.addDETAIL("Recherche des libellés pour la table $table et numéro écran $numEcran")
		def query = """SELECT COLUMN_NAME as name, obj_lan.st_lib as lib
						FROM INFORMATION_SCHEMA.COLUMNS
						left join obj
							on  obj.st_nomobj = COLUMN_NAME and obj.st_typobj = 'col' and obj.ID_NUMECR = $numEcran
						left join obj_lan
						    on obj_lan.ID_NUMECR = obj.ID_NUMECR and obj_lan.ID_NUMOBJ = obj.ID_NUMOBJ and obj_lan.id_codlan = 'FRA' 
						WHERE TABLE_NAME=$table"""

		def rows = sqlInstance.rows(query)

		// Créer une liste de maps à partir des résultats
		def resultMap = [:]
		rows.each {
			resultMap.putAt(it.name, it.lib)
		}
		return resultMap
	}




} // end of class