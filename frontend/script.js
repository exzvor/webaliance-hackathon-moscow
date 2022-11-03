'use strict'

const form = document.getElementById('form');
const btnMakeCalculation = document.querySelector('.btn-calculate');
const btnGetTable = document.querySelector('.btn-gettable');
const dropdown = document.querySelector('#dropdown');
const table = document.querySelector('.table');
const tbody = document.querySelector('.tbody');
const ul = document.querySelector('#pagination');

let pagiLi = document.querySelectorAll('#pagination li');
let liElems = "";
let ROWS = 20;  // default dropdown menu position
let token = "Bearer ";

const newUserURL = "https://hack-auth.herokuapp.com/api/user/new";
const loginURL = "https://hack-auth.herokuapp.com/api/user/login";
const createNewTableURL = "https://immense-sea-70871.herokuapp.com/https://hack-auth.herokuapp.com/api/table/new";
const getTablesURL = "https://immense-sea-70871.herokuapp.com/https://hack-auth.herokuapp.com/api/me/tables";

const arrayOfCells = [];
const arrayOfRows = [];

const situation = {
    tableLoaded: false,
}

class Table {

    constructor(location = "-", rooms = "-", age = "-", floors = "-", material = "-", currentFloor = "-", squareFlat = "-", squareKitchen = "-", balcony = "-", subway = "-", condition = "-", counter = 0) {
        this.location = location;
        this.rooms = rooms;
        this.age = age;
        this.floors = floors;
        this.material = material;
        this.currentFloor = currentFloor;
        this.squareFlat = squareFlat;
        this.squareKitchen = squareKitchen;
        this.balcony = balcony;
        this.subway = subway;
        this.condition = condition;
        this.counter = counter;
    }
    makeRow() {
        const row = `<tr id='${this.counter++}'><td class="checkbox"><input type=checkbox></td><td class="location"><input type='text' value='${this.location}'></td><td class="rooms"><input type='text' value='${this.rooms}'></td><td class="age"><input type='text' value='${this.age}'></td><td class="floors"><input type='text' value='${this.floors}'></td><td class="material"><input type='text' value='${this.material}'></td><td class="currentFloor"><input type='text' value='${this.currentFloor}'></td><td class="squareFlat"><input type='text' value='${this.squareFlat}'></td><td class="squareKitchen"><input type='text' value='${this.squareKitchen}'></td><td class="balcony"><input type='text' value='${this.balcony}'></td><td class="subway"><input type='text' value='${this.subway}'></td><td class="condition"><input type='text' value='${this.condition}'></td></tr>`;
        const tb = document.querySelector('.tbody');
        tb.insertAdjacentHTML('beforeend', row);
    };
}

function fileReader(oEvent) {
    const oFile = oEvent.target.files[0];
    const reader = new FileReader();
    situation.tableLoaded = true;

    reader.onload = function (e) {
        let data = e.target.result;
        data = new Uint8Array(data);
        const workbook = XLSX.read(data, { type: "array" });
        const result = {};
        workbook.SheetNames.forEach(function (sheetName) {
            const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], { header: 1 });
            if (roa.length) result[sheetName] = roa;
        });
        getRows(result);
    };
    reader.readAsArrayBuffer(oFile);
}

function displayTable() {
    for (let i = 0; i < ROWS; i++) {
        arrayOfRows[i].makeRow();
    }
}

function getRows(result) {
    let firstIndex = 0;
    let arr = Array.from(Object.values(result));
    for (let i = 1; i < arr[firstIndex].length; i++) {
        let table = new Table(arr[0][i][0], arr[0][i][1], arr[0][i][2], arr[0][i][3], arr[0][i][4], arr[0][i][5], arr[0][i][6], arr[0][i][7], arr[0][i][8], arr[0][i][9], arr[0][i][10], i - 1);
        arrayOfRows.push(table);
    };
    liElems = Math.ceil(arrayOfRows.length / ROWS);
    simEvent(dropdown);
    pagination();
    listenUsersModification();
}

function listenUsersModification() {

    let locat = Array.from(document.querySelectorAll('.location'));
    let rooms = Array.from(document.querySelectorAll('.rooms'));
    let age = Array.from(document.querySelectorAll('.age'));
    let floors = Array.from(document.querySelectorAll('.floors'));
    let material = Array.from(document.querySelectorAll('.material'));
    let currentFloor = Array.from(document.querySelectorAll('.currentFloor'));
    let squareFlat = Array.from(document.querySelectorAll('.squareFlat'));
    let squareKitchen = Array.from(document.querySelectorAll('.squareKitchen'));
    let balcony = Array.from(document.querySelectorAll('.balcony'));
    let subway = Array.from(document.querySelectorAll('.subway'));
    let condition = Array.from(document.querySelectorAll('.condition'));

    arrayOfCells.push(...locat, ...rooms, ...age, ...floors, ...material, ...currentFloor, ...squareFlat, ...squareKitchen, ...balcony, ...subway, ...condition);

    arrayOfCells.forEach(cell => cell.addEventListener('change', (e) => {
        arrayOfRows[e.currentTarget.parentElement.id][e.currentTarget.className] = e.target.value;
    }));
}


function displayMap() {
    var myMap = new ymaps.Map('map', {
        center: [55.753994, 37.622093],
        zoom: 9
    });

    ymaps.geocode('Ласинаостровкая ул., вл. 43', {
        /**
         * Опции запроса
         * @see https://api.yandex.ru/maps/doc/jsapi/2.1/ref/reference/geocode.xml
         */
        // Сортировка результатов от центра окна карты.
        // boundedBy: myMap.getBounds(),
        // strictBounds: true,
        // Вместе с опцией boundedBy будет искать строго внутри области, указанной в boundedBy.
        // Если нужен только один результат, экономим трафик пользователей.
        results: 1
    }).then(function (res) {
        // Выбираем первый результат геокодирования.
        var firstGeoObject = res.geoObjects.get(0),
            // Координаты геообъекта.
            coords = firstGeoObject.geometry.getCoordinates(),
            // Область видимости геообъекта.
            bounds = firstGeoObject.properties.get('boundedBy');

        firstGeoObject.options.set('preset', 'islands#darkBlueDotIconWithCaption');
        // Получаем строку с адресом и выводим в иконке геообъекта.
        firstGeoObject.properties.set('iconCaption', firstGeoObject.getAddressLine());

        // Добавляем первый найденный геообъект на карту.
        myMap.geoObjects.add(firstGeoObject);
        // Масштабируем карту на область видимости геообъекта.
        myMap.setBounds(bounds, {
            // Проверяем наличие тайлов на данном масштабе.
            checkZoomRange: true
        });

        /**
         * Все данные в виде javascript-объекта.
         */
        console.log('Все данные геообъекта: ', firstGeoObject.properties.getAll());
        /**
         * Метаданные запроса и ответа геокодера.
         * @see https://api.yandex.ru/maps/doc/geocoder/desc/reference/GeocoderResponseMetaData.xml
         */
        // console.log('Метаданные ответа геокодера: ', res.metaData);
        /**
         * Метаданные геокодера, возвращаемые для найденного объекта.
         * @see https://api.yandex.ru/maps/doc/geocoder/desc/reference/GeocoderMetaData.xml
         */
        // console.log('Метаданные геокодера: ', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData'));
        /**
         * Точность ответа (precision) возвращается только для домов.
         * @see https://api.yandex.ru/maps/doc/geocoder/desc/reference/precision.xml
         */
        // console.log('precision', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData.precision'));
        /**
         * Тип найденного объекта (kind).
         * @see https://api.yandex.ru/maps/doc/geocoder/desc/reference/kind.xml
         */
        // console.log('Тип геообъекта: %s', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData.kind'));
        // console.log('Название объекта: %s', firstGeoObject.properties.get('name'));
        // console.log('Описание объекта: %s', firstGeoObject.properties.get('description'));
        // console.log('Полное описание объекта: %s', firstGeoObject.properties.get('text'));
        /**
        * Прямые методы для работы с результатами геокодирования.
        * @see https://tech.yandex.ru/maps/doc/jsapi/2.1/ref/reference/GeocodeResult-docpage/#getAddressLine
        */
        // console.log('\nГосударство: %s', firstGeoObject.getCountry());
        // console.log('Населенный пункт: %s', firstGeoObject.getLocalities().join(', '));
        // console.log('Адрес объекта: %s', firstGeoObject.getAddressLine());
        // console.log('Наименование здания: %s', firstGeoObject.getPremise() || '-');
        // console.log('Номер здания: %s', firstGeoObject.getPremiseNumber() || '-');

        /**
         * Если нужно добавить по найденным геокодером координатам метку со своими стилями и контентом балуна, создаем новую метку по координатам найденной и добавляем ее на карту вместо найденной.
         */

        var myPlacemark = new ymaps.Placemark(coords, {
            iconContent: 'моя метка',
            balloonContent: 'Содержимое балуна <strong>моей метки</strong>'
        }, {
            preset: 'islands#violetStretchyIcon'
        });

        myMap.geoObjects.add(myPlacemark);
    });
}
ymaps.ready(displayMap);

// Connect to web-server login
form.addEventListener('submit', (e) => {
    e.preventDefault();

    const email = document.querySelector(".email").value;
    const password = document.querySelector(".password").value;
    const body = `{\"email\": \"${email}\", \"password\": \"${password}\"}`;
    if (token === 'Bearer ') {
        fetch(loginURL, {
            method: "POST",
            body: body,
        })
            .then(response => response.json())
            .then(result => token += result.account.token)
            .catch(error => console.log('error', error));
        console.log('you are succesfully logged in');
    } else console.log('you are logged in already');
});

// Download and read excel file 
document.querySelector('#input__file').addEventListener('change', function (e) {
    if (situation.tableLoaded) {
        tbody.innerHTML = "";
        ul.innerHTML = "";
        arrayOfRows.length = 0;
        arrayOfCells.length = 0;
        fileReader(e);
    } else {
        fileReader(e);
    }
});

btnMakeCalculation.addEventListener('click', (e) => {
    console.log('sending request to the server')
    e.preventDefault();
    let arr = []

    Array.from(document.querySelectorAll('input[type=checkbox]:checked')).forEach((item => { item.checked === true ? arr.push(item.parentElement.parentElement.id) : null }));

    let body = "";

    arr.forEach(item => {

        arrayOfRows[item]['balcony'] === 'да' ? arrayOfRows[item]['balcony'] = true : arrayOfRows[item]['balcony'] = false;

        body = JSON.stringify(arrayOfRows[item]);
    })
    let myHeaders = new Headers();
    myHeaders.append("Authorization", token);
    myHeaders.append("Content-Type", "application/json");

    fetch(createNewTableURL, {
        method: "POST",
        headers: myHeaders,
        body: body
    })
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
});

function simEvent(element) {
    const ev = new Event('change', { "bubbles": true, "cancelable": false });
    element.dispatchEvent(ev);
}

dropdown.addEventListener('change', () => {
    console.log('send request to get table')
    dropdown.options.value != 20 ? ROWS = dropdown.value : ROWS = 20;
    liElems = Math.ceil(arrayOfRows.length / ROWS);
    tbody.innerHTML = "";
    ul.innerHTML = "";
    displayTable();
    for (let i = 0; i < liElems; i++) {
        let li = document.createElement('li');
        ul.appendChild(li);
        li.innerHTML = i + 1;
    }
    pagiLi = document.querySelectorAll('#pagination li');
    pagination();
})

function pagination() {
    for (let item of pagiLi) {
        item.addEventListener('click', (e) => {
            let pageNum = +e.target.innerHTML;
            let start = (pageNum - 1) * ROWS;
            let end = start + +ROWS;

            let notes = arrayOfRows.slice(start, end);
            tbody.innerHTML = "";

            for (let note of notes) {
                note.makeRow();
            }
        })
    }
}

btnGetTable.addEventListener('click', (e) => {
    e.preventDefault();

    let myHeaders = new Headers();
    myHeaders.append("Authorization", token);
    myHeaders.append("Content-Type", "application/json");

    fetch(getTablesURL, {
        method: "GET",
        headers: myHeaders,
    }).then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
})