import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; 
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrWebUI.*



// Lecture du JDD
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLCreate('','SEL=0&OPERATION=NEW_SON')
	STEP.checkCreateScreen()

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			STEP.simpleClick(myJDD, "tab_Organisation")
			STEP.verifyElementVisible(myJDD, "tab_OrganisationSelected")
			
			STEP.setRadio(myJDD, "LblNU_TYP")
			STEP.scrollToPosition( 0, 0)

			STEP.setText(myJDD, "ST_CODCOU")
			STEP.setText(myJDD, "ST_CODPERSGES")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(myJDD, "ST_DES")
			STEP.setText(myJDD, "ID_CODIMP")
			STEP.setText(myJDD, "ID_CODCAL")
			STEP.setText(myJDD, "ID_GESNIV")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_EXT", "O")
			STEP.setText(myJDD, "NU_EFF")
			
			STEP.clickCheckboxIfNeeded(myJDD, "ST_AFF", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.simpleClick(myJDD, "tab_Adresses")
			STEP.verifyElementVisible(myJDD, "tab_AdressesSelected")
			
			
			STEP.clickCheckboxIfNeeded(myJDD, "ST_RAT", "O")
			
			

			
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
		
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData(),'','Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMGES',0)
	
		STEP.checkJDDWithBD(myJDD)
		
		if (!JDDKW.isNU(myJDD.getStrData('ID_CODSER')) && !JDDKW.isNULL(myJDD.getStrData('ID_CODSER'))) {
			
			JDD JDDSER = new JDD(JDDFileMapper.getFullnameFromModObj('RO.ORG'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			JDDSER.replaceSEQUENCIDInJDD('ID_CODSER',0)
			JDDSER.replaceSEQUENCIDInJDD('ID_NUMGES',0)
			
			STEP.checkJDDWithBD(JDDSER)
		}
		
	TNRResult.addEndTestCase()

} // fin du if



