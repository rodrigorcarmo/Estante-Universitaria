estanteApp.controller('HeaderController', ['$cookies','$scope', '$location', function ($cookies, $scope, $location) {       
        $scope.checkIfLogged = function(){
          var idUsuario = -1;
          
          if(typeof $cookies.getObject('idUsuario') != "undefined"){
             idUsuario = $cookies.getObject('idUsuario');
          }
          if(idUsuario > 0)
              return true;
          else
              return false;
        };
        
        $scope.logout = function(){
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

estanteApp.controller('LoginController', ['$cookies','$scope', '$location', '$http', function ($cookies, $scope, $location, $http) {

        $scope.go = function (path) {
              $location.path( path );
        };
        
        $scope.validateUser = function (){
         $http({
            method : 'POST',
            url : '/UserController',
            data: $.param($scope.login),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function sucessCallback(data){
            if(data.data < 0){
                alert("Usuário não encontrado");
                $cookies.put('idUsuario', -1);
            }
            else{
                $cookies.put('idUsuario', data.data);
                $location.path('/anuncios');
            }
        });
        };
 }]);
 
 estanteApp.controller('RegisterController', ['$scope', '$location', '$http', function ($scope, $location, $http) {
    $scope.insertUser = function (){
         $http({
            method : 'POST',
            url : '/UserController',
            data : $.param($scope.user),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        };
 }]);
 
 estanteApp.controller('UpdateController', ['$cookies','$scope', '$location', '$http', function ($cookies, $scope, $location, $http) {
      $scope.updateUser = function (){
        var idUsuario = $cookies.getObject('idUsuario');
        var data = JSON.stringify($scope.user);
        data = data.replace('}',', "id":"'+idUsuario+'"}');
        data = $.parseJSON(data);
        $http({
            method : 'POST',
            url : '/UserController',
            data : $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function sucessCallback(data){
            alert("Alteraçõs salvas com sucesso!");
        });
        };
 }]);