angular.module('restApp.services', []).factory('logStore', function ($http, $resource, $q) {
    let logStore = {};
    logStore.api = {
        log: $resource(apiEndpoint + 'logs/:id', {id: '@id'}, {
            list: {params: {id: null}, method: 'GET', isArray: true}
        }),

        user: $resource(apiEndpoint + 'users/me/', {}, {
            login:  {method: 'PUT'},
            logout: {method: 'DELETE'},
            get:    {method: 'GET'}
        })
    };

    logStore.model = {
        logs: [],
        username: null
    };

    logStore.get = function(query) {
        return logStore.api.log.list(query,
            (result) => {
                angular.copy(result, logStore.model.logs);
            }
        ).$promise;
    };

    logStore.getUser = function() {
        return logStore.api.user.get(
            (result) => {
                logStore.model.username = result.username;
            }
        ).$promise;
    };

    logStore.login = function(user) {
        return logStore.api.user.login(user,
            (result) => {
                logStore.model.username = result.username;
            },
            () => {}
        ).$promise;
    };

    logStore.logout = function() {
        return logStore.api.user.logout(
            (result) => {
                logStore.model.username = null;
            }
        ).$promise;
    };


    logStore.insert = function(log) {
        return logStore.api.log.save(log,
            (result) => {
                logStore.model.logs.push(result);
            }
        ).$promise;
    };

    logStore.delete = function(log) {
        return logStore.api.log.delete({ id: log.id },
            () => {
                let pos = logStore.model.logs.findIndex((elem) => elem.id === log.id);
                logStore.model.logs.splice(pos, 1);
            }
        ).$promise;
    };



    return logStore;
});
