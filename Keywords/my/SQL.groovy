package my

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.sql.Sql
import internal.GlobalVariable

public class SQL {
	/**
	 * 
	 */


	private static sqlInstance = Sql.newInstance(GlobalVariable.BDD_URL, GlobalVariable.BDD_USER, GlobalVariable.BDD_MDP)



	/**
	 * Vérification à la fin des insertions, donc dans le cas de 2 insertions, il faut boucler sur le nombre d'insertions
	 *
	 * @param
	 * @return
	 *
	 *
	 *
	 */
	static checkJDDWithBD(JDD myJDD,Map specificValueMap=[:]){

		WebUI.delay(1)

		boolean pass = true
		my.Log.addSTEP("Début de la vérification des valeurs en Base de Données")
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()


		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				my.Log.addSUBSTEP("Contrôle cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}
			String query = "SELECT * FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD,casDeTestNum)
			my.Log.addDEBUG("query =  $query")

			def rows

			try {

				rows = sqlInstance.rows(query)
			}
			catch(Exception ex) {
				pass=false
				my.Log.addERROR("Erreur d'execution de sql.rows($query) : ")
				my.Log.addDETAIL(ex.getMessage())
			}

			my.Log.addDEBUG("size =  ${rows.size()}")

			if (rows.size() == 0) {
				my.Log.addERROR("Pas de résultat pour la requête : $query")
				pass = false
			}else if (rows.size() > 1){
				my.Log.addERROR(rows.size() + "résultats pour la requête : $query")
				pass = false
			}else {
				rows.each { row ->

					row.each{fieldName,val ->

						my.Log.addDEBUG("fieldName = $fieldName , val = $val , JDD value = " + myJDD.getData(fieldName))

						boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)

						if (!specificValue && myJDD.getParamForThisName('FOREIGNKEY', fieldName)!=null) {

							if (!this.checkForeignKey(myJDD, fieldName, val)) {
								pass = false
							}
						}else {
							// test valeur du JDD
							switch (myJDD.getData(fieldName)) {

								case my.JDDKW.getKW_VIDE() :
								case my.JDDKW.getKW_NULL():
									if (val == null || val =='') {

										my.Log.addDEBUG("Contrôle de la valeur de $fieldName OK : la valeur attendue est VIDE ou null et la valeur en BD est  : $val" )
									}else {
										my.Log.addDETAIL("Contrôle de la valeur de $fieldName KO : la valeur attendue est VIDE ou null et la valeur en BD est  : $val")
										pass = false
									}

									break

								case my.JDDKW.getKW_DATE() :

									my.Log.addDETAIL("Contrôle de la valeur DATE de $fieldName KO : ******* reste à faire ******* la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
									pass = false
									break

								case my.JDDKW.getKW_DATETIME() :

									if (val instanceof java.sql.Timestamp) {
										my.Log.addDEBUG("Contrôle de la valeur DATETIME de $fieldName OK : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val " )
									}else {
										my.Log.addDETAIL("Contrôle de la valeur DATETIME de $fieldName KO : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
										pass = false
									}
									break

								case my.JDDKW.getKW_SEQUENCEID() :

									my.Log.addDETAIL("Contrôle IDINTERNE valeur $fieldName KO : ******* reste à faire la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
								//il faut peut etre testé si la valeur est num et unique ? ******

									my.Log.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' dans le JDD : ' + myJDD.getData(fieldName).getClass())
									if ( val == myJDD.getData(fieldName)) {
										my.Log.addDEBUG("Contrôle de la valeur SEQUENCEID de $fieldName OK : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val " )
									}else {
										my.Log.addDETAIL("Contrôle de la valeur SEQUENCEID de $fieldName KO : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
										pass = false
									}

									break

								case my.JDDKW.getKW_ORDRE() :

									my.Log.addDETAIL("Contrôle de la valeur ORDRE de $fieldName KO : ******* reste à faire la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
								//voir aussi le NU_NIV *******
									pass = false
									break

								default:

									if (specificValue) {
										my.Log.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' la valeur spécifique est  : ' + specificValueMap[fieldName].getClass())
										if ( val == my.InfoBDD.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
											my.Log.addDEBUG("Contrôle de la valeur spécifique de $fieldName OK : la valeur attendue est : " + specificValueMap[fieldName] + " et la valeur en BD est : $val " )
										}else {
											my.Log.addDETAIL("Contrôle de la valeur spécifique de $fieldName KO : la valeur attendue est : " + specificValueMap[fieldName] + " et la valeur en BD est : $val")
											pass = false
										}
									}else {
										my.Log.addDEBUG("Pour '$fieldName' en BD :" + val.getClass() + ' dans le JDD : ' + myJDD.getData(fieldName).getClass())
										if ( val == my.InfoBDD.castJDDVal(myJDD.getDBTableName(), fieldName, myJDD.getData(fieldName))) {
											my.Log.addDEBUG("Contrôle de la valeur de $fieldName OK : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val " )
										}else {
											my.Log.addDETAIL("Contrôle de la valeur de $fieldName KO : la valeur attendue est : " + myJDD.getData(fieldName) + " et la valeur en BD est : $val")
											pass = false
										}
									}
									break
							}//case
						}//else
					}//row
				}//rows
			}//else
		}//else
		if (pass) {
			my.Log.addSTEPPASS("Fin de la vérification des valeurs en Base de Données")
		}else {
			my.Log.addSTEPFAIL("Fin de la  vérification des valeurs en Base de Données")
		}
	}


	
	private static getFirstRow(String query) {
		def frow = null
		try {
			frow = sqlInstance.firstRow(query)
		}catch(Exception ex) {
			my.Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
			my.Log.addDETAIL(ex.getMessage())
		}
		return frow
	}
	
	

	public static String getMaintaVersion() {
		
		String query = "SELECT ST_VAL FROM VER WHERE ID_CODINF = 'CURR_VERS'"

		def frow = this.getFirstRow(query)

		if (frow == null) {
			my.Log.addDEBUG("getMaintaVersion() est null")
			return null
		}else {
			String val = frow.getAt(0).toString()
			my.Log.addDEBUG("getMaintaVersion() est $val")
			return val
		}
	}






	private static boolean checkForeignKey(JDD myJDD, String fieldName, def val) {
		boolean pass = true
		String query = myJDD.getSqlForForeignKey(fieldName)

		def frow = sqlInstance.firstRow(query)

		if (frow == null) {
			my.Log.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO, pas de résultat pour la query : $query")
			pass = false
		}else if (val != frow.getAt(0)) {
			my.Log.addDETAIL("Contrôle de la valeur de $fieldName (FK) KO : ** la valeur du JDD attendue est : " + myJDD.getData(fieldName) + ' et la valeur est BD est : ' +  frow.getAt(0))
			pass = false
		}else {
			my.Log.addDEBUG("Contrôle de la valeur de $fieldName (FK) OK : la valeur attendue est : " + frow.getAt(0) + " et la valeur en BD est : $val")
		}
		return pass
	}




	/**
	 * Dans le cas d'une vérif 	à la fin des insertions, donc dans le cas de 2 insertions, il faut boucler sur le nombre d'insertions
	 * @param myJDD
	 * 
	 * @return
	 */

	static checkIDNotInBD(JDD myJDD){

		WebUI.delay(1)
		boolean pass = true
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()

		my.Log.addSTEP("Début de la vérification de la suppression des valeurs en Base de Données")
		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				my.Log.addSUBSTEP("Contrôle de la suppression du cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}
			String query = "SELECT count(*) FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD,casDeTestNum)

			my.Log.addDEBUG("query =  $query")

			def row

			try {
				row = sqlInstance.firstRow(query)

			}catch(Exception ex) {
				pass = false
				my.Log.addERROR("Erreur d'execution de sqlInstance.firstRow($query) : ")
				my.Log.addDETAIL(ex.getMessage())
			}

			if (row[0]>0) {
				my.Log.addDETAIL("Supression KO")
				pass = false
			}
		}

		if (pass) {
			my.Log.addSTEPPASS("Fin de la vérification de la suppression des valeurs en Base de Données")
			//KeywordUtil.markPassed("Supression OK")
		}else {
			my.Log.addSTEPFAIL("Fin de la  vérification de la suppression des valeurs en Base de Données")
			//KeywordUtil.markFailed("Supression KO")
		}
	}



	/**
	 * 
	 * @param myJDD
	 * @param casDeTestNum
	 * @return
	 */
	private static String getWhereWithAllPK(JDD myJDD,int casDeTestNum) {
		List PKList = my.InfoBDD.getPK(myJDD.getDBTableName())
		String query = ' WHERE '
		PKList.each {
			query = query + it + "='" + myJDD.getData(it,casDeTestNum) + "' and "
		}
		return query.substring(0,query.length()-5)
	}




	static insertSQL(String req) {
		try {
			def nbRowInserted = sqlInstance.executeInsert req
			if (nbRowInserted <= 0) {
				my.Log.addDEBUG("insertSQL($req) OK, nombre de ligne inséré : ${nbRowInserted}")
			}else {
				my.Log.addERROR("Erreur d'execution de insertSQL() : $req")
			}
			return nbRowInserted
		}
		catch(Exception ex) {
			my.Log.addERROR("Erreur d'execution de insertSQL() : ")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}

	}




	static int getMaxFromTable(String fieldName, String tableName) {

		my.Log.addDEBUG("getMaxFromTable(String fieldName, String tableName) '$fieldName' , '$tableName'")

		String req = "SELECT MAX($fieldName) as num FROM $tableName"

		try {
			int num = sqlInstance.firstRow(req).num
			my.Log.addDETAIL("get Max '$fieldName From Table '$tableName' = $num")
			return num
		} catch(Exception ex) {
			my.Log.addERROR("Erreur d'execution de getMaxFromTable() : ")
			my.Log.addDETAIL(ex.getMessage())
		}
		return null
	}

} // end of class