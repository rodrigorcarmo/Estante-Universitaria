var estanteApp = angular.module('estanteApp', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/home',{
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
                        .otherwise('/home',{
                            templateUrl: 'home.html'
                        }) 
            }]);
 
    
    
    
    