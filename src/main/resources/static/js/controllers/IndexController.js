(function() {
    'use strict';

    angular
        .module('zzpjcrypt',['ngFileUpload'])
        .controller('IndexController', IndexController);
    
    IndexController.$inject = ['$scope','Upload', '$http'];
    
    function IndexController($scope, Upload, $http) { 
        'use strict';

    	var self = this;
        var keyPattern = /^[0-9a-fA-F]{8}$/;

    	self.inputType="text";

    	self.key = '';
    	self.isKeyValid = false;

    	self.textInput = '';
    	self.textOutput = '';

        self.validateKey = function(){
            self.isKeyValid = keyPattern.test(self.key);
            console.log(keyPattern.test(self.key));
        }
    	self.encrypt = function(){
    	    var data = {text: self.textInput, key: self.key};
            $http.post('api/des/encrypt/text', data)
            .then(function(response){
                self.textOutput = response.data.encryptedText;
            });
    	}

    	self.decrypt = function(){
    	    var data = {text: self.textInput, key: self.key};
    	    $http.post('api/des/decrypt/text', data)
                .then(function(response){
                    self.textOutput = response.data.decryptedText;
                });
    	}

    	self.encryptFile=function(files){
    	    var formData=new FormData();
    	    console.log(files);
    	    formData.append("key",self.key);
    	    formData.append("file", files[0]);

    	    var request = new XMLHttpRequest();
    	    request.open('POST', 'api/des/encrypt/file');
    	    request.send(formData);
    	    console.log(request.status);
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

    	self.decryptFile = function(files){
            var formData=new FormData();
            console.log(files);
            formData.append("key",self.key);
            formData.append("file", files[0]);

            var request = new XMLHttpRequest();
            request.open('POST', 'api/des/decrypt/file');
            request.send(formData);
        }
    }
})();



