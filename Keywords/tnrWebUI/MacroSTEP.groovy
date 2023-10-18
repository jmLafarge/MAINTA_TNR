package tnrWebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult

/**
 *
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class MacroSTEP {

	private static final String CLASS_NAME = 'MacroSTEP'



	public static void suppression(String idNameForURL=null, String idFromtextToVerify=null) {
		Log.addTraceBEGIN(CLASS_NAME, "suppression", [idNameForURL:idNameForURL , idFromtextToVerify:idFromtextToVerify])
		//'Lecture du JDD'
		JDD myJDD = new JDD()

		for (String cdt in myJDD.getCDTList()) {

			myJDD.setCasDeTest(cdt)

			String idValue = idNameForURL ? myJDD.getStrData(idNameForURL) : myJDD.getStrData()
			String textToVerify = idFromtextToVerify ? myJDD.getStrData(idFromtextToVerify) : myJDD.getStrData()

			TNRResult.addStartTestCase(cdt)

			//'Naviguer vers la bonne url et controle des infos du cartouche'
			STEP.goToURLReadUpdateDelete(idValue)
			STEP.checkReadUpdateDeleteScreen(textToVerify)


			//'Boucle sur les lignes d\'un même TC'
			for (int i : (1..myJDD.getNbrLigneCasDeTest())) {

				if (myJDD.getNbrLigneCasDeTest()>1) {
					TNRResult.addSTEPLOOP("Supression $i / " + myJDD.getNbrLigneCasDeTest())
				}

				myJDD.setCasDeTestNum(i)

				/*
				 * de temps en temps l'alert s'ouvre furtivement sans avoir le temps d'Accepter !
				 * --> Peut être dù au fait qu'il y ait d'autres webDrivers qui tournent
				 * 		-->ne pas oublier de faire  >Tools > Web > Terminate running WebDrivers
				 *
				 * SOLUTION on boucle 3x
				 */

				//'Suppression'
				for ( n in 1..3) {
					TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
					if (STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Supprimer')) {
						if (STEP.waitAndAcceptAlert((int)GlobalVariable.TIMEOUT, (n==3) ? 'FAIL':'INFO')) {
							WUI.delay(1000)
							STEP.checkGridScreen()
							break
						}
					}else {
						break
					}
				}
			}


			//'Vérification en BD que l\'objet n\'existe plus'
			STEP.checkIDNotInBD(myJDD)

			//TNRResult.addEndTestCase()
		}
		Log.addTraceEND(CLASS_NAME, "suppression")
	}





	public static void recherche(String idNameToSearch=null, String idFromtextToVerify=null) {
		Log.addTraceBEGIN(CLASS_NAME, "recherche", [idNameToSearch:idNameToSearch , idFromtextToVerify:idFromtextToVerify])

		//'Lecture du JDD'
		JDD myJDD = new JDD()

		for (String cdt in myJDD.getCDTList()) {

			myJDD.setCasDeTest(cdt)

			String idValueToSearch = idNameToSearch ? myJDD.getStrData(idNameToSearch) : myJDD.getStrData()
			String textToVerify = idFromtextToVerify ? myJDD.getStrData(idFromtextToVerify) : myJDD.getStrData()

			TNRResult.addStartTestCase(cdt)

			//'Naviguer vers la bonne url et controle des infos du cartouche'
			STEP.goToGridURL()
			STEP.checkGridScreen()

			//'Filtrer la valeur dans la grille'
			STEP.setText(myJDD,'input_Filtre_Grille', idValueToSearch)

			//'Attendre que le nombre de record = 1'
			STEP.verifyElementVisible(GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')

			//'Vérifier que la valeur soit dans la grille filtrée'
			STEP.verifyText(myJDD,'td_Grille', textToVerify)


			//TNRResult.addEndTestCase()
		}
		Log.addTraceEND(CLASS_NAME, "recherche")
	}


} // end of class
