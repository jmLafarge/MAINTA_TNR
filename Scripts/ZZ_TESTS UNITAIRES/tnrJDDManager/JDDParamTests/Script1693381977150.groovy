import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.lang.reflect.Method

import tnrLog.Log
import tnrJDDManager.JDDHeader
import tnrJDDManager.JDDParam

/**
 * TESTS UNITAIRES
 * 
 * private boolean isParamAllowed(String name)
 * def List<String> getParamListAllowed()
 * def Map<String, String> getAllLOCATOR()
 * def Map<String, String> getAllPREREQUIS()
 * def String getParamFor(String param, String name)
 * def String getPREREQUISFor(String name)
 * def String getFOREIGNKEYFor(String name)
 * def String getSEQUENCEFor(String name)
 * def String getLOCATORFor(String name)
 * def String getINTERNALVALUEFor(String name)
 * def boolean isRADIO(String name)
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrJDDManager.JDDParam'

Workbook  book = tnrCommon.ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')

Map <String, Map<String ,String>> paramsMapTest = [
	'PREREQUIS':['ID_JML':'RO.CAL*001*ID_CODCAL', 'ST_DES':'', 'ST_INA':'', 'NU_IV':''], 
	'FOREIGNKEY':['ID_JML':'', 'ST_DES':'ST_DES*GES*ID_CODGES', 'ST_INA':'', 'NU_IV':''], 
	'SEQUENCE':['ID_JML':'', 'ST_DES':'', 'ST_INA':'JML_ID', 'NU_IV':''], 
	'INTERNALVALUE':['ID_JML':'', 'ST_DES':'', 'ST_INA':'', 'NU_IV':'MAT.NU_TYP'],
	'LOCATOR':['ID_JML':'input', 'ST_DES':'radio', 'ST_INA':'checkbox', 'NU_IV':'']]
	

Map <String, Map<String ,String>> paramsMapTest2 = [
	'PREREQUIS':['ID_JML':'RO.CAL*001*ID_CODCAL', 'ST_DES':'', 'ST_INA':'', 'NU_IV':''],
	'FOREIGNKEY':['ID_JML':'', 'ST_DES':'ST_DES*GES*ID_CODGES', 'ST_INA':'', 'NU_IV':''],
	'LOCATOR':['ID_JML':'input', 'ST_DES':'radio', 'ST_INA':'checkbox', 'NU_IV':'']]



JDDHeader myJDDHeader = new JDDHeader(sheet)

JDDParam myJDDParam = new JDDParam(sheet,myJDDHeader,'CAS_DE_TEST')


Log.addAssert(CLASS_FOR_LOG,"myJDDParam.paramsMap",paramsMapTest,myJDDParam.paramsMap)



Method method = JDDParam.class.getDeclaredMethod("isParamAllowed", String.class);
method.setAccessible(true);
Log.addAssert(CLASS_FOR_LOG,"private myJDDParam.isParamAllowed('LOCATOR')",true,(boolean) method.invoke(myJDDParam, 'LOCATOR'))
Log.addAssert(CLASS_FOR_LOG,"private myJDDParam.isParamAllowed('NOTALLOWEDPARA')",false,(boolean) method.invoke(myJDDParam, 'NOTALLOWEDPARA'))



Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getParamListAllowed()",['PREREQUIS','FOREIGNKEY','LOCATOR','SEQUENCE','INTERNALVALUE'],myJDDParam.getParamListAllowed())


Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getAllLOCATOR()",paramsMapTest['LOCATOR'],myJDDParam.getAllLOCATOR())


Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getAllPREREQUIS()",paramsMapTest['PREREQUIS'],myJDDParam.getAllPREREQUIS())



Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getParamFor('PREREQUIS','ID_JML')",paramsMapTest['PREREQUIS']['ID_JML'],myJDDParam.getParamFor('PREREQUIS','ID_JML'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getParamFor('FOREIGNKEY','ST_DES')",paramsMapTest['FOREIGNKEY']['ST_DES'],myJDDParam.getParamFor('FOREIGNKEY','ST_DES'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getParamFor('SEQUENCE','ST_INA')",paramsMapTest['SEQUENCE']['ST_INA'],myJDDParam.getParamFor('SEQUENCE','ST_INA'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getParamFor('INTERNALVALUE','NU_IV')",paramsMapTest['INTERNALVALUE']['NU_IV'],myJDDParam.getParamFor('INTERNALVALUE','NU_IV'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getParamFor('LOCATOR','ST_INA')",paramsMapTest['LOCATOR']['ST_INA'],myJDDParam.getParamFor('LOCATOR','ST_INA'))



Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getPREREQUISFor('ID_JML')",paramsMapTest['PREREQUIS']['ID_JML'],myJDDParam.getPREREQUISFor('ID_JML'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getFOREIGNKEYFor('ST_DES')",paramsMapTest['FOREIGNKEY']['ST_DES'],myJDDParam.getFOREIGNKEYFor('ST_DES'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getSEQUENCEFor('ST_INA')",paramsMapTest['SEQUENCE']['ST_INA'],myJDDParam.getSEQUENCEFor('ST_INA'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getLOCATORFor('ST_INA')",paramsMapTest['LOCATOR']['ST_INA'],myJDDParam.getLOCATORFor('ST_INA'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getINTERNALVALUEFor('NU_IV')",paramsMapTest['INTERNALVALUE']['NU_IV'],myJDDParam.getINTERNALVALUEFor('NU_IV'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getPREREQUISFor('ID_UNK')",null,myJDDParam.getPREREQUISFor('ID_UNK'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.getPREREQUISFor(null)",null,myJDDParam.getPREREQUISFor(null))




Log.addAssert(CLASS_FOR_LOG,"myJDDParam.isRADIO('ST_DES')",true,myJDDParam.isRADIO('ST_DES'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.isRADIO('ID_JML')",false,myJDDParam.isRADIO('ID_JML'))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.isRADIO(null)",false,myJDDParam.isRADIO(null))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.isRADIO('')",false,myJDDParam.isRADIO(''))
Log.addAssert(CLASS_FOR_LOG,"myJDDParam.isRADIO('UNK')",false,myJDDParam.isRADIO('UNK'))


Sheet sheet2 = book.getSheet('002')

JDDHeader myJDDHeader2 = new JDDHeader(sheet2)
JDDParam myJDDParam2 = new JDDParam(sheet2,myJDDHeader2,'CAS_DE_TEST')

Log.addAssert(CLASS_FOR_LOG,"JDDParam2.paramsMap",paramsMapTest2,myJDDParam2.paramsMap)

Log.addAssert(CLASS_FOR_LOG,"JDDParam2.getPREREQUISFor('ID_JML')",paramsMapTest2['PREREQUIS']['ID_JML'],myJDDParam2.getPREREQUISFor('ID_JML'))
Log.addAssert(CLASS_FOR_LOG,"JDDParam2.getINTERNALVALUEFor('NU_IV')",'',myJDDParam2.getINTERNALVALUEFor('NU_IV'))

