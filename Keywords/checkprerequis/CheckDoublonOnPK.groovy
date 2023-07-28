package checkprerequis

import groovy.transform.CompileStatic
import my.InfoDB
import my.JDD
import my.Log
import my.JDDKW


@CompileStatic
public class CheckDoublonOnPK {
	
	
	static boolean run(List <List> datas, List <String> headers, String table, String JDDFullName, String sheetName, boolean status,boolean withDetails) {
		
		List <String> PKList = InfoDB.getPK(table)
		
		if (PKList) {
		
			Log.addDEBUGDETAIL("Contrôle absence de doublon sur PRIMARY KEY : " +  PKList.join(' , '))
	
			Map <String,Integer> PKval = [:]
	
			datas.eachWithIndex { li,numli ->
				List <String> PKnames = []
				List PKvalues = []
				li.eachWithIndex { val,i ->

					String headerName = headers.get(i)
					String valStr = val.toString()
					if (JDDKW.isAllowedKeyword(valStr)) {
						if (JDDKW.isUPD(valStr)) {
							PKvalues.add(JDDKW.getOldValueOfKW_UPD(valStr))
						}else if (JDDKW.isTBD(valStr)) {
							PKvalues.add(JDDKW.getValueOfKW_TBD(valStr))

						}else {
							return
						}
					}else if (headerName in PKList ) {
						PKvalues.add(val)
					}
				}
				if (withDetails) {
					Log.addDEBUGDETAIL("PKvalues =  " +  PKvalues.join(' , '))
				}
				if (PKvalues && PKval.containsKey(PKvalues.join('-'))) {
					Log.addDETAILFAIL("$JDDFullName ($sheetName) DATA ligne:${numli+1}: La PK '" + PKvalues.join('-') + "' existe déjà en ligne " + (PKval.getAt(PKvalues.join('-')) + 1))
					status=false
				}else {
					PKval.put(PKvalues.join('-'),numli)
				}
			}
		}else {
			Log.addDETAILWARNING("$JDDFullName ($sheetName) : Pas de PRIMARY KEY !")
		}
		
		return status
	}
}
