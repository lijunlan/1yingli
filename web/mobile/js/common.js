$(function() {
    FastClick.attach(document.body);
});

function $_ToJson(st) {
    return eval('(' + st + ')');
}

function remove() {
    event.preventDefault();
}

var app = angular.module('app', ['ngRoute','ngTouch']);

app.filter('shortTime', function () {
    return function (inputString) {
        return inputString.split(' ')[0];
    }
});


app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    delete $httpProvider.defaults.headers.common["X-Requested-With"];
    $httpProvider.defaults.headers.common["Accept"] = "application/json";
    $httpProvider.defaults.headers.common["Content-Type"] = "application/json";
}]);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when("/main/:tid", {
            templateUrl: "pages/main/main.html",
            controller: "MainController"
        })
}]);