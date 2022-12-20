// нумерация картинок
let i = 1;
for (let li of carousel.querySelectorAll('li')) {
    li.style.position = 'relative';
    li.insertAdjacentHTML('beforeend', `<span style="position:absolute;left:0;top:0">${i}</span>`);
    i++;
}

// конфигурация
let height = 130;
let count = 1;
let list = carousel.querySelector('ul');
let listElems= carousel.querySelectorAll('li');

let position = 0;

carousel.onwheel = function (e) {
    let deltaY = e.deltaY;
    if (deltaY > 0) {
        position -= height * count;
        position = Math.max(position, -height * (listElems.length - count));
        list.style.marginTop = position + 'px';
    } else if (deltaY < 0) {
        position += height * count;
        position = Math.min(position, 0);
        list.style.marginTop = position + 'px';
    }
};

carousel.querySelector('.prev').onclick = function () {
    position += height * count;
    position = Math.min(position, 0);
    list.style.marginTop = position + 'px';
};

carousel.querySelector('.next').onclick = function () {
    position -= height * count;
    position = Math.max(position, -height * (listElems.length - count));
    list.style.marginTop = position + 'px';
}