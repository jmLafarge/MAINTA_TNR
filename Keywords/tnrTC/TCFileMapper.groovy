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

	private static final String CLASS_FOR_LOG = 'TCFiles'


	// mapping between tcName and tcFullname [ tcName : tcFullname ]
	public static Map <String,String> tcFileMap = [:]


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

	
	public static boolean isTCNameExist(String tcName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"isTCNameExist",[tcName:tcName])
		boolean ret = tcFileMap.containsKey(tcName)
		Log.addTraceEND(CLASS_FOR_LOG,"isTCNameExist" ,ret)
		return ret
	}



	public static String getTCNameTitle() { //////////////////////////////////////////////////// NOT USED

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getTCNameTitle",[:])
		Log.addINFO ('GlobalVariable.CAS_DE_TEST_PATTERN :' + GlobalVariable.CAS_DE_TEST_PATTERN)

		List liTCFullName= Arrays.asList(tcFileMap[GlobalVariable.CAS_DE_TEST_PATTERN.toString()].split(Pattern.quote(File.separator)))

		//Détermine objet et sous-ressources à partir des noms des dossier père (SR) et grandpère(OBJ) des TC
		String obj =''
		String sr =''
		String ret = ''
		if (liTCFullName.size()>2) {
			obj = liTCFullName[liTCFullName.size()-3].split(' ').drop(1).join(' ')
			if (liTCFullName[liTCFullName.size()-2].split(' ').size()>1) {
				sr = liTCFullName[liTCFullName.size()-2].split(' ').drop(1).join(' ')
			}
		}

		// si un titre existe au niveau du TC on le prend sinon on le construit
		List liTCName = Arrays.asList(liTCFullName[-1].split(' '))
		if (liTCName.size()>1) {
			ret = liTCName.drop(1).join(' ')
		}else {
			def liTCName2 = GlobalVariable.CAS_DE_TEST_PATTERN.toString().split('\\.')
			if (liTCName2.size()>2) {
				ret = getAutoTitle(obj,sr,liTCName2[-2])
			}
			ret = ''
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getTCNameTitle", ret)
		return ret
	}








	public static String getTitle() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getTitle",[:])
		String TCfile = tcFileMap[GlobalVariable.CAS_DE_TEST_PATTERN]
		Log.addTrace("TCfile = '$TCfile'")
		String ret = ''
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
				ret = ''
			}
		}else {
			ret =  'UNKNOWN TITLE'
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






} // end of class
