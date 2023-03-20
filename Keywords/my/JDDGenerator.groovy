package my

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import internal.GlobalVariable



class JDDGenerator {


	static String trameJDD = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEJDDFILENAME')
	static String tramePREJDD = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEPREJDDFILENAME')

	static add(String table, String modObj, String fct='001',String msg='Initialisation') {

		XSSFWorkbook JDDbook
		XSSFWorkbook PREJDDbook
		my.Log.addINFO('')
		if (!my.JDDFiles.getFullName(modObj)) {
			my.Log.addINFO("Création du fichier JDD pour $modObj")
			String fullName = this.createJDDFileByCopy(table,modObj)
			JDDbook = my.XLS.open(fullName)
			my.JDDFiles.add(modObj, fullName)
		}else {
			my.Log.addINFO("Le fichier JDD pour $modObj existe déjà : " + my.JDDFiles.getFullName(modObj))
			JDDbook = my.XLS.open(my.JDDFiles.getFullName(modObj))
		}

		this.addJDDSheet(JDDbook, table, modObj,fct)

		this.addParaFromInfoBDD(JDDbook.getSheet(fct))

		this.addInfoVersion(JDDbook,GlobalVariable.AUTEUR,msg)

		OutputStream JDDfileOut = new FileOutputStream(my.JDDFiles.getFullName(modObj))
		JDDbook.write(JDDfileOut)



		if (!my.PREJDDFiles.getFullName(modObj)) {
			my.Log.addINFO("Création du fichier PREJDD pour $modObj")
			String fullName = this.createPREJDDFileByCopy(table,modObj)
			PREJDDbook = my.XLS.open(fullName)
			my.PREJDDFiles.add(modObj, fullName)
		}else {
			my.Log.addINFO("Le fichier PREJDD pour $modObj existe déjà : " + my.PREJDDFiles.getFullName(modObj))
			PREJDDbook = my.XLS.open(my.PREJDDFiles.getFullName(modObj))
		}
		this.addPREJDDSheet(PREJDDbook, table, modObj,fct)

		this.addInfoVersion(PREJDDbook,GlobalVariable.AUTEUR,msg)

		OutputStream PREJDDfileOut = new FileOutputStream(my.PREJDDFiles.getFullName(modObj))
		PREJDDbook.write(PREJDDfileOut)
	}





	private static addParaFromInfoBDD(Sheet shFCT) {

		my.Log.addDETAIL("Ajout des paramètres dans l'onglet "+shFCT.getSheetName())

		Row row = shFCT.getRow(0)

		for (int i = 1; i < row.getLastCellNum(); i++) {

			String cval = my.XLS.getCellValue(row.getCell(i))

			if (cval=='') {
				break
			}

			if (my.InfoBDD.paraMap.containsKey(cval)) {
				int icol = 2
				for (para in ['PREREQUIS', 'FOREIGNKEY', 'SEQUENCE', 'LOCATOR']) {

					Row rowPara = this.getRowOfPara(shFCT,para)
					if (rowPara!=null && my.InfoBDD.paraMap[cval][icol]!='') {
						my.XLS.writeCell(rowPara,i, my.InfoBDD.paraMap[cval][icol])
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
			if (my.XLS.getCellValue(row.getCell(0))==para) {
				break
			}
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				row=null
				break
			}
		}
		return row
	}


	private static addJDDSheet(XSSFWorkbook JDDbook , String table, String modObj, String fct) {

		Sheet shFCT = JDDbook.getSheet(fct)

		if (!shFCT) {
			my.Log.addDETAIL("Création de l'onglet $fct pour la table $table")
			JDDbook.cloneSheet(JDDbook.getSheetIndex('MODELE'))
			JDDbook.setSheetName(JDDbook.getNumberOfSheets()-1, fct)
			JDDbook.setSheetOrder(fct, JDDbook.getNumberOfSheets()-3)
			shFCT = JDDbook.getSheet(fct)

			table = table.toUpperCase()




			Sheet shJDDInfo = JDDbook.getSheet('Info')

			// sheet Info
			int numEcran = my.SQL.getNumEcran(table)
			Map lib = my.SQL.getLibelle(table, numEcran)

			my.Log.addDETAIL("Renseigner l'onglet Info")
			int rowNumInfo = my.XLS.getLastRow(shJDDInfo, 0)

			def styleFct = shJDDInfo.getRow(0).getCell(0).getCellStyle()
			def styleTable = shJDDInfo.getRow(0).getCell(1).getCellStyle()

			my.XLS.writeCell(shJDDInfo.getRow(rowNumInfo),0,fct,styleFct)
			my.XLS.writeCell(shJDDInfo.getRow(rowNumInfo),1,table,styleTable)
			my.XLS.writeCell(shJDDInfo.getRow(rowNumInfo),2,'')
			my.XLS.writeCell(shJDDInfo.getRow(rowNumInfo),3,numEcran,styleFct)
			my.XLS.writeCell(shJDDInfo.getRow(rowNumInfo),4,'',styleFct)
			my.XLS.writeCell(shJDDInfo.getRow(rowNumInfo),5,'',styleFct)
			rowNumInfo++


			int numColFct = 1
			my.XLS.writeCell(shFCT.getRow(0),0,table)

			my.Log.addDETAIL("Renseigner l'onglet $fct")

			def styleChamp = shFCT.getRow(0).getCell(1).getCellStyle()
			def stylePara = shFCT.getRow(1).getCell(1).getCellStyle()
			def styleCdt = shFCT.getRow(5).getCell(1).getCellStyle()

			//my.InfoBDD.colnameMap[table].each{
			my.InfoBDD.map[table].each{col,vlist ->
				// Sheet FCT
				my.XLS.writeCell(shFCT.getRow(0),numColFct,col,styleChamp)
				for (int i in 1..4) {
					my.XLS.writeCell(shFCT.getRow(i),numColFct,null,stylePara)
				}
				my.XLS.writeCell(shFCT.getRow(5),numColFct,null,styleCdt)
				numColFct++

				// Sheet Info
				Row row = shJDDInfo.getRow(rowNumInfo)
				if (row == null) shJDDInfo.createRow(rowNumInfo)
				my.XLS.writeCell(row,0,col)
				my.XLS.writeCell(row,1,lib.getAt(col))
				rowNumInfo++

			}
		}else {
			my.Log.addERROR("L'onglet $fct du JDD existe déjà ")
		}
	}





	private static String createJDDFileByCopy(String table, String modObj) {

		String dir = my.PropertiesReader.getMyProperty('JDD_PATH') + File.separator + modObj.split(/\./)[0]
		my.Tools.createDir(dir)

		Path source = Paths.get(this.trameJDD)
		String fullName = dir + File.separator + "JDD.${modObj}.xlsx"
		my.Log.addDETAIL(fullName)
		Path target = Paths.get(fullName)
		Files.copy(source, target)
		return fullName
	}


	private static String createPREJDDFileByCopy(String table,String modObj) {

		String dir = my.PropertiesReader.getMyProperty('PREJDD_PATH') + File.separator + modObj.split(/\./)[0]
		my.Tools.createDir(dir)

		Path source = Paths.get(this.tramePREJDD)
		String fullName = dir + File.separator + "PREJDD.${modObj}.xlsx"
		my.Log.addDETAIL(fullName)
		Path target = Paths.get(fullName)
		Files.copy(source, target)
		return fullName
	}



	private static addPREJDDSheet(XSSFWorkbook JDDbook , String table, String modObj, String fct) {

		Sheet shFCT = JDDbook.getSheet(fct)

		if (!shFCT) {
			my.Log.addDETAIL("Création de l'onglet $fct pour la table $table")
			JDDbook.cloneSheet(JDDbook.getSheetIndex('MODELE'))
			JDDbook.setSheetName(JDDbook.getNumberOfSheets()-1, fct)
			JDDbook.setSheetOrder(fct, JDDbook.getNumberOfSheets()-2)
			shFCT = JDDbook.getSheet(fct)

			table = table.toUpperCase()

			my.Log.addDETAIL("Renseigner l'onglet $fct")

			my.XLS.writeCell(shFCT.getRow(0),0,"CAS DE TEST ($table)")

			int numColFct = 1

			def stylePREJDDChamp = shFCT.getRow(0).getCell(1).getCellStyle()

			//my.InfoBDD.colnameMap[table].each{
			my.InfoBDD.map[table].each{col,vlist ->
				my.XLS.writeCell(shFCT.getRow(0),numColFct,col,stylePREJDDChamp)
				numColFct++
			}

		}
	}


	private static addInfoVersion(XSSFWorkbook book, String auteur, String objet, String edition='', String version='' ) {

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
		my.Log.addDETAIL("Renseigner l'onglet Version")
		int rowNumVersion = my.XLS.getLastRow(shVersion, 0)
		//shVersion.shiftRows(rowNumVersion, shVersion.lastRowNum, 1, true, true)
		def newRow = shVersion.createRow(rowNumVersion)
		my.XLS.writeCell(newRow,0,new Date(),cellStyle_date)
		my.XLS.writeCell(newRow,1,auteur,thinBlackBorderStyle)
		my.XLS.writeCell(newRow,2,objet,thinBlackBorderStyle)
		my.XLS.writeCell(newRow,3,edition,thinBlackBorderStyle)
		my.XLS.writeCell(newRow,4,version,thinBlackBorderStyle)
	}

} // end of class
