document.addEventListener('DOMContentLoaded', function () {

    // General methods
    //
    var charCount = document.querySelectorAll('.charcounter');
    M.CharacterCounter.init(charCount);

    // Main Nav
    //
    var elemsMainNav = document.querySelectorAll('#nav-main');
    var optionsMainNav = {
        edge: 'left',
        draggable: true,
        inDuration: 250,
        outDuration: 200,
        onOpenStart: null,
        onOpenEnd: null,
        onCloseStart: null,
        onCloseEnd: null,
        preventScrolling: true
    }
    var instances = M.Sidenav.init(elemsMainNav, optionsMainNav);

    // Account Nav
    //
    var elemsAccountNav = document.querySelectorAll('#nav-account');
    var optionsAccountNav = {
        edge: 'right',
        draggable: true,
        inDuration: 250,
        outDuration: 200,
        onOpenStart: null,
        onOpenEnd: null,
        onCloseStart: null,
        onCloseEnd: null,
        preventScrolling: true
    }
    var instances = M.Sidenav.init(elemsAccountNav, optionsAccountNav);

    // Home
    //
    var cEl = document.querySelectorAll('.carousel');
    var cOptions = {
        dist: 0,
        fullWidth: false,
        indicators: true
    }
    var instances = M.Carousel.init(cEl, cOptions);

});