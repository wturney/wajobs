'use strict';

describe('wawork module', function() {

  beforeEach(module('wawork'));

  it('should forward to postings', inject(function($route) {
    expect($route.routes[null].redirectTo).toEqual('/postings');
  }));

});
