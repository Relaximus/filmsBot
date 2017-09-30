package org.telegram.bot.controllers;

import pro.zackpollard.telegrambot.api.chat.message.send.*;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageReceivedEvent;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;

public class HomeController implements BotController {
    @Override
    public void handle(MessageReceivedEvent event) {
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .addRow(InlineKeyboardButton.builder().text("Watch list").callbackData("watch id").build(),
                        InlineKeyboardButton.builder().text("Find movie by name...").build())
                .build();

        SendableMessage message =
                SendableTextMessage.builder()
                        .message("Hi, you can check you watch-list or search for a new film. Enjoy!")
                        .replyMarkup(inlineKeyboardMarkup)
                            .build();
        event.getChat().sendMessage(message);
    }
}
