package it.unica.informatica.ro.vrp.console;

import java.util.regex.Pattern;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;


public class LogLevelValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		
		Pattern pattern = Pattern.compile("INFO|DEBUG", Pattern.CASE_INSENSITIVE);
		
		if(!pattern.matcher(value).matches())
			throw new ParameterException("Parameter " + name + " should be INFO or DEBUG (found " + value +")");
	}

}
