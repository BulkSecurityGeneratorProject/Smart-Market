(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('MarketDialogController', MarketDialogController);

    MarketDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Market', 'User'];

    function MarketDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Market, User) {
        var vm = this;

        vm.market = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

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
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
