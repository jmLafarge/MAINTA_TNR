package my

import my.Log as MYLOG
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.util.KeywordUtil

public class InfoBDD {

	public static Map map = [:]      // Map de Map de List[TABLE_NAME] [COLUMN_NAME] [0]:ORDINAL_POSITION [1]:IS_NULLABLE [2]:DATA_TYPE [3]:MAXCHAR [4]:DOMAIN_NAME [5]:CONSTRAINT_NAME
	//public static Map paraMap= [:]


	private static XSSFWorkbook book
	private static String fileName = ''

	//private static CellStyle whereStyle
	//private static CellStyle paraStyle

	private static final List HEADERS	 = ['TABLE_NAME', 'COLUMN_NAME', 'ORDINAL_POSITION', 'IS_NULLABLE', 'DATA_TYPE', 'MAXCHAR', 'DOMAIN_NAME', 'CONSTRAINT_NAME']

	//private static Sheet shPara


	public static load() {

		this.fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME')
		MYLOG.addSubTITLE("Chargement de : " + this.fileName,'-',120,1)
		this.book = my.XLS.open(this.fileName)

		Sheet sheet = this.book.getSheet('INFO')


		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List headers = my.XLS.loadRow(row)
		MYLOG.addINFO('Contrôle entête fichier',1)
		if (headers!=this.HEADERS) {
			MYLOG.addERROR(this.fileName + ' Entête fichier différente de celle attendue :')
			MYLOG.addDETAIL('Entête attendue : ' + this.HEADERS.join(' - '))
			MYLOG.addDETAIL('Entête lue      : ' + headers.join(' - '))
			KeywordUtil.markErrorAndStop("Entête fichier ${this.fileName} différente de celle attendue")
		}

		//Row row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}

			List listxls = my.XLS.loadRow(row,this.HEADERS.size())

			if (!this.map[listxls[0]]) {
				this.map[listxls[0]] = [:]
			}
			this.map[listxls[0]][listxls[1]] = listxls.subList(2, 8)

		}


		/*		this.shPara = this.book.getSheet('PARA')
		 rowIt = this.shPara.rowIterator()
		 row = rowIt.next()
		 if (my.XLS.getCellValue(row.getCell(2))=='') my.XLS.writeCell(row, 2, 'PREREQUIS')
		 if (my.XLS.getCellValue(row.getCell(3))=='') my.XLS.writeCell(row, 3, 'PREREQUIS_JDD')
		 if (my.XLS.getCellValue(row.getCell(4))=='') my.XLS.writeCell(row, 4, 'FOREIGNKEY')
		 if (my.XLS.getCellValue(row.getCell(5))=='') my.XLS.writeCell(row, 5, 'FOREIGNKEY_JDD')
		 if (my.XLS.getCellValue(row.getCell(6))=='') my.XLS.writeCell(row, 6, 'SEQUENCE')
		 if (my.XLS.getCellValue(row.getCell(7))=='') my.XLS.writeCell(row, 7, 'SEQUENCE_JDD')
		 if (my.XLS.getCellValue(row.getCell(8))=='') my.XLS.writeCell(row, 8, 'LOCATOR')
		 if (my.XLS.getCellValue(row.getCell(9))=='') my.XLS.writeCell(row, 9, 'LOCATOR_JDD')
		 while(rowIt.hasNext()) {
		 row = rowIt.next()
		 if (my.XLS.getCellValue(row.getCell(0))=='') {
		 break
		 }
		 this.paraMap.putAt(my.XLS.getCellValue(row.getCell(0)),my.XLS.loadRow(row,10))
		 }
		 this.whereStyle = this.book.createCellStyle()
		 this.whereStyle.setWrapText(true)
		 this.whereStyle.setVerticalAlignment(VerticalAlignment.TOP)
		 this.paraStyle = this.book.createCellStyle()
		 this.paraStyle.setVerticalAlignment(VerticalAlignment.TOP)*/

	}










	/*
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
	 Row row = rowIt.next()
	 row = rowIt.next()
	 while(rowIt.hasNext()) {
	 row = rowIt.next()
	 if (my.XLS.getCellValue(row.getCell(0))==col) {
	 if (my.XLS.getCellValue(row.getCell(icol))==valPara) {
	 MYLOG.addDETAIL("\t$para, ajout du JDD '$where' pour $col")
	 my.XLS.writeCell(row, icol+1,this.paraMap[col][icol+1])
	 break
	 }else {
	 MYLOG.addDETAIL("\tAjout du $para '$valPara' et JDD '$where' pour $col")
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
	 */




	/*
	 public static write(){
	 MYLOG.addDEBUG('update PARA dans InfoBDD')
	 OutputStream fileOut = new FileOutputStream(this.fileName)
	 this.book.write(fileOut);
	 }
	 */

	public static List getPK(String table) {

		List list=[]
		this.map[table].each { k, li ->

			if (li[5]!='NULL') list.add(k)
		}
		return list
	}


	public static boolean isTableExist(String table) {
		//return this.colnameMap.containsKey(table)
		return this.map.containsKey(table)
	}


	private static String getDATA_TYPE(String table, String name) {

		return this.map[table][name][2]
	}


	public static castJDDVal(String table, String name, def val) {

		switch (this.getDATA_TYPE( table, name)) {

			case 'varchar':
				return val.toString()
				break
			default :
				return val
		}
	}

	/*
	 public static updateParaInfoBDD(my.JDD myJDD,String col,String fullName, String where) {
	 int icol = 2
	 for (para in ['PREREQUIS', 'FOREIGNKEY', 'SEQUENCE', 'LOCATOR']) {
	 String valPara = myJDD.getParamForThisName(para, col)
	 if (valPara) {
	 MYLOG.addDEBUG("\t$para pour '$col' : $valPara")
	 if (this.paraMap.containsKey(col)) {
	 MYLOG.addDEBUG("\t'$col' trouvé dans InfoBDD")
	 if (!this.paraMap[col][icol]) {
	 this.updatePara(para,col,icol,valPara,where)
	 MYLOG.addDEBUG("\t\tTrouvé dans $where --> $fullName table : " + myJDD.getDBTableName())
	 MYLOG.addDEBUG("\t\t" + this.paraMap[col][icol] +" ajouté dans $col")
	 MYLOG.addDEBUG("\t\t" + this.paraMap[col][icol+1] +" ajouté dans $col")
	 }else if (this.paraMap[col][icol]!=valPara) {
	 MYLOG.addDETAILWARNING("$para pour '$col'($icol) : $valPara différent de la valeur enregistrée " + this.paraMap[col][icol])
	 }else if(this.paraMap[col][icol+1].contains(where)) {
	 MYLOG.addDEBUG("\t$para $valPara pour '$col' et $where existe déjà")
	 }else {
	 this.updatePara(para,col,icol,valPara,where)
	 MYLOG.addDEBUG("\t$para $valPara pour '$col' existe déjà mais rajout de $where dans " + this.paraMap[col][icol+1])
	 }
	 }
	 }
	 icol+=2
	 }
	 }
	 */




}// end of class
