
import java.lang.reflect.Method

import tnrCheck.data.CheckType
import tnrLog.Log

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

/**
 *
 * TEST private static boolean isCasDeTestSUPorREC(String cdt)
 *
 */


Method method = CheckType.class.getDeclaredMethod("isCasDeTestSUPorREC", String.class);
method.setAccessible(true);

Log.addAssert("private isCasDeTestSUPorREC : controle true avec *.SUP.nn",true, method.invoke(CheckType, 'AA.BBB.001.SUP.01'))
Log.addAssert("private isCasDeTestSUPorREC : controle true avec *.REC.nn",true, method.invoke(CheckType, 'AA.BBB.001.REC.01'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.LEC.nn",false, method.invoke(CheckType, 'AA.BBB.001.LEC.01'))

Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.RECnn",false, method.invoke(CheckType, 'AA.BBB.001.REC01'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *RECnn",false, method.invoke(CheckType, 'AA.BBB.001REC.01'))

Log.addAssert("private isCasDeTestSUPorREC : controle false avec .REC.nn",false, method.invoke(CheckType, '.REC.01'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.REC.",false, method.invoke(CheckType, 'AA.BBB.001.REC.'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.REC.AA",false, method.invoke(CheckType, 'AA.BBB.001.REC.AA'))
