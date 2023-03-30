

/*
 * 
 * -----------------------------------------------------------------------------------------------------
 * FAIT
 * -----------------------------------------------------------------------------------------------------
 * 


Log : Create folder if not exist
PREJDDFiles : Create folder if not exist
Result : Create folder if not exist
Result.addStep : update 
TCFiles : add getTCNameTitle and getAutoTitle
Log.addStartTestCase : add getTCNameTitle
Log.addSTEPERROR : add this.status.ERROR++


 * 
 * -----------------------------------------------------------------------------------------------------
 * NOTE
 * -----------------------------------------------------------------------------------------------------
 * 
	PROJET SETTING 
	
		SMART WAIT disabled permet de supprimer le popup chrome, l'ajout de waitForPageLoad() et de delay à solutionner le probléme
	 
	PROFIL
	
		TIMEOUT à 1 ça passe, je mets 2
 *
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *
	

	
	

 

 

 
 * 
 * -----------------------------------------------------------------------------------------------------
 * A FAIRE EN PRIORITE
 * -----------------------------------------------------------------------------------------------------
 * 
	
	
	JDDGenerator --> inclure la lecture du TCGenerator pour alimenter le LOCATOR du JDD
		
	Traiter les valeurs des $DATESYS et $DATETIME

	DOC ORGANISATION DES JDD DE RÉFÉRENCE DANS LE CADRE DES TNR --> Stockage par version ? A préciser par JML



	
 * 
 * -----------------------------------------------------------------------------------------------------
 * EVOLUTION
 * -----------------------------------------------------------------------------------------------------
 * 
	
	HEADLESS 
	
		Chrome  : marche pas, par exemple le click sur a_Habilitation (voir pour prendre une capture écran)
		Firefox : marche mais pas le double click 
	
	
	A TESTER takeScreenshot
		
		gReportPathway = ".\\Reports\\Screenshots\\RT 030 RPCS\\"
		WebUI.takeScreenshot(gReportPathway + "msg1.png")

 
 
	JDD
		créer une class pour makeTO ?

	XLS
		ajouter un controle sur open file
		Verifier si XLS.open est pertinant ! car il faut normalement fermer la connexion
		JDD et autre : Ajouter un close() du fichier dans tous les TC --> créer un JDD.close()
		
		
	CHECK PREREQUIS 
	  
		Ajouter les controle des PARAM_LIST_ALLOWED et des TAG_LIST_ALLOWED dans les controles JDD "CHECK PREREQUIS" plutot que dans le code des tests
		
		AJOUTER un ctrl sur les PK il faut que tous les JDD "CDT + les PK" des JDD soit dans les PREJDD correspondant, SAUF pour CRE et SRA
		   
		Le controle des PREREQUIS des JDD dans les PREJDD est fait mais il faudrait aussi  controler les PREREQUIS des PREJDD dans les PREJDD 
		  		--> voir si on peut utiliser le paramètrage PREREQUIS ou s'il faut mettre en place des regles, par exemple
		  		check ('PREJDD.RO.CAL','001.IDCODCAL','001A.ID_CODCAL')
		  		check ('PREJDD.RO.CAL','001A.IDCODCAL','001.ID_CODCAL')
		  
		CREER UNE FONCTION POUR ça : --> je ne sais plus trop ce que c'est --> a vérifier si encore valable
		  		Vérifier que pour tous les PRE.XX.ICB.ID_ENTMOS='INT', les ICB.ID_CODMOS sont dans PRE.RO.ACT.ID_CODINT
		  		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_CODUTI sont dans dans PRE.RO.ACT.ID_CODINT
		  		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_NUMSOC sont dans dans PRE.XX.SOCIETE.ID_NUMSOC
		 
		Verifir si les valeurs collent avec les types :  my.InfoBDD.getDATA_TYPE( myJDD.getDBTableName(), fieldName)
		  
		Dans les JDD, mettre en vert les cellules des attributs modifiés par rapport aux PREJDD pour les cas de tests MODIF
		
		
		  
		  
	RESULT
	 
		renommer le fichier resultat avec la version et le nav
		Ajouter les step FAIL dans le xls en grouper sous le test ou dans un autre onglet --> avec les élements nécessaire pour le ticket
		Ou faire un plan(regrouper) avec les STEPs
	  		si on veut lister les STEP dans le xls il faut peut être simplifiué
	
	RESULT RECAP
		
		Ajouter un récap des résultats de Test, par RESUME, par cas de test,  voir ce que fait Katalon
		Si on veut indentifier les STEP pour comparer entr eplusieurs campagne il faut les identifier, par exemple CDT_nnnn_texte --> mais si on maj le texte :-(
	  		du coup faudrait peut être ajouter la durée  
	 
	TEST SUITE
	
		Vérifier si fonctionne encore 

	TEST LISTENER 

	  	beforeTestCase	: Vérifier le besoin dans les differents cas 
	  	afterTestCase 	: dans le cas de test suite	  
		  
		  
	SELENIUM
	
		Penser à passer sur Selenium
	 
	
	PREJDD
	
		Faire un script pour lancer les script SQL PREJDD dans l'ordre	
	
	
	BDD

		Faire un script pour restaurer base + redémarrage server SQlL(ou autre pour pouvoir faire la sauvegarde)	
	
	
	EMP
		
		Revoir la suppression des emplacements par rapport à ST_DEF pour rendre automatique le truc
	

	LOG

		Revoir le DEBUG --> c'est pourri
		integrer LOG4J
  		Ajouter def var en para d'un DEBUG pour parser un map , un list,... --> pour une list.toListString() ou join mais c'est en ligne ... voir avec \n
  		ajouter une fonction addTRACE(def fct, def para=null, String msg=null)
  			this donne le package
  			para est un Map de paramètre
  			-> manque le nom de la fonction
	  


	    
 * 
 * -----------------------------------------------------------------------------------------------------
 * IDEE : GENERATEUR DE Test Case
 * -----------------------------------------------------------------------------------------------------
 * 
	
		Faire une liste de step à dérouler on pourrait se passer des script de cas de test, du style
				  click on Creer
				  set ID_CIDINT
				  delay(1)
				  set prenom
		voir m^me donner un ordre dans le JDD --> ajout d'un mot clé STEPNUM
		prévoir rajouter des step spécifique
		  		dans le JDD, comme des delay 
		  		dans du code s'il existe ....
		  		...
  	
  		
 * 
 * -----------------------------------------------------------------------------------------------------
 * INFO
 * -----------------------------------------------------------------------------------------------------
 * 

	  TABLE SERLOG log des server	et surtout UTILOG 
	  	menu acteur > utilisateur Mainta > historique de connexion
	  	manque la déconnexion
	  	
	  BTSYN table de lien entre BT et HISBT (historique)
	  BTEFF BT effacé (si on veut tester si un BT effacé est supprimé de BT et mis dans BTEFF
	  
	  HISCOM c'est l'historique des valeur du COM
	  
	  VER CURRENT 
	 	Pour les ver des dll : Propriété version du fichier ou version du produit 
	  
	  EQU CFO Fiche fonctionnelle
	  
	  EQU	CMA Obsolete n'est plus utilisé
	  
	  Ces valeurs peuvent aussi venir de la table organisation
		AT	INTER	Propriétaire	 
		MAT	INTER	Gestionnaire	 
		MAT	INTER	Exploitant	 
		MAT	INTER	Mainteneur
	  
	  BT Demande : texte libre en fait c'est un assistant à la saisie mais on ne le test pas 
	  
	  PREREQUIS On pourrait Ajouter controle BDD exemple pour ID_CODUTI BDD*INTER*ID_CODINT
	  		
	  		
	  
 * 
 * 
*/