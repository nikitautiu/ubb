angular.module('restApp').component('listItem', {
    templateUrl: 'templates/artist_item.html',
    bindings: {
        artist: '<',
        onEdit: '&',
        onDelete: '&'
    },
    controller: function() {
        this.value = "";

        this.$onInit = () => {
            this.value = this.artist.name;
        };

        this.update = () => {
            this.artist.name = this.value;
            this.onEdit({value: this.artist});
        };
    }
});