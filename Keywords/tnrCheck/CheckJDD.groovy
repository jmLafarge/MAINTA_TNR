package tnrCheck

import org.apache.poi.ss.usermodel.*

import groovy.transform.CompileStatic
import tnrCheck.column.CheckColumn
import tnrCheck.data.CheckData
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrLog.Log

@CompileStatic
public class CheckJDD {

	private static final String CLASS_FOR_LOG = 'CheckJDD'


	/**
	 * Controle la liste des paramètres
	 * Controle le nom de la table
	 * Controle si toutes les champs de la table sont bien des colonnes du JDD et à la bonne place
	 * Contrôle les mots clés dans les DATA
	 * @return
	 */
	static run() {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[:])

		Log.addSubTITLE('Vérification des JDD')

		boolean status = true

		JDDFileMapper.JDDfilemap.each { modObj,JDDFullname ->

			JDD myJDD = new JDD(JDDFullname,null,null,false)

			Log.addDEBUG("")
			Log.addDEBUG("Controle de $JDDFullname")

			for(Sheet JDDsheet: myJDD.book) {

				String JDDsheetName = JDDsheet.getSheetName()

				if (myJDD.isSheetAvailable(JDDsheetName)) {

					Log.addDEBUGDETAIL("Onglet : $JDDsheetName")

					myJDD.loadTCSheet(JDDsheet)
					String table = myJDD.getDBTableName()

					if (myJDD.myJDDHeader.getSize() >1) {
						if (table) {
							status &= CheckColumn.run('JDD',myJDD.myJDDHeader.getList(), table)

							status &= CheckData.run('JDD',myJDD,myJDD.myJDDData,table, JDDFullname,JDDsheetName)

							//reste à faire status &= CheckPrerequis.run2('JDD',myJDD,JDDFullname)

						}else {
							Log.addDEBUGDETAIL('Pas de table dans le JDD')
						}
					}else {
						Log.addDEBUGDETAIL('Pas de colonnes dans le JDD')
					}
				}else {
					Log.addDEBUGDETAIL("Onglet : $JDDsheetName (skip)")
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FOR_LOG,"run")
	}
}// Fin de class
