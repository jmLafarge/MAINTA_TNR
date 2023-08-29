package tnrCheck.data

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrJDDManager.JDDIV
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrSqlManager.InfoDB

/**
 * Vérifie la valeur d'un champ de données par rapport au type du champ dans la base de données
 *
 * @author JM Lafarge
 * @version 1.0
 *
 *
 */
@CompileStatic
public class CheckType {

	private static final String CLASS_FOR_LOG = 'CheckType'



	public static boolean run(List<Map<String, Map<String, Object>>> datasList, JDD myJDD, String table, String filename) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",['datasList.size()':datasList.size() , myJDD:myJDD , table:table , filename:filename ])

		Log.addDEBUGDETAIL("Contrôle des types dans les DATA")
		boolean status = true
		datasList.eachWithIndex { CDTmap,numli ->

			CDTmap.each { cdt,dataMap ->

				dataMap.each { name,val ->

					if (InfoDB.inTable(table, name) && !myJDD.isFK(name)) {

						boolean ctrlVal = true

						// Cas des val TBD
						if (JDDKW.startWithTBD(val)) {

							Log.addTrace("Détection d'une valeur TBD sur $name '$val'")

							def newValue = JDDKW.getValueOfKW_TBD(val)
							// si une valeur de test existe, on remplace la valeur du JDD par cette valeur
							if (newValue) {
								val = newValue
								//Log.addToList('TBDOK',"$filename\t$cdt\t$table.$name\t${val.toString()}")
								Log.addToList('TBDOK',"$filename\t$cdt\t$table.$name\t$val")
							}else {
								Log.addToList('TBDKO',"$filename\t$cdt\t$table.$name")
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

						if (myJDD.isOBSOLETE(name) || JDDKW.isNU(val)) {
							ctrlVal = false
						}

						if (isCasDeTestSUPorREC(cdt) && !InfoDB.isPK(table,name)  && val=='') {
							ctrlVal = false
						}

						if ( ctrlVal) {
							// cas d'un champ lié à une INTERNALVALUE
							String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)

							if (paraIV) {

								if (val && val !='$NULL') {

									Log.addTrace("Détection d'une IV sur $name, IV= $paraIV value=$val ")

									String internalVal = JDDIV.getInternalValueOf(paraIV, val.toString())

									if (internalVal) {
										val = internalVal
									}else {
										Log.addDETAILFAIL(cdt + "($table.$name) : La valeur '$val' n'est pas autorisé pour une internal value de type '$paraIV'")
										status = false
										ctrlVal=false
									}


								}else {
									Log.addTrace("Détection d'une INTERNALVALUE sur $name, IV= $paraIV la valeur est vide ou null")
									ctrlVal=false
								}

							}
						}


						if ( ctrlVal) {

							if (InfoDB.isNumeric(table, name)) {
								if (val.toString().isNumber() || JDDKW.isNULL(val) || JDDKW.isSEQUENCEID(val) || JDDKW.isORDRE(val)) {
									// c'est bon
									Log.addTrace("$table.$name est un numeric autorisé = '$val'")
								}else {
									Log.addDETAILFAIL(cdt + "($table.$name) : La valeur '$val' n'est pas autorisé pour un champ numérique")
									status = false
								}

							}else if (InfoDB.isVarchar(table, name)) {
								if (!(JDDKW.isNULL(val) || JDDKW.isVIDE(val)) && val.toString().length() > InfoDB.getDATA_MAXCHAR(table, name)) {
									Log.addDETAILFAIL(cdt +" ($table.$name) : La valeur $val est trop longue,  "+val.toString().length() + ' > ' + InfoDB.getDATA_MAXCHAR(table, name) )
									status = false
								}else {
									Log.addTrace(cdt +" ($table.$name) : La valeur $val est un varchar autorisé,  "+val.toString().length() + ' / ' + InfoDB.getDATA_MAXCHAR(table, name) )
								}

							}else {
								Log.addTrace("$table.$name est de type "+ InfoDB.getDATA_TYPE(table, name) + " = '$val' : " )
							}
						}
					}
				}
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"run", status)
		return status
	}




	/**
	 * Vérifie si cas de test est de type *.SUP.nn ou *.REC.nn (nn = deux chiffres)
	 * @param cdt cas de test
	 * @return true si cdt est de type *.SUP.nn ou *.REC.nn, sinon false
	 */
	private static boolean isCasDeTestSUPorREC(String cdt){

		def pattern = /.+(\.SUP\.|\.REC\.)\d{2}$/
		return cdt =~ pattern
	}
} // Fin de class
