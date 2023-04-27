package my

import com.kms.katalon.core.util.KeywordUtil

class PropertiesReader {


	static Properties properties = null
	private static String propertiesFilename = 'TNR.Properties'

	//DO NOT INSERT LOG HERE BECAUSE MYLOG.createFile() call this method !!

	static String getMyProperty(String propertyName) {

		if (properties == null ) { loadProperties() }

		String prop = properties.getProperty(propertyName)

		if (prop==null) {
			KeywordUtil.markErrorAndStop("La propriété $propertyName n'existe pas dans " + propertiesFilename)
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
