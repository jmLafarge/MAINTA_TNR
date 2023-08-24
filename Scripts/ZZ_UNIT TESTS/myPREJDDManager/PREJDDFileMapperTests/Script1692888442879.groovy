import myPREJDDManager.PREJDDFileMapper
import my.Log

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

Log.addAssert("PREJDDFiles.getFullnameFromModObj('RO.ACT')",'TNR_PREJDD\\PREJDD.RO.ACT.xlsx',PREJDDFileMapper.getFullnameFromModObj('RO.ACT'))
Log.addAssert("PREJDDFiles.getFullnameFromModObj('ROACT')",null,PREJDDFileMapper.getFullnameFromModObj('ROACT'))
Log.addAssert("PREJDDFiles.getFullnameFromModObj('')",null,PREJDDFileMapper.getFullnameFromModObj(''))
Log.addAssert("PREJDDFiles.getFullnameFromModObj(null)",null,PREJDDFileMapper.getFullnameFromModObj(null))


