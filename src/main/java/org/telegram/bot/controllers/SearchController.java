package org.telegram.bot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.MovieService;
import org.telegram.bot.domain.Movie;
import org.telegram.bot.session.SessionContext;
import pro.zackpollard.telegrambot.api.chat.message.send.InputFile;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendablePhotoMessage;
import pro.zackpollard.telegrambot.api.event.chat.message.TextMessageReceivedEvent;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;

import java.util.List;

@Service
@Slf4j
public class SearchController implements BotController<TextMessageReceivedEvent> {
    @Autowired
    SessionContext sessionContext;

    @Autowired
    MovieService movieService;

    @Autowired
    ControllerService controllerService;

    @Override
    public boolean isApplicable(TextMessageReceivedEvent event) {
        Boolean isSearching = sessionContext.get(event).getAttribute("isSearching");
        return isSearching != null && isSearching;
    }

    @Override
    public void handle(TextMessageReceivedEvent event) {
        try {
            String filmSearchStr = event.getContent().getContent();
            log.debug("Got search film str : {}", filmSearchStr);
            List<Movie> movies = movieService.findMoviesByTitle(filmSearchStr);
            log.debug("Found {} movies.", movies.size());

            for (Movie movie : movies) {

                InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                        .addRow(InlineKeyboardButton.builder().text("Add to watch list ...")
                                .callbackData(Callbacks.ADD.forChat()+":" + movie.getId()).build())
                        .build();

                SendableMessage message = controllerService.movieForChat(movie, inlineKeyboardMarkup);

                event.getChat().sendMessage(message);
            }
        } finally {
            sessionContext.get(event).addAttribute("isSearching", false);
        }
    }
}
