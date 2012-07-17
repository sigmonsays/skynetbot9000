package com.johnwyles.skynetbot9000.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class Listener {
    private static final Logger _log = LoggerFactory.getLogger(Listener.class);

    public void onMessage(String chatName, String author, String message) {
	_log.debug("Listener [" + this.getClass().getName() + "] is responding to the message: [" + chatName + ":" + author + "]: " + message);
    }
}
