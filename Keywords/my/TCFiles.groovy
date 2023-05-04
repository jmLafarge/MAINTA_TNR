package my

import java.util.regex.Pattern

import groovy.io.FileType
import groovy.transform.CompileStatic
import internal.GlobalVariable


@CompileStatic
public class TCFiles {


	public static Map <String,String> TCfileMap = [:]



	private static load() {

		Log.addSubTITLE("Load TC file List",'-',120,1)
		Log.addDEBUG("\t" + 'TCNAME'.padRight(24) + 'TCFULLNAME')
		Log.addDEBUG("")

		new File(my.PropertiesReader.getMyProperty('TC_PATH')).eachFileRecurse(FileType.FILES) { file ->

			String TCFullName = file.getPath().replace(my.PropertiesReader.getMyProperty('TC_PATH') + '\\', '').replace('.tc', '')
			String TCName= file.getName().replace('.tc','').split(' ')[0]

			if (TCFullName.matches("^[A-Z]{2}[ ].*") && !TCName.startsWith('.')) {

				TCfileMap.put(TCName, TCFullName)

				Log.addDEBUG('\t'+ TCName.padRight(24) + TCFullName)
			}
		}
	} //end





	public static String getTCNameTitle() {

		Log.addINFO ('GlobalVariable.CASDETESTPATTERN :' + GlobalVariable.CASDETESTPATTERN)

		List liTCFullName= Arrays.asList(TCfileMap[GlobalVariable.CASDETESTPATTERN.toString()].split(Pattern.quote(File.separator)))

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
			return liTCName.drop(1).join(' ')
		}else {
			def liTCName2 = GlobalVariable.CASDETESTPATTERN.toString().split('\\.')
			if (liTCName2.size()>2) {
				return getAutoTitle(obj,sr,liTCName2[-2])
			}
			return ''
		}
	}








	public static String getTitle() {

		String TCfile = TCfileMap[GlobalVariable.CASDETESTPATTERN]

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
				return liTCName.drop(1).join(' ')
			}else {
				def liTCName2 = liTCFullName[-1].split('\\.')
				if (liTCName2.size()>2) {
					return getAutoTitle(obj,sr,liTCName2[3])
				}
				return ''
			}
		}else {
			return 'UNKNOWN TITLE'
		}
	}








	private static String getAutoTitle(String obj, String sr, String code) {

		switch (code) {
			case "CRE" :
				return "Création $obj"
				break
			case "LEC" :
				return "Lecture $obj"
				break
			case "MAJ" :
				return "Mise à jour $obj"
				break
			case "REC" :
				return "Recherche $obj"
				break
			case "SUP" :
				return "Suppression $obj"
				break
			case "SRA" :
				return "$obj : Ajout $sr"
				break
			case "SRL" :
				return "$obj : Lecture $sr"
				break
			case "SRM" :
				return "$obj : Modification $sr"
				break
			case "SRS" :
				return "$obj : Suppression $sr"
				break
			default :
				return '--'
		}
	}






} // end of class
