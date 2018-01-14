package ru.deysa.investment.assistant.server.scheduler;

/**
 * Created by Deys on 11.06.2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.deysa.investment.assistant.server.Config;
import ru.deysa.telegram.bot.client.TelegramBotClient;

import java.util.Calendar;

@Component
public class TelegramTask extends Thread {

    private final Logger log = LoggerFactory.getLogger(TelegramTask.class);

    @Autowired
    private Config config;

    private String message = "";

    private int hour = 0;

    private int minute = 0;

    @Override
    public void run() {
        try {
            while (true) {
                sleep(getTimeout(hour, minute));
                TelegramBotClient telegramBotClient =
                        new TelegramBotClient(config.getTelegramBotChatId(), config.getTelegramBotToken());
                telegramBotClient.sendMessage(message);
            }
        } catch (InterruptedException e) {
            log.error("Error in TelegramScheduler", e);
        }
    }

    public TelegramTask setMessage(String message) {
        this.message = message;
        return this;
    }

    public TelegramTask setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public TelegramTask setMinute(int minute) {
        this.minute = minute;
        return this;
    }

    private static long getTimeout(int hour, int minute) {
        Calendar futureTime = Calendar.getInstance();
        futureTime.set(Calendar.HOUR_OF_DAY, hour);
        futureTime.set(Calendar.MINUTE, minute);
        futureTime.set(Calendar.SECOND, 0);

        if (futureTime.before(Calendar.getInstance())) {
            futureTime.set(Calendar.DAY_OF_YEAR, futureTime.get(Calendar.DAY_OF_YEAR) + 1);
        }

        return futureTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    }

}