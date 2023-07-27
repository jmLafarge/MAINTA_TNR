import my.JsonFile

//def TNRvar = [key1: "value1", key2: "value2", key3: 42,key4: 'JML'] TNRDATAFILENAME


def TNRvar = new JsonFile(my.PropertiesReader.getMyProperty('TNRVARFILENAME'))
//jsonFile.writeToJsonFile(TNRvar)

println TNRvar.getVar('key1')

TNRvar.setVar('key1','toto')

println TNRvar.getVar('key1')
/*
TNRvar.setVar('newkey','titi')

println TNRvar.getVar('newkey')
*/