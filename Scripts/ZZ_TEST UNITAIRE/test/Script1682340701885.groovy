

import my.SQL


def requete = "INSERT INTO CMP (ID_CODCMP, ST_DES,ID_CODUTI) VALUES (?,?,?)"

List list = ['JM05', "C'est toto",'NULL']
def resultat = SQL.sqlInstance.executeInsert(requete, list)


