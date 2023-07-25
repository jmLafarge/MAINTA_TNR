import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook




//PREJDDFiles.insertPREJDDinDB('RO.CAT','001')
//PREJDDFiles.insertPREJDDinDB('RO.HAB','001') // faut traiter les $VIDE et $DATESYS
//PREJDDFiles.insertPREJDDinDB('RO.MET','001')




XSSFWorkbook book = my.XLS.open('TNR_PREJDD/PREJDD.RO.CAL.xlsx')

for(Sheet sheet: book) {

	List headersPREJDD = my.XLS.loadRow(sheet.getRow(0))
	List datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())
	List PKList = InfoBDD.getPK('CALDEF')
	
	
	println '----------------------------------------------'
	println sheet.getSheetName()
	println '----------------------------------------------'
	
	println 'PKLIST : ' + PKList 
	
	Map PKval = [:]
	
	if (headersPREJDD.size()>1) {
		
		datas.eachWithIndex { li,numli ->
			List PKnames = []
			List PKvalues = []
			li.eachWithIndex { val,i ->
				if (headersPREJDD.get(i) in PKList) {
					//TNRResult.addDETAIL("- CDT : " + li[0] + ' PK : ' + headersPREJDD.get(i) +' = ' + val)
					PKvalues.add(val)
				}
			}
			if (PKval.containsKey(PKvalues.join('-'))) {
				println "La valeur '" + PKvalues.join('-') + "' en ligne " + (numli+2) + " existe déjà en ligne " + (PKval.getAt(PKvalues.join('-')) + 2)
			}else {
				PKval.put(PKvalues.join('-'),numli)
			}

		}
	}
	
	
	
	
	
	
	/*
	 * 	if (headersPREJDD.size()>1) {
		
		datas.eachWithIndex { li,numli ->
			li.eachWithIndex { val,i ->
				if (headersPREJDD.get(i) in PKList) {
					//TNRResult.addDETAIL("- CDT : " + li[0] + ' PK : ' + headersPREJDD.get(i) +' = ' + val)
					
					println "- CDT : " + li[0] + ' PK : ' + headersPREJDD.get(i) +' = ' + val
					if (PKval.containsKey(val)) {
						println "La valeur '$val' en ligne " + (numli+2) + " existe déjà en ligne " + (PKval.getAt(val) + 2)
					}else {
						PKval.put(val,numli)
					}
					
					
					
				}
			}
		}
	}

	 */
	
}
