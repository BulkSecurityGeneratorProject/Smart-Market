(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('MarketListController', MarketListController);

    MarketListController.$inject = ['entity'];

    function MarketListController (entity) {
        var vm = this;
        vm.markets = entity;
    }
})();
