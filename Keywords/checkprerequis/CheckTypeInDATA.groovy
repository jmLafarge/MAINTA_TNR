package checkprerequis

import groovy.transform.CompileStatic
import internal.GlobalVariable
import my.InfoDB
import my.JDD
import my.Log
import my.NAV
import my.JDDKW


@CompileStatic
public class CheckTypeInDATA {

	public static boolean run(List <List> datas, JDD myJDD, String table, String filename, boolean status) {
		
		Log.addDEBUGDETAIL("Contrôle des types dans les DATA",0)

		List PKlist=InfoDB.getPK(table)

		datas.eachWithIndex { li,numli ->

			li.eachWithIndex { val,i ->
				String name = myJDD.getHeaderNameOfIndex((int)i)
				if (i!=0 && InfoDB.inTable(table, name) && !myJDD.isFK(name)) {

					boolean ctrlVal = true

					String cdtName = li[0]


					// Cas des val TBD
					if (JDDKW.startWithTBD(val)) {

						Log.addTrace("Détection d'une valeur TBD sur $name '$val'")

						def newValue = JDDKW.getValueOfKW_TBD(val)
						// si une valeur de test existe, on remplace la valeur du JDD par cette valeur
						if (newValue) {
							val = newValue
							Log.addToList('TBDOK',"$filename\t$cdtName\t$table.$name\t${val.toString()}")
						}else {
							Log.addToList('TBDKO',"$filename\t$cdtName\t$table.$name")
							ctrlVal = false
						}

					}
					
					/*
					if (JDDKW.startWithUPD(val)) { // cas d'un JDD
						if (JDDKW.isUPD(val)) {
							Log.addTrace("Détection d'un \$UPD, valeur = '${JDDKW.getOldValueOfKW_UPD(val)}', nouvelle valeur = '${JDDKW.getNewValueOfKW_UPD(val)}'")
							
						}else {
							Log.addDETAILFAIL("Format du \$UPD non correct : '$val'")
							ctrlVal = false
						}
						
					}
					*/

					if (myJDD.isOBSOLETE(name) || val.toString()=='$NU') ctrlVal = false

					if (isCasDeTestSUPorREC(cdtName) && !PKlist.contains(name)  && val.toString()=='') ctrlVal = false

					if ( ctrlVal) {
						// cas d'un champ lié à une INTERNALVALUE
						String IV = myJDD.getParamForThisName('INTERNALVALUE',name)

						if (IV) {

							if (val && val !='$NULL') {

								Log.addTrace("Détection d'une IV sur $name, IV= $IV value=$val ")

								String internalVal = NAV.myGlobalJDD.getInternalValueOf(IV, val.toString())

								if (internalVal) {
									val = internalVal
								}else {
									Log.addDETAILFAIL(cdtName + "($table.$name) : La valeur '$val' n'est pas autorisé pour une internal value de type '$IV'")
									status = false
									ctrlVal=false
								}


							}else {
								Log.addTrace("Détection d'une INTERNALVALUE sur $name, IV= $IV la valeur est vide ou null")
								ctrlVal=false
							}

						}
					}


					if ( ctrlVal) {

						switch (InfoDB.getDATA_TYPE(table, name)){

							case InfoDB.getNumeric() :
								if (val.toString().isNumber() || val in ['$NULL', '$SEQUENCEID', '$ORDRE']) {
									// c'est bon
									Log.addTrace("$table.$name est un numeric autorisé = '$val'")
								}else {
									Log.addDETAILFAIL(cdtName + "($table.$name) : La valeur '$val' n'est pas autorisé pour un champ numérique")
									status = false
								}
								break
							case InfoDB.getVarchar() :

								if (!(val in ['$VIDE', '$NULL']) && val.toString().length() > InfoDB.getDATA_MAXCHAR(table, name)) {

									Log.addDETAILFAIL(cdtName +" ($table.$name) : La valeur $val est trop longue,  "+val.toString().length() + ' > ' + InfoDB.getDATA_MAXCHAR(table, name) )
									status = false
								}else {

									Log.addTrace(cdtName +" ($table.$name) : La valeur $val est un varchar autorisé,  "+val.toString().length() + ' / ' + InfoDB.getDATA_MAXCHAR(table, name) )
								}
								break
							default :
								Log.addTrace("$table.$name est de type "+ InfoDB.getDATA_TYPE(table, name) + " = '$val' : " )
						}
					}
				}
			}
		}
		return status
	}


	private static boolean isCasDeTestSUPorREC(String cdt){

		def pattern = /(\.SUP\.|\.REC\.)\d{2}$/
		return cdt =~ pattern
	}
}
