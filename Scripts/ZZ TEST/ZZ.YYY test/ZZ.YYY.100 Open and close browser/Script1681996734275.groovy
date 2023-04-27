
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW

WebUI.openBrowser(GlobalVariable.BASE_URL)
KW.delay(1)


def br=my.Tools.getBrowserAndVersion()

println br.NAME
println br.VERSION

	
WebUI.closeBrowser()