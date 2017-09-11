package ru.deysa.investment.assistant.server.external;

import java.util.List;

public interface IInvestmentAssistantStore {

    double getMinValue(String name);

    double getMaxValue(String name);

    List<String> getShares();
}
