import tnrWebUI.KW
import tnrWebUI.NAV
import tnrResultManager.TNRResult
import tnrJDDManager.JDD

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())

	

			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")

			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			KW.scrollAndClick(myJDD, "tab_Matricule")
			KW.waitForElementVisible(myJDD, "tab_MatriculeSelected")
			
			KW.verifyValue(myJDD, "ID_NUMMAT")
			KW.verifyValue(myJDD, "ST_NUMINV")
			KW.verifyElementCheckedOrNot(myJDD, "ST_INA", "O")
			KW.verifyValue(myJDD, "ST_DES")
			KW.verifyOptionSelectedByValue(myJDD, "NU_TYP")
			KW.verifyValue(myJDD, "NU_PRISTO")
			KW.verifyValue(myJDD, "ID_CODART")
			KW.verifyValue(myJDD, "ST_DESID_CODART")
			KW.verifyValue(myJDD, "ID_CODMOY")
			KW.verifyValue(myJDD, "ST_DESID_CODMOY")
			KW.verifyValue(myJDD, "ID_CODGES")
			KW.verifyValue(myJDD, "ST_DESGES")
			KW.verifyValue(myJDD, "ID_NUMCRI")
			KW.verifyValue(myJDD, "ST_DESID_NUMCRI")
			KW.verifyValue(myJDD, "ID_CODIMP")
			KW.verifyValue(myJDD, "ST_DESIMP")
			KW.verifyValue(myJDD, "ID_NUMGRO")
			KW.verifyValue(myJDD, "ST_DESGRO")
			KW.verifyValue(myJDD, "ID_CODCOM")
			KW.verifyValue(myJDD, "ST_DESID_CODCOM")
			KW.verifyValue(myJDD, "NU_USA")
			KW.verifyValue(myJDD, "ID_CODCON")
			KW.verifyElementCheckedOrNot(myJDD, "ST_TEC", "O")
			KW.verifyElementCheckedOrNot(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			KW.scrollAndClick(myJDD, "tab_Fiche")
			KW.waitForElementVisible(myJDD, "tab_FicheSelected")
			
			KW.verifyValue(myJDD, "ID_CODFOUINI")
			KW.verifyValue(myJDD, "ST_REFFOU")
			KW.verifyValue(myJDD, "ID_CODCONSTR")
			KW.verifyValue(myJDD, "ST_REFCON")
			KW.verifyDateText(myJDD, "DT_ACH")
			KW.verifyDateText(myJDD, "DT_FAC")
			KW.verifyValue(myJDD, "NU_PRIACH")
			KW.verifyDateText(myJDD, "DT_FINGAR")
			KW.verifyDateText(myJDD, "DT_FINVIE")
			KW.verifyValue(myJDD, "NU_PRIACT")
			KW.verifyValue(myJDD, "NU_USAGAR")
			KW.verifyValue(myJDD, "NU_FINUSA")
			KW.verifyValue(myJDD, "ID_CODCAL")
			KW.verifyValue(myJDD, "ST_DESID_CODCAL")
			KW.verifyValue(myJDD, "ID_CODGAR")
			KW.verifyValue(myJDD, "ST_DESID_CODGAR")
			KW.verifyValue(myJDD, "ID_REFCOM")
			KW.verifyValue(myJDD, "ST_DESID_REFCOM")
			KW.verifyValue(myJDD, "ST_AFFCOM")
			KW.verifyValue(myJDD, "ID_CODINTPRO")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTPRO")
			KW.verifyValue(myJDD, "ID_CODINTGES")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTGES")
			KW.verifyValue(myJDD, "ID_CODINTEXP")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTEXP")
			KW.verifyValue(myJDD, "ID_CODINTMAI")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTMAI")
			KW.verifyElementCheckedOrNot(myJDD, "ST_CONTRA", "O")
			KW.verifyElementCheckedOrNot(myJDD, "ST_CONTRABT", "O")
			KW.verifyElementCheckedOrNot(myJDD, "ST_PRE", "O")
			KW.verifyElementCheckedOrNot(myJDD, "ST_INS", "O")
			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			KW.scrollAndClick(myJDD, "tab_Notes")
			KW.waitForElementVisible(myJDD, "tab_NotesSelected")
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			KW.scrollAndClick(myJDD, "tab_Etat")
			KW.waitForElementVisible(myJDD, "tab_EtatSelected")
			
			KW.verifyValue(myJDD, "ID_NUMEMP")
			KW.verifyValue(myJDD, "ST_DESEMP")
			KW.verifyValue(myJDD, "ST_CODLON")
			KW.verifyValue(myJDD, "EQU_DES")
			KW.verifyValue(myJDD, "ID_CODMAG")
			KW.verifyValue(myJDD, "ST_DESMAG")
			KW.verifyValue(myJDD, "ID_CODFOU")
			KW.verifyValue(myJDD, "ST_DESID_CODFOU")
			KW.verifyValue(myJDD, "ST_AUT")
			KW.verifyValue(myJDD, "DT_DEP")
			KW.verifyValue(myJDD, "DT_RET")
			//KW.verifyElementCheckedOrNot(myJDD, "NUMMAT2NUMAUTO", "O")
			//KW.verifyElementCheckedOrNot(myJDD, "DISABLE_RECTIFSTO", "O")
	
	
	
	TNRResult.addEndTestCase()
}

