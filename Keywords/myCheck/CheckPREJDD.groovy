package myCheck

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import my.InfoDB
import my.Log
import myJDDManager.JDD
import myJDDManager.JDDFiles
import myJDDManager.PREJDDFiles


@CompileStatic
public class CheckPREJDD {


	private static final String CLASS_FORLOG = 'CheckPREJDD'

	private static myJDDManager.JDD myJDD
	private static List <List> datas = []
	private static String PREJDDFullName =''
	private static String sheetName =''
	private static String table =''
	private static List <String> headersPREJDD=[]
	//private static List <String> PKList=[]
	private static boolean status = true



	/**
	 * 
	 * 
	 * Controle si toutes les champs de la table sont bien des colonnes du PREJDD
	 * Contrôle les mots clés dans les DATA
	 * Controle qu'il n'y ait pas de doublons dans les PREJDD par rapport au PRIMARY KEY
	 *
	 * @return
	 */
	static run(boolean withDetails) {

		Log.addTraceBEGIN(CLASS_FORLOG,"run",[withDetails:withDetails])

		Log.addSubTITLE('Vérification des PREJDD')


		PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->

			PREJDDFullName = fullName
			Log.addTrace("")
			Log.addTrace("Lecture du PREJDD : $fullName")

			myJDD = new JDD(JDDFiles.JDDfilemap.getAt(modObj),null,null,false)

			XSSFWorkbook book = my.XLS.open(fullName)

			for(Sheet sheet: book) {

				sheetName = sheet.getSheetName()

				if (myJDD.isSheetAvailable(sheetName)) {

					myJDD.loadTCSheet(myJDD.getBook().getSheet(sheetName))
					headersPREJDD = my.XLS.loadRow(sheet.getRow(0))
					datas = myJDDManager.PREJDD.loadDATA(sheet,headersPREJDD.size())
					table = myJDD.getDBTableName()

					Log.addTrace("Onglet : " + sheetName)

					if (headersPREJDD.size()>1) {

						checkColumn()
						status = CheckKWInDATA.run(datas,true, fullName,sheetName, status)
						if (table) {
							status = CheckTypeInDATA.run(datas,myJDD, table,fullName,status)
							status = CheckDoublonOnPK.run(datas, headersPREJDD, table, fullName, sheetName, status,withDetails)
						}
					}
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FORLOG,"run")
	}






	private static checkColumn() {

		Log.addTraceBEGIN(CLASS_FORLOG,"checkColumn",[:])

		Log.addDEBUGDETAIL("Contrôle des colonnes")

		InfoDB.map[table].each{col,vlist ->

			if (col == headersPREJDD[(int)vlist[0]]) {
				Log.addTrace("'$col' OK")
			}else if (col in headersPREJDD) {
				Log.addDETAILFAIL("'$col' est dans le PREJDD mais pas à la bonne place")
				status=false
			}else {
				Log.addDETAILFAIL("Le champ '$col' n'est pas dans le PREJDD")
				status=false
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"checkColumn")
	}


}
