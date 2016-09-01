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
                self.textOutput = response.data;
            });
    	}

    	self.decrypt = function(){
    	    var data = {text: self.textInput, key: self.key};
    	    $http.post('api/des/decrypt/text', data)
                .then(function(response){
                    self.textOutput = response.data;
                });
    	}
    	
    	self.encryptFile=function(files){
            var filename = files[0].name;
            
            var formdata = new FormData();
            formdata.append('file', files[0]);
            formdata.append('key', self.key);
            
            var request = {
            			method: 'POST',
            			url: './api/des/encrypt/file',
            			responseType: 'arraybuffer',
            			headers: {'Content-Type': undefined},
            			transformRequest: angular.identity,
            			data: formdata
            		};
            
            $http(request).then(function (response) {
                var encrypted = new File([response.data], filename, {type: response.headers('Content-Type')});
                saveAs(encrypted, filename);
            });
    	}

    	self.decryptFile = function(files){
    		var filename = files[0].name;
            
            var formdata = new FormData();
            formdata.append('file', files[0]);
            formdata.append('key', self.key);
            
            var request = {
            			method: 'POST',
            			url: './api/des/decrypt/file',
            			responseType: 'arraybuffer',
            			headers: {'Content-Type': undefined},
            			transformRequest: angular.identity,
            			data: formdata
            		};
            
            $http(request).then(function (response) {
                var decrypted = new File([response.data], filename, {type: response.headers('Content-Type')});
                saveAs(decrypted, filename);
            });
        }
    }
})();



