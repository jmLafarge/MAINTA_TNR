package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFDataFormat
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import internal.GlobalVariable
import my.Log


@CompileStatic
public class JDD {



	private final String TOSHEETNAME = 'IHMTO'
	private final String INTERNALVALUESHEETNAME = 'INTERNALVALUE'

	private final List PARAM_LIST_ALLOWED		= [
		'PREREQUIS',
		'FOREIGNKEY',
		'LOCATOR',
		'SEQUENCE',
		'INTERNALVALUE'
	]
	private final List TAG_LIST_ALLOWED			= [
		'input',
		'select',
		'textarea',
		'td',
		'checkbox',
		'radio'
	]
	private final List SKIP_LIST_SHEETNAME		= [
		'Version',
		'Info',
		TOSHEETNAME,
		'MODELE',
		INTERNALVALUESHEETNAME
	]


	private final String START_DATA_WORD		= 'CAS_DE_TEST'


	private XSSFWorkbook book
	private Sheet TCSheet
	private Sheet TOSheet

	private String JDDFullName = ''
	private String TCTabName   = ''
	private String casDeTest = ''
	private int casDeTestNum   = 1

	private List <String> headers = []
	private List <List <String>> params  = []
	private List <List> datas   = []
	private Map <String,String> xpathTO  = [:]

	private List <List <String>> internalValues  = []

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
		TOSheet = book.getSheet(TOSHEETNAME)
		if (TOSheet) {
			Iterator<Row> rowIt = TOSheet.rowIterator()
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
		}else {
			Log.addDEBUG("Pas de d'onglet '$TOSHEETNAME'" )
		}

		Log.addDEBUG("xpathTO = " + xpathTO.toString(),2)


		// ajout des INTERNALVALUE de l'onglet INTERNALVALUESHEETNAME s il existe
		if (book.getSheet(INTERNALVALUESHEETNAME) != null) {
			Iterator<Row> rowIV = book.getSheet(INTERNALVALUESHEETNAME).rowIterator()
			rowIV.next()
			while(rowIV.hasNext()) {
				Row row = rowIV.next()
				String IV_para = my.XLS.getCellValue(row.getCell(0))
				String IV_val = my.XLS.getCellValue(row.getCell(1))
				String IV_code = my.XLS.getCellValue(row.getCell(2))
				Log.addDEBUG("IV_para : $IV_para IV_val : $IV_val IV_code : $IV_code ",2)
				if (IV_code == '') {
					break
				}else {
					List newIV = []
					newIV.addAll([IV_para, IV_val, IV_code])
					internalValues.add(newIV)
				}

			}
		}
		Log.addDEBUG("internalValues = " + internalValues.toString(),2)


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
			def ret = datas[getDataLineNum(casDeTest, casDeTestNum)][headers.indexOf(name)]
			Log.addDEBUG("getData() --> $ret" , 2)
			return ret
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


	def int getHeaderIndexOf(String name) {
		return headers.indexOf(name)
	}





	private addXpath(List <String> locators) {
		locators.eachWithIndex {loc,i ->
			if (loc!=null && loc!='' && i!=0) {
				String name = headers[i]
				Log.addDEBUG("addXpath i = $i name = '$name' loc='$loc' ",2 )
				if (loc in TAG_LIST_ALLOWED) {
					if (loc in ['checkbox', 'radio']) {
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

		Log.addDEBUG("getParamForThisName($param $name)",2)

		List params = getParam(param)

		String ret = ''
		if (params != null) {
			if (headers.contains(name)) { /// verifier si dans headers
				if (params[headers.indexOf(name)] !='') {
					ret = params[headers.indexOf(name)]
				}
			}else {
				Log.addERROR("getParamForThisName(param=$param, name=$name) '$name' n'est pas une colonne du JDD")
			}
		}
		Log.addDEBUG("getParamForThisName() --> $ret",2)
		return ret
	}
	
	
	def setParamForThisName(String param, String name, String val) {
		
		Log.addDEBUG("setParamForThisName($param, $name, $val)",2)

		List params = getParam(param)

		String ret = ''
		if (params != null) {
			if (headers.contains(name)) { /// verifier si dans headers
				String para = params[headers.indexOf(name)]
				if (para) {
					Log.addDETAILWARNING("setParamForThisName(param=$param, name=$name val=$val) : la paramètre existe déjà '$para'")
				}else {
					params[headers.indexOf(name)] = val
				}
				
			}else {
				Log.addERROR("setParamForThisName(param=$param, name=$name val=$val) '$name' n'est pas une colonne du JDD")
			}
		}
		Log.addDEBUG("setParamForThisName() --> RAZ",2)
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

	def String getInternalValueOf(String para, String val) {
		Log.addDEBUG("getInternalValueOf($para, $val)")
		String res = internalValues.find { it[0] == para && it[1] == val }?.get(2)
		Log.addDEBUG("getInternalValueOf() --> $res")
		return res
	}

	def int getLineNumberOfParam(String para) {
		//voir JDDGenerator.getRowOfPara
		Log.addDEBUG("getLineNumberOfParam($para)")
		int ret =-1
		for (List param : params) {
			if (param[0] == para){
				ret = params.indexOf(param)+1
			}
		}
		Log.addDEBUG("getLineNumberOfParam() --> $ret")
		return ret
	}


	def setLOCATOR(String name, String val) {
		Log.addDEBUG("setLOCATOR($name, $val)")
		CellStyle stylePara = TCSheet.getRow(1).getCell(1).getCellStyle()
		int locLineNumber = getLineNumberOfParam('LOCATOR')
		int colNumber = getHeaderIndexOf(name)
		my.XLS.writeCell(TCSheet.getRow(locLineNumber),colNumber,val,stylePara)
		OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
		book.write(JDDfileOut)
		setParamForThisName('LOCATOR', name, val)
		Log.addDEBUG("setLOCATOR() --> raz")
	}


	def addIHMTO(String tab, String nom, String xpath) {
		Log.addDEBUG("addIHMTO($tab, $nom, $xpath)")
		if (xpathTO[nom]) {
			Log.addDETAILFAIL("IHMTO '$nom' existe déjà")
		}else {
			Row newRow = my.XLS.getNextRow(TOSheet)
	
			my.XLS.writeCell(newRow,0,tab)
			my.XLS.writeCell(newRow,1,nom)
			my.XLS.writeCell(newRow,2,xpath)
			OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
			book.write(JDDfileOut)
			xpathTO.put(nom, xpath)
		}
		Log.addDEBUG("addIHMTO() --> raz")
	}



	def addColumn(String name) {
		Log.addDEBUG("addColumn($name)")
		if (name in headers) {  // si la colonne existe déjà
			Log.addDEBUG("\t- la colonne '$name' existe déjà")
		}else {
			Log.addDEBUG("\t- Ajout de la colonne '$name'")
			CellStyle styleChampIHM = book.createCellStyle()
			styleChampIHM.cloneStyleFrom(TCSheet.getRow(0).getCell(1).getCellStyle())
			styleChampIHM.setFillPattern(FillPatternType.SOLID_FOREGROUND)
			styleChampIHM.setFillForegroundColor(IndexedColors.PALE_BLUE.index)
			styleChampIHM.setFont(book.createFont())
			styleChampIHM.getFont().setColor(IndexedColors.BLACK.getIndex())
			styleChampIHM.getFont().setBold(true)
			CellStyle stylePara = TCSheet.getRow(1).getCell(1).getCellStyle()
			CellStyle styleCdt = TCSheet.getRow(params.size()+1).getCell(1).getCellStyle()
			int numColFct = my.XLS.getLastColumnIndex(TCSheet,0)
			my.XLS.writeCell(TCSheet.getRow(0),numColFct,name,styleChampIHM)
			for (int i in 1..params.size()) {
				my.XLS.writeCell(TCSheet.getRow(i),numColFct,null,stylePara)
			}
			my.XLS.writeCell(TCSheet.getRow(params.size()+1),numColFct,null,styleCdt)
			OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
			book.write(JDDfileOut)
			headers.add(name)
		}
		Log.addDEBUG("addColumn() --> raz")
	}

} // end of class
