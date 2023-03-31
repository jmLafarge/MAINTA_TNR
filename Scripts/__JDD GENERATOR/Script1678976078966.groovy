
import my.JDDGenerator


List listRubriquesIHM = []
//my.JDDGenerator.add('INTER','ZZ.YYY')
//my.JDDGenerator.add('INTER_HAB','ZZ.YYY','002')
//my.JDDGenerator.add('INT_MET','ZZ.YYY','003')


//listRubriquesIHM = ['ST_DESGES','ST_DESID_CODMOD','ST_DESID_CODPAI','ST_DESID_CODMOD','ST_DESID_CODDEV','ST_DESID_CODPOR','ST_DESID_CODEMB']

listRubriquesIHM = ['ST_DESGES']
JDDGenerator.add('COM','MP.CPT','001', listRubriquesIHM)


