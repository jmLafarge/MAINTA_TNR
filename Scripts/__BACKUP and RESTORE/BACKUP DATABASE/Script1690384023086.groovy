
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import javax.swing.JOptionPane

import my.PropertiesReader
import my.SQL


String suffix = JOptionPane.showInputDialog(null, "Saisir un suffixe de nom de fichier ", "BACKUP", JOptionPane.QUESTION_MESSAGE)

SQL.setNewInstance(SQL.AllowedDBProfilNames.LEGACYTNR)

String backupFile = SQL.backup(suffix)

SQL.sqlInstance.close()

// on déplace le fichier dans le dossier LOCAL
String origin = PropertiesReader.getMyProperty('LEGACYTNR_BDDBACKUPPATH') + File.separator + backupFile
String dest = PropertiesReader.getMyProperty('LOCALTNR_BDDBACKUPPATH') + File.separator + backupFile
Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)
