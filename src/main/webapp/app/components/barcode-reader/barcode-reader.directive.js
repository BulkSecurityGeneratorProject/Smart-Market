(function() {
    'use strict';

    var barcodeReader = function() {

        return {
            templateUrl: 'app/components/barcode-reader/barcode-reader.html',
            controller: barcodeReaderController,
            restrict: 'E',
            scope: {
                code: '='
            }
        }
    };

    angular
        .module('smartmarketApp')
        .directive('barcodeReader', barcodeReader);

    barcodeReaderController.$inject = ['$scope'];

    function barcodeReaderController($scope) {

        console.log($scope.code);

        $scope.use = use;
        $scope.picture = picture;
        $scope.use = use;
        $scope.close = close;

        var resultCollector = Quagga.ResultCollector.create({
            capture: true,
            capacity: 20,
            blacklist: [{ code: "2167361334", format: "i2of5" }],
            filter: function(codeResult) {
                // only store results which match this constraint
                // e.g.: codeResult
                return true;
            }
        });

        var state = {
            inputStream: {
                type: "LiveStream",
                constraints: {
                    width: { min: 640 },
                    height: { min: 480 },
                    facingMode: "environment",
                    aspectRatio: { min: 1, max: 2 }
                }
            },
            locator: {
                patchSize: "medium",
                halfSample: true
            },
            numOfWorkers: 4,
            decoder: {
                readers: [{
                    format: "ean_reader",
                    config: {}
                }, {
                    format: "ean_8_reader",
                    config: {}
                }]
            },
            locate: true
        };


        function use() {
            $scope.showLive = true;



            // Wait the element rederization
            setTimeout(function() {
                Quagga.init({
                    inputStream: {
                        name: "Live",
                        type: "LiveStream",
                        target: document.querySelector('#interactive') // Or '#yourElement' (optional)
                    },
                    decoder: {
                        readers: ["ean_reader"]
                    }
                }, function(err) {
                    if (err) {
                        console.log(err);
                        return
                    }
                    console.log("Initialization finished. Ready to start");
                    Quagga.start();
                    setOnDetect();
                });
            }, 500);
        }


        function setOnDetect() {
            Quagga.onDetected(function(result) {
                $scope.$apply(function() {
                    $scope.code = result.codeResult.code;
                    close();
                });
                console.log(result.codeResult.code);
            });
        }

        function picture() {

        }

        function handleError(err) {
            console.log(err);
        }

        function close() {
            $scope.showLive = false;
            Quagga.stop();
        }
    }
})();
