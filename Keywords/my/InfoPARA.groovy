package my

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.Log
import my.result.TNRResult
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


	public static load() {

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
			Log.addDEBUG("Créer la premiere ligne ")
			row =shPara.createRow(0)
		}

		if (XLS.getCellValue(row.getCell(0))=='') XLS.writeCell(row, 0, 'NOM',)
		if (XLS.getCellValue(row.getCell(1))=='') XLS.writeCell(row, 1, 'NBBDD')
		if (XLS.getCellValue(row.getCell(2))=='') XLS.writeCell(row, 2, 'PREREQUIS')
		if (XLS.getCellValue(row.getCell(3))=='') XLS.writeCell(row, 3, 'PREREQUIS_JDD')
		if (XLS.getCellValue(row.getCell(4))=='') XLS.writeCell(row, 4, 'FOREIGNKEY')
		if (XLS.getCellValue(row.getCell(5))=='') XLS.writeCell(row, 5, 'FOREIGNKEY_JDD')
		if (XLS.getCellValue(row.getCell(6))=='') XLS.writeCell(row, 6, 'SEQUENCE')
		if (XLS.getCellValue(row.getCell(7))=='') XLS.writeCell(row, 7, 'SEQUENCE_JDD')
		if (XLS.getCellValue(row.getCell(8))=='') XLS.writeCell(row, 8, 'LOCATOR')
		if (XLS.getCellValue(row.getCell(9))=='') XLS.writeCell(row, 9, 'LOCATOR_JDD')

		Iterator<Row> rowIt = shPara.rowIterator()
		row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			String name  = XLS.getCellValue(row.getCell(0))
			paraMap[name] = XLS.loadRow2(row,0,10,null)
		}

		Log.addDEBUG("paraMap.size= " + paraMap.size())

	}




	public static update(JDD myJDD,String col,String fullName, String where) {

		int icol = 2
		for (para in ['PREREQUIS', 'FOREIGNKEY', 'SEQUENCE', 'LOCATOR']) {

			String valPara = myJDD.getParamForThisName(para, col)

			if (valPara) {

				Log.addDEBUG("\t$para = $valPara")

				if (!paraMap.containsKey(col)) {

					paraMap[col]=[null]*10


					Log.addDEBUG("\tCréation '$col' pour sheetname "+shPara.getSheetName())

					Row row = XLS.getNextRow(shPara)

					XLS.writeCell(row,0,col,paraStyle)
				}
				
				String li_JDD = paraMap[col][icol+1]
				
				

				if (paraMap[col][icol]==null) {

					updatePara(para,col,icol,valPara,where)

					Log.addDEBUG("\tTrouvé dans $where --> $fullName table : " + myJDD.getDBTableName())
					Log.addDEBUG('\t' +paraMap[col][icol] +" ajouté dans $col")
					Log.addDEBUG('\t' + paraMap[col][icol+1] +" ajouté dans $col")
				}else if (paraMap[col][icol]!=valPara) {
					if (paraMap[col][icol]=='OBSOLETE') {
						Log.addDETAILWARNING("\t$para pour '$col'($icol) : $valPara remplacement de la valeur OBSOLETE ")
						updatePara(para,col,icol,valPara,where)
					}else {
						Log.addDETAILWARNING("\t$para pour '$col'($icol) : $valPara différent de la valeur enregistrée " + paraMap[col][icol])
					}
				}else if(li_JDD.contains(where)) {
					Log.addDEBUG("\t$para $valPara pour '$col' et $where existe déjà")
				}else {
					updatePara(para,col,icol,valPara,where)

					Log.addDEBUG("\t$para $valPara pour '$col' existe déjà mais rajout de $where dans " + paraMap[col][icol+1])
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
					//TNRResult.addDETAIL("$para, ajout du JDD '$where' pour $col")
					TNRResult.addDETAIL("$col : ajout du JDD '$where'")
					XLS.writeCell(row, icol+1,paraMap[col][icol+1])
					break
				}else {
					//TNRResult.addDETAIL("Ajout du $para '$valPara' et JDD '$where' pour $col")
					TNRResult.addDETAIL("$col : ajout du $para '$valPara' et du JDD '$where'")
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
		Log.addDEBUG('update PARA dans InfoPARA')
		OutputStream fileOut = new FileOutputStream(fileName)
		book.write(fileOut);
	}


}
