package checkprerequis

import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import my.InfoBDD
import my.JDD
import my.JDDFiles
import my.Log
import my.PREJDD
import my.PREJDDFiles
import my.result.TNRResult
import my.JDDKW

@CompileStatic
public class CheckPrerequis {


	private static List <Map> list =[]

	private static JDD myJDD

	static run() {

		Log.addSubTITLE('Collecte de tous les PREREQUIS JDD')

		//Récupére la liste de tous les PREREQUIS de tous les JDD
		JDDFiles.JDDfilemap.each { modObj,fullName ->
			Log.addDEBUG("Lecture du JDD : " + fullName,0)
			myJDD = new JDD(fullName,null,null,false)
			getAllPrerequis()
		}



		Log.addSubTITLE('Contrôle des PREREQUIS dans les PREJDD')
		TNRResult.addSUBSTEP("Détails en cas d'erreur")
		TNRResult.addDETAIL('    CAS DE TEST      -     VALEUR')
		Log.addINFO('')
		/*
		 * Controle si tous les PREREQUIS des JDD sont bien dans les PREJDD
		 */
		list.eachWithIndex { map,idx ->
			Log.addDEBUG(idx + ' : ' + PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ').toString()))
			map.each { key,val ->
				Log.addDEBUG('\t' + key + ' : ' +val)
			}
			PREJDD.checkPREJDD(map)
		}
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
	private static getAllPrerequis() {

		for(Sheet sheet: myJDD.book) {
			if (myJDD.isSheetAvailable(sheet.getSheetName())) {

				myJDD.loadTCSheet(sheet)
				String PRInThisSheet =''
				myJDD.getParam('PREREQUIS').eachWithIndex { value,i ->
					if (!(value in ['', 'PREREQUIS', 'OBSOLETE'])) {
						PRInThisSheet = PRInThisSheet + myJDD.getHeader(i)+','
						Log.addDEBUG('\theader = ' + myJDD.getHeader(i))
						Log.addDEBUG('\tvalue = ' + value)
						Map prerequisMap = [:]
						prerequisMap.putAt('PREJDDMODOBJ',value.split(/\*/)[0])
						prerequisMap.putAt('PREJDDTAB',value.split(/\*/)[1])
						prerequisMap.putAt('PREJDDID',value.split(/\*/)[2])
						prerequisMap.putAt('JDDNAME',myJDD.getJDDFullName())
						prerequisMap.putAt('TAB',sheet.getSheetName())
						prerequisMap.putAt('JDDID',myJDD.getHeader(i))
						prerequisMap.putAt('LISTCDTVAL',getListCDTVAL(i))
						Log.addDEBUG('\tPrerequisMap : ')
						prerequisMap.each { key,val ->
							Log.addDEBUG('\t\t'+key + ' : ' +val)
						}
						list.add(prerequisMap)
					}
				}
				if (PRInThisSheet.size()>0) {
					Log.addDEBUGDETAIL("Lecture onglet '" + sheet.getSheetName() + "' --> PREREQUIS : " + PRInThisSheet.substring(0,PRInThisSheet.length()-1),0 )
				}else {
					Log.addDEBUGDETAIL("Lecture onglet '" + sheet.getSheetName() + "'",0 )
				}
			}
		}
	}




	/**
	 *
	 * @param sheet
	 * @param index
	 * @return
	 */
	private static List getListCDTVAL(int index) {
		List PKlist=InfoBDD.getPK(myJDD.getDBTableName())
		List list =[]
		myJDD.datas.each{
			if (it[index]!=null && it[index]!='' && !JDDKW.isNU(it[index]) && !JDDKW.isNULL(it[index]) ) {
				if (it[0].toString().contains('.CRE.') && PKlist.contains(myJDD.getHeader(index))) {
					Log.addDEBUG("skip : " + "'" + it[0] + "' - '" + it[index] + "'")
				}else {
					list.add("'" + it[0] + "' - '" + it[index] + "'")
				}
			}
		}
		return list
	}


} //end of class
