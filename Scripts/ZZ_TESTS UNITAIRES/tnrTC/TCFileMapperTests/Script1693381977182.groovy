import java.lang.reflect.Method
import tnrLog.Log
import tnrTC.TCFileMapper

/**
 * TESTS UNITAIRES
 * 
 * public static Map  getValuesWhereTCNameStartsWith(String substr)
 * public static String getTCNameWhereTxtStartingWithTCName(String str) {
 * public static String  getTCFullname(String tcName)
 * public static String getTitle(String cdt)
 * private static String getAutoTitle(String obj, String sr, String code)
 * private static boolean isTCNameExist(String tcName)
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrTC.TCFileMapper'

Map <String, String> tcFileMap

tcFileMap = [
	'ZZ.XXX.001.CRE.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case',
	'ZZ.XXX.001.CRE.11'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.11 cas sans CDT',
	'ZZ.XXX.001.CRE'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE',
	'ZZ.XXX.001.LEC.01'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.LEC.01',
	'ZZ.XXX.001.LEC.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.LEC.10'
]
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001')", tcFileMap ,  TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001'))


tcFileMap = [
	'ZZ.XXX.001.CRE.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case',
	'ZZ.XXX.001.CRE.11'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.11 cas sans CDT',
	'ZZ.XXX.001.CRE'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE'
]
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE')", tcFileMap ,  TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE'))


tcFileMap = [
	'ZZ.XXX.001.CRE.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case'
]
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", tcFileMap ,  TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE.10'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getValuesWhereTCNameStartsWith('')", [:] ,  TCFileMapper.getValuesWhereTCNameStartsWith(''))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getValuesWhereTCNameStartsWith(null)", [:] ,  TCFileMapper.getValuesWhereTCNameStartsWith(null))





Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", 'ZZ.XXX.001.CRE.10' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001.CRE.10'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", 'ZZ.XXX.001.CRE' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001.CRE'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", 'ZZ.XXX.001.CRE' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001.CRE.12'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", null ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCNameWhereTCNameStartsWith('')", '' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName(''))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCNameWhereTCNameStartsWith(null)", '' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName(null))




Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCFullname('ZZ.XXX.001.CRE.10')", 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case' ,  TCFileMapper.getTCFullname('ZZ.XXX.001.CRE.10'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCFullname('ZZ.XXX.001.UNK')", null ,  TCFileMapper.getTCFullname('ZZ.XXX.001.UNK'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCFullname('')", '' ,  TCFileMapper.getTCFullname(''))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.getTCFullname(null)", '' ,  TCFileMapper.getTCFullname(null))



Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.isTCNameExist('RT.MAT.001.LEC')" , true ,  TCFileMapper.isTCNameExist('RT.MAT.001.LEC'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.isTCNameExist('UNK')" , false ,  TCFileMapper.isTCNameExist( 'UNK'))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.isTCNameExist('')" , false ,  TCFileMapper.isTCNameExist( ''))
Log.addAssert(CLASS_FOR_LOG,"TCFileMapper.isTCNameExist(null)" , false ,  TCFileMapper.isTCNameExist(null))




Log.addAssert(CLASS_FOR_LOG,"getTitle('AD.SEC.001.FON.01') un titre existe" , 'Ouvrir session - mot de passe valide' ,  TCFileMapper.getTitle('AD.SEC.001.FON.01'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('ZZ.XXX.001.CRE.10') titre auto" , 'un titre de test case' ,  TCFileMapper.getTitle('ZZ.XXX.001.CRE.10'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RT.MAT.001.LEC') titre auto" , 'Lecture Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.LEC'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RT.MAT.001.LEC.01') titre auto" , 'Lecture Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.LEC.01'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RT.MAT.001.MAJ') titre auto" , 'Mise à jour Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.MAJ'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RT.MAT.001.SUP') titre auto" , 'Suppression Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.SUP'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RT.MAT.001.REC') titre auto" , 'Recherche Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.REC'))

Log.addAssert(CLASS_FOR_LOG,"getTitle('RO.ACT.003HAB.SRA') titre auto" , 'Acteur : Ajout Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRA'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RO.ACT.003HAB.SRL') titre auto" , 'Acteur : Lecture Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRL'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RO.ACT.003HAB.SRM') titre auto" , 'Acteur : Modification Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRM'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('RO.ACT.003HAB.SRS') titre auto" , 'Acteur : Suppression Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRS'))

Log.addAssert(CLASS_FOR_LOG,"getTitle('RT.MAT.001.UNK') titre auto" , 'UNKNOWN TITLE' ,  TCFileMapper.getTitle('RT.MAT.001.UNK'))
Log.addAssert(CLASS_FOR_LOG,"getTitle('') titre auto" , 'UNKNOWN TITLE' ,  TCFileMapper.getTitle(''))
Log.addAssert(CLASS_FOR_LOG,"getTitle(null) titre auto" , 'UNKNOWN TITLE' ,  TCFileMapper.getTitle(null))




Method method1 = TCFileMapper.class.getDeclaredMethod("getAutoTitle", String.class, String.class, String.class)
method1.setAccessible(true)

Log.addAssert(CLASS_FOR_LOG,"(private) getAutoTitle('Objet','Sous-ressource', 'CRE')" , 'Création Objet' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', 'CRE'))
Log.addAssert(CLASS_FOR_LOG,"(private) getAutoTitle('Objet','Sous-ressource', 'SRA')" , 'Objet : Ajout Sous-ressource' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', 'SRA'))
Log.addAssert(CLASS_FOR_LOG,"(private) getAutoTitle('Objet','Sous-ressource', 'UNK')" , '--' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', 'UNK'))
Log.addAssert(CLASS_FOR_LOG,"(private) getAutoTitle('Objet','Sous-ressource', '')" , '--' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', ''))
Log.addAssert(CLASS_FOR_LOG,"(private) getAutoTitle('Objet','Sous-ressource', null)" , '--' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', null))



