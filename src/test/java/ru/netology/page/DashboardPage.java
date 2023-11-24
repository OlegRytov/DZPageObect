package ru.netology.page;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");
    private final String balanseStart = "баланс: ";
    private final String balanseFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalanse(DataHelper.CardsInfo cardsInfo) {
        var text = cards.findBy(text(cardsInfo.getCardNumber().substring(15))).getText();
        return extractBalanse(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardsInfo cardsInfo) {
        cards.findBy(attribute("data-test-id",cardsInfo.getTestid())).$("button").click();
        return new TransferPage();
    }

    private int extractBalanse(String text) {
        var start = text.indexOf(balanseStart);
        var finish = text.indexOf(balanseFinish);
        var value = text.substring(start + balanseStart.length(), finish);
        return Integer.parseInt(value);

    }
}
