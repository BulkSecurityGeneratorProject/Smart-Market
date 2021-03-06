(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Category', 'Product'];

    function CategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, Category, Product) {
        var vm = this;

        vm.category = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartmarketApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
