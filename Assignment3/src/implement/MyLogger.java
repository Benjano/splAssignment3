package implement;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static private FileHandler fileHTML;
	static private Formatter formatterHTML;

	static public void setup() throws IOException {

		// get the global logger to configure it
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		// Logger.getL

		logger.setLevel(Level.ALL);
		fileTxt = new FileHandler("Logging.txt");
		fileHTML = new FileHandler("Logging.html");

		// create a TXT formatter
		logger.addHandler(fileTxt);
		logger.addHandler(fileHTML);
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);

		// create an HTML formatter
		formatterHTML = new MyHtmlFormatter();
		fileHTML.setFormatter(formatterHTML);
	}
}