import tnrPREJDDManager.PREJDDFileMapper
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * static String getFullnameFromModObj(String modObj)
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrPREJDDManager.PREJDDFileMapper'

Log.addAssert(CLASS_FOR_LOG,"PREJDDFilesMapper.getFullnameFromModObj('RO.ACT')",'TNR_PREJDD\\PREJDD.RO.ACT.xlsx',PREJDDFileMapper.getFullnameFromModObj('RO.ACT'))
Log.addAssert(CLASS_FOR_LOG,"PREJDDFilesMapper.getFullnameFromModObj('ROACT')",null,PREJDDFileMapper.getFullnameFromModObj('ROACT'))
Log.addAssert(CLASS_FOR_LOG,"PREJDDFilesMapper.getFullnameFromModObj('')",null,PREJDDFileMapper.getFullnameFromModObj(''))
Log.addAssert(CLASS_FOR_LOG,"PREJDDFilesMapper.getFullnameFromModObj(null)",null,PREJDDFileMapper.getFullnameFromModObj(null))


