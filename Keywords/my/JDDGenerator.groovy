package my

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import internal.GlobalVariable
import my.Log as MYLOG
import my.InfoBDD as INFOBDD


class JDDGenerator {


	static String trameJDD = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEJDDFILENAME')
	static String tramePREJDD = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEPREJDDFILENAME')


	private static CellStyle styleChamp
	private static CellStyle styleChampIHM
	private static CellStyle  stylePara
	private static CellStyle  styleCdt






	static add(String table, String modObj, String fct='001',List listRubriquesIHM = []) {

		if (my.InfoPARA.paraMap.isEmpty()) {
			my.InfoPARA.load()
		}

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

		String msg=this.addJDDSheet(JDDbook, table, modObj,fct,listRubriquesIHM)

		this.addParaFromInfoPARA(JDDbook.getSheet(fct),msg)

		if (msg) {

			this.addInfoVersion(JDDbook,GlobalVariable.AUTEUR,msg)

			OutputStream JDDfileOut = new FileOutputStream(my.JDDFiles.getFullName(modObj))
			JDDbook.write(JDDfileOut)
		}


		MYLOG.addINFO('')
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





	private static addParaFromInfoPARA(Sheet shFCT,def mes) {

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
					if (rowPara!=null && my.InfoPARA.paraMap[cval][icol]) {

						if(my.XLS.getCellValue(rowPara.getCell(i))==''){

							MYLOG.addDEBUG("$cval : $para = " + my.InfoPARA.paraMap[cval][icol])
							my.XLS.writeCell(rowPara,i, my.InfoPARA.paraMap[cval][icol])

							if (!mes) "Mise à jour paramètre"
						}else if(my.XLS.getCellValue(rowPara.getCell(i))!=my.InfoPARA.paraMap[cval][icol]){

							MYLOG.addDETAILWARNING("\t$cval : $para, valeur du JDD '"+my.XLS.getCellValue(rowPara.getCell(i))+"' différente de InfoPARA '" + my.InfoPARA.paraMap[cval][icol] + "'")
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
			if (my.XLS.getCellValue(row.getCell(0))==para) {
				break
			}
			if (my.XLS.getCellValue(row.getCell(0))=='CAS_DE_TEST') {
				row=null
				break
			}
		}
		return row
	}



	private static String addJDDSheet(XSSFWorkbook JDDbook , String table, String modObj, String fct,List listRubriquesIHM) {

		Sheet shFCT = JDDbook.getSheet(fct)
		String msg=''


		int numColFct = 1

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
			def numEcran = my.SQL.getNumEcran(table)

			Map lib = numEcran ? my.SQL.getLibelle(table, numEcran) : [:]

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


			MYLOG.addDETAIL("Renseigner l'onglet $fct")
			my.XLS.writeCell(shFCT.getRow(0),0,table)

			this.setStyle(JDDbook,shFCT)



			INFOBDD.map[table].each{col,vlist ->
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
				String type = INFOBDD.map[table][col][2]+'('+INFOBDD.map[table][col][3]+')'
				if (INFOBDD.map[table][col][2]=='numeric') {
					type = 'numeric'
				}else if (INFOBDD.map[table][col][2]=='datetime') {
					type = 'datetime'
				}else if (INFOBDD.map[table][col][4]=='T_BOOLEEN') {
					type = 'boolean'
				}

				if (INFOBDD.map[table][col][5]!='NULL') {
					CellStyle stylePK = JDDbook.createCellStyle()
					def fontPK = JDDbook.createFont()
					fontPK.setColor(IndexedColors.RED.index)
					fontPK.setBold(true)
					stylePK.setFont(fontPK)

					my.XLS.writeCell(rowInfo,2,type,stylePK)
				}else {
					my.XLS.writeCell(rowInfo,2,type)
				}
			}

			this.ajouterRubriqueIHM(shFCT, listRubriquesIHM)


		}else {

			this.setStyle(JDDbook,shFCT)
			msg = this.ajouterRubriqueIHM(shFCT, listRubriquesIHM)

		}
		return msg
	}



	private static setStyle(XSSFWorkbook JDDbook,Sheet shFCT) {

		this.styleChamp = shFCT.getRow(0).getCell(1).getCellStyle()
		this.stylePara = shFCT.getRow(1).getCell(1).getCellStyle()
		this.styleCdt = shFCT.getRow(5).getCell(1).getCellStyle()

		this.styleChampIHM = JDDbook.createCellStyle()
		this.styleChampIHM.cloneStyleFrom(shFCT.getRow(0).getCell(1).getCellStyle())
		this.styleChampIHM.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.styleChampIHM.setFillForegroundColor(IndexedColors.PALE_BLUE.index)

	}



	private static String ajouterRubriqueIHM(Sheet shFCT, List listRubriquesIHM) {

		MYLOG.addDETAIL("Ajouter les rubriques IHM")

		List headers = my.XLS.loadRow(shFCT.getRow(0))
		String msg
		for (rub in listRubriquesIHM) {
			if (rub in headers) {  // si la rub existe déjà
				MYLOG.addDETAIL("\t$rub existe déjà")
			}else {
				MYLOG.addDETAIL("\tAjout de $rub")
				msg="Ajout des rubriques IHM pour l'onglet " + shFCT.getSheetName()
				int numColFct = my.XLS.getLastColumnIndex(shFCT,0)
				my.XLS.writeCell(shFCT.getRow(0),numColFct,rub,this.styleChampIHM)
				for (int i in 1..4) {
					my.XLS.writeCell(shFCT.getRow(i),numColFct,null,this.stylePara)
				}
				my.XLS.writeCell(shFCT.getRow(5),numColFct,null,this.styleCdt)
			}
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
		String msg
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

			INFOBDD.map[table].each{col,vlist ->
				my.XLS.writeCell(shFCT.getRow(0),numColFct,col,stylePREJDDChamp)
				numColFct++
			}

		}else {
			MYLOG.addDETAIL("L'onglet $fct du PREJDD existe déjà ")
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

			my.XLS.writeCell(row,0,new Date().format('dd/MM/yyyy'),cellStyle_date)
			my.XLS.writeCell(row,1,auteur,thinBlackBorderStyle)
			my.XLS.writeCell(row,2,msg,thinBlackBorderStyle)
			my.XLS.writeCell(row,3,edition,thinBlackBorderStyle)
			my.XLS.writeCell(row,4,version,thinBlackBorderStyle)
		}
	}


} // end of class
