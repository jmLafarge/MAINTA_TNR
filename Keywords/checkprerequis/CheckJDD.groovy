package checkprerequis

import org.apache.poi.ss.usermodel.*
import my.Log as MYLOG
import my.InfoBDD as MYINFOBDD

public class CheckJDD {


	static run() {

		MYLOG.addSubTITLE('Vérification des JDD')
		/*
		 * Controle la liste des paramètres
		 * Controle le nom de la table
		 * Controle si toutes les champs de la table sont bien des colonnes du JDD et à la bonne place
		 * Contrôle les mots clés dans les DATA
		 */
		my.JDDFiles.JDDfilemap.each { modObj,fullName ->

			def myJDD = new my.JDD(fullName)
			
			
			for(Sheet sheet: myJDD.book) {
				if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {

					//MYLOG.addINFO("                  Onglet : " + sheet.getSheetName())
					MYLOG.addSUBSTEP("Onglet : " + sheet.getSheetName())
					MYLOG.addDETAIL("Contrôle de la liste des paramètres")
					myJDD.loadTCSheet(sheet)
					String table = myJDD.getDBTableName()
					//if (fullName.split('\\\\')[-1] in JDDTOSKIP) {
					if (table=='') {
						MYLOG.addDETAIL("Pas de table DB")
					}else {
						if (MYINFOBDD.isTableExist(table)) {
							MYLOG.addDETAIL("Contrôle de la table DB '$table'")

							if (myJDD.headers.size()>1) {
								MYLOG.addDETAIL("Contrôle des colonnes (Présence, ordre)")
								//MYINFOBDD.colnameMap[table].eachWithIndex{col,index ->
								MYINFOBDD.map[table].each{col,vlist ->

									if (col == myJDD.headers[(int)vlist[0]]) {
										MYLOG.addDEBUG("'$col' OK")

										//MYINFOBDD.updateParaInfoBDD(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())


									}else if (col in myJDD.headers) {
										MYLOG.addDETAILFAIL("'$col' est dans le JDD mais pas à la bonne place")
									}else {
										MYLOG.addDETAILFAIL("Le champ '$col' n'est pas dans le JDD")
									}


								}
							}else {
								MYLOG.addDETAILFAIL("Pas de colonnes ! ")
							}

						}else {
							MYLOG.addDETAILFAIL("Contrôle de la table DB KO, la table '$table' n'existe pas !")
						}
					}

					if (myJDD.headers.size()>1) {
						MYLOG.addDETAIL("Contrôle des LOCATOR")
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


					if (myJDD.headers.size()>1) {

						MYLOG.addDETAIL("Contrôle des mots clés dans les DATA")
						myJDD.datas.eachWithIndex { li,numli ->
							li.eachWithIndex { val,i ->
								if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
									MYLOG.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")

								}
							}
						}
					}



					if (myJDD.headers.size()>1) {

						MYLOG.addDETAIL("Contrôle des types dans les DATA")
						myJDD.datas.eachWithIndex { li,numli ->
							li.eachWithIndex { val,i ->
								String name = myJDD.getHeaderNameOfIndex(i)
								if (i!=0 && MYINFOBDD.inTable(table, name) && !myJDD.isFK(name)) {
								
									if (MYINFOBDD.isNumeric(table, name)) {
	
										if (val.toString().isNumber() || val in ['$NULL','$NU','$SEQUENCEID']) {
											// c'est bon
										}else {
											
											MYLOG.addDETAILFAIL(li[0] + "($name) : La valeur '$val' n'est pas autorisé pour un champ numérique")
										}
									}
								}
							}
						}
					}



				}
			}
		}

	}

}
