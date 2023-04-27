

def varname = 'foo'
def value = 42

new GroovyShell(binding).evaluate("${varname} = ${value}")

println foo