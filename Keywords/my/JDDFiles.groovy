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

				this.JDDfilemap.put(modObj,file.getPath())

				MYLOG.addINFO('\t' + modObj.padRight(16) + file.getPath(),1)
			}
		}
	}



	/**
	 * @param casDeTest
	 * @return
	 */
	static getJDDFullNameFromCasDeTest(String casDeTest) {

		return this.JDDfilemap.getAt(casDeTest.find(/^\w+\.\w+/))
	}



	/**
	 * @param modObj
	 * @return
	 */
	static String getFullName(String modObj) {
		return this.JDDfilemap.getAt(modObj)
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