package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.util.KeywordUtil

public class InfoBDD {

	private static XSSFWorkbook book
	private static String fileName = ''

	private static CellStyle whereStyle
	private static CellStyle paraStyle

	private static Map colnameMap= [:]
	private static Map PKMap= [:]
	private static Map paraMap= [:]

	private static final List HEADERS	 = ['TABLE_NAME', 'COLUMN_NAME', 'ORDINAL_POSITION', 'IS_NULLABLE', 'DATA_TYPE', 'MAXCHAR', 'DOMAIN_NAME', 'CONSTRAINT_NAME']

	private static List line = []

	private static Sheet shPara

	static load() {

		this.fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME')
		my.Log.addSubTITLE("Chargement de : " + this.fileName,'-',120,1)
		this.book = my.XLS.open(this.fileName)

		Sheet sheet = this.book.getSheet('INFO')


		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List headers = my.XLS.loadRow(row)
		my.Log.addINFO('Contrôle entête fichier',1)
		if (headers!=this.HEADERS) {
			my.Log.addERROR(this.fileName + ' Entête fichier différente de celle attendue :')
			my.Log.addDETAIL('Entête attendue : ' + this.HEADERS.join(' - '))
			my.Log.addDETAIL('Entête lue      : ' + headers.join(' - '))
			KeywordUtil.markErrorAndStop("Entête fichier ${this.fileName} différente de celle attendue")
		}

		//Row row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			this.line << my.XLS.loadRow(row,this.HEADERS.size())
		}
		this.colnameMap = this.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt(this.HEADERS.indexOf('COLUMN_NAME'))]}

		this.PKMap = this.line.groupBy { it[ 0 ] }.collectEntries { key, value -> [key, value*.getAt(this.HEADERS.indexOf('CONSTRAINT_NAME'))]}


		this.shPara = this.book.getSheet('PARA')
		rowIt = this.shPara.rowIterator()
		row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			this.paraMap.putAt(my.XLS.getCellValue(row.getCell(0)),my.XLS.loadRow(row,6))
		}

		this.whereStyle = this.book.createCellStyle()
		this.whereStyle.setWrapText(true)
		this.whereStyle.setVerticalAlignment(VerticalAlignment.TOP)

		this.paraStyle = this.book.createCellStyle()
		this.paraStyle.setVerticalAlignment(VerticalAlignment.TOP)
	}


	static updatePara(String col, int icol,String valPara, String where) {
		
		//Agrandit la liste di besoin
		while (this.paraMap[col].size()<icol) {
			this.paraMap[col].add(this.paraMap[col].size(),null)
		}

		if (!this.paraMap[col][icol]) {
			//add
			this.paraMap[col].add(icol,valPara)
			this.paraMap[col].add(icol+1,where)
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
					my.Log.addDETAIL("\tRajout de $where pour $col")
					my.XLS.writeCell(row, icol+1,this.paraMap[col][icol+1])
					break
				}else {
					my.Log.addDETAIL("\tAjout de $valPara et $where pour $col")
					my.XLS.writeCell(row, icol,valPara,this.paraStyle)
					my.XLS.writeCell(row, icol+1,where,this.whereStyle)
					break
				}
			}
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
		}
	}



	private static write(){
		my.Log.addDEBUG('update PARA dans InfoBDD')
		OutputStream fileOut = new FileOutputStream(this.fileName)
		this.book.write(fileOut);
	}



	static List getPK(String table) {

		List list=[]
		this.PKMap.getAt(table).eachWithIndex { v,i ->
			if (v!='NULL') {
				list.add(this.colnameMap.getAt(table)[i])
			}
		}
		return list
	}


	static boolean isTableExist(String table) {
		return this.colnameMap.containsKey(table)
	}


	private static String getDATA_TYPE(String table, String name) {
		String ret = null
		this.line.each {
			if (it[0]==table && it[1]==name) {
				ret = it[4]
			}
		}
		return ret
	}

	static castJDDVal(String table, String name, def val) {

		switch (this.getDATA_TYPE( table, name)) {

			case 'varchar':
				return val.toString()
				break
			default :
				return val
		}
	}
	
	
	static updateParaInfoBDD(String para,my.JDD myJDD,String col,int icol,String fullName, String where) {
		
		String valPara = myJDD.getParamForThisName(para, col)
		if (valPara) {
			my.Log.addDEBUG("\t$para pour '$col' : $valPara")
			if (this.paraMap.containsKey(col)) {
				my.Log.addDEBUG("\t'$col' trouvé dans InfoBDD")

				if (!this.paraMap[col][icol]) {
					
					this.updatePara(col,icol,valPara,where)
					
					my.Log.addDEBUG("\t\tTrouvé dans $where --> $fullName table : " + myJDD.getDBTableName())
					my.Log.addDEBUG("\t\t" + this.paraMap[col][icol] +" ajouté dans $col")
					my.Log.addDEBUG("\t\t" + this.paraMap[col][icol+1] +" ajouté dans $col")
				}else if (this.paraMap[col][icol]!=valPara) {
					my.Log.addDETAILWARNING("$para pour '$col'($icol) : $valPara différent de la valeur enregistrée " + this.paraMap[col][icol])
				}else if(this.paraMap[col][icol+1].contains(where)) {
					my.Log.addDEBUG("\t$para $valPara pour '$col' et $where existe déjà")
				}else {
					this.updatePara(col,icol,valPara,where)
					
					my.Log.addDEBUG("\t$para $valPara pour '$col' existe déjà mais rajout de $where dans " + this.paraMap[col][icol+1])
	
				}
			}
		}
		
		
	}
}// end of class
