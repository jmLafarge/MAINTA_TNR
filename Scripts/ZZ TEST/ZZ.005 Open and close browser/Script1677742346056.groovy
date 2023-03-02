
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

WebUI.openBrowser(GlobalVariable.BASE_URL)
WebUI.delay(1)


def br=my.Tools.getBrowserAndVersion()

println br.NAME
println br.VERSION

	
WebUI.closeBrowser()