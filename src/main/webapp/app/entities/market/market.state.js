(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('market', {
            parent: 'entity',
            url: '/market',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Markets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/market/markets.html',
                    controller: 'MarketController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('market-detail', {
            parent: 'entity',
            url: '/market/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Market'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/market/market-detail.html',
                    controller: 'MarketDetailController',
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
        })
        .state('market-detail.edit', {
            parent: 'market-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market/market-dialog.html',
                    controller: 'MarketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Market', function(Market) {
                            return Market.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('market.new', {
            parent: 'market',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market/market-dialog.html',
                    controller: 'MarketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                cnpj: null,
                                street: null,
                                number: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('market', null, { reload: 'market' });
                }, function() {
                    $state.go('market');
                });
            }]
        })
        .state('market.edit', {
            parent: 'market',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market/market-dialog.html',
                    controller: 'MarketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Market', function(Market) {
                            return Market.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('market', null, { reload: 'market' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('market.delete', {
            parent: 'market',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market/market-delete-dialog.html',
                    controller: 'MarketDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Market', function(Market) {
                            return Market.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('market', null, { reload: 'market' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
