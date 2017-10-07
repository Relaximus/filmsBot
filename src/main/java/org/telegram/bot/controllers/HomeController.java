package org.telegram.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.session.SessionContext;
import pro.zackpollard.telegrambot.api.chat.Chat;
import pro.zackpollard.telegrambot.api.chat.message.send.*;
import pro.zackpollard.telegrambot.api.event.Event;
import pro.zackpollard.telegrambot.api.event.chat.CallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.CommandMessageReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.TextMessageReceivedEvent;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;

@Service
public class HomeController implements BotController<MessageReceivedEvent> {

    @Autowired
    SessionContext sessionContext;

    @Override
    public boolean isApplicable(MessageReceivedEvent event) {
        if (event instanceof CommandMessageReceivedEvent) {
            return true;
        } else if (event instanceof TextMessageReceivedEvent) {
            Boolean isSearching = sessionContext.get(event).getAttribute("isSearching");
            return isSearching == null || !isSearching;
        }
        return false;
    }

    @Override
    public void handle(MessageReceivedEvent event) {
        sendWelcome(event.getChat());
    }

    private void sendWelcome(Chat chat) {
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .addRow(InlineKeyboardButton.builder().text("Watch list").callbackData(Callbacks.WATCH.forChat()).build(),
                        InlineKeyboardButton.builder().text("Find movie by name...").callbackData(Callbacks.FIND.forChat()).build())
                .build();

        SendableMessage message =
                SendableTextMessage.builder()
                        .message("Hi, you can check you watch-list or search for a new film. Enjoy!")
                        .replyMarkup(inlineKeyboardMarkup)
                        .build();
        chat.sendMessage(message);
    }
}
