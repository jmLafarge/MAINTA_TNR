import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.Parser
import org.apache.tika.sax.BodyContentHandler

def parser = new AutoDetectParser()
def handler = new BodyContentHandler()
def metadata = new Metadata()
def context = new ParseContext()
context.set(Parser.class, parser)

def file = new File('C:/MAINTA/SERVER/IIS/MAINTA_TEST/Bin/mos_xml.dll')
try {
    FileInputStream inputstream = new FileInputStream(file)
    parser.parse(inputstream, handler, metadata, context)
    inputstream.close();
} catch (Exception e) {
    println "Erreur : ${e.getMessage()}"
}

println "Type : ${metadata.get('Content-Type')}"
println "Version : ${metadata.get('version')}"

