package checkprerequis

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.InfoDB
import my.JDD
import my.JDDFiles
import my.Log
import my.PREJDDFiles
import my.XLS
import my.JDDKW


@CompileStatic
public class CheckPREJDD {


	private static my.JDD myJDD
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


		Log.addSubTITLE('Vérification des PREJDD')


		PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->

			PREJDDFullName = fullName
			Log.addTrace("",0)
			Log.addTrace("Lecture du PREJDD : $fullName",0)

			myJDD = new JDD(JDDFiles.JDDfilemap.getAt(modObj),null,null,false)

			XSSFWorkbook book = my.XLS.open(fullName)

			for(Sheet sheet: book) {

				sheetName = sheet.getSheetName()

				if (myJDD.isSheetAvailable(sheetName)) {

					myJDD.loadTCSheet(myJDD.getBook().getSheet(sheetName))
					headersPREJDD = my.XLS.loadRow(sheet.getRow(0))
					datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())
					table = myJDD.getDBTableName()

					Log.addTrace("Onglet : " + sheetName,0)

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
	}






	private static checkColumn() {

		Log.addDEBUGDETAIL("Contrôle des colonnes",0)

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
	}


}
