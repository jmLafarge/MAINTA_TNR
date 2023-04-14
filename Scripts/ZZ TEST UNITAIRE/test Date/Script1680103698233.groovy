
def now = new Date()
println now
println now.toTimestamp() //2023-01-18 07:52:43.967
println now.toTimestamp().getClass() //class java.sql.Timestamp

println now.format('yyyy-dd-MM 00:00:00.000')

println now.getClass()
println now instanceof java.util.Date

println new Date().format('dd/MM/yyyy')
