# Сервис для расчета рыночной стоимости жилой недвижимости города Москва

## Api эндпойнты бекэнд сервиса:

- POST https://hack-auth.herokuapp.com/api/user/new // регистрация нового пользователя
- POST https://hack-auth.herokuapp.com/api/user/login // авторизация пользователя
- POST https://hack-auth.herokuapp.com/api/table/new // создание пользователем новой таблицы
- GET https://hack-auth.herokuapp.com/api/me/tables // вывод таблицы пользователя

### Пример JSON body для регистрации и авторизации пользователя:
*{<br/>
"email": "user@mail.ru",<br/>
"password": "secret"<br/>
}*

### Пример JSON body для создания новой таблицы:
*{<br/>
"location": "ул.Пирогова д.27",<br/>
"analog": 1,<br/>
"age": "Новостройка",<br/>
"balcony": true,<br/>
"counter": 3,<br/>
"current_floor": 2,<br/>
"floors": 30,<br/>
"subway": 5,<br/>
"etalon": false,<br/>
"market_price": 100000,<br/>
"price": 50000,<br/>
"metro_station": "ВДНХ",<br/>
"rooms": 4,<br/>
"walls": " ",<br/>
"area": 3,<br/>
"condition": 2<br/>
}*

### Дополнительная информация
- бекенд часть выложена на Heroku
- используемая СУБД - PostgreSQL
- архитектурный стиль взаимодействия компонентов - REST