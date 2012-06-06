package org.zeroturnaround.web;

import org.eclipse.jetty.server.Server;
import org.zeroturnaround.Configuration;

public class WebServer {
  private static Server server;

  public static void startWebServer() {
    server = new Server(Configuration.portNumber);
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
