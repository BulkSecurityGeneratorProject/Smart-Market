(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('cart', {
            parent: 'app',
            url: '/cart',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cart'
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/cart/cart.html',
                    controller: 'CartController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();


