// Dependencies:
// npm install cycletls
// npm i jsdom
// npm install xmldom


import initCycleTLS from 'cycletls';
import jsdom from 'jsdom';
const { JSDOM } = jsdom;
import { DOMParser } from 'xmldom';


(async () => {
  
    // Initiate CycleTLS
    const cycleTLS = await initCycleTLS();

// --> GET PARAMETERS FROM JSON ON SERVER 
// --> var area;

    // List all parameters from avito filter by value
    
    // Building age
    const secondaryHousing = '&params%5B499%5D%5B0%5D=5254';
    const newBuilding = '&params%5B499%5D%5B0%5D=5255';

    // Number of rooms
    const studio = '&params%5B549%5D%5B0%5D=5695';
    const oneRoom = '&params%5B549%5D%5B0%5D=5696';
    const twoRooms = '&params%5B549%5D%5B0%5D=5697';
    const threeRooms = '&params%5B549%5D%5B0%5D=5698';
    const fourRooms = '&params%5B549%5D%5B0%5D=5699';
    const fiveMoreRooms = '&params%5B549%5D%5B0%5D=414718';

    // Floors in building 
    // Add changing parameter for maxFloor int value
    const maxFloor = '&params%5B2950%5D%5Bto%5D=13';
    const notFirstFloor = '&params%5B110486%5D%5B0%5D=438738';
    
    // Flat parameter, not apartments
    const isFlat = '&params%5B110688%5D=458589';

    // footWalkingMetro accepts only [5, 10, 15, 20, 30]

    // Renovation condition for secondaryHousing
    const noDec = '&params%5B110710%5D%5B0%5D=472004';
    const cosmeticDec = '&params%5B110710%5D%5B0%5D=472001';
    const euroDec = '&params%5B110710%5D%5B0%5D=472002';

    // Renovation condition for newBuilding
    const withoutDec = '&params%5B100068%5D%5B0%5D=192856';
    const partialDec = '&params%5B100068%5D%5B0%5D=192857';
    const withDec = '&params%5B100068%5D%5B0%5D=192858';
  
    // Balcony
    const balcony = '&params%5B118593%5D%5B0%5D=1714473&params%5B118593%5D%5B1%5D=1714472';
    
    // Building type
    const brick = '&params%5B498%5D%5B0%5D=5244';
    const panel = '&params%5B498%5D%5B0%5D=5245';
    const monolith = '&params%5B498%5D%5B0%5D=5247';

// --> WRITE FUNCTION TO EVALUATE ETALON PARAMETERS FROM SERVER

    var testLink = 'https://www.avito.ru/web/1/js/items?categoryId=24&locationId=637640&params%5B201%5D=1059' + secondaryHousing + twoRooms + isFlat + cosmeticDec + balcony + monolith + '&presentationType=fullMap&showMap=1&footWalkingMetro=' + '20';
    // console.log(testLink);

    // Send filter request according to input parameters of flat from server
    const response1 = await cycleTLS(testLink, {
      body: '',
      ja3: '771,4865-4867-4866-49195-49199-52393-52392-49196-49200-49162-49161-49171-49172-51-57-47-53-10,0-23-65281-10-11-35-16-5-51-43-13-45-28-21,29-23-24-25-256-257,0',
      userAgent: 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:87.0) Gecko/20100101 Firefox/87.0',
      proxy: ''
    }, 'get');
  
    
    const fliterResponse = response1;
    console.log(fliterResponse.body.url);
    // console.log(response);
    // const link = 'https://www.avito.ru' + fliterResponse.body.url;
    // console.log(link);

    // Get all ads for subway station
    const responseMetro = await cycleTLS('https://www.avito.ru/js/1/search/locations?locationId=637640&categoryId=24', {
        body: '',
        ja3: '771,4865-4867-4866-49195-49199-52393-52392-49196-49200-49162-49161-49171-49172-51-57-47-53-10,0-23-65281-10-11-35-16-5-51-43-13-45-28-21,29-23-24-25-256-257,0',
        userAgent: 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:87.0) Gecko/20100101 Firefox/87.0',
        proxy: ''
        }, 'get');


// --> REWRITE evaluateMetro() INPUT TO BE METRO PARAMETER FROM SERVER
    var metroResult;
    evaluateMetro();
    var metroURL = '&metro=' + metroResult;
    //console.log(metroResult);
    //console.log(metroURL);

    // Function to get metro id value from Avito
    function evaluateMetro() {

        var metroObj = responseMetro;
        if (metroObj.body.result.params[0].parameters[1].value[50].name == 'Давыдково'){
            metroResult = metroObj.body.result.params[0].parameters[1].value[50].id;
            return metroResult;
        }
    }
  
    // Send request to Avito to find ads according to selected metro
    const response2 = await cycleTLS('https://www.avito.ru' + fliterResponse.body.url + metroURL, {
      body: '',
      ja3: '771,4865-4867-4866-49195-49199-52393-52392-49196-49200-49162-49161-49171-49172-51-57-47-53-10,0-23-65281-10-11-35-16-5-51-43-13-45-28-21,29-23-24-25-256-257,0',
      userAgent: 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:87.0) Gecko/20100101 Firefox/87.0',
      proxy: ''
    }, 'get');
  
    var xml = response2.body;
    // var doc = new DOMParser().parseFromString(xml);
    // var nodes = xpath.select("//div[contains(@class,'iva-item-sliderLink')]", doc);
  
    // console.log(doc);
    const dom = new JSDOM(xml);
    //console.log(dom);
  
    const document = dom.window.document;
    const items = document.querySelectorAll('[data-marker=item]');
    const itemsFound = Number(document.querySelector('[data-marker="page-title/count"]').textContent);
    console.log(itemsFound);

//--> WRITE FUNCTION TO CHECK FOR itemsFound NOT TO BE 0, IF SO, EXPAND METRO RADIUS

    const newAds = {};

    // Copy nodes from Avito response to newAds object
    items.forEach(node => {
        newAds[node.id] = {
            id: node.id,
            title: node.querySelector('[itemprop="name"]').textContent, 
            price: Number(node.querySelector('[itemprop="price"]').getAttribute('content')),
            url: node.querySelector('[itemprop="url"]').getAttribute('href'),
            adress: node.querySelector('[class^="geo-address"]').textContent,
            priceMeter: Number((node.querySelector('[class^="price-noaccent"]').textContent).replace(/\D/g, ''))
            }
        });

    // console.log(newAds);
    // var keys = Object.keys(newAds);
    // console.log(keys);
    // console.log(Object.values(newAds)[0].price);

    // New Array for prices of meter squared for each Ad from avito metro response
    var meterPriceList = [];

    // Variable for sum of meter squared prices from avito metro response
    var totalMeterPrice = 0;

    // Copy meter squared price for each app to meterPriceList
    for (var i = 0; i < itemsFound; i++) {
        meterPriceList[i] = Object.values(newAds)[i].priceMeter;
    }

    // Sum Ads total meter squared price
    for (var i = 0; i < meterPriceList.length; i++) {
        totalMeterPrice = totalMeterPrice + meterPriceList[i];
    }

    // Calculate average price for metetr squared
    var meterMarketPrice = totalMeterPrice/itemsFound;

    //console.log(meterPriceList);
    //console.log(totalMeterPrice);
    //console.log(meterMarketPrice);

// --> CHANGE 85 TO AREA VALUE FROM SERVER   
    // Calculate market price
    var marketPrice = meterMarketPrice*85;
    console.log(marketPrice);

  
  })();