package org.telegram.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.MovieService;
import org.telegram.bot.domain.Movie;
import org.telegram.bot.session.SessionContext;
import pro.zackpollard.telegrambot.api.chat.message.MessageCallbackQuery;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageCallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;
import pro.zackpollard.telegrambot.api.user.User;

@Service
public class WatchListController implements BotController<MessageCallbackQueryReceivedEvent> {
    @Autowired
    SessionContext sessionContext;

    @Autowired
    MovieService movieService;
    @Autowired
    ControllerService controllerService;

    @Override
    public boolean isApplicable(MessageCallbackQueryReceivedEvent event) {
        return event.getCallbackQuery().getData().equals(Callbacks.WATCH.forChat());
    }

    @Override
    public void handle(MessageCallbackQueryReceivedEvent event) {
        MessageCallbackQuery query = event.getCallbackQuery();
        User user = query.getFrom();
        for(Movie movie : movieService.getMovies(user.getId())){

            InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                    .addRow(InlineKeyboardButton.builder().text("Remove from watch list ...")
                            .callbackData(Callbacks.REMOVE.forChat()+":" + movie.getId()).build())
                    .build();

            SendableMessage message = controllerService.movieForChat(movie, inlineKeyboardMarkup);
            query.getMessage().getChat().sendMessage(message);
        }

        controllerService.sendContinue(event,"Now you can:");
    }
}