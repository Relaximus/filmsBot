package org.telegram.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import pro.zackpollard.telegrambot.api.chat.message.send.InputFile;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendablePhotoMessage;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;

@Data
@AllArgsConstructor
public class Movie {
    Long id;
    String title;
    int year;
    // url
    String poster;
}
