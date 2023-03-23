package checkprerequis

import org.apache.poi.ss.usermodel.*
import my.Log as MYLOG

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
					//if (fullName.split('\\\\')[-1] in JDDTOSKIP) {
					if (myJDD.getDBTableName()=='') {
						MYLOG.addDETAIL("Pas de table DB")
					}else {
						if (my.InfoBDD.isTableExist(myJDD.getDBTableName())) {
							MYLOG.addDETAIL("Contrôle de la table DB '" + myJDD.getDBTableName() + "'")

							if (myJDD.headers.size()>1) {
								MYLOG.addDETAIL("Contrôle des colonnes (Présence, ordre, prérequis, foreignkey)")
								//my.InfoBDD.colnameMap[myJDD.getDBTableName()].eachWithIndex{col,index ->
								my.InfoBDD.map[myJDD.getDBTableName()].each{col,vlist ->

									if (col == myJDD.headers[(int)vlist[0]]) {
										MYLOG.addDEBUG("'$col' OK")

										//my.InfoBDD.updateParaInfoBDD(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())


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
							MYLOG.addDETAILFAIL("Contrôle de la table DB KO, la table '" + myJDD.getDBTableName() + "' n'existe pas !")
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
				}
			}
		}

	}

}
