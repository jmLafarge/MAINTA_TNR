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

		Log.addTraceBEGIN("JDD.JDD(fullname = '$fullname'    tabName = '$tabName' Cas de test = '$cdt' step = $step)")


		Log.addTrace("GlobalVariable.CASDETESTPATTERN : "+GlobalVariable.CASDETESTPATTERN)

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
		 Log.addTrace("Lecture du JDD : " + JDDFullName)
		 }
		 */
		Log.addTrace("Lecture du JDD : " + JDDFullName)


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
				Log.addTrace("tab : $tab name : $name xpath : $xpath TCTabName =${TCTabName}",2)
				if (tab == '') {
					break
				}else if (tab in [TCTabName, 'ALL']) {
					Log.addTrace("xpathTO.put($name, $xpath)",2)
					xpathTO.put(name, xpath)
				}

			}
		}else {
			Log.addTrace("Pas de d'onglet '$TOSHEETNAME'" )
		}

		Log.addTrace("xpathTO = " + xpathTO.toString(),2)


		// ajout des INTERNALVALUE de l'onglet INTERNALVALUESHEETNAME s il existe
		if (book.getSheet(INTERNALVALUESHEETNAME) != null) {
			Iterator<Row> rowIV = book.getSheet(INTERNALVALUESHEETNAME).rowIterator()
			rowIV.next()
			while(rowIV.hasNext()) {
				Row row = rowIV.next()
				String IV_para = my.XLS.getCellValue(row.getCell(0))
				String IV_val = my.XLS.getCellValue(row.getCell(1))
				String IV_code = my.XLS.getCellValue(row.getCell(2))
				Log.addTrace("IV_para : $IV_para IV_val : $IV_val IV_code : $IV_code ",2)
				if (IV_code == '') {
					break
				}else {
					List newIV = []
					newIV.addAll([IV_para, IV_val, IV_code])
					internalValues.add(newIV)
				}

			}
		}
		Log.addTrace("internalValues = " + internalValues.toString(),2)

		Log.addTraceEND("JDD.JDD()")
	}





	def loadTCSheet(Sheet sheet) {

		Log.addTraceBEGIN("JDD.loadTCSheet(${sheet.getSheetName()})")

		TCSheet = sheet
		Log.addTrace('Lecture headers')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		headers = my.XLS.loadRow(row)
		Log.addTrace('- headers.size = ' + headers.size())

		Log.addTrace('Lecture paramètres')
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
		Log.addTrace('- params.size = ' + params.size())

		Log.addTrace('Lecture data')
		datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			datas << my.XLS.loadRow(row,headers.size())
		}
		Log.addTrace('- datas.size = ' + datas.size())


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

		Log.addTraceEND("JDD.loadTCSheet()")

	}





	/**
	 * Compte le nombre de lignes dans le cas de test donné.
	 *
	 * @param cdt Le nom du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return Le nombre de lignes dans le cas de test.
	 */
	def int getNbrLigneCasDeTest(String cdt = casDeTest) {
		Log.addTraceBEGIN("JDD.getNbrLigneCasDeTest(${cdt})")

		// Compte le nombre de lignes pour ce le cas de test.
		int ret = (int)datas.count { it[0] == cdt }

		Log.addTraceEND("JDD.getNbrLigneCasDeTest()",ret)
		return ret
	}



	/**
	 * Définit le numéro du cas de test.
	 *
	 * @param i Le numéro du cas de test.
	 */
	def void setCasDeTestNum(int i) {
		Log.addTraceBEGIN("JDD.setCasDeTestNum(${i})")
		casDeTestNum = i
		Log.addTraceEND("JDD.setCasDeTestNum()")
	}



	/**
	 * Définit le cas de test courant.
	 *
	 * @param cdt Le nom du cas de test.
	 */
	def void setCasDeTest(String cdt) {
		Log.addTraceBEGIN("JDD.setCasDeTest(${cdt})")
		casDeTest = cdt
		Log.addTraceEND("JDD.setCasDeTest()")
	}




	/**
	 * Récupère le numéro de la ligne de données pour le cas de test et le numéro de cas de test donnés.
	 *
	 * @param cdt Le nom du cas de test (valeur par défaut : cas de test courant).
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return Le numéro de la ligne de données pour le cas de test et le numéro de cas de test donnés, ou null si le numéro de cas de test est invalide.
	 */
	def int getDataLineNum(String cdt = casDeTest, int casDeTestNum = casDeTestNum) {
		Log.addTraceBEGIN("JDD.getDataLineNum(${cdt}, ${casDeTestNum})")

		if (casDeTestNum > getNbrLigneCasDeTest(cdt) || casDeTestNum < 1) {
			Log.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ getNbrLigneCasDeTest(cdt) + ')')
			Log.addTraceEND("JDD.getDataLineNum()")
			return null
		}

		int dataLineNum = 0
		int cdtnum = 0

		for (int i=0; i< datas.size();i++) {
			if (datas[i][0]==cdt) {
				cdtnum++
				if (cdtnum==casDeTestNum) {
					dataLineNum = i
					Log.addTrace("Numéro de la ligne trouvée : $i")
					break
				}
			}
		}

		Log.addTraceEND("JDD.getDataLineNum()",dataLineNum)
		return dataLineNum
	}





	/**
	 * Récupère la donnée correspondant au nom et au numéro de cas de test donnés.
	 *
	 * @param name Le nom de la donnée à récupérer.
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return La donnée correspondant au nom et au numéro de cas de test donnés, ou null si le numéro de cas de test ou le nom est invalide.
	 */
	def getData(String name, int casDeTestNum = casDeTestNum) {
		Log.addTraceBEGIN("JDD.getData(${name}, ${casDeTestNum})")

		// Vérifie que le numéro de cas de test est valide.
		if (casDeTestNum > getNbrLigneCasDeTest() || casDeTestNum < 1) {
			Log.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ getNbrLigneCasDeTest() + ')')
			Log.addTraceEND("JDD.getData()")
			return null
		}

		// Vérifie que le nom fait partie des en-têtes.
		if (headers.contains(name)) {
			// Récupère la donnée correspondante.
			def ret = datas[getDataLineNum(casDeTest, casDeTestNum)][headers.indexOf(name)]
			Log.addTraceEND("JDD.getData()",ret)
			return ret
		} else {
			Log.addERROR("getData($name, $casDeTestNum ) '$name' n'est pas une colonne du JDD")
			Log.addTraceEND("JDD.getData()")
			return null
		}
	}



	/**
	 * Récupère la donnée correspondant au nom et au numéro de cas de test donnés en tant que chaîne de caractères.
	 *
	 * @param name Le nom de la donnée à récupérer (valeur par défaut : '')
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return La donnée correspondant au nom et au numéro de cas de test donnés, convertie en chaîne de caractères.
	 */
	def String getStrData(String name='', int casDeTestNum = casDeTestNum) {
		Log.addTraceBEGIN("JDD.getStrData(${name}, ${casDeTestNum})")

		String ret
		if (name=='') {
			int dataLineNum = getDataLineNum()
			ret = datas[dataLineNum][1]
		} else {
			ret = getData(name, casDeTestNum).toString()
		}

		Log.addTraceEND("JDD.getStrData()",ret)
		return ret
	}



	/**
	 * Récupère le nom de la table de la base de données.
	 *
	 * @return Le nom de la table de la base de données.
	 */
	public String getDBTableName() {
		Log.addTraceBEGIN("JDD.getDBTableName()")
		String tableName = headers[0]
		Log.addTraceEND("JDD.getDBTableName()",tableName)
		return tableName
	}



	/**
	 * Récupère le nom de l'en-tête à l'indice donné.
	 *
	 * @param i L'indice de l'en-tête à récupérer.
	 * @return Le nom de l'en-tête à l'indice donné.
	 */
	def String getHeaderNameOfIndex(int i) {
		Log.addTraceBEGIN("JDD.getHeaderNameOfIndex(${i})")
		String headerName = headers[i]
		Log.addTraceEND("JDD.getHeaderNameOfIndex()",headerName)
		return headerName
	}


	/**
	 * Récupère l'indice de l'en-tête correspondant au nom donné.
	 *
	 * @param name Le nom de l'en-tête.
	 * @return L'indice de l'en-tête correspondant au nom donné, ou -1 si le nom n'est pas trouvé.
	 */
	def int getHeaderIndexOf(String name) {
		Log.addTraceBEGIN("JDD.getHeaderIndexOf(${name})")
		int headerIndex = headers.indexOf(name)
		Log.addTraceEND("JDD.getHeaderIndexOf()",headerIndex)
		return headerIndex
	}






	/**
	 * Ajoute des expressions XPath à xpathTO
	 *
	 * @param locators Une liste de localisateurs.
	 */
	private addXpath(List <String> locators) {
		Log.addTraceBEGIN("JDD.addXpath(${locators})")

		// Itère sur chaque localisateur et son index.
		locators.eachWithIndex {loc,i ->
			// Assure que le localisateur est valide et que l'index n'est pas 0.
			if (loc!=null && loc!='' && i!=0) {
				String name = headers[i]

				// Ajoute le XPath approprié.
				if (loc in TAG_LIST_ALLOWED) {
					// cas spécifiques de checkbox et radio.
					if (loc in ['checkbox', 'radio']) {
						xpathTO.put(name, "//input[@id='" + name +"']")
						xpathTO.put('Lbl'+name, "//label[@id='Lbl$name']".toString())
					}else if (loc=='input') {
						// cas spécifique de input.
						xpathTO.put(name, "//$loc[@id='$name' and not(@type='hidden')]".toString())
					}else {
						// XPath standard
						xpathTO.put(name, "//$loc[@id='$name']".toString())
					}
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					// balises avec attributs.
					def lo = loc.toString().split(/\*/)
					if (lo[0] in TAG_LIST_ALLOWED) {
						xpathTO.put(name, "//${lo[0]}[@${lo[1]}='$name']".toString())
					}else {
						Log.addERROR("LOCATOR inconnu : ${lo[0]} in '$loc'")
					}
				}else if (loc[0] == '/') {
					// XPath avec des valeurs potentiellement dynamiques.
					xpathTO.put(name,loc)
				}else {
					Log.addERROR("LOCATOR inconnu : '$loc'")
				}
			}
		}

		Log.addTraceEND("JDD.addXpath()")
	}




	/**
	 * Récupère une liste de paramètres.
	 *
	 * @param param Le nom du paramètre à récupérer.
	 * @return Une liste de valeurs du paramètre.
	 */
	def List <String> getParam(String param) {
		Log.addTraceBEGIN("JDD.getParam(${param})")

		List  ret = null
		if (!(param in PARAM_LIST_ALLOWED)) {
			Log.addERROR("Ce paramètre n'est pas autorisé")
		}

		for (List para : params) {
			if (para[0] == param) {
				ret = para
				break
			}
		}

		Log.addTraceEND("JDD.getParam()",ret)
		return ret
	}




	/**
	 * Récupère un paramètre pour un nom spécifique.
	 *
	 * @param param Le nom du paramètre à récupérer.
	 * @param name Le nom pour lequel récupérer le paramètre.
	 * @return La valeur du paramètre pour le nom spécifié.
	 */
	def String getParamForThisName(String param, String name) {
		Log.addTraceBEGIN("JDD.getParamForThisName(${param}, ${name})")

		List params = getParam(param)
		String ret = ''
		if (params != null) {

			if (headers.contains(name)) {
				if (params[headers.indexOf(name)] !='') {
					ret = params[headers.indexOf(name)]
				}
			} else {
				Log.addERROR("'${name}' n'est pas une colonne du JDD")
			}
		}

		Log.addTraceEND("JDD.getParamForThisName()",ret)
		return ret
	}




	/**
	 * Définit un paramètre pour un nom spécifique.
	 *
	 * @param param Le nom du paramètre à définir.
	 * @param name Le nom pour lequel définir le paramètre.
	 * @param val La valeur à définir pour le paramètre.
	 */
	def setParamForThisName(String param, String name, String val) {
		Log.addTraceBEGIN("JDD.setParamForThisName(${param}, ${name}, ${val})")

		List params = getParam(param)
		if (params != null) {

			if (headers.contains(name)) {
				String para = params[headers.indexOf(name)]
				if (para) {
					Log.addDETAILWARNING("La paramètre existe déjà '${para}'")
				} else {
					params[headers.indexOf(name)] = val
				}
			} else {
				Log.addERROR("'${name}' n'est pas une colonne du JDD")
			}
		}

		Log.addTraceEND("JDD.setParamForThisName()")
	}




	/**
	 * Vérifie si une valeur est obsolète.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est obsolète, false sinon.
	 */
	def boolean isOBSOLETE(String name) {
		Log.addTraceBEGIN("JDD.isOBSOLETE()")

		String ret = getParamForThisName('PREREQUIS',name)

		boolean result = ret ? ret == 'OBSOLETE' : false

		Log.addTraceEND("JDD.isOBSOLETE()",result)
		return result
	}




	/**
	 * Vérifie si une valeur est une clé étrangère.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est une clé étrangère, false sinon.
	 */
	def boolean isFK(String name) {
		Log.addTraceBEGIN("JDD.isFK(${name})")

		boolean result = getParamForThisName('FOREIGNKEY', name)!=''

		Log.addTraceEND("JDD.isFK()",result)
		return result
	}



	/**
	 * Construit une requête SQL pour une clé étrangère.
	 *
	 * @param name Le nom de la clé étrangère.
	 * @return La requête SQL construite.
	 */
	def String getSqlForForeignKey(String name) {
		Log.addTraceBEGIN("JDD.getSqlForForeignKey(${name})")

		def sql = getParamForThisName('FOREIGNKEY', name).toString().split(/\*/)
		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '" + getData(name) + "'"

		Log.addTraceEND("JDD.getSqlForForeignKey()",query)
		return query
	}





	/**
	 * Remplace un SEQUENCID dans le JDD par une séquence de base de données.
	 *
	 * @param fieldName Le nom du champ dans lequel remplacer le SEQUENCID.
	 * @param delta Le décalage à appliquer à la séquence. Valeur par défaut : 0.
	 */
	def replaceSEQUENCIDInJDD(String fieldName, int delta=0) {
		Log.addTraceBEGIN("JDD.replaceSEQUENCIDInJDD(${fieldName}, ${delta})")

		int dataLineNum = getDataLineNum()
		int index = headers.indexOf(fieldName)
		if (JDDKW.isSEQUENCEID(getStrData(fieldName))){

			String paraSeq =  getParamForThisName('SEQUENCE', fieldName)
			datas[dataLineNum][index] = SQL.getLastSequence(paraSeq)+delta
		}

		Log.addTraceEND("JDD.replaceSEQUENCIDInJDD()")
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

	def List <List> getParamListAllowed() {
		return PARAM_LIST_ALLOWED
	}




	/**
	 * Récupère la valeur interne d'un paramètre.
	 *
	 * @param para Nom du paramètre.
	 * @param val Valeur du paramètre.
	 * @return La valeur interne du paramètre.
	 */
	def String getInternalValueOf(String para, String val) {
		Log.addTraceBEGIN("JDD.getInternalValueOf(${para}, ${val})")

		String res = internalValues.find { it[0] == para && it[1] == val }?.get(2)

		Log.addTraceEND("JDD.getInternalValueOf()",res)
		return res
	}

	/**
	 * Récupère le numéro de ligne d'un paramètre.
	 *
	 * @param para Nom du paramètre.
	 * @return Le numéro de ligne du paramètre.
	 */
	def int getLineNumberOfParam(String para) {
		Log.addTraceBEGIN("JDD.getLineNumberOfParam(${para})")

		int ret =-1
		for (List param : params) {
			if (param[0] == para){
				ret = params.indexOf(param)+1
			}
		}

		Log.addTraceEND("JDD.getLineNumberOfParam()",ret)
		return ret
	}



	/**
	 * Met à jour la valeur d'un locator dans le JDD.
	 *
	 * @param name Nom du locator.
	 * @param val Nouvelle valeur du locator.
	 */
	def setLOCATOR(String name, String val) {
		Log.addTraceBEGIN("JDD.setLOCATOR(${name}, ${val})")

		CellStyle stylePara = TCSheet.getRow(1).getCell(1).getCellStyle()
		int locLineNumber = getLineNumberOfParam('LOCATOR')
		int colNumber = getHeaderIndexOf(name)
		my.XLS.writeCell(TCSheet.getRow(locLineNumber),colNumber,val,stylePara)
		OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
		book.write(JDDfileOut)
		setParamForThisName('LOCATOR', name, val)

		Log.addTraceEND("JDD.setLOCATOR()")
	}




	/**
	 * Ajoute un nouvel élément à l'IHMTO.
	 *
	 * @param tab Nom de l'onglet.
	 * @param nom Nom du nouvel élément.
	 * @param xpath Xpath du nouvel élément.
	 */
	def addIHMTO(String tab, String nom, String xpath) {
		Log.addTraceBEGIN("JDD.addIHMTO(${tab}, ${nom}, ${xpath})")

		if (xpathTO[nom]) {
			Log.addDETAILFAIL("IHMTO '${nom}' existe déjà")
		} else {
			Row newRow = my.XLS.getNextRow(TOSheet)
			my.XLS.writeCell(newRow, 0, tab)
			my.XLS.writeCell(newRow, 1, nom)
			my.XLS.writeCell(newRow, 2, xpath)
			OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
			book.write(JDDfileOut)
			xpathTO.put(nom, xpath)
		}

		Log.addTraceEND("JDD.addIHMTO()")
	}


	/**
	 * Ajoute une nouvelle colonne au JDD.
	 *
	 * @param name Nom de la nouvelle colonne.
	 */
	def addColumn(String name) {
		Log.addTraceBEGIN("JDD.addColumn(${name})")

		if (name in headers) {  // Si la colonne existe déjà
			Log.addTrace("\t- La colonne '${name}' existe déjà")
		} else {
			Log.addTrace("\t- Ajout de la colonne '${name}'")
			CellStyle styleChampIHM = book.createCellStyle()
			styleChampIHM.cloneStyleFrom(TCSheet.getRow(0).getCell(1).getCellStyle())
			styleChampIHM.setFillPattern(FillPatternType.SOLID_FOREGROUND)
			styleChampIHM.setFillForegroundColor(IndexedColors.PALE_BLUE.index)
			styleChampIHM.setFont(book.createFont())
			styleChampIHM.getFont().setColor(IndexedColors.BLACK.getIndex())
			styleChampIHM.getFont().setBold(true)
			CellStyle stylePara = TCSheet.getRow(1).getCell(1).getCellStyle()
			CellStyle styleCdt = TCSheet.getRow(params.size() + 1).getCell(1).getCellStyle()
			int numColFct = my.XLS.getLastColumnIndex(TCSheet, 0)
			my.XLS.writeCell(TCSheet.getRow(0), numColFct, name, styleChampIHM)
			for (int i in 1..params.size()) {
				my.XLS.writeCell(TCSheet.getRow(i), numColFct, null, stylePara)
			}
			my.XLS.writeCell(TCSheet.getRow(params.size() + 1), numColFct, null, styleCdt)
			OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
			book.write(JDDfileOut)
			headers.add(name)
		}

		Log.addTraceEND("JDD.addColumn()")
	}


} // end of class
