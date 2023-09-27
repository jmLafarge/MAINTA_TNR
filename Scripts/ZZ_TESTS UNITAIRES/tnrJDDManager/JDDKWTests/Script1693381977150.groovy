import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDKW
import tnrLog.Log

/**
 * TESTS UNITAIRES
 *
 * static List getKWAllowedList()
 * static boolean isAllowedKeyword(def val)
 * static boolean isNU(def val)
 * static boolean isVIDE(def val)
 * static boolean isNULL(def val)
 * static boolean isDATE(def val)
 * static boolean isDATETIME(def val)
 * static boolean isORDRE(def val)
 * static boolean isSEQUENCEID(def val)
 * static boolean startWithTBD(def val)
 * static boolean startWithUPD(def val)
 * static boolean isTBD(def val)
 * static boolean isUPD(def val)
 * static String getKW_NU()
 * static String getKW_VIDE()
 * static String getKW_NULL()
 * static String getKW_DATE()
 * static String getKW_DATETIME()
 * static String getKW_ORDRE()
 * static String getKW_SEQUENCEID()
 * static String getKW_TBD()
 * static def getValueOfKW_TBD(def val)
 * static def getOldValueOfKW_UPD(def val)
 * static def getNewValueOfKW_UPD(def val)

 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrJDDManager.JDDKW'

List getKWAllowedListTest = [
	'$VIDE', 
	'$NULL', 
	'$DATESYS', 
	'$DATETIMESYS', 
	'$ORDRE', 
	'$SEQUENCEID', 
	'$NU', 
	'$TBD', 
	'$UPD'
]

Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKWAllowedList()",getKWAllowedListTest,JDDKW.getKWAllowedList())

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$NULL')",true,JDDKW.isAllowedKeyword('$NULL'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$TBD')",true,JDDKW.isAllowedKeyword('$TBD'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$TBD*0')",true,JDDKW.isAllowedKeyword('$TBD*0'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$UPD*VAL*NEWVAL')",true,JDDKW.isAllowedKeyword('$UPD*VAL*NEWVAL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$UNK')",false,JDDKW.isAllowedKeyword('$UNK'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('VIDE')",false,JDDKW.isAllowedKeyword('VIDE'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword(32)",false,JDDKW.isAllowedKeyword(32))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$UPD')",false,JDDKW.isAllowedKeyword('$UPD'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$UPD*VAL')",false,JDDKW.isAllowedKeyword('$UPD*VAL'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isAllowedKeyword('\$UPD*VAL*NEWVAL*VAL3')",false,JDDKW.isAllowedKeyword('$UPD*VAL*NEWVAL*VAL3'))


Log.addAssert(CLASS_FOR_LOG,"JDDKW.isNU('\$NU')",true,JDDKW.isNU('$NU'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isNU('\$NULL')",false,JDDKW.isNU('$NULL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isVIDE('\$VIDE')",true,JDDKW.isVIDE('$VIDE'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isVIDE('\$NULL')",false,JDDKW.isVIDE('$NULL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isNULL('\$NULL')",true,JDDKW.isNULL('$NULL'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isNULL('\$VIDE')",false,JDDKW.isNULL('$VIDE'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isDATE('\$DATESYS')",true,JDDKW.isDATE('$DATESYS'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isDATE('\$NULL')",false,JDDKW.isDATE('$NULL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isDATETIME('\$DATETIMESYS')",true,JDDKW.isDATETIME('$DATETIMESYS'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isDATETIME('\$NULL')",false,JDDKW.isDATETIME('$NULL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isORDRE('\$ORDRE')",true,JDDKW.isORDRE('$ORDRE'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isORDRE('\$NULL')",false,JDDKW.isORDRE('$NULL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isSEQUENCEID('\$SEQUENCEID')",true,JDDKW.isSEQUENCEID('$SEQUENCEID'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isSEQUENCEID('\$NULL')",false,JDDKW.isSEQUENCEID('$NULL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.startWithTBD('\$TBDxxxxxx')",true,JDDKW.startWithTBD('$TBDxxxxxx'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.startWithTBD('\$NULL')",false,JDDKW.startWithTBD('$NULL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.isTBD('\$TBD*0')",true,JDDKW.isTBD('$TBD*0'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isTBD('\$TBD')",false,JDDKW.isTBD('$TBD'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isTBD('\$TBD*0*1')",false,JDDKW.isTBD('$TBD*0*1'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isTBD('\$UNK*0')",false,JDDKW.isTBD('$UNK*0'))


Log.addAssert(CLASS_FOR_LOG,"JDDKW.isUPD('\$UPD*VAL*NEWVAL')",true,JDDKW.isUPD('$UPD*VAL*NEWVAL'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isUPD('\$UPD')",false,JDDKW.isUPD('$UPD'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isUPD('\$UPD*VAL')",false,JDDKW.isUPD('$UPD*VAL'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isUPD('\$UPD*VAL*NEWVAL*val3')",false,JDDKW.isUPD('$UPD*VAL*NEWVAL*val3'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.isUPD('\$UNK*VAL*NEWVAL')",false,JDDKW.isUPD('$UNK*VAL*NEWVAL'))

Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_NU()",'$NU',JDDKW.getKW_NU())
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_VIDE()",'$VIDE',JDDKW.getKW_VIDE())
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_NULL()",'$NULL',JDDKW.getKW_NULL())
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_DATE()",'$DATESYS',JDDKW.getKW_DATE())
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_DATETIME()",'$DATETIMESYS',JDDKW.getKW_DATETIME())
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_ORDRE()",'$ORDRE',JDDKW.getKW_ORDRE())
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_SEQUENCEID()",'$SEQUENCEID',JDDKW.getKW_SEQUENCEID())
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getKW_TBD()",'$TBD',JDDKW.getKW_TBD())

Log.addAssert(CLASS_FOR_LOG,"JDDKW.getValueOfKW_TBD('\$TBD*0')",'0',JDDKW.getValueOfKW_TBD('$TBD*0'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getValueOfKW_TBD('\$TBD')",null,JDDKW.getValueOfKW_TBD('$TBD'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getValueOfKW_TBD('\$UNK*0')",null,JDDKW.getValueOfKW_TBD('$UNK*0'))
Log.addAssert(CLASS_FOR_LOG,"JDDKW.getValueOfKW_TBD('\$TBD*0*2')",null,JDDKW.getValueOfKW_TBD('$TBD*0*2'))


Log.addAssert(CLASS_FOR_LOG,"JDDKW.getOldValueOfKW_UPD('\$UPD*VAL*NEWVAL')",'VAL',JDDKW.getOldValueOfKW_UPD('$UPD*VAL*NEWVAL'))


Log.addAssert(CLASS_FOR_LOG,"JDDKW.getNewValueOfKW_UPD('\$UPD*VAL*NEWVAL')",'NEWVAL',JDDKW.getNewValueOfKW_UPD('$UPD*VAL*NEWVAL'))
