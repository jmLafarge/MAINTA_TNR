import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.text.SimpleTemplateEngine



class TO {
	
	TestObject to
	String xpath =''
	
	
	TO(String ID, tag, theXpath = ''){
	
		this.to = new TestObject(ID)
		
		this.to.setSelectorMethod(SelectorMethod.XPATH)
		
		if (theXpath =='') {
			// it's a standard xpath
			this.xpath  = "//" + tag + "[@id='" + ID + "']"
		}else if (theXpath.substring(0,1) == '/') {
			// it's a xpath
			this.xpath  = theXpath
		}else {
			// it's another ID
			this.xpath  = "//" + tag + "[@id='" + theXpath + "']"
		}
		this.to.setSelectorValue(SelectorMethod.XPATH, this.xpath )
		
		
	}	
	
	
	public eval(Map binding){
		// dynamic xpath
		def engine = new SimpleTemplateEngine()
		String newXpath = engine.createTemplate(this.xpath).make(binding).toString()
		this.to.setSelectorValue(SelectorMethod.XPATH, newXpath)
		
	}

}




class JDD{
	
	Map maprop=[:];
	
	JDD(){

	this.test()
	}
	
	
	private test(){
		
		maprop['ID1'] = new TO('ID1','',"//div[@id=/table/tbody//td[3][text()=" + '${ID_CODGES}')
		maprop['ID2'] = new TO('ID2','input','AUTRE_ID')
		maprop['ID3'] = new TO('ID3','input')
		
	}
	
	def getProperty(String property){
		return maprop[property];
	}
	
	
	/*
	void setProperty(String property, Object newValue){
		maprop[property] = newValue;
	}
	*/

	
}


JDD myJDD = new my.JDD()

println myJDD.ID1.to.getObjectId()
println myJDD.ID2.to.getObjectId()

println myJDD.ID1.to.getSelectorCollection()[SelectorMethod.XPATH]
println myJDD.ID2.to.getSelectorCollection()[SelectorMethod.XPATH]

println myJDD.ID1.eval(['ID_CODGES':'JML001'])

println myJDD.ID1.to.getSelectorCollection()[SelectorMethod.XPATH]
println myJDD.ID2.to.getSelectorCollection()[SelectorMethod.XPATH]

println myJDD.ID1.eval(['ID_CODGES':'XXXXXX'])


println myJDD.ID1.to.getSelectorCollection()[SelectorMethod.XPATH]
println myJDD.ID2.to.getSelectorCollection()[SelectorMethod.XPATH]

println myJDD.ID3.to.getSelectorCollection()[SelectorMethod.XPATH]



