import javax.swing.JOptionPane

def filePath = "fichier.txt"

int option = JOptionPane.showConfirmDialog(
	null,
	"Voulez-vous vraiment supprimer le fichier ?",
	"Confirmation de suppression",
	JOptionPane.YES_NO_OPTION
)

if (option == JOptionPane.YES_OPTION) {
	def file = new File(filePath)
	if (file.exists()) {
		file.delete()
		println "Le fichier a été supprimé avec succès."
	} else {
		println "Le fichier n'existe pas."
	}
} else {
	println "La suppression a été annulée."
}


def chaine = "20230727_145503-MASTERTNR_MAINTA_TNR_MASTER_V13.0.2.bak"
def nouvelleChaine = chaine.substring(16)
println nouvelleChaine



