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
public class SearchWithHelper {


	private static final String CLASS_NAME = 'SearchWithHelper'



	private static runSearchWithHelper(JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){
		Log.addTraceBEGIN(CLASS_NAME, "runSearchWithHelper",[ myJDD:myJDD.toString() , name:name , btnXpath:btnXpath , inputSearchName:inputSearchName , index_td:index_td])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'runSearchWithHelper'+ myJDD.toString() + name)
		StepID.setParentStepID(strStepID)
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

		WUIWindow.init()

		STEP.simpleClick( myJDD,'btnSearch')

		if (WUIWindow.waitForNewWindowToOpenAndSwitch()) {
			if (STEP.verifyElementVisible( myJDD, 'inputSearch')) {
				STEP.setText( myJDD,'inputSearch', myJDD.getStrData(name))
				'mise à jour dynamique du xpath'
				STEP.verifyText( myJDD,'tdSearch', myJDD.getStrData(name))
				STEP.simpleClick( myJDD,'tdSearch')
			}
			WUI.delay(500)
			WUIWindow.closeWindowIfOpen()
		}else {
			TNRResult.addSTEP(strStepID,"Saisie de $name en utilisant l'assistant de recherche",'FAIL')
			TNRResult.addDETAIL("La fenetre de recherche ne s'est pas ouverte")
		}
		StepID.clearParentStepID()
		WUIWindow.switchToMainWindow()
		Log.addTraceEND(CLASS_NAME, "runSearchWithHelper")
	}




	static void searchWithHelper( JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){
		Log.addTraceBEGIN(CLASS_NAME, "searchWithHelper",[ myJDD:myJDD.toString() , name:name , btnXpath:btnXpath , inputSearchName:inputSearchName , index_td:index_td])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'searchWithHelper'+ myJDD.toString() + name)
		String val = myJDD.getStrData(name)

		if (JDDKW.isNULL(val) || JDDKW.isNU(val)) {
			TNRResult.addSTEP(strStepID,"Pas de recherche pour $name, valeur du JDD = $val")
		}else {
			TNRResult.addSUBSTEP("Saisie de $name en utilisant l'assistant de recherche")
			TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
			String errMsg = WUI.goToElementByObj(tObj, name)
			if (!errMsg) {
				String value = WebUI.getAttribute(tObj, 'value')
				if (value==val){
					TNRResult.addDETAIL("La valeur est déjà saisie, pas de modification.")
				}else {
					String msg = Text.deleteText(tObj)
					if (msg) {
						TNRResult.addSTEP(strStepID,"Saisie de $name en utilisant l'assistant de recherche",'FAIL')
						TNRResult.addDETAIL(msg)
					}else {
						runSearchWithHelper(myJDD, name , btnXpath , inputSearchName , index_td)
					}
				}
			}else {
				TNRResult.addSTEP(strStepID,"Saisie de $name en utilisant l'assistant de recherche",'FAIL')
				TNRResult.addDETAIL(errMsg)
			}
		}
		Log.addTraceEND(CLASS_NAME, "searchWithHelper")
	}
} // end of class
