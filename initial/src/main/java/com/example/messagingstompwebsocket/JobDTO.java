package com.example.messagingstompwebsocket;

/**
 * A Job to send to the client.
 * 
 * name - name of the js file to load.
 * args - any arguments for the job execution
 * res - the url to post the result to
 *
 */
public class JobDTO {

	private String name, args, res;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}
	
	
	
}
