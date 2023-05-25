

/*
 * 
 * -----------------------------------------------------------------------------------------------------
 * FAIT
 * -----------------------------------------------------------------------------------------------------
 * 
 * 

Rajout de CODARTAUTO dans RT.ART



 * 
 * -----------------------------------------------------------------------------------------------------
 * NOTE
 * -----------------------------------------------------------------------------------------------------
 * 
	PROJET SETTING 
	
		SMART WAIT disabled permet de supprimer le popup chrome, l'ajout de waitForPageLoad() et de delay à solutionner le probléme
	 
	PROFIL
	
		TIMEOUT à 1 ça passe, je mets 2
		
		
	UTILISATION de 
	
		L'utilisation de variable dans une boucle d'un map semble poser problème 
		Par exemple dans checkJDD.run() mettre myJDD = new my.JDD(fullName,null,null,false) --> provoque une erreur
		--> je ne sais pas pourquoi
		--> peut être parce que .each est une closure
		
	FOU
	
		cas des adresses --> la clé primaire ID_NUMADR n'est pas présente dans le table ! 
		 - je ne peux pas savoir de quelle adresse il s'agit
		 - apparement le javascript utilise .getSelectedRowId() sur boutonModifier.click()
		
		
	TC et CDT
		
		si TestCase ZZ.YYY.001.CRE existe, il est utilisé pour tous les CDT ZZ.YYY.001.CRE.*
		si TestCase ZZ.YYY.001.CRE.01 existe, c'est lui qui sera utilisé pour le CDT correspondant
		
		
		
	ANNOTATION
	
		import groovy.transform.CompileStatic et @CompileStatic sur une class (ou une fonction)
			--> active les controle à la compilation
			
		import groovy.transform.CompileDynamic et @CompileDynamic sur une class (ou une fonction) 
			indique que la méthode ou la classe doit être compilée dynamiquement plutôt que statiquement
			même si la compilation statique est activée pour la classe parente.
		
		
		
 *
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *


 
 Ajouter les DES dans ART avec JDD Generator listRubriquesIHM
 
 ST_TYPART
 	modifier verifyOptionSelectedByValue pour prendre en compte un Map avec les valeurs/valeurs interne OU ajouter dans foreing key
 
 

 
 * 
 * -----------------------------------------------------------------------------------------------------
 * A FAIRE EN PRIORITE
 * -----------------------------------------------------------------------------------------------------
 * 


		  		
		
	

	DOC ORGANISATION DES JDD DE RÉFÉRENCE DANS LE CADRE DES TNR --> Stockage par version ? A préciser par JML





	
 * 
 * -----------------------------------------------------------------------------------------------------
 * EVOLUTION
 * -----------------------------------------------------------------------------------------------------
 * 
	
	GLOBALE VARIABLES
		 Remplacer les GlobalVariable.CASDETESTENCOURS  et GlobalVariable.CASDETESTPATTERN --> voir ou les mettre
		 voir aussi les autres variables ?
	
	CONTROLE
	
		Préférer les imports vs appel direct de type my.XLS ....
	
	
	HEADLESS 
	
		Chrome  : marche pas, par exemple le click sur a_Habilitation (voir pour prendre une capture écran)
		Firefox : marche mais pas le double click 
	
	
	JDD
		créer une class pour parametre
		
	SQL
		Traitement les valeurs des $DATESYS et $DATETIME --> voir pour ajouter un ctrl que la valeur soit autour de la datetime du moment voir Tools.getDurationFromNow


	XLS
		ajouter un controle sur open file
		Verifier si XLS.open est pertinant ! car il faut normalement fermer la connexion
		JDD et autre : Ajouter un close() du fichier dans tous les TC --> créer un JDD.close()
		
		
	CHECK PREREQUIS 
	
		Ajouter un ctrl sur les prérequis des prérequis
		
		J'ai ajouté le Check_CAL --> faire pareil avec les autres chck spécifique , comme par exe les org/ser et inter...
	  
		Ajouter les controle des PARAM_LIST_ALLOWED dans les controles JDD "CHECK PREREQUIS" plutot que dans le code des tests
		
		Ajouter un ctrl sur les valeurs de chaque paramètre ex : 
			pour PREREQUIS --> OBSOLETE ou ?*?*?
			pour LOCATOR --> c'est déjà fait
		  
		Dans les JDD, mettre en vert les cellules des attributs modifiés par rapport aux PREJDD pour les cas de tests MODIF
		
		ctrl des prerequis sur les paires de données, par ex int_met
		  
		  
	RESULT
	
		groupDetail pose problème, les lignes jusquà 595 sont masquées !? --> mis en commentaire
	 
		Sortir la gestion des STEP et du Status de Log
			- Mettre status dans une class à part
			- Mettre la gestion des STEPs dans Result --> ou à part ?
			
		Ajouter les step FAIL dans le xls en grouper sous le test ou dans un autre onglet --> avec les élements nécessaire pour le ticket
		
		Ou faire un plan(regrouper) avec les STEPs
	  		si on veut lister les STEP dans le xls il faut peut être simplifiué
	  		
	  	modif le step de ctrl en BDD pour faire une ligne + details avec un refrech de la ligne du dessus comme ....
	  	
	
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
	
		Sortir la gestion des STEP et du Status
		
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