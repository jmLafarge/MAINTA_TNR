package checkprerequis

import my.Log as MYLOG


public class CheckPrerequis {


	static run() {

		MYLOG.addSubTITLE('Collecte de tous les PREREQUIS JDD')
		/*
		 * Récupére la liste de tous les PREREQUIS de tous les JDD
		 */
		List list =[]
		my.JDDFiles.JDDfilemap.each { modObj,fullName ->
			MYLOG.addDEBUG("Lecture du JDD : " + fullName,0)
			def myJDD = new my.JDD(fullName,null,null,false)
			myJDD.getAllPrerequis(list)
		}



		MYLOG.addSubTITLE('Contrôle des PREREQUIS dans les PREJDD')
		MYLOG.addSUBSTEP("Détails en cas d'erreur")
		MYLOG.addDETAIL('    CAS DE TEST      -     VALEUR')
		MYLOG.addINFO('')
		/*
		 * Controle si tous les PREREQUIS des JDD sont bien dans les PREJDD
		 */
		list.eachWithIndex { map,idx ->
			MYLOG.addDEBUG(idx + ' : ' + my.PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ')))
			map.each { key,val ->
				MYLOG.addDEBUG('\t' + key + ' : ' +val)
			}
			my.PREJDD.checkPREJDD(map)
		}
	}
}
