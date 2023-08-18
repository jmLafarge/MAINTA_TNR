import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.lang.reflect.Method

import my.Log
import myJDDManager.JDDHeaders
import myJDDManager.JDDParams

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

Workbook  book = my.XLS.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')

Map <String, Map<String ,String>> paramsMapTest = [
	'PREREQUIS':['ID_JML':'RO.CAL*001*ID_CODCAL', 'ST_DES':'', 'ST_INA':'', 'NU_IV':''], 
	'FOREIGNKEY':['ID_JML':'', 'ST_DES':'ST_DES*GES*ID_CODGES', 'ST_INA':'', 'NU_IV':''], 
	'SEQUENCE':['ID_JML':'', 'ST_DES':'', 'ST_INA':'JML_ID', 'NU_IV':''], 
	'INTERNALVALUE':['ID_JML':'', 'ST_DES':'', 'ST_INA':'', 'NU_IV':'MAT.NU_TYP'],
	'LOCATOR':['ID_JML':'input', 'ST_DES':'input', 'ST_INA':'checkbox', 'NU_IV':'']]
	

JDDHeaders JDDHeader = new JDDHeaders(sheet)

JDDParams JDDParam = new JDDParams(sheet,JDDHeader,'CAS_DE_TEST')



Log.addAssert("JDDParam.paramsMap",paramsMapTest,JDDParam.paramsMap)

Log.addAssert("JDDParam.getPREREQUISFor('ID_JML')",paramsMapTest['PREREQUIS']['ID_JML'],JDDParam.getPREREQUISFor('ID_JML'))
Log.addAssert("JDDParam.getFOREIGNKEYFor('ST_DES')",paramsMapTest['FOREIGNKEY']['ST_DES'],JDDParam.getFOREIGNKEYFor('ST_DES'))
Log.addAssert("JDDParam.getSEQUENCEFor('ST_INA')",paramsMapTest['SEQUENCE']['ST_INA'],JDDParam.getSEQUENCEFor('ST_INA'))
Log.addAssert("JDDParam.getINTERNALVALUEFor('NU_IV')",paramsMapTest['INTERNALVALUE']['NU_IV'],JDDParam.getINTERNALVALUEFor('NU_IV'))
Log.addAssert("JDDParam.getLOCATORFor('ST_INA')",paramsMapTest['LOCATOR']['ST_INA'],JDDParam.getLOCATORFor('ST_INA'))

Log.addAssert("JDDParam.getAllLOCATOR()",paramsMapTest['LOCATOR'],JDDParam.getAllLOCATOR())

Log.addAssert("JDDParam.getPREREQUISFor('ID_UNK')",null,JDDParam.getPREREQUISFor('ID_UNK'))

Log.addAssert("JDDParam.getLOCATORFor('NU_IV')",'',JDDParam.getLOCATORFor('NU_IV'))




Method method = JDDParams.class.getDeclaredMethod("isParamAllowed", String.class);
method.setAccessible(true);

Log.addAssert("private JDDParam.isParamAllowed('LOCATOR')",true,(boolean) method.invoke(JDDParam, 'LOCATOR'))
Log.addAssert("private JDDParam.isParamAllowed('NOTALLOWEDPARA')",false,(boolean) method.invoke(JDDParam, 'NOTALLOWEDPARA'))


Log.addAssert("JDDParam.isLOCATOR('LOCATOR')",true,JDDParam.isLOCATOR('LOCATOR'))
Log.addAssert("JDDParam.isLOCATOR('')",false,JDDParam.isLOCATOR(''))
Log.addAssert("JDDParam.isLOCATOR(null)",false,JDDParam.isLOCATOR(null))
Log.addAssert("JDDParam.isLOCATOR('UNK')",false,JDDParam.isLOCATOR('UNK'))
Log.addAssert("JDDParam.isLOCATOR('SEQUENCE')",false,JDDParam.isLOCATOR('SEQUENCE'))


