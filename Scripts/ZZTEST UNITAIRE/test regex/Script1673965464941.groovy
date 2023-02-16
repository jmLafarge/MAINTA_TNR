import groovy.text.SimpleTemplateEngine

def engine = new SimpleTemplateEngine()

Map data = [ 'ID_CODINT' : 'JML001', 'ST_DES':'DESCRIPTION']

String template = 'toto${ID_CODINT}titi avec un{ST_DES}titi'

Map binding = [:]

def matcher = template =~  /\$\{(.+?)\}/
println matcher.size()


matcher.each{k,v->
    println "value " + k + " at index " +v
	
	binding.put(v,data.getAt(v))

}

println binding

//assert binding == ['ID_CODINT':'JML001', 'ST_DES':'DESCRIPTION']


def result = engine.createTemplate(template).make(binding).toString()

println result