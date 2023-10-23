package tnrTC

import groovy.transform.CompileStatic





/**
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
//@CompileStatic
public class Sequencer2 {


	private static boolean tryRunClass(String className, String packageName) {
		String truncatedClassName = className
		int truncatedChars = 0; // Compteur pour le nombre de caractères tronqués
		while (truncatedClassName  && truncatedClassName.length() > 1 && truncatedChars <= 3) {
			println "\tclassName:$truncatedClassName"
			try {
				Class<?> clazz = Class.forName("${packageName}.${truncatedClassName}")
				def obj = clazz.newInstance()

				if (obj.respondsTo('run')) {
					obj.run(className)
					return true  // Classe et méthode trouvées et exécutées
				}
			} catch (ClassNotFoundException e) {
				// Classe non trouvée, continue la boucle
			} catch (Exception e) {
				println("Erreur lors de l'instanciation ou de l'exécution de ${truncatedClassName}: ${e}")
			}

			// Tronquer le dernier caractère et réessayer
			truncatedClassName = truncatedClassName[0..-2]
			truncatedChars ++
		}

		return false  // Aucune classe trouvée
	}




	public static run(List classList ) {

		classList.each { className ->
			println "--- $className"
			className = className.replaceAll("\\.", "_")  // Remplacer les points par des underscores
			if (className.length() >= 2 && className.contains("_") ) {
				// Déterminer le nom du package à partir du nom de la classe
				def packageNameParts = className.split('_')[0..1]
				def packageName = "tnrTC.${packageNameParts.join('_')}"
	
				println "\tpackageName:$packageName"
	
				if (!tryRunClass(className, packageName)) {
					println("Aucune classe correspondante trouvée pour ${className}")
				}
			}else {
				println("${className} n'est pas conforme")
			}
		}


	}

}//end of class
