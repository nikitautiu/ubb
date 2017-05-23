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
}).
controller('loginController', function($scope, $location, articlesAPI) {
    $scope.login = {username: "", password: ""};

    $scope.submit = function() {
        articlesAPI.login($scope.login.username, $scope.login.password).then(success => {
            if(success) {
                $scope.loginForm.$setValidity("usernameOrPassword", true);
            }
            else {
                $scope.loginForm.$setValidity("usernameOrPassword", false);
            }
        });
    };
}).
controller('welcomeController', function($window, $scope) {
    $scope.curretUser = $window.sessionStorage.getItem(userStorageKey);
});