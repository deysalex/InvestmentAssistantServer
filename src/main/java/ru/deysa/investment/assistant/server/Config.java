package ru.deysa.investment.assistant.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${investment.assistant.store.api.url}")
    private String investmentAssistantStoreApiUrl;

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.chat.id}")
    private String telegramBotChatId;

    public String getInvestmentAssistantStoreApiUrl() {
        return investmentAssistantStoreApiUrl;
    }

    public String getTelegramBotToken() {
        return telegramBotToken;
    }

    public String getTelegramBotChatId() {
        return telegramBotChatId;
    }
}
