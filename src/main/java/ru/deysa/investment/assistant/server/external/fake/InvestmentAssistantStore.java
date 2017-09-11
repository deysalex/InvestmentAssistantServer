package ru.deysa.investment.assistant.server.external.fake;

import ru.deysa.investment.assistant.server.external.IInvestmentAssistantStore;

import java.util.ArrayList;
import java.util.List;

public class InvestmentAssistantStore implements IInvestmentAssistantStore {
    @Override
    public double getMinValue(String name) {
        return -999;
    }

    @Override
    public double getMaxValue(String name) {
        return 999;
    }

    @Override
    public List<String> getShares() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        return list;
    }
}
