/**
 * Created by vitiv on 5/22/17.
 */
angular.module('articlesApp', [
    'articlesApp.controllers',
    'articlesApp.services',
    'ngRoute'
]).
config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    when("/articles", {templateUrl: "templates/articles.html", controller: "articlesController", name: 'article-list'
    }).  // list view
    when("/articles/:id", {templateUrl: "templates/article.html", controller: "articleController", name: 'article-detail'}).  // detail view
    otherwise({redirectTo: '/articles'});  // default
}]);
