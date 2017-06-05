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
    when("/artists", {templateUrl: "templates/artist_list.html", controller: "artistsController as artistsCtrl", name: 'artists-list'
    }).  // list view
    otherwise({redirectTo: '/artists'});  // default
}]);
