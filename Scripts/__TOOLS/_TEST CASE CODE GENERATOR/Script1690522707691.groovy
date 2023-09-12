@Grab('org.jsoup:jsoup:1.16.1') // Importer la dépendance Jsoup

import org.jsoup.Jsoup
import org.apache.commons.lang3.StringUtils
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrLog.Log
import tnrSqlManager.InfoDB


// Enregistrer la page html dans C:\Users\A1008045\Documents\IHM
// Mettre à jour la liste

String testPour ='Matricule'

Map map = [
	'Acteur'	: ['RO.ACT','001','Acteur_fichiers//FormE21.htm'],
	'Matricule'	: ['RT.MAT','001','Matricule_fichiers//FormE50.htm'],
	'Equipement'	: ['RT.EQU','001','Equipement_fichiers//FormE7.htm'],
	'Inventaire'	: ['RT.XXX','001','Inventaire_fichiers//FormE50.htm'],
	'Organisation'	: ['RO.ORG','001','Organisation_fichiers//FormE233.htm'],
	'Bon de travail'	: ['TR.BTR','001','xxxxxxxxxxxxxxxx']
]


String folder = 'C://Users//A1008045//Documents//IHM//'

Log.addTITLE("Lancement de _TEST CASE CODE GENERATOR ")


JDD myJDD = new JDD(JDDFileMapper.getFullnameFromModObj(map[testPour][0]),map[testPour][1],null,false)
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
	def attribute = [
		tag: 'input',
		type: tag.attr('type'),
		id: tag.attr('id'),
		tabindex: tag.attr('tabindex') ? tag.attr('tabindex').toInteger() : null,
		mlText3: null,
		readonly: tag.hasAttr('readonly')
	]
	tagsWithAttributes << attribute
}

// Extraction pour les balises <select>
selectTags.each { tag ->
	def attribute = [
		tag: 'select',
		id: tag.attr('id'),
		tabindex: tag.attr('tabindex') ? tag.attr('tabindex').toInteger() : null,
		mlText3: null,
		readonly: null
	]
	tagsWithAttributes << attribute
}

// Extraction pour les balises <a>
aTags.each { tag ->
	def attribute = [
		tag: 'a',
		id: tag.attr('id'),
		tabindex: tag.attr('tabindex') ? tag.attr('tabindex').toInteger() : null,
		mlText3: tag.attr('ml-text3'),
		readonly: null,
		text: StringUtils.stripAccents(tag.text().split(" ")[0])
	]
	tagsWithAttributes << attribute
}

// Afficher les résultats
tagsWithAttributes.each { attribute ->
	Log.addDEBUG(attribute.toString())
}


Log.addINFO('')
Log.addINFO('Mise à jour du JDD :')
Log.addINFO('')

List headers =myJDD.myJDDHeader.getList()

if (headers.size() >1) {
	
	Log.addDETAIL('Completer le paramètre LOCATOR')
	
	headers.eachWithIndex {name,i ->
	
		
		
		String loc = myJDD.myJDDParam.getLOCATORFor(name)
		
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


Log.addDETAIL('Completer les rubriques IHM seulement')

tagsWithAttributes.each { attribute ->
	
	if (attribute.tag == 'input' && attribute.type == 'text' && attribute.readonly && !headers.contains(attribute.id)) {
		myJDD.addColumn(attribute.id)
		Log.addDETAIL("Ajout de la rubrique '${attribute.id}' (IHM seulement)")
	}
	if (attribute.tag == 'input' && attribute.type == 'text' && attribute.readonly && headers.contains(attribute.id)) {
		String loc = myJDD.myJDDParam.getLOCATORFor(attribute.id)
		if (!loc) {
			Log.addDETAIL("Ajout de '${attribute.tag}' pour '${attribute.id}'")
			myJDD.setLOCATOR(attribute.id, attribute.tag)
		}
	}
}



def addAttributeIHMTO (JDD myJDD,String xpathName, String xpath) {
	
	if (myJDD.myJDDXpath.getXPath(xpathName)) {
		if (myJDD.myJDDXpath.getXPath(xpathName) != xpath){
			Log.addDETAILFAIL("Pour '$xpathName', xpath ihm = '$xpath' et xpath JDD = '${myJDD.myJDDXpath.getXPath(xpathName)}'")
		}else {
			Log.addTrace("'$xpathName' existe déjà !")
		}
	}else {
		Log.addDETAIL("Ajout de '$xpathName'")
		myJDD.addIHMTO('ALL',xpathName,xpath)
	}
	
}




Log.addINFO('')
Log.addDETAIL("Completer l'onglet 'IHMTO'")

tagsWithAttributes.sort { a, b -> a.tag <=> b.tag }

tagsWithAttributes.each { attribute ->
	
		if (attribute.tag == 'a' && attribute.mlText3 ) {
			addAttributeIHMTO(myJDD, 'tab_' + attribute.text				,'$TAB$' + attribute.mlText3)
			addAttributeIHMTO(myJDD, 'tab_' + attribute.text + "Selected"	,'$TABSELECTED$' + attribute.mlText3)
		}
		
		
		if (attribute.tag == 'a'  && !attribute.mlText3) {
			addAttributeIHMTO(myJDD, 'tab_' + attribute.text				,'*** TBD ***')
			addAttributeIHMTO(myJDD, 'tab_' + attribute.text + "Selected",'*** TBD ***')
		}
		
		
		if (attribute.type == 'radio') {
			addAttributeIHMTO(myJDD, 'radio_' + attribute.id, "//input[@id='" + attribute.id +"']")
		}
}



// Tri des balises par ordre croissant de tabindex
tagsWithAttributes.sort { a, b -> a.tabindex <=> b.tabindex }

def first=true

tagsWithAttributes.each { attribute ->
	if (first) {
		first=false
		Log.addSubTITLE(' CODE POUR CRE')
		Log.addB('')
		Log.addB('//Rappel pour ajouter un block dans le fichier Resultat :')
		Log.addB('//TNRResult.addSTEPBLOCK("DU TEXTE")')
		Log.addB('')
	}
	if (attribute.tag == 'input' && attribute.type == 'text') {
		if (!attribute.readonly) {
			if (InfoDB.isDatetime(myJDD.getDBTableName(),attribute.id)) {
				Log.addB("KW.scrollAndSetDate(myJDD, \"${attribute.id}\")")
			}else {
				Log.addB("KW.scrollAndSetText(myJDD, \"${attribute.id}\")")
			}
		}
		
	} else if (attribute.tag == 'input' && attribute.type == 'checkbox') {
		Log.addB("KW.scrollAndCheckIfNeeded(myJDD, \"${attribute.id}\", \"O\")")
		
	} else if (attribute.tag == 'select') {
		Log.addB("KW.scrollAndSelectOptionByLabel(myJDD, \"${attribute.id}\")")
		
	} else if (attribute.tag == 'a') {
		Log.addB('')
		Log.addB("TNRResult.addSTEPGRP(\"ONGLET ${attribute.text.toUpperCase()}\")")
		Log.addB('')
		Log.addB("KW.scrollAndClick(myJDD, \"tab_${attribute.text}\")")
		Log.addB("KW.waitForElementVisible(myJDD, \"tab_${attribute.text}Selected\")")
		Log.addB('')
	}
}

Log.addB('')
first=true

tagsWithAttributes.each { attribute ->
	if (first) {
		first=false
		Log.addSubTITLE(' CODE POUR MAJ')
		Log.addB('')
		Log.addB('//Rappel pour ajouter un block dans le fichier Resultat :')
		Log.addB('//TNRResult.addSTEPBLOCK("DU TEXTE")')
		Log.addB('')
	}
	if (attribute.tag == 'input' && attribute.type == 'text' && !InfoDB.isPK(myJDD.getDBTableName(),attribute.id)) {
		if (attribute.id.startsWith('ID_')) {
			Log.addB("KW.searchWithHelper(myJDD, \"${attribute.id}\",\"\",\"\")")
		}else if (!attribute.readonly) {
			Log.addB("KW.scrollAndSetText(myJDD, \"${attribute.id}\")")
		}
	} else if (attribute.tag == 'input' && attribute.type == 'checkbox') {
		Log.addB("KW.scrollAndCheckIfNeeded(myJDD, \"${attribute.id}\", \"O\")")
	} else if (attribute.tag == 'select') {
		Log.addB("KW.scrollAndSelectOptionByLabel(myJDD, \"${attribute.id}\")")
	} else if (attribute.tag == 'a') {
		Log.addB('')
		Log.addB("TNRResult.addSTEPGRP(\"ONGLET ${attribute.text.toUpperCase()}\")")
		Log.addB('')
		Log.addB("KW.scrollAndClick(myJDD, \"tab_${attribute.text}\")")
		Log.addB("KW.waitForElementVisible(myJDD, \"tab_${attribute.text}Selected\")")
		Log.addB('')
	}
}

Log.addB('')
first=true



tagsWithAttributes.each { attribute ->
	if (first) {
		first=false
		Log.addSubTITLE(' CODE POUR LEC')
		Log.addB('')
		Log.addB('//Rappel pour ajouter un block dans le fichier Resultat :')
		Log.addB('//TNRResult.addSTEPBLOCK("DU TEXTE")')
		Log.addB('')
	}
	
	if (attribute.tag == 'input') {
		if (attribute.type == 'text') {
			if (InfoDB.isDatetime(myJDD.getDBTableName(),attribute.id)) {
				if (myJDD.myJDDXpath.getXPath(attribute.id)=='input') {
					Log.addB("KW.verifyDateValue(myJDD, \"${attribute.id}\") // ou verifyDateText")
				}else {
					Log.addB("KW.verifyDateText(myJDD, \"${attribute.id}\")// ou verifyDateValue")
				}
			}else {
				Log.addB("KW.verifyValue(myJDD, \"${attribute.id}\")")
			}
		} else if (attribute.type == 'checkbox') {
			Log.addB("KW.verifyElementCheckedOrNot(myJDD, \"${attribute.id}\", \"O\")")
			
		}
	} else if (attribute.tag == 'select') {
		Log.addB("KW.verifyOptionSelectedByLabel(myJDD, \"${attribute.id}\")")
		
	} else if (attribute.tag == 'a') {
		Log.addB('')
		Log.addB("TNRResult.addSTEPGRP(\"ONGLET ${attribute.text.toUpperCase()}\")")
		Log.addB('')
		Log.addB("KW.scrollAndClick(myJDD, \"tab_${attribute.text}\")")
		Log.addB("KW.waitForElementVisible(myJDD, \"tab_${attribute.text}Selected\")")
		Log.addB('')
	}

}



