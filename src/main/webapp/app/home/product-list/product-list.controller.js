(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('ProductListController', ProductListController);

    ProductListController.$inject = ['$timeout', '$scope', 'entity', 'Market', 'ParseLinks'];

    function ProductListController ($timeout, $scope, entity, Market, ParseLinks) {
        var vm = this;

        vm.market = entity;
        vm.products = [];
        vm.save = save;

        Market.product({id: entity.id}, function(data) {
            vm.products = data;
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.market.id !== null) {
                Market.update(vm.market, onSaveSuccess, onSaveError);
            } else {
                Market.save(vm.market, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartmarketApp:marketUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
