var app = 
  angular.module("services", []).
  factory('packingslipService', function($http) {

    var packingList = {}
    var carriersApi =  {}
    var shipmentResponse = {}
    $http.defaults.headers.post["Content-Type"] = "application/json";
    
    packingList.getPendingPackings = function() {
    	
        return $http({
            method: 'GET', 
            url: './shipping/packingslips/List'
        });
    }
    
    packingList.getCarriersApi = function() {
    	
        return $http({
            method: 'GET', 
            url: './shipping/carriers/list'
        });
    }
    
    
    packingList.getPackingById = function(id) {
    	
        return $http({
            method: 'GET', 
            url: './shipping/packingslips/id?id='+id
        });
    	
    }
    
    
    packingList.createShipment = function(ships, username, packno, custshipAcc) {
    	var dataShips = {ship_re : ships, ship_re_jason : JSON.stringify(ships), packingslipNo : packno, user : username, cust_ship_account : custshipAcc }; 
    	console.log("DATA SHIP" ,dataShips);
    	console.log("JSON REQUEST" , JSON.stringify(ships));
        return $http({
            method: 'POST', 
            url: './shipping/packingslips/create-shipment',
            data : dataShips
        });
    }
    
    
    packingList.getCustomers = function() {
        return $http({
            method: 'GET', 
            url: "./list-customers-exp"
        });
    	
    }
    
    
    packingList.getLabelsByCustomer = function(idCustomer) {
        return $http({
            method: 'GET', 
            url: "./shipping/labels/byCustomer?customerId="+idCustomer
                  
        });
    	
    }
    
    packingList.getLabelsByToday = function(idCustomer) {
        return $http({
            method: 'GET', 
            url: "./shipping/labels/byToday"
                  
        });
    	
    }
    
    
    packingList.getPackDimensions = function(idCustomer) {
        return $http({
            method: 'GET', 
            url: "./shipping/packdimensions/List"
                 
        });
    	
    }
    
    
    packingList.sendAsns = function(shipment) {
    	var dataAsn = {id_shipment : shipment.id , packingslip_no : shipment.packingSlipno};
    	console.log('ASN TO SEND' , dataAsn ); 
        return $http({
            method: 'POST',  
            url: "./shipping/send-asn",  
            data : dataAsn 
        });
    	
    }
    
    packingList.cancelShipment = function(voidedLabel) {
    	//var dataAsn = {api_label_id : voidedLabel , packingslip_no : shipment.packingSlipno};
    	console.log('Cancel Shipment' , voidedLabel ); 
        return $http({
            method: 'POST',  
            url: "./shipping/labels/voidLabel", 
            data : voidedLabel    
        });
    	
    }
    
    
    
    return packingList;
  });