package org.telegram.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.bot.domain.Movie;
import pro.zackpollard.telegrambot.api.chat.Chat;
import pro.zackpollard.telegrambot.api.chat.message.send.InputFile;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendablePhotoMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;
import pro.zackpollard.telegrambot.api.event.Event;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageCallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;

@Service
public class ControllerService {
    @Autowired
    ApplicationContext context;

    public void redirect(Class<? extends BotController> controllerClass, Event event) {
        context.getBean(controllerClass).handle(event);
    }

    public SendableMessage movieForChat(Movie movie) {
        return movieForChat(movie, null);
    }

    public SendableMessage movieForChat(Movie movie, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendablePhotoMessage.SendablePhotoMessageBuilder builder = SendablePhotoMessage.builder();
        builder.caption(movie.getTitle() + " (" + movie.getYear() + ")")
                .photo(new InputFile(movie.getPoster()));
        if (inlineKeyboardMarkup != null)
            builder.replyMarkup(inlineKeyboardMarkup);
        return builder.build();
    }

    public void sendContinue(Chat chat, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .addRow(InlineKeyboardButton.builder().text("Watch list").callbackData(Callbacks.WATCH.forChat()).build(),
                        InlineKeyboardButton.builder().text("Find movie by name").callbackData(Callbacks.FIND.forChat()).build())
                .build();

        SendableMessage message =
                SendableTextMessage.builder()
                        .message(text)
                        .replyMarkup(inlineKeyboardMarkup)
                        .build();
        chat.sendMessage(message);
    }

    public void sendContinue(MessageCallbackQueryReceivedEvent event, String text) {
        sendContinue(event.getCallbackQuery().getMessage().getChat(), text);
    }
}
