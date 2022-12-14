var app =  angular.module("controllers", []);
   
    app.controller('shippingController', function($scope, $rootScope, $location, $window, packingslipService, $uibModal, notificationService) {
    $scope.nameFilter = null;
    $scope.pendingPackings = [];
    $scope.carriersApi = [];
    $scope.packingSelected = {}
    var carriers = [];
    
	 $scope.res = $location.search();
	 $scope.resLoc = $location.absUrl();
	 $scope.path = $location.path();
	 $scope.shipResul = {};
	 var selected_device;
	 
	 
     
	 angular.element(document).ready(function() {
			
			var devices = []; 
			
			BrowserPrint.getDefaultDevice("printer", function(device)
					{
				
						//Add device to list of devices and to html select element
						selected_device = device;
						devices.push(device);
						var html_select = document.getElementById("selected_device");
						var option = document.createElement("option");
						option.text = device.name;
						//html_select.add(option);
						$rootScope.selectedPrinter = device.name;
						
						var errorCallback = function(errorMessage){
							alert("Error: " + errorMessage);
						}
						
							
						//selected_device.sendFile("https://api.shipengine.com/v1/downloads/10/IIpROsliME-jyzuDvlDoxw/label-49875447.zpl", undefined, errorCallback) 	
						
						//Discover any other devices available to the application
						BrowserPrint.getLocalDevices(function(device_list){
							for(var i = 0; i < device_list.length; i++)
							{
								//Add device to list of devices and to html select element
								var device = device_list[i];
								if(!selected_device || device.uid != selected_device.uid)
								{
									devices.push(device);
									var option = document.createElement("option");
									
									option.text = device.name;
									option.value = device.uid;
									html_select.add(option);
								}
							}
							
						}, function(){alert("Error getting local devices")},"printer");
						
					}, function(error){
						alert(error);
					})
			
			
	 });
	 
	 
	 packingslipService.getCarriersApi().success(function (response) {
	        $scope.carriersApi = response;
	 });
	 
   	 if ($scope.path == '/shipmentsByToday') {
   		 
		 $scope.getTodayShipments = function() { 
			 packingslipService.getLabelsByToday().success(function (response) {
		        //Dig into the responde to get the relevant data
		        $scope.labelsToday = response;
  		        console.log('LABELS TODAY FOUND : ', $scope.labelsToday);
		        $rootScope.labels = $scope.labelsToday.shipmentList
		        console.log('LABELS  : ', $scope.labels);
		        iniciar_tabla('#tbLabelsToday');
	        
	         }); 
		 
		 }
		 
		 $scope.getTodayShipments();
   		 
   	 }
	 
	 
	 
	if ($scope.path == '/create-shipment-packing'){
		    $scope.shipments = [];
		    $scope.countPack = 0
		    $scope.shipment = {}
		    $scope.shipTo = {}
		    $scope.packageDimension = {};
		    $scope.packDimensions = [];
		    $scope.validateShip = false;
		    $scope.identicalPacks = 'NO'; 
		    $scope.multiPackages = false;
		    $scope.firstQty = '';
		    $scope.flagGenerated = false;   
		    $scope.clickSaving = false;
		    $scope.ship_from = {
		         name: "Seal Methods Inc",
		         company_name: "Seal Methods Inc",
		         phone: "562.946.9439",
		         address_line1: "11915 Shoemaker Av",
		         city_locality: "Santa Fe Springs",
		         state_province: "CA",
		         postal_code: "90670",
		         country_code: "US",
		         address_residential_indicator: "no" 
		      }
		    $scope.returnTo = {}
		    $scope.packages = []
		    $scope.pack = {}
		    $scope.advanced_options = {}
		    
			packingslipService.getPackDimensions().success(function (response) {
			        //Dig into the responde to get the relevant data
			        $scope.packDimensions = response; 
			});
		    

		  	packingslipService.getPackingById($scope.res.packNro).success(function (response) {
				$scope.packingSelected = response
				$rootScope.currPack = $scope.packingSelected;
			   	 packingslipService.getCarriersApi().success(function (response) {
				        //Dig into the responde to get the relevant data
				        $scope.carriersApi = response;
				        carriers = $scope.carriersApi.carriers;
				        console.log('CARRIERS' ,$scope.carriersApi );
				        var index = carriers.findIndex( record => record.carrier_id === $scope.packingSelected.carrier_id );
				        console.log('INDEX', index);
				        $scope.car =  carriers[index]
				        $scope.shipTo = {
				        		name : $scope.packingSelected.contact_name, 
				        		phone : $scope.packingSelected.phone_no,
				        		company_name : $scope.packingSelected.customer_name,
				        		address_line1 : $scope.packingSelected.address_1,
				        		address_line2 : $scope.packingSelected.address_2,
				        		address_line3 : $scope.packingSelected.address_3,
				        		city_locality : $scope.packingSelected.city,
				        		state_province : $scope.packingSelected.state,
				        		postal_code    : $scope.packingSelected.zip_code,
				        		country_code  : 'US',
				        	    address_residential_indicator : 'no'	
				        }
				        $scope.advanced_options.bill_to_account = $scope.packingSelected.bill_to_account;
				        if ($scope.advanced_options.bill_to_account != "")
				        	$scope.advanced_options.bill_to_party = "recipient";
				        else
				        	$scope.advanced_options.bill_to_party = "";
				        $scope.customerPo = $scope.packingSelected.cust_po;
				        $scope.customerSo = $scope.packingSelected.so_id; 
 
				 });

		  	});
		    
		    
	} 
	
	var errorCallback = function(errorMessage){
		alert("Error: " + errorMessage);
	}
	
	function zebra_print($rootScope,$scope, fileUrl, printer) {
		 var fileTest = "https://api.shipengine.com/v1/downloads/10/F7lShjHOVE2UEZwHoyDC9g/label-48591827.zpl"
			 console.log('GOING TO PRINT TO : ' , printer)
	    selected_device.sendFile(fileUrl, undefined, errorCallback)

	}
	
	$scope.getpackDetails = function(index) {
		
		console.log("PACK DIMENSIONS : ", $scope.packDimensions);
		console.log("INDEX : ", index);
		$scope.packW =   $scope.packageDimension.widht;
		$scope.packH =   $scope.packageDimension.height; 
		$scope.packL =   $scope.packageDimension.length;
		
	}
	
	
	$scope.createShipment = function() {
		 
		 /*********************    Just One Package   ***********************************  /
		  * 
		  *   Implementation must change for serveral packages
		  * 
		  *******************************************************************************/
		$scope.clickSaving = true;
		$scope.saveShip = true;
		$scope.labelMessages = { reference1 : '', reference2 : '', reference3 : '' };
		$scope.labelMessages = { reference1 : $scope.customerSo, reference2 : $scope.customerPo, reference3 :  '' };
		console.log('NRO DE PACKS ', $scope.noPacks);
		console.log('IDENTICAL : ', $scope.identicalPacks); 
		
		if ($scope.shipTo.name == '') {
			$scope.saveShip = false;
			alert('Contact must be filled');
		}
		
		if ($scope.saveShip) {
			if ($scope.shipTo.postal_code.length > 5 ){
				$scope.saveShip = false;
				alert('Only 5 Characters are permited for Postal Code');
			}
		}
		
		if ($scope.saveShip) {
			if (isNaN($scope.shipTo.postal_code)){
				$scope.saveShip = false;
				alert('Only Numbers are permited for Postal Code');
			}
		}
		
		
		if ($scope.saveShip) {
			if ($scope.shipTo.city_locality == '' ){
				$scope.saveShip = false;
				alert('City must be filled');
			}
		}
		
		if ($scope.saveShip) {
			if ($scope.shipTo.state_province == '' ){
				$scope.saveShip = false;
				alert('State field mujst be filled'); 
			}
		}
		
		if ($scope.saveShip) {
			if ($scope.shipTo.state_province.length > 2 ){
				$scope.saveShip = false;
				alert('Only 2 Chatacters are permited for the state field'); 
			}
		}
		
		
		console.log("Nro de Packs  : " + $scope.noPacks );
		
		if ($scope.saveShip) {
			
			if (($scope.noPacks == "") || ($scope.noPacks == 0) || ($scope.noPacks == undefined)) {
				$scope.saveShip = false;
				alert('Please enter packages quantities, Thats make sense, no..?');
			}
		}
		
		
		
		if ($scope.noPacks == 1){
			console.log('ONLY ONE PACKAGE');
			$scope.pack =  {
					weight : {
						value : $scope.packWeight,
						unit : "pound"
					} ,
					dimensions : {
						height : $scope.packageDimension.height,
						width  : $scope.packageDimension.widht,
						length : $scope.packageDimension.length,
						unit   : "inch" 
					} , 
					label_messages : $scope.labelMessages
			}
			  
			

			$scope.packages.push($scope.pack);
			console.log('PACKAGES TO SAVE ', $scope.packages );
		}
		
		
		if (($scope.noPacks > 1) && ($scope.identicalPacks == 'NO')) {
			console.log('TWO PACKAGES, NOT IDENTICAL', $scope.packagesTemp);			
			    if ($scope.validateShip == true){
					
				    console.log('VALIDATED SHIPPING ', $scope.packagesTemp.size)
				    for (var j=0; j < $scope.noPacks; j++){   
						$scope.pack =  {
								weight : {
									value : $scope.packagesTemp[j].weight.value,
									unit : "pound"
								} ,
								dimensions : {
									height : $scope.packagesTemp[j].dimensions.height,
									width  : $scope.packagesTemp[j].dimensions.widht, 
									length : $scope.packagesTemp[j].dimensions.length,
									unit   : "inch"
								} ,
								label_messages : $scope.labelMessages
						}
						
						$scope.packages.push($scope.pack);

				    }
			    }    
				else {
					$scope.saveShip = false;
					$scope.clickSaving = false;
					alert("Please Review Multi-Piece Information"); 
				}


			    console.log('PACKAGES TO SAVE ', $scope.packages );
				
			 
		}   //  End MultiPAcks Not identical
		
		
		if (($scope.noPacks > 1) && ($scope.identicalPacks == 'YES')) {
			console.log('TWO PACKAGES, *** IDENTICAL ****');	
			for (var j=0; j < $scope.noPacks; j++){   

				$scope.pack =  {
						weight : {
							value : $scope.packWeight,
							unit : "pound"
						} ,
						dimensions : {
							height : $scope.packageDimension.height,
							width  : $scope.packageDimension.widht,
							length : $scope.packageDimension.length,
							unit   : "inch"
						} ,
						label_messages : $scope.labelMessages
				}
				  
				$scope.packages.push($scope.pack);
		    }	
			console.log('PACKAGES TO SAVE ', $scope.packages );
		}  // End Multipacks Identical 

        if ($scope.advanced_options.bill_to_account == "")
        	$scope.advanced_options.bill_to_party = ""; 

         
        $scope.shipment = {
        		 carrier_id   : $scope.car.carrier_id,
        		 service_code : $scope.servicetype,
        		 ship_to      : $scope.shipTo,
         		 ship_from    : $scope.ship_from,
         		 packages     : $scope.packages,
         		 advanced_options : $scope.advanced_options 
        } 
        
        $scope.shipments.push($scope.ship); 
        $scope.shipRequest = {
        		shipment : $scope.shipment,
        		
        }
        
                      
        console.log('SHIMENT TO CREATE', $scope.shipRequest); 
        console.log('SELECTED PRINTER : ' , $rootScope.selectedPrinter);
        
        if ($scope.saveShip == true) {
        	
            packingslipService.createShipment($scope.shipRequest,  $rootScope.user.username, $scope.packingSelected.packingslip_no,
            		$scope.advanced_options.bill_to_account).success(function (response) {
            	$scope.shipResul = response;
            	console.log("RESUL", $scope.shipResul);
            	if ($scope.shipResul.result != 'Y'){
            		alert('Error : '  + $scope.shipResul.badRequest.errors[0].message );
            		$scope.clickSaving = false;
            	} else {
            		$scope.clickSaving = false;
            		alert('Shipment has been processed , Click to print label'); 
            		
            		$window.open($scope.shipResul.labelResponse.label_download.pdf, '_blank');
            		zebra_print($rootScope,$scope, $scope.shipResul.labelResponse.label_download.zpl, $rootScope.selectedPrinter);
            		packingslipService.sendAsns($scope.shipResul.shipDetails.ship).success(function (response) {
            			console.log('ASN SENT...')
            		});
            	}
            	
            });
        	
        }else{
        	$scope.clickSaving = false;
        }
        

        
        
	}
	

    packingslipService.getPendingPackings().success(function (response) {
        //Dig into the responde to get the relevant data
        $scope.pendingPackings = response
        console.log('Packings Resu : ' +  $scope.pendingPackings.soId);
        iniciar_tabla('#tbPackings');
    });
   					 

    
	$scope.clickShipVia = function(){   // Pending Quotes - Admin Dash
		console.log('Index : ' +  $scope.car.carrier_id);
		console.log('Pack Dimension Selected ' , $scope.packageDimension);

	}
	
	$scope.generatemultiPack = function() {
		console.log('Generating Packages ' , $scope.packageDimension);
		$scope.rebuildMulti = false;
	    
	    if ($scope.firstQty == ''){
	    	$scope.firstQty = $scope.noPacks;
	    } else {
	    	if ($scope.firstQty != $scope.noPacks)
	    		$scope.rebuildMulti = true
	    }
		
	    if ($scope.rebuildMulti)  {
			$scope.packagesTemp = [];
			$scope.countPack = 0;
		    for (var j=0; j < $scope.noPacks; j++){   
				var packTemp =  {
						packageType : '',
						packageDimension : '',	
						weight : {
							value : '',
							unit : "pound"
						} ,
						dimensions : {}
				}
				$scope.packagesTemp.push(packTemp);
		    }
	    }
	    console.log('LIST PACK TO SEND ',  $scope.packagesTemp);
		
	}
	
	
	$scope.validMulti = function() {
		$scope.validateShip = true;
		
		
		
		console.log("Validating");
		for (var j=0; j < $scope.noPacks; j++){   
			if (($scope.packagesTemp[j].weight.value == '') || 	($scope.packagesTemp[j].weight.value == 0))
				$scope.validateShip = false;
			if ($scope.packagesTemp[j].packageDimension == '') {
				$scope.validateShip = false;
			} else {
				if (($scope.packagesTemp[j].dimensions.height == '') || ($scope.packagesTemp[j].dimensions.height == 0))
					$scope.validateShip = false;
				if (($scope.packagesTemp[j].dimensions.length == '') || ($scope.packagesTemp[j].dimensions.length == 0))
					$scope.validateShip = false;
				if (($scope.packagesTemp[j].dimensions.widht == '') || ($scope.packagesTemp[j].dimensions.widht == 0))
					$scope.validateShip = false;
			}
			console.log("Validating",j);
		} 
		
	}
	
	$scope.noPacksChange = function() {
		if ($scope.noPacks > 1) {
			$scope.multiPackages=true;
			
		}else {
			$scope.multiPackages=false;
		}
				
	}	
	
	$scope.movenextPack = function() {
		
		if ($scope.countPack < $scope.noPacks-1) {
			$scope.countPack = $scope.countPack + 1;
		}
	}
	
	$scope.movepreviousPack = function() {
		
		if ($scope.countPack > 0) {
			$scope.countPack = $scope.countPack - 1;
		}
	}
	
    $scope.getPackingDetails = function(index) {
        
    	$window.open('./sealhome#/create-shipment-packing?packNro='+$scope.pendingPackings[index].packingSlipno, '_self');
    }   
    
    $scope.printPdf = function(index) {
    	$window.open($rootScope.labels[index].labelPdf, '_blank');
    }
    
    $scope.cancelShip = function() {
    	
		  packingslipService.cancelShipment($scope.cancelRequest).success(function (response) {
		        $scope.resultCancel = response;
		        console.log("Result Cancel : ", $scope.resultCancel);
		        $scope.modalInstance.close();
		        if ($scope.resultCancel.approved == true){
		        	notificationService.success('Shipment Sucessfully canceled');
		        	 $scope.getTodayShipments();
		        }
          });
    	
    }
    
    
	$scope.modalDelete = function (pack, id, apiLabelId, apiShippingId) {
		console.log("PackingSlip to delete : " , pack );
		$scope.packtoCancel = pack;
	    $scope.cancelRequest = {api_label_id : apiLabelId , shipping_id : id, api_shipping_id : apiShippingId};
	    
	    $scope.modalInstance = $uibModal.open({
	    	ariaLabelledBy: 'modal-title',
	        ariaDescribedBy: 'modal-body',
	    	templateUrl: 'borrar.html',
	        scope: $scope,
	        size: 'sm'	        
	    });
	}	
	
	$scope.cancel = function () {		
		$scope.modalInstance.dismiss('cancel');
	};
    
    
    
  }); 
    
    /**************************************************************************************
     * 
     *                                 CUSTOMER CONTROLLER
     **************************************************************************************/


    app.controller('customerController', ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log','packingslipService',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log, packingslipService) {

	var self = this;
	self.simulateQuery = false;
	self.isDisabled    = false;
	self.repos         = loadCustomers();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange;
	self.searchTextChange  = searchTextChange;
	$rootScope.customer = {};
	$rootScope.foundCust = 0;
	$rootScope.labels = []
	
	
	function querySearch (query) {
		  var results = query ? self.repos.filter( createFilterFor(query) ) : self.repos,
		      deferred;
		  if (self.simulateQuery) {
		    deferred = $q.defer();
		    $timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
		    return deferred.promise;
		  } else {
		    return results;
		  }
		}
		function searchTextChange(text) {
		  $log.info('Text changed to ' + text);
		  $rootScope.customer.customerName = text;
		  $rootScope.foundCust = 0;
		}
		function selectedItemChange(item) {
		  $log.info('Item changed to ' + JSON.stringify(item));
		  $rootScope.customer.id = item.sid;
		  $rootScope.customer.customerName = item.display;
		  $rootScope.customer.contactName = item.contact;
		  $rootScope.customer.contactEmail = item.email;
		  $rootScope.customer.contactPhone = item.phone;
		  $rootScope.customer.webSite = item.webSite
		  $rootScope.customer.teslaSubContrator = item.tesla;
		  $rootScope.customer.cutomerOrigin = item.origin;
		  
		  
		  packingslipService.getLabelsByCustomer($rootScope.customer.id).success(function (response) {
			        //Dig into the responde to get the relevant data
			        $scope.labelsCustomer = response;
			        
			        console.log('LABELS CUSTOMER FOUND : ', $scope.labelsCustomer);
			        $rootScope.labels = $scope.labelsCustomer.shipmentList
			        console.log('LABELS  : ', $scope.labels);  ;
			        iniciar_tabla('#tbLabels');
			        $rootScope.foundCust = 1; 
			        //$scope.labels = [{packingSlipno: "9959S110290", serviceDesc: "fedex_ground",shipTo: "036751A2",shipmentCost: 16.8}]
			        
		  });
		  
		  packingslipService.getPendingPackings().success(function (response) {
		        //Dig into the responde to get the relevant data
		        $scope.pendingPackings = response
		        console.log('Packings Resu : ' +  $scope.pendingPackings.soId);
		        iniciar_tabla('#tbPackings');
		  });
		  
		    
		  $log.info("Root Customer : " + $rootScope.customer.contactName );
		  
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadCustomers() {
		    var url = "./list-customers-shipping";
		    var sourcetransfer = [];
		    $scope.sourceTransfer = {};
		    
		   $http.get(url)
		        .success(function(data, status, headers, config) { 
		            $scope.sourceTransfer = data; 
		            console.log("Size Source : " + $scope.sourceTransfer.length) 
		            
		            
		    	    for (var j=0; j < $scope.sourceTransfer.length; j++){   
		    	    	//console.log("ITEM : " + $scope.sourceTransfer[j].customerName);
		    	    	if  ($scope.sourceTransfer[j].customerName == null){
		    	    		$scope.sourceTransfer[j].customerName = 'No Name'
		    	    	}
		    	    	var city = {display : $scope.sourceTransfer[j].customerName, value : $scope.sourceTransfer[j].customerName.toLowerCase() , sid : $scope.sourceTransfer[j].customerId,  contact : $scope.sourceTransfer[j].city , phone : $scope.sourceTransfer[j].contactPhone, webSite : $scope.sourceTransfer[j].webSite, email : $scope.sourceTransfer[j].contactEmail , tesla : $scope.sourceTransfer[j].teslaSubContrator, origin : $scope.sourceTransfer[j].cutomerOrigin };
		             	sourcetransfer.push(city);
		    	    }

		        })
		        .error(function(data, status, headers, config) {
		          console.log("Error");
		      });
		    return  sourcetransfer;
		    
		}			
		
		/**
		 * Create filter function for a query string */
		 
		function createFilterFor(query) {
		  var lowercaseQuery = angular.lowercase(query);
		  return function filterFn(item) {
		    return (item.value.indexOf(lowercaseQuery) === 0);
		  };
		}
  
  
  
  /*    END Autocomplete Customer ex[andable     */	


  }]);




