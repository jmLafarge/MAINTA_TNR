package my

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import internal.GlobalVariable
import my.Log as MYLOG


class JDDGenerator {


	static String trameJDD = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEJDDFILENAME')
	static String tramePREJDD = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEPREJDDFILENAME')

	static add(String table, String modObj, String fct='001') {
		
		if (my.InfoPARA.paraMap.isEmpty()) { my.InfoPARA.load() }

		XSSFWorkbook JDDbook
		XSSFWorkbook PREJDDbook
		MYLOG.addINFO('')
		if (!my.JDDFiles.getFullName(modObj)) {
			MYLOG.addINFO("Création du fichier JDD pour $modObj")
			String fullName = this.createJDDFileByCopy(table,modObj)
			JDDbook = my.XLS.open(fullName)
			my.JDDFiles.add(modObj, fullName)
		}else {
			MYLOG.addINFO("Le fichier JDD pour $modObj existe déjà : " + my.JDDFiles.getFullName(modObj))
			JDDbook = my.XLS.open(my.JDDFiles.getFullName(modObj))
		}

		String msg=this.addJDDSheet(JDDbook, table, modObj,fct)

		if (msg) {
			this.addParaFromInfoPARA(JDDbook.getSheet(fct))
			this.addInfoVersion(JDDbook,GlobalVariable.AUTEUR,msg)

			OutputStream JDDfileOut = new FileOutputStream(my.JDDFiles.getFullName(modObj))
			JDDbook.write(JDDfileOut)
		}



		if (!my.PREJDDFiles.getFullName(modObj)) {
			MYLOG.addINFO("Création du fichier PREJDD pour $modObj")
			String fullName = this.createPREJDDFileByCopy(table,modObj)
			PREJDDbook = my.XLS.open(fullName)
			my.PREJDDFiles.add(modObj, fullName)
		}else {
			MYLOG.addINFO("Le fichier PREJDD pour $modObj existe déjà : " + my.PREJDDFiles.getFullName(modObj))
			PREJDDbook = my.XLS.open(my.PREJDDFiles.getFullName(modObj))
		}

		msg=this.addPREJDDSheet(PREJDDbook, table, modObj,fct)

		if (msg) {
			this.addInfoVersion(PREJDDbook,GlobalVariable.AUTEUR,msg)

			OutputStream PREJDDfileOut = new FileOutputStream(my.PREJDDFiles.getFullName(modObj))
			PREJDDbook.write(PREJDDfileOut)
		}
	}





	private static addParaFromInfoPARA(Sheet shFCT) {

		MYLOG.addDETAIL("Ajout des paramètres dans l'onglet "+shFCT.getSheetName())

		Row row = shFCT.getRow(0)

		for (int i = 1; i < row.getLastCellNum(); i++) {

			String cval = my.XLS.getCellValue(row.getCell(i))

			if (cval=='') {
				break
			}

			if (my.InfoPARA.paraMap.containsKey(cval)) {
				int icol = 2
				for (para in ['PREREQUIS', 'FOREIGNKEY', 'SEQUENCE', 'LOCATOR']) {

					Row rowPara = this.getRowOfPara(shFCT,para)
					if (rowPara!=null && my.InfoPARA.paraMap[cval][icol]!='') {
						my.XLS.writeCell(rowPara,i, my.InfoPARA.paraMap[cval][icol])
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


	private static String addJDDSheet(XSSFWorkbook JDDbook , String table, String modObj, String fct) {

		Sheet shFCT = JDDbook.getSheet(fct)
		String msg=''
		if (!shFCT) {
			MYLOG.addDETAIL("Création de l'onglet $fct pour la table $table")
			msg="Création de l'onglet $fct pour la table $table"
			JDDbook.cloneSheet(JDDbook.getSheetIndex('MODELE'))
			JDDbook.setSheetName(JDDbook.getNumberOfSheets()-1, fct)
			JDDbook.setSheetOrder(fct, JDDbook.getNumberOfSheets()-3)
			shFCT = JDDbook.getSheet(fct)

			table = table.toUpperCase()




			Sheet shJDDInfo = JDDbook.getSheet('Info')

			// sheet Info
			int numEcran = my.SQL.getNumEcran(table)
			Map lib = my.SQL.getLibelle(table, numEcran)

			MYLOG.addDETAIL("Renseigner l'onglet Info")
			Row rowInfo = my.XLS.getNextRow(shJDDInfo)

			def styleFct = shJDDInfo.getRow(0).getCell(0).getCellStyle()
			def styleTable = shJDDInfo.getRow(0).getCell(1).getCellStyle()

			my.XLS.writeCell(rowInfo,0,fct,styleFct)
			my.XLS.writeCell(rowInfo,1,table,styleTable)
			my.XLS.writeCell(rowInfo,2,'',styleTable)
			my.XLS.writeCell(rowInfo,3,numEcran,styleFct)
			my.XLS.writeCell(rowInfo,4,'',styleFct)
			my.XLS.writeCell(rowInfo,5,'',styleFct)
			//rowNumInfo++


			int numColFct = 1
			my.XLS.writeCell(shFCT.getRow(0),0,table)

			MYLOG.addDETAIL("Renseigner l'onglet $fct")

			def styleChamp = shFCT.getRow(0).getCell(1).getCellStyle()
			def stylePara = shFCT.getRow(1).getCell(1).getCellStyle()
			def styleCdt = shFCT.getRow(5).getCell(1).getCellStyle()

			my.InfoBDD.map[table].each{col,vlist ->
				// Sheet FCT
				my.XLS.writeCell(shFCT.getRow(0),numColFct,col,styleChamp)
				for (int i in 1..4) {
					my.XLS.writeCell(shFCT.getRow(i),numColFct,null,stylePara)
				}
				my.XLS.writeCell(shFCT.getRow(5),numColFct,null,styleCdt)
				numColFct++

				// Sheet Info
				rowInfo = my.XLS.getNextRow(shJDDInfo)
				my.XLS.writeCell(rowInfo,0,col)
				my.XLS.writeCell(rowInfo,1,lib.getAt(col))
				String type = my.InfoBDD.map[table][col][2]+'('+my.InfoBDD.map[table][col][3]+')'
				if (my.InfoBDD.map[table][col][2]=='numeric') {
					type = 'numeric'
				}else if (my.InfoBDD.map[table][col][2]=='datetime') {
					type = 'datetime'
				}else if (my.InfoBDD.map[table][col][4]=='T_BOOLEEN') {
					type = 'boolean'
				}
				
				if (my.InfoBDD.map[table][col][5]!='NULL') {
					CellStyle stylePK = JDDbook.createCellStyle()
					def fontPK = JDDbook.createFont()
					fontPK.setColor(IndexedColors.RED.index)
					fontPK.setBold(true)
					stylePK.setFont(fontPK)

					my.XLS.writeCell(rowInfo,2,type,stylePK)
				}else {
					my.XLS.writeCell(rowInfo,2,type)
				}
				//rowNumInfo++

			}
		}else {
			MYLOG.addERROR("L'onglet $fct du JDD existe déjà ")
		}
		return msg
	}





	private static String createJDDFileByCopy(String table, String modObj) {

		String dir = my.PropertiesReader.getMyProperty('JDD_PATH') + File.separator + modObj.split(/\./)[0]
		my.Tools.createDir(dir)

		Path source = Paths.get(this.trameJDD)
		String fullName = dir + File.separator + "JDD.${modObj}.xlsx"
		MYLOG.addDETAIL(fullName)
		Path target = Paths.get(fullName)
		Files.copy(source, target)
		return fullName
	}


	private static String createPREJDDFileByCopy(String table,String modObj) {

		String dir = my.PropertiesReader.getMyProperty('PREJDD_PATH') + File.separator + modObj.split(/\./)[0]
		my.Tools.createDir(dir)

		Path source = Paths.get(this.tramePREJDD)
		String fullName = dir + File.separator + "PREJDD.${modObj}.xlsx"
		MYLOG.addDETAIL(fullName)
		Path target = Paths.get(fullName)
		Files.copy(source, target)
		return fullName
	}



	private static String addPREJDDSheet(XSSFWorkbook JDDbook , String table, String modObj, String fct) {

		Sheet shFCT = JDDbook.getSheet(fct)
		String msg=''
		if (!shFCT) {
			MYLOG.addDETAIL("Création de l'onglet $fct pour la table $table")
			msg="Création de l'onglet $fct pour la table $table"
			JDDbook.cloneSheet(JDDbook.getSheetIndex('MODELE'))
			JDDbook.setSheetName(JDDbook.getNumberOfSheets()-1, fct)
			JDDbook.setSheetOrder(fct, JDDbook.getNumberOfSheets()-2)
			shFCT = JDDbook.getSheet(fct)

			table = table.toUpperCase()

			MYLOG.addDETAIL("Renseigner l'onglet $fct")

			my.XLS.writeCell(shFCT.getRow(0),0,"CAS DE TEST ($table)")

			int numColFct = 1

			def stylePREJDDChamp = shFCT.getRow(0).getCell(1).getCellStyle()

			my.InfoBDD.map[table].each{col,vlist ->
				my.XLS.writeCell(shFCT.getRow(0),numColFct,col,stylePREJDDChamp)
				numColFct++
			}

		}else {
			MYLOG.addERROR("L'onglet $fct du PREJDD existe déjà ")
		}
		return msg
	}


	private static addInfoVersion(XSSFWorkbook book, String auteur, String msg, String edition='', String version='' ) {

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
			MYLOG.addDETAIL("Renseigner l'onglet Version")
			
			Row row = my.XLS.getNextRow(shVersion)

			my.XLS.writeCell(row,0,new Date(),cellStyle_date)
			my.XLS.writeCell(row,1,auteur,thinBlackBorderStyle)
			my.XLS.writeCell(row,2,msg,thinBlackBorderStyle)
			my.XLS.writeCell(row,3,edition,thinBlackBorderStyle)
			my.XLS.writeCell(row,4,version,thinBlackBorderStyle)
		}
	}

} // end of class
