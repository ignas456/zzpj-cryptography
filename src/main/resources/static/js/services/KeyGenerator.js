angular.module('zzpjcrypt').service('KeyGenerator', [function(){
    'use strict';

    var chars = 'ABCDEF0123456789';

    this.generateKey = function(){
        var key = '';
        for(var i = 0; i < 16; i++){
            var idx = Math.floor((Math.random() * 16));
            key += chars[idx];
        }
        return key;
    }
}]);