import internal.GlobalVariable
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*

// Lecture du JDD
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
	String fct = TNRPropertiesReader.getMyProperty('CODESCREEN_TR.BTR')
	String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?ID1="+ myJDD.getStrData('ID_NUMBT') 
	STEP.navigateToUrl(url,'Consultation')

	
	STEP.verifyValue(myJDD, "ID_NUMEQU")
	STEP.verifyValue(myJDD, "ST_DESEQU")
	STEP.verifyValue(myJDD, "ID_NUMEMP")
	STEP.verifyValue(myJDD, "ST_DESEMP")
	STEP.verifyValue(myJDD, "ID_CODDEM")
	STEP.verifyValue(myJDD, "ST_NOM_ID_CODDEM")
	
	//manque bouton radio
	
	STEP.verifyDateValue(myJDD, "DT_DEM")// ou verifyDateValue
	
	STEP.verifyDateValue(myJDD, "DT_READEM")// ou verifyDateValue
	KW.verifyTimeValue(myJDD, "NU_HEUDEM")// ou verifyDateValue --> c'est un datetime au formatheure
	STEP.verifyValue(myJDD, "ID_CODIMP")
	STEP.verifyValue(myJDD, "ST_DESIMP")
	STEP.verifyValue(myJDD, "ID_NUMMAT")
	STEP.verifyValue(myJDD, "ST_DESMAT")
	STEP.verifyValue(myJDD, "ID_CODGES")
	STEP.verifyValue(myJDD, "ST_DESGES")
	
	
	STEP.verifyValue(myJDD, "NU_HEUPRE",Tools.convertFloatToHH_MM(myJDD.getData("NU_HEUPRE")))
	STEP.verifyValue(myJDD, "NU_DURARR",Tools.convertFloatToHH_MM(myJDD.getData("NU_DURARR")))
	
	STEP.verifyValue(myJDD, "ST_TRADEM")
	
	STEP.verifyText(new JDD(JDDFileMapper.getFullnameFromModObj('TR.BTR'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")
	
	STEP.verifyValue(myJDD, "ID_CODCONTRA")
	STEP.verifyValue(myJDD, "ST_DESID_CODCONTRA")
	STEP.verifyValue(myJDD, "OPE_COD")
	STEP.verifyValue(myJDD, "ST_DESID_NUMOPE")
	STEP.verifyValue(myJDD, "ID_CODPROJET")
	STEP.verifyValue(myJDD, "ST_DESID_CODPROJET")
	STEP.verifyValue(myJDD, "NU_USA")
	STEP.verifyBoxCheckedOrNot(myJDD, "ST_BLO", "O")
	
	
	/*
	STEP.verifyBoxCheckedOrNot(myJDD, "TOTALTIME", "O")
	STEP.verifyBoxCheckedOrNot(myJDD, "TOTALSTOP", "O")
	STEP.verifyBoxCheckedOrNot(myJDD, "MOS_AUTOAPPLINEAIRE", "O")
	STEP.verifyBoxCheckedOrNot(myJDD, "PREV2REA", "O")
	*/
	
	TNRResult.addEndTestCase()
}

