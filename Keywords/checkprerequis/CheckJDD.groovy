package checkprerequis

import org.apache.poi.ss.usermodel.*

import groovy.transform.CompileStatic
import my.InfoDB
import my.JDD
import my.JDDFiles
import my.Log

@CompileStatic
public class CheckJDD {
	
	private static final String CLASS_FORLOG = 'CheckJDD'
	

	private static my.JDD myJDD
	private static String table
	private static String JDDFullName
	private static String sheetName
	private static boolean status = true


	/**
	 * Controle la liste des paramètres
	 * Controle le nom de la table
	 * Controle si toutes les champs de la table sont bien des colonnes du JDD et à la bonne place
	 * Contrôle les mots clés dans les DATA
	 * @return
	 */
	static run(boolean withDetails) {
		
		Log.addTraceBEGIN(CLASS_FORLOG,"run",[withDetails:withDetails])

		Log.addSubTITLE('Vérification des JDD')

		JDDFiles.JDDfilemap.each { modObj,fullname ->

			JDDFullName = fullname

			myJDD = new JDD(JDDFullName,null,null,false)

			for(Sheet sheet: myJDD.book) {

				sheetName = sheet.getSheetName()

				if (myJDD.isSheetAvailable(sheetName)) {


					Log.addTrace("Onglet : $sheetName")
					Log.addDEBUGDETAIL("Contrôle de la liste des paramètres")

					myJDD.loadTCSheet(sheet)
					table = myJDD.getDBTableName()

					if (myJDD.getHeadersSize() >1) {

						checkTable()
						checkLOCATOR()
						status = CheckKWInDATA.run(myJDD.getDatas(),false, JDDFullName,sheetName, status)
						
						if (myJDD.getDBTableName()) {
							status = CheckDoublonOnPK.run(myJDD.getDatas(), myJDD.getHeaders(), table, JDDFullName, sheetName, status,withDetails)
						}else {
							Log.addDETAILWARNING("$JDDFullName ($sheetName) : Pas de table donc pas de contrôle de l'unicité des PKs")
						}
					}else {
						Log.addDETAILWARNING("$JDDFullName ($sheetName) : Pas de colonnes dans le JDD")
					}
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FORLOG,"run")
	}





	private static checkTable() {
		
		Log.addTraceBEGIN(CLASS_FORLOG,"checkTable",[:])

		if (table=='') {
			Log.addDEBUGDETAIL("Pas de table DB dans le JDD ")
		}else {
			if (InfoDB.isTableExist(table)) {
				Log.addDEBUGDETAIL("Contrôle de la table DB '$table'")

				checkColumn()
				status = CheckTypeInDATA.run(myJDD.getDatas(),myJDD, table,JDDFullName,status)
			}else {
				Log.addDETAILFAIL("$JDDFullName ($sheetName) : La table '$table' n'existe pas !")
				status=false
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"checkTable")
	}





	private static checkColumn() {
		
		Log.addTraceBEGIN(CLASS_FORLOG,"checkColumn",[:])

		Log.addDEBUGDETAIL("Contrôle des colonnes (Présence, ordre)")

		InfoDB.map[table].each{col,vlist ->

			Log.addTrace(vlist.join('|'))

			if (col == myJDD.getHeader((int)vlist[0])) {
				Log.addTrace("'$col' OK")

				//InfoDB.updateParaInfoDB(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())

			}else if (col in myJDD.getHeaders()) {

				Log.addDETAILFAIL("$JDDFullName ($sheetName) : La colonne '$col' est dans le JDD mais pas à la bonne place")
				status=false
			}else {
				Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le champ '$col' n'est pas dans le JDD")
				status=false
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"checkColumn")
	}




	private static checkLOCATOR() {
		
		Log.addTraceBEGIN(CLASS_FORLOG,"checkLOCATOR",[:])

		Log.addDEBUGDETAIL("Contrôle des LOCATOR")
		myJDD.getParam('LOCATOR').eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = myJDD.getHeader(i)

				if (myJDD.isTagAllowed(loc)) {
					Log.addTrace("$name : $loc in myJDD.TAG_LIST_ALLOWED")
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					//it's a tag with an attribut
					def lo = loc.toString().split(/\*/)
					if (myJDD.isTagAllowed(lo[0])) {
						Log.addTrace("$name : $name : ${loc[0]} in myJDD.TAG_LIST_ALLOWED dans $loc")
					}else {
						Log.addDETAILFAIL("$JDDFullName ($sheetName) : Champ '$name' : LOCATOR inconnu : ${lo[0]} in '$loc'")
						status=false
					}
				}else if (loc[0] == '/') {
					Log.addTrace("$loc OK")
				}else {
					Log.addDETAILFAIL("$JDDFullName ($sheetName) : Champ '$name' : LOCATOR inconnu : '$loc'")
					status=false
				}
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"checkLOCATOR")
	}

	


}// end of class
