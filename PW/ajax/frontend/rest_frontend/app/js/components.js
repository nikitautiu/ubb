angular.module('restApp').component('listItem', {
    templateUrl: 'templates/log_item.html',
    bindings: {
        log: '<',
        user: '<',
        onEdit: '&',
        onDelete: '&'
    },
    controller: function() {
        this.value = "";

        this.$onInit = () => {
            this.value = this.log.name;
        };

    }
});