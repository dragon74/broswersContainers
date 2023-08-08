package com.example.messagingstompwebsocket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller to demonstrated sending jobs to a browser via a web socket and
 * storing results.
 * 
 * @author Dan Erez
 */
@RequestMapping("/jobs")
@RestController
public class JobController {

	@Autowired
	private SimpMessagingTemplate webSocket;
	// Saving some results, as we handle it all locally
	private String automationResult, classifyRes;
	// a queue to hold numbers for the parallel task
	private Queue<Long> queue;
	// sum of the numbers to compare with the jobs' calculation
	private long sum;
	// Result of ml model training
	private String trainRes;

	// ************* Running a classification job ***********************/
	@PostMapping("/classify")
	public void classify(@RequestBody String img) throws Exception {
		System.out.println("Job Submitted!");
		classifyRes = "";
		JobDTO job = new JobDTO();
		job.setName("classify");
		job.setArgs(img);
		job.setRes("./jobs/classifyRes");
		webSocket.convertAndSend("/topic/job", job);
	}

	@PostMapping("/classifyRes")
	public synchronized void classifyRes(@RequestBody String res) throws Exception {
		System.out.println("Got a response in classifyRes " + res);
		classifyRes = res;
	}

	@GetMapping("/getClassifyRes")
	public synchronized String getClassifyRes() throws Exception {
		return classifyRes;
	}

	// ************* Running a train job ***********************/
	@PostMapping("/train")
	public void train(@RequestBody String num) throws Exception {
		System.out.println("Job Submitted!");
		classifyRes = "";
		JobDTO job = new JobDTO();
		job.setName("train");
		job.setArgs(num);
		job.setRes("./jobs/trainRes");
		webSocket.convertAndSend("/topic/job", job);
	}

	@PostMapping("/trainRes")
	public synchronized void trainRes(@RequestBody String res) throws Exception {
		System.out.println("Got a response in trainRes " + res);
		trainRes = res;
	}

	@GetMapping("/getTrainRes")
	public synchronized String getTrainRes() throws Exception {
		return trainRes;
	}

	// ************* Running a job in Parallel ***********************/
	@PostMapping("/parallel")
	public void parallel() throws Exception {
		System.out.println("Job Submitted!");
		queue = new ConcurrentLinkedQueue<Long>();
		sum = 0;
		for (int i = 0; i < 100; i++) {
			long num = Double.valueOf(Math.random() * 100).longValue();
			sum += num;
			queue.offer(num);
		}
		System.out.println("Sum to reach is " + sum);
		JobDTO job = new JobDTO();
		job.setName("para3");
		job.setRes("./jobs/paraRes");
		webSocket.convertAndSend("/topic/job", job);
	}

	@GetMapping("/readnumber")
	public synchronized TwoNumbers readNumber() throws Exception {
		if (queue.size() < 2) {
			// We are done with the numbers summation
			return null;
		}
		TwoNumbers tn = new TwoNumbers();
		tn.setN1(queue.poll());
		tn.setN2(queue.poll());
		return tn;
	}

	@PostMapping("/paraRes")
	public synchronized void parares(@RequestBody Long num) throws Exception {
		// result of some browser calculation
		queue.offer(num);
	}

	@GetMapping("/getsum")
	public synchronized long getsum() throws Exception {
		// return the calculates sum
		return queue.peek();
	}

	// *****************************************************************/
	// ************* Running an pdf creation job ***********************/
	@PostMapping("/invoice")
	public void invoice(@RequestBody Integer sum) throws Exception {
		System.out.println("Invoice Job Submitted!");
		JobDTO job = new JobDTO();
		job.setName("invoice");
		job.setArgs(sum.toString());
		job.setRes("./jobs/invoiceRes");
		webSocket.convertAndSend("/topic/job", job);
	}

	// *****************************************************************/
	// ************* Running an automation job ***********************/
	// ************* DO NOT USE !!!!!!!!!!!!!! ***********************/
	@PostMapping("/automate")
	public void automate(@RequestBody String url) throws Exception {
		System.out.println("Automation Job Submitted!");
		automationResult = "";
		JobDTO job = new JobDTO();
		job.setName("automateSite");
		job.setArgs(url);
		job.setRes("./jobs/automateRes");
		webSocket.convertAndSend("/topic/job", job);
	}

	@PostMapping("/automateRes")
	public synchronized void automateRes(@RequestBody String res) throws Exception {
		System.out.println("Got a response in automateRes " + res);
		automationResult = res;
	}

	@GetMapping("/getAutomateRes")
	public synchronized String getAutomateRes() throws Exception {
		return automationResult;
	}
}
