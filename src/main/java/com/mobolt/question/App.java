package com.mobolt.question;


public class App
{

  public static void main(String... anArgs) throws Exception
  {
    new App().start();
  }

  private final WebServer server;

  public App()
  {
    server = new WebServer(8020);
  }

  public void start() throws Exception
  {
    server.start();
    server.join();
  }
}
