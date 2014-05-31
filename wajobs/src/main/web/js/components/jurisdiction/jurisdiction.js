angular.module('wawork.jurisdictions.service', ['ngResource'])

.factory('Jurisdictions', ['$resource',
  function($resource) {
    return $resource('/jurisdictions', {}, {
      query: {
        method: 'GET',
        isArray: true
      }
    });
  }
]);
