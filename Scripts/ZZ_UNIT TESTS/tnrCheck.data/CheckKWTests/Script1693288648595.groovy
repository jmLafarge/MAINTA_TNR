

import tnrCheck.data.CheckKW
import tnrLog.Log


/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

/**
 *
 * TEST
 * 
 * 	checkKW(String fileType, String JDDFullname, String sheetName){
 * 	public boolean checkKW( String cdt, String name, def val ) {
 *
 */


CheckKW checkKW = new CheckKW( 'JDD','Le JDDFullname', 'Le sheetName')

Log.addAssert("checkValue un varchar" , true ,  checkKW.checkValue('Le cas de test', 'NAME', 'MonNom'))

Log.addAssert("checkValue un numéric" , true ,  checkKW.checkValue('Le cas de test', 'NAME', 1))
Log.addAssert('checkValue $SEQUENCEID' , true ,  checkKW.checkValue('Le cas de test', 'NAME', '$SEQUENCEID'))
Log.addAssert('checkValue $ORDRE' , true ,  checkKW.checkValue('Le cas de test', 'NAME', '$ORDRE'))
Log.addAssert('checkValue $UPD*OLD*NEW' , true ,  checkKW.checkValue('Le cas de test', 'NAME', '$UPD*OLD*NEW'))

Log.addAssert('checkValue $UPD*OLD' , false ,  checkKW.checkValue('Le cas de test', 'NAME', '$UPD*OLD'))
Log.addAssert('checkValue $TBD' , true ,  checkKW.checkValue('Le cas de test', 'NAME', '$ORDRE'))

Log.addAssert('checkValue $UNK' , false ,  checkKW.checkValue('Le cas de test', 'NAME', '$UNK'))

checkKW = new CheckKW( 'PREJDD','Le PREJDDFullname', 'Le sheetName')
Log.addAssert('checkValue $UPD*OLD*NEW dans un PREJDD' , false ,  checkKW.checkValue('Le cas de test', 'NAME', '$UPD*OLD*NEW'))

	

