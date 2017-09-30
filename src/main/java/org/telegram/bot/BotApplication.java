package org.telegram.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.zackpollard.telegrambot.api.TelegramBot;

@SpringBootApplication
public class BotApplication implements CommandLineRunner {

    @Bean
    MainListener listener(){
        return new MainListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        TelegramBot bot = TelegramBot.login("388454574:AAGm5nfjt46wirLxelsCa-GdLaMOpICgv7g");
        //The API key was invalid, an error will have also been printed into the console.
        if (bot == null) System.exit(-1);

        bot.getEventsManager().register(listener());

        //This will tell the API to start polling the servers for updates
        //If you specify true as the argument you will receive any previous messages before the bot started.
        //If you specify false the API will discard any messages from before the bot was started.
        bot.startUpdates(false);
    }

}
