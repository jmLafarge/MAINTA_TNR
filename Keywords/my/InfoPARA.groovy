package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.Log as MYLOG



public class InfoPARA {

	public static Map paraMap= [:]

	private static String fileName = ''
	private static XSSFWorkbook book
	private static Sheet shPara

	private static CellStyle whereStyle
	private static CellStyle paraStyle








	public static load() {

		fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOPARAFILENAME')
		MYLOG.addSubTITLE("Chargement de : " + fileName,'-',120,1)
		book = my.XLS.open(fileName)

		shPara = book.getSheet('PARA')

		whereStyle = book.createCellStyle()
		whereStyle.setWrapText(true)
		whereStyle.setVerticalAlignment(VerticalAlignment.TOP)

		paraStyle = book.createCellStyle()
		paraStyle.setVerticalAlignment(VerticalAlignment.TOP)


		Row row = shPara.getRow(0)
		if (row==null) {
			MYLOG.addDEBUG("Créer la premiere ligne ")
			row =shPara.createRow(0)
		}

		if (my.XLS.getCellValue(row.getCell(0))=='') my.XLS.writeCell(row, 0, 'NOM',)
		if (my.XLS.getCellValue(row.getCell(1))=='') my.XLS.writeCell(row, 1, 'NBBDD')
		if (my.XLS.getCellValue(row.getCell(2))=='') my.XLS.writeCell(row, 2, 'PREREQUIS')
		if (my.XLS.getCellValue(row.getCell(3))=='') my.XLS.writeCell(row, 3, 'PREREQUIS_JDD')
		if (my.XLS.getCellValue(row.getCell(4))=='') my.XLS.writeCell(row, 4, 'FOREIGNKEY')
		if (my.XLS.getCellValue(row.getCell(5))=='') my.XLS.writeCell(row, 5, 'FOREIGNKEY_JDD')
		if (my.XLS.getCellValue(row.getCell(6))=='') my.XLS.writeCell(row, 6, 'SEQUENCE')
		if (my.XLS.getCellValue(row.getCell(7))=='') my.XLS.writeCell(row, 7, 'SEQUENCE_JDD')
		if (my.XLS.getCellValue(row.getCell(8))=='') my.XLS.writeCell(row, 8, 'LOCATOR')
		if (my.XLS.getCellValue(row.getCell(9))=='') my.XLS.writeCell(row, 9, 'LOCATOR_JDD')

		Iterator<Row> rowIt = shPara.rowIterator()
		row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			paraMap.putAt(my.XLS.getCellValue(row.getCell(0)),my.XLS.loadRow2(row,0,10,null))
		}

		MYLOG.addDEBUG("paraMap.size= " + paraMap.size())

		//my.Tools.parseMap(paraMap)
	}




	public static update(my.JDD myJDD,String col,String fullName, String where) {

		int icol = 2
		for (para in ['PREREQUIS', 'FOREIGNKEY', 'SEQUENCE', 'LOCATOR']) {

			String valPara = myJDD.getParamForThisName(para, col)
			
			if (valPara) {
				
				MYLOG.addDEBUG("\t$para = $valPara")

				if (!paraMap.containsKey(col)) {

					paraMap[col]=[null]*10


					MYLOG.addDEBUG("\tCréation '$col' pour sheetname "+shPara.getSheetName())

					Row row = my.XLS.getNextRow(shPara)
					
					my.XLS.writeCell(row,0,col,paraStyle)
				}



				if (paraMap[col][icol]==null) {

					updatePara(para,col,icol,valPara,where)

					MYLOG.addDEBUG("\tTrouvé dans $where --> $fullName table : " + myJDD.getDBTableName())
					MYLOG.addDEBUG('\t' +paraMap[col][icol] +" ajouté dans $col")
					MYLOG.addDEBUG('\t' + paraMap[col][icol+1] +" ajouté dans $col")
				}else if (paraMap[col][icol]!=valPara) {
					MYLOG.addDETAILWARNING("\t$para pour '$col'($icol) : $valPara différent de la valeur enregistrée " + paraMap[col][icol])
				}else if(paraMap[col][icol+1].contains(where)) {
					MYLOG.addDEBUG("\t$para $valPara pour '$col' et $where existe déjà")
				}else {
					updatePara(para,col,icol,valPara,where)

					MYLOG.addDEBUG("\t$para $valPara pour '$col' existe déjà mais rajout de $where dans " + paraMap[col][icol+1])
				}
			}
			icol+=2
		}
	}








	private static updatePara(String para, String col, int icol,String valPara, String where) {

		//Agrandit la liste di besoin
		while (paraMap[col].size()<=icol+1) {
			paraMap[col].add(paraMap[col].size(),null)
		}

		if (!paraMap[col][icol]) {
			//add
			paraMap[col].set(icol,valPara)
			paraMap[col].set(icol+1,where)
		}else {
			//update
			paraMap[col].set(icol+1,paraMap[col][icol+1]+'\n'+where)
		}

		Iterator<Row> rowIt = shPara.rowIterator()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()

			if (my.XLS.getCellValue(row.getCell(0))==col) {
				if (my.XLS.getCellValue(row.getCell(icol))==valPara) {
					//MYLOG.addDETAIL("$para, ajout du JDD '$where' pour $col")
					MYLOG.addDETAIL("$col : ajout du JDD '$where'")
					my.XLS.writeCell(row, icol+1,paraMap[col][icol+1])
					break
				}else {
					//MYLOG.addDETAIL("Ajout du $para '$valPara' et JDD '$where' pour $col")
					MYLOG.addDETAIL("$col : ajout du $para '$valPara' et du JDD '$where'")
					my.XLS.writeCell(row, icol,valPara,paraStyle)
					my.XLS.writeCell(row, icol+1,paraMap[col][icol+1],whereStyle)
					break
				}
			}
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
		}
	}


	public static write(){
		MYLOG.addDEBUG('update PARA dans InfoPARA')
		OutputStream fileOut = new FileOutputStream(fileName)
		book.write(fileOut);
	}


}
