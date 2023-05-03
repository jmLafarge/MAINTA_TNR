
import java.text.SimpleDateFormat

import groovy.time.TimeCategory
import groovy.time.TimeDuration

def now = new Date()

def dateString = "2023-05-03 14:42:03.513"

// Définir le format de la chaîne de caractères
def dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

// Transformer la chaîne de caractères en un objet Date
def date = dateFormat.parse(dateString)

TimeDuration timeDuration = TimeCategory.minus( now, date )

println timeDuration.toMilliseconds() / 1000

/*
println(now.toTimestamp().getClass( //class java.sql.Timestamp
        ))

println(now.format('yyyy-dd-MM 00:00:00.000'))

println(now.getClass())

println(now instanceof Date)

println(new Date().format('dd/MM/yyyy'))
*/

