package my

/*
 * use my.Log in the code without import --> to be sure that this Log is used
 * 
 * To log message in files
 * 
 */
class Log {

	private static Map status = [WARNING:0,FAIL:0,PASS:0,ERROR:0]


	private static File file = this.createFile()

	private static int debugLevel = 0

	private static String tab = ''

	private static String TCName = ''

	private static Date start



	/*
	 * 
	 */
	private static File createFile(){

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		File file =new File(my.PropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + '-log.txt')

		this.debugLevel = my.PropertiesReader.getMyProperty('LOG_DEBUGLEVEL').toInteger()

		return file
	}


	/*
	 * 
	 */
	private static add (String typ, String msg) {

		String h = new Date().format("yyyy-MM-dd HH:mm:ss.SSS")
		this.file.append("[$h][$typ]:" + this.tab +"$msg\n")

		println "[my Log][$typ]:" + this.tab +"$msg"
	}


	/*
	 * 
	 */
	static public addINFO (String msg) {

		this.add('-------',msg )
	}


	/*
	 *
	 */
	static public addWARNING (String msg) {

		this.add('WARNING',msg )
		this.status.WARNING++
	}

	/*
	 *
	 */
	static public addFAIL (String msg) {

		this.add(' FAIL  ',msg )
		this.status.FAIL++
	}

	/*
	 *
	 */
	static public addPASS (String msg) {

		this.add(' PASS  ',msg )
		this.status.PASS++
	}


	/*
	 *
	 */
	static public addDEBUG (String msg, int level=1) {

		if (level <= this.debugLevel) {
			this.add('DEBUG ' + level,'\t'+msg )
		}else if (level<3){
			println "[my Log][ DEBUG ]:" + this.tab +"$msg"
		}
	}


	/*
	 * 
	 */
	static public addERROR (String msg) {

		this.add(' ERROR ',msg )
		this.status.ERROR++
	}



	static public addSTEPGRP (String msg) {

		this.addINFO('      '+ msg.padRight(60, '_'))
		//this.addINFO('            STEP : '+ msg)
	}



	static public addDETAIL (String msg) {

		this.addINFO('                   - '+ msg)
	}

	static public addDETAILPASS (String msg) {

		this.addPASS('                   - '+ msg)
	}


	static public addDETAILFAIL (String msg) {

		this.addFAIL('                   - '+ msg)
	}

	/*
	 static public addDETAILWARNING (String msg) {
	 this.addWARNING('                   - '+ msg)
	 }
	 */
	static public addSTEP (String msg) {

		this.addINFO('            STEP : '+ msg)
	}

	static public addSUBSTEP (String msg) {
		
		this.addINFO('                   '+ msg)
	}
		

	static public addSTEPPASS (String msg) {

		this.addPASS('            STEP : '+ msg)
	}


	static public addSTEPFAIL (String msg) {

		this.addFAIL('            STEP : '+ msg)
	}

	/*
	 static public addSTEPWARNING (String msg) {
	 this.addWARNING('            STEP : '+ msg)
	 }
	 */



	/*
	 *
	 */
	static public addStartTestCase (String testCaseName) {

		this.start = new Date()
		this.TCName = testCaseName
		this.status.WARNING = 0
		this.status.FAIL = 0
		this.status.PASS = 0
		this.status.ERROR = 0
		this.addINFO('')
		this.addINFO('START  TEST CASE : '+ testCaseName )
	}


	static public addEndTestCase () {

		Date stop = new Date()
		my.Result.addCasDeTest(this.TCName, this.status, this.start , stop)

		if (this.status.ERROR ==0 && this.status.FAIL == 0 ) {
			this.addPASS('END    TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		}else if (this.status.ERROR ==0 && this.status.FAIL != 0 ) {
			this.addFAIL('END    TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		}else if (this.status.ERROR !=0 ) {
			this.addERROR('END    TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		}
		this.TCName = ''
	}





	static public addTITLE(String title, String car ='*',int nbcar = 100) {
		if (title.length()+4 >= nbcar) nbcar = title.length()+4
		int ecart = (nbcar-2-title.length()).intdiv(2)
		this.addINFO('')
		this.addINFO(car*nbcar)
		this.addINFO(car + ' ' * (nbcar-2) + car)
		this.addINFO(car + ' '*ecart + title + ' '*(nbcar-2-title.length()-ecart) + car)
		this.addINFO(car + ' ' * (nbcar-2) + car)
		this.addINFO(car*nbcar)
		this.addINFO('')
	}



	static public addSubTITLE(String subtitle, String car ='-', int nbcar = 100 ) {
		this.addINFO('')
		this.addINFO(car*nbcar)
		this.addINFO(subtitle)
		this.addINFO(car*nbcar)
		this.addINFO('')
	}
}// end of class