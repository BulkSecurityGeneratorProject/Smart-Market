(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home-market', {
            parent: 'entity',
            url: '/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Market'
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/product-list/product-list.html',
                    controller: 'ProductListController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Market', function($stateParams, Market) {
                    return Market.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'market',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }
})();


