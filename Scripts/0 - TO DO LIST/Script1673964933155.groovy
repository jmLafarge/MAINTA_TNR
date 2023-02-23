

/*
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *
 *
 *
 *
 *
 *
 *
 * Faire une fonction pour suppression avec accept alert 3x --> pour eviter les FAIL des tentatives et mettre des warning ?
 * 
 * TESTER WebUI.getAttribute(  avant de setter un champ
 * 
 *  
 *
 * recharger JDD.RO.ACT sur DRIVE --> ATTENTION au ROUGE
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
 * Verifir si les valeurs collent avec les types :  my.InfoBDD.getDATA_TYPE( myJDD.getDBTableName(), fieldName)
 *
 * -----------------------------------------------------------------------------------------------------
 * VRAC
 * -----------------------------------------------------------------------------------------------------
 * 
 * 
 * - Faire un TC pour créer les JDD et les PREJDD, dans l'ordre des champs --> est-ce util ? --> faut il un controle de l'odre des champs ?
 *
 * 
 * XLS : ajouter un controle sur open file
 * 
 * LOG : Ajouter def var en para d'un DEBUG pour parser un map , un list,...
 * LOG : faire le log à 0 et faire un autre à 1 2 ou 3
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
*/