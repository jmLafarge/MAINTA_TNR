package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.text.SimpleTemplateEngine
import internal.GlobalVariable
import my.Log as MYLOG


public class JDD {

	private final String TOSHEETNAME = 'IHMTO'

	private final List PARAM_LIST_ALLOWED		= ['PREREQUIS', 'FOREIGNKEY', 'LOCATOR', 'SEQUENCE']
	private final List TAG_LIST_ALLOWED			= ['input', 'select', 'textarea', 'td', 'checkbox']
	private final List SKIP_LIST_SHEETNAME		= ['Version', 'Info', TOSHEETNAME, 'MODELE']
	private final String START_DATA_WORD		= 'CAS_DE_TEST'

	private XSSFWorkbook book
	private Sheet TCSheet

	private String JDDFullName = ''
	private String TCTabName   = ''
	private String casDeTest   = ''
	private int casDeTestNum   = 1

	private List headers = []
	private List params  = []
	private List datas   = []
	private Map xpathTO  = [:]

	/**
	 * CONSTRUCTEUR : lit le JDD, si pas de paramètre, utilise la variable globale CASDETESTENCOURS pour définir le JDD à utiliser
	 * 
	 * @param JDDFullName 
	 * @param TCTabName
	 * @param casDeTest
	 */
	JDD(String JDDFullName = null, String TCTabName = null,String casDeTest = null,boolean step=true) {

		MYLOG.addDEBUG("JDD Construtor JDDFullName = '$JDDFullName'    TCTabName = '$TCTabName' Cas de test = '$casDeTest' step = $step")
		MYLOG.addDEBUG("GlobalVariable.CASDETESTENCOURS : "+GlobalVariable.CASDETESTENCOURS)

		if(JDDFullName == null) {
			this.JDDFullName = my.JDDFiles.getJDDFullNameFromCasDeTest(GlobalVariable.CASDETESTENCOURS)
			this.TCTabName = GlobalVariable.CASDETESTENCOURS.split('\\.')[2]
			this.casDeTest = GlobalVariable.CASDETESTENCOURS
		}else {
			this.JDDFullName = JDDFullName
			this.TCTabName = TCTabName
			this.casDeTest = casDeTest
		}

		if (step) {
			MYLOG.addSTEP("Lecture du JDD : " + this.JDDFullName)
			MYLOG.addDETAIL("Onglet : " + this.TCTabName)
		}else {
			MYLOG.addINFO("Lecture du JDD : " + this.JDDFullName)
		}

		this.book = my.XLS.open(this.JDDFullName)

		if (this.TCTabName != null) {

			this.TCSheet = this.book.getSheet(this.TCTabName)
			this.loadTCSheet(this.TCSheet)

			// ajout des xpath de l'onglet des cas de test en cours
			this.addXpath(this.getParam('LOCATOR'))


			// ajout des xpath de l'onglet Test_Object sil existe
			if (this.book.getSheet(this.TOSHEETNAME) != null) {
				Iterator<Row> rowIt = this.book.getSheet(this.TOSHEETNAME).rowIterator()
				rowIt.next()
				while(rowIt.hasNext()) {
					Row row = rowIt.next()
					String tab = my.XLS.getCellValue(row.getCell(0))
					String name = my.XLS.getCellValue(row.getCell(1))
					String xpath = my.XLS.getCellValue(row.getCell(2))
					MYLOG.addDEBUG("tab : $tab name : $name xpath : $xpath TCTabName =${this.TCTabName}",2)
					if (tab == '') {
						break
					}else if (tab in [this.TCTabName, 'ALL']) {
						MYLOG.addDEBUG("this.xpathTO.put($name, $xpath)",2)
						this.xpathTO.put(name, xpath)
					}

				}
			}
			MYLOG.addDEBUG("xpathTO = " + this.xpathTO.toString(),2)

		}
	}





	def loadTCSheet(Sheet sheet) {
		MYLOG.addDEBUG('loadTCSheet : ' + sheet.getSheetName())

		this.TCSheet = sheet
		MYLOG.addDEBUG('\tLecture headers')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		this.headers = my.XLS.loadRow(row)
		MYLOG.addDEBUG('\t - headers.size = ' + this.headers.size())

		MYLOG.addDEBUG('\tLecture paramètres')
		this.params =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))==this.START_DATA_WORD) {
				break
			}
			if (my.XLS.getCellValue(row.getCell(0)) in this.PARAM_LIST_ALLOWED) {
				this.params << my.XLS.loadRow(row,this.headers.size())
			}else {
				MYLOG.addERROR("Le paramètre '" +  my.XLS.getCellValue(row.getCell(0)) + "' n'est pas autorisé")
			}
		}
		MYLOG.addDEBUG('\t - params.size = ' + this.params.size())

		MYLOG.addDEBUG('\tLecture data')
		this.datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			this.datas << my.XLS.loadRow(row,this.headers.size())
		}
		MYLOG.addDEBUG('\t - datas.size = ' + this.datas.size())
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

		for(Sheet sheet: this.book) {
			if (sheet.getSheetName() in SKIP_LIST_SHEETNAME) {
				// skip
			}else {
				this.loadTCSheet(sheet)
				String PRInThisSheet =''
				this.getParam('PREREQUIS').eachWithIndex { value,i ->
					if (!(value in ['', 'PREREQUIS', 'OBSOLETE'])) {
						PRInThisSheet = PRInThisSheet + this.headers[i]+','
						MYLOG.addDEBUG('\theader = ' + this.headers[i])
						MYLOG.addDEBUG('\tvalue = ' + value)
						Map prerequisMap = [:]
						prerequisMap.putAt('PREJDDMODOBJ',value.split(/\*/)[0])
						prerequisMap.putAt('PREJDDTAB',value.split(/\*/)[1])
						prerequisMap.putAt('PREJDDID',value.split(/\*/)[2])
						prerequisMap.putAt('JDDNAME',this.JDDFullName)
						prerequisMap.putAt('TAB',sheet.getSheetName())
						prerequisMap.putAt('JDDID',this.headers[i])
						prerequisMap.putAt('LISTCDTVAL',this.getListCDTVAL(i))
						prerequisMap.each { key,val ->
							MYLOG.addDEBUG('\t\t'+key + ' : ' +val)
						}
						list.add(prerequisMap)
					}
				}
				if (PRInThisSheet.size()>0) {
					MYLOG.addDETAIL("Lecture onglet '" + sheet.getSheetName() + "' --> PREREQUIS : " + PRInThisSheet.substring(0,PRInThisSheet.length()-1) )
					//MYLOG.addDETAIL('\t'+list.join('|'))
				}else {
					MYLOG.addDETAIL("Lecture onglet '" + sheet.getSheetName() + "'" )
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
		List list =[]
		this.datas.each{
			if (it[index]!=null && it[index]!='') list.add("'" + it[0] + "' - '" + it[index] + "'")
		}
		return list
	}





	/**
	 * 
	 * @return : nombre de ligne pour le cas de test en cours (très souvent 1)
	 */
	def int getNbrLigneCasDeTest(String casDeTest = this.casDeTest) {
		int nbr = 0
		this.datas.each{
			if (it[0]==casDeTest) {
				nbr++
			}
		}
		return nbr
	}


	def setCasDeTestNum(int i) {
		this.casDeTestNum=i
	}

	def getCasDeTestNum() {
		return this.casDeTestNum
	}






	/**
	 *
	 * @param name 			: nom du cas de test
	 * @param casDeTestNum 	: numéro de cas de test quand plusieurs lignes pour un mm cas de test
	 *
	 * @return				: la valeur du champ pour la ligne du cas de test en cours
	 */
	def getDataLineNum(String casDeTest = this.casDeTest, int casDeTestNum = this.casDeTestNum) {
		MYLOG.addDEBUG("getDataLineNum($casDeTest, $casDeTestNum)" )
		if (casDeTestNum > this.getNbrLigneCasDeTest(casDeTest) || casDeTestNum < 1) {
			MYLOG.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ this.getNbrLigneCasDeTest(casDeTest) + ')')
			return null
		}
		int dataLineNum = 0
		int cdtnum = 0
		for (int i=0; i< this.datas.size();i++) {
			if (this.datas[i][0]==casDeTest) {
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
	def getData(String name, int casDeTestNum = this.casDeTestNum) {

		MYLOG.addDEBUG("getData($name, $casDeTestNum)" , 2)
		if (casDeTestNum > this.getNbrLigneCasDeTest() || casDeTestNum < 1) {
			MYLOG.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ this.getNbrLigneCasDeTest() + ')')
			return
		}
		//def ret = null
		//int cdtnum = 0
		if (this.headers.contains(name)) {

			return this.datas[this.getDataLineNum(this.casDeTest, casDeTestNum)][this.headers.indexOf(name)]
			/*
			 for (def li : this.datas) {
			 if (li[0]==this.casDeTest) {
			 cdtnum++
			 if (cdtnum==casDeTestNum) {
			 ret = li[this.headers.indexOf(name)]
			 MYLOG.addDEBUG("getData($name, $casDeTestNum) = " + li[this.headers.indexOf(name)], 2)
			 break
			 }
			 }
			 }
			 */
		}else {
			MYLOG.addERROR("getData($name, $casDeTestNum ) '$name' n'est pas une colonne du JDD")
			return null
		}
	}



	def getStrData(String name='', int casDeTestNum = this.casDeTestNum) {

		if (name=='') {
			int dataLineNum = this.getDataLineNum()
			return this.datas[dataLineNum][1]
		}else {
			return this.getData(name, casDeTestNum).toString()
		}
	}




	def String getDBTableName() {
		return this.headers[0]
	}



	/**
	 * Create a TestObject with info from JDD and evaluate the dynamic xpath with info from JDD (binding method)
	 *
	 * @param ID 		name of the TestObject
	 * @param binding 	to be set if we need to bind with external value
	 *
	 * @return the TestObject created
	 */
	def makeTO(String ID, Map  binding = [:]){

		if (!this.xpathTO.containsKey(ID)) {
			return [null, "L'ID '$ID' n'existe pas, impossible de créer le TEST OBJET"]
		}
		MYLOG.addDEBUG("makeTO( $ID, Map  binding = [:])" + binding.toString())

		TestObject to = new TestObject(ID)
		to.setSelectorMethod(SelectorMethod.XPATH)
		String xpath = this.xpathTO.getAt(ID)
		MYLOG.addDEBUG("\txpath : $xpath")

		if (xpath.startsWith('$')) {

			if (xpath.split('\\$').size()==3){

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
						binding['idnameval']=this.getData(xpath.split('\\$')[2])
						xpath = NAV.myGlobalJDD.xpathTO['TDGRILLE']
						break

					default:
						return [null, "makeTO $ID, xpath avec "+'$'+" mot clé inconnu : $xpath"]
				}
				MYLOG.addDEBUG("\t\tGLOBAL xpath : $xpath")
				MYLOG.addDEBUG("\t\tbinding  : " + binding.toString())
			}else {
				return [null, "makeTO $ID, xpath avec "+'$'+" non conforme : $xpath"]
			}

		}

		MYLOG.addDEBUG("\txpath : $xpath")

		xpath = this.bindingXpath(xpath, binding)

		to.setSelectorValue(SelectorMethod.XPATH, xpath)

		MYLOG.addDEBUG('\tgetObjectId : ' + to.getObjectId())
		MYLOG.addDEBUG('\tget(SelectorMethod.XPATH) : ' + to.getSelectorCollection().get(SelectorMethod.XPATH))
		return [to, '']
	}



	private String bindingXpath(String xpath,Map  binding) {

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
				}else if (value in this.headers) {
					binding.put(value,this.getData(value))
					MYLOG.addDEBUG('\t\tJDD binding k --> v : '+ value + ' --> '+ this.getData(value))
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




	private addXpath(List locators) {
		locators.eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = this.headers[i]
				MYLOG.addDEBUG("addXpath i = $i name = '$name' loc='$loc' ",2 )
				if (loc in TAG_LIST_ALLOWED) {
					if (loc=='checkbox') {
						this.xpathTO.put(name, "//input[@id='$name']")
						this.xpathTO.put('Lbl'+name, "//label[@id='Lbl$name']")
					}else {
						// it's a standard xpath
						this.xpathTO.put(name, "//$loc[@id='$name']")
					}
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					//it's a tag with an attribut
					def lo = loc.toString().split(/\*/)
					if (lo[0] in TAG_LIST_ALLOWED) {
						this.xpathTO.put(name, "//${lo[0]}[@${lo[1]}='$name']")
						MYLOG.addDEBUG("\tLOCATOR //${lo[0]}[@${lo[1]}='$name']",2)
					}else {
						MYLOG.addERROR("LOCATOR inconnu : ${lo[0]} in '$loc'")
					}

				}else if (loc[0] == '/') {
					// it's a xpath with potential dynamic values
					this.xpathTO.put(name,loc)
				}else {
					MYLOG.addERROR("LOCATOR inconnu : '$loc'")
				}
			}
		}
	}






	private List getParam(String param) {
		List ret = null
		if (!(param in this.PARAM_LIST_ALLOWED)) {
			MYLOG.addERROR("getParam(param=$param) Ce paramètre n'est pas autorisé")
		}
		for (def para : this.params) {
			if (para[0] == param) {
				ret = para
				break
			}
		}
		return ret
	}


	def String getParamForThisName(String param, String name) {
		String ret = null
		if (this.getParam(param) != null) {
			if (this.headers.contains(name)) { /// verifier si pas dans headers
				if (this.getParam(param)[this.headers.indexOf(name)] !='') {
					ret = this.getParam(param)[this.headers.indexOf(name)]
				}
			}else {
				MYLOG.addERROR("getParamForThisName(param=$param, name=$name) '$name' n'est pas une colonne du JDD")
			}
		}
		return ret
	}



	def String getSqlForForeignKey(String name) {

		def sql = this.getParamForThisName('FOREIGNKEY', name).toString().split(/\*/)

		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '" + this.getData(name) + "'"

		MYLOG.addDEBUG("getSqlForForeignKey = $query")

		return query

	}



	def readSEQUENCID() {
		int dataLineNum = this.getDataLineNum()
		this.datas[dataLineNum].eachWithIndex { val,i ->
			if (my.JDDKW.isSEQUENCEID(val)) {
				MYLOG.addSTEP("Récupération de la séquence actuelle de ${this.headers[i]} ")
				my.SQL.getMaxFromTable(this.headers[i], this.getDBTableName())
			}
		}
	}


	def replaceSEQUENCIDInJDD() {
		int dataLineNum = this.getDataLineNum()
		this.datas[dataLineNum].eachWithIndex { val,i ->
			if (my.JDDKW.isSEQUENCEID(val)) {
				MYLOG.addSTEP("Récupération de la séquence ${this.headers[i]} de l'objet créé")
				this.datas[dataLineNum][i] = my.SQL.getMaxFromTable(this.headers[i], this.getDBTableName())
			}
		}
	}



} // end of class
