@Grab('org.jsoup:jsoup:1.16.1') // Importer la dépendance Jsoup

import org.jsoup.Jsoup
import org.apache.commons.lang3.StringUtils

import my.InfoBDD
import my.JDD
import my.JDDFiles
import my.NAV
import my.PREJDDFiles
import my.TCFiles

import my.Log


// Enregistrer la page html dans C:\Users\A1008045\Documents\IHM
// Mettre à jour la liste

String testPour ='Matricule'

Map map = [
	'Acteur'	: ['RO.ACT','001','Acteur_fichiers//FormE21.htm'],
	'Matricule'	: ['RT.MAT','001','Matricule_fichiers//FormE50.htm']
]

String folder = 'C://Users//A1008045//Documents//IHM//'

Log.addTITLE("Lancement de _TEST CASE CODE GENERATOR ")

if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (PREJDDFiles.PREJDDfilemap.isEmpty()) { PREJDDFiles.load() }

NAV.loadJDDGLOBAL()



JDD myJDD = new JDD(JDDFiles.getJDDFullName(map[testPour][0]),map[testPour][1],null,false)
def htmlFile = new File(folder + map[testPour][2])



Log.addINFO("Ouverture  de $htmlFile")

def htmlContent = htmlFile.text

// Analyser le contenu HTML avec Jsoup
def doc = Jsoup.parse(htmlContent)

// Extraire les balises <input>, <select> et <a> pertinentes
def inputTags = doc.select('input[id]:not([type="hidden"]):not([type="submit"]):not(#in_zoom)')
def selectTags = doc.select('select[id]')
def aTags = doc.select('a[ml-text3]')


// Extraire les attributs id, name, tabindex, ml-text3 et texte pour chaque balise
def tagsWithAttributes = []

// Extraction pour les balises <input>
inputTags.each { tag ->
    def attributes = [
        tag: 'input',
        type: tag.attr('type'),
        id: tag.attr('id'),
        tabindex: tag.attr('tabindex') ? tag.attr('tabindex').toInteger() : null,
        mlText3: null, 
		readonly: tag.hasAttr('readonly')
    ]
    tagsWithAttributes << attributes
}

// Extraction pour les balises <select>
selectTags.each { tag ->
    def attributes = [
        tag: 'select',
        id: tag.attr('id'),
        tabindex: tag.attr('tabindex') ? tag.attr('tabindex').toInteger() : null,
        mlText3: null, 
		readonly: null
    ]
    tagsWithAttributes << attributes
}

// Extraction pour les balises <a>
aTags.each { tag ->
    def attributes = [
        tag: 'a',
        id: tag.attr('id'),
        tabindex: tag.attr('tabindex') ? tag.attr('tabindex').toInteger() : null,
        mlText3: tag.attr('ml-text3'), 
		readonly: null,
		text: StringUtils.stripAccents(tag.text().split(" ")[0])
    ]
    tagsWithAttributes << attributes
}

// Tri des balises par ordre croissant de tabindex
tagsWithAttributes.sort { a, b -> a.tabindex <=> b.tabindex }

// Afficher les résultats

tagsWithAttributes.each { attributes ->
    println(attributes)
}



if (myJDD.getHeadersSize() >1) {
	
	List headers =myJDD.getHeaders()
	
	headers.eachWithIndex {name,i ->
	
		String loc = myJDD.getParamForThisName('LOCATOR',name)
		
		String tag = tagsWithAttributes.find { it.id == name }?.tag
		String type = tagsWithAttributes.find { it.id == name }?.type
		
		// si radio c'est pas id c'est name ?
		

		
		
		
		if (tag == 'select') {
			if (loc && loc != 'select') {
				Log.addDETAILFAIL("Pour $name, tag ihm = $tag et locator JDD = $loc")
			} else if (!loc) {
				Log.addDETAIL("Ajout de $tag pour $name")
				myJDD.setLOCATOR(name, tag)
			}
		}else if (tag == 'input' && type == 'checkbox') {
			if (loc && loc != 'checkbox') {
				Log.addDETAILFAIL("Pour $name, tag ihm = $type et locator JDD = $loc")
			} else if (!loc) {
				Log.addDETAIL("Ajout de $type pour $name")
				myJDD.setLOCATOR(name, type)
			}
		}else if (tag == 'input' && type == 'text') {
			if (loc && loc != 'input') {
				Log.addDETAILFAIL("Pour $name, tag ihm = $tag et locator JDD = $loc")
			} else if (!loc) {
				Log.addDETAIL("Ajout de $tag pour $name")
				myJDD.setLOCATOR(name, tag)
			}
		}else if (tag == 'input' && type == 'radio') {
			if (loc && loc != 'radio') {
				Log.addDETAILFAIL("Pour $name, tag ihm = $tag et locator JDD = $loc")
			} else if (!loc) {
				Log.addDETAIL("Ajout de $tag pour $name")
				myJDD.setLOCATOR(name, tag)
			}
		}else if (tag) {
			Log.addERROR("Pour $name, tag ihm = $tag --> pas de traitement trouvé")
		}
		
		
	}
	
}



def first=true

tagsWithAttributes.each { attributes ->
	if (first) {
		first=false
		Log.addB('')
		Log.addSubTITLE(' CODE POUR CRE')
		Log.addB('')
		Log.addB('//Rappel pour ajouter un block dans le fichier Resultat :')
		Log.addB('//TNRResult.addSTEPBLOCK("DU TEXTE")')
		Log.addB('')
	}
	if (attributes.tag == 'input' && attributes.type == 'text') {
		if (!attributes.readonly) {
			Log.addB("KW.scrollAndSetText(myJDD, \"${attributes.id}\")")
		}
		
	} else if (attributes.tag == 'input' && attributes.type == 'checkbox') {
		Log.addB("KW.scrollAndCheckIfNeeded(myJDD, \"${attributes.id}\", \"O\")")
		
	} else if (attributes.tag == 'select') {
		Log.addB("KW.scrollAndSelectOptionByValue(myJDD, \"${attributes.id}\")")
		
	} else if (attributes.tag == 'a') {
		Log.addB('')
		Log.addB("TNRResult.addSTEPGRP(\"ONGLET ${attributes.text.toUpperCase()}\")")
		Log.addB('')
		Log.addB("KW.scrollAndClick(myJDD, \"tab_${attributes.text}\")")
		Log.addB("KW.waitForElementVisible(myJDD, \"tab_${attributes.text}Selected\")")	
		Log.addB('')
	}
}

Log.addB('')
first=true

tagsWithAttributes.each { attributes ->
	if (first) {
		first=false
		Log.addB('')
		Log.addSubTITLE(' CODE POUR MAJ')
		Log.addB('')
		Log.addB('//Rappel pour ajouter un block dans le fichier Resultat :')
		Log.addB('//TNRResult.addSTEPBLOCK("DU TEXTE")')
		Log.addB('')
	}
	if (attributes.tag == 'input' && attributes.type == 'text' && !InfoBDD.isPK(myJDD.getDBTableName(),attributes.id)) {
		if (attributes.id.startsWith('ID_')) {
			Log.addB("KW.searchWithHelper(myJDD, \"${attributes.id}\",\"\",\"\")")
		}else if (!attributes.readonly) {
			Log.addB("KW.scrollAndSetText(myJDD, \"${attributes.id}\")")
		}
	} else if (attributes.tag == 'input' && attributes.type == 'checkbox') {
		Log.addB("KW.scrollAndCheckIfNeeded(myJDD, \"${attributes.id}\", \"O\")")
	} else if (attributes.tag == 'select') {
		Log.addB("KW.scrollAndSelectOptionByValue(myJDD, \"${attributes.id}\")")
	} else if (attributes.tag == 'a') {
		Log.addB('')
		Log.addB("TNRResult.addSTEPGRP(\"ONGLET ${attributes.text.toUpperCase()}\")")
		Log.addB('')
		Log.addB("KW.scrollAndClick(myJDD, \"tab_${attributes.text}\")")
		Log.addB("KW.waitForElementVisible(myJDD, \"tab_${attributes.text}Selected\")")
		Log.addB('')
	}
}

Log.addB('')
first=true



tagsWithAttributes.each { attributes ->
	if (first) {
		first=false
		Log.addSubTITLE(' CODE POUR LEC')
		Log.addB('')
		Log.addB('//Rappel pour ajouter un block dans le fichier Resultat :')
		Log.addB('//TNRResult.addSTEPBLOCK("DU TEXTE")')
		Log.addB('')
	}
	
	if (attributes.tag == 'input') {
		if (attributes.type == 'text') {
			Log.addB("KW.verifyValue(myJDD, \"${attributes.id}\")")
		} else if (attributes.type == 'checkbox') {
			Log.addB("KW.verifyElementCheckedOrNot(myJDD, \"${attributes.id}\", \"O\")")
			
		}
	} else if (attributes.tag == 'select') {
		Log.addB("KW.verifyOptionSelectedByValue(myJDD, \"${attributes.id}\")")
		
	} else if (attributes.tag == 'a') {
		Log.addB('')
		Log.addB("TNRResult.addSTEPGRP(\"ONGLET ${attributes.text.toUpperCase()}\")")
		Log.addB('')
		Log.addB("KW.scrollAndClick(myJDD, \"tab_${attributes.text}\")")
		Log.addB("KW.waitForElementVisible(myJDD, \"tab_${attributes.text}Selected\")")
		Log.addB('')
	}
}


Log.addB('')
first=true


tagsWithAttributes.each { attributes ->
	if (first) {
		first=false
		Log.addSubTITLE(' CODE POUR IHM')
		Log.addB('')
	}
	
	if (attributes.tag == 'a' && attributes.mlText3) {
		Log.addB('ALL' + '\ttab_' + attributes.text + '\t$TAB$' + attributes.mlText3)
		Log.addB('ALL' + '\ttab_' + attributes.text + "Selected" + '\t$TABSELECTED$' + attributes.mlText3)
	}
	
	if (attributes.tag == 'a'  && !attributes.mlText3) {
		Log.addB('ALL' + '\ttab_' + attributes.text + '*** TBD ***')
		Log.addB('ALL' + '\ttab_' + attributes.text + "Selected" + '*** TBD ***')
	}
	
	if (attributes.type == 'radio') {
		Log.addB(myJDD.TCTabName + '\tradio_' + attributes.id + "\t" + "//input[@id='" + attributes.id +"']")
	}
}


