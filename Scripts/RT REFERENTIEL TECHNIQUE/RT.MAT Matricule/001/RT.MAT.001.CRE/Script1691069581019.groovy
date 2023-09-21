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
    NAV.goToURL_Creation_and_checkCartridge()
	


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
		
			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			KW.click(myJDD, "tab_Matricule")
			KW.isElementVisible(myJDD, "tab_MatriculeSelected")
			
			

			KW.setText(myJDD, "ID_NUMMAT")
			KW.setText(myJDD, "ST_NUMINV")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			KW.setText(myJDD, "ST_DES")
			KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP")
			KW.setText(myJDD, "NU_PRISTO")
			KW.setText(myJDD, "ID_CODART")		
			if(!JDDKW.isNULL(myJDD.getStrData('ID_CODMOY'))) {
				KW.setText(myJDD, "ID_CODMOY")
			}
			KW.setText(myJDD, "ID_CODGES")
			KW.setText(myJDD, "ID_NUMCRI")
			KW.setText(myJDD, "ID_CODIMP")
			KW.setText(myJDD, "ID_NUMGRO")
			KW.setText(myJDD, "ID_CODCOM")
			KW.setText(myJDD, "NU_USA")
			KW.setText(myJDD, "ID_CODCON")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_TEC", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_PAT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			KW.click(myJDD, "tab_Fiche")
			KW.isElementVisible(myJDD, "tab_FicheSelected")
			
			KW.setText(myJDD, "ID_CODFOUINI")
			KW.setText(myJDD, "ST_REFFOU")
			KW.setText(myJDD, "ID_CODCONSTR")
			KW.setText(myJDD, "ST_REFCON")
			KW.setDate(myJDD, "DT_ACH")
			KW.setDate(myJDD, "DT_FAC")
			KW.setText(myJDD, "NU_PRIACH")
			KW.setDate(myJDD, "DT_FINGAR")
			KW.setDate(myJDD, "DT_FINVIE")
			KW.setText(myJDD, "NU_PRIACT")
			KW.setText(myJDD, "NU_USAGAR")
			KW.setText(myJDD, "NU_FINUSA")
			KW.setText(myJDD, "ID_CODCAL")
			KW.setText(myJDD, "ID_CODGAR")
			KW.setText(myJDD, "ID_REFCOM") 
			KW.setText(myJDD, "ST_AFFCOM")
			KW.setText(myJDD, "ID_CODINTPRO")
			KW.setText(myJDD, "ID_CODINTGES")
			KW.setText(myJDD, "ID_CODINTEXP")
			KW.setText(myJDD, "ID_CODINTMAI")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRA", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_PRE", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INS", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			KW.click(myJDD, "tab_Notes")
			KW.isElementVisible(myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			KW.scrollToPositionAndWait(0, 0,1)
			KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
			
			
			
	// Pas de test avec ONGLET ETAT
			
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.click(NAV.myGlobalJDD,'button_Valider')
		
		//Vérifier que l’écran de mouvement technique est affiché et saisir:
		
		NAV.verifierEcranRUD(myJDD.getStrData(),'343')
		
		
			
		switch (myJDD.getStrData('SCENARIO')) {
	
			case 'C2.1':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.setText(myJDD, "EQU_CODLON",myJDD.getStrData('ID_NUMEQU'))
				break;
	
			case 'C2.2':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				KW.setText(myJDD, "MATID_CODMAG",myJDD.getStrData('ID_CODMAG'))
				break;
	
			case 'C2.3':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				KW.setText(myJDD, "MATID_CODFOU",myJDD.getStrData('ID_CODFOU'))
				break;
			case 'C2.4':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				KW.setText(myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
			case 'C3.2':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				KW.setText(myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
		}
			

		
		//2eme validation
		
		KW.click(myJDD,'button_Valider2')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		
		SQL.checkJDDWithBD(myJDD)
		SQL.checkJDDWithBD(JDD_Note)
		
	TNRResult.addEndTestCase()

} // fin du if



