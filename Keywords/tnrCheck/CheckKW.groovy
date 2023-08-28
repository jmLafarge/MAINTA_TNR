package tnrCheck

import groovy.transform.CompileStatic
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrSqlManager.InfoDB

/**
 *
 * vérifie les mot clés (KW) dans les JDD/PREJDDD
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class CheckKW {

	private final String CLASS_FOR_LOG = 'CheckKW'


	private String fileType =''
	private String JDDFullname =''
	private String sheetName =''


	/**
	 * CONSTRUCTOR
	 * 
	 * @param fileType Type du fichier à vérifier JDD ou PREJDD
	 * @param JDDFullname Nom complet du JDD
	 * @param sheetName Nom de la feuille 
	 */
	CheckKW(String fileType, String JDDFullname, String sheetName){
		Log.addTraceBEGIN(CLASS_FOR_LOG, "CheckKW", [fileType:fileType, JDDFullname:JDDFullname, sheetName:sheetName])
		this.fileType = fileType
		this.JDDFullname = JDDFullname
		this.sheetName = sheetName
		Log.addTraceEND(CLASS_FOR_LOG, "CheckKW")
	}


	/**
	 * Vérifie la valeur 
	 * 
	 * @param cdt cas de test
	 * @param name Nom de la colonne
	 * @param val Valeur à vérifier
	 * @return status Retourne true si la valeur est correcte, sinon false
	 */
	public boolean checkValue(String cdt, String name, def val ) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkValue",[ cdt:cdt , name:name , val:val])
		boolean status = true
		// FAIL si la valeur commence par un $ et que ce n'est pas un mot clé
		if ((val instanceof String) && val.startsWith('$') && !JDDKW.isAllowedKeyword(val)) {
			Log.addDETAILFAIL("$JDDFullname ($sheetName) : Le mot clé '$val' est inconnu. Trouvé dans le cas de test $cdt colonne $name")
			status=false
			// FAIL si dans les PREJDD on trouve un mot clé de type $UPD
		}else if (fileType=='PREJDD' && (val instanceof String) && val.startsWith('$') && JDDKW.startWithUPD(val)) {
			Log.addDETAILFAIL("$JDDFullname ($sheetName) : Le mot clé '$val' n'est pas autorisé dans les PREJDD. Trouvé dans le cas de test $cdt colonne $name")
			status=false
		}
		Log.addTraceEND(CLASS_FOR_LOG,"checkValue",status)
		return status
	}



}// Fin de class
