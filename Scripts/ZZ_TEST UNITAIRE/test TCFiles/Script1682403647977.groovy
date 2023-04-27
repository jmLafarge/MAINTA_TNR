
import my.TCFiles

if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }

//println TCFiles.TCfileMap['ZZ.YYY.001']

println TCFiles.getTitle('AD ADMINISTRATION'+File.separator+'AD.SEC SECURITE'+File.separator+'AD.SEC.001.FON.01 Ouvrir session - mot de passe valide')
println TCFiles.getTitle('MP MAINTENANCE PREVENTIVE'+File.separator+'MP.CPT Compteur'+File.separator+'001'+File.separator+'MP.CPT.001.LEC.01')
println TCFiles.getTitle('RO REFERENCIEL ORGANISATION'+File.separator+'RO.ACT Acteur'+File.separator+'003HAB Habilitation'+File.separator+'RO.ACT.003HAB.SRA')
println TCFiles.getTitle('RO REFERENCIEL ORGANISATION'+File.separator+'RO.ACT Acteur'+File.separator+'005'+File.separator+'RO.ACT.005.FON.01 Copie Acteur')
println TCFiles.getTitle('ZZ TEST'+File.separator+'ZZ.XXX'+File.separator+'001'+File.separator+'ZZ.XXX.001.CRE.10 un titre de test case')
