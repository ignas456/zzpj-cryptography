(function() {
    'use strict';

    angular
        .module('zzpjcrypt',['ngFileUpload'])
        .controller('IndexController', IndexController);
    
    IndexController.$inject = ['$scope'];
    
    function IndexController($scope) { 
    	$scope.testText = "AngularDziala";
    }

})();



