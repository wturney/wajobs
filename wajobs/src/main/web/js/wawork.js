'use strict';

/* WorkWA Module */

angular.module('wawork', [
  'ngRoute',
  'wawork.postings'
])

.config(function($routeProvider) {
  $routeProvider.otherwise({
    redirectTo: '/postings'
  });
});

function WaworkCtrl($scope) {

}
