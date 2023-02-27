package my

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.sql.Sql
import internal.GlobalVariable

public class SQL {
	/**
	 * 
	 */


	private static sql = Sql.newInstance(GlobalVariable.BD_URL, GlobalVariable.BD_USER, GlobalVariable.BD_MDP)




	private checkSEQUENCEID(JDD myJDD) {
	}





	/**
	 *
	 * @param
	 * @return
	 *
	 *
	 *
	 */
	static checkJDDWithBD3(JDD myJDD,Map specificValueMap=[:]){

		WebUI.delay(1)

		boolean pass = true
		my.Log.addSTEP("Début de la vérification des valeurs en Base de Données")
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()

		myJDD.replaceSEQUENCIDInJDD()

		if (nbrLigneCasDeTest>1) {
			my.Log.addSUBSTEP("Contrôle cas de test ${myJDD.getCasDeTestNum()} / $nbrLigneCasDeTest")
		}
		String query = "SELECT * FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK3(myJDD)
		my.Log.addDEBUG("query =  $query")

		def rows = sql.rows(query)
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

						this.checkForeignKey(myJDD, fieldName, val)
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


		if (pass) {
			my.Log.addSTEPPASS("Fin de la vérification des valeurs en Base de Données")
		}else {
			my.Log.addSTEPFAIL("Fin de la  vérification des valeurs en Base de Données")
		}
	}





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

			def rows = sql.rows(query)
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

							this.checkForeignKey(myJDD, fieldName, val)
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









	private static checkForeignKey(JDD myJDD, String fieldName, def val) {
		String query = myJDD.getSqlForForeignKey(fieldName)
		def frow = this.sql.firstRow(query)
		if (frow == null) {
			my.Log.addDETAILFAIL("Contrôle valeur $fieldName KO, pas de résultat pour la query : $query")
		}else if (val != frow.getAt(0)) {
			my.Log.addDETAILFAIL("Contrôle valeur $fieldName KO : ** la valeur du JDD attendue est : " + myJDD.getData(fieldName) + ' et la valeur est BD est : ' +  frow.getAt(0))
		}else {
			my.Log.addDEBUG("Contrôle valeur $fieldName OK : la valeur attendue est : " + frow.getAt(0) + " et la valeur en BD est : $val")
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

	static checkIDNotInBD3(JDD myJDD){

		WebUI.delay(1)
		boolean pass = true
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()

		my.Log.addSTEP("Début de la vérification de la suppression des valeurs en Base de Données")


		if (nbrLigneCasDeTest>1) {
			my.Log.addSUBSTEP("Contrôle de la suppression du cas de test ${myJDD.getCasDeTestNum()} / $nbrLigneCasDeTest")
		}
		String query = "SELECT count(*) FROM " + myJDD.getDBTableName() + this.getWhereWithAllPK(myJDD)

		my.Log.addDEBUG("query =  $query")

		def row = this.sql.firstRow(query)


		if (row[0]>0) {
			my.Log.addDETAILFAIL("Supression KO")
			pass = false
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

			def row = this.sql.firstRow(query)


			if (row[0]>0) {
				my.Log.addDETAILFAIL("Supression KO")
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
			def keys = this.sql.executeInsert req
			// dans le cas où il y a un ID auto ?
			//println keys.getClass()
			//println keys
		}
		catch(Exception e) {
			my.Log.addERROR("Erreur d'execution de insertSQL() : " + e)
		}

	}




	static int getMaxFromTable(String fieldName, String tableName) {

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