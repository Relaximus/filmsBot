package org.telegram.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.session.SessionContext;
import pro.zackpollard.telegrambot.api.chat.message.MessageCallbackQuery;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageCallbackQueryReceivedEvent;

@Service
public class SearchInvitationController implements BotController<MessageCallbackQueryReceivedEvent> {
    @Autowired
    SessionContext sessionContext;

    @Override
    public boolean isApplicable(MessageCallbackQueryReceivedEvent event) {
        return event.getCallbackQuery().getData().equals(Callbacks.FIND.forChat());
    }

    @Override
    public void handle(MessageCallbackQueryReceivedEvent event) {
     sessionContext.get(event).addAttribute("isSearching", true);
     event.getCallbackQuery().getMessage().getChat().sendMessage(
             SendableTextMessage.builder().message("Ok, type the name of film...").build());
    }
}