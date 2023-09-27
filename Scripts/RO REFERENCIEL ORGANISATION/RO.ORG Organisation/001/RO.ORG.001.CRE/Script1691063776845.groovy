import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDKW
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
		
			STEP.simpleClick(0, myJDD, "tab_Organisation")
			STEP.verifyElementVisible(0, myJDD, "tab_OrganisationSelected")
			
			STEP.scrollAndSetRadio(0, myJDD, "LblNU_TYP")
			STEP.scrollToPosition('', 0, 0)

			STEP.setText(0, myJDD, "ST_CODCOU")
			STEP.setText(0, myJDD, "ST_CODPERSGES")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			STEP.setText(0, myJDD, "ID_CODIMP")
			STEP.setText(0, myJDD, "ID_CODCAL")
			STEP.setText(0, myJDD, "ID_GESNIV")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_EXT", "O")
			STEP.setText(0, myJDD, "NU_EFF")
			
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_AFF", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.simpleClick(0, myJDD, "tab_Adresses")
			STEP.verifyElementVisible(0, myJDD, "tab_AdressesSelected")
			
			
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_RAT", "O")
			
			

			
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
		
	    STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(0, myJDD.getStrData(),'','Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMGES',0)
	
		STEP.checkJDDWithBD(0, myJDD)
		
		if (!JDDKW.isNU(myJDD.getStrData('ID_CODSER')) && !JDDKW.isNULL(myJDD.getStrData('ID_CODSER'))) {
			
			JDD JDDSER = new JDD(JDDFileMapper.getFullnameFromModObj('RO.ORG'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			JDDSER.replaceSEQUENCIDInJDD('ID_CODSER',0)
			JDDSER.replaceSEQUENCIDInJDD('ID_NUMGES',0)
			
			STEP.checkJDDWithBD(0, JDDSER)
		}
		
	TNRResult.addEndTestCase()

} // fin du if



