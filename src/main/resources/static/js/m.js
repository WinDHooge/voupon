document.addEventListener('DOMContentLoaded', function () {

    // General methods
    //

    // Charcounter elements
    var charCount = document.querySelectorAll('.charcounter');
    M.CharacterCounter.init(charCount);

    // Fix for thymeleaf switcher checkbox fields
    var checkboxField = document.querySelectorAll('input[type="checkbox"]');
    var hiddenCheckboxField = document.querySelectorAll('input[type="checkbox"] + input[type="hidden"]');
    if(checkboxField.length > 0 && hiddenCheckboxField.length > 0){
        for(var i=0;i < checkboxField.length;i++){
           checkboxField[i].parentNode.insertBefore(hiddenCheckboxField[i], checkboxField[i]);
        }
    }

    // Datepicker fields
    var dateElems = document.querySelectorAll('.datepicker');
    var dateOptions = {format:"yyyy-mm-dd"};
    M.Datepicker.init(dateElems, dateOptions);


    // Logout methods
    //
    var logoutLink = document.querySelector('#logoutLink');
    if(logoutLink != null) {
        logoutLink.addEventListener('click', evt => {
            evt.preventDefault();
            document.logoutForm.submit();
        });
    }

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

    // Signup form methods
    //
    var pass = document.querySelector('#signupform #password');
    var cpass = document.querySelector('#signupform #confirmpassword');
    if(cpass != null) {
        cpass.addEventListener('focusout', (event) => {
            if(cpass.value != null && cpass.value != pass.value){
                cpass.classList.remove("valid");
                cpass.classList.add("invalid");
            }
        });
    }

});