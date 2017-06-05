angular.module('restApp.controllers', []).
controller('artistsController', function(artistsStore) {
    this.model = artistsStore.model;
    this.formModel = {name: ''};

    let intialize = () =>
        artistsStore.get();

    this.remove = (artist) => {
        artistsStore.delete(artist);
    };

    this.add = () => {
      artistsStore.insert(this.formModel);
    };

    this.update = (artist) => {
       artistsStore.modify(artist);
    } ;


    intialize();
});