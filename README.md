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
"analog": 2,<br/>
"etalon": "ул.Пирогова д.27",<br/>
"market_price": 2200000,<br/>
"price": 2300000,<br/>
"metro_station": "ВДНХ",<br/>
"rooms": 2,<br/>
"walls": " ",<br/>
"area ": " ",<br/>
"age": "Новостройка",<br/>
"balcony": true,<br/>
"condition": "с отделкой",<br/>
"counter": 3,<br/>
"current_floor": 5,<br/>
"floors": 30,<br/>
"subway": 5<br/>
}*

### Дополнительная информация
- бекенд часть выложена на Heroku
- используемая СУБД - PostgreSQL
- архитектурный стиль взаимодействия компонентов - REST