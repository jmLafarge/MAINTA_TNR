package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook




public class InfoPARA {

	public static Map paraMap= [:]

	private static String fileName = ''
	private static XSSFWorkbook book
	private static Sheet shPara

	private static CellStyle whereStyle
	private static CellStyle paraStyle








	public static load() {

		this.fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOPARAFILENAME')
		my.Log.addSubTITLE("Chargement de : " + this.fileName,'-',120,1)
		this.book = my.XLS.open(this.fileName)

		this.shPara = this.book.getSheet('PARA')

		this.whereStyle = this.book.createCellStyle()
		this.whereStyle.setWrapText(true)
		this.whereStyle.setVerticalAlignment(VerticalAlignment.TOP)

		this.paraStyle = this.book.createCellStyle()
		this.paraStyle.setVerticalAlignment(VerticalAlignment.TOP)


		Row row = this.shPara.getRow(0)
		if (row==null) {
			my.Log.addDEBUG("\tCréer la premiere ligne ")
			row =this.shPara.createRow(0)
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

		Iterator<Row> rowIt = this.shPara.rowIterator()
		row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			this.paraMap.putAt(my.XLS.getCellValue(row.getCell(0)),my.XLS.loadRow2(row,0,10,null))
		}

		my.Log.addDEBUG("\tthis.paraMap.size= " + this.paraMap.size())

		//my.Tools.parseMap(this.paraMap)
	}




	public static update(my.JDD myJDD,String col,String fullName, String where) {

		int icol = 2
		for (para in ['PREREQUIS', 'FOREIGNKEY', 'SEQUENCE', 'LOCATOR']) {

			String valPara = myJDD.getParamForThisName(para, col)
			my.Log.addDEBUG("\tvalPara $valPara")
			if (valPara) {
				my.Log.addDEBUG("\t$para pour '$col' : $valPara")

				if (!this.paraMap.containsKey(col)) {

					this.paraMap[col]=[null]*10


					my.Log.addDEBUG("\tCréation '$col' pour sheetname "+this.shPara.getSheetName())

					Row row = my.XLS.getNextRow(this.shPara)
					
					my.XLS.writeCell(row,0,col,this.paraStyle)
				}



				if (this.paraMap[col][icol]==null) {

					this.updatePara(para,col,icol,valPara,where)

					my.Log.addDEBUG("\t\tTrouvé dans $where --> $fullName table : " + myJDD.getDBTableName())
					my.Log.addDEBUG("\t\t" + this.paraMap[col][icol] +" ajouté dans $col")
					my.Log.addDEBUG("\t\t" + this.paraMap[col][icol+1] +" ajouté dans $col")
				}else if (this.paraMap[col][icol]!=valPara) {
					my.Log.addDETAILWARNING("$para pour '$col'($icol) : $valPara différent de la valeur enregistrée " + this.paraMap[col][icol])
				}else if(this.paraMap[col][icol+1].contains(where)) {
					my.Log.addDEBUG("\t$para $valPara pour '$col' et $where existe déjà")
				}else {
					this.updatePara(para,col,icol,valPara,where)

					my.Log.addDEBUG("\t$para $valPara pour '$col' existe déjà mais rajout de $where dans " + this.paraMap[col][icol+1])
				}
			}
			icol+=2
		}
	}








	private static updatePara(String para, String col, int icol,String valPara, String where) {

		//Agrandit la liste di besoin
		while (this.paraMap[col].size()<=icol+1) {
			this.paraMap[col].add(this.paraMap[col].size(),null)
		}

		if (!this.paraMap[col][icol]) {
			//add
			this.paraMap[col].set(icol,valPara)
			this.paraMap[col].set(icol+1,where)
		}else {
			//update
			this.paraMap[col].set(icol+1,this.paraMap[col][icol+1]+'\n'+where)
		}

		Iterator<Row> rowIt = this.shPara.rowIterator()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()

			if (my.XLS.getCellValue(row.getCell(0))==col) {
				if (my.XLS.getCellValue(row.getCell(icol))==valPara) {
					my.Log.addDETAIL("\t$para, ajout du JDD '$where' pour $col")
					my.XLS.writeCell(row, icol+1,this.paraMap[col][icol+1])
					break
				}else {
					my.Log.addDETAIL("\tAjout du $para '$valPara' et JDD '$where' pour $col")
					my.XLS.writeCell(row, icol,valPara,this.paraStyle)
					my.XLS.writeCell(row, icol+1,this.paraMap[col][icol+1],this.whereStyle)
					break
				}
			}
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
		}
	}


	public static write(){
		my.Log.addDEBUG('update PARA dans InfoPARA')
		OutputStream fileOut = new FileOutputStream(this.fileName)
		this.book.write(fileOut);
	}


}
