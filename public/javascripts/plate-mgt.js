(function () {
    var app = angular.module('plateMgtApp', ['restangular', 'ui.bootstrap']);

    //config Restangular globally
    app.config(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/rest');
        //must-have: or else DELETE method will not work
        RestangularProvider.setDefaultHeaders({'Content-Type': 'application/json'});
    });

    app.service('plateMgtService', function (Restangular) {

        var basePlates = Restangular.all('plates');

        this.addPlate = function (plate) {
            return basePlates.post(plate);
        }

        this.plateList = function () {
            return basePlates.getList();
        }

        this.deletePlate = function (plate) {
            return plate.remove();
        }

        this.updatePlate = function(plate) {
            return plate.save();
        }
    })

    app.controller('plateMgtCtrl', function ($scope, $uibModal, plateMgtService) {

        $scope.plates = [];

        $scope.openAddPlateModal = function () {

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'addPlateModal.html',
                controller: 'addPlateModalCtrl',
                size: ''
            });

            modalInstance.result.then(function (plate) {
                plateMgtService.addPlate(plate).then(function(){
                    $scope.refresh();
                })
            });
        };

        $scope.openEditPlateModal = function(plate){
           var modalInstance = $uibModal.open({
               animation: true,
               templateUrl: 'editPlateModal.html',
               controller: 'editPlateModalCtrl',
               resolve: {
                   editing: plate
               }
           });

            modalInstance.result.then(function(plate){
                plateMgtService.updatePlate(plate).then(function(){
                    $scope.refresh();
                })
            })
        };

        $scope.refresh = function () {
            plateMgtService.plateList().then(function (result) {
                $scope.plates = result;
            })
        }

        $scope.deletePlate = function (plate) {
            plateMgtService.deletePlate(plate).then(function(){
               $scope.refresh();
           })
        }


        //init load all plates
        $scope.refresh();
    });

    app.controller('addPlateModalCtrl', function ($scope, $uibModalInstance) {

        $scope.plate = {};

        $scope.save = function () {
            $uibModalInstance.close($scope.plate)
        }

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        }
    });

    app.controller('editPlateModalCtrl', function($scope, $uibModalInstance, editing) {
        $scope.plate = editing;

        $scope.save = function () {
            $uibModalInstance.close($scope.plate)
        }

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        }
    });

})();