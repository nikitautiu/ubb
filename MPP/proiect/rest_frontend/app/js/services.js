angular.module('restApp.services', []).factory('artistsStore', function ($http, $resource, $q) {
    let artistsStore = {};
    artistsStore.api = {
        artist: $resource(apiEndpoint + 'artists/:id', {id: '@id'}, {
            list:   {params: {id: null}, method: 'GET', isArray: true},
            detail: {method: 'GET'},
            update: { method:'PUT' }  // needed coz ngResource doesn't know how to update out-of-the-box
        })
    };

    artistsStore.model = {
        artists: []
    };

    artistsStore.get = function() {
        return artistsStore.api.artist.list(
            (result) => {
                angular.copy(result, artistsStore.model.artists);
            }
        ).$promise;
    };

    artistsStore.insert = function(artist) {
        return artistsStore.api.artist.save(artist,
            (result) => {
                artistsStore.model.artists.push(result);
            }
        ).$promise;
    };

    artistsStore.delete = function(artist) {
        return artistsStore.api.artist.delete({ id: artist.id },
            () => {
                let pos = artistsStore.model.artists.findIndex((elem) => elem.id === artist.id);
                artistsStore.model.artists.splice(pos, 1);
            }
        ).$promise;
    };

    artistsStore.modify = function(artist) {
        return artistsStore.api.artist.update({ id: artist.id }, artist).$promise;
    };

    return artistsStore;
});
