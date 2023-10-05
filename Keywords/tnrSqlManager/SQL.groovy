package tnrSqlManager

import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit

import com.kms.katalon.core.configuration.RunConfiguration

import groovy.sql.Sql
import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult


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






	public static String getMaintaVersion() {

		Log.addTraceBEGIN(CLASS_NAME,"getMaintaVersion",[:])

		def fval =this.getFirstVal("SELECT ST_VAL FROM VER WHERE ID_CODINF = 'CURR_VERS'")
		String ret = (fval) ? fval.toString() : null

		Log.addTraceEND(CLASS_NAME,"getMaintaVersion",ret)
		return ret
	}


	public static getValueFromForeignKey(JDD myJDD, String fieldName) {
		Log.addTraceBEGIN(CLASS_NAME,"getValueFromForeignKey",[myJDD:myJDD.toString() ,fieldName:fieldName])
		String query = myJDD.getSqlForForeignKey(fieldName)
		def fval = getFirstVal(query)
		Log.addTraceEND(CLASS_NAME,"getValueFromForeignKey",fval)
		return fval
	}



	public static String getTextFromImageType(JDD myJDD, String fieldName) {
		Log.addTraceBEGIN(CLASS_NAME,"getTextFromImageType",[myJDD:myJDD.toString()])
		String query = "SELECT cast(cast($fieldName as varbinary(max)) as varchar(max)) FROM " + myJDD.getDBTableName() + getWhereWithAllPK(myJDD)
		def firstVal = SQL.getFirstVal(query)
		String textValue =''
		if (firstVal ) {
			def texte = new DefaultStyledDocument()
			def editorKit = new RTFEditorKit()
			editorKit.read(new StringReader(firstVal.toString()), texte , 0)
			//textValue = texte.getText(0, texte.getLength()-1) // car il y a un CRLF !?
			textValue = texte.getText(0, texte.getLength())
		}
		Log.addTraceEND(CLASS_NAME,"getTextFromImageType",textValue)
		return textValue
	}




	/**
	 * 
	 * @param myJDD
	 * @param casDeTestNum
	 * @return
	 */
	public static String getWhereWithAllPK(JDD myJDD) {
		Log.addTraceBEGIN(CLASS_NAME,"getWhereWithAllPK",[myJDD:myJDD.toString()])
		List <String> PKList = InfoDB.getPK(myJDD.getDBTableName())
		String ret =''
		if (PKList) {
			String query = ' WHERE '
			PKList.each {
				query = query + it + "='" + myJDD.getData(it,myJDD.getCasDeTestNum(),true) + "' and "
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