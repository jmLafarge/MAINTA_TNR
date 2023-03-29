import my.Log as MYLOG

Random random = new Random()

int maxj = random.nextInt(3) + 2

for (j in 1..maxj) {
	
	int maxi = random.nextInt(10)
	
	switch (maxi) {
		case 1 :
			MYLOG.addSTEPWARNING("Un warning pour tester")
			break
		case 2 :
			MYLOG.addSTEPFAIL("On vérifie le step fail")
			
			MYLOG.addDETAIL('un detail')
			MYLOG.addDETAIL('un autre detail')
			MYLOG.addDETAIL('et encore un autre')
		
			break
		case 3:
			MYLOG.addSTEPERROR("Attention c'est une erreur")
		case 4 :
			for (i in 1..2) MYLOG.addSTEPPASS("C'est un autre exemple de step $i")
			break
		case 5:
			MYLOG.addSTEPGRP('ONGLET TRUC MUCHE')
			for (i in 1..3) MYLOG.addSTEPPASS("Step $i encore un exemple")
			break
		case 6:
			MYLOG.addSTEP('VALIDATION')
			for (i in 1..4) MYLOG.addSTEPPASS("Encore des step  $i pour commentaire")
			break
		default:
			MYLOG.addSTEPPASS("On ajoute qq step ^pour faiure du texte")
			MYLOG.addDETAIL('un detail')

	}
}

