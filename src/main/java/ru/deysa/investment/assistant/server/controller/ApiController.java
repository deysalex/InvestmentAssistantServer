package ru.deysa.investment.assistant.server.controller;

/**
 * Created by Deys on 11.06.2015.
 */

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.deysa.investment.assistant.server.Config;
import ru.deysa.telegram.bot.client.TelegramBotClient;

@Controller
@RequestMapping("api")
public class ApiController {

    private final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private Config config;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String send(@RequestParam("text") String text) {
        String result = "false";
        String error = "";
        JSONObject jsonObject = new JSONObject();
        try {
            TelegramBotClient telegramBotClient =
                    new TelegramBotClient(config.getTelegramBotChatId(), config.getTelegramBotToken());
            telegramBotClient.sendMessage(text);
            result = "true";
        } catch (Exception e) {
            error = e.getMessage();
            log.error(error);
        }
        jsonObject.put("result", result);
        jsonObject.put("error", error);

        return jsonObject.toJSONString();
    }
}
