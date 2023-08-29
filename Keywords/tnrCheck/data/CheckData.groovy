package tnrCheck.data

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrJDDManager.JDDData
import tnrLog.Log


/**
 * Vérifie la validité des données des JDD/PREJDD.
 *
 * - vérifie l'unicité des clés primaires
 * - vérifie les mots-clés
 * - vérifie les types de données
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class CheckData {

	private static final String CLASS_FOR_LOG = 'CheckData'


	/**
	 * Effectue tous les contrôles des données des JDD/PREJDD.
	 *
	 * @param fileType  Type de fichier (JDD ou PREJDD)
	 * @param myJDD     L'objet JDD assosié 
	 * @param datas		Les données du JDD/PREJDD à vérifier
	 * @param tableName Le nom de la table associée
	 * @param filename  Le nom du JDD/PREJDD
	 * @param sheetName Le nom de la feuille du JDD/PREJDD
	 * @return          Statut du contrôle (true si OK, false sinon)
	 */
	static boolean run(String fileType,JDD myJDD, JDDData myJDDDatas, String tableName, String filename, String sheetName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[fileType:fileType , myJDD:myJDD , tableName:tableName , filename:filename , sheetName:sheetName])
		Log.addDEBUGDETAIL("Contrôle des DATA (mots clé, unicité des PKs, type par rapport à la BDD")
		boolean status = true

		CheckPK checkPK = new CheckPK(filename, sheetName,tableName)
		CheckKW checkKW = new CheckKW(fileType,filename, sheetName)
		CheckType checkType = new CheckType( myJDD, tableName, filename, sheetName)

		myJDDDatas.getList().eachWithIndex { dataLine, numLine ->
			// Pour chaque ligne de donnée
			dataLine.each { cdt,data ->
				// on controle chaque valeur
				data.each { name,val ->

					status &= checkPK.checkPKValues(cdt,numLine,name,val)

					status &= checkKW.checkValue(cdt, name, val )

					status &= checkType.checkValue( cdt, name, val )

				}
				// on vérifie les doublons de PKs
				status &=checkPK.checkDuplicates(cdt,numLine)

			}

		}
		Log.addTraceEND(CLASS_FOR_LOG,"run",status)
		return status
	}




} // Fin de class
