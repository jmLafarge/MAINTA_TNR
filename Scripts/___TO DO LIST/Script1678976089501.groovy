

/*
 * 
 * -----------------------------------------------------------------------------------------------------
 * FAIT
 * -----------------------------------------------------------------------------------------------------
 * 

 Correction format date dans JDD Generator.addInfoVersion
 Suppression du screenshot sur msg = "Fin de la  vérification des valeurs en Base de Données"
 Ajout des liens vers screenshot
 Ajout du ctrl varchar dans CheckTypeInDATA.run()
 Ajout de fonction dans InfoBDD
 Traitement des memo dans RO.FOU
 en cours traitement de ADR

 * 
 * -----------------------------------------------------------------------------------------------------
 * NOTE
 * -----------------------------------------------------------------------------------------------------
 * 
	PROJET SETTING 
	
		SMART WAIT disabled permet de supprimer le popup chrome, l'ajout de waitForPageLoad() et de delay à solutionner le probléme
	 
	PROFIL
	
		TIMEOUT à 1 ça passe, je mets 2
		
		
	UTILISATION de this.
	
		L'utilisation de this.variable dans une boucle d'un map semble poser problème 
		Par exemple dans checkJDD.run() mettre this.myJDD = new my.JDD(fullName,null,null,false) --> provoque une erreur
		--> je ne sais pas pourquoi
		--> peut être parce que .each est une closure
		
		
 *
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *
 	
 RO.FOU.001.MAJ le cas des adresses
 - il faut revoir les binding pour les xpath dyn car le para dans makeTO ne sert plus ! vu que les TO sont créé depuis KW
 - mettre à jour le map xpath ne va pas car on perd le paramètrage
 - il faudrait un autre moyen de mettre à jour le xpath
 - il serit peut etre temps de sortie makeTO de JDD pour en faire une calass à part
 
 
 
 
 * 
 * -----------------------------------------------------------------------------------------------------
 * A FAIRE EN PRIORITE
 * -----------------------------------------------------------------------------------------------------
 * 
		
Les JDD et PREJDD sont modifiés en ajoutant le prefixe de l'objet depuis les codes des Sous ressources
--> mettre à jour la doc		
		  
		  		
		  		
		  		
	JDDGenerator --> inclure la lecture du TCGenerator pour alimenter le LOCATOR du JDD
		
	Traiter les valeurs des $DATESYS et $DATETIME

	DOC ORGANISATION DES JDD DE RÉFÉRENCE DANS LE CADRE DES TNR --> Stockage par version ? A préciser par JML



	
 * 
 * -----------------------------------------------------------------------------------------------------
 * EVOLUTION
 * -----------------------------------------------------------------------------------------------------
 * 
	
	CONTROLE
	
		Préférer les imports vs appel direct de type my.XLS ....
	
	
	HEADLESS 
	
		Chrome  : marche pas, par exemple le click sur a_Habilitation (voir pour prendre une capture écran)
		Firefox : marche mais pas le double click 
	
	

 
	JDD
		créer une class pour makeTO ?

	XLS
		ajouter un controle sur open file
		Verifier si XLS.open est pertinant ! car il faut normalement fermer la connexion
		JDD et autre : Ajouter un close() du fichier dans tous les TC --> créer un JDD.close()
		
		
	CHECK PREREQUIS 
	
		J'ai ajouté le Check_CAL --> faire pareil avec les autres chck spécifique , comme par exe les org/ser et inter...
	  
		Ajouter les controle des PARAM_LIST_ALLOWED et des TAG_LIST_ALLOWED dans les controles JDD "CHECK PREREQUIS" plutot que dans le code des tests
		 
		Verifir si les valeurs collent avec les types :  INFOBDD.getDATA_TYPE( myJDD.getDBTableName(), fieldName)
		Par exemple les numéric ne doivent pas être vide mais $NULL
		  
		Dans les JDD, mettre en vert les cellules des attributs modifiés par rapport aux PREJDD pour les cas de tests MODIF
		
		
		  
		  
	RESULT
	 
		Ajouter les step FAIL dans le xls en grouper sous le test ou dans un autre onglet --> avec les élements nécessaire pour le ticket
		Ou faire un plan(regrouper) avec les STEPs
	  		si on veut lister les STEP dans le xls il faut peut être simplifiué
	  	
	
	RESULT RECAP
		
		Ajouter un récap des résultats de Test, par RESUME, par cas de test,  voir ce que fait Katalon
		Si on veut indentifier les STEP pour comparer entr eplusieurs campagne il faut les identifier, par exemple CDT_nnnn_texte --> mais si on maj le texte :-(
	  		du coup faudrait peut être ajouter la durée  
	 
	TEST SUITE
	
		Vérifier si fonctionne encore 



		  
		  
	SELENIUM
	
		Penser à passer sur Selenium
	 

	
	
	BDD

		Faire un script pour restaurer base + redémarrage server SQlL(ou autre pour pouvoir faire la sauvegarde)	
	
	
	EMP
		
		Revoir la suppression des emplacements par rapport à ST_DEF pour rendre automatique le truc
	

	LOG

		Revoir le DEBUG --> c'est pourri 
		
		integrer LOG4J --> non pas avec Katalon
		
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