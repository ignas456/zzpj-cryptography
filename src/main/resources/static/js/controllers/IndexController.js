(function() {
    'use strict';

    angular
        .module('zzpjcrypt',['ngFileUpload'])
        .controller('IndexController', IndexController);
    
    IndexController.$inject = ['$scope','Upload', '$http'];
    
    function IndexController($scope, Upload, $http) { 
    	$scope.testText = "AngularDziala";
    	
    	$scope.uploadFile=function($files){
    	    var formData=new FormData();
    	    console.log($files)
    	    formData.append("key","asdaasda")
    	    formData.append("file", $files[0]);
    	    
    	    var request = new XMLHttpRequest();
    	    request.open('POST', 'api/des/encrypt/file');
    	    request.send(formData);
    	    
    	   /* $http.post('/api/des/encrypt/file', formData, {
    	        transformRequest: function(data, headersGetterFunction) {
    	            return data;
    	        },
    	        headers: { 'Content-Type': undefined }
    	        }).success(function(data, status) {                       
    	            alert("Success ... " + status);
    	        }).error(function(data, status) {
    	            alert("Error ... " + status);
    	        });*/
    	}
    }
})();



