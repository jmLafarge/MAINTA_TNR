

/*
 * 
 * -----------------------------------------------------------------------------------------------------
 * FAIT
 * -----------------------------------------------------------------------------------------------------
 * 

	Pour simplifier l'écriture du code de test, passer le myJDD et le code du champs aux fonction KW 
	 AS IS : KW.scrollAndSetText(myJDD.makeTO('ID_CODINT'), myJDD.getStrData('ID_CODINT'))
	 TO BE : KW.scrollAndSetText(myJDD,'ID_CODINT')
	 		 
	 AS IS : scrollAndSetText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')
	 TO BE : scrollAndSetText(my.JDD myJDD, String name, String text, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')
	
	Mofif
		reprise de toutes les definitions de KW
		reprise NAV
		reprise de tous les TC


 *
 *
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *

	Création d'un générateur de JDD/PREJDD
		
		
	Pour la version, voir la table ver



	Traiter les valeurs des $DATESYS et $DATETIME
	
	
	Voir les FAIL des O/N dasn RO.ACT.001.MAJ.01
	
[2023-03-10 11:38:16.111][-------]:                   - Contrôle de la valeur de ST_PRIPRE KO : la valeur attendue est : O et la valeur en BD est : N --> apparement j'ai coché mais pas enregistré
[2023-03-10 11:38:16.114][-------]:                   - Contrôle de la valeur de ST_DEM KO : la valeur attendue est : O et la valeur en BD est : N
[2023-03-10 11:38:16.116][-------]:                   - Contrôle de la valeur de ST_INT KO : la valeur attendue est : O et la valeur en BD est : N
[2023-03-10 11:38:16.117][-------]:                   - Contrôle de la valeur de ST_ACH KO : la valeur attendue est : O et la valeur en BD est : N
	
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
	  LOG : Ajouter def var en para d'un DEBUG pour parser un map , un list,...
	  LOG : faire le log à 0 et faire un autre à 1 2 ou 3
	  
	  RESULT : faire un plan avec les STEPs
	  
	  
	    
	   
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
 * 
 * 
*/