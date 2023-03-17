
println GroovySystem.version

def li = ["coucou", "hello"]

println li.toListString()

def index = 6
println li.size()

println li[1]

li.add(2,'titi')

print li


while (li.size()<index) {
	li.add(li.size(),null)
}
li.add(index,'toto')
my.Log.addINFO(li.toListString())

