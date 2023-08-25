package tnrCommon

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrLog.Log

@CompileStatic
public class InfoPARA {

	public static Map <String,List> paraMap= [:]

	private static String fileName = ''
	private static XSSFWorkbook book
	private static Sheet shPara
	private static Sheet shKW

	private static CellStyle whereStyle
	private static CellStyle paraStyle

	private static List headersPara = ['NOM', 'NBBDD']
	private static List headersKW = ['JDD', 'CDT', 'NAME', 'KW']

	private static int numRowKW=0

	static {

		fileName = TNRPropertiesReader.getMyProperty('TNR_PATH') + File.separator + TNRPropertiesReader.getMyProperty('INFO_PARA_FILENAME')
		Log.addSubTITLE("Chargement de : " + fileName,'-',120)
		book = ExcelUtils.open(fileName)

		// Init shPARA
		shPara = book.getSheet('PARA')

		if (shPara) {
			book.removeSheetAt(book.getSheetIndex(shPara))
		}
		shPara = book.createSheet('PARA')
		Row row =shPara.createRow(0)

		whereStyle = book.createCellStyle()
		whereStyle.setWrapText(true)
		whereStyle.setVerticalAlignment(VerticalAlignment.TOP)

		paraStyle = book.createCellStyle()
		paraStyle.setVerticalAlignment(VerticalAlignment.TOP)


		def jdd = new tnrJDDManager.JDD(JDDFileMapper.JDDfilemap.values()[0])
		List paramListAllowed = jdd.myJDDParam.getParamListAllowed()

		for (String para : paramListAllowed) {
			headersPara.add(para)
			headersPara.add(para + '_JDD')
		}


		for (i in 0..headersPara.size()-1) {
			if (ExcelUtils.getCellValue(row.getCell(i))=='') {
				ExcelUtils.writeCell(row, i, headersPara[i])
			}
		}


		Iterator<Row> rowIt = shPara.rowIterator()
		row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (ExcelUtils.getCellValue(row.getCell(0))=='') {
				break
			}
			String name  = ExcelUtils.getCellValue(row.getCell(0))
			paraMap[name] = ExcelUtils.loadRow2(row,0,headersPara.size(),null)
		}

		Log.addTrace("paraMap.size= " + paraMap.size())

		// Init shKW
		shKW = book.getSheet('KW')

		if (shKW) {
			book.removeSheetAt(book.getSheetIndex(shKW))
		}
		shKW = book.createSheet('KW')
		row =shKW.createRow(0)

		headersKW.eachWithIndex { name,idx ->  ExcelUtils.writeCell(row, idx, name)}

	}

	public static writeLineKW(String fullName,String cdt, String name, String kw) {
		Row row = shKW.createRow(++numRowKW)
		ExcelUtils.writeCell(row,0,fullName,paraStyle)
		ExcelUtils.writeCell(row,1,cdt,paraStyle)
		ExcelUtils.writeCell(row,2,name,paraStyle)
		ExcelUtils.writeCell(row,3,kw,paraStyle)
	}




	public static updateShPara(JDD myJDD,String col,String fullName, String where) {

		int icol = 2

		List paramListAllowed = myJDD.myJDDParam.getParamListAllowed()

		for (String para in paramListAllowed) {

			String valPara = myJDD.myJDDParam.getParamFor(para, col)

			if (valPara) {

				Log.addTrace("\t$para = $valPara")

				if (!paraMap.containsKey(col)) {

					paraMap[col]=[null]*headersPara.size()


					Log.addTrace("\tCréation '$col' pour sheetname "+shPara.getSheetName())

					Row row = ExcelUtils.getNextRow(shPara)

					ExcelUtils.writeCell(row,0,col,paraStyle)
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

			if (ExcelUtils.getCellValue(row.getCell(0))==col) {
				if (ExcelUtils.getCellValue(row.getCell(icol))==valPara) {
					Log.addTrace("$col : ajout du JDD '$where'")
					ExcelUtils.writeCell(row, icol+1,paraMap[col][icol+1])
					break
				}else {
					Log.addTrace("$col : ajout du $para '$valPara' et du JDD '$where'")
					ExcelUtils.writeCell(row, icol,valPara,paraStyle)
					ExcelUtils.writeCell(row, icol+1,paraMap[col][icol+1],whereStyle)
					break
				}
			}
			if (ExcelUtils.getCellValue(row.getCell(0))=='') {
				break
			}
		}
	}


	public static write(){
		println paraMap
		Log.addTrace('update PARA dans InfoPARA')
		OutputStream fileOut = new FileOutputStream(fileName)
		book.write(fileOut);
	}


}
