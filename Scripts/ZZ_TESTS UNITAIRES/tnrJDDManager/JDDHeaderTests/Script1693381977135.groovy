

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrCommon.ExcelUtils
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDHeader
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * public int getSize()
 * public List <String> getList()
 * public String getTableName()
 * public String add(String name) {
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_NAME = 'tnrJDDManager.JDDHeader'


Workbook  book = ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')
List headersListTest = ['ID_JML', 'ST_DES', 'ST_INA', 'NU_IV']
JDDHeader myJDDHeader = new JDDHeader(sheet)


Log.addAssert(CLASS_NAME,"JDDHeader.getSize()",4,myJDDHeader.getSize())
Log.addAssert(CLASS_NAME,"JDDHeader.getList()",headersListTest,myJDDHeader.getList())
Log.addAssert(CLASS_NAME,"JDDHeader.tableName",'JMLTABLE',myJDDHeader.tableName)

myJDDHeader.add('ID_ADDED')
Log.addAssert(CLASS_NAME,"JDDHeader.add('ID_ADDED')",['ID_JML', 'ST_DES', 'ST_INA', 'NU_IV','ID_ADDED'],myJDDHeader.getList())




