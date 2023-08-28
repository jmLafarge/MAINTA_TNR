package tnrJDDManager

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrLog.Log
import tnrSqlManager.SQL
import tnrTC.TCFileMapper
import tnrCommon.Tools

@CompileStatic
public class JDD {

	private final String CLASS_FOR_LOG = 'JDD'



	private final String TO_SHEET_NAME 				= 'IHMTO'
	private final String INTERNAL_VALUE_SHEET_NAME 	= 'INTERNALVALUE'
	private final String START_DATA_WORD			= 'CAS_DE_TEST'


	private final List SKIP_LIST_SHEETNAME		= [
		'Version',
		'Info',
		TO_SHEET_NAME,
		'MODELE',
		INTERNAL_VALUE_SHEET_NAME
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
	 * CONSTRUCTEUR : lit le JDD, si pas de paramètre, utilise la variable globale CAS_DE_TEST_EN_COURS pour définir le JDD à utiliser
	 * 
	 * @param JDDFullName 
	 * @param TCTabName
	 * @param casDeTest
	 * @param step
	 */
	JDD(String fullname = null, String tabName = null,String cdt = null,boolean step=true) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"JDD",[fullname:fullname,tabName:tabName,cdt:cdt,step:step])


		Log.addTrace("GlobalVariable.CAS_DE_TEST_PATTERN : "+GlobalVariable.CAS_DE_TEST_PATTERN)

		if(fullname == null) {
			def modObj = Tools.getMobObj(GlobalVariable.CAS_DE_TEST_PATTERN.toString())
			JDDFullName = JDDFileMapper.getFullnameFromModObj(modObj)
			TCSheetName = GlobalVariable.CAS_DE_TEST_PATTERN.toString().split('\\.')[2]
		}else {
			JDDFullName = fullname
			TCSheetName = tabName
			casDeTest = cdt
		}

		Log.addTrace("Lecture du JDD : " + JDDFullName)


		book = tnrCommon.ExcelUtils.open(JDDFullName)

		if (TCSheetName) {
			TCSheet = book.getSheet(TCSheetName)
			loadTCSheet(TCSheet)
		}


		// add INTERNALVALUE
		if (book.getSheet(INTERNAL_VALUE_SHEET_NAME) != null) {
			JDDIV.addAll(book.getSheet(INTERNAL_VALUE_SHEET_NAME))
		}

		Log.addTraceEND(CLASS_FOR_LOG,"JDD")
	}





	public void loadTCSheet(Sheet sheet) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"loadTCSheet",[sheet:sheet.getSheetName()])

		TCSheet = sheet

		myJDDHeader = new JDDHeader(sheet)

		myJDDParam = new JDDParam(sheet,myJDDHeader,START_DATA_WORD)

		myJDDData = new JDDData(sheet,myJDDHeader,START_DATA_WORD)

		myJDDXpath  = new JDDXpath()

		myJDDXpath.add(myJDDParam.getAllLOCATOR())

		///////////////////////////////////////////////////////////// est ce que cela ne doit pas etre dans JDDData ?

		String cdtPattern = casDeTest ? casDeTest : GlobalVariable.CAS_DE_TEST_PATTERN

		//Liste des cas de test qui répondent au pattern, sans doublons (les doublons sont des casDeTestNum)
		List <String> cdtli = myJDDData.getCdtsContainingSubstringWithoutDuplicates(cdtPattern)

		//supprimer de la liste les cas de test traités par un autre Test Case
		CDTList = cdtli.findAll { cdt ->
			cdt.startsWith(cdtPattern) && (!TCFileMapper.tcFileMap.containsKey(cdt) || cdt == cdtPattern)
		}

		if (CDTList.size()==0 && cdtPattern){
			Log.addINFO('Pas de cas de test défini pour '+ cdtPattern)
		}

		//casDeTest = casDeTest ? casDeTest : CDTList[0]

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////




		// add IHMTO
		TOSheet = book.getSheet(TO_SHEET_NAME)
		if (TOSheet) {
			myJDDIHMTO = new JDDIHMTO(TOSheet,TCSheetName)
			myJDDXpath.add(myJDDIHMTO.getXpaths())
		}else {
			Log.addTrace("Pas de d'onglet '$TO_SHEET_NAME'" )
		}

		Log.addTraceEND(CLASS_FOR_LOG,"loadTCSheet")

	}





	/**
	 * Compte le nombre de lignes dans le cas de test donné.
	 *
	 * @param cdt Le nom du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return Le nombre de lignes dans le cas de test.
	 */
	public int getNbrLigneCasDeTest(String cdt = casDeTest) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getNbrLigneCasDeTest",[cdt:cdt])

		int ret = myJDDData.getNbrLigneCasDeTest(cdt)

		Log.addTraceEND(CLASS_FOR_LOG,"getNbrLigneCasDeTest",ret)
		return ret
	}



	/**
	 * Définit le numéro du cas de test. Dans le cas il y a plusieurs lignes de tests pour un même cas de test ex:RO.ACT.003HAB.SRA.01
	 * Par défaut la valeur est à car la grande majorité des cas de test n'ont qu'une seule ligne
	 *  
	 * @param i Le numéro du cas de test.
	 */
	public void setCasDeTestNum(int i) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"setCasDeTestNum",[i:i])
		casDeTestNum = i
		Log.addTraceEND(CLASS_FOR_LOG,"setCasDeTestNum")
	}



	/**
	 * Définit le cas de test courant. Dans le cas où un même script execute plusieurs cas de test. 
	 * Ex RT.ART.001.CRE, ce script execute tous les cas de test de type RT.ART.001.CRE.nn
	 *
	 * @param cdt Le nom du cas de test.
	 */
	public void setCasDeTest(String cdt) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"setCasDeTest",[cdt:cdt])
		casDeTest = cdt
		Log.addTraceEND(CLASS_FOR_LOG,"setCasDeTest")
	}






	boolean isDataUPD(String name, def cdtnum=null) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isDataUPD",[name:name , cdtnum:cdtnum])
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
		Log.addTraceEND(CLASS_FOR_LOG,"isDataUPD",ret)
		return ret
	}





	/**
	 * Récupère la donnée correspondant au nom et au numéro de cas de test donnés.
	 *
	 * @param name Le nom de la donnée à récupérer.
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return La donnée correspondant au nom et au numéro de cas de test donnés, ou null si le numéro de cas de test ou le nom est invalide.
	 */
	public getData(String name, def cdtnum=null, boolean UPD = false) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getData",[name:name , cdtnum:cdtnum , UPD:UPD])
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
		Log.addTraceEND(CLASS_FOR_LOG,"getData",ret)
		return ret
	}







	/**
	 * Récupère la donnée correspondant au nom et au numéro de cas de test donnés en tant que chaîne de caractères.
	 *
	 * @param name Le nom de la donnée à récupérer (valeur par défaut : '')
	 * @param casDeTestNum Le numéro du cas de test (valeur par défaut : numéro de cas de test courant).
	 * @return La donnée correspondant au nom et au numéro de cas de test donnés, convertie en chaîne de caractères.
	 */
	public String getStrData(String name = null, def cdtnum =null , boolean UPD = false) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getStrData",[name:name,cdtnum:cdtnum , UPD:UPD])
		cdtnum = (cdtnum?:casDeTestNum) as int
		name = name?:myJDDHeader.list[0]
		String ret = getData(name, casDeTestNum,UPD).toString()
		Log.addTraceEND(CLASS_FOR_LOG,"getStrData",ret)
		return ret
	}






	/**
	 * Récupère le nom de la table de la base de données.
	 *
	 * @return Le nom de la table de la base de données.
	 */
	public String getDBTableName() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getDBTableName",[:])
		String tableName = myJDDHeader.tableName
		Log.addTraceEND(CLASS_FOR_LOG,"getDBTableName",tableName)
		return tableName
	}







	/**
	 * Vérifie si une valeur est obsolète.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est obsolète, false sinon.
	 */
	public boolean isOBSOLETE(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isOBSOLETE",[name:name])

		String ret = myJDDParam.getPREREQUISFor(name)

		boolean result = ret ? ret == 'OBSOLETE' : false

		Log.addTraceEND(CLASS_FOR_LOG,"isOBSOLETE",result)
		return result
	}





	/**
	 * Vérifie si une valeur est une clé étrangère.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est une clé étrangère, false sinon.
	 */
	public boolean isFK(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isFK",[name:name])

		boolean result = myJDDParam.getFOREIGNKEYFor(name)!=''

		Log.addTraceEND(CLASS_FOR_LOG,"isFK",result)
		return result
	}



	/**
	 * Construit une requête SQL pour une clé étrangère.
	 *
	 * @param name Le nom de la clé étrangère.
	 * @return La requête SQL construite.
	 */
	public String getSqlForForeignKey(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getSqlForForeignKey",[name:name])

		def sql = myJDDParam.getFOREIGNKEYFor(name).toString().split(/\*/)
		String query = "SELECT ${sql[0]} FROM ${sql[1]} WHERE ${sql[2]} = '" + getData(name) + "'"

		Log.addTraceEND(CLASS_FOR_LOG,"getSqlForForeignKey",query)
		return query
	}






	/**
	 * Remplace un SEQUENCID dans le JDD par une séquence de base de données.
	 *
	 * @param fieldName Le nom du champ dans lequel remplacer le SEQUENCID.
	 * @param delta Le décalage à appliquer à la séquence. Valeur par défaut : 0.
	 */
	public void replaceSEQUENCIDInJDD(String fieldName, int delta=0) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"replaceSEQUENCIDInJDD",[fieldName:fieldName,delta:delta])

		if (JDDKW.isSEQUENCEID(getStrData(fieldName))){

			String paraSeq =  myJDDParam.getSEQUENCEFor(fieldName)
			int val = SQL.getLastSequence(paraSeq)+delta
			myJDDData.setValueOf(fieldName, val,casDeTest,casDeTestNum)
		}

		Log.addTraceEND(CLASS_FOR_LOG,"replaceSEQUENCIDInJDD")
	}




	public boolean isSheetAvailable(String sheetName) {

		return !(sheetName in SKIP_LIST_SHEETNAME)
	}




	public XSSFWorkbook getBook() {
		return book
	}



	public String getJDDFullName() {
		return JDDFullName
	}


	public String getTCSheetName() {
		return JDDFullName
	}



	/**
	 * Ajoute un nouvel élément à l'IHMTO.
	 *
	 * @param tab Nom de l'onglet.
	 * @param nom Nom du nouvel élément.
	 * @param xpath Xpath du nouvel élément.
	 */
	/*
	 public void addIHMTO(String tab, String nom, String xpath) {
	 Log.addTraceBEGIN(CLASS_FOR_LOG,"addIHMTO",[tab:tab,nom:nom,xpath:xpath])
	 if (xpathTO[nom]) {
	 Log.addDETAILFAIL("IHMTO '${nom}' existe déjà")
	 } else {
	 Row newRow = tnrCommon.ExcelUtils.getNextRow(TOSheet)
	 tnrCommon.ExcelUtils.writeCell(newRow, 0, tab)
	 tnrCommon.ExcelUtils.writeCell(newRow, 1, nom)
	 tnrCommon.ExcelUtils.writeCell(newRow, 2, xpath)
	 OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
	 book.write(JDDfileOut)
	 xpathTO.put(nom, xpath)
	 }
	 Log.addTraceEND(CLASS_FOR_LOG,"addIHMTO")
	 }
	 */


	/**
	 * Ajoute une nouvelle colonne au JDD.
	 *
	 * @param name Nom de la nouvelle colonne.
	 */
	/*
	 public void addColumn(String name) {
	 Log.addTraceBEGIN(CLASS_FOR_LOG,"addColumn",[name:name])
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
	 int numColFct = tnrCommon.ExcelUtils.getLastColumnIndex(TCSheet, 0)
	 tnrCommon.ExcelUtils.writeCell(TCSheet.getRow(0), numColFct, name, styleChampIHM)
	 for (int i in 1..params.size()) {
	 tnrCommon.ExcelUtils.writeCell(TCSheet.getRow(i), numColFct, null, stylePara)
	 }
	 tnrCommon.ExcelUtils.writeCell(TCSheet.getRow(params.size() + 1), numColFct, null, styleCdt)
	 OutputStream JDDfileOut = new FileOutputStream(JDDFullName)
	 book.write(JDDfileOut)
	 headers.add(name)
	 }
	 Log.addTraceEND(CLASS_FOR_LOG,"addColumn")
	 }
	 */


} // Fin de class
