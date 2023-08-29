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

	private final String CLASS_FOR_LOG = 'CheckType'

	private JDD myJDD
	private String tableName
	private String JDDFilename
	private String sheetName

	private boolean status



	/**
	 * Constructeur
	 * 
	 * @param myJDD       Le JDD en cours
	 * @param tableName   Le nom de la table
	 * @param JDDFilename Le nom du fichier JDD
	 * @param sheetName   Le nom de la feuille
	 */
	CheckType ( JDD myJDD, String tableName, String JDDFilename, String sheetName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"CheckType",[ myJDD:myJDD , tableName:tableName , JDDFilename:JDDFilename , sheetName:sheetName ])
		boolean status = true
		this.myJDD = myJDD
		this.tableName = tableName
		this.JDDFilename = JDDFilename
		this.sheetName = sheetName
		Log.addTraceEND(CLASS_FOR_LOG,"CheckType")
	}



	/**
	 * Vérifie la valeur du champ
	 * @param cdt   Cas de test
	 * @param name  Nom du champ à vérifier
	 * @param value Valeur à vérifier
	 * @return      Status du contrôle (true si OK, false sinon)
	 */
	public boolean checkValue(String cdt, String name, def value) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkValue",[ cdt:cdt , name:name , value:value ])
		status = true

		if (!InfoDB.inTable(tableName, name) || myJDD.isFK(name)) {
			//pas de ctrl

		}else if(myJDD.isOBSOLETE(name) || JDDKW.isNU(value)  || JDDKW.isNULL(value) ) {
			//pas de ctrl

		}else if (isCasDeTestSUPorREC(cdt) && !InfoDB.isPK(tableName,name)  && value=='') {
			//pas de ctrl

		}else {
			def newValue = value

			newValue= checkTBD(cdt,name,value)

			newValue =checkIV(cdt,name,newValue)

			if (status) {
				checkTypeDB(cdt,name,newValue)
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"checkValue",status)
		return status
	}



	/**
	 * Vérifie si la valeur du champ  est de type "TBD"
	 * @param cdt   Cas de test
	 * @param name  Nom du champ à vérifier
	 * @param value Valeur à vérifier
	 * @return      Nouvelle valeur si la valeur est de type $TBD*newValue
	 */
	private checkTBD( String cdt, String name, def value) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkTBD",[ cdt:cdt , name:name , value:value ])
		// Cas des val TBD
		if (JDDKW.startWithTBD(value)) {

			Log.addTrace("Détection d'une valeur TBD sur $name '$value'")

			def newValue = JDDKW.getValueOfKW_TBD(value)
			// si une valeur de test existe, on remplace la valeur du JDD par cette valeur
			if (newValue) {
				value = newValue
				//Log.addToList('TBDOK',"$filename\t$cdt\t$table.$name\t${val.toString()}")
				Log.addToList('TBDOK',"$JDDFilename\t$cdt\t$tableName.$name\t$value")
			}else {
				Log.addToList('TBDKO',"$JDDFilename\t$cdt\t$tableName.$name")
				status = false
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"checkTBD",value)
		return value
	}



	/**
	 * Vérifie si la valeur du champ est de type InternalValue
	 * @param cdt   Cas de test
	 * @param name  Nom du champ à vérifier
	 * @param value Valeur à vérifier
	 * @return      Nouvelle valeur si la valeur est de type InternalValue
	 */
	private checkIV( String cdt, String name, def value) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkIV",[ cdt:cdt , name:name , value:value ])
		String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)
		if (paraIV) {

			Log.addTrace("Détection d'une IV sur $name, IV= $paraIV value=$value ")

			def internalVal = JDDIV.getInternalValueOf(paraIV, value.toString())

			if (internalVal) {
				value = internalVal
			}else {
				Log.addDETAILFAIL(cdt + "($tableName.$name) : La valeur '$value' n'est pas autorisé pour une internal value de type '$paraIV'")
				status = false
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"checkIV",value)
		return value
	}



	/**
	 * Vérifie la valeur du champ par rapport au type du champ dans la BDD
	 * @param cdt   Cas de test
	 * @param name  Nom du champ à vérifier
	 * @param value Valeur à vérifier
	 */
	public void checkTypeDB( String cdt, String name, def value) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkTypeDB",[ cdt:cdt , name:name , value:value ])
		if (InfoDB.isNumeric(tableName, name)) {
			if (value.toString().isNumber() || JDDKW.isNULL(value) || JDDKW.isSEQUENCEID(value) || JDDKW.isORDRE(value)) {
				// c'est bon
				Log.addTrace("$tableName.$name est un numeric autorisé = '$value'")
			}else {
				Log.addDETAILFAIL(cdt + "($tableName.$name) : La valeur '$value' n'est pas autorisé pour un champ numérique")
				status = false
			}
		}else if (InfoDB.isVarchar(tableName, name)) {
			if (!(JDDKW.isNULL(value) || JDDKW.isVIDE(value)) && value.toString().length() > InfoDB.getDATA_MAXCHAR(tableName, name)) {
				Log.addDETAILFAIL(cdt +" ($tableName.$name) : La valeur $value est trop longue,  "+value.toString().length() + ' > ' + InfoDB.getDATA_MAXCHAR(tableName, name) )
				status = false
			}else {
				Log.addTrace(cdt +" ($tableName.$name) : La valeur $value est un varchar autorisé,  "+value.toString().length() + ' / ' + InfoDB.getDATA_MAXCHAR(tableName, name) )
			}
		}else {
			Log.addTrace("$tableName.$name est de type "+ InfoDB.getDATA_TYPE(tableName, name) + " = '$value' : " )
		}
		Log.addTraceEND(CLASS_FOR_LOG,"checkTypeDB")
	}






	/**
	 * Vérifie si cas de test est de type *.SUP.nn ou *.REC.nn (nn = deux chiffres)
	 * @param cdt cas de test
	 * @return true si cdt est de type *.SUP.nn ou *.REC.nn, sinon false
	 */
	private boolean isCasDeTestSUPorREC(String cdt){

		def pattern = /.+(\.SUP\.|\.REC\.)\d{2}$/
		return cdt =~ pattern
	}
} // Fin de class
