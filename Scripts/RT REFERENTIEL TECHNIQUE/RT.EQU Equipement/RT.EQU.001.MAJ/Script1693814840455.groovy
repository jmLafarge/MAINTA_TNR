import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD(myJDD.getStrData())
	NAV.verifierEcranRUD(myJDD.getStrData('ST_CODCOU'))



	  
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.click(0, myJDD, "tab_Equipement")
		STEP.verifyElementVisible(0, myJDD, "tab_EquipementSelected")
		
		STEP.setText(0, myJDD, "ST_CODCOU")
		STEP.setText(0, myJDD, "ST_CODPERS")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
		STEP.setText(0, myJDD, "ST_DESEQU")
		KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_NIVABS", "O")
		KWSearchHelper.launch(myJDD, "ID_CODGES","","")
		KWSearchHelper.launch(myJDD, "ID_NUMEMP","//a[@id='BtnEMP_CODLON']/i","SEARCH_ST_CODLON") //EMP_CODLON
		KWSearchHelper.launch(myJDD, "ID_CODIMP","","")
		KWSearchHelper.launch(myJDD, "ID_NUMGRO","//a[@id='BtnGRO_CODLON']/i","SEARCH_ST_CODLON") // GRO_CODLON		
		KWSearchHelper.launch(myJDD, "ID_CODCOM","","")
		STEP.setText(0, myJDD, "NU_USA")
		KWSearchHelper.launch(myJDD, "ID_CODCON","","")
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.click(0, myJDD, "tab_Fiche")
		STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
		
		STEP.setText(0, myJDD, "ST_NOMFOU")
		STEP.setText(0, myJDD, "ST_REFFOU")
		STEP.setText(0, myJDD, "NU_PRIACH")
		STEP.setText(0, myJDD, "ST_NOMCON")
		STEP.setText(0, myJDD, "ST_REFCON")
		KW.setDate(myJDD, "DT_ACH")
		KW.setDate(myJDD, "DT_SER")
		STEP.setText(0, myJDD, "NU_PRIACT")
		KW.setDate(myJDD, "DT_FINGAR")
		KW.setDate(myJDD, "DT_FINVIE")
		STEP.setText(0, myJDD, "NU_COUHOR")
		STEP.setText(0, myJDD, "NU_USAGAR")
		STEP.setText(0, myJDD, "NU_FINUSA")
		STEP.setText(0, myJDD, "NU_COUARR")
		STEP.setText(0, myJDD, "ST_NUMINV")
		STEP.setText(0, myJDD, "ST_OBS")
		KWSearchHelper.launch(myJDD, "ID_CODCAL","","")
		KWSearchHelper.launch(myJDD, "ID_CODCONTRA","","")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_COM", "O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_MAT", "O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_ANA", "O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
	
		STEP.scrollToPosition(0, 0)
		
		STEP.click(0, myJDD, "tab_Notes")
		STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.EQU'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		String notes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC1'))
		String consignes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC2'))
	
		KWMemo.setText(notes, 'Notes',true,myJDD,'Memo_ModifierNotes')
		KWMemo.setText(consignes, 'Consignes',true,myJDD,'Memo_ModifierConsignes')
		

		
		
		
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		STEP.click(0, myJDD, "tab_Adresse")
		STEP.verifyElementVisible(0, myJDD, "tab_AdresseSelected")
	

	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		SQL.checkJDDWithBD(myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if


