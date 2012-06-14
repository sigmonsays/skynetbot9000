package com.johnwyles.skynetbot9000;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnwyles.skynetbot9000.web.WebServer;

public class SkypeChatBot {
	private static String _defaultPortNumber = "2500";
	private static String _defaultPostUrl = "/";
	private static String _propertiesFile = "skynetbot9000.properties";

	private static final Logger _log = LoggerFactory
			.getLogger(SkypeChatBot.class);

	public static void main(String[] args) throws Exception {
		_initConfiguration();
		WebServer.startWebServer();
		SkypeEngine skypeBot = new SkypeEngine();
		skypeBot.start();
	}

	public static void stop() {
		WebServer.stopWebServer();
	}

	private static void _initConfiguration() {
		Properties properties = new Properties();

		File propertiesFile = new File(_propertiesFile);
		try {
			properties.load(new FileReader(propertiesFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Unable to read properties file "
					+ propertiesFile.getAbsolutePath(), e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read properties file "
					+ propertiesFile.getAbsolutePath(), e);
		}

		Configuration.skypeUsername = properties.getProperty("username", null);
		Configuration.skypePassword = properties.getProperty("password", null);
		Configuration.pemFile = properties.getProperty("pemFile", null);
		Configuration.portNumber = properties.getProperty("portNumber", _defaultPortNumber);
		Configuration.postUrl = properties.getProperty("postUrl", _defaultPostUrl);

		if (Configuration.pemFile == null
				|| Configuration.skypePassword == null
				|| Configuration.skypeUsername == null) {
			String errorMessage = "Unable to find username, password or pemfile from a project.properties or personal.properties file. Exiting";
			_log.error(errorMessage);
			System.err.println(errorMessage);
			System.exit(1);
		}
	}
}
