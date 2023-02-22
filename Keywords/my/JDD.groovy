package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.text.SimpleTemplateEngine
import internal.GlobalVariable



/********************************************************************************
 * 
 *                JDD nouvelle version
 * 
 * 
 * 
 *********************************************************************************/


public class JDD {

	private final String TOSHEETNAME = 'IHMTO'
	//private final String INFOSHEETNAME = 'Info'


	private final List PARAM_LIST_ALLOWED		= ['PREREQUIS', 'FOREIGNKEY', 'LOCATOR', 'SEQUENCE'] 	// allowed param list
	private final List TAG_LIST_ALLOWED			= ['input', 'select', 'textarea', 'td'] 			// allowed tag list
	private final List SKIP_LIST_SHEETNAME		= ['Version', 'Info', TOSHEETNAME]
	private final String START_DATA_WORD		= 'CAS_DE_TEST'

	private XSSFWorkbook book
	private Sheet TCSheet

	private String JDDFullName = ''
	private String TCTabName = ''
	private String casDeTest = ''
	private int casDeTestNum = 1

	private List headers = []
	private List params = []
	private List datas= []
	private Map xpathTO= [:]

	/**
	 * CONSTRUCTEUR : lit le JDD, si pas de paramètre, utilise la variable globale CASDETESTENCOURS pour définir le JDD à utiliser
	 * 
	 * @param JDDFullName 
	 * @param TCTabName
	 * @param casDeTest
	 */
	JDD(String JDDFullName = null, String TCTabName = null,String casDeTest = null,boolean step=true) {

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
			if (this.TCTabName == null) {
				my.Log.addSTEP("Lecture du JDD : " + this.JDDFullName)
			}else {
				my.Log.addSTEP("Lecture du JDD : " + this.JDDFullName + '     Onglet : ' + this.TCTabName + '     Cas de test : ' + this.casDeTest)
			}
		}else {
			my.Log.addDETAIL("Lecture du JDD : " + this.JDDFullName)
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
					my.Log.addDEBUG("\t\ttab : $tab name : $name xpath : $xpath TCTabName =${this.TCTabName}",2)
					if (tab == '') {
						break
					}else if (tab in [this.TCTabName, 'ALL']) {
						my.Log.addDEBUG("\t\tthis.xpathTO.put($name, $xpath)",2)
						this.xpathTO.put(name, xpath)
					}

				}
			}
			my.Log.addDEBUG("my.Tools.parseMap(this.xpathTO)",2)
			my.Tools.parseMap(this.xpathTO)
		}
	}





	public loadTCSheet(Sheet sheet) {
		my.Log.addDEBUG('loadTCSheet : ' + sheet.getSheetName())

		this.TCSheet = sheet
		my.Log.addDEBUG('Lecture headers')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		this.headers = my.XLS.loadRow(row)
		my.Log.addDEBUG('headers.size = ' + this.headers.size())

		my.Log.addDEBUG('Lecture paramètres')
		this.params =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))==this.START_DATA_WORD) {
				break
			}
			if (my.XLS.getCellValue(row.getCell(0)) in this.PARAM_LIST_ALLOWED) {
				this.params << my.XLS.loadRow(row,this.headers.size())
			}else {
				my.Log.addDETAILFAIL("- Le paramètre '" +  my.XLS.getCellValue(row.getCell(0)) + "' n'est pas autorisé")
			}
		}
		my.Log.addDEBUG('params.size = ' + this.params.size())

		my.Log.addDEBUG('Lecture data')
		this.datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			this.datas << my.XLS.loadRow(row,this.headers.size())
		}
		my.Log.addDEBUG('datas.size = ' + this.datas.size())
	}




	/**
	 * @param list : to be completed
	 * 
	 */
	public getAllPrerequis(List list) {

		for(Sheet sheet: this.book) {
			if (sheet.getSheetName() in SKIP_LIST_SHEETNAME) {
				// skip
			}else {
				this.loadTCSheet(sheet)
				String PRInThisSheet =''
				this.getParam('PREREQUIS').eachWithIndex { value,i ->
					if (!(value in ['', 'PREREQUIS', 'OBSOLETE'])) {
						PRInThisSheet = PRInThisSheet + this.headers[i]+','
						my.Log.addDEBUG('\theader = ' + this.headers[i])
						my.Log.addDEBUG('\tvalue = ' + value)
						Map prerequisMap = [:]
						prerequisMap.putAt('PREJDDMODOBJ',value.split(/\*/)[0])
						prerequisMap.putAt('PREJDDTAB',value.split(/\*/)[1])
						prerequisMap.putAt('PREJDDID',value.split(/\*/)[2])
						prerequisMap.putAt('JDDNAME',this.JDDFullName)
						prerequisMap.putAt('TAB',sheet.getSheetName())
						prerequisMap.putAt('JDDID',this.headers[i])
						prerequisMap.putAt('LISTCDTVAL',this.getListCDTVAL(i))
						prerequisMap.each { key,val ->
							my.Log.addDEBUG('\t\t'+key + ' : ' +val)
						}
						list.add(prerequisMap)
					}
				}
				if (PRInThisSheet.size()>0) {
					my.Log.addDETAIL("Lecture onglet '" + sheet.getSheetName() + "' --> PREREQUIS : " + PRInThisSheet.substring(0,PRInThisSheet.length()-1) )
				}else {
					my.Log.addDETAIL("Lecture onglet '" + sheet.getSheetName() + "'" )
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
			if (it[index]!='') list.add("'" + it[0] + "' - '" + it[index] + "'")
		}
		return list
	}





	/**
	 * 
	 * @return : nombre de ligne pour le cas de test en cours (très souvent 1)
	 */
	public int getNbrLigneCasDeTest() {
		int nbr = 0
		this.datas.each{
			if (it[0]==this.casDeTest) {
				nbr++
			}
		}
		return nbr
	}


	public setCasDeTestNum(int i) {
		this.casDeTestNum=i
	}



	/**
	 * 
	 * @param name 			: nom du champ
	 * @param casDeTestNum 	: numéro de cas de test quand plusieurs lignes pour un mm cas de test
	 * 
	 * @return				: la valeur du champ pour la ligne du cas de test en cours
	 */
	public getData(String name, int casDeTestNum = this.casDeTestNum) {
		my.Log.addDEBUG("getData($name, $casDeTestNum)" , 2)
		if (casDeTestNum > this.getNbrLigneCasDeTest() || casDeTestNum < 1) {
			my.Log.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ this.getNbrLigneCasDeTest() + ')')
			return null
		}
		def ret = null
		int cdtnum = 0
		if (this.headers.contains(name)) {
			for (def li : this.datas) {
				if (li[0]==this.casDeTest) {
					cdtnum++
					if (cdtnum==casDeTestNum) {
						ret = li[this.headers.indexOf(name)]
						my.Log.addDEBUG("getData($name, $casDeTestNum) = " + li[this.headers.indexOf(name)], 2)
						break
					}
				}
			}

		}else {
			my.Log.addERROR("getData($name, $casDeTestNum ) '$name' n'est pas une colonne du JDD")
		}
		return ret
	}



	public getStrData(String name, int casDeTestNum = 1) {

		return this.getData(name, casDeTestNum).toString()

	}


	public String getDBTableName() {
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
	public TestObject makeTO(String ID, Map  binding = [:]){

		if (!this.xpathTO.containsKey(ID)) {
			my.Log.addERROR("L'ID '$ID' n'existe pas, impossible de créer le TEST OBJET")
		}
		my.Log.addDEBUG("makeTO( $ID, Map  binding = [:])" + Tools.parseMap(binding))
		TestObject to = new TestObject(ID)
		to.setSelectorMethod(SelectorMethod.XPATH)
		String xpath = this.xpathTO.getAt(ID)
		my.Log.addDEBUG('\t\txpath :' + xpath)
		// is it a dynamic xpath
		def matcher = xpath =~  /\$\{(.+?)\}/
		//LOG
		my.Log.addDEBUG('\tmatcher.size() = ' + matcher.size())
		if (matcher.size() > 0) {
			// yes it's a dynamic path
			def engine = new SimpleTemplateEngine()
			matcher.each{k,value->
				//LOG
				my.Log.addDEBUG('\t\tmatcher k --> v : ' + k + ' --> ' + value,2)
				if (binding.containsKey(value)) {
					//LOG
					my.Log.addDEBUG('\tnothing to do because external binding already set')
				}else if (value in this.headers) {
					binding.put(value,this.getData(value))
					my.Log.addDEBUG('\tput in binding k --> v : '+ value + ' --> '+ this.getData(value),2)
				}else {
					my.Log.addDEBUG('binding not possible because xpath parameter not found : ' + k,2)
				}
			}
			my.Log.addDEBUG('\t\tdynamic xpath',2)
			String dynxpath = engine.createTemplate(xpath).make(binding).toString()
			to.setSelectorValue(SelectorMethod.XPATH, dynxpath)
		}else {
			my.Log.addDEBUG('\t\tnormal xpath',2)
			to.setSelectorValue(SelectorMethod.XPATH, xpath)
		}
		my.Log.addDEBUG('getObjectId : ' + to.getObjectId(),2)
		my.Log.addDEBUG('get(SelectorMethod.XPATH) : ' + to.getSelectorCollection().get(SelectorMethod.XPATH),2)
		return to
	}




	private addXpath(List locators) {
		locators.eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = this.headers[i]
				my.Log.addDEBUG("\t\taddXpath i = $i name = '$name' loc='$loc' " ,2 )
				if (loc in TAG_LIST_ALLOWED) {
					if (loc=='label') {
						this.xpathTO.put(name, "//$loc[@id='Lbl$name']")
					}else {
						// it's a standard xpath
						this.xpathTO.put(name, "//$loc[@id='$name']")
					}
				}else if (loc[0] == '/') {
					// it's a xpath with potential dynamic values
					this.xpathTO.put(name,loc)
				}else {
					my.Log.addERROR("LOCATOR inconnu : '$loc'")
				}
			}
		}
	}

	/*
	 if (loc in TAG_LIST_ALLOWED) {
	 String name = this.headers[i]
	 if (this.getParam('LOCATOR')!=null) {
	 String loc = this.getParam('LOCATOR')[i]
	 my.Log.addDEBUG("\t\t $i addXpath name = '$name' loc='$loc' tag='$tag'" ,2 )
	 if (loc=='') {
	 // it's a standard xpath
	 if (tag != '') this.xpathTO.put(name, "//" + tag + "[@id='" + name + "']")
	 }else if (loc[0] == '/') {
	 // it's a xpath with potential dynamic values
	 this.xpathTO.put(name,loc)
	 }
	 }else {
	 // it's a standard xpath
	 if (tag != '') this.xpathTO.put(name, "//" + tag + "[@id='" + name + "']")
	 }
	 }
	 */





	private List getParam(String param) {
		List ret = null
		if (!(param in this.PARAM_LIST_ALLOWED)) {
			my.Log.addERROR("getParam(param=$param) Ce paramètre n'est pas autorisé")
		}
		for (def para : this.params) {
			if (para[0] == param) {
				ret = para
				break
			}
		}
		return ret
	}


	public String getParamForThisName(String param, String name) {
		String ret = null
		if (this.getParam(param) != null) {
			if (this.headers.contains(name)) { /// verifier si pas dans headers
				if (this.getParam(param)[this.headers.indexOf(name)] !='') {
					ret = this.getParam(param)[this.headers.indexOf(name)]
				}
			}else {
				my.Log.addERROR("getParamForThisName(param=$param, name=$name) '$name' n'est pas une colonne du JDD")
			}
		}
		return ret
	}



	public String getSqlForForeignKey(String name) {

		def sql = this.getParamForThisName('FOREIGNKEY', name).toString().split(/\*/)

		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '" + this.getData(name) + "'"

		my.Log.addDEBUG("getSqlForForeignKey = $query")

		return query

	}


} // end of class
