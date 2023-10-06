package tnrWebUI

import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult


/**
 * Gère les mémos (Notes)
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class Memo {



	private static final String CLASS_NAME = 'Memo'

	/**
	 * Gère la mise à jour du texte des Mémos (Notes)
	 * 
	 * @param newText	: texte du mémo
	 * @param memoName	: nom du mémo
	 * @param maj		: true = MAJ, false = CRE
	 * @param myJDD		: le JDD en cas de nom de bouton 'Modifier' spécifique
	 * @param modifierNom : le nom du bouton Modifier spécifique, '' pour utiliser le nom standard de GLOBALJDD
	 * 
	 * 
	 * Exemple :
	 * pour les cas de test CRE : STEP.setMemoText(0, JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
	 * pour les cas de test MAJ : STEP.setMemoText(0, JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
	 * 
	 */
	public static void setMemoText( String newText, String memoName, boolean maj, JDD myJDD,String modifierNom) {
		Log.addTraceBEGIN(CLASS_NAME, "setMemoText",[ newTexte:newText , memoName:memoName , maj:maj ])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'setMemoText'+ myJDD.toString() + memoName)
		StepID.setParentStepID(strStepID)
		WUIWindow.init()
		// Cas où le nom du bouton modifier est différent
		if (modifierNom) {
			STEP.simpleClick( myJDD,modifierNom)
		}else {
			STEP.simpleClick( GlobalJDD.myGlobalJDD,"Memo_Modifier")
		}
		if (WUIWindow.waitForNewWindowToOpenAndSwitch()) {
			if (WUI.isElementPresent(GlobalJDD.myGlobalJDD,'Memo_Frame', (int)GlobalVariable.TIMEOUT)) {
				WUI.switchToFrame(GlobalJDD.myGlobalJDD, 'Memo_Frame')
				if (maj) {
					STEP.sendKeys( GlobalJDD.myGlobalJDD, 'Memo_Texte', Keys.chord(Keys.CONTROL, "a"))
					STEP.sendKeys( GlobalJDD.myGlobalJDD, 'Memo_Texte', Keys.chord(Keys.DELETE))
					STEP.sendKeys( GlobalJDD.myGlobalJDD, 'Memo_Texte', Keys.chord(Keys.BACK_SPACE))
				}

				STEP.setText( GlobalJDD.myGlobalJDD, 'Memo_Texte',newText)

				WebUI.switchToDefaultContent()

				STEP.simpleClick( GlobalJDD.myGlobalJDD,"Memo_Valider")
			}else {
				TNRResult.addSTEPFAIL(strStepID, "Saisie de $newText dans mémo '$memoName'")
				TNRResult.addDETAIL("'Memo_Frame' n'est pas présent")
			}
			WUI.delay( 1000)
			WUIWindow.closeWindowIfOpen()
		}else {
			TNRResult.addSTEPFAIL(strStepID, "Saisie de $newText dans mémo '$memoName'")
			TNRResult.addDETAIL("La fenetre des notes ne s'est pas ouverte")
		}
		StepID.clearParentStepID()
		WUIWindow.switchToMainWindow()
		Log.addTraceEND(CLASS_NAME, "setMemoText")
	}


} //end of class
