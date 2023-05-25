package checkprerequis

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.InfoBDD
import my.JDD
import my.JDDFiles
import my.Log
import my.PREJDDFiles
import my.XLS


@CompileStatic
public class CheckPREJDD {


	private static my.JDD myJDD
	private static List <List> datas = []
	private static String table =''
	private static List <String> headersPREJDD=[]
	private static List <String> PKList=[]



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


		Log.addSubTITLE('Vérification des PREJDD')


		PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->

			Log.addINFO("Lecture du PREJDD : $fullName")

			myJDD = new JDD(JDDFiles.JDDfilemap.getAt(modObj),null,null,false)

			XSSFWorkbook book = my.XLS.open(fullName)

			for(Sheet sheet: book) {

				if (myJDD.isSheetAvailable(sheet.getSheetName())) {
					

					myJDD.loadTCSheet(myJDD.getBook().getSheet(sheet.getSheetName()))
					
					headersPREJDD = my.XLS.loadRow(sheet.getRow(0))

					datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())

					table = myJDD.getDBTableName()
					PKList = InfoBDD.getPK(table)

					Log.addDEBUG("Onglet : " + sheet.getSheetName(),0)

					if (headersPREJDD.size()>1) {

						checkColumn()
						checkKWInDATA()
						CheckTypeInDATA.run(datas,myJDD, table)
						checkDoublonOnPK()
					}
				}
			}
		}
	}






	private static checkColumn() {

		Log.addDEBUGDETAIL("Contrôle des colonnes",0)

		InfoBDD.map[table].each{col,vlist ->

			if (col == headersPREJDD[(int)vlist[0]]) {
				Log.addDEBUG("'$col' OK")
			}else if (col in headersPREJDD) {
				Log.addDETAILFAIL("'$col' est dans le PREJDD mais pas à la bonne place")
			}else {
				Log.addDETAILFAIL("Le champ '$col' n'est pas dans le PREJDD")
			}
		}
	}






	private static checkKWInDATA() {

		Log.addDEBUGDETAIL("Contrôle des mots clés dans les DATA",0)
		datas.eachWithIndex { li,numli ->
			li.eachWithIndex { val,i ->
				if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
					Log.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
				}
			}
		}
	}






	private static checkDoublonOnPK() {

		Log.addDEBUGDETAIL("Contrôle absence de doublon sur PRIMARY KEY : " +  PKList.join(' , '),0)

		Map <String,Integer> PKval = [:]

		datas.eachWithIndex { li,numli ->
			List <String> PKnames = []
			List PKvalues = []
			li.eachWithIndex { val,i ->
				if (headersPREJDD.get(i) in PKList) PKvalues.add(val)
			}
			if (PKval.containsKey(PKvalues.join('-'))) {
				Log.addDETAILFAIL("La valeur '" + PKvalues.join('-') + "' en ligne " + (numli+2) + " existe déjà en ligne " + (PKval.getAt(PKvalues.join('-')) + 2))
			}else {
				PKval.put(PKvalues.join('-'),numli)
			}
		}
	}
}
