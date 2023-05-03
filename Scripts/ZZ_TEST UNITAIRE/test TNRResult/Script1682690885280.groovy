

import my.result.TNRResult


Map <String,Integer> status = ['WARNING':0,'FAIL':0,'PASS':10,'ERROR':0]


TNRResult.addStartInfo('start info')

Date startDate = new Date()


for (n in 1..100) {
	int x = new Random().nextInt(20) + 3
	TNRResult.addStartTestCase("Cas de test $n")
	
	for (i in 1..x) {

		switch (new Random().nextInt(50)) {
		
		case 0: TNRResult.addSTEPWARNING("un warning"); break;
		case 1: TNRResult.addSTEPFAIL("un fail"); break;
		case 2: TNRResult.addSTEPPASS("un pass"); break;
		case 3: TNRResult.addSTEPERROR("un error"); break;
		case 4: 
		case 10 :
			TNRResult.addSTEPWARNING("un warning")
			int id = new Random().nextInt(5) + 1
			for (a in 1..id) {
				TNRResult.addDETAIL("un détail")
			}
			
			break;
		case 5: 
		case 11 :
			TNRResult.addSTEPFAIL("un fail")
			int id = new Random().nextInt(5) + 1
			for (a in 1..id) {
				TNRResult.addDETAIL("un autre détail")
			}
			break;
		case 6: 
		case 12 :
			TNRResult.addSTEPPASS("un pass")
			break;
		case 7: 
			TNRResult.addSTEPERROR("un error")
			int id = new Random().nextInt(5) + 1
			for (a in 1..id) {
				TNRResult.addDETAIL("encore un détail")
			}
			break;
		default :TNRResult.addSTEPPASS("encore des pass"); break;		
		}
	}
	TNRResult.addEndTestCase()
}

TNRResult.close('FIN')
