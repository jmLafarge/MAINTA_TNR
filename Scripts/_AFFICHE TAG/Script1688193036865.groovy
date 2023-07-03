@Grab('org.jsoup:jsoup:1.13.1')

import org.jsoup.Jsoup

import my.Log


Log.addTITLE("Lancement de _AFFICHE TAG")

def htmlFilePath = 'C://Users//A1008045//Downloads//ACTEUR_Acteur_fichiers//FormE21.htm'

Log.addINFO("Ouverture  de $htmlFilePath")

// Charger le fichier HTML d'origine
def htmlFile = new File(htmlFilePath)
def htmlContent = htmlFile.text

// Vérifier si le commentaire spécifique est déjà présent dans la page HTML
def existingComment = htmlContent.contains("// Permet d'afficher les ID et NAME des TAG suivant :")

// Ajouter le code JavaScript si le commentaire n'est pas déjà présent
if (!existingComment) {
	def javascriptCode = """
    <script>
    // Permet d'afficher les ID et NAME des TAG suivant :
	var inputElements = document.querySelectorAll('input[id]:not([type="hidden"]), select[id], input[name]:not([type="hidden"]), select[name], a[ml-text3]');
	
	inputElements.forEach(function(element) {
	  var idLabel = document.createElement('span');
	  
	  if (element.tagName === 'A' && element.getAttribute('ml-text3')) {
	    idLabel.textContent = element.getAttribute('ml-text3');
	    idLabel.style.backgroundColor = 'lightblue';
	  } else if (element.id) {
	    idLabel.textContent = element.id;
	    if (element.id.startsWith('ID_')) {
	      idLabel.style.backgroundColor = 'orange';
	    } else {
	      idLabel.style.backgroundColor = 'yellow';
	    }
	  } else if (element.name) {
	    idLabel.textContent = element.name;
	    idLabel.style.backgroundColor = 'lightgreen';
	  }
	
	  idLabel.style.padding = '1px 2px 1px 2px';
	  idLabel.style.position = 'absolute';
	  idLabel.style.top = '0';
	  idLabel.style.left = '0';
	  idLabel.style.zIndex = '9999';
	  idLabel.style.fontSize = '12px';
	  element.parentNode.style.position = 'relative';
	  idLabel.style.border = '1px solid red';
	  element.parentNode.appendChild(idLabel);
	
	  if (element.type === 'checkbox') {
	    idLabel.style.display = 'block';
	    idLabel.style.left = '-70px';
	  }
	});
    </script>
    """

	// Insérer le code JavaScript juste avant la balise </body>
	def modifiedHtml = htmlContent.replace("</body>", "${javascriptCode}\n</body>")

	// Écrire le contenu HTML modifié dans le fichier d'origine
	def writer = new FileWriter(htmlFilePath)
	writer.write(modifiedHtml)
	writer.close()

	Log.addINFO("Le code JavaScript a été ajouté dans le fichier HTML d'origine : $htmlFilePath.")
} else {
	Log.addINFO("Le code JavaScript est déjà présent dans la page HTML.")
}
