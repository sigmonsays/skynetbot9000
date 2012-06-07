package com.johnwyles.skypebot.web;

import org.eclipse.jetty.server.Server;
import com.johnwyles.skypebot.Configuration;

public class WebServer {
  private static Server server;

  public static void startWebServer() {
    server = new Server(Integer.parseInt(Configuration.portNumber));
    server.setHandler(new HTTPHandler());
    try {
      server.start();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void stopWebServer() {
    try {
      server.stop();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
