package ru.wanderer.tourist_city_bot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.wanderer.tourist_city_bot.botapi.TelegramFacade;

@Setter
@Getter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "telegrambot")
public class TouristCityTelegramBot extends TelegramWebhookBot {

    @Autowired
    private TelegramFacade telegramFacade;

    private String botUsername;
    private String botToken;
    private String botPath;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }
}
