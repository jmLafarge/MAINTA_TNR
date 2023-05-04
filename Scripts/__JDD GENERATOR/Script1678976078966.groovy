
import my.JDDGenerator
import my.result.TNRResult
import my.InfoBDD
import my.PREJDDFiles
import my.JDDFiles
import my.Log


Log.addTITLE("Lancement de JDD GENERATOR")
if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (PREJDDFiles.PREJDDfilemap.isEmpty()) { PREJDDFiles.load() }

List listRubriquesIHM = []

listRubriquesIHM = ['ST_DESID_CODNATART','ST_DESGES','ST_DESID_CODFOU','ST_DESST_CODCOM']

my.JDDGenerator.add('ART','RT.ART','001',listRubriquesIHM)


//my.JDDGenerator.add('ZONLIG','RO.ZON','001A')


//my.JDDGenerator.add('INTER','ZZ.YYY')
//my.JDDGenerator.add('INTER_HAB','ZZ.YYY','002')
//my.JDDGenerator.add('INT_MET','ZZ.YYY','003')


//listRubriquesIHM = ['ST_DESGES','ST_DESID_CODMOD','ST_DESID_CODPAI','ST_DESID_CODMOD','ST_DESID_CODDEV','ST_DESID_CODPOR','ST_DESID_CODEMB']

//listRubriquesIHM = ['ST_DESGES']
//JDDGenerator.add('COM','MP.CPT','001', listRubriquesIHM)


