package tnrCheck.data

import groovy.transform.CompileStatic
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrSqlManager.InfoDB

/**
 *
 * vérifie les valeurs et les doublons de clés primaires (PK) dans les JDD/PREJDDD
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class CheckPK {

	private final String CLASS_FOR_LOG = 'CheckPK'



	private List <String> PKList
	private List PKVals = []
	private List <String> concatenedPKVals = []
	private List <String> cdts = []
	private String JDDFullname =''
	private String sheetName =''
	private boolean checkPKValuesStatus = true



	/**
	 * Constructeur
	 *
	 * @param jddFullname : Nom du JDD/PREJDD
	 * @param shName : Nom de la feuille.
	 * @param tableName : Nom de la table BD.
	 */
	CheckPK(String JDDFullname, String shName,String tableName){
		Log.addTraceBEGIN(CLASS_FOR_LOG, "CheckPK", [JDDFullname:JDDFullname, sheetName:sheetName,tableName:tableName])
		this.JDDFullname = JDDFullname
		this.sheetName = sheetName
		PKList = InfoDB.getPK(tableName)
		Log.addTraceEND(CLASS_FOR_LOG, "CheckPK")
	}


	/**
	 * Vérifie l'unicité des clés primaires.
	 *
	 * @param cdt : cas de test en cours de vérif.
	 * @param numLine : Numéro de ligne du cas de test en cours de verif.
	 * @return : Retourne true s'il n'y a pas de doublons
	 */
	public boolean checkDuplicates( String cdt, int numLine) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "checkDuplicates", [ cdt:cdt, numLine:numLine])
		boolean status = true
		if (checkPKValuesStatus) {
			// on concatene les PKVals
			String concatenedPKVal = PKVals.join(' - ')
			if ( (!PKVals) ||concatenedPKVal.contains(JDDKW.getKW_SEQUENCEID()) || concatenedPKVal.contains(JDDKW.getKW_ORDRE())) {
				Log.addTrace(" PKVals est vide ou concatenedPKVal contient SEQUENCEID ou ORDRE, on skipe le controle : $PKVals")
			}else {
				cdts.add(cdt)
				// si ces valeurs ont déjà été détectées c'est un doublon
				if (concatenedPKVals.contains(concatenedPKVal)) {
					int i = concatenedPKVals.indexOf(concatenedPKVal)
					String c = cdts[i]
					Log.addDETAILFAIL("$JDDFullname ($sheetName) ligne ${numLine} cas de test:$cdt, la PK '$concatenedPKVal' existe déjà en ligne ${i+1} cas de test:$c")
					status=false
				}else {
					// sinon on les mémorise
					concatenedPKVals.add(concatenedPKVal)
				}
			}
		}
		PKVals = []
		checkPKValuesStatus = true
		Log.addTraceEND(CLASS_FOR_LOG, "checkDuplicates", status)
		return status
	}


	/**
	 * Vérifie les valeurs des clés primaires.
	 *
	 * @param cdt : Cas de test enc ours.
	 * @param numLine : Numéro de ligne du Cas de test en cours.
	 * @param name : Nom du champ.
	 * @param val : Valeur du champ.
	 * @return : Retourne true si la vérification est correcte
	 */
	public boolean checkPKValues(String cdt, int numLine, String name, def val) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "checkPKValues", [cdt:cdt , numLine:numLine , name:name , val:val])
		if (name in PKList) {
			if (JDDKW.isUPD(val)) {
				PKVals.add(JDDKW.getOldValueOfKW_UPD(val))
			}else if (JDDKW.isTBD(val)) {
				PKVals.add(JDDKW.getValueOfKW_TBD(val))
			}else if (val) {
				PKVals.add(val)
			}else {
				Log.addDETAILFAIL("$JDDFullname ($sheetName) ligne ${numLine} cas de test:$cdt, la PK est vide ou null")
				checkPKValuesStatus=false
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG, "checkPKValues",checkPKValuesStatus)
		return checkPKValuesStatus
	}


}// Fin de class
