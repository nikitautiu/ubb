angular.module('restApp.controllers', []).
controller('logsController', function(logStore, $routeParams) {
    this.model = logStore.model;
    this.formModel = {text: '', type: '', severity: ''};
    this.loginModel = {username: '', password: ''};


    let intialize = () => {
        logStore.get({type: $routeParams.type, username: $routeParams.username, severity: $routeParams.severity});
        logStore.getUser();
    };

    this.remove = (log) => {
        logStore.delete(log);
    };

    this.add = () => {
      logStore.insert(this.formModel);
    };

    this.login = () => {
        logStore.login(this.loginModel)
    };

    this.logout = () => {
        logStore.logout();
    };

    intialize();
});