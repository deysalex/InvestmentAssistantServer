package ru.deysa.investment.assistant.server.scheduler;

/**
 * Created by Deys on 11.06.2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.deysa.investment.assistant.server.Config;
import ru.deysa.investment.assistant.server.external.IAssistant;
import ru.deysa.investment.assistant.store.client.StoreClient;
import ru.deysa.investment.assistant.store.client.api.v1.share.ShareResponse;
import ru.deysa.telegram.bot.client.TelegramBotClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelegramScheduler {

    private final Logger log = LoggerFactory.getLogger(TelegramScheduler.class);

    @Autowired
    private IAssistant assistant;

    @Autowired
    private Config config;

    @Scheduled(cron = "${cron.setting}")
    public void checkQuote() {
        StoreClient storeClient = new StoreClient(config.getInvestmentAssistantStoreApiUrl());
        List<ShareResponse> list =
                storeClient.getShares().stream().filter(ShareResponse::getEnable).collect(Collectors.toList());
        for (ShareResponse shareResponse : list) {
            try {
                String message = getMessage(shareResponse);
                if (!message.isEmpty()) {
                    TelegramBotClient telegramBotClient =
                            new TelegramBotClient(config.getTelegramBotChatId(), config.getTelegramBotToken());
                    telegramBotClient.sendMessage(message);
                    storeClient.disable(shareResponse);
                }
            } catch (Exception e) {
                log.error("Scheduler error", e);
            }
        }
    }

    private String getMessage(ShareResponse shareResponse) throws Exception {
        String result = "";
        String name = shareResponse.getName();
        Double quote = assistant.getValue(name);
        if (quote < shareResponse.getMin()) {
            result = name + " down " + quote;
        } else if (quote > shareResponse.getMax()) {
            result = name + " up " + quote;
        }
        return result;
    }

}