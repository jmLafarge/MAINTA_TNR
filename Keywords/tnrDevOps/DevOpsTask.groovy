package tnrDevOps

import groovy.transform.CompileStatic

/**
 *
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

@CompileStatic
public class DevOpsTask {
	
	private static final String CLASS_NAME = 'DevOpsTask'
	
	
	
	
	
	static void createTask(Map fields) {
		
		DevOpsClient.createTask(fields)
	}




	static void updateTask(int taskId, Map fields) {

		DevOpsClient.updateTask(taskId, fields)
	}

	
	
}
