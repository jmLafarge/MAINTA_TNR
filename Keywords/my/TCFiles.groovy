package my

import java.util.regex.Pattern

import groovy.io.FileType


public class TCFiles {


	//private static List TCfileList = []
	private static Map TCfileMap = [:]


	private static load() {

		my.Log.addSubTITLE("Load TC file List")
		my.Log.addINFO("\tTCNAME                        TCFULLNAME")
		my.Log.addINFO("")

		new File(my.PropertiesReader.getMyProperty('TC_PATH')).eachFileRecurse(FileType.FILES) { file ->

			String TCFullName = file.getPath().replace(my.PropertiesReader.getMyProperty('TC_PATH') + '\\', '').replace('.tc', '')
			String TCName= file.getName().replace('.tc','').split(' ')[0]

			if (TCFullName.matches("^[A-Z]{2}[ ].*") && !TCName.startsWith('.')) {

				this.TCfileMap.put(TCName, TCFullName)

				my.Log.addINFO('\t'+ TCName.padRight(30) + TCFullName)
			}

		}
	} //end




	/*
	 static public String getTCNameFromTCFullName(String TCFullName) {
	 return TCFullName.split(Pattern.quote(File.separator))[-1].split(' ')[0]
	 }
	 */

	/*
	 static public String getModObjFromFullName(String TCFullName) {
	 return this.getTCNameFromTCFullName(TCFullName).find(/^\w+\.\w+/)
	 }
	 */	

	/*
	 static public String getTabNameFromTCName(String TCName) {
	 return TCName.split('\\.')[2]
	 }
	 */

	/*
	 static public String getModObjFromTCName(String TCName) {
	 return TCName.find(/^\w+\.\w+/)
	 }
	 */





} // end of class
