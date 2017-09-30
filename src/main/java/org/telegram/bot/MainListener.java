package org.telegram.bot;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.bot.domain.Movie;
import pro.zackpollard.telegrambot.api.chat.CallbackQuery;
import pro.zackpollard.telegrambot.api.chat.CallbackQueryType;
import pro.zackpollard.telegrambot.api.chat.Chat;
import pro.zackpollard.telegrambot.api.chat.message.MessageCallbackQuery;
import pro.zackpollard.telegrambot.api.chat.message.send.InputFile;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendablePhotoMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;
import pro.zackpollard.telegrambot.api.event.Listener;
import pro.zackpollard.telegrambot.api.event.chat.CallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.CommandMessageReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.TextMessageReceivedEvent;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;


public class MainListener implements Listener {
    Logger log = Logger.getLogger(MainListener.class);

    @Autowired
    MovieService movieService;

    @Override
    public void onCommandMessageReceived(CommandMessageReceivedEvent event) {
        String command = event.getCommand();
        Chat chat = event.getChat();
        log.info("Got command : " + event.getCommand() + " with args : " + event.getArgsString());

        try {
            Commands c = Commands.valueOf(command.toUpperCase());
            switch (c) {
                case FIND:
                    handleFind(event);
                    break;
                default:
                    throw new UnsupportedOperationException(c+" not implemented yet.");
            }
        }catch(IllegalArgumentException | UnsupportedOperationException ex){
            log.error(ex);
            sendWelcome(chat);
            return;
        }
    }

    private void sendWelcome(Chat chat){
        chat.sendMessage("Hi, you can search for the movies using next command: \n" +
                "/find <movie name>.\n" +
                "Enjoy.");
    }

    private void handleFind(CommandMessageReceivedEvent event){
        String args = event.getArgsString();
        if (args.isEmpty()) {
            sendWelcome(event.getChat());
            return;
        }

        for (Movie movie : movieService.findMoviesByTitle(args)) {

            InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                    .addRow(InlineKeyboardButton.builder().text("Add to watch list ...").callbackData("movie id").build())
                    .build();

            SendableMessage message =
            SendablePhotoMessage.builder()
                    .caption(movie.getTitle()+" ("+movie.getYear()+")")
                    .photo(new InputFile(movie.getPoster()))
                    .replyMarkup(inlineKeyboardMarkup)
                    .build();

            event.getChat().sendMessage(message);
        }
    }

    @Override
    public void onTextMessageReceived(TextMessageReceivedEvent event) {
        sendWelcome(event.getChat());
    }

    @Override
    public void onCallbackQueryReceivedEvent(CallbackQueryReceivedEvent event) {
        CallbackQuery callbackQuery = event.getCallbackQuery();
        CallbackQueryType mType = callbackQuery.getType();
        switch (mType){
            case MESSAGE:
                MessageCallbackQuery query = (MessageCallbackQuery) callbackQuery;
                log.info("Got the callback from inline button with data : " + query.getData());
                query.answer("You sent me "+query.getData(),false);
                break;
            default:
                throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
