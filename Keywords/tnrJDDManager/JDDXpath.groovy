package tnrJDDManager



import groovy.transform.CompileStatic
import tnrLog.Log


/**
 *
 *
 * 
 * @author JM Lafarge
 * @version 1.0
 */

@CompileStatic
public class JDDXpath {


	private final String CLASS_FOR_LOG = 'JDDXpaths'


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
	public String getXPath(String name) {
		return xpaths[name]
	}



	public void add(Map <String,String> map) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"add",[map:map])
		map.each { name,loc ->
			if (xpaths.containsKey(name)) {
				Log.addTrace("Le xpath pour '$name' existe déjà, on met à jour")
			}
			add(name,loc)
		}
		Log.addTraceEND(CLASS_FOR_LOG,"add")
	}



	public void add(String name, String loc) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"add",[name:name , loc:loc])

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
		Log.addTraceEND(CLASS_FOR_LOG,"add")
	}




}// Fin de class
