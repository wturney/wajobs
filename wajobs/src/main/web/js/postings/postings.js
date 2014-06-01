var employmentTypes = ['N/A', 'Full Time', 'Part Time', 'Seasonal', 'Temporary', 'Internship', 'On Call'];

angular.module('wawork.postings', [
  'ngRoute',
  'wawork.postings.service',
  'wawork.jurisdictions.service'
])

.config(function($routeProvider) {
  $routeProvider
    .when('/postings', {
      controller: 'PostingsCtrl',
      templateUrl: 'postings/postings.tpl.html',
      resolve: {
        jurisdictions: function(Jurisdictions) {
          return Jurisdictions.query()
            .$promise.then(function(data) {
              var idMap = {};
              for (var id in data) {
                idMap[id] = data[id]['name'];
              }
              return idMap;
            });
        },
        postings: function(Postings) {
          return Postings.query()
            .$promise.then(function(data) {
              return data;
            });
        }
      }
    });
})

.filter('employmentType', function() {
  return function(id, placeholder) {
    return employmentTypes[id] ? employmentTypes[id] : placeholder;
  };
})

.filter('jurisdiction', function() {
  return function(id, idNameMap, placeholder) {
    return idNameMap[id] ? idNameMap[id] : placeholder;
  };
})

.filter('epochDate', function() {
  return function(input, placeholder) {
    var result = placeholder;

    if (input) {
      var epoch = parseInt(input);
      if (!isNaN(epoch)) {
        var date = moment(epoch);
        result = date.format('MMM D');
      }
    }
    return result;
  };
})

function PostingsCtrl($scope, $routeParams, postings, jurisdictions) {
  $scope.postings = postings;
  $scope.jurisdictions = jurisdictions;
}
