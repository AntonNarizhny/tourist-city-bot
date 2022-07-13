# Tourist Telegram Bot
 
## Telegram bot,  который поможет узнать о различных городах🕌🏛🕍⛪🌴⛱⛩🛕  
Ботом могут пользоваться несколько пользователей одновременно.  
Имеет следующие функции :
- Вывод списка всех сохраненных городов
- Создание, редактирование и удаление городов
- Получение информации по введённому городу
- Добавление, редактирование и удаление информации о городах

## Необходимые инструменты ❗
1. `JDK 17` - для работы бота
2. `Maven` - для сборки проекта
3. `PostgreSQL` - для хранения данных

## Подготовка 🔨
Обратитесь к [BotFather](https://t.me/BotFather), создайте своего бота и получите токен.  
После Вам понадобится СУБД PostgreSQL. Отройте pgAdmin и создайте базу данных с названием "bot".  
Также необходим webhook https адрес, его можно получить с помощью программы [ngrok](https://ngrok.com/download). Создавать его нужно для порта 8081. Если хотите использовать другой порт, тогда измените порт в файле application.yaml.   
Telegram будет отправлять на него запросы, которые после будут перенаправляться на Ваш локальный сервер.  
Webhook https адрес необходимо будет установить для Telegram с помощью команды: https://api.telegram.org/bot[токен]/setWebhook?url=[webhook_https_адрес].
## Запуск проекта
1. Скопируйте проект к себе с помощью git clone.
2. В файле application.yaml добавьте данные для работы с ботом (username бота, токен и webhook https адрес) и данные для подключения к базе данных (имя пользователя, пароль и URL).
```java
server:
port: 8081 - порт

localeTag: ru-RU

telegrambot:
    botUsername: "@bot_username" - username Вашего бота
    botToken: telegram_bot_token_from_BotFather - токен бота
    botPath: https://webhook_path - webhook https адрес

spring:

    datasource:
        driver-class-name: org.postgresql.Driver
        username: db_username - имя пользователя PostgreSQL
        password: db_password - пароль пользователя PostgreSQL
        url: jdbc:postgresql://localhost:5432/bot - URL для подключения к базе даныых (добавьте порт и адрес, если у Вас другие)
    jpa:
        properties.hibernate:
        show_sql: true
        format_sql: true
        hbm2ddl.auto: validate
        open-in-view: false
```
3. При первом запуске проекта Liquibase создаст все необходимые таблицы в базе данных и добавит в них данные.
### **Технологии, которые были использованы**:
* Java 17
* Spring Framework (Core, Boot, Web, Data JPA)
* TelegramBots
* PostgreSQL
* Maven
* Lombok
* Liquibase

![Image of Maint](images/starting_page.png)
![Image of Maint](images/start_command.png)
![Image of Maint](images/all_cities.png)
![Image of Maint](images/add_city.png)
![Image of Maint](images/update_city.png)
![Image of Maint](images/delete_city.png)
![Image of Maint](images/get_information_about_city.png)
![Image of Maint](images/add_information_about_city.png)
![Image of Maint](images/update_information_about_city.png)
![Image of Maint](images/delete_information_about_city.png)
![Image of Maint](images/bot_profile.png)
