import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
		
			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			KW.scrollAndClick(myJDD, "tab_Matricule")
			KW.waitForElementVisible(myJDD, "tab_MatriculeSelected")
			
			

			KW.scrollAndSetText(myJDD, "ID_NUMMAT")
			KW.scrollAndSetText(myJDD, "ST_NUMINV")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			KW.scrollAndSetText(myJDD, "ST_DES")
			KW.scrollAndSelectOptionByValue(myJDD, "ST_ETA")
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP")
			KW.scrollAndSetText(myJDD, "NU_PRISTO")
			KW.scrollAndSetText(myJDD, "ID_CODART")
			
			if(!JDDKW.isNULL(myJDD.getStrData('ID_CODMOY'))) {
				KW.scrollAndSetText(myJDD, "ID_CODMOY")
			}
			
			KW.scrollAndSetText(myJDD, "ID_CODGES")
			KW.scrollAndSetText(myJDD, "ID_NUMCRI")
			KW.scrollAndSetText(myJDD, "ID_CODIMP")
			KW.scrollAndSetText(myJDD, "ID_NUMGRO")
			KW.scrollAndSetText(myJDD, "ID_CODCOM")
			KW.scrollAndSetText(myJDD, "NU_USA")
			KW.scrollAndSetText(myJDD, "ID_CODCON")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_TEC", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			KW.scrollAndClick(myJDD, "tab_Fiche")
			KW.waitForElementVisible(myJDD, "tab_FicheSelected")
			
			KW.scrollAndSetText(myJDD, "ID_CODFOUINI")
			KW.scrollAndSetText(myJDD, "ST_REFFOU")
			KW.scrollAndSetText(myJDD, "ID_CODCONSTR")
			KW.scrollAndSetText(myJDD, "ST_REFCON")
			KW.scrollAndSetDate(myJDD, "DT_ACH")
			KW.scrollAndSetDate(myJDD, "DT_FAC")
			KW.scrollAndSetText(myJDD, "NU_PRIACH")
			KW.scrollAndSetDate(myJDD, "DT_FINGAR")
			KW.scrollAndSetDate(myJDD, "DT_FINVIE")
			KW.scrollAndSetText(myJDD, "NU_PRIACT")
			KW.scrollAndSetText(myJDD, "NU_USAGAR")
			KW.scrollAndSetText(myJDD, "NU_FINUSA")
			KW.scrollAndSetText(myJDD, "ID_CODCAL")
			KW.scrollAndSetText(myJDD, "ID_CODGAR")
			KW.scrollAndSetText(myJDD, "ID_REFCOM") 
			KW.scrollAndSetText(myJDD, "ST_AFFCOM")
			KW.scrollAndSetText(myJDD, "ID_CODINTPRO")
			KW.scrollAndSetText(myJDD, "ID_CODINTGES")
			KW.scrollAndSetText(myJDD, "ID_CODINTEXP")
			KW.scrollAndSetText(myJDD, "ID_CODINTMAI")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRA", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_PRE", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_INS", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			KW.scrollAndClick(myJDD, "tab_Notes")
			KW.waitForElementVisible(myJDD, "tab_NotesSelected")
			
			// A completer
			
			
			// Pas de test avec ONGLET ETAT
			
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
		
		//Vérifier que l’écran de mouvement technique est affiché et saisir:
		
		NAV.verifierEcranRUD(myJDD.getStrData(),'343')
		
		
			
		switch (myJDD.getStrData('SCENARIO')) {
	
			case 'C2.1':
				KW.scrollAndSetRadio(myJDD, "CHOIX")
				KW.scrollAndSetText(myJDD, "EQU_CODLON",myJDD.getStrData('ID_NUMEQU'))
				break;
	
			case 'C2.2':
				KW.scrollAndSetRadio(myJDD, "CHOIX")
				KW.scrollAndSetRadio(myJDD, "CHOIX2")
				KW.scrollAndSetText(myJDD, "MATID_CODMAG",myJDD.getStrData('ID_CODMAG'))
				break;
	
			case 'C2.3':
				KW.scrollAndSetRadio(myJDD, "CHOIX")
				KW.scrollAndSetRadio(myJDD, "CHOIX2")
				KW.scrollAndSetText(myJDD, "MATID_CODFOU",myJDD.getStrData('ID_CODFOU'))
				break;
			case 'C2.4':
				KW.scrollAndSetRadio(myJDD, "CHOIX")
				KW.scrollAndSetRadio(myJDD, "CHOIX2")
				KW.scrollAndSetText(myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
			case 'C3.2':
				KW.scrollAndSetRadio(myJDD, "CHOIX")
				KW.scrollAndSetRadio(myJDD, "CHOIX2")
				KW.scrollAndSetText(myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
		}
			

		
		//2eme validation
		
		KW.scrollAndClick(myJDD,'button_Valider2')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',1)
		
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



