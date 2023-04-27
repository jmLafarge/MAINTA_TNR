package checkprerequis

import org.apache.poi.ss.usermodel.*
import my.Log as MYLOG
import my.InfoBDD
import my.JDD
import my.JDDFiles as MYJDDFILES


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

		MYLOG.addSubTITLE('Vérification des JDD')

		MYJDDFILES.JDDfilemap.each { modObj,fullName ->

			MYLOG.addINFO("Lecture du JDD : $fullName")
			myJDD = new my.JDD(fullName,null,null,false)

			for(Sheet sheet: myJDD.book) {
				if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {

					MYLOG.addDEBUG("Onglet : " + sheet.getSheetName(),0)
					MYLOG.addDEBUGDETAIL("Contrôle de la liste des paramètres",0)

					myJDD.loadTCSheet(sheet)
					table = myJDD.getDBTableName()

					if (myJDD.headers.size()>1) {

						checkTable()

						checkLOCATOR()

						checkKWInDATA()
					}else {
						MYLOG.addDETAILWARNING("Pas de colonnes dans le JDD ! ")
					}
				}
			}
		}
	}












	private static checkTable() {

		if (table=='') {
			MYLOG.addDEBUGDETAIL("Pas de table DB dans le JDD ",0)
		}else {
			if (InfoBDD.isTableExist(table)) {
				MYLOG.addDEBUGDETAIL("Contrôle de la table DB '$table'",0)

				checkColumn()
				CheckTypeInDATA.run(myJDD.datas, myJDD, table)
			}else {
				MYLOG.addDETAILFAIL("Contrôle de la table DB KO, la table '$table' n'existe pas !")
			}
		}
	}





	private static checkColumn() {

		MYLOG.addDEBUGDETAIL("Contrôle des colonnes (Présence, ordre)",0)
		//InfoBDD.colnameMap[table].eachWithIndex{col,index ->
		InfoBDD.map[table].each{col,vlist ->

			if (col == myJDD.headers[(int)vlist[0]]) {
				MYLOG.addDEBUG("'$col' OK")

				//InfoBDD.updateParaInfoBDD(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())

			}else if (col in myJDD.headers) {
				MYLOG.addDETAILFAIL("'$col' est dans le JDD mais pas à la bonne place")
			}else {
				MYLOG.addDETAILFAIL("Le champ '$col' n'est pas dans le JDD")
			}
		}

	}






	private static checkLOCATOR() {

		MYLOG.addDEBUGDETAIL("Contrôle des LOCATOR",0)
		myJDD.getParam('LOCATOR').eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = myJDD.headers[i]

				if (loc in myJDD.TAG_LIST_ALLOWED) {
					MYLOG.addDEBUG("$name : $loc in myJDD.TAG_LIST_ALLOWED")
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					//it's a tag with an attribut
					def lo = loc.toString().split(/\*/)
					if (lo[0] in myJDD.TAG_LIST_ALLOWED) {
						MYLOG.addDEBUG("$name : $name : ${loc[0]} in myJDD.TAG_LIST_ALLOWED dans $loc")
					}else {
						MYLOG.addDETAILFAIL("$name : LOCATOR inconnu : ${lo[0]} in '$loc'")
					}
				}else if (loc[0] == '/') {
					MYLOG.addDEBUG("$loc OK")
				}else {
					MYLOG.addDETAILFAIL("$name : LOCATOR inconnu : '$loc'")
				}
			}
		}
	}









	private static checkKWInDATA() {

		if (myJDD.headers.size()>1) {
			MYLOG.addDEBUGDETAIL("Contrôle des mots clés dans les DATA",0)
			myJDD.datas.eachWithIndex { li,numli ->
				li.eachWithIndex { val,i ->
					if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
						MYLOG.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")

					}
				}
			}
		}else {

		}
	}











}
