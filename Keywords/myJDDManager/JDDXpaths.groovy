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
public class JDDXpaths {

	
	private final String CLASS_FORLOG = 'JDDXpaths'
	
	
	private Map <String,String> xpaths  = [:]
	
	
	
	def addFromMap(Map <String,String> map) {
		
		map.each { name,loc ->
			
			if (xpaths.containsKey(name)) {
				
				Log.addERROR("Le xpath pour '$name' existe déjà !")
			}else {
				Log.addTrace("ajout $name = '$loc'")
				xpaths[name] = loc
			}
			
		}

	}
	
	
	
	
	private addXpath(List <String> locators) {
		Log.addTraceBEGIN(CLASS_FORLOG,"addXpath",[locators:locators])

		// Itère sur chaque localisateur et son index.
		locators.eachWithIndex {loc,i ->
			// Assure que le localisateur est valide et que l'index n'est pas 0.
			if (loc!=null && loc!='' && i!=0) {
				String name = headers[i]

				// Ajoute le XPath approprié.
				if (loc in TAG_LIST_ALLOWED) {
					if (loc == 'checkbox') {
						xpathTO.put(name, "//input[@id='" + name +"' and @type='checkbox']")
						xpathTO.put('Lbl'+name, "//label[@id='Lbl$name']".toString())
					}else if (loc == 'radio') {
						//xpathTO.put(name, "//input[@id='" + name +"' and @type='radio']")
						xpathTO.put(name, "//label[@id='L${name}']".toString())
					}else if (loc=='input') {
						xpathTO.put(name, "//$loc[@id='$name' and not(@type='hidden')]".toString())
					}else {
						xpathTO.put(name, "//$loc[@id='$name']".toString())
					}
				}else if ((loc[0] != '/') && (loc.toString().split(/\*/).size()>1)) {
					// balises avec attributs.
					def lo = loc.toString().split(/\*/)
					if (lo[0] in TAG_LIST_ALLOWED) {
						xpathTO.put(name, "//${lo[0]}[@${lo[1]}='$name']".toString())
					}else {
						Log.addERROR("LOCATOR inconnu : ${lo[0]} in '$loc'")
					}
				}else if (loc[0] == '/') {
					// XPath avec des valeurs potentiellement dynamiques.
					xpathTO.put(name,loc)
				}else {
					Log.addERROR("LOCATOR inconnu : '$loc'")
				}
			}
		}
		Log.addTrace("xpathTO $xpathTO")
		Log.addTraceEND(CLASS_FORLOG,"addXpath")
	}

	
	
	
	
	
}// end of class
