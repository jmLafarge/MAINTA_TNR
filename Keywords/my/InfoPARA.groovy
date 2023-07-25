package my

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.Log
import my.JDD
import my.XLS

@CompileStatic
public class InfoPARA {

	public static Map <String,List> paraMap= [:]

	private static String fileName = ''
	private static XSSFWorkbook book
	private static Sheet shPara

	private static CellStyle whereStyle
	private static CellStyle paraStyle
	
	private static List headers = [
								'NOM',
								'NBBDD'
								]

	static {

		fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOPARAFILENAME')
		Log.addSubTITLE("Chargement de : " + fileName,'-',120,1)
		book = XLS.open(fileName)

		shPara = book.getSheet('PARA')

		whereStyle = book.createCellStyle()
		whereStyle.setWrapText(true)
		whereStyle.setVerticalAlignment(VerticalAlignment.TOP)

		paraStyle = book.createCellStyle()
		paraStyle.setVerticalAlignment(VerticalAlignment.TOP)


		Row row = shPara.getRow(0)
		if (row==null) {
			Log.addTrace("Créer la premiere ligne ")
			row =shPara.createRow(0)
		}
		
		def jdd = new my.JDD(JDDFiles.JDDfilemap.values()[0])
		List paramListAllowed = jdd.getParamListAllowed()
		
		for (String para : paramListAllowed) {
			headers.add(para)
			headers.add(para + '_JDD')
		}

		
		for (i in 0..headers.size()-1) {
			if (XLS.getCellValue(row.getCell(i))=='') XLS.writeCell(row, i, headers[i])
		}


		Iterator<Row> rowIt = shPara.rowIterator()
		row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			String name  = XLS.getCellValue(row.getCell(0))
			paraMap[name] = XLS.loadRow2(row,0,headers.size(),null)
		}

		Log.addTrace("paraMap.size= " + paraMap.size())
	}




	public static update(JDD myJDD,String col,String fullName, String where) {

		int icol = 2
		for (para in ['PREREQUIS', 'FOREIGNKEY', 'SEQUENCE', 'LOCATOR','INTERNALVALUE']) {

			String valPara = myJDD.getParamForThisName(para, col)

			if (valPara) {

				Log.addTrace("\t$para = $valPara")

				if (!paraMap.containsKey(col)) {

					paraMap[col]=[null]*headers.size()


					Log.addTrace("\tCréation '$col' pour sheetname "+shPara.getSheetName())

					Row row = XLS.getNextRow(shPara)

					XLS.writeCell(row,0,col,paraStyle)
				}

				String li_JDD = paraMap[col][icol+1]



				if (paraMap[col][icol]==null) {

					updatePara(para,col,icol,valPara,where)

					Log.addTrace("\tTrouvé dans $where --> $fullName table : " + myJDD.getDBTableName())
					Log.addTrace('\t' +paraMap[col][icol] +" ajouté dans $col")
					Log.addTrace('\t' + paraMap[col][icol+1] +" ajouté dans $col")
				}else if (paraMap[col][icol]!=valPara) {
					if (paraMap[col][icol]=='OBSOLETE') {
						Log.addDETAILWARNING("\t$para pour '$col'($icol) : $valPara remplacement de la valeur OBSOLETE ")
						updatePara(para,col,icol,valPara,where)
					}else {
						Log.addDETAILWARNING("$para pour '$col'($icol) : $valPara différent de la valeur enregistrée " + paraMap[col][icol])
					}
				}else if(li_JDD.contains(where)) {
					Log.addTrace("\t$para $valPara pour '$col' et $where existe déjà")
				}else {
					updatePara(para,col,icol,valPara,where)

					Log.addTrace("\t$para $valPara pour '$col' existe déjà mais rajout de $where dans " + paraMap[col][icol+1])
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
			paraMap[col].set(icol+1,paraMap[col][icol+1].toString()+'\n'+where)
		}

		Iterator<Row> rowIt = shPara.rowIterator()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()

			if (XLS.getCellValue(row.getCell(0))==col) {
				if (XLS.getCellValue(row.getCell(icol))==valPara) {
					Log.addTrace("$col : ajout du JDD '$where'")
					XLS.writeCell(row, icol+1,paraMap[col][icol+1])
					break
				}else {
					Log.addTrace("$col : ajout du $para '$valPara' et du JDD '$where'")
					XLS.writeCell(row, icol,valPara,paraStyle)
					XLS.writeCell(row, icol+1,paraMap[col][icol+1],whereStyle)
					break
				}
			}
			if (XLS.getCellValue(row.getCell(0))=='') {
				break
			}
		}
	}


	public static write(){
		Log.addTrace('update PARA dans InfoPARA')
		OutputStream fileOut = new FileOutputStream(fileName)
		book.write(fileOut);
	}


}
