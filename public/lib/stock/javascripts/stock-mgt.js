(function () {
    var app = angular.module('stockMgtApp', ['restangular', 'ui.bootstrap']);

    //config Restangular globally
    app.config(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/stock');
        //must-have: or else DELETE method will not work
        RestangularProvider.setDefaultHeaders({'Content-Type': 'application/json'});
    });

    app.service('stockMgtService', function (Restangular) {

        var baseStocks = Restangular.withConfig(function (config) {
            config.setRestangularFields({id: "code"})
        }).all('stocks');

        this.addStock = function (stock) {
            return baseStocks.post(stock);
        }

        this.stockList = function () {
            return baseStocks.getList();
        }

        this.deleteStock = function (stock) {
            return stock.remove();
        }

        this.updateStock = function(stock) {
            return stock.save();
        }
    })

    app.controller('stockMgtCtrl', function ($scope, $uibModal, stockMgtService) {

        $scope.stocks = [];

        $scope.openAddStockModal = function () {

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'addStockModal.html',
                controller: 'addStockModalCtrl',
                size: ''
            });

            modalInstance.result.then(function (newStock) {
                stockMgtService.addStock(newStock).then(function(){
                    $scope.refresh();
                })
            });
        };

        $scope.openEditStockModal = function(stock){
           var modalInstance = $uibModal.open({
               animation: true,
               templateUrl: 'editStockModal.html',
               controller: 'editStockModalCtrl',
               resolve: {
                   editing: stock
               }
           });

            modalInstance.result.then(function(stock){
                stockMgtService.updateStock(stock).then(function(){
                    $scope.refresh();
                })
            })
        };

        $scope.refresh = function () {
            stockMgtService.stockList().then(function (result) {
                $scope.stocks = result;
            })
        }

        $scope.deleteStock = function (stock) {
           stockMgtService.deleteStock(stock).then(function(){
               $scope.refresh();
           })
        }


        //init load all stocks
        $scope.refresh();
    });

    app.controller('addStockModalCtrl', function ($scope, $uibModalInstance) {

        $scope.stock = {};

        $scope.save = function () {
            $uibModalInstance.close($scope.stock)
        }

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        }
    });

    app.controller('editStockModalCtrl', function($scope, $uibModalInstance, editing) {
        $scope.stock = editing;

        $scope.save = function () {
            $uibModalInstance.close($scope.stock)
        }

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        }
    });

})();