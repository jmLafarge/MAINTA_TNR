package tnrCommon

import groovy.transform.CompileStatic

/**
 *
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

@CompileStatic
public class HtmlTableBuilder {


	private StringBuilder tableHtml
	private String cssTable =''


	HtmlTableBuilder(String css='') {
		tableHtml = new StringBuilder()
		if (css) {
			cssTable = " style='$css'"
		}

	}



	public void addColumn(String text) {
		def column = "<th>$text</th>"
		tableHtml.append(column)
	}


	public void addCell(String text, String css = null) {
		def cell = "<td"
		if (css) {
			cell += " style='$css'"
		}
		cell += ">$text</td>"
		tableHtml.append(cell)
	}


	public void startRow() {
		tableHtml.append("<tr>")
	}


	public void endRow() {
		tableHtml.append("</tr>")
	}
	
	public void nextRow() {
		tableHtml.append("</tr><tr>")
	}
	
	
	def addRow(List<List> rowDataList,String type = 'td') {
		def row = "<tr>"
		rowDataList.each { rowData ->
			def cellData = rowData[0]
			def cellCss = rowData[1]
			def cellHtml = "<$type"
			if (cellCss) {
				cellHtml += " style='${cellCss}'"
			}
			cellHtml += ">${cellData}</$type>"
			row += cellHtml
		}
		row += "</tr>"
		tableHtml.append(row)
	}

	
	public String build() {
		String html = """
        <table $cssTable>
            ${tableHtml}
        </table>
        """
		return html
	}
	
} //end of class


