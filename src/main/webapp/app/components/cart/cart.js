(function(){
    'use strict';

    angular
        .module('smartmarketApp')
        .service('cart', cart);

    cart.$inject = ['$localStorage', 'AlertService', 'toaster'];
    function cart($localStorage, AlertService, toaster) {
        var currentCard = $localStorage.card;
        if(!currentCard) currentCard = [];

        this.add = function (item, quantity) {
            toaster.pop('success', '', 'Product added to you card');
            currentCard.push({'item' : item, 'quantity' : quantity});
            save();
            return currentCard;
        };

        this.remove = function (index) {
            toaster.pop('success', '', 'Product removed from you card');
            currentCard.splice(index, 1);
            save();
            return currentCard;
        };

        this.get = function () {
            return currentCard;
        }
        function save() {
            $localStorage.card = currentCard;

        }
    }

})();
