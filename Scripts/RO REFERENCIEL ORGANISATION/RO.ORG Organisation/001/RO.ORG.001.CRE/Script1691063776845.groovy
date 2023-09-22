import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP_NAV.goToURL_Creation_and_checkCartridge(1,'','SEL=0&OPERATION=NEW_SON')
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			STEP.click(0, myJDD, "tab_Organisation")
			STEP.verifyElementVisible(0, myJDD, "tab_OrganisationSelected")
			
			KW.scrollAndSetRadio(myJDD, "LblNU_TYP")
			STEP.scrollToPosition(0, 0)

			STEP.setText(0, myJDD, "ST_CODCOU")
			STEP.setText(0, myJDD, "ST_CODPERSGES")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			STEP.setText(0, myJDD, "ID_CODIMP")
			STEP.setText(0, myJDD, "ID_CODCAL")
			STEP.setText(0, myJDD, "ID_GESNIV")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_EXT", "O")
			STEP.setText(0, myJDD, "NU_EFF")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_AFF", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.click(0, myJDD, "tab_Adresses")
			STEP.verifyElementVisible(0, myJDD, "tab_AdressesSelected")
			
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_RAT", "O")
			
			

			
	switch (myJDD.getStrData('SCENARIO')) {

		case 'A2.1':

		break
		case 'A2.2.1':

		break
		case 'A2.3':

		break	
		case 'A2.4':
		
		break
	}
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData(),'','Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMGES',0)
	
		SQL.checkJDDWithBD(myJDD)
		
		if (!JDDKW.isNU(myJDD.getStrData('ID_CODSER')) && !JDDKW.isNULL(myJDD.getStrData('ID_CODSER'))) {
			
			JDD JDDSER = new JDD(JDDFileMapper.getFullnameFromModObj('RO.ORG'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			JDDSER.replaceSEQUENCIDInJDD('ID_CODSER',0)
			JDDSER.replaceSEQUENCIDInJDD('ID_NUMGES',0)
			
			SQL.checkJDDWithBD(JDDSER)
		}
		
	TNRResult.addEndTestCase()

} // fin du if



