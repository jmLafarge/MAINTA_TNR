

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import my.Log
import myJDDManager.JDDHeaders

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

Workbook  book = my.XLS.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')

List headersListTest = ['ID_JML', 'ST_DES', 'ST_INA', 'NU_IV']

JDDHeaders JDDHeader = new JDDHeaders(sheet)



Log.addAssert("JDDHeader.getList()",headersListTest,JDDHeader.getList())
Log.addAssert("JDDHeader.tableName",'JMLTABLE',JDDHeader.tableName)
Log.addAssert("JDDHeader.getSize()",4,JDDHeader.getSize())


