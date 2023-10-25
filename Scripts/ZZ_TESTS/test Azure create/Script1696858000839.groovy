import tnrDevOps.DevOpsClient
import tnrDevOps.DevOpsTask





//systemInfoValue.replaceAll(" ", "&nbsp;")
//println systemInfoValue


//DevOpsManager.create("NE PAS TRAITER - Test TNR5", "RO.ACT.001.MAJ.01 : Mise à jour Acteur", 'systemInfoValue', "zzc54d2091a1463cbf25fdce70e3d387079884208a9732367d5c946620557e9f")

def bugDetails = [
	"/fields/System.Title": "NE PAS TRAITER - TEST TNR JML"
]

//DevOpsClient.createBug(bugDetails)




DevOpsTask.createWorkItem('Le title', 'La description')

String filePath = "TNR_Resultat/20231018_084512/20231018_084512_TNR_MSSQL_Firefox_Mainta_V13.0.4.xlsx" 
DevOpsTask.addFileToTask(filePath)


