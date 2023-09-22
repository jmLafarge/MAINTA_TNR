import internal.GlobalVariable
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*

'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
	String fct = TNRPropertiesReader.getMyProperty('CODESCREEN_TR.BTR')
	String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?ID1="+ myJDD.getStrData('ID_NUMBT') 
	STEP.navigateToUrl(0, url,'Consultation')

	
	KW.verifyValue(myJDD, "ID_NUMEQU")
	KW.verifyValue(myJDD, "ST_DESEQU")
	KW.verifyValue(myJDD, "ID_NUMEMP")
	KW.verifyValue(myJDD, "ST_DESEMP")
	KW.verifyValue(myJDD, "ID_CODDEM")
	KW.verifyValue(myJDD, "ST_NOM_ID_CODDEM")
	
	//manque bouton radio
	
	KW.verifyDateValue(myJDD, "DT_DEM")// ou verifyDateValue
	
	KW.verifyDateValue(myJDD, "DT_READEM")// ou verifyDateValue
	KW.verifyTimeValue(myJDD, "NU_HEUDEM")// ou verifyDateValue --> c'est un datetime au formatheure
	KW.verifyValue(myJDD, "ID_CODIMP")
	KW.verifyValue(myJDD, "ST_DESIMP")
	KW.verifyValue(myJDD, "ID_NUMMAT")
	KW.verifyValue(myJDD, "ST_DESMAT")
	KW.verifyValue(myJDD, "ID_CODGES")
	KW.verifyValue(myJDD, "ST_DESGES")
	
	
	KW.verifyValue(myJDD, "NU_HEUPRE",Tools.convertFloatToHH_MM(myJDD.getData("NU_HEUPRE")))
	KW.verifyValue(myJDD, "NU_DURARR",Tools.convertFloatToHH_MM(myJDD.getData("NU_DURARR")))
	
	KW.verifyValue(myJDD, "ST_TRADEM")
	
	STEP.verifyText(0, new JDD(JDDFileMapper.getFullnameFromModObj('TR.BTR'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")
	
	KW.verifyValue(myJDD, "ID_CODCONTRA")
	KW.verifyValue(myJDD, "ST_DESID_CODCONTRA")
	KW.verifyValue(myJDD, "OPE_COD")
	KW.verifyValue(myJDD, "ST_DESID_NUMOPE")
	KW.verifyValue(myJDD, "ID_CODPROJET")
	KW.verifyValue(myJDD, "ST_DESID_CODPROJET")
	KW.verifyValue(myJDD, "NU_USA")
	KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_BLO", "O")
	
	
	/*
	KWCheckbox.verifyElementCheckedOrNot(myJDD, "TOTALTIME", "O")
	KWCheckbox.verifyElementCheckedOrNot(myJDD, "TOTALSTOP", "O")
	KWCheckbox.verifyElementCheckedOrNot(myJDD, "MOS_AUTOAPPLINEAIRE", "O")
	KWCheckbox.verifyElementCheckedOrNot(myJDD, "PREV2REA", "O")
	*/
	
	TNRResult.addEndTestCase()
}

