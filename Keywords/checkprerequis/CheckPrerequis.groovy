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
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@CompileStatic
public class CheckPrerequis {


	private static List <Map> list =[]

	private static JDD myJDD
	
	private static XSSFWorkbook PREJDDBook

	static run() {

		Log.addSubTITLE('Collecte de tous les PREREQUIS des JDD')

		//Récupére la liste de tous les PREREQUIS de tous les JDD
		JDDFiles.JDDfilemap.each { modObj,fullName ->
			Log.addDEBUG("Lecture du JDD : " + fullName,0)
			myJDD = new JDD(fullName,null,null,false)
			getAllPrerequis(fullName,true)
		}



		Log.addSubTITLE('Contrôle des PREREQUIS des JDD')
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
			if (PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ').toString())) {
				PREJDD.checkPREJDD(map)
			}else {
				Log.addERROR('Pas de fichier PREJDD pour '+map.getAt('PREJDDMODOBJ'))
			}
		}
		
		
		list =[]
		Log.addSubTITLE('Collecte de tous les PREREQUIS des PREJDD')
		
		PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->
			Log.addDEBUG("Lecture du JDD pour modObj : " + modObj,0)
			myJDD = new JDD(JDDFiles.getJDDFullName(modObj),null,null,false)
			PREJDDBook = my.XLS.open(fullName)
			getAllPrerequis(fullName,false)
		}
		
		
		Log.addSubTITLE('Contrôle des PREREQUIS des PREJDD')
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
			if (PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ').toString())) {
				PREJDD.checkPREJDD(map)
			}else {
				Log.addERROR('Pas de fichier PREJDD pour '+map.getAt('PREJDDMODOBJ'))
			}
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
	private static getAllPrerequis(String fullName,boolean forJDD) {

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
							prerequisMap.putAt('JDDNAME',fullName)
						prerequisMap.putAt('JDDID',myJDD.getHeader(i))
						if (forJDD) {
							prerequisMap.putAt('LISTCDTVAL',getListCDTVAL(myJDD.getDatas(),i))
						}else {
							Sheet shPREJDD = PREJDDBook.getSheet(sheet.getSheetName())
							if (shPREJDD) {
								List <String> headersPREJDD = my.XLS.loadRow(shPREJDD.getRow(0))
								prerequisMap.putAt('LISTCDTVAL',getListCDTVAL(my.PREJDD.loadDATA(shPREJDD,headersPREJDD.size()),i))
							}else {
								Log.addDEBUG('le sheet '+sheet.getSheetName() + " n'existe pas dans ce PREJDD")
							}
						}
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
	private static List getListCDTVAL(List <List> datas,int index) {
		List PKlist=InfoBDD.getPK(myJDD.getDBTableName())
		List list =[]
		datas.each{
			if (it[index]!=null && it[index]!='' && !JDDKW.isNU(it[index]) && !JDDKW.isNULL(it[index]) && !JDDKW.isVIDE(it[index]) ) {
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
