package com.sustainability.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class BotController {
    @GetMapping("/hello")

    public String sayHello() {
        return "EcoQuestBot is running! Send a message to the bot on Telegram to see it in action.";
    }
    @PostMapping("/update")
    public void receiveUpdate(@RequestBody String update) {
    System.out.println("Message received: " + update);
    }
    
    
}
