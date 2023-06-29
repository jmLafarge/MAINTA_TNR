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
	static run() {

		Log.addSubTITLE('Vérification des JDD')

		JDDFiles.JDDfilemap.each {
			modObj,fullname ->

			JDDFullName = fullname
			Log.addDEBUG("",0)
			Log.addDEBUG("Lecture du JDD : $JDDFullName",0)
			myJDD = new JDD(JDDFullName,null,null,false)

			for(Sheet sheet: myJDD.book) {
				
				sheetName = sheet.getSheetName()
				
				if (myJDD.isSheetAvailable(sheetName)) {
					
					
					Log.addDEBUG("Onglet : $sheetName",0)
					Log.addDEBUGDETAIL("Contrôle de la liste des paramètres",0)

					myJDD.loadTCSheet(sheet)
					table = myJDD.getDBTableName()

					if (myJDD.getHeadersSize() >1) {

						checkTable()

						checkLOCATOR()

						checkKWInDATA()
						
						checkUniquenessPK()
					}else {
						Log.addDETAILWARNING("$JDDFullName ($sheetName) : Pas de colonnes dans le JDD")
					}
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
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
				Log.addDETAILFAIL("$JDDFullName ($sheetName) : La table '$table' n'existe pas !")
				status=false
			}
		}
	}





	private static checkColumn() {

		Log.addDEBUGDETAIL("Contrôle des colonnes (Présence, ordre)",0)
		
		InfoBDD.map[table].each{col,vlist ->
			
			Log.addDEBUG(vlist.join('|'))

			if (col == myJDD.getHeader((int)vlist[0])) {
				Log.addDEBUG("'$col' OK")

				//InfoBDD.updateParaInfoBDD(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())

			}else if (col in myJDD.getHeaders()) {
				
				Log.addDETAILFAIL("$JDDFullName ($sheetName) : La colonne '$col' est dans le JDD mais pas à la bonne place")
				status=false
			}else {
				Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le champ '$col' n'est pas dans le JDD")
				status=false
			}
		}

	}




	private static checkLOCATOR() {

		Log.addDEBUGDETAIL("Contrôle des LOCATOR",0)
		myJDD.getParam('LOCATOR').eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = myJDD.getHeader(i)

				if (myJDD.isTagAllowed(loc)) {
					Log.addDEBUG("$name : $loc in myJDD.TAG_LIST_ALLOWED")
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					//it's a tag with an attribut
					def lo = loc.toString().split(/\*/)
					if (myJDD.isTagAllowed(lo[0])) {
						Log.addDEBUG("$name : $name : ${loc[0]} in myJDD.TAG_LIST_ALLOWED dans $loc")
					}else {
						Log.addDETAILFAIL("$JDDFullName ($sheetName) : Champ '$name' : LOCATOR inconnu : ${lo[0]} in '$loc'")
						status=false
					}
				}else if (loc[0] == '/') {
					Log.addDEBUG("$loc OK")
				}else {
					Log.addDETAILFAIL("$JDDFullName ($sheetName) : Champ '$name' : LOCATOR inconnu : '$loc'")
					status=false
				}
			}
		}
	}









	private static checkKWInDATA() {

		Log.addDEBUGDETAIL("Contrôle des mots clés dans les DATA",0)
		myJDD.datas.eachWithIndex { li,numli ->
			li.eachWithIndex { val,i ->
				if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
					Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
					status=false
				}
			}
		}
	}




	private static checkUniquenessPK() {


		List <String> PKList = InfoBDD.getPK(myJDD.getDBTableName())

		if (PKList) {
			
			
			Log.addDEBUGDETAIL("Contrôle de l'unicité des PKs : " + PKList.join(' - '),0)
			
			List listPKi = getIndexListOfPKs(PKList)
			
			//List PKdatas = myJDD.datas.collect { ligne -> listPKi.collect { ligne[(int) it] }.join(' - ') }
			
			List PKdatas = myJDD.datas.findAll { ligne ->
				!listPKi.collect { ligne[(int)it] }.any { it.toString().startsWith('$') }
			}.collect { ligne ->
				listPKi.collect { ligne[(int)it] }.join('')
			}
			
			Map PKValMap = [:]
			PKdatas.eachWithIndex { pks,numli ->
				
				if (PKValMap.containsKey(pks)) {
					Log.addDETAILFAIL("$JDDFullName ($sheetName) : La PK $pks existe déjà en ligne " + PKValMap[pks])
					status=false
				}else {
					PKValMap[pks] = numli
				}
				
			}
		}else {
			Log.addDETAILWARNING("$JDDFullName ($sheetName) : Pas de PKs pour la table $table" )
		}
	}
	
	private static List getIndexListOfPKs(List PKList) {
		
		List list = []
		for (pk in PKList) {
			list.add(myJDD.getHeaderIndexOf(pk.toString()))
		}
		return list
	}
	

}// end of class
