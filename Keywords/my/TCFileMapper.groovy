package my

import java.util.regex.Pattern

import groovy.io.FileType
import groovy.transform.CompileStatic
import internal.GlobalVariable


@CompileStatic
public class TCFileMapper {

	private static final String CLASS_FORLOG = 'TCFiles'


	public static Map <String,String> TCfileMap = [:]


	static {
		Log.addTraceBEGIN(CLASS_FORLOG,"static",[:])
		Log.addSubTITLE("Load TC file List",'-',120,true)
		Log.addTrace("\t" + 'TCNAME'.padRight(24) + 'TCFULLNAME')
		Log.addTrace("")

		new File(my.PropertiesReader.getMyProperty('TC_PATH')).eachFileRecurse(FileType.FILES) { file ->

			Log.addTrace('file.getPath() : '+file.getPath())

			String TCFullName = file.getPath().replace(my.PropertiesReader.getMyProperty('TC_PATH') + '\\', '').replace('.tc', '')
			String TCName= file.getName().replace('.tc','').split(' ')[0]

			if (TCFullName.matches("^[A-Z]{2}[ ].*") && !TCName.startsWith('.')) {

				TCfileMap.put(TCName, TCFullName)

				Log.addTrace('\t'+ TCName.padRight(24) + TCFullName)
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"static")
	} //end





	public static String getTCNameTitle() {

		Log.addTraceBEGIN(CLASS_FORLOG,"getTCNameTitle",[:])
		Log.addINFO ('GlobalVariable.CASDETESTPATTERN :' + GlobalVariable.CASDETESTPATTERN)

		List liTCFullName= Arrays.asList(TCfileMap[GlobalVariable.CASDETESTPATTERN.toString()].split(Pattern.quote(File.separator)))

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
			def liTCName2 = GlobalVariable.CASDETESTPATTERN.toString().split('\\.')
			if (liTCName2.size()>2) {
				ret = getAutoTitle(obj,sr,liTCName2[-2])
			}
			ret = ''
		}
		Log.addTraceEND(CLASS_FORLOG,"getTCNameTitle", ret)
		return ret
	}








	public static String getTitle() {
		Log.addTraceBEGIN(CLASS_FORLOG,"getTitle",[:])
		String TCfile = TCfileMap[GlobalVariable.CASDETESTPATTERN]
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
		Log.addTraceEND(CLASS_FORLOG,"getTitle", ret)
		return ret
	}








	private static String getAutoTitle(String obj, String sr, String code) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getAutoTitle",[obj:obj , sr:sr , code:code])
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
		Log.addTraceEND(CLASS_FORLOG,"getAutoTitle", ret)
		return ret
	}






} // end of class
