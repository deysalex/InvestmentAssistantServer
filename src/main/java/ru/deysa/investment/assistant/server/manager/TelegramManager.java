package ru.deysa.investment.assistant.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Created by Deys on 15.06.2015.
 */
@Service
public class TelegramManager {

    private final Logger log = LoggerFactory.getLogger(TelegramManager.class);

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.chat.id}")
    private String telegramBotChatId;

    private String url = "https://api.telegram.org/bot";

    public boolean sendMessage(String message) {
        boolean result = true;
        try {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            map.add("chat_id", telegramBotChatId);
            map.add("text", message);

            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            rest.postForObject(url + telegramBotToken+ "/" + "sendMessage", map, String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;
        }
        return result;
    }

}
