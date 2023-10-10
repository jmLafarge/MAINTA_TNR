
/*
*
* -----------------------------------------------------------------------------------------------------
* FAIT
* -----------------------------------------------------------------------------------------------------
*
*


	COMMIT MARCHE PLUS




Ajout DevOpsManager
Ajout de test Azure read
Ajout de test Azure create




/*
*
* -----------------------------------------------------------------------------------------------------
* A FAIRE EN PRIORITE
* -----------------------------------------------------------------------------------------------------
*

/!\ TEST sur LEGACY TNR le navigateur ne répond plus au bout d'environ 5 min 
	--> j'ai l'impression que cela arrive quand je suis en Wifi (chez moi et a l'apave)



	

 
*
* -----------------------------------------------------------------------------------------------------
* EN COURS
* -----------------------------------------------------------------------------------------------------
*
*

RESULTAT des tests :

	RT.MAT.001.CRE.04 05 06 07: ST_AUT est dans l'onglet Etat





Pas faire de doubleClick si la valeur est bonne
--> faire une fonction pour gerer les modif dans les tableaus ?
--> Voir la différence avec SRA !?

   

 

 * 
 * -----------------------------------------------------------------------------------------------------
 * NOTE
 * -----------------------------------------------------------------------------------------------------
 * 
	
	KATALON 
	
		Action > Advenced search : Pour rechercher WUI.delay(*,* avec 'regular expression' :; WUI\.delay\(.*,\.*
 
	
	
	NE PAS OUBLIER DE CHANGER LE BD OWNER APRES UNE RESTAURATION 
	
		20230801 --> pas sur que ce soit utile avec la restauration scriptée, cela semble fonctionner sans !?
	
	
	PROJET SETTING 
	
		SMART WAIT disabled permet de supprimer le popup chrome, l'ajout de waitForPageLoad() et de delay à solutionner le probléme
		
	 
	PROFIL
	
		TIMEOUT à 1 ça passe, je mets 2
		
		
	UTILISATION de varaible dans une closure de Map
	
		L'utilisation de variable dans une boucle d'un map semble poser problème 
		Par exemple dans checkJDD.run() mettre myJDD = new JDD(fullName,null,null,false) --> provoque une erreur
		--> je ne sais pas pourquoi
		--> peut être parce que .each est une closure
		
		
	FOU
	
		cas des adresses --> la clé primaire ID_NUMADR n'est pas présente dans le table ! 
		 - je ne peux pas savoir de quelle adresse il s'agit
		 - apparement le javascript utilise .getSelectedRowId() sur boutonModifier.click()
		
		
	TC et CDT
		
		si TestCase ZZ.YYY.001.CRE existe, il est utilisé pour tous les CDT ZZ.YYY.001.CRE.*
		si TestCase ZZ.YYY.001.CRE.01 existe, c'est lui qui sera utilisé pour le CDT correspondant
		
		
		
		
	ST_TYPART
 	
 		La valeur stockée en base est le texte (traduit) pas la valeur interne définit dans les paramètres fonctionnels
 		--> je laisse comme ça pour générer l'erreur
	
	
	Contrôle des PREREQUIS des JDD
	
		Controle de 'ID_CODSER' dans 'TNR_PREJDD\PREJDD.RO.ORG.xlsx' (001A) 'ST_DES'
		- 'RO.ORG.001.CRE.02' - 'ORG.RO.ORG.001.CRE.02' non trouvé !
		- 'RO.ORG.001.CRE.04' - 'ORG.RO.ORG.001.CRE.04' non trouvé !
		--> due au fait que ces valeurs seront créées en même temps que l'organisation
		--> je ne sais pas comment les différencier des PREJDD
		
		
	ATTETION, il y a 2 return dans la class SQL.checkValue
	
	
	BUG KATALON
	
		La fonction WebUI.verifyElementInViewport(tObj, timeout, FailureHandling.CONTINUE_ON_FAILURE) semble ne pas toujours attendre le timeout !
	



	
 * 
 * -----------------------------------------------------------------------------------------------------
 * EVOLUTION
 * -----------------------------------------------------------------------------------------------------
 * 

		
		
	CLASS TO 
	
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		en fait on pourrait avoir TestObject tObj  = TO.make(myJDD,name) ;String msgTO = TO.getMsg() avec TO comme class static
		
	
	TEST CASE 
	
		déterminer le traitement en fonction du type de locator (input, radio, ...)
			si input --> STEP.setText(myJDD, "ST_ fois --> voir Suppr MATDES")
			si radio --> STEP.setRadio(myJDD, "LblNU_TYP")
			...
			
		voir si on strap certains steps en cas d'erreur (par exemple si une fenetre de recherche ne s'ouvre pas, le reste plante)
		car ça prend du temps à cause des timeout
			- soit ne pas faire les steps si FAIL (du coup on n'aura pas le nombre de step habituel)
			- soit les passer rapidement (agir sur le timeout ?)
	
	
	GLOBALE VARIABLES
		 Remplacer les GlobalVariable.CAS_DE_TEST_EN_COURS  et GlobalVariable.CAS_DE_TEST_PATTERN --> voir ou les mettre
		 voir aussi les autres variables ?
	
	
	
	HEADLESS 
	
		Chrome  : marche pas, par exemple le click sur a_Habilitation (voir pour prendre une capture écran)
		Firefox : marche mais pas le double click 
	
		
		
	SQL
		Traitement les valeurs des $DATESYS et $DATETIME --> voir pour ajouter un ctrl que la valeur soit autour de la datetime du moment voir Tools.getDurationFromNow


	XLS
		ajouter un controle sur open file
		Verifier si ExcelUtils.open est pertinant ! car il faut normalement fermer la connexion
		JDD et autre : Ajouter un close() du fichier dans tous les TC --> créer un JDD.close()
		
		
	CHECK  
	
		
		Ajouter les controle des PARAM_LIST_ALLOWED dans les controles JDD "CHECK PREREQUIS" plutot que dans le code des tests
		
		Ajouter un ctrl sur les valeurs de chaque paramètre ex : 
			pour PREREQUIS --> OBSOLETE ou ?*?*? --> FAIT voir dans getAllPrerequis
			pour FOREIGNKEY --> ?*?*?
			pour INTERNALVALUE --> ?*? et la valeur dans l'onglet INTERNALVALUE
			pour LOCATOR --> c'est déjà fait
		  
		Dans les JDD, mettre en vert les cellules des attributs modifiés par rapport aux PREJDD pour les cas de tests MODIF
		
		Vérifier ques les valeurs des PREJDD...LEC... soient égales aux valeurs des JDD...LEC...
		
		ctrl des prerequis sur les paires de données, par ex int_met
		
		Ajouter un check PREREQUIS spécifique pour RO.ORG en fonction de la valeur de NU_TYP et ST_AFF
			- car cela conditionne les créations de SOCIETE SER INTER et UTI
		
		Ajouter un check PREREQUIS spécifique pour ST_TRAUTICRE	ST_TRAUTIUPD	DT_TRACRE	DT_TRAUPD 
			Vérifier que pour tous les cdt : ST_TRAUTICRE et DT_TRACRE soient renseignées
			Vérifier que pour tous les cdt LEC : ST_TRAUTIUPD et DT_TRAUPD soient renseignées
			Vérifier que pour tous les cdt MAJ : ST_TRAUTIUPD et DT_TRAUPD soient $NULL 
				--> permet de vérifier qu'ils soient bien renseignés après MAJ 
		  
	Check_CAL
	
		Revoir la class et la mettre à jour avec les nouvelles class JDDData par exemple
		Faire pareil avec d'autres chck spécifique , comme par exe les org/ser et inter...
		GES.ST_CODPERSGES doit être unique

	  	
	
	RESULT RECAP
		
		Ajouter un récap des résultats de Test, par RESUME, par cas de test,  voir ce que fait Katalon
		Si on veut indentifier les STEP pour comparer entr eplusieurs campagne il faut les identifier, par exemple CDT_nnnn_texte --> mais si on maj le texte :-(
	  		du coup faudrait peut être ajouter la durée  
	 
	 
	TEST SUITE
	
		Vérifier si fonctionne encore 

		  
	SELENIUM
	
		Penser à passer sur Selenium
	 

	
	
	
	EMP
		
		Revoir la suppression des emplacements par rapport à ST_DEF pour rendre automatique le truc
	

	LOG
	
		Ajouter des stats sur les logbegin....

* 
 * -----------------------------------------------------------------------------------------------------
 * IDEE : TESTER les valeurs max des varchar
 * -----------------------------------------------------------------------------------------------------
 * 

	Ajouter une colonne dans TNR Seque,cer pour dire de tester tous les varchar au max
	Ajouter automatiquement des car à tous les champs du JDD
		Peut etre choisir dans la colonne sequencer les car ou type de car à ajouter (ou  dans proprietes ou dans Profiles...)
		
	
	TEST des CAR SPE ? : Si on veut tester des car spé, quid des chmaps qui sont déjà au max 
	

	    
 * 
 * -----------------------------------------------------------------------------------------------------
 * IDEE : GENERATEUR DE Test Case
 * -----------------------------------------------------------------------------------------------------
 * 
	
		Faire une liste de step à dérouler dans le JDD (inventer un vocabulaire) on pourrait se passer des script de cas de test, du style
				  click on Creer
				  set ID_CIDINT
				  delay(1)
				  set prenom
		prévoir rajouter des step spécifique
		  		dans le JDD, comme des delay 
		  		dans du code s'il existe ....
		  		...
  		--> Ca me parait compliquer de traiter les tous cas spécifique
  	
  		
  		
  		
 * 
 * -----------------------------------------------------------------------------------------------------
 * IDEE : VERIFIER si le code est standard
 * -----------------------------------------------------------------------------------------------------
 * 	
  	
  	Lorsque j'écris les test case, vérifier si le rendu est conforme à un standard, par exemple gestion des grille, la séquence en BDD ...
  		

	

  		
  		
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