package checkprerequis

import groovy.transform.CompileStatic

import org.apache.poi.ss.usermodel.*
import my.Log
import my.InfoBDD
import my.JDD
import my.JDDFiles

@CompileStatic
public class CheckJDD {

	private static my.JDD myJDD
	private static String table


	/**
	 * Controle la liste des paramètres
	 * Controle le nom de la table
	 * Controle si toutes les champs de la table sont bien des colonnes du JDD et à la bonne place
	 * Contrôle les mots clés dans les DATA
	 * @return
	 */
	static run() {

		Log.addSubTITLE('Vérification des JDD')

		JDDFiles.JDDfilemap.each { modObj,fullName ->

			Log.addINFO("Lecture du JDD : $fullName")
			myJDD = new JDD(fullName,null,null,false)

			for(Sheet sheet: myJDD.book) {
				if (myJDD.isSheetAvailable(sheet.getSheetName())) {

					Log.addDEBUG("Onglet : " + sheet.getSheetName(),0)
					Log.addDEBUGDETAIL("Contrôle de la liste des paramètres",0)

					myJDD.loadTCSheet(sheet)
					table = myJDD.getDBTableName()

					if (myJDD.getHeadersSize() >1) {

						checkTable()

						checkLOCATOR()

						checkKWInDATA()
					}else {
						Log.addDETAILWARNING("Pas de colonnes dans le JDD ! ")
					}
				}
			}
		}
	}












	private static checkTable() {

		if (table=='') {
			Log.addDEBUGDETAIL("Pas de table DB dans le JDD ",0)
		}else {
			if (InfoBDD.isTableExist(table)) {
				Log.addDEBUGDETAIL("Contrôle de la table DB '$table'",0)

				checkColumn()
				CheckTypeInDATA.run(myJDD.getDatas(), myJDD, table)
			}else {
				Log.addDETAILFAIL("Contrôle de la table DB KO, la table '$table' n'existe pas !")
			}
		}
	}





	private static checkColumn() {

		Log.addDEBUGDETAIL("Contrôle des colonnes (Présence, ordre)",0)
		//InfoBDD.colnameMap[table].eachWithIndex{col,index ->
		InfoBDD.map[table].each{col,vlist ->

			if (col == myJDD.getHeader((int)vlist[0])) {
				Log.addDEBUG("'$col' OK")

				//InfoBDD.updateParaInfoBDD(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())

			}else if (col in myJDD.getHeaders()) {
				Log.addDETAILFAIL("'$col' est dans le JDD mais pas à la bonne place")
			}else {
				Log.addDETAILFAIL("Le champ '$col' n'est pas dans le JDD")
			}
		}

	}






	private static checkLOCATOR() {

		Log.addDEBUGDETAIL("Contrôle des LOCATOR",0)
		myJDD.getParam('LOCATOR').eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = myJDD.getHeader(i)

				if (loc in myJDD.TAG_LIST_ALLOWED) {
					Log.addDEBUG("$name : $loc in myJDD.TAG_LIST_ALLOWED")
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					//it's a tag with an attribut
					def lo = loc.toString().split(/\*/)
					if (lo[0] in myJDD.TAG_LIST_ALLOWED) {
						Log.addDEBUG("$name : $name : ${loc[0]} in myJDD.TAG_LIST_ALLOWED dans $loc")
					}else {
						Log.addDETAILFAIL("$name : LOCATOR inconnu : ${lo[0]} in '$loc'")
					}
				}else if (loc[0] == '/') {
					Log.addDEBUG("$loc OK")
				}else {
					Log.addDETAILFAIL("$name : LOCATOR inconnu : '$loc'")
				}
			}
		}
	}









	private static checkKWInDATA() {

		Log.addDEBUGDETAIL("Contrôle des mots clés dans les DATA",0)
		myJDD.datas.eachWithIndex { li,numli ->
			li.eachWithIndex { val,i ->
				if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
					Log.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")

				}
			}
		}
	}


}
