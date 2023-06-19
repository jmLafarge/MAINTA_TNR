package my

import groovy.transform.CompileStatic

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.sql.Sql
import internal.GlobalVariable
import my.Log
import my.InfoBDD
import my.JDDKW
import my.result.TNRResult

//Pour la lecture du format RTF
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit
import java.io.StringReader


@CompileStatic
public class SQL {
	/**
	 * 
	 */


	private static Sql sqlInstance = Sql.newInstance(GlobalVariable.BDD_URL.toString(), GlobalVariable.BDD_USER.toString(), GlobalVariable.BDD_MDP.toString())



	static executeInsert(String query, List values) {
		try {
			def resultat = sqlInstance.executeInsert(query,values)
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de executeInsert : ")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}



	static executeSQL(String query) {
		try {
			sqlInstance.execute(query)
		}
		catch(Exception ex) {
			Log.addERROR("Erreur d'execution de execute($query) : ")
			TNRResult.addDETAIL("executeSQL()")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	static Map getFirstRow(String query) {
		try {
			Log.addDEBUG("getFirstRow($query)")
			return sqlInstance.firstRow(query)
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("getFirstRow()")
			TNRResult.addDETAIL(ex.getMessage())
			return null
		}
	}


	static int checkIfExist(String table, String where) {

		String sql = "SELECT count(*) as nbr FROM $table WHERE $where"
		Map result = SQL.getFirstRow("$sql")
		if(result) {
			return result.nbr as int
		}else {
			return 0
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

		TNRResult.addSTEP("Début de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')

		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()


		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				TNRResult.addSUBSTEP("Contrôle cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}

			def rows
			def row

			String query =''
			if (sql) {
				query = sql
				try {
					row = sqlInstance.firstRow(query)
				} catch(Exception ex) {
					TNRResult.addDETAIL("Erreur d'execution de sqlInstance.firstRow($query) : ")
					TNRResult.addDETAIL(ex.getMessage())
					verifStatus = 'ERROR'
				}
			}else {

				query = "SELECT * FROM " + myJDD.getDBTableName() + getWhereWithAllPK(myJDD,casDeTestNum)

				Log.addDEBUG("query =  $query")
				try {
					rows = sqlInstance.rows(query)
					if (rows.size() == 0) {
						TNRResult.addDETAIL("Pas de résultat pour la requête : $query")
						verifStatus = 'FAIL'
					}else if (rows.size() > 1){
						TNRResult.addDETAIL(rows.size() + "résultats pour la requête : $query")
						verifStatus = 'FAIL'
					}else {
						row=rows[0]
					}
				}
				catch(Exception ex) {
					verifStatus = 'ERROR'
					TNRResult.addDETAIL("Erreur d'execution de sql.rows($query) : ")
					TNRResult.addDETAIL(ex.getMessage())
				}
			}



			if (verifStatus =='PASS') {

				row.each{fieldName,val ->

					Log.addDEBUG("fieldName = $fieldName , val = $val , JDD value = " + myJDD.getData(fieldName.toString()))

					verifStatus = checkValue(myJDD,fieldName.toString(), val,verifStatus, specificValueMap,casDeTestNum)
				}//row

			}//pass
		}//for

		switch (verifStatus) {
			case 'PASS':
				TNRResult.addSTEPPASS("Fin de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
				break
			case 'FAIL':
				TNRResult.addSTEPFAIL("Fin de la  vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
				break
			case 'ERROR':
				TNRResult.addSTEPERROR("Fin de la  vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
				break
			default :
				TNRResult.addDETAIL("verifStatus inconnu '$verifStatus'")
				TNRResult.addSTEPERROR("Fin de la  vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')

				break
		}

	}





	private static String checkValue(my.JDD myJDD, String fieldName, val,String verifStatus, Map specificValueMap, int casDeTestNum) {

		Log.addDEBUG("checkValue : fieldName=$fieldName val=$val verifStatus=$verifStatus casDeTestNum=$casDeTestNum" )

		if (myJDD.isOBSOLETE(fieldName)) return verifStatus

		boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)

		String IV = myJDD.getParamForThisName('INTERNALVALUE', fieldName)


		if (!specificValue && myJDD.getParamForThisName('FOREIGNKEY', fieldName)) {

			if (!JDDKW.isNU(myJDD.getData(fieldName))) {
				if (!checkForeignKey(myJDD, fieldName, val)) verifStatus= 'FAIL'
			}else {
				Log.addDEBUG("$fieldName est NULL, pas de recherche de FK")
			}

		}else if (!specificValue && IV){

			String internalVal = my.PropertiesReader.getMyProperty('IV_' + IV + '_' + val.toString())

			if (internalVal==val) {
				Log.addDEBUG("Contrôle de la valeur INTERNAL de '$fieldName' pour '$val' OK : la valeur attendue est '" + myJDD.getData(fieldName) + "' et la valeur en BD est  : '$internalVal'" )
			}else {
				TNRResult.addDETAIL("Contrôle de la valeur INTERNAL de '$fieldName' pour '$val' KO : la valeur attendue est '" + myJDD.getData(fieldName) + "' et la valeur en BD est  : '$internalVal'")
				verifStatus = 'FAIL'
			}

		}else {

			switch (myJDD.getData(fieldName)) {

				case JDDKW.getKW_NU() :

					Log.addDEBUG("NU : Pas de contrôle pour '$fieldName' : la valeur en BD est  : '$val'" )
					break

				case JDDKW.getKW_VIDE() :
				case JDDKW.getKW_NULL():
					if (val == null || val =='') {

						logAddDEBUG('',fieldName,'VIDE ou NULL',val)
					}else {
						logAddDETAIL('',fieldName,'VIDE ou NULL',val)
						verifStatus= 'FAIL'
					}

					break

				case JDDKW.getKW_DATE() :

					logAddDETAIL('DATE ***** reste à faire *****',fieldName,myJDD.getData(fieldName),val)
					verifStatus= 'FAIL'
					break

				case JDDKW.getKW_DATETIME() :

					if (val instanceof java.sql.Timestamp) {
						logAddDEBUG('DATETIME',fieldName,myJDD.getData(fieldName),val)
					}else {
						logAddDETAIL('DATETIME',fieldName,myJDD.getData(fieldName),val)
						verifStatus= 'FAIL'
					}
					break

				case JDDKW.getKW_SEQUENCEID() :
				/*
				 String paraSeq =  myJDD.getParamForThisName('SEQUENCE', fieldName)
				 int lastSeq = getLastSequence( paraSeq)
				 if ( val == lastSeq) {
				 logAddDEBUG('SEQUENCEID',fieldName,lastSeq,val)
				 }else {
				 */
					logAddDETAIL('SEQUENCEID',fieldName,myJDD.getData(fieldName),val)
					verifStatus= 'FAIL'
				//}

					break

				case JDDKW.getKW_ORDRE() :

					logAddDETAIL('ORDRE ***** reste àfaire *****',fieldName,myJDD.getData(fieldName),val)
				//voir aussi le NU_NIV *******
					verifStatus= 'FAIL'
					break

				default:

					Log.addDEBUG("checkValue case default")

					if (specificValue) {

						String valClass = val ? val.getClass() : 'NULL'
						Log.addDEBUG("Pour '$fieldName' la class de la valeur en BD est:$val,  la class de la valeur spécifique est  : " + specificValueMap[fieldName].getClass())


						if ( val == InfoBDD.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
							logAddDEBUG('spécifique',fieldName,specificValueMap[fieldName],val)
						}else {
							logAddDETAIL('spécifique',fieldName,specificValueMap[fieldName],val)
							verifStatus = 'FAIL'
						}
					}else {

						Log.addDEBUG("checkValue case default else")

						if (InfoBDD.isImage(myJDD.getDBTableName(), fieldName)) {

							String query = "SELECT cast(cast($fieldName as varbinary(max)) as varchar(max)) FROM " + myJDD.getDBTableName() + getWhereWithAllPK(myJDD,casDeTestNum)
							Map frow = getFirstRow(query)
							if (frow ) {
								def texte = new DefaultStyledDocument()

								def editorKit = new RTFEditorKit()
								editorKit.read(new StringReader(frow[0].toString()), texte , 0)
								val = texte.getText(0, texte.getLength()-2)

							}
						}

						Log.addDEBUG("checkValue case default else 2")


						if ( val == InfoBDD.castJDDVal(myJDD.getDBTableName(), fieldName, myJDD.getData(fieldName))) {
							logAddDEBUG('',fieldName,myJDD.getData(fieldName),val)
						}else {
							logAddDETAIL('',fieldName,myJDD.getData(fieldName),val)
							verifStatus = 'FAIL'
						}
					}
					break
			}//case
		}
		return verifStatus
	}




	private static logAddDEBUG(String type,String fieldName, def valJDD, def val) {
		Log.addDEBUG("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
	}



	private static logAddDETAIL(String type,String fieldName, def valJDD, def val) {
		TNRResult.addDETAIL("Contrôle de la valeur $type de '$fieldName' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
	}



	public static String getMaintaVersion() {

		String query = "SELECT ST_VAL FROM VER WHERE ID_CODINF = 'CURR_VERS'"

		try {
			def frow = sqlInstance.firstRow(query)
			if (frow ) {
				return frow.getAt(0).toString()
			}else {
				Log.addDEBUG("getMaintaVersion() est null")
				return null
			}
		}catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("getMaintaVersion()")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	private static boolean checkForeignKey(JDD myJDD, String fieldName, def val) {
		boolean pass = false
		String query = myJDD.getSqlForForeignKey(fieldName)
		try {
			def frow = sqlInstance.firstRow(query)
			if (frow == null) {
				TNRResult.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO, pas de résultat pour la query : $query")
			}else if (val != frow.getAt(0)) {
				TNRResult.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO : ** la valeur du JDD attendue est : " + myJDD.getData(fieldName) + ' et la valeur est BD est : ' +  frow.getAt(0))
			}else {
				Log.addDEBUG("Contrôle de la valeur de $fieldName (FK) OK : la valeur attendue est : " + frow.getAt(0) + " et la valeur en BD est : $val")
				pass = true
			}
		}catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("checkForeignKey()")
			TNRResult.addDETAIL(ex.getMessage())
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

		TNRResult.addSTEP("Début de la vérification de la suppression des valeurs en Base de Données")
		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				TNRResult.addSUBSTEP("Contrôle de la suppression du cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}
			String query = "SELECT count(*) FROM " + myJDD.getDBTableName() + getWhereWithAllPK(myJDD,casDeTestNum)

			Log.addDEBUG("query =  $query")

			def row

			try {
				row = sqlInstance.firstRow(query)

			}catch(Exception ex) {
				pass = false
				Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
				TNRResult.addDETAIL("checkIDNotInBD()")
				TNRResult.addDETAIL(ex.getMessage())
			}

			if ((Integer)row[0]>0) {
				TNRResult.addDETAIL("Supression KO")
				pass = false
			}
		}

		if (pass) {
			TNRResult.addSTEPPASS("Fin de la vérification de la suppression des valeurs en Base de Données")
			//KeywordUtil.markPassed("Supression OK")
		}else {
			TNRResult.addSTEPFAIL("Fin de la  vérification de la suppression des valeurs en Base de Données")
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
		List <String> PKList = InfoBDD.getPK(myJDD.getDBTableName())
		if (PKList) {
			String query = ' WHERE '
			PKList.each {
				query = query + it + "='" + myJDD.getData(it,casDeTestNum) + "' and "
			}
			return query.substring(0,query.length()-5)
		}
		return ''
	}




	/*
	 static insertSQL(String req) {
	 try {
	 def nbRowInserted = sqlInstance.executeInsert req
	 if (nbRowInserted >= 0) {
	 Log.addDEBUG("insertSQL($req) OK, nombre de ligne inséré : ${nbRowInserted}")
	 }else {
	 Log.addERROR("Erreur de insertSQL()")
	 TNRResult.addDETAIL(req)
	 }
	 return nbRowInserted
	 }
	 catch(Exception ex) {
	 Log.addERROR("Erreur d'execution de insertSQL() : $req ")
	 TNRResult.addDETAIL(ex.getMessage())
	 return -1
	 }
	 }
	 */


	static int getMaxFromTable(String fieldName, String tableName) {

		Log.addDEBUG("getMaxFromTable(String fieldName, String tableName) '$fieldName' , '$tableName'")

		String req = "SELECT MAX($fieldName) as num FROM $tableName"

		try {
			def res = sqlInstance.firstRow(req).num

			int num = (res) ? (Integer)res : 0

			TNRResult.addDETAIL("get Max '$fieldName From Table '$tableName' = $num")
			return num
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de firstRow($req) : ")
			TNRResult.addDETAIL("getMaxFromTable()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		return null
	}


	static getNumEcran(String table) {

		def query = "SELECT ID_NUMECR as num FROM OBJ where ST_NOMOBJ=$table"

		try {
			def res = sqlInstance.firstRow(query)
			def ret = (res) ? res.num : null
			TNRResult.addDETAIL("getNumEcran($table) = $ret")
			return ret
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de firstRow($query) : ")
			TNRResult.addDETAIL("getNumEcran()")
			TNRResult.addDETAIL(ex.getMessage())
			return null
		}
	}

	static Map getLibelle(String table, numEcran) {

		TNRResult.addDETAIL("Recherche des libellés pour la table $table et numéro écran $numEcran")
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

	static int getLastSequence(String seq) {

		String req = "SELECT IDENT_CURRENT('$seq') as lastSeq"
		def result = sqlInstance.rows(req)
		int lastSeq = (int)result[0].lastSeq
		Log.addDEBUG("getLastSequence of $seq : $lastSeq")
		return lastSeq
	}


} // end of class