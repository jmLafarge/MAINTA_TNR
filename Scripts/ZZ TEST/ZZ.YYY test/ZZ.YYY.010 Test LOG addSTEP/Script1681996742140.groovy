import myResult.TNRResult

Random random = new Random()

int maxj = random.nextInt(3) + 2

for (j in 1..maxj) {
	
	int maxi = random.nextInt(10)
	
	switch (maxi) {
		case 1 :
			TNRResult.addSTEPWARNING("Un warning pour tester")
			break
		case 2 :
			TNRResult.addSTEPFAIL("On vérifie le step fail")
			
			TNRResult.addDETAIL('un detail')
			TNRResult.addDETAIL('un autre detail')
			TNRResult.addDETAIL('et encore un autre')
		
			break
		case 3:
			TNRResult.addSTEPERROR("Attention c'est une erreur")
		case 4 :
			for (i in 1..2) TNRResult.addSTEPPASS("C'est un autre exemple de step $i")
			break
		case 5:
			TNRResult.addSTEPGRP('ONGLET TRUC MUCHE')
			for (i in 1..3) TNRResult.addSTEPPASS("Step $i encore un exemple")
			break
		case 6:
			TNRResult.addSTEPACTION('VALIDATION')
			for (i in 1..4) TNRResult.addSTEPPASS("Encore des step  $i pour commentaire")
			break
		default:
			TNRResult.addSTEPPASS("On ajoute qq step ^pour faiure du texte")
			TNRResult.addDETAIL('un detail')

	}
}

