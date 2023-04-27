package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.text.SimpleTemplateEngine
import internal.GlobalVariable
import my.InfoBDD
import my.Log as MYLOG
import my.JDDFiles


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

	private List headers = []
	private List params  = []
	private List datas   = []
	private Map xpathTO  = [:]

	private Map  binding = [:]

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
	JDD(String JDDFullName = null, String TCTabName = null,String cdt = null,boolean step=true) {

		MYLOG.addDEBUG("JDD Construtor JDDFullName = '$JDDFullName'    TCTabName = '$TCTabName' Cas de test = '$cdt' step = $step")
		MYLOG.addDEBUG("GlobalVariable.CASDETESTPATTERN : "+GlobalVariable.CASDETESTPATTERN)

		if(JDDFullName == null) {
			def modObj = Tools.getMobObj(GlobalVariable.CASDETESTPATTERN)
			JDDFullName = JDDFiles.getJDDFullName(modObj)
			TCTabName = GlobalVariable.CASDETESTPATTERN.split('\\.')[2]
		}else {
			JDDFullName = JDDFullName
			TCTabName = TCTabName
			casDeTest = cdt
		}

		/*
		 if (step) {
		 MYLOG.addSTEP("Lecture du JDD : " + JDDFullName)
		 MYLOG.addDETAIL("Onglet : " + TCTabName)
		 }else {
		 MYLOG.addDEBUG("Lecture du JDD : " + JDDFullName)
		 }
		 */
		MYLOG.addDEBUG("Lecture du JDD : " + JDDFullName)


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
				MYLOG.addDEBUG("tab : $tab name : $name xpath : $xpath TCTabName =${TCTabName}",2)
				if (tab == '') {
					break
				}else if (tab in [TCTabName, 'ALL']) {
					MYLOG.addDEBUG("xpathTO.put($name, $xpath)",2)
					xpathTO.put(name, xpath)
				}

			}
		}
		MYLOG.addDEBUG("xpathTO = " + xpathTO.toString(),2)

	}





	def loadTCSheet(Sheet sheet) {

		MYLOG.addDEBUG('loadTCSheet : ' + sheet.getSheetName())

		TCSheet = sheet
		MYLOG.addDEBUG('\tLecture headers')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		headers = my.XLS.loadRow(row)
		MYLOG.addDEBUG('\t - headers.size = ' + headers.size())

		MYLOG.addDEBUG('\tLecture paramètres')
		params =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))==START_DATA_WORD) {
				break
			}
			if (my.XLS.getCellValue(row.getCell(0)) in PARAM_LIST_ALLOWED) {
				params << my.XLS.loadRow(row,headers.size())
			}else {
				MYLOG.addERROR("Le paramètre '" +  my.XLS.getCellValue(row.getCell(0)) + "' n'est pas autorisé")
			}
		}
		MYLOG.addDEBUG('\t - params.size = ' + params.size())

		MYLOG.addDEBUG('\tLecture data')
		datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			datas << my.XLS.loadRow(row,headers.size())
		}
		MYLOG.addDEBUG('\t - datas.size = ' + datas.size())


		String cdtPattern = casDeTest ? casDeTest : GlobalVariable.CASDETESTPATTERN

		//Liste des cas de test qui répondent au pattern, sans doublons (les doublons sont des casDeTestNum)
		List cdtli = datas.findAll{it[0].contains(cdtPattern)}.collect { it[0] }.unique()

		//supprimer de la liste les cas de test traités par un autre Test Case
		CDTList = cdtli.findAll { cdt ->
			cdt.startsWith(cdtPattern) && (!TCFiles.TCfileMap.containsKey(cdt) || cdt == cdtPattern)
		}


		if (CDTList.size()==0 && cdtPattern){
			MYLOG.addINFO('Pas de cas de test défini pour '+cdtPattern)
		}

	}




	/**
	 * @param list : to be completed
	 * [
	 PREJDDMODOBJ:RO.ACT, 
	 PREJDDTAB:001, 
	 PREJDDID:ID_CODINT, 
	 JDDNAME:TNR_JDD\RO\JDD.RO.ACT.xlsx, 
	 TAB:001, 
	 JDDID:ID_CODINT, 
	 LISTCDTVAL:[
	 'RO.ACT.001.CRE.01' - 'RO.ACT.001.CRE.01', 
	 'RO.ACT.001.LEC.01' - 'RO.ACT.001.LEC.01', 
	 'RO.ACT.001.MAJ.01' - 'RO.ACT.001.MAJ.01', 
	 'RO.ACT.001.SUP.01' - 'RO.ACT.001.SUP.01', 
	 'RO.ACT.001.REC.01' - 'RO.ACT.001.REC.01']
	 ]
	 * 
	 */
	def getAllPrerequis(List list) {

		for(Sheet sheet: book) {
			if (sheet.getSheetName() in SKIP_LIST_SHEETNAME) {
				// skip
			}else {
				loadTCSheet(sheet)
				String PRInThisSheet =''
				getParam('PREREQUIS').eachWithIndex { value,i ->
					if (!(value in ['', 'PREREQUIS', 'OBSOLETE'])) {
						PRInThisSheet = PRInThisSheet + headers[i]+','
						MYLOG.addDEBUG('\theader = ' + headers[i])
						MYLOG.addDEBUG('\tvalue = ' + value)
						Map prerequisMap = [:]
						prerequisMap.putAt('PREJDDMODOBJ',value.split(/\*/)[0])
						prerequisMap.putAt('PREJDDTAB',value.split(/\*/)[1])
						prerequisMap.putAt('PREJDDID',value.split(/\*/)[2])
						prerequisMap.putAt('JDDNAME',JDDFullName)
						prerequisMap.putAt('TAB',sheet.getSheetName())
						prerequisMap.putAt('JDDID',headers[i])
						prerequisMap.putAt('LISTCDTVAL',getListCDTVAL(i))
						MYLOG.addDEBUG('\tPrerequisMap : ')
						prerequisMap.each { key,val ->
							MYLOG.addDEBUG('\t\t'+key + ' : ' +val)
						}
						list.add(prerequisMap)
					}
				}
				if (PRInThisSheet.size()>0) {
					MYLOG.addDEBUGDETAIL("Lecture onglet '" + sheet.getSheetName() + "' --> PREREQUIS : " + PRInThisSheet.substring(0,PRInThisSheet.length()-1),0 )
					//MYLOG.addDETAIL('\t'+list.join('|'))
				}else {
					MYLOG.addDEBUGDETAIL("Lecture onglet '" + sheet.getSheetName() + "'",0 )
				}
			}
		}
	}



	/**
	 * 
	 * @param sheet
	 * @param index
	 * @return
	 */
	private List getListCDTVAL(int index) {
		List PKlist=InfoBDD.getPK(getDBTableName())
		List list =[]
		datas.each{
			if (it[index]!=null && it[index]!='' && !JDDKW.isNU(it[index]) && !JDDKW.isNULL(it[index]) ) {
				if (it[0].toString().contains('.CRE.') && PKlist.contains(headers[index])) {
					MYLOG.addDEBUG("skip : " + "'" + it[0] + "' - '" + it[index] + "'")
				}else {
					list.add("'" + it[0] + "' - '" + it[index] + "'")
				}
			}
		}
		return list
	}





	/**
	 * 
	 * @return : nombre de ligne pour le cas de test en cours (très souvent 1)
	 */
	def int getNbrLigneCasDeTest(String cdt = casDeTest) {

		return datas.count { it[0] == cdt }
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
	def getDataLineNum(String cdt = casDeTest, int casDeTestNum = casDeTestNum) {
		MYLOG.addDEBUG("getDataLineNum($cdt, $casDeTestNum)" )
		if (casDeTestNum > getNbrLigneCasDeTest(cdt) || casDeTestNum < 1) {
			MYLOG.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ getNbrLigneCasDeTest(cdt) + ')')
			return null
		}
		int dataLineNum = 0
		int cdtnum = 0
		for (int i=0; i< datas.size();i++) {
			if (datas[i][0]==cdt) {
				cdtnum++
				if (cdtnum==casDeTestNum) {
					dataLineNum = i
					MYLOG.addDEBUG("Numéro de la ligne trouvée : $i")
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

		MYLOG.addDEBUG("getData($name, $casDeTestNum)" , 2)
		if (casDeTestNum > getNbrLigneCasDeTest() || casDeTestNum < 1) {
			MYLOG.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ getNbrLigneCasDeTest() + ')')
			return null
		}
		if (headers.contains(name)) {
			return datas[getDataLineNum(casDeTest, casDeTestNum)][headers.indexOf(name)]
		}else {
			MYLOG.addERROR("getData($name, $casDeTestNum ) '$name' n'est pas une colonne du JDD")
			return null
		}
	}



	def getStrData(String name='', int casDeTestNum = casDeTestNum) {

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



	/**
	 * Create a TestObject with info from JDD and evaluate the dynamic xpath with info from JDD (binding method)
	 *
	 * @param ID 		name of the TestObject
	 * @param binding 	to be set if we need to bind with external value
	 *
	 * @return the TestObject created
	 */
	def makeTO(String ID){

		if (!xpathTO.containsKey(ID)) {
			return [
				null,
				"L'ID '$ID' n'existe pas, impossible de créer le TEST OBJET"
			]
		}

		MYLOG.addDEBUG("makeTO( '$ID' ) with binding = )" + binding.toString())

		TestObject to = new TestObject(ID)
		to.setSelectorMethod(SelectorMethod.XPATH)
		String xpath = xpathTO.getAt(ID)
		MYLOG.addDEBUG("\txpath : $xpath")

		if (xpath.startsWith('$')) {

			switch(xpath.split('\\$')[1]) {

				case "TAB":
					binding['tabname']=xpath.split('\\$')[2]
					xpath = NAV.myGlobalJDD.xpathTO['TAB']
					break

				case "TABSELECTED":
					binding['tabname']=xpath.split('\\$')[2]
					xpath = NAV.myGlobalJDD.xpathTO['TABSELECTED']
					break

				case "FILTREGRILLE":
					binding['idname']=xpath.split('\\$')[2]
					xpath = NAV.myGlobalJDD.xpathTO['FILTREGRILLE']
					break

				case "TDGRILLE":
				// faire la m^me chose sur les autrees
					if (xpath.split('\\$').size()!=4){
						return [
							null,
							"makeTO $ID, xpath avec "+'$'+" non conforme : $xpath"
						]
					}
				//////
					binding['numTD']=xpath.split('\\$')[2]
					binding['idnameval']=getData(xpath.split('\\$')[3])
					xpath = NAV.myGlobalJDD.xpathTO['TDGRILLE']
					break

				default:
					return [
						null,
						"makeTO $ID, xpath avec "+'$'+" mot clé inconnu : $xpath"
					]
			}
			MYLOG.addDEBUG("\t\tGLOBAL xpath : $xpath")
			MYLOG.addDEBUG("\t\tbinding  : " + binding.toString())
		}

		MYLOG.addDEBUG("\txpath : $xpath")

		xpath = resolveXpath(xpath)

		to.setSelectorValue(SelectorMethod.XPATH, xpath)

		MYLOG.addDEBUG('\tgetObjectId : ' + to.getObjectId())
		MYLOG.addDEBUG('\tget(SelectorMethod.XPATH) : ' + to.getSelectorCollection().get(SelectorMethod.XPATH))

		binding=[:]

		return [to, '']
	}




	private String resolveXpath(String xpath) {

		// is it a dynamic xpath
		def matcher = xpath =~  /\$\{(.+?)\}/
		//LOG
		MYLOG.addDEBUG('\tmatcher.size() = ' + matcher.size())
		if (matcher.size() > 0) {
			MYLOG.addDEBUG('\tdynamic xpath')
			def engine = new SimpleTemplateEngine()
			matcher.each{k,value->
				MYLOG.addDEBUG('\t\tmatcher k --> v : ' + k + ' --> ' + value)
				if (binding.containsKey(value)) {
					MYLOG.addDEBUG('\t\tExternal binding')
				}else if (value in headers) {
					binding.put(value,getData(value))
					MYLOG.addDEBUG('\t\tJDD binding k --> v : '+ value + ' --> '+ getData(value))
				}else {
					MYLOG.addERROR('binding not possible because xpath parameter not in JDD : ' + k)
				}
			}
			xpath = engine.createTemplate(xpath).make(binding).toString()
			MYLOG.addDEBUG("\tdynamic xpath = $xpath")
			return xpath
		}else {
			MYLOG.addDEBUG('\tnormal xpath')
			return xpath
		}
	}




	public setBinding(String name, String val) {

		binding[name]=val
	}






	private addXpath(List locators) {
		locators.eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = headers[i]
				MYLOG.addDEBUG("addXpath i = $i name = '$name' loc='$loc' ",2 )
				if (loc in TAG_LIST_ALLOWED) {
					if (loc=='checkbox') {
						xpathTO.put(name, "//input[@id='$name']")
						xpathTO.put('Lbl'+name, "//label[@id='Lbl$name']")
					}else {
						// it's a standard xpath
						xpathTO.put(name, "//$loc[@id='$name']")
					}
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					//it's a tag with an attribut
					def lo = loc.toString().split(/\*/)
					if (lo[0] in TAG_LIST_ALLOWED) {
						xpathTO.put(name, "//${lo[0]}[@${lo[1]}='$name']")
						MYLOG.addDEBUG("\tLOCATOR //${lo[0]}[@${lo[1]}='$name']",2)
					}else {
						MYLOG.addERROR("LOCATOR inconnu : ${lo[0]} in '$loc'")
					}

				}else if (loc[0] == '/') {
					// it's a xpath with potential dynamic values
					xpathTO.put(name,loc)
				}else {
					MYLOG.addERROR("LOCATOR inconnu : '$loc'")
				}
			}
		}
	}






	private List getParam(String param) {
		List ret = null
		if (!(param in PARAM_LIST_ALLOWED)) {
			MYLOG.addERROR("getParam(param=$param) Ce paramètre n'est pas autorisé")
		}
		for (def para : params) {
			if (para[0] == param) {
				ret = para
				break
			}
		}
		return ret
	}



	def String getParamForThisName(String param, String name) {
		String ret = ''
		if (getParam(param) != null) {
			if (headers.contains(name)) { /// verifier si dans headers
				if (getParam(param)[headers.indexOf(name)] !='') {
					ret = getParam(param)[headers.indexOf(name)]
				}
			}else {
				MYLOG.addERROR("getParamForThisName(param=$param, name=$name) '$name' n'est pas une colonne du JDD")
			}
		}
		return ret
	}



	def boolean isOBSOLETE(String name) {

		String ret = getParamForThisName('PREREQUIS',name)

		if (ret) return ret == 'OBSOLETE'

		return false
	}




	def boolean isFK(String name) {

		return getParamForThisName('FOREIGNKEY', name)!=''
	}



	def String getSqlForForeignKey(String name) {

		def sql = getParamForThisName('FOREIGNKEY', name).toString().split(/\*/)

		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '" + getData(name) + "'"

		MYLOG.addDEBUG("getSqlForForeignKey = $query")

		return query

	}



	def readSEQUENCID() {
		int dataLineNum = getDataLineNum()
		datas[dataLineNum].eachWithIndex { val,i ->
			if (JDDKW.isSEQUENCEID(val)) {
				MYLOG.addSTEP("Récupération de la séquence actuelle de ${headers[i]} ")
				my.SQL.getMaxFromTable(headers[i], getDBTableName())
			}
		}
	}


	def replaceSEQUENCIDInJDD() {
		int dataLineNum = getDataLineNum()
		datas[dataLineNum].eachWithIndex { val,i ->
			if (JDDKW.isSEQUENCEID(val)) {
				MYLOG.addSTEP("Récupération de la séquence ${headers[i]} de l'objet créé")
				datas[dataLineNum][i] = my.SQL.getMaxFromTable(headers[i], getDBTableName())
			}
		}
	}






} // end of class
