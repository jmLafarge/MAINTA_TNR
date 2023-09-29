package tnrWebUI

import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.text.SimpleTemplateEngine
import groovy.transform.CompileStatic
import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD
import tnrLog.Log


@CompileStatic
public class TO {

	private static final String CLASS_NAME = 'TO'

	private TestObject to
	private String msgTO


	public TestObject make(JDD myJDD,String ID) {

		Log.addTraceBEGIN(CLASS_NAME,"make",[myJDD:myJDD.toString() ,ID:ID])

		TestObject to = null

		String xpath = myJDD.myJDDXpath.getXPath(ID)

		if (xpath==null) {
			msgTO = "L'ID '$ID' n'existe pas, impossible de créer le TEST OBJET"
		}else if (xpath==''){
			msgTO = "Pas de locator(xpath) de défini dans le JDD pour '$ID', impossible de créer le TEST OBJET"
		}else {
			Map  binding = [:]

			to = new TestObject(ID)
			to.setSelectorMethod(SelectorMethod.XPATH)

			Log.addTrace("xpath : $xpath")

			if (xpath.startsWith('$')) {

				switch(xpath.split('\\$')[1]) {


					case "TAB":
						binding['tabname']=xpath.split('\\$')[2]
						xpath = GlobalJDD.myGlobalJDD.myJDDXpath.getXPath('TAB')
						break

					case "TABSELECTED":
						binding['tabname']=xpath.split('\\$')[2]
						xpath = GlobalJDD.myGlobalJDD.myJDDXpath.getXPath('TABSELECTED')
						break

					case "FILTREGRILLE":
						binding['idname']=xpath.split('\\$')[2]
						xpath = GlobalJDD.myGlobalJDD.myJDDXpath.getXPath('FILTREGRILLE')
						break

					case "TDGRILLE":
					// faire la m^me chose sur les autrees
						if (xpath.split('\\$').size()!=4){
							msgTO = "makeTO $ID, xpath avec "+'$'+" non conforme : $xpath"
							to = null
						}
					//////
						binding['numTD']=xpath.split('\\$')[2]
						binding['idnameval']=myJDD.getData(xpath.split('\\$')[3])
						xpath = GlobalJDD.myGlobalJDD.myJDDXpath.getXPath('TDGRILLE')
						break

					default:
						msgTO = "makeTO $ID, xpath avec "+'$'+" mot clé inconnu : $xpath"
						to = null
				}
				Log.addTrace("GLOBAL xpath : $xpath")
				Log.addTrace("binding  : " + binding.toString())
			}

			xpath = resolveXpath( myJDD, ID,xpath, binding)

			to.setSelectorValue(SelectorMethod.XPATH, xpath)

			Log.addTrace('getObjectId : ' + to.getObjectId())
			Log.addTrace('get(SelectorMethod.XPATH) : ' + to.getSelectorCollection().get(SelectorMethod.XPATH))

			binding=[:]

		}
		this.to = to
		Log.addTraceEND(CLASS_NAME,"make",to)
		return to

	}




	public String getMsg() {
		Log.addTrace("${CLASS_NAME}.getMsg <--'$msgTO'")
		return msgTO
	}

	/*
	 public String getXpath() {
	 return to.getSelectorCollection().get(SelectorMethod.XPATH)
	 }
	 public void setXpath(String xpath) {
	 to.setSelectorValue(SelectorMethod.XPATH, xpath)
	 }
	 */


	/**
	 * Résout un xpath en remplaçant les variables dynamiques par leurs valeurs correspondantes.
	 * 
	 * @param myJDD Instance de JDD.
	 * @param xpath Xpath à résoudre.
	 * @param binding Map des variables de liaison.
	 * @return L'xpath résolu.
	 */
	private String resolveXpath(JDD myJDD, String name, String xpath, Map binding) {
		Log.addTraceBEGIN(CLASS_NAME,"resolveXpath",[myJDD:myJDD.toString() , name:name,xpath:xpath,binding:binding])

		String nameWithoutLbl = name.substring(3)
		// Vérifie si c'est un xpath dynamique
		def matcher = xpath =~ /\$\{(.+?)\}/

		String xpathResolved = xpath
		Log.addTrace('matcher.size() = ' + matcher.size())

		if (matcher.size() > 0) {
			Log.addTrace('dynamic xpath')
			def engine = new SimpleTemplateEngine()

			matcher.each { k, value ->
				Log.addTrace('matcher k --> v : ' + k + ' --> ' + value)

				if (binding.containsKey(value)) {
					Log.addTrace('External binding')
				} else if (value in myJDD.myJDDHeader.getList()) {
					binding.put(value, myJDD.getData(value.toString()))
					Log.addTrace('JDD binding k --> v : ' + value + ' --> ' + myJDD.getData(value.toString()))
				} else {
					Log.addERROR('binding not possible because xpath parameter not in JDD : ' + k)
				}
			}

			xpathResolved = engine.createTemplate(xpath).make(binding).toString()

		} else if (myJDD.myJDDParam.isRADIO(name)) {
			Log.addTrace('radio xpath cas du input')
			xpathResolved = xpath.replaceAll(name, name + myJDD.getStrData(name))
		} else if (name.startsWith('Lbl') && myJDD.myJDDParam.isRADIO(nameWithoutLbl)) {
			Log.addTrace('radio xpath cas du label')
			xpathResolved = xpath.replaceAll(nameWithoutLbl, nameWithoutLbl + myJDD.getStrData(nameWithoutLbl))
		}else {
			Log.addTrace('normal xpath')
		}
		Log.addTraceEND(CLASS_NAME,"resolveXpath",xpathResolved)
		return xpathResolved
	}


}
