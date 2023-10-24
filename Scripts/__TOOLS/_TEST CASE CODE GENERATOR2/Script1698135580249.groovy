
import org.openqa.selenium.WebElement

import internal.GlobalVariable
import tnrCommon.Tools
import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrTC.AD_SEC.AD_SEC_001_FON
import tnrWebUI.STEP


TNRResult.addStartInfo('TNR TEST')
Tools.logInfoContext()
GlobalJDD.myGlobalJDD
GlobalVariable.CAS_DE_TEST_PATTERN = 'AD.SEC.001.FON.01'

AD_SEC_001_FON tc = new AD_SEC_001_FON()

tc.cdt_01()

STEP.goToURLCreate('21')



// Fetch filtered elements
List<WebElement> elements = Tools.fetchFilteredElements()


def printAttributes(List<WebElement> elements) {
	// En-têtes de colonnes
	println "+---------------------+---------------------+---------------------+---------------------+"
	printf "| %-18s | %-18s | %-18s | %-18s |\n", "ID", "Name", "Type", "Tabindex"
	println "+---------------------+---------------------+---------------------+---------------------+"

	// Lignes de données
	elements.each { element ->
		String id = element.getAttribute('id') ?: "N/A"
		String name = element.getAttribute('name') ?: "N/A"
		String type = element.getAttribute('type') ?: "N/A"
		String tabindex = element.getAttribute('tabindex') ?: "N/A"
		printf "| %-18s | %-18s | %-18s | %-18s |\n", id, name, type, tabindex
	}
	println "+---------------------+---------------------+---------------------+---------------------+"
}


printAttributes(elements)




