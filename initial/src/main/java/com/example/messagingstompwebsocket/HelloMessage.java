package com.example.messagingstompwebsocket;

/**
 * An initial message DTO for WS connection verification.
 *
 */
public class HelloMessage {

	  private String name;

	  public HelloMessage() {
	  }

	  public HelloMessage(String name) {
	    this.name = name;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }
	}