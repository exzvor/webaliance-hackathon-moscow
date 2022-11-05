Существующие Api эндпойнты

- POST https://hack-auth.herokuapp.com/api/user/new //регистрация нового пользователя
- POST https://hack-auth.herokuapp.com/api/user/login //авторизация пользователя
- POST https://hack-auth.herokuapp.com/api/table/new //создание пользователем новой таблицы
- POST https://hack-auth.herokuapp.com/api/me/tables //таблицы пользователя

### Пример JSON body для регистрации и авторизации пользователя:
*{<br/>
"email": "user@mail.ru",<br/>
"password": "secret"<br/>
}*

### Пример JSON body для создания новой таблицы:
*{<br/>
"location": "ул.Пирогова д.27",<br/>
"age": "Новостройка",<br/>
"balcony": true,<br/>
"condition": "с отделкой",<br/>
"counter": 3,<br/>
"current_floor": 5,<br/>
"floors": 30,<br/>
"subway": 5<br/>
}*