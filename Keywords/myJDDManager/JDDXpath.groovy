package myJDDManager



import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import my.Log
import my.Tools


/**
 *
 *
 * 
 * @author JM Lafarge
 * @since 1.0
 */

@CompileStatic
public class JDDXpath {


	private final String CLASS_FORLOG = 'JDDXpaths'


	private final List TAG_LIST_ALLOWED			= ['input', 'select', 'textarea', 'td', 'checkbox', 'radio']



	private Map <String,String> xpaths  = [:]



	/**
	 * Retrieves the XPath corresponding to a given name.
	 *
	 * @param name - The name for which the XPath is desired.
	 * @return String - Returns the XPath associated with the provided name.
	 *
	 * @note : The dynamic XPath is calculated during the creation of the TO
	 *
	 */
	def String getXPath(String name) {
		return xpaths[name]
	}



	def addFromMap(Map <String,String> map) {
		Log.addTraceBEGIN(CLASS_FORLOG,"addFromMap",[map:map])
		map.each { name,loc ->
			if (xpaths.containsKey(name)) {
				Log.addTrace("Le xpath pour '$name' existe déjà, on met à jour")
			}
			addXpath(name,loc)
		}
		Log.addTraceEND(CLASS_FORLOG,"addFromMap")
	}



	private addXpath(String name, String loc) {
		Log.addTraceBEGIN(CLASS_FORLOG,"addXpath",[name:name , loc:loc])

		if (!loc) {
			Log.addTrace("Pas de locator pour $name")
		}else if (loc in TAG_LIST_ALLOWED) {

			switch (loc){
				case 'checkbox' :
					xpaths[name] = "//input[@id='" + name +"' and @type='checkbox']"
					xpaths['Lbl'+name] = "//label[@id='Lbl$name']".toString()
					break

				case 'radio' :
				//xpaths[name, "//input[@id='" + name +"' and @type='radio']")
					xpaths[name] = "//label[@id='L${name}']".toString()
					break

				case 'input' :
					xpaths[name] = "//$loc[@id='$name' and not(@type='hidden')]".toString()
					break

				default:
					xpaths[name] = "//$loc[@id='$name']".toString()
					break

			}
		}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
			// balises avec attributs.
			def lo = loc.toString().split(/\*/)
			if (lo[0] in TAG_LIST_ALLOWED) {
				xpaths[name] = "//${lo[0]}[@${lo[1]}='$name']".toString()
			}else {
				Log.addERROR("LOCATOR inconnu : ${lo[0]} in '$loc'")
			}
		}else if (loc[0] in ['/' , '$']) {
			// XPath avec des valeurs potentiellement dynamiques.
			xpaths[name] = loc
		}else {
			Log.addERROR("LOCATOR inconnu : '$loc'")
		}

		Log.addTrace("xpaths $xpaths")
		Log.addTraceEND(CLASS_FORLOG,"addXpath")
	}




}// end of class
