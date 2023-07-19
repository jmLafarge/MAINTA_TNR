package my

import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.text.SimpleTemplateEngine
import groovy.transform.CompileStatic

import my.NAV


@CompileStatic
public class TO {

	private String msgTO

	public TestObject make(JDD myJDD,String ID) {

		Log.addTraceBEGIN("TO.make(myJDD, ${ID})")

		if (!myJDD.xpathTO.containsKey(ID)) {
			msgTO = "L'ID '$ID' n'existe pas, impossible de créer le TEST OBJET"
			return null
		}

		Map  binding = [:]

		TestObject to = new TestObject(ID)
		to.setSelectorMethod(SelectorMethod.XPATH)
		String xpath = myJDD.xpathTO.getAt(ID)
		Log.addDEBUG("xpath : $xpath")

		if (xpath.startsWith('$')) {

			switch(xpath.split('\\$')[1]) {

				case "TAB":
					binding['tabname']=xpath.split('\\$')[2]
					xpath = NAV.myGlobalJDD.getXpathTO('TAB')
					break

				case "TABSELECTED":
					binding['tabname']=xpath.split('\\$')[2]
					xpath = NAV.myGlobalJDD.getXpathTO('TABSELECTED')
					break

				case "FILTREGRILLE":
					binding['idname']=xpath.split('\\$')[2]
					xpath = NAV.myGlobalJDD.getXpathTO('FILTREGRILLE')
					break

				case "TDGRILLE":
				// faire la m^me chose sur les autrees
					if (xpath.split('\\$').size()!=4){
						msgTO = "makeTO $ID, xpath avec "+'$'+" non conforme : $xpath"
						Log.addTraceEND("TO.make() --> null")
						return null
					}
				//////
					binding['numTD']=xpath.split('\\$')[2]
					binding['idnameval']=myJDD.getData(xpath.split('\\$')[3])
					xpath = NAV.myGlobalJDD.getXpathTO('TDGRILLE')
					break

				default:
					msgTO = "makeTO $ID, xpath avec "+'$'+" mot clé inconnu : $xpath"
					Log.addTraceEND("TO.make() --> null")
					return null
			}
			Log.addDEBUG("GLOBAL xpath : $xpath")
			Log.addDEBUG("binding  : " + binding.toString())
		}

		Log.addDEBUG("xpath : $xpath")

		xpath = resolveXpath( myJDD, xpath, binding)

		to.setSelectorValue(SelectorMethod.XPATH, xpath)

		Log.addDEBUG('getObjectId : ' + to.getObjectId())
		Log.addDEBUG('get(SelectorMethod.XPATH) : ' + to.getSelectorCollection().get(SelectorMethod.XPATH))

		binding=[:]

		Log.addTraceEND("TO.make() --> ${to}")

		return to

	}

	public String getMsg() {

		return msgTO
	}


	/**
	 * Résout un xpath en remplaçant les variables dynamiques par leurs valeurs correspondantes.
	 * 
	 * @param myJDD Instance de JDD.
	 * @param xpath Xpath à résoudre.
	 * @param binding Map des variables de liaison.
	 * @return L'xpath résolu.
	 */
	private String resolveXpath(JDD myJDD, String xpath, Map binding) {
		Log.addTraceBEGIN("TO.resolveXpath(myJDD, ${xpath}, ${binding})")

		// Vérifie si c'est un xpath dynamique
		def matcher = xpath =~ /\$\{(.+?)\}/

		Log.addDEBUG('matcher.size() = ' + matcher.size())

		if (matcher.size() > 0) {
			Log.addDEBUG('dynamic xpath')
			def engine = new SimpleTemplateEngine()

			matcher.each { k, value ->
				Log.addDEBUG('matcher k --> v : ' + k + ' --> ' + value)

				if (binding.containsKey(value)) {
					Log.addDEBUG('External binding')
				} else if (value in myJDD.headers) {
					binding.put(value, myJDD.getData(value.toString()))
					Log.addDEBUG('JDD binding k --> v : ' + value + ' --> ' + myJDD.getData(value.toString()))
				} else {
					Log.addERROR('binding not possible because xpath parameter not in JDD : ' + k)
				}
			}

			xpath = engine.createTemplate(xpath).make(binding).toString()
			Log.addDEBUG("dynamic xpath = ${xpath}")

			Log.addTraceEND("resolveXpath() --> ${xpath}")
			return xpath
		} else {
			Log.addDEBUG('normal xpath')

			Log.addTraceEND("TO.resolveXpath() --> ${xpath}")
			return xpath
		}
	}


}
