package com.johnwyles.skynetbot9000.commands;

import java.util.ArrayList;
import java.util.LinksIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnwyles.skynetbot9000.listeners.LinksListener;

public class LinksCommand extends Command {
    private static final Logger _log = LoggerFactory.getLogger(LinksCommand.class);
    private static final Integer _defaultLinkCount = 20;

    public String execute() {
	String linksString = "";
        Integer linkCount = 0;
	ArrayList<String> links = LinksListener.getLinks(this._chatName);

	if (links != null) {
            ListIterator linksIterator = links.listIterator(links.size());
	    while(linksIterator.hasPrevious() && linkCount < _defaultLinkCount) {
		linksString += linksIterator.previous() + "\n";
                linkCount++;
	    }
	}

	return linksString;
    }

    public String execute(String[] arguments) {
	return null;
    }
}
