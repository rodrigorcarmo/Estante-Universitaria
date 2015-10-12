estanteApp.controller('HeaderController', ['$cookies', '$scope', '$location', function ($cookies, $scope, $location) {
        $scope.checkIfLogged = function () {
            var idUsuario = -1;

            if (typeof $cookies.getObject('idUsuario') != "undefined") {
                idUsuario = $cookies.getObject('idUsuario');
            }
            if (idUsuario > 0)
                return true;
            else
                return false;
        };

        $scope.logout = function () {
            $cookies.remove('idUsuario');
            $location.path('/home');
        }

        $scope.isActive = function (viewLocation) {
            if ($location.path() === '/home') {
                $('#main').removeClass('main');
                $('#main').addClass('main-home');
            }
            else {
                $('#main').addClass('main');
                $('#main').removeClass('main-home');
            }
            return viewLocation === $location.path();
        };
    }]);

estanteApp.controller('LoginController', ['$cookies', '$scope', '$location', '$http', function ($cookies, $scope, $location, $http) {

        $scope.go = function (path) {
            $location.path(path);
        };

        $scope.validateUser = function () {
            $http({
                method: 'POST',
                url: '/UserController',
                data: $.param($scope.login),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                if (data.data < 0) {
                    alert("Usuário não encontrado");
                    $cookies.put('idUsuario', -1);
                }
                else {
                    $cookies.put('idUsuario', data.data);
                    $location.path('/anuncios');
                }
            });
        };
    }]);

estanteApp.controller('RegisterController', ['$scope', '$location', '$http', function ($scope, $location, $http) {
        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.href = "#home";
        });
        $scope.insertUser = function () {
            $http({
                method: 'POST',
                url: '/UserController',
                data: $.param($scope.user),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                alert("Cadastrou!");
                 $('#sucess-modal').modal('show');
            });
        };
    }]);

estanteApp.controller('UpdateController', ['$cookies', '$scope', '$location', '$http', function ($cookies, $scope, $location, $http) {
        $scope.updateUser = function () {
            var idUsuario = $cookies.getObject('idUsuario');
            var data = JSON.stringify($scope.user);
            data = data.replace('}', ', "id":"' + idUsuario + '"}');
            data = $.parseJSON(data);
            $http({
                method: 'POST',
                url: '/UserController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                alert("Alteraçõs salvas com sucesso!");
            });
        };
    }]);

estanteApp.controller('BookController', ['$cookies', '$scope', '$location', '$http', function ($cookies, $scope, $location, $http) {
        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.href = "#livros";
        });
        $scope.insertBook = function () {
            var idUsuario = $cookies.getObject('idUsuario');
            var data = JSON.stringify($scope.book);
            data = data.replace('}', ', "idUsuario":"' + idUsuario + '"}');
            data = $.parseJSON(data);
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                 $('#sucess-modal').modal('show');
            });
        };

    }]);

estanteApp.controller('GetBooksController', ['$cookies','$scope','$modal', '$location', '$http', function ($cookies, $scope, $modal, $location, $http) {
        $scope.openModal = function (_idLivro) {
            $cookies.put('idLivroDelete', _idLivro);
            if($location.path('/livros')){
                var modalInstance = $modal.open({
                    templateUrl: 'deletebookmodal.html',
                    controller: 'DeleteBookController'
                });
            }
            else{
                var modalInstance = $modal.open({
                    templateUrl: 'deletewishlistmodal.html',
                    controller: 'DeleteBookController'
                });
            }
        };

        var update = function (callback) {
            var idUsuario = $cookies.getObject('idUsuario');
            var data =
                    {
                        "idUsuario": idUsuario,
                        "action": "getBooks"
                    };
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.livros = data;
                callback(data);
            });
        };
        update(function (data) {
            $scope.livros = data;
        });

    }]);

estanteApp.controller('UpdateBookController', ['$routeParams', '$scope', '$location', '$http', function ($routeParams, $scope, $location, $http) {

        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.href = "#livros";
        });

        $scope.nomeLivro = $routeParams.param2;
        $scope.updateBook = function () {
            var idLivro = $routeParams.param1;
            var data = JSON.stringify($scope.book);
            data = data.replace('}', ', "idLivro":"' + idLivro + '"}');
            data = $.parseJSON(data);
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                $('#sucess-modal').modal('show');
            });
        };
    }]);

estanteApp.controller('DeleteBookController', ['$scope','$http','$cookies','$modalInstance','$location', function ($scope, $http, $cookies,$modalInstance,$location) {
        $scope.closeModal = function (){
            $modalInstance.close();
        };
        $scope.idLivro = $cookies.get('idLivroDelete');
        $cookies.remove('idLivroDelete');
        var data = {
                "idLivro": $scope.idLivro,
                "action": "delete"
            };
        $scope.deleteBook = function () {
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function () {
                $modalInstance.close();
                window.parent.location.href = "#livros";

            });
        };
    }]);

estanteApp.controller('GetWishlistController', ['$modal', '$cookies', '$scope', '$rootScope', '$http', function ($modal, $cookies, $scope, $rootScope, $http) {
        $scope.openModal = function (_idLivro) {
            $cookies.put('idWishlistDelete', _idLivro);
            var modalInstance = $modal.open({
                templateUrl: 'deletewishlistmodal.html',
                controller: 'DeleteWishlistController'
            });
        };

        var update = function (callback) {
            var idUsuario = $cookies.getObject('idUsuario');
            var data =
                    {
                        "idUsuario": idUsuario,
                        "action": "getBooks"
                    };
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.livros = data;
                callback(data);
            });
        };
        update(function (data) {
            $scope.livros = data;
        });

    }]);

estanteApp.controller('DeleteWishlistController', ['$scope','$http','$cookies','$modalInstance','$location', function ($scope, $http, $cookies,$modalInstance,$location) {
        $scope.closeModal = function (){
            $modalInstance.close();
        };
        $scope.idLivro = $cookies.get('idWishlistDelete');
        $cookies.remove('idWishlistDelete');
        var data = {
                "idLivro": $scope.idLivro,
                "action": "delete"
            };
        $scope.deleteBook = function () {
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function () {
                $modalInstance.close();
                window.parent.location.href = "#wishlist";

            });
        };
    }]);

estanteApp.controller('UpdateWishlistController', ['$routeParams', '$scope', '$location', '$http', function ($routeParams, $scope, $location, $http) {

        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.href = "#wishlist";
        });

        $scope.nomeLivro = $routeParams.param2;
        $scope.updateWishlist = function () {
            var idLivro = $routeParams.param1;
            var data = JSON.stringify($scope.book);
            data = data.replace('}', ', "idLivro":"' + idLivro + '"}');
            data = $.parseJSON(data);
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                $('#sucess-modal').modal('show');
            });
        };
    }]);


estanteApp.directive("fileread", [function () {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                var reader = new FileReader();
                reader.onload = function (loadEvent) {
                    scope.$apply(function () {
                        scope.fileread = loadEvent.target.result;
                    });
                }
                reader.readAsDataURL(changeEvent.target.files[0]);
            });
        }
    }
}]);

estanteApp.controller('ViewBookController', ['$routeParams', '$scope', '$location', '$http', function ($routeParams, $scope, $location, $http) {
        var idLivro = $routeParams.param1;
        var update = function (callback) {
            var data =
                    {
                        "idLivro": idLivro,
                        "action": "getBookbyId"
                    };
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.livro = data;
                callback(data);
            });
        };
        update(function (data) {
            $scope.livro = data;
        });
    }]);

estanteApp.controller('WishlistController', ['$cookies', '$scope', '$location', '$http', function ($cookies, $scope, $location, $http) {
        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.href = "#wishlist";
        });
        $scope.insertBook = function () {
            var idUsuario = $cookies.getObject('idUsuario');
            var data = JSON.stringify($scope.book);
            data = data.replace('}', ', "idUsuario":"' + idUsuario + '"}');
            data = $.parseJSON(data);
            $http({
                method: 'POST',
                url: '/BookController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                 $('#sucess-modal').modal('show');
            });
        };

    }]);