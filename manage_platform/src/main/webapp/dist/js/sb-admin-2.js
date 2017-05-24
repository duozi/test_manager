/*!
 * Start Bootstrap - SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
 * Copyright 2013-2016 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE)
 */
$(function() {
    $('#side-menu').metisMenu();
});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        var topOffset = 50;
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var originurl = url.origin;
    var path = url.pathname;
    var pathurl = originurl + path;
    // var element = $('ul.nav a').filter(function() {
    //     return this.href == url;
    // }).addClass('active').parent().parent().addClass('in').parent();
    var element = $('ul.nav a').filter(function() {
        var herfurl = this.href;
        if(path=="/autotest/report/report_detail" && herfurl==originurl +"/autotest/report/report_list"){
                return true;
        }
        if(path=="/performance/report/report_detail" && herfurl==originurl +"/performance/report/report_list"){
            return true;
        }
        if(path=="/performance/report/jmeter_log" && herfurl==originurl +"/performance/report/report_list"){
            return true;
        }

        if(path=="/autotest/case/case_item_single" && herfurl==originurl +"/autotest/case/case_item"){
            return true;
        }
        if(path=="/autotest/case/case_item_multiple" && herfurl==originurl +"/autotest/case/case_item"){
            return true;
        }
         return herfurl == pathurl;

    }).addClass('active').parent();

    while (true) {
        if (element.is('li')) {
            element = element.parent().addClass('in').parent();
        } else {
            break;
        }
    }
});
