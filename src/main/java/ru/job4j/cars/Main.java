package ru.job4j.cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
//На сайте должны быть объявления. В объявлении должно быть: описание, марка машины, тип кузова, фото.
//Объявление имеет статус продано или нет.

//1. Основная страница. таблица со всеми объявлениям машин на продажу.
//2. На странице должна быть кнопка. добавить новое объявление.
//3. Переходить на страницу добавления.
//4. Должны быть категории машины, марка, тип кузова и тд. Пример с сайта auto.ru. - Фильтры поиска крч.
//5. Можно добавлять фото.
//6. объявление имеет статус продано. или нет.
//7. Должны существовать пользователи. кто подал заявление. только он может менять статус.


//Прайс из истории цены в dto можешь подставить. Нужно взять последнюю по дате цену из истории цен.