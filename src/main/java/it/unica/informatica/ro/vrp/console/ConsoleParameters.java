package it.unica.informatica.ro.vrp.console;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters
public class ConsoleParameters {

	@Parameter(description = "[FILE_1 FILE_2 ...]", required = false)
	private List<String> files = new ArrayList<String>();
	
	@Parameter(names = {"-h", "--help"}, description = "Display this usage", help = true)
	private boolean help;
	
	@Parameter(names = {"-l", "--log-level"}, validateWith = LogLevelValidator.class, description = "Select the log level (INFO or DEBUG)")
	private String logLevel = "INFO";

	public List<String> getFiles() {
		return files;
	}
	
	public void setFiles(List<String> files) {
		this.files = files;
	}
	
	public boolean isHelp() {
		return help;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}

	public String getLogLevel() {
		return logLevel;
	}
	
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
}
