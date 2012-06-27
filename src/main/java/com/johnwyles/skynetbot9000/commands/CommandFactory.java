package com.johnwyles.skynetbot9000.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> _availableCommands = new HashMap<String, Command>();

    static {
	_availableCommands.put("quote", new QuoteCommand());
	_availableCommands.put("reddit", new RedditCommand());
	_availableCommands.put("links", new LinksCommand());
    }

    public static Command getCommand(String chatName, String author, String command) {
	command = command.toLowerCase();
	if (respondsTo(command)) {
	    Command specificCommand = _availableCommands.get(command);
	    specificCommand.initialize(chatName, author);
	    return specificCommand;
	}

	return null;
    }

    public static boolean respondsTo(String command) {
	if (_availableCommands.containsKey(command)) {
	    return true;
	}

	return false;
    }
}
