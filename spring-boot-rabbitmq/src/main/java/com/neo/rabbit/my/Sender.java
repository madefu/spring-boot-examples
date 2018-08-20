package com.neo.rabbit.my;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.model.User;


@Component
public class Sender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@SuppressWarnings("static-access")
	public void send() {
		while(true) {
			String context = "hi, fanout msg "+ new User().getDateStr();
			System.out.println("Sender : " + context);
			this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
			try {
				Thread.currentThread().sleep(1000*3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}