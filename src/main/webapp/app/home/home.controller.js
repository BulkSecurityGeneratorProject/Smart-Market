(function() {
    'use strict';

    angular
        .module('smartmarketApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Market', 'AlertService', 'ParseLinks'];

    function HomeController ($scope, Principal, LoginService, $state, Market, AlertService, ParseLinks) {
        var vm = this;

        vm.marketPage = 0;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.markets = [];

        function loadAll () {
            Market.query({
                page: vm.marketPage,
                size: 999,
                sort: 'id,asc'
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.markets.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        loadAll();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
