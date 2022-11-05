




const tablePopUpBtn = document.querySelector('.table-btn');
console.log(tablePopUpBtn)
const tablePopUp = document.querySelector('.table__pop-up');
tablePopUpBtn.addEventListener('click', function(){
    tablePopUp.classList.toggle('active');
    tablePopUpBtn.classList.toggle('active');
});
const mapBlock = document.querySelector('#map');
const header = document.querySelector('.header');

window.addEventListener('resize', init);

function init(){
    let topProperty = window.innerHeight/2  - header.clientHeight ;
    tablePopUpBtn.style.top = topProperty +'px';
    let mapHeight = (window.screen.availHeight - header.clientHeight);
    mapBlock.style.height = mapHeight + 'px';
    mapBlock.style.aspectRatio = window.screen.availWidth + '/' + window.screen.availHeight
}

init();

