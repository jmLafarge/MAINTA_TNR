

/*
 * -----------------------------------------------------------------------------------------------------
 * EN COURS
 * -----------------------------------------------------------------------------------------------------
 *
 * Dans le Log mettre plotot un total de step plutot que 12/12
 *
 *
 * 	REVOIR la suppression des emplacements par rapport à ST_DEF pour rendre automatique le truc
 * 
 * 	Ajouter une lecture du max table avant la validation de l'ajout dans le cas replaceSEQUENCIDInJDD pour vérifier qu'on a bien inserer qq chose
 * 
 *
 *
 * Inclure getDataLine dans getData
 *
 * Faire un defaut quand on met un nom dans le sequencer qui ne trouve rien
 *
 *
 * Faire une fonction pour suppression avec accept alert 3x --> pour eviter les FAIL des tentatives et mettre des warning ?
 * 
 * TESTER WebUI.getAttribute()  avant de setter un champ
 * 
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
 * 		Vérifier que pour tous les PRE.XX.ICB.ID_ENTMOS='INT', les ICB.ID_CODMOS sont dans PRE.RO.ACT.ID_CODINT
 * 		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_CODUTI sont dans dans PRE.RO.ACT.ID_CODINT
 * 		Vérifier que pour tous les PRE.XX.UTI.IDCODPRO='SOC', les UTI.ID_NUMSOC sont dans dans PRE.XX.SOCIETE.ID_NUMSOC
 *
 * Verifir si les valeurs collent avec les types :  my.InfoBDD.getDATA_TYPE( myJDD.getDBTableName(), fieldName)
 * 
 * 
 *  --> Dans les JDD, mettre en vert les cellules des attributs modifiés par rapport aux PREJDD pour les cas de tests MODIF
 *
 * -----------------------------------------------------------------------------------------------------
 * VRAC
 * -----------------------------------------------------------------------------------------------------
 * 
 * Faire un script pour lancer les script SQL PREJDD dans l'ordre
 * 
 * Faire un TC pour créer les JDD et les PREJDD, 
 * 	- dans l'ordre des champs --> est-ce util ? --> faut il un controle de l'odre des champs ?
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
*/