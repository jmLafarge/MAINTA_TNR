package tnrCheck

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrJDDManager.JDD
import tnrJDDManager.JDDData
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDHeader
import tnrPREJDDManager.PREJDDFileMapper
import tnrSqlManager.InfoDB


@CompileStatic
public class CheckPREJDD {


	private static final String CLASS_FOR_LOG = 'CheckPREJDD'


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

		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[:])

		Log.addSubTITLE('Vérification des PREJDD')

		boolean status = true

		PREJDDFileMapper.PREJDDfilemap.each { modObj,PREJDDfullname ->

			Log.addTrace("")
			Log.addTrace("Lecture du PREJDD : $PREJDDfullname")

			JDD myJDD = new JDD(JDDFileMapper.JDDfilemap.getAt(modObj),null,null,false)

			XSSFWorkbook PREJDDbook = tnrCommon.ExcelUtils.open(PREJDDfullname)

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
							//status = CheckPrerequis.run2('PREJDD',myJDD,PREJDDfullname,status)
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
		Log.addTraceEND(CLASS_FOR_LOG,"run")
	}
}
