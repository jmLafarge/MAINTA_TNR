package my

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testdata.ExcelData


public class JDD {

	// on peut ajouter un ctrl du JDD




	def ArrayList getIndexOf_CasDeTest(ExcelData data, String casDeTest){

		List listIdx = []
		for (int index : (0..data.getRowNumbers() - 1)) {

			if (data.internallyGetValue("CAS_DE_TEST", index) == casDeTest) {
				listIdx.add(index)
			}
		}
		return listIdx
	}

	def getval(ExcelData data, int idx) {

		Map JD = [:]
		for (int i : (0..data.getColumnNumbers()-1)) {
			JD[data.columnNames[i]] = data.internallyGetValue(i, idx)
		}
		return JD
	}



	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	def getValuesOf(String JDDpath, JDDname, casDeTest){

		ExcelData data = findTestData(JDDpath+JDDname)

		ArrayList listIdx = getIndexOf_CasDeTest(data, JDDname + '.' + casDeTest)

		Map listJDD = [:]

		int num = 1

		listIdx.each {

			listJDD.put(num,getval(data,it))
			num ++
		}

		return listJDD

	}

}
