
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDGenerator
import tnrSqlManager.InfoDB
import tnrPREJDDManager.JDDFilesMapper
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrLog.Log


Log.addTITLE("Lancement de JDD GENERATOR")
 



List listRubriquesIHM = []

my.JDDGenerator.add('GRO','RT.GRO','001')

//my.JDDGenerator.add('GRO','RT.GRO','001')


//listRubriquesIHM = ['ST_DESID_CODNATART','ST_DESGES','ST_DESID_CODFOU','ST_DESST_CODCOM']

//my.JDDGenerator.add('FAM','RT.MOD','001') // ID_CODFAM dans RT.EQU et RT.MAT 

//my.JDDGenerator.add('xxx','xxxx','001') // ID_CODCONTRA dans RT.EQU et EQU_CONTRA et MAT_CONTRA et TR.BT
//my.JDDGenerator.add('xxx','xxxx','001')// ID_NUMMOD dans RT.EQU et RT.MAT
//my.JDDGenerator.add('GRO','xxxx','001') // ID_NUMGRO dans RT.EQU et RT.MAT
//my.JDDGenerator.add('xxx','xxxx','001') // ID_CODUNI dans RT.EQU et RT.MAT
//my.JDDGenerator.add('BUDGETLIG','xxxx','001') //ID_NUMBUDGETLIGINV et ID_NUMBUDGETLIGFON dans TR.BT




//my.JDDGenerator.add('xxx','xxxx','001')



//my.JDDGenerator.add('INTER','ZZ.YYY')
//my.JDDGenerator.add('INTER_HAB','ZZ.YYY','002')
//my.JDDGenerator.add('INT_MET','ZZ.YYY','003')


//listRubriquesIHM = ['ST_DESGES','ST_DESID_CODMOD','ST_DESID_CODPAI','ST_DESID_CODMOD','ST_DESID_CODDEV','ST_DESID_CODPOR','ST_DESID_CODEMB']

//listRubriquesIHM = ['ST_DESGES']
//JDDGenerator.add('COM','MP.CPT','001', listRubriquesIHM)


