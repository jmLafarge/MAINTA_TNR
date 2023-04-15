package my

import groovy.io.FileType
import my.Log as MYLOG


/**
 * @author A1008045
 *
 */
public class JDDFiles {


	public static Map JDDfilemap = [:]


	/**
	 * 
	 * 
	 * 
	 * modObj   JDDFullName
	 * ------   -------------------------- 
	 * AD.SEC : TNR_JDD\AD\JDD.AD.SEC.xlsx, 
	 * GLOBAL : TNR_JDD\JDD.GLOBAL.xlsx, 
	 * RO.ACT : TNR_JDD\RO\JDD.RO.ACT.xlsx,
	 * 
	 * 
	 * @return
	 */
	public static load() {


		MYLOG.addSubTITLE("Load JDDfileList",'-',120,1)

		MYLOG.addINFO("\t"+'MODOBJ'.padRight(16) + 'JDDFULLNAME',1)

		new File(my.PropertiesReader.getMyProperty('JDD_PATH')).eachFileRecurse(FileType.FILES) { file ->

			// keep only TC Name like JDD.*.xlsx
			if (file.getName()==~ /JDD\..*\.xlsx/ && file.getPath()==~ /^((?!standby).)*$/) {

				String modObj = file.getName().replace('JDD.','').replace('.xlsx','')

				JDDfilemap.put(modObj,file.getPath())

				MYLOG.addINFO('\t' + modObj.padRight(16) + file.getPath(),1)
			}
		}
	}



	/**
	 * Récupère le nom complet du fichier JDD à partir du nom du cas de test.
	 * @param casDeTest le nom du cas de test.
	 * @return le nom complet du fichier JDD correspondant, ou null si aucun fichier JDD ne correspond.
	 */
	static getJDDFullNameFromCasDeTest(String casDeTest) {

		def modObj = casDeTest.find(/^\w+\.\w+/)

		return modObj ? JDDfilemap[modObj] : null
	}



	/**
	 * @param modObj
	 * @return
	 */
	static String getFullName(String modObj) {
		return JDDfilemap[modObj]
	}



	/**
	 * @param modObj
	 * @param fullName
	 * @return
	 */
	static add(String modObj,String fullName) {
		this.JDDfilemap.put(modObj,fullName)
	}

} // end of class