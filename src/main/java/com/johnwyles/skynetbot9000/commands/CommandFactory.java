package com.johnwyles.skynetbot9000.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> availableCommands = new HashMap<String, Command>();

    static {
	availableCommands.put("quote", new Quote());
	availableCommands.put("reddit", new Reddit());
	availableCommands.put("links", new Links());
    }

    public static Command getCommand(String chatName, String author, String command) {
	command = command.toLowerCase();
	if (respondsTo(command)) {
	    Command specificCommand = availableCommands.get(command);
	    specificCommand.initialize(chatName, author);
	    return specificCommand;
	}

	return null;
    }

    public static boolean respondsTo(String command) {
	if (availableCommands.containsKey(command)) {
	    return true;
	}

	return false;
    }
}
