
import my.Log
import myCheck.CheckDoublonOnPK
import java.lang.reflect.Method

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */



/**
 *
 * TEST private static String concatPKVal(Map<String, Object> datas,List<String>PKList)
 *
 */

Method method = CheckDoublonOnPK.class.getDeclaredMethod("concatPKVal",  Map.class, List.class)
method.setAccessible(true)

Map<String, Object> datas

datas = ['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]
Log.addAssert("(private) concatPKVal Avec PKList vide" , '' ,  method.invoke(CheckDoublonOnPK,datas,[]))
Log.addAssert("(private) concatPKVal Avec PKList ['']" , '' ,  method.invoke(CheckDoublonOnPK,datas,['']))
Log.addAssert("(private) concatPKVal Avec PKList 1 valeur" , 'DESJMLCRE01' ,  method.invoke(CheckDoublonOnPK,datas,['ST_DES']))
Log.addAssert("(private) concatPKVal Avec PKList 2 valeurs" , 'JMLCRE01 - JMLCRE01_INA' ,  method.invoke(CheckDoublonOnPK,datas,['ID_JML','ST_INA']))
Log.addAssert("(private) concatPKVal Avec PKList 3 valeurs" , 'JMLCRE01 - DESJMLCRE01 - JMLCRE01_INA' ,  method.invoke(CheckDoublonOnPK,datas,['ID_JML','ST_DES','ST_INA']))

Log.addAssert("(private) concatPKVal Avec datas vide" , '' ,  method.invoke(CheckDoublonOnPK,[:],['ST_DES']))

datas = ['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]
Log.addAssert("(private) concatPKVal Avec \$SEQUENCEID" , '' ,  method.invoke(CheckDoublonOnPK,datas,['ID_JML','ST_INA']))

datas = ['ID_JML':'$ORDRE', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]
Log.addAssert("(private) concatPKVal Avec \$ORDRE" , '' ,  method.invoke(CheckDoublonOnPK,datas,['ID_JML','ST_INA']))

datas = ['ID_JML':'$UPD*OLDVAL*NEWVAL', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]
Log.addAssert("(private) concatPKVal Avec \$UPD" , 'OLDVAL - JMLCRE01_INA' ,  method.invoke(CheckDoublonOnPK,datas,['ID_JML','ST_INA']))

datas = ['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'$TBD', 'NU_IV':null]
Log.addAssert("(private) concatPKVal Avec \$TBD seul" , 'JMLCRE01 - $TBD' ,  method.invoke(CheckDoublonOnPK,datas,['ID_JML','ST_INA']))

datas = ['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'$TBD*DRAFT', 'NU_IV':null]
Log.addAssert("(private) concatPKVal Avec \$TBD" , 'JMLCRE01 - DRAFT' ,  method.invoke(CheckDoublonOnPK,datas,['ID_JML','ST_INA']))




/**
 *
 * TEST static boolean run(List<Map<String, Map<String, Object>>> datasList, List <String> PKList, String JDDFullName, String sheetName, boolean status)
 *
 */


List<Map<String, Map<String, Object>>> datasListTRUE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE02', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'DESJMLLEC11', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC12', 'ST_DES':'DESJMLLEC12', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'DESJMLLEC13', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]], 
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]], 
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'DESJMLMAJ02', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]
Log.addAssert("Pas de doublons status true",true,CheckDoublonOnPK.run(datasListTRUE, ['ID_JML','ST_DES'], 'fullname','sheetname', true))
Log.addAssert("Pas de doublons status false",false,CheckDoublonOnPK.run(datasListTRUE, ['ID_JML','ST_DES'], 'fullname','sheetname', false))


List<Map<String, Map<String, Object>>> datasListFALSE = [
	['AA.BBB.001.CRE.01':['ID_JML':'xxxxxxxx', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.01':['ID_JML':'yyyyyyyy', 'ST_DES':'zzzzzzzzzz', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE02', 'ST_DES':'ooooooooooo', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'xxxxxxxx', 'ST_DES':'oooooooooo', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC12', 'ST_DES':'DESJMLLEC12', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'xxxxxxxx', 'ST_DES':'oooooooooo', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]], 
	['AA.BBB.001.MAJ.01':['ID_JML':'xxxxxxxx', 'ST_DES':'oooooooooo', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]], 
	['AA.BBB.001.MAJ.01':['ID_JML':'yyyyyyyy', 'ST_DES':'zzzzzzzzzz', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'DESJMLMAJ02', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]
Log.addAssert("Plusieurs doublons",false,CheckDoublonOnPK.run(datasListFALSE, ['ID_JML','ST_DES'], 'fullname','sheetname', true))


List<Map<String, Map<String, Object>>> datasListTRUE_SEQUENCEID = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.02':['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'$SEQUENCEID', 'ST_DES':'xxxxxxxxxx', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'$SEQUENCEID', 'ST_DES':'xxxxxxxxxx', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'DESJMLLEC13', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]], 
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]], 
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'DESJMLMAJ02', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]
Log.addAssert("Plusieurs SEQUENCEID",true,CheckDoublonOnPK.run(datasListTRUE_SEQUENCEID, ['ID_JML','ST_DES'], 'fullname','sheetname', true))

List<Map<String, Map<String, Object>>> datasListTRUE_ORDRE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$ORDRE', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'xxxxxxxxxx', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'xxxxxxxxxx', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'DESJMLLEC13', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'DESJMLMAJ02', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]
Log.addAssert("Plusieurs ORDRE",true,CheckDoublonOnPK.run(datasListTRUE_ORDRE, ['ID_JML','ST_DES'], 'fullname','sheetname', true))




List<Map<String, Map<String, Object>>> datasListTRUE_UPD = [
	['AA.BBB.001.CRE.01':['ID_JML':'$UPD*xxxx*yyyy', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'yyyy', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE04', 'ST_DES':'DESJMLCRE04', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]]
]
Log.addAssert("Avec UPD OK",true,CheckDoublonOnPK.run(datasListTRUE_UPD, ['ID_JML'], 'fullname','sheetname', true))

List<Map<String, Map<String, Object>>> datasListFALSE_UPD = [
	['AA.BBB.001.CRE.01':['ID_JML':'$UPD*xxxx*yyyy', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE04', 'ST_DES':'DESJMLCRE04', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'xxxx', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]]
]
Log.addAssert("Avec UPD KO",false,CheckDoublonOnPK.run(datasListFALSE_UPD, ['ID_JML'], 'fullname','sheetname', true))



List<Map<String, Map<String, Object>>> datasListTRUE_TBD = [
	['AA.BBB.001.CRE.01':['ID_JML':'$TBD', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE02', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE04', 'ST_DES':'DESJMLCRE04', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]]
]
Log.addAssert("Avec TBD seul",true,CheckDoublonOnPK.run(datasListTRUE_TBD, ['ID_JML'], 'fullname','sheetname', true))


List<Map<String, Map<String, Object>>> datasListTRUE_TBD2 = [
	['AA.BBB.001.CRE.01':['ID_JML':'$TBD*xxxx', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE02', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE04', 'ST_DES':'DESJMLCRE04', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]]
]
Log.addAssert("Avec TBD*VAL sans doublons",true,CheckDoublonOnPK.run(datasListTRUE_TBD2, ['ID_JML'], 'fullname','sheetname', true))


List<Map<String, Map<String, Object>>> datasListFALSE_TBD = [
	['AA.BBB.001.CRE.01':['ID_JML':'$TBD*xxxx', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'xxxx', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE04', 'ST_DES':'DESJMLCRE04', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]]
]
Log.addAssert("Avec TBD*VAL avec doublons",false,CheckDoublonOnPK.run(datasListFALSE_TBD, ['ID_JML'], 'fullname','sheetname', true))



List<Map<String, Map<String, Object>>> datasListFALSE_TBD2 = [
	['AA.BBB.001.CRE.01':['ID_JML':'$TBD', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$TBD', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]]
]
Log.addAssert("Avec plusieurs TBD seul",false,CheckDoublonOnPK.run(datasListFALSE_TBD2, ['ID_JML'], 'fullname','sheetname', true))





