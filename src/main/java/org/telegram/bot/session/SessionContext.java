package org.telegram.bot.session;

import org.springframework.stereotype.Repository;
import pro.zackpollard.telegrambot.api.event.Event;
import pro.zackpollard.telegrambot.api.event.chat.CallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.MessageEvent;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SessionContext {
    Map<Long, Session> sessionStorage;

    @PostConstruct
    private void init() {
        sessionStorage = new HashMap<>();
    }

    public <T extends Event> Session get(T event) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public Session get(MessageEvent event) {
        return get(event.getMessage().getSender().getId());
    }

    public Session get(CallbackQueryReceivedEvent event) {
        return get(event.getCallbackQuery().getFrom().getId());
    }

    public Session get(Long id) {
        sessionStorage.putIfAbsent(id,new Session());
        return sessionStorage.get(id);
    }
}
