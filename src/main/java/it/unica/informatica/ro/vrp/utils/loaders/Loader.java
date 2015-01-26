package it.unica.informatica.ro.vrp.utils.loaders;

import it.unica.informatica.ro.vrp.problem.Problem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Loader{

	protected static Logger log;
	
	protected Loader() {
		log = LoggerFactory.getLogger(this.getClass());
	}
	
	public Problem load(String inputFile) throws IOException {
		log.debug("Loading from file path '{}'",inputFile);
		return this.load(new File(inputFile));
	}
	
	public Problem load(File inputFile) throws IOException {
		log.debug("Loading from File '{}'",inputFile);
		
		@SuppressWarnings("resource")
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(inputFile);
			return this.load(fis);
		}
		finally {
			IOUtils.closeQuietly(fis);
		}
	}
	
	public Problem load(InputStream input) throws IOException {
		return this.load(new InputStreamReader(input));
	}
	
	abstract public Problem load(Reader input) throws IOException;
}
