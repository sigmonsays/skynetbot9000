package com.johnwyles.skynetbot9000.commands;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnwyles.skynetbot9000.commands.quote.StockTicker;

public class Links extends Command {
    private static final Logger _log = LoggerFactory.getLogger(Quote.class);
    
    private static final Integer _MAX_CACHE_COUNT = 100;
    private static final Integer _DEFAULT_OUTPUT_COUNT = 10;
    
    private static Map<String, HashMap<String, String>> _authorLinks = new HashMap<String, HashMap<String, String>>();
    private static Map<String, HashMap<String, String>> _linksAuthor = new HashMap<String, HashMap<String, String>>();

    public String execute() {
	String linksString = "";
	HashMap<String, String> currentChat = _linksAuthor.get(this._chatName);
	if (currentChat != null) {
	    for (Map.Entry<String, String> entry : currentChat.entrySet()) {
		linksString += entry.getKey() + " [" + entry.getValue() + "]\n";
	    }
	}

	return linksString;
    }

    public String execute(String[] arguments) {
	return null;
    }

    public static void addLink(String chatName, String author, String link) {
	HashMap<String, String> authorLink = new HashMap<String, String>();
	authorLink.put(author, link);
	HashMap<String, String> linkAuthor = new HashMap<String, String>();
	linkAuthor.put(link, author);

	HashMap<String, String> chatMap = _authorLinks.get(chatName);
	if (chatMap != null) {
	    String testAuthorLink = (String) chatMap.get(author);
	    if (testAuthorLink != null && !testAuthorLink.equals(link)) {
		_authorLinks.put(chatName, authorLink);
	    } else if (testAuthorLink == null) {
		_authorLinks.put(chatName, authorLink);
	    }
	} else {
	    _authorLinks.put(chatName, authorLink);
	}
	
	HashMap<String, String> linkMap = _linksAuthor.get(chatName);
	if (linkMap != null) {
	    String testLinkAuthor = (String) linkMap.get(link);
	    if (testLinkAuthor != null && !testLinkAuthor.equals(author)) {
		_linksAuthor.put(chatName, linkAuthor);
	    } else if (testLinkAuthor == null) {
		_linksAuthor.put(chatName, linkAuthor);
	    }
	} else {
	    _linksAuthor.put(chatName, linkAuthor);
	}
    }
}
