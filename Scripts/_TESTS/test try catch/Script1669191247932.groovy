import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

/*------------------------------------------------
 * 	TEST
 ------------------------------------------------
String hab = 'JML1;JML2'

ArrayList list_hab = hab.split(';')

list_hab.each({ 
        println("$it [$it.class]")

        println(it)
    })
*/
'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.001.FON.01 Ouvrir session'), [:])

}

    WebUI.verifyElementPresent(findTestObject('0 - COMMUN/Accueil/div_desk'), 1)

	//println RunConfiguration.getExecutionProperties().get("current_testcase")
	//println RunConfiguration.getExecutionProperties()
	
	
try {
	
	//WebUI.verifyElementPresent(findTestObject('0 - COMMUN/Accueil/Icone_LogOut'), 1)
	throw new com.kms.katalon.core.exception.StepFailedException('Value required')
}
catch (Exception e) {
	println 'Dans le catch'
	this.println(e) // marche
    while(e.getCause() != null) {
		println 'Dans la boucle du catch' //marche pas
        e = e.getCause();
        String exceptionName = e.getClass().getSimpleName();
        if (exceptionName.equals("StepFailedException")) {
            println 'do something'
        } else if (exceptionName.equals("WebElementNotFoundException")) {
            println 'do something else'
        }
        println 'add more else ifs for exceptions you care about...'
    }
}
finally {
	this.println("Dans le finally")
}