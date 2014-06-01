'use strict';

describe('wawork.postings module routing', function() {

  var $httpBackend, $location, $route, $rootScope, $q, deferred;

  beforeEach(module('wawork.postings'));
  beforeEach(module('wawork.jurisdictions.service'));

  beforeEach(inject(function(_$httpBackend_, _$location_, _$route_, _$rootScope_, _$q_) {
    $httpBackend = _$httpBackend_;
    $location = _$location_;
    $route = _$route_;
    $rootScope = _$rootScope_;
  }));

  it('should resolve postings and jurisdictions prior to loading postings', inject(function($controller) {
    $httpBackend.expectGET('/jurisdictions').respond([{}]);
    $httpBackend.expectGET('/postings').respond([{}]);
    $httpBackend.expectGET('postings/postings.tpl.html').respond('');

    $location.path('/postings');
    $rootScope.$digest();
    expect($route.current.controller).toEqual('PostingsCtrl');
  }));

});

describe('wawork.postings module filters', function() {
  beforeEach(module('wawork.postings'))

  it('should replace employment type IDs with names', inject(function(employmentTypeFilter) {
    expect(employmentTypeFilter(1)).toEqual(employmentTypes[1]);
  }));

  it('should replace unknown employment type IDs with a placeholder', inject(function(employmentTypeFilter) {
    expect(employmentTypeFilter(114, 'N/A')).toEqual('N/A');
  }));

  it('should replace epoch millis with formatted date', inject(function(epochDateFilter) {
    expect(epochDateFilter(1401659323000)).toEqual('Jun 1');
  }));

  it('should replace jurisdiction IDs with jurisdiction names', inject(function(jurisdictionFilter) {
    expect(jurisdictionFilter(3, {
      1: 'City of Olympia',
      3: 'Port of Tacoma',
      5: 'City of Seattle'
    })).toEqual('Port of Tacoma');
  }));

  it('should replace invalid jurisdiction IDs with a placeholder', inject(function(jurisdictionFilter) {
    expect(jurisdictionFilter(45, {
      1: 'City of Olympia',
      3: 'Port of Tacoma',
      5: 'City of Seattle'
    }, '--')).toEqual('--');
  }));

  it('should replace invalid epoch millis with a placeholder', inject(function(epochDateFilter) {
    expect(epochDateFilter(null, '-')).toEqual('-');
    expect(epochDateFilter('asdg', 'wub')).toEqual('wub');
  }));
});
