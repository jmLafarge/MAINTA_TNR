import com.kms.katalon.core.configuration.RunConfiguration

import groovy.text.SimpleTemplateEngine

println RunConfiguration.getExecutionSourceName()

def engine = new SimpleTemplateEngine()

def template = "//div[@id='v-dbtdhtmlxHAB']/table/tbody//td[3][text()='" + '${ID_CODHAB}' + "']//preceding-sibling::td//span"

def binding = [ "ID_CODHAB": "TOTO" ]
def result = engine.createTemplate(template).make(binding).toString()

assert result=="//div[@id='v-dbtdhtmlxHAB']/table/tbody//td[3][text()='TOTO']//preceding-sibling::td//span"

println template
println result

