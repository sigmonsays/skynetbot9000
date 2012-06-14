package com.johnwyles.skynetbot9000.commands;

import java.text.DateFormat;
import java.util.Date;

public class Time extends Command {
	@Override
	public String execute() {
		DateFormat format = DateFormat.getDateTimeInstance();
		return "Current time: " + format.format(new Date());
	}
}
