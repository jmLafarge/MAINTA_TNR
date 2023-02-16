

/*
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *
 * 
 * ID_NUMZON c'est 0 par déaut
 * 
 *
 * recharger JDD.RO.ACT sur DRIVE --> ATTENTION au ROUGE
 * 
 * Pour les checkbox il faut passer par le label --> voir ST_GES
 * mettre à jour les autres CB dans JDD RO
 * 
 * on arrive pas à afficher les INTER  --> c'est ST_ETA = A c'est KO il faut mettre DEFAUT
 * 	- j'ai corrigé les CLA et CALDEF (défaut sur le format date heure) mais c'est pas ça	
 * 
 * $Identifiant technique dans JDD RO ACT 004EMP --> l création d'un emplacement crée qq chose ? ou c'est plutot l'utilisation dans ACTEUR !?
 * 
 * 
 * 
 * -----------------------------------------------------------------------------------------------------
 * CHECK PREREQUIS
 * -----------------------------------------------------------------------------------------------------
 * 
 *  - ajouter les controle des PARAM_LIST_ALLOWED et des TAG_LIST_ALLOWED dans les controles JDD "CHECK PREREQUIS" plutot que dans le code des tests
 *  
 * Le controle des PREREQUIS des JDD dans les PREJDD est fait mais il faudrait aussi  controler les PREREQUIS des PREJDD dans les PREJDD 
 * 		--> voir si on peut utiliser le paramètrage PREREQUIS ou s'il faut mettre en place des regles, par exemple
 * 		check ('PREJDD.RO.CAL','001.IDCODCAL','001A.ID_CODCAL')
 * 		check ('PREJDD.RO.CAL','001A.IDCODCAL','001.ID_CODCAL')
 * 
 * CREER UNE FONCTION POUR ça : --> je ne sais plus trop ce que c'est --> a vérifier si encore valable
 * 
 * 		Vérifier que pour tous les PRE.XX.ICB.ID_ENTMOS='INT', les ICB.ID_CODMOS sont dans PRE.RO.ACT.ID_CODINT
 * 		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_CODUTI sont dans dans PRE.RO.ACT.ID_CODINT
 * 		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_NUMSOC sont dans dans PRE.XX.SOCIETE.ID_NUMSOC
 *
 *
 *
 * -----------------------------------------------------------------------------------------------------
 * VRAC
 * -----------------------------------------------------------------------------------------------------
 * 
 * 
 * - controler les cle primaire depuis infoBDD.xlsx									--> fait
 * - controler les mot clé $... dans les JDD										--> fait 
 * - controler les mot clé $... dans les PREJDD										 
 * - controler si tous les champs infoBDD sont dans les headers de JDD  			--> fait voir pour faire l'ordre
 * - controler si tous les champs infoBDD sont dans les headers de PREJD 			--> fait voir pour faire l'ordre
 * - ajouter OBSOLETE dans les PREREQUIS............................................--> fait uniquement dans getAllPrerequis() pour éviter de la prendre comme FK 
 * 
 * - Faire un TC pour créer les JDD et les PREJDD, dans l'ordre des champs --> est-ce util ? --> faut il un controle de l'odre des champs ?
 *
 * 
 * InfoBDD : remplacer les getAt pour les noms de colonne
 * 
 * XLS : ajouter un controle sur open file
 * 
 * //private static final List COLUMNNAME	 = ['TABLE_NAME','COLUMN_NAME','ORDINAL_POSITION','IS_NULLABLE','DATA_TYPE','MAXCHAR','DOMAIN_NAME','CONSTRAINT_NAME']
 * 
 * 
 * 
 * voir pour faire une class MOTCLE ou KEYWORD pour les $... des JDD
 * 
 * LOG : Ajouter def var en para d'un DEBUG pour parser un map , un list,...
 * LOG : faire le log à 0 et faire un autre à 1 2 ou 3
 * 
 * 
 * 
 *     
 * vérifier les steps de HAB
 * 
 * Vérification en BD de HAB
 * 
 * 
 * voir pour simplifier JDD car il n'y a plus qu'un seul casDeTest (avec éventuellement plusieurs ligne, comme habilitation)
 * 
 * Faire une fonction pour tester si $VIDE plutot que my.PropertiesReader.getMyProperty('JDD_KEYWORD_VIDE')
 * 
 * 
 * 
 * voir pourquoi WebUI.getText(tObj) ne fonctionne pas dans verifyElementText()  ---> voir Favori Katalon
 * 
 * 
 * 
*/