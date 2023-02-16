package my

import com.kms.katalon.core.util.KeywordUtil

class PropertiesReader {


	static Properties properties = null
	private static String propertiesFilename = 'TNR.Properties'

	//DO NOT INSERT LOG HERE BECAUSE my.Log.createFile() call this method !!

	static String getMyProperty(String propertyName) {

		if (properties == null ) { this.loadProperties() }

		String prop = properties.getProperty(propertyName)

		if (prop==null) {
			KeywordUtil.markErrorAndStop("La propriété $propertyName n'existe pas dans " + this.propertiesFilename)
		}

		return prop
	}


	private static loadProperties() {

		FileInputStream file = new FileInputStream (this.propertiesFilename)
		this.properties = new Properties()
		this.properties.load(file)
		file.close()
	}


} // end of class
