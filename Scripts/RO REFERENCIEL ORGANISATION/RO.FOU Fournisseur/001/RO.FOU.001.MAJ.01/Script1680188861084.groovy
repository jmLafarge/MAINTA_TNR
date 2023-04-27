import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.Log as MYLOG
import my.NAV
import my.JDD
import my.JDDFiles

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	MYLOG.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())



	MYLOG.addSTEPGRP("ONGLET FOURNISSEUR")

		KW.scrollAndClick(myJDD,"tab_Fournisseur")
		KW.waitForElementVisible(myJDD,"tab_FournisseurSelected")
		
		//KW.scrollAndSetText(myJDD, "ID_CODFOU")
		KW.scrollAndSetText(myJDD, "ST_NOM")
		KW.searchWithHelper(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KW.scrollAndCheckIfNeeded(myJDD,"ST_CONSTR","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KW.searchWithHelper(myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP") //specific
		//ST_DESST_CODCOM --> pas d'action en modification

		
		MYLOG.addSTEPBLOCK("ADRESSE")
		
		def JDD_Adr = new my.JDD(JDDFiles.getFullName('RO.ADR'),'001',GlobalVariable.CASDETESTENCOURS)
		
		KW.scrollAndClick(myJDD,"TD_Adresse")
		KW.scrollAndClick(myJDD,"BTN_ModifierAdresse")
		
		//WebUI.switchToWindowIndex('1')
		
		//KW.scrollAndCheckIfNeeded(JDD_Adr,"ST_ADRPAR","O")
		KW.scrollAndSetText(JDD_Adr, "ST_ADR")
		KW.scrollAndSetText(JDD_Adr, "ST_ADRCOM")
		KW.scrollAndSetText(JDD_Adr, "ST_ADRCOM2")
		KW.scrollAndSetText(JDD_Adr, "ST_MENSPE")
		KW.scrollAndSetText(JDD_Adr, "ST_CODPOS")
		KW.scrollAndSetText(JDD_Adr, "ST_VIL")
		KW.scrollAndSetText(JDD_Adr, "ST_CEDEX")
		KW.scrollAndSetText(JDD_Adr, "NU_CEDEX")
		KW.scrollAndSetText(JDD_Adr, "ID_CODPAY")
		KW.scrollAndSetText(JDD_Adr, "ST_PAY")
		KW.scrollAndSetText(JDD_Adr, "ST_REFEXT")
		KW.scrollAndSetText(JDD_Adr, "ST_RELAPA")
		KW.scrollAndSetText(JDD_Adr, "ST_GPS")
		
		KW.scrollAndClick(JDD_Adr,"BTN_Valider")
		
		
		
		MYLOG.addSTEPBLOCK("CONTACT")
		KW.scrollAndSetText(myJDD, "ST_TELPHO")
		KW.scrollAndSetText(myJDD, "ST_CON")
		KW.scrollAndSetText(myJDD, "ST_TELMOB")
		KW.scrollAndSetText(myJDD, "ST_EMA")
		KW.scrollAndSetText(myJDD, "ST_TELCOP")
		KW.scrollAndSetText(myJDD, "ST_TELEX")
		
	MYLOG.addSTEPGRP("ONGLET COMMANDE")
		
		KW.scrollAndClick(myJDD,"tab_Commande")
		KW.waitForElementVisible(myJDD,"tab_CommandeSelected")
		
		KW.scrollAndSetText(myJDD, "ST_PRIDEL")
		KW.searchWithHelper(myJDD, "ID_CODPAI","","")
		//ST_DESID_CODPAI --> pas d'action en modification
		KW.scrollAndSetText(myJDD, "ST_NOTPRO")
		KW.searchWithHelper(myJDD, "ID_CODMOD","","")
		//ST_DESID_CODMOD --> pas d'action en modification
		KW.searchWithHelper(myJDD, "ID_CODDEV","","")
		//ST_DESID_CODDEV --> pas d'action en modification
		KW.searchWithHelper(myJDD, "ID_CODPOR","","")
		//ST_DESID_CODPOR --> pas d'action en modification
		KW.searchWithHelper(myJDD, "ID_CODEMB","","")
		//ST_DESID_CODEMB --> pas d'action en modification
		KW.scrollAndSetText(myJDD, "ST_REL")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_FIGCAT","O")
		
		MYLOG.addSTEPBLOCK("TEXTES COMMANDE")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS1")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS2")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS3")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS4")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS5")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS6")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_FIGCDE","O")

	MYLOG.addSTEPGRP("ONGLET NOTES")
	
		def JDD_Note = new JDD(JDDFiles.getFullName('RO.FOU'),'001A',GlobalVariable.CASDETESTENCOURS)
		
		KW.scrollAndClick(myJDD,"tab_Notes")
		KW.waitForElementVisible(myJDD,"tab_NotesSelected")
	
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		
		KW.scrollAndClick(myJDD,"ModifierNote")
		KW.delay(1)
		
		WebUI.switchToWindowIndex('1')
		
		if (KW.isElementPresent(myJDD,'frameNote', GlobalVariable.TIMEOUT)) {
			
			KW.switchToFrame(myJDD, 'frameNote')
			
			
			KW.setText(myJDD, 'textNote',JDD_Note.getStrData("OL_DOC"))
			
			WebUI.switchToDefaultContent()
			
			KW.scrollAndClick(myJDD,"BTN_ValiderEtFermerNote")
			WebUI.switchToWindowIndex('0')
		}
	
	  		 

		
	MYLOG.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		my.SQL.checkJDDWithBD(myJDD)
		
		my.SQL.checkJDDWithBD(JDD_Adr)
	
	MYLOG.addEndTestCase()
} // fin du if


