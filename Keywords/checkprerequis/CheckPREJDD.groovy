package checkprerequis

import my.Log as MYLOG
import my.InfoBDD as MYINFOBDD
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

public class CheckPREJDD {

	static run() {


		MYLOG.addSubTITLE('Vérification des PREJDD')
		/*
		 * Controle si toutes les champs de la table sont bien des colonnes du PREJDD
		 * Contrôle les mots clés dans les DATA
		 * Controle qu'il n'y ait pas de doublons dans les PREJDD par rapport au PRIMARY KEY
		 */

		my.PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->

			MYLOG.addSTEP("Lecture du PREJDD : $fullName")

			def myJDD = new my.JDD(my.JDDFiles.JDDfilemap.getAt(modObj),null,null,false)

			XSSFWorkbook book = my.XLS.open(fullName)

			for(Sheet sheet: book) {

				if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {

					myJDD.loadTCSheet(myJDD.book.getSheet(sheet.getSheetName()))
					List headersPREJDD = my.XLS.loadRow(sheet.getRow(0))
					List datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())
					String table = myJDD.getDBTableName()
					List PKList = MYINFOBDD.getPK(table)

					MYLOG.addSUBSTEP("Onglet : " + sheet.getSheetName() )

					Map PKval = [:]

					if (headersPREJDD.size()>1) {


						MYLOG.addDETAIL("Contrôle des colonnes")

						//MYINFOBDD.colnameMap[table].eachWithIndex{col,index ->
						MYINFOBDD.map[table].each{col,vlist ->
							if (col == headersPREJDD[(int)vlist[0]]) {
								MYLOG.addDEBUG("'$col' OK")
							}else if (col in headersPREJDD) {
								MYLOG.addDETAILFAIL("'$col' est dans le PREJDD mais pas à la bonne place")
							}else {
								MYLOG.addDETAILFAIL("Le champ '$col' n'est pas dans le PREJDD")
							}
						}


						MYLOG.addDETAIL("Contrôle des mots clés dans les DATA")

						datas.eachWithIndex { li,numli ->
							li.eachWithIndex { val,i ->
								if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
									MYLOG.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
								}
							}
						}
						
						
						
						
						MYLOG.addDETAIL("Contrôle des types dans les DATA")
						
						datas.eachWithIndex { li,numli ->
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
						
						


						MYLOG.addDETAIL("Contrôle absence de doublon sur PRIMARY KEY : " +  PKList.join(' , '))

						datas.eachWithIndex { li,numli ->
							List PKnames = []
							List PKvalues = []
							li.eachWithIndex { val,i ->
								if (headersPREJDD.get(i) in PKList) PKvalues.add(val)
							}
							if (PKval.containsKey(PKvalues.join('-'))) {
								MYLOG.addDETAILFAIL("La valeur '" + PKvalues.join('-') + "' en ligne " + (numli+2) + " existe déjà en ligne " + (PKval.getAt(PKvalues.join('-')) + 2))
							}else {
								PKval.put(PKvalues.join('-'),numli)
							}
						}
					}
				}
			}
		}

	}


}
