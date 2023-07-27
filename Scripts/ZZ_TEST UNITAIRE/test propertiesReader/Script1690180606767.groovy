

println my.PropertiesReader.getMyProperty('INFODBFILENAME') // ok
println "'"+my.PropertiesReader.getMyProperty('TEST')+"'" // c'et vide quand pas de valeur même si pas de =
println my.PropertiesReader.getMyProperty('EXISTEPAS') // c'est null quand existe pas


