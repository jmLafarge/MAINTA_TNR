package tnrCheck

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCheck.data.CheckKW
import tnrCheck.data.CheckPK
import tnrCheck.data.CheckType
import tnrCheck.header.CheckHeader
import tnrCommon.ExcelUtils
import tnrJDDManager.JDD
import tnrJDDManager.JDDData
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDHeader
import tnrLog.Log
import tnrPREJDDManager.PREJDDFileMapper
import tnrSqlManager.InfoDB


@CompileStatic
public class CheckPREJDD {


	private static final String CLASS_NAME = 'CheckPREJDD'


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

		Log.addTraceBEGIN(CLASS_NAME,"run",[:])

		Log.addSubTITLE('Vérification des PREJDD')

		boolean status = true

		PREJDDFileMapper.PREJDDfilemap.each { modObj,PREJDDfullname ->

			Log.addDEBUG("")
			Log.addDEBUG("Controle de $PREJDDfullname")

			JDD myJDD = new JDD(JDDFileMapper.JDDfilemap.getAt(modObj),null,null)

			XSSFWorkbook PREJDDbook = ExcelUtils.open(PREJDDfullname)

			for(Sheet PREJDDsheet: PREJDDbook) {

				String PREJDDsheetName = PREJDDsheet.getSheetName()

				if (myJDD.isSheetAvailable(PREJDDsheetName)) {

					JDDHeader PREJDDheader = new JDDHeader(PREJDDsheet)

					myJDD.loadTCSheet(myJDD.getBook().getSheet(PREJDDsheetName))
					JDDData PREJDDData = new JDDData(PREJDDsheet,PREJDDheader.getList(),'')
					String table = myJDD.getDBTableName()
					Log.addDEBUGDETAIL("Onglet : $PREJDDsheetName")

					if (table) {
						status &= CheckHeader.run('PREJDD',PREJDDheader.getList(), table)
						if (PREJDDheader.getSize()>1) {
							status &= CheckKW.run('PREJDD',PREJDDData.getList(),myJDD.myJDDParam , PREJDDfullname,PREJDDsheetName)
							status &= CheckType.run(PREJDDData.getList(),myJDD, table,PREJDDfullname)
							status &= CheckPK.run(PREJDDData.getList(), InfoDB.getPK(table), PREJDDfullname, PREJDDsheetName)
						}else {
							Log.addDETAIL("$PREJDDsheetName ($PREJDDsheetName) : Pas de colonnes dans le PREJDD")
						}
					}else {
						Log.addDETAIL("$PREJDDsheetName ($PREJDDsheetName) : Pas de table dans le JDD ${myJDD.getJDDFullName()}")
					}
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_NAME,"run")
	}
}
