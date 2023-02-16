package my2

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.text.SimpleTemplateEngine
import internal.GlobalVariable

class oldJDD {

	/*
	 * JDD Class manage JDD Param, JDD data and JDD Test Object from JDD xlsx
	 * 
	 */

	public Map data = [:]				// map with data of a test case [ key : value ]
	// note that for key = 'CAS_DE_TEST', value is the "CasDeTest" name
	// simply access, example JDD.data.ID_CODINT

	private final String TOSHEETNAME = 'IHMTO'
	private final String INFOSHEETNAME = 'Info'

	private final List PARAM_LIST_ALLOWED		= ['PREREQUIS', 'FOREIGNKEY', 'TAGIHM', 'LOCATOR', 'SEQUENCE'] 	// allowed param list
	private final List TAG_LIST_ALLOWED			= ['input', 'select', 'textarea', 'td'] 			// allowed tag list
	private final List SKIP_LIST_SHEETNAME		= ['Version', INFOSHEETNAME , TOSHEETNAME]




	private int totalNumberOfDataLine	= 0 // Total number of data line for a same "cas de test"

	private Map param = [:]				// map with param  example param.LOCATOR.getAt('ST_NOM') or param.getAt('LOCATOR').getAt('ST_NOM')

	private String JDDFullName = ''
	private XSSFWorkbook book
	private Sheet shTC					// "Test case" sheet

	private String DBTableName = ''		// DB table name of this MOD OBJ



	/**
	 * CONSTRUCTOR 
	 * 
	 * 
	 * @return JDD class
	 */

	oldJDD(String JDDFullName ='' ){

		if(JDDFullName == '') {
			this.JDDFullName = my.JDDFiles.getJDDFullNameFromCasDeTest(GlobalVariable.CASDETESTENCOURS)
		}else {
			this.JDDFullName = JDDFullName
		}

		my.Log.addSTEP("Lecture du JDD : " + this.JDDFullName)

		// read JDD
		File sourceExcel = new File(this.JDDFullName)
		def fxls = new FileInputStream(sourceExcel)
		this.book = new XSSFWorkbook(fxls)



	}




	public prepareJDD(TCTabName ='', casDeTest = '' ){


		if(TCTabName == '') {
			TCTabName = GlobalVariable.CASDETESTENCOURS.split('\\.')[2]
		}

		my.Log.addSTEP("Lecture du TAB : $TCTabName du JDD : " + this.JDDFullName)

		if(casDeTest == '') {
			casDeTest = GlobalVariable.CASDETESTENCOURS
		}
		my.Log.addDEBUG("casDeTest : $casDeTest")

		// set tab (sheet)
		this.shTC = this.book.getSheet(TCTabName)

		this.loadParam(shTC)
		this.addXpathFromTOSheet(this.book.getSheet(this.TOSHEETNAME))

		this.DBTableName = this.getTableNameFromInfoSheet(this.book.getSheet(this.INFOSHEETNAME),TCTabName)

		if (casDeTest !='') {
			this.prepareCasDeTestData(casDeTest)
		}

	}


	public getAllPrerequis(List list) {

		for(Sheet sheet: this.book) {

			if (sheet.getSheetName() in SKIP_LIST_SHEETNAME) {
				// skip
			}else {

				this.loadParam(sheet,'PREREQUIS')

				this.param.getAt('PREREQUIS').each { fieldName,value ->

					Map prerequisMap = [:]

					prerequisMap.putAt('PREJDDMODOBJ',value.split(/\*/)[0])
					prerequisMap.putAt('PREJDDTAB',value.split(/\*/)[1])
					prerequisMap.putAt('PREJDDID',value.split(/\*/)[2])
					prerequisMap.putAt('JDDNAME',this.JDDFullName)
					prerequisMap.putAt('TAB',sheet.getSheetName())
					prerequisMap.putAt('JDDID',fieldName)
					prerequisMap.putAt('LISTCDTVAL',this.getDataOfFieldName(sheet, fieldName))
					prerequisMap.each { key,val ->
						my.Log.addDEBUG('\t\t'+key + ' : ' +val)
					}
					list.add(prerequisMap)
				}

			}

		}
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

		//LOG
		my.Log.addDEBUG('public TestObject makeTO(String ID, Map  binding = [:]) ' + ID + ' , ' + Tools.parseMap(binding))

		TestObject to = new TestObject(ID)

		to.setSelectorMethod(SelectorMethod.XPATH)

		String xpath = this.getXpathFromID(ID)

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

				}else if (this.data.containsKey(value)) {

					binding.put(value,this.data.getAt(value))

					//LOG
					my.Log.addDEBUG('\tput in binding k --> v : '+ value + ' --> '+ this.data.getAt(value),2)

				}else {
					//LOG
					my.Log.addDEBUG('binding not possible because xpath parameter not found : ' + k,2)
				}
			}

			// dynamic xpath
			String dynxpath = engine.createTemplate(xpath).make(binding).toString()

			to.setSelectorValue(SelectorMethod.XPATH, dynxpath)

		}else {
			// normal xpath
			to.setSelectorValue(SelectorMethod.XPATH, xpath)
		}

		//LOG
		my.Log.addDEBUG('getObjectId : ' + to.getObjectId(),2)
		my.Log.addDEBUG('get(SelectorMethod.XPATH) : ' + to.getSelectorCollection().get(SelectorMethod.XPATH),2)

		return to
	}





	public  isParamExistForThisID(String param, String ID = null) {

		if (this.param.getAt(param) != null) {

			if (ID == null) {
				return true
			}else {
				return this.param.getAt(param).getAt(ID) != null
			}

		}else {
			return false
		}
	}




	private String getXpathFromID(String ID) {

		//private String defineXpath (String ID, tag, loc){

		my.Log.addDEBUG("\tgetXpathFromID () ID = $ID" ,2 )

		String loc = this.isParamExistForThisID('LOCATOR',ID) ? this.param.LOCATOR.getAt(ID) : ''
		String tag = this.isParamExistForThisID('TAGIHM',ID) ? this.param.TAGIHM.getAt(ID) : ''

		my.Log.addDEBUG("\t\t loc='$loc' tag='$tag'" ,2 )

		if (loc=='') {
			// it's a standard xpath
			if (tag != '') {
				return "//" + tag + "[@id='" + ID + "']"
			}else {
				my.Log.addERROR("Pas de TAGIHM pour le champ $ID")
			}


		}else if (loc.substring(0,1) == '/') {
			// it's a xpath with potential dynamic values
			return loc

		}else {
			// it's another ID
			return "//" + tag + "[@id='" + loc + "']"
		}

	}




	/**
	 * 
	 *
	 * @param
	 *
	 * @return NA
	 */
	public prepareCasDeTestDataIfNeeded(int casDeTestNum) {

		if (this.totalNumberOfDataLine>1 && casDeTestNum==1) {
			my.Log.addSTEP("Etapes pour JDD N° 1")
		}

		//set myJDD.data (casDeTestNum)
		if (casDeTestNum>1) {
			this.prepareCasDeTestData(GlobalVariable.CASDETESTENCOURS,casDeTestNum)
			my.Log.addSTEP("Etapes pour JDD N° $casDeTestNum")
		}

	}



	private int getFirstDataLine(Sheet shTC) {

		int firstLine = 1
		for (int numLine : 1..shTC.getLastRowNum()) {

			Row row = shTC.getRow(numLine)

			String paramName = my.XLS.getCellValue(row.getCell(0))

			firstLine = numLine

			// exit if lastRow of param
			if ( my.XLS.getCellValue(row.getCell(0))=="DATA") {
				break
			}
		}

		my.Log.addDEBUG("getFirstDataLine(Sheet shTC) SheetName = " + shTC.getSheetName() + "FirstDataLine = " + firstLine+1,2)
		return firstLine+1
	}


	/**
	 * Read data from JDD Test case and set data map
	 *
	 * @param casDeTest	: "Cas de test" name
	 *
	 * @return NA
	 */
	private prepareCasDeTestData(String casDeTestPattern, int casDeTestNum = 1) {

		my.Log.addDEBUG("prepareCasDeTestData()  casDeTestPattern = '$casDeTestPattern'  casDeTestNum = '$casDeTestNum'")

		Map mAllLines = [:]

		this.totalNumberOfDataLine = 0

		int firstDataLine = this.getFirstDataLine(this.shTC)

		// for each data line
		for (int numline : (firstDataLine..this.shTC.getLastRowNum())) {

			Row row = this.shTC.getRow(numline)

			// exit if lastRow
			if (my.XLS.getCellValue(row.getCell(0))=="") {
				break
			}

			// if this line is for this casDeTest
			if (my.XLS.getCellValue(row.getCell(0)).startsWith(casDeTestPattern)) {

				Map mLine = [:]

				for (Cell c in row) {


					// clean cell to keep only some characters
					String id = my.XLS.getCellValue(this.shTC.getRow(0).getCell(c.getColumnIndex()))

					mLine.put(id,my.XLS.getCellValue(c))

					//LOG
					my.Log.addDEBUG('\tid : ' + id , 2)

				}

				this.totalNumberOfDataLine++
				mAllLines.put(this.totalNumberOfDataLine,mLine)
			}

			my.Log.addDEBUG('\ttotalNumberOfDataLine : ' + this.totalNumberOfDataLine)

		}
		this.data = mAllLines[casDeTestNum]

	} // end






	private loadParam(Sheet shTC, String param = '') {

		my.Log.addDEBUG("loadParam() param = $param")

		if (param !='' && !(param in PARAM_LIST_ALLOWED)) {

			my.Log.addERROR("loadParam() - param inconnu $param")

		}

		// read the lines
		for (int numline : 1..shTC.getLastRowNum()) {

			Row row = shTC.getRow(numline)

			String paramName = my.XLS.getCellValue(row.getCell(0))


			// exit if lastRow of param
			//28/01/2023 if ( paramName=="" || paramName=="DATA" || (param != '' && paramName!=param)) {
			if (paramName=="DATA") {
				break
			}

			//28/01/2023if (paramName in PARAM_LIST_ALLOWED) {
			if (paramName in PARAM_LIST_ALLOWED) {

				if (param == '' || paramName==param) {

					my.Log.addDEBUG("paramName allowed '$paramName'")

					Map mapFieldValue = [:]
					// read column
					for (Cell c in shTC.getRow(0)) {

						// Skip first column
						if (c.getColumnIndex()!=0) {

							String fieldName = my.XLS.getCellValue(c)

							if (fieldName == "") {
								break // exit if last cell
							}

							// value in param
							String paramValue = my.XLS.getCellValue(row.getCell(c.columnIndex))
							if (paramValue!='') {

								mapFieldValue.put(fieldName,paramValue)

							}

						}

					}
					this.putParamInMap(paramName,mapFieldValue)
				}
			}else {

				my.Log.addERROR("loadParam() - paramètre inconnu $paramName ligne : $numline" )

			}
		}

	} //




	private putParamInMap(String paramName, Map map=[:]) {

		this.param.put(paramName, map)
	}





	private List getDataOfFieldName(Sheet sheet, String ID) {

		int fline = this.getFirstDataLine(sheet)

		int colIdx = my.XLS.getColumnIndexOfColumnName(sheet,ID)

		List list =[]

		for (int i = fline; i <= sheet.getLastRowNum(); i++) {

			String casDeTest = my.XLS.getCellValue(sheet.getRow(i).getCell(0))
			String IDvalue = my.XLS.getCellValue(sheet.getRow(i).getCell(colIdx))

			if (IDvalue !='') {
				list.add("'" + casDeTest + "' - '" + IDvalue + "'")
			}

			if (casDeTest =="") {
				break // exit if last cell
			}

		}
		return list
	}




	private int getColIndexOfFieldName(Sheet sheet, String fieldName) {

		for (Cell cell : sheet.getRow(0)) {
			if (my.XLS.getCellValue(cell)==fieldName) {
				return cell.columnIndex
			}
		}
	}





	private addXpathFromTOSheet(Sheet shTO) {

		if (shTO != null) {

			my.Log.addDEBUG('addXpathFromTOSheet()')

			for (int i = 1; i <= shTO.getLastRowNum(); i++) {

				String tab = my.XLS.getCellValue(shTO.getRow(i).getCell(0))
				String nom = my.XLS.getCellValue(shTO.getRow(i).getCell(1))
				String toxpath = my.XLS.getCellValue(shTO.getRow(i).getCell(2))

				if (nom =="") {
					break // exit if last cell
				}

				my.Log.addDEBUG("\ttab, nom , xpath : $tab , $nom , $toxpath",2 )

				if (this.param.getAt('LOCATOR') == null) {
					this.putParamInMap('LOCATOR')
				}
				this.param.LOCATOR.put(nom,toxpath)

			}
		}
	} //end





	/**
	 * Read 
	 *
	 * @param NA
	 *
	 * @return NA
	 */
	private String getTableNameFromInfoSheet(Sheet shINFO, String tabName) {

		my.Log.addDEBUG("getTableNameFromInfoSheet(String tabName) tabName='$tabName'")

		for (int i = 0; i <= shINFO.getLastRowNum(); i++) {

			String tab = my.XLS.getCellValue(shINFO.getRow(i).getCell(0))

			if (tab =="") {

				my.Log.addDEBUG("Nom de la table DB pour l'onglet '$tabName', non trouvé dans l'onglet '" + shINFO.getSheetName() + "'" )
				break // exit if last cell
			}

			if (tab == tabName) {

				my.Log.addDEBUG("Nom de la table DB pour l'onglet '$tabName', trouvé : " + my.XLS.getCellValue(shINFO.getRow(i).getCell(1)))

				return my.XLS.getCellValue(shINFO.getRow(i).getCell(1))

				break // exit if tabName found
			}

		}

	} //end






	public boolean isForeignKey(String id) {

		return this.isParamExistForThisID('FOREIGNKEY', id)

	}




	public String getSqlForForeignKey(String id, String whereVal) {

		def sql = this.param.FOREIGNKEY.getAt(id).toString().split(/\*/)

		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '$whereVal'"

		my.Log.addDEBUG("getSqlForForeignKey = $query")

		return query

	}




	public String getDBTableName() {

		return this.DBTableName
	}




	public int getTotalNumberOfDataLine() {

		return this.totalNumberOfDataLine
	}

} // end of class
