import my2.ExcelSAV

def filename = 'TNR_JDD/RO/JDD.RO.ACT.xlsx'
ExcelSAV parser = new ExcelSAV()
def (headers, rows) = parser.parse(filename,'001')

println headers
println rows.each { it -> println it }






println 'Headers'
println '------------------'
headers.each { header ->
  println header
}
println "\n"
println 'Rows'
println '------------------'
rows.each { row ->
  println parser.toXml(headers, row)
}

