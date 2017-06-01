/**
 * Created by vitiv on 5/22/17.
 */
angular.module('articlesApp', [
    'ngResource',
    'articlesApp.controllers',
    'articlesApp.services',
    'ngRoute'
]).
config(['$routeProvider', '$resourceProvider', '$httpProvider', function($routeProvider, $resourceProvider, $httpProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
    $httpProvider.defaults.withCredentials = true;  // this is needed for CORS, in case the api runs on a different domain

    $routeProvider.
    when("/articles", {templateUrl: "templates/articles.html", controller: "articlesController as articlesCtrl", name: 'article-list'
    }).  // list view
    when("/articles/:id", {templateUrl: "templates/article.html", controller: "articleController as articleCtrl", name: 'article-detail'}).  // detail view
    when("/login", {templateUrl: "templates/login.html", controller: "loginController as loginCtrl", name: 'login'}).  // login view

    otherwise({redirectTo: '/articles'});  // default
}]);
