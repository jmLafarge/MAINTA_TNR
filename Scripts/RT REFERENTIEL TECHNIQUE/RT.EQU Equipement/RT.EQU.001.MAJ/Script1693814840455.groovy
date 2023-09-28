import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData())
	NAV.checkReadUpdateDeleteScreen(myJDD.getStrData('ST_CODCOU'))



	  
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.simpleClick(myJDD, "tab_Equipement")
		STEP.verifyElementVisible(myJDD, "tab_EquipementSelected")
		
		STEP.setText(myJDD, "ST_CODCOU")
		STEP.setText(myJDD, "ST_CODPERS")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_INA", "O")
		STEP.setText(myJDD, "ST_DESEQU")
		STEP.selectOptionByLabel(myJDD, "ST_ETA")
		STEP.selectOptionByLabel(myJDD, "NU_CRI")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_NIVABS", "O")
		STEP.searchWithHelper(myJDD, "ID_CODGES","","")
		STEP.searchWithHelper(myJDD, "ID_NUMEMP","//a[@id='BtnEMP_CODLON']/i","SEARCH_ST_CODLON") //EMP_CODLON
		STEP.searchWithHelper(myJDD, "ID_CODIMP","","")
		STEP.searchWithHelper(myJDD, "ID_NUMGRO","//a[@id='BtnGRO_CODLON']/i","SEARCH_ST_CODLON") // GRO_CODLON		
		STEP.searchWithHelper(myJDD, "ID_CODCOM","","")
		STEP.setText(myJDD, "NU_USA")
		STEP.searchWithHelper(myJDD, "ID_CODCON","","")
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.simpleClick(myJDD, "tab_Fiche")
		STEP.verifyElementVisible(myJDD, "tab_FicheSelected")
		
		STEP.setText(myJDD, "ST_NOMFOU")
		STEP.setText(myJDD, "ST_REFFOU")
		STEP.setText(myJDD, "NU_PRIACH")
		STEP.setText(myJDD, "ST_NOMCON")
		STEP.setText(myJDD, "ST_REFCON")
		STEP.setDate(myJDD, "DT_ACH")
		STEP.setDate(myJDD, "DT_SER")
		STEP.setText(myJDD, "NU_PRIACT")
		STEP.setDate(myJDD, "DT_FINGAR")
		STEP.setDate(myJDD, "DT_FINVIE")
		STEP.setText(myJDD, "NU_COUHOR")
		STEP.setText(myJDD, "NU_USAGAR")
		STEP.setText(myJDD, "NU_FINUSA")
		STEP.setText(myJDD, "NU_COUARR")
		STEP.setText(myJDD, "ST_NUMINV")
		STEP.setText(myJDD, "ST_OBS")
		STEP.searchWithHelper(myJDD, "ID_CODCAL","","")
		STEP.searchWithHelper(myJDD, "ID_CODCONTRA","","")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_COM", "O")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_MAT", "O")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_CONTRABT", "O")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_ANA", "O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
	
		STEP.scrollToPosition( 0, 0)
		
		STEP.simpleClick(myJDD, "tab_Notes")
		STEP.verifyElementVisible(myJDD, "tab_NotesSelected")
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.EQU'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		String notes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC1'))
		String consignes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC2'))
	
		STEP.setMemoText(notes, 'Notes',true,myJDD,'Memo_ModifierNotes')
		STEP.setMemoText(consignes, 'Consignes',true,myJDD,'Memo_ModifierConsignes')
		

		
		
		
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		STEP.simpleClick(myJDD, "tab_Adresse")
		STEP.verifyElementVisible(myJDD, "tab_AdresseSelected")
	

	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		STEP.checkJDDWithBD(myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if


