
import tnrLog.Log
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDXpath

/**
 * TESTS UNITAIRES
 * 
 * public String getXPath(String name)
 * public void add(Map <String,String> map)
 * public void add(String name, String loc) 
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrJDDManager.JDDXpath'

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

myJDDXpath.add(xpathMapTest_pass)

Log.addAssert(CLASS_FOR_LOG,"JDDXpath.xpaths",xpathMapExpectedTest_pass,myJDDXpath.xpaths)

Log.addAssert(CLASS_FOR_LOG,"JDDXpath.xpaths","//button[@id='ValidE39']",myJDDXpath.getXPath('UN_XPATH'))
Log.addAssert(CLASS_FOR_LOG,"JDDXpath.getXPath('UN_XPATH')","//button[@id='ValidE39']",myJDDXpath.getXPath('UN_XPATH'))

Log.addAssert(CLASS_FOR_LOG,"myJDDXpath.add(['UN_XPATH'	:\"//button[@id='Valid']\"])",null,myJDDXpath.add(['UN_XPATH'	:"//button[@id='Valid']"]))
Log.addAssert(CLASS_FOR_LOG,"JDDXpath_pass.xpaths","//button[@id='Valid']",myJDDXpath.getXPath('UN_XPATH'))

myJDDXpath.xpaths=[:]

Log.addAssert(CLASS_FOR_LOG,"myJDDXpath.add(['UNKNOWN_TAG' : 'machin'])",null,myJDDXpath.add(['UNKNOWN_TAG' : 'machin']))
Log.addAssert(CLASS_FOR_LOG,"JDDXpath.xpaths --> LOCATOR inconnu : truc in 'truc*name'",[:],myJDDXpath.xpaths)

Log.addAssert(CLASS_FOR_LOG,"myJDDXpath.add(['UNKNOWN_TAG_WITH_STAR' : 'truc*name'])",null,myJDDXpath.add(['UNKNOWN_TAG_WITH_STAR' : 'truc*name']))
Log.addAssert(CLASS_FOR_LOG,"JDDXpath.xpaths --> ERROR : LOCATOR inconnu : truc in 'truc*name'",[:],myJDDXpath.xpaths)

