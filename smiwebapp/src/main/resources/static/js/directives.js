

var app = angular.module("directives",[])
      .directive("select2", function($timeout, $parse) {
	  return {
	    restrict: 'AC',
	    require: 'ngModel',
	    link: function($scope, element, $attrs) {
	      console.log($attrs);
	      $timeout(function() {
	    	console.log('Directive');
	    	$(element).select2();  
	        //element.select2();
	    	
	       
	    	$(element).select2Initialized = true;
	      });

	      var refreshSelect = function() {
	    	// alert($(element).select2Initialized);
	       //if (!element.select2Initialized) return;
	        $timeout(function() {
	          console.log('Weeee');
	          $(element).trigger('change');
	        });
	      };
	      
	      var recreateSelect = function () {
	       //if (!element.select2Initialized) return;
	        $timeout(function() {
	          console.log('Waaaa');
	          $(element).select2('destroy');
	          $(element).select2();
	        });
	      };

	      $scope.$watch($attrs.ngModel, refreshSelect);

	      if ($attrs.ngOptions) {
	        var list = $attrs.ngOptions.match(/ in ([^ ]*)/)[1];
	        // watch for option list change
	        $scope.$watch(list, recreateSelect);
	        console.log('attrs2');
	      }

	      if ($attrs.ngDisabled) {
	        $scope.$watch($attrs.ngDisabled, refreshSelect);
	      }
	    }
	  };
});