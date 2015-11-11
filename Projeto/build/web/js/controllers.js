function formataData(dataAntiga) {
    var anoMesDia = dataAntiga.split(" ")[0];
    var tempo = dataAntiga.split(" ")[1].split(".")[0];
    var ano = anoMesDia.split("-")[0];
    var mes = anoMesDia.split("-")[1];
    var dia = anoMesDia.split("-")[2];
    var novaData = tempo + " de " + dia + "/" + mes + "/" + ano;
    return novaData;
}

estanteApp.controller('HeaderController', ['$cookies', '$scope', '$location', function ($cookies, $scope, $location) {

        $scope.checkIfLogged = function () {
            var idUsuario = -1;

            if (typeof $cookies.getObject('idUsuario') != "undefined") {
                idUsuario = $cookies.getObject('idUsuario');
            }
            if (idUsuario > 0) {
                $("#adFavoriteId").removeClass("hidden");
                $("#helloUserId").removeClass("hidden");
                $("#negociarAdId").removeClass("hidden");
                $("#idComentario").removeClass("hidden");
                return true;
            }
            else {
                $("#negociarAdId").addClass("hidden");
                $("#helloUserId").addClass("hidden");
                $("#adFavoriteId").addClass("hidden");
                $("#idComentario").addClass("hidden");
                return false;
            }
        };

        $scope.logout = function () {
            $cookies.remove('idUsuario');
            $location.path('/home');
        };

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
                $('#sucess-modal').modal('show');
            });

        };
    }]);

estanteApp.controller('UpdateController', ['$cookies', '$scope', '$location', '$http', function ($cookies, $scope, $location, $http) {
        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.reload();
        });
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
                $('#sucess-modal').modal('show');
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

estanteApp.controller('GetBooksController', ['$cookies', '$scope', '$modal', '$location', '$http', function ($cookies, $scope, $modal, $location, $http) {
        $scope.openModal = function (_idLivro) {
            $cookies.put('idLivroDelete', _idLivro);
            if ($location.path('/livros')) {
                var modalInstance = $modal.open({
                    templateUrl: 'Livro/deletebookmodal.html',
                    controller: 'DeleteBookController'
                });
            }
            else {
                var modalInstance = $modal.open({
                    templateUrl: 'Wishlist/deletewishlistmodal.html',
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

estanteApp.controller('DeleteBookController', ['$scope', '$http', '$cookies', '$modalInstance', '$location', function ($scope, $http, $cookies, $modalInstance, $location) {
        $scope.closeModal = function () {
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
                templateUrl: 'Wishlist/deletewishlistmodal.html',
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

estanteApp.controller('DeleteWishlistController', ['$scope', '$http', '$cookies', '$modalInstance', '$location', function ($scope, $http, $cookies, $modalInstance, $location) {
        $scope.closeModal = function () {
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

estanteApp.controller('MyAdsController', ['$cookies', '$scope', '$modal', '$location', '$http', function ($cookies, $scope, $modal, $location, $http) {
        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.href = "#meusanuncios";
        });
        $scope.insertAd = function () {
            var idUsuario = $cookies.getObject('idUsuario');
            var data = JSON.stringify($scope.ad);
            data = data.replace('}', ', "idUsuario":"' + idUsuario + '"}');
            data = $.parseJSON(data);
            $http({
                method: 'POST',
                url: '/AdController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                $('#sucess-modal').modal('show');
            });
        };
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
        });

    }]);

estanteApp.controller('GetAdController', ['$modal', '$cookies', '$scope', '$rootScope', '$http', function ($modal, $cookies, $scope, $rootScope, $http) {
        $scope.openModal = function (_idAnuncio) {
            $cookies.put('idAdDelete', _idAnuncio);
            var modalInstance = $modal.open({
                templateUrl: 'MeusAnuncios/deleteadmodal.html',
                controller: 'DeleteAdController'
            });
        };

        var update = function (callback) {
            var idUsuario = $cookies.getObject('idUsuario');
            var data =
                    {
                        "idUsuario": idUsuario,
                        "action": "getAds"
                    };
            $http({
                method: 'POST',
                url: '/AdController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.ads = data;
                callback(data);
            });
        };
        update(function (data) {
            $scope.ads = data;
        });

    }]);

estanteApp.controller('ViewAdController', ['$routeParams', '$scope', '$location', '$http', function ($routeParams, $scope, $location, $http) {
        var idAnuncio = $routeParams.param1;
        $scope.openNegociarModal = function () {
            $('#negociarModal').modal('show');
        }
        var update = function (callback) {
            var data =
                    {
                        "idAnuncio": idAnuncio,
                        "action": "getAdById"
                    };
            $http({
                method: 'POST',
                url: '/AdController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.ad = data;
                callback(data);
            });
        };
        update(function (data) {
            $scope.ad = data;
        });
    }]);

estanteApp.controller('DeleteAdController', ['$scope', '$http', '$cookies', '$modalInstance', '$location', function ($scope, $http, $cookies, $modalInstance, $location) {
        $scope.closeModal = function () {
            $modalInstance.close();
        };
        $scope.idAnuncio = $cookies.get('idAdDelete');
        $cookies.remove('idAdDelete');
        var data = {
            "idAnuncio": $scope.idAnuncio,
            "action": "delete"
        };
        $scope.deleteBook = function () {
            $http({
                method: 'POST',
                url: '/AdController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function () {
                $modalInstance.close();
                window.parent.location.href = "#meusanuncios";

            });
        };
    }]);

estanteApp.controller('UpdateAdController', ['$routeParams', '$scope', '$cookies', '$location', '$http', function ($routeParams, $scope, $cookies, $location, $http) {
        $('#sucess-modal').on('hidden.bs.modal', function () {
            window.parent.location.href = "#meusanuncios";
        });

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
        });

        $scope.updateAd = function () {
            var idAnuncio = $routeParams.param1;
            var data = JSON.stringify($scope.ad);
            data = data.replace('}', ', "idAnuncio":"' + idAnuncio + '"}');
            data = $.parseJSON(data);
            $http({
                method: 'POST',
                url: '/AdController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                $('#sucess-modal').modal('show');
            });
        };
    }]);

estanteApp.controller('AllAdsController', ['$routeParams', '$scope', '$location', '$http', function ($routeParams, $scope, $location, $http) {
        var chunk = function (arr, size) {
            var newArr = [];
            for (var i = 0; i < arr.length; i += size) {
                newArr.push(arr.slice(i, i + size));
            }
            return newArr;
        };

        var update = function (callback) {
            var data =
                    {
                        "action": "getAllAds"
                    };
            $http({
                method: 'POST',
                url: '/AdController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.ads = chunk(data, 3);
                callback(data);
            });
        };
        update(function (data) {
            $scope.ads = chunk(data, 3);
        });
    }]);

estanteApp.controller('ViewDetailedAdController', ['$routeParams', '$scope', '$location', '$http', function ($routeParams, $scope, $location, $http) {
        var idAnuncio = $routeParams.param1;
        var data =
                {
                    "idAnuncio": idAnuncio,
                    "action": "getAdById"
                };
        $http({
            method: 'POST',
            url: '/AdController',
            data: $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function callback(data) {
            $scope.ad = data;
        });
    }]);

estanteApp.controller('AddFavoriteController', ['$routeParams', '$cookies', '$scope', '$modal', '$location', '$http', function ($routeParams, $cookies, $scope, $modal, $location, $http) {
        $scope.addFavorite = function () {
            var idUsuario = $cookies.getObject('idUsuario');
            var idAnuncio = $routeParams.param1;
            var data = {
                "idUsuario": idUsuario,
                "idAnuncio": idAnuncio,
                "action": "Add"
            }
            $http({
                method: 'POST',
                url: '/FavoriteController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function sucessCallback(data) {
                var result = data.data.resultado;
                if (result === 1)
                    $('#sucess-modal').modal('show');
                else
                    $('#fail-modal').modal('show');
            });
        };
    }]);

estanteApp.controller('FavoritesController', ['$modal', '$cookies', '$scope', '$rootScope', '$http', function ($modal, $cookies, $scope, $rootScope, $http) {
        $scope.openModal = function (_idFavorito) {
            $cookies.put('idFavoriteDelete', _idFavorito);
            var modalInstance = $modal.open({
                templateUrl: 'Favoritos/deletefavoritemodal.html',
                controller: 'DeleteFavoriteController'
            });
        };
        var update = function (callback) {
            var idUsuario = $cookies.getObject('idUsuario');
            var data =
                    {
                        "idUsuario": idUsuario,
                        "action": "getFavorites"
                    };
            $http({
                method: 'POST',
                url: '/FavoriteController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.favorites = data;
                callback(data);
            });
        };
        update(function (data) {
            $scope.favorites = data;
        });

    }]);

estanteApp.controller('DeleteFavoriteController', ['$scope', '$http', '$cookies', '$modalInstance', '$location', function ($scope, $http, $cookies, $modalInstance, $location) {
        $scope.closeModal = function () {
            $modalInstance.close();
        };
        $scope.idFavorito = $cookies.get('idFavoriteDelete');
        $cookies.remove('idFavoriteDelete');
        var data = {
            "idFavorito": $scope.idFavorito,
            "action": "delete"
        };
        $scope.deleteFavorite = function () {
            $http({
                method: 'POST',
                url: '/FavoriteController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function () {
                $modalInstance.close();
                window.parent.location.href = "#favoritos";

            });
        };
    }]);

estanteApp.controller('GetUserController', ['$modal', '$cookies', '$scope', '$rootScope', '$http', '$location', function ($modal, $cookies, $scope, $rootScope, $http, $location) {

        $scope.logout = function () {
            $cookies.remove('idUsuario');
            $location.path('/home');
        };

        var update = function (callback) {
            var idUsuario = $cookies.getObject('idUsuario');
            var data =
                    {
                        "idUsuario": idUsuario,
                        "action": "GETUSER"
                    };
            $http({
                method: 'POST',
                url: '/UserController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $scope.user = data;
                callback(data);
            });
        };
        update(function (data) {
            $scope.user = data;
        });

    }]);

estanteApp.controller('AskAdController', ['$routeParams', '$scope', '$cookies', '$http', function ($routeParams, $scope, $cookies, $http) {
        $scope.askAd = function () {
            var idAnuncio = $routeParams.param1;
            var idUsuario = $cookies.getObject('idUsuario');
            var requisicao = {
                "idUsuario": idUsuario,
                "question": $scope.question,
                "idAnuncio": idAnuncio,
                "action": "question"

            };
            $http({
                method: 'POST',
                url: '/AdController',
                data: $.param(requisicao),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
            window.location.reload()

        }
    }]);

estanteApp.controller('ComentarioController', ['$routeParams', '$scope', '$cookies', '$http', function ($routeParams, $scope, $cookies, $http) {
        var idAnuncio = $routeParams.param1;
        var data =
                {
                    "idAnuncio": idAnuncio,
                    "action": "GETCOMMENTS"
                };
        $http({
            method: 'POST',
            url: '/AdController',
            data: $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function callback(data) {
            $scope.comments = data;
            var newData = $scope.comments;
            for (var index = 0; index < newData.length; index++) {
                var anoMesDia = newData[index].date.split(" ")[0];
                var tempo = newData[index].date.split(" ")[1].split(".")[0];
                var ano = anoMesDia.split("-")[0];
                var mes = anoMesDia.split("-")[1];
                var dia = anoMesDia.split("-")[2];
                var novaData = tempo + " de " + dia + "/" + mes + "/" + ano;
                $scope.comments[index].date = novaData;
            }
            ;
        });
    }]);

estanteApp.controller('MensagemNegociarController', ['$routeParams', '$scope', '$cookies', '$http', function ($routeParams, $scope, $cookies, $http) {
        var idAnuncio = $routeParams.param1;
        var idCliente = $cookies.getObject('idUsuario');
        $scope.enviarMensagem = function () {
            var mensagem = $scope.mensagem;
            var data = {
                "idAnuncio": idAnuncio,
                "idCliente": idCliente,
                "mensagem": mensagem,
                "action": "CREATECHAT"
            };
            $http({
                method: 'POST',
                url: '/ChatController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $('#negociarModal').modal('hide');
                $('#confirmaNegociacao').modal('show');
            });
        };
    }]);

estanteApp.controller('GetChatController', ['$routeParams', '$location', '$scope', '$cookies', '$http', function ($routeParams, $location, $scope, $cookies, $http) {
        $scope.openEnviarMensagem = function (chat) {
            var idUsuarioEnvia;
            var idUsuarioRecebe;
            if (chat.anunciante === "Voce") {
                idUsuarioEnvia = chat.idAnunciante;
                idUsuarioRecebe = chat.idCliente;
            }
            else {
                idUsuarioEnvia = chat.idCliente;
                idUsuarioRecebe = chat.idAnunciante;
            }

            var chatHeader = "" + idUsuarioEnvia + ":" + idUsuarioRecebe + ":" + chat.idChat;
            localStorage.setItem('chatHeader', chatHeader);
            $('#enviarMensagemModal').modal('show');
        };
        $scope.openHistoricoModal = function (chat) {
            var usuario;
            if (chat.anunciante === "Voce") {
                usuario = chat.cliente;
            }
            else {
                usuario = chat.anunciante;
            }
            var chatHeader = "" + chat.idChat + ":" + usuario;
            localStorage.setItem('chatHeader', chatHeader);
            $location.path("/verhistoricochat")
        };
        var idUsuario = $cookies.getObject('idUsuario');
        var data = {
            "idUsuario": idUsuario,
            "action": "getallchats"
        };
        $http({
            method: 'POST',
            url: '/ChatController',
            data: $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function callback(data) {
            $scope.chats = data;
            var newData = $scope.chats;
            for (var index = 0; index < newData.length; index++) {
                novaData = formataData(newData[index].date);
                $scope.chats[index].date = novaData;
            }
            ;
        });
    }]);

estanteApp.controller('EnviarMensagemController', ['$routeParams', '$scope', '$cookies', '$http', function ($routeParams, $scope, $cookies, $http) {
        $scope.enviarMensagem = function () {
            var header = localStorage.getItem('chatHeader');
            header = header.split(":");
            var idUsuarioEnvia = header[0];
            var idUsuarioRecebe = header[1];
            var idChat = header[2];
            var data = {
                "idUsuarioEnvia": idUsuarioEnvia,
                "idUsuarioRecebe": idUsuarioRecebe,
                "idChat": idChat,
                "mensagem": $scope.mensagem,
                "action": "INSERTMESSAGE"
            };
            $http({
                method: 'POST',
                url: '/MessageController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $('#enviarMensagemModal').modal('hide');
                $('#confirmaModal').modal('show');
            });
        };
        $scope.closeModal = function () {
            $cookies.remove('chatHeader');
            $('#enviarMensagemModal').modal('hide');
        };

    }]);

estanteApp.controller('GetMensagensController', ['$routeParams', '$scope', '$cookies', '$http', function ($routeParams, $scope, $cookies, $http) {
        var header = localStorage.getItem('chatHeader');
        var idChat = header.split(":")[0];
        $scope.usuario = header.split(":")[1];
        var data = {
            "idChat": idChat,
            "action": "GETCHAT"
        };
        $http({
            method: 'POST',
            url: '/MessageController',
            data: $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function callback(data) {
            $scope.messages = data;
            var newData = $scope.messages;
            for (var index = 0; index < newData.length; index++) {
                novaData = formataData(newData[index].date);
                $scope.messages[index].date = novaData;
            }
        });


    }]);

estanteApp.controller('GetTransacoesController', ['$routeParams', '$scope', '$rootScope', '$cookies', '$http', function ($routeParams, $scope, $rootScope, $cookies, $http) {
        $scope.openModalConfirmacao = function (transacao) {
            localStorage.setItem("transacao", transacao.idTransacao);
            $("#cancelarModal").modal('show');
        }
        $scope.openModalConclusao = function (transacao) {
            localStorage.setItem("transacao", transacao.idTransacao);
            if (transacao.anunciante === "Voce") {
                localStorage.setItem("avaliarTexto", "Avalie como a/o cliente " + transacao.cliente + " se comportou nessa transação");
                localStorage.setItem("avaliar", "cliente:" + transacao.idCliente);
            }
            else {
                localStorage.setItem("avaliarTexto", "Avalie como a/o anunciante " + transacao.anunciante + " se comportou nessa transação");
                localStorage.setItem("avaliar", "anunciante:" + transacao.idAnunciante);
            }
            $rootScope.$broadcast("avaliar-finished");
            $("#concluirModal").modal('show');
        };
        var idUsuario = $cookies.getObject('idUsuario');
        var data = {
            "idUsuario": idUsuario,
            "action": "GETTRANSACOES"
        };
        $http({
            method: 'POST',
            url: '/TransacaoController',
            data: $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function callback(data) {
            $scope.transacoes = data;
            var newData = $scope.transacoes;
            for (var index = 0; index < newData.length; index++) {
                if (newData[index].status > 0) {
                    $scope.transacoes[index].status = "Concluido";
                } else if (newData[index].status === 0) {
                    $scope.transacoes[index].status = "Pendente";
                } else {
                    $scope.transacoes[index].status = "Cancelado";
                }
            }
        });
    }]);

estanteApp.controller('CancelarTransacaoController', ['$routeParams', '$scope', '$cookies', '$http', function ($routeParams, $scope, $cookies, $http) {
        $scope.cancelarTransacao = function () {
            $("#cancelarModal").modal('hide');
            var idTransacao = localStorage.getItem("transacao");
            var data = {
                "idTransacao": idTransacao,
                "action": "UPDATETRANSACAO",
                "status": -1
            };
            $http({
                method: 'POST',
                url: '/TransacaoController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $("#confirmacaoCancelamentoModal").modal('show');
            });
        };
    }]);

estanteApp.controller('ConcluirTransacaoController', ['$routeParams', '$scope', '$cookies', '$http', function ($routeParams, $scope, $cookies, $http) {
        $scope.concluirTransacao = function () {
            $("#concluirModal").modal('hide');
            var idTransacao = localStorage.getItem("transacao");
            var data = {
                "idTransacao": idTransacao,
                "action": "UPDATETRANSACAO",
                "status": 1
            };
            $http({
                method: 'POST',
                url: '/TransacaoController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $("#confirmacaoConcluirModal").modal('show');
            });
        };

    }]);

estanteApp.controller('AvaliacaoController', ['$routeParams', '$scope', '$rootScope', '$cookies', '$http', function ($routeParams, $scope,$rootScope, $cookies, $http) { 
       $scope.$on('avaliar-finished',function(event,args){ 
           $scope.avaliarTexto = localStorage.getItem("avaliarTexto");
            var header = localStorage.getItem("avaliar");   
        $scope.avaliar = function () {
            var data = {
                "idUsuario": header.split(':')[1],
                "tipo": header.split(':')[0],
                "action": "AVALIAR",
                "nota": $scope.nota
            };
            $http({
                method: 'POST',
                url: '/AvaliacaoController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                $("#obrigadoModal").modal('show');
            });
        };
    });
    }]);

estanteApp.controller('GetAvaliacoesController', ['$routeParams', '$scope', '$rootScope', '$cookies', '$http', function ($routeParams, $scope,$rootScope, $cookies, $http) { 
            var data = {
                "idUsuario":  $cookies.getObject('idUsuario'),
                "action": "GETAVALIACOES",
            };
            $http({
                method: 'POST',
                url: '/AvaliacaoController',
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function callback(data) {
                var avaliacaoAnunciante = data.anunciante;
                var avaliacaoCliente = data.cliente;
                for(var index=0;index<avaliacaoAnunciante;index++){
                    $( "#avaliacaoAnuncianteEstrelas" ).append( "<i class=\"fa fa-star\"></i>" );
                }
                for(var index=0;index<avaliacaoCliente;index++){
                    $( "#avaliacaoClienteEstrelas" ).append( "<i class=\"fa fa-star\"></i>" );
                }
        });
    }]);