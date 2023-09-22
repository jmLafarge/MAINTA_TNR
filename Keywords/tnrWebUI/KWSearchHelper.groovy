package tnrWebUI

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrResultManager.TNRResult

/**
 * Gère les recherches par Assistant
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class KWSearchHelper {


	private static final String CLASS_FOR_LOG = 'KWSearchHelper'


	private static runSearchWithHelper(JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){
		Log.addTraceBEGIN(CLASS_FOR_LOG, "runSearchWithHelper",[ myJDD:myJDD , name:name , btnXpath:btnXpath , inputSearchName:inputSearchName , index_td:index_td])
		String val = myJDD.getStrData(name)
		if (btnXpath=='') {
			btnXpath = "//a[@id='Btn$name']/i"
		}
		if (inputSearchName=='') {
			inputSearchName = "SEARCH_$name"
		}
		String inputXpath 	= "//input[@name='$inputSearchName']"
		String tdXpath 		= "//div[@id='v-dbtdhtmlx1']/table/tbody/tr[2]/td[$index_td][text()='$val']"
		myJDD.myJDDXpath.add(['btnSearch':btnXpath , 'inputSearch':inputXpath , 'tdSearch':tdXpath])

		KWWindow.init()

		STEP.click(0, myJDD,'btnSearch')

		if (KWWindow.waitForNewWindowToOpenAndSwitch()) {
			if (STEP.verifyElementVisible(0, myJDD, 'inputSearch')) {
				STEP.setText(0, myJDD,'inputSearch', myJDD.getStrData(name))
				'mise à jour dynamique du xpath'
				STEP.verifyText(0, myJDD,'tdSearch', myJDD.getStrData(name))
				STEP.click(0, myJDD,'tdSearch')
			}
			STEP.delay(1)
			KWWindow.closeWindowIfOpen()
		}else {
			TNRResult.addSTEP("Saisie de $name en utilisant l'assistant de recherche",'FAIL')
			TNRResult.addDETAIL("La fenetre de recherche ne s'est pas ouverte")
		}

		KWWindow.switchToMainWindow()
		Log.addTraceEND(CLASS_FOR_LOG, "runSearchWithHelper")
	}




	static void launch(JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){
		Log.addTraceBEGIN(CLASS_FOR_LOG, "launch",[ myJDD:myJDD , name:name , btnXpath:btnXpath , inputSearchName:inputSearchName , index_td:index_td])
		String val = myJDD.getStrData(name)

		if (JDDKW.isNULL(val) || JDDKW.isNU(val)) {
			TNRResult.addSTEP("Pas de recherche pour $name, valeur du JDD = $val")
		}else {
			TNRResult.addSUBSTEP("Saisie de $name en utilisant l'assistant de recherche")
			TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
			String errMsg = KWElement.goToElementByObj(tObj, name)
			if (!errMsg) {
				String value = WebUI.getAttribute(tObj, 'value')
				if (value==val){
					TNRResult.addDETAIL("La valeur est déjà saisie, pas de modification.")
				}else {
					String msg = KW.deleteText(tObj)
					if (msg) {
						TNRResult.addSTEP("Saisie de $name en utilisant l'assistant de recherche",'FAIL')
						TNRResult.addDETAIL(msg)
					}else {
						runSearchWithHelper(myJDD, name , btnXpath , inputSearchName , index_td)
					}
				}
			}else {
				TNRResult.addSTEP("Saisie de $name en utilisant l'assistant de recherche",'FAIL')
				TNRResult.addDETAIL(errMsg)
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG, "launch")
	}
} // end of class
