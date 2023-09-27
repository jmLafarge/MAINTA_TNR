import internal.GlobalVariable
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
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

	
	STEP.verifyValue(0, myJDD, "ID_NUMEQU")
	STEP.verifyValue(0, myJDD, "ST_DESEQU")
	STEP.verifyValue(0, myJDD, "ID_NUMEMP")
	STEP.verifyValue(0, myJDD, "ST_DESEMP")
	STEP.verifyValue(0, myJDD, "ID_CODDEM")
	STEP.verifyValue(0, myJDD, "ST_NOM_ID_CODDEM")
	
	//manque bouton radio
	
	STEP.verifyDateValue(0, myJDD, "DT_DEM")// ou verifyDateValue
	
	STEP.verifyDateValue(0, myJDD, "DT_READEM")// ou verifyDateValue
	KW.verifyTimeValue(myJDD, "NU_HEUDEM")// ou verifyDateValue --> c'est un datetime au formatheure
	STEP.verifyValue(0, myJDD, "ID_CODIMP")
	STEP.verifyValue(0, myJDD, "ST_DESIMP")
	STEP.verifyValue(0, myJDD, "ID_NUMMAT")
	STEP.verifyValue(0, myJDD, "ST_DESMAT")
	STEP.verifyValue(0, myJDD, "ID_CODGES")
	STEP.verifyValue(0, myJDD, "ST_DESGES")
	
	
	STEP.verifyValue(0, myJDD, "NU_HEUPRE",Tools.convertFloatToHH_MM(myJDD.getData("NU_HEUPRE")))
	STEP.verifyValue(0, myJDD, "NU_DURARR",Tools.convertFloatToHH_MM(myJDD.getData("NU_DURARR")))
	
	STEP.verifyValue(0, myJDD, "ST_TRADEM")
	
	STEP.verifyText(0, new JDD(JDDFileMapper.getFullnameFromModObj('TR.BTR'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")
	
	STEP.verifyValue(0, myJDD, "ID_CODCONTRA")
	STEP.verifyValue(0, myJDD, "ST_DESID_CODCONTRA")
	STEP.verifyValue(0, myJDD, "OPE_COD")
	STEP.verifyValue(0, myJDD, "ST_DESID_NUMOPE")
	STEP.verifyValue(0, myJDD, "ID_CODPROJET")
	STEP.verifyValue(0, myJDD, "ST_DESID_CODPROJET")
	STEP.verifyValue(0, myJDD, "NU_USA")
	STEP.verifyElementCheckedOrNot(0, myJDD, "ST_BLO", "O")
	
	
	/*
	STEP.verifyElementCheckedOrNot(0, myJDD, "TOTALTIME", "O")
	STEP.verifyElementCheckedOrNot(0, myJDD, "TOTALSTOP", "O")
	STEP.verifyElementCheckedOrNot(0, myJDD, "MOS_AUTOAPPLINEAIRE", "O")
	STEP.verifyElementCheckedOrNot(0, myJDD, "PREV2REA", "O")
	*/
	
	TNRResult.addEndTestCase()
}

