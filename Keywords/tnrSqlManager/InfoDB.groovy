package tnrSqlManager

import groovy.transform.CompileStatic
import tnrLog.Log


/**
 * Fournit des méthodes pour obtenir des informations sur les tables et les colonnes de la base de données.
 *
 * <p>Structure de la map 'datas' :</p>
 * <ul>
 *   <li>Clé : Nom de la table (String)</li>
 *   <li>Valeur : Map où la clé est le nom de la colonne (String) et la valeur est une Map avec les détails de la colonne (Map&lt;String, Object&gt;)</li>
 * </ul>
 *
 * 
 * @author JM Lafarge
 * @version 1.0
 * 
 * 
 **/
@CompileStatic
public class InfoDB {

	private static final String CLASS_FOR_LOG = 'InfoDB'

	/*
	 * Structure des données pour stocker les informations
	 *
	 * [TABLE_NAME : nom de la table ]
	 * 		[COLUMN_NAME : nom de la colonne]
	 * 			[ORDINAL_POSITION 	: de 1 à n correspond à l'ordre de la colonne dans la table]
	 * 			[IS_NULLABLE 		: YES ou NO --> on ne l'utilise pas pour l'instant]
	 * 			[DATA_TYPE 			: le type de la donnée (varchar, numéric,...)]
	 * 			[MAXCHAR 			: le nombre de caractere pour un varchar]
	 * 			[DOMAIN_NAME		: le nom du domaine de données]
	 * 			[CONSTRAINT_NAME	: indique si c'est une clé primaire PK_leNomDuChamp]
	 *
	 *
	 */
	private static Map  <String, Map<String, Map <String , Object>>> datas = [:]


	/**
	 * CONSTRUCTOR : lit le fichier et charget le map 'datas'
	 */
	static {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"static",[:])

		InfoDBLoader infoDB = new InfoDBLoader()
		datas = infoDB.getDatas()

		Log.addTraceEND(CLASS_FOR_LOG,"static")
	}



	/**
	 * Vérifie si une table existe.
	 *
	 * @param table Le nom de la table à vérifier.
	 * @return vrai si la table existe, faux sinon.
	 */
	public static boolean isTableExist(String table) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isTableExist",[table:table])
		boolean ret = datas.containsKey(table)
		Log.addTraceEND(CLASS_FOR_LOG,"isTableExist",ret)
		return ret
	}


	/**
	 * Vérifie si un nom est présent dans la table.
	 *
	 * @param table Le nom de la table.
	 * @param name Le nom à vérifier.
	 * @return vrai si le nom est présent, faux sinon.
	 */
	public static boolean inTable(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"inTable",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table)) {
			ret = datas[table].containsKey(name)
		}
		Log.addTraceEND(CLASS_FOR_LOG,"inTable",ret)
		return ret
	}


	/**
	 * Récupère les clés primaires pour une table spécifique.
	 *
	 * @param table Le nom de la table.
	 * @return Une liste de clés primaires.
	 */
	public static List getPK(String table) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getPK",[table:table])
		List list= []
		if (isTableExist(table)) {
			list= datas[table].findAll { entry -> entry.value.CONSTRAINT_NAME != 'NULL'}.keySet().toList()
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getPK",list)
		return list
	}



	/**
	 * Vérifie si un nom donné est une clé primaire dans une table spécifique.
	 *
	 * @param table Le nom de la table.
	 * @param name Le nom à vérifier.
	 * @return vrai si le nom est une clé primaire, faux sinon.
	 */
	public static boolean isPK(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isPK",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret= datas[table][name]['CONSTRAINT_NAME'] !='NULL'
		}
		Log.addTraceEND(CLASS_FOR_LOG,"isPK",ret)
		return ret
	}



	/**
	 * Récupère le type de données d'un nom spécifique dans une table donnée.
	 *
	 * @param table Le nom de la table.
	 * @param name Le nom à vérifier.
	 * @return Le type de données du nom.
	 */
	public static String getDATA_TYPE(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getDATA_TYPE",[table:table,name:name])
		String ret = null
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getDATA_TYPE",ret)
		return ret
	}



	/**
	 * Récupère le nombre maximal de caractères pour une colonne donnée dans une table donnée.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @return Le nombre maximal de caractères pour la colonne, ou 0 si la table ou la colonne n'existe pas
	 */
	public static int getDATA_MAXCHAR(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getDATA_MAXCHAR",[table:table,name:name])
		int ret = 0
		if (isTableExist(table) && inTable(table,name)) {
			def val = datas[table][name]['MAXCHAR']
			if (val && val!='NULL') {
				ret = (int) val
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getDATA_MAXCHAR",ret)
		return ret
	}



	/**
	 * Récupère la position ordinale d'une colonne donnée dans une table donnée.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @return La position ordinale de la colonne, ou 0 si la table ou la colonne n'existe pas
	 */
	public static int getORDINAL_POSITION(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getORDINAL_POSITION",[table:table,name:name])
		int ret = 0
		if (isTableExist(table) && inTable(table,name)) {
			def val = datas[table][name]['ORDINAL_POSITION']
			if (val && val!='NULL') {
				ret = (int) val
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getORDINAL_POSITION",ret)
		return ret
	}



	/**
	 * Vérifie si une colonne donnée dans une table donnée est de type numérique.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @return true si la colonne est de type numérique, false sinon
	 */
	public static boolean isNumeric(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isNumeric",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='numeric'
		}
		Log.addTraceEND(CLASS_FOR_LOG,"isNumeric",ret)
		return ret
	}


	/**
	 * Vérifie si une colonne donnée dans une table donnée est de type image.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @return true si la colonne est de type image, false sinon
	 */
	public static boolean isImage(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isImage",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='image'
		}
		Log.addTraceEND(CLASS_FOR_LOG,"isImage",ret)
		return ret
	}



	/**
	 * Vérifie si une colonne donnée dans une table donnée est de type varchar.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @return true si la colonne est de type varchar, false sinon
	 */
	public static boolean isVarchar(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isImage",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='varchar'
		}
		Log.addTraceEND(CLASS_FOR_LOG,"isImage",ret)
		return ret
	}


	/**
	 * Vérifie si une colonne donnée dans une table donnée est de type datetime.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @return true si la colonne est de type datetime, false sinon
	 */
	public static boolean isDatetime(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isDatetime",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='datetime'
		}
		Log.addTraceEND(CLASS_FOR_LOG,"isDatetime",ret)
		return ret
	}



	/**
	 * Vérifie si une colonne donnée dans une table donnée est de type booléen.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @return true si la colonne est de type booléen, false sinon
	 */
	public static boolean isBoolean(String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isBoolean",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DOMAIN_NAME']=='T_BOOLEEN'
		}
		Log.addTraceEND(CLASS_FOR_LOG,"isBoolean",ret)
		return ret
	}



	/**
	 * Convertit la valeur d'une colonne donnée en fonction de son type.
	 *
	 * @param table Le nom de la table
	 * @param name  Le nom de la colonne
	 * @param val   La valeur à convertir
	 * @return La valeur convertie
	 */
	public static castJDDVal(String table, String name, def val) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"castJDDVal",[table:table,name:name,val:val])
		def ret
		if (isVarchar(table,name)) {
			ret = val.toString()
		}else {
			ret = val
		}
		Log.addTraceEND(CLASS_FOR_LOG,"castJDDVal",ret)
		return ret
	}



	/**
	 * Récupère toutes les données pour une table donnée.
	 *
	 * @param table Le nom de la table
	 * @return Un Map contenant les détails des colonnes pour la table donnée
	 */
	public static Map<String, Map <String , Object>> getDatasForTable(String table) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getDatasForTable",[table:table])
		Map<String, Map <String , Object>> ret =[:]
		if (isTableExist(table)) {
			ret = datas[table]
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getDatasForTable",ret)
		return ret
	}
}// end of class
