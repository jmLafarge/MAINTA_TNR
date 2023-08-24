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
import my.TCFileMapper
import my.Tools

@CompileStatic
public class JDD {

	private final String CLASS_FORLOG = 'JDD'



	private final String TOSHEETNAME 			= 'IHMTO'
	private final String INTERNALVALUESHEETNAME = 'INTERNALVALUE'
	private final String START_DATA_WORD		= 'CAS_DE_TEST'


	private final List SKIP_LIST_SHEETNAME		= [
		'Version',
		'Info',
		TOSHEETNAME,
		'MODELE',
		INTERNALVALUESHEETNAME
	]


	private XSSFWorkbook book
	private Sheet TCSheet
	private Sheet TOSheet

	private String JDDFullName = ''
	private String TCSheetName   = ''

	private String casDeTest = ''
	private int casDeTestNum   = 1

	private List CDTList = []


	/////////////////////// a ajouter évebtuellement changer de nom

	public JDDHeader myJDDHeader
	public JDDParam myJDDParam
	public JDDData myJDDData
	public JDDIHMTO myJDDIHMTO
	public JDDXpath myJDDXpath



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
			JDDFullName = JDDFileMapper.getFullnameFromModObj(modObj)
			TCSheetName = GlobalVariable.CASDETESTPATTERN.toString().split('\\.')[2]
		}else {
			JDDFullName = fullname
			TCSheetName = tabName
			casDeTest = cdt
		}

		Log.addTrace("Lecture du JDD : " + JDDFullName)


		book = my.XLS.open(JDDFullName)

		if (TCSheetName) {
			TCSheet = book.getSheet(TCSheetName)
			loadTCSheet(TCSheet)
		}


		// add INTERNALVALUE
		if (book.getSheet(INTERNALVALUESHEETNAME) != null) {
			JDDIV.addAll(book.getSheet(INTERNALVALUESHEETNAME))
		}

		Log.addTraceEND(CLASS_FORLOG,"JDD")
	}





	def loadTCSheet(Sheet sheet) {

		Log.addTraceBEGIN(CLASS_FORLOG,"loadTCSheet",[sheet:sheet.getSheetName()])

		TCSheet = sheet

		myJDDHeader = new JDDHeader(sheet)

		myJDDParam = new JDDParam(sheet,myJDDHeader,START_DATA_WORD)

		myJDDData = new JDDData(sheet,myJDDHeader,'CAS_DE_TEST')

		myJDDXpath  = new JDDXpath()

		myJDDXpath.addFromMap(myJDDParam.getAllLOCATOR())

		///////////////////////////////////////////////////////////// est ce que cela ne doit pas etre dans JDDData ?

		String cdtPattern = casDeTest ? casDeTest : GlobalVariable.CASDETESTPATTERN

		//Liste des cas de test qui répondent au pattern, sans doublons (les doublons sont des casDeTestNum)
		List <String> cdtli = myJDDData.getCdtsContainingSubstringWithoutDuplicates(cdtPattern)

		//supprimer de la liste les cas de test traités par un autre Test Case
		CDTList = cdtli.findAll { cdt ->
			cdt.startsWith(cdtPattern) && (!TCFileMapper.TCfileMap.containsKey(cdt) || cdt == cdtPattern)
		}

		if (CDTList.size()==0 && cdtPattern){
			Log.addINFO('Pas de cas de test défini pour '+ cdtPattern)
		}

		//casDeTest = casDeTest ? casDeTest : CDTList[0]

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////




		// add IHMTO
		TOSheet = book.getSheet(TOSHEETNAME)
		if (TOSheet) {
			myJDDIHMTO = new JDDIHMTO(TOSheet,TCSheetName)
		}else {
			Log.addTrace("Pas de d'onglet '$TOSHEETNAME'" )
		}

		myJDDXpath.addFromMap(myJDDIHMTO.getXpaths())

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

		int ret = myJDDData.getNbrLigneCasDeTest(cdt)

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






	boolean isDataUPD(String name, def cdtnum=null) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isDataUPD",[name:name , cdtnum:cdtnum])
		cdtnum = (cdtnum?:casDeTestNum) as int
		boolean ret = false
		// Vérifie que le numéro de cas de test est valide.
		if (cdtnum > getNbrLigneCasDeTest() || cdtnum < 1) {
			Log.addERROR("Le cas de test N° : $cdtnum n'existe pas (max = "+ getNbrLigneCasDeTest() + ')')
		}else {
			// Vérifie que le nom fait partie des en-têtes.
			if (myJDDHeader.list.contains(name)) {
				// Récupère la donnée correspondante et vérifie si c'est de type UPD

				ret = JDDKW.isUPD(myJDDData.getRawData(name,casDeTest, cdtnum))
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
			if (myJDDHeader.list.contains(name)) {
				// Récupère la donnée correspondante.
				ret = myJDDData.getRawData(name,casDeTest, cdtnum)
				if (JDDKW.isUPD(ret)) {
					if (UPD) {
						ret = JDDKW.getNewValueOfKW_UPD(ret)
					}else {
						ret = JDDKW.getOldValueOfKW_UPD(ret)
					}
				}
				String paraIV = myJDDParam.getINTERNALVALUEFor(name)

				if (paraIV) {
					ret = JDDIV.getInternalValueOf(paraIV,ret.toString())
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
		name = name?:myJDDHeader.list[0]
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
		String tableName = myJDDHeader.tableName
		Log.addTraceEND(CLASS_FORLOG,"getDBTableName",tableName)
		return tableName
	}













	/**
	 * Vérifie si une valeur est obsolète.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est obsolète, false sinon.
	 */
	def boolean isOBSOLETE(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isOBSOLETE",[name:name])

		String ret = myJDDParam.getPREREQUISFor(name)

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

		boolean result = myJDDParam.getFOREIGNKEYFor(name)!=''

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

		def sql = myJDDParam.getFOREIGNKEYFor(name).toString().split(/\*/)
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

		if (JDDKW.isSEQUENCEID(getStrData(fieldName))){

			String paraSeq =  myJDDParam.getSEQUENCEFor(fieldName)
			int val = SQL.getLastSequence(paraSeq)+delta
			myJDDData.setValueOf(fieldName, val,casDeTest,casDeTestNum)
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



	/******************************************
	 * 
	 * EXTENSION DES FONCTIONS DE JDDHeaders
	 * 
	 *****************************************/

	def List <String> getHeaderList() {
		return myJDDHeader.getList()
	}








	/******************************************
	 * 
	 * EXTENSION DES FONCTIONS DE JDDXpath
	 * 
	 *****************************************/

	/**
	 * Retrieves the XPath corresponding to a given name.
	 *
	 * @param name - The name for which the XPath is desired.
	 * @return String - Returns the XPath associated with the provided name.
	 *
	 * @note : The dynamic XPath is calculated during the creation of the TO
	 * 
	 */
	def String getXPath(String name) {
		return myJDDXpath.getXPath(name)
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	def addXpath(Map <String,String> map) {
		myJDDXpath.addFromMap(map)
	}





	/**
	 * Ajoute un nouvel élément à l'IHMTO.
	 *
	 * @param tab Nom de l'onglet.
	 * @param nom Nom du nouvel élément.
	 * @param xpath Xpath du nouvel élément.
	 */
	/*
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
	 */


	/**
	 * Ajoute une nouvelle colonne au JDD.
	 *
	 * @param name Nom de la nouvelle colonne.
	 */
	/*
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
	 */


} // end of class
