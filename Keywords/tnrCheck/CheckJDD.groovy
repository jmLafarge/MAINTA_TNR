package tnrCheck

import org.apache.poi.ss.usermodel.*

import groovy.transform.CompileStatic
import tnrCommon.InfoDB
import tnrLog.Log
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper

@CompileStatic
public class CheckJDD {

	private static final String CLASS_FORLOG = 'CheckJDD'



	/**
	 * Controle la liste des paramètres
	 * Controle le nom de la table
	 * Controle si toutes les champs de la table sont bien des colonnes du JDD et à la bonne place
	 * Contrôle les mots clés dans les DATA
	 * @return
	 */
	static run() {

		Log.addTraceBEGIN(CLASS_FORLOG,"run",[:])

		Log.addSubTITLE('Vérification des JDD')
		
		boolean status = true

		JDDFileMapper.JDDfilemap.each { modObj,JDDFullname ->

			JDD myJDD = new JDD(JDDFullname,null,null,false)
			
			Log.addINFO("")
			Log.addINFO("Controle de $JDDFullname")

			for(Sheet JDDsheet: myJDD.book) {

				String JDDsheetName = JDDsheet.getSheetName()
				
				if (myJDD.isSheetAvailable(JDDsheetName)) {

					Log.addDETAIL("Onglet : $JDDsheetName")
					//Log.addDEBUGDETAIL("Contrôle de la liste des paramètres")

					myJDD.loadTCSheet(JDDsheet)
					String table = myJDD.getDBTableName()

					if (myJDD.myJDDHeader.getSize() >1) {
						if (table) {
							status = CheckColumn.run('JDD',myJDD.myJDDHeader.getList(), table,status)
							status = CheckKWInData.run('JDD',myJDD.myJDDData.getList(),JDDFullname,JDDsheetName, status)
							status = CheckTypeInData.run(myJDD.myJDDData.getList(),myJDD, table,JDDFullname,status)
							status = CheckDoublonOnPK.run(myJDD.myJDDData.getList(), InfoDB.getPK(table), JDDFullname, JDDsheetName, status)
						}else {
							Log.addDETAIL('Pas de table dans le JDD')
						}
					}else {
						Log.addDETAIL('Pas de colonnes dans le JDD')
					}
				}else {
					Log.addDETAIL("Onglet : $JDDsheetName (skip)")
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FORLOG,"run")
	}


}// end of class
