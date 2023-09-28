import java.lang.reflect.Method

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrCommon.ExcelUtils
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDHeader
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDParam
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * private boolean isParamAllowed(String name)
 * public List<String> getParamListAllowed()
 * public Map<String, String> getAllLOCATOR()
 * public Map<String, String> getAllPREREQUIS()
 * public String getParamFor(String param, String name)
 * public String getPREREQUISFor(String name)
 * public String getFOREIGNKEYFor(String name)
 * public String getSEQUENCEFor(String name)
 * public String getLOCATORFor(String name)
 * public String getINTERNALVALUEFor(String name)
 * public boolean isRADIO(String name)
 * public int getLOCATORIndex() {
 * public int getSize() {
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_NAME = 'tnrJDDManager.JDDParam'

Workbook  book = ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')

Map <String, Map<String ,String>> paramsMapTest = [
	'PREREQUIS':['ID_JML':'RO.CAL*001*ID_CODCAL', 'ST_DES':'', 'ST_INA':'', 'NU_IV':''], 
	'FOREIGNKEY':['ID_JML':'', 'ST_DES':'ST_DES*GES*ID_CODGES', 'ST_INA':'', 'NU_IV':''], 
	'SEQUENCE':['ID_JML':'EQUDOC_ID', 'ST_DES':'', 'ST_INA':'JML_ID', 'NU_IV':''], 
	'INTERNALVALUE':['ID_JML':'', 'ST_DES':'', 'ST_INA':'', 'NU_IV':'MAT.NU_TYP'],
	'LOCATOR':['ID_JML':'input', 'ST_DES':'radio', 'ST_INA':'checkbox', 'NU_IV':'']]
	

Map <String, Map<String ,String>> paramsMapTest2 = [
	'PREREQUIS':['ID_JML':'RO.CAL*001*ID_CODCAL', 'ST_DES':'', 'ST_INA':'', 'NU_IV':''],
	'FOREIGNKEY':['ID_JML':'', 'ST_DES':'ST_DES*GES*ID_CODGES', 'ST_INA':'', 'NU_IV':''],
	'LOCATOR':['ID_JML':'input', 'ST_DES':'radio', 'ST_INA':'checkbox', 'NU_IV':'']]



JDDHeader myJDDHeader = new JDDHeader(sheet)

JDDParam myJDDParam = new JDDParam(sheet,myJDDHeader)


Log.addAssert(CLASS_NAME,"myJDDParam.paramsMap",paramsMapTest,myJDDParam.paramsMap)



Method method = JDDParam.class.getDeclaredMethod("isParamAllowed", String.class);
method.setAccessible(true);
Log.addAssert(CLASS_NAME,"private myJDDParam.isParamAllowed('LOCATOR')",true,(boolean) method.invoke(myJDDParam, 'LOCATOR'))
Log.addAssert(CLASS_NAME,"private myJDDParam.isParamAllowed('NOTALLOWEDPARA')",false,(boolean) method.invoke(myJDDParam, 'NOTALLOWEDPARA'))



Log.addAssert(CLASS_NAME,"myJDDParam.getParamListAllowed()",['PREREQUIS','FOREIGNKEY','LOCATOR','SEQUENCE','INTERNALVALUE'],myJDDParam.getParamListAllowed())


Log.addAssert(CLASS_NAME,"myJDDParam.getAllLOCATOR()",paramsMapTest['LOCATOR'],myJDDParam.getAllLOCATOR())


Log.addAssert(CLASS_NAME,"myJDDParam.getAllPREREQUIS()",paramsMapTest['PREREQUIS'],myJDDParam.getAllPREREQUIS())



Log.addAssert(CLASS_NAME,"myJDDParam.getParamFor('PREREQUIS','ID_JML')",paramsMapTest['PREREQUIS']['ID_JML'],myJDDParam.getParamFor('PREREQUIS','ID_JML'))
Log.addAssert(CLASS_NAME,"myJDDParam.getParamFor('FOREIGNKEY','ST_DES')",paramsMapTest['FOREIGNKEY']['ST_DES'],myJDDParam.getParamFor('FOREIGNKEY','ST_DES'))
Log.addAssert(CLASS_NAME,"myJDDParam.getParamFor('SEQUENCE','ST_INA')",paramsMapTest['SEQUENCE']['ST_INA'],myJDDParam.getParamFor('SEQUENCE','ST_INA'))
Log.addAssert(CLASS_NAME,"myJDDParam.getParamFor('INTERNALVALUE','NU_IV')",paramsMapTest['INTERNALVALUE']['NU_IV'],myJDDParam.getParamFor('INTERNALVALUE','NU_IV'))
Log.addAssert(CLASS_NAME,"myJDDParam.getParamFor('LOCATOR','ST_INA')",paramsMapTest['LOCATOR']['ST_INA'],myJDDParam.getParamFor('LOCATOR','ST_INA'))



Log.addAssert(CLASS_NAME,"myJDDParam.getPREREQUISFor('ID_JML')",paramsMapTest['PREREQUIS']['ID_JML'],myJDDParam.getPREREQUISFor('ID_JML'))
Log.addAssert(CLASS_NAME,"myJDDParam.getFOREIGNKEYFor('ST_DES')",paramsMapTest['FOREIGNKEY']['ST_DES'],myJDDParam.getFOREIGNKEYFor('ST_DES'))
Log.addAssert(CLASS_NAME,"myJDDParam.getSEQUENCEFor('ST_INA')",paramsMapTest['SEQUENCE']['ST_INA'],myJDDParam.getSEQUENCEFor('ST_INA'))
Log.addAssert(CLASS_NAME,"myJDDParam.getLOCATORFor('ST_INA')",paramsMapTest['LOCATOR']['ST_INA'],myJDDParam.getLOCATORFor('ST_INA'))
Log.addAssert(CLASS_NAME,"myJDDParam.getINTERNALVALUEFor('NU_IV')",paramsMapTest['INTERNALVALUE']['NU_IV'],myJDDParam.getINTERNALVALUEFor('NU_IV'))
Log.addAssert(CLASS_NAME,"myJDDParam.getPREREQUISFor('ID_UNK')",null,myJDDParam.getPREREQUISFor('ID_UNK'))
Log.addAssert(CLASS_NAME,"myJDDParam.getPREREQUISFor(null)",null,myJDDParam.getPREREQUISFor(null))




Log.addAssert(CLASS_NAME,"myJDDParam.isRADIO('ST_DES')",true,myJDDParam.isRADIO('ST_DES'))
Log.addAssert(CLASS_NAME,"myJDDParam.isRADIO('ID_JML')",false,myJDDParam.isRADIO('ID_JML'))
Log.addAssert(CLASS_NAME,"myJDDParam.isRADIO(null)",false,myJDDParam.isRADIO(null))
Log.addAssert(CLASS_NAME,"myJDDParam.isRADIO('')",false,myJDDParam.isRADIO(''))
Log.addAssert(CLASS_NAME,"myJDDParam.isRADIO('UNK')",false,myJDDParam.isRADIO('UNK'))


Log.addAssert(CLASS_NAME,"myJDDParam.getLOCATORIndex()",4,myJDDParam.getLOCATORIndex())

Log.addAssert(CLASS_NAME,"myJDDParam.getSize()",5,myJDDParam.getSize())



Sheet sheet2 = book.getSheet('002')

JDDHeader myJDDHeader2 = new JDDHeader(sheet2)
JDDParam myJDDParam2 = new JDDParam(sheet2,myJDDHeader2)

Log.addAssert(CLASS_NAME,"JDDParam2.paramsMap",paramsMapTest2,myJDDParam2.paramsMap)

Log.addAssert(CLASS_NAME,"JDDParam2.getPREREQUISFor('ID_JML')",paramsMapTest2['PREREQUIS']['ID_JML'],myJDDParam2.getPREREQUISFor('ID_JML'))
Log.addAssert(CLASS_NAME,"JDDParam2.getINTERNALVALUEFor('NU_IV')",'',myJDDParam2.getINTERNALVALUEFor('NU_IV'))

