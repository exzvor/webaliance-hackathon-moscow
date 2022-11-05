const tablePopUpBtn = document.querySelector('.table-btn');
console.log(tablePopUpBtn)
const tablePopUp = document.querySelector('.table__pop-up');
tablePopUpBtn.addEventListener('click', function(){
    tablePopUp.classList.toggle('active');
    tablePopUpBtn.classList.toggle('active');
    document.querySelector('body').classList.toggle('lock');
    if (!tablePopUp.classList.contains('active')){
        window.scroll({
            top: 0, 
            left: 0, 
            behavior: 'smooth' 
        });
    }
});
const mapBlock = document.querySelector('#map');
const header = document.querySelector('.header');

const inputFile = document.querySelector('.input__file');
inputFile.addEventListener('change', function(){
    modal.classList.add('hidden');
    overlay.classList.add('hidden');
    document.querySelector('body').classList.add('lock');

})

window.addEventListener('resize', init);

function init(){
    let topProperty = window.innerHeight/2  - header.clientHeight ;
    tablePopUpBtn.style.top = topProperty +'px';
    let mapHeight = (window.screen.availHeight - header.clientHeight);
    mapBlock.style.height = mapHeight + 'px';
    mapBlock.style.aspectRatio = window.screen.availWidth + '/' + window.screen.availHeight;

    if (!document.querySelector('.modal').classList.contains('hidden')){
        document.querySelector('body').classList.remove('lock');

    }
}

init();

