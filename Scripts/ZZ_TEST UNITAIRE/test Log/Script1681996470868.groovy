import my.Log



Log.addTrace('une trace au debut')

Log.addTraceBEGIN('JML.run()')

	Log.addTraceBEGIN('XLS.run()')
	
		Log.addTraceBEGIN('XLS.open()')

			Log.addTrace('une trce dans XLS.open()')
					Log.addTraceBEGIN('XLS.Wtie()')
			
						Log.addTrace('une trce dans XLS.Wtie()')
								Log.addTraceBEGIN('SQL.insert()')
									Log.addTrace('une trce dans SQL.insert()')
							
									Log.addTraceEND('SQL.insert()')
					
					Log.addTraceEND('XLS.Wtie()')
		
		Log.addTraceEND('XLS.open()')
	
	Log.addTrace('une trce dans XLS.run()')
	
	Log.addTraceEND('XLS.run()')

	Log.addTrace('une trace dans JML.run()')

	Log.addTraceBEGIN('JML1.run()')
		Log.addTrace('une trace dans JML1.run()')
		Log.addTrace('une autre trace dans JML1.run()')
	Log.addTraceEND('JML1.run()')
	
	Log.addTrace('une 2e trace dans JML.run()')
	Log.addTrace('une 2e autre trace dans JML.run()')
	
	Log.addTraceBEGIN('JML1.run()')
	
		Log.addTraceBEGIN('JML2.run()')
			Log.addTrace('une trce dans JML2.run()')
			
			Log.addTraceBEGIN('SQL.run()')
				Log.addTrace('une trce dans SQL.run()')
			
			Log.addTraceEND('SQL.run()')
			
			Log.addTrace('toujours une trce dans JML2.run()')
			
			Log.addTraceBEGIN('JML3a.run()')
				Log.addTrace('une trce dans JML3a.run()')
			
			Log.addTraceEND('JML3a.run()')
			
			Log.addTrace('une trce dans JML2.run()')
		Log.addTraceEND('JML2.run()')
		
	Log.addTraceEND('JML1.run()')
	
	Log.addTrace('une traceavant la finJML.run()')

Log.addTraceEND('JML.run()')


Log.addTrace('une trace a al afin')





