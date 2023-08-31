package tnrTC

import java.util.regex.Pattern

import groovy.io.FileType
import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.TNRPropertiesReader
import tnrLog.Log





/**
 * This class is responsible for mapping Test Case (TC) files using a static Map.
 * It associates a short name of a Test Case file (TCName) to its full name (TCFullName),
 * based on a specified directory path.
 *
 * The mapping is performed during the static initialization of the class.
 * 
 * @author JM Lafarge
 * @version 1.0
 * 
 */
@CompileStatic
public class TCFileMapper {

	private static final String CLASS_FOR_LOG = 'TCFileMapper'


	// mapping between tcName and tcFullname [ tcName : tcFullname ]
	private static Map <String,String> tcFileMap = [:]


	static {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"static",[:])
		Log.addSubTITLE("Load TC file List",'-',120,true)
		Log.addTrace("\t" + 'TCNAME'.padRight(24) + 'TCFULLNAME')
		Log.addTrace("")

		String tcPath = TNRPropertiesReader.getMyProperty('TC_PATH')
		new File(tcPath).eachFileRecurse(FileType.FILES) { file ->

			String tcFullname = file.getPath().replace("$tcPath\\", '').replace('.tc', '')
			String tcName = file.getName().replace('.tc','').split(' ')[0]

			// add tcName and tcFullname to the map only if it matches with pattern
			if (tcFullname.matches("^[A-Z]{2}[ ].*") && !tcName.startsWith('.')) {
				tcFileMap.put(tcName, tcFullname)
				Log.addTrace('\t'+ tcName.padRight(24) + tcFullname)
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"static")
	}








	public static Map<String,String>  getValuesWhereTCNameStartsWith(String substr) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getValuesWhereTCNameStartsWith",[substr:substr])
		Map<String,String> map = [:]
		if (substr) {
			map = tcFileMap.findAll { it.key.startsWith(substr) }
		}else {
			Log.addTrace("substr vide ou null")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getValuesWhereTCNameStartsWith",map)
		return map
	}



	public static String  getTCNameWhereTxtStartingWithTCName(String txt) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getTCNameWhereTxtStartingWithTCName",[txt:txt])
		String tcName =''
		if (txt) {
			tcName = tcFileMap.keySet().find { txt.startsWith(it) }
		}else {
			Log.addTrace("txt vide ou null")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getTCNameWhereTxtStartingWithTCName",tcName)
		return tcName
	}




	public static String  getTCFullname(String tcName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getTCFullname",[tcName:tcName])
		String tcFullname =''
		if (tcName) {
			tcFullname = tcFileMap[tcName]
		}else {
			Log.addTrace("tcName vide ou null")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getTCFullname",tcFullname)
		return tcFullname
	}



	public static boolean isTCNameExist(String tcName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isTCNameExist",[tcName:tcName])
		boolean tcNameExist = false
		if (tcName) {
			tcNameExist = tcFileMap.containsKey(tcName)
		}else {
			Log.addTrace("tcName vide ou null")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"isTCNameExist",tcNameExist)
		return tcNameExist
	}



	public static String getTitle(String cdt) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getTitle",[cdt:cdt])
		String ret = 'UNKNOWN TITLE'
		String TCName = getTCNameWhereTxtStartingWithTCName(cdt)
		if (TCName) {
			String TCfile = getTCFullname(TCName)
			Log.addTrace("TCfile = '$TCfile'")
			if (TCfile) {

				List liTCFullName= Arrays.asList(TCfile.split(Pattern.quote(File.separator)))

				//Détermine objet et sous-ressources à partir des noms des dossier père (SR) et grandpère(OBJ) des TC
				String obj =''
				String sr =''
				if (liTCFullName.size()>2) {
					obj = liTCFullName[liTCFullName.size()-3].split(' ').drop(1).join(' ')
					if (liTCFullName[liTCFullName.size()-2].split(' ').size()>1) {
						sr = liTCFullName[liTCFullName.size()-2].split(' ').drop(1).join(' ')
					}
				}

				// si un titre existe au niveau du TC on le prend sinon on le construit
				List liTCName = Arrays.asList(liTCFullName[-1].split(' '))
				if (liTCName.size()>1) {
					ret =  liTCName.drop(1).join(' ')
				}else {
					def liTCName2 = liTCFullName[-1].split('\\.')
					if (liTCName2.size()>2) {
						ret = getAutoTitle(obj,sr,liTCName2[3])
					}
				}
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getTitle", ret)
		return ret
	}








	private static String getAutoTitle(String obj, String sr, String code) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getAutoTitle",[obj:obj , sr:sr , code:code])
		String ret = ''
		switch (code) {
			case "CRE" :
				ret = "Création $obj"
				break
			case "LEC" :
				ret = "Lecture $obj"
				break
			case "MAJ" :
				ret = "Mise à jour $obj"
				break
			case "REC" :
				ret = "Recherche $obj"
				break
			case "SUP" :
				ret = "Suppression $obj"
				break
			case "SRA" :
				ret = "$obj : Ajout $sr"
				break
			case "SRL" :
				ret = "$obj : Lecture $sr"
				break
			case "SRM" :
				ret = "$obj : Modification $sr"
				break
			case "SRS" :
				ret = "$obj : Suppression $sr"
				break
			default :
				ret = '--'
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getAutoTitle", ret)
		return ret
	}


} // Fin de class
