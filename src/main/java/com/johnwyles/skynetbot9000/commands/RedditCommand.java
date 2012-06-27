package com.johnwyles.skynetbot9000.commands;

import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RedditCommand extends Command {
    private static final String _REDDIT_ALL_API_URL = "http://www.reddit.com/r/all.xml";
    private static final Logger _log = LoggerFactory.getLogger(QuoteCommand.class);
    private static final SAXParserFactory _saxParserFactory = SAXParserFactory
	    .newInstance();

    @Override
    public String execute() {
	return null;
    }

    @Override
    public String execute(String[] arguments) {
	return null;
    }
}

class Data {
    private String title;
    private Long id;
    private Boolean children;
    private List<Data> groups;

    public String getTitle() { return title; }
    public Long getId() { return id; }
    public Boolean getChildren() { return children; }
    public List<Data> getGroups() { return groups; }

    public void setTitle(String title) { this.title = title; }
    public void setId(Long id) { this.id = id; }
    public void setChildren(Boolean children) { this.children = children; }
    public void setGroups(List<Data> groups) { this.groups = groups; }

    public String toString() {
        return String.format("title:%s,id:%d,children:%s,groups:%s", title, id, children, groups);
    }
}
