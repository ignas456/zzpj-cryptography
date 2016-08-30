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
            var name = files[0].name;
            var type = files[0].type;
    	    Upload.upload({
                url: 'api/des/encrypt/file',
                data: {file: files[0], 'key': self.key}
            })
            .then(function (response) {
                //console.log(response.data.encryptedFile);
                var encrypted = new File([window.atob(response.data.encryptedFile)], name, {type: type});
                saveAs(encrypted);
            });
    	}

    	self.decryptFile = function(files){
    	      var name = files[0].name;
              var type = files[0].type;
              Upload.upload({
                 url: 'api/des/decrypt/file',
                 data: {file: files[0], 'key': self.key}
              })
              .then(function (response) {
                //console.log(response.data.decryptedFile);
                var decrypted = new File([window.atob(response.data.decryptedFile)], name, {type: type});
                saveAs(decrypted);
              });
        }
    }
})();



