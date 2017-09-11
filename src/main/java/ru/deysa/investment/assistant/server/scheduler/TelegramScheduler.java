package ru.deysa.investment.assistant.server.scheduler;

/**
 * Created by Deys on 11.06.2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.deysa.investment.assistant.server.external.IAssistant;
import ru.deysa.investment.assistant.server.external.IInvestmentAssistantStore;
import ru.deysa.investment.assistant.server.manager.TelegramManager;

@Component
public class TelegramScheduler {

    private final Logger log = LoggerFactory.getLogger(TelegramScheduler.class);

    @Autowired
    private TelegramManager telegramManager;

    @Autowired
    private IAssistant assistant;

    @Autowired
    private IInvestmentAssistantStore investmentAssistantStore;

    @Scheduled(cron = "${cron.setting}")
    public void checkQuote()
    {
        for (String name : investmentAssistantStore.getShares()) {
            try {
                Double quote = assistant.getValue(name);
                if (quote > investmentAssistantStore.getMaxValue(name)) {
                    telegramManager.sendMessage(" up " + quote);
                } else if (quote < investmentAssistantStore.getMinValue(name)) {
                    telegramManager.sendMessage(" down " + quote);
                }
            } catch (Exception e) {
                log.error("Scheduler error", e);
            }
        }
    }

}