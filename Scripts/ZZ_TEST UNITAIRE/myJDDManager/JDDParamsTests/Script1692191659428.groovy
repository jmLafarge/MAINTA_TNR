import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.lang.reflect.Method

import my.Log
import myJDDManager.JDDHeaders
import myJDDManager.JDDParams

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



Log.addAssert('JDDParam.paramsMap',paramsMapTest,JDDParam.paramsMap)

Log.addAssert('JDDParam.getPREREQUISFor',paramsMapTest['PREREQUIS']['ID_JML'],JDDParam.getPREREQUISFor('ID_JML'))
Log.addAssert('JDDParam.getFOREIGNKEYFor',paramsMapTest['FOREIGNKEY']['ST_DES'],JDDParam.getFOREIGNKEYFor('ST_DES'))
Log.addAssert('JDDParam.getSEQUENCEFor',paramsMapTest['SEQUENCE']['ST_INA'],JDDParam.getSEQUENCEFor('ST_INA'))
Log.addAssert('JDDParam.getINTERNALVALUEFor',paramsMapTest['INTERNALVALUE']['NU_IV'],JDDParam.getINTERNALVALUEFor('NU_IV'))
Log.addAssert('JDDParam.getLOCATORFor',paramsMapTest['LOCATOR']['ST_INA'],JDDParam.getLOCATORFor('ST_INA'))

Log.addAssert('JDDParam.getPREREQUISFor',null,JDDParam.getPREREQUISFor('ID_UNK'))

Log.addAssert('JDDParam.getLOCATORFor','',JDDParam.getLOCATORFor('NU_IV'))



Method method = JDDParams.class.getDeclaredMethod("isParamAllowed", String.class);
method.setAccessible(true);

Log.addAssert('JDDParam.isParamAllowed',true,(boolean) method.invoke(JDDParam, 'LOCATOR'))
Log.addAssert('JDDParam.isParamAllowed',false,(boolean) method.invoke(JDDParam, 'NOTALLOWEDPARA'))





