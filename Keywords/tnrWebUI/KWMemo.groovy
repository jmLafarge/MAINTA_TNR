package tnrWebUI

import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
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
public class KWMemo {



	private static final String CLASS_FOR_LOG = 'KWMemo'


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
	 * pour les cas de test CRE : KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
	 * pour les cas de test MAJ : KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
	 * 
	 */
	public static void setText(String newText, String memoName, boolean maj, JDD myJDD,String modifierNom) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setText",[newTexte:newText , memoName:memoName , maj:maj ])
		KWWindow.init()
		// Cas où le nom du bouton modifier est différent
		if (modifierNom) {
			KW.click(myJDD,modifierNom)
		}else {
			KW.click(NAV.myGlobalJDD,"Memo_Modifier")
		}
		if (KWWindow.waitForNewWindowToOpenAndSwitch()) {
			if (KW.isElementPresent(NAV.myGlobalJDD,'Memo_Frame', (int)GlobalVariable.TIMEOUT)) {
				//KW.switchToFrame(NAV.myGlobalJDD, 'Memo_Frame')
				if (maj) {
					KW.sendKeys(NAV.myGlobalJDD, 'Memo_Texte', Keys.chord(Keys.CONTROL, "a"))
					KW.sendKeys(NAV.myGlobalJDD, 'Memo_Texte', Keys.chord(Keys.DELETE))
					KW.sendKeys(NAV.myGlobalJDD, 'Memo_Texte', Keys.chord(Keys.SHIFT, Keys.TAB))
				}

				KW.setText(NAV.myGlobalJDD, 'Memo_Texte',newText)

				WebUI.switchToDefaultContent()

				KW.click(NAV.myGlobalJDD,"Memo_Valider")
			}else {
				TNRResult.addSTEPFAIL("Saisie de $newText dans mémo '$memoName'")
				TNRResult.addDETAIL("'Memo_Frame' n'est pas présent")
			}
			KW.delay(1)
			KWWindow.closeWindowIfOpen()
		}else {
			TNRResult.addSTEPFAIL("Saisie de $newText dans mémo '$memoName'")
			TNRResult.addDETAIL("La fenetre des notes ne s'est pas ouverte")
		}

		KWWindow.switchToMainWindow()
		Log.addTraceEND(CLASS_FOR_LOG, "setText")
	}


} //end of class
