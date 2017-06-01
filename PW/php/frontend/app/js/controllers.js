/**
 * Created by vitiv on 5/22/17.
 */
angular.module('articlesApp.controllers', []).
controller('articlesController', function(articlesAPI) {
    this.articles = [];

    let intialize = () => {
        articlesAPI.getArticles().then(
            (result) => {
                this.articles = result;
            });
    };
    intialize();
}).
controller('articleController', function($routeParams, articlesAPI) {
    this.id = parseInt($routeParams.id);
    this.article = {};
    this.commInput = {};


    intialize = () => {
        // set the article async
        articlesAPI.getArticle(this.id).then(
            (result) => {
                this.article = result;
            });
    };

    this.moderate = (comment) => {
        articlesAPI.moderateComment(comment.id).then(
            () => {
                comment.moderated = true;
            }
        );
    };

    this.submitComment = () => {
        let comment = this.commInput;
        let id = this.id;
        articlesAPI.addComment(id, comment).then((commResult) => {
            if(articlesAPI.model.loginName !== null)
                this.article.comments.push(commResult); // append the returned comment
        });
    };

    intialize();
}).
controller('loginController', function($location, articlesAPI) {
    this.model = articlesAPI.model;
    this.login = {username: "", password: ""};

    this.submitLogin = () => {
        let loginUser = this.login.username;
        articlesAPI.login(this.login.username, this.login.password).then(success => {
            if(success) {
                this.loginName = loginUser; // to avoid rewrite
            }
            else {
                this.loginForm.$setValidity("usernameOrPassword", false);
            }
        });
    };

    this.submitLogout = () => {
        articlesAPI.logout().then((result) => {
            this.loginName = null;
        });
    };
}).
controller('welcomeController', function($window) {
    this.curretUser = $window.sessionStorage.getItem(userStorageKey);
}).
controller('headerController', function(articlesAPI) {
    this.model = articlesAPI.model;
    articlesAPI.getLoginName().then( function() {});
});