package com.johnwyles.skynetbot9000.commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnwyles.skynetbot9000.listeners.LinksListener;

public class LinksCommand extends Command {
    private static final Logger _log = LoggerFactory.getLogger(LinksCommand.class);

    public String execute() {
	String linksString = "";
	ArrayList<String> links = LinksListener.getLinks(this._chatName);
	if (links != null) {
	    Iterator<String> linksIterator = links.iterator();
	    while(linksIterator.hasNext()) {
		linksString += linksIterator.next() + "\n";
	    }
	}

	return linksString;
    }

    public String execute(String[] arguments) {
	return null;
    }
}
