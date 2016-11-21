(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home-market-list', {
            parent: 'entity',
            url: '/market-list',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Market List'
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/market-list/market-list.html',
                    controller: 'MarketListController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Market', function($stateParams, Market) {
                    return Market.query({}).$promise;
                }]
            }
        });
    }
})();


