package my

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.sql.Sql
import internal.GlobalVariable
import my.Log as MYLOG
import my.InfoBDD as INFOBDD
import my.JDDKW as MYJDDKW

//Pour la lecture du format RTF
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit
import java.io.StringReader

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

		String verifStatus = 'PASS'

		MYLOG.addSTEP("Début de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
		
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
					MYLOG.addDETAIL("Erreur d'execution de sqlInstance.firstRow($query) : ")
					MYLOG.addDETAIL(ex.getMessage())
					verifStatus = 'ERROR'
				}
			}else {

				query = "SELECT * FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD,casDeTestNum)

				MYLOG.addDEBUG("query =  $query")
				try {
					rows = sqlInstance.rows(query)
					if (rows.size() == 0) {
						MYLOG.addDETAIL("Pas de résultat pour la requête : $query")
						verifStatus = 'ERROR'
					}else if (rows.size() > 1){
						MYLOG.addDETAIL(rows.size() + "résultats pour la requête : $query")
						verifStatus = 'ERROR'
					}else {
						row=rows[0]
					}
				}
				catch(Exception ex) {
					verifStatus = 'ERROR'
					MYLOG.addDETAIL("Erreur d'execution de sql.rows($query) : ")
					MYLOG.addDETAIL(ex.getMessage())
				}
			}



			if (verifStatus =='PASS') {

				row.each{fieldName,val ->

					MYLOG.addDEBUG("fieldName = $fieldName , val = $val , JDD value = " + myJDD.getData(fieldName))

					verifStatus = this.checkValue(myJDD,fieldName, val,verifStatus,specificValueMap,casDeTestNum)
				}//row

			}//pass
		}//for

		switch (verifStatus) {
			case 'PASS':
				MYLOG.addSTEPPASS("Fin de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
				break
			case 'FAIL':
				MYLOG.addSTEPFAIL("Fin de la  vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
				break
			case 'ERROR':
				MYLOG.addSTEPERROR("Fin de la  vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
				break
			default : 
				MYLOG.addDETAIL("verifStatus inconnu '$verifStatus'")
				MYLOG.addSTEPERROR("Fin de la  vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
				
				break
		}

	}





	private static String checkValue(my.JDD myJDD, String fieldName, val,String statusCheckValue,Map specificValueMap, int casDeTestNum) {

		boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)

		if (!specificValue && myJDD.getParamForThisName('FOREIGNKEY', fieldName)!=null) {

			if (!this.checkForeignKey(myJDD, fieldName, val)) {
				statusCheckValue = 'FAIL'
			}

		}else {

			switch (myJDD.getData(fieldName)) {

				case MYJDDKW.getKW_NU() :

				MYLOG.addDEBUG("NU : Pas de contrôle pour '$fieldName' : la valeur en BD est  : '$val'" )
				break

				case MYJDDKW.getKW_VIDE() :
				case MYJDDKW.getKW_NULL():
				if (val == null || val =='') {

					this.logAddDEBUG('',fieldName,'VIDE ou NULL',val)
				}else {
					this.logAddDETAIL('',fieldName,'VIDE ou NULL',val)
					statusCheckValue = 'FAIL'
				}

				break

				case MYJDDKW.getKW_DATE() :

				this.logAddDETAIL('DATE ***** reste à faire *****',fieldName,myJDD.getData(fieldName),val)
				statusCheckValue = 'FAIL'
				break

				case MYJDDKW.getKW_DATETIME() :

				if (val instanceof java.sql.Timestamp) {
					this.logAddDEBUG('DATETIME',fieldName,myJDD.getData(fieldName),val)
				}else {
					this.logAddDETAIL('DATETIME ***** reste à faire *****',fieldName,myJDD.getData(fieldName),val)
					statusCheckValue = 'FAIL'
				}
				break

				case MYJDDKW.getKW_SEQUENCEID() :

				this.logAddDETAIL('IDINTERNE ***** reste à faire *****',fieldName,myJDD.getData(fieldName),val)
				//il faut peut etre testé si la valeur est num et unique ? ******

				MYLOG.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' dans le JDD : ' + myJDD.getData(fieldName).getClass())
				if ( val == myJDD.getData(fieldName)) {
					this.logAddDEBUG('SEQUENCEID',fieldName,myJDD.getData(fieldName),val)
				}else {
					this.logAddDETAIL('SEQUENCEID',fieldName,myJDD.getData(fieldName),val)
					statusCheckValue = 'FAIL'
				}

				break

				case MYJDDKW.getKW_ORDRE() :

				this.logAddDETAIL('ORDRE ***** reste àfaire *****',fieldName,myJDD.getData(fieldName),val)
				//voir aussi le NU_NIV *******
				statusCheckValue = 'FAIL'
				break

				default:

				if (specificValue) {
					MYLOG.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' la valeur spécifique est  : ' + specificValueMap[fieldName].getClass())
					if ( val == INFOBDD.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
						this.logAddDEBUG('spécifique',fieldName,specificValueMap[fieldName],val)
					}else {
						this.logAddDETAIL('spécifique',fieldName,specificValueMap[fieldName],val)
						statusCheckValue = 'FAIL'
					}
				}else {

					if (INFOBDD.isImage(myJDD.getDBTableName(), fieldName)) {

						String query = "SELECT cast(cast($fieldName as varbinary(max)) as varchar(max)) FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD,casDeTestNum)
						def frow = this.getFirstRow(query)
						if (frow ) {
							def texte = new DefaultStyledDocument()

							def editorKit = new RTFEditorKit()
							editorKit.read(new StringReader(frow.getAt(0).toString()), texte , 0)
							val = texte.getText(0, texte.getLength()-2)

						}
					}

					MYLOG.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' dans le JDD : ' + myJDD.getData(fieldName).getClass())
					if ( val == INFOBDD.castJDDVal(myJDD.getDBTableName(), fieldName, myJDD.getData(fieldName))) {
						this.logAddDEBUG('',fieldName,myJDD.getData(fieldName),val)
					}else {
						this.logAddDETAIL('',fieldName,myJDD.getData(fieldName),val)
						statusCheckValue = 'FAIL'
					}


				}
				break
			}//case
		}
		return statusCheckValue
	}




	private static logAddDEBUG(String type,String fieldName, def valJDD, def val) {
		MYLOG.addDEBUG("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
	}



	private static logAddDETAIL(String type,String fieldName, def valJDD, def val) {
		MYLOG.addDETAIL("Contrôle de la valeur $type de '$fieldName' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
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