package tnrJDDManager

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCommon.FileUtils
import tnrCommon.InfoPARA
import tnrCommon.TNRPropertiesReader
import tnrLog.Log
import tnrPREJDDManager.PREJDDFileMapper
import tnrSqlManager.InfoDB
import tnrSqlManager.SQL


@CompileStatic
class JDDFileGenerator {


	static String trameJDD = TNRPropertiesReader.getMyProperty('TNR_PATH') + File.separator + TNRPropertiesReader.getMyProperty('TRAME_JDD_FILENAME')
	static String tramePREJDD = TNRPropertiesReader.getMyProperty('TNR_PATH') + File.separator + TNRPropertiesReader.getMyProperty('TRAME_PREJDD_FILENAME')
	static String auteur = TNRPropertiesReader.getMyProperty('AUTEUR')

	private static CellStyle styleChamp
	private static CellStyle styleChampIHM
	private static CellStyle  stylePara
	private static CellStyle  styleCdt






	static add(String table, String modObj, String fct='001',List listRubriquesIHM = []) {

		XSSFWorkbook JDDbook
		XSSFWorkbook PREJDDbook
		Log.addINFO('')
		if (!JDDFileMapper.getFullnameFromModObj(modObj)) {
			Log.addINFO("Création du fichier JDD pour $modObj")
			String fullName = createJDDFileByCopy(table,modObj)
			JDDbook = tnrCommon.ExcelUtils.open(fullName)
			JDDFileMapper.add(modObj, fullName)
		}else {
			Log.addINFO("Le fichier JDD pour $modObj existe déjà : " + JDDFileMapper.getFullnameFromModObj(modObj))
			JDDbook = tnrCommon.ExcelUtils.open(JDDFileMapper.getFullnameFromModObj(modObj))
		}

		String msg=addJDDSheet(JDDbook, table, modObj,fct,listRubriquesIHM)

		addParaFromInfoPARA(JDDbook.getSheet(fct),msg)

		if (msg) {

			addInfoVersion(JDDbook,msg)

			OutputStream JDDfileOut = new FileOutputStream(JDDFileMapper.getFullnameFromModObj(modObj))
			JDDbook.write(JDDfileOut)
		}


		Log.addINFO('')
		if (!PREJDDFileMapper.getFullnameFromModObj(modObj)) {
			Log.addINFO("Création du fichier PREJDD pour $modObj")
			String fullName = createPREJDDFileByCopy(table,modObj)
			PREJDDbook = tnrCommon.ExcelUtils.open(fullName)
			PREJDDFileMapper.add(modObj, fullName)
		}else {
			Log.addINFO("Le fichier PREJDD pour $modObj existe déjà : " + PREJDDFileMapper.getFullnameFromModObj(modObj))
			PREJDDbook = tnrCommon.ExcelUtils.open(PREJDDFileMapper.getFullnameFromModObj(modObj))
		}

		msg=addPREJDDSheet(PREJDDbook, table, modObj,fct)

		if (msg) {
			addInfoVersion(PREJDDbook,msg)

			OutputStream PREJDDfileOut = new FileOutputStream(PREJDDFileMapper.getFullnameFromModObj(modObj))
			PREJDDbook.write(PREJDDfileOut)
		}
	}





	private static addParaFromInfoPARA(Sheet shFCT,def mes) {

		Log.addINFO("Ajout des paramètres dans l'onglet "+shFCT.getSheetName())

		Row row = shFCT.getRow(0)

		for (int i = 1; i < row.getLastCellNum(); i++) {

			String cval = tnrCommon.ExcelUtils.getCellValue(row.getCell(i))

			if (cval=='') {
				break
			}

			if (InfoPARA.paraMap.containsKey(cval)) {
				int icol = 2
				for (para in [
					'PREREQUIS',
					'FOREIGNKEY',
					'SEQUENCE',
					'LOCATOR'
				]) {

					Row rowPara = getRowOfPara(shFCT,para)
					if (rowPara!=null && InfoPARA.paraMap[cval][icol]) {

						if(tnrCommon.ExcelUtils.getCellValue(rowPara.getCell(i))==''){

							Log.addTrace("$cval : $para = " + InfoPARA.paraMap[cval][icol])
							tnrCommon.ExcelUtils.writeCell(rowPara,i, InfoPARA.paraMap[cval][icol])

							if (!mes) "Mise à jour paramètre"
						}else if(tnrCommon.ExcelUtils.getCellValue(rowPara.getCell(i))!=InfoPARA.paraMap[cval][icol]){

							Log.addDETAILWARNING("\t$cval : $para, valeur du JDD '"+tnrCommon.ExcelUtils.getCellValue(rowPara.getCell(i))+"' différente de InfoPARA '" + InfoPARA.paraMap[cval][icol] + "'")
						}
					}
					icol+=2
				}
			}
		}
	}




	private static Row getRowOfPara(Sheet sheet, String para) {

		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (tnrCommon.ExcelUtils.getCellValue(row.getCell(0))==para) {
				break
			}
			if (tnrCommon.ExcelUtils.getCellValue(row.getCell(0))=='CAS_DE_TEST') {
				row=null
				break
			}
		}
		return row
	}



	private static String addJDDSheet(XSSFWorkbook JDDbook , String table, String modObj, String fct,List listRubriquesIHM) {

		Sheet shFCT = JDDbook.getSheet(fct)

		Sheet shMODELE = JDDbook.getSheet('MODELE')

		if (!shMODELE) {
			Log.addErrorAndStop("L'onglet 'MODELE' n'existe pas !")
		}
		String msg=''


		int numColFct = 1

		if (!shFCT) {
			Log.addINFO("Création de l'onglet $fct pour la table $table")
			msg="Création de l'onglet $fct pour la table $table"
			JDDbook.cloneSheet(JDDbook.getSheetIndex('MODELE'))
			JDDbook.setSheetName(JDDbook.getNumberOfSheets()-1, fct)
			JDDbook.setSheetOrder(fct, JDDbook.getNumberOfSheets()-3)
			shFCT = JDDbook.getSheet(fct)

			table = table.toUpperCase()

			Sheet shJDDInfo = JDDbook.getSheet('Info')

			// sheet Info
			String numEcran = SQL.getNumEcran(table)

			Map lib = numEcran ? SQL.getLibelle(table, numEcran) : [:]

			Log.addINFO("Renseigner l'onglet Info")
			Row rowInfo = tnrCommon.ExcelUtils.getNextRow(shJDDInfo)

			def styleFct = shJDDInfo.getRow(0).getCell(0).getCellStyle()
			def styleTable = shJDDInfo.getRow(0).getCell(1).getCellStyle()

			tnrCommon.ExcelUtils.writeCell(rowInfo,0,fct,styleFct)
			tnrCommon.ExcelUtils.writeCell(rowInfo,1,table,styleTable)
			tnrCommon.ExcelUtils.writeCell(rowInfo,2,'',styleTable)
			tnrCommon.ExcelUtils.writeCell(rowInfo,3,numEcran,styleFct)
			tnrCommon.ExcelUtils.writeCell(rowInfo,4,'',styleFct)
			tnrCommon.ExcelUtils.writeCell(rowInfo,5,'',styleFct)
			//rowNumInfo++


			Log.addINFO("Renseigner l'onglet $fct")
			tnrCommon.ExcelUtils.writeCell(shFCT.getRow(0),0,table)

			setStyle(JDDbook,shMODELE)



			InfoDB.getDatasForTable(table).each{col,infos ->
				// Sheet FCT
				CellStyle stylePK = JDDbook.createCellStyle()
				def fontPK = JDDbook.createFont()
				fontPK.setColor(IndexedColors.RED.index)
				fontPK.setBold(true)
				stylePK.setFont(fontPK)

				if (InfoDB.isPK(table,col)) {
					tnrCommon.ExcelUtils.writeCell(shFCT.getRow(0),numColFct,col,stylePK)
				}else {
					tnrCommon.ExcelUtils.writeCell(shFCT.getRow(0),numColFct,col,styleChamp)
				}

				for (int i in 1..4) {
					tnrCommon.ExcelUtils.writeCell(shFCT.getRow(i),numColFct,null,stylePara)
				}
				tnrCommon.ExcelUtils.writeCell(shFCT.getRow(5),numColFct,null,styleCdt)
				numColFct++

				// Sheet Info
				rowInfo = tnrCommon.ExcelUtils.getNextRow(shJDDInfo)
				tnrCommon.ExcelUtils.writeCell(rowInfo,0,col)
				tnrCommon.ExcelUtils.writeCell(rowInfo,1,lib.getAt(col))
				String type = ''
				if (InfoDB.isVarchar(table,col)) {
					type = InfoDB.getDATA_TYPE(table, col)+'('+InfoDB.getDATA_MAXCHAR(table, col)+')'
				}else {
					type = InfoDB.getDATA_TYPE(table, col)
				}

				if (InfoDB.isPK(table,col)) {
					tnrCommon.ExcelUtils.writeCell(rowInfo,2,type,stylePK)
				}else {
					tnrCommon.ExcelUtils.writeCell(rowInfo,2,type)
				}
			}

			ajouterRubriqueIHM(shFCT, listRubriquesIHM)


		}else {

			setStyle(JDDbook,shMODELE)
			msg = ajouterRubriqueIHM(shFCT, listRubriquesIHM)

		}
		return msg
	}



	private static setStyle(XSSFWorkbook JDDbook,Sheet sh) {

		styleChamp = sh.getRow(0).getCell(1).getCellStyle()
		stylePara = sh.getRow(1).getCell(1).getCellStyle()
		styleCdt = sh.getRow(5).getCell(1).getCellStyle()

		styleChampIHM = JDDbook.createCellStyle()
		styleChampIHM.cloneStyleFrom(sh.getRow(0).getCell(1).getCellStyle())
		styleChampIHM.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		styleChampIHM.setFillForegroundColor(IndexedColors.PALE_BLUE.index)

	}



	private static String ajouterRubriqueIHM(Sheet shFCT, List listRubriquesIHM) {

		Log.addINFO("Ajouter les rubriques IHM")

		List headers = tnrCommon.ExcelUtils.loadRow(shFCT.getRow(0))
		String msg
		for (rub in listRubriquesIHM) {
			if (rub in headers) {  // si la rub existe déjà
				Log.addINFO("\t$rub existe déjà")
			}else {
				Log.addINFO("\tAjout de $rub")
				msg="Ajout des rubriques IHM pour l'onglet " + shFCT.getSheetName()
				int numColFct = tnrCommon.ExcelUtils.getLastColumnIndex(shFCT,0)
				tnrCommon.ExcelUtils.writeCell(shFCT.getRow(0),numColFct,rub,styleChampIHM)
				for (int i in 1..4) {
					tnrCommon.ExcelUtils.writeCell(shFCT.getRow(i),numColFct,null,stylePara)
				}
				tnrCommon.ExcelUtils.writeCell(shFCT.getRow(5),numColFct,null,styleCdt)
			}
		}

		return msg
	}



	private static String createJDDFileByCopy(String table, String modObj) {

		String dir = TNRPropertiesReader.getMyProperty('JDD_PATH')
		FileUtils.createFolderIfNotExist(dir)

		Path source = Paths.get(trameJDD)
		String fullName = dir + File.separator + "JDD.${modObj}.xlsx"
		Log.addINFO(fullName)
		Path target = Paths.get(fullName)
		Files.copy(source, target)
		return fullName
	}


	private static String createPREJDDFileByCopy(String table,String modObj) {

		String dir = TNRPropertiesReader.getMyProperty('PREJDD_PATH')
		FileUtils.createFolderIfNotExist(dir)

		Path source = Paths.get(tramePREJDD)
		String fullName = dir + File.separator + "PREJDD.${modObj}.xlsx"
		Log.addINFO(fullName)
		Path target = Paths.get(fullName)
		Files.copy(source, target)
		return fullName
	}



	private static String addPREJDDSheet(XSSFWorkbook JDDbook , String table, String modObj, String fct) {

		Sheet shFCT = JDDbook.getSheet(fct)
		String msg
		if (!shFCT) {
			Log.addINFO("Création de l'onglet $fct pour la table $table")
			msg="Création de l'onglet $fct pour la table $table"
			JDDbook.cloneSheet(JDDbook.getSheetIndex('MODELE'))
			JDDbook.setSheetName(JDDbook.getNumberOfSheets()-1, fct)
			JDDbook.setSheetOrder(fct, JDDbook.getNumberOfSheets()-2)
			shFCT = JDDbook.getSheet(fct)

			table = table.toUpperCase()

			Log.addINFO("Renseigner l'onglet $fct")

			tnrCommon.ExcelUtils.writeCell(shFCT.getRow(0),0,"CAS DE TEST ($table)")

			int numColFct = 1

			def stylePREJDDChamp = shFCT.getRow(0).getCell(1).getCellStyle()

			InfoDB.getDatasForTable(table).each { col,infos ->
				if (InfoDB.isPK(table,col)) {
					CellStyle stylePK = JDDbook.createCellStyle()
					def fontPK = JDDbook.createFont()
					fontPK.setColor(IndexedColors.RED.index)
					fontPK.setBold(true)
					stylePK.setFont(fontPK)
					tnrCommon.ExcelUtils.writeCell(shFCT.getRow(0),numColFct,col,stylePK)
				}else {
					tnrCommon.ExcelUtils.writeCell(shFCT.getRow(0),numColFct,col,stylePREJDDChamp)
				}
				numColFct++
			}

		}else {
			Log.addINFO("L'onglet $fct du PREJDD existe déjà ")
		}
		return msg
	}


	private static addInfoVersion(XSSFWorkbook book, String msg, String edition='', String version='' ) {

		if (msg) {
			CreationHelper createHelper = book.getCreationHelper()
			CellStyle cellStyle_date = book.createCellStyle()
			// Créez un nouveau style avec les 4 bordures fines noires
			def thinBlackBorderStyle = book.createCellStyle()
			thinBlackBorderStyle.setBorderTop(BorderStyle.THIN)
			thinBlackBorderStyle.setBorderBottom(BorderStyle.THIN)
			thinBlackBorderStyle.setBorderLeft(BorderStyle.THIN)
			thinBlackBorderStyle.setBorderRight(BorderStyle.THIN)
			thinBlackBorderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex())
			thinBlackBorderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex())
			thinBlackBorderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex())
			thinBlackBorderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex())

			cellStyle_date.cloneStyleFrom(thinBlackBorderStyle)
			cellStyle_date.setDataFormat( createHelper.createDataFormat().getFormat("dd/MM/yyyy"))

			Sheet shVersion = book.getSheet('Version')
			Log.addINFO("Renseigner l'onglet Version")

			Row row = tnrCommon.ExcelUtils.getNextRow(shVersion)

			tnrCommon.ExcelUtils.writeCell(row,0,new Date().format('dd/MM/yyyy'),cellStyle_date)
			tnrCommon.ExcelUtils.writeCell(row,1,auteur,thinBlackBorderStyle)
			tnrCommon.ExcelUtils.writeCell(row,2,msg,thinBlackBorderStyle)
			tnrCommon.ExcelUtils.writeCell(row,3,edition,thinBlackBorderStyle)
			tnrCommon.ExcelUtils.writeCell(row,4,version,thinBlackBorderStyle)
		}
	}


} // end of class
