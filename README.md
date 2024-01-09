# job4j_cars

В проекте "job4j_cars" реализован сайт по продаже машин.

Функционал проекта позволяет:
* Просматривать все объявления;
* подавать объявления, загружать фотографии;
* просматривать подробное описание объявления;
* менять статус объявления (в наличии/продан);
* изменять стоимость;
* добавлять бывших владельцев;
* подписываться на объявления;
* использовать фильтры поиска;
* регистрировать пользователя;
* авторизация пользователя.

### Стек технологий :technologist:.
Основные :man_technologist:: 
- Java 17
- Spring Boot 2.7.6
- Hibernate 5.5
- Thymeleaf
- Bootstrap CSS
- Liquibase 4.15.0
- PostgreSQL 15.1 (драйвер JDBC 42.5.1)
- checkstyle 10.0.

Тестирование :mechanic::
- H2database 2.1.214
- Jacoco 0.8.8
- Spring boot starter test (JUnit 5 + AssertJ, Mockito).

### Требования к окружению :black_circle:.
- Java 17
- Maven 3.8
- PostgreSQL 15.

### Запуск проекта :running:.
```Скачать проект job4j_cars в IntelliJ Idea```

```Создать БД "cars" (с помощью pgAdmin4)```

```Cоздайте и заполните таблицы БД  "cars". Откройте закладку Maven -> plugins -> liquibase. Найдите задачу liquibase:update и выполните ее.```

```Запустите приложение в классе Main (ru/job4j/cars/Main.java)```

```Откройте страницу http://localhost:8080/ в браузере```

### Screenshots работы с приложением по продаже автомобилей :Cars sale:.

- [x] Страница со всеми объявлениями

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/1.jpg?raw=true)

- [x] Добавить объявление

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/1.1.jpg?raw=true)

- [x] Страница со всеми объявлениями авторизованного пользователя

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/2.jpg?raw=true)

- [x] Выводит список поданных объявлений пользователя

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/3.jpg?raw=true)

- [x] Успешная подписка на объявление

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/4.jpg?raw=true)

- [x] Подписки пользователя

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/5.jpg?raw=true)

- [x] Страница подробного описания объявления

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/6.jpg?raw=true)

- [x] Страница редактирования объявления

  ![](https://github.com/PavelValger/job4j_cars/blob/master/img/7.jpg?raw=true)

#### Контакты для связи :iphone::
* Вальгер Павел Иванович;
* +79920045094 telegram, whatsapp;
* pavelwalker@mail.ru.