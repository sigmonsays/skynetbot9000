package com.johnwyles.skynetbot9000.commands;

import java.util.ArrayList;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnwyles.skynetbot9000.listeners.LinksListener;

public class LinksCommand extends Command {
    private static final Logger _log = LoggerFactory.getLogger(LinksCommand.class);
    public Integer defaultLinkCount = 20;

    public String execute() {
	String linksString = "";
        Integer linkCount = 0;
	ArrayList<String> links = LinksListener.getLinks(this._chatName);

	if (links != null) {
            ListIterator<String> linksIterator = links.listIterator(links.size());
	    while(linksIterator.hasPrevious() && linkCount < defaultLinkCount) {
		linksString += linksIterator.previous() + "\n";
                linkCount++;
	    }
	}

	return linksString;
    }

    public String execute(String[] arguments) {
	Integer linkCount = Integer.parseInt(arguments[0]);
	if (linkCount > LinksListener.MAX_LINK_COUNT) {
	    defaultLinkCount = LinksListener.MAX_LINK_COUNT;
	} else if (linkCount <= 0) {
	    defaultLinkCount = 1;
	} else {
	    defaultLinkCount = linkCount;
	}

	return execute();
    }
}
