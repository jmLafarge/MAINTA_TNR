package myJDDManager

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import internal.GlobalVariable
import my.Log
import my.SQL
import my.TCFiles
import my.Tools

@CompileStatic
public class JDD {

	private static final String CLASS_FORLOG = 'JDD'



	private final String TOSHEETNAME = 'IHMTO'
	private final String INTERNALVALUESHEETNAME = 'INTERNALVALUE'


	/////////////////////////// à suppriler
	private final List PARAM_LIST_ALLOWED		= [
		'PREREQUIS',
		'FOREIGNKEY',
		'LOCATOR',
		'SEQUENCE',
		'INTERNALVALUE'
	]
	///////////////////////////////////////////



	/////////////////////// a ajouter évebtuellement changer de nom

	JDDHeaders JDDHeader
	JDDParams JDDParam
	JDDDatas JDDData

	///////////////////////










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

	//private List <String> headers = []
	//private List <List <String>> params  = []
	//private List <List> datas   = []
	private Map <String,String> xpathTO  = [:]





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

		Log.addTraceBEGIN(CLASS_FORLOG,"JDD",[fullname:fullname,tabName:tabName,cdt:cdt,step:step])


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
				Log.addTrace("tab : $tab name : $name xpath : $xpath TCTabName =${TCTabName}")
				if (tab == '') {
					break
				}else if (tab in [TCTabName, 'ALL']) {
					Log.addTrace("xpathTO.put($name, $xpath)")
					xpathTO.put(name, xpath)
				}

			}
		}else {
			Log.addTrace("Pas de d'onglet '$TOSHEETNAME'" )
		}

		Log.addTrace("xpathTO = " + xpathTO.toString())

		// ajout des INTERNALVALUE de l'onglet INTERNALVALUESHEETNAME s il existe
		if (book.getSheet(INTERNALVALUESHEETNAME) != null) {
			IV.addAll(book.getSheet(INTERNALVALUESHEETNAME))
		}

		Log.addTraceEND(CLASS_FORLOG,"JDD")
	}





	def loadTCSheet(Sheet sheet) {

		Log.addTraceBEGIN(CLASS_FORLOG,"loadTCSheet",[sheet:sheet.getSheetName()])

		TCSheet = sheet

		JDDHeader = new JDDHeaders(sheet)

		JDDParam = new JDDParams(sheet,JDDHeader,START_DATA_WORD)

		JDDData = new JDDDatas(sheet,JDDHeader,'CAS_DE_TEST')

		String cdtPattern = casDeTest ? casDeTest : GlobalVariable.CASDETESTPATTERN

		//Liste des cas de test qui répondent au pattern, sans doublons (les doublons sont des casDeTestNum)
		List <String> cdtli = JDDData.getCdtsContainingSubstringWithoutDuplicates(cdtPattern)

		//supprimer de la liste les cas de test traités par un autre Test Case
		CDTList = cdtli.findAll { cdt ->
			cdt.startsWith(cdtPattern) && (!TCFiles.TCfileMap.containsKey(cdt) || cdt == cdtPattern)
		}


		if (CDTList.size()==0 && cdtPattern){
			Log.addINFO('Pas de cas de test défini pour '+cdtPattern)
		}

		Log.addTraceEND(CLASS_FORLOG,"loadTCSheet")

	}





	/**
	 * Compte le nombre de lignes dans le cas de test donné.
	 *
	 * @param cdt Le nom du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return Le nombre de lignes dans le cas de test.
	 */
	def int getNbrLigneCasDeTest(String cdt = casDeTest) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getNbrLigneCasDeTest",[cdt:cdt])

		int ret = JDDData.getNbrLigneCasDeTest(cdt)

		Log.addTraceEND(CLASS_FORLOG,"getNbrLigneCasDeTest",ret)
		return ret
	}



	/**
	 * Définit le numéro du cas de test.
	 *
	 * @param i Le numéro du cas de test.
	 */
	def void setCasDeTestNum(int i) {
		Log.addTraceBEGIN(CLASS_FORLOG,"setCasDeTestNum",[i:i])
		casDeTestNum = i
		Log.addTraceEND(CLASS_FORLOG,"setCasDeTestNum")
	}



	/**
	 * Définit le cas de test courant.
	 *
	 * @param cdt Le nom du cas de test.
	 */
	def void setCasDeTest(String cdt) {
		Log.addTraceBEGIN(CLASS_FORLOG,"setCasDeTest",[cdt:cdt])
		casDeTest = cdt
		Log.addTraceEND(CLASS_FORLOG,"setCasDeTest")
	}




	/**
	 * Récupère le numéro de la ligne de données pour le cas de test et le numéro de cas de test donnés.
	 *
	 * @param cdt Le nom du cas de test (valeur par défaut : cas de test courant).
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return Le numéro de la ligne de données pour le cas de test et le numéro de cas de test donnés, ou null si le numéro de cas de test est invalide.
	 */
	def int getDataLineNum(String cdt = casDeTest, int casDeTestNum = casDeTestNum) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDataLineNum",[cdt:cdt,casDeTestNum:casDeTestNum])
		int dataLineNum = -1
		int cdtnum = 0
		if (casDeTestNum > getNbrLigneCasDeTest(cdt) || casDeTestNum < 1) {
			Log.addERROR("Le cas de test N° : $casDeTestNum n'existe pas (max = "+ getNbrLigneCasDeTest(cdt) + ')')
		}else {
			for (int i=0; i< datas.size();i++) {
				if (datas[i][0]==cdt) {
					cdtnum++
					if (cdtnum==casDeTestNum) {
						dataLineNum = i
						break
					}
				}
			}
		}

		Log.addTraceEND(CLASS_FORLOG,"getDataLineNum",dataLineNum)
		return dataLineNum
	}




	boolean isDataUPD(String name, def cdtnum=null) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isDataUPD",[name:name , cdtnum:cdtnum])
		cdtnum = (cdtnum?:casDeTestNum) as int
		boolean ret = false
		// Vérifie que le numéro de cas de test est valide.
		if (cdtnum > getNbrLigneCasDeTest() || cdtnum < 1) {
			Log.addERROR("Le cas de test N° : $cdtnum n'existe pas (max = "+ getNbrLigneCasDeTest() + ')')
		}else {
			// Vérifie que le nom fait partie des en-têtes.
			if (JDDHeader.headersList.contains(name)) {
				// Récupère la donnée correspondante et vérifie si c'est de type UPD

				ret = JDDKW.isUPD(JDDData.getRawData(name,casDeTest, cdtnum))
			} else {
				Log.addERROR("'$name' n'est pas une colonne du JDD")
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"isDataUPD",ret)
		return ret
	}





	/**
	 * Récupère la donnée correspondant au nom et au numéro de cas de test donnés.
	 *
	 * @param name Le nom de la donnée à récupérer.
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return La donnée correspondant au nom et au numéro de cas de test donnés, ou null si le numéro de cas de test ou le nom est invalide.
	 */
	def getData(String name, def cdtnum=null, boolean UPD = false) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getData",[name:name , cdtnum:cdtnum , UPD:UPD])
		cdtnum = (cdtnum?:casDeTestNum) as int
		def ret
		// Vérifie que le numéro de cas de test est valide.
		if (cdtnum > getNbrLigneCasDeTest() || cdtnum < 1) {
			Log.addERROR("Le cas de test N° : $cdtnum n'existe pas (max = "+ getNbrLigneCasDeTest() + ')')
		}else {
			// Vérifie que le nom fait partie des en-têtes.
			if (JDDHeader.headersList.contains(name)) {
				// Récupère la donnée correspondante.
				ret = JDDData.getRawData(name,casDeTest, cdtnum)
				if (JDDKW.isUPD(ret)) {
					if (UPD) {
						ret = JDDKW.getNewValueOfKW_UPD(ret)
					}else {
						ret = JDDKW.getOldValueOfKW_UPD(ret)
					}
				}
				String paraIV = JDDParam.getINTERNALVALUEFor(name)

				if (paraIV) {
					ret = IV.getInternalValueOf(paraIV,ret.toString())
				}

				//
			} else {
				Log.addERROR("'$name' n'est pas une colonne du JDD")
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"getData",ret)
		return ret
	}







	/**
	 * Récupère la donnée correspondant au nom et au numéro de cas de test donnés en tant que chaîne de caractères.
	 *
	 * @param name Le nom de la donnée à récupérer (valeur par défaut : '')
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return La donnée correspondant au nom et au numéro de cas de test donnés, convertie en chaîne de caractères.
	 */
	def String getStrData(String name = null, def cdtnum =null , boolean UPD = false) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getStrData",[name:name,cdtnum:cdtnum , UPD:UPD])
		cdtnum = (cdtnum?:casDeTestNum) as int
		name = name?:JDDHeader.headersList[0]
		String ret = getData(name, casDeTestNum,UPD).toString()
		Log.addTraceEND(CLASS_FORLOG,"getStrData",ret)
		return ret
	}






	/**
	 * Récupère le nom de la table de la base de données.
	 *
	 * @return Le nom de la table de la base de données.
	 */
	public String getDBTableName() {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDBTableName",[:])
		String tableName = JDDHeader.tableName
		Log.addTraceEND(CLASS_FORLOG,"getDBTableName",tableName)
		return tableName
	}





	/**
	 * Ajoute des expressions XPath à xpathTO
	 *
	 * @param locators Une liste de localisateurs.
	 */
	private addXpath(List <String> locators) {
		Log.addTraceBEGIN(CLASS_FORLOG,"addXpath",[locators:locators])

		// Itère sur chaque localisateur et son index.
		locators.eachWithIndex {loc,i ->
			// Assure que le localisateur est valide et que l'index n'est pas 0.
			if (loc!=null && loc!='' && i!=0) {
				String name = headers[i]

				// Ajoute le XPath approprié.
				if (loc in TAG_LIST_ALLOWED) {
					if (loc == 'checkbox') {
						xpathTO.put(name, "//input[@id='" + name +"' and @type='checkbox']")
						xpathTO.put('Lbl'+name, "//label[@id='Lbl$name']".toString())
					}else if (loc == 'radio') {
						//xpathTO.put(name, "//input[@id='" + name +"' and @type='radio']")
						xpathTO.put(name, "//label[@id='L${name}']".toString())
					}else if (loc=='input') {
						xpathTO.put(name, "//$loc[@id='$name' and not(@type='hidden')]".toString())
					}else {
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
		Log.addTrace("xpathTO $xpathTO")
		Log.addTraceEND(CLASS_FORLOG,"addXpath")
	}









	/**
	 * Vérifie si une valeur est obsolète.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est obsolète, false sinon.
	 */
	def boolean isOBSOLETE(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isOBSOLETE",[name:name])

		String ret = JDDParam.getPREREQUISFor(name)

		boolean result = ret ? ret == 'OBSOLETE' : false

		Log.addTraceEND(CLASS_FORLOG,"isOBSOLETE",result)
		return result
	}





	/**
	 * Vérifie si une valeur est une clé étrangère.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est une clé étrangère, false sinon.
	 */
	def boolean isFK(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isFK",[name:name])

		boolean result = JDDParam.getFOREIGNKEYFor(name)!=''

		Log.addTraceEND(CLASS_FORLOG,"isFK",result)
		return result
	}



	/**
	 * Construit une requête SQL pour une clé étrangère.
	 *
	 * @param name Le nom de la clé étrangère.
	 * @return La requête SQL construite.
	 */
	def String getSqlForForeignKey(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getSqlForForeignKey",[name:name])

		def sql = JDDParam.getFOREIGNKEYFor(name).toString().split(/\*/)
		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '" + getData(name) + "'"

		Log.addTraceEND(CLASS_FORLOG,"getSqlForForeignKey",query)
		return query
	}





	/**
	 * Remplace un SEQUENCID dans le JDD par une séquence de base de données.
	 *
	 * @param fieldName Le nom du champ dans lequel remplacer le SEQUENCID.
	 * @param delta Le décalage à appliquer à la séquence. Valeur par défaut : 0.
	 */
	def replaceSEQUENCIDInJDD(String fieldName, int delta=0) {
		Log.addTraceBEGIN(CLASS_FORLOG,"replaceSEQUENCIDInJDD",[fieldName:fieldName,delta:delta])

		int dataLineNum = getDataLineNum()
		int index = headers.indexOf(fieldName)
		if (JDDKW.isSEQUENCEID(getStrData(fieldName))){

			String paraSeq =  JDDParam.getSEQUENCEFor(fieldName)
			datas[dataLineNum][index] = SQL.getLastSequence(paraSeq)+delta
		}

		Log.addTraceEND(CLASS_FORLOG,"replaceSEQUENCIDInJDD")
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

	/*
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
	 */

	def boolean isTagAllowed(String tag) {

		return tag in TAG_LIST_ALLOWED
	}

	def List <List> getParamListAllowed() {
		return PARAM_LIST_ALLOWED
	}




	/**
	 * Récupère le numéro de ligne d'un paramètre.
	 *
	 * @param para Nom du paramètre.
	 * @return Le numéro de ligne du paramètre.
	 */
	def int getLineNumberOfParam(String para) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getLineNumberOfParam",[para:para])

		int ret =-1
		for (List param : params) {
			if (param[0] == para){
				ret = params.indexOf(param)+1
			}
		}

		Log.addTraceEND(CLASS_FORLOG,"getLineNumberOfParam",ret)
		return ret
	}



	/**
	 * Met à jour la valeur d'un locator dans le JDD.
	 *
	 * @param name Nom du locator.
	 * @param val Nouvelle valeur du locator.
	 */
	/* NOT USED
	 def setLOCATOR(String name, String val) {
	 Log.addTraceBEGIN(CLASS_FORLOG,"setLOCATOR",[name:name,val:val])
	 CellStyle stylePara = TCSheet.getRow(1).getCell(1).getCellStyle()
	 int locLineNumber = getLineNumberOfParam('LOCATOR')
	 int colNumber = getHeaderIndexOf(name)
	 my.XLS.writeCell(TCSheet.getRow(locLineNumber),colNumber,val,stylePara)
	 OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
	 book.write(JDDfileOut)
	 setParamForThisName('LOCATOR', name, val)
	 Log.addTraceEND(CLASS_FORLOG,"setLOCATOR")
	 }
	 */



	/**
	 * Ajoute un nouvel élément à l'IHMTO.
	 *
	 * @param tab Nom de l'onglet.
	 * @param nom Nom du nouvel élément.
	 * @param xpath Xpath du nouvel élément.
	 */
	def addIHMTO(String tab, String nom, String xpath) {
		Log.addTraceBEGIN(CLASS_FORLOG,"addIHMTO",[tab:tab,nom:nom,xpath:xpath])

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

		Log.addTraceEND(CLASS_FORLOG,"addIHMTO")
	}


	/**
	 * Ajoute une nouvelle colonne au JDD.
	 *
	 * @param name Nom de la nouvelle colonne.
	 */
	def addColumn(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"addColumn",[name:name])

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

		Log.addTraceEND(CLASS_FORLOG,"addColumn")
	}


} // end of class
