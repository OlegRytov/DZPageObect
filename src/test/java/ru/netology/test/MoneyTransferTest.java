package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;


public class MoneyTransferTest {
    DashboardPage dashboargPage;
    CardsInfo firstCardInfo;
    CardsInfo secondCardInfo;

    @BeforeEach
    void setup(){
        var LoginPage = open("http://localhost:9999/", LoginPage.class);
        var autoInfo = getAuthInfo();
        var verificationPage = LoginPage.validLogin(autoInfo);
        var verificationCode = getVerificationCodeFor();
        dashboargPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
    }

    @Test
    @DisplayName("Should Transfer From First To Second")
    void shouldTransferFromFirstToSecond() {
        var firstCardBalance = dashboargPage.getCardBalanse(firstCardInfo);
        var secondCardBalance = dashboargPage.getCardBalanse(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboargPage.selectCardToTransfer(secondCardInfo);
        dashboargPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboargPage.getCardBalanse(firstCardInfo);
        var actualBalanceSecondCard = dashboargPage.getCardBalanse(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    /*@Test
    @DisplayName("Should Transfer From Second To First")
    void shouldTransferFromSecondToFirst() {
        var firstCardBalance = dashboargPage.getCardBalanse(firstCardInfo);
        var secondCardBalance = dashboargPage.getCardBalanse(secondCardInfo);
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var transferPage = dashboargPage.selectCardToTransfer(firstCardInfo);
        dashboargPage = transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
        var actualBalanceFirstCard = dashboargPage.getCardBalanse(firstCardInfo);
        var actualBalanceSecondCard = dashboargPage.getCardBalanse(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

     */
    @Test
    @DisplayName("should Get Error Message If Amount More Balance")
    void shouldGetErrorMessageIfAmountMoreBalance() {
        var firstCardBalance = dashboargPage.getCardBalanse(firstCardInfo);
        var secondCardBalance = dashboargPage.getCardBalanse(secondCardInfo);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboargPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.findErrorMessege("Ошибка");
        var actualBalanceFirstCard = dashboargPage.getCardBalanse(firstCardInfo);
        var actualBalanceSecondCard = dashboargPage.getCardBalanse(secondCardInfo);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }
}
