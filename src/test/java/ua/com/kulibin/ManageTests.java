package ua.com.kulibin;

import org.testng.annotations.Test;
import ua.com.kulibin.test.FirstTest;
import ua.com.kulibin.test.FourthTest;
import ua.com.kulibin.test.SecondTest;
import ua.com.kulibin.test.ThirdTest;

/**
 * этим классом можно запускать все тесты одновременно
 */
public class ManageTests {
    @Test
    public static void main(String[] args) {
        System.out.println("Run first test!");
        FirstTest checkForOldAndPromotionalPrices = new FirstTest();
        checkForOldAndPromotionalPrices.actionBeforeTest();
        checkForOldAndPromotionalPrices.test();
        checkForOldAndPromotionalPrices.actionsAfterTest();

        System.out.println("Run second test!");
        SecondTest checkThePricesOntThePages = new SecondTest();
        checkThePricesOntThePages.actionBeforeTest();
        checkThePricesOntThePages.test();
        checkThePricesOntThePages.actionsAfterTest();

        System.out.println("Run third test!");
        ThirdTest checkTheProductFlag = new ThirdTest();
        checkTheProductFlag.actionBeforeTest();
        checkTheProductFlag.test();
        checkTheProductFlag.actionsAfterTest();

        System.out.println("Run fourth test!");
        FourthTest calculationThePromotionalPrice = new FourthTest();
        calculationThePromotionalPrice.actionBeforeTest();
        calculationThePromotionalPrice.test();
        calculationThePromotionalPrice.actionsAfterTest();
    }

}
