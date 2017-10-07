package org.telegram.bot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.MovieService;
import org.telegram.bot.session.SessionContext;
import pro.zackpollard.telegrambot.api.chat.message.MessageCallbackQuery;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageCallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.user.User;

@Service
@Slf4j
public class RemoveWatchListController implements BotController<MessageCallbackQueryReceivedEvent> {
    @Autowired
    SessionContext sessionContext;

    @Autowired
    MovieService movieService;

    @Autowired
    ControllerService controllerService;

    @Override
    public boolean isApplicable(MessageCallbackQueryReceivedEvent event) {
        return event.getCallbackQuery().getData().startsWith(Callbacks.REMOVE.forChat());
    }

    @Override
    public void handle(MessageCallbackQueryReceivedEvent event) {

        MessageCallbackQuery query = event.getCallbackQuery();
        Long filmId;
        try {
             filmId = Long.parseLong(query.getData().substring(Callbacks.REMOVE.forChat().length() + 1));
        } catch (NumberFormatException ex) {
            log.error("Fuck", ex);
            controllerService.sendContinue(event, "Something went wrong, try again, please...");
            return;
        }
        User user = query.getFrom();
        log.info("Removing movie {} for user {}", filmId, user.getId());

        movieService.removeFromWatchList(user, filmId);

        controllerService.sendContinue(event, "Movie was removed from your watch list.");

    }
}
