

Properties properties = null
String propertiesFilename = 'TNR.Properties'



FileInputStream file = new FileInputStream (propertiesFilename)
properties = new Properties()
properties.load(file)
file.close()


String txt = properties.getProperty('MASTERTNR_BDDBACKUPPATH')


println txt