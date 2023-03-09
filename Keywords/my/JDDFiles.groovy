package my

import groovy.io.FileType


public class JDDFiles {

	private static Map JDDfilemap = [:]


	static public load() {


		my.Log.addSubTITLE("Load JDDfileList")

		my.Log.addINFO("\t"+'MODOBJ'.padRight(16) + 'JDDFULLNAME')


		my.Log.addINFO('')

		new File(my.PropertiesReader.getMyProperty('JDD_PATH')).eachFileRecurse(FileType.FILES) { file ->

			// keep only TC Name like JDD.*.xlsx
			if (file.getName()==~ /JDD\..*\.xlsx/ && file.getPath()==~ /^((?!standby).)*$/) {

				String modObj = file.getName().replace('JDD.','').replace('.xlsx','')

				this.JDDfilemap.put(modObj,file.getPath())

				my.Log.addINFO('\t' + modObj.padRight(16) + file.getPath())
			}
			/* Example
			 *
			 * modObj   JDDFullName
			 * ------   -------------------------- 
			 * AD.SEC : TNR_JDD\AD\JDD.AD.SEC.xlsx, 
			 * GLOBAL : TNR_JDD\JDD.GLOBAL.xlsx, 
			 * RO.ACT : TNR_JDD\RO\JDD.RO.ACT.xlsx,
			 */

		}

	} //end


	static getJDDFullNameFromCasDeTest(String casDeTest) {

		return this.JDDfilemap.getAt(casDeTest.find(/^\w+\.\w+/))
	}




} // end of class