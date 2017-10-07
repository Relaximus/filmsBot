package org.telegram.bot;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.bot.controllers.BotController;
import org.telegram.bot.domain.Movie;
import pro.zackpollard.telegrambot.api.chat.CallbackQuery;
import pro.zackpollard.telegrambot.api.chat.CallbackQueryType;
import pro.zackpollard.telegrambot.api.chat.Chat;
import pro.zackpollard.telegrambot.api.chat.message.MessageCallbackQuery;
import pro.zackpollard.telegrambot.api.chat.message.send.InputFile;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendablePhotoMessage;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;
import pro.zackpollard.telegrambot.api.event.Event;
import pro.zackpollard.telegrambot.api.event.Listener;
import pro.zackpollard.telegrambot.api.event.chat.CallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.*;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public class MainListener implements Listener {
    Logger log = Logger.getLogger(MainListener.class);

    @Autowired
    List<BotController> controllers;

    @Override
    public void onMessageCallbackQueryReceivedEvent(MessageCallbackQueryReceivedEvent event) {
        handle(event);
    }

    @Override
    public void onTextMessageReceived(TextMessageReceivedEvent event) {
        handle(event);
    }

    @Override
    public void onCommandMessageReceived(CommandMessageReceivedEvent event) {
        handle(event);
    }

    private void handle(Event event) {
        for (BotController controller : controllers)
            if (instanceOf(controller, event) && controller.isApplicable(event)) controller.handle(event);
    }

    private boolean instanceOf(BotController<?> controller, Event event) {
        Type[] genericInterfaces = controller.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    try {
                        if (Class.forName(genericType.getTypeName()).isAssignableFrom(event.getClass()))
                            return true;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
