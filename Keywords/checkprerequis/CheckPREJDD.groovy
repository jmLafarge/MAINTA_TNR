package checkprerequis


import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import my.Log as MYLOG
import my.InfoBDD as INFOBDD
import my.JDD
import my.XLS as MYXLS
import my.PREJDDFiles
import my.JDDFiles



public class CheckPREJDD {
	
	
	private static my.JDD myJDD
	private static List datas = []
	private static String table =''
	private static List headersPREJDD=[]
	private static List PKList=[]
	
	

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


		MYLOG.addSubTITLE('Vérification des PREJDD')


		PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->

			MYLOG.addINFO("Lecture du PREJDD : $fullName")

			myJDD = new my.JDD(JDDFiles.JDDfilemap.getAt(modObj),null,null,false)

			XSSFWorkbook book = MYXLS.open(fullName)

			for(Sheet sheet: book) {

				if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {

					myJDD.loadTCSheet(myJDD.book.getSheet(sheet.getSheetName()))
					
					headersPREJDD = MYXLS.loadRow(sheet.getRow(0))
					
					datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())
					
					table = myJDD.getDBTableName()
					PKList = INFOBDD.getPK(table)

					MYLOG.addDEBUG("Onglet : " + sheet.getSheetName(),0)

					if (headersPREJDD.size()>1) {

						this.checkColumn()
						this.checkKWInDATA()
						this.checkTypeInDATA()
						this.checkDoublonOnPK()
					}
				}
			}
		}

	}

	
	
	
	
	
	private static checkColumn() {
		
		MYLOG.addDEBUGDETAIL("Contrôle des colonnes",0)
		
		//INFOBDD.colnameMap[table].eachWithIndex{col,index ->
		INFOBDD.map[table].each{col,vlist ->
			if (col == headersPREJDD[(int)vlist[0]]) {
				MYLOG.addDEBUG("'$col' OK")
			}else if (col in headersPREJDD) {
				MYLOG.addDETAILFAIL("'$col' est dans le PREJDD mais pas à la bonne place")
			}else {
				MYLOG.addDETAILFAIL("Le champ '$col' n'est pas dans le PREJDD")
			}
		}
	}
	
	
	
	
	
	
	private static checkKWInDATA() {

		MYLOG.addDEBUGDETAIL("Contrôle des mots clés dans les DATA",0)
		datas.eachWithIndex { li,numli ->
			li.eachWithIndex { val,i ->
				if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
					MYLOG.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
				}
			}
		}
	}
	
	
	

	
	private static checkTypeInDATA() {

		MYLOG.addDEBUGDETAIL("Contrôle des types dans les DATA",0)

		datas.eachWithIndex { li,numli ->
			li.eachWithIndex { val,i ->
				String name = myJDD.getHeaderNameOfIndex(i)
				if (i!=0 && INFOBDD.inTable(table, name) && !myJDD.isFK(name)) {

					if (INFOBDD.isNumeric(table, name)) {

						if (val.toString().isNumber() || val in ['$NULL', '$NU', '$SEQUENCEID', '$ORDRE']) {
							// c'est bon
						}else {

							MYLOG.addDETAILFAIL(li[0] + "($name) : La valeur '$val' n'est pas autorisé pour un champ numérique")
						}
					}
				}
			}
		}
	}
	
	
	
	private static checkDoublonOnPK() {
		
		MYLOG.addDEBUGDETAIL("Contrôle absence de doublon sur PRIMARY KEY : " +  PKList.join(' , '),0)
		Map PKval = [:]
		this.datas.eachWithIndex { li,numli ->
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
