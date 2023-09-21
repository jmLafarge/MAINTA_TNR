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
		
		KW.click(myJDD, "tab_Equipement")
		KW.isElementVisible(myJDD, "tab_EquipementSelected")
		
		KW.setText(myJDD, "ST_CODCOU")
		KW.setText(myJDD, "ST_CODPERS")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
		KW.setText(myJDD, "ST_DESEQU")
		KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_NIVABS", "O")
		KWSearchHelper.launch(myJDD, "ID_CODGES","","")
		KWSearchHelper.launch(myJDD, "ID_NUMEMP","//a[@id='BtnEMP_CODLON']/i","SEARCH_ST_CODLON") //EMP_CODLON
		KWSearchHelper.launch(myJDD, "ID_CODIMP","","")
		KWSearchHelper.launch(myJDD, "ID_NUMGRO","//a[@id='BtnGRO_CODLON']/i","SEARCH_ST_CODLON") // GRO_CODLON		
		KWSearchHelper.launch(myJDD, "ID_CODCOM","","")
		KW.setText(myJDD, "NU_USA")
		KWSearchHelper.launch(myJDD, "ID_CODCON","","")
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		KW.click(myJDD, "tab_Fiche")
		KW.isElementVisible(myJDD, "tab_FicheSelected")
		
		KW.setText(myJDD, "ST_NOMFOU")
		KW.setText(myJDD, "ST_REFFOU")
		KW.setText(myJDD, "NU_PRIACH")
		KW.setText(myJDD, "ST_NOMCON")
		KW.setText(myJDD, "ST_REFCON")
		KW.setDate(myJDD, "DT_ACH")
		KW.setDate(myJDD, "DT_SER")
		KW.setText(myJDD, "NU_PRIACT")
		KW.setDate(myJDD, "DT_FINGAR")
		KW.setDate(myJDD, "DT_FINVIE")
		KW.setText(myJDD, "NU_COUHOR")
		KW.setText(myJDD, "NU_USAGAR")
		KW.setText(myJDD, "NU_FINUSA")
		KW.setText(myJDD, "NU_COUARR")
		KW.setText(myJDD, "ST_NUMINV")
		KW.setText(myJDD, "ST_OBS")
		KWSearchHelper.launch(myJDD, "ID_CODCAL","","")
		KWSearchHelper.launch(myJDD, "ID_CODCONTRA","","")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_COM", "O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_MAT", "O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_ANA", "O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
	
		KW.scrollToPositionAndWait(0, 0,1)
		
		KW.click(myJDD, "tab_Notes")
		KW.isElementVisible(myJDD, "tab_NotesSelected")
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.EQU'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		String notes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC1'))
		String consignes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC2'))
	
		KWMemo.setText(notes, 'Notes',true,myJDD,'Memo_ModifierNotes')
		KWMemo.setText(consignes, 'Consignes',true,myJDD,'Memo_ModifierConsignes')
		

		
		
		
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		KW.click(myJDD, "tab_Adresse")
		KW.isElementVisible(myJDD, "tab_AdresseSelected")
	

	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.click(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		SQL.checkJDDWithBD(myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if


