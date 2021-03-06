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
    private static String _defaultCommandPrefix = "!";
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
	} catch (Exception e) {
	    throw new RuntimeException(
		    "[ERROR] Unable to read properties file "
			    + propertiesFile.getAbsolutePath(), e);
	}

	Configuration.setSkypeUsername(properties.getProperty("skypeUsername",
		null));
	Configuration.setSkypePassword(properties.getProperty("skypePassword",
		null));
	Configuration.setSkypePemFile(properties.getProperty("skypePemFile",
		null));
	Configuration.setWebPortNumber(properties.getProperty("webPortNumber",
		_defaultPortNumber));
	Configuration.setWebPostUrl(properties.getProperty("webPostUrl",
		_defaultPostUrl));
	Configuration.setBotCommandPrefix(properties.getProperty(
		"botCommandPrefix", _defaultCommandPrefix));

	if (Configuration.getSkypePemFile() == null
		|| Configuration.getSkypePassword() == null
		|| Configuration.getSkypeUsername() == null) {
	    String errorMessage = "[ERROR] Unable to find skypeUsername, skypePassword or skypePemfile from the '"
		    + propertiesFile.getAbsolutePath() + "' file.";
	    _log.error(errorMessage);
	    System.err.println(errorMessage);
	    System.exit(1);
	}
    }
}
