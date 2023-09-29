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
import tnrWebUI.*


@CompileStatic
public class SQL {


	private static final String CLASS_NAME = 'SQL'


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
		Log.addTraceBEGIN(CLASS_NAME,"static",[:])
		setNewInstance()
		Log.addTraceEND(CLASS_NAME,"static")
	}


	private static AllowedDBProfilNames getDBProfilBasedOnExecProfile() {
		Log.addTraceBEGIN(CLASS_NAME,"getDBProfilBasedOnExecProfile",[:])
		String execProfileName = RunConfiguration.getExecutionProfile()
		Log.addTrace("execProfileName '$execProfileName'")
		AllowedDBProfilNames allowedDBProfilNames
		try {
			allowedDBProfilNames = AllowedDBProfilNames.valueOf(execProfileName)
		} catch (IllegalArgumentException e) {
			Log.addErrorAndStop( "$execProfileName n'est pas une valeur valide d'AllowedDBProfilNames.")
		}
		Log.addTraceEND(CLASS_NAME,"getDBProfilBasedOnExecProfile",allowedDBProfilNames)
		return allowedDBProfilNames
	}


	static setNewInstance(AllowedDBProfilNames  name = getDBProfilBasedOnExecProfile()) {

		Log.addTraceBEGIN(CLASS_NAME,"setNewInstance",[nameStr:name.toString()])

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

		Log.addTraceEND(CLASS_NAME,"setNewInstance")
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

		Log.addTraceBEGIN(CLASS_NAME,"restore",[backupFilePath:backupFilePath])
		executeSQL("USE master")
		executeSQL("ALTER DATABASE ${databaseName} SET SINGLE_USER WITH ROLLBACK IMMEDIATE")
		executeSQL("RESTORE DATABASE ${databaseName} FROM DISK = '${backupFilePath}' WITH REPLACE")
		executeSQL("ALTER DATABASE ${databaseName} SET MULTI_USER")
		Log.addTraceEND(CLASS_NAME,"restore")
	}



	static String backup() {

		Log.addTraceBEGIN(CLASS_NAME,"backup",[:])

		String dateFile = new Date().format("yyyyMMdd_HHmmss")
		String backupFile = "${dateFile}-${profileName}_${databaseName}_${getMaintaVersion()}.bak"

		executeSQL("BACKUP DATABASE ${databaseName} TO DISK = '${backupFile}' WITH INIT, FORMAT")

		Log.addTraceEND(CLASS_NAME,"backup",backupFile)
		return backupFile
	}




	static executeSQL(String query) {

		Log.addTraceBEGIN(CLASS_NAME,"executeSQL",[query:query])

		try {
			sqlInstance.execute(query)
		}
		catch(Exception ex) {
			Log.addERROR("Erreur d'execution de execute($query) : ")
			TNRResult.addDETAIL("executeSQL()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_NAME,"executeSQL")
	}


	static Map getFirstRow(String query) {

		Log.addTraceBEGIN(CLASS_NAME,"getFirstRow",[query:query])

		Map map
		try {
			map = sqlInstance.firstRow(query)
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("getFirstRow()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_NAME,"getFirstRow",map)
		return map
	}




	static getFirstVal(String query) {

		Log.addTraceBEGIN(CLASS_NAME,"getFirstVal",[query:query])

		Map frow = getFirstRow(query)
		def ret = (frow) ? frow.values().first() : null

		Log.addTraceEND(CLASS_NAME,"getFirstVal",ret)
		return ret
	}







	static int checkIfExist(String table, String where) {

		Log.addTraceBEGIN(CLASS_NAME,"checkIfExist",[table:table,where:where])

		def fval = getFirstVal("SELECT count(*) FROM $table WHERE $where")
		int ret = (fval) ? fval as int: 0

		Log.addTraceEND(CLASS_NAME,"checkIfExist",ret)
		return ret
	}










	public static String checkValue(tnrJDDManager.JDD myJDD, String fieldName, def valDB,String verifStatus, Map specificValueMap, int casDeTestNum) {



		Log.addTraceBEGIN(CLASS_NAME,"checkValue",[myJDD:myJDD.toString() , fieldName:fieldName , val:valDB , verifStatus:verifStatus,specificValueMap:specificValueMap,casDeTestNum:casDeTestNum])

		def valJDD = myJDD.getData(fieldName,casDeTestNum)

		if (myJDD.myJDDParam.isOBSOLETE(fieldName)) {
			Log.addTraceEND(CLASS_NAME,"checkValue",verifStatus)
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
							def firstVal = getFirstVal(query)
							if (firstVal ) {
								def texte = new DefaultStyledDocument()
								def editorKit = new RTFEditorKit()
								editorKit.read(new StringReader(firstVal.toString()), texte , 0)
								valDB=texte.getText(0, texte.getLength()-1) // car il y a un CRLF !?
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

		Log.addTraceEND(CLASS_NAME,"checkValue",verifStatus)
		return verifStatus
	}




	private static logaddTrace(String type,String fieldName, def valJDD, def val) {
		Log.addTrace("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
	}



	private static logAddDETAIL(String type,String fieldName, def valJDD, def val) {
		TNRResult.addDETAIL("Contrôle de la valeur $type de '$fieldName' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
	}



	public static String getMaintaVersion() {

		Log.addTraceBEGIN(CLASS_NAME,"getMaintaVersion",[:])

		def fval =this.getFirstVal("SELECT ST_VAL FROM VER WHERE ID_CODINF = 'CURR_VERS'")
		String ret = (fval) ? fval.toString() : null

		Log.addTraceEND(CLASS_NAME,"getMaintaVersion",ret)
		return ret
	}


	private static boolean checkForeignKey(JDD myJDD, String fieldName, def valDB) {

		Log.addTraceBEGIN(CLASS_NAME,"checkForeignKey",[myJDD:myJDD.toString() ,fieldName:fieldName,val:valDB])

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
		Log.addTraceEND(CLASS_NAME,"checkForeignKey",pass)
		return pass
	}









	/**
	 * 
	 * @param myJDD
	 * @param casDeTestNum
	 * @return
	 */
	public static String getWhereWithAllPK(JDD myJDD,int casDeTestNum) {

		Log.addTraceBEGIN(CLASS_NAME,"getWhereWithAllPK",[myJDD:myJDD.toString() ,casDeTestNum:casDeTestNum])

		List <String> PKList = InfoDB.getPK(myJDD.getDBTableName())
		String ret =''
		if (PKList) {
			String query = ' WHERE '
			PKList.each {
				query = query + it + "='" + myJDD.getData(it,casDeTestNum,true) + "' and "
			}
			ret = query.substring(0,query.length()-5)
		}
		Log.addTraceEND(CLASS_NAME,"getWhereWithAllPK",ret)
		return ret
	}




	static int getMaxFromTable(String fieldName, String tableName) {

		Log.addTraceBEGIN(CLASS_NAME,"getMaxFromTable",[fieldName:fieldName, table:tableName])

		def fval = getFirstVal("SELECT MAX($fieldName) as num FROM $tableName")
		int ret = (fval) ? fval as int: 0

		Log.addTraceEND(CLASS_NAME,"getMaxFromTable",ret)
		return ret
	}




	static String getNumEcran(String table) {

		Log.addTraceBEGIN(CLASS_NAME,"getNumEcran",[table:table])
		def fval = getFirstVal("SELECT ID_NUMECR as num FROM OBJ where ST_NOMOBJ=$table")
		String ret = (fval) ? fval.toString(): null
		Log.addTraceEND(CLASS_NAME,"getNumEcran",ret)
		return ret
	}




	static Map getLibelle(String table, String numEcran) {

		Log.addTraceBEGIN(CLASS_NAME,"getLibelle",[table:table, numEcran:numEcran])

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
		Log.addTraceEND(CLASS_NAME,"getLibelle")
		return resultMap
	}
	

	static int getLastSequence(String seq) {
		Log.addTraceBEGIN(CLASS_NAME,"getLastSequence",[seq:seq])
		/*
		 String req = "SELECT IDENT_CURRENT('$seq') as lastSeq"
		 def result = sqlInstance.rows(req)
		 int lastSeq = (int)result[0].lastSeq
		 */
		def fval = getFirstVal("SELECT IDENT_CURRENT('$seq') as lastSeq")
		int ret = (fval) ? fval as int: 0

		Log.addTraceEND(CLASS_NAME,"getLastSequence",ret)
		return ret
	}



	static String getPathDB() {
		Log.addTrace(CLASS_NAME+".getPathDB () --> $pathDB")
		return pathDB
	}

	static String getDatabaseName() {
		Log.addTrace(CLASS_NAME+".getDatabaseName () --> $databaseName")
		return databaseName
	}

	static String getDBBackupPath() {
		Log.addTrace(CLASS_NAME+".getDBBackupPath () --> $DBBackupPath")
		return DBBackupPath
	}


	static String getProfileName() {
		Log.addTrace(CLASS_NAME+".getProfileName () --> $profileName")
		return profileName
	}

	static String close() {
		Log.addTrace(CLASS_NAME+".close()")
		return sqlInstance.close()
	}


	static List getRows(String query) {
		Log.addTrace(CLASS_NAME+".getRows (query:$query)")
		return sqlInstance.rows(query)
	}


} // Fin de class