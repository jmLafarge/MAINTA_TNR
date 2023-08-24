
import my.Log
import myJDDManager.JDDXpath

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */


Map <String,String> xpathMapTest_pass = [
	// no locator case
	'LOC_VIDE'	:'',
	'LOC_NULL'	:null,
	//tag allowed case
	'UN_INPUT'		:'input', 
	'UN_SELECT'		:'input', 
	'UN_TEXTAREA'	:'textarea', 
	'UN_TD'			:'td',
	'UN_CHECKBOX'	:'checkbox',
	'UN_RADIO'		:'radio',
	//tag allowed case with *
	'INPUT_NAME'	:'input*name',
	'TD_ID'	:'td*id',
	//xpath direct case
	'UN_XPATH'		:"//button[@id='ValidE39']",
	//with $ case
	'WITH_DOLLAR'	:'$TAB$ID_CODINT'
	]
	
	
Map <String,String> xpathMapExpectedTest_pass = [
	'UN_INPUT'		:"//input[@id='UN_INPUT' and not(@type='hidden')]",
	'UN_SELECT'		:"//input[@id='UN_SELECT' and not(@type='hidden')]",
	'UN_TEXTAREA'	:"//textarea[@id='UN_TEXTAREA']",
	'UN_TD'			:"//td[@id='UN_TD']",
	'UN_CHECKBOX'	:"//input[@id='UN_CHECKBOX' and @type='checkbox']",
	'LblUN_CHECKBOX':"//label[@id='LblUN_CHECKBOX']",
	'UN_RADIO'		:"//label[@id='LUN_RADIO']",
	'INPUT_NAME'	:"//input[@name='INPUT_NAME']",
	'TD_ID'			:"//td[@id='TD_ID']",
	'UN_XPATH'		:"//button[@id='ValidE39']",
	'WITH_DOLLAR'	:'$TAB$ID_CODINT'
	]
	

JDDXpath myJDDXpath = new JDDXpath()

myJDDXpath.addFromMap(xpathMapTest_pass)

Log.addAssert("JDDXpath.xpaths",xpathMapExpectedTest_pass,myJDDXpath.xpaths)

Log.addAssert("JDDXpath.xpaths","//button[@id='ValidE39']",myJDDXpath.xpaths['UN_XPATH'])
Log.addAssert("JDDXpath.getXPath('UN_XPATH')","//button[@id='ValidE39']",myJDDXpath.getXPath('UN_XPATH'))

myJDDXpath.addFromMap(['UN_XPATH'	:"//button[@id='Valid']"])

Log.addAssert("JDDXpath_pass.xpaths","//button[@id='Valid']",myJDDXpath.xpaths['UN_XPATH'])

myJDDXpath.xpaths=[:]

myJDDXpath.addFromMap(['UNKNOWN_TAG' : 'machin'])
Log.addAssert("JDDXpath.xpaths --> LOCATOR inconnu : truc in 'truc*name'",[:],myJDDXpath.xpaths)

myJDDXpath.addFromMap(['UNKNOWN_TAG_WITH_STAR' : 'truc*name'])
Log.addAssert("JDDXpath.xpaths --> ERROR : LOCATOR inconnu : truc in 'truc*name'",[:],myJDDXpath.xpaths)

