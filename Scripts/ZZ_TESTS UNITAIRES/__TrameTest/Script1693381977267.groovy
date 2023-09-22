

import tnrLog.Log



/**
 * TESTS UNITAIRES
 * 
 * Pour tester 
 * 
 * 		Log.addAssert(CLASS_FOR_LOG,"Un message pour expliquer de test" , la valeur de retour attendue , la fonction a testé)
 * 
 * Pour tester les fonctions private
 * 
 * 		import java.lang.reflect.Method
 * 
 * 		Method method = LaClassATesté.class.getDeclaredMethod("La methode private", énumérer les class des paramètres ex : List.class, String.class, Object.class)
 * 
 * 		method.setAccessible(true)
 * 
 * 		Log.addAssert(CLASS_FOR_LOG,"(private) Un message pour expliquer de test" , la valeur de retour attendue ,  method.invoke(instance de LaClassATesté (ou la LaClassATesté si static), énumérer les valeurs des paramètres ex : ['truc','machin','chose'], 'INTER','ID_CODINT'))
 * 
 * 		IMPORTANT : Pour passer la valeur null quand il n'y a qu'un seul paramètre, il faut caster ce null en tableau : maMethode.invoke(monObjet, [null] as Object[])
 * 
 *
 * @author JM Lafarge
 * @version 1.0
 */


final String CLASS_FOR_LOG = 'package.Class'


Log.addAssert(CLASS_FOR_LOG,"Controle OK",true,true)