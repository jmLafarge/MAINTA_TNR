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

		if (!myJDD.xpathTO.containsKey(ID)) {
			msgTO = "L'ID '$ID' n'existe pas, impossible de créer le TEST OBJET"
			return null
		}
		
		Map  binding = [:]

		Log.addDEBUG("makeTO( '$ID' ) with binding = )" + binding.toString())

		TestObject to = new TestObject(ID)
		to.setSelectorMethod(SelectorMethod.XPATH)
		String xpath = myJDD.xpathTO.getAt(ID)
		Log.addDEBUG("\txpath : $xpath")

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
						return null
					}
				//////
					binding['numTD']=xpath.split('\\$')[2]
					binding['idnameval']=myJDD.getData(xpath.split('\\$')[3])
					xpath = NAV.myGlobalJDD.getXpathTO('TDGRILLE')
					break

				default:
					msgTO = "makeTO $ID, xpath avec "+'$'+" mot clé inconnu : $xpath"
					return null
			}
			Log.addDEBUG("\t\tGLOBAL xpath : $xpath")
			Log.addDEBUG("\t\tbinding  : " + binding.toString())
		}

		Log.addDEBUG("\txpath : $xpath")

		xpath = resolveXpath( myJDD, xpath, binding)

		to.setSelectorValue(SelectorMethod.XPATH, xpath)

		Log.addDEBUG('\tgetObjectId : ' + to.getObjectId())
		Log.addDEBUG('\tget(SelectorMethod.XPATH) : ' + to.getSelectorCollection().get(SelectorMethod.XPATH))

		binding=[:]

		return to

	}

	public String getMsg() {
		
		return msgTO
	}


	private String resolveXpath(JDD myJDD, String xpath, Map binding) {

		// is it a dynamic xpath
		def matcher = xpath =~  /\$\{(.+?)\}/
		//LOG
		Log.addDEBUG('\tmatcher.size() = ' + matcher.size())
		if (matcher.size() > 0) {
			Log.addDEBUG('\tdynamic xpath')
			def engine = new SimpleTemplateEngine()
			matcher.each{k,value->
				Log.addDEBUG('\t\tmatcher k --> v : ' + k + ' --> ' + value)
				if (binding.containsKey(value)) {
					Log.addDEBUG('\t\tExternal binding')
				}else if (value in myJDD.headers) {
					binding.put(value,myJDD.getData(value.toString()))
					Log.addDEBUG('\t\tJDD binding k --> v : '+ value + ' --> '+ myJDD.getData(value.toString()))
				}else {
					Log.addERROR('binding not possible because xpath parameter not in JDD : ' + k)
				}
			}
			xpath = engine.createTemplate(xpath).make(binding).toString()
			Log.addDEBUG("\tdynamic xpath = $xpath")
			return xpath
		}else {
			Log.addDEBUG('\tnormal xpath')
			return xpath
		}
	}

}
