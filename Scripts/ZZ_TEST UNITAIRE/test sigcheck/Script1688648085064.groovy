
// test de sigcheck (Utilitaire de MS )
// à télécharger sur https://learn.microsoft.com/fr-fr/sysinternals/downloads/sigcheck
// sigcheck64.exe -n ne sort que la version fichier --> mais c'est pas la bonne !

def process = "TNR_Sigcheck\\sigcheck64.exe -nobanner  C:\\MAINTA\\SERVER\\IIS\\MAINTA_TEST\\Bin\\mos_xml.dll".execute()
def outputStream = new StringBuffer()
process.consumeProcessOutput(outputStream, System.err)
process.waitFor()

println outputStream.toString()

def outputStreamString = outputStream.toString()
def outputLines = outputStreamString.split("\n")
def outputMap = [:]

outputLines.each { line ->
	if (line.contains(':')) {
		def (key, value) = line.split(":").collect { it.trim() }
		outputMap[key] = value
	}
}

outputMap.each { key, value ->
	println "$key\t: $value"
}

/* résultat --> File version n'est pas la version attendue qui est 13.0.0.1 !

c	: \mainta\server\iis\mainta_test\bin\mos_xml.dll
Verified	: Unsigned
Link date	: 10
Publisher	: n/a
Company	: APAVE
Description	: Mainta Web Module IIS
Product	: Mainta
Prod version	: 13.0.2
File version	: 13.0.2
MachineType	: 64-bit

*/