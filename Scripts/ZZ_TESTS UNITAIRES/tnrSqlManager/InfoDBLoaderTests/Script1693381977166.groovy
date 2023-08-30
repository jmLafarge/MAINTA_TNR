import tnrSqlManager.InfoDBLoader
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * infoDB.datas
 *
 * @author JM Lafarge
 * @version 1.0
 */

InfoDBLoader infoDB = new InfoDBLoader()


Log.addAssert("infoDB.datas['ABR']['ID_NUMABR']['ORDINAL_POSITION'] 1ere ligne",1,infoDB.datas['ABR']['ID_NUMABR']['ORDINAL_POSITION'])
Log.addAssert("infoDB.datas['INTER']['ID_CODINT']['DATA_TYPE']",'varchar',infoDB.datas['INTER']['ID_CODINT']['DATA_TYPE'])
Log.addAssert("infoDB.datas['ZONLIG_ID']['ID_SESSION']['CONSTRAINT_NAME'] Derniere ligne)",'NULL',infoDB.datas['ZONLIG_ID']['ID_SESSION']['CONSTRAINT_NAME'])

