import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.lang.reflect.Method

import my.Log
import myJDDManager.JDDHeader
import myJDDManager.JDDParam

/**
 * UNIT TESTS
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
	'LOCATOR':['ID_JML':'input', 'ST_DES':'radio', 'ST_INA':'checkbox', 'NU_IV':'']]
	

Map <String, Map<String ,String>> paramsMapTest2 = [
	'PREREQUIS':['ID_JML':'RO.CAL*001*ID_CODCAL', 'ST_DES':'', 'ST_INA':'', 'NU_IV':''],
	'FOREIGNKEY':['ID_JML':'', 'ST_DES':'ST_DES*GES*ID_CODGES', 'ST_INA':'', 'NU_IV':''],
	'LOCATOR':['ID_JML':'input', 'ST_DES':'radio', 'ST_INA':'checkbox', 'NU_IV':'']]



JDDHeader myJDDHeader = new JDDHeader(sheet)

JDDParam myJDDParam = new JDDParam(sheet,myJDDHeader,'CAS_DE_TEST')


Log.addAssert("myJDDParam.paramsMap",paramsMapTest,myJDDParam.paramsMap)


Log.addAssert("myJDDParam.getParamListAllowed()",['PREREQUIS','FOREIGNKEY','LOCATOR','SEQUENCE','INTERNALVALUE'],myJDDParam.getParamListAllowed())

Log.addAssert("myJDDParam.getPREREQUISFor('ID_JML')",paramsMapTest['PREREQUIS']['ID_JML'],myJDDParam.getPREREQUISFor('ID_JML'))
Log.addAssert("myJDDParam.getFOREIGNKEYFor('ST_DES')",paramsMapTest['FOREIGNKEY']['ST_DES'],myJDDParam.getFOREIGNKEYFor('ST_DES'))
Log.addAssert("myJDDParam.getSEQUENCEFor('ST_INA')",paramsMapTest['SEQUENCE']['ST_INA'],myJDDParam.getSEQUENCEFor('ST_INA'))
Log.addAssert("myJDDParam.getINTERNALVALUEFor('NU_IV')",paramsMapTest['INTERNALVALUE']['NU_IV'],myJDDParam.getINTERNALVALUEFor('NU_IV'))
Log.addAssert("myJDDParam.getLOCATORFor('ST_INA')",paramsMapTest['LOCATOR']['ST_INA'],myJDDParam.getLOCATORFor('ST_INA'))


Log.addAssert("myJDDParam.getParamFor('PREREQUIS','ID_JML')",paramsMapTest['PREREQUIS']['ID_JML'],myJDDParam.getParamFor('PREREQUIS','ID_JML'))
Log.addAssert("myJDDParam.getParamFor('FOREIGNKEY','ST_DES')",paramsMapTest['FOREIGNKEY']['ST_DES'],myJDDParam.getParamFor('FOREIGNKEY','ST_DES'))
Log.addAssert("myJDDParam.getParamFor('SEQUENCE','ST_INA')",paramsMapTest['SEQUENCE']['ST_INA'],myJDDParam.getParamFor('SEQUENCE','ST_INA'))
Log.addAssert("myJDDParam.getParamFor('INTERNALVALUE','NU_IV')",paramsMapTest['INTERNALVALUE']['NU_IV'],myJDDParam.getParamFor('INTERNALVALUE','NU_IV'))
Log.addAssert("myJDDParam.getParamFor('LOCATOR','ST_INA')",paramsMapTest['LOCATOR']['ST_INA'],myJDDParam.getParamFor('LOCATOR','ST_INA'))


Log.addAssert("myJDDParam.getAllLOCATOR()",paramsMapTest['LOCATOR'],myJDDParam.getAllLOCATOR())
Log.addAssert("myJDDParam.getAllPREREQUIS()",paramsMapTest['PREREQUIS'],myJDDParam.getAllPREREQUIS())

Log.addAssert("myJDDParam.getPREREQUISFor('ID_UNK')",null,myJDDParam.getPREREQUISFor('ID_UNK'))

Log.addAssert("myJDDParam.getPREREQUISFor(null)",null,myJDDParam.getPREREQUISFor(null))

Log.addAssert("myJDDParam.getLOCATORFor('NU_IV')",'',myJDDParam.getLOCATORFor('NU_IV'))


Method method = JDDParam.class.getDeclaredMethod("isParamAllowed", String.class);
method.setAccessible(true);

Log.addAssert("private myJDDParam.isParamAllowed('LOCATOR')",true,(boolean) method.invoke(myJDDParam, 'LOCATOR'))
Log.addAssert("private myJDDParam.isParamAllowed('NOTALLOWEDPARA')",false,(boolean) method.invoke(myJDDParam, 'NOTALLOWEDPARA'))


Log.addAssert("myJDDParam.isRADIO('ST_DES')",true,myJDDParam.isRADIO('ST_DES'))
Log.addAssert("myJDDParam.isRADIO('ID_JML')",false,myJDDParam.isRADIO('ID_JML'))
Log.addAssert("myJDDParam.isRADIO(null)",false,myJDDParam.isRADIO(null))
Log.addAssert("myJDDParam.isRADIO('')",false,myJDDParam.isRADIO(''))
Log.addAssert("myJDDParam.isRADIO('UNK')",false,myJDDParam.isRADIO('UNK'))


Sheet sheet2 = book.getSheet('002')

JDDHeader myJDDHeader2 = new JDDHeader(sheet2)
JDDParam myJDDParam2 = new JDDParam(sheet2,myJDDHeader2,'CAS_DE_TEST')

Log.addAssert("JDDParam2.paramsMap",paramsMapTest2,myJDDParam2.paramsMap)

Log.addAssert("JDDParam2.getPREREQUISFor('ID_JML')",paramsMapTest2['PREREQUIS']['ID_JML'],myJDDParam2.getPREREQUISFor('ID_JML'))
Log.addAssert("JDDParam2.getINTERNALVALUEFor('NU_IV')",'',myJDDParam2.getINTERNALVALUEFor('NU_IV'))

