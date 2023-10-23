package tnrTC

import java.nio.file.Files
import java.nio.file.Paths





/**
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
//@CompileStatic
public class Sequencer3 {

	def static testCasesList = []
	

	public static run(List sequencerList ) {

		// Parcourir la liste sequencerList
		sequencerList.each { seqMap ->
		    // Extraire les deux premières parties séparées par le point
		    def className = seqMap.name
		    def rep = seqMap.rep
		    def modObj = className.split('\\.').take(2).join('.')
		    
		    // Chemin relatif du dossier TNR et du fichier potentiel
		    def filePath = Paths.get("TNR_JDD/JDD.${modObj}.xlsx")
		    
		    // Vérifier si le fichier existe
		    if (Files.exists(filePath)) {
		        // Ajouter à testCasesList
		        def testCaseMap = [testCasePattern: className, JDDFullname: filePath.toString(), rep: rep]
				testCasesList << testCaseMap
		    } else {
		        println "Pas de JDD pour $modObj"
		    }
		}
		
		// Afficher le contenu de testCasesList pour vérification
		testCasesList.each { li -> println li }



	}

}//end of class
