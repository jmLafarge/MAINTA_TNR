import internal.GlobalVariable
import my.PREJDDFiles
import my.result.TNRResult
import my.InfoBDD
import my.JDDFiles
import my.Log
import my.NAV

Log.setDebugLevel(0)
Log.setDebugDeph(1)

Log.addTITLE("Lancement de CREATE PREJDD IN DB")
if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (PREJDDFiles.PREJDDfilemap.isEmpty()) { PREJDDFiles.load() }
NAV.loadJDDGLOBAL()


Log.addSubTITLE('Création des PREJDD')



// pour le proto

PREJDDFiles.insertPREJDDinDB('RO.CAT','001')

PREJDDFiles.insertPREJDDinDB('RO.HAB','001') 

PREJDDFiles.insertPREJDDinDB('RO.MET','001')

PREJDDFiles.insertPREJDDinDB('RO.CAL','001')

PREJDDFiles.insertPREJDDinDB('RO.CAL','001A') 

PREJDDFiles.insertPREJDDinDB('RT.EMP','001')

PREJDDFiles.insertPREJDDinDB('RO.ORG','001')

PREJDDFiles.insertPREJDDinDB('RO.ORG','001A')

PREJDDFiles.insertPREJDDinDB('RO.ACT','001')
PREJDDFiles.insertPREJDDinDB('RO.ACT','005')

PREJDDFiles.insertPREJDDinDB('RO.ACT','003HAB')

PREJDDFiles.insertPREJDDinDB('RO.ACT','003MET')
PREJDDFiles.insertPREJDDinDB('RO.ACT','004EMP')

PREJDDFiles.insertPREJDDinDB('RO.SOCIETE','001')

PREJDDFiles.insertPREJDDinDB('RO.UTI','001')

// En plus pour Fournisseur

PREJDDFiles.insertPREJDDinDB('RO.CCO','001')
PREJDDFiles.insertPREJDDinDB('RO.DEV','001')

PREJDDFiles.insertPREJDDinDB('AC.CEM','001')
PREJDDFiles.insertPREJDDinDB('AC.CMR','001')
PREJDDFiles.insertPREJDDinDB('AC.CPA','001')
PREJDDFiles.insertPREJDDinDB('AC.CPO','001')


PREJDDFiles.insertPREJDDinDB('RO.ADR','001')
PREJDDFiles.insertPREJDDinDB('RO.LIE','001')
PREJDDFiles.insertPREJDDinDB('RO.FOU','001A')
PREJDDFiles.insertPREJDDinDB('RO.FOU','001')

PREJDDFiles.insertPREJDDinDB('MP.CPT','001')

PREJDDFiles.insertPREJDDinDB('RT.NOM','001') //ajout

PREJDDFiles.insertPREJDDinDB('RT.ART','001')

PREJDDFiles.insertPREJDDinDB('RT.ART','001A')
PREJDDFiles.insertPREJDDinDB('RT.ART','001B')
PREJDDFiles.insertPREJDDinDB('RT.ART','001C')

PREJDDFiles.insertPREJDDinDB('RT.MOY','001')

PREJDDFiles.insertPREJDDinDB('RT.EQU','001')
//PREJDDFiles.insertPREJDDinDB('RT.EQU','001A') --> PREJDD vide
PREJDDFiles.insertPREJDDinDB('RT.EQU','001B')
PREJDDFiles.insertPREJDDinDB('RT.EQU','001C')


PREJDDFiles.insertPREJDDinDB('RT.MAT','001')
//PREJDDFiles.insertPREJDDinDB('RT.MAT','001A') --> PREJDD vide
PREJDDFiles.insertPREJDDinDB('RT.MAT','001B')
PREJDDFiles.insertPREJDDinDB('RT.MAT','001C')


Log.addTITLE("Fin des créations des PRE REQUIS")



