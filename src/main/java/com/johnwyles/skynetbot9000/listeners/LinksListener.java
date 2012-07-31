package com.johnwyles.skynetbot9000.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinksListener extends Listener {
    private static final Logger _log = LoggerFactory.getLogger(LinksListener.class);
    private static HashMap<String, ArrayList<String>> _links = new HashMap<String, ArrayList<String>>();
    public static final Integer MAX_LINK_COUNT = 100;

    public void onMessage(String chatName, String author, String message) {
	Pattern urlPattern = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
	Matcher urlMatcher = urlPattern.matcher(message);
	while (urlMatcher.find()) {
	    String urlMatch = urlMatcher.group();
	    _log.debug("Message contains a URL: " + urlMatch);
	    _addLink(chatName, author, urlMatch);
	}
    }

    public static ArrayList<String> getLinks(String chatName) {
	return _links.get(chatName);
    }

    private void _addLink(String chatName, String author, String link) {
	ArrayList<String> authorLinks = _links.get(chatName);
	if (authorLinks != null && authorLinks.size() >= MAX_LINK_COUNT) {
	    authorLinks.remove(MAX_LINK_COUNT - 1);
	    authorLinks.trimToSize();
	} else if (authorLinks == null) {
	    authorLinks = new ArrayList<String>();
	}

	if (!authorLinks.contains(link + " [" + author + "]")) {
	    authorLinks.add(link + " [" + author + "]");
	    _links.put(chatName, authorLinks);
	}	
    }
}
