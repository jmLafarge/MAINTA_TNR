package myCheck

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import my.InfoDB
import my.Log
import myJDDManager.JDD
import myJDDManager.JDDData
import myJDDManager.JDDFileMapper
import myJDDManager.JDDHeader
import myPREJDDManager.PREJDDFileMapper


@CompileStatic
public class CheckPREJDD {


	private static final String CLASS_FORLOG = 'CheckPREJDD'


	/**
	 * 
	 * 
	 * Controle si toutes les champs de la table sont bien des colonnes du PREJDD
	 * Contrôle les mots clés dans les DATA
	 * Controle qu'il n'y ait pas de doublons dans les PREJDD par rapport au PRIMARY KEY
	 *
	 * @return
	 */
	static run() {

		Log.addTraceBEGIN(CLASS_FORLOG,"run",[:])

		Log.addSubTITLE('Vérification des PREJDD')

		boolean status = true

		PREJDDFileMapper.PREJDDfilemap.each { modObj,PREJDDfullname ->

			Log.addTrace("")
			Log.addTrace("Lecture du PREJDD : $PREJDDfullname")

			JDD myJDD = new JDD(JDDFileMapper.JDDfilemap.getAt(modObj),null,null,false)

			XSSFWorkbook PREJDDbook = my.XLS.open(PREJDDfullname)

			for(Sheet PREJDDsheet: PREJDDbook) {

				String PREJDDsheetName = PREJDDsheet.getSheetName()

				if (myJDD.isSheetAvailable(PREJDDsheetName)) {
					
					JDDHeader PREJDDheader = new JDDHeader(PREJDDsheet)

					myJDD.loadTCSheet(myJDD.getBook().getSheet(PREJDDsheetName))
					JDDData PREJDDData = new JDDData(PREJDDsheet,PREJDDheader,'')
					String table = myJDD.getDBTableName()

					Log.addTrace("Onglet : " + PREJDDsheetName)

					if (PREJDDheader.getSize()>1) {
						if (table) {
							status = CheckColumn.run('PREJDD',PREJDDheader.getList(), table,status)
							status = CheckKWInData.run('PREJDD',PREJDDData.getList(), PREJDDfullname,PREJDDsheetName, status)
							status = CheckTypeInData.run(PREJDDData.getList(),myJDD, table,PREJDDfullname,status)
							status = CheckDoublonOnPK.run(PREJDDData.getList(), InfoDB.getPK(table), PREJDDfullname, PREJDDsheetName, status)
						}else {
							Log.addDETAIL('Pas de table dans le PREJDD')
						}
					}else {
						Log.addDETAIL('Pas de colonnes dans le PREJDD')
					}
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FORLOG,"run")
	}


}
