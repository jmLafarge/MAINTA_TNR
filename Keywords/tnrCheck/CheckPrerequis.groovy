package tnrCheck

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCommon.ExcelUtils
import tnrJDDManager.JDD
import tnrJDDManager.JDDData
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDHeader
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrPREJDDManager.PREJDD
import tnrPREJDDManager.PREJDDFileMapper
import tnrSqlManager.InfoDB

@CompileStatic
public class CheckPrerequis {


	private static final String CLASS_FORLOG = 'CheckPrerequis'

	private static List <Map> list =[]
	private static JDD myJDD
	private static XSSFWorkbook PREJDDBook
	private static boolean status = true

	static runForJDD() {

		Log.addTraceBEGIN(CLASS_FORLOG,"runForJDD",[:])

		status = true
		list =[]

		Log.addSubTITLE('Contrôle des PREREQUIS des JDD')
		Log.addINFO('Collecte de tous les PREREQUIS des JDD')


		//Récupére la liste de tous les PREREQUIS de tous les JDD
		JDDFileMapper.JDDfilemap.each { modObj,fullName ->
			Log.addDEBUG("Lecture du JDD : " + fullName)
			myJDD = new JDD(fullName,null,null)
			getAllPrerequis('JDD',fullName)
		}

		Log.addINFO('Contrôle ...')
		Log.addINFO('')
		/*
		 * Controle si tous les PREREQUIS des JDD sont bien dans les PREJDD
		 */
		list.eachWithIndex { map,idx ->
			Log.addTrace(idx + ' : ' + PREJDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString()))
			map.each { key,val ->
				Log.addTrace('\t' + key + ' : ' +val)
			}
			if (PREJDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString())) {
				if (!PREJDD.checkPREJDD(map)) {
					status=false
				}
			}else {
				Log.addERROR('Pas de fichier PREJDD pour '+map.getAt('PREJDDMODOBJ'))
				status=false
			}
		}

		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FORLOG,"runForJDD")
	}



	
	
	
	static runForPREJDD() {

		Log.addTraceBEGIN(CLASS_FORLOG,"runForPREJDD",[:])

		status = true
		list =[]

		Log.addSubTITLE('Contrôle des PREREQUIS des PREJDD')
		Log.addINFO('Collecte de tous les PREREQUIS des PREJDD')

		PREJDDFileMapper.PREJDDfilemap.each { modObj,fullName ->
			Log.addDEBUG("Lecture du JDD pour modObj : " + modObj)
			myJDD = new JDD(JDDFileMapper.getFullnameFromModObj(modObj),null,null)
			PREJDDBook = ExcelUtils.open(fullName)
			getAllPrerequis('PREJDD',fullName)
		}

		Log.addINFO('Contrôle')
		Log.addINFO('')
		/*
		 * Controle si tous les PREREQUIS des JDD sont bien dans les PREJDD
		 */
		list.eachWithIndex { map,idx ->
			Log.addTrace(idx + ' : ' + PREJDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString()))
			map.each { key,val ->
				Log.addTrace('\t' + key + ' : ' +val)
			}
			if (PREJDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString())) {
				if (!PREJDD.checkPREJDD(map)) {
					status=false
				}
			}else {
				Log.addERROR('Pas de fichier PREJDD pour '+map.getAt('PREJDDMODOBJ'))
				status=false
			}
		}

		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FORLOG,"runForPREJDD")
	}


	/**
	 * @param list : to be completed
	 * [
	 PREJDDMODOBJ:RO.ACT,
	 PREJDDTAB:001,
	 PREJDDID:ID_CODINT,
	 JDDNAME:TNR_JDD\RO\JDD.RO.ACT.xlsx,
	 TAB:001,
	 JDDID:ID_CODINT,
	 LISTCDTVAL:[
	 'RO.ACT.001.CRE.01' - 'RO.ACT.001.CRE.01',
	 'RO.ACT.001.LEC.01' - 'RO.ACT.001.LEC.01',
	 'RO.ACT.001.MAJ.01' - 'RO.ACT.001.MAJ.01',
	 'RO.ACT.001.SUP.01' - 'RO.ACT.001.SUP.01',
	 'RO.ACT.001.REC.01' - 'RO.ACT.001.REC.01']
	 ]
	 *
	 */
	private static getAllPrerequis(String type, String fullName) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getAllPrerequis",[type:type , fullName:fullName])

		for(Sheet sheet: myJDD.book) {
			if (myJDD.isSheetAvailable(sheet.getSheetName())) {

				myJDD.loadTCSheet(sheet)
				String PRInThisSheet =''
				myJDD.myJDDParam.getAllPREREQUIS().each { name,value ->
					if (!(value in ['', 'OBSOLETE', 'CALCULEE'])) {
						if (value ==~ /.*\*.{3,}\*.{3,}.*/) {
							PRInThisSheet = PRInThisSheet + name+','
							Log.addTrace('\tname = ' + name)
							Log.addTrace('\tvalue = ' + value)
							Map prerequisMap = [:]
							prerequisMap.putAt('PREJDDMODOBJ',value.split(/\*/)[0])
							prerequisMap.putAt('PREJDDTAB',value.split(/\*/)[1])
							prerequisMap.putAt('PREJDDID',value.split(/\*/)[2])
							prerequisMap.putAt('JDDNAME',fullName)
							prerequisMap.putAt('JDDID',name)
							if (type=='JDD') {
								prerequisMap.putAt('LISTCDTVAL',getListCDTVAL(myJDD.myJDDData.getList(),myJDD.getDBTableName(),name))
							}else if (type == 'PREJDD'){
								Sheet PREJDDsheet = PREJDDBook.getSheet(sheet.getSheetName())
								if (PREJDDsheet) {
									JDDHeader PREJDDheader = new JDDHeader(PREJDDsheet)
									JDDData PREJDDData = new JDDData(PREJDDsheet,PREJDDheader.getList(),'')
									prerequisMap.putAt('LISTCDTVAL',getListCDTVAL(PREJDDData.getList(),myJDD.getDBTableName(),name))
								}else {
									Log.addTrace('le sheet '+sheet.getSheetName() + " n'existe pas dans ce PREJDD")
								}
							}else {
								Log.addERROR("Type '$type' non connu !")
							}
							Log.addTrace('\tPrerequisMap : ')
							prerequisMap.each { key,val ->
								Log.addTrace('\t\t'+key + ' : ' +val)
							}
							list.add(prerequisMap)
						}else {
							Log.addERROR("$fullName(${sheet.getSheetName()}) paramètre PREREQUIS pour '$name'")
							Log.addDETAIL("La valeur '$value' n'est pas autorisée !")
						}
					}
				}
				//}
				if (PRInThisSheet.size()>0) {
					Log.addDEBUGDETAIL("Lecture onglet '" + sheet.getSheetName() + "' --> PREREQUIS : " + PRInThisSheet.substring(0,PRInThisSheet.length()-1))
				}else {
					Log.addDEBUGDETAIL("Lecture onglet '" + sheet.getSheetName() + "'")
				}
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"getAllPrerequis")
	}







	/**
	 * Récupere la liste des couples CDT - Valeur de la colonne name
	 *
	 * Sauf si la valeur est null ou vide ou $NU ou $NULK ou $VIDE
	 * Sauf si le CDT est un cdt de création et que le name est une PK (il ne doit pas être en prérequis casr on va le créer)
	 * Pour les $UPD*oldValue*newValue, la oldValue doit être en prérequis, mais pas la newValue
	 *
	 * @param datas
	 * @param table
	 * @param name
	 * @return
	 */
	private static List getListCDTVAL(List <Map<String, Map<String, Object>>> datas,String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getListCDTVAL",[datas:datas , table:table , name:name])
		List list =[]

		if (InfoDB.isTableExist(table)) {

			//if (InfoDB.inTable(table, name)) {
			List<List<Object>> listAllCDTVAL = []
			datas.each { outerMap ->
				outerMap.each { key, innerMap ->
					listAllCDTVAL << [key, innerMap[name]]
				}
			}
			listAllCDTVAL.each {li ->
				String cdt = li[0]
				String val = li[1]
				if (val!=null && val!='' && !JDDKW.isNU(val) && !JDDKW.isNULL(val) && !JDDKW.isVIDE(val) ) {
					String cdtVal = "'$cdt' - '$val'"
					if (cdt.toString().contains('.CRE.') && InfoDB.isPK(table,name)) {
						Log.addTrace("skip : $cdtVal")
					}else {
						if (JDDKW.getOldValueOfKW_UPD(val)) {
							cdtVal= "'$cdt' - '" + JDDKW.getOldValueOfKW_UPD(val) + "'"
							list.add(cdtVal)
							Log.addTrace("add $cdtVal \$UPD")
						}else {
							list.add(cdtVal)
							Log.addTrace("add $cdtVal")
						}
					}
				}
			}
			//}else {
			//	Log.addERROR("La colonne '$name' n'existe pas dans la table '$table'!")
			//}
		}else {
			Log.addERROR("La table '$table' n'existe pas!")
		}
		Log.addTraceEND(CLASS_FORLOG,"getListCDTVAL",list)
		return list
	}


} //end of class