
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit
import java.io.StringReader

//def texteRTF = "{\\rtf1\\ansi{\\fonttbl\\f0\\fswiss Helvetica;}\\f0\\par Bienvenue dans mon document RTF!\\par }"
//def texteRTF ="{\\rtf1\\fbidis\\ansi\\ansicpg0\\uc1\\deff0\\deflang0\\deflangfe0{\\fonttbl{\\f0\\fnil Arial;}}{\\colortbl;}{\\stylesheet{\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0 Paragraph Style;}{\\*\\cs1\\f0\\fs24 Font Style;}}      \\pard\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0\\itap0 \\plain \\cs1\\f0\\fs24 Coucou\\par}"
//def texteRTF ="{\\rtf1\\fbidis\\ansi\\ansicpg0\\uc1\\deff0\\deflang0\\deflangfe0{\\fonttbl{\\f0\\fnil Arial;}}{\\colortbl;}{\\stylesheet{\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0 Paragraph Style;}{\\*\\cs1\\f0\\fs24 Font Style;}}\\pard\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0\\itap0 \\plain \\cs1\\f0\\fs24 Coucou\\par}"

def texteRTF ="{\\rtf1\\fbidis\\ansi\\ansicpg0\\uc1\\deff0\\deflang0\\deflangfe0{\\fonttbl{\\f0\\fnil Arial;}}{\\colortbl;}{\\stylesheet{\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0 Paragraph Style;}{\\*\\cs1\\f0\\fs24 Font Style;}}\\pard\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0\\itap0 \\plain \\cs1\\f0\\fs24 Bonjour\\par}"

def document = new DefaultStyledDocument()
def editorKit = new RTFEditorKit()
editorKit.read(new StringReader(texteRTF), document, 0)

def contenu = document.getText(0, document.getLength()-1)


println "'$contenu'"