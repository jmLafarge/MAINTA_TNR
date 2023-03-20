package my

import java.util.regex.Pattern

import groovy.io.FileType


public class TCFiles {


	//private static List TCfileList = []
	private static Map TCfileMap = [:]


	private static load() {

		my.Log.addSubTITLE("Load TC file List",'-',120,1)
		my.Log.addDEBUG("\t" + 'TCNAME'.padRight(24) + 'TCFULLNAME')
		my.Log.addDEBUG("")

		new File(my.PropertiesReader.getMyProperty('TC_PATH')).eachFileRecurse(FileType.FILES) { file ->

			String TCFullName = file.getPath().replace(my.PropertiesReader.getMyProperty('TC_PATH') + '\\', '').replace('.tc', '')
			String TCName= file.getName().replace('.tc','').split(' ')[0]

			if (TCFullName.matches("^[A-Z]{2}[ ].*") && !TCName.startsWith('.')) {

				this.TCfileMap.put(TCName, TCFullName)

				my.Log.addDEBUG('\t'+ TCName.padRight(24) + TCFullName)
			}

		}
	} //end




	/*
	 public static String getTCNameFromTCFullName(String TCFullName) {
	 return TCFullName.split(Pattern.quote(File.separator))[-1].split(' ')[0]
	 }
	 */

	/*
	 public static String getModObjFromFullName(String TCFullName) {
	 return this.getTCNameFromTCFullName(TCFullName).find(/^\w+\.\w+/)
	 }
	 */	

	/*
	 public static String getTabNameFromTCName(String TCName) {
	 return TCName.split('\\.')[2]
	 }
	 */

	/*
	 public static String getModObjFromTCName(String TCName) {
	 return TCName.find(/^\w+\.\w+/)
	 }
	 */





} // end of class
