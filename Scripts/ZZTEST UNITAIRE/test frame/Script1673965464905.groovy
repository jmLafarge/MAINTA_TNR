import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.SelectorMethod






println findTestObject('0 - COMMUN/Accueil/div_desk').getClass()
println findTestObject('0 - COMMUN/Accueil/div_desk').getProperties()
println findTestObject('0 - COMMUN/Accueil/div_desk').getParentObject()
println findTestObject('0 - COMMUN/Accueil/div_desk').getSelectorCollection().get(SelectorMethod.XPATH)
println findTestObject('0 - COMMUN/Accueil/div_desk').getSelectorMethod()

println findTestObject('null').getParentObject()


