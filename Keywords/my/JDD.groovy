package my

import groovy.transform.CompileStatic

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

//import com.kms.katalon.core.testobject.SelectorMethod
//import com.kms.katalon.core.testobject.TestObject

//import groovy.text.SimpleTemplateEngine
import internal.GlobalVariable
import my.result.TNRResult


@CompileStatic
public class JDD {



	private final String TOSHEETNAME = 'IHMTO'

	private final List PARAM_LIST_ALLOWED		= [
		'PREREQUIS',
		'FOREIGNKEY',
		'LOCATOR',
		'SEQUENCE'
	]
	private final List TAG_LIST_ALLOWED			= [
		'input',
		'select',
		'textarea',
		'td',
		'checkbox'
	]
	private final List SKIP_LIST_SHEETNAME		= [
		'Version',
		'Info',
		TOSHEETNAME,
		'MODELE'
	]


	private final String START_DATA_WORD		= 'CAS_DE_TEST'


	private XSSFWorkbook book
	private Sheet TCSheet

	private String JDDFullName = ''
	private String TCTabName   = ''
	private String casDeTest = ''
	private int casDeTestNum   = 1

	private List <String> headers = []
	private List <List <String>> params  = []
	private List <List> datas   = []
	private Map <String,String> xpathTO  = [:]

	//private Map  binding = [:]

	////////////////////
	private List CDTList = []

	/**
	 * CONSTRUCTEUR : lit le JDD, si pas de paramètre, utilise la variable globale CASDETESTENCOURS pour définir le JDD à utiliser
	 * 
	 * @param JDDFullName 
	 * @param TCTabName
	 * @param casDeTest
	 * @param step
	 */
	JDD(String fullname = null, String tabName = null,String cdt = null,boolean step=true) {

		Log.addDEBUG("JDD Construtor fullname = '$fullname'    tabName = '$tabName' Cas de test = '$cdt' step = $step")
		Log.addDEBUG("GlobalVariable.CASDETESTPATTERN : "+GlobalVariable.CASDETESTPATTERN)

		if(fullname == null) {
			def modObj = Tools.getMobObj(GlobalVariable.CASDETESTPATTERN.toString())
			JDDFullName = JDDFiles.getJDDFullName(modObj)
			TCTabName = GlobalVariable.CASDETESTPATTERN.toString().split('\\.')[2]
		}else {
			JDDFullName = fullname
			TCTabName = tabName
			casDeTest = cdt
		}

		/*
		 if (step) {
		 TNRResult.addSTEP("Lecture du JDD : " + JDDFullName)
		 TNRResult.addDETAIL("Onglet : " + TCTabName)
		 }else {
		 Log.addDEBUG("Lecture du JDD : " + JDDFullName)
		 }
		 */
		Log.addDEBUG("Lecture du JDD : " + JDDFullName)


		book = my.XLS.open(JDDFullName)

		if (TCTabName) {

			TCSheet = book.getSheet(TCTabName)
			loadTCSheet(TCSheet)

			if (!casDeTest) casDeTest = CDTList[0]

			// ajout des xpath de l'onglet des cas de test en cours
			addXpath(getParam('LOCATOR'))
		}

		// ajout des xpath de l'onglet Test_Object sil existe
		if (book.getSheet(TOSHEETNAME) != null) {
			Iterator<Row> rowIt = book.getSheet(TOSHEETNAME).rowIterator()
			rowIt.next()
			while(rowIt.hasNext()) {
				Row row = rowIt.next()
				String tab = my.XLS.getCellValue(row.getCell(0))
				String name = my.XLS.getCellValue(row.getCell(1))
				String xpath = my.XLS.getCellValue(row.getCell(2))
				Log.addDEBUG("tab : $tab name : $name xpath : $xpath TCTabName =${TCTabName}",2)
				if (tab == '') {
					break
				}else if (tab in [TCTabName, 'ALL']) {
					Log.addDEBUG("xpathTO.put($name, $xpath)",2)
					xpathTO.put(name, xpath)
				}

			}
		}
		Log.addDEBUG("xpathTO = " + xpathTO.toString(),2)

	}





	def loadTCSheet(Sheet sheet) {

		Log.addDEBUG('loadTCSheet : ' + sheet.getSheetName())

		TCSheet = sheet
		Log.addDEBUG('\tLecture headers')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		headers = my.XLS.loadRow(row)
		Log.addDEBUG('\t - headers.size = ' + headers.size())

		Log.addDEBUG('\tLecture paramètres')
		params =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))==START_DATA_WORD) {
				break
			}
			if (my.XLS.getCellValue(row.getCell(0)) in PARAM_LIST_ALLOWED) {
				params << my.XLS.loadRow(row,headers.size())
			}else {
				Log.addERROR("Le paramètre '" +  my.XLS.getCellValue(row.getCell(0)) + "' n'est pas autorisé")
			}
		}
		Log.addDEBUG('\t - params.size = ' + params.size())

		Log.addDEBUG('\tLecture data')
		datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			datas << my.XLS.loadRow(row,headers.size())
		}
		Log.addDEBUG('\t - datas.size = ' + datas.size())


		String cdtPattern = casDeTest ? casDeTest : GlobalVariable.CASDETESTPATTERN

		//Liste des cas de test qui répondent au pattern, sans doublons (les doublons sont des casDeTestNum)
		List <String> cdtli = datas.findAll{it[0].toString().contains(cdtPattern)}.collect { it[0] }.unique()

		//supprimer de la liste les cas de test traités par un autre Test Case
		CDTList = cdtli.findAll { cdt ->
			cdt.startsWith(cdtPattern) && (!TCFiles.TCfileMap.containsKey(cdt) || cdt == cdtPattern)
		}


		if (CDTList.size()==0 && cdtPattern){
			Log.addINFO('Pas de cas de test défini pour '+cdtPattern)
		}

	}





	/**
	 * 
	 * @return : nombre de ligne pour le cas de test en cours (très souvent 1)
	 */
	def int getNbrLigneCasDeTest(String cdt = casDeTest) {

		return (int)datas.count { it[0] == cdt }
	}


	def setCasDeTestNum(int i) {
		casDeTestNum=i
	}


	def setCasDeTest(String cdt) {
		casDeTest = cdt
	}

	/**
	 *
	 * @param name 			: nom du cas de test
	 * @param casDeTestNum 	: numéro de cas de test quand plusieurs lignes pour un mm cas de test
	 *
	 * @return				: la valeur du champ pour la ligne du cas de test en cours
	 */
	def int getDataLineNum(String cdt = casDeTest, int casDeTestNum = casDeTestNum) {
		Log.addDEBUG("getDataLineNum($cdt, $casDeTestNum)" )
		if (casDeTestNum > getNbrLigneCasDeTest(cdt) || casDeTestNum < 1) {
			Log.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ getNbrLigneCasDeTest(cdt) + ')')
			return null
		}
		int dataLineNum = 0
		int cdtnum = 0
		for (int i=0; i< datas.size();i++) {
			if (datas[i][0]==cdt) {
				cdtnum++
				if (cdtnum==casDeTestNum) {
					dataLineNum = i
					Log.addDEBUG("Numéro de la ligne trouvée : $i")
					break
				}
			}
		}
		return dataLineNum
	}



	/**
	 * 
	 * @param name 			: nom du champ
	 * @param casDeTestNum 	: numéro de cas de test quand plusieurs lignes pour un mm cas de test
	 * 
	 * @return				: la valeur du champ pour la ligne du cas de test en cours
	 */
	def getData(String name, int casDeTestNum = casDeTestNum) {

		Log.addDEBUG("getData($name, $casDeTestNum)" , 2)
		if (casDeTestNum > getNbrLigneCasDeTest() || casDeTestNum < 1) {
			Log.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ getNbrLigneCasDeTest() + ')')
			return null
		}
		if (headers.contains(name)) {
			return datas[getDataLineNum(casDeTest, casDeTestNum)][headers.indexOf(name)]
		}else {
			Log.addERROR("getData($name, $casDeTestNum ) '$name' n'est pas une colonne du JDD")
			return null
		}
	}



	def String getStrData(String name='', int casDeTestNum = casDeTestNum) {

		if (name=='') {
			int dataLineNum = getDataLineNum()
			return datas[dataLineNum][1]
		}else {
			return getData(name, casDeTestNum).toString()
		}
	}




	public String getDBTableName() {
		return headers[0]
	}



	def String getHeaderNameOfIndex(int i) {
		return headers[i]
	}







	private addXpath(List <String> locators) {
		locators.eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = headers[i]
				Log.addDEBUG("addXpath i = $i name = '$name' loc='$loc' ",2 )
				if (loc in TAG_LIST_ALLOWED) {
					if (loc=='checkbox') {
						xpathTO.put(name, "//input[@id='" + name +"']")
						xpathTO.put('Lbl'+name, "//label[@id='Lbl$name']".toString())
					}else if (loc=='input') {
						xpathTO.put(name, "//$loc[@id='$name' and not(@type='hidden')]".toString())
					}else {
						// it's a standard xpath
						xpathTO.put(name, "//$loc[@id='$name']".toString())
					}
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					//it's a tag with an attribut
					def lo = loc.toString().split(/\*/)
					if (lo[0] in TAG_LIST_ALLOWED) {
						xpathTO.put(name, "//${lo[0]}[@${lo[1]}='$name']".toString())
						Log.addDEBUG("\tLOCATOR //${lo[0]}[@${lo[1]}='$name']",2)
					}else {
						Log.addERROR("LOCATOR inconnu : ${lo[0]} in '$loc'")
					}

				}else if (loc[0] == '/') {
					// it's a xpath with potential dynamic values
					xpathTO.put(name,loc)
				}else {
					Log.addERROR("LOCATOR inconnu : '$loc'")
				}
			}
		}
	}






	def List <String> getParam(String param) {

		Log.addDEBUG("getParam $param ",2)

		List  ret = null
		if (!(param in PARAM_LIST_ALLOWED)) {
			Log.addERROR("getParam(param=$param) Ce paramètre n'est pas autorisé")
		}
		for (List para : params) {
			if (para[0] == param) {
				ret = para
				Log.addDEBUG("\tparam trouvé "+para.join(","),2)
				break
			}
		}
		return ret
	}



	def String getParamForThisName(String param, String name) {

		Log.addDEBUG("getParamForThisName $param $name",2)

		String ret = ''
		if (getParam(param) != null) {
			if (headers.contains(name)) { /// verifier si dans headers
				if (getParam(param)[headers.indexOf(name)] !='') {
					ret = getParam(param)[headers.indexOf(name)]
				}
			}else {
				Log.addERROR("getParamForThisName(param=$param, name=$name) '$name' n'est pas une colonne du JDD")
			}
		}
		return ret
	}



	def boolean isOBSOLETE(String name) {

		Log.addDEBUG("isOBSOLETE $name",2)

		String ret = getParamForThisName('PREREQUIS',name)

		if (ret) return ret == 'OBSOLETE'

		return false
	}




	def boolean isFK(String name) {

		Log.addDEBUG("isFK $name",2)

		return getParamForThisName('FOREIGNKEY', name)!=''
	}



	def String getSqlForForeignKey(String name) {

		Log.addDEBUG("getSqlForForeignKey $name",2)

		def sql = getParamForThisName('FOREIGNKEY', name).toString().split(/\*/)

		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '" + getData(name) + "'"

		Log.addDEBUG("getSqlForForeignKey = $query" ,2)

		return query

	}


	/*
	 def readSEQUENCID() {
	 int dataLineNum = getDataLineNum()
	 datas[dataLineNum].eachWithIndex { val,i ->
	 if (JDDKW.isSEQUENCEID(val)) {
	 TNRResult.addSTEP("Récupération de la séquence actuelle de ${headers[i]} ")
	 SQL.getMaxFromTable(headers[i], getDBTableName())
	 }
	 }
	 }
	 */


	def replaceSEQUENCIDInJDD(String fieldName, int delta=0) {

		int dataLineNum = getDataLineNum()
		int index = headers.indexOf(fieldName)
		if (JDDKW.isSEQUENCEID(getStrData(fieldName))){
			String paraSeq =  getParamForThisName('SEQUENCE', fieldName)
			Log.addDEBUG("replaceSEQUENCIDInJDD fieldName=$fieldName delta=$delta")
			datas[dataLineNum][index] = SQL.getLastSequence(paraSeq)+delta
		}
	}


	def boolean isSheetAvailable(String sheetName) {

		return !(sheetName in SKIP_LIST_SHEETNAME)
	}

	def XSSFWorkbook getBook() {
		return book
	}

	def String getJDDFullName() {
		return JDDFullName
	}

	def String getHeader(int i) {

		return headers[i]
	}

	def List <String> getHeaders() {

		return headers
	}

	def String getXpathTO(String name) {

		return xpathTO[name]
	}

	def int getHeadersSize() {

		return headers.size()
	}

	def List <List> getDatas() {

		return datas
	}

	def boolean isTagAllowed(String tag) {

		return tag in TAG_LIST_ALLOWED
	}



} // end of class
