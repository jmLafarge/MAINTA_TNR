package tnrSqlManager

import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit

import com.kms.katalon.core.configuration.RunConfiguration

import groovy.sql.Sql
import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrResultManager.TNRResult
import tnrWebUI.KW


@CompileStatic
public class SQL {


	private static final String CLASS_FOR_LOG = 'SQL'


	/**
	 * 
	 */

	static enum AllowedDBProfilNames {
		LOCALTNR,
		MASTERTNR,
		LEGACYTNR
	}


	private static String databaseName = ''
	private static Sql sqlInstance
	private static String pathDB = ''
	private static String profileName = ''
	private static String DBBackupPath = ''



	static {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"static",[:])
		setNewInstance()
		Log.addTraceEND(CLASS_FOR_LOG,"static")
	}


	private static AllowedDBProfilNames getDBProfilBasedOnExecProfile() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getDBProfilBasedOnExecProfile",[:])
		String execProfileName = RunConfiguration.getExecutionProfile()
		Log.addTrace("execProfileName '$execProfileName'")
		AllowedDBProfilNames allowedDBProfilNames
		try {
			allowedDBProfilNames = AllowedDBProfilNames.valueOf(execProfileName)
		} catch (IllegalArgumentException e) {
			Log.addErrorAndStop( "$execProfileName n'est pas une valeur valide d'AllowedDBProfilNames.")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getDBProfilBasedOnExecProfile",allowedDBProfilNames)
		return allowedDBProfilNames
	}


	static setNewInstance(AllowedDBProfilNames  name = getDBProfilBasedOnExecProfile()) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"setNewInstance",[nameStr:name.toString()])

		profileName = name.toString()

		String serverName 	= TNRPropertiesReader.getMyProperty("${profileName}_DBSERVER")
		String instanceName = TNRPropertiesReader.getMyProperty("${profileName}_DBINSTANCE")
		databaseName 		= TNRPropertiesReader.getMyProperty("${profileName}_DBNAME")
		String username 	= TNRPropertiesReader.getMyProperty("${profileName}_DBUSER")
		String pw 			= TNRPropertiesReader.getMyProperty("${profileName}_DBPW")
		DBBackupPath	= TNRPropertiesReader.getMyProperty("${profileName}_DBBACKUPPATH")

		String url = "jdbc:sqlserver:$serverName;instanceName=$instanceName;databaseName=$databaseName"
		Log.addTrace("url='$url'")

		pathDB = "$serverName/$instanceName/$databaseName"

		Log.addTrace("pathDB='$pathDB'")

		sqlInstance = Sql.newInstance(url, username, pw)

		Log.addTraceEND(CLASS_FOR_LOG,"setNewInstance")
	}



	static executeInsert(String query, List values) {
		Log.addTrace("executeInsert()")
		try {
			def resultat = sqlInstance.executeInsert(query,values)
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de executeInsert : ")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	static restore(String backupFilePath) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"restore",[backupFilePath:backupFilePath])
		executeSQL("USE master")
		executeSQL("ALTER DATABASE ${databaseName} SET SINGLE_USER WITH ROLLBACK IMMEDIATE")
		executeSQL("RESTORE DATABASE ${databaseName} FROM DISK = '${backupFilePath}' WITH REPLACE")
		executeSQL("ALTER DATABASE ${databaseName} SET MULTI_USER")
		Log.addTraceEND(CLASS_FOR_LOG,"restore")
	}



	static String backup() {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"backup",[:])

		String dateFile = new Date().format("yyyyMMdd_HHmmss")
		String backupFile = "${dateFile}-${profileName}_${databaseName}_${getMaintaVersion()}.bak"

		executeSQL("BACKUP DATABASE ${databaseName} TO DISK = '${backupFile}' WITH INIT, FORMAT")

		Log.addTraceEND(CLASS_FOR_LOG,"backup",backupFile)
		return backupFile
	}




	static executeSQL(String query) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"executeSQL",[query:query])

		try {
			sqlInstance.execute(query)
		}
		catch(Exception ex) {
			Log.addERROR("Erreur d'execution de execute($query) : ")
			TNRResult.addDETAIL("executeSQL()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG,"executeSQL")
	}


	static Map getFirstRow(String query) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getFirstRow",[query:query])

		Map map
		try {
			map = sqlInstance.firstRow(query)
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("getFirstRow()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getFirstRow",map)
		return map
	}




	static getFirstVal(String query) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getFirstVal",[query:query])

		Map frow = getFirstRow(query)
		def ret = (frow) ? frow.values().first() : null

		Log.addTraceEND(CLASS_FOR_LOG,"getFirstVal",ret)
		return ret
	}







	static int checkIfExist(String table, String where) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkIfExist",[table:table,where:where])

		def fval = getFirstVal("SELECT count(*) FROM $table WHERE $where")
		int ret = (fval) ? fval as int: 0

		Log.addTraceEND(CLASS_FOR_LOG,"checkIfExist",ret)
		return ret
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

		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkJDDWithBD",[:])
		KW.delay(1)

		String verifStatus = 'PASS'

		//TNRResult.addSTEP("Début de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
		TNRResult.addBeginBlock("Début de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')

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

				Log.addTrace("query =  $query")
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
					verifStatus = checkValue(myJDD,fieldName.toString(), val,verifStatus, specificValueMap,casDeTestNum)
				}//row

			}//pass
		}//for

		TNRResult.addEndBlock("Fin de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')',verifStatus)
		Log.addTraceEND(CLASS_FOR_LOG,"checkJDDWithBD")

	}





	private static String checkValue(tnrJDDManager.JDD myJDD, String fieldName, def valDB,String verifStatus, Map specificValueMap, int casDeTestNum) {



		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkValue",[myJDD:myJDD.toString() , fieldName:fieldName , val:valDB , verifStatus:verifStatus,specificValueMap:specificValueMap,casDeTestNum:casDeTestNum])

		def valJDD = myJDD.getData(fieldName,casDeTestNum)

		if (myJDD.myJDDParam.isOBSOLETE(fieldName)) {
			Log.addTraceEND(CLASS_FOR_LOG,"checkValue",verifStatus)
			return verifStatus
		}

		boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)

		//String paraIV = myJDD.getParamForThisName('INTERNALVALUE', fieldName)


		if (myJDD.isDataUPD(fieldName,casDeTestNum)) {

			String newVal = myJDD.getData(fieldName,casDeTestNum,true).toString()

			if (newVal==valDB.toString()) {
				logaddTrace('',fieldName,newVal,valDB)
			}else {
				logAddDETAIL('',fieldName,newVal,valDB)
				verifStatus = 'FAIL'
			}
		}else if (!specificValue && myJDD.myJDDParam.getFOREIGNKEYFor(fieldName)) {

			if (!JDDKW.isNU(valJDD) && !JDDKW.isNULL(valJDD)) {
				if (!checkForeignKey(myJDD, fieldName, valDB)) {
					verifStatus= 'FAIL'
				}
			}else {
				Log.addTrace("$fieldName est NU ou NULL, pas de recherche de FK")
			}
			/*
			 }else if (!specificValue && paraIV){
			 String internalVal = IV.getInternalValueOf(paraIV,valJDD.toString())
			 if (internalVal==valDB) {
			 Log.addTrace("Contrôle de la valeur INTERNAL de '$fieldName' pour '$valDB' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$internalVal'" )
			 }else {
			 TNRResult.addDETAIL("Contrôle de la valeur INTERNAL de '$fieldName' pour '$valDB' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$internalVal'")
			 verifStatus = 'FAIL'
			 }
			 */
		}else {

			switch (valJDD) {

				case JDDKW.getKW_NU() :

					Log.addTrace("NU : Pas de contrôle pour '$fieldName' : la valeur en BD est  : '$valDB'" )
					break

				case JDDKW.getKW_VIDE() :
				case JDDKW.getKW_NULL():
					if (valDB == null || valDB =='') {

						logaddTrace('',fieldName,'VIDE ou NULL',valDB)
					}else {
						logAddDETAIL('',fieldName,'VIDE ou NULL',valDB)
						verifStatus= 'FAIL'
					}

					break

				case JDDKW.getKW_DATE() :

					if (valDB instanceof java.sql.Timestamp) {
						logaddTrace('DATEJML',fieldName,valJDD,valDB)
					}else {
						logAddDETAIL('DATEJML',fieldName,valJDD,valDB)
						verifStatus= 'FAIL'
					}
					break

				case JDDKW.getKW_DATETIME() :

					if (valDB instanceof java.sql.Timestamp) {
						logaddTrace('DATETIME',fieldName,valJDD,valDB)
					}else {
						logAddDETAIL('DATETIME',fieldName,valJDD,valDB)
						verifStatus= 'FAIL'
					}
					break

				case JDDKW.getKW_SEQUENCEID() :
				/*
				 String paraSeq =  myJDD.getParamForThisName('SEQUENCE', fieldName)
				 int lastSeq = getLastSequence( paraSeq)
				 if ( val == lastSeq) {
				 logaddTrace('SEQUENCEID',fieldName,lastSeq,val)
				 }else {
				 */
					logAddDETAIL('SEQUENCEID',fieldName,valJDD,valDB)
					verifStatus= 'FAIL'
				//}

					break

				case JDDKW.getKW_ORDRE() :

					logAddDETAIL('ORDRE ***** reste àfaire *****',fieldName,valJDD,valDB)
				//voir aussi le NU_NIV *******
					verifStatus= 'FAIL'
					break

				default:

					if (specificValue) {

						String valClass = valDB ? valDB.getClass() : 'NULL'
						Log.addTrace("Pour '$fieldName' la class de la valeur en BD est:$valDB,  la class de la valeur spécifique est  : " + specificValueMap[fieldName].getClass())


						if ( valDB == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
							logaddTrace('spécifique',fieldName,specificValueMap[fieldName],valDB)
						}else {
							logAddDETAIL('spécifique',fieldName,specificValueMap[fieldName],valDB)
							verifStatus = 'FAIL'
						}
					}else {

						if (InfoDB.isImage(myJDD.getDBTableName(), fieldName)) {

							String query = "SELECT cast(cast($fieldName as varbinary(max)) as varchar(max)) FROM " + myJDD.getDBTableName() + getWhereWithAllPK(myJDD,casDeTestNum)
							Map frow = getFirstRow(query)
							if (frow ) {
								def texte = new DefaultStyledDocument()

								def editorKit = new RTFEditorKit()
								editorKit.read(new StringReader(frow[0].toString()), texte , 0)
								valDB = texte.getText(0, texte.getLength()-2)

							}
						}


						if ( valDB == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, valJDD)) {
							logaddTrace('',fieldName,valJDD,valDB)
						}else {
							logAddDETAIL('',fieldName,valJDD,valDB)
							verifStatus = 'FAIL'
						}
					}
					break
			}//case
		}

		Log.addTraceEND(CLASS_FOR_LOG,"checkValue",verifStatus)
		return verifStatus
	}




	private static logaddTrace(String type,String fieldName, def valJDD, def val) {
		Log.addTrace("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
	}



	private static logAddDETAIL(String type,String fieldName, def valJDD, def val) {
		TNRResult.addDETAIL("Contrôle de la valeur $type de '$fieldName' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
	}



	public static String getMaintaVersion() {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getMaintaVersion",[:])

		def fval =this.getFirstVal("SELECT ST_VAL FROM VER WHERE ID_CODINF = 'CURR_VERS'")
		String ret = (fval) ? fval.toString() : null

		Log.addTraceEND(CLASS_FOR_LOG,"getMaintaVersion",ret)
		return ret
	}


	private static boolean checkForeignKey(JDD myJDD, String fieldName, def valDB) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkForeignKey",[myJDD:myJDD.toString(),fieldName:fieldName,val:valDB])

		boolean pass = false
		String query = myJDD.getSqlForForeignKey(fieldName)
		def fval = getFirstVal(query)
		if (fval == null) {
			TNRResult.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO, pas de résultat pour la query : $query")
		}else if (valDB != fval) {
			TNRResult.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO : ** la valeur du JDD attendue est : " + myJDD.getData(fieldName) + " ($fval) et la valeur est BD est : " +  valDB)
		}else {
			Log.addTrace("Contrôle de la valeur de $fieldName (FK) OK : la valeur du JDD attendue est : " + myJDD.getData(fieldName) + " ($fval) et la valeur est BD est : " +  valDB)
			pass = true
		}
		Log.addTraceEND(CLASS_FOR_LOG,"checkForeignKey",pass)
		return pass
	}




	/**
	 * Dans le cas d'une vérif 	à la fin des insertions, donc dans le cas de 2 insertions, il faut boucler sur le nombre d'insertions
	 * @param myJDD
	 * 
	 * @return
	 */

	static checkIDNotInBD(JDD myJDD){

		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkIDNotInBD",[myJDD:myJDD.toString()])

		KW.delay(1)
		//boolean pass = true
		String status = 'PASS'
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()

		TNRResult.addBeginBlock("Début de la vérification de la suppression des valeurs en Base de Données")

		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				TNRResult.addSUBSTEP("Contrôle de la suppression du cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}

			def fval =this.getFirstVal("SELECT count(*) FROM " + myJDD.getDBTableName() + getWhereWithAllPK(myJDD,casDeTestNum))
			int ret = (fval) ? fval as int: 0
			if (ret>0) {
				TNRResult.addDETAIL("Supression KO")
				//pass = false
				status = 'FAIL'
			}

		}

		TNRResult.addEndBlock("Fin de la  vérification de la suppression des valeurs en Base de Données",status)
		Log.addTraceEND(CLASS_FOR_LOG,"checkIDNotInBD")
	}



	/**
	 * 
	 * @param myJDD
	 * @param casDeTestNum
	 * @return
	 */
	private static String getWhereWithAllPK(JDD myJDD,int casDeTestNum) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getWhereWithAllPK",[myJDD:myJDD.toString(),casDeTestNum:casDeTestNum])

		List <String> PKList = InfoDB.getPK(myJDD.getDBTableName())
		String ret =''
		if (PKList) {
			String query = ' WHERE '
			PKList.each {
				query = query + it + "='" + myJDD.getData(it,casDeTestNum,true) + "' and "
			}
			ret = query.substring(0,query.length()-5)
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getWhereWithAllPK",ret)
		return ret
	}




	static int getMaxFromTable(String fieldName, String tableName) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getMaxFromTable",[fieldName:fieldName, table:tableName])

		def fval = getFirstVal("SELECT MAX($fieldName) as num FROM $tableName")
		int ret = (fval) ? fval as int: 0

		Log.addTraceEND(CLASS_FOR_LOG,"getMaxFromTable",ret)
		return ret
	}




	static String getNumEcran(String table) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getNumEcran",[table:table])
		def fval = getFirstVal("SELECT ID_NUMECR as num FROM OBJ where ST_NOMOBJ=$table")
		String ret = (fval) ? fval.toString(): null
		Log.addTraceEND(CLASS_FOR_LOG,"getNumEcran",ret)
		return ret
	}




	static Map getLibelle(String table, String numEcran) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getLibelle",[table:table, numEcran:numEcran])

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
		Log.addTrace("map.size : " + resultMap.size())
		Log.addTraceEND(CLASS_FOR_LOG,"getLibelle")
		return resultMap
	}

	static int getLastSequence(String seq) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getLastSequence",[seq:seq])
		/*
		 String req = "SELECT IDENT_CURRENT('$seq') as lastSeq"
		 def result = sqlInstance.rows(req)
		 int lastSeq = (int)result[0].lastSeq
		 */
		def fval = getFirstVal("SELECT IDENT_CURRENT('$seq') as lastSeq")
		int ret = (fval) ? fval as int: 0

		Log.addTraceEND(CLASS_FOR_LOG,"getLastSequence",ret)
		return ret
	}



	static String getPathDB() {
		return pathDB
	}

	static String getDatabaseName() {
		return databaseName
	}

	static String getDBBackupPath() {
		return DBBackupPath
	}


	static String getProfileName() {
		Log.addTrace("SQL.getProfileName : $profileName")
		return profileName
	}

	static String close() {
		return sqlInstance.close()
	}


} // Fin de class