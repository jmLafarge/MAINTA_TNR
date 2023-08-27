import tnrTC.TCFileMapper
import tnrLog.Log
import tnrCommon.Tools
import java.lang.reflect.Method

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

//Log.addAssert("PREJDDFiles.getFullnameFromModObj('RO.ACT')",'TNR_PREJDD\\PREJDD.RO.ACT.xlsx',PREJDDFileMapper.getFullnameFromModObj('RO.ACT'))

println TCFileMapper.tcFileMap

Method method = TCFileMapper.class.getDeclaredMethod("isTCNameExist", String.class)
method.setAccessible(true)

Log.addAssert("(private) Un message pour expliquer de test" , true ,  method.invoke(TCFileMapper, 'RT.MAT.001.LEC'))
Log.addAssert("(private) Un message pour expliquer de test" , false ,  method.invoke(TCFileMapper, 'UNK'))
Log.addAssert("(private) Un message pour expliquer de test" , false ,  method.invoke(TCFileMapper, ''))
//Log.addAssert("(private) Un message pour expliquer de test" , false ,  method.invoke(TCFileMapper, null))