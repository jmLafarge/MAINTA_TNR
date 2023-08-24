package my

import groovy.transform.CompileStatic
import my.Log


@CompileStatic
class PropertiesReader {


	static Properties properties = null
	private static String propertiesFilename = 'TNR.Properties'

	static {
		FileInputStream file = new FileInputStream (propertiesFilename)
		properties = new Properties()
		properties.load(file)
		file.close()
	}

	static String getMyProperty(String propertyName) {

		String prop = properties.getProperty(propertyName)
		if (prop==null) {
			Log.addERROR("La propriété $propertyName n'existe pas dans " + propertiesFilename)
		}
		return prop
	}
} // end of class
