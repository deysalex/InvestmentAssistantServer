package ru.deysa.investment.assistant.server.external.fake;

import ru.deysa.investment.assistant.server.external.IAssistant;

public class Assistant implements IAssistant {

    @Override
    public double getValue(String name) throws Exception {
        return 5000;
    }
}
