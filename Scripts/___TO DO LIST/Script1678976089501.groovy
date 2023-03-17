

/*
 * 
 * -----------------------------------------------------------------------------------------------------
 * FAIT
 * -----------------------------------------------------------------------------------------------------
 * 
	feat(CHECK PREREQUIS) Ajout du controle de l'ordre des champs des JDD /PREJDD dans les onglets FCT
	feat(CHECK PREREQUIS) Rechecher dans les JDD les PARA et renseigner infoBDD
	feat(TNR SEQUENCER) Ajout try catch
	refactor(KW) gestion case à cocher et verifyElementText suppression du try catch
	refactor(Some TC) acceptAlert suppression status WARNING 

 *
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *

	feat(CHECK PREREQUIS) Ajout du controle de l'ordre des champs des JDD /PREJDD dans l'onglet Info
	
	
	RESULT 
		renommer le fichier resultat avec la version et le nav
		faire un fichier recap pour les résumés de test
		c'est peut etre là qu'il faut mettre les détails des resumés des STEP
	
	
	JDD Ajouter un close() du fichier dans tous les TC --> créer un JDD.close()
	
	
	NOTE : le double click fonctionne de temps en temps avec Chrome 
	
	
		
	Traiter les valeurs des $DATESYS et $DATETIME

	DOC ORGANISATION DES JDD DE RÉFÉRENCE DANS LE CADRE DES TNR --> Stockage par version ? A préciser par JML

	
 * 
 * -----------------------------------------------------------------------------------------------------
 * CHECK PREREQUIS
 * -----------------------------------------------------------------------------------------------------
 * 
	   - ajouter les controle des PARAM_LIST_ALLOWED et des TAG_LIST_ALLOWED dans les controles JDD "CHECK PREREQUIS" plutot que dans le code des tests
	   
	  Le controle des PREREQUIS des JDD dans les PREJDD est fait mais il faudrait aussi  controler les PREREQUIS des PREJDD dans les PREJDD 
	  		--> voir si on peut utiliser le paramètrage PREREQUIS ou s'il faut mettre en place des regles, par exemple
	  		check ('PREJDD.RO.CAL','001.IDCODCAL','001A.ID_CODCAL')
	  		check ('PREJDD.RO.CAL','001A.IDCODCAL','001.ID_CODCAL')
	  
	  CREER UNE FONCTION POUR ça : --> je ne sais plus trop ce que c'est --> a vérifier si encore valable
	  		Vérifier que pour tous les PRE.XX.ICB.ID_ENTMOS='INT', les ICB.ID_CODMOS sont dans PRE.RO.ACT.ID_CODINT
	  		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_CODUTI sont dans dans PRE.RO.ACT.ID_CODINT
	  		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_NUMSOC sont dans dans PRE.XX.SOCIETE.ID_NUMSOC
	 
	  Verifir si les valeurs collent avec les types :  my.InfoBDD.getDATA_TYPE( myJDD.getDBTableName(), fieldName)
	  
	  
	   --> Dans les JDD, mettre en vert les cellules des attributs modifiés par rapport aux PREJDD pour les cas de tests MODIF
 *
 * -----------------------------------------------------------------------------------------------------
 * VRAC
 * -----------------------------------------------------------------------------------------------------
 * 
 * 
 	Penser à passer sur Selenium
	 
	Ajouter un récap des résultats de Test, par RESUME, par cas de test,  voir ce que fait Katalon
	
	Ajouter les step FAIL dans le xls en grouper sous le test ou dans un autre onglet --> avec les élements nécessaire pour le ticket
	
	Faire un script pour restaurer base + redémarrage server SQlL(ou autre pour pouvoir faire la sauvegarde)	
	
	REVOIR la suppression des emplacements par rapport à ST_DEF pour rendre automatique le truc
	
	Faire un script pour lancer les script SQL PREJDD dans l'ordre
	  
	  Faire un TC pour créer les JDD et les PREJDD, 
	  	- dans l'ordre des champs --> est-ce util ? --> faut il un controle de l'odre des champs ?
	 
	  
	  XLS : ajouter un controle sur open file
	  
	  LOG : integrer LOG4J
	  
	  LOG : Ajouter def var en para d'un DEBUG pour parser un map , un list,... --> pour une list.toListString() ou join mais c'est en ligne ... voir avec \n
	  
	  LOG : ajouter une fonction addTRACE(def fct, def para=null, String msg=null)
	  		this donne le package
	  		para est un Map de paramètre
	  		-> manque le nom de la fonction
	  
	  RESULT : faire un plan avec les STEPs
	  		si on veut lister les STEP dans le xls il faut peut être simplifiué
	  		si on veut indentifier les STEP pour comparer entr eplusieurs campagne il faut les identifier, par exemple CDT_nnnn_texte --> mais si on maj le texte :-(
	  		du coup faudrait peut être ajouter la durée
	  		
	  
	  TestListener : 
	  	beforeTestCase	: le besoin dans les differents cas 
	  	afterTestCase 	: dans le cas de test suite
	    
	   
	   
	   et si on faisait une liste de step à dérouler on pourrait se passer des script de cas de test, du style
			  click on Creer
			  set ID_CIDINT
			  delay(1)
			  set prenom
	  voir m^me donner un ordre dans le JDD --> ajout d'un mot clé STEPNUM
	  prévoir rajouter des step spécifique
	  		dans le JDD, comme des delay 
	  		dans du code s'il existe ....
	  		...
	  		
	  TABLE SERLOG log des server	et UTILOG 
	  	menu acteur > utilisateur Mainta > historique de connexion
	  	
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