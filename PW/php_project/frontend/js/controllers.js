/**
 * Created by vitiv on 5/22/17.
 */
angular.module('articlesApp.controllers', []).
controller('articlesController', function($scope, articlesAPI) {
    articlesAPI.getArticles().then(
        function(result) {
            $scope.articles = result;
        });
}).
controller('articleController', function($scope, $routeParams, articlesAPI) {
    $scope.id = parseInt($routeParams.id);

    // set the article async
    articlesAPI.getArticle($scope.id).then(
        function(result) {
            $scope.article = result;
        });
});