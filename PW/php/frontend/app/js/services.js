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
                    {id: 1, author: "Gelu", email: "gelu@yahoo.com", content: "Da", moderated: false},
                    {id: 2, author: "Andrei", email: "xXxSephirothxXx@hotmail.com", content: "daa", moderated: false},
                    {id: 3, author: "Gigi", email: "manu@gmail.com", content: "Daasdasd", moderated: false}

                ]
            },
            {
                id: 2,
                title: "Articol 2",
                content: "Lorem ipsum dolor",
                comments: [
                    {id: 4, author: "Relu", email: "relu@relumal.com", content: "Nu", moderated: true},
                    {id: 5, author: "Gica", email: "gica@relumal.com", content: "Poate", moderated: true}
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

    articlesAPI.model = {
        loginName: null
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
        if(user !== undefined && user.password === password) {
            $window.sessionStorage.setItem(userStorageKey, username);
            articlesAPI.model.loginName = username;
        }

        return deferred.promise;
    };

    articlesAPI.logout = function() {
        let deferred = $q.defer();
        $window.sessionStorage.removeItem(userStorageKey);
        deferred.resolve();
        articlesAPI.model.loginName = null;

        return deferred.promise;
    };

    articlesAPI.getLoginName = function () {
        let deferred = $q.defer();
        articlesAPI.model.loginName = $window.sessionStorage.getItem(userStorageKey);
        deferred.resolve($window.sessionStorage.getItem(userStorageKey));
        return deferred.promise;
    };

    articlesAPI.moderateComment = function(commId) {
        let deferred = $q.defer();
        let storage = _getFromLocalStorage();
        for(let article of storage['articles'])
            for(let comm of article.comments)
                if(comm.id === commId) {
                    if(!comm.moderated) {
                        comm.moderated = true;
                        _saveToLocalStorage(storage);
                        deferred.resolve();
                        return deferred.promise;

                    }
                    else {
                        deferred.reject({error: "comment already moderated"});
                        return deferred.promise;
                    }
                }
    };

    articlesAPI.addComment = function(articleId, comm) {
        let deferred = $q.defer();
        let storage = _getFromLocalStorage();

        let foundArticle =  null;
        for(let article of storage['articles'])
            if(article.id === articleId)
                foundArticle = article;

        if(foundArticle === null) {
            deferred.reject({error: "article doesn't exist"});
            return deferred.promise;
        }

        // validation
        if(comm.author.trim() === "") {
            deferred.reject({error: "author cannot be blank"});
            return deferred.promise;
        }
        if(comm.email.trim() === "") {
            deferred.reject({error: "email cannot be blank"});
            return deferred.promise;
        }
        if(comm.content.trim() === "") {
            deferred.reject({error: "content cannot be blank"});
            return deferred.promise;
        }


        // get max id
        let maxInd = -1;
        for(let article of storage['articles'])
            for(let comm of article.comments)
                maxInd = Math.max(maxInd, comm.id);

        comm.id = maxInd + 1;
        foundArticle.comments.push(comm);
        _saveToLocalStorage(storage);
        deferred.resolve(comm);

        return deferred.promise;
    };

    return articlesAPI;
});