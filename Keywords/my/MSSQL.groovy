package my

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil

import groovy.sql.Sql
import internal.GlobalVariable

public class MSSQL {
	/**
	 * 
	 */

	// map objet / table
	Map mObjTableName = [
		acteur:'dbo.INTER',
		article:'dbo.ART',
		organisation:'dbo.XXX',
		emplacement:'dbo.YYY']


	def sql = Sql.newInstance(GlobalVariable.BD_URL, GlobalVariable.BD_USER, GlobalVariable.BD_MDP)



	/**
	 * 
	 * @param 
	 * @return 
	 */
	@Keyword
	def verifJDD_enBD(String objTableName,String colID, String valID, Map mColVal){

		// concatener avec un separateur
		String listCol = mColVal.keySet().join(',')

		// Table de l'objet
		String objTbl = mObjTableName[objTableName]


		// requete pour lire les champs
		String query = "SELECT $listCol FROM $objTbl WHERE $colID='$valID'"

		KeywordUtil.logInfo( "QUERY : $query")

		// lire les valeurs
		def fRow = sql.firstRow(query)

		// Ctrl
		for (colVal in mColVal) {
			if (fRow[colVal.key] == colVal.value) {
				println ' - ' + colVal.key + ' OK : ' + colVal.value
			} else {
				KeywordUtil.markFailed("Controle KO pour : $colVal.key --> JDD value = $colVal.value BD value = " + fRow[colVal.key])
			}
		}
	}


	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	def verifSupprID_enBD(String objTableName,String colID, String valID){

		// Table de l'objet
		String objTbl = mObjTableName[objTableName]


		// requete pour vérifier si l'objet existe
		String query = "SELECT count(*) FROM $objTbl WHERE $colID='$valID'"

		KeywordUtil.logInfo( "QUERY : $query")

		// lire les valeurs
		def fRow = sql.firstRow(query)

		// Ctrl
		if (fRow[0] != 0 ) {
			KeywordUtil.markFailed("Controle KO : '$valID' n'a pas été supprimé")
		}
	}


}