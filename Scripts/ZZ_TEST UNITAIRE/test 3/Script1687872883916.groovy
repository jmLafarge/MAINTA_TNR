
String xpath = "//input[@id='" + 'NAME' +"\${OPTIONRADIO}' and @type='radio')]"
def matcher = xpath =~ /\$\{(.+?)\}/

println matcher[0]
