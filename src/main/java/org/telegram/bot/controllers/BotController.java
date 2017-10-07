package org.telegram.bot.controllers;

import pro.zackpollard.telegrambot.api.event.Event;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageReceivedEvent;

public interface BotController <T extends Event> {
    boolean isApplicable(T event);
    void handle(T event);
}
