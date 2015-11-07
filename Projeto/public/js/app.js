var estanteApp = angular.module('estanteApp', ['ngRoute', 'ngCookies', 'ui.bootstrap'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/home', {
                            templateUrl: 'home.html'
                        })
                        .when('/anuncios', {
                            templateUrl: 'Anuncios/anuncios.html'
                        })
                        .when('/quemsomos', {
                            templateUrl: 'quemsomos.html'
                        })
                        .when('/faleconosco', {
                            templateUrl: 'faleconosco.html'
                        })
                        .when('/cadastrar', {
                            templateUrl: 'cadastrar.html'
                        })
                        .when('/entre', {
                            templateUrl: 'entre.html'
                        })
                        .when('/usuario', {
                            templateUrl: 'Usuario/usuario.html'
                        })
                        .when('/livros', {
                            templateUrl: 'Livro/livros.html'
                        })
                        .when('/favoritos', {
                            templateUrl: 'Favoritos/favoritos.html'
                        })
                        .when('/wishlist', {
                            templateUrl: 'Wishlist/wishlist.html'
                        })
                        .when('/addwishlist', {
                            templateUrl: 'Wishlist/addwishlist.html'
                        })
                        .when('/addlivro', {
                            templateUrl: 'Livro/addlivro.html'
                        })
                        .when('/editlivro/:param1/:param2', {
                            templateUrl: 'Livro/editlivro.html',
                        })
                        .when('/editwishlist/:param1/:param2', {
                            templateUrl: 'Wishlist/editwishlist.html',
                        })
                        .when('/viewlivro/:param1', {
                            templateUrl: 'Livro/viewlivro.html',
                        })
                        .when('/viewwishlist/:param1', {
                            templateUrl: 'Wishlist/viewwishlist.html',
                        })
                        .when('/meusanuncios', {
                            templateUrl: 'MeusAnuncios/meusanuncios.html',
                        })
                        .when('/addmyads', {
                            templateUrl: 'MeusAnuncios/addmyads.html',
                        })
                        .when('/viewmyads/:param1', {
                            templateUrl: 'MeusAnuncios/viewmyads.html',
                        })
                        .when('/viewad/:param1', {
                            templateUrl: 'Anuncios/viewad.html',
                        })
                        .when('/editmyads/:param1', {
                            templateUrl: 'MeusAnuncios/editmyads.html',
                        })
                        .otherwise('/anuncios', {
                            templateUrl: 'Anuncios/anuncios.html'
                        })
            }]);




    