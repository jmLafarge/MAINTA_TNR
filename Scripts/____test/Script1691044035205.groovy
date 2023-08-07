
import my.SQL

println SQL.getMaintaVersion()


def myMap = [
	key1: 'valeur1',
	key2: 'valeur2',
	key3: 'valeur3'
]

def premiereValeur = myMap.values().first()

println "Première valeur de la Map : ${premiereValeur}"

