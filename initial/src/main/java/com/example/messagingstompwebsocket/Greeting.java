package com.example.messagingstompwebsocket;

/**
 * 
 * A greeting message, to verify WS connection.
 *
 */
public class Greeting {

	  private String content;

	  public Greeting() {
	  }

	  public Greeting(String content) {
	    this.content = content;
	  }

	  public String getContent() {
	    return content;
	  }

	}