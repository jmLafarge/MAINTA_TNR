package my

import com.kms.katalon.core.util.KeywordUtil

import groovy.sql.Sql
import internal.GlobalVariable

public class SQL {
	/**
	 * 
	 */


	private static sql = Sql.newInstance(GlobalVariable.BD_URL, GlobalVariable.BD_USER, GlobalVariable.BD_MDP)



	/**
	 * 
	 * @param 
	 * @return 
	 * 
	 * 
	 *  
	 */
	static public checkJDDWithBD(JDD myJDD){

		boolean checkStatus = true

		my.Log.addSTEP("Démarrage vérification en Base de Données")

		String query = "SELECT * FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD)
		my.Log.addDEBUG("query =  $query")

		Map rowMap = this.putRowInMap(this.sql.firstRow(query))

		if (rowMap.size() == 0) {

			my.Log.addFAIL("Pas de résultat pour la requête : $query")
		}


		rowMap.each { fieldName,val ->

			my.Log.addDEBUG("fieldName = $fieldName , val = $val , JDD value = " + myJDD.getData(fieldName))

			if (myJDD.getParamForThisName('FOREIGNKEY', fieldName)!=null) {

				if (this.checkForeignKey(myJDD, fieldName, rowMap)) {
					//ok
				}else {
					checkStatus = false
				}
			}else {
				// test valeur du JDD
				switch (myJDD.getData(fieldName)) {

					case '$VIDE' :
					case '$NULL' :
						if (rowMap.getAt(fieldName) == null || val =='') {

							my.Log.addDEBUG("Contrôle valeur $fieldName OK : la valeur attendue est VIDE ou null et la valeur en BD est  : $val" )
						}else {

							my.Log.addDETAILFAIL("Contrôle valeur $fieldName KO : la valeur attendue est VIDE ou null et la valeur en BD est  : $val")
							checkStatus = false
						}

						break

					case '$DATESYS' :

						my.Log.addDETAIL("Contrôle valeur $fieldName KO : ******* reste à faire **********")

						break

					case '$DATETIMESYS' :

						my.Log.addDETAIL("Contrôle valeur $fieldName KO : ******* reste à faire **********")

						break

					case '$IDINTERNE' :

						my.Log.addDETAIL("Contrôle valeur $fieldName KO : ******* reste à faire ****peut etre testé si la valeur est num et unique ? ******")

						break

					case '$ORDRE' :

						my.Log.addDETAIL("Contrôle valeur $fieldName KO : ******* reste à faire *** voir aussi le NU_NIV *******")

						break

					default:

						my.Log.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' dans le JDD : ' + myJDD.getData(fieldName).getClass())

						if ( val == my.InfoBDD.castJDDVal(myJDD.getDBTableName(), fieldName, myJDD.getData(fieldName))) {

							my.Log.addDEBUG("Contrôle valeur $fieldName OK : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val " )
						}else {

							my.Log.addDETAILFAIL("Contrôle valeur $fieldName KO : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
							checkStatus = false
						}
						break
				}
			}
		}





		if (checkStatus) {

			KeywordUtil.markPassed('Vérification en Base de Données OK')
			my.Log.addSTEPPASS("Vérification en Base de Données OK")

		}else {

			KeywordUtil.markFailed('Vérification en Base de Données KO')
			my.Log.addSTEPFAIL ("Vérification en Base de Données KO")

		}

	}





	static public boolean checkForeignKey(JDD myJDD, String fieldName, Map rowMap) {

		String query = myJDD.getSqlForForeignKey(fieldName)
		def frow = this.sql.firstRow(query)

		if (frow == null) {

			my.Log.addFAIL("Contrôle valeur $fieldName KO, pas de résultat pour la query : $query")
			return false

		}else if (rowMap.getAt(fieldName) != frow.getAt(fieldName)) {

			my.Log.addFAIL("Contrôle valeur $fieldName KO : ** la valeur du JDD attendue est : " + myJDD.getData(fieldName) + ' et la valeur est BD est : ' +  frow.getAt(fieldName))
			return false

		}else {

			my.Log.addDEBUG("Contrôle valeur $fieldName OK : la valeur attendue est : " + frow.getAt(fieldName) + " et la valeur en BD est : " + rowMap.getAt(fieldName))
			return true
		}
	}




	private static Map putRowInMap(def row) {
		Map rowMap = [:]
		row.each {fieldName, val ->
			rowMap[fieldName] = val
		}
		return rowMap
	}





	/**
	 *
	 * @param
	 * @return
	 */

	static public checkIDNotInBD(JDD myJDD){

		String query = "SELECT count(*) FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD)
		my.Log.addDEBUG("query =  $query")

		def row = this.sql.firstRow(query)

		if (row[0]>0) {

			my.Log.addSTEPFAIL("Contrôle supression KO")
			KeywordUtil.markFailed("Contrôle supression KO")
		}else {

			my.Log.addSTEPPASS("Contrôle supression OK")
			KeywordUtil.markPassed("Contrôle supression OK")
		}
	}


	private static String getWhereWithAllPK(JDD myJDD) {
		List PKList = my.InfoBDD.getPK(myJDD.getDBTableName())
		String query = ' WHERE '
		PKList.each {
			query = query + it + "='" + myJDD.getData(it) + "' and "
		}
		return query.substring(0,query.length()-5)
	}



	static public insertSQL(String req) {
		try {
			def keys = this.sql.executeInsert req
			// dans le cas où il y a un ID auto ?
			//println keys.getClass()
			//println keys
		}
		catch(Exception e) {
			my.Log.addERROR("Erreur d'execution de insertSQL() : " + e)
		}

	}

	static public int getMaxFromTable(String fieldName, String tableName) {

		my.Log.addDEBUG("getMaxFromTable(String fieldName, String tableName) '$fieldName' , '$tableName'")

		String req = "SELECT MAX($fieldName) as num FROM $tableName"

		try {
			int num = this.sql.firstRow(req).num
			my.Log.addDETAIL("get Max '$fieldName From Table '$tableName' = $num")
			return num
		}
		catch(Exception e) {
			my.Log.addERROR("Erreur d'execution de getMaxFromTable() : " + e)
		}
		return

	}

} // end of class