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

	public static Command getCommand(String command) {
		command = command.toLowerCase();
		if (respondsTo(command)) {
			return availableCommands.get(command);
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
