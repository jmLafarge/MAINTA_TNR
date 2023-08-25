package tnrJDDManager

import groovy.io.FileType
import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrLog.Log



@CompileStatic
public class JDDFileMapper {


	private static final String CLASS_FORLOG = 'JDDFiles'


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
		Log.addTraceBEGIN(CLASS_FORLOG,"static",[:])
		Log.addSubTITLE("Load JDDfileList",'-',120)

		Log.addINFO("\t"+'MODOBJ'.padRight(16) + 'JDDFULLNAME')

		new File(TNRPropertiesReader.getMyProperty('JDD_PATH')).eachFileRecurse(FileType.FILES) { file ->

			// keep only TC Name like JDD.*.xlsx
			if (file.getName()==~ /JDD\..*\.xlsx/ && file.getPath()==~ /^((?!standby).)*$/) {

				String modObj = file.getName().replace('JDD.','').replace('.xlsx','')

				JDDfilemap.put(modObj,file.getPath())

				Log.addINFO('\t' + modObj.padRight(16) + file.getPath())
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"static")
	}



	/**
	 * Récupère le nom complet du fichier JDD à partir du nom du cas de test.
	 * @param casDeTest le nom du cas de test.
	 * @return le nom complet du fichier JDD correspondant, ou null si aucun fichier JDD ne correspond.
	 */
	static String getFullnameFromCasDeTest(String casDeTest) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getFullnameFromCasDeTest",[casDeTest:casDeTest])
		def modObj = Tools.getMobObj(casDeTest)
		Log.addTraceEND(CLASS_FORLOG,"getFullnameFromCasDeTest",JDDfilemap[modObj])
		return JDDfilemap[modObj]
	}



	/**
	 * @param modObj
	 * @return
	 */
	static String getFullnameFromModObj(String modObj) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getFullname",[modObj:modObj])
		Log.addTraceEND(CLASS_FORLOG,"getFullname",JDDfilemap[modObj])
		return JDDfilemap[modObj]
	}



	/**
	 * @param modObj
	 * @param fullName
	 * @return
	 */
	static void add(String modObj,String fullName) {
		Log.addTraceBEGIN(CLASS_FORLOG,"add",[modObj:modObj , fullName:fullName])
		JDDfilemap.put(modObj,fullName)
		Log.addTraceEND(CLASS_FORLOG,"add")
	}

} // end of class