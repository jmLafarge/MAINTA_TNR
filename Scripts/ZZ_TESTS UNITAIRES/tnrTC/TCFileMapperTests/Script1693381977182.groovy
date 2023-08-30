import tnrTC.TCFileMapper
import tnrLog.Log
import tnrCommon.Tools
import java.lang.reflect.Method

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



Map <String, String> tcFileMap

tcFileMap = [
	'ZZ.XXX.001.CRE.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case',
	'ZZ.XXX.001.CRE.11'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.11 cas sans CDT',
	'ZZ.XXX.001.CRE'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE',
	'ZZ.XXX.001.LEC.01'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.LEC.01',
	'ZZ.XXX.001.LEC.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.LEC.10'
]
Log.addAssert("TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001')", tcFileMap ,  TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001'))


tcFileMap = [
	'ZZ.XXX.001.CRE.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case',
	'ZZ.XXX.001.CRE.11'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.11 cas sans CDT',
	'ZZ.XXX.001.CRE'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE'
]
Log.addAssert("TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE')", tcFileMap ,  TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE'))


tcFileMap = [
	'ZZ.XXX.001.CRE.10'	: 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case'
]
Log.addAssert("TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", tcFileMap ,  TCFileMapper.getValuesWhereTCNameStartsWith('ZZ.XXX.001.CRE.10'))
Log.addAssert("TCFileMapper.getValuesWhereTCNameStartsWith('')", [:] ,  TCFileMapper.getValuesWhereTCNameStartsWith(''))
Log.addAssert("TCFileMapper.getValuesWhereTCNameStartsWith(null)", [:] ,  TCFileMapper.getValuesWhereTCNameStartsWith(null))





Log.addAssert("TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", 'ZZ.XXX.001.CRE.10' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001.CRE.10'))
Log.addAssert("TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", 'ZZ.XXX.001.CRE' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001.CRE'))
Log.addAssert("TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", 'ZZ.XXX.001.CRE' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001.CRE.12'))
Log.addAssert("TCFileMapper.getTCNameWhereTCNameStartsWith('ZZ.XXX.001.CRE.10')", null ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName('ZZ.XXX.001'))
Log.addAssert("TCFileMapper.getTCNameWhereTCNameStartsWith('')", '' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName(''))
Log.addAssert("TCFileMapper.getTCNameWhereTCNameStartsWith(null)", '' ,  TCFileMapper.getTCNameWhereTxtStartingWithTCName(null))




Log.addAssert("TCFileMapper.getTCFullname('ZZ.XXX.001.CRE.10')", 'ZZ TEST\\ZZ.XXX OBJET\\001 SOUSRES\\ZZ.XXX.001.CRE.10 un titre de test case' ,  TCFileMapper.getTCFullname('ZZ.XXX.001.CRE.10'))
Log.addAssert("TCFileMapper.getTCFullname('ZZ.XXX.001.UNK')", null ,  TCFileMapper.getTCFullname('ZZ.XXX.001.UNK'))
Log.addAssert("TCFileMapper.getTCFullname('')", '' ,  TCFileMapper.getTCFullname(''))
Log.addAssert("TCFileMapper.getTCFullname(null)", '' ,  TCFileMapper.getTCFullname(null))



Log.addAssert("TCFileMapper.isTCNameExist('RT.MAT.001.LEC')" , true ,  TCFileMapper.isTCNameExist('RT.MAT.001.LEC'))
Log.addAssert("TCFileMapper.isTCNameExist('UNK')" , false ,  TCFileMapper.isTCNameExist( 'UNK'))
Log.addAssert("TCFileMapper.isTCNameExist('')" , false ,  TCFileMapper.isTCNameExist( ''))
Log.addAssert("TCFileMapper.isTCNameExist(null)" , false ,  TCFileMapper.isTCNameExist(null))




Log.addAssert("getTitle('AD.SEC.001.FON.01') un titre existe" , 'Ouvrir session - mot de passe valide' ,  TCFileMapper.getTitle('AD.SEC.001.FON.01'))
Log.addAssert("getTitle('MP.CPT.001.CRE.10') titre auto" , 'Création Compteur' ,  TCFileMapper.getTitle('MP.CPT.001.CRE.10'))
Log.addAssert("getTitle('RT.MAT.001.LEC') titre auto" , 'Lecture Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.LEC'))
Log.addAssert("getTitle('RT.MAT.001.LEC.01') titre auto" , 'Lecture Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.LEC.01'))
Log.addAssert("getTitle('RT.MAT.001.MAJ') titre auto" , 'Mise à jour Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.MAJ'))
Log.addAssert("getTitle('RT.MAT.001.SUP') titre auto" , 'Suppression Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.SUP'))
Log.addAssert("getTitle('RT.MAT.001.REC') titre auto" , 'Recherche Matricule' ,  TCFileMapper.getTitle('RT.MAT.001.REC'))

Log.addAssert("getTitle('RO.ACT.003HAB.SRA') titre auto" , 'Acteur : Ajout Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRA'))
Log.addAssert("getTitle('RO.ACT.003HAB.SRL') titre auto" , 'Acteur : Lecture Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRL'))
Log.addAssert("getTitle('RO.ACT.003HAB.SRM') titre auto" , 'Acteur : Modification Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRM'))
Log.addAssert("getTitle('RO.ACT.003HAB.SRS') titre auto" , 'Acteur : Suppression Habilitation' ,  TCFileMapper.getTitle('RO.ACT.003HAB.SRS'))

Log.addAssert("getTitle('RT.MAT.001.UNK') titre auto" , 'UNKNOWN TITLE' ,  TCFileMapper.getTitle('RT.MAT.001.UNK'))
Log.addAssert("getTitle('') titre auto" , 'UNKNOWN TITLE' ,  TCFileMapper.getTitle(''))
Log.addAssert("getTitle(null) titre auto" , 'UNKNOWN TITLE' ,  TCFileMapper.getTitle(null))




Method method1 = TCFileMapper.class.getDeclaredMethod("getAutoTitle", String.class, String.class, String.class)
method1.setAccessible(true)

Log.addAssert("(private) getAutoTitle('Objet','Sous-ressource', 'CRE')" , 'Création Objet' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', 'CRE'))
Log.addAssert("(private) getAutoTitle('Objet','Sous-ressource', 'SRA')" , 'Objet : Ajout Sous-ressource' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', 'SRA'))
Log.addAssert("(private) getAutoTitle('Objet','Sous-ressource', 'UNK')" , '--' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', 'UNK'))
Log.addAssert("(private) getAutoTitle('Objet','Sous-ressource', '')" , '--' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', ''))
Log.addAssert("(private) getAutoTitle('Objet','Sous-ressource', null)" , '--' ,  method1.invoke(TCFileMapper, 'Objet','Sous-ressource', null))



