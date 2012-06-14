package com.johnwyles.skynetbot9000;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnwyles.skynetbot9000.commands.Command;
import com.johnwyles.skynetbot9000.commands.CommandFactory;
import com.skype.api.Contact;
import com.skype.api.ContactGroup;
import com.skype.api.Conversation;
import com.skype.api.Conversation.ConversationListener;
import com.skype.api.Conversation.LIST_TYPE;
import com.skype.api.Message;
import com.skype.api.Message.MessageListener;
import com.skype.api.Message.PROPERTY;
import com.skype.api.Skype.PROXYTYPE;
import com.skype.api.Skype.SkypeListener;
import com.skype.api.SkypeObject;
import com.skype.ipc.RootObject.ErrorListener;

public class SkypeGlobalListener implements MessageListener, SkypeListener, ConversationListener, ErrorListener {
	public static final String BOT_MSG_PREFIX = "!";
	private static final Logger _log = LoggerFactory
			.getLogger(SkypeGlobalListener.class);
	private static Map<String, Conversation> _conversations = new HashMap<String, Conversation>();

	@Override
	public void OnPropertyChange(SkypeObject skypeObject, PROPERTY property, Object value) {
		String propertyString = skypeObject.toString() + "." + property.toString() + " = ";
		if (value != null) {
			propertyString += value.toString();
		} else {
			propertyString += "null";
		}

		_log.debug("OnPropertyChange: " + propertyString);
	}

	@Override
	public void OnNewCustomContactGroup(ContactGroup group) {
	}

	@Override
	public void OnContactOnlineAppearance(Contact contact) {
	}

	@Override
	public void OnContactGoneOffline(Contact contact) {
	}

	@Override
	public void OnConversationListChange(Conversation conversation,
			LIST_TYPE type, boolean added) {
	}

	@Override
	public void OnMessage(Message message, boolean changesInboxTimestamp, Message supersedesHistoryMessage, Conversation conversation) {
		String author = message.GetStrProperty(Message.PROPERTY.author);
		_log.debug("Incoming message from " + author);

		int type = message.GetIntProperty(Message.PROPERTY.type);
		_log.debug("SKYPE.OnMessage." + author + " Message::TYPE = "
				+ Message.TYPE.get(type));
		_log.debug("CHAT." + author + ";oid=" + conversation.getOid() + ":"
				+ message.GetStrProperty(Message.PROPERTY.body_xml));

		String conversationStr = conversation
				.GetStrProperty(Conversation.PROPERTY.displayname);
		_conversations.put(conversationStr, conversation);
		String identity = conversation
				.GetStrProperty(Conversation.PROPERTY.identity);
		_log.info("convo display name = " + conversationStr);
		_log.info("convo identity = " + identity);

		String messageString = message.GetStrProperty(Message.PROPERTY.body_xml);

		// ignore the processing of our own messages to avoid
		// indefinite loop
		if (Configuration.skypeUsername.equals(author)) {
			_log.debug("Ignoring command because it is from " + Configuration.skypeUsername);
			return;
		}

		if (messageString.startsWith(BOT_MSG_PREFIX)) {
			String[] commandString = messageString.split("\\s+");
			String command = commandString[0].replaceFirst(Pattern.quote(BOT_MSG_PREFIX), "");
			String[] arguments = Arrays.copyOfRange(commandString, 1, commandString.length);

			String result = _processCommand(command, arguments);
			if (result != null) {
				conversation.PostText(result, false);
			}
		}
	}

	private String _processCommand(String command, String[] arguments) {
		if (!CommandFactory.respondsTo(command)) {
			_log.info("Got invalid command: " + command);
			return "Invalid command received: '" + command + "' is not a recognized command.";
		}

		Command commandInstance = CommandFactory.getCommand(command);
		if (commandInstance != null) {
			if (arguments.length > 0) {
				return commandInstance.execute(arguments);
			} else {
				return commandInstance.execute();
			}
		} else {
			_log.error("Unable to get an actual command for the command string " + command);
		}

		return null;
	}

	@Override
	public void OnAvailableVideoDeviceListChange() {
	}

	@Override
	public void OnAvailableDeviceListChange() {
	}

	@Override
	public void OnNrgLevelsChange() {
	}

	@Override
	public void OnProxyAuthFailure(PROXYTYPE type) {
	}

	@Override
	public void OnPropertyChange(SkypeObject skypeObject,
			com.skype.api.Conversation.PROPERTY property, Object value) {
		_log.debug("onPropertyCHange listener called - doing nothing");
	}

	@Override
	public void OnParticipantListChange(SkypeObject skypeObject) {
	}

	@Override
	public void OnMessage(SkypeObject skypeObject, Message message) {
		_log.debug("OnMessage" + skypeObject + message);
	}

	@Override
	public void OnSpawnConference(SkypeObject skypeObject, Conversation spawned) {
	}

	@Override
	public void OnSkypeKitFatalError() {
		_log.debug("OnSkypeKitFatalError");
	}

	@Override
	public void OnSkypeKitConnectionClosed() {
		_log.debug("OnSkypeKitConnectionClosed");
	}

	public static void postToChat(String chatName, String message) {
		_log.debug("PostToChat=" + chatName);
		Conversation convo = _conversations.get(chatName);
		if (convo != null) {
			_log.debug("Found the conversation, posting");
			convo.PostText(message, false);
		}
	}
}
