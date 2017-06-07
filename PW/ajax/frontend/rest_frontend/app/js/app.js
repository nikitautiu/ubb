angular.module('restApp', [
    'ngResource',
    'restApp.controllers',
    'restApp.services',
    'ngRoute'
]).
config(['$routeProvider', '$resourceProvider', '$httpProvider', function($routeProvider, $resourceProvider, $httpProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
    $httpProvider.defaults.withCredentials = true;  // this is needed for CORS, in case the api runs on a different domain

    $routeProvider.
    when("/logs", {templateUrl: "templates/log_list.html", controller: "logsController as logsCtrl", name: 'logs-list'
    }).  // list view
    otherwise({redirectTo: '/logs'});  // default
}]);
