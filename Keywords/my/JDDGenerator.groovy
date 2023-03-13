package my

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook



class JDDGenerator {



	static createFile(String table, String mod, String obj, String fct='001') {

		table = table.toUpperCase()
		
		String fPREJDDName = this.createPREJDDFileByCopy(mod, obj)
		XSSFWorkbook PREJDDbook = my.XLS.open(fPREJDDName)
		Sheet shPREJDDFct = PREJDDbook.getSheet(fct)
		
		String fJDDName = this.createJDDFileByCopy(mod, obj)
		XSSFWorkbook JDDbook = my.XLS.open(fJDDName)
		Sheet shJDDInfo = JDDbook.getSheet('Info')
		Sheet shJDDFct = JDDbook.getSheet(fct)
		my.XLS.writeCell(shJDDInfo.getRow(2),1,table)
		
		my.XLS.writeCell(shJDDFct.getRow(0),0,table)

		int rowNumInfo = 2
		int numColFct = 1
		
		def styleChamp = shJDDFct.getRow(0).getCell(1).getCellStyle()
		def stylePara = shJDDFct.getRow(1).getCell(1).getCellStyle()
		def styleCdt = shJDDFct.getRow(5).getCell(1).getCellStyle()
		
		def stylePREJDDChamp = shPREJDDFct.getRow(0).getCell(1).getCellStyle()

		my.InfoBDD.colnameMap[table].each{

			Row row = shJDDInfo.getRow(rowNumInfo)

			if (row == null) shJDDInfo.createRow(rowNumInfo)
			my.XLS.writeCell(row,0,it)
			my.XLS.writeCell(shJDDFct.getRow(0),numColFct,it,styleChamp)
			
			for (int i in 1..4) {
				my.XLS.writeCell(shJDDFct.getRow(i),numColFct,null,stylePara)
			}
			my.XLS.writeCell(shJDDFct.getRow(5),numColFct,null,styleCdt)
			
			my.XLS.writeCell(shPREJDDFct.getRow(0),numColFct,it,stylePREJDDChamp)

			rowNumInfo++
			numColFct++
		}
		OutputStream JDDfileOut = new FileOutputStream(fJDDName)
		JDDbook.write(JDDfileOut)
		
		OutputStream PREJDDfileOut = new FileOutputStream(fPREJDDName)
		PREJDDbook.write(PREJDDfileOut)
	}

		

	private static String createJDDFileByCopy(String mod, String obj) {

		Path source = Paths.get(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEJDDFILENAME'))

		String fName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + 'JDD.'+ mod +'.' + obj +'.xlsx'

		Path target = Paths.get(fName)

		Files.copy(source, target)

		return 	fName
	}
	
	private static String createPREJDDFileByCopy(String mod, String obj) {
		
		Path source = Paths.get(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('TRAMEPREJDDFILENAME'))

		String fName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + 'PREJDD.'+ mod +'.' + obj +'.xlsx'

		Path target = Paths.get(fName)

		Files.copy(source, target)

		return 	fName
	}
} // end of class
