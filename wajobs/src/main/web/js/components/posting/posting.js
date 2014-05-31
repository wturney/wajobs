angular.module('wawork.postings.service', ['ngResource'])

.factory('Postings', ['$resource',
  function($resource) {
    return $resource('/postings', {}, {
      query: {
        method: 'GET',
        isArray: true
      }
    });
  }
]);
