

angular.module('articlesApp.services', []).
factory('articlesAPI', function ($http, $injector) {

    // check whether there is an open api
    // if not get via the injector, soem dummy data
    if(useAPI)
        return $injector.get('API');
    return $injector.get('dummy');

}).factory('API', function ($http) {
    var articlesAPI = {};

    articlesAPI.getArticle = function (id) {

        return $http({
            method: 'JSONP',
            url: 'http://ergast.com/api/f1/2013/driverStandings.json?callback=JSON_CALLBACK'
        });
    };

    return articlesAPI;
}).factory('dummy', function ($q) {
    var articlesAPI = {};

    articlesAPI.getArticle = function (id) {
        var deferred = $q.defer();

        switch (id) {
            case 1:
                deferred.resolve({
                    title: "Articol 1",
                    content: "Lorem ipsum",
                    comments: [
                        {author: "Gelu", content: "Da", moderated: true},
                        {author: "Andrei", content: "daa", moderated: true},
                        {author: "Gigi", content: "Daasdasd", moderated: true}

                    ]
                });
                break;
            case 2:
                deferred.resolve({
                    title: "Articol 2",
                    content: "Lorem ipsum dolor",
                    comments: [
                        {author: "Relu", content: "Nu", moderated: true},
                        {author: "Gica", content: "Poate", moderated: true}
                    ]

                });
                break;
            default:
                deferred.resolve(null);
                break;
        }
        return deferred.promise; // must return a promise. async stff
    };

    articlesAPI.getArticles = function () {
        var deferred = $q.defer(); // must be returned async
        deferred.resolve(
            [{
                id: 1,
                title: "Articol 1",
                content: "Lorem ipsum"
            },
            {
                id: 2,
                title: "Articol 2",
                content: "Lorem ipsum dolor"
            }]);
        return deferred.promise;
    };

    return articlesAPI;
});