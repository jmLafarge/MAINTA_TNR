
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrWebUI.*

WebUI.openBrowser(GlobalVariable.BASE_URL)
STEP.delay(1)


def br=Tools.getBrowserAndVersion()

println br.NAME
println br.VERSION

	
WebUI.closeBrowser()