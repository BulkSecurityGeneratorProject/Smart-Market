(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('CartController', CartController);

    CartController.$inject = ['cart'];

    function CartController (cart) {
        var vm = this;
        vm.cart = cart;
    }
})();
