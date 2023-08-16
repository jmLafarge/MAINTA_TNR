import my.Log
import myJDDManager.JDDKW

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

Log.addAssert('JDDKW.getKWAllowedList()',getKWAllowedListTest,JDDKW.getKWAllowedList())

Log.addAssert('JDDKW.isAllowedKeyword()',true,JDDKW.isAllowedKeyword('$NULL'))
Log.addAssert('JDDKW.isAllowedKeyword()',true,JDDKW.isAllowedKeyword('$TBD'))
Log.addAssert('JDDKW.isAllowedKeyword()',true,JDDKW.isAllowedKeyword('$TBD*0'))
Log.addAssert('JDDKW.isAllowedKeyword()',true,JDDKW.isAllowedKeyword('$UPD*VAL*NEWVAL'))

Log.addAssert('JDDKW.isAllowedKeyword()',false,JDDKW.isAllowedKeyword('$UNK'))
Log.addAssert('JDDKW.isAllowedKeyword()',false,JDDKW.isAllowedKeyword('VIDE'))
Log.addAssert('JDDKW.isAllowedKeyword()',false,JDDKW.isAllowedKeyword(32))
Log.addAssert('JDDKW.isAllowedKeyword()',false,JDDKW.isAllowedKeyword('$UPD'))
Log.addAssert('JDDKW.isAllowedKeyword()',false,JDDKW.isAllowedKeyword('$UPD*VAL'))
Log.addAssert('JDDKW.isAllowedKeyword()',false,JDDKW.isAllowedKeyword('$UPD*VAL*NEWVAL*VAL3'))


Log.addAssert('JDDKW.isNU()',true,JDDKW.isNU('$NU'))
Log.addAssert('JDDKW.isNU()',false,JDDKW.isNU('$NULL'))

Log.addAssert('JDDKW.isVIDE()',true,JDDKW.isVIDE('$VIDE'))
Log.addAssert('JDDKW.isVIDE()',false,JDDKW.isVIDE('$NULL'))

Log.addAssert('JDDKW.isNULL()',true,JDDKW.isNULL('$NULL'))
Log.addAssert('JDDKW.isNULL()',false,JDDKW.isNULL('$VIDE'))

Log.addAssert('JDDKW.isDATE()',true,JDDKW.isDATE('$DATESYS'))
Log.addAssert('JDDKW.isDATE()',false,JDDKW.isDATE('$NULL'))

Log.addAssert('JDDKW.isDATETIME()',true,JDDKW.isDATETIME('$DATETIMESYS'))
Log.addAssert('JDDKW.isDATETIME()',false,JDDKW.isDATETIME('$NULL'))

Log.addAssert('JDDKW.isORDRE()',true,JDDKW.isORDRE('$ORDRE'))
Log.addAssert('JDDKW.isORDRE()',false,JDDKW.isORDRE('$NULL'))

Log.addAssert('JDDKW.isSEQUENCEID()',true,JDDKW.isSEQUENCEID('$SEQUENCEID'))
Log.addAssert('JDDKW.isSEQUENCEID()',false,JDDKW.isSEQUENCEID('$NULL'))

Log.addAssert('JDDKW.startWithTBD()',true,JDDKW.startWithTBD('$TBDxxxxxx'))
Log.addAssert('JDDKW.startWithTBD()',false,JDDKW.startWithTBD('$NULL'))

Log.addAssert('JDDKW.isTBD()',true,JDDKW.isTBD('$TBD*0'))
Log.addAssert('JDDKW.isTBD()',false,JDDKW.isTBD('$TBD'))
Log.addAssert('JDDKW.isTBD()',false,JDDKW.isTBD('$TBD*0*1'))
Log.addAssert('JDDKW.isTBD()',false,JDDKW.isTBD('$UNK*0'))


Log.addAssert('JDDKW.isUPD()',true,JDDKW.isUPD('$UPD*VAL*NEWVAL'))
Log.addAssert('JDDKW.isUPD()',false,JDDKW.isUPD('$UPD'))
Log.addAssert('JDDKW.isUPD()',false,JDDKW.isUPD('$UPD*VAL'))
Log.addAssert('JDDKW.isUPD()',false,JDDKW.isUPD('$UPD*VAL*NEWVAL*val3'))
Log.addAssert('JDDKW.isUPD()',false,JDDKW.isUPD('$UNK*VAL*NEWVAL'))

Log.addAssert('JDDKW.getKW_NU()','$NU',JDDKW.getKW_NU())
Log.addAssert('JDDKW.getKW_VIDE()','$VIDE',JDDKW.getKW_VIDE())
Log.addAssert('JDDKW.getKW_NULL()','$NULL',JDDKW.getKW_NULL())
Log.addAssert('JDDKW.getKW_DATE()','$DATESYS',JDDKW.getKW_DATE())
Log.addAssert('JDDKW.getKW_DATETIME()','$DATETIMESYS',JDDKW.getKW_DATETIME())
Log.addAssert('JDDKW.getKW_ORDRE()','$ORDRE',JDDKW.getKW_ORDRE())
Log.addAssert('JDDKW.getKW_SEQUENCEID()','$SEQUENCEID',JDDKW.getKW_SEQUENCEID())
Log.addAssert('JDDKW.getKW_TBD()','$TBD',JDDKW.getKW_TBD())

Log.addAssert('JDDKW.getValueOfKW_TBD()','0',JDDKW.getValueOfKW_TBD('$TBD*0'))
Log.addAssert('JDDKW.getValueOfKW_TBD()',null,JDDKW.getValueOfKW_TBD('$TBD'))
Log.addAssert('JDDKW.getValueOfKW_TBD()',null,JDDKW.getValueOfKW_TBD('$UNK*0'))
Log.addAssert('JDDKW.getValueOfKW_TBD()',null,JDDKW.getValueOfKW_TBD('$TBD*0*2'))


Log.addAssert('JDDKW.getOldValueOfKW_UPD()','VAL',JDDKW.getOldValueOfKW_UPD('$UPD*VAL*NEWVAL'))


Log.addAssert('JDDKW.getNewValueOfKW_UPD()','NEWVAL',JDDKW.getNewValueOfKW_UPD('$UPD*VAL*NEWVAL'))
















