import my.Log

// Enregistrer la page html dans C:\Users\A1008045\Documents\IHM
// Mettre à jour la liste et renseigner l

String testPour ='Organisation'

Map map = [
	'Acteur'	: ['RO.ACT','001','Acteur_fichiers//FormE21.htm'],
	'Matricule'	: ['RT.MAT','001','Matricule_fichiers//FormE50.htm'],
	'Equipement'	: ['RT.EQU','001','Equipement_fichiers//FormE7.htm'],
	'Inventaire'	: ['RT.XXX','001','Inventaire_fichiers//FormE50.htm'],
	'Organisation'	: ['RO.ORG','001','Organisation_fichiers//FormE233.htm']
]


String folder = 'C://Users//A1008045//Documents//IHM//'

def htmlFilePath = folder + map[testPour][2]


Log.addTITLE("Lancement de _AFFICHE TAG")



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
    var idLabel = null;
    var nameLabel = null;
	
    if (element.tagName === 'A') {
		idLabel = document.createElement('span');
		idLabel.style.backgroundColor = 'lightblue';
		if (element.getAttribute('ml-text3')) {
	    	idLabel.textContent = element.getAttribute('ml-text3');
		} else {
			idLabel.textContent = '-- ? --';
		}


    } else {
        if (element.id) {
            idLabel = document.createElement('span');
            idLabel.textContent = element.id;
            if (element.id.startsWith('ID_')) {
                idLabel.style.backgroundColor = 'orange';
            } else {
                idLabel.style.backgroundColor = 'yellow';
            }
        }
		
        if (element.name && (!idLabel || element.name !== idLabel.textContent)) {
            nameLabel = document.createElement('span');
            nameLabel.textContent = element.name;
            nameLabel.style.backgroundColor = 'lightgreen';
        }
    }
	
    if (idLabel) {
        idLabel.style.padding = '1px 2px 1px 2px';
        idLabel.style.position = 'absolute';
        idLabel.style.top = '0';
        idLabel.style.left = '0';
        idLabel.style.zIndex = '9999';
        idLabel.style.fontSize = '12px';
        idLabel.style.border = '1px solid red';
        element.parentNode.style.position = 'relative';
        element.parentNode.appendChild(idLabel);
		if (element.type === 'checkbox' || element.type === 'radio') {
            idLabel.style.display = 'block';
            idLabel.style.left = '-70px';
        }
    }
	
    if (nameLabel) {
        nameLabel.style.padding = '1px 2px 1px 2px';
        nameLabel.style.position = 'absolute';
        nameLabel.style.top = idLabel ? '22px' : '0';
        nameLabel.style.left = '0';
        nameLabel.style.zIndex = '9999';
        nameLabel.style.fontSize = '12px';
        nameLabel.style.border = '1px solid red';
		element.parentNode.style.position = 'relative';
		element.parentNode.appendChild(nameLabel);
		if (element.type === 'radio') {
            idLabel.style.backgroundColor = 'pink';
        }	
		if (element.type === 'checkbox' || element.type === 'radio') {
			nameLabel.style.display = 'block';
			nameLabel.style.left = '-70px';
		}
        
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
