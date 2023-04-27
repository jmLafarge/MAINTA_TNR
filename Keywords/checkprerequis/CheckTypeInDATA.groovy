package checkprerequis

import my.Log as MYLOG

import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW

import my.InfoBDD
import my.JDD as MYJDD

public class CheckTypeInDATA {

	public static run(List datas, MYJDD myJDD, String table) {

		if (myJDD.headers.size()>0) {
			
			List PKlist=InfoBDD.getPK(table)
			
			MYLOG.addDEBUGDETAIL("Contrôle des types dans les DATA",0)
			datas.eachWithIndex { li,numli ->
				li.eachWithIndex { val,i ->
					String name = myJDD.getHeaderNameOfIndex(i)
					if (i!=0 && InfoBDD.inTable(table, name) && !myJDD.isFK(name)) {
						
						boolean ctrlVal = true
						
						if (myJDD.isOBSOLETE(name) || val.toString()=='$NU') ctrlVal = false
						
						if (isCasDeTestSUPorREC(li[0]) && !PKlist.contains(name)  && val.toString()=='') ctrlVal = false
						

						if ( ctrlVal) {

							switch (InfoBDD.getDATA_TYPE(table, name)){

								case InfoBDD.getNumeric() :
									if (val.toString().isNumber() || val in [
										'$NULL',
										'$SEQUENCEID',
										'$ORDRE'
									]) {
										// c'est bon
										MYLOG.addDEBUG("$table.$name est un numeric autorisé = '$val'")
									}else {
										MYLOG.addDETAILFAIL(li[0] + "($table.$name) : La valeur '$val' n'est pas autorisé pour un champ numérique")
									}
									break
								case InfoBDD.getVarchar() :

									if (!(val in ['$VIDE', '$NULL']) && val.toString().length() > InfoBDD.getDATA_MAXCHAR(table, name)) {

										MYLOG.addDETAILFAIL(li[0] +" ($table.$name) : La valeur $val est trop longue,  "+val.toString().length() + ' > ' + InfoBDD.getDATA_MAXCHAR(table, name) )
									}else {

										MYLOG.addDEBUG(li[0] +" ($table.$name) : La valeur $val est un varchar autorisé,  "+val.toString().length() + ' / ' + InfoBDD.getDATA_MAXCHAR(table, name) )
									}
									break
								default :
									MYLOG.addDEBUG("$table.$name est de type "+ InfoBDD.getDATA_TYPE(table, name) + " = '$val' : " )
							}
						}
					}
				}
			}
		}
	}
	
	
	private static boolean isCasDeTestSUPorREC(String cdt){
		
		def pattern = /(\.SUP\.|\.REC\.)\d{2}$/
		return cdt =~ pattern
	}



}
