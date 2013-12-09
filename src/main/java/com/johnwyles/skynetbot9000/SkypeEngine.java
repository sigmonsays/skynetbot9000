package com.johnwyles.skynetbot9000;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skype.api.Account;
import com.skype.api.Conversation;
import com.skype.api.Conversation.LIST_TYPE;
import com.skype.api.Message;
import com.skype.api.Skype;
import com.skype.ipc.TCPSocketTransport;
import com.skype.ipc.TLSServerTransport;
import com.skype.ipc.Transport;
import com.skype.util.PemReader;

public class SkypeEngine extends Thread {
    private static final Logger _log = LoggerFactory
	    .getLogger(SkypeEngine.class);
    private static Transport _transport;
    private static Skype _skypeInstance;

    public SkypeEngine() {
	_skypeInstance = new Skype();
    }

    public void run() {
	try {
	    SkypeGlobalListener listener = _connectToSkype();
	    while (_transport.isConnected()) {
		_log.debug("Connected to Skype.");
		Thread.sleep(60000);
	    }
	    _tearDownSkype(listener);
	} catch (Exception e) {
	    _log.debug("Skype thread hickup.", e);
	} finally {
	    SkypeChatBot.stop();
	}
    }

    private void _tearDownSkype(SkypeGlobalListener listener)
	    throws IOException {
	_log.debug("Connection lost; cleaning up...");

	_unRegisterAllListeners(_skypeInstance, listener);

	if (_transport != null) {
	    _transport.disconnect();
	}

	if (_skypeInstance != null) {
	    _skypeInstance.Close();
	}
    }

    private SkypeGlobalListener _connectToSkype() {
	_log.debug("Connecting to Skype...");

	PemReader certAsPem;
	X509Certificate cert;
	PrivateKey privateKey;
	try {
	    _log.debug("Using Skype PEM file "
		    + Configuration.getSkypePemFile());
	    certAsPem = new PemReader(Configuration.getSkypePemFile());
	    cert = certAsPem.getCertificate();
	    privateKey = certAsPem.getKey();
	} catch (FileNotFoundException e1) {
	    throw new RuntimeException(e1);
	} catch (InvalidKeySpecException e) {
	    throw new RuntimeException(e);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	Transport socketTransport = new TCPSocketTransport("127.0.0.1", 8963);
	_transport = new TLSServerTransport(socketTransport, cert, privateKey);
	_log.debug("TLSServerTransport created; calling skype.Init()...");
	SkypeGlobalListener skypeListener = new SkypeGlobalListener();
	_registerAllListeners(_skypeInstance, skypeListener);
	try {
	    _skypeInstance.Init(_transport);
	    _skypeInstance.Start();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	try {
	    if (_transport.isConnected()) {
		String version = _skypeInstance.GetVersionString();
		_log.debug("Skype version is: " + version);

		Account account = _skypeInstance.GetAccount(Configuration
			.getSkypeUsername());
		account.LoginWithPassword(Configuration.getSkypePassword(),
			false, true);
	    }
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	return skypeListener;
    }

    private static void _registerAllListeners(Skype skype,
	    SkypeGlobalListener skypeListener) {
	skype.RegisterListener(Skype.getmoduleid(), skypeListener);
	skype.RegisterListener(Message.moduleID(), skypeListener);
	skype.RegisterListener(Conversation.moduleID(), skypeListener);
	skype.SetErrorListener(skypeListener);
    }

    private static void _unRegisterAllListeners(Skype skype,
	    SkypeGlobalListener skypeListener) {
	skype.UnRegisterListener(Skype.getmoduleid(), skypeListener);
	skype.UnRegisterListener(Message.moduleID(), skypeListener);
	skype.UnRegisterListener(Conversation.moduleID(), skypeListener);
    }

    public static boolean post(String group, String message) {
	Conversation[] conversations = _skypeInstance
		.GetConversationList(LIST_TYPE.ALL_CONVERSATIONS);
	for (int i = 0; i < conversations.length; i++) {
	    String groupName = conversations[i]
		    .GetStrProperty(Conversation.PROPERTY.displayname);
	    if (groupName.contains(groupName)) {
		conversations[i].PostText(message, false);
		return true;
	    }
	}

	return false;
    }
}
