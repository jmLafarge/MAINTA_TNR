package my

import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit

import com.kms.katalon.core.configuration.RunConfiguration

import groovy.sql.Sql
import groovy.transform.CompileStatic
import my.result.TNRResult


@CompileStatic
public class SQL {


	private static final String CLASS_FORLOG = 'SQL'


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
		Log.addTraceBEGIN(CLASS_FORLOG,"static",[:])
		setNewInstance()
		Log.addTraceEND(CLASS_FORLOG,"static")
	}


	private static AllowedDBProfilNames getDBProfilBasedOnExecProfile() {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDBProfilBasedOnExecProfile",[:])
		String execProfileName = RunConfiguration.getExecutionProfile()
		Log.addTrace("execProfileName '$execProfileName'")
		AllowedDBProfilNames allowedDBProfilNames
		try {
			allowedDBProfilNames = AllowedDBProfilNames.valueOf(execProfileName)
		} catch (IllegalArgumentException e) {
			Log.addErrorAndStop( "$execProfileName n'est pas une valeur valide d'AllowedDBProfilNames.")
		}
		Log.addTraceEND(CLASS_FORLOG,"getDBProfilBasedOnExecProfile",allowedDBProfilNames)
		return allowedDBProfilNames
	}


	static setNewInstance(AllowedDBProfilNames  name = getDBProfilBasedOnExecProfile()) {

		Log.addTraceBEGIN(CLASS_FORLOG,"setNewInstance",[nameStr:name.toString()])

		profileName = name.toString()

		String serverName 	= PropertiesReader.getMyProperty("${profileName}_DBSERVER")
		String instanceName = PropertiesReader.getMyProperty("${profileName}_DBINSTANCE")
		databaseName 		= PropertiesReader.getMyProperty("${profileName}_DBNAME")
		String username 	= PropertiesReader.getMyProperty("${profileName}_DBUSER")
		String pw 			= PropertiesReader.getMyProperty("${profileName}_DBPW")
		DBBackupPath	= PropertiesReader.getMyProperty("${profileName}_DBBACKUPPATH")

		String url = "jdbc:sqlserver:$serverName;instanceName=$instanceName;databaseName=$databaseName"
		Log.addTrace("url='$url'")

		pathDB = "$serverName/$instanceName/$databaseName"

		Log.addTrace("pathDB='$pathDB'")

		sqlInstance = Sql.newInstance(url, username, pw)

		Log.addTraceEND(CLASS_FORLOG,"setNewInstance")
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

		Log.addTraceBEGIN(CLASS_FORLOG,"restore",[backupFilePath:backupFilePath])
		executeSQL("USE master")
		executeSQL("ALTER DATABASE ${databaseName} SET SINGLE_USER WITH ROLLBACK IMMEDIATE")
		executeSQL("RESTORE DATABASE ${databaseName} FROM DISK = '${backupFilePath}' WITH REPLACE")
		executeSQL("ALTER DATABASE ${databaseName} SET MULTI_USER")
		Log.addTraceEND(CLASS_FORLOG,"restore")
	}



	static String backup() {

		Log.addTraceBEGIN(CLASS_FORLOG,"backup",[:])

		String dateFile = new Date().format("yyyyMMdd_HHmmss")
		String backupFile = "${dateFile}-${profileName}_${databaseName}_${getMaintaVersion()}.bak"

		executeSQL("BACKUP DATABASE ${databaseName} TO DISK = '${backupFile}' WITH INIT, FORMAT")

		Log.addTraceEND(CLASS_FORLOG,"backup",backupFile)
		return backupFile
	}




	static executeSQL(String query) {

		Log.addTraceBEGIN(CLASS_FORLOG,"executeSQL",[query:query])

		try {
			sqlInstance.execute(query)
		}
		catch(Exception ex) {
			Log.addERROR("Erreur d'execution de execute($query) : ")
			TNRResult.addDETAIL("executeSQL()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FORLOG,"executeSQL")
	}


	static Map getFirstRow(String query) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getFirstRow",[query:query])

		Map map
		try {
			map = sqlInstance.firstRow(query)
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("getFirstRow()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FORLOG,"getFirstRow",map)
		return map
	}


	static int checkIfExist(String table, String where) {

		Log.addTraceBEGIN(CLASS_FORLOG,"checkIfExist",[table:table,where:where])

		String sql = "SELECT count(*) as nbr FROM $table WHERE $where"
		Map result = SQL.getFirstRow("$sql")
		int ret = 0
		if(result) {
			ret =  result.nbr as int
		}
		Log.addTraceEND(CLASS_FORLOG,"checkIfExist",ret)
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

		Log.addTraceBEGIN(CLASS_FORLOG,"checkJDDWithBD",[:])
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

					Log.addTrace("fieldName = $fieldName , val = $val , JDD value = " + myJDD.getData(fieldName.toString()))

					verifStatus = checkValue(myJDD,fieldName.toString(), val,verifStatus, specificValueMap,casDeTestNum)
				}//row

			}//pass
		}//for

		TNRResult.addEndBlock("Fin de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')',verifStatus)
		Log.addTraceEND(CLASS_FORLOG,"checkJDDWithBD")

	}





	private static String checkValue(my.JDD myJDD, String fieldName, val,String verifStatus, Map specificValueMap, int casDeTestNum) {



		Log.addTraceBEGIN(CLASS_FORLOG,"checkValue",[myJDD:myJDD.toString(),fieldName:fieldName,verifStatus:verifStatus,specificValueMap:specificValueMap,casDeTestNum:casDeTestNum])

		if (myJDD.isOBSOLETE(fieldName)) {
			Log.addTraceEND(CLASS_FORLOG,"checkValue",verifStatus)
			return verifStatus
		}

		boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)

		String IV = myJDD.getParamForThisName('INTERNALVALUE', fieldName)


		if (!specificValue && myJDD.getParamForThisName('FOREIGNKEY', fieldName)) {

			if (!JDDKW.isNU(myJDD.getData(fieldName)) && !JDDKW.isNULL(myJDD.getData(fieldName))) {
				if (!checkForeignKey(myJDD, fieldName, val)) verifStatus= 'FAIL'
			}else {
				Log.addTrace("$fieldName est NU ou NULL, pas de recherche de FK")
			}

		}else if (!specificValue && IV){

			String internalVal = NAV.myGlobalJDD.getInternalValueOf(IV,val.toString())


			if (internalVal==val) {
				Log.addTrace("Contrôle de la valeur INTERNAL de '$fieldName' pour '$val' OK : la valeur attendue est '" + myJDD.getData(fieldName) + "' et la valeur en BD est  : '$internalVal'" )
			}else {
				TNRResult.addDETAIL("Contrôle de la valeur INTERNAL de '$fieldName' pour '$val' KO : la valeur attendue est '" + myJDD.getData(fieldName) + "' et la valeur en BD est  : '$internalVal'")
				verifStatus = 'FAIL'
			}

		}else {

			switch (myJDD.getData(fieldName)) {

				case JDDKW.getKW_NU() :

					Log.addTrace("NU : Pas de contrôle pour '$fieldName' : la valeur en BD est  : '$val'" )
					break

				case JDDKW.getKW_VIDE() :
				case JDDKW.getKW_NULL():
					if (val == null || val =='') {

						logaddTrace('',fieldName,'VIDE ou NULL',val)
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
						logaddTrace('DATETIME',fieldName,myJDD.getData(fieldName),val)
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
				 logaddTrace('SEQUENCEID',fieldName,lastSeq,val)
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

					if (specificValue) {

						String valClass = val ? val.getClass() : 'NULL'
						Log.addTrace("Pour '$fieldName' la class de la valeur en BD est:$val,  la class de la valeur spécifique est  : " + specificValueMap[fieldName].getClass())


						if ( val == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
							logaddTrace('spécifique',fieldName,specificValueMap[fieldName],val)
						}else {
							logAddDETAIL('spécifique',fieldName,specificValueMap[fieldName],val)
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
								val = texte.getText(0, texte.getLength()-2)

							}
						}


						if ( val == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, myJDD.getData(fieldName))) {
							logaddTrace('',fieldName,myJDD.getData(fieldName),val)
						}else {
							logAddDETAIL('',fieldName,myJDD.getData(fieldName),val)
							verifStatus = 'FAIL'
						}
					}
					break
			}//case
		}

		Log.addTraceEND(CLASS_FORLOG,"checkValue",verifStatus)
		return verifStatus
	}




	private static logaddTrace(String type,String fieldName, def valJDD, def val) {
		Log.addTrace("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
	}



	private static logAddDETAIL(String type,String fieldName, def valJDD, def val) {
		TNRResult.addDETAIL("Contrôle de la valeur $type de '$fieldName' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
	}



	public static String getMaintaVersion() {

		Log.addTraceBEGIN(CLASS_FORLOG,"getMaintaVersion",[:])

		String query = "SELECT ST_VAL FROM VER WHERE ID_CODINF = 'CURR_VERS'"
		String ret
		try {
			def frow = sqlInstance.firstRow(query)
			if (frow ) {
				ret = frow.getAt(0).toString()
			}
		}catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("getMaintaVersion()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FORLOG,"getMaintaVersion",ret)
		return ret
	}


	private static boolean checkForeignKey(JDD myJDD, String fieldName, def val) {

		Log.addTraceBEGIN(CLASS_FORLOG,"checkForeignKey",[myJDD:myJDD.toString(),fieldName:fieldName,val:val])

		boolean pass = false
		String query = myJDD.getSqlForForeignKey(fieldName)
		try {
			def frow = sqlInstance.firstRow(query)
			if (frow == null) {
				TNRResult.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO, pas de résultat pour la query : $query")
			}else if (val != frow.getAt(0)) {
				TNRResult.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO : ** la valeur du JDD attendue est : " + myJDD.getData(fieldName) + ' et la valeur est BD est : ' +  frow.getAt(0))
			}else {
				Log.addTrace("Contrôle de la valeur de $fieldName (FK) OK : la valeur attendue est : " + frow.getAt(0) + " et la valeur en BD est : $val")
				pass = true
			}
		}catch(Exception ex) {
			Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			TNRResult.addDETAIL("checkForeignKey()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FORLOG,"checkForeignKey",pass)
		return pass
	}




	/**
	 * Dans le cas d'une vérif 	à la fin des insertions, donc dans le cas de 2 insertions, il faut boucler sur le nombre d'insertions
	 * @param myJDD
	 * 
	 * @return
	 */

	static checkIDNotInBD(JDD myJDD){

		Log.addTraceBEGIN(CLASS_FORLOG,"checkIDNotInBD",[myJDD:myJDD.toString()])

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
			String query = "SELECT count(*) FROM " + myJDD.getDBTableName() + getWhereWithAllPK(myJDD,casDeTestNum)

			Log.addTrace("query =  $query")

			def row

			try {
				row = sqlInstance.firstRow(query)

			}catch(Exception ex) {
				//pass = false
				status = 'ERROR'
				Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
				TNRResult.addDETAIL("checkIDNotInBD()")
				TNRResult.addDETAIL(ex.getMessage())
			}

			if ((Integer)row[0]>0) {
				TNRResult.addDETAIL("Supression KO")
				//pass = false
				status = 'FAIL'
			}
		}

		/*
		 if (pass) {
		 TNRResult.addSTEPPASS("Fin de la vérification de la suppression des valeurs en Base de Données")
		 //KeywordUtil.markPassed("Supression OK")
		 }else {
		 TNRResult.addSTEPFAIL("Fin de la  vérification de la suppression des valeurs en Base de Données")
		 //KeywordUtil.markFailed("Supression KO")
		 }
		 */

		TNRResult.addEndBlock("Fin de la  vérification de la suppression des valeurs en Base de Données",status)
		Log.addTraceEND(CLASS_FORLOG,"checkIDNotInBD")
	}



	/**
	 * 
	 * @param myJDD
	 * @param casDeTestNum
	 * @return
	 */
	private static String getWhereWithAllPK(JDD myJDD,int casDeTestNum) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getWhereWithAllPK",[myJDD:myJDD.toString(),casDeTestNum:casDeTestNum])

		List <String> PKList = InfoDB.getPK(myJDD.getDBTableName())
		String ret =''
		if (PKList) {
			String query = ' WHERE '
			PKList.each {
				query = query + it + "='" + myJDD.getData(it,casDeTestNum,true) + "' and "
			}
			ret = query.substring(0,query.length()-5)
		}
		Log.addTraceEND(CLASS_FORLOG,"getWhereWithAllPK",ret)
		return ret
	}

	


	static int getMaxFromTable(String fieldName, String tableName) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getMaxFromTable",[fieldName:fieldName, table:tableName])

		String req = "SELECT MAX($fieldName) as num FROM $tableName"
		int num = 0
		try {
			def res = sqlInstance.firstRow(req).num

			num = (res) ? (Integer)res : 0
			TNRResult.addDETAIL("get Max '$fieldName From Table '$tableName' = $num")
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de firstRow($req) : ")
			TNRResult.addDETAIL("getMaxFromTable()")
			TNRResult.addDETAIL(ex.getMessage())

		}
		Log.addTraceEND(CLASS_FORLOG,"getMaxFromTable",num)
		return num
	}




	static getNumEcran(String table) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getNumEcran",[table:table])

		def query = "SELECT ID_NUMECR as num FROM OBJ where ST_NOMOBJ=$table"
		def ret

		try {
			def res = sqlInstance.firstRow(query)
			ret = (res) ? res.num : null
			TNRResult.addDETAIL("getNumEcran($table) = $ret")
		} catch(Exception ex) {
			Log.addERROR("Erreur d'execution de firstRow($query) : ")
			TNRResult.addDETAIL("getNumEcran()")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FORLOG,"getNumEcran",ret)
		return ret
	}




	static Map getLibelle(String table, numEcran) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getLibelle",[table:table, numEcran:numEcran])

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
		Log.addTraceEND(CLASS_FORLOG,"getLibelle")
		return resultMap
	}

	static int getLastSequence(String seq) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getLastSequence",[seq:seq])

		String req = "SELECT IDENT_CURRENT('$seq') as lastSeq"
		def result = sqlInstance.rows(req)
		int lastSeq = (int)result[0].lastSeq
		Log.addTraceEND(CLASS_FORLOG,"getLastSequence",lastSeq)
		return lastSeq
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


} // end of class