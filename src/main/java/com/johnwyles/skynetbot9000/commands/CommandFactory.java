package com.johnwyles.skynetbot9000.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
	private static Map<String, Command> cmds = new HashMap<String, Command>();

	static {
		cmds.put("trivia", new Trivia());
		cmds.put("quote", new Quote());
		cmds.put("time", new Time());
	}

	public static Command getCommand(String command) {
		command = command.toLowerCase();
		if (respondsTo(command)) {
			return cmds.get(command);
		}

		return null;
	}

	public static boolean respondsTo(String command) {
		if (cmds.containsKey(command)) {
			return true;
		}

		return false;
	}
}
