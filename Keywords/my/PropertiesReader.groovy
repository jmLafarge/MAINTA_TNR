package my

import groovy.transform.CompileStatic
import my.Log


@CompileStatic
class PropertiesReader {


	static Properties properties = null
	private static String propertiesFilename = 'TNR.Properties'

	static String getMyProperty(String propertyName) {

		if (properties == null ) { loadProperties() }

		String prop = properties.getProperty(propertyName)

		if (prop==null) {
			//KeywordUtil.markErrorAndStop("La propriété $propertyName n'existe pas dans " + propertiesFilename)
			Log.addERROR("La propriété $propertyName n'existe pas dans " + propertiesFilename)
		}

		return prop
	}


	private static loadProperties() {

		FileInputStream file = new FileInputStream (propertiesFilename)
		properties = new Properties()
		properties.load(file)
		file.close()
	}


} // end of class
