import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.KW
import tnrWebUI.NAV


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD(myJDD.getStrData())
	NAV.verifierEcranRUD(myJDD.getStrData('ST_CODCOU'))



	  
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		KW.scrollAndClick(myJDD, "tab_Equipement")
		KW.waitForElementVisible(myJDD, "tab_EquipementSelected")
		
		KW.scrollAndSetText(myJDD, "ST_CODCOU")
		KW.scrollAndSetText(myJDD, "ST_CODPERS")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
		KW.scrollAndSetText(myJDD, "ST_DESEQU")
		KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_NIVABS", "O")
		KW.searchWithHelper(myJDD, "ID_CODGES","","")
		KW.searchWithHelper(myJDD, "ID_NUMEMP","//a[@id='BtnEMP_CODLON']/i","SEARCH_ST_CODLON") //EMP_CODLON
		KW.searchWithHelper(myJDD, "ID_CODIMP","","")
		KW.searchWithHelper(myJDD, "ID_NUMGRO","//a[@id='BtnGRO_CODLON']/i","SEARCH_ST_CODLON") // GRO_CODLON		
		KW.searchWithHelper(myJDD, "ID_CODCOM","","")
		KW.scrollAndSetText(myJDD, "NU_USA")
		KW.searchWithHelper(myJDD, "ID_CODCON","","")
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		KW.scrollAndClick(myJDD, "tab_Fiche")
		KW.waitForElementVisible(myJDD, "tab_FicheSelected")
		
		KW.scrollAndSetText(myJDD, "ST_NOMFOU")
		KW.scrollAndSetText(myJDD, "ST_REFFOU")
		KW.scrollAndSetText(myJDD, "NU_PRIACH")
		KW.scrollAndSetText(myJDD, "ST_NOMCON")
		KW.scrollAndSetText(myJDD, "ST_REFCON")
		KW.scrollAndSetDate(myJDD, "DT_ACH")
		KW.scrollAndSetDate(myJDD, "DT_SER")
		KW.scrollAndSetText(myJDD, "NU_PRIACT")
		KW.scrollAndSetDate(myJDD, "DT_FINGAR")
		KW.scrollAndSetDate(myJDD, "DT_FINVIE")
		KW.scrollAndSetText(myJDD, "NU_COUHOR")
		KW.scrollAndSetText(myJDD, "NU_USAGAR")
		KW.scrollAndSetText(myJDD, "NU_FINUSA")
		KW.scrollAndSetText(myJDD, "NU_COUARR")
		KW.scrollAndSetText(myJDD, "ST_NUMINV")
		KW.scrollAndSetText(myJDD, "ST_OBS")
		KW.searchWithHelper(myJDD, "ID_CODCAL","","")
		KW.searchWithHelper(myJDD, "ID_CODCONTRA","","")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_COM", "O")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_MAT", "O")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_ANA", "O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		KW.scrollAndClick(myJDD, "tab_Notes")
		KW.waitForElementVisible(myJDD, "tab_NotesSelected")
		
		KW.scrollToPositionAndWait(0, 0,1)
		
		JDD myJDDnote = new JDD(JDDFileMapper.getFullnameFromModObj('RT.EQU'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		String notes = myJDDnote.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC1'))
		String consignes = myJDDnote.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC2'))
	
		
		KW.scrollAndClick(myJDD,"ModifierNotes")
		KW.delay(1)
		
		WebUI.switchToWindowIndex('1')
		
		if (KW.isElementPresent(myJDD,'frameNote', GlobalVariable.TIMEOUT)) {
			
			KW.switchToFrame(myJDD, 'frameNote')
			
			KW.setText(myJDD, 'textNote',notes)
			
			WebUI.switchToDefaultContent()
			
			KW.scrollAndClick(myJDD,"BTN_ValiderEtFermerNote")
			WebUI.switchToWindowIndex('0')
		}
		
		
		
		KW.scrollAndClick(myJDD,"ModifierConsignes")
		KW.delay(1)
		
		WebUI.switchToWindowIndex('1')
		
		if (KW.isElementPresent(myJDD,'frameNote', GlobalVariable.TIMEOUT)) {
			
			KW.switchToFrame(myJDD, 'frameNote')
			
			KW.setText(myJDD, 'textNote',consignes)
			
			WebUI.switchToDefaultContent()
			
			KW.scrollAndClick(myJDD,"BTN_ValiderEtFermerNote")
			WebUI.switchToWindowIndex('0')
		}
		
		
		
		
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		KW.scrollAndClick(myJDD, "tab_Adresse")
		KW.waitForElementVisible(myJDD, "tab_AdresseSelected")
	

	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		SQL.checkJDDWithBD(myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if

