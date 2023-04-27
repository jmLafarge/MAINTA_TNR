import com.kms.katalon.core.configuration.RunConfiguration

println RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf("\\")+1)
