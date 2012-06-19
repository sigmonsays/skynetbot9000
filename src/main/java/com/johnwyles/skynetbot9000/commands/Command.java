package com.johnwyles.skynetbot9000.commands;

abstract public class Command {
    protected String _chatName;
    protected String _author;

    public void initialize(String chatName, String author) {
	_chatName = chatName;
	_author = author;
    }
    public String execute() {
	return null;
    }

    public String execute(String[] arguments) {
	return null;
    }
}
