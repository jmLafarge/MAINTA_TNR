package checkprerequis

import groovy.transform.CompileStatic
import my.InfoBDD
import my.JDD
import my.Log

@CompileStatic
public class CheckTypeInDATA {

	public static run(List <List> datas, JDD myJDD, String table) {

		List PKlist=InfoBDD.getPK(table)

		Log.addDEBUGDETAIL("Contrôle des types dans les DATA",0)
		datas.eachWithIndex { li,numli ->

			li.eachWithIndex { val,i ->
				String name = myJDD.getHeaderNameOfIndex((int)i)
				if (i!=0 && InfoBDD.inTable(table, name) && !myJDD.isFK(name)) {

					boolean ctrlVal = true

					String cdtName = li[0]

					if (myJDD.isOBSOLETE(name) || val.toString()=='$NU') ctrlVal = false

					if (isCasDeTestSUPorREC(cdtName) && !PKlist.contains(name)  && val.toString()=='') ctrlVal = false
					
					if ( ctrlVal) {
						// cas d'un champ lié à une INTERNALVALUE
						String IV = myJDD.getParamForThisName('INTERNALVALUE',name)
						
						if (IV) {
							
							if (val) {
							
								String internalVal = my.PropertiesReader.getMyProperty('IV_' + IV + '_' + val)
								
								Log.addDEBUG("Détection d'une IV sur $name, IV= $IV value=$val internal value =$internalVal")
								
								val = internalVal
							}else {
								Log.addDETAILFAIL("Détection d'une INTERNALVALUE sur $name, IV= $IV la valeur est vide ou null")
								ctrlVal=false
							}
							
						}
					}


					if ( ctrlVal) {

						switch (InfoBDD.getDATA_TYPE(table, name)){

							case InfoBDD.getNumeric() :
								if (val.toString().isNumber() || val in ['$NULL', '$SEQUENCEID', '$ORDRE']) {
									// c'est bon
									Log.addDEBUG("$table.$name est un numeric autorisé = '$val'")
								}else {
									Log.addDETAILFAIL(cdtName + "($table.$name) : La valeur '$val' n'est pas autorisé pour un champ numérique")
								}
								break
							case InfoBDD.getVarchar() :

								if (!(val in ['$VIDE', '$NULL']) && val.toString().length() > InfoBDD.getDATA_MAXCHAR(table, name)) {

									Log.addDETAILFAIL(cdtName +" ($table.$name) : La valeur $val est trop longue,  "+val.toString().length() + ' > ' + InfoBDD.getDATA_MAXCHAR(table, name) )
								}else {

									Log.addDEBUG(cdtName +" ($table.$name) : La valeur $val est un varchar autorisé,  "+val.toString().length() + ' / ' + InfoBDD.getDATA_MAXCHAR(table, name) )
								}
								break
							default :
								Log.addDEBUG("$table.$name est de type "+ InfoBDD.getDATA_TYPE(table, name) + " = '$val' : " )
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
