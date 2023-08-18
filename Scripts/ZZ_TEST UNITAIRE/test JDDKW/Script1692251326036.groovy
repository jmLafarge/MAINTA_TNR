import myJDDManager.JDDKW

def liUPD = ['$UPD','$UPD*ss','$UPD*ss*','$UPD*ss*qq']

liUPD.each { str ->
	println "str ='$str'" + '\tisUPD:' + JDDKW.isUPD(str).toString() + '\tstartWith:' + JDDKW.startWithUPD(str).toString() + "\toldVal='"+JDDKW.getOldValueOfKW_UPD(str)+ "\tnewVal='"+JDDKW.getNewValueOfKW_UPD(str)
}


def liTBD = ['$TBD','$TBD*','$TBD**','$TBD*xxx']

liTBD.each { str ->
	println "str ='$str'" + '\tisTBD:' + JDDKW.isTBD(str).toString() + '\tstartWith:' + JDDKW.startWithTBD(str).toString() + "\tVal='"+JDDKW.getValueOfKW_TBD(str)
}