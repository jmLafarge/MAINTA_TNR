import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes

println new File("C:/MAINTA/SERVER/IIS/MAINTA_TEST/Bin/mos_xml.dll").getName()

println new File("C:/MAINTA/SERVER/IIS/MAINTA_TEST/Bin/mos_xml.dll").length()

Path filePath = Paths.get("C:/MAINTA/SERVER/IIS/MAINTA_TEST/Bin/mos_xml.dll")
BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class)

// Accéder aux métadonnées du fichier
println "Nom du fichier : " + filePath.getFileName()
println "Chemin absolu : " + filePath.toAbsolutePath()
println "Taille : " + attr.size()
println "Date de création : " + attr.creationTime()
println "Date de dernière modification : " + attr.lastModifiedTime()
println "Permissions : " + attr.getProperties()