package ua.com.kulibin;

import org.testng.Assert;
import java.util.Random;

public class NumberGenerator {

    // инициализируем рандом
    Random random = new Random();

    /**
     * метод генерирует рандомно число, это число будет порядковым номером товара на странице;
     * в случае если рандомно число равно 0 - генерируется новый номер
     * @return порядковый номер товара
     */
    public int randomSequenceNumberProduct(int productOnPage) {
        Assert.assertNotEquals(productOnPage, 0, "Товаров на странице не найдено.");
        int sequenceNumberProduct = random.nextInt(productOnPage);

        while (sequenceNumberProduct == 0)
            sequenceNumberProduct = random.nextInt(productOnPage);

        return sequenceNumberProduct;
    }

    /**
     * метод генерирует рандомно число, это число будет номером страницы;
     * в случае если рандомно число равно 0 - генерируется новый номер
     * @return порядковый номер товара
     */
    public int randomPageNumber(int pageNumbers){
        Assert.assertNotEquals(pageNumbers, 0, "Страниц не найдено.");
        return randomSequenceNumberProduct(pageNumbers);
    }

}
