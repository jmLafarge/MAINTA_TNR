package checkprerequis

import my.Log as MYLOG
import my.InfoBDD as INFOBDD

public class CheckTypeInDATA {

	public static run(List datas, my.JDD myJDD, String table) {

		if (myJDD.headers.size()>0) {
			MYLOG.addDEBUGDETAIL("Contrôle des types dans les DATA",0)
			datas.eachWithIndex { li,numli ->
				li.eachWithIndex { val,i ->
					String name = myJDD.getHeaderNameOfIndex(i)
					if (i!=0 && INFOBDD.inTable(table, name) && !myJDD.isFK(name)) {

						if (val!='$NU') {

							switch (INFOBDD.getDATA_TYPE(table, name)){

								case INFOBDD.getNumeric() :
									if (val.toString().isNumber() || val in [
										'$NULL',
										'$SEQUENCEID',
										'$ORDRE'
									]) {
										// c'est bon
										MYLOG.addDEBUG("$name est un numeric autorisé = '$val'")
									}else {
										MYLOG.addDETAILFAIL(li[0] + "($name) : La valeur '$val' n'est pas autorisé pour un champ numérique")
									}
									break
								case INFOBDD.getVarchar() :

									if (!(val in ['$VIDE', '$NULL']) && val.toString().length() > INFOBDD.getDATA_MAXCHAR(table, name)) {

										MYLOG.addDETAILFAIL(li[0] +" ($name) : La valeur $val est trop longue,  "+val.toString().length() + ' > ' + INFOBDD.getDATA_MAXCHAR(table, name) )
									}else {

										MYLOG.addDEBUG(li[0] +" ($name) : La valeur $val est un varchar autorisé,  "+val.toString().length() + ' / ' + INFOBDD.getDATA_MAXCHAR(table, name) )
									}

									break
								default :
									MYLOG.addDEBUG("$name est de type "+ INFOBDD.getDATA_TYPE(table, name) + " = '$val' : " )
							}
						}
					}
				}
			}
		}
	}



}
