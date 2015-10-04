estanteApp.controller('HeaderController', ['$scope', '$location', function ($scope, $location) {
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

estanteApp.controller('LoginController', ['$scope', '$location', function ($scope, $location) {
        $scope.go = function (path) {
              $location.path( path );
        };
    }]);