

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrLog.Log
import tnrJDDManager.JDDHeader

/**
 * TESTS UNITAIRES
 * 
 * public int getSize()
 * public List <String> getList()
 * public String getTableName()
 *
 * @author JM Lafarge
 * @version 1.0
 */

Workbook  book = tnrCommon.ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')
List headersListTest = ['ID_JML', 'ST_DES', 'ST_INA', 'NU_IV']
JDDHeader myJDDHeader = new JDDHeader(sheet)


Log.addAssert("JDDHeader.getSize()",4,myJDDHeader.getSize())
Log.addAssert("JDDHeader.getList()",headersListTest,myJDDHeader.getList())
Log.addAssert("JDDHeader.tableName",'JMLTABLE',myJDDHeader.tableName)



