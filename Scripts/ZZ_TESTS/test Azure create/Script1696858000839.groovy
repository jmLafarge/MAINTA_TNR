import tnrDevOps.DevOpsClient





//systemInfoValue.replaceAll(" ", "&nbsp;")
//println systemInfoValue


//DevOpsManager.create("NE PAS TRAITER - Test TNR5", "RO.ACT.001.MAJ.01 : Mise à jour Acteur", 'systemInfoValue', "zzc54d2091a1463cbf25fdce70e3d387079884208a9732367d5c946620557e9f")

def bugDetails = [
	"System.AreaPath": "Héraclès\\GMAO",
	"System.IterationPath": "Héraclès\\TNR - TEST",
	"System.Title": "NE PAS TRAITER - TEST TNR JML"
]

DevOpsClient.createBug(bugDetails)


def taskDetails = [
	"System.AreaPath": "Héraclès\\GMAO",
	"System.IterationPath": "Héraclès\\TNR - TEST",
	"System.Title": "NE PAS TRAITER - TACHE TEST"
]

DevOpsClient.createTask(taskDetails)