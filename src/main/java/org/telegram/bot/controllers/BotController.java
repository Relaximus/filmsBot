package org.telegram.bot.controllers;

import pro.zackpollard.telegrambot.api.event.Event;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageReceivedEvent;

public interface BotController {
    void handle(MessageReceivedEvent event);
}
