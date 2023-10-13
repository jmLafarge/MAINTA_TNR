
import tnrDevOps.DevOpsClient
import tnrDevOps.DevOpsManager


//DevOpsManager.updateWorkItem(16301, "System.Title", "NE PAS TRAITER - Test TNR New Title")
//DevOpsManager.updateWorkItem(16301, "System.History", "Toujours présent")

def updates = [
	"Microsoft.VSTS.TCM.ReproSteps": "Les étapes pour reproduire ont été mises à jour."
]

DevOpsClient.updateBug(16492, updates)

def updatesTask = [
	"System.Description": "Description de la tâche"
]

DevOpsClient.updateTask(16493, updatesTask) 

