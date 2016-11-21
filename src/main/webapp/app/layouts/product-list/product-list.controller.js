(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('ProductListController', ProductListController);

    ProductListController.$inject = ['$timeout', '$scope', 'entity', 'Market', 'ParseLinks', 'cart'];

    function ProductListController ($timeout, $scope, entity, Market, ParseLinks, cart) {
        var vm = this;

        vm.market = entity;
        vm.products = [];
        vm.addToCard = addToCard;

        Market.product({id: entity.id}, function(data) {
            vm.products = data;
        });

        function addToCard(item, quantity) {
            cart.add(item, quantity);

        }

    }
})();
