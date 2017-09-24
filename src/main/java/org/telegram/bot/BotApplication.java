package org.telegram.bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pro.zackpollard.telegrambot.api.TelegramBot;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;
import pro.zackpollard.telegrambot.api.event.Listener;
import pro.zackpollard.telegrambot.api.event.chat.message.CommandMessageReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.TextMessageReceivedEvent;
import pro.zackpollard.telegrambot.api.keyboards.KeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.ReplyKeyboardMarkup;

@SpringBootApplication
public class BotApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        TelegramBot bot = TelegramBot.login("388454574:AAGm5nfjt46wirLxelsCa-GdLaMOpICgv7g");
        //The API key was invalid, an error will have also been printed into the console.
        if (bot == null) System.exit(-1);

        bot.getEventsManager().register(new MyListener());

        //This will tell the API to start polling the servers for updates
        //If you specify true as the argument you will receive any previous messages before the bot started.
        //If you specify false the API will discard any messages from before the bot was started.
        bot.startUpdates(false);
    }

    //Listener class
    public class MyListener implements Listener {

        @Override
        public void onCommandMessageReceived(CommandMessageReceivedEvent event) {
            String command = event.getCommand();
            System.out.println("Got command : " + event.getCommand());
            SendableTextMessage message = SendableTextMessage.builder().message("try one of those ....").replyTo(event.getMessage())
                    .replyMarkup(ReplyKeyboardMarkup.builder()
                            .addRow(KeyboardButton.builder().text("/click one").build(), KeyboardButton.builder().text("/click two").build())
                            .build()
                    ).build();
            System.out.println("Trying to send : "+message);
            //event.getChat().sendMessage("You sent me : " + command);
            event.getChat().sendMessage(message);

        }

//        public void onCommandMessageReceived(CommandMessageReceivedEvent event) {
//            String command = event.getCommand();
//            System.out.println("Got command : " + event.getCommand());
//            if (event.getCommand().equals("sya_hi")) {
//                SendableTextMessage message = SendableTextMessage.builder().message("try one of those ....").replyTo(event.getMessage())
//                        .replyMarkup(ReplyKeyboardMarkup.builder()
//                                .addRow(KeyboardButton.builder().text("click one").build(), KeyboardButton.builder().text("click two").build())
//                                .build()
//                        ).build();
//                System.out.println("Trying to send : "+message);
//                event.getChat().sendMessage(message);
//            }
//        }

        @Override
        public void onTextMessageReceived(TextMessageReceivedEvent event) {
            event.getChat().sendMessage(
                    SendableTextMessage.builder()
                            .message("You sent me a text based message!")
                            .replyTo(event.getMessage())
                            .build()
            );
        }
    }
}
