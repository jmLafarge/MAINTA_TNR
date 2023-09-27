
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrWebUI.*

WebUI.openBrowser(GlobalVariable.BASE_URL)
WUI.delay( 1000)


def br=Tools.getBrowserAndVersion()

println br.NAME
println br.VERSION

	
WebUI.closeBrowser()