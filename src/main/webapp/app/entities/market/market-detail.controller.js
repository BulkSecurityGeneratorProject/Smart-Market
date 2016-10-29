(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('MarketDetailController', MarketDetailController);

    MarketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Market', 'User'];

    function MarketDetailController($scope, $rootScope, $stateParams, previousState, entity, Market, User) {
        var vm = this;

        vm.market = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartmarketApp:marketUpdate', function(event, result) {
            vm.market = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
