package ua.com.kulibin.testSettings;

public interface ThirdTestSetting {
    // TODO количстево проверяемых страниц сделал гибким
    int NUMBER_PAGE_FOR_TESTING_FLAG = 3;

    /* TODO назву флага закинул в коннстанту, таким способом можно просто изменять логику программы,
     *  например заменить на "Japan", тогда будут выводиться все японские товары */
    String NAME_THE_FLAG = "United_states";
}
