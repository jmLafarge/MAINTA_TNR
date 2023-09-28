package tnrJDDManager

import groovy.io.FileType
import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrLog.Log



@CompileStatic
public class JDDFileMapper {


	private static final String CLASS_NAME = 'JDDFileMapper'


	public static Map <String,String> JDDfilemap = [:]


	/**
	 * 
	 * 
	 * 
	 * modObj   JDDFullName
	 * ------   -------------------------- 
	 * AD.SEC : TNR_JDD\AD\JDD.AD.SEC.xlsx, 
	 * GLOBAL : TNR_JDD\JDD.GLOBAL.xlsx, 
	 * RO.ACT : TNR_JDD\RO\JDD.RO.ACT.xlsx,
	 * 
	 * 
	 * @return
	 */


	static {
		Log.addTraceBEGIN(CLASS_NAME,"static",[:])

		Log.addTrace("Load JDDfileList")
		Log.addTrace("")
		Log.addTrace("\t"+'MODOBJ'.padRight(16) + 'JDDFULLNAME')

		new File(TNRPropertiesReader.getMyProperty('JDD_PATH')).eachFileRecurse(FileType.FILES) { file ->

			// keep only TC Name like JDD.*.xlsx
			if (file.getName()==~ /JDD\..*\.xlsx/ && file.getPath()==~ /^((?!standby).)*$/) {

				String modObj = file.getName().replace('JDD.','').replace('.xlsx','')

				JDDfilemap.put(modObj,file.getPath())

				Log.addDEBUG('\t' + modObj.padRight(16) + file.getPath())
			}
		}
		Log.addTraceEND(CLASS_NAME,"static")
	}



	/**
	 * Récupère le nom complet du fichier JDD à partir du nom du cas de test.
	 * @param casDeTest le nom du cas de test.
	 * @return le nom complet du fichier JDD correspondant, ou null si aucun fichier JDD ne correspond.
	 */
	static String getFullnameFromCasDeTest(String casDeTest) {
		Log.addTraceBEGIN(CLASS_NAME,"getFullnameFromCasDeTest",[casDeTest:casDeTest])
		def modObj = Tools.getMobObj(casDeTest)
		Log.addTraceEND(CLASS_NAME,"getFullnameFromCasDeTest",JDDfilemap[modObj])
		return JDDfilemap[modObj]
	}



	/**
	 * @param modObj
	 * @return
	 */
	static String getFullnameFromModObj(String modObj) {
		Log.addTraceBEGIN(CLASS_NAME,"getFullname",[modObj:modObj])
		Log.addTraceEND(CLASS_NAME,"getFullname",JDDfilemap[modObj])
		return JDDfilemap[modObj]
	}



	/**
	 * @param modObj
	 * @param fullName
	 * @return
	 */
	static void add(String modObj,String fullName) {
		Log.addTraceBEGIN(CLASS_NAME,"add",[modObj:modObj , fullName:fullName])
		JDDfilemap.put(modObj,fullName)
		Log.addTraceEND(CLASS_NAME,"add")
	}

} // Fin de class