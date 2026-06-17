# 🌦 Weather Viewer

## 📖 Описание проекта

Weather Viewer — веб-приложение для просмотра текущей погоды в выбранных пользователем локациях.

Пользователь может:

* зарегистрироваться и авторизоваться
* искать города через OpenWeather API
* добавлять локации в свою коллекцию
* просматривать текущую температуру и описание погоды
* удалять сохранённые локации
* выходить из аккаунта

Проект реализован без использования Spring Security и Spring Session — работа с cookies и сессиями выполнена вручную для лучшего понимания механизма авторизации.

---

# ⚙️ Использованные технологии

## Backend

* Java 24
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* PostgreSQL
* Flyway
* Gradle
* Lombok
* OpenWeather API

## Frontend

* Thymeleaf
* HTML5
* CSS3
* Bootstrap 5

## Testing

* JUnit 5
* Mockito
* H2 Database

---

# 🚀 Функционал

## 👤 Пользователи

### Регистрация

Адрес: `/sign-up`

Возможности:

* регистрация пользователя
* проверка уникальности логина
* подтверждение пароля
* валидация данных

---

### Авторизация

Адрес: `/sign-in`

Возможности:

* вход по логину и паролю
* создание пользовательской сессии
* сохранение session id в cookies

---

### Logout

Возможности:

* удаление пользовательской сессии
* очистка cookies

---

# 🌍 Работа с локациями

## Главная страница

Адрес: `/`

Возможности:

* просмотр сохранённых локаций
* отображение текущей температуры
* отображение погодного описания
* удаление локаций
* поиск новых локаций
* отображение текущего пользователя

---

## Страница поиска

Адрес: `/locations`

Возможности:

* поиск города через OpenWeather API
* отображение найденных локаций
* добавление локации в коллекцию пользователя

---

# 🔐 Работа с сессиями

Авторизация реализована вручную через:

* cookies
* собственную таблицу `sessions`
* UUID идентификаторы сессий

Spring Security и Spring Session не использовались.

---

# 🗄 База данных

## Таблица users

| Поле     | Тип     |
| -------- | ------- |
| id       | bigint  |
| login    | varchar |
| password | varchar |

---

## Таблица locations

| Поле      | Тип     |
| --------- | ------- |
| id        | bigint  |
| name      | varchar |
| latitude  | decimal |
| longitude | decimal |
| user_id   | bigint  |

---

## Таблица sessions

| Поле       | Тип       |
| ---------- | --------- |
| id         | uuid      |
| expires_at | timestamp |
| user_id    | bigint    |

---

# 🧪 Тестирование

Проект покрыт интеграционными тестами:

* регистрация пользователя
* создание сессии
* проверка истечения сессии
* проверка невалидных session id
* тестирование OpenWeather API клиента через mock RestTemplate

Для тестов используется:

* JUnit 5
* Mockito
* H2 in-memory database
* отдельный test profile

---

# 🔧 Запуск проекта

## Требования

* Java 24+
* PostgreSQL
* Gradle

---

## Клонирование проекта

```bash
git clone https://github.com/your-username/weather-app.git
```

---

## Настройка базы данных

Создайте PostgreSQL базу данных:

```sql
CREATE DATABASE weather_app;
```

---

## Настройка application.properties

Укажите свои данные:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/weather_app
spring.datasource.username=postgres
spring.datasource.password=your_password

weather.api.key=your_openweather_api_key
```

---

## Получение API ключа

Зарегистрируйтесь на:

https://openweathermap.org/

Создайте бесплатный API key.

---

## Запуск приложения

```bash
./gradlew bootRun
```

После запуска приложение будет доступно по адресу:

```text
http://localhost:8080
```

---

# 📚 Что было изучено в проекте

* Spring MVC
* работа с cookies и sessions
* интеграция внешнего API
* Hibernate / JPA
* Flyway migrations
* Thymeleaf
* интеграционное тестирование
* mock объекты
* работа с PostgreSQL
* архитектура MVC приложения

---

# 📌 Особенности проекта

* ручная реализация авторизации
* отдельный test profile
* интеграционные тесты сервисов
* использование DTO для внешнего API
* разделение слоёв Controller / Service / Repository
