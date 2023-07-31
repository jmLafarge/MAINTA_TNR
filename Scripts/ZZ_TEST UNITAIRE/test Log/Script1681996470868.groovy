import my.Log


Log.addTrace('une trace au debut')

Log.addTraceBEGIN('CLA','run')

	Log.addTraceBEGIN('UneCla','uneFonction',[:])
	
		Log.addTraceBEGIN('UneCla','uneAutre')
		
			Log.addTraceBEGIN('AutreCla','uneAdd',[:])
			
			
			
			Log.addTraceEND('AutreCla','uneAdd',[:])
		
			Log.addTraceBEGIN('SQL','open',[:])
			
			
			
			Log.addTraceEND('SQL','open',[:])
			
		Log.addTraceEND('UneCla','uneAutre',[:])
	
	
	Log.addTraceEND('UneCla','uneFonction',[:])

	Log.addTraceBEGIN('SQL','open',[:])
	
		String name = 'le nom'
		String file = 'le fichier'
	
		Log.addTraceBEGIN('XLS','open',[name:name,file:file])
		
		Log.addTraceEND('XLS','open',[:])
		
		Log.addTraceBEGIN('XLS','opened',[:])

		Log.addTraceEND('XLS','opened',[:])
	
	Log.addTraceEND('SQL','open',[:])

Log.addTraceEND('CLA','run',[:])


Log.addTrace('une trace a al afin')





