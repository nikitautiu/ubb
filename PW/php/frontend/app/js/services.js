angular.module('articlesApp.services', []).factory('articlesAPI', function ($http, $injector) {

    // check whether there is an open api
    // if not get via the injector, soem dummy data
    if (useAPI)
        return $injector.get('API');
    return $injector.get('dummy');

}).factory('API', function ($http) {
    let articlesAPI = {};

    articlesAPI.getArticle = function (id) {

        return $http({
            method: 'JSONP',
            url: 'http://ergast.com/api/f1/2013/driverStandings.json?callback=JSON_CALLBACK'
        });
    };

    return articlesAPI;
}).factory('dummy', function ($q, $window) {
    let articlesAPI = {};

    const _dummyData = {
        articles: [
            {
                id: 1,
                title: "Articol 1",
                content: "Lorem ipsum",
                comments: [
                    {author: "Gelu", email: "gelu@yahoo.com", content: "Da", moderated: true},
                    {author: "Andrei", email: "xXxSephirothxXx@hotmail.com", content: "daa", moderated: false},
                    {author: "Gigi", email: "manu@gmail.com", content: "Daasdasd", moderated: true}

                ]
            },
            {
                id: 2,
                title: "Articol 2",
                content: "Lorem ipsum dolor",
                comments: [
                    {author: "Relu", email: "relu@relumal.com", content: "Nu", moderated: true},
                    {author: "Gica", email: "gica@relumal.com", content: "Poate", moderated: true}
                ]
            }
        ],
        users: [
            {username: "nicu", password: "parola1"},
            {username: "misu", password: "pass"}
        ]
    };

    let _getFromLocalStorage = function () {
        // fallback to the implicit data if none present int he storage
        return JSON.parse($window.localStorage.getItem(STORAGE_ID) || JSON.stringify(_dummyData));
    };

    let _saveToLocalStorage = function (todos) {
        $window.localStorage.setItem(STORAGE_ID, JSON.stringify(todos));
    };

    articlesAPI.getArticle = function (id) {
        let deferred = $q.defer();
        let article = _getFromLocalStorage()['articles'].filter(function (article) {
            return article.id === id;
        })[0];

        if(($window.sessionStorage.getItem("article-app-user") || "") === "")
            article.comments = article.comments.filter(comment => comment.moderated);

        deferred.resolve(article);

        return deferred.promise; // must return a promise. async stuff
    };

    articlesAPI.getArticles = function () {
        let deferred = $q.defer(); // must be returned async
        deferred.resolve(_getFromLocalStorage()['articles']);
        return deferred.promise;
    };

    articlesAPI.login = function(username, password) {
        let user = _getFromLocalStorage()["users"].find(x => x.username === username);
        let deferred = $q.defer();
        deferred.resolve(user !== undefined && user.password === password);

        $window.sessionStorage.setItem(userStorageKey, username);

        return deferred.promise;
    };

    articlesAPI.logout = function() {
        let deferred = $q.defer();
        deferred.resolve(true);
        $window.sessionStorage.removeItem(userStorageKey);

        return deferred.promise;
    };

    return articlesAPI;
});