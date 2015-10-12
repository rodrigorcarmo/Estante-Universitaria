var estanteApp = angular.module('estanteApp', ['ngRoute','ngCookies','ui.bootstrap'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/home', {
                            templateUrl: 'home.html'
                        })
                        .when('/anuncios', {
                            templateUrl: 'anuncios.html'
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
                            templateUrl: 'usuario.html'
                        })
                        .when('/livros', {
                            templateUrl: 'livros.html'
                        })
                        .when('/favoritos', {
                            templateUrl: 'favoritos.html'
                        })
                        .when('/wishlist', {
                            templateUrl: 'wishlist.html'
                        })
                        .when('/addlivro', {
                            templateUrl: 'addlivro.html'
                        }).
                        when('/editlivro/:param1/:param2', {
                        templateUrl: 'editlivro.html',    
    
                        })
                        .otherwise('/anuncios', {
                            templateUrl: 'anuncios.html'
                        })
            }]);




    