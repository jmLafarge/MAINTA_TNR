package tnrCommon

import groovy.io.FileType
import groovy.transform.CompileStatic
import tnrLog.Log


@CompileStatic
class FileUtils {

	private static final String CLASS_NAME = 'FileUtils'


	static createFolderIfNotExist(String dir) {
		Log.addTraceBEGIN(CLASS_NAME,"createFolderIfNotExist",[dir:dir])
		File fdir = new File(dir)
		if (!fdir.exists()) {
			fdir.mkdirs()
			Log.addTrace("Création dossier")
		}else {
			Log.addTrace("Dossier existe déjà")
		}
		Log.addTraceEND(CLASS_NAME,"createFolderIfNotExist")
	}


	static void deleteFilesFromFolder(String filePath ) {
		Log.addTraceBEGIN(CLASS_NAME,"deleteFilesFromFolder",[filePath:filePath])
		def file = new File(filePath)
		if (file.exists()) {
			if (file.delete()) {
				Log.addTrace("Le fichier a été supprimé avec succès.")
			} else {
				Log.addTrace("Impossible de supprimer le fichier.")
			}
		} else {
			Log.addTrace( "Le fichier n'existe pas.")
		}
		Log.addTraceEND(CLASS_NAME,"deleteFilesFromFolder")
	}


	/**
	 * Supprime les fichiers correspondant à une expression régulière dans un dossier spécifié.
	 *
	 * @param fileRegex  L'expression régulière pour filtrer les noms de fichiers à supprimer.
	 *                  Exemples d'expressions régulières :
	 *                  - Supprimer tous les fichiers qui commencent par "backup_", suivis de 8 chiffres, et se terminent par ".bak" :
	 *                    "backup_\\d{8}\\.bak"
	 *                  - Supprimer tous les fichiers qui commencent par "image" ou "photo", suivis de n'importe quel caractère, et se terminent par ".jpg" ou ".png" :
	 *                    "(image|photo).*\\.(jpg|png)"
	 *                  - Supprimer tous les fichiers dont le nom est exactement "logfile.log" (correspondance exacte) :
	 *                    "logfile\\.log"
	 *                  - Supprimer tous les fichiers qui contiennent "-old" ou "-backup" dans leur nom :
	 *                    ".*(-old|-backup).*"
	 *                  - Supprimer tous les fichiers dont le nom commence par une lettre majuscule, suivie de lettres minuscules et de chiffres, et se termine par ".txt" :
	 *                    "[A-Z][a-z0-9]*\\.txt"
	 *
	 * @param folderPath Le chemin du dossier où supprimer les fichiers.
	 * 
	 * @return Un message de statut indiquant le résultat de l'opération de suppression.
	 */
	static void deleteFilesFromFolder(String fileRegex , String folderPath) {
		Log.addTraceBEGIN(CLASS_NAME,"deleteFilesFromFolder",[fileRegex:fileRegex,folderPath:folderPath])
		def folder = new File(folderPath)
		//def regexPattern = Pattern.compile(fileRegex)
		if (folder.exists() && folder.isDirectory()) {
			folder.eachFileMatch (FileType.FILES,~/${fileRegex}/) { File file ->
				if (file.delete()) {
					Log.addTrace("Fichier '${file.name}' supprimé")
				}else {
					Log.addTrace("Impossible de supprimer le fichier '${file.name}'")
				}
			}
		} else {
			Log.addTrace("Le dossier spécifié n'existe pas ou n'est pas un dossier valide.")
		}
		Log.addTraceEND(CLASS_NAME,"deleteFilesFromFolder")
	}


	static List <String> getFilesFromFolder( String fileRegex , String folderPath) {
		Log.addTraceBEGIN(CLASS_NAME,"getFilesFromFolder",[fileRegex:fileRegex,folderPath:folderPath])
		List <String> ret = []
		def folder = new File(folderPath)
		if (folder.exists() && folder.isDirectory()) {
			folder.eachFileMatch (FileType.FILES,~/${fileRegex}/) { File file ->
				ret.add(file.name)
				Log.addTrace("Ajout du fichier '${file.name}'")
			}
		} else {
			Log.addTrace("Le dossier spécifié n'existe pas ou n'est pas un dossier valide.")
		}
		Log.addTraceEND(CLASS_NAME,"getFilesFromFolder",ret)
		return ret
	}


} // Fin de class
