package tnrCheck

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrLog.Log


/**
 * Vérifie la validité des mots-clés (KW) des données des JDD/PREJDD.
 *
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class CheckData {

	private static final String CLASS_FOR_LOG = 'CheckData'


	static boolean run(String fileType,JDD myJDD, String tableName, String filename, String sheetName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[fileType:fileType , myJDD:myJDD , tableName:tableName , filename:filename , sheetName:sheetName])
		Log.addDETAIL(" - Contrôle des mots clés dans les DATA")
		boolean status = true
		
		List <Map<String, Map<String, Object>>> datas = myJDD.myJDDData.getList()

		CheckPK checkPK = new CheckPK(filename, sheetName,tableName)
		CheckKW checkKW = new CheckKW(fileType,filename, sheetName)
		CheckType checkType = new CheckType( myJDD, tableName, filename, sheetName)

		datas.eachWithIndex { dataLine, numLine ->
			// Pour chaque ligne de donnée
			dataLine.each { cdt,data ->
				// on controle chaque valeur
				data.each { name,val ->

					// on vérifie les valeurs des PKs
					status &= checkPK.checkPKValues(cdt,numLine,name,val)

					status &= checkKW.checkValue(cdt, name, val )
					
					status &= checkType.checkValue( cdt, name, val )


				}
				// on vérifie les doublons de PKs
				status &=checkPK.checkDuplicates(cdt,numLine)

			}

		}
		Log.addTraceEND(CLASS_FOR_LOG,"run",status)
		return status
	}




} // Fin de class
