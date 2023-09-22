

import tnrCommon.Tools
import tnrLog.Log



/**
 * TESTS UNITAIRES
 * 
 * public static String addZero(int val, int n = 2) {
 * public static String formatStrStepID(String code,int stepID,int NBCAR_STEPID) {
 *
 * @author JM Lafarge
 * @version 1.0
 */


final String CLASS_FOR_LOG = 'package.Class'


Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(9)",'09',Tools.addZero(9))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(9,2)",'09',Tools.addZero(9,2))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(9,2)",'9',Tools.addZero(9,0))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(9,1)",'9',Tools.addZero(9,1))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(9,5)",'00009',Tools.addZero(9,5))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(777,0)",'777',Tools.addZero(777,0))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(777,1)",'777',Tools.addZero(777,1))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(777)",'777',Tools.addZero(777))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(777,3)",'777',Tools.addZero(777,3))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(777,4)",'0777',Tools.addZero(777,4))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(777,5)",'00777',Tools.addZero(777,5))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(0,0)",'0',Tools.addZero(0,0))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(0,1)",'0',Tools.addZero(0,1))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(0,2)",'00',Tools.addZero(0,2))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(-1,0)",'-1',Tools.addZero(-1,0))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(-1,1)",'-1',Tools.addZero(-1,1))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(-1,2)",'-1',Tools.addZero(-1,2))
Log.addAssert(CLASS_FOR_LOG,"Tools.addZero(-1,2)",'-1',Tools.addZero(-1,3))

Log.addAssert(CLASS_FOR_LOG,"Tools.formatStrStepID('JML',7,6)",'JML007',Tools.formatStrStepID('JML',7,6))
Log.addAssert(CLASS_FOR_LOG,"Tools.formatStrStepID('JML',7,2)",'JML7',Tools.formatStrStepID('JML',7,2))
Log.addAssert(CLASS_FOR_LOG,"Tools.formatStrStepID('JML',7,0)",'JML7',Tools.formatStrStepID('JML',7,0))
Log.addAssert(CLASS_FOR_LOG,"Tools.formatStrStepID('JML',7,-1)",'JML7',Tools.formatStrStepID('JML',7,-1))
Log.addAssert(CLASS_FOR_LOG,"Tools.formatStrStepID('',7,6)",'000007',Tools.formatStrStepID('',7,6))
Log.addAssert(CLASS_FOR_LOG,"Tools.formatStrStepID(null,7,6)",'000007',Tools.formatStrStepID(null,7,6))

