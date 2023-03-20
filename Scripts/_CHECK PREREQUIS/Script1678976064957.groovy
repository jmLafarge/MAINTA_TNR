
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook





my.Log.addSubTITLE('Vérification des JDD')
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
			
			//my.Log.addINFO("                  Onglet : " + sheet.getSheetName())
			my.Log.addSUBSTEP("Onglet : " + sheet.getSheetName())
			my.Log.addDETAIL("Contrôle de la liste des paramètres")
			myJDD.loadTCSheet(sheet)
			//if (fullName.split('\\\\')[-1] in JDDTOSKIP) {
			if (myJDD.getDBTableName()=='') {
				my.Log.addDETAIL("Pas de table DB")
			}else {
				if (my.InfoBDD.isTableExist(myJDD.getDBTableName())) {
					my.Log.addDETAIL("Contrôle de la table DB '" + myJDD.getDBTableName() + "'")
					
					if (myJDD.headers.size()>1) {
						my.Log.addDETAIL("Contrôle des colonnes (Présence, ordre, prérequis, foreignkey)")
						my.InfoBDD.colnameMap[myJDD.getDBTableName()].eachWithIndex{col,index ->
							
							if (col == myJDD.headers[index+1]) {
								my.Log.addDEBUG("'$col' OK")
								
								my.InfoBDD.updateParaInfoBDD(myJDD, col,fullName, modObj+'.'+sheet.getSheetName()) 


							}else if (col in myJDD.headers) {
								my.Log.addDETAILFAIL("'$col' est dans le JDD mais pas à la bonne place")
							}else {
								my.Log.addDETAILFAIL("Le champ '$col' n'est pas dans le JDD")
							}
							
							
						}
					}else {
						my.Log.addDETAILFAIL("Pas de colonnes ! ")
					}
					
				}else {
					my.Log.addDETAILFAIL("Contrôle de la table DB KO, la table '" + myJDD.getDBTableName() + "' n'existe pas !")
				}
			}
			
			if (myJDD.headers.size()>1) {
				
				my.Log.addDETAIL("Contrôle des mots clés dans les DATA")
				myJDD.datas.eachWithIndex { li,numli ->
					li.eachWithIndex { val,i ->
						if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
							my.Log.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
							
						}
					}
				}
			}
		}
	}
}


my.InfoBDD.write()


my.Log.addSubTITLE('Vérification des PREJDD')
/*
 * Controle si toutes les champs de la table sont bien des colonnes du PREJDD
 * Contrôle les mots clés dans les DATA
 * Controle qu'il n'y ait pas de doublons dans les PREJDD par rapport au PRIMARY KEY
 */

my.PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->
	
	my.Log.addSTEP("Lecture du PREJDD : $fullName")
	
	def myJDD = new my.JDD(my.JDDFiles.JDDfilemap.getAt(modObj),null,null,false)
	
	XSSFWorkbook book = my.XLS.open(fullName)
	
	for(Sheet sheet: book) {

		if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {
			
			myJDD.loadTCSheet(myJDD.book.getSheet(sheet.getSheetName()))
			List headersPREJDD = my.XLS.loadRow(sheet.getRow(0))
			List datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())
			List PKList = my.InfoBDD.getPK(myJDD.getDBTableName())
			
			my.Log.addSUBSTEP("Onglet : " + sheet.getSheetName() )
			
			Map PKval = [:]
			
			if (headersPREJDD.size()>1) {
				
				
				my.Log.addDETAIL("Contrôle des colonnes")
				/*
				my.InfoBDD.colnameMap[myJDD.getDBTableName()].each{
					if (it in headersPREJDD) {
						my.Log.addDEBUG("'$it' OK")
					}else {
						my.Log.addDETAILFAIL("Le champ '$it' n'est pas dans le PREJDD")
					}
				}
				*/
				my.InfoBDD.colnameMap[myJDD.getDBTableName()].eachWithIndex{col,index ->
					
					if (col == headersPREJDD[index+1]) {
						my.Log.addDEBUG("'$col' OK")
					}else if (col in headersPREJDD) {
						my.Log.addDETAILFAIL("'$col' est dans le PREJDD mais pas à la bonne place")
					}else {
						my.Log.addDETAILFAIL("Le champ '$col' n'est pas dans le PREJDD")
					}
				}
				
				
				my.Log.addDETAIL("Contrôle des mots clés dans les DATA")
				
				datas.eachWithIndex { li,numli ->
					li.eachWithIndex { val,i ->
						if ((val instanceof String) && val.startsWith('$') && !my.JDDKW.isAllowedKeyword(val)) {
							my.Log.addDETAILFAIL("- Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
						}
					}
				}
				

				my.Log.addDETAIL("Contrôle absence de doublon sur PRIMARY KEY : " +  PKList.join(' , '))
				
				datas.eachWithIndex { li,numli ->
					List PKnames = []
					List PKvalues = []
					li.eachWithIndex { val,i ->
						if (headersPREJDD.get(i) in PKList) PKvalues.add(val)
					}
					if (PKval.containsKey(PKvalues.join('-'))) {
						my.Log.addDETAILFAIL("La valeur '" + PKvalues.join('-') + "' en ligne " + (numli+2) + " existe déjà en ligne " + (PKval.getAt(PKvalues.join('-')) + 2))
					}else {
						PKval.put(PKvalues.join('-'),numli)
					}
				}
			}
		}
	}
}







my.Log.addSubTITLE('Collecte de tous les PREREQUIS JDD')
/*
 * Récupére la liste de tous les PREREQUIS de tous les JDD
 */
List list =[]
my.JDDFiles.JDDfilemap.each { modObj,fullName ->
	def myJDD = new my.JDD(fullName)
	myJDD.getAllPrerequis(list)
}





my.Log.addSubTITLE('Contrôle des PREREQUIS dans les PREJDD')
my.Log.addSUBSTEP("Détails en cas d'erreur")
my.Log.addDETAIL('    CAS DE TEST      -     VALEUR')
my.Log.addINFO('')
/*
 * Controle si tous les PREREQUIS des JDD sont bien dans les PREJDD
 */
list.eachWithIndex { map,idx ->
	my.Log.addDEBUG(idx + ' : ' + my.PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ')))
	map.each { key,val ->
		my.Log.addDEBUG('\t' + key + ' : ' +val)
	}
	my.PREJDD.checkPREJDD(map)
}






