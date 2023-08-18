import my.Log
import myJDDManager.JDDKW

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

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

Log.addAssert("JDDKW.getKWAllowedList()",getKWAllowedListTest,JDDKW.getKWAllowedList())

Log.addAssert("JDDKW.isAllowedKeyword('\$NULL')",true,JDDKW.isAllowedKeyword('$NULL'))
Log.addAssert("JDDKW.isAllowedKeyword('\$TBD')",true,JDDKW.isAllowedKeyword('$TBD'))
Log.addAssert("JDDKW.isAllowedKeyword('\$TBD*0')",true,JDDKW.isAllowedKeyword('$TBD*0'))
Log.addAssert("JDDKW.isAllowedKeyword('\$UPD*VAL*NEWVAL')",true,JDDKW.isAllowedKeyword('$UPD*VAL*NEWVAL'))

Log.addAssert("JDDKW.isAllowedKeyword('\$UNK')",false,JDDKW.isAllowedKeyword('$UNK'))
Log.addAssert("JDDKW.isAllowedKeyword('VIDE')",false,JDDKW.isAllowedKeyword('VIDE'))
Log.addAssert("JDDKW.isAllowedKeyword(32)",false,JDDKW.isAllowedKeyword(32))
Log.addAssert("JDDKW.isAllowedKeyword('\$UPD')",false,JDDKW.isAllowedKeyword('$UPD'))
Log.addAssert("JDDKW.isAllowedKeyword('\$UPD*VAL')",false,JDDKW.isAllowedKeyword('$UPD*VAL'))
Log.addAssert("JDDKW.isAllowedKeyword('\$UPD*VAL*NEWVAL*VAL3')",false,JDDKW.isAllowedKeyword('$UPD*VAL*NEWVAL*VAL3'))


Log.addAssert("JDDKW.isNU('\$NU')",true,JDDKW.isNU('$NU'))
Log.addAssert("JDDKW.isNU('\$NULL')",false,JDDKW.isNU('$NULL'))

Log.addAssert("JDDKW.isVIDE('\$VIDE')",true,JDDKW.isVIDE('$VIDE'))
Log.addAssert("JDDKW.isVIDE('\$NULL')",false,JDDKW.isVIDE('$NULL'))

Log.addAssert("JDDKW.isNULL('\$NULL')",true,JDDKW.isNULL('$NULL'))
Log.addAssert("JDDKW.isNULL('\$VIDE')",false,JDDKW.isNULL('$VIDE'))

Log.addAssert("JDDKW.isDATE('\$DATESYS')",true,JDDKW.isDATE('$DATESYS'))
Log.addAssert("JDDKW.isDATE('\$NULL')",false,JDDKW.isDATE('$NULL'))

Log.addAssert("JDDKW.isDATETIME('\$DATETIMESYS')",true,JDDKW.isDATETIME('$DATETIMESYS'))
Log.addAssert("JDDKW.isDATETIME('\$NULL')",false,JDDKW.isDATETIME('$NULL'))

Log.addAssert("JDDKW.isORDRE('\$ORDRE')",true,JDDKW.isORDRE('$ORDRE'))
Log.addAssert("JDDKW.isORDRE('\$NULL')",false,JDDKW.isORDRE('$NULL'))

Log.addAssert("JDDKW.isSEQUENCEID('\$SEQUENCEID')",true,JDDKW.isSEQUENCEID('$SEQUENCEID'))
Log.addAssert("JDDKW.isSEQUENCEID('\$NULL')",false,JDDKW.isSEQUENCEID('$NULL'))

Log.addAssert("JDDKW.startWithTBD('\$TBDxxxxxx')",true,JDDKW.startWithTBD('$TBDxxxxxx'))
Log.addAssert("JDDKW.startWithTBD('\$NULL')",false,JDDKW.startWithTBD('$NULL'))

Log.addAssert("JDDKW.isTBD('\$TBD*0')",true,JDDKW.isTBD('$TBD*0'))
Log.addAssert("JDDKW.isTBD('\$TBD')",false,JDDKW.isTBD('$TBD'))
Log.addAssert("JDDKW.isTBD('\$TBD*0*1')",false,JDDKW.isTBD('$TBD*0*1'))
Log.addAssert("JDDKW.isTBD('\$UNK*0')",false,JDDKW.isTBD('$UNK*0'))


Log.addAssert("JDDKW.isUPD('\$UPD*VAL*NEWVAL')",true,JDDKW.isUPD('$UPD*VAL*NEWVAL'))
Log.addAssert("JDDKW.isUPD('\$UPD')",false,JDDKW.isUPD('$UPD'))
Log.addAssert("JDDKW.isUPD('\$UPD*VAL')",false,JDDKW.isUPD('$UPD*VAL'))
Log.addAssert("JDDKW.isUPD('\$UPD*VAL*NEWVAL*val3')",false,JDDKW.isUPD('$UPD*VAL*NEWVAL*val3'))
Log.addAssert("JDDKW.isUPD('\$UNK*VAL*NEWVAL')",false,JDDKW.isUPD('$UNK*VAL*NEWVAL'))

Log.addAssert("JDDKW.getKW_NU()",'$NU',JDDKW.getKW_NU())
Log.addAssert("JDDKW.getKW_VIDE()",'$VIDE',JDDKW.getKW_VIDE())
Log.addAssert("JDDKW.getKW_NULL()",'$NULL',JDDKW.getKW_NULL())
Log.addAssert("JDDKW.getKW_DATE()",'$DATESYS',JDDKW.getKW_DATE())
Log.addAssert("JDDKW.getKW_DATETIME()",'$DATETIMESYS',JDDKW.getKW_DATETIME())
Log.addAssert("JDDKW.getKW_ORDRE()",'$ORDRE',JDDKW.getKW_ORDRE())
Log.addAssert("JDDKW.getKW_SEQUENCEID()",'$SEQUENCEID',JDDKW.getKW_SEQUENCEID())
Log.addAssert("JDDKW.getKW_TBD()",'$TBD',JDDKW.getKW_TBD())

Log.addAssert("JDDKW.getValueOfKW_TBD('\$TBD*0')",'0',JDDKW.getValueOfKW_TBD('$TBD*0'))
Log.addAssert("JDDKW.getValueOfKW_TBD('\$TBD')",null,JDDKW.getValueOfKW_TBD('$TBD'))
Log.addAssert("JDDKW.getValueOfKW_TBD('\$UNK*0')",null,JDDKW.getValueOfKW_TBD('$UNK*0'))
Log.addAssert("JDDKW.getValueOfKW_TBD('\$TBD*0*2')",null,JDDKW.getValueOfKW_TBD('$TBD*0*2'))


Log.addAssert("JDDKW.getOldValueOfKW_UPD('\$UPD*VAL*NEWVAL')",'VAL',JDDKW.getOldValueOfKW_UPD('$UPD*VAL*NEWVAL'))


Log.addAssert("JDDKW.getNewValueOfKW_UPD('\$UPD*VAL*NEWVAL')",'NEWVAL',JDDKW.getNewValueOfKW_UPD('$UPD*VAL*NEWVAL'))
