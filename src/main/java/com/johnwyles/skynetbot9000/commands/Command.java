package com.johnwyles.skynetbot9000.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class Command {
	public String execute() {
		return null;
	}

	public String execute(String[] arguments) {
		return null;
	}
}
