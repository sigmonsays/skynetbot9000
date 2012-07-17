package com.johnwyles.skynetbot9000.listeners;

import java.util.ArrayList;

import com.skype.api.Conversation;

public class ListenerFactory {
    private static ArrayList<Listener> _availableListeners = new ArrayList<Listener>();

    static {
	_availableListeners.add(new LinksListener());
    }

    public static ArrayList<Listener> getListeners() {
	return _availableListeners;
    }

    public static void onMessage(String chatName, String author, String message) {
	for( Listener listener : _availableListeners ) {
	    listener.onMessage(chatName, author, message);
	}
    }
}
