import myJDDManager.PREJDDFiles
import my.Log

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

Log.addAssert("PREJDDFiles.getFullname('RO.ACT')",'TNR_PREJDD\\PREJDD.RO.ACT.xlsx',PREJDDFiles.getFullname('RO.ACT'))
Log.addAssert("PREJDDFiles.getFullname('ROACT')",null,PREJDDFiles.getFullname('ROACT'))
Log.addAssert("PREJDDFiles.getFullname('')",null,PREJDDFiles.getFullname(''))
Log.addAssert("PREJDDFiles.getFullname(null)",null,PREJDDFiles.getFullname(null))


