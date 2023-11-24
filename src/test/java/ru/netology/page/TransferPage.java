package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardsInfo cardsInfo) {
        makeTransfer(amountToTransfer, cardsInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardsInfo cardsInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardsInfo.getCardNumber());
        transferButton.click();
    }

    public void findErrorMessege(String expectedText) {
        errorMessage.shouldHave(text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);

    }
}