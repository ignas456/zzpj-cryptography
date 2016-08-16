(function() {
    'use strict';

    angular
        .module('zzpjcrypt',[])
        .controller('IndexController', IndexController);
    
    IndexController.$inject = ['$scope'];
    
    function IndexController($scope) { 
    	$scope.testText = "AngularDziala";
    }

})();



