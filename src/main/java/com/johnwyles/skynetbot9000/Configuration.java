package com.johnwyles.skynetbot9000;

public class Configuration {
    private static String _skypeUsername;
    private static String _skypePassword;
    private static String _skypePemFile;
    private static String _webPortNumber;
    private static String _webPostUrl;
    private static String _botCommandPrefix;

    public static String getSkypeUsername() {
	return _skypeUsername;
    }

    public static void setSkypeUsername(String skypeUsername) {
	_skypeUsername = skypeUsername;
    }

    public static String getSkypePassword() {
	return _skypePassword;
    }

    public static void setSkypePassword(String skypePassword) {
	_skypePassword = skypePassword;
    }

    public static String getSkypePemFile() {
	return _skypePemFile;
    }

    public static void setSkypePemFile(String skypePemFile) {
	_skypePemFile = skypePemFile;
    }

    public static String getWebPortNumber() {
	return _webPortNumber;
    }

    public static void setWebPortNumber(String webPortNumber) {
	_webPortNumber = webPortNumber;
    }

    public static String getWebPostUrl() {
	return _webPostUrl;
    }

    public static void setWebPostUrl(String webPostUrl) {
	_webPostUrl = webPostUrl;
    }

    public static String getBotCommandPrefix() {
	return _botCommandPrefix;
    }

    public static void setBotCommandPrefix(String botCommandPrefix) {
	_botCommandPrefix = botCommandPrefix;
    }
}
