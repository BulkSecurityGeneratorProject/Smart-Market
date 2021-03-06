(function() {
    'use strict';
    angular
        .module('smartmarketApp')
        .factory('Market', Market);

    Market.$inject = ['$resource'];

    function Market ($resource) {
        var resourceUrl =  'api/markets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'product':{
                url: resourceUrl + '/products',
                method:'GET',
                isArray:true
            },
            'update': { method:'PUT' }
        });
    }
})();
