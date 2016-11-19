(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'Market', 'Category'];

    function ProductDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, Market, Category) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartmarketApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
