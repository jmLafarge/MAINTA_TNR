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

final String CLASS_NAME = 'tnrSqlManager.InfoDBLoader'

InfoDBLoader infoDB = new InfoDBLoader()


Log.addAssert(CLASS_NAME,"infoDB.datas['ABR']['ID_NUMABR']['ORDINAL_POSITION'] 1ere ligne",1,infoDB.datas['ABR']['ID_NUMABR']['ORDINAL_POSITION'])
Log.addAssert(CLASS_NAME,"infoDB.datas['INTER']['ID_CODINT']['DATA_TYPE']",'varchar',infoDB.datas['INTER']['ID_CODINT']['DATA_TYPE'])
Log.addAssert(CLASS_NAME,"infoDB.datas['ZONLIG_ID']['ID_SESSION']['CONSTRAINT_NAME'] Derniere ligne)",'NULL',infoDB.datas['ZONLIG_ID']['ID_SESSION']['CONSTRAINT_NAME'])

