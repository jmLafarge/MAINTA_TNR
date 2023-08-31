import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFiles
import tnrJDDManager.JDDKW
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge('','SEL=0&OPERATION=NEW_SON')
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			KW.scrollAndClick(myJDD, "tab_Organisation")
			KW.waitForElementVisible(myJDD, "tab_OrganisationSelected")
			
			KW.scrollAndSetRadio(myJDD, "NU_TYP")

			KW.scrollAndSetText(myJDD, "ST_CODCOU")
			KW.scrollAndSetText(myJDD, "ST_CODPERSGES")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			KW.scrollAndSetText(myJDD, "ST_DES")
			KW.scrollAndSetText(myJDD, "ID_CODIMP")
			KW.scrollAndSetText(myJDD, "ID_CODCAL")
			KW.scrollAndSetText(myJDD, "ID_GESNIV")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_EXT", "O")
			KW.scrollAndSetText(myJDD, "NU_EFF")
			
			KW.scrollAndCheckIfNeeded(myJDD, "ST_AFF", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			KW.scrollAndClick(myJDD, "tab_Adresses")
			KW.waitForElementVisible(myJDD, "tab_AdressesSelected")
			
			
			KW.scrollAndCheckIfNeeded(myJDD, "ST_RAT", "O")
			
			

			
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData(),'','Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMGES',0)
	
		SQL.checkJDDWithBD(myJDD)
		
		if (!JDDKW.isNU(myJDD.getStrData('ID_CODSER')) && !JDDKW.isNULL(myJDD.getStrData('ID_CODSER'))) {
			
			JDD JDDSER = new JDD(JDDFiles.getFullnameFromModObj('RO.ORG'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			JDDSER.replaceSEQUENCIDInJDD('ID_CODSER',0)
			JDDSER.replaceSEQUENCIDInJDD('ID_NUMGES',0)
			
			SQL.checkJDDWithBD(JDDSER)
		}
		
	TNRResult.addEndTestCase()

} // fin du if



