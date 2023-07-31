// Créer une List de Map avec des associations nom-boolean
List<Map<String, Boolean>> maListeDeMaps = [
	[nom: "variable1", trace: true],
	[nom: "variable2", trace: false],
	[nom: "variable3", trace: true]
]

// Afficher la liste de Map avant la suppression
println("Liste avant la suppression : $maListeDeMaps")

// Supprimer le dernier Map de la liste
maListeDeMaps.remove(-1)

// Afficher la liste de Map après la suppression
println("Liste après la suppression : $maListeDeMaps")
