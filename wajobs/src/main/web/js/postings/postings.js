angular.module('wawork.postings', [
  'ngRoute',
  'wawork.postings.service',
  'wawork.jurisdictions.service'
])

.config(function($routeProvider) {
  $routeProvider
    .when('/postings', {
      controller: PostingsCtrl,
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
        }
      }
    });
})

.filter('employmentType', function() {
  var employmentTypes = ['N/A', 'Full Time', 'Part Time', 'Seasonal', 'Temporary', 'Internship', 'On Call'];

  return function(id) {
    return employmentTypes[id];
  };
})

.filter('jurisdiction', function() {
  return function(id, jurisdictions) {
    return jurisdictions[id];
  };
})

.filter('longDate', function() {
  return function(input) {
    if (input) {
      var epoch = parseInt(input);
      if (!isNaN(epoch)) {
        var date = moment(epoch);
        return date.format('MMM D');
      }
    }
    return null;
  };
})

.filter('closeDate', function() {
  return function(input, alternative) {
    return input ? input : alternative;
  };
});

function PostingsCtrl($scope, $routeParams, Postings, jurisdictions) {
  $scope.postings = Postings.query();
  $scope.jurisdictions = jurisdictions;
}
