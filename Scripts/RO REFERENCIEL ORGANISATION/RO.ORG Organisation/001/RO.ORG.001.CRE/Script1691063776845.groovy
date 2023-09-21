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
    NAV.goToURL_Creation_and_checkCartridge('','SEL=0&OPERATION=NEW_SON')
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			KW.click(myJDD, "tab_Organisation")
			KW.isElementVisible(myJDD, "tab_OrganisationSelected")
			
			KW.scrollAndSetRadio(myJDD, "LblNU_TYP")
			KW.scrollToPositionAndWait(0, 0,1)

			KW.setText(myJDD, "ST_CODCOU")
			KW.setText(myJDD, "ST_CODPERSGES")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			KW.setText(myJDD, "ST_DES")
			KW.setText(myJDD, "ID_CODIMP")
			KW.setText(myJDD, "ID_CODCAL")
			KW.setText(myJDD, "ID_GESNIV")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_EXT", "O")
			KW.setText(myJDD, "NU_EFF")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_AFF", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			KW.click(myJDD, "tab_Adresses")
			KW.isElementVisible(myJDD, "tab_AdressesSelected")
			
			
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
		
	    KW.click(NAV.myGlobalJDD,'button_Valider')
	
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



