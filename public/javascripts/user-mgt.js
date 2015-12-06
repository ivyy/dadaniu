(function () {
    var app = angular.module('userMgtApp', ['ui.bootstrap', 'smart-table']);

    app.service('userService', ['$http', function ($http) {

        //return a page of users
        this.pagingList = function (paging) {
            return $http({
                method: 'POST',
                url: '/users/paging',
                data: paging
            })
        };

        this.addUser = function (user) {
            return $http.post('/users', user)
        };

        this.userList = function () {
            return $http.get('/users')
        };

        this.deleteUser = function (userId) {
            return $http.delete('/users/' + userId)
        }

        this.updateUser = function (user) {
            return $http.put('/users/' + user.id, user)
        }
    }]);

    app.controller('userMgtCtrl', function ($scope, $modal, $log, userService) {
        $scope.users = [];
        $scope.displayed = [].concat($scope.users);

        //paging parameters
        $scope.paging = {
            currentPage : 1,
            itemsPerPage: 20,
            totalItems: 0
        }

        $scope.reloadUserList = function () {
            var start = ($scope.currentPage - 1) * $scope.itemsPerPage
            userService.pagingList($scope.paging).success(function (result) {
                $scope.users = result.data;
                $scope.paging.totalItems = result.paging.totalItems;
            });
        }

        $scope.openEditUserModal = function (user) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'editUserModal.html',
                controller: 'editUserModalCtrl',
                size: '',
                resolve: {
                    user: user
                }
            });

            modalInstance.result.then(function (user) {
                userService.updateUser(user).then(function () {
                    $scope.reloadUserList();
                })
            })
        }

        $scope.openAddUserModal = function () {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'addUserModal.html',
                controller: 'addUserModalCtrl',
                size: '',
                resolve: {
                    user: {}
                }
            });

            modalInstance.result.then(function (user) {
                $scope.reloadUserList();
            });
        }

        $scope.deleteUser = function (userId) {
            userService.deleteUser(userId).success(function () {
                $scope.reloadUserList();
            }).error(function () {
                $log('cannot delete user with id ' + userId)
            });
        }

        //load users list after initialized
        $scope.reloadUserList();

        //watch currentPage value change
        $scope.$watch(function (scope) {
            return scope.paging.currentPage;
        }, function (newValue, oldValue) {
            $scope.reloadUserList()
        })

        //watch itemsPerpage value change
        $scope.$watch(function (scope) {
            return scope.paging.itemsPerPage;
        }, function (newValue, oldValue) {
            $scope.reloadUserList()
        })

    });

    app.controller('addUserModalCtrl', function ($http, $scope, $modalInstance, userService, user) {

        $scope.user = user;

        $scope.save = function () {
            userService.addUser($scope.user).success(function (user) {
                $modalInstance.close(user);
            });
        }

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel')
        }
    });

    app.controller('editUserModalCtrl', function ($http, $scope, $modalInstance, userService, user) {

        $scope.user = user;

        $scope.save = function () {
            userService.updateUser($scope.user).success(function (user) {
                $modalInstance.close(user);
            });
        }

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel')
        }
    });

    app.controller('pagingCtrl', function ($scope, $log, userService) {

        $scope.setPage = function (pageNo) {
            $scope.paging.currentPage = pageNo;
        }

    });

    //a plugin for smart table, this should be
    //a common feature and put in a single js file
    app.directive('csSelect', function () {
        return {
            require: '^stTable',
            template: '<input type="checkbox"/>',
            scope: {
                row: '=csSelect'
            },
            link: function (scope, element, attr, ctrl) {

                element.bind('change', function (evt) {
                    scope.$apply(function () {
                        ctrl.select(scope.row, 'multiple');
                    });
                });

                scope.$watch('row.isSelected', function (newValue, oldValue) {
                    if (newValue === true) {
                        element.parent().addClass('st-selected');
                    } else {
                        element.parent().removeClass('st-selected');
                    }
                });
            }
        };
    });
})();