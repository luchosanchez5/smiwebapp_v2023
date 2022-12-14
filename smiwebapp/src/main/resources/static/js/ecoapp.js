
var app = angular.module("appEco",['ngRoute','jlareau.pnotify','chart.js','ngProgress','ui.bootstrap','vsGoogleAutocomplete','ngFileUpload','directives','thatisuday.dropzone','ngMaterial','angularPromiseButtons','ngAnimate',
	'ngFileUpload','ngResource','services','controllers']);



/*function dataService ($http, $q) {  
    return {
        getAll: getAll
    }

    function getAll () {
        var defered = $q.defer();
        var promise = defered.promise;

        $http.get('./q_quotesestimatoryear?year=2018')
            .success(function(data) {
                defered.resolve(data);
            })
            .error(function(err) {
                defered.reject(err)
            });

        return promise;
    }
}*/


/// ------------------------------------------------------------------------------------------------
/// --------------------          SERVICES FOR SMIQUOTING              -----------------------------
/// ------------------------------------------------------------------------------------------------
/// ------------------------------------------------------------------------------------------------


function showItemsByQuote($http,$rootScope, $scope,baseUrl,qActual) {
	
	
	listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);
	
}


function printSheetCost($http,$rootScope, $scope,baseUrl,itemPrint) {

	$scope.swPrint = '0'; 
	var filename = '/sheetcostForm';  
	//console.log('Item to print : ' , itemPrint.idItem);
	$scope.itemPrint = itemPrint;
	
	
	$http.get(baseUrl + filename + '?idItem='+$scope.itemPrint.idItem,{responseType:'arraybuffer'}).
	//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
	success(function(response){
		
		
		//alert("Preparing to Print..!");
		$scope.file = response;
	    var file = new Blob([response], {type: 'application/pdf'});
	    var fileURL = URL.createObjectURL(file);
	    
	    var subject = "Example";
	    var message = "TEsting";
	    var fileDir = "Test.docx"
	    $scope.swPrint = '1';	
	    //$window.location = fileURL;
	    window.open(fileURL, '_blank');
	    
	//    window.open("mailto:sanchezluis25@gmail.com" + "?subject=" + subject+"&body="+message+"&attachment="+fileDir,"_self");
	    //FileSaver.saveAs(myData, 'printrep.pdf');
	    //alert("En Reports http wind Buffer");
	
	});

}



function getQuoteDetails($http,$rootScope, $scope,baseUrl,qActual){
	
	
	
	 $http.get(baseUrl + '/quDetail?id='+qActual) 
		.success(function(result){
			{
     		    $scope.quoDet = result;
     		    $scope.currQuote = $scope.quoDet;
	    	    $rootScope.quoActual = $scope.quoDet; 
	    	    
	    	    console.log('**** NEW STATUS AFTER COMPLETED ****', $rootScope.quoActual);  
	    	    $rootScope.currQuote =  $rootScope.quoActual; 
	    	    
							 
	        }
	 });
	
}

function initFilterStatus($http,$scope,baseUrl){  
	
    $scope.filterStatusQuote = {
        statNew: "X",
        inProgress: "X",
        cancelled: "X",
        partialEstimate: "X",
        completeEstimate: "X",
        feedbackPending: "X",
        orderReceived: "X",
        onHold: "X",
        complete: "X"
   }
  
	
	
}


function customersAutocomplete($http,$scope,baseUrl,$timeout, $q, $log){ 


// ******************************
// Internal methods
// ******************************
/**
 * Search for repos... use $timeout to simulate
 * remote dataservice call.
 */

$scope.customers = [];

	$http.get(baseUrl + "/list-customers").success(function (data) {      
		$scope.customers = data; 
		return "0"
	}).then(function (response){
		//alert("Promise Leads Ready");
		console.log("Customers After Promise " + $scope.customers.length);
		
		iniciar_tabla('#tbCust') 
		//$('#tbLead').DataTable({responsive: true});
		
		//alert("Prueba " + $scope.leads.sellers);
		

	});




}

function newmaterial($http,$scope,baseUrl){
	
	$scope.materialsnew = [];
	$http.get(baseUrl + "/list-newmaterial").success(function (data) {      
		$scope.materialsnew = data;     
        $scope.newMat = $scope.materialsnew[0];

	});	
}


function listAllsubStatus($http,$scope,baseUrl,quotestatus){
	
	$scope.subStatus = [];
	$scope.quotesubStatus = [];
	console.log("SUBSTATUS (Status)   ==> " ,$scope.quotestatus );
	$http.get(baseUrl + "/list-quotes-substatus?quotestatus="+quotestatus).success(function (data) {      
		$scope.subStatus = data;    
		$scope.quotesubStatus = data;
		console.log("SUBSTATUS  ==> " ,$scope.subStatus );
       // $scope.newMat = $scope.materialsnew[0];

	});	
}


function query_quotesestimatoryear($http,$scope,baseUrl,year){
	
	$scope.qresul = [];
	$http.get(baseUrl + "/querys/quotesEstimatorsYear?year="+year).success(function (data) {      
		$scope.qresul = data;     
		//iniciar_tabla('#tbqestyear');
		init_buttons_table("#tbqestyear");

	});	
}

function query_quotessummary($http,$scope,baseUrl,year){
	
	$scope.qSumm = [];
	var url = ''
	if (year != 'All') {
		url = "/querys/findsummaryquotesYear?year="+year
	}else{
		url = "/querys/findsummaryquotes"
	}
	
	$http.get(baseUrl + url).success(function (data) {      
		$scope.qSumm = data;     
		//iniciar_tabla('#tbqestyear');


	});	
}


function query_quotessummarySeller($http,$scope,baseUrl,year,userId){
	
	$scope.qSumm = [];
	$http.get(baseUrl + "/querys/findsummaryquotesSeller?year="+year+"&userId="+userId).success(function (data) {      
		$scope.qSumm = data;     
		//iniciar_tabla('#tbqestyear');
	});	
}


function query_quotessummaryEstimator($http,$scope,baseUrl,year,userId){
	
	$scope.qSumm = [];
	$http.get(baseUrl + "/querys/findsummaryquotesEstimator?year="+year+"&userId="+userId).success(function (data) {      
		$scope.qSumm = data;     
		//iniciar_tabla('#tbqestyear');
	});	
}

function query_lostquotessummarySeller($http,$scope,baseUrl,year,userId){
	
	$scope.qSummLost = [];
	$http.get(baseUrl + "/querys/findsummarylostquotesSeller?year="+year+"&userId="+userId).success(function (data) {      
		$scope.qSummLost = data;     
		console.log('QUOTE LOST : ' ,  $scope.qSummLost);
		//iniciar_tabla('#tbqestyear');
	});	
}



function findActualSeller($http,$scope,baseUrl,user){
	
	$http.get(baseUrl + '/findOneSellerUser?username='+user).
	success(function(response){
		console.log("SELLER ACTUAL REPOSNSE " ,response );
		$scope.actSel = response;

	}); 
}	


function findActualEstimator($http,$scope,baseUrl,user){
	
	$http.get(baseUrl + '/findOneEstimatorUser?username='+user).
	success(function(response){
		
		$scope.actEst = response;

	}); 
}


function findNotificationsEstimator($http,$scope,baseUrl,user){
	console.log('Notificacions User :' , user);
	$http.get(baseUrl + '/notifications/estimator?username='+user).
	success(function(response){
		console.log('Notificacions Results :' , response);
		$scope.notesEstimators = response;

	}).then(function (response){
		iniciar_tabla('#tbNotifications') 
	});	 
}	

function findTotalNotificationsEstimator($http,$scope,$rootScope,baseUrl,user){
	console.log('Notificacions User :' , user);
	$http.get(baseUrl + '/notifications/estimatorcount?username='+user).
	success(function(response){
		console.log('Notificacions Results :' , response);
		$scope.totalnotesEstimator = response;
		$rootScope.totNotEst = $scope.totalnotesEstimator;
		

	}); 
	
	

}

function findTop4NotificationsEstimator($http,$scope,baseUrl,user){
	console.log('Notificacions User :' , user);
	$scope.totalTop4notesEstimator = [];
	$http.get(baseUrl + '/notifications/estimatorTop4?username='+user).
	success(function(response){
		console.log('Details Notificacions Results :' , response);
		$scope.totalTop4notesEstimator = response;

	}); 
}




function query_quotesselleryear($http,$scope,baseUrl,year){
	
	$scope.qresulSel = [];
	$http.get(baseUrl + "/querys/quotesSellersYear?year="+year).success(function (data) {      
		$scope.qresulSel = data;     
		//iniciar_tabla('#tbqestyear');
		console.log('TABLE SELLER YEAR',$scope.qresulSel);
		init_buttons_table('#tbqselleryear'); 

	});	
}

function init_buttons_table(name){
	
	
	angular.element(document).ready(function() {
        var handleDataTableButtons = function() {
          if ($(name).length) {
            $(name).DataTable({
              dom: "Bfrtip",
              buttons: [
                {
                  extend: "copy",
                  className: "btn-sm"
                },
                {
                  extend: "csv",
                  className: "btn-sm"
                },
                {
                  extend: "excel",
                  className: "btn-sm"
                },
                {
                  extend: "pdfHtml5",
                  className: "btn-sm"
                },
                {
                  extend: "print",
                  className: "btn-sm"
                },
              ],
              responsive: true,
              pageLength:20
            });
          }
        };

        TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              handleDataTableButtons();
            }
          };
        }();

        $('#datatable1').dataTable();
        $('#datatable-keytable').DataTable({
          keys: true
        });

        //$('#tbfmeca1').DataTable();

        $('#datatable-scroller').DataTable({
          ajax: "js/datatables/json/scroller-demo.json",
          deferRender: true,
          scrollY: 380,
          scrollCollapse: true,
          scroller: true
        });

        var table = $('#datatable-fixed-header').DataTable({
          fixedHeader: true
        });

        TableManageButtons.init();
      });	 



}



function userInterface($http,$scope,$rootScope,baseUrl,$filter,role,username,$window){
	
       console.log("User Interface Rol : " , role);
	   if (role == '1'){
		   dashboard($http,$rootScope, $scope,baseUrl,$filter,$window);
	   }else if (role == '2'){
		   dashboardSeller($http,$rootScope,$scope,baseUrl,$filter,$window) 
	   }else if (role == '3'){
		   dashboardEstimator($http,$rootScope, $scope,baseUrl,$filter,$window);
	   }else if (role == '4'){
		   dashboard($http,$rootScope, $scope,baseUrl,$filter,$window);	   
	   }

	
}

function listmatdetailsitems($http,$scope,baseUrl,id){
	
	$scope.matdetails = [];
	$scope.totMat = 0;
	$http.get(baseUrl + "/list-matdetailsitem?idItem="+id).success(function (data) {      
		$scope.matdetails = data;  
		$scope.totMat = $scope.matdetails.length;
		console.log('******************* MAT LIST SIZE :' + $scope.totMat); 
		$scope.getTotalMatCost();
		//iniciar_tabla('#tbQitemsType');
	/*	angular.element(document).ready( function () {
		    //dTable = $(nombre).DataTable({responsive: true});
				//$(nombre).DataTable({"pageLength": 50});	listmatdetailsitems
				$('#tbMaterials2').DataTable({
			        "paging":   false, "searching": false}); :
		    
	}).then(function (response){
		$scope.getTotalLabCost(); 	});	*/
	}).then(function (response){
		$scope.getTotalMatCost();	
		$scope.updateTotals(); 
	});	
}

function listlabdetailsitems($http,$scope,baseUrl,id){
	
	$scope.labdetails = [];
	$scope.totLab = 0;
	$http.get(baseUrl + "/list-labdetailsitem?idItem="+id).success(function (data) {     
		$scope.labdetails = data;  
		$scope.totLab = $scope.labdetails.length;
		console.log('********************* LAB LIST SIZE :' + $scope.totLab);
		$scope.getTotalLabCost();
		//iniciar_tabla('#tbQitemsType');
	/*	angular.element(document).ready( function () {
		    //dTable = $(nombre).DataTable({responsive: true});
				//$(nombre).DataTable({"pageLength": 50});	
				$('#tbMaterials3').DataTable({
			        "paging":   false, "searching": false});
		    
		});	*/
	}).then(function (response){
		$scope.getTotalLabCost(); 
		$scope.updateTotals();
	});	
}


function deletetempfiles($http,$scope,baseUrl,sessionId){
	
	
	$http.put(baseUrl + "/quotes/temps/delete?sessionId="+sessionId).then(function (response) {      
		
		console.log("Deleting Old Session " + response);

	}, function error(response) {
		
		console.log("Error Deleting");
	});	
}

function listmachines($http,$scope,baseUrl){
	
	$scope.machines = [];
	$http.get(baseUrl + "/list-machines").success(function (data) {      
		$scope.machines = data;     
		iniciar_tabla('#tbMachines');

	});	
}


function listteslamodels($http,$scope,baseUrl){
	
	$scope.tesla = [];
	$http.get(baseUrl + "/list-tesla-models").success(function (data) {      
		$scope.tesla = data;     

	});	
}


function listsalescodes($http,$scope,baseUrl){
	
	$scope.salesCodes = [];
	$http.get(baseUrl + "/list-salescode-exp").success(function (data) {      
		$scope.salesCodes = data;     
		console.log("SALES CODES : " ,$scope.salesCodes );
	});	
}


function listincoterms($http,$scope,baseUrl){
	
	$scope.incoterms = [];
	$http.get(baseUrl + "/list-incoterms").success(function (data) {      
		$scope.incoterms = data;     

	});	
}

function listtools($http,$scope,baseUrl){
	
	$scope.tools = [];
	$http.get(baseUrl + "/list-tools").success(function (data) {      
		$scope.tools = data;     
		iniciar_tabla('#tbTools');

	});	
}

function listmeasureunits($http,$scope,baseUrl){
	
	$scope.measureunits = [];
	$http.get(baseUrl + "/list-measureunits").success(function (data) {      
		$scope.measureunits = data;     
		iniciar_tabla('#tbMeasures');

	});	
}

function listquotesseller($http,$scope,baseUrl, user){
	
	$scope.quotesseller = [];
	console.log('USER SELLER ' ,user);
	$http.get(baseUrl + "/list-quotesseller?userId="+user).success(function (data) {      
		$scope.quotesseller = data;     
		console.log('QUOTE SELLERS',$scope.quotesseller );
		//iniciar_tabla('#tbQitemsType');

	});	
}


function listPendingItemsSeller($http,$scope,baseUrl, user){
	
	$scope.itemsSeller = []; 
	$http.get(baseUrl + "/list-pendingitems?idSeller="+user).success(function (data) {      
		$scope.itemsSeller = data;  
		iniciar_tabla('#tbQitemsType');
	}).then(function (response){ 
		init_buttons_table('#tbQuotesPendingSeller');  
	});	
}


function listPendingItemsEstimator($http,$scope,baseUrl, year){
	
	$scope.itemsSeller = [];
	var url = ''
		
	//alert("Items");	
	
	if (year != 'All')
		url = "/list-pendingitemsEst-year?year="+year
		else
		url = "/list-pendingitemsEst"	
	
	
	$http.get(baseUrl + url).success(function (data) {      
		$scope.itemspEst = data;     
		//iniciar_tabla('#tbQitemsType');
	}).then(function (response){
		init_buttons_table('#tbQuotesPendingEst'); 
	});	
}


function sellergraphicprofile($http,$scope,baseUrl,sel){
	
	
	$http.get(baseUrl + "/sellers/sellergrprofile?idSeller="+sel).success(function (data) {
		
		//alert("Graphic Profile");
	    $scope.daily1 = data;
	    
	    var dataGr = [];
	    var d1 = [];
	    var vlabels = [];
	    var dataGr = $scope.daily1;
	    var vdata = [];

	   
	    for (var j=0; j < dataGr.length; j++){
	    	
	    	var inicio = (new Date(dataGr[j].day).getTime());
	    	d1.push([inicio, dataGr[j].totQuotes]);
	    	//alert("DAY : " + inicio + "  Total : " + inicio);
	    	vlabels.push(dataGr[j].name);
	    	vdata.push(dataGr[j].total);
	    }
		
        var data1 = d1;
	    
	    $scope.labels1Sel = vlabels;
	    $scope.series1Sel = ['Total quotes'];
	   /* $scope.data = [
	      [65, 59, 80, 81, 56, 55, 40],
	      [28, 48, 40, 19, 86, 27, 90]
	    ];*/
	    
	    $scope.data1Sel = [vdata];
	    console.log(JSON.stringify($scope.data));
	    //$scope.data = [[65, 59, 80, 81, 56, 55, 40]];
	    
	    $scope.onClick = function (points, evt) {
	      console.log(points, evt);
	    };
	    $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
	    $scope.options1Sel = {
	      scales: {
	        yAxes: [
	          {
	            id: 'y-axis-1',
	            type: 'linear',
	            display: true,
	            position: 'left'
	          },
	          {
	            id: 'y-axis-2',
	            type: 'linear',
	            display: false,
	            position: 'right'
	          }
	        ]
	      }
	    };		    

});
	
	
}


function percentsitemseller($http,$scope,baseUrl,sel){
	
	$scope.pitems = [];
	$http.get(baseUrl + "/sellers/percentitemsseller?idSeller="+sel).success(function (data) {      
		$scope.pitems = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
}

function activitiesseller($http,$scope,baseUrl,sel){
	
	$scope.sact = [];
	$http.get(baseUrl + "/sellers/activities?idSeller="+sel).success(function (data) {      
		$scope.sact = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
}


function listquotessellerbyid($http,$scope,baseUrl,sel){
	
	$scope.quotes = [];
	$http.get(baseUrl + "/list-quotessellerbyid?idSel="+sel).success(function (data) {      
		$scope.quotes = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
	
	
	$http.get(baseUrl + "/list-sharedquotessellerbyid?userId="+sel).success(function (data) {      
		$scope.sharedQuotes = data; 
		console.log("**************SHARED QUOTES ***** ",  $scope.sharedQuotes)	
		//iniciar_tabla('#tbQitemsType');

	});	
}


function listfilterquotes($http,$scope,baseUrl,fRec,est,opt){
	
	$scope.quotes = [];
	//console.log("/list-filter-quotes?s0="+fRec.statNew+"&s1"+fRec.inProgress+"&s2"+fRec.cancelled);

	$http.get(baseUrl + "/list-filter-quotes?opt="+opt+"&s0="+fRec.statNew+"&s1="+fRec.inProgress+"&s2="+fRec.cancelled+"&s3="+fRec.partialEstimate
			+"&s4="+fRec.completeEstimate+"&s5="+fRec.feedbackPending+"&s6="+fRec.onHold+"&s7="+fRec.orderReceived+"&s8="+fRec.complete+"&est="+est).success(function (data) {      
		$scope.quotes = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
}

function listfilterquotesEst($http,$scope,baseUrl,fRec,user){
	
	$scope.quotes = [];
	console.log("/list-filter-quotes-est?s0="+fRec.statNew+"&s1="+fRec.inProgress+"&s2="+fRec.cancelled+"&s3="+fRec.partialEstimate
			+"&s4="+fRec.completeEstimate+"&s5="+fRec.feedbackPending+"&s6="+fRec.onHold+"&s7="+fRec.orderReceived+"&s8="+fRec.complete+"&userId="+user);

	$http.get(baseUrl + "/list-filter-quotes-est?s0="+fRec.statNew+"&s1="+fRec.inProgress+"&s2="+fRec.cancelled+"&s3="+fRec.partialEstimate
			+"&s4="+fRec.completeEstimate+"&s5="+fRec.feedbackPending+"&s6="+fRec.onHold+"&s7="+fRec.orderReceived+"&s8="+fRec.complete+"&userId="+user).success(function (data) {      
	     $scope.quotes = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
}

function listfilterquotesSeller($http,$scope,baseUrl,fRec,user){
	
	$scope.quotes = [];
	console.log("/list-filter-quotes-sel?s0="+fRec.statNew+"&s1="+fRec.inProgress+"&s2="+fRec.cancelled+"&s3="+fRec.partialEstimate
			+"&s4="+fRec.completeEstimate+"&s5="+fRec.feedbackPending+"&s6="+fRec.onHold+"&s7="+fRec.orderReceived+"&s8="+fRec.complete+"&userId="+user);

	$http.get(baseUrl + "/list-filter-quotes-sel?s0="+fRec.statNew+"&s1="+fRec.inProgress+"&s2="+fRec.cancelled+"&s3="+fRec.partialEstimate
			+"&s4="+fRec.completeEstimate+"&s5="+fRec.feedbackPending+"&s6="+fRec.onHold+"&s7="+fRec.orderReceived+"&s8="+fRec.complete+"&userId="+user).success(function (data) {      
	     $scope.quotes = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
}


function listpendingquotesEst($http,$scope,baseUrl,user,year){
	
	$scope.quotes = [];
	var url = '';
	if (year == 'All')
		url = "/list-filter-quotes-est?s0=0&s1=1&s2=X&s3=3&s4=X&s5=X&s6=X&s7=X&s8=X&userId="+user
	else
		url = "/list-filter-quotes-est-year?s0=0&s1=1&s2=X&s3=3&s4=X&s5=X&s6=X&s7=X&s8=X&userId="+user+"&year="+year
		
		
	console.log("/list-filter-quotes-est?s0=0&s1=1&s2=X&s3=3&s4=X&s5=X&s6=6&s7=X&s8=X&userId="+user);

	$http.get(baseUrl + url).success(function (data) {      
		$scope.quotesPendEst = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
}



function listitemact($http,$scope,baseUrl,itemId){
	
	$scope.itemActivitys = [];
	$http.get(baseUrl + "/list-itemactivitys?itemId="+itemId).success(function (data) {      
		$scope.itemActivitys = data;    
    	//iniciar_tabla('#tbItemsquote');
    	//alert("Found Items :" + $scope.itemsquote.length)

	});	
}

function listar_itemsActivitys($http,$scope,baseUrl,le){	
	
	$scope.itemActivitys = [];
	$http.get(baseUrl + "/list-itemactivitys?itemId="+le).success(function (data) {  
		$scope.itemActivitys = data;        
		//iniciar_tabla('#tbActivity')
	});
	
}

function listquotenotes($http,$scope,baseUrl,quoteId){
	
	$scope.notesquote = [];
	$http.get(baseUrl + "/list-quotesnotes?id="+quoteId).success(function (data) {      
		$scope.notesquote = data;    
    	//iniciar_tabla('#tbItemsquote');
    	//alert("Found Items :" + $scope.itemsquote.length)

	});	
}

function listquotenotesbytype($http,$scope,$rootScope,baseUrl,quoteId,type){
	
	$scope.notesquote = [];
	$http.get(baseUrl + "/list-quotesnotesbytype?id="+quoteId+"&type="+type).success(function (data) {      
		$scope.notesquote = data;    
		$rootScope.notesq =  $scope.notesquote;
    	//iniciar_tabla('#tbItemsquote');
    	alert("Found Items :" + $scope.notesquote.length)

	});	
}

function listquoteitems($http,$scope,baseUrl,quoteId){
	
	$scope.itemsquote = [];
	$http.get(baseUrl + "/list-itemsquote?id="+quoteId).success(function (data) {      
		$scope.itemsquote = data;    
    	//iniciar_tabla('#tbItemsquote');
    	//alert("Found Items :" + $scope.itemsquote.length)

	});	
}

function listAllitems($http,$scope,baseUrl){ 
	
	
	$scope.itemsAll = [];
	$scope.showTable = false;
	console.log("ITEMS ALL TEST")
		
		$http.get(baseUrl + "/list-itemsall").success(function (data) {      
			$scope.itemsAll = data;    
		}).then(function (response){
			$scope.showTable = true;
			init_buttons_table('#tbItemsQuery');
		});	
	
	//$scope.showTable = true;
	

}




function listAllitemsByKeyword($http,$scope,baseUrl,keyword){ 
	
	
	
	$scope.itemsAll = [];
	$scope.showTable = false;
	console.log("ITEMS BY KEYWORD")
		
	$http.get(baseUrl + "/list-itemsByKeyword?keyword="+keyword).success(function (data) {      
			$scope.itemsAll = data;    
	}).then(function (response){
			$scope.showTable = true;
			init_buttons_table('#tbItemsQuery');
	});	
	

	

}




function listOrdereditems($http,$scope,baseUrl,year){ 
	
	
	$scope.itemsOrd = []; 
	var url = ''
		
		if (year != 'All'){
			url =  "/list-ordereditems-year?year="+year
			//alert("Year : " + year);
		}	
		else
		    url =  "/list-ordereditems"	

		
		$http.get(baseUrl + url).success(function (data) {      
			$scope.itemsOrd = data;  
			console.log('ITEMS ORDERED : ' , $scope.itemsOrd );
		}).then(function (response){
			init_buttons_table('#tbItemsQueryOrd');
		});	
	

}


function listOrdereditemsEst($http,$scope,baseUrl,year){ 
	
	
	$scope.itemsOrd = []; 
	var url = ''
	
		if (year != 'All'){
			url =  "/list-ordereditems-year?year="+year
			//alert("Year : " + year);
		}	
		else
		    url =  "/list-ordereditems"		
		
	
		
		$http.get(baseUrl + url).success(function (data) {      
			$scope.itemsOrd = data;  
			console.log('ITEMS ORDERED : ' , $scope.itemsOrd );
		}).then(function (response){
			init_buttons_table('#tbItemsQueryOrd');
		});	
	

}




function listOrdereditemsEstNouse($http,$scope,baseUrl,id, year){ 
	
	
	$scope.itemsOrd = [];
	
		
		$http.get(baseUrl + "/list-ordereditemsEst?idEstimator="+id ).success(function (data) {      
			$scope.itemsOrd = data;    
		}).then(function (response){
			init_buttons_table('#tbItemsQueryOrd');
		});	
	

}


function listquotes($http,$scope,baseUrl,user, role){
	
	$scope.quotes = [];
	$scope.quotesAll = [];
	$scope.sharedQuotes = [];
	
	//alert("USER LIST QUOTES :" + user   +  "  ROLE : " +  role );
	
	if (role == "1") {
		$scope.showTable = false;
		$http.get(baseUrl + "/list-quotes").success(function (data) {      
			$scope.quotes = data;   
			$scope.quotesAll = data;
			//init_buttons_table('#tbQuotesAll');
			//iniciar_tabla('#tbQuotesAll');
		}).then(function (response){
			init_buttons_table('#tbQuotesAll');	
			$scope.showTable = true;
		});	
	}
	
	if (role == "2") {   //Seller
		$scope.showTable = false;
		$http.get(baseUrl + "/list-quotesseller?userId="+user).success(function (data) {      
			$scope.quotes = data; 
			$scope.quotesAll = data;
			//iniciar_tabla('#tbQitemsType');
		}).then(function (response){
			init_buttons_table('#tbQuotesAll');	
			$scope.showTable = true;	
		});	
		
		$http.get(baseUrl + "/list-sharedquotesseller?userId="+user).success(function (data) {      
			$scope.sharedQuotes = data; 
			
			//iniciar_tabla('#tbQitemsType');
	
		});	
		

		
	}	
	
	if (role == "3") {  //Estimator
		$scope.showTable = false;
		$scope.showTable2 = false;
		$http.get(baseUrl + "/list-quoteestimator?userId="+user).success(function (data) {      
			$scope.quotes = data;  
			
			//iniciar_tabla('#tbQitemsType');
		}).then(function (response){
			//init_buttons_table('#tbQuotesAll');	
			//$scope.showTable = true;	
			$scope.showTable2 = true;
		});	
		
		$http.get(baseUrl + "/list-quotes").success(function (data) {      
				
				$scope.quotesAll = data;
				//init_buttons_table('#tbQuotesAll');
				//iniciar_tabla('#tbQuotesAll');
		}).then(function (response){
			init_buttons_table('#tbQuotesAll');	
			$scope.showTable = true;		
		});			
		
	}	
	
}



function listViewQuotes($http,$scope,baseUrl,year){
	

	$scope.viewqAll = [];
	$scope.viewqAllPr = false;


		$http.get(baseUrl + "/querys/findallvquotes?year="+year).success(function (data) {      
			$scope.viewqAll = data;

			//init_buttons_table('#tbVQuotesStatus');

		}).then(function (response){

			$scope.viewqAllPr = true;
			//$scope.showTable = true;
	   	});		
}


function listViewSumQuotesByStatus($http,$scope,baseUrl,year,stat){
	

	$scope.viewqAll = [];
	$scope.viewqAllPr = false; 
    var url = ''
    	
    	if (year != 'All'){   
    		url =  "/querys/findallvquotesByStatus?year="+year+"&status="+stat 
    	}else{
    		url = "/querys/findallvquotesHistoryByStatus?status="+stat
    	}
	
		//$http.get(baseUrl + "/querys/findallvquotesByStatus?year="+year+"&status="+stat).success(function (data) {     
	    $http.get(baseUrl + url).success(function (data) { 
			$scope.viewqAll = data;

		}).then(function (response){
			
			$scope.viewqAllPr = true;
			init_buttons_table('#tbVQuotesStatus');
			$scope.showTable = true;
	   	});		
}



function listViewQuotesPendingAdmin($http,$scope,baseUrl,year){
	

	$scope.viewqAllPending = [];
	var url = ''
		
	    if (year == 'All')
	    	url = "/querys/findallvquotesPendingHistory"
	    	else
	        url = "/querys/findallvquotesPending?year="+year
	

		$http.get(baseUrl + url).success(function (data) {      
			$scope.viewqAllPending = data;
			
		
		}).then(function (response){
			

	   	});		
}


function listViewQuotesSeller($http,$scope,baseUrl,year,userId){
	

	$scope.viewqAllSel = [];
	$scope.viewqAllPrSel = false;

		$http.get(baseUrl + "/querys/findallvquotesSeller?year="+year+"&userId="+userId).success(function (data) {      
			$scope.viewqAllSel = data;
			
		
		}).then(function (response){
			
			$scope.viewqAllPrSel = true;
	   	});		
}

/*function listViewQuotesEstimator($http,$scope,baseUrl,year,userId){
	

	$scope.viewqAllEst = [];
	$scope.viewqAllPrEst = false;

		$http.get(baseUrl + "/querys/findallvquotesEstimator?year="+year+"&userId="+userId).success(function (data) {      
			$scope.viewqAllEst = data;
			
		
		}).then(function (response){
			
			$scope.viewqAllPrEst = true;
	   	});		
}*/


function listViewQuotesSellerByStatus($http,$scope,baseUrl,year,userId,stat){
	

	$scope.viewqAllSel = [];
	$scope.viewqAllPrSel = false;

		$http.get(baseUrl + "/querys/findallvquotesSeller?year="+year+"&userId="+userId+"&status="+stat).success(function (data) {      
			$scope.viewqAllSel = data;
		
		}).then(function (response){
			
			init_buttons_table('#tbVQuotesStatus');
			$scope.viewqAllPrSel = true;
			$scope.showTable = true;
	   	});		
}

function listViewQuotesEstimatorByStatus($http,$scope,baseUrl,year,userId,stat){
	

	$scope.viewqAllEst = [];
	$scope.viewqAllPrEst = false;

		$http.get(baseUrl + "/querys/findallvquotesEstimatorByStatus?year="+year+"&userId="+userId+"&status="+stat).success(function (data) {      
			$scope.viewqAllEst = data;
		
		}).then(function (response){
			
			console.log('RESULT HOLD : ' , $scope.viewqAllEst );
			
			init_buttons_table('#tbVQuotesStatus'); 
			$scope.viewqAllPrEst = true;
			$scope.showTable = true;
	   	});		
}

function listViewQuotesEstimatorOnHold($http,$scope,baseUrl,year,userId,stat){
	

	$scope.viewqAllEstHold = [];
	$scope.viewqAllPrEst = false;
	var url = ''
	if (year == 'All')
		url = "/querys/findallvquotesEstimatorByStatusHistory?userId="+userId+"&status="+stat;
	else	
		url = "/querys/findallvquotesEstimatorByStatus?year="+year+"&userId="+userId+"&status="+stat;

		$http.get(baseUrl + url).success(function (data) {      
			$scope.viewqAllEstHold = data; 
		
		}).then(function (response){
			
			console.log("ON HOLD RESLTS : " , $scope.viewqAllEstHold );
			init_buttons_table('#tbVQuotesStatus'); 
			$scope.viewqAllPrEst = true;
			$scope.showTable = true;
	   	});		
}

function listViewQuotesEstimatorOrdered($http,$scope,baseUrl,year,userId,stat){
	

	$scope.viewqAllEstOrd = [];
	$scope.viewqAllPrEst = false;
	
	var url = ''
		console.log('Year : ', year )
		if (year == 'All')
			url = "/querys/findallvquotesEstimatorByStatusHistory?userId="+userId+"&status="+stat;
		else	
			url = "/querys/findallvquotesEstimatorByStatus?year="+year+"&userId="+userId+"&status="+stat;

		$http.get(baseUrl + url).success(function (data) {      
			$scope.viewqAllEstOrd = data; 
		
		}).then(function (response){
			
			console.log("ON ORDERES RESLTS : " , $scope.viewqAllEst ); 
			init_buttons_table('#tbVQuotesStatusOrdered'); 
			$scope.viewqAllPrEst = true;
			$scope.showTable = true;
	   	});		
}




function listViewOnHoldQuotesEstimator($http,$scope,baseUrl,year,userId){
	

	$scope.viewqOnHoldEst = [];
	$scope.viewqOnHoldEst = false;

		$http.get(baseUrl + "/querys/findallvquotesEstimatorByStatus?year="+year+"&userId="+userId+"&status='7'").success(function (data) {      
			$scope.viewqOnHoldEst = data;
		
		}).then(function (response){
			
			init_buttons_table('#tbVQuotesStatusOnHold'); 
			$scope.viewqOnHoldEst = true;
			$scope.showTable = true;
	   	});		
}



/*function listViewQuotesByStatus($http,$scope,baseUrl,year,user,status){
	

	$scope.viewqAllStat = [];
	$scope.viewqAllPr = false;
	var request = ""
	
	    if ($rootScope.role == '2'){
	    	request = "/querys/findquotesSellerStatus?year="+year+"&userId="+user+"&status="+status
	    }


		$http.get(baseUrl + request).success(function (data) {      
			$scope.viewqAll = data;
			console.log('2 - VIEW QUOTES : ' , $scope.viewqAll);
		
		}).then(function (response){
			console.log("Quotes After"); 
			$scope.viewqAllPr = true;
	   	});		



}*/



function listqitems($http,$scope,baseUrl){
	
	$scope.qitemsType = [];
	$http.get(baseUrl + "/list-qitemstype").success(function (data) {      
		$scope.qitemsType = data;     
		iniciar_tabla('#tbQitemsType');

	});	
}

function listtempitems($http,$scope,baseUrl,username, sessionid){
	
	$scope.itemsTemp = [];

	$http.get(baseUrl + "/list-tempitems?username="+username+"&sessionId="+sessionid).success(function (data) {      
		$scope.itemsTemp = data;    
    	//iniciar_tabla('#tbItemsTemp');

	});	
}

function listMaterials($http,$scope,baseUrl){
	
	//alert('MAterials');
	$scope.materials = [];
	$http.get(baseUrl + "/list-materials").success(function (data) {      
		$scope.materials = data;  
		iniciar_tabla('#tbMaterials');
	


	});	
}

function listCustomers($http,$scope,baseUrl){
	
	$scope.customers = [];

	$http.get(baseUrl + "/list-customers").success(function (data) {      
		$scope.customers = data; 
		//return "0"
	}).then(function (response){
		//alert("Promise Leads Ready");
		console.log("Customers After Promise " + $scope.customers.length);
		iniciar_tabla('#tbCust'); 
	});
	  
}

function listCustomersSeller($http,$scope,baseUrl, username){
	
	$scope.customersSeller = [];

	$http.get(baseUrl + "/sellers/customers?username="+username).success(function (data) {      
		$scope.customersSeller = data; 
		//return "0"
	}).then(function (response){
		//alert("Promise Leads Ready");
		//console.log("Customers Seller After Promise " + $scope.customersSeller.length);
		iniciar_tabla('#tbCustSeller'); 
	});
	  
}

function listCustomersExp($http,$scope,baseUrl){
	
	$scope.customersExp = [];
	$http.get(baseUrl + "/list-customers-exp").success(function (data) {      
		$scope.customersExp = data;        
		//iniciar_tabla('#tbCust');

	});	
}

function listSummarizedExp($http,$scope,baseUrl, partId){
	
	$scope.partExp = [];
	$http.get(baseUrl + '/findOnePartExp?partId='+partId).success(function (data) {      
		$scope.partExp = data;      
		console.log("PArt Master Exzp :",$scope.partExp);
	}).then(function(response){
		
		$http.get(baseUrl + '/list-summarized-exp?partId='+partId).success(function (data) {      
 		   $scope.listSummarized = data; 
		   console.log("List Summarized Exzp :", $scope.listSummarized); 

		});		    
	

	});	
	

	
}

function findpartExp($http,$scope,baseUrl, partId){
	
	$scope.smipartExp = [];
	$scope.matExp = {};
	console.log('URL : ' +  baseUrl + '/findOnePartExp?partId='+partId+"TRIm");
	$http.get(baseUrl + '/findOnePartExp?partId='+partId).success(function (data) {      
		$scope.smipartExp = data;    
		console.log("RESULT ", $scope.smipartExp);
		
		  $scope.matExp.descMaterial = $scope.smipartExp.partDesc;
		  $scope.matExp.partNumber = $scope.smipartExp.partNumber;
		  //$scope.matExp.unitCost = parseFloat($scope.smipartExp.unitCost).toFixed(4);
		  $scope.matExp.unitCost = $scope.smipartExp.unitCost;
		  $scope.matdetail.material.descMaterial = $scope.smipartExp.partDesc;
		  $scope.getPrice();

	});	
	
	
}


function findpartExpEdit($http,$scope,baseUrl, partId){
	
	$scope.smipartExp = [];
	$http.get(baseUrl + '/findOnePartExp?partId='+partId).success(function (data) {      
		$scope.smipartExp = data;    
		console.log("RESULT EDIT PART ", $scope.smipartExp);
		$scope.itemDel.part = {};
		$scope.itemDel.part.partDesc = $scope.smipartExp.partDesc; 
		$scope.itemDel.part.partCost = $scope.smipartExp.unitCost; 
		$scope.itemDel.part.partCode = $scope.smipartExp.partId; 
		$scope.itemDel.part.partNumber = $scope.smipartExp.partId; 
		 
		 console.log("NEW ITEMDEL ", $scope.itemDel);


	});	
	
	
}


function listCustomersAll($http,$scope,baseUrl){
	
	$scope.customersAll = [];
	$scope.customersAll = $scope.customersAll.push($scope.customers);
	$scope.customersAll = $scope.customersAll.push($scope.customersExp);
}


function listIndustries($http,$scope,baseUrl){
	
	$scope.industries = [];
	$http.get(baseUrl + "/list-industries").success(function (data) {      
		$scope.industries = data;        
		iniciar_tabla('#tbIndustries');

	});	
}

function listParts($http,$scope,baseUrl){
	
	$scope.parts = [];
	$http.get(baseUrl + "/list-parts").success(function (data) {      
		$scope.parts = data;   
		console.log('PARTS AVAILABLE : ' ,  $scope.parts);
		iniciar_tabla('#tbParts');

	});	
}


function listPartsExp($http,$scope,baseUrl){
	
	$scope.partsExp = [];
	$http.get(baseUrl + "/list-part-exp-type?type=B").success(function (data) {      
		$scope.partsExp = data;        
		//iniciar_tabla('#tbParts');

	});	
}


function listitemsType($http,$scope,baseUrl){
	
	$scope.itemsType = []; 
	$http.get(baseUrl + "/list-TypeItems").success(function (data) {      
		$scope.itemsType = data;        
		iniciar_tabla('#tbitemsType');

	});
	
	
}

function listitemsstatus($http,$scope,baseUrl,act){
	
	$scope.itemStatus = [];
	
	$http.get(baseUrl + "/list-itemstatus?act="+act ).success(function (data) {      
		$scope.itemStatus = data;    
		

	});
	
}

function listTerms($http,$scope,baseUrl){
	
	$scope.terms = []; 
	$http.get(baseUrl + "/list-terms").success(function (data) {      
		$scope.terms = data;        
		iniciar_tabla('#tbTerms');

	});
	
	
}

function listTermsExp($http,$scope,baseUrl){
	
	$scope.termsExp = [];
	$http.get(baseUrl + "/list-terms-exp").success(function (data) {      
		$scope.termsExp = data;        
		iniciar_tabla('#tbTerms');

	});
	
	
}

function listSellers($http,$scope,baseUrl){
	
	$scope.sellers = [];
	$http.get(baseUrl + "/list-sellers").success(function (data) {      
		$scope.sellers = data; 
		iniciar_tabla('#tbSellers');
		                
	});
	
	
}

function listEstimators($http,$scope,baseUrl){
	
	$scope.estimators = [];
	$http.get(baseUrl + "/list-estimators").success(function (data) {      
		$scope.estimators = data;        
		iniciar_tabla('#tbEstimators');

	});
	
	
}

function listReferrals($http,$scope,baseUrl){
	
	$scope.referrals = [];
	$http.get(baseUrl + "/list-referrals").success(function (data) {      
		$scope.referrals = data;        
		iniciar_tabla('#tbReferrals');

	});
	
	
}



/// ------------------------------------------------------------------------------------------------
/// ----------------------------------END SERVICES SMI QUOTING ---------------------------------------
/// ------------------------------------------------------------------------------------------------

function loadYears($http,$scope,baseUrl){

	$scope.years = [];

    $scope.years = [{
        id: "0",
        descYear: '2022'
      }, {
    	  id: "1",
    	  descYear: '2021'
    		  
      }, {
    	  id: "2",
    	  descYear: '2020'
      }, {
    	  id: "3",
    	  descYear: 'All'		  
    		  
      }];
}    


function cargarValores($http,$scope,baseUrl){

	$scope.tipoStatus = [];

    $scope.tipoSubContrator = [{
        idTipoStatus: "0",
        descTipoStatus: 'Architecture'
      }, {
    	idTipoStatus: "1",
    	descTipoStatus: 'Construction'
      }];
    
    $scope.tipoPagoGasto = [{
        idTipoStatus: "0",
        descTipoStatus: 'Cash'
      }, {
    	idTipoStatus: "1",
    	descTipoStatus: 'Check'
      }, {
    	idTipoStatus: "2",
    	descTipoStatus: 'Debit Card'
      },{
    	idTipoStatus: "3",
    	descTipoStatus: 'Direct Deposit'		
      }];
	
    $scope.tipoExp = [{
        idTipoStatus: "0",
        descTipoStatus: 'Seller Comition'
      }, {
    	idTipoStatus: "1",
    	descTipoStatus: 'Supplies'
      }, {
    	idTipoStatus: "2",
    	descTipoStatus: 'Sub-Contrator'
      },  {
    	idTipoStatus: "4",
    	descTipoStatus: 'City Permises'
      },  {
    	idTipoStatus: "5",
    	descTipoStatus: 'Referral Comition'
      },{
      	idTipoStatus: "3",
      	descTipoStatus: 'Others'
      }];
   
    $scope.tipoStatus = [{
        idTipoStatus: "0",
        descTipoStatus: 'Active'
      }, {
    	idTipoStatus: "1",
    	descTipoStatus: 'Cancelled'
      }, {
    	idTipoStatus: "2",
    	descTipoStatus: 'FollowUp'
      }, {
      	idTipoStatus: "3",
      	descTipoStatus: 'Signed'
      }];    
    
    $scope.tipoFiltro1 = [{
        idTipoFiltro: "1",
        descTipoFiltro: "Specific day"
      }, {
    	idTipoFiltro: "2",
    	descTipoFiltro: "Specific Period"
      }, {
    	idTipoFiltro: "3",
    	descTipoFiltro: "Last Seven"
      }, {
      	idTipoFiltro: "4",
      	descTipoFiltro: "Last 30 Days"
      }];     

    $scope.tipoPago = [{
    	idtipoPago: "1",
        desctipoPago: 'Financial'
      }, {
    	idtipoPago: "2",
    	desctipoPago: 'Cash'
      }, {
    	idtipoPago: "3",
    	desctipoPago: 'Both (Cash & Financial)'
      }];

    $scope.terminosPago = [{
    	idTerminoPago: "0",
        descTerminoPago: "0 Years"
      },{
    	idTerminoPago: "1",
        descTerminoPago: "5 Years"
      }, {
      	idTerminoPago: "2",
        descTerminoPago: "10 Years"
      }, {
      	idTerminoPago: "3",
        descTerminoPago: "15 Years"
      }, {
        idTerminoPago: "4",
        descTerminoPago: "20 Years"
      }, {
        idTerminoPago: "5",
        descTerminoPago: "25 Years"
      }, {
        idTerminoPago: "6",
        descTerminoPago: "30 Years"
      }];
    
    $scope.tipoStatProyect = [{
        idTipoStatProyect: "0",
        descTipoStatProyect: 'Active'
      }, {
    	idTipoStatProyect: "1",
    	descTipoStatProyect: 'Cancelled'
      }, {
    	idTipoStatProyect: "2",
    	descTipoStatProyect: 'Signed'
      }, {
      	idTipoStatProyect: "3",
      	descTipoStatProyect: 'Preeliminared'
      }, {
        idTipoStatProyect: "4",
        descTipoStatProyect: 'Preeliminared Signed'
      }, {
        idTipoStatProyect: "5",
        descTipoStatProyect: 'City Someted'
      }, {
        idTipoStatProyect: "6",
        descTipoStatProyect: 'Corrections'
      }, {
          idTipoStatProyect: "7",
          descTipoStatProyect: 'Permised Approved'
      },{
          idTipoStatProyect: "9",
          descTipoStatProyect: 'Executing'  
      },{
          idTipoStatProyect: "10",
          descTipoStatProyect: 'Completed'         	  
      }];  
    
    
    $scope.tipoStatProyectOther = [{
        idTipoStatProyect: "0",
        descTipoStatProyect: 'Active'
      }, {
    	idTipoStatProyect: "1",
    	descTipoStatProyect: 'Cancelled'
      }, {
    	idTipoStatProyect: "2",
    	descTipoStatProyect: 'Signed'
      }, {
      	idTipoStatProyect: "7",
      	descTipoStatProyect: 'Permised'
      }, {
        idTipoStatProyect: "9",
        descTipoStatProyect: 'Executing'
      }, {
        idTipoStatProyect: "10",
        descTipoStatProyect: 'Completed'
 	  
      }];    
	
}


function listQuoteStatus($http,$scope,baseUrl){
    
	
	$scope.statQuotes = [{
        idStat: "0",
        descStat: 'Created'
      }, {
          idStat: "4",
          descStat: 'Complete Estimate'
      },{
    	idStat: "5",
    	descStat: 'FeedBack Pending'
      }, {
    	idStat: "6",
    	descStat: 'Order Received'
      }, {
    	idStat: "7",
    	descStat: 'On Hold'
      }]; 
	
	
	$scope.statQuotesFeed = [{
        idStat: "8",
        descStat: 'Completed'
    }]; 
	
	
	$scope.statRejected = [{
        idStatR: "0",
        descStatR: 'Price Too High'
      }, {
          idStatR: "1",
          descStatR: 'Leed Time too long'
      },{
    	idStatR: "2",
    	descStatR: 'Late Response'
      }, {
    	idStatR: "3",
    	descStatR: 'Better Price with Another Company'
      }, {
    	idStatR: "4",
    	descStatR: 'Other'
      }]; 
	
	$scope.sosyearList = [{
        id: "2021",
        yearSop: '2021'
      }, {
          id: "2022",
          yearSop: '2022'
      },{
    	id: "2023",
    	yearSop: '2023'
      }, {
    	id: "2024",
    	yearSop: '2024'
      }, {
    	id: "2025",
    	yearSop: '2025'
      }]; 
		
	
}


function listSellersProyect($http,$scope,baseUrl,s1,s2,s3){	
	$scope.sellersPr = [];
	$http.get(baseUrl + "/list-sellersPr?idSeller1="+s1+"&idSeller2="+s2+"&idSeller3="+s3).success(function (data) {      
		$scope.sellersPr = data;    

	});
	
}


function iniciar_tabla(nombre){
	angular.element(document).ready( function () {
    //dTable = $(nombre).DataTable({responsive: true});
		//$(nombre).DataTable({"pageLength": 50});	
     //$(nombre).DataTable().ajax.reload();
    //dTable.ajax.reload();
		$(nombre).DataTable();
    
	});
}

function limpiar_tabla(nombre){

	angular.element(document).ready( function () {
		    //$(nombre).DataTable({"pageLength": 50});	
			$(nombre).DataTable().clear().draw();
			$(nombre).DataTable().destroy();
	    
		});	
}

function listar_leadsActivitys($http,$scope,baseUrl,le){	
	$scope.activitys = [];
	$http.get(baseUrl + "/listarActivitysLead?idLead="+le).success(function (data) {  
		$scope.leadActivitys = data;        
		//iniciar_tabla('#tbActivity')
	});
	
}



function listar_activitys($http,$scope,baseUrl){	
	$scope.activitys = [];
	$http.get(baseUrl + "/listarActivitys").success(function (data) {  
		$scope.activitys = data;        
		iniciar_tabla('#tbActivity')

	});
	
}


function listar_files($http,$scope,baseUrl,idq){	
	$scope.prFiles = [];
	$http.get(baseUrl + "/list-quotefiles?idQuote="+idq).success(function (data) {  
		$scope.prFiles = data;        
        //alert("Total Files" + $scope.prFiles.length);

	});
	
}





/*function listSellers($http,$scope,baseUrl){	
	$scope.sellers = [];
	$scope.sellersFil = [];
	$http.get(baseUrl + "/list-sellers").success(function (data) {      
		$scope.sellers = data;    
		$scope.sellersFil = data;
		iniciar_tabla('#tbSell')

	});
	
}*/

function listLeads($http,$scope,baseUrl){	
	//$scope.sellers = [];
	$scope.leads = [];
	$scope.leadSellers = '';
	
	$http.get(baseUrl + "/list-leads").success(function (data) {      
		$scope.leads = data;  
		return "0"
	}).then(function (response){
		//alert("Promise Leads Ready");
		iniciar_tabla('#tbLead');
		//$('#tbLead').DataTable({responsive: true});
		
		//alert("Prueba " + $scope.leads.sellers);
		
		

	});
	
	
	
}



//Controlador ActividadTag

function RemoteResourceProvider() {
  var _baseUrl;
  this.setBaseUrl = function(baseUrl) {
    _baseUrl = baseUrl;
  }
  this.$get = ['$http',
    function($http) {
      return new RemoteResource($http, _baseUrl);
    }
  ];
}

function validatefields($http,$scope,baseUrl,$filter){
	
	
	angular.element(document).ready(function() {
    // initialize the validator function
		    validator.message.date = 'not a real date';
		
		    // validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
		    $('form')
		      .on('blur', 'input[required], input.optional, select.required', validator.checkField)
		      .on('change', 'select.required', validator.checkField)
		      .on('keypress', 'input[required][pattern]', validator.keypress);
		
		    $('.multi.required').on('keyup blur', 'input', function() {
		      validator.checkField.apply($(this).siblings().last()[0]);
		    });
		
		  /*  $('form').submit(function(e) {
		      e.preventDefault();
		      var submit = true;
		
		      // evaluate the form using generic validaing
		      if (!validator.checkAll($(this))) {
		        submit = true;  // es false inicialmente
		      }
		
		      if (submit)
		        this.submit();
		
		      return false;
		    }); */
    
	});

	
}



function zebra($http, $rootScope,$scope) {
	
	angular.element(document).ready(function() {
		var selected_device;
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
					console.log("DEV : " , device.name)
					$rootScope.selectedPrinter = device.name;
					console.log("ROOT PRINTER : " , $rootScope.selectedPrinter);
					
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
								console.log("DEVICE : " , device.name)
							}
						}
						
					}, function(){alert("Error getting local devices")},"printer");
					
				}, function(error){
					alert(error);
				})
		
		
	});
}


function dashboard($http,$rootScope,$scope,baseUrl,$filter,$window){
	
	console.log("DASH ADMIN");
	
	$scope.graphicMainDayEst = [];
	$scope.viewGr1 = '0';
	var currentYear = $filter('date')(new Date(),'yyyy');  
	//zebra($http,$rootScope,$scope);
	
	$scope.yearRoot = $rootScope.yearRoot;
	

		
	
	
	//init_echarts($http,$rootScope,$scope,baseUrl,$filter,$window);
	
	$http.get(baseUrl + "/quotes/grahpicMainDash?typeGraph=1").success(function (data) {
		
		    $scope.daily1 = data;
		    
		    var dataGr = [];
		    var d1 = [];
		    var vlabels = [];
		    var dataGr = $scope.daily1;
		    var vdata = [];

		   
		    for (var j=0; j < dataGr.length; j++){
		    	
		    	var inicio = (new Date(dataGr[j].day).getTime());
		    	d1.push([inicio, dataGr[j].totQuotes]);
		    	//alert("DAY : " + inicio + "  Total : " + inicio);
		    	vlabels.push($filter('date')(inicio,"MMM d"));
		    	vdata.push(dataGr[j].totQuotes);
		    }
			
	        var data1 = d1;
		    
		    $scope.labels = vlabels;
		    $scope.series = ['Total quotes'];
		   /* $scope.data = [
		      [65, 59, 80, 81, 56, 55, 40],
		      [28, 48, 40, 19, 86, 27, 90]
		    ];*/
		    
		    $scope.data = [vdata];
		    console.log(JSON.stringify($scope.data));
		    //$scope.data = [[65, 59, 80, 81, 56, 55, 40]];
		    
		    $scope.onClick = function (points, evt) {
		      console.log(points, evt);
		    };
		    $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
		    $scope.options = {
		      scales: {
		        yAxes: [
		          {
		            id: 'y-axis-1',
		            type: 'linear',
		            display: true,
		            position: 'left'
		          },
		          {
		            id: 'y-axis-2',
		            type: 'linear',
		            display: true,
		            position: 'right'
		          }
		        ]
		      }
		    };		    
	
	});
	
	$http.get(baseUrl + "/quotes/grahpicMainDash?typeGraph=2").success(function (data) {
		
	    $scope.monthly = data;
	    
	    var dataGr = [];
	    var d1 = [];
	    var vlabels = [];
	    var vdata = [];
	    var dataGr = $scope.monthly;

	   
	    for (var j=0; j < dataGr.length; j++){
	    	
         	vlabels.push(dataGr[j].name);
	    	vdata.push(dataGr[j].totQuotes)
	    }
		
       
	    
	    $scope.labels2 = vlabels;
	    $scope.series2 = ['Total Quotes'];
	   /* $scope.data = [
	      [65, 59, 80, 81, 56, 55, 40],
	      [28, 48, 40, 19, 86, 27, 90]
	    ];*/
	    
	    //$scope.data = [dataGr.totQuotes];
	    $scope.data2 = [vdata];
	    
	    $scope.onClick = function (points, evt) {
	      console.log(points, evt);
	    };
	    $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
	    $scope.options = {
	      scales: {
	        yAxes: [
	          {
	            id: 'y-axis-1',
	            type: 'linear',
	            display: true,
	            position: 'left'
	          },
	          {
	            id: 'y-axis-2',
	            type: 'linear',
	            display: false,
	            position: 'right'
	          }
	        ]
	      }
	    };		    

   });	
	
	/*angular.element(document).ready(function() {


        $(".sparkline_two").sparkline([2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6], {
          type: 'line',
          width: '200',
          height: '40',
          lineColor: '#26B99A',
          fillColor: 'rgba(223, 223, 223, 0.57)',
          lineWidth: 2,
          spotColor: '#26B99A',
          minSpotColor: '#26B99A'
          });
    });	*/
	//alert("Dashboard Admin");
	var linkIndEst = ''
    var sparkline1 = ''
    var sparkline2 = ''
	
		
		
	if (($rootScope.yearRoot.descYear != 'All') && ($rootScope.yearRoot.descYear != '')){
		linkIndEst = '/quotes/indicatorsYear?year='+$rootScope.yearRoot.descYear
		sparkline1 = '/quotes/sparklinesYear?typeGraph=1&year='+$rootScope.yearRoot.descYear
		sparkline2 = '/quotes/sparklinesYear?typeGraph=2&year='+$rootScope.yearRoot.descYear
	}else{
		linkIndEst = '/quotes/indicatorsHistory'
	    sparkline1 = '/quotes/sparklines?typeGraph=1'
	    sparkline2 = '/quotes/sparklines?typeGraph=2'
	}
	
	if (currentYear == $rootScope.yearRoot.descYear) {
		sparkline1 = '/quotes/sparklines?typeGraph=1'
	    sparkline2 = '/quotes/sparklines?typeGraph=2'
	} 
	
	
	//alert("User : " + $rootScope.user.username);
	$http.get(baseUrl + linkIndEst).success(function (data) {      
		$scope.indicadores = data; 
		$rootScope.totOrder = $scope.indicadores[5];
	});
	
	
	
	$scope.clickInd1 = function(){   // Pending Quotes - Admin Dash
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qSummaryQuotes');
	}
	
	
	$scope.clickInd2 = function(){   // Pending Quotes - Admin Dash
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qQuotesAll_Pending'); 
	}
	
	$scope.clickInd4 = function(){   // Ordered Quotes - Admin Dash :
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qdashItemsOrdered'); 
	}
	
    $http.get(baseUrl + sparkline1).success(function (data) {  
    	   
	       $scope.spark1 = data;
	     
	       var arrspark1 = $scope.spark1.totals
	      // var arrspark1 = [2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6];
	       
	        $(".sparkline1").sparkline(arrspark1, {
	            type: 'line',
	            width: '200',
	            height: '40',
	            lineColor: '#26B99A',
	            fillColor: 'rgba(223, 223, 223, 0.57)',
	            lineWidth: 2,
	            spotColor: '#26B99A',
	            minSpotColor: '#26B99A'
	         });	       
	       
    });	
    
    $http.get(baseUrl + sparkline2).success(function (data) {      
    	   
    	   $scope.spark2 = data;
    	   
	       var arrspark2 = $scope.spark2.totals
	     //  var arrspark2 = [2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6];
	      
	        $(".sparkline2").sparkline(arrspark2, {
	          type: 'bar',
	          height: '40',
	          barWidth: 9,
	          colorMap: {
	            '7': '#a1a1a1'
	          },
	          barSpacing: 2,
	          barColor: '#26B99A'
	       });
	       
    });
    
 
    $http.get(baseUrl + "/quotes/sparklines?typeGraph=3").success(function (data) {      
 	   
 	   $scope.spark3 = data;
 	   
	   var arrspark3 = $scope.spark3.totals
	  // var arrspark3 = [2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6];   
	      
	        $(".sparkline3").sparkline(arrspark3, {
	          type: 'bar',
	          height: '40',
	          barWidth: 9,
	          colorMap: {
	            '7': '#a1a1a1'
	          },
	          barSpacing: 2,
	          barColor: '#26B99A'
	       });
	       
    });
    
    
    $http.get(baseUrl + "/quotes/sparklines?typeGraph=4").success(function (data) {       
  	   //
  	   $scope.spark4 = data;
 	   var arrspark4 = $scope.spark4.totals
 	   //var arrspark4 = [2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6];   
 	      
 	        $(".sparkline4").sparkline(arrspark4, {
 	          type: 'bar',
 	          height: '40',
 	          barWidth: 9,
 	          colorMap: {
 	            '7': '#a1a1a1'
 	          },
 	          barSpacing: 2,
 	          barColor: '#26B99A'
 	       });
 	       
     });
    
    $http.get(baseUrl + "/quotes/sparklines?typeGraph=5").success(function (data) {      
   	   //
   	   $scope.spark5 = data;
  	   var arrspark5 = $scope.spark5.totals
  	   //var arrspark4 = [2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6];   
  	      
  	        $(".sparkline5").sparkline(arrspark5, {
  	          type: 'bar',
  	          height: '125',
  	          barWidth: 13,
  	          colorMap: {
  	            '7': '#a1a1a1'
  	          },
  	          barSpacing: 2,
  	          barColor: '#26B99A'
  	       });
  	       
      });    
    
    
	
	$http.get(baseUrl + "/quotes/topSellersDashYear").success(function (data) {      
		$scope.topSellers = data;
		//alert("Size Seller top" + $scope.topSellers.length)
	});	
	
	

      
  	$http.get(baseUrl + "/quotes/donutcharts?typeGraph=1").success(function (data) {      
         
  		
  		$scope.donut1 = data;
        angular.element(document).ready(function() {
            var canvasDoughnut,
                options = {
                  legend: false,
                  responsive: false
                };
            
            var ltotals = $scope.donut1.totals;
            var llabels = $scope.donut1.labels;
            var lback   = $scope.donut1.backColor;
            var lhover  = $scope.donut1.hover;
            
            new Chart(document.getElementById("canvas1i"), {
              type: 'pie',
              tooltipFillColor: "rgba(51, 51, 51, 0.55)",
              data: {
                labels: llabels,
                datasets: [{
                  data: ltotals,
                  backgroundColor: lback,
                  hoverBackgroundColor: lhover

                }]
              },
              options: options
            });
  		
	});	
        
  });
  	
  	
	    $http.get(baseUrl + "/quotes/donutcharts?typeGraph=2").success(function (data) {      
	        
	  		
	  		$scope.donut2 = data;
	        angular.element(document).ready(function() {
	            var canvasDoughnut,
	                options = {
	                  legend: false,
	                  responsive: false
	                };
	            
	            var ltotals = $scope.donut2.totals;
	            var llabels = $scope.donut2.labels;
	            var lback   = $scope.donut2.backColor;
	            var lhover  = $scope.donut2.hover;
	            
	            new Chart(document.getElementById("canvas1i2"), {
	              type: 'pie',
	              tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	              data: {
	                labels: llabels,
	                datasets: [{
	                  data: ltotals,
	                  backgroundColor: lback,
	                  hoverBackgroundColor: lhover
	
	                }]
	              },
	              options: options
	            });
	  		
		}); 	
	        
	    });      
	        
 
	    $http.get(baseUrl + "/quotes/donutcharts?typeGraph=3").success(function (data) {      
	        
	  		
	  		$scope.donut3 = data;
	        angular.element(document).ready(function() {
	            var canvasDoughnut,
	                options = {
	                  legend: false,
	                  responsive: false
	                };
	            
	            var ltotals = $scope.donut3.totals;
	            var llabels = $scope.donut3.labels;
	            var lback   = $scope.donut3.backColor;
	            var lhover  = $scope.donut3.hover;
	            
	            new Chart(document.getElementById("canvas1i3"), {
	              type: 'pie',
	              tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	              data: {
	                labels: llabels,
	                datasets: [{
	                  data: ltotals,
	                  backgroundColor: lback,
	                  hoverBackgroundColor: lhover
	
	                }]
	              },
	              options: options
	            });
	  		
		}); 	
	        
	    }); 	    
	    
      angular.element(document).ready(function() {

        var cb = function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
          $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        };

        var optionSet1 = {
          startDate: moment().subtract(29, 'days'),
          endDate: moment(),
          minDate: '01/01/2012',
          maxDate: '12/31/2015',
          dateLimit: {
            days: 60
          },
          showDropdowns: true,
          showWeekNumbers: true,
          timePicker: false,
          timePickerIncrement: 1,
          timePicker12Hour: true,
          ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
          },
          opens: 'left',
          buttonClasses: ['btn btn-default'],
          applyClass: 'btn-small btn-primary',
          cancelClass: 'btn-small',
          format: 'MM/DD/YYYY',
          separator: ' to ',
          locale: {
            applyLabel: 'Submit',
            cancelLabel: 'Clear',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
          }
        };
        $('#reportrange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
        $('#reportrange').daterangepicker(optionSet1, cb);
        $('#reportrange').on('show.daterangepicker', function() {
          console.log("show event fired");
        });
        $('#reportrange').on('hide.daterangepicker', function() {
          console.log("hide event fired");
        });
        $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
          console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
        });
        $('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
          console.log("cancel event fired");
        });
        $('#options1').click(function() {
          $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
        });
        $('#options2').click(function() {
          $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
        });
        $('#destroy').click(function() {
          $('#reportrange').data('daterangepicker').remove();
        });
        
        $('form')
        .on('blur', 'input[required], input.optional, select.required', validator.checkField)
        .on('change', 'select.required', validator.checkField)
        .on('keypress', 'input[required][pattern]', validator.keypress);

      $('.multi.required').on('keyup blur', 'input', function() {
        validator.checkField.apply($(this).siblings().last()[0]);
      });

      $('form').submit(function(e) {
        e.preventDefault();
        var submit = true;

        // evaluate the form using generic validaing
        if (!validator.checkAll($(this))) {
          submit = false;
        }

        if (submit)
          this.submit();

        return false;
      });
        
      });
}


function dashboardEstimator($http,$rootScope,$scope,baseUrl,$filter,$window){
	var year = $filter('date')(new Date(),'yyyy');
	var linkIndEst = ''
	
	console.log("Year Aux Dash ==>: " +  $rootScope.yearAux.descYear);	
	
	$scope.yearEst = $rootScope.yearEst;
	
	if (($rootScope.yearEst.descYear != 'All') && ($rootScope.yearEst.descYear != '')){
		linkIndEst = '/estimators/indicatorsYear?user='+$rootScope.user.username+'&year='+$rootScope.yearEst.descYear
	}else{
		linkIndEst = '/estimators/indicators?user='+$rootScope.user.username
	}
		
	
	
	$scope.graphicMainDayEst = [];
	
	$scope.clickInd3 = function(){
		
		$window.location.assign(baseUrl + '/sealhome#/qQuotePendingItemsEst'); 
	}
	
	$scope.clickInd2 = function(){
		
		$window.location.assign(baseUrl + '/sealhome#/qQuotePendingEst'); 
	}
	
	$scope.clickInd4 = function(){   // Ordered Quotes - Estimator Dash :
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qdashItemsOrderedEst'); 
	}
	
	$scope.clickInd5 = function(){   // Pending Quotes - Admin Dash
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qQuotesSummaryEstimator'); 
	}
	
	$scope.clickInd6 = function(){   // Pending Quotes - Admin Dash
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qQuotesOnHoldEstimator'); 
		//listViewQuotesEstimatorByStatus($http,$scope,baseUrl,year,$rootScope.user.username, '7'); 
	}
	
	
	$scope.clickInd7 = function(){   // Pending Quotes - Admin Dash
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qQuotesOrderedEstimator'); 
		//listViewQuotesEstimatorByStatus($http,$scope,baseUrl,year,$rootScope.user.username, '7'); 
	}
	
	
	
	//alert("User : " + $rootScope.user.username);
	$http.get(baseUrl + linkIndEst).success(function (data) {  
		$scope.indicadores = data;  
	});
	
	$http.get(baseUrl + "/estimators/lastQuotes?user="+$rootScope.user.username).success(function (data) {      
		$scope.lastQuotes = data;
	});	
	
	$http.get(baseUrl + "/estimators/graphicQuotesPerDayEstimator?user="+$rootScope.user.username).success(function (data) {      
		$scope.graphicMainDayEst = data;
		
		
		angular.element(document).ready(function() {

		    var dataGr = [];
		    var d1 = [];
		    var dataGr = $scope.graphicMainDayEst;
	  
		    
		   
		    for (var j=0; j < dataGr.length; j++){
		    	
		    	var inicio = (new Date(dataGr[j].day).getTime());
		    	d1.push([inicio, dataGr[j].totQuotes]);
		    	//alert("DAY : " + inicio + "  Total : " + inicio);
		    	
		    }
	    
	        var chartColours = ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'];

	        //generate random number for charts
	        var randNum = function() {
	          return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
	        };

	  
	        //var d2 = [];

	        //here we generate data for chart
	        /*
	        for (var i = 0; i < 30; i++) {
	          d1.push([new Date(Date.today().add(i).days()).getTime(), randNum() + i + i + 10]);
	          //    d2.push([new Date(Date.today().add(i).days()).getTime(), randNum()]);
	        }
	        */
	 
	        var chartMinDate = d1[0][0]; //first day
	        //alert(" chartMinDate : " + chartMinDate);
	        var chartMaxDate = d1[dataGr.length-1][0]; //last day
	        //alert(" MaxDate : " + chartMaxDate);

	        var tickSize = [1, "day"];
	        var tformat = "%d/%m/%y";

	        //graph options
	        var options = {
	          grid: {
	            show: true,
	            aboveData: true,
	            color: "#3f3f3f",
	            labelMargin: 10,
	            axisMargin: 0,
	            borderWidth: 0,
	            borderColor: null,
	            minBorderMargin: 5,
	            clickable: true,
	            hoverable: true,
	            autoHighlight: true,
	            mouseActiveRadius: 100
	          },
	          series: {
	            lines: {
	              show: true,
	              fill: true,
	              lineWidth: 2,
	              steps: false
	            },
	            points: {
	              show: true,
	              radius: 4.5,
	              symbol: "circle",
	              lineWidth: 3.0
	            }
	          },
	          legend: {
	            position: "ne",
	            margin: [0, -25],
	            noColumns: 0,
	            labelBoxBorderColor: null,
	            labelFormatter: function(label, series) {
	              // just add some space to labes
	              return label + '&nbsp;&nbsp;';
	            },
	            width: 40,
	            height: 1
	          },
	          colors: chartColours,
	          shadowSize: 0,
	          tooltip: true, //activate tooltip
	          tooltipOpts: {
	            content: "%s: %y.0",
	            xDateFormat: "%d/%m",
	            shifts: {
	              x: -30,
	              y: -50
	            },
	            defaultTheme: false
	          },
	          yaxis: {
	            min: 0
	          },
	          xaxis: {
	            mode: "time",
	            minTickSize: tickSize,
	            timeformat: tformat,
	            min: chartMinDate,
	            max: chartMaxDate
	          }
	        };
	        var plot = $.plot($("#placeholder33x"), [{
	          label: "Quotes",
	          data: d1,
	          lines: {
	            fillColor: "rgba(150, 202, 89, 0.12)"
	          }, //#96CA59 rgba(150, 202, 89, 0.42)
	          points: {
	            fillColor: "#fff"
	          }
	        }], options);
	      });
		
	});		
	
	
	
	




  /*  angular.element(document).ready(function() {
        $(".sparkline_one").sparkline([2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 4, 5, 6, 3, 5, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6], {
          type: 'bar',
          height: '125',
          barWidth: 13,
          colorMap: {
            '7': '#a1a1a1'
          },
          barSpacing: 2,
          barColor: '#26B99A'
        });

        $(".sparkline11").sparkline([2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 6, 2, 4, 3, 4, 5, 4, 5, 4, 3], {
          type: 'bar',
          height: '40',
          barWidth: 8,
          colorMap: {
            '7': '#a1a1a1'
          },
          barSpacing: 2,
          barColor: '#26B99A'
        });

        $(".sparkline22").sparkline([2, 4, 3, 4, 7, 5, 4, 3, 5, 6, 2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 6], {
          type: 'line',
          height: '40',
          width: '200',
          lineColor: '#26B99A',
          fillColor: '#ffffff',
          lineWidth: 3,
          spotColor: '#34495E',
          minSpotColor: '#34495E'
        });
      });  */
      
      
      $http.get(baseUrl + "/estimators/sparklinesestimator?user="+$rootScope.user.username+"&typeGraph=1").success(function (data) {      
	       $scope.spark1 = data;
 	       var arrspark1 = $scope.spark1.totals
 	      
 	      
	        $(".sparkline1").sparkline(arrspark1, {
	          type: 'bar',
	          height: '40',
	          barWidth: 8,
	          colorMap: {
	            '7': '#a1a1a1'
	          },
	          barSpacing: 2,
	          barColor: '#26B99A'
	        });
   
   
      });
      
      $http.get(baseUrl + "/estimators/sparklinesestimator?user="+$rootScope.user.username+"&typeGraph=2").success(function (data) {      
	       $scope.spark2 = data;
	       var arrspark2 = $scope.spark2.totalsDbl
	      
	      
		   $(".sparkline2").sparkline(arrspark2, {
			     type: 'line',
			     height: '40',
			     width: '200',
			     lineColor: '#26B99A',
			     fillColor: '#ffffff',
			     lineWidth: 3,
			     spotColor: '#34495E',
			     minSpotColor: '#34495E'
			});
  
  
      });      
      
      
      $http.get(baseUrl + "/estimators/sparklinesestimator?user="+$rootScope.user.username+"&typeGraph=3").success(function (data) {      
	       $scope.spark3 = data;
	       var arrspark3 = $scope.spark3.totals
	      
	      
	        $(".sparkline3").sparkline(arrspark3, {
		          type: 'bar',
		          height: '40',
		          barWidth: 8,
		          colorMap: {
		            '7': '#a1a1a1'
		          },
		          barSpacing: 2,
		          barColor: '#26B99A'
		     });
 
 
     });
      
      
      $http.get(baseUrl + "/estimators/sparklinesestimator?user="+$rootScope.user.username+"&typeGraph=4").success(function (data) {      
	       $scope.spark4 = data;
	       var arrspark4 = $scope.spark4.totals
	      
	      
	        $(".sparkline4").sparkline(arrspark4, {
	            type: 'bar',
	            height: '125',
	            barWidth: 13,
	            colorMap: {
	              '7': '#a1a1a1'
	            },
	            barSpacing: 2,
	            barColor: '#26B99A'
	         });


      });
      
      
		/* angular.element(document).ready(function() { 
		   $(".sparkline_one").sparkline([2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 6, 2, 4, 3, 4, 5, 4, 5, 4, 3], {
		     type: 'bar',
		     height: '40',
		     barWidth: 8,
		     colorMap: {
		       '7': '#a1a1a1'
		     },
		     barSpacing: 2,
		     barColor: '#26B99A'
		   });
		 });  
		
		 angular.element(document).ready(function() {
		   $(".sparkline22").sparkline([2, 4, 3, 4, 7, 5, 4, 3, 5, 6, 2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 6], {
		     type: 'line',
		     height: '40',
		     width: '200',
		     lineColor: '#26B99A',
		     fillColor: '#ffffff',
		     lineWidth: 3,
		     spotColor: '#34495E',
		     minSpotColor: '#34495E'
		   });
		 });      */
      
      
  	$http.get(baseUrl + "/estimators/donutchartestimator?user="+$rootScope.user.username+"&typeGraph=1").success(function (data) {      
         
  		
  		$scope.donut1 = data;
        angular.element(document).ready(function() {
            var canvasDoughnut,
                options = {
                  legend: false,
                  responsive: false
                };
            
            var ltotals = $scope.donut1.totals;
            var llabels = $scope.donut1.labels;
            var lback   = $scope.donut1.backColor;
            var lhover  = $scope.donut1.hover;
            
            new Chart(document.getElementById("canvas1i"), {
              type: 'doughnut',
              tooltipFillColor: "rgba(51, 51, 51, 0.55)",
              data: {
                labels: llabels,
                datasets: [{
                  data: ltotals,
                  backgroundColor: lback,
                  hoverBackgroundColor: lhover

                }]
              },
              options: options
            });
  		
	});	
        
   
    
    $http.get(baseUrl + "/estimators/donutchartestimator?user="+$rootScope.user.username+"&typeGraph=2").success(function (data) {   
    	
    	
    	$scope.donut2 = data;
        angular.element(document).ready(function() {
            var canvasDoughnut,
                options = {
                  legend: false,
                  responsive: false
                };
            var ltotals = $scope.donut2.totals;
            var llabels = $scope.donut2.labels;
            var lback   = $scope.donut2.backColor;
            var lhover  = $scope.donut2.hover;    
            
            new Chart(document.getElementById("canvas1i2"), {
                type: 'doughnut',
                tooltipFillColor: "rgba(51, 51, 51, 0.55)",
                data: {
                  labels: llabels,
                  datasets: [{
                    data: ltotals,
                    backgroundColor: lback,
                    hoverBackgroundColor: lhover

                  }]
                },
                options: options
              });
        
    });    	
    	
    });
        

    $http.get(baseUrl + "/estimators/donutchartestimator?user="+$rootScope.user.username+"&typeGraph=3").success(function (data) {       

    
    $scope.donut3 = data; 	
    angular.element(document).ready(function() {
        var canvasDoughnut,
            options = {
              legend: false,
              responsive: false
            };   
        
        var ltotals = $scope.donut3.totals;
        var llabels = $scope.donut3.labels;
        var lback   = $scope.donut3.backColor;
        var lhover  = $scope.donut3.hover;        
    
        new Chart(document.getElementById("canvas1i3"), {
            type: 'doughnut',
            tooltipFillColor: "rgba(51, 51, 51, 0.55)",
            data: {
              labels: llabels,
              datasets: [{
                data: ltotals,
                backgroundColor: lback,
                hoverBackgroundColor: lhover

              }]
            },
            options: options
          });
      });

    
  	});    

  	});
  	
  	
      angular.element(document).ready(function() {

        var cb = function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
          $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        };

        var optionSet1 = {
          startDate: moment().subtract(29, 'days'),
          endDate: moment(),
          minDate: '01/01/2012',
          maxDate: '12/31/2015',
          dateLimit: {
            days: 60
          },
          showDropdowns: true,
          showWeekNumbers: true,
          timePicker: false,
          timePickerIncrement: 1,
          timePicker12Hour: true,
          ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
          },
          opens: 'left',
          buttonClasses: ['btn btn-default'],
          applyClass: 'btn-small btn-primary',
          cancelClass: 'btn-small',
          format: 'MM/DD/YYYY',
          separator: ' to ',
          locale: {
            applyLabel: 'Submit',
            cancelLabel: 'Clear',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
          }
        };
        $('#reportrange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
        $('#reportrange').daterangepicker(optionSet1, cb);
        $('#reportrange').on('show.daterangepicker', function() {
          console.log("show event fired");
        });
        $('#reportrange').on('hide.daterangepicker', function() {
          console.log("hide event fired");
        });
        $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
          console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
        });
        $('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
          console.log("cancel event fired");
        });
        $('#options1').click(function() {
          $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
        });
        $('#options2').click(function() {
          $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
        });
        $('#destroy').click(function() {
          $('#reportrange').data('daterangepicker').remove();
        });
        
        $('form')
        .on('blur', 'input[required], input.optional, select.required', validator.checkField)
        .on('change', 'select.required', validator.checkField)
        .on('keypress', 'input[required][pattern]', validator.keypress);

      $('.multi.required').on('keyup blur', 'input', function() {
        validator.checkField.apply($(this).siblings().last()[0]);
      });

      $('form').submit(function(e) {
        e.preventDefault();
        var submit = true;

        // evaluate the form using generic validaing
        if (!validator.checkAll($(this))) {
          submit = false;
        }

        if (submit)
          this.submit();

        return false;
      });
        
      });
}


function dashboardSeller($http,$rootScope,$scope,baseUrl,$filter,$window){
	
	
	$scope.graphicMainDayEst = [];
	
	$scope.clickInd3 = function(){
		
		$window.location.assign(baseUrl + '/sealhome#/qQuotePendingItems'); 
	}
	
	findActualSeller($http,$scope,baseUrl,$rootScope.user.username); 
	percentsitemseller($http,$scope,baseUrl,$scope.actSel.id); 
	
	//alert("User : " + $rootScope.user.username);
	$http.get(baseUrl + "/sellers/indicators?user="+$rootScope.user.username).success(function (data) {      
		$scope.indicadores = data;      
		console.log("Indicators Salesman : " ,$scope.indicadores  );
		
		$rootScope.totOrder = $scope.indicadores[4];
	});
	
	$http.get(baseUrl + "/sellers/lastQuotes?user="+$rootScope.user.username).success(function (data) {      
		$scope.lastQuotes = data;
	});	
	
	$scope.clickInd4 = function(){   // Ordered Quotes - Admin Dash :
		
		//alert('Click2'); 
		$window.location.assign(baseUrl + '/sealhome#/qdashItemsOrderedSel'); 
	}
	
	$http.get(baseUrl + "/sellers/graphicQuotesPerDayEstimator?user="+$rootScope.user.username).success(function (data) {      
		$scope.graphicMainDayEst = data;
		
		
		angular.element(document).ready(function() {

		    var dataGr = [];
		    var d1 = [];
		    var dataGr = $scope.graphicMainDayEst;
	  
		    
		   
		    for (var j=0; j < dataGr.length; j++){
		    	
		    	var inicio = (new Date(dataGr[j].day).getTime());
		    	d1.push([inicio, dataGr[j].totQuotes]);
		    	//alert("DAY : " + inicio + "  Total : " + inicio);
		    	
		    }
	    
	        var chartColours = ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'];

	        //generate random number for charts
	        var randNum = function() {
	          return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
	        };

	  
	        //var d2 = [];

	        //here we generate data for chart
	        /*
	        for (var i = 0; i < 30; i++) {
	          d1.push([new Date(Date.today().add(i).days()).getTime(), randNum() + i + i + 10]);
	          //    d2.push([new Date(Date.today().add(i).days()).getTime(), randNum()]);
	        }
	        */
	 
	        var chartMinDate = d1[0][0]; //first day
	        //alert(" chartMinDate : " + chartMinDate);
	        var chartMaxDate = d1[dataGr.length-1][0]; //last day
	        //alert(" MaxDate : " + chartMaxDate);

	        var tickSize = [1, "day"];
	        var tformat = "%d/%m/%y";

	        //graph options
	        var options = {
	          grid: {
	            show: true,
	            aboveData: true,
	            color: "#3f3f3f",
	            labelMargin: 10,
	            axisMargin: 0,
	            borderWidth: 0,
	            borderColor: null,
	            minBorderMargin: 5,
	            clickable: true,
	            hoverable: true,
	            autoHighlight: true,
	            mouseActiveRadius: 100
	          },
	          series: {
	            lines: {
	              show: true,
	              fill: true,
	              lineWidth: 2,
	              steps: false
	            },
	            points: {
	              show: true,
	              radius: 4.5,
	              symbol: "circle",
	              lineWidth: 3.0
	            }
	          },
	          legend: {
	            position: "ne",
	            margin: [0, -25],
	            noColumns: 0,
	            labelBoxBorderColor: null,
	            labelFormatter: function(label, series) {
	              // just add some space to labes
	              return label + '&nbsp;&nbsp;';
	            },
	            width: 40,
	            height: 1
	          },
	          colors: chartColours,
	          shadowSize: 0,
	          tooltip: true, //activate tooltip
	          tooltipOpts: {
	            content: "%s: %y.0",
	            xDateFormat: "%d/%m",
	            shifts: {
	              x: -30,
	              y: -50
	            },
	            defaultTheme: false
	          },
	          yaxis: {
	            min: 0
	          },
	          xaxis: {
	            mode: "time",
	            minTickSize: tickSize,
	            timeformat: tformat,
	            min: chartMinDate,
	            max: chartMaxDate
	          }
	        };
	        var plot = $.plot($("#placeholder33x"), [{
	          label: "Quotes",
	          data: d1,
	          lines: {
	            fillColor: "rgba(150, 202, 89, 0.12)"
	          }, //#96CA59 rgba(150, 202, 89, 0.42)
	          points: {
	            fillColor: "#fff"
	          }
	        }], options);
	      });
		
	});		
	
      
      $http.get(baseUrl + "/sellers/sparklinesseller?user="+$rootScope.user.username+"&typeGraph=1").success(function (data) {      
	       $scope.spark1 = data;
 	       var arrspark1 = $scope.spark1.totals
 	      
 	      
	        $(".sparkline1").sparkline(arrspark1, {
	          type: 'bar',
	          height: '40',
	          barWidth: 8,
	          colorMap: {
	            '7': '#a1a1a1'
	          },
	          barSpacing: 2,
	          barColor: '#26B99A'
	        });
   
   
      });
      
      $http.get(baseUrl + "/sellers/sparklinesseller?user="+$rootScope.user.username+"&typeGraph=2").success(function (data) {      
	       $scope.spark2 = data;
	       var arrspark2 = $scope.spark2.totalsDbl
	      
	      
		   $(".sparkline2").sparkline(arrspark2, {
			     type: 'line',
			     height: '40',
			     width: '200',
			     lineColor: '#26B99A',
			     fillColor: '#ffffff',
			     lineWidth: 3,
			     spotColor: '#34495E',
			     minSpotColor: '#34495E'
			});
  
  
      });      
      
      
      $http.get(baseUrl + "/sellers/sparklinesseller?user="+$rootScope.user.username+"&typeGraph=3").success(function (data) {      
	       $scope.spark3 = data;
	       var arrspark3 = $scope.spark3.totals
	      
	      
	        $(".sparkline3").sparkline(arrspark3, {
		          type: 'bar',
		          height: '40',
		          barWidth: 8,
		          colorMap: {
		            '7': '#a1a1a1'
		          },
		          barSpacing: 2,
		          barColor: '#26B99A'
		     });
 
 
     });
      
      
      $http.get(baseUrl + "/sellers/sparklinesseller?user="+$rootScope.user.username+"&typeGraph=4").success(function (data) {      
	       $scope.spark4 = data;
	       var arrspark4 = $scope.spark4.totals
	      
	      
	        $(".sparkline4").sparkline(arrspark4, {
	            type: 'bar',
	            height: '125',
	            barWidth: 13,
	            colorMap: {
	              '7': '#a1a1a1'
	            },
	            barSpacing: 2,
	            barColor: '#26B99A'
	         });


      });
      
    
      
  	$http.get(baseUrl + "/sellers/donutchartseller?user="+$rootScope.user.username+"&typeGraph=1").success(function (data) {      
         
  		
  		$scope.donut1 = data;
        angular.element(document).ready(function() {
            var canvasDoughnut,
                options = {
                  legend: false,
                  responsive: false
                };
            
            var ltotals = $scope.donut1.totals;
            var llabels = $scope.donut1.labels;
            var lback   = $scope.donut1.backColor;
            var lhover  = $scope.donut1.hover;
            
            new Chart(document.getElementById("canvas1i"), {
              type: 'doughnut',
              tooltipFillColor: "rgba(51, 51, 51, 0.55)",
              data: {
                labels: llabels,
                datasets: [{
                  data: ltotals,
                  backgroundColor: lback,
                  hoverBackgroundColor: lhover

                }]
              },
              options: options
            });
  		
	});	
        
   
    
    $http.get(baseUrl + "/sellers/donutchartseller?user="+$rootScope.user.username+"&typeGraph=2").success(function (data) {   
    	
    	
    	$scope.donut2 = data;
        angular.element(document).ready(function() {
            var canvasDoughnut,
                options = {
                  legend: false,
                  responsive: false
                };
            var ltotals = $scope.donut2.totals;
            var llabels = $scope.donut2.labels;
            var lback   = $scope.donut2.backColor;
            var lhover  = $scope.donut2.hover;    
            
            new Chart(document.getElementById("canvas1i2"), {
                type: 'doughnut',
                tooltipFillColor: "rgba(51, 51, 51, 0.55)",
                data: {
                  labels: llabels,
                  datasets: [{
                    data: ltotals,
                    backgroundColor: lback,
                    hoverBackgroundColor: lhover

                  }]
                },
                options: options
              });
        
    });    	
    	
    });
        

    $http.get(baseUrl + "/sellers/donutchartseller?user="+$rootScope.user.username+"&typeGraph=3").success(function (data) {       

    
    $scope.donut3 = data; 	
    angular.element(document).ready(function() {
        var canvasDoughnut,
            options = {
              legend: false,
              responsive: false
            };   
        
        var ltotals = $scope.donut3.totals;
        var llabels = $scope.donut3.labels;
        var lback   = $scope.donut3.backColor;
        var lhover  = $scope.donut3.hover;        
    
        new Chart(document.getElementById("canvas1i3"), {
            type: 'doughnut',
            tooltipFillColor: "rgba(51, 51, 51, 0.55)",
            data: {
              labels: llabels,
              datasets: [{
                data: ltotals,
                backgroundColor: lback,
                hoverBackgroundColor: lhover

              }]
            },
            options: options
          });
      });

    
  	});    

  	});
  	
  	
      angular.element(document).ready(function() {

        var cb = function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
          $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        };

        var optionSet1 = {
          startDate: moment().subtract(29, 'days'),
          endDate: moment(),
          minDate: '01/01/2012',
          maxDate: '12/31/2015',
          dateLimit: {
            days: 60
          },
          showDropdowns: true,
          showWeekNumbers: true,
          timePicker: false,
          timePickerIncrement: 1,
          timePicker12Hour: true,
          ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
          },
          opens: 'left',
          buttonClasses: ['btn btn-default'],
          applyClass: 'btn-small btn-primary',
          cancelClass: 'btn-small',
          format: 'MM/DD/YYYY',
          separator: ' to ',
          locale: {
            applyLabel: 'Submit',
            cancelLabel: 'Clear',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
          }
        };
        $('#reportrange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
        $('#reportrange').daterangepicker(optionSet1, cb);
        $('#reportrange').on('show.daterangepicker', function() {
          console.log("show event fired");
        });
        $('#reportrange').on('hide.daterangepicker', function() {
          console.log("hide event fired");
        });
        $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
          console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
        });
        $('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
          console.log("cancel event fired");
        });
        $('#options1').click(function() {
          $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
        });
        $('#options2').click(function() {
          $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
        });
        $('#destroy').click(function() {
          $('#reportrange').data('daterangepicker').remove();
        });
        
        $('form')
        .on('blur', 'input[required], input.optional, select.required', validator.checkField)
        .on('change', 'select.required', validator.checkField)
        .on('keypress', 'input[required][pattern]', validator.keypress);

      $('.multi.required').on('keyup blur', 'input', function() {
        validator.checkField.apply($(this).siblings().last()[0]);
      });

      $('form').submit(function(e) {
        e.preventDefault();
        var submit = true;

        // evaluate the form using generic validaing
        if (!validator.checkAll($(this))) {
          submit = false;
        }

        if (submit)
          this.submit();

        return false;
      });
        
      });
}





function init_icheck(){
	
	
	 /* angular.element(document).ready(function() {
		    if ($("input.flat")[0]) {
		        $(document).ready(function () {
		            $('input.flat').iCheck({
		                checkboxClass: 'icheckbox_flat-green',
		                radioClass: 'iradio_flat-green'
		            });
		        });
		    }
		});   */
	  
	 
	  
	  angular.element(document).ready(function() {
		    if ($(".js-switch")[0]) {
		        var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
		        elems.forEach(function (html) {
		            var switchery = new Switchery(html, {
		                color: '#26B99A'
		            });
		        });
		    }
		});
	  
	  
	  
	  angular.element(document).ready(function() {
         /* $(".select2_single").select2({
            placeholder: "Select a Customer",
            allowClear: true
          });
          */
          $(".select2_group").select2({});
         /* $(".select2_multiple").select2({
            maximumSelectionLength: 4,
            placeholder: "With Max Selection limit 4",
            allowClear: true
          });*/
        }); 
	  

	  //alert("Init completed");
}


function init_autocomplete(){

	angular.element(document).ready(function() {
      var countries = { AD:"Andorra",A2:"Andorra Test",AE:"United Arab Emirates",AF:"Afghanistan",AG:"Antigua and Barbuda",AI:"Anguilla",AL:"Albania",AM:"Armenia",AN:"Netherlands Antilles",AO:"Angola",AQ:"Antarctica",AR:"Argentina",AS:"American Samoa",AT:"Austria",AU:"Australia",AW:"Aruba",AX:"Åland Islands",AZ:"Azerbaijan",BA:"Bosnia and Herzegovina",BB:"Barbados",BD:"Bangladesh",BE:"Belgium",BF:"Burkina Faso",BG:"Bulgaria",BH:"Bahrain",BI:"Burundi",BJ:"Benin",BL:"Saint Barthélemy",BM:"Bermuda",BN:"Brunei",BO:"Bolivia",BQ:"British Antarctic Territory",BR:"Brazil",BS:"Bahamas",BT:"Bhutan",BV:"Bouvet Island",BW:"Botswana",BY:"Belarus",BZ:"Belize",CA:"Canada",CC:"Cocos [Keeling] Islands",CD:"Congo - Kinshasa",CF:"Central African Republic",CG:"Congo - Brazzaville",CH:"Switzerland",CI:"Côte d’Ivoire",CK:"Cook Islands",CL:"Chile",CM:"Cameroon",CN:"China",CO:"Colombia",CR:"Costa Rica",CS:"Serbia and Montenegro",CT:"Canton and Enderbury Islands",CU:"Cuba",CV:"Cape Verde",CX:"Christmas Island",CY:"Cyprus",CZ:"Czech Republic",DD:"East Germany",DE:"Germany",DJ:"Djibouti",DK:"Denmark",DM:"Dominica",DO:"Dominican Republic",DZ:"Algeria",EC:"Ecuador",EE:"Estonia",EG:"Egypt",EH:"Western Sahara",ER:"Eritrea",ES:"Spain",ET:"Ethiopia",FI:"Finland",FJ:"Fiji",FK:"Falkland Islands",FM:"Micronesia",FO:"Faroe Islands",FQ:"French Southern and Antarctic Territories",FR:"France",FX:"Metropolitan France",GA:"Gabon",GB:"United Kingdom",GD:"Grenada",GE:"Georgia",GF:"French Guiana",GG:"Guernsey",GH:"Ghana",GI:"Gibraltar",GL:"Greenland",GM:"Gambia",GN:"Guinea",GP:"Guadeloupe",GQ:"Equatorial Guinea",GR:"Greece",GS:"South Georgia and the South Sandwich Islands",GT:"Guatemala",GU:"Guam",GW:"Guinea-Bissau",GY:"Guyana",HK:"Hong Kong SAR China",HM:"Heard Island and McDonald Islands",HN:"Honduras",HR:"Croatia",HT:"Haiti",HU:"Hungary",ID:"Indonesia",IE:"Ireland",IL:"Israel",IM:"Isle of Man",IN:"India",IO:"British Indian Ocean Territory",IQ:"Iraq",IR:"Iran",IS:"Iceland",IT:"Italy",JE:"Jersey",JM:"Jamaica",JO:"Jordan",JP:"Japan",JT:"Johnston Island",KE:"Kenya",KG:"Kyrgyzstan",KH:"Cambodia",KI:"Kiribati",KM:"Comoros",KN:"Saint Kitts and Nevis",KP:"North Korea",KR:"South Korea",KW:"Kuwait",KY:"Cayman Islands",KZ:"Kazakhstan",LA:"Laos",LB:"Lebanon",LC:"Saint Lucia",LI:"Liechtenstein",LK:"Sri Lanka",LR:"Liberia",LS:"Lesotho",LT:"Lithuania",LU:"Luxembourg",LV:"Latvia",LY:"Libya",MA:"Morocco",MC:"Monaco",MD:"Moldova",ME:"Montenegro",MF:"Saint Martin",MG:"Madagascar",MH:"Marshall Islands",MI:"Midway Islands",MK:"Macedonia",ML:"Mali",MM:"Myanmar [Burma]",MN:"Mongolia",MO:"Macau SAR China",MP:"Northern Mariana Islands",MQ:"Martinique",MR:"Mauritania",MS:"Montserrat",MT:"Malta",MU:"Mauritius",MV:"Maldives",MW:"Malawi",MX:"Mexico",MY:"Malaysia",MZ:"Mozambique",NA:"Namibia",NC:"New Caledonia",NE:"Niger",NF:"Norfolk Island",NG:"Nigeria",NI:"Nicaragua",NL:"Netherlands",NO:"Norway",NP:"Nepal",NQ:"Dronning Maud Land",NR:"Nauru",NT:"Neutral Zone",NU:"Niue",NZ:"New Zealand",OM:"Oman",PA:"Panama",PC:"Pacific Islands Trust Territory",PE:"Peru",PF:"French Polynesia",PG:"Papua New Guinea",PH:"Philippines",PK:"Pakistan",PL:"Poland",PM:"Saint Pierre and Miquelon",PN:"Pitcairn Islands",PR:"Puerto Rico",PS:"Palestinian Territories",PT:"Portugal",PU:"U.S. Miscellaneous Pacific Islands",PW:"Palau",PY:"Paraguay",PZ:"Panama Canal Zone",QA:"Qatar",RE:"Réunion",RO:"Romania",RS:"Serbia",RU:"Russia",RW:"Rwanda",SA:"Saudi Arabia",SB:"Solomon Islands",SC:"Seychelles",SD:"Sudan",SE:"Sweden",SG:"Singapore",SH:"Saint Helena",SI:"Slovenia",SJ:"Svalbard and Jan Mayen",SK:"Slovakia",SL:"Sierra Leone",SM:"San Marino",SN:"Senegal",SO:"Somalia",SR:"Suriname",ST:"São Tomé and Príncipe",SU:"Union of Soviet Socialist Republics",SV:"El Salvador",SY:"Syria",SZ:"Swaziland",TC:"Turks and Caicos Islands",TD:"Chad",TF:"French Southern Territories",TG:"Togo",TH:"Thailand",TJ:"Tajikistan",TK:"Tokelau",TL:"Timor-Leste",TM:"Turkmenistan",TN:"Tunisia",TO:"Tonga",TR:"Turkey",TT:"Trinidad and Tobago",TV:"Tuvalu",TW:"Taiwan",TZ:"Tanzania",UA:"Ukraine",UG:"Uganda",UM:"U.S. Minor Outlying Islands",US:"United States",UY:"Uruguay",UZ:"Uzbekistan",VA:"Vatican City",VC:"Saint Vincent and the Grenadines",VD:"North Vietnam",VE:"Venezuela",VG:"British Virgin Islands",VI:"U.S. Virgin Islands",VN:"Vietnam",VU:"Vanuatu",WF:"Wallis and Futuna",WK:"Wake Island",WS:"Samoa",YD:"People's Democratic Republic of Yemen",YE:"Yemen",YT:"Mayotte",ZA:"South Africa",ZM:"Zambia",ZW:"Zimbabwe",ZZ:"Unknown or Invalid Region" };

      var countriesArray = $.map(countries, function(value, key) {
        return {
          value: value,
          data: key
        };
      });

      // initialize autocomplete with custom appendTo
      $('#autocomplete-custom-append').autocomplete({
        lookup: countriesArray,
        appendTo: '#autocomplete-container'
      });
    });

	
}

function init_echarts($http,$rootScope,$scope,baseUrl,$filter,$window){
	
	$scope.cmonths = [];
	$http.get(baseUrl + "/quotes/montlyGraph?year=2020").success(function (data) {
		  $scope.cmonths = data;	
		  var months =  $scope.cmonths.list_str_month;
		  var countAll = $scope.cmonths.list_tot_quotes;
		  var countOrd = $scope.cmonths.list_tot_quotes_ord;
		  var totalPo = $scope.cmonths.list_po_amount;
		  
		  var theme = {
		            color: [
		                '#26B99A', '#34495E', '#BDC3C7', '#3498DB',
		                '#9B59B6', '#8abb6f', '#759c6a', '#bfd3b7'
		            ],

		            title: {
		                itemGap: 8,
		                textStyle: {
		                    fontWeight: 'normal',
		                    color: '#408829'
		                }
		            },

		            dataRange: {
		                color: ['#1f610a', '#97b58d']
		            },

		            toolbox: {
		                color: ['#408829', '#408829', '#408829', '#408829']
		            },

		            tooltip: {
		                backgroundColor: 'rgba(0,0,0,0.5)',
		                axisPointer: {
		                    type: 'line',
		                    lineStyle: {
		                        color: '#408829',
		                        type: 'dashed'
		                    },
		                    crossStyle: {
		                        color: '#408829'
		                    },
		                    shadowStyle: {
		                        color: 'rgba(200,200,200,0.3)'
		                    }
		                }
		            },

		            dataZoom: {
		                dataBackgroundColor: '#eee',
		                fillerColor: 'rgba(64,136,41,0.2)',
		                handleColor: '#408829'
		            },
		            grid: {
		                borderWidth: 0
		            },

		            categoryAxis: {
		                axisLine: {
		                    lineStyle: {
		                        color: '#408829'
		                    }
		                },
		                splitLine: {
		                    lineStyle: {
		                        color: ['#eee']
		                    }
		                }
		            },

		            valueAxis: {
		                axisLine: {
		                    lineStyle: {
		                        color: '#408829'
		                    }
		                },
		                splitArea: {
		                    show: true,
		                    areaStyle: {
		                        color: ['rgba(250,250,250,0.1)', 'rgba(200,200,200,0.1)']
		                    }
		                },
		                splitLine: {
		                    lineStyle: {
		                        color: ['#eee']
		                    }
		                }
		            },
		            timeline: {
		                lineStyle: {
		                    color: '#408829'
		                },
		                controlStyle: {
		                    normal: { color: '#408829' },
		                    emphasis: { color: '#408829' }
		                }
		            },

		            k: {
		                itemStyle: {
		                    normal: {
		                        color: '#68a54a',
		                        color0: '#a9cba2',
		                        lineStyle: {
		                            width: 1,
		                            color: '#408829',
		                            color0: '#86b379'
		                        }
		                    }
		                }
		            },
		            map: {
		                itemStyle: {
		                    normal: {
		                        areaStyle: {
		                            color: '#ddd'
		                        },
		                        label: {
		                            textStyle: {
		                                color: '#c12e34'
		                            }
		                        }
		                    },
		                    emphasis: {
		                        areaStyle: {
		                            color: '#99d2dd'
		                        },
		                        label: {
		                            textStyle: {
		                                color: '#c12e34'
		                            }
		                        }
		                    }
		                }
		            },
		            force: {
		                itemStyle: {
		                    normal: {
		                        linkStyle: {
		                            strokeColor: '#408829'
		                        }
		                    }
		                }
		            },
		            chord: {
		                padding: 4,
		                itemStyle: {
		                    normal: {
		                        lineStyle: {
		                            width: 1,
		                            color: 'rgba(128, 128, 128, 0.5)'
		                        },
		                        chordStyle: {
		                            lineStyle: {
		                                width: 1,
		                                color: 'rgba(128, 128, 128, 0.5)'
		                            }
		                        }
		                    },
		                    emphasis: {
		                        lineStyle: {
		                            width: 1,
		                            color: 'rgba(128, 128, 128, 0.5)'
		                        },
		                        chordStyle: {
		                            lineStyle: {
		                                width: 1,
		                                color: 'rgba(128, 128, 128, 0.5)'
		                            }
		                        }
		                    }
		                }
		            },
		            gauge: {
		                startAngle: 225,
		                endAngle: -45,
		                axisLine: {
		                    show: true,
		                    lineStyle: {
		                        color: [[0.2, '#86b379'], [0.8, '#68a54a'], [1, '#408829']],
		                        width: 8
		                    }
		                },
		                axisTick: {
		                    splitNumber: 10,
		                    length: 12,
		                    lineStyle: {
		                        color: 'auto'
		                    }
		                },
		                axisLabel: {
		                    textStyle: {
		                        color: 'auto'
		                    }
		                },
		                splitLine: {
		                    length: 18,
		                    lineStyle: {
		                        color: 'auto'
		                    }
		                },
		                pointer: {
		                    length: '90%',
		                    color: 'auto'
		                },
		                title: {
		                    textStyle: {
		                        color: '#333'
		                    }
		                },
		                detail: {
		                    textStyle: {
		                        color: 'auto'
		                    }
		                }
		            },
		            textStyle: {
		                fontFamily: 'Arial, Verdana, sans-serif'
		            }
		        };


		        var echartBar = echarts.init(document.getElementById('mainb'), theme);

		        echartBar.setOption({
		            title: {
		                text: 'Quotes',
		                subtext: 'Requested / Ordered'
		            },
		            tooltip: {
		                trigger: 'axis'
		            },
		            legend: {
		                data: ['Requested', 'Ordered']
		            },
		            toolbox: {
		                show: false
		            },
		            calculable: false,
		            xAxis: [{
		                type: 'category',
		                data: months
		            }],
		            yAxis: [{
		                type: 'value'
		            }],
		            series: [{
		                name: 'Requested',
		                type: 'bar',
		                data: countAll, 
		                markPoint: {
		                    data: [{
		                        type: 'max',
		                        name: 'Total',
		                    }, {
		                        type: 'min',
		                        name: 'Total'
		                    }]
		                },
		                markLine: {
		                    data: [{
		                        type: 'average',
		                        name: 'Avg.'
		                    }]
		                }
		            }, {
		                name: 'Ordered',
		                type: 'bar',
		                data: countOrd,
		                markPoint: {
		                    data: [{
		                        type: 'max',
		                        name: 'Total',
		                    }, {
		                        type: 'min',
		                        name: 'Total'
		                    }]
		                },
		                markLine: {
		                    data: [{
		                        type: 'average',
		                        name: 'Avg.'
		                    }]
		                }
		            }]
		        });
		
			
	});
	
	
  

	
	
}


function iniciar_elementos(){
	
	angular.element(document).ready(function() {
    var theme = {
            color: [
                '#26B99A', '#34495E', '#BDC3C7', '#3498DB',
                '#9B59B6', '#8abb6f', '#759c6a', '#bfd3b7'
            ],

            title: {
                itemGap: 8,
                textStyle: {
                    fontWeight: 'normal',
                    color: '#408829'
                }
            },

            dataRange: {
                color: ['#1f610a', '#97b58d']
            },

            toolbox: {
                color: ['#408829', '#408829', '#408829', '#408829']
            },

            tooltip: {
                backgroundColor: 'rgba(0,0,0,0.5)',
                axisPointer: {
                    type: 'line',
                    lineStyle: {
                        color: '#408829',
                        type: 'dashed'
                    },
                    crossStyle: {
                        color: '#408829'
                    },
                    shadowStyle: {
                        color: 'rgba(200,200,200,0.3)'
                    }
                }
            },

            dataZoom: {
                dataBackgroundColor: '#eee',
                fillerColor: 'rgba(64,136,41,0.2)',
                handleColor: '#408829'
            },
            grid: {
                borderWidth: 0
            },

            categoryAxis: {
                axisLine: {
                    lineStyle: {
                        color: '#408829'
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: ['#eee']
                    }
                }
            },

            valueAxis: {
                axisLine: {
                    lineStyle: {
                        color: '#408829'
                    }
                },
                splitArea: {
                    show: true,
                    areaStyle: {
                        color: ['rgba(250,250,250,0.1)', 'rgba(200,200,200,0.1)']
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: ['#eee']
                    }
                }
            },
            timeline: {
                lineStyle: {
                    color: '#408829'
                },
                controlStyle: {
                    normal: {color: '#408829'},
                    emphasis: {color: '#408829'}
                }
            },

            k: {
                itemStyle: {
                    normal: {
                        color: '#68a54a',
                        color0: '#a9cba2',
                        lineStyle: {
                            width: 1,
                            color: '#408829',
                            color0: '#86b379'
                        }
                    }
                }
            },
            map: {
                itemStyle: {
                    normal: {
                        areaStyle: {
                            color: '#ddd'
                        },
                        label: {
                            textStyle: {
                                color: '#c12e34'
                            }
                        }
                    },
                    emphasis: {
                        areaStyle: {
                            color: '#99d2dd'
                        },
                        label: {
                            textStyle: {
                                color: '#c12e34'
                            }
                        }
                    }
                }
            },
            force: {
                itemStyle: {
                    normal: {
                        linkStyle: {
                            strokeColor: '#408829'
                        }
                    }
                }
            },
            chord: {
                padding: 4,
                itemStyle: {
                    normal: {
                        lineStyle: {
                            width: 1,
                            color: 'rgba(128, 128, 128, 0.5)'
                        },
                        chordStyle: {
                            lineStyle: {
                                width: 1,
                                color: 'rgba(128, 128, 128, 0.5)'
                            }
                        }
                    },
                    emphasis: {
                        lineStyle: {
                            width: 1,
                            color: 'rgba(128, 128, 128, 0.5)'
                        },
                        chordStyle: {
                            lineStyle: {
                                width: 1,
                                color: 'rgba(128, 128, 128, 0.5)'
                            }
                        }
                    }
                }
            },
            gauge: {
                startAngle: 225,
                endAngle: -45,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: [[0.2, '#86b379'], [0.8, '#68a54a'], [1, '#408829']],
                        width: 8
                    }
                },
                axisTick: {
                    splitNumber: 10,
                    length: 12,
                    lineStyle: {
                        color: 'auto'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: 'auto'
                    }
                },
                splitLine: {
                    length: 18,
                    lineStyle: {
                        color: 'auto'
                    }
                },
                pointer: {
                    length: '90%',
                    color: 'auto'
                },
                title: {
                    textStyle: {
                        color: '#333'
                    }
                },
                detail: {
                    textStyle: {
                        color: 'auto'
                    }
                }
            },
            textStyle: {
                fontFamily: 'Arial, Verdana, sans-serif'
            }
        };
        
        var echartPie = echarts.init(document.getElementById('echart_pie'), theme);

        echartPie.setOption({
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            x: 'center',
            y: 'bottom',
            data: ['Direct Access', 'E-mail Marketing', 'Union Ad', 'Video Ads', 'Search Engine']
          },
          toolbox: {
            show: true,
            feature: {
              magicType: {
                show: true,
                type: ['pie', 'funnel'],
                option: {
                  funnel: {
                    x: '25%',
                    width: '50%',
                    funnelAlign: 'left',
                    max: 1548
                  }
                }
              },
              restore: {
                show: true,
                title: "Restore"
              },
              saveAsImage: {
                show: true,
                title: "Save Image"
              }
            }
          },
          calculable: true,
          series: [{
            name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '48%'],
            data: [{
              value: 335,
              name: 'Direct Access'
            }, {
              value: 310,
              name: 'E-mail Marketing'
            }, {
              value: 234,
              name: 'Union Ad'
            }, {
              value: 135,
              name: 'Video Ads'
            }, {
              value: 1548,
              name: 'Search Engine'
            }]
          }]
        });

        var dataStyle = {
          normal: {
            label: {
              show: false
            },
            labelLine: {
              show: false
            }
          }
        };

        var placeHolderStyle = {
          normal: {
            color: 'rgba(0,0,0,0)',
            label: {
              show: false
            },
            labelLine: {
              show: false
            }
          },
          emphasis: {
            color: 'rgba(0,0,0,0)'
          }
        };	
        
    });        
	
    angular.element(document).ready(function() {
        //define chart clolors ( you maybe add more colors if you want or flot will add it automatic )
        var chartColours = ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'];

        //generate random number for charts
        randNum = function() {
          return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
        };

        var d1 = [];
        //var d2 = [];

        //here we generate data for chart
        for (var i = 0; i < 30; i++) {
          d1.push([new Date(Date.today().add(i).days()).getTime(), randNum() + i + i + 10]);
          //    d2.push([new Date(Date.today().add(i).days()).getTime(), randNum()]);
        }

        var chartMinDate = d1[0][0]; //first day
        var chartMaxDate = d1[20][0]; //last day

        var tickSize = [1, "day"];
        var tformat = "%d/%m/%y";

        //graph options
        var options = {
          grid: {
            show: true,
            aboveData: true,
            color: "#3f3f3f",
            labelMargin: 10,
            axisMargin: 0,
            borderWidth: 0,
            borderColor: null,
            minBorderMargin: 5,
            clickable: true,
            hoverable: true,
            autoHighlight: true,
            mouseActiveRadius: 100
          },
          series: {
            lines: {
              show: true,
              fill: true,
              lineWidth: 2,
              steps: false
            },
            points: {
              show: true,
              radius: 4.5,
              symbol: "circle",
              lineWidth: 3.0
            }
          },
          legend: {
            position: "ne",
            margin: [0, -25],
            noColumns: 0,
            labelBoxBorderColor: null,
            labelFormatter: function(label, series) {
              // just add some space to labes
              return label + '&nbsp;&nbsp;';
            },
            width: 40,
            height: 1
          },
          colors: chartColours,
          shadowSize: 0,
          tooltip: true, //activate tooltip
          tooltipOpts: {
            content: "%s: %y.0",
            xDateFormat: "%d/%m",
            shifts: {
              x: -30,
              y: -50
            },
            defaultTheme: false
          },
          yaxis: {
            min: 0
          },
          xaxis: {
            mode: "time",
            minTickSize: tickSize,
            timeformat: tformat,
            min: chartMinDate,
            max: chartMaxDate
          }
        };
        var plot = $.plot($("#placeholder33x"), [{
          label: "Valor PR",
          data: d1,
          lines: {
            fillColor: "rgba(150, 202, 89, 0.12)"
          }, //#96CA59 rgba(150, 202, 89, 0.42)
          points: {
            fillColor: "#fff"
          }
        }], options);
      });
   
	
	angular.element(document).ready(function() {
        var data1 = [
          [gd(2012, 1, 1), 17],
          [gd(2012, 1, 2), 74],
          [gd(2012, 1, 3), 6],
          [gd(2012, 1, 4), 39],
          [gd(2012, 1, 5), 20],
          [gd(2012, 1, 6), 85],
          [gd(2012, 1, 7), 7]
        ];

        var data2 = [
          [gd(2012, 1, 1), 82],
          [gd(2012, 1, 2), 23],
          [gd(2012, 1, 3), 66],
          [gd(2012, 1, 4), 9],
          [gd(2012, 1, 5), 119],
          [gd(2012, 1, 6), 6],
          [gd(2012, 1, 7), 9]
        ];
        $("#canvas_dahs").length && $.plot($("#canvas_dahs"), [
          data1, data2
        ], {
          series: {
            lines: {
              show: false,
              fill: true
            },
            splines: {
              show: true,
              tension: 0.4,
              lineWidth: 1,
              fill: 0.4
            },
            points: {
              radius: 0,
              show: true
            },
            shadowSize: 2
          },
          grid: {
            verticalLines: true,
            hoverable: true,
            clickable: true,
            tickColor: "#d5d5d5",
            borderWidth: 1,
            color: '#fff'
          },
          colors: ["rgba(38, 185, 154, 0.38)", "rgba(3, 88, 106, 0.38)"],
          xaxis: {
            tickColor: "rgba(51, 51, 51, 0.06)",
            mode: "time",
            tickSize: [1, "day"],
            //tickLength: 10,
            axisLabel: "Date",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Verdana, Arial',
            axisLabelPadding: 10
          },
          yaxis: {
            ticks: 8,
            tickColor: "rgba(51, 51, 51, 0.06)",
          },
          tooltip: false
        });

        function gd(year, month, day) {
          return new Date(year, month - 1, day).getTime();
        }
      });	
	
	angular.element(document).ready(function() {
	      var cb = function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	        $('#reportrange_right span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	      };

	      var optionSet1 = {
	        startDate: moment().subtract(29, 'days'),
	        endDate: moment(),
	        minDate: '01/01/2012',
	        maxDate: '12/31/2015',
	        dateLimit: {
	          days: 60
	        },
	        showDropdowns: true,
	        showWeekNumbers: true,
	        timePicker: false,
	        timePickerIncrement: 1,
	        timePicker12Hour: true,
	        ranges: {
	          'Today': [moment(), moment()],
	          'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	          'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	          'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	          'This Month': [moment().startOf('month'), moment().endOf('month')],
	          'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	        },
	        opens: 'right',
	        buttonClasses: ['btn btn-default'],
	        applyClass: 'btn-small btn-primary',
	        cancelClass: 'btn-small',
	        format: 'MM/DD/YYYY',
	        separator: ' to ',
	        locale: {
	          applyLabel: 'Submit',
	          cancelLabel: 'Clear',
	          fromLabel: 'From',
	          toLabel: 'To',
	          customRangeLabel: 'Custom',
	          daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	          monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
	          firstDay: 1
	        }
	      };

	      $('#reportrange_right span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));

	      $('#reportrange_right').daterangepicker(optionSet1, cb);

	      $('#reportrange_right').on('show.daterangepicker', function() {
	        console.log("show event fired");
	      });
	      $('#reportrange_right').on('hide.daterangepicker', function() {
	        console.log("hide event fired");
	      });
	      $('#reportrange_right').on('apply.daterangepicker', function(ev, picker) {
	        console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
	      });
	      $('#reportrange_right').on('cancel.daterangepicker', function(ev, picker) {
	        console.log("cancel event fired");
	      });

	      $('#options1').click(function() {
	        $('#reportrange_right').data('daterangepicker').setOptions(optionSet1, cb);
	      });

	      $('#options2').click(function() {
	        $('#reportrange_right').data('daterangepicker').setOptions(optionSet2, cb);
	      });

	      $('#destroy').click(function() {
	        $('#reportrange_right').data('daterangepicker').remove();
	      });

	    });



	  angular.element(document).ready(function() {
	      var cb = function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	      };

	      var optionSet1 = {
	        startDate: moment().subtract(29, 'days'),
	        endDate: moment(),
	        minDate: '01/01/2012',
	        maxDate: '12/31/2015',
	        dateLimit: {
	          days: 60
	        },
	        showDropdowns: true,
	        showWeekNumbers: true,
	        timePicker: false,
	        timePickerIncrement: 1,
	        timePicker12Hour: true,
	        ranges: {
	          'Today': [moment(), moment()],
	          'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	          'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	          'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	          'This Month': [moment().startOf('month'), moment().endOf('month')],
	          'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	        },
	        opens: 'left',
	        buttonClasses: ['btn btn-default'],
	        applyClass: 'btn-small btn-primary',
	        cancelClass: 'btn-small',
	        format: 'MM/DD/YYYY',
	        separator: ' to ',
	        locale: {
	          applyLabel: 'Submit',
	          cancelLabel: 'Clear',
	          fromLabel: 'From',
	          toLabel: 'To',
	          customRangeLabel: 'Custom',
	          daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	          monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
	          firstDay: 1
	        }
	      };
	      $('#reportrange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
	      $('#reportrange').daterangepicker(optionSet1, cb);
	      $('#reportrange').on('show.daterangepicker', function() {
	        console.log("show event fired");
	      });
	      $('#reportrange').on('hide.daterangepicker', function() {
	        console.log("hide event fired");
	      });
	      $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
	        console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
	      });
	      $('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
	        console.log("cancel event fired");
	      });
	      $('#options1').click(function() {
	        $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
	      });
	      $('#options2').click(function() {
	        $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
	      });
	      $('#destroy').click(function() {
	        $('#reportrange').data('daterangepicker').remove();
	      });
	    });


	 
	  angular.element(document).ready(function() {
	      $('#single_cal1').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_1"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	      $('#single_cal2').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_2"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	      $('#single_cal3').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_3"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	      $('#single_cal4').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_4"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	    });
	 

	 
	  angular.element(document).ready(function() {
	      $('#reservation').daterangepicker(null, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	    });
	  
	  //Nuevo en la Oficina
	  

	  
	  angular.element(document).ready(function() {
	         var handleDataTableButtons = function() {
	           if ($("#tbqestyear").length) {
	             $("#tbqestyear").DataTable({
	               dom: "Bfrtip",
	               buttons: [
	                 {
	                   extend: "copy",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "csv",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "excel",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "pdfHtml5",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "print",
	                   className: "btn-sm"
	                 },
	               ],
	               responsive: true
	             });
	           }
	         };

	         TableManageButtons = function() {
	           "use strict";
	           return {
	             init: function() {
	               handleDataTableButtons();
	             }
	           };
	         }();

	         $('#datatable1').dataTable();
	         $('#datatable-keytable').DataTable({
	           keys: true
	         });

	         //$('#tbfmeca1').DataTable();

	         $('#datatable-scroller').DataTable({
	           ajax: "js/datatables/json/scroller-demo.json",
	           deferRender: true,
	           scrollY: 380,
	           scrollCollapse: true,
	           scroller: true
	         });

	         var table = $('#datatable-fixed-header').DataTable({
	           fixedHeader: true
	         });

	         TableManageButtons.init();
	       });	 
	  
	  
	  
	 

	  
}

/*app.config(function ($httpProvider) { 
	  $httpProvider.responseInterceptors.push('myHttpInterceptor');

	  var spinnerFunction = function spinnerFunction(data, headersGetter) {
	    $("#spinner").show();
	    return data;
	  };

	  $httpProvider.defaults.transformRequest.push(spinnerFunction);
}); */


app.factory('myHttpInterceptor', function ($q, $window) {
	  return function (promise) {
	    return promise.then(function (response) {
	      $("#spinner").hide();
	      return response;
	    }, function (response) {
	      $("#spinner").hide();
	      return $q.reject(response);
	    });
	  };
});


app.provider("remoteResource", RemoteResourceProvider);

app.config(['$routeProvider','$httpProvider',function($routeProvider,$httpProvider) {
	  
	  $httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = '*';                                                                                                                                                                                                                                                                                    
	  
	  $routeProvider.when('/', {
	    templateUrl: 'plainpage.html',
	    controller: 'dashboardController'
	  }); 
	  
	  
	  $routeProvider.when('/dashboard', { 
		    templateUrl: 'dashboard.html',
		    controller: 'homeController'
	  });	  
	  
	  $routeProvider.when('/dashboardSeller', {
		    templateUrl: 'dashboardSeller.html',
		    controller: 'homeController'
	  });		  
	  
	  $routeProvider.when('/dashboardEstimator', {
		    templateUrl: 'dashboardEstimator.html',
		    controller: 'homeController'
	  });	  
	  
	  $routeProvider.when('/add-qlead', {
		  templateUrl : 'leadwizard.html',
		  controller : 'quoteleadController'
	  });	
	  
	  
	  $routeProvider.when('/add-qlead-inco', {
		  templateUrl : 'leadwizard_incoterms.html',
		  controller : 'quoteleadController'
	  });
	  
	  
	  $routeProvider.when('/find-item', {
		  templateUrl : 'items.html',
		  controller : 'querysController' 
	  });	
	  
	  $routeProvider.when('/find-itemSeller', {
		  templateUrl : 'items_seller.html',
		  controller : 'querysController' 
	  });
	  
	  $routeProvider.when('/find-item-estimator', { 
		  templateUrl : 'items_estimator.html',
		  controller : 'querysController'  
	  });	  
	  
	  $routeProvider.when('/quote-detail', {
		  templateUrl : 'quoteDetail.html',
		  controller : 'detQuotesController' 
	  });
	  
	  
	  $routeProvider.when('/quotenote-detail?:event', {
		  templateUrl : 'quoteDetail.html',
		  controller : 'emailnoteController'
	  });
	  
	  
	  $routeProvider.when('/download', {
		  templateUrl : 'quoteDetail.html',
		  controller : 'detQuotesController'
	  });	  
	  
	  
	  $routeProvider.when('/item-sheetcost', { 
		  templateUrl : 'itemsheetcost.html',
		  controller : 'itemController'
	  });
	  
	  $routeProvider.when('/item-sheetcost-new', { 
		  templateUrl : 'itemsheetcost_new.html', 
		  controller : 'itemController'
	  });
	  
	  /*
	  
	  $routeProvider.when('/item-sheetcost', { 
		  templateUrl : 'itemsheetcost.html',
		  controller : 'itemController'
	  });
	  
	  */
	  $routeProvider.when('/vCustomerQuotes', { 
		  templateUrl : 'quotesByCustomer.html',
		  controller : 'quoteleadController'
	  });
	  
	  $routeProvider.when('/quote-leads', { 
		  templateUrl : 'quotes.html',
		  controller : 'quoteleadController'
	  });
	  
	  $routeProvider.when('/sharedquote-leads', { 
		  templateUrl : 'sharedQuotes.html',
		  controller : 'quoteleadController'
	  });
	  
	  $routeProvider.when('/qQuotesAll', { 
		  templateUrl : 'qQuotesAll.html',
		  controller : 'quoteleadController'
	  });
	  
	/*  $routeProvider.when('/qQuotesAll_Pending', { 
		  templateUrl : 'qQuotesAll_Pending.html',
		  controller : 'quoteleadController' 	  
	  });	  */
	  
	  $routeProvider.when('/findonequote', {
		  templateUrl : 'findonequote.html', 
		  controller : 'quoteleadController'
	  }); 
	  


	  
	  // Querys ----------------------------------
	  
	  $routeProvider.when('/qNotifications', {
		  templateUrl : 'qNotifications.html',
		  controller : 'notificationsController'
	  });		  
	  
	  $routeProvider.when('/qfollowup', { 
		  templateUrl : 'followupAsk.html',
		  controller : 'quoteleadController'
	  });	  
	  
	  $routeProvider.when('/q-quotesEstimatorsYear', {
		  templateUrl : 'qquotesEstimatorsYear.html',
		  controller : 'querysController'
	  });
	  
	  
	  $routeProvider.when('/q-sellerActivity', {
		  templateUrl : 'qsellerActivities.html',
		  controller : 'querysController'
	  });
	  

	  $routeProvider.when('/qQuotesCustomers', {
		  templateUrl : 'qQuotesCustomers.html',
		  controller : 'querysController'
	  });	  
	  
	  
	  $routeProvider.when('/qQuotePendingItems', {
		  templateUrl : 'qQuotesPendingItems.html',
		  controller : 'querysController'
	  });	
	  
	  
	  $routeProvider.when('/qQuotePendingEst', {
		  templateUrl : 'quotes _pending.html',
		  controller : 'querysController'
	  });
	  

	  $routeProvider.when('/qQuotesOnHoldEstimator', {
		  templateUrl : 'qQuotesOnHoldEstimator.html',
		  controller : 'querysController'
	  });
	  
	  $routeProvider.when('/qQuotesOrderedEstimator', {
		  templateUrl : 'qQuotesOrderedEstimator.html',
		  controller : 'querysController'
	  });
	  
	  $routeProvider.when('/qQuotePendingEst', {
		  templateUrl : 'quotes _pending.html',
		  controller : 'querysController'
	  });
	  
	  $routeProvider.when('/qSummarized', { 
		  templateUrl : 'qSummarized.html',
		  controller : 'querysController' 	  
	  });
	  
	  
	  $routeProvider.when('/qdashItemsOrdered', {     //*  Dashboard
		  templateUrl : 'ordered_items.html',
		  controller : 'dashboardQueriesController' 	  
	  });
	  
	  $routeProvider.when('/qdashItemsOrderedEst', {     //*  Dashboard :
		  templateUrl : 'ordered_itemsEst.html',
		  controller : 'dashboardQueriesController' 	 
	  });
	  
	  $routeProvider.when('/qdashItemsOrderedSel', {     //*  Dashboard :
		  templateUrl : 'ordered_itemsSel.html',
		  controller : 'dashboardQueriesController' 	 
	  });
	  
	  $routeProvider.when('/qQuotePendingItemsEst', {    //* Dashboard
		  templateUrl : 'qQuotesPendingItemsEst.html',
		  controller : 'dashboardQueriesController'        
	  });
	  
	  
	  $routeProvider.when('/qSummaryQuotes', {    //* Dashboard
		  templateUrl : 'qSummaryQuotes.html',
		  controller : 'dashboardQueriesController'        
	  });
	  
	  $routeProvider.when('/qMonthlyCount', {    //* Dashboard
		  templateUrl : 'qMonthlyCount.html',
		  controller : 'dashboardQueriesController'
	  });
	  
	  $routeProvider.when('/qQuotesAll_Pending', { 
		  templateUrl : 'qQuotesAll_Pending.html',
		  controller : 'dashboardQueriesController' 	  
	  });	
	  
	  
	  $routeProvider.when('/qQuotesSummarySeller', {    //* Dashboard
		  templateUrl : 'qSummaryQuotesSeller.html',
		  controller : 'dashboardQueriesController'        
	  });
	  
	  $routeProvider.when('/qQuotesSummaryEstimator', {    //* Dashboard
		  templateUrl : 'qSummaryQuotesEstimator.html',
		  controller : 'dashboardQueriesController'        
	  });
	  
	  $routeProvider.when('/qSummaryLostQuotesSellerYear', {    //* Dashboard
		  templateUrl : 'qSummaryLostQuotesSellerYear.html',
		  controller : 'dashboardQueriesController'        
	  });
	  
	  
	  
	  //  Basic Tables -----------------------------
	  
	  
	  $routeProvider.when('/estimators', {
		  templateUrl : 'estimators.html',
		  controller : 'estimatorsController'
	  });
	  
	  
	  $routeProvider.when('/sellers', {
		  templateUrl : 'sellers.html',
		  controller : 'sellersController'
	  });
	  
	  $routeProvider.when('/industry', {  
		  templateUrl : 'industries.html',
		  controller : 'industriesController'
	  });
	  
	  $routeProvider.when('/machine', {
		  templateUrl : 'machines.html',
		  controller : 'machinesController'
	  });
	  
	  $routeProvider.when('/measure', {
		  templateUrl : 'measureunits.html',
		  controller : 'measuresController'
	  });
	  
	  $routeProvider.when('/terms', {
		  templateUrl : 'terms.html',
		  controller : 'termsController'
	  });	
	  
	  $routeProvider.when('/termsExp', {
		  templateUrl : 'termsExp.html',
		  controller : 'termsController'
	  });
	  
	  $routeProvider.when('/sellerprofile', {
		  templateUrl : 'viewselprofile.html',
		  controller : 'profileselController'
	  });	  
	  
	  //---------------------------------------------
	  
	  $routeProvider.when('/seller-leads', {
		  templateUrl : 'quotesseller.html',
		  controller : 'quoteleadController'
	  });
	  
	  $routeProvider.when('/add-parts', {
		  templateUrl : 'parts.html',
		  controller : 'partsController'
	  });
	  
	  $routeProvider.when('/add-materials', {
		  templateUrl : 'materials.html',
		  controller : 'materialsController'
	  });	  
	  
	  $routeProvider.when('/scontrators', {
		  templateUrl : 'scontrator.html',
    	  controller : 'subcontController'
	  });

	  $routeProvider.when('/customers', {
		  templateUrl : 'customers.html',
		  controller : 'customersController'
	  });
	  
	  $routeProvider.when('/customersSeller', {
		  templateUrl : 'customersSeller.html',
		  controller : 'quoteleadController'
	  });
	  
	  $routeProvider.when('/sealhome', {
		  templateUrl : 'principal.html',
		  controller : 'homeController'
	  });
	  


	  $routeProvider.when('/inventory', {
		  templateUrl : 'invent.html',
		  controller : 'inventoryController'
	  });
	  
	  $routeProvider.when('/add-lead', {
		  templateUrl : 'leads.html',
		  controller : 'leadsController'
	  });		 

	  $routeProvider.when('/seller-lead', {
		  templateUrl : 'leads_seller.html',
		  controller : 'leadsController'
	  });
	  
	  $routeProvider.when('/proyect-wizard', {
		  templateUrl : 'proyectwizard.html',
		  controller : 'proyectsController'
	  });	  
	  
	  $routeProvider.when('/login', {
		  templateUrl : 'login.html',
		  controller : 'homeController'
	  });	
	  
	  $routeProvider.when('/Leads_per_Seller', {
		  templateUrl : 'q_leads_seller.html',
		  controller : 'querysController'
	  });
	  
	  $routeProvider.when('/Leads-Seller-Status', {
		  templateUrl : 'q_leads_seller_status.html',
		  controller : 'querysController'
	  });
	  

	  
	  $routeProvider.when('/proyects', {
		  templateUrl : 'proyects.html',
		  controller : 'proyectsController'
	  });
	  

	  
	  $routeProvider.when('/proyectsFinished', {
		  templateUrl : 'proyectsFinished.html',
		  controller : 'proyectsController'
	  });	  
	  
	  $routeProvider.when('/proyect/sign/embedded', {
		  templateUrl : 'viewEveloped.html',
		  controller : 'signerController'
	  });	  
	  
	  
	  $routeProvider.when('/proyect/sign/return/result?:event', {
		  templateUrl : 'signresult.html',
		  controller : 'resultsignerController'
	  });
	  
	  $routeProvider.when('/proyectActivity', {
		  templateUrl : 'proyectActivity.html',
		  controller : 'proyectsController'
	  });	  
	  
	  $routeProvider.when('/proyectAppoiment', {
		  templateUrl : 'proyectAppoiment.html',
		  controller : 'proyectsController'
	  });
	  
	  	  
	  $routeProvider.when('/profile', {
		  templateUrl : 'profile.html',
		  controller : 'profileController'
	  });
	  
	  $routeProvider.when('/proyectsSeller', {
		  templateUrl : 'proyectsSeller.html',
		  controller : 'proyectsController'
	  });
	  	  

	  $routeProvider.when('/Proyect-Expensive', {
		  templateUrl : 'proyectExpensive.html',
		  controller : 'detProyectsController'
	  });                   
	  
	  $routeProvider.when('/listaddress', {
		  templateUrl : 'listaddress.html',
		  controller : 'addressController'
	  });
	  

	  $routeProvider.when('/viewReport', {
		  templateUrl : 'viewReport.html',
		  controller : 'reportController'
	  });
	  
	  $routeProvider.when('/viewReportSel', {
		  templateUrl : 'q_leads_seller_profile.html',
		  controller : 'querysController'
	  });	
	  
	  $routeProvider.when('/viewReportOrig', {
		  templateUrl : 'q_leads_origin.html',
		  controller : 'querysController'
	  });
	  
	  $routeProvider.when('/viewReportLeadsAct', {
		  templateUrl : 'q_leads_act_dates.html',
		  controller : 'querysController'
	  });		  

	  $routeProvider.when('/viewFilterProjects', {
		  templateUrl : 'q_filter_projects.html',
		  controller : 'querysController'
	  });
	  

	  
	  $routeProvider.otherwise({
	        redirectTo: '/'
	  });   
	  
	  
	  
	  //--------------------------------  Shipping New Modulde ------------------------------------------------//
	  
	  
	  $routeProvider.when('/packings-pendings', {
		  templateUrl : 'packingSlips.html',
		  controller : 'shippingController'
	  });
	  
	  $routeProvider.when('/create-shipment', {
		  templateUrl : 'createShipment.html',
		  controller : 'shippingController'
	  });
	  
	  $routeProvider.when('/create-shipment-packing?:event', { 
		  templateUrl : 'createShipment.html',
		  controller : 'shippingController'
	  });
	  
	  $routeProvider.when('/shipmentsByCustomer', {
		  templateUrl : 'shipmentsByCustomer.html',
		  controller : 'shippingController'
	  });
	  
	  $routeProvider.when('/shipmentsByToday', {
		  templateUrl : 'shipmentsByToday.html',
		  controller : 'shippingController'
	  });
	  

	}]);



app.config(['notificationServiceProvider', function(notificationServiceProvider) {

	notificationServiceProvider

		.setDefaults({
            styling: 'bootstrap3',
			delay: 2500,
			buttons: {
				closer: false,
				closer_hover: false,
				sticker: false,
				sticker_hover: false
			},
			type: 'error'
		})

		// Configure a stack named 'bottom_right' that append a call 'stack-bottomright'
		.setStack('bottom_right', 'stack-bottomright', {
			dir1: 'up',
			dir2: 'left',
			firstpos1: 25,
			firstpos2: 25
		})

		// Configure a stack named 'top_left' that append a call 'stack-topleft'
		.setStack('top_left', 'stack-topleft', {
			dir1: 'down',
			dir2: 'right',
			push: 'top'
		})

	;

}]);


app.config(function($sceDelegateProvider) {
	 $sceDelegateProvider.resourceUrlWhitelist([
	   // Allow same origin resource loads.
	   'self',
	   // Allow loading from our assets domain.  Notice the difference between * and **.
	   'https://demo.docusign.net/Signing/**'], ['self','https://www.shipengine.com/**'],['self','https://api.shipengine.com/**']);
});


app.config(function(dropzoneOpsProvider){
	dropzoneOpsProvider.setOptions({
		url : './upload',
		maxFilesize : '10',
		//acceptedFiles : 'image/jpeg, images/jpg, image/png',
		addRemoveLinks : true,
		dictDefaultMessage : 'Click to add or drop files (5 max)',
		dictRemoveFile : 'Remove',
		dictResponseError : 'Could not upload this File'		
	});
});


app.constant("baseUrl", ".");
app.config(['baseUrl', 'remoteResourceProvider',
  function(baseUrl, remoteResourceProvider) {
    remoteResourceProvider.setBaseUrl(baseUrl);
  }
]);


app.config(['ChartJsProvider', function (ChartJsProvider) {
    // Configure all charts
	
    ChartJsProvider.setOptions({
      chartColors: ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'],
      //chartColors: [ '#803690', '#00ADF9', '#DCDCDC', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
      responsive: true
    });
    // Configure all line charts
    ChartJsProvider.setOptions('line', {
      showLines: true
    });
    
   /* ChartJsProvider.setOptions('doughnut', {
        legend: true
      });  */  
  }]);


app.filter('songTime',function(){

    return function (s) {
        var ms = s % 1000;
        s = (s - ms) / 1000;
        var secs = s % 60;
        s = (s - secs) / 60;
        var mins = s % 60;
        var hrs = (s - mins) / 60;
        
        if (secs < 10){
        	secs = '0' + secs;
        }
        
        if (mins < 10){
        	mins = '0' + mins;
        }
        
        if (hrs < 10){
        	hrs = '0' + hrs;
        }        
      

        return hrs + ':' + mins + ':' + secs;        
    };
});

app.filter('formatDate', function($filter)
		{
		 return function(input)
		 {
		  //alert(input);	 
		  if(input == null){ 
			  
			  return ""; 
			  
		  } 
		 
		  var _date = $filter('date')(new Date(input), 'yyyy-MM-dd');
		  
		  return _date;

		 };
		});


app.filter('time', function($filter)
		{
		 return function(input)
		 {
		  //alert(input);	 
		  if(input == null){ 
			  
			  return ""; 
			  
		  } 
		 
		  var _date = $filter('date')(new Date(input), 'HH:mm:ss');
		  
		  return _date;

		 };
		});


app.filter('fecActual',['$filter',  function($filter) {
    return function() {
        return $filter('date')(new Date(), 'yyyy-MM-dd');
    	//return $filter('date')(new Date(), 'HH:mm:ss');
    };
}])


app.directive('imageonload', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('load', function() {
            	
            });
        }
    };
});


app.directive('format', ['$filter', function ($filter) { 
    return {
        require: '?ngModel',
        link: function (scope, elem, attrs, ctrl) {
            if (!ctrl) return;


            ctrl.$formatters.unshift(function (a) {
                return $filter(attrs.format)(ctrl.$modelValue)
            });


            ctrl.$parsers.unshift(function (viewValue) {
                              
          $(elem).priceFormat({
            prefix: '',
            centsSeparator: '.',
            thousandsSeparator: ','
        });                
                         
                return elem[0].value; 
            });
        }
    };
}]);

(function($){$.fn.priceFormat=function(options){var defaults={prefix:'US$ ',suffix:'',centsSeparator:'.',thousandsSeparator:',',limit:false,centsLimit:2,clearPrefix:false,clearSufix:false,allowNegative:false,insertPlusSign:false};var options=$.extend(defaults,options);return this.each(function(){var obj=$(this);var is_number=/[0-9]/;var prefix=options.prefix;var suffix=options.suffix;var centsSeparator=options.centsSeparator;var thousandsSeparator=options.thousandsSeparator;var limit=options.limit;var centsLimit=options.centsLimit;var clearPrefix=options.clearPrefix;var clearSuffix=options.clearSuffix;var allowNegative=options.allowNegative;var insertPlusSign=options.insertPlusSign;if(insertPlusSign)allowNegative=true;function to_numbers(str){var formatted='';for(var i=0;i<(str.length);i++){char_=str.charAt(i);if(formatted.length==0&&char_==0)char_=false;if(char_&&char_.match(is_number)){if(limit){if(formatted.length<limit)formatted=formatted+char_}else{formatted=formatted+char_}}}return formatted}function fill_with_zeroes(str){while(str.length<(centsLimit+1))str='0'+str;return str}function price_format(str){var formatted=fill_with_zeroes(to_numbers(str));var thousandsFormatted='';var thousandsCount=0;if(centsLimit==0){centsSeparator="";centsVal=""}var centsVal=formatted.substr(formatted.length-centsLimit,centsLimit);var integerVal=formatted.substr(0,formatted.length-centsLimit);formatted=(centsLimit==0)?integerVal:integerVal+centsSeparator+centsVal;if(thousandsSeparator||$.trim(thousandsSeparator)!=""){for(var j=integerVal.length;j>0;j--){char_=integerVal.substr(j-1,1);thousandsCount++;if(thousandsCount%3==0)char_=thousandsSeparator+char_;thousandsFormatted=char_+thousandsFormatted}if(thousandsFormatted.substr(0,1)==thousandsSeparator)thousandsFormatted=thousandsFormatted.substring(1,thousandsFormatted.length);formatted=(centsLimit==0)?thousandsFormatted:thousandsFormatted+centsSeparator+centsVal}if(allowNegative&&(integerVal!=0||centsVal!=0)){if(str.indexOf('-')!=-1&&str.indexOf('+')<str.indexOf('-')){formatted='-'+formatted}else{if(!insertPlusSign)formatted=''+formatted;else formatted='+'+formatted}}if(prefix)formatted=prefix+formatted;if(suffix)formatted=formatted+suffix;return formatted}function key_check(e){var code=(e.keyCode?e.keyCode:e.which);var typed=String.fromCharCode(code);var functional=false;var str=obj.val();var newValue=price_format(str+typed);if((code>=48&&code<=57)||(code>=96&&code<=105))functional=true;if(code==8)functional=true;if(code==9)functional=true;if(code==13)functional=true;if(code==46)functional=true;if(code==37)functional=true;if(code==39)functional=true;if(allowNegative&&(code==189||code==109))functional=true;if(insertPlusSign&&(code==187||code==107))functional=true;if(!functional){e.preventDefault();e.stopPropagation();if(str!=newValue)obj.val(newValue)}}function price_it(){var str=obj.val();var price=price_format(str);if(str!=price)obj.val(price)}function add_prefix(){var val=obj.val();obj.val(prefix+val)}function add_suffix(){var val=obj.val();obj.val(val+suffix)}function clear_prefix(){if($.trim(prefix)!=''&&clearPrefix){var array=obj.val().split(prefix);obj.val(array[1])}}function clear_suffix(){if($.trim(suffix)!=''&&clearSuffix){var array=obj.val().split(suffix);obj.val(array[0])}}$(this).bind('keydown.price_format',key_check);$(this).bind('keyup.price_format',price_it);$(this).bind('focusout.price_format',price_it);if(clearPrefix){$(this).bind('focusout.price_format',function(){clear_prefix()});$(this).bind('focusin.price_format',function(){add_prefix()})}if(clearSuffix){$(this).bind('focusout.price_format',function(){clear_suffix()});$(this).bind('focusin.price_format',function(){add_suffix()})}if($(this).val().length>0){price_it();clear_prefix();clear_suffix()}})};$.fn.unpriceFormat=function(){return $(this).unbind(".price_format")};$.fn.unmask=function(){var field=$(this).val();var result="";for(var f in field){if(!isNaN(field[f])||field[f]=="-")result+=field[f]}return result}})(jQuery);



app.directive('dropzone2', function() {
    return {
        restrict: 'C',
        link: function(scope, element, attrs) {

            var config = {
                //url: 'http://localhost:8585/upload',
                url: './upload',
                maxFilesize: 100,
                paramName: "uploadfile",
                maxThumbnailFilesize: 10,
                parallelUploads: 2,
                autoProcessQueue: true
            };

            var eventHandlers = {
                'addedfile': function(file) {
                    scope.file = file;
                    if (this.files[1]!=null) {
                        this.removeFile(this.files[0]);
                    }
                    scope.$apply(function() {
                        scope.fileAdded = true;
                    });
                },

                'success': function (file, response) {
                }

            };

            dropzone = new Dropzone(element[0], config);

            angular.forEach(eventHandlers, function(handler, event) {
                dropzone.on(event, handler);
            });

            scope.processDropzone = function() {
                dropzone.processQueue();
            };

            scope.resetDropzone = function() {
                dropzone.removeAllFiles();
            }
        }
    }
});





//------------------------------------------------------------------------------------------------------------
// ----------------------                    CUSTOMERS    -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("customersController", ['$scope','$http','$filter','$uibModal','notificationService','$timeout', '$q', '$log',function($scope, $http,$filter,$uibModal,notificationService,$timeout, $q, $log) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listCustomers($http,$scope,baseUrl);
		
		
		//alert("Longitud : " + $scope.customers.length);
		//listCustomersExp($http,$scope,baseUrl);
		//listCustomersAll($http,$scope,baseUrl);
		$scope.client = {};
		$scope.typeC = "0";
		init_autocomplete();
		$scope.selectState = '';
		$scope.newCustomer = {};
		
		//customersAutocomplete($http,$scope,baseUrl,$timeout, $q, $log);
		//$timeout(function () { }, 2000);
		//dashboard1($http,$scope,baseUrl);
		
		
		
		
		
		
		
		/* END TEST WITH TIMEOUT PROMISE 
		
		var promise = $timeout(3000);
		
		promise.then(function($timeout, $q, $log) {
		
		
		*/
		
		
		var self = this;
		self.simulateQuery = false;
		self.isDisabled    = false;
		self.repos         = loadCustomers();
		self.querySearch   = querySearch;
		self.selectedItemChange = selectedItemChange;
		self.searchTextChange   = searchTextChange;
		
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
			}
			function selectedItemChange(item) {
			  $log.info('Item changed to ' + JSON.stringify(item));
			  $scope.newCustomer.id = item.sid;
			}
			/**
			 * Build `components` list of key/value pairs */
			 
			function loadAll() {
			  var repos = [
			    {
			      'name'      : 'Angular 1',
			      'url'       : 'https://github.com/angular/angular.js',
			      'watchers'  : '3,623',
			      'forks'     : '16,175',
			    },
			    {
			      'name'      : 'Angular 2',
			      'url'       : 'https://github.com/angular/angular',
			      'watchers'  : '469',
			      'forks'     : '760',
			    },
			    {
			      'name'      : 'Angular Material',
			      'url'       : 'https://github.com/angular/material',
			      'watchers'  : '727',
			      'forks'     : '1,241',
			    },
			    {
			      'name'      : 'Bower Material',
			      'url'       : 'https://github.com/angular/bower-material',
			      'watchers'  : '42',
			      'forks'     : '84',
			    },
			    {
			      'name'      : 'Material Start',
			      'url'       : 'https://github.com/angular/material-start', 
			      'watchers'  : '81',
			      'forks'     : '303',
			    }
			  ];
			  
			  
			  console.log('Customers 1 : ' +  JSON.stringify($scope.customers)) 
			  //var repos = $scope.customers;
			  return repos.map( function (repo) {
			    repo.value = repo.name.toLowerCase();
			    return repo;
			  });
			}
			
			
			
			function loadCustomers() {
			    var url = "./list-customers-exp";
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
			    	    	var city = {display : $scope.sourceTransfer[j].customerName, value : $scope.sourceTransfer[j].customerName.toLowerCase() , sid : $scope.sourceTransfer[j].customerId,  contact : $scope.sourceTransfer[j].soContact + ' - ' +  $scope.sourceTransfer[j].soPhone  };
			             	sourcetransfer.push(city);
			    	    }
			    
			            
			          /*  angular.forEach($scope.sourceTransfer.data, function(value, key){
			            	  
			             var city = {display : value.customerName, value : value.id , sid : value.cellPhone };
			              sourcetransfer.push(city);
			            });*/
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
			
			
			/*
		});	      
		
		/*END TEST WITH TIMEOUT PROMISE */
	
		
		
		
		
		//****************************************   Automcomplete **********************************************************  /
		
      /* 
            var self = this;
            self.simulateQuery = false;
            self.isDisabled    = false; 
            
            // list of states to be displayed
            self.states        = loadStates(); 
            self.querySearch   = querySearch; 
            self.selectedItemChange = selectedItemChange;
            self.searchTextChange   = searchTextChange;
            self.newState = newState;
            
            
            
            function newState(state) {
               alert("This functionality is yet to be implemented!");
            }
            
            function querySearch (query) {
               var results = query ? self.states.filter( createFilterFor(query) ) :
                  self.states, deferred;
                  
               if (self.simulateQuery) {
                  deferred = $q.defer();
                     
                  $timeout(function () { 
                     deferred.resolve( results ); 
                  }, 
                  Math.random() * 1000, false);
                  return deferred.promise;
               } else {
                  return results;
               }
            }
            function searchTextChange(text) {
               $log.info('Text changed to ' + text);
               $scope.selectState = '';
            }
            function selectedItemChange(item) {
               $log.info('Item changed to ' + JSON.stringify(item));
               $scope.selectState = item.value;
            }
            
            //build list of states as map of key-value pairs
   *        function loadStates() {
               var allStates = 'Alabama, Alaska, Arizona, Arkansas, California, Colorado, Connecticut, Delaware,\
                  Florida, Georgia, Hawaii, Idaho, Illinois, Indiana, Iowa, Kansas, Kentucky, Louisiana,\
                  Maine, Maryland, Massachusetts, Michigan, Minnesota, Mississippi, Missouri, Montana,\
                  Nebraska, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina,\
                  North Dakota, Ohio, Oklahoma, Oregon, Pennsylvania, Rhode Island, South Carolina,\
                  South Dakota, Tennessee, Texas, Utah, Vermont, Virginia, Washington, West Virginia,\
                  Wisconsin, Wyoming';
                  
               return allStates.split(/, +/g).map( function (state) {
                  return {
                     value: state.toLowerCase(),
                     display: state
                  };
               });
            } 
            
            function loadStates(){
            	var customerExp = $scope.customers;
                return customerExp.map( function (state) {
                    return {
                       value: state.id,
                    };
                });          	
            }
            
            //filter function for search query
            function createFilterFor(query) {
               var lowercaseQuery = angular.lowercase(query);
               return function filterFn(state) {
                  return (state.value.indexOf(lowercaseQuery) === 0);  
               };
            }
        		
        */
        /*   ************************************************   End Autocomplete ************************************************************/
		
		

		
		/* ***************************    ANOTHER TEST ********************************************************/
		
		
		
		
		$scope.initTable = function(){
			iniciar_tabla('#tbCust');
			//alert('Customers length : ' +  $scope.customers.length); 
			//alert("Init Table");
		}
		
		$scope.initTableExp = function(){
			iniciar_tabla('#tbCustExp');
			//alert("Init Table Exp");  
		}
		

		$scope.addCustomer = function(){   	
	    	
    		$http.post(baseUrl + '/client', $scope.client ).
    		success(function(response){
    			$('#addCustomer').modal('hide');
    			notificationService.success('Client Sucessfully registered');
    			$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customer = data;
    			});
    			$scope.txtDesc=null;
  
    		});
       }	
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removeCustomer = function(index){    	
				
			
			$http.put(baseUrl + '/client/' +$scope.customers[index].idClient, $scope.customers[index]).
			success(function(response){
				    notificationService.success('Client Sucessfully removed');
					$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customers = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrCustomer = function(index){
				$http.get(baseUrl + "/findCustomerById?companyId=" + $scope.customers[index].id).success(function (data) {      
					$scope.client = data; 

				});
		}
		
		$scope.editCustomer = function(){   	
	    	
    		$http.put(baseUrl + '/customer/edit', $scope.client ).
    		success(function(response){
    			$('#editCustomer').modal('hide');
    			$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customers = data;
					$scope.client = {};
    			});
 
  
    		});
        }		
		
}]);


//------------------------------------------------------------------------------------------------------------
//----------------------       TERMS UNITS               -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("termsController", ['$scope','$http','$filter','$uibModal','notificationService','$interval',function($scope, $http,$filter,$uibModal,notificationService,$interval) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		
		listTerms($http,$scope,baseUrl);
		listTermsExp($http,$scope,baseUrl);
		$scope.term = {};
		//dashboard1($http,$scope,baseUrl);

		
		$scope.refreshTable = function() {
			
			//listTermsExp($http,$scope,baseUrl);
			
			$http.get(baseUrl + "/list-terms-exp").success(function (data) {      
				
				$scope.aux = data;
				if ($scope.aux.length != $scope.termsExp.length){
				       $scope.termsExp = $scope.aux;
				       console.log($scope.termsExp.size);
				       iniciar_tabla('#tbTerms');
				}       
		    });
			
			
		}		
		
		$interval( function(){ $scope.refreshTable(); }, 1000);

		$scope.addTerm = function(){   	
	    	
		$http.post(baseUrl + '/terms/add', $scope.terms ).
		success(function(response){
			$('#addTerm').modal('hide');
			notificationService.success('Term Sucessfully registered');
			$http.get(baseUrl + "/list-terms").success(function (data) {      
					$scope.term = data;
			});
			$scope.txtDesc=null;

		});
        }
		
		
		$scope.addTermExp = function(){   	
	    	
			$http.post(baseUrl + '/terms-exp/new', $scope.term ).
			success(function(response){
				$('#addTerm').modal('hide');
				notificationService.success('Term Sucessfully registered');
				listTermsExp($http,$scope,baseUrl);
				
	
			});
        
		}		
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removeterm = function(index){    	
				
			
			$http.put(baseUrl + '/term/delete/' +$scope.terms[index].id, $scope.terms[index]).
			success(function(response){
				    notificationService.success('Term Unit Sucessfully removed');
					$http.get(baseUrl + "/list-terms").success(function (data) {      
					$scope.terms = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrTerm = function(index){
          $scope.termed = $scope.terms[index];
		}
		
		$scope.editTerm= function(){   	
	    	
		$http.put(baseUrl + '/term/edit/' + $scope.term.id, $scope.term ).
		success(function(response){
			$('#editTerm').modal('hide');
			$http.get(baseUrl + "/list-terms").success(function (data) {      
					$scope.terms = data;
			});
			$scope.txtDesc=null;

		});
}		
		
}]);

//------------------------------------------------------------------------------------------------------------
//----------------------       MEASURES UNITS               -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("measuresController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listmeasureunits($http,$scope,baseUrl);
		$scope.measure = {};
		//dashboard1($http,$scope,baseUrl);

		$scope.addMeasure = function(){   	
	    	
		$http.post(baseUrl + '/measure/new', $scope.measure ).
		success(function(response){
			$('#addMeasure').modal('hide');
			notificationService.success('Measure Sucessfully registered');
			$http.get(baseUrl + "/list-measureunits").success(function (data) {      
					$scope.measureunits = data;
			});
			$scope.txtDesc=null;

		});
   }	
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removemeasure = function(index){    	
				
			
			$http.put(baseUrl + '/measure/delete/' +$scope.measureunits[index].idMeasureunit, $scope.measureunits[index]).
			success(function(response){
				    notificationService.success('Measure Unit Sucessfully removed');
					$http.get(baseUrl + "/list-measureunits").success(function (data) {      
						$scope.measureunits = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrMeasure = function(index){
            $scope.measure = $scope.measureunits[index];
		}
		
		$scope.editMeasure= function(){   	
	    	
		$http.put(baseUrl + '/measure/edit/' + $scope.measure.idMeasureunit, $scope.measure ).
		success(function(response){
			notificationService.success('Measure Unit Sucessfully updated');
			$('#editMeasure').modal('hide');
			$http.get(baseUrl + "/list-measureunits").success(function (data) {      
					$scope.measureunits = data;
			});
			$scope.txtDesc=null;

		});
}		
		
}]);


//------------------------------------------------------------------------------------------------------------
//----------------------                   MACHINES    -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("machinesController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listmachines($http,$scope,baseUrl);
		$scope.machine = {};
		//dashboard1($http,$scope,baseUrl);

		$scope.addMachine = function(){   	
	    	
		$http.post(baseUrl + '/list-machines', $scope.machine ).
		success(function(response){
			$('#addMachine').modal('hide');
			notificationService.success('Machine Sucessfully registered');
			$http.get(baseUrl + "/list-machines").success(function (data) {      
					$scope.machine = data;
			});
			$scope.txtDesc=null;

		});
     }	
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removemachine = function(index){    	
				
			
			$http.put(baseUrl + '/machine/' +$scope.machines[index].id, $scope.machines[index]).
			success(function(response){
				    notificationService.success('Machines Sucessfully removed');
					$http.get(baseUrl + "/list-machines").success(function (data) {      
					$scope.estimators = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrMachine = function(index){
              $scope.machined = $scope.machines[index];
		}
		
		$scope.editMachine= function(){   	
	    	
		$http.put(baseUrl + '/machine/edit/' + $scope.estimator.id, $scope.estimator ).
		success(function(response){
			$('#editMachine').modal('hide');
			$http.get(baseUrl + "/list-machines").success(function (data) {      
					$scope.machines = data;
			});
			$scope.txtDesc=null;

		});
 }		
		
}]);



//------------------------------------------------------------------------------------------------------------
//----------------------                   INDUSTRY    -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("industriesController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listIndustries($http,$scope,baseUrl);
		$scope.industry = {};
		//dashboard1($http,$scope,baseUrl);

		$scope.addIndustry = function(){   	
	    	
		$http.post(baseUrl + '/list-industries', $scope.industry ).
		success(function(response){
			$('#addIndustry').modal('hide');
			notificationService.success('Industry Sucessfully registered');
			$http.get(baseUrl + "/list-industries").success(function (data) {      
					$scope.insdustry = data;
			});
			$scope.txtDesc=null;

		});
       }	
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removeinsdustry = function(index){    	
				
			
			$http.put(baseUrl + '/industry/' +$scope.insdustriues[index].id, $scope.estimators[index]).
			success(function(response){
				    notificationService.success('Industry Sucessfully removed');
					$http.get(baseUrl + "/list-estimators").success(function (data) {      
					$scope.estimators = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrIndsutry = function(index){
                $scope.industryed = $scope.insdustries[index];
		}
		
		$scope.editIndustry= function(){   	
	    	
		$http.put(baseUrl + '/industry/edit/' + $scope.estimator.id, $scope.estimator ).
		success(function(response){
			$('#editIndustry').modal('hide');
			$http.get(baseUrl + "/list-industries").success(function (data) {      
					$scope.industries = data;
			});
			$scope.txtDesc=null;

		});
   }		
		
}]);




//------------------------------------------------------------------------------------------------------------
//----------------------                   ESTIMATORS    -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("estimatorsController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listEstimators($http,$scope,baseUrl);
		$scope.estimator = {};
		//dashboard1($http,$scope,baseUrl);

		$scope.addEstimator = function(){   	
	    	
 		$http.post(baseUrl + '/estimator/new', $scope.estimator ).
 		success(function(response){
 			$('#addEstimator').modal('hide');
 			notificationService.success('Estimator Sucessfully registered');
 			$http.get(baseUrl + "/list-estimators").success(function (data) {      
					$scope.estimator = data;
 			});
 			$scope.txtDesc=null;

 		});
        }	
		
 		$scope.clicknewEstimator = function(){
 			
 			
 			$scope.estimator = {};
 			
 		}
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removeEstimator = function(index){    	
				
			
			$http.put(baseUrl + '/estimator/delete/' +$scope.estimators[index].id).
			success(function(response){
				    notificationService.success('Estimator Sucessfully removed');
					$http.get(baseUrl + "/list-estimators").success(function (data) {      
					$scope.estimators = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrEstimator = function(index){
			/*	$http.get(baseUrl + "/findOneEstimator?id=" + $scope.estimators[index].id).success(function (data) {      
					$scope.estimator = data; 

				});*/
			
			$scope.estimator = $scope.estimators[index];
		}
		
		$scope.editEstimator = function(){   	
	    	
 		$http.put(baseUrl + '/estimator/edit/' + $scope.estimator.id, $scope.estimator ).
 		success(function(response){
 			$('#editEstimator').modal('hide');
 			$http.get(baseUrl + "/list-estimators").success(function (data) {    
 				   
					$scope.estimators = data;
					notificationService.success('Estimator Sucessfully updated');
 			});
 			$scope.txtDesc=null;

 		});
 		
 		

     }		
		
}]);

//------------------------------------------------------------------------------------------------------------
//----------------------                    SELLERS    ----------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("profileselController", ['$rootScope','$scope','$http','$filter','$uibModal','notificationService','$location','$window',function($rootScope, $scope, $http,$filter,$uibModal,notificationService,$location,$window) {
	
	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	listquotessellerbyid($http,$scope,baseUrl,$rootScope.actSeller.id);
	percentsitemseller($http,$scope,baseUrl,$rootScope.actSeller.id)
	activitiesseller($http,$scope,baseUrl,$rootScope.actSeller.id);
	sellergraphicprofile($http,$scope,baseUrl,$rootScope.actSeller.id);
	
	//$scope.order = 75;
	
	
      $scope.findListQuote = function(index) {
                 
    	  
    	        //alert($scope.quotes[index].nroRfq);
       		    $rootScope.quoActual = $scope.quotes[index];
  				//$window.location.assign(baseUrl + '/sealhome#/quote-detail');
  				$window.open(baseUrl + '/sealhome#/quotenote-detail?quoteID='+$scope.quotes[index].id, '_blank');
   	
      }
	

	
}]);

app.controller("sellersController", ['$rootScope','$scope','$http','$filter','$uibModal','notificationService','$location','$window',function($rootScope, $scope, $http,$filter,$uibModal,notificationService,$location,$window) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listSellers($http,$scope,baseUrl);
		$scope.seller = {};
		$scope.userAct = {};
		$scope.user  = {};
		
		

		//$scope.actSeller = {};
		
		//alert("Seller");
		
		
		/*
		var Files = $resource('/images/:id', { id: "@id" });

		angular.extend($scope, {

			model: { file: null },

			upload: function(model) {
				Files.prototype.$save.call(model.file, function(self, headers) {
					// Handle server response
				});
			}
		});
		
		*/

		$scope.addSeller = function(){   	
	    
	
			
 		$http.post(baseUrl + '/seller/new', $scope.seller ).
 		success(function(response){
 			$('#addseller').modal('hide');
 			$scope.sel = response;
 			notificationService.success('Seller Sucessfully registered :' + $scope.sel.id);

 			
  			
 			$http.get(baseUrl + "/list-sellers").success(function (data) {      
					$scope.seller = data;
					iniciar_tabla("#tbSellers");
 			});


 		});
 		
 		
       }	
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removeSeller = function(index){    	
				
			
			$http.put(baseUrl + '/seller/delete/' +$scope.sellers[index].id, $scope.sellers[index]).
			success(function(response){
				    notificationService.success('Seller Sucessfully Removed');
					$http.get(baseUrl + "/list-sellers").success(function (data) {      
					$scope.sellers = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		       
		$scope.editCurrSeller = function(index){
			/*	$http.get(baseUrl + "/findOneSeller?id=" + $scope.sellers[index].idSeller).success(function (data) {      
					$scope.seller = data; 

				});*/
			//alert("Edit Seller");
			$scope.sellered = $scope.sellers[index]; 
		}
		
		$scope.editSeller = function(){   	
	    	
 		$http.put(baseUrl + '/seller/edit/' + $scope.sellered.id, $scope.sellered ).
 		success(function(response){
 			$('#editseller').modal('hide');
 			$http.get(baseUrl + "/list-sellers").success(function (data) {    
 				    notificationService.success('Seller Sucessfully Update');
					$scope.sellers = data;
					$scope.sellered = {};
 			});
 			$scope.txtDesc=null;

 		});
 		 
 		

 		} 
		
		
		
}]);
	
//----------------------------------------------------------------------------------------------------------
//----------------------------------- USER AUTHENTICATION -------------------------------------------------/
//----------------------------------------------------------------------------------------------------------

app.controller('navigation',['$rootScope', '$scope', '$http', '$location','$window',
		  function($rootScope, $scope, $http, $location,$window) {
		  
	      $http.defaults.headers.post["Content-Type"] = "application/json";
	      var baseUrl = ".";
	      

	      

	
	      var authenticate = function(callback) {
		    $http.get('welcome').success(function(data) {
		      if (data.userName) {
		        $rootScope.authenticated = true;
		        console.log('2 - Authenticate User ', data  ); 
		        $window.location.assign("./sealhome");
		      } else {
		        $rootScope.authenticated = false;
		      }
		      callback && callback();
		    }).error(function() {
		      $rootScope.authenticated = false;
		      callback && callback();
		    });
		  }
		  authenticate();
		  $scope.credentials = {};
		  
		  
		  $scope.login = function() {
			//alert("Email : " + $scope.credentials.username + " -- PAssword : " + $scope.credentials.password);  
		    $http.post(baseUrl + '/loginini', $.param($scope.credentials),  {
		        headers : {
		            "content-type" : "application/x-www-form-urlencoded"
		          }
		        }).success(function(data) {
		        	//alert("I Passed Auth");
		    //alert("error la cagste pajuo");	
		  		/*  $http.get("https://api.ipify.org/?format=json").then(function (response) 
							{
		  			            console.log('DATA REPSONSE : ' , response);
								$scope.ip = response.data.ip;
				  });  */
				  
				  
		        	
		        	
		      authenticate(function() {
		        if ($rootScope.authenticated) {
		        	
		   
		          	//$location.path("/ecohome").replace();
		            $scope.error = false;
	            	$window.location.assign("./sealhome");  
  
		        } else {
		          //$window.location.href = '/principal.html';
		        	$window.location.assign('/');
		          //$location.path("/ecohome").replace();
		          $scope.error = true;
		        }
		      });
		    }).error(function(data) {
		    
		      $location.path("/login");
		      $scope.error = true;
		      $rootScope.authenticated = false;
		      //alert("Error");
		    })
		  };
		  

		  $scope.logout = function() {
		    $http.post('logout', {}).success(function() {
		      $rootScope.authenticated = false;
		      $location.path("/");
		      $window.location.assign('/');
		    }).error(function(data) {
		      $rootScope.authenticated = false;
		    });
		  }
		  
		  $scope.goProfile = function(){ 
			  alert("GO TO PROFILE"); 
			  $window.location.assign(baseUrl + '/sealhome#/profile'); 
		  }
		  
		  
		}]);

//-------------------------------------------------------------------------------------------------------------/
//--------------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------------
//------------------------------------------ HOME CONTROLLER ---------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

app.controller('homeController',['$rootScope', '$scope', '$http', '$location','$window','$filter',
                   		  function($rootScope, $scope, $http, $location,$window,$filter) {
	
    $http.defaults.headers.post["Content-Type"] = "application/json"; 
    var baseUrl = ".";
    $rootScope.yearAux = {id : '' , descYear : ''};
    var clickYear = 0
    
    
    
    $http.get(baseUrl + '/welcome').success(function(data) {
      
      $scope.greeting = data;
      $rootScope.userName = data.userName;
      $rootScope.idSesion = data.idSesion;
      $rootScope.role = $scope.greeting.role;
      $rootScope.user = data.users;
      $rootScope.itemActual = null;
       

      
      $rootScope.partsAutocomplete = $scope.greeting.users.partsAutocomplete; 
      console.log("GREETING" , $scope.greeting);
      loadYears($http,$scope,baseUrl);
      
      if ($rootScope.role == '1'){
          if ($rootScope.clickYear == '1')
          { 
        	  console.log("Pase") 
          }
        	  	
          else {
        	      $rootScope.yearRoot = {id : '3' , 
    	          descYear : 'All'} 
          }  
      }
      
      if ($rootScope.role == '2'){
    	  findActualSeller($http,$scope,baseUrl,$rootScope.user.username);  
    	  $rootScope.sessionSeller = $scope.actSel;	  
    	  //console.log('SELLER ACTUAL : ' +  $rootScope.sessionSeller.sureName);
      } 
      
      if ($rootScope.role == '3'){
    	 
          if ($rootScope.clickYear == '1')
          { 
        	  console.log("Pase") 
          }
        	  	
          else {
        	      $rootScope.yearEst = {id : '0' , 
    	          descYear : '2022'} 
          }    	  

    	  
    	  findActualEstimator($http,$scope,baseUrl,$rootScope.user.username);  
    	  $rootScope.sessionEstimator = $scope.actEst;	 
    	  findTotalNotificationsEstimator($http,$scope,$rootScope,baseUrl,$rootScope.user.username);
    	  findTop4NotificationsEstimator($http,$scope,baseUrl,$rootScope.user.username);
    	  //console.log('SELLER ACTUAL : ' +  $rootScope.sessionSeller.sureName);
      }     
      
	  $scope.clickYearDash = function(){ 
		  if ($rootScope.role == '3'){
			  $rootScope.yearEst = $scope.yearEst;
			  $rootScope.yearAux = $scope.yearEst;
			  $rootScope.clickYear = '1'
			  dashboardEstimator($http,$rootScope,$scope,baseUrl,$filter,$window);		  
		  }
		  
		  if ($rootScope.role == '1'){
			  $rootScope.yearRoot = $scope.yearRoot;
			  $rootScope.clickYear = '1'
		      dashboard($http,$rootScope,$scope,baseUrl,$filter,$window)	  
		  }		  
		  
		  
	
	  }
      
      
      //alert($scope.greeting.role);
      //alert("Home Controller User : " + $rootScope.userName);
      
      
      //$scope.dirimageUrl = 'images/' + $scope.greeting.imageUrl; 
      $rootScope.dirimageUrl = 'images/' + $scope.greeting.imageUrl; 
      
      $rootScope.imgUrl = $scope.dirimageUrl;
      //$scope.dirimageUrl = $scope.greeting.imageUrl;
      
 	 if (data.userName){
		 console.log('Logged')
	 }else
		 $scope.logout();

    });	
    
    //alert("Image :"  + $scope.greeting.imageUrl);
    
    //dashboard1($http,$scope,baseUrl,$filter);
    userInterface($http,$scope,$rootScope,baseUrl,$filter,$rootScope.role, $rootScope.userName,$window);
    deletetempfiles($http,$scope,baseUrl,$rootScope.idSesion); 
    

    //dashboard1($http,$scope,baseUrl,$filter);
    
     $scope.viewSellers = function() {
    	
    	 $window.location.assign('#/q-sellerActivity');
     }
     
	$scope.viewSalesman = function(index){
 				  		   
	     $rootScope.actSeller =  $scope.topSellers[index].seller;
	  		  // alert("Profile : " +  $rootScope.actSeller.sureName);	
	     $window.location.assign(baseUrl + '/sealhome#/sellerprofile');
	}
     

    
    
	 $scope.logout = function() {
		    //alert("Cerrando Session");
		    $http.post('logout', {}).success(function() {
		      $rootScope.authenticated = false;
		      $location.path("./");
		      $window.location.assign('./');
		    }).error(function(data) {
		      $rootScope.authenticated = false;
		    });
	  }
	 

	
}]);


//------------------------------------------------------------------------------------------------------------
//---------------------------------- LEADS CONTROLLER --------------------------------------------------------
//------------------------------------------------------------------------------------------------------------

app.controller('leadsController',['$rootScope', '$scope', '$http', '$location','$window','$uibModal','notificationService','$filter',
                          		  function($rootScope, $scope, $http, $location,$window,$uibModal,notificationService,$filter) {
       	
           $http.defaults.headers.post["Content-Type"] = "application/json";
           var baseUrl = ".";
           //alert($rootScope.userName);
           $scope.lead = {
        		   statCheck : '0'
           };
           $scope.statCheck = "0";
           
           $scope.showFields = "0";
           $scope.statFilter = "0";
           $scope.filSeller = {
        		   idsel : null
           };
           
           
           
           listSellers($http,$scope,baseUrl);
           listOrigin($http,$scope,baseUrl);
           listReferral($http,$scope,baseUrl);
           
           $scope.LeadActivity = {};
           cargarValores($http,$scope,baseUrl);
           cboStatus = null;
           actLeads($http,$scope,baseUrl,$rootScope.role,$rootScope.userName);
           //$scope.lead.sellers = [];
           
	       	$http.get(baseUrl + '/findOneSellerUser?username='+$rootScope.userName).
			success(function(response){
				
				$scope.actSel = response;
	
	 		});   
	       	
	       	if ($scope.leads != []){
	       		
	       	}
	       	
	       	
	       	

           
     	  angular.element(document).ready(function() {
              $("#select2_single").select2({
                placeholder: "Select a Origin",
                allowClear: false
              });
              $("#select2_group").select2({});
              $("#select2_multiple").select2({
                maximumSelectionLength: 2,
                placeholder: "Select Seller(s)",
                allowClear: false
              });
            });
     	  
     	 angular.element(document).ready(function() {
              $('#birthday').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_4"
              }, function(start, end, label) {
                console.log(start.toISOString(), end.toISOString(), label);
              });
            });
     	 
      	$scope.qLeadsFilter = function(){
     		//alert("Filtro " + $scope.filSeller.idsel + "  " + $scope.statFilter);
     		
     		if ($scope.filSeller.idsel != null){
     			$scope.leads = null;
     			limpiar_tabla('#tbLead');
     			actLeadsFilter($http,$scope,baseUrl,$rootScope.userName,$scope.filSeller.idsel);
     		}	
     	}
      	
      	$scope.validateCheck = function(){
      		if ($scope.statFilter == "0"){
      			actLeads($http,$scope,baseUrl,$rootScope.role,$rootScope.userName);
      		}
      	}
        
        
     	 $scope.options = {
     		    types: ['(cities)'],
     		    componentRestrictions: { country: 'FR' }
     		  };
     		  

     	$scope.init = function(){
     		  //iniciar_tabla('#tbLead');
     		  //$scope.lead.leadDate = '';
	   		  $scope.addressDb = {
	        		    name: '',
	        		    place: '',
	           		    streetNumber: '',
	           		    street: '',
	           		    city: '',
	      		        state: '',
	      		        postCode: '',
	      		        address2: '',
	        		    components: {
	        		      placeId: '',
	        		      streetNumber: '', 
	        		      street: '',
	        		      city: '',
	        		      state: '',
	        		      countryCode: '',
	        		      country: '',
	        		      postCode: '',
	        		      district: '',
	        		      suite: '',
	        		      location: {
	        		        lat: '',
	        		        long: ''
	        		      }
	        		    }
	        		  };
     	}	 
     	
     		  
     	$scope.addLead = function(){
     	
        //$scope.lead.sellers	= [];	
     	//alert($scope.lead.sellers);	
     		
     	//alert("Seller select " + $scope.lead.sellers);	
     	
	     //alert("Role : " + $rootScope.role);
	     if ($rootScope.role == "SELLER"){
	        	$scope.lead.sellers	= [];
	        	$scope.lead.sellers.push($scope.actSel.idSeller);
	        	
	     }
         
	     
	     
		 $scope.address = {
       		    name: '',
       		    place: '',
       		    streetNumber: '',
       		    street: '',
       		    city: '',
  		        state: '',
  		        postCode: '',
  		        address2: '',
       		    components: {
       		      placeId: '',
       		      streetNumber: '', 
       		      street: '',
       		      city: '',
       		      state: '',
       		      countryCode: '',
       		      country: '',
       		      postCode: '',
       		      district: '',
       		      suite: '',
       		      location: {
       		        lat: '',
       		        long: ''
       		      }
       		    }
       		  };   
   		       
		        
		 
  		   if ($scope.lead.sellers == null){
  			 notificationService.success('You must to add at least one Seller');
  		   }else{
	     		
  		   var inicio = $filter('date')(new Date($scope.lead.leadDate),'yyyy-MM-dd');
  		   var appo = $filter('date')(new Date($scope.lead.appoimentDate),'yyyy-MM-dd');
  			  
  		    //alert("Hora YA : " + $scope.lead.appoimentTime);
	        $http.post(baseUrl + "/address", $scope.addressDb).then(function (result) {
	     				  return $http.post(baseUrl +"/lead?idReferral=" + $scope.lead.referral + "&idOrigin= "+ $scope.lead.origin + "&lDate= "+ inicio + "&contact="+ $scope.lead.contactName + "&phone="+ $scope.lead.phoneNumber+ "&placeId="+ $scope.addressDb.placeId + "&observations=" + $scope.lead.observations + "&userName=" + $rootScope.userName + "&aDate= "+ appo + "&aHour=" + $scope.lead.appoimentTime ,$scope.lead.sellers)
	     	}).then(function(result){
	     		      $('#addLead').modal('hide');
	     		      notificationService.success('Lead Sucessfully Added');
	     		      //alert("LEAD : " + result.data.idLead);
	     		      $scope.le = result.data;
	     	   		  $scope.addressDb = {
	     	        		    name: '',
	     	        		    place: '',
	     	          		    streetNumber: '',
	     	          		    street: '',
	     	          		    city: '',
	     	     		        state: '',
	     	     		        postCode: '',
	     	     		        address2: '',
	     	        		    components: {
	     	        		      placeId: '',
	     	        		      streetNumber: '', 
	     	        		      street: '',
	     	        		      city: '',
	     	        		      state: '',
	     	        		      countryCode: '',
	     	        		      country: '',
	     	        		      postCode: '',
	     	        		      district: '',
	     	        		      suite: '',
	     	        		      location: {
	     	        		        lat: '',
	     	        		        long: ''
	     	        		      }
	     	        		    }
	     	        		  };
	     	         $scope.lead.observations = null;
	     	         $scope.lead.origin = null;
	     	         $scope.lead.referral = null;
	     	         $scope.lead.contactName = null;
	     	         $scope.lead.phoneNumber = null;
	     	         $scope.lead.lDate = null;
	     		     //listLeads($http,$scope,baseUrl);
	     	         $scope.lead.sellers	= [];
	     	         if ($scope.statFilter == "0")
	     	        	 actLeads($http,$scope,baseUrl,$rootScope.role,$rootScope.userName);
	     	         else{
	     	     		if ($scope.filSeller.idsel != null){
	     	     			$scope.leads = null;
	     	     			limpiar_tabla('#tbLead');
	     	     			actLeadsFilter($http,$scope,baseUrl,$rootScope.userName,$scope.filSeller.idsel);
	     	     		}
	     	         }
	     	         
	     		     listSellers($http,$scope,baseUrl);
	     		     return $http.post(baseUrl +"/lead/notify",$scope.le)
	     	}).then(function(result){
	     		     console.log("Email Enviado");
	     		     
	     	}, function errorCallback(response){
	     		      $scope.res = response.data;
	     		      //alert('Error : ' + $scope.res);
		     		     notificationService.notify({
		     		    	title: 'Address',
		     		    	title_escape: false,
		     		    	text: $scope.res.message,
		     		    	text_escape: false,
		     		    	styling: "bootstrap3",
		     		    	type: "notice",
		     		    	icon: true
		     		    });
	     		    //  notificationService.error('Error2 :' + $scope.res.message);
	     		    //  notificationService.error('An error was ocurred');
	        })
	        
  		   } // Fin del Validar si hay venededors
           
     	}   
     	
     	
     	

     	

     	$scope.editCurrLead = function(index){
     		//alert("Indice" + index);
			$http.get(baseUrl + "/findOneLead?id=" + $scope.leads[index].idLead).success(function (data) {      
				$scope.leadEd = data; 
				$scope.dateConv = new Date($scope.leadEd.leadDate);
				$scope.leadEd.leadDate = $scope.dateConv;
				$scope.dateConv = new Date($scope.leadEd.appoimentDate);
				$scope.leadEd.appoimentDate = $scope.dateConv;
				
				
		       	//alert("Role : " + $rootScope.role);
		        if ($rootScope.role == "SELLER"){
		        	$scope.leadEd.sellers	= [];
		        	$scope.leadEd.sellers.push($scope.actSel.idSeller);
		        	
		        }	

			});
			
			$http.get(baseUrl + "/findOneAddress?id=" + $scope.leads[index].address.idAddress).success(function (data) {      
				$scope.addressDb = data; 

			});
	    }
	
	    $scope.editLead = function(){   	
	    	
	  		   if ($scope.leadEd.sellers == []){
	    			 notificationService.success('You must to add at least one Seller');
	    		   }else{
	    			   
   				var inicioEd = $filter('date')(new Date($scope.leadEd.leadDate),'yyyy-MM-dd');
   				var appo = $filter('date')(new Date($scope.leadEd.appoimentDate),'yyyy-MM-dd');
 
				$http.put(baseUrl + "/lead/edit?id="+$scope.leadEd.idLead +"&idAddress="+ $scope.leadEd.address.idAddress +  "&idReferral=" + $scope.leadEd.referral.idReferral + "&idOrigin= "+ $scope.leadEd.origin.idOrigin + "&lDate= "+ inicioEd + "&contact="+ $scope.leadEd.contactName + "&phone="+ $scope.leadEd.phoneNumber+ "&placeId="+ $scope.addressDb.placeId + "&observations=" + $scope.leadEd.observations + "&aDate= "+ appo + "&aHour=" + $scope.leadEd.appoimentTime,$scope.leadEd.sellers ).
				success(function(response){
					$('#editLead').modal('hide');
					notificationService.success('Lead Sucessfully Update');
					$scope.resulLead = response;
					//alert("Rsul Lead : " + $scope.resulLead);
					
					$http.post(baseUrl +"/lead/notify",$scope.resulLead).success(function(response){      
						console.log("Modify email has been sent");

					});
					
					//listSellers($http,$scope,baseUrl);
					//alert(" Id Seller 1 : " + $scope.filSeller.idsel);
					
					
	     	         if ($scope.statFilter == "0")
	     	        	 actLeads($http,$scope,baseUrl,$rootScope.role,$rootScope.userName);
	     	         else
	     	        {
		     	     		if ($scope.filSeller.idsel != null){
		     	     			$scope.leads = null;
		     	     			limpiar_tabla('#tbLead');
		     	     			actLeadsFilter($http,$scope,baseUrl,$rootScope.userName,$scope.filSeller.idsel);
		     	     			//alert(" Id Seller 2 : " + $scope.filSeller.idsel);
		     	     		}
		     	         }
	     	        
	     			 $scope.address = {
	     	       		    name: '',
	     	       		    place: '',
	     	       		    components: {
	     	       		      placeId: '',
	     	       		      streetNumber: '', 
	     	       		      street: '',
	     	       		      city: '',
	     	       		      state: '',
	     	       		      countryCode: '',
	     	       		      country: '',
	     	       		      postCode: '',
	     	       		      district: '',
	     	       		      suite: '',
	     	       		      location: {
	     	       		        lat: '',
	     	       		        long: ''
	     	       		      }
	     	       		    }
	     	       		  };
	     			//alert(" Id Seller 3 : " + $scope.filSeller.idsel);
					
		
				});
				
	         } //Fin de else si selecciono vendedores
		
		}
	    
     	$scope.editCurrLeadStatus = function(index){
			$http.get(baseUrl + "/findOneLead?id=" + $scope.leads[index].idLead).success(function (data) {      
				$scope.leadEdSt = data; 

			});
	    }
     	
	    $scope.editLeadStatus = function(){   	
	    	
	    	//alert("Stat : " + $scope.leadEdSt.statLead);
			$http.put(baseUrl + "/lead/editst?id="+$scope.leadEdSt.idLead + "&stat="+JSON.parse($scope.leadEdSt.statLead) + "&user="+$rootScope.userName).
			success(function(response){
				$('#editLeadStatus').modal('hide');
				notificationService.success('Lead Status Sucessfully Update');
    	         if ($scope.statFilter == "0")
     	        	 actLeads($http,$scope,baseUrl,$rootScope.role,$rootScope.userName);
     	         else
     	             {
	     	     		if ($scope.filSeller.idsel != null){
	     	     			$scope.leads = null;
	     	     			limpiar_tabla('#tbLead');
	     	     			actLeadsFilter($http,$scope,baseUrl,$rootScope.userName,$scope.filSeller.idsel);
	     	     		}
	     	         }
				//listLeads($http,$scope,baseUrl);
				//listar_leadsActivitys($http,$scope,baseUrl, $scope.leadEdSt);
				
				iniciar_tabla('#tbLead');

			});
			
		}     
	    
     	$scope.changeCheck = function(index){
     		
     		//alert("VAL : " + $scope.statCheck);
			$http.put(baseUrl + "/changeCheck?id=" + $scope.leads[index].idLead+"&stat="+$scope.lead.statCheck).success(function (data) {      
				console.log("Resul : "); 
   	            if ($scope.statFilter == "0")
 	        	 actLeads($http,$scope,baseUrl,$rootScope.role,$rootScope.userName);
 	            else
 	             {
     	     		if ($scope.filSeller.idsel != null){
     	     			$scope.leads = null;
     	     			limpiar_tabla('#tbLead');
     	     			actLeadsFilter($http,$scope,baseUrl,$rootScope.userName,$scope.filSeller.idsel);
     	     		}
     	         }

			});
	    }

	    
		$scope.cancel = function () {		
			$scope.modalInstance.dismiss('cancel');
		};
		
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
		    $scope.modalInstance = $uibModal.open({
		    	ariaLabelledBy: 'modal-title',
		        ariaDescribedBy: 'modal-body',
		    	templateUrl: 'borrar.html',
		        scope: $scope,
		        size: 'sm'	        
		    });
		}	
		
		
		$scope.removeLead = function(index){    	
			
			
			//alert("Index Actual : " + index);
			$http.put(baseUrl + "/lead/delete?id="+$scope.leads[index].idLead + "&stat=1&user="+$rootScope.userName).
			success(function(response){
				    
				//alert("Valor :" + response);
				 if (response == "1"){    
				    notificationService.success('Lead Sucessfully Removed');
	     	         if ($scope.statFilter == "0"){
	     	        	 actLeads($http,$scope,baseUrl,$rootScope.role,$rootScope.userName);
	     	         }
	     	         else
	     	             {
		     	     		if ($scope.filSeller.idsel != null){
		     	     			$scope.leads = null;
		     	     			limpiar_tabla('#tbLead');
		     	     			actLeadsFilter($http,$scope,baseUrl,$rootScope.userName,$scope.filSeller.idsel);
		     	     		}
		     	         }
				 }else{
					    notificationService.error('Error : Just the Owner can Delete this Lead'); 
				      }
				    /*$http.get(baseUrl + "/list-leads").success(function (data) {      
								$scope.sellers = data; 

				    });*/

				});
			$scope.modalInstance.close();
			
		}	
		
		
		       
		
		
		
		$scope.leadWizard = function(index){
			
		/*	$rootScope.wizIdAddress = $scope.leadsSellerPr[index].idAddress;
			$rootScope.wizAddressName = $scope.leadsSellerPr[index].addressName;
			$rootScope.wizcontactName = $scope.leadsSellerPr[index].contactName;
			$rootScope.address =  $scope.leadsSellerPr[index].address; */
			
			$rootScope.wizIdAddress = $scope.leads[index].idAddress;
			$rootScope.wizAddressName = $scope.leads[index].addressName;
			$rootScope.wizcontactName = $scope.leads[index].contactName;
			$rootScope.address =  $scope.leads[index].address;
			
			//alert("BOTON : " + $rootScope.address);
			
		}
		
		
		
		$scope.leadActivity = function(index){
			
			$scope.indiceAct = index;
			listar_leadsActivitys($http,$scope,baseUrl, $scope.leads[index].idLead);
			$scope.leadAct = $scope.leads[index].idLead;
			$scope.showFields = "0";
			$scope.actL = $scope.leads[index];
			$scope.nameCustomer = $scope.actL.contactName;
			$scope.LeadActivity.descActivity = " ";
			
		}
		
		$scope.buttLeadAct = function(index){
			 $scope.showFields = "1";
		}
		
		$scope.saveLeadAct = function(){
	    	
			//alert("Valor : " + $scope.LeadActivity.descActivity);
			if ($scope.LeadActivity.descActivity != " "){
			
				$scope.LeadActivity.username = $rootScope.userName;
				$http.post(baseUrl + '/lead/newActivity?idLead='+$scope.leadAct, $scope.LeadActivity ).
				success(function(response){
					notificationService.success('Lead Activity Sucessfull Added');	
					listar_leadsActivitys($http,$scope,baseUrl, $scope.leadAct);
					$scope.showFields = "0";
					$scope.LeadActivity.descActivity = " "; 
		 		});	
			
			}
			
		}
		
 		
		
       	 
}]);



//--------------------------------------------------------------------------------------------------------------
//------------------------------------------ QUERYS CONTROLLER ---------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

app.controller('querysController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService',
                 		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService) {
	
  $http.defaults.headers.post["Content-Type"] = "application/json";
  var baseUrl = ".";
  
  cargarValores($http,$scope,baseUrl);
  listSellers($http,$scope,baseUrl);
  //listAllitems($http,$scope,baseUrl); 
  listCustomers($http,$scope,baseUrl);
  $scope.proyfilter = {}; 
  $scope.itemActivity = {}; 
  //tablaResponsive();
  var year = $filter('date')(new Date(),'yyyy');
  query_quotesestimatoryear($http,$scope,baseUrl,year);
  query_quotesselleryear($http,$scope,baseUrl,year);
  console.log('ACTUAL USER :',$rootScope.user );
  
  $scope.swPrint = '1';
  
  if ($rootScope.role == '1'){
	  //listAllitems($http,$scope,baseUrl);
  }
  
  if ($rootScope.role == '2'){
	  listPendingItemsSeller($http,$scope,baseUrl, $rootScope.sessionSeller.id); 
  }	  
  
  if ($rootScope.role == '3'){
	  //listPendingItemsEstimator($http,$scope,baseUrl);
	  var yearQuery = $rootScope.yearEst.descYear;
	  if ($rootScope.yearEst.descYear == '')
		  yearQuery = 'All'
			  
	  //alert('Qyerys Estimator : ' ) 	
	  
	  if ($location.path() == '/find-item-estimator')
		  listAllitems($http,$scope,baseUrl);
	  else {
		  listpendingquotesEst($http,$scope,baseUrl,$rootScope.user.username,$rootScope.yearEst.descYear); 
		  listViewQuotesEstimatorOnHold($http,$scope,baseUrl,yearQuery,$rootScope.user.username,'7')
		  listViewQuotesEstimatorOrdered($http,$scope,baseUrl,yearQuery,$rootScope.user.username,'6')		  
	  }
	   
  }	
  
  
  $scope.readyRe1 = true;
  $scope.readyRe4 = true;
  $scope.txtPromise = "Procesing...";
  $scope.date4 = { date: {startDate: null, endDate: null} };  
  $scope.countQ4 = 0;
  $scope.showFields = 0;
  $scope.newNote = ""; 
  $scope.noteFollow = "";
  $scope.viewSumm = '0';
  //$scope.selectedCus = {}; :
  
  
  $scope.clickViewSumm = function() {
	  
	  iniciar_tabla("#tbSummarized"); 
	  //$('#tbSummarized').DataTable({responsive: true}); 
	  $scope.viewSumm = '1';
  }
  
  $scope.resetSumm = function(){
	  
	  $scope.viewSumm = '0';
	  $scope.q_partId = "";
	  $scope.partExp = {};
	  
  }
  
  $scope.findSummarized = function(){
	  
	  listSummarizedExp($http,$scope,baseUrl, $scope.q_partId); 
	  
  }
  
  
   $scope.findLQuote = function(index) {
      
	   console.log("Quote :  SIZE SELLER  " +  $scope.itemsSeller[index].quote.id );   
       // alert("Quote " + $scope.itemsSeller[index].quote); 
		$rootScope.quoActual = $scope.itemsSeller[index].quote;  
		$window.location.assign(baseUrl + '/sealhome#/quote-detail');
 
   }
  
  
   $scope.clickFollowUp = function(index) {   // This for Report qQuotesCustomers
	   
	  // console.log("Quote : " + $scope.qQuotes4[index].id  +  " SIZE SELLER  " + $scope.itemsSeller.length ); 
	   listquotenotes($http,$scope,baseUrl,$scope.qQuotes4[index].id);
	   $scope.idQuoteNote = $scope.qQuotes4[index].id;
	   $scope.showFields = 0;
	   $scope.noteFollow = ""; 
	   
   }
   
   $scope.clickFollowUpAll = function(index) {
       var idQ =  $scope.itemsSeller[index].quote.id;
	   listquotenotes($http,$scope,baseUrl,idQ);
	   $scope.idQuoteNote = idQ;
	   
   }
   
   
   $scope.buttNewFollow = function(){ 
	   
	   $scope.showFields = 1;
	   $scope.noteFollow = "";   
   }
  
   $scope.addQNote = function(){ 
	   console.log("NOTE ACTUAL " +  $scope.noteFollow ); 
		//alert("Valor : " + $scope.LeadActivity.descActivity);
		if ($scope.noteFollow != " "){ 
		    var note = $scope.noteFollow;
			
			$http.post(baseUrl + '/quote/note/add?quoteId='+$scope.idQuoteNote+'&message='+$scope.noteFollow+'&typeNote=F', $rootScope.user ).
			success(function(response){
				notificationService.success('Follow up Sucessfull Added');	
				//listquotenotesbytype($http,$scope,$rootScope, baseUrl,$scope.qId,"F"); :
				listquotenotes($http,$scope,baseUrl,$scope.idQuoteNote); 
				//alert("Follow up added");
				//$rootScope.notesq = $scope.notesquote; 
				//console.log('ADDING NOW SIZE NEW IS : ' + $rootScope.notesq.length);
				//console.log('LOCAL SIZE NEW IS : ' + $rootScope.notesq.length);
				                                            
				$scope.showFields = 0; 
				return 	$http.post(baseUrl + '/quote/notifyMessage?message='+note+'&idQuote='+$scope.idQuoteNote+'&username='+ $rootScope.user.username)
	     	}).then(function(result){
    		     console.log("Email Enviado"); 
    		     $scope.noteFollow = "";  
    	    }, function errorCallback(response){
    		      $scope.res = response.data;
    		      //alert('Error : ' + $scope.res);
	     		     notificationService.notify({
	     		    	title: 'Address',
	     		    	title_escape: false,
	     		    	text: $scope.res.message,
	     		    	text_escape: false,
	     		    	styling: "bootstrap3",
	     		    	type: "notice",
	     		    	icon: true
	     		    });
    		    //  notificationService.error('Error2 :' + $scope.res.message);
    		    //  notificationService.error('An error was ocurred');
            });		
			$scope.noteFollow = "";  
		
		}
		
	}
    
 	
 	
    $scope.newValue = function(value) {
    	$scope.proyfilter.typeProject = value;
    	console.log($scope.proyfilter.typeProject);
     }  

  
	$scope.qLeads = function() {
		

		
		var inicio = $filter('date')(new Date($scope.fecIni),'yyyy/MM/dd');
		if ($scope.selFiltro == '1'){
			var fin = inicio;
		}
		
		if ($scope.selFiltro == '2'){
			var fin = $filter('date')(new Date($scope.fecFin),'yyyy/MM/dd');
		}

		$http.get(baseUrl + '/lead/querybyLeadsperSeller?inicio='+inicio+'&fin='+fin)
		.success(function(result){
			{
    		   $scope.leadsSel = result;
    		   //tablaResponsive();
    		   iniciar_elementos();
			   //iniciar_tabla('#tbQuery1');		
	        }
		});
	}	
	
	$scope.clearresultQ4 = function() {
		$scope.qQuotes4 = 0;
		$scope.cus = {};
		$scope.fecIni4 = null;
		$scope.fecFin4 = null;
	}
	
	
	$scope.findresultQ4 = function() {
		 
		
		console.log("Ini : " +  $scope.fecIni4);
		console.log("Fin : " +  $scope.fecFin4);
		
		console.log("Customer : " +  $scope.cus.id);
		                                   
		
		if (($scope.fecIni4 != null) && ($scope.fecFin4 != null) && ($scope.cus != null)){ 
			var inicio = $filter('date')(new Date($scope.fecIni4),'yyyy/MM/dd');
			var fin = $filter('date')(new Date($scope.fecFin4),'yyyy/MM/dd');
	        console.log("Valores Buenos");
	        //alert('/qQuotesCustomers?inicio='+inicio+'&fin='+fin+'&idCustomer='+$scope.cus.id);
			$http.get(baseUrl + '/qQuotesCustomers?inicio='+inicio+'&fin='+fin+'&idCustomer='+$scope.cus.id)
			.success(function(result){
				
	    		   $scope.qQuotes4 = result;
	    		   $scope.countQ4 = $scope.qQuotes4.length;
	    		   //tablaResponsive();
	    		   //iniciar_elementos();
				   //iniciar_tabla('#tbQueryDet');		
		        
			}).then(function (response){
				iniciar_tabla('#tbQuotesQuery4');
			});		        
	        
			
		}else
			notificationService.error('You must select all filter fields');
		
	}
	
	$scope.printresultQ4 = function() {
		$scope.readyRe4 = false;
		
		var inicio = $filter('date')(new Date($scope.fecIni4),'yyyy/MM/dd');
		var fin = $filter('date')(new Date($scope.fecFin4),'yyyy/MM/dd');
		$http.get(baseUrl + '/r_quotesCustomers?initD='+inicio+'&endD='+fin+'&idCustomer='+$scope.cus.id,{responseType:'arraybuffer'}).
		//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
		success(function(response){

			$scope.file = response;
		    var file = new Blob([response], {type: 'application/pdf'});
		    var fileURL = URL.createObjectURL(file);
			$scope.readyRe4 = true;
			$scope.txtPromise = "Print"				    
		    //$window.location = fileURL;
		    window.open(fileURL, '_blank');
		    //$scope.readyRe = true;
		    //alert("En Reports http wind Buffer");
        })
        .error(function(err) {
        	console.log('Error printing');		     
	     });		
	}
	
	
	$scope.qLeadsSeller = function() {
		
		//alert($rootScope.role);
		if ($rootScope.role == "SELLER"){
			  //alert("RootScope");	
			  $scope.selFilSeller = $scope.actSel.idSeller;
			  //alert($scope.selFilSeller);
		}
		
		var inicio = $filter('date')(new Date($scope.fecIni),'yyyy/MM/dd');
		if ($scope.selFiltro == '1'){
			var fin = inicio;
		}
		
		if ($scope.selFiltro == '2'){
			var fin = $filter('date')(new Date($scope.fecFin),'yyyy/MM/dd');
		}		
		$http.get(baseUrl + '/lead/queryLeadsSellerStatus?inicio='+inicio+'&fin='+fin+'&idSeller='+$scope.selFilSeller)
		.success(function(result){
			{
    		   $scope.leadsSelDet = result;
    		   //tablaResponsive();
    		   iniciar_elementos();
			   //iniciar_tabla('#tbQueryDet');		
	        }
		});		
	}
	
	$scope.btqLeadsSeller = function() {
		
			var inicio = $filter('date')(new Date($scope.fecIni),'yyyy/MM/dd');
			if ($scope.selFiltro == '1'){
				var fin = inicio;
			}
			
			if ($scope.selFiltro == '2'){
				var fin = $filter('date')(new Date($scope.fecFin),'yyyy/MM/dd');
			}	
			
			$http.get(baseUrl + '/rptsellerLeads?idSeller='+$scope.selFilSeller+'&fecIni='+inicio+'&fecFin='+fin,{responseType:'arraybuffer'}).
			//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
			success(function(response){
				
				$scope.file = response;
			    var file = new Blob([response], {type: 'application/pdf'});
			    var fileURL = URL.createObjectURL(file);
			    //$window.location = fileURL;
			    window.open(fileURL, '_blank');
			    //alert("En Reports http wind Buffer");
		
			});
	
	}
	
	$scope.btqLeadsActivityDates = function() {
		
		var inicio = $filter('date')(new Date($scope.fecIni),'yyyy/MM/dd');
		if ($scope.selFiltro == '1'){
			var fin = inicio;
		}
		
		if ($scope.selFiltro == '2'){
			var fin = $filter('date')(new Date($scope.fecFin),'yyyy/MM/dd');
		}	
		
		$http.get(baseUrl + '/leadsActivityDates?fecIni='+inicio+'&fecFin='+fin,{responseType:'arraybuffer'}).
		//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
		success(function(response){
			
			$scope.file = response;
		    var file = new Blob([response], {type: 'application/pdf'});
		    var fileURL = URL.createObjectURL(file);
		    //$window.location = fileURL;
		    window.open(fileURL, '_blank');
		    //alert("En Reports http wind Buffer");
	
		});

     }

	$scope.btqLeadsOrigin = function() {
		
		$http.get(baseUrl + '/rptleadsOrigin',{responseType:'arraybuffer'}).
		//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
		success(function(response){
			
			$scope.file = response;
		    var file = new Blob([response], {type: 'application/pdf'});
		    var fileURL = URL.createObjectURL(file);
		    //$window.location = fileURL;
		    window.open(fileURL, '_blank');
		    //alert("En Reports http wind Buffer");
	
	});
		
		
	
		
		
		
     }  
	
	
	 $scope.btqprint_q1 = function() { 
				 
			$scope.readyRe1 = false;
			
				$http.get(baseUrl + '/q_quotesestimatoryear?year='+year,{responseType:'arraybuffer'}).
				//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
				success(function(response){

					$scope.file = response;
				    var file = new Blob([response], {type: 'application/pdf'});
				    var fileURL = URL.createObjectURL(file);
					$scope.readyRe1 = true;
					$scope.txtPromise = "Print"				    
				    //$window.location = fileURL;
				    window.open(fileURL, '_blank');
				    //$scope.readyRe = true;
				    //alert("En Reports http wind Buffer");
	            })
	            .error(function(err) {
	            	console.log('Error printing');		     
			     });	
		/* $scope.readyRe = false;
		 dataService
	        .getAll()
	        .then(function(data) {
	        	
	        	$scope.readyRe = true;
	        	
	        })
	        .catch(function(err) {
	            console.log('Error printing')
	        })    */

	 } 
	 
	 
		$scope.viewSeller = function(index){
 			
	  		   
	  		   $rootScope.actSeller =  $scope.qresulSel[index].seller;
	  		  // alert("Profile : " +  $rootScope.actSeller.sureName);	
	 		   $window.location.assign(baseUrl + '/sealhome#/sellerprofile');
	 		   
	   
	 		   
	 		
	      }
	
	 $scope.findFilterProjects = function() {
         
	    	
			$http.post(baseUrl + '/listarproyectsfilter',$scope.proyfilter)
			.success(function(result){
				{
					$scope.projFils = result

	    		    
		        }
			});		    	
	    		    	
	  }	
	 
	$scope.btqTotalProjects = function() {
			
		
			$http.get(baseUrl + '/rptProjects',{responseType:'arraybuffer'}).
			//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
			success(function(response){
				
				$scope.file = response;
			    var file = new Blob([response], {type: 'application/pdf'});
			    var fileURL = URL.createObjectURL(file);
			    //$window.location = fileURL;
			    window.open(fileURL, '_blank');
			    //alert("En Reports http wind Buffer");
		
		});
	}
	
    $scope.findListQuote = function(index) {
        
  	  
        //alert($scope.quotes[index].nroRfq);
		    $rootScope.quoActual = $scope.itemsAll[index].quote;
		    console.log('QUOTE ACTUAL QUERY : ' + $rootScope.quoActual); 
			//$window.location.assign(baseUrl + '/sealhome#/quote-detail'); 
			$window.open(baseUrl + '/sealhome#/quotenote-detail?quoteID='+$scope.itemsAll[index].quote.id, '_blank');	

    }	
    
    
    $scope.findListQuotePendEst = function(index) {
        
    	  
        //alert($scope.quotes[index].nroRfq);
		 /*   $rootScope.quoActual = $scope.quotesPendEst[index]; 
		    console.log('QUOTE ACTUAL QUERY : ', $rootScope.quoActual); 
			$window.location.assign(baseUrl + '/sealhome#/quote-detail'); */
			
			
	  		$http.get(baseUrl + '/quDetail?id='+$scope.quotesPendEst[index].id)
	  		.success(function(result){
	  			{

	       		    $scope.quoDet = result;
	  	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
	       		    $scope.currQuote = $scope.quoDet;
	  	    	    $rootScope.quoActual = $scope.quoDet;
	  	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
	  	    	    //alert($rootScope.quoActual);
	  	    	    
	  				$window.location.assign(baseUrl + '/sealhome#/quote-detail');				
	  	        }
	  		});				

    }	    
    
 
    
	$scope.clickitemDetails = function(index){

		$scope.itemActual = $scope.itemsAll[index];  
		
	} 
	
	
	$scope.clickitemActivity = function(index){
		
		$scope.indiceAct = index;
		listitemact($http,$scope,baseUrl,$scope.itemsAll[index].idItem);
		$scope.showFields = "0";
		$scope.itemActual = $scope.itemsAll[index].idItem;   // Ojo chequear esto
		$scope.currItem = $scope.itemsAll[index];

		
	}	
	
	
	$scope.buttLeadAct = function(index){
		 $scope.showFields = "1";
	}  
	
	
	$scope.saveItemAct = function(){
    	
		//alert("Valor : " + $scope.LeadActivity.descActivity);
		if ($scope.itemActivity.descActivity != " "){
		    
			$scope.itemActivity.user = $rootScope.user;
			$scope.itemActivity.username = $rootScope.userName;
			
			$http.post(baseUrl + '/item/newActivity?idItem='+$scope.itemActual, $scope.itemActivity ).
			success(function(response){
				notificationService.success('Item Activity Sucessfull Added');	
				listitemact($http,$scope,baseUrl,$scope.itemActual);
				$scope.showFields = "0";
				$scope.itemActivity.descActivity = " ";
				
	 		});	
		
		}
		
	}	
	
	
	$scope.printSheetCost = function(index) {
		
		$scope.swPrint = '0'; 
		var filename = '/sheetcostForm';  
		console.log('Item to print : ' , $scope.itemActual.idItem);

		
        $http.get(baseUrl + filename + '?idItem='+$scope.itemActual.idItem,{responseType:'arraybuffer'}).
		//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
		success(function(response){
			
			
			//alert("Preparing to Print..!");
			$scope.file = response;
		    var file = new Blob([response], {type: 'application/pdf'});
		    var fileURL = URL.createObjectURL(file);
		    
		    var subject = "Example";
		    var message = "TEsting";
		    var fileDir = "Test.docx"
		    $scope.swPrint = '1';	
		    //$window.location = fileURL;
		   window.open(fileURL, '_blank');
		    
		//    window.open("mailto:sanchezluis25@gmail.com" + "?subject=" + subject+"&body="+message+"&attachment="+fileDir,"_self");
		    //FileSaver.saveAs(myData, 'printrep.pdf');
		    //alert("En Reports http wind Buffer");
	
		});
        
	}
	
	  $scope.findVListQuote = function(index) {
          var url = ""
	        if ($rootScope.role == '1'){
	        	url = '/quDetail?id='+$scope.viewqAll[index][0]
	        }
          
	        if ($rootScope.role == '2'){
	        	url = '/quDetail?id='+$scope.viewqAllSel[index][0]
	        }
	        
	        if ($rootScope.role == '3'){
	        	url = '/quDetail?id='+$scope.viewqAllEst[index][0] 
	        }

	  		$http.get(baseUrl + url)
	  		.success(function(result){
	  			{

	       		    $scope.quoDet = result;
	  	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
	       		    $scope.currQuote = $scope.quoDet;
	  	    	    $rootScope.quoActual = $scope.quoDet;
	  	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
	  	    	    //alert($rootScope.quoActual);
	  	    	    
	  				//$window.location.assign(baseUrl + '/sealhome#/quote-detail');	
	  				$window.open(baseUrl + '/sealhome#/quotenote-detail?quoteID='+$scope.quoDet.id, '_blank');
	  	        }
	  		});				

	  }	
	  
	  $scope.showquoteItems = function(index) {
		  
		  if ($rootScope.role == '1')
			  listquoteitems($http,$scope,baseUrl,$scope.viewqAll[index][0]);
		  if ($rootScope.role == '2')
			  listquoteitems($http,$scope,baseUrl,$scope.viewqAllSel[index][0]);
		  if ($rootScope.role == '3')
			  listquoteitems($http,$scope,baseUrl,$scope.viewqAllEst[index][0]);
		  
	  }
	  
	  
	  $scope.printsheetCost = function(index) {
		  console.log("PRINT SHEET COST  "  , $scope.itemsquote );
		  $scope.selectedIndex = index;
		  printSheetCost($http,$rootScope, $scope,baseUrl,$scope.itemsquote[index])
	  }
	  
	  $scope.findItemByKeyword = function() {
		  
		  listAllitemsByKeyword($http,$scope,baseUrl,$scope.k_partId)

	  }
	  
	  $scope.resetItems = function(){
		  
		  $scope.itemsAll = [];
		  $scope.showTable = null;
		  
	  }
	
	
	
	
}]);


//--------------------------------------------------------------------------------------------------------------
//------------------------------------------ PROYECT CONTROLLER ---------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

app.controller('proyectsController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$uibModal','Upload', '$timeout',
               		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,$uibModal,Upload, $timeout) {
	
$http.defaults.headers.post["Content-Type"] = "application/json";
var baseUrl = "."; 
	
    listar_activitys($http,$scope,baseUrl);
	listContrator($http,$scope,baseUrl);
	listFinancial($http,$scope,baseUrl);
	listRoof($http,$scope,baseUrl);
	listSellers($http,$scope,baseUrl);

	
	
	
	
	
	//alert("ROLE PROYECT CONTROLLER : " + $rootScope.role);
	
	if ($rootScope.role == 'SELLER'){
		$scope.proyects = [];
		listar_proyectosuser($http,$scope,baseUrl,$rootScope.userName);
	}else{
		listar_proyectos($http,$scope,baseUrl);
		listar_proyectosFinished($http,$scope,baseUrl);
	}	
		
	
	
	cargarValores($http,$scope,baseUrl);
	listar_appoiments($http,$scope,baseUrl);
	listar_expenses($http,$scope,baseUrl);
	
	$scope.userActual = $rootScope.userName;
	$scope.IdAddress = $rootScope.wizIdAddress;
	$scope.AddressName = $rootScope.wizAddressName;
	                     
	$scope.ContactName = $rootScope.wizcontactName;
	
	$scope.proyect = {};
	$scope.proyDet = {};
	$scope.proyect.numOwners = 0;
	$scope.proyect.typeConstruction = 0;
	$scope.proyect.typeRoof = 0;
    $scope.proyect.typeWinDoors = 0 ;
    $scope.proyect.typeSolarPanels = 0;
    $scope.proyect.typeAc = 0;
    $scope.proyect.typeConcrete = 0;

    
   
    $scope.findProyect = function(value) {
	         
    	//alert("Proyect Nro : " + value);
		$http.get(baseUrl + '/prDetail?id='+value)
		.success(function(result){
			{
    		   $scope.proyDet = result;
		   		
	        }
		});		    	
    		    	
     }	
    	

	
 	angular.element(document).ready(function() {
        $('#wizard').smartWizard({
            onLeaveStep:leaveAStepCallback
        });

        $('#wizard_verticle').smartWizard({
          transitionEffect: 'slide'
        });
        
        $('#wizard').fixHeight;
        
        function leaveAStepCallback(obj, context){
            //alert("Leaving step " + context.fromStep + " to go to step " + context.toStep);
            return validateSteps(context.fromStep); // return false to stay on step and true to continue navigation 
        }

        function onFinishCallback(objs, context){
            if(validateAllSteps()){
                $('wizard').submit();
                $('wizard2').submit();
                $('wizard3').submit();
            }
        }        
        
        function validateSteps(stepnumber){
            var isStepValid = true;
            // validate step 1
            if(stepnumber == 1){
            	
    	    	if (($scope.proyect.typeConstruction == 0) && ($scope.proyect.typeRoof == 0) && ($scope.proyect.typeWinDoors == 0) && ($scope.proyect.typeSolarPanels == 0) && ($scope.proyect.typeAc == 0) && ($scope.proyect.typeConcrete == 0)){
    	    		notificationService.error('You Must Select at Least one Proyect Type');
    	    		$('#wizard').smartWizard('setError',{stepnum:1,iserror:true});
    	    		isStepValid = false;
    	    		
    	    	}  

    	    	if (($scope.proyect.contrator == null) || ($scope.proyect.paymentMethod == null)){
    	    		notificationService.error('You Must Complete all fields');
    	    		isStepValid = false;
    	    	} 
    	    	
            }
            
            if (stepnumber == 2){
            	
            	if ($scope.proyect.numOwners == 0){
    	    		notificationService.error('You Must include at least One Owner');
    	    		isStepValid = false;            		
            	}
            	
            }
            
            if (stepnumber == 3){
            	
            	if ($scope.proyect.typeRoof == 1){
    	    		if (($scope.proyect.existingRoof == null) || ($scope.proyect.roof == null) || ($scope.proyect.sqftRoof == null) || ($scope.proyect.roofColor == null)){
    	    			isStepValid = false; 
    	    			notificationService.error('You Must Complete all fields');
    	    		}
            	}
            	
            	if ($scope.proyect.winDoor == 1){
            		
            		if (($scope.proyect.winManufacture == null) || ($scope.proyect.winNumber == null) || ($scope.proyect.winMeasures == null)){
    	    			isStepValid = false; 
    	    			notificationService.error('You Must Complete all fields');           			
            		}
            	}
            	
            }            
            return isStepValid;
            // ...      
        }
        

        function validateAllSteps(){
            var isStepValid = true;
            // all step validation logic  
            if (($scope.proyect.financial == null) || ($scope.proyect.todayInsvement == null) || ($scope.proyect.anualPayment == null) || ($scope.proyect.firstDatePay == null) || ($scope.proyect.termsOption == null)){
            	notificationService.error('You Must Complete all fields'); 
            	isStepValid = false;
            }
            
            return isStepValid;
        }  
        
        $('.buttonNext').addClass('btn btn-success');
        
        $('.buttonPrevious').addClass('btn btn-primary');
        
        $('.buttonFinish').addClass('btn btn-default').on('click', function(){ 
            $scope.proyect.userName = $scope.userActual;
            $scope.proyect.address = $rootScope.address;
            
            if ($scope.proyect.paymentMethod == "3"){
            	if ($scope.proyect.cashAmount > 0){
            		var part = $scope.proyect.cashAmount/4;
            		$scope.proyect.cash1 = part;
            		$scope.proyect.cash2 = part;
            		$scope.proyect.cash3 = part;
            		$scope.proyect.cash4 = part;
            	}
            }
                          
            //alert("Id Address : " + $rootScope.address + " -- " + $scope.userActual);
        	$http.post(baseUrl + '/proyect?idAddress='+$rootScope.address.idAddress, $scope.proyect ).
    		success(function(response){
    			   notificationService.success('Project Sucessfull Added');	
	 		       //$location.path("./Proyect-Detail");
      			   $scope.proy = response;
      	    return $http.get(baseUrl + '/prDetail?id='+$scope.proy.idProyect)
    		}).then(function(response){
    					
    		    		   $scope.proyDet = response.data;
    				   	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
    		    		   $scope.currProyect = $scope.proyDet;
    		    		   $rootScope.proyActual = $scope.proyDet;
    		    		   $window.location.assign(baseUrl + '/ecohome#/Proyect-Detail');	
    			       
    		});	    	

	    	
        }); 
      });  
	
	
	   $(document).ready(function() {
	       $(":input").inputmask();
	   });
	   
	   	   
	   //$scope.proyect.typeRoof = "0";
	   //$scope.proyect.typeWinDoors = "0";
	   //$scope.proyect.typeSolarPanels = "0";
	   //$scope.proyect.typeAc = "0";
	   //$scope.proyect.typeConcrete = "0";
	   $scope.tempOwners = [];
	   
	    $scope.checkAll = function(name)
	    {
	        console.log(name);
	    };
	    
	    $scope.testButton = function(){
			notificationService.success('Project Sucessfull Added');	
		       //$location.path("./Proyect-Detail");
		       //$scope.$apply();
		
			   
			   findProyect(464);
		       $window.location.assign('/ecohome#/Proyect-Detail');
	    }
	    
	    $scope.validateCheck = function()
	    {
	    	if (($scope.proyect.typeRoof == "1") || ($scope.proyect.typeWinDoors == "1") || ($scope.proyect.typeSolarPanels == "1") || ($scope.proyect.typeAc == "1") || ($scope.proyect.typeConcrete == "1")){
	    		$scope.proyect.typeConstruction = "0";
	    	}
	    }
	    
	    $scope.newValue = function(value) {
	        console.log(value);
	     }
	    
	    
		$scope.addOwner = function() {
			    $scope.reg = {
		       		    idClient: '',
		       		    sureName: '',
		       		    lastName: '',
		       		    ssn     : '',
		       		    dob     : '',
		       		    email   : ''
		       		  };
		 
				if (($scope.client.sureName != '') && ($scope.client.lastName != '') && ($scope.client.email != '')) {
					
					$scope.proyect.numOwners = $scope.proyect.numOwners + 1;
					$scope.client.middleName = "";
					$scope.client.status = "A";
					$scope.client.homePhone = "";
		    		$http.post(baseUrl + '/client', $scope.client ).
		    		success(function(response){
		    			
		    			$scope.res = response;
		    			$scope.reg.idClient = $scope.res.idClient;
		    			$scope.reg.sureName = $scope.client.sureName;
		    			$scope.reg.lastName = $scope.client.lastName;
		    			$scope.reg.email = $scope.client.email;
		    			$scope.reg.ssn = $scope.client.ssn;
		    			$scope.reg.dob = $scope.client.dob;
		    			$scope.reg.cellPhone = $scope.client.cellPhone;
		    			
		    			
		    			$scope.client.sureName = "";
		    			$scope.client.lastName = "";
		    			$scope.client.email = "";
		    			$scope.client.dob = "";
		    			$scope.client.ssn = "";
		    			$scope.client.cellPhone = "";
		    			

		    			
		    			iniciar_tabla('#tbClientWiz');
		    			
		    			$scope.tempOwners.push($scope.reg);
		    			//$scope.tempClients.push($scope.res.idClient);
		    			$scope.proyect.clients = $scope.tempOwners;
		    			//$scope.proyect.numberOwners = $scope.NumOwners;
		    			
		    			
		  
		    		});
		    		
				}	
		}	
		
	    $scope.addProyect = function() {
	        
	    	$http.post(baseUrl + '/proyect', $scope.proyect ).
    		success(function(response){
    			notificationService.success('Project Sucessfull Added');	
  			
     		});	    	
	    	
	    	
	     }
	    


	    $scope.findListProyect = function(index) {
  	         
	    	//alert(index);
			$http.get(baseUrl + '/prDetail?id='+$scope.proyects[index].idProyect)
			.success(function(result){
				{
					$rootScope.proyDet = result;
					$rootScope.proyActual = $rootScope.proyDet;
					//$scope.currProyect = $scope.proyDet;
					//alert("Proyect Contorller ! $SCOPE Find Proyect :" + $rootScope.proyActual.companyId );
					$window.location.assign(baseUrl + '/ecohome#/Proyect-Detail');
	    		    
		        }
			});		    	
	    		    	
	     }
	    
	    $scope.findListProyectFinis = function(index) {
 	         
	    	//alert(index);
			$http.get(baseUrl + '/prDetail?id='+$scope.proyectsFinis[index].idProyect)
			.success(function(result){
				{
					$rootScope.proyDet = result;
					$rootScope.proyActual = $rootScope.proyDet;
					//$scope.currProyect = $scope.proyDet;
					//alert("Proyect Contorller ! $SCOPE Find Proyect :" + $rootScope.proyActual.companyId );
					$window.location.assign(baseUrl + '/ecohome#/Proyect-Detail');
	    		    
		        }
			});		    	
	    		    	
	     }	    
	    
	    $scope.alerta = function() {
	    	//alert("Resultado  INVEST" + $scope.proyDet.todayInsvement);
	    		    	
	     }
	    
	   
	   /* function findProyect(value){
			$http.get(baseUrl + '/prDetail?id='+value)
			.success(function(result){
				{
				   alert("Proyect Contorller ! Function Find Proyect");	 
	    		   $scope.proyDet = result;
			   		
		        }
			});		    	
	    } */ 
	    
		$scope.clickAddAct = function () {		
			$scope.proyectActivity = {};
		};
		
		
	    
	    $scope.addProyectActivity = function() {
	        
	    	var mailSeller = "N";
	    	var statPr = "N";
	    	
	    	$scope.proyectActivity.userName = $scope.userActual;
	    	//alert("Usuario Actual : " + $rootScope.userName  + "  UserActual : " + $scope.userActual);
	    	
	    	if ($scope.notifySeller == "1"){
	    		mailSeller = "Y";
	    	}
	    	
	    	if ($scope.statusChange == "1"){
	    		statPr = "Y"; 
	    	}
	    	
	    	$scope.idProyect = $scope.proy.idProyect;
	    		
	    	
	    	$http.post(baseUrl + '/proyect/newActivity?idProyect='+$scope.idProyect+"&mailSeller="+mailSeller+"&statPr="+statPr, $scope.proyectActivity ).
    		success(function(response){
    			$scope.tempproyActiviy = {};
    			notificationService.success('Project Activity Sucessfull Added');	
    			listar_activitys($http,$scope,baseUrl);
    			$('#addAct').modal('hide');
    			iniciar_tabla('#tbActivity');
    			$scope.tempproyActiviy = response;
    		return 	$http.post(baseUrl + '/activity/notify',$scope.tempproyActiviy)
    		}).then(function(response){
    			 
    			if (response.data == "1")
    			 console.log("Email Enviado");
     		});	 
	    	
	    	

	    	
	    	
	     }	
	    
		$scope.removerActivity = function(index){    	
	    	var statPr = "1";                                                
			$http.put(baseUrl + '/proyect/delete?id='+$scope.activitys[index].idProyectActivity+'&stat='+statPr).
			success(function(response){
				listar_activitys($http,$scope,baseUrl);
			});
			//$scope.modalInstance.close();
			
		}	
		
	    $scope.addProyectAppoiment = function() {
	        
	    	var mailSeller = "N";
	    	var statPr = null;
	    	$scope.proyectAppoiment.userName = $scope.userActual;
	    	//alert("Usuario Actual : " + $rootScope.userName  + "  UserActual : " + $scope.userActual);
	    	
	  
	    	
	    	$http.post(baseUrl + '/proyect/newAppoiment?id='+$scope.idProyect, $scope.proyectAppoiment ).
    		success(function(response){
    			notificationService.success('Project Appoiment Sucessfull Added');	
    			listar_appoiments($http,$scope,baseUrl);
    			$('#addApp').modal('hide');
    			//iniciar_tabla('#tbAppoiment');
     		});	    	
	    	
	    	
	     }	
	    
		$scope.borrarApp = function (index) {

		    $scope.index = index;
		    
		    $scope.modalInstance = $uibModal.open({
		    	ariaLabelledBy: 'modal-title',
		        ariaDescribedBy: 'modal-body',
		    	templateUrl: 'borrar.html',
		        scope: $scope,
		        size: 'sm'	        
		    });
		}
		
		$scope.removeAppoiment = function(index){    
			
	    	
			$http.put(baseUrl + '/proyectApp/delete/'+$scope.appoiments[index].idProyectAppoiment).
			success(function(response){
				listar_appoiments($http,$scope,baseUrl);
			});
			$scope.modalInstance.close();
			
		}	
		
		$scope.cancel = function () {		
			$scope.modalInstance.dismiss('cancel');
		};
		
		
		
		
		

		

		
	
	
	
}]);


app.controller('detProyectsController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','Upload', '$timeout','$uibModal',
                              		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,Upload, $timeout,$uibModal) {

	
	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	$scope.currProyect = $rootScope.proyActual;
	$scope.envelopeId = {};
	$scope.proyectExpensive = {};
	$scope.actFund = {};
	$scope.proyectExpensive.statRefund = '0';
	$scope.proyectExpensive.invQty = '0';
	$scope.totfinancial = 0;
	$scope.prfinancial = [];
	$scope.totFund = 0;
	//listar_activitys($http,$scope,baseUrl);
	listRoof($http,$scope,baseUrl);
	//alert("PROYECT ACTUALLLL " + $rootScope.proyActual.companyId);
	
	listar_files($http,$scope,baseUrl,$scope.currProyect.idProyect);
	listar_activitysPr($http,$scope,baseUrl,$scope.currProyect.idProyect);
	$scope.baseUrl2 = new $window.URL($location.absUrl()).origin;
	
	listSellersProyect($http,$scope,baseUrl,$rootScope.proyActual.leader1,$rootScope.proyActual.leader2,$rootScope.proyActual.leader3);
	listar_subcont($http,$scope,baseUrl);
	listar_subcontrators($http,$scope,baseUrl);
	cargarValores($http,$scope,baseUrl);
	listar_expensesPr($http,$scope,baseUrl,$scope.currProyect.idProyect);
	query_proyects($http,$scope,baseUrl,$scope.currProyect.idProyect);
	listar_prfinancials($http,$scope,baseUrl,$scope.currProyect.idProyect);
	listar_prfinancialsAct($http,$scope,baseUrl,$scope.currProyect.idProyect);
	listReferral($http,$scope,baseUrl);
	listContrator($http,$scope,baseUrl);
	listFinancial($http,$scope,baseUrl);
	listRoof($http,$scope,baseUrl);
	cargarFundingsTypes($http,$scope,baseUrl,$scope.currProyect.paymentMethod);
	listar_funding($http,$scope,baseUrl,$scope.currProyect.idProyect)
	
	
	                 
	
	
	
	//dashboard1($http,$scope,baseUrl,$filter);
	
	
	   $(document).ready(function() {
	       $(":input").inputmask();
	   });
	
	
	 $scope.requestSign = function() {
		    
		    notificationService.success('Wait 3 seconds...');
		                         
	    	$http.get(baseUrl + '/requestInitialCont?proyectId='+$scope.currProyect.idProyect+'&sesionId='+$rootScope.idSesion+'&username='+$rootScope.userName+'&urlResul='+$scope.baseUrl2, $scope.currProyect ).
    		success(function(response){
    			
    			$scope.envelopeId = response;
    			$rootScope.viewUrl = $scope.envelopeId.url;
    			//alert($scope.currProyect.idProyect);
    			$window.location.assign(baseUrl + '/ecohome#/proyect/sign/embedded');
     		});			 
		 
	 }
	 

	 $scope.requestSignPree = function() {
		    
		notificationService.success('Wait 3 seconds...');
		                         
	   if ($scope.currProyect.paymentMethod == "1"){
		
			$http.get(baseUrl + '/requestPreeliminare?proyectId='+$scope.currProyect.idProyect+'&sesionId='+$rootScope.idSesion+'&username='+$rootScope.userName+'&urlResul='+$scope.baseUrl2, $scope.currProyect ).
	 		success(function(response){
	 			
	 			$scope.envelopeId = response;
	 			$rootScope.viewUrl = $scope.envelopeId.url;
	 			//alert($scope.currProyect.idProyect);
	 			$window.location.assign(baseUrl + '/ecohome#/proyect/sign/embedded');
	  		});	
		
	   }
	   
	   if ($scope.currProyect.paymentMethod == "3"){
			
			$http.get(baseUrl + '/requestPreeliminareFinCash?proyectId='+$scope.currProyect.idProyect+'&sesionId='+$rootScope.idSesion+'&username='+$rootScope.userName+'&urlResul='+$scope.baseUrl2, $scope.currProyect ).
	 		success(function(response){
	 			
	 			$scope.envelopeId = response;
	 			$rootScope.viewUrl = $scope.envelopeId.url;
	 			//alert($scope.currProyect.idProyect);
	 			$window.location.assign(baseUrl + '/ecohome#/proyect/sign/embedded');
	  		});	
		
	   }	   
	   
		 
	 }	 
	 
		 
	
	    $scope.clickaddExp = function(){
	    	$scope.proyectExpensive = {};
	    }
	 
	    $scope.addExpense = function(id) {
	         
	    	//alert("Proyect " + id);
	    	
	    	
	    	
		    	$http.get(baseUrl + '/prDetail?id='+id)
				.success(function(result){
					{
						$rootScope.proyDet = result;
						$rootScope.proyActual = $rootScope.proyDet;
						//$scope.currProyect = $scope.proyDet;
						//alert("Proyect Contorller ! $SCOPE Find Proyect :" + $rootScope.proyActual.companyId );
						$window.location.assign(baseUrl + '/ecohome#/Proyect-Expensive');
						
		    		    
			        }
				});	
	    	
	    		    	
	     }	
	    
	    
	    $scope.buttMember = function(){
	    	listSellers($http,$scope,baseUrl);  
	    	findActualSeller($http,$scope,baseUrl,$rootScope.userName);	    	
	        //$scope.sellers.pop($scope.actSel.idSeller);
	    	
	    	
	    }
	    
	    
	    $scope.saveMember = function(){
	    	
	      if (($rootScope.proyActual.leader2 == null) || ($rootScope.proyActual.leader3 == null)){	
	    	
	    	$http.put(baseUrl + '/proyect/addMember?idProyect='+$scope.proyActual.idProyect+"&idSeller="+$scope.newMember).
    		success(function(response){
    			$scope.currProyect = response;
    			$rootScope.proyActual = $scope.currProyect;
    			notificationService.success('Member Succesfulled Added');	
    			$('#addMember').modal('hide');
     		});	 
	    	
	      }	
	      
	    }
	    
	    $scope.addProyectExp = function() {
	        
	    	var statPr = null;
	    	$scope.proyectExpensive.proyect= $rootScope.proyActual;
	    	//alert("Usuario Actual : " + $rootScope.userName  + "  UserActual : " + $scope.userActual);
	    	
	    	 
	    	
	    	if ($scope.proyectExpensive.invQty == "1"){
	    		//alert("TOT Inov " + parseFloat($scope.totInvoices).toFixed(2));
		    	if (parseFloat($scope.totInvoices).toFixed(2) == parseFloat($scope.proyectExpensive.mountExpensive).toFixed(2)){
		    	
				    	$http.post(baseUrl + '/proyect/newExpense?idProyect='+$rootScope.proyActual.idProyect+'&username='+$rootScope.userName, $scope.proyectExpensive ).
			    		success(function(response){
			    			notificationService.success('Project Expense Sucessfull Added');	
			    			$('#addExp').modal('hide');
			    			listar_expensesPr($http,$scope,baseUrl,$rootScope.proyActual.idProyect);
			    			iniciar_tabla('#tbExp');
			    			$scope.proyectExpensive = {};
			     		});	  
		    	 } 
		    	else
		    	{
		    		//alert("Amount Total Invoices must be equal than Amount Total Expense");
		    	}	
		    	
		    	
	    	}
	    	else
	    	{
		    	$http.post(baseUrl + '/proyect/newExpense?idProyect='+$rootScope.proyActual.idProyect+'&username='+$rootScope.userName, $scope.proyectExpensive ).
	    		success(function(response){
	    			notificationService.success('Project Expense Sucessfull Added');	
	    			$('#addExp').modal('hide');
	    			listar_expensesPr($http,$scope,baseUrl,$rootScope.proyActual.idProyect);
	    			iniciar_tabla('#tbExp');
	    			$scope.proyectExpensive = {};
	     		});		    		
	    	}	
	    		
	    	
	    	
	     }	
	    
		$scope.borrarExp = function (index) {

		    $scope.index = index;
		    
		    $scope.modalInstance = $uibModal.open({
		    	ariaLabelledBy: 'modal-title',
		        ariaDescribedBy: 'modal-body',
		    	templateUrl: 'borrar.html',
		        scope: $scope,
		        size: 'sm'	        
		    });
		}
		
		$scope.removeExpensive = function(index){    
			
	    	
			$http.put(baseUrl + '/proyectExp/delete/'+$scope.expensesPr[index].idProyectExpensive).
			success(function(response){
				listar_expensesPr($http,$scope,baseUrl,$rootScope.proyActual.idProyect);
				iniciar_tabla('#tbExp');
    			
			});
			$scope.modalInstance.close();
			
		}	
		
		$scope.borrarInv = function (index) {

		    
	    $scope.modalInstance = $uibModal.open({
		    	ariaLabelledBy: 'modal-title',
		        ariaDescribedBy: 'modal-body',
		    	templateUrl: 'borrarInv.html',
		        scope: $scope,
		        size: 'sm'	        
		    });
		}		
		
		$scope.removeInvoice = function(index){
			
			$scope.index = index;
			$scope.totInvoices = $scope.totInvoices - $scope.tempInvoices[index].invoiceAmount;
			//alert($scope.tempInvoices[index].invoiceAmount);
			$scope.tempInvoices.splice(index,1);
			
			
			//$scope.modalInstance.close();
		}
		
		$scope.clickAdd = function(){
			
	     	$scope.numInvoices = 0;
	     	$scope.invoice     = {};
	     	$scope.totInvoices = 0;
	     	$scope.tempInvoices = [];	
	     	//validatefields($http,$scope,baseUrl,$filter);
		}
		
		
		$scope.cancel = function () {		
			$scope.modalInstance.dismiss('cancel');
		};	    
		
		
     	$scope.editCurrExp = function(index){
			$http.get(baseUrl + "/findexpensive?id=" + $scope.expensesPr[index].idProyectExpensive).success(function (data) {      
				$scope.proyectExpensiveEd = data; 
				
				$scope.tempInvoices = $scope.proyectExpensiveEd.invoices;
				
				if ($scope.tempInvoices != null){
					$scope.numInvoices = $scope.tempInvoices.length;
					//alert($scope.numInvoices);
				}
				
		     	
		     	$scope.totInvoices = 0;
		     	//$scope.tempInvoices = [];				

			});
			
			
	    }
     	
     	$scope.numInvoices = 0;
     	$scope.invoice     = {};
     	$scope.totInvoices = 0;
     	$scope.tempInvoices = [];
     	
     	
     	$scope.addInvoice = function(){
     		
     		if (($scope.invoice.invoiceNumber != null) && ($scope.invoice.invoiceAmount != null))
     		{	
     			//alert("SUMA : " + (parseFloat($scope.totInvoices,2)+parseFloat($scope.invoice.invoiceAmount,2)));
     			//alert("TOTAl : " + parseFloat($scope.proyectExpensive.mountExpensive) );
     			if (parseFloat((parseFloat($scope.totInvoices) + parseFloat($scope.invoice.invoiceAmount)).toFixed(2)) <= parseFloat($scope.proyectExpensive.mountExpensive).toFixed(2)){
     				
     				   
								$http.post(baseUrl + '/proyectInvoice/add/', $scope.invoice).
								success(function(response){
									
									$scope.inv = response;
									$scope.numInvoices = $scope.numInvoices + 1;
									//listar_expensesPr($http,$scope,baseUrl,$rootScope.proyActual.idProyect);
									//iniciar_tabla('#tbExp');
								    $scope.reg = {
							       		    idInvoice     : '',
							       		    invoiceNumber : '',
							       		    invoiceDate   : '',
							       		    invoiceAmount : '',
							       		    statInvoice   : '',
							       		    statRefund    : '',
							       		    invQty        : ''
							       		  };
								    
								    $scope.reg.idInvoice     =  $scope.inv.idInvoice;
								    $scope.reg.invoiceNumber =  $scope.inv.invoiceNumber;
								    $scope.reg.invoiceDate   =  $scope.inv.invoiceDate;
								    $scope.reg.invoiceAmount =  $scope.inv.invoiceAmount;	
								    $scope.reg.statInvoice   =  $scope.inv.statInvoice;	
								    $scope.reg.statRefund    =  $scope.inv.statRefund;
								    $scope.reg.invQty        =  $scope.inv.invQty;
								    
								        			
					    			$scope.tempInvoices.push($scope.reg);
					    			$scope.totInvoices =parseFloat($scope.totInvoices) + $scope.inv.invoiceAmount;
					    			$scope.proyectExpensive.invoices = $scope.tempInvoices;
					    			
					    			$scope.invoice.invoiceNumber = null;
					    			$scope.invoice.invoiceAmount = null;
									
					    			
								}); 
     				   
					
		     		} // fin Validar Campos Invoice
		     		else{
		     			alert("Amount invoices must be less than Expense Total Amount");
		     		}	
     			
     		} // validar que el monto sea menor
     		else
     		{
     			alert("You must to complete all invoice fields");
     			
     		}
     		
     	}
     	
     	
     	$scope.updateDetails = function(){

	    	$http.put(baseUrl + '/proyect/updateDetails?idProyect='+$scope.currProyect.idProyect+'&username='+$rootScope.userName,$scope.currProyect).
    		success(function(response){
    			$scope.currProyect = response;
    			$rootScope.proyActual = $scope.currProyect;
    			notificationService.success('Proyect Details Succesfulled Upddate');
    			cargarFundingsTypes($http,$scope,baseUrl,$scope.currProyect.paymentMethod);
    			listar_prfinancialsAct($http,$scope,baseUrl,$scope.currProyect.idProyect);
    			$('#details').modal('hide');
     		});
     		
     	}
     	
     	
     	     	
	    
     	
     	$scope.clickaddfin = function(){
     		$scope.totfinancial = $scope.prfinancial.length;
     		//alert($scope.totfinancial);
     	}
     	
     	

        
     	$scope.addprfinancial = function(){
     		
	    	$http.post(baseUrl + '/proyect/newprFinancial?id='+$rootScope.proyActual.idProyect+'&username='+$rootScope.userName, $scope.financialpr ).
    		success(function(response){
    			
    			$scope.resul = response;
    			if ($scope.resul.statRecord == "-1"){
    				
    				notificationService.error('Error ,' + $scope.resul.statFunding);
    			}else{
    			    			
	    			listar_prfinancials($http,$scope,baseUrl,$scope.currProyect.idProyect);
	    			listar_prfinancialsAct($http,$scope,baseUrl,$scope.currProyect.idProyect);
	    			$scope.totfinancial = $scope.totfinancial + 1;
	    			//alert($scope.totfinancial);
	    			$scope.financialpr = {};
    			
    			}
    			
     		});	 
     	}
     	
     	$scope.deleteprFinancial = function(index){
     		
			$http.put(baseUrl + '/proyectFin/delete?id='+$scope.prfinancial[index].idProyectFinancial+'&username='+$rootScope.userName).
			success(function(response){
				listar_prfinancials($http,$scope,baseUrl,$scope.currProyect.idProyect);
				listar_prfinancialsAct($http,$scope,baseUrl,$scope.currProyect.idProyect);
    			
			});
			   		
     		
     	}
     	
     	$scope.getamountFund = function(){
     		
     			$http.get(baseUrl + "/findOneProFin?id="+$scope.actFund.prf.idProyectFinancial).success(function (data) {  
     				$scope.prFinAct = data;  
     				$scope.actFund.amount = $scope.prFinAct.amount;
     			});
     		   
     	}
     	
     	$scope.addFunding = function(){

     		
	    	$http.post(baseUrl + '/proyect/newprFunding?id='+$rootScope.proyActual.idProyect+'&username='+$rootScope.userName, $scope.actFund ).
    		success(function(response){
    			
    			$scope.resul = response;
    			
    			if ($scope.resul.statRecord == "-1"){
    				
    				notificationService.error('Error ,' + $scope.resul.bank);
    			}else{
	    			listar_funding($http,$scope,baseUrl,$rootScope.proyActual.idProyect);
	    			listar_prfinancials($http,$scope,baseUrl,$scope.currProyect.idProyect);
	    			listar_activitysPr($http,$scope,baseUrl,$rootScope.proyActual.idProyect);
	    			listar_prfinancialsAct($http,$scope,baseUrl,$scope.currProyect.idProyect);
	    			notificationService.success('Project Funding Sucessfull Added');
	    			$scope.totFund = $scope.totFund + 1;
	    			$scope.actFund = {};
    			}
    			
     		});
     		
     	}
     	
     	$scope.deleteprFunding = function(index){
     		
			$http.put(baseUrl + '/proyectFun/delete?id='+$scope.listfundings[index].idProyectFunding+'&username='+$rootScope.userName).
			success(function(response){
				listar_funding($http,$scope,baseUrl,$rootScope.proyActual.idProyect);
				listar_prfinancialsAct($http,$scope,baseUrl,$scope.currProyect.idProyect);
    			
			});
			   		
     		
     	}     	
     	

     	
	
}]);


app.controller('signerController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$sce',
                                		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService, $sce) {

  	
  	$http.defaults.headers.post["Content-Type"] = "application/json";
  	var baseUrl = ".";
	//$window.location.href = $rootScope.viewUrl;
	//$location.url = '/proyect/sign/embedded';
  	$window.location =  $rootScope.viewUrl;
	
	$scope.getTemplateUrl = function() {
		  return $rootScope.viewUrl;
	};
	

 }]);

app.controller('resultsignerController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$sce',
                         		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService, $sce) {


	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	$scope.event = ""
	
	$scope.currProyect = {};
	$scope.currProyect = $rootScope.proyActual;
	$scope.res = $location.search().event;
	//alert("Usuario Actual : " + $rootScope.userName  + "  Sesion : " + $rootScope.idSesion);
	//alert("Resultado : " + $rootScope.proyActual + "  " + $scope.res);
	$scope.txtResul = ""
	
	if ($scope.res == "signing_complete"){
		
		
		
    	$http.get(baseUrl + '/lastEnvelope?currSession='+$rootScope.idSesion).
		then(function(response){
			
			$scope.proyectId = response.data;
			//alert("Id Proyecto : " + $scope.proyectId);
	    return $http.put(baseUrl + "/proyect/editst?id="+$scope.proyectId+"&stat=2") 
		}).then(function(response){
			    notificationService.success('Proyect Sucessfully Signed');
		    	$scope.result = response.data;
		    	$rootScope.proyActual = $scope.result;
			    //alert("Actual : " + $rootScope.proyActual.companyId);
			    $scope.event = "2";
			    $scope.txtResul = "You have signed the document! The document will be securely stored on the DocuSign"

		})		
    	
		
	}

}]);


app.controller('addressController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$sce',
                                		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService, $sce) {
	
	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	//alert("Controller Address");
	$scope.address = {};
	listAddress($http,$scope,baseUrl);


}]);



app.controller('profileController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$sce','Upload','$timeout',
                                		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService, $sce, Upload, $timeout) {


       	$http.defaults.headers.post["Content-Type"] = "application/json";
       	var baseUrl = ".";
       	$scope.currProyect = {};
       	$scope.currProyect = $rootScope.proyActual; 
        $scope.newPassword = "";
       	$scope.repnewPassword =  "";
       	$scope.profileSel  = {}; 
       	$scope.changePassword = 0; 
       	console.log("ENTER TO CONTROLLER : " + $rootScope.partsAutocomplete);
       	$scope.partsAuto = 1; 
       	

       	
    	$http.get(baseUrl + '/findProfileUser?username='+$rootScope.user.username).
		success(function(response){
			$scope.profileSel = response;
			console.log("TOTAL",$scope.profileSel);   
			if ($scope.profileSel.partsAutocomplete == '1'){
				//$scope.profileSel.partsAutocomplete = true; 
				$scope.profileSel.partsAutocomplete = 1; 
			}
 		});
    	
    	
    	$scope.optionsParts = function(){
    		
    		$rootScope.partsAutocomplete = $scope.profileSel.partsAutocomplete;
    		console.log("ROOT CLICK : " + $scope.profileSel.partsAutocomplete);
    	}

   	 	
    	$scope.uploadFiles = function(file, errFiles) {
	        $scope.f = file;
	        $scope.errFile = errFiles && errFiles[0];
	        //alert("Entre a Files : ");
	        if (file) {
	            file.upload = Upload.upload({
	                url: baseUrl + '/uploadAvatar',
	                data: {file: file,  username : $rootScope.user.username}
	                
	            });
             
	            $scope.prFiles = {};
	            $scope.activitysPr = {};
	            file.upload.then(function (response) {
	                $timeout(function () {
	                    file.result = response.data; 
	                    //$rootScope.imgUrl =  $scope.dirimageUrl = 'images/' + $scope.greeting.imageUrl; 
	                    
	                    $http.get(baseUrl + '/welcome').success(function(data) {
	                        
	                        $scope.greeting = data;
	                        $rootScope.dirimageUrl = 'images/' + $scope.greeting.imageUrl;  
	                        //$rootScope.imgUrl = $scope.dirimageUrl;
	                        //$scope.dirimageUrl = $scope.greeting.imageUrl;

	                      });	
	                    
	                    
	                    
	                });
	            }, function (response) {
	                if (response.status > 0)
	                    $scope.errorMsg = response.status + ': ' + response.data;
	                    notificationService.error($scope.errorMsg);
	            }, function (evt) {
	                file.progress = Math.min(100, parseInt(100.0 * 
	                                         evt.loaded / evt.total));
	                
	            });
	            
	            
	            
	        }   
	    }	
    	
    	$scope.initChange = function(){
    		
    		$scope.newPassword = '';
    		$scope.repnewPassword = '';
    		
    	}
    	
    	
    	$scope.updateProfile = function() {
    		
	    	if (($scope.profileSel.email != "") && ($scope.profileSel.name != "") && ($scope.profileSel.lastName != ""))  {
    		    
	    		
	    		if ($scope.changePassword == 1) {
	    		    
	    			console.log("NEW : " + $scope.newPassword + " CONFIRMM : " +  $scope.repnewPassword); 
	    			
	    			if (($scope.newPassword == $scope.repnewPassword) &&  ($scope.newPassword != "") &&  ($scope.newPassword.length > 5)){
	    			    
	    				$scope.profileSel.password = $scope.newPassword; 
			    		$http.put(baseUrl + '/updateProfile?changeP='+$scope.changePassword, $scope.profileSel ).
			    		success(function(response){
			    			
			    			$scope.resul = response;
			    			notificationService.success('Profile Updated');
			    		});
		    		
	    			} else {
	    				notificationService.error("Please be sure Passwords are equal, not blank and length gretar than five characters");
	    			}
		    		
	    		} else {
	    			
		    		$http.put(baseUrl + '/updateProfile?changeP='+$scope.changePassword, $scope.profileSel ).
		    		success(function(response){
		    			
		    			$scope.resul = response;
		    			notificationService.success('Profile Updated');
		    		});	    			
	    		}	
    		
	    	}else
	    	{
	    		notificationService.error("All fields must be completed");
	    	}	
    		
    		
    	}
    	
    	
       	

 }]);


app.controller('reportController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$sce',
                          		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService, $sce) {


 	//$http.defaults.headers.post["Content-Type"] = "application/json";
 	var baseUrl = ".";
 	$scope.currProyect = {};
 	$scope.currProyect = $rootScope.proyActual;
 	$scope.newPassword = null;
 

	$http.get(baseUrl + '/rptsellerLeads',{responseType:'arraybuffer'}).
	success(function(response){
		
		$scope.file = response;
	    var file = new Blob([response], {type: 'application/pdf'});
	    var fileURL = URL.createObjectURL(file);
	    $window.location = fileURL;
	    //alert("En Reports http wind Buffer");

	});
	
	

 }]);



app.directive('iCheck', ['$timeout', function($timeout){
    return {
      require: 'ngModel',
      link: function($scope, element, $attrs, ngModel) {
        return $timeout(function() {
          var value = $attrs['value'];

          $scope.$watch($attrs['ngModel'], function(newValue){
            $(element).iCheck('update');
          })

          return $(element).iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green',
          }).on('ifChanged', function(event) {
            if ($(element).attr('type') === 'checkbox' && $attrs['ngModel']) {
              $scope.$apply(function() {
                return ngModel.$setViewValue(event.target.checked);
              });
            }
            if ($(element).attr('type') === 'radio' && $attrs['ngModel']) {
              return $scope.$apply(function() {
                return ngModel.$setViewValue(value);
              });
            }
          });
        });
      }
    };
}]);


app.directive('inputMask', function(){
	  return {
	    restrict: 'A',
	    link: function(scope, el, attrs){
	      $(el).inputmask(scope.$eval(attrs.inputMask));
	      $(el).on('change', function(){
	        scope.$eval(attrs.ngModel + "='" + el.val() + "'");
	        // or scope[attrs.ngModel] = el.val() if your expression doesn't contain dot.
	      });
	    }
	  };
	});




//------------------------------------------------------------------------------------------------------------
//----------------------                    MATERIALS    -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("materialsController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		//listCustomers($http,$scope,baseUrl);
		$scope.client = {};
		//dashboard1($http,$scope,baseUrl);
		listMaterials($http,$scope,baseUrl);

		$scope.addCustomer = function(){   	
	    	
		$http.post(baseUrl + '/client', $scope.client ).
		success(function(response){
			$('#addCustomer').modal('hide');
			notificationService.success('Client Sucessfully registered');
			$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customer = data;
			});
			$scope.txtDesc=null;

		});
}	
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removeCustomer = function(index){    	
				
			
			$http.put(baseUrl + '/client/' +$scope.customers[index].idClient, $scope.customers[index]).
			success(function(response){
				    notificationService.success('Client Sucessfully removed');
					$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customers = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrCustomer = function(index){
				$http.get(baseUrl + "/findOneCustomer?id=" + $scope.customers[index].idClient).success(function (data) {      
					$scope.client = data; 

				});
		}
		
		$scope.editCustomer = function(){   	
	    	
		$http.put(baseUrl + '/client/edit/' + $scope.client.idClient, $scope.client ).
		success(function(response){
			$('#editCustomer').modal('hide');
			$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customers = data;
			});
			$scope.txtDesc=null;

		});
 }		
		
}]);


//------------------------------------------------------------------------------------------------------------
//----------------------                    PARTS    -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("partsController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		//listCustomers($http,$scope,baseUrl);
		$scope.client = {};
		//dashboard1($http,$scope,baseUrl);
		listParts($http,$scope,baseUrl);

		$scope.addCustomer = function(){   	
	    	
		$http.post(baseUrl + '/client', $scope.client ).
		success(function(response){
			$('#addCustomer').modal('hide');
			notificationService.success('Client Sucessfully registered');
			$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customer = data;
			});
			$scope.txtDesc=null;

		});
  }	
		
		$scope.borrar = function (index) {

		    $scope.index = index;
		    
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
		
		
		$scope.removeCustomer = function(index){    	
				
			
			$http.put(baseUrl + '/client/' +$scope.customers[index].idClient, $scope.customers[index]).
			success(function(response){
				    notificationService.success('Client Sucessfully removed');
					$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customers = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrCustomer = function(index){
				$http.get(baseUrl + "/findOneCustomer?id=" + $scope.customers[index].idClient).success(function (data) {      
					$scope.client = data; 

				});
		}
		
		$scope.editCustomer = function(){   	
	    	
		$http.put(baseUrl + '/client/edit/' + $scope.client.idClient, $scope.client ).
		success(function(response){
			$('#editCustomer').modal('hide');
			$http.get(baseUrl + "/list-customers").success(function (data) {      
					$scope.customers = data;
			});
			$scope.txtDesc=null;

		});
   }		
		
}]);


//-------------------------------------------------------------------------------------------
//----------------      Wizard Add New QUOATE LEAD   -----------------------------------------
//-------------------------------------------------------------------------------------------


app.controller('quoteleadController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$uibModal','Upload', '$timeout','$q', '$log',
		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,$uibModal,Upload, $timeout, $q, $log) {

	$http.defaults.headers.post["Content-Type"] = "application/json";
	//alert("Entre Constoller lead");
	var baseUrl = ".";
	$scope.newNo = 0;
	$scope.newC = '1';
	$scope.quote = {};
	$scope.quote.customer = {};
	$scope.quote.expirationDays = "20";
	//$scope.customerExp = {};
	$scope.item = {}; 
	$scope.auxquote = {};
	$scope.auxquote2 = {};
	//$scope.customer = {};
	$scope.item.drawingAva = '0';
	$scope.item.newPart = '0';
	$scope.item.sampleAva = '0';
	$scope.item.concernsCurr = '0';
	$scope.item.cadfileAva = '0';
	$scope.item.packageReq = '0';
	$scope.item.partKissCut = '0';
	$scope.totItems = 0;
	$scope.userActual = $rootScope.userName;
	init_icheck();
	listincoterms($http,$scope,baseUrl);
	listteslamodels($http,$scope,baseUrl);
	listTerms($http,$scope,baseUrl);
	listSellers($http,$scope,baseUrl);
	listEstimators($http,$scope,baseUrl);
	listReferrals($http,$scope,baseUrl);
	listitemsType($http,$scope,baseUrl);
	listParts($http,$scope,baseUrl);
	listIndustries($http,$scope,baseUrl);
	listCustomers($http,$scope,baseUrl);
	listMaterials($http,$scope,baseUrl);
	listqitems($http,$scope,baseUrl);
	listquotes($http,$scope,baseUrl, $rootScope.user.username, $rootScope.role);
	listmeasureunits($http,$scope,baseUrl);
	deletetempfiles($http,$scope,baseUrl,$rootScope.idSesion);
	initFilterStatus($http,$scope,baseUrl);
	$scope.lbFilterEst = "All Estimators";
	$scope.lbFilerStat = "All Status";
	$scope.tempidEstimator = "";
	$scope.path = $location.path();
    
	if ($rootScope.role == '2'){
    	listCustomersSeller($http,$scope,baseUrl,$rootScope.user.username);
    }
	
	if ($scope.path == '/add-qlead')
		listsalescodes($http,$scope,baseUrl);
	
	
	
	//$rootScope.partsAutocomplete = 0;
	
//	$scope.qSelected    = "";
//	$scope.qSelectedCus = "";
//	$scope.qSales  = "";
//	$scope.qSw  = '1';
//	$scope.qContact = "";
//    $scope.qPhone =    "";	
	
	
	$scope.dzOptions = {
			url : './upload',
			paramName : 'photo',
			maxFilesize : '10',
			maxFiles : '10'
	};
	
	
	$scope.refreshTableQ4 = function(){
		
		init_buttons_table('#tbQuotesAll');
		
	}
	
	
	$scope.dzCallbacks = {
			'addedfile' : function(file){
				console.info('File Added To Quote.', file.name);
			},
			'error' : function(file, xhr){
				console.warn('File failed to upload from dropzone 2.', file, xhr);
			},
			'success' : function(file, data){
			     console.info('Suceess File.', data.id);
			     $http.put(baseUrl + '/quoteFile/updateSession/?id='+data.id+'&sessionId='+$rootScope.idSesion).
			     success(function(response){
			    	     console.info('Suceess File.', data.id);
				 });
			},
			'removedfile' : function(file){
			     $http.put(baseUrl + '/quoteFile/deletesessionFile/?sessionId='+$rootScope.idSesion+'&fileName='+file.name).
			     success(function(response){
			    	     console.info('Suceess delete File.');
				 });				
				console.info('File Remove From Quote.', file.name);
			} 
    };   
	
	
	$scope.removeFile = function() {
		
		
	}
	
	 
	  $scope.auxCo = {};
	  $scope.client = {
			    primary_address: {},
			    countries: [{
			      id : 1,	
			      name: 'Australia'
			    }, {
			      id : 2,	
			      name: 'United States'
			    }, {
			      id : 3,	
			      name: 'United Kingdom'
			    }]
			  };
	
	if ($rootScope.role == '2')
	      listquotesseller($http,$scope,baseUrl, $rootScope.user.username); 
	
	//listtempitems($http,$scope,baseUrl);
	
    $scope.validateCheck = function()
    {
    	
    	//alert("Click Checkbox");
    	if ($scope.newYes == 1){
    		$scope.newNo = 0;
    		//init_icheck();
    		$scope.quote = {};
    	}
    	
    	if ($scope.newNo == 1){
    		$scope.newYes = 0;
    		$scope.quote = {};
    	}
    	
    	
    }
    
    function validateAllSteps(){
        var isStepValid = true;
        // all step validation logic  
        //alert("Items  " + $scope.totItems);
		if ($scope.totItems < 2){
			isStepValid = false;
			notificationService.error('You must Add at least one Item');			
		}
        
        return isStepValid; 
    } 
    
    
	
	angular.element(document).ready(function() {
        $('#wizard').smartWizard({
            onLeaveStep:leaveAStepCallback
        });

        $('#wizard_verticle').smartWizard({
          transitionEffect: 'slide'
        });
        
        $('#wizard').fixHeight;
        
        function leaveAStepCallback(obj, context){
            //alert("Leaving step " + context.fromStep + " to go to step " + context.toStep);
            return validateSteps(context.fromStep); // return false to stay on step and true to continue navigation 
        }

        function onFinishCallback(objs, context){
            if(validateAllSteps()){
                $('wizard').submit();
                $('wizard2').submit();
                $('wizard3').submit();
            }
        }        
        
        function validateSteps(stepnumber){
            var isStepValid = true;
            // validate step 1
            if(stepnumber == 1){
            	//$scope.customer.customerName = "testing";
            	//alert("Is Existing : " +  $rootScope.foundCust );
            	console.log("Name Cust : " + $scope.customer.customerName);
            	console.log("Name Contact : " + $scope.customer.contactName);
            	console.log("Name Teslas : " + $scope.customer.teslaSubContrator);
            	console.log("Name Origin : " + $scope.customer.cutomerOrigin);
            	console.log("Is Existing : " +  $rootScope.foundCust);
            	
            	
            	
            	if ($scope.newC == "1"){ 
            		if (($scope.customer.customerName == "") || ($scope.customer.contactName == "") || ($scope.customer.teslaSubContrator == null) || ($scope.customer.cutomerOrigin == "") ){
            			isStepValid = false;
            			notificationService.error('You Must Complete all obligatory fields - Step 1 New');
            		}
            	}
            	
            	if ($scope.newC == "0"){
            		
            		if (($scope.customerExp == null) || ($scope.customerExp.teslaSubContrator == null) || ($scope.customerExp.cutomerOrigin == "") ){
            			isStepValid = false;
            			notificationService.error('You Must Complete all obligatory fields - Step 1');
            		}            		
            		
            	}
            	
            	console.log("Step 1");
            	console.log("New C :" + $scope.newC); 
            	//alert("Customer Tesla Sub " + $scope.customer.teslaSubContrator) ;
    	    	
            }
            
            if (stepnumber == 2){
            	
            	if (($scope.quote.terms == null) || ($scope.quote.seller == null) || ($scope.quote.estimator == null) || ($scope.quote.referral == null)) {
        			isStepValid = false;
        			notificationService.error('You Must Complete all obligatory fields - Step 2');            		
            	}
                
				isStepValid = true;
				console.log("QUOTE NOTES" + $scope.quote.quoteNotes);
				console.log("Step 2");
				
            	
            }
            
            if (stepnumber == 3){
            	
            	/*
            	if ($scope.proyect.winDoor == 1){
            		
            		if (($scope.proyect.winManufacture == null) || ($scope.proyect.winNumber == null) || ($scope.proyect.winMeasures == null)){
    	    			isStepValid = false; 
    	    			notificationService.error('You Must Complete all fields');           			
            		}
            	} */
            	
        		if ($scope.totItems < 1){
        			isStepValid = false;
        			notificationService.error('You must Add at least one Item');			
        		}
            	
            	console.log("Step 3");
            	
            }            
            return isStepValid;
            // ...      
        }
        

 
        
        $('.buttonNext').addClass('btn btn-success');
        
        $('.buttonPrevious').addClass('btn btn-primary');
        
        $('.buttonFinish').addClass('btn btn-default').on('click', function(){ 
            
        	$scope.validIt = true
        	if ($scope.totItems < 1){
        		$scope.validIt = false;
    			notificationService.error('You must Add at least one Item');			
    		}
        	
        	
        	if ($scope.validIt) {
        	        
        		    var statNew = '';
		            $scope.quote.user = $rootScope.user; 
		            
		            $scope.auxquote2 = {};  
		            
		            if ($scope.newC == "1"){    //  Try to add new customer, i by any chance exits newC = YY else YN
							//alert('New Customer');
							
							
		            	
		            	    if ($rootScope.foundCust == 1 )
		            	           statNew='YY';
		            	    
		            	         else
		            	           statNew = 'YN';	 
		            	
		            		$scope.quote.newCustomer = "1"   
		            		$scope.quote.customer = $scope.customer;	
		            	    $scope.customer.user = $scope.quote.user;
		            	    $scope.quote.contactName = $scope.customer.contactName;
		            	    $scope.quote.contactEmail = $scope.customer.contactEmail;
		            	    $scope.quote.contactPhone = $scope.customer.contactPhone;
		            	    
		            		$http.post(baseUrl + '/quote?sessionId='+$rootScope.idSesion+'&newC='+statNew, $scope.quote ). 
		            		success(function(response){
		
		                   		$scope.auxquote= response; 
		                   		$scope.auxquote2= response.data; 
		                   		$scope.quote = response;
		                   		console.info('Quote sucesfull Added 1 : ' + $scope.auxquote2.id);	
		                   		//console.info('Quote sucesfull Added 2  : ' + JSON.stringify(response));
				    			    
				      	    return $http.get(baseUrl + '/quDetail?id='+$scope.auxquote2.id)
				    		}).then(function(response){
		
				    		    		   $scope.quoDet = response.data;
				    		    		   $rootScope.quoActual = $scope.quoDet;
				    		    		   $window.location.assign(baseUrl + '/sealhome#/quote-detail');
				            return $http.post(baseUrl + '/quote/notify',$scope.quote)
				            	
				            }).then(function(response){    		   
				     		         console.log("Email Sent");              
				            	
				     	    }, function errorCallback(response){
				     		      $scope.res = response.data;
				     		      //alert('Error : ' + $scope.res);
					     		    notificationService.notify({
					     		    	title: 'Error Saving Quote',
					     		    	title_escape: false,
					     		    	text: $scope.res.message,
					     		    	text_escape: false,
					     		    	styling: "bootstrap3",
					     		    	type: "notice",
					     		    	icon: true
					     		    });
				     		    //  notificationService.error('Error2 :' + $scope.res.message);
				     		    //  notificationService.error('An error was ocurred');
				             		   
		  	    		    });	            			
		            			
		            			
		            }
		            	else {
		            		
		            		$scope.quote.newCustomer = "0"; 
		            		$scope.customerExp.user = $scope.quote.user;  
		            		$scope.quote.customer = $scope.customerExp;
		            		//$scope.quote.customer = {};
		            		
		            	    $scope.quote.contactName = $scope.customerExp.contactName;
		            	    $scope.quote.contactEmail = $scope.customerExp.contactEmail;
		            	    $scope.quote.contactPhone = $scope.customerExp.contactPhone;
		            		
		            		//$scope.quote.customer.contactName = "ERRRRRR ** PIBE";
		            		
		            		
		            	    		            		
		            		console.log("SAVING CUSTOMER FROM EXP : " + $scope.customerExp.customerName  +  " COMPANY ID :" + $scope.customerExp.idCompany );
		            		//$log.info('Quote Customer ' + JSON.stringify($scope.quote.customer));
		            		//$log.info('Customer Exp ' + JSON.stringify($scope.customerExp));
		            		$log.info('***** QUOTE *** ' + JSON.stringify($scope.quote));
		                   	
		            		if ($scope.quote.customer != null){
		            			
		            			

			            		$http.post(baseUrl + '/quote?sessionId='+$rootScope.idSesion+'&newC=N', $scope.quote ).
					    		success(function(response){
					    			   notificationService.success('Quote Sucessfull Added');	
					      			   $scope.quote= response;
					      			   
					      	    return $http.get(baseUrl + '/quDetail?id='+$scope.quote.id)
					    		}).then(function(response){
			
					    		    		   $scope.quoDet = response.data;
					    		    		   $rootScope.quoActual = $scope.quoDet;
					    		    		   $window.location.assign(baseUrl + '/sealhome#/quote-detail');
					    	    return $http.post(baseUrl + '/quote/notify',$scope.quote)
					    		            	
					            }).then(function(response){    		   
					     		         console.log("Email Sent");      		   
			  	    		    });	 
			            		
		            		}else
		            		{
		            			notificationService.error('Customer in Blank');
		            		}	
		            }    	
        	
        	} //End Valid Items at lest one
	    	
        }); 
      });
	
	  $scope.setfilterEst  = function (index) {
		  
		  $scope.lbFilterEst = $scope.estimators[index].sureName + " " +  $scope.estimators[index].lastName;
		  $scope.tempidEstimator =  $scope.estimators[index].id;
	  }
	  
	  
	  
	
	  $scope.borrar = function (index) {
	
		    $scope.index = index;
		    
		    $scope.modalInstance = $uibModal.open({
		    	ariaLabelledBy: 'modal-title',
		        ariaDescribedBy: 'modal-body',
		    	templateUrl: 'borrar.html',
		        scope: $scope,
		        size: 'sm'	        
		    });
	  }	
	  
	  $scope.setfilterStat = function(){
		  
		  $scope.lbFilerStat = "Filter";
		  
		  
	  
	  }
	  
	  $scope.refreshQuotes = function(){
		  console.log("Entre a refeesh"); 
		  console.log($scope.lbFilerStat);
		  console.log("ROLE ==>  : " , $rootScope.role);
		  console.log("Estimator " + $scope.tempidEstimator);

		  
		  if ($rootScope.role == '1') {
		  
			  if (($scope.lbFilerStat == "Filter") && ($scope.lbFilterEst == "All Estimators")) { 
				  console.log("Option 1 : "); 
				  listfilterquotes($http,$scope,baseUrl,$scope.filterStatusQuote,"0",1);	
			       
			  }
			                                                                
			  if (($scope.lbFilerStat == "Filter") && ($scope.lbFilterEst != "All Estimators")) {
				  console.log("Option 2 : "); 
				  listfilterquotes($http,$scope,baseUrl,$scope.filterStatusQuote,$scope.tempidEstimator,2);
			       
			  }     
		  
		  }
		  
		  
		  if ($rootScope.role == '3') {
			  
			  listfilterquotesEst($http,$scope,baseUrl,$scope.filterStatusQuote,$rootScope.user.username);	
			  
		  }
		  
		  if ($rootScope.role == '2') {
			  console.log("Entre a ROLE SELLER");
			  listfilterquotesSeller($http,$scope,baseUrl,$scope.filterStatusQuote,$rootScope.user.username);	
			  
		  }
		  
		  
	  }
	  
	  
	  
	  
	  
	  $scope.cancel = function(){
		  
		  
		  $scope.item = {};
		  $scope.itemDel = {};
		  $scope.modalInstance.close();
		  
	  }
	  
	  $scope.cancelQuote = function(){
		  
		  
		  $scope.item = {};
		  $scope.itemDel = {};
		  $scope.modalInstance.close();
		  
	  }
	  

	  $scope.cancelQuote = function(index) {    
	    	
	    	//alert("Updating...");
	    	$scope.quotes[index].statQuote = "2"; 
	    	$scope.quotes[index].deletedUser = $rootScope.user;
			$http.put(baseUrl + "/quote/update",$scope.quotes[index]).success(function (data) {      
				//console.log("Updating Mat proccesing Details..."); 
				//listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
				//$scope.matdetail = {};
				//$scope.button    = 0;
				notificationService.success('Quote has been Canceled');	
				$('#quotedetails1').modal('hide');
				$scope.modalInstance.close();
	    	}, function errorCallback(response){
	  	      $scope.res = response.data;
	  	      //alert('Error : ' + $scope.res);
	  		     notificationService.notify({
	  		    	title: 'Error',
	  		    	title_escape: false,
	  		    	text: $scope.res.message,
	  		    	text_escape: false,
	  		    	styling: "bootstrap3",
	  		    	type: "notice",
	  		    	icon: true
	  		    });	       				
				
			});
	    	
	    

	 }  	  
	  
	  
	 $scope.findOneQuote = function(){
	  		
		 $http.get(baseUrl + '/quDetail?id='+$rootScope.qOneQuoteId) 
	  		.success(function(result){
	  			{
	  				$rootScope.proyDet = result;
	  				$rootScope.proyActual = $rootScope.proyDet;
	       		    $scope.quoDet = result;
	  	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
	       		    $scope.currQuote = $scope.quoDet;
	  	    	    $rootScope.quoActual = $scope.quoDet;
	  	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
	  	    	    //alert($rootScope.quoActual);
	  	    	    
	  				$window.location.assign(baseUrl + '/sealhome#/quote-detail');
	  				
	  	        }
	  	 });			 	
	 } 
	  
	 
	   $scope.findPartExpandableEdit = function() {
			 
  	     findpartExpEdit($http,$scope,baseUrl, $scope.itemDel.part.partNumber);
			 console.log('PART EXp : ',  $scope.smipartExp); 
			 
	  }
	   
	   $scope.onChangePart = function() { 

	 	    $scope.itemDel.part.partDesc   = '';
			$scope.itemDel.part.partCost  = '';  
 
	   }
	 
	 
	  
	  $scope.editTempItem = function(index){
		  
		  $scope.itemDel = $scope.itemsTemp[index];
		  
		  console.log(JSON.stringify($scope.itemDel.drawingAva));
		  
		  if ($scope.itemDel.drawingAva == "1")
			  $scope.itemDel.drawingAva = true;
		  
		  if ($scope.itemDel.newPart == "1")
			  $scope.itemDel.newPart = true
			  
		  if ($scope.itemDel.sampleAva == "1")
			  $scope.itemDel.sampleAva = true;
		  
		  if ($scope.itemDel.concernsCurr == "1")
			  $scope.itemDel.concernsCurr = true
			  
		  if ($scope.itemDel.cadfileAva == "1")
			  $scope.itemDel.cadfileAva = true
			  
		  if ($scope.itemDel.packageReq == "1")
			  $scope.itemDel.packageReq = true
			  
		   $scope.date_lead = new Date($scope.itemDel.rfqdueDate);	  
			  
			  
		  
		  
		  
	  }
	  
	  $scope.updateTempItem = function(){
		  
		  $scope.valid = true;
		  
		  $scope.itemDel.rfqdueDate = $scope.date_lead;
		  
		  if ($scope.itemDel.drawingAva == true)
			  	  $scope.itemDel.drawingAva = "1"
			  else
				  $scope.itemDel.drawingAva = "0"  
		  
		  if ($scope.itemDel.newPart == true)
			      $scope.itemDel.newPart = "1"
			  else
				  $scope.itemDel.newPart = "0"
			  
		  if ($scope.itemDel.sampleAva == true)
			  	  $scope.itemDel.sampleAva = "1";
		  	  else
		  		  $scope.itemDel.sampleAva = "0";
		  
		  if ($scope.itemDel.concernsCurr == true)
			      $scope.itemDel.concernsCurr = "1"
			  else
				  $scope.itemDel.concernsCurr = "0"
			  
		  if ($scope.itemDel.cadfileAva == true)
			      $scope.itemDel.cadfileAva = "1"
			  else
				  $scope.itemDel.cadfileAva = "0"		  
			  
		  if ($scope.itemDel.packageReq == true)
			      $scope.itemDel.packageReq = "1"
			  else
				  $scope.itemDel.packageReq = "0"
					  
					  
				        if (($scope.itemDel.itemType == null) || ($scope.itemDel.industry == null) || ($scope.itemDel.qitemType == null) 
				                || ($scope.itemDel.anualUsage == "") || ($scope.itemDel.quantity == "" ) || ($scope.itemDel.targetPrice == "") 
				                || ($scope.itemDel.suggestMat == null) || ($scope.itemDel.suggestVend == null) 
				                || ($scope.itemDel.fob == "") || ($scope.itemDel.rfqdueDate == "") || $scope.itemDel.measureUnit == null){
				             	$scope.valid = false;
				             	notificationService.error('You Must Complete all obligatory fields');
				             }
				             
				             if (($scope.itemDel.itemType.id == 1) && ($scope.itemDel.newPartName == "")){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field New Part Name'); 
				             }

				     		if (($scope.itemDel.itemType.id == 4) && ($scope.partExp == null)){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field Part Name'); 
				     		}
				     		
				     		
				     		if ($scope.itemDel.itemType.id == 4) {
				     			
				     			//$scope.itemDel.part = $scope.partExp;
				     			console.log('PART FROM EXP TO SAVE : ' + $scope.partExp.partCode);
				     			console.log('PART ARRAY : ',  $scope.itemDel.part); 
				     			
				     			if ($scope.itemDel.part.partDesc == ' ') {
				     				console.log('PART ARRAY : ',  $scope.itemDel.part); 
				     				$scope.valid = false;
				     			}	
				     		}
				     		
				     		if ($scope.valid) { 
				     			
				     			
				    			$http.put(baseUrl + '/tempItem/update', $scope.itemDel).
				    			success(function(response){
				    				    notificationService.success('Item Sucessfully updated');
				    				    listtempitems($http,$scope,baseUrl,$rootScope.user.username,$rootScope.idSesion);

				    				});
				    			//$scope.modalInstance.close();		     			
				     			
				     		} 
				     		
				     	
				     		
		  
		  
		  
	  }
	  
	  $scope.initItem = function(){
		  $scope.item = {};
		  $scope.itemDel = {};
		  
	  }
	  
	  
	  
	  $scope.clickCopytoPaste = function(index){
		  
		  alert("Click Copy Paste"); 
		  $rootScope.currCopyItem = $scope.itemsquote[index];
		  console.log('Actual Copy Item', $rootScope.currCopyItem); 
		  
	  }
	  
	  
	  
	  
	  $scope.copyTempItem = function(){
		  
		  $scope.valid = true;
		  
		  
		  //alert("Copy Item,");
		  
		  $scope.itemDel.rfqdueDate = $scope.date_lead;
		  
		  if ($scope.itemDel.drawingAva == true)
			  	  $scope.itemDel.drawingAva = "1"
			  else
				  $scope.itemDel.drawingAva = "0"  
		  
		  if ($scope.itemDel.newPart == true)
			      $scope.itemDel.newPart = "1"
			  else
				  $scope.itemDel.newPart = "0"
			  
		  if ($scope.itemDel.sampleAva == true)
			  	  $scope.itemDel.sampleAva = "1";
		  	  else
		  		  $scope.itemDel.sampleAva = "0";
		  
		  if ($scope.itemDel.concernsCurr == true)
			      $scope.itemDel.concernsCurr = "1"
			  else
				  $scope.itemDel.concernsCurr = "0"
			  
		  if ($scope.itemDel.cadfileAva == true)
			      $scope.itemDel.cadfileAva = "1"
			  else
				  $scope.itemDel.cadfileAva = "0"		  
			  
		  if ($scope.itemDel.packageReq == true)
			      $scope.itemDel.packageReq = "1"
			  else
				  $scope.itemDel.packageReq = "0"
					  
					  
				        if (($scope.itemDel.itemType == null) || ($scope.itemDel.industry == null) || ($scope.itemDel.qitemType == null) 
				                || ($scope.itemDel.anualUsage == "") || ($scope.itemDel.quantity == "" ) || ($scope.itemDel.targetPrice == "") 
				                || ($scope.itemDel.suggestMat == null) || ($scope.itemDel.suggestVend == null) 
				                || ($scope.itemDel.fob == "") || ($scope.itemDel.rfqdueDate == "") || $scope.itemDel.measureUnit == null){
				             	$scope.valid = false;
				             	notificationService.error('You Must Complete all obligatory fields');
				             }
				             
				             if (($scope.itemDel.itemType.id == 1) && ($scope.itemDel.newPartName == "")){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field New Part Name'); 
				             }

				     		if (($scope.itemDel.itemType.id == 4) && ($scope.itemDel.part == null)){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field Part Name'); 
				     		}
				     		
				     		
				     		if ($scope.itemDel.itemType.id == 4) {
				     			
				     			//$scope.itemDel.part = $scope.partExp;
				     			
				     			if ($scope.itemDel.part.partDesc == '') {
				     				console.log('PART ARRAY : ',  $scope.itemDel.part);
				     				notificationService.error('Please fill field Part Name'); 
				     				$scope.valid = false;
				     			}	
				     		}
				     		
				     		if ($scope.valid) { 
				     			
				     			
						    	$scope.item.sessionId =  $rootScope.idSesion; 
						    	$scope.item.users = $rootScope.user;
							    	$http.post(baseUrl + '/copyitem', $scope.itemDel ).
									success(function(response){
										notificationService.success('Item Sucessfull Copied');	
										listtempitems($http,$scope,baseUrl,$rootScope.user.username,$rootScope.idSesion);  
					
										
										
							   });	
				    			//$scope.modalInstance.close();		     			
				     			
				     		}
				     		
				     	
				     		
		  
		  
		  
	  }
		  
	
	
	  $scope.removeItem = function(index){    	
			
			
			$http.put(baseUrl + '/tempItem/delete', $scope.itemsTemp[index]).
			success(function(response){
				    notificationService.success('Item Sucessfully removed');
				    $scope.totItems = $scope.totItems - 1;
				    listtempitems($http,$scope,baseUrl,$rootScope.user.username,$rootScope.idSesion);

				});
			$scope.modalInstance.close();
			
	  }	
	
	  
      $scope.addTempItem = function() {
        
        $scope.valid = true;
        

        if (($scope.item.itemType == null) || ($scope.item.industry == null) || ($scope.item.qitemType == null) 
           || ($scope.item.anualUsage == "") || ($scope.item.quantity == "" ) || ($scope.item.targetPrice == "") 
           || ($scope.item.suggestMat == null) || ($scope.item.suggestVend == null) 
           || ($scope.item.fob == "") || ($scope.item.rfqdueDate == "") || $scope.item.measureUnit == null){
        	$scope.valid = false;
        	notificationService.error('You Must Complete all obligatory fields');
        }
        
        if (($scope.item.itemType.id == 1) && ($scope.item.newPartName == "")){
			$scope.valid = false;
			notificationService.error('Please fill field New Part Name'); 
        }

		if (($scope.item.itemType.id == 4) && ($scope.partExp == null)){
			$scope.valid = false;
			notificationService.error('Please fill field Part Name'); 
		}
		
		
		if ($scope.item.itemType.id == 4) {
			
			$scope.item.part = $scope.partExp;
			console.log('PART FROM EXP TO SAVE : ' + $scope.partExp.partCode)
		}

        
    	if ($scope.valid) { 
		    	$scope.item.sessionId =  $rootScope.idSesion;
		    	$scope.item.users = $rootScope.user;
			    	$http.post(baseUrl + '/tempitem', $scope.item ).
					success(function(response){
						notificationService.success('Item Sucessfull Added');	
						listtempitems($http,$scope,baseUrl,$rootScope.user.username,$rootScope.idSesion);
						$scope.item = {};
						$scope.totItems = $scope.totItems + 1;
						$scope.item.drawingAva = '0';
						$scope.item.newPart = '0';
						$scope.item.sampleAva = '0';
						$scope.item.concernsCurr = '0';
						$scope.item.cadfileAva = '0';
						$scope.item.packageReq = '0';
						$scope.item.partKissCut = '0';						
						
						
			   });	
    	} 	    	
    	
    	
     }
      
      $scope.findListQuote = function(index) { 
	         
      	
  		$http.get(baseUrl + '/quDetail?id='+$scope.quotes[index].id)
  		.success(function(result){
  			{
  				$rootScope.proyDet = result;
  				$rootScope.proyActual = $rootScope.proyDet; 
       		    $scope.quoDet = result;
  	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
       		    $scope.currQuote = $scope.quoDet;
  	    	    $rootScope.quoActual = $scope.quoDet;
  	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
  	    	    //alert($rootScope.quoActual);
  	    	    
  				//$window.location.assign(baseUrl + '/sealhome#/quote-detail');		
  				$window.open(baseUrl + '/sealhome#/quotenote-detail?quoteID='+$scope.quotes[index].id, '_blank');
  	        }
  		});		    	
      		    	
       }   
      
      
      
      $scope.findListSharedQuote = function(index) { 
	         
        	
    		$http.get(baseUrl + '/quDetail?id='+$scope.sharedQuotes[index].id)
    		.success(function(result){
    			{
    				$rootScope.proyDet = result;
    				$rootScope.proyActual = $rootScope.proyDet; 
         		    $scope.quoDet = result;
    	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
         		    $scope.currQuote = $scope.quoDet;
    	    	    $rootScope.quoActual = $scope.quoDet;
    	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
    	    	    //alert($rootScope.quoActual);
    	    	    
    				$window.location.assign(baseUrl + '/sealhome#/quote-detail');				
    	        }
    		});		    	
    		
         } 
      
      
      $scope.findListQuoteAll = function(index) {
          
			
	  		$http.get(baseUrl + '/quDetail?id='+$scope.quotesAll[index].id)
	  		.success(function(result){
	  			{
	  				$rootScope.proyDet = result;
	  				$rootScope.proyActual = $rootScope.proyDet; 
	       		    $scope.quoDet = result;
	  	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
	       		    $scope.currQuote = $scope.quoDet;
	  	    	    $rootScope.quoActual = $scope.quoDet;
	  	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
	  	    	    //alert($rootScope.quoActual);
	  	    	    
	  				//$window.location.assign(baseUrl + '/sealhome#/quote-detail', '_blank');	
	  				$window.open(baseUrl + '/sealhome#/quotenote-detail?quoteID='+$scope.quotesAll[index].id, '_blank');
	  	        }
	  		});				
			

     }   
      
      
      $scope.sendEmail = function(index) {
    	  
    	  $http.post(baseUrl + '/quote/notify',$scope.quotes[index])
    	  .success(function(result){
      	         console.log("Email Sent");
          });
    	  
      }
      
      
	   $scope.addQuoteNote = function(){ 
	    	
			//alert("Valor : " + $scope.LeadActivity.descActivity);
			if ($scope.txtNote != " "){
			var note = $scope.txtNote;
				
				$http.post(baseUrl + '/quote/note/add?quoteId='+$scope.qId+'&message='+$scope.txtNote+'&typeNote=F', $rootScope.user ).
				success(function(response){
					notificationService.success('Follow up Sucessfull Added');	
					listquotenotesbytype($http,$scope,$rootScope, baseUrl,$scope.qId,"F");
					//alert("Follow up added");
					//$rootScope.notesq = $scope.notesquote; 
					console.log('ADDING NOW SIZE NEW IS : ' + $rootScope.notesq.length);
					console.log('LOCAL SIZE NEW IS : ' + $rootScope.notesq.length);
					
					return 	$http.post(baseUrl + '/quote/notifyMessage?message='+note+'&idQuote='+$scope.qId+'&username='+ $rootScope.user.username) 
		     	}).then(function(result){
	    		     console.log("Email Enviado"); 
	    		     $scope.txtNote = ""; 
	    		     
	    	    }, function errorCallback(response){
	    		      $scope.res = response.data;
	    		      //alert('Error : ' + $scope.res);
		     		     notificationService.notify({
		     		    	title: 'Address',
		     		    	title_escape: false,
		     		    	text: $scope.res.message,
		     		    	text_escape: false,
		     		    	styling: "bootstrap3",
		     		    	type: "notice",
		     		    	icon: true
		     		    }); 
		 		});	
			
			}
			
		}
	   
		$scope.viewCustomerQuote = function(index){
 			
	  		   
	  		   $rootScope.idCustomer =  $scope.customersSeller[index].idCustomer;
	  		   console.log("ID CUST  " , $scope.customersSeller[index].idCustomer);
	  		  // alert("Profile : " +  $rootScope.actSeller.sureName);	
	 		   $window.location.assign(baseUrl + '/sealhome#/vCustomerQuotes');
	 		   
	   
	 		   
	 		
	   }
	
			
			
			
		      /*     Autocomplete Customer Expandable      */
		      
			 
		
	
}]);


app.controller("partExpCustController", ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log) {

    /*     Autocomplete Customer Expandable      */
    
	 
	var self = this; 
	self.simulateQuery = false; 
	self.isDisabled    = false;
	self.repos         = loadPartsCustExp();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange; 
	self.searchTextChange   = searchTextChange;
	$rootScope.partExp = {};
	
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
		}
		function selectedItemChange(item) {
		  $log.info('Item changed to ' + JSON.stringify(item));
		  console.log(' 0.1 - FILTER APRT CUSTOMER : ' + $rootScope.idCustomer);
		  $rootScope.partExp.partDesc = item.display;
		  $rootScope.partExp.partCode = item.sid;
		  $rootScope.partExp.partNumber = item.sid;
		  $rootScope.partExp.partCost = item.cost;
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadPartsCustExp() {
			
			console.log(' 1 - FILTER APRT CUSTOMER : ' + $rootScope.idCustomer);
		    var url = "./list-part-cust-exp?custId="+$rootScope.idCustomer;
		    var sourcetransfer = [];
		    $scope.sourceTransfer = {};
		    $http.get(url)
		        .success(function(data, status, headers, config) { 
		            $scope.sourceTransfer = data; 
		            console.log("2 - PARTS SIZES : " + $scope.sourceTransfer.length) 
		            
		            
		    	    for (var j=0; j < $scope.sourceTransfer.length; j++){   
		    	    	//console.log("ITEM : " + $scope.sourceTransfer[j].customerName);
		    	    	var city = {display : $scope.sourceTransfer[j].partDesc, value : $scope.sourceTransfer[j].partDesc.toLowerCase() , sid : $scope.sourceTransfer[j].partId,  type : $scope.sourceTransfer[j].partType , um :  $scope.sourceTransfer[j].partUm, cost :  $scope.sourceTransfer[j].unitCost, custpartId : $scope.sourceTransfer[j].custPartId};
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


app.controller("partExpControllerEdit", ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log) {

    /*     Autocomplete Customer Expandable      */
    
	 
	var self = this; 
	self.simulateQuery = false; 
	self.isDisabled    = false;
	self.repos         = loadPartsExp();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange;
	self.searchTextChange   = searchTextChange;
	$rootScope.partExp = {};
	
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
		}
		function selectedItemChange(item) {
		  $log.info('Item changed to ' + JSON.stringify(item));
		  
		  $rootScope.partExp.partDesc = item.display;
		  $rootScope.partExp.partCode = item.sid;
		  $rootScope.partExp.partNumber = item.sid;
		  $rootScope.partExp.partCost = item.cost;
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadPartsExp() {
		    var url = "./list-part-exp-type?type=M";
		    var sourcetransfer = [];
		    $scope.sourceTransfer = {};
		    $http.get(url)
		        .success(function(data, status, headers, config) { 
		            $scope.sourceTransfer = data; 
		            console.log("PARTS SIZES : " + $scope.sourceTransfer.length) 
		            
		            
		    	    for (var j=0; j < $scope.sourceTransfer.length; j++){   
		    	    	//console.log("ITEM : " + $scope.sourceTransfer[j].customerName);
		    	    	var city = {display : $scope.sourceTransfer[j].partDesc, value : $scope.sourceTransfer[j].partDesc.toLowerCase() , sid : $scope.sourceTransfer[j].partId,  type : $scope.sourceTransfer[j].partType , um :  $scope.sourceTransfer[j].partUm, cost :  $scope.sourceTransfer[j].unitCost };
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


app.controller("partExpController", ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log) {

    /*     Autocomplete Customer Expandable      */
    
	 
	var self = this;
	self.simulateQuery = false;
	self.isDisabled    = false;
	self.repos         = loadPartsExp();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange;
	self.searchTextChange   = searchTextChange;
	$rootScope.partExp = {};
	
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
		}
		function selectedItemChange(item) {
		  $log.info('Item changed to ' + JSON.stringify(item));
		  
		  $rootScope.partExp.partDesc = item.display;
		  $rootScope.partExp.partCode = item.sid;
		  $rootScope.partExp.partNumber = item.sid;
		  $rootScope.partExp.partCost = item.cost;
		  
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadPartsExp() {
		    var url = "./list-part-exp-type?type=M";
		    var sourcetransfer = [];
		    $scope.sourceTransfer = {};
		    $http.get(url)
		        .success(function(data, status, headers, config) { 
		            $scope.sourceTransfer = data; 
		            console.log("Size Source : " + $scope.sourceTransfer.length) 
		            
		            
		    	    for (var j=0; j < $scope.sourceTransfer.length; j++){   
		    	    	//console.log("ITEM : " + $scope.sourceTransfer[j].customerName);
		    	    	var city = {display : $scope.sourceTransfer[j].partDesc, value : $scope.sourceTransfer[j].partDesc.toLowerCase() , sid : $scope.sourceTransfer[j].partId,  type : $scope.sourceTransfer[j].partType , part_um :  $scope.sourceTransfer[j].partUm, cost :  $scope.sourceTransfer[j].unitCost };
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

app.controller("matExpController", ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log) {

    /*     Autocomplete Customer Expandable      */
    
	 
	var self = this;
	self.simulateQuery = false;
	self.isDisabled    = false;
	self.repos         = loadPartsExp();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange;
	self.searchTextChange   = searchTextChange;
	$rootScope.matExp = {};
	
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
		}
		function selectedItemChange(item) {
		  $log.info('Item changed to ' + JSON.stringify(item));
		  
		  $rootScope.matExp.descMaterial = item.display;
		  $rootScope.matExp.partNumber = item.sid;
		  $rootScope.matExp.unitCost = item.cost;
		  
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadPartsExp() {
		    var url = "./list-part-exp-type?type=B";
		    var sourcetransfer = [];
		    $scope.sourceTransfer = {};   
		    $http.get(url)
		        .success(function(data, status, headers, config) { 
		            $scope.sourceTransfer = data; 
		            console.log("Size Source : " + $scope.sourceTransfer.length) 
		            
		            
		    	    for (var j=0; j < $scope.sourceTransfer.length; j++){   
		    	    	//console.log("ITEM : " + $scope.sourceTransfer[j].customerName);
		    	    	var city = {display : $scope.sourceTransfer[j].partDesc, value : $scope.sourceTransfer[j].partDesc.toLowerCase() , sid : $scope.sourceTransfer[j].partId,  type : $scope.sourceTransfer[j].partType , part_um :  $scope.sourceTransfer[j].partUm, cost :  $scope.sourceTransfer[j].unitCost };
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


app.controller("customerExpController", ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log) {

    /*     Autocomplete Customer Expandable      */
    
	 
	var self = this;
	self.simulateQuery = false;
	self.isDisabled    = false;
	self.repos         = loadCustomersExp();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange;
	self.searchTextChange   = searchTextChange;
	$rootScope.customerExp = {};
	$rootScope.idCustomer = '';
	
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
		}
		function selectedItemChange(item) {
		  $log.info('Item changed to ' + JSON.stringify(item));
		  
		  $rootScope.customerExp.customerName = item.display;
		  $rootScope.customerExp.contactPhone = item.phone;
		  $log.info('Item Contact Phone Exp changed to ' +  $rootScope.customerExp.contactPhone );
		  $rootScope.customerExp.contactName = item.contact;
		  $rootScope.customerExp.faxNo = item.fax;
		  $rootScope.customerExp.contactPhone = item.phone;
		  $rootScope.customerExp.idCompany = item.sid;
		  $rootScope.customerExp.termsDesc = item.termsDesc;
		  $rootScope.customerExp.descSales = item.sales;
		  $rootScope.idCustomer =  item.sid;
		  console.log("****** ACTUAL CUSTOMER ******" , $rootScope.idCustomer);
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadCustomersExp() {
		    var url = "./list-customers-exp";
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
		    	    	var city = {display : $scope.sourceTransfer[j].customerName, value : $scope.sourceTransfer[j].customerName.toLowerCase() , sid : $scope.sourceTransfer[j].customerId,  contact : $scope.sourceTransfer[j].soContact , phone :  $scope.sourceTransfer[j].sophoneNo, location :  $scope.sourceTransfer[j].city + ' ,' + $scope.sourceTransfer[j].state , fax : $scope.sourceTransfer[j].faxNo, termsDesc :  $scope.sourceTransfer[j].termsDesc, sales : $scope.sourceTransfer[j].descSales };
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


app.controller("quoteListController", ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log) {

    /*     Autocomplete Customer Expandable      */
    
	 
	var self = this;
	var baseUrl = ".";
	self.simulateQuery = false; 
	self.isDisabled    = false;
	self.repos         = loadfilterQuotes();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange;
	self.searchTextChange   = searchTextChange;
	  $rootScope.qSelected    = '';
	  $rootScope.qSelectedCus = '';
	  $rootScope.qSales  = '';
	  $rootScope.qSw  = '0';
	  $rootScope.qContact = ''; 
	  $rootScope.qPhone =    '';
	  $rootScope.id =    '';
	  

	  //$rootScope.notesq = {};
	
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
		}
		function selectedItemChange(qu) {
		  $log.info('Quote changed to ' + JSON.stringify(qu));
		  if (qu != null){
			  $rootScope.qOneQuoteId = qu.idQuote;
			  $rootScope.qSelected    = qu.display;
			  $rootScope.qSelectedCus = qu.sid
			  $rootScope.qSales  = qu.sales
			  $rootScope.qSw  = '1';
			  $rootScope.qContact = qu.contact; 
			  $rootScope.qPhone =    qu.phone;
			  $rootScope.qId   = qu.idQuote; 
			  $rootScope.currQuote =  qu.idQuote;
			  console.log("ID QUOTE : " +  qu.idQuote );
			  if (qu.idQuote != null){
					$scope.notesq = [];
					$http.get(baseUrl + "/list-quotesnotesbytype?id="+qu.idQuote+"&type=F").success(function (data) {      
						$rootScope.notesq = data;   
					    console.log("ROOT NOTES : " + $rootScope.notesq.length);
				    	//iniciar_tabla('#tbItemsquote');
				    	
	
					});	
	
			  }	   
		  }else{
			  $rootScope.notesq = {}; 
			  console.log("NOTES are becoming in null");
			  $rootScope.qSw  = '0';
		      }
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadfilterQuotes() {
		   // var url = "./list-quotes-status?stat=5";
			var url = "./list-quotes";
			
		    var sourcetransfer = [];
		    $scope.sourceTransfer = {};
		    $http.get(url)
		        .success(function(data, status, headers, config) { 
		            $scope.sourceTransfer = data; 
		            console.log("TOTAL QUOTES : " + $scope.sourceTransfer.length) 
		            
		            
		    	    for (var j=0; j < $scope.sourceTransfer.length; j++){   
		    	    	//console.log("ITEM : " + $scope.sourceTransfer[j].customerName);
		    	   // 	var city = {display : $scope.sourceTransfer[j].partDesc, value : $scope.sourceTransfer[j].partDesc.toLowerCase() , sid : $scope.sourceTransfer[j].partId,  type : $scope.sourceTransfer[j].partType , um :  $scope.sourceTransfer[j].partUm, cost :  $scope.sourceTransfer[j].unitCost };
		    	    	if  ($scope.sourceTransfer[j].customer != null){
		    	    		var city = {display : $scope.sourceTransfer[j].nroRfq, value : $scope.sourceTransfer[j].nroRfq.toLowerCase() , sid : $scope.sourceTransfer[j].customer.customerName, sales : $scope.sourceTransfer[j].seller.sureName + " " + $scope.sourceTransfer[j].seller.lastName , contact : $scope.sourceTransfer[j].customer.contactName, phone : $scope.sourceTransfer[j].customer.cellPhone, idQuote : $scope.sourceTransfer[j].id };
		    	    	}	
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
		  return function filterFn(qu) {
		    return (qu.value.indexOf(lowercaseQuery) === 0);
		  };
		}
  
  
  
  /*    END Autocomplete Customer ex[andable     */
	
}]);


app.controller("newCustomerController", ['$scope', '$http', '$location','$window','$filter','$rootScope','$timeout', '$q', '$log',
    function($scope, $http, $location,$window,$filter,$rootScope,$timeout, $q, $log) {

	var self = this;
	self.simulateQuery = false;
	self.isDisabled    = false;
	self.repos         = loadCustomers();
	self.querySearch   = querySearch;
	self.selectedItemChange = selectedItemChange;
	self.searchTextChange  = searchTextChange;
	$rootScope.customer = {};
	$rootScope.foundCust = 0;
	
	
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
		    
		  $log.info("Root Customer : " + $rootScope.customer.contactName );
		  $rootScope.foundCust = 1;
		}
		/**
		 * Build `components` list of key/value pairs */

	
		
		function loadCustomers() {
		    var url = "./list-customers";
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
		    	    	var city = {display : $scope.sourceTransfer[j].customerName, value : $scope.sourceTransfer[j].customerName.toLowerCase() , sid : $scope.sourceTransfer[j].id,  contact : $scope.sourceTransfer[j].contactName , phone : $scope.sourceTransfer[j].contactPhone, webSite : $scope.sourceTransfer[j].webSite, email : $scope.sourceTransfer[j].contactEmail , tesla : $scope.sourceTransfer[j].teslaSubContrator, origin : $scope.sourceTransfer[j].cutomerOrigin };
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


app.controller('detQuotesController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','Upload', '$timeout','$uibModal',
	  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,Upload, $timeout,$uibModal) {

	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	//alert("Detail");  
	$scope.currQuote = {};
	console.log('##### QUOTE ACTUAL DETAIl #######', $rootScope.quoActual); 
	$scope.currQuote = $rootScope.quoActual;
	listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);
	listquotenotes($http,$scope,baseUrl,$rootScope.quoActual.id);
	listar_files($http,$scope,baseUrl,$rootScope.quoActual.id);
	listincoterms($http,$scope,baseUrl);
	listteslamodels($http,$scope,baseUrl);
	listTerms($http,$scope,baseUrl);
	listSellers($http,$scope,baseUrl);
	listEstimators($http,$scope,baseUrl);
	listReferrals($http,$scope,baseUrl);
	listitemsType($http,$scope,baseUrl);
	listIndustries($http,$scope,baseUrl);
	listmeasureunits($http,$scope,baseUrl);	
	listqitems($http,$scope,baseUrl);
	listitemsstatus($http,$scope,baseUrl,"2");
	listQuoteStatus($http,$scope,baseUrl); 
	listAllsubStatus($http,$scope,baseUrl,$scope.currQuote.statQuote);
	$scope.swPrint = '1';
	//listPartsExp($http,$scope,baseUrl);
	
	
	
	$scope.itemActivity  = {};
	$scope.itemDel = {};
	$scope.partialDownloadLink  =  "sealhome#/download?filename=";
	$scope.partExp = {};
	//$scope.swPrint = '0'; 

	
	
	$scope.clickRequote = function (index) {
		
		console.log("CLICK REUQOTE"); 
	    $scope.index = index; 
	    
	    $scope.modalInstance = $uibModal.open({
	    	ariaLabelledBy: 'modal-title',
	        ariaDescribedBy: 'modal-body',
	    	templateUrl: 'requote.html',
	        scope: $scope,
	        size: 'sm'	        
	    });
	}
	
	$scope.generateRequote = function() {
		
		console.log("GENERATE REQUOTE", $rootScope.user);
		$rootScope.quoActual.requoteUser = $rootScope.user;
		
		$http.post(baseUrl + '/quote/requote', $rootScope.quoActual ).
		success(function(response){
			notificationService.success('Requote was Sucessfull created'); 
			$scope.currQuote = {};
			$scope.currQuote = response;
			listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
			listquotenotes($http,$scope,baseUrl,$scope.currQuote.id);
			listar_files($http,$scope,baseUrl,$scope.currQuote.id);
			$rootScope.quoActual = $scope.currQuote;
			$scope.modalInstance.close();

 		});	
	
	} 
	
	$scope.clickQuoteStatus = function() {
		
		listAllsubStatus($http,$scope,baseUrl,$scope.currQuote.statQuote)
	}
	
	
	$scope.saveItemAct = function(){
    	
		//alert("Valor : " + $scope.LeadActivity.descActivity);
		if ($scope.itemActivity.descActivity != " "){
		    
			$scope.itemActivity.user = $rootScope.user;
			$scope.itemActivity.username = $rootScope.userName;
			
			$http.post(baseUrl + '/item/newActivity?idItem='+$scope.itemActual, $scope.itemActivity ).
			success(function(response){
				notificationService.success('Item Activity Sucessfull Added');	
				listitemact($http,$scope,baseUrl,$scope.itemActual);
				$scope.showFields = "0";
				$scope.itemActivity.descActivity = " ";
				listquotenotes($http,$scope,baseUrl,$rootScope.quoActual.id);
	 		});	
		
		}
		
	}
	
	$scope.removeItemAct = function(index){    	
		

		$http.put(baseUrl + "/item/removeActivity?idAct="+$scope.itemActivitys[index].idItemActivity + "&stat=1&user="+$rootScope.userName).
		success(function(response){
			    
			 if (response == "1"){    
			    //notificationService.success('Lead Activity Sucessfully Removed');
				 listitemact($http,$scope,baseUrl,$scope.itemActual);

			 }else
				 notificationService.error('Error : Just the Owner can Delete this Lead'); 

			});
		
		
	}		
	
	$scope.buttLeadAct = function(index){
		 $scope.showFields = "1";
	}
	
	
	$scope.clickeditqDet = function(index){
		
		$scope.quoteAux =  $scope.currQuote;
		
	}
	
	$scope.canceleditDet = function(index){
		
		
		$scope.currQuote =  $scope.quoteAux;
	}
	
	
	$scope.clickCopytoPaste = function(index){
		  
		  alert("Click Copy Paste");
		  $rootScope.currCopyItem = $scope.itemsquote[index];
		  console.log('Actual Copy Item', $rootScope.currCopyItem); 
		  
	  
	}
	

	$scope.printsheetCost = function() {
		  
		  printSheetCost($http,$rootScope, $scope,baseUrl,$scope.itemActual)
	}
	
	$scope.clickitemActivity = function(index){
		
		$scope.indiceAct = index;
		//alert($scope.itemsquote[index].idItem);
		listitemact($http,$scope,baseUrl,$scope.itemsquote[index].idItem);
		$scope.showFields = "0";
		$scope.itemActual = $scope.itemsquote[index].idItem;   // Ojo chequear esto
		//listquotenotes($http,$scope,baseUrl,2);
		$scope.currItem = $scope.itemsquote[index];

		
		/*$scope.leadAct = $scope.leads[index].idLead;
		
		$scope.actL = $scope.leads[index];
		$scope.nameCustomer = $scope.actL.contactName;
		$scope.LeadActivity.descActivity = " ";*/
		
	}
	
	 $scope.uploadFiles = function(file, errFiles) {
		    $scope.added = 0;
		    $scope.error = 0;
	        $scope.f = file;
	        $scope.errFile = errFiles && errFiles[0]; 
	       // alert("Entre a Files : ");
	        if (file) {
	            file.upload = Upload.upload({ 
	                url: './upload' 
	                ,data: {file: file}
	                
	            });
             
	            $scope.prFiles = {};
	            $scope.activitysPr = {};
	            file.upload.then(function (response) {
	                $timeout(function () {
	                    file.result = response.data;
	                    console.info('ID FILE' + file.result.id );
		   			     $http.put(baseUrl + '/quoteFile/updateFiledetails/?sessionId='+$rootScope.idSesion+'&fileName='+file.name+'&idQuote='+$rootScope.quoActual.id+'&userName='+$rootScope.user.username+'&id='+ file.result.id).
					     success(function(response){
					    	     console.info('Suceess Update File Details.');
					    	     listar_files($http,$scope,baseUrl,$rootScope.quoActual.id);
						 })
	                    //listar_files($http,$scope,baseUrl,$rootScope.quoActual.id);
	                });
	            }, function (response) {
	                if (response.status > 0)
	                    $scope.errorMsg = response.status + ': ' + response.data;
	            }, function (evt) {
	                file.progress = Math.min(100, parseInt(100.0 * 
	                                         evt.loaded / evt.total));
	                
	                
	                if (file.progress > 99){
	                	
;
	                	
	                }
	                	
	                	
	                	
	                	
	                //listar_activitysPr($http,$scope,baseUrl,$scope.currProyect.idProyect);
	                
	            });
	            
	            
	            
	            
	            
	        }   
	 }
	 
     $scope.downloadFile = function(index) {
    	 
    	    
    	    
    	   
			
	        $http.get(baseUrl + '/download?filename='+$scope.prFiles[index].filename+'&id='+$scope.prFiles[index].id,{responseType:'arraybuffer'}).
			//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
			success(function(response){
				
				//alert("Preparing to Donwload");
				$scope.file = response;
				var url = $window.URL || $window.webkitURL;
			    var file = new Blob([response], {type: $scope.prFiles[index].mimeType});
			    var fileURL = URL.createObjectURL(file);
			    
			    $scope.fileUrl = fileURL;
			    //$window.location = fileURL;
			    //alert("File Ope : " + fileURL);
			   // window.open(fileURL, '_blank');
			    //FileSaver.saveAs(myData, 'printrep.pdf');
			    //alert("En Reports http wind Buffer");
		
		});
	        
	}
	
	$scope.clickitemDetails = function(index){
		
		$scope.indiceAct = index;
		$scope.itemActual = $scope.itemsquote[index]
	}
	
		
	$scope.clickitemsheetcost = function(index){
		$scope.itemActual = $scope.itemsquote[index];
		$rootScope.itemActual = $scope.itemActual;
		$window.location.assign(baseUrl + '/sealhome#/item-sheetcost');
	}
	
	
	$scope.printQuote = function() {
		
		$scope.swPrint = '0';
		var filename = '/printQuoteForm'; 
		
		if ($rootScope.quoActual.setorderItems == '1'){
			filename = '/printQuoteFormOrderbyPart'; 
		}
		
		
        $http.get(baseUrl + filename + '?idQuote='+$rootScope.quoActual.id,{responseType:'arraybuffer'}).
		//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
		success(function(response){
			
			
			//alert("Preparing to Print..!");
			$scope.file = response;
		    var file = new Blob([response], {type: 'application/pdf'});
		    var fileURL = URL.createObjectURL(file);
		    
		    var fileLink = document.createElement('a');
		    fileLink.href = fileURL;
		    fileLink.download = 'QUOTE_' + $rootScope.quoActual.nroRfq; 

		 // triggers the click event
		    fileLink.click();
		    
		    var emailHd = [{ type:"ecfg", 
		    		  		version:"1", 
		    		  		email_to : "someone@yoursite.com", 
		    		  		email_cc : ["john@yoursite.com", "jane@yoursite.com"], 
		    		  		email_bcc : ["bob@yoursite.com"], 
		    		  		email_subject : "Requested documents", 
		    		  		email_body : "<html><body>Text</body></html>", 
		    		  		email_attachments : [{ 
		    		  			path : "C:\Users\luiss\Downloads\QUOTE_20-1429.pdf", 
		    		  			mime_type : "application/pdf" }]
		    			 }]
		    var email64 = btoa(JSON.stringify(emailHd));
		    console.log('EMAIL JSON : ', JSON.stringify(emailHd)); 
		    
		    //let email64 = Buffer.from(emailHd).toString("base64");
		    
		    
		    var subject = "Example";
		    var message = "TEsting";
		    var fileDir = "Test.docx"
		    $scope.swPrint = '1';	
		    //$window.location = fileURL;
		    
		    if ($rootScope.user.username != 'joseq') 
		    	window.open(fileURL, '_blank');
		    
		    //window.open("mailto:sanchezluis25@gmail.com" + "?subject=" + subject+"&body="+message+"&ni_email_cfg_base64json="+email64,"_self"); 
		    //FileSaver.saveAs(myData, 'printrep.pdf');
		    //alert("En Reports http wind Buffer");
	
		});
        
	}
	
	
	$scope.printQuoteFOB = function() {
		
		$scope.swPrint = '0';
		var filename = '/printQuoteForm'; 
		
		if ($rootScope.quoActual.setorderItems == '1'){
			filename = '/printQuoteFormOrderbyPart'; 
		}
		
		
        $http.get(baseUrl + filename + '?idQuote='+$rootScope.quoActual.id,{responseType:'arraybuffer'}).
		//$http.post(baseUrl + '/rptsellerLeads',{idSeller : $scope.selFilSeller, fecIni : inicio, fecFin : fin},{responseType:'arraybuffer'}).
		success(function(response){
			
			
			//alert("Preparing to Print..!");
			$scope.file = response;
		    var file = new Blob([response], {type: 'application/pdf'});
		    var fileURL = URL.createObjectURL(file);
		    
		    var fileLink = document.createElement('a');
		    fileLink.href = fileURL;
		    fileLink.download = 'QUOTE_' + $rootScope.quoActual.nroRfq; 

		 // triggers the click event
		    fileLink.click();
		    
		    var emailHd = [{ type:"ecfg", 
		    		  		version:"1", 
		    		  		email_to : "someone@yoursite.com", 
		    		  		email_cc : ["john@yoursite.com", "jane@yoursite.com"], 
		    		  		email_bcc : ["bob@yoursite.com"], 
		    		  		email_subject : "Requested documents", 
		    		  		email_body : "<html><body>Text</body></html>", 
		    		  		email_attachments : [{ 
		    		  			path : "C:\Users\luiss\Downloads\QUOTE_20-1429.pdf", 
		    		  			mime_type : "application/pdf" }]
		    			 }]
		    var email64 = btoa(JSON.stringify(emailHd));
		    console.log('EMAIL JSON : ', JSON.stringify(emailHd)); 
		    
		    //let email64 = Buffer.from(emailHd).toString("base64");
		    
		    
		    var subject = "Example";
		    var message = "TEsting";
		    var fileDir = "Test.docx"
		    $scope.swPrint = '1';	
		    //$window.location = fileURL;
		    
		    if ($rootScope.user.username != 'joseq') 
		    	window.open(fileURL, '_blank');
		    
		    //window.open("mailto:sanchezluis25@gmail.com" + "?subject=" + subject+"&body="+message+"&ni_email_cfg_base64json="+email64,"_self"); 
		    //FileSaver.saveAs(myData, 'printrep.pdf');
		    //alert("En Reports http wind Buffer");
	
		});
        
	}
	
	
	
        
        
    $scope.updateQuote = function() {    
    	
    	//alert("Updating...");
    	
    	$scope.currQuote.lastupdateUser = $rootScope.user;
		$http.put(baseUrl + "/quote/update",$scope.currQuote).success(function (data) {      
			console.log("Updating Mat proccesing Details..."); 
			//listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
			$scope.matdetail = {};
			$scope.button    = 0;
			listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id); 
			notificationService.success('Quote Succesfulled Update');	
			$('#quotedetails1').modal('hide');
    	}, function errorCallback(response){
  	      $scope.res = response.data;
  	      //alert('Error : ' + $scope.res);
  		     notificationService.notify({
  		    	title: 'Error',
  		    	title_escape: false,
  		    	text: $scope.res.message,
  		    	text_escape: false,
  		    	styling: "bootstrap3",
  		    	type: "notice",
  		    	icon: true
  		    });	       				
			
		});
    	
    

	}	
    
    
    $scope.updateQuoteStatus = function() {    
    	
    	//alert("Updating...");
    	//$scope.currQuote.statQuote = 
    	console.log("SUBSTATUS CHANGE ==> " , $scope.currQuote.quotesubStatus)
    	$scope.currQuote.lastupdateUser = $rootScope.user;
		$http.put(baseUrl + "/quote/update",$scope.currQuote).success(function (data) {      
			console.log("Updating Mat proccesing Details..."); 
			//listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
			$scope.matdetail = {};
			$scope.button    = 0;
			notificationService.success('Quote Status Succesfulled change');	
			$('#quotedetails1').modal('hide');
    	}, function errorCallback(response){
  	      $scope.res = response.data;
  	      //alert('Error : ' + $scope.res);
  		     notificationService.notify({
  		    	title: 'Error',
  		    	title_escape: false,
  		    	text: $scope.res.message,
  		    	text_escape: false,
  		    	styling: "bootstrap3",
  		    	type: "notice",
  		    	icon: true
  		    });	       				
			
		});
    	
    

	}    
    
    
	  $scope.borrar = function (index) {
			
		    $scope.index = index;
		  //  console.log('USER', $rootScope.user); 
		  //  console.log('USER EST', $scope.itemsquote[index].quote.estimator); 
		    
		 //   if ($scope.itemsquote[index].itemStatus.action == '1' ){
		    
		    $scope.modalInstance = $uibModal.open({
		    	ariaLabelledBy: 'modal-title',
		        ariaDescribedBy: 'modal-body',
		    	templateUrl: 'borrar.html',
		        scope: $scope,
		        size: 'sm'	        
		    });
		    
		//    }else
		//    	notificationService.notice('You can not delete a item with Status in : ' + $scope.itemsquote[index].itemStatus.itemStatusDesc + ' - Call DB Admin');
		    	
	  }	
	  
	  $scope.cancel = function(){
		  
		  $scope.modalInstance.close();
	  }
	  
	  
	  $scope.cancelUpdate = function(){
		  
		  //alert("CAncel update");
		  //$scope.modalInstance.close(); 
		  $scope.itemDel = {};
		  $scope.partExp = {};  
		  $scope.itemDel.part = {}; 

		  listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);
	  }
	  
	  
	  $scope.updateItemStat = function(){ 
		  
		    
		  $scope.itemActivity = {}; 
			$http.post(baseUrl + "/updateitemStatus?newStat="+$scope.itemDel.itemStatus.id,$scope.itemDel).success(function (data) {      
				console.log("Updating Status to Completed..."); 
				$scope.itemActivity.descActivity = "Status Change to : " +  $scope.itemDel.itemStatus.itemStatusDesc;
				console.log("Status Change to : " +  $scope.itemDel.itemStatus.itemStatusDesc); 
				$scope.itemActivity.user = $rootScope.user;
				$scope.itemActivity.username = $rootScope.userName;
				return $http.post(baseUrl + '/item/newActStatus?idItem='+$scope.itemDel.idItem,$scope.itemActivity) 
		    }).then(function(response){
		    	notificationService.success('Status was Updated');
		    	getQuoteDetails($http,$rootScope, $scope,baseUrl,$rootScope.quoActual.id); 
		    	listquotenotes($http,$scope,baseUrl,$rootScope.quoActual.id);
		    	
		    	if ($scope.itemDel.itemStatus.id == 4) { 
		    		var nota = "Status on Item Number : " +  $scope.itemDel.itemNumber + "-  Quote : " +  $rootScope.quoActual.nroRfq  + " has been Changed to Order Received"
		    		return 	$http.post(baseUrl + '/quote/notifyMessage?message='+nota+'&idQuote='+$rootScope.quoActual.id+'&username='+ $rootScope.user.username)
		    	}
		    }).then(function(response){
		    	console.log("Posible Notify"); 
		    	
	    	}, function errorCallback(response){
	  	      $scope.res = response.data;
	  	      //alert('Error : ' + $scope.res);
	  		     notificationService.notify({
	  		    	title: 'Error',
	  		    	title_escape: false,
	  		    	text: $scope.res.message,
	  		    	text_escape: false,
	  		    	styling: "bootstrap3",
	  		    	type: "notice",
	  		    	icon: true
	  		    });	       				
				
			}); 
		  
		  
	  }
	  
	  $scope.addFeedback = function(){ 
		   console.log("FEEDBACK ACTUAL " +  $scope.noteFollow ); 
		   var nota = $scope.noteFollow;
			//alert("Valor : " + $scope.LeadActivity.descActivity);
			if ($scope.noteFollow != " "){ 
			
				
				$http.post(baseUrl + '/quote/note/add?quoteId='+$scope.currQuote.id+'&message='+$scope.noteFollow+'&typeNote=F', $rootScope.user ).
				success(function(response){
					notificationService.success('Follow up Sucessfull Added');	
					listquotenotes($http,$scope,baseUrl,$scope.currQuote.id); 
					return 	$http.post(baseUrl + '/quote/notifyMessage?message='+nota+'&idQuote='+$scope.currQuote.id+'&username='+ $rootScope.user.username)
		     	}).then(function(result){
	    		     console.log("Email Enviado"); 
	    		     $scope.noteFollow = "";  
	    	    }, function errorCallback(response){
	    		      $scope.res = response.data;
	    		      //alert('Error : ' + $scope.res);
		     		     notificationService.notify({
		     		    	title: 'Address',
		     		    	title_escape: false,
		     		    	text: $scope.res.message,
		     		    	text_escape: false,
		     		    	styling: "bootstrap3",
		     		    	type: "notice",
		     		    	icon: true
		     		    });
	    		    //  notificationService.error('Error2 :' + $scope.res.message);
	    		    //  notificationService.error('An error was ocurred');
	            });		
				$scope.noteFollow = "";  
			
			}
			
	 }


	  $scope.removeItem = function(index){    	
			
		  $scope.itemsquote[index].userDeleted = $rootScope.user;
	  
			
			$http.put(baseUrl + '/item/delete', $scope.itemsquote[index]).
			success(function(response){
				    notificationService.success('Item Sucessfully removed');
				    listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);

				});
			$scope.modalInstance.close();
			
	  }  
	  
	  $scope.clickEditOrd = function(){
		  
		  $scope.editOrd = 'Y';
	  }
	  
	  
	  $scope.editItem = function(index){
		  
		  $scope.ordened = 'N'
		  $scope.editOrd = 'N'	 
		  
		  $scope.itemDel = $scope.itemsquote[index];
		  
		  console.log(JSON.stringify($scope.itemDel));
		  
		  if ($scope.itemDel.drawingAva == "1")
			  $scope.itemDel.drawingAva = true;
		  
		  if ($scope.itemDel.newPart == "1")
			  $scope.itemDel.newPart = true
			  
		  if ($scope.itemDel.sampleAva == "1")
			  $scope.itemDel.sampleAva = true;
		  
		  if ($scope.itemDel.concernsCurr == "1") 
			  $scope.itemDel.concernsCurr = true
			  
		  if ($scope.itemDel.cadfileAva == "1")
			  $scope.itemDel.cadfileAva = true
			  
		  if ($scope.itemDel.packageReq == "1")
			  $scope.itemDel.packageReq = true 
			  
		   console.log('ACTUAL STATUS ', $scope.itemDel.itemStatus); 
		   if ($scope.itemDel.itemStatus.id == 2){
			   listitemsstatus($http,$scope,baseUrl,"3"); 
		   }
		   
		   if ($scope.itemDel.itemStatus.id == 4){
			   listitemsstatus($http,$scope,baseUrl,"3"); 
			   $scope.ordened = 'Y'  
		   }
				   
			  
			  
			  
		   $scope.date_lead = new Date($scope.itemDel.rfqdueDate);	 
		   $scope.partExp.partId = $scope.itemDel.part.partNumber;
		   $scope.partExp.partDesc = $scope.itemDel.part.partDesc;
		   $scope.partExp.unitCost = $scope.itemDel.part.partCost;
		   
		   
		    console.log('1 - TESTING NEW DATE VALUE : ***************** ', $scope.itemDel.dateRegisterOrdered )
		    
		   if  ($scope.itemDel.dateRegisterOrdered != null) {
			   $scope.itemDel.dateRegisterOrdered =  new Date($scope.itemDel.dateRegisterOrdered);
		   }
		   
		   
		   console.log('2 - TESTING NEW DATE VALUE : ***************** ', $scope.itemDel.dateRegisterOrdered )
		   if ($scope.itemDel.dateRegisterOrdered == " "){
			   $scope.itemDel.dateRegisterOrdered = new Date();
		   }
		   

			  
			  
		  
		  
		  
	  }
	  
	  $scope.updateItemEdit = function(){
		  
		  $scope.valid = true;
		  
		  $scope.itemDel.rfqdueDate = $scope.date_lead;
		  
		  if ($scope.itemDel.drawingAva == true)
			  	  $scope.itemDel.drawingAva = "1"
			  else
				  $scope.itemDel.drawingAva = "0"  
		  
		  if ($scope.itemDel.newPart == true)
			      $scope.itemDel.newPart = "1"
			  else
				  $scope.itemDel.newPart = "0"
			  
		  if ($scope.itemDel.sampleAva == true)
			  	  $scope.itemDel.sampleAva = "1";
		  	  else
		  		  $scope.itemDel.sampleAva = "0";
		  
		  if ($scope.itemDel.concernsCurr == true)
			      $scope.itemDel.concernsCurr = "1"
			  else
				  $scope.itemDel.concernsCurr = "0"
			  
		  if ($scope.itemDel.cadfileAva == true)
			      $scope.itemDel.cadfileAva = "1"
			  else
				  $scope.itemDel.cadfileAva = "0"		  
			  
		  if ($scope.itemDel.packageReq == true)
			      $scope.itemDel.packageReq = "1"
			  else
				  $scope.itemDel.packageReq = "0"
					  
					  
				        if (($scope.itemDel.itemType == null) || ($scope.itemDel.industry == null) || ($scope.itemDel.qitemType == null) 
				                || ($scope.itemDel.anualUsage == "") || ($scope.itemDel.quantity == "" ) || ($scope.itemDel.targetPrice == "") 
				                || ($scope.itemDel.suggestMat == null) || ($scope.itemDel.suggestVend == null) 
				                || ($scope.itemDel.fob == "") || ($scope.itemDel.rfqdueDate == "") || $scope.itemDel.measureUnit == null){
				             	$scope.valid = false;
				             	notificationService.error('You Must Complete all obligatory fields');
				             }
				             
				             if (($scope.itemDel.itemType.id == 1) && ($scope.itemDel.newPartName == "")){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field New Part Name'); 
				             }

				     		if (($scope.itemDel.itemType.id == 4) && ($scope.partExp == null)){
				     			$scope.valid = false; 
				     			notificationService.error('Please fill field Part Name'); 
				     		}
				     		
				     		
				     		if ($scope.itemDel.itemType.id == 4) { 
				     			
				     			if ($scope.itemDel.part.partDesc == '')
				     				$scope.valid = false;
				     		}
				     		
				     		if ($scope.valid) { 
				     			
				     			
				    			$http.put(baseUrl + '/item/update', $scope.itemDel).
				    			success(function(response){
				    				    notificationService.success('Item Sucessfully updated');
				    				    listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);
				    				    $scope.itemDel = {};
							    	      if ($scope.itemDel.drawingAva == '1')
						    			  	  $scope.itemDel.drawingAva = true
						    			  else
						    				  $scope.itemDel.drawingAva = false 
						    		  
						    		  if ($scope.itemDel.newPart == '1')
						    			      $scope.itemDel.newPart = true
						    			  else
						    				  $scope.itemDel.newPart = false
						    			  
						    		  if ($scope.itemDel.sampleAva == '1')
						    			  	  $scope.itemDel.sampleAva = true
						    		  	  else
						    		  		  $scope.itemDel.sampleAva = false
						    		  
						    		  if ($scope.itemDel.concernsCurr == '1')
						    			      $scope.itemDel.concernsCurr = true
						    			  else
						    				  $scope.itemDel.concernsCurr = false
						    			  
						    		  if ($scope.itemDel.cadfileAva == '1')
						    			      $scope.itemDel.cadfileAva = true
						    			  else
						    				  $scope.itemDel.cadfileAva = false		  
						    			  
						    		  if ($scope.itemDel.packageReq == '1')
						    			      $scope.itemDel.packageReq = true
						    			  else
						    				  $scope.itemDel.packageReq = false	

				    				});
     			
				     			
				     		}
				     		
				     	
				     		
		  
		  
		  
	  }	 
	  
	  
	   $scope.findPartExpandableEdit = function() {
			 
    	     findpartExpEdit($http,$scope,baseUrl, $scope.itemDel.part.partNumber);
			 console.log('PART EXp : ',  $scope.smipartExp)
			 
	  }
	   
	   $scope.onChangePart = function() { 

	 	    $scope.itemDel.part.partDesc   = '';
			$scope.itemDel.part.partCost  = '';
   
	   }
	  
	  
	  
	  $scope.copyItem = function(){ 
		  
		  $scope.valid = true;
		  
		  $scope.itemDel.rfqdueDate = $scope.date_lead;
		  $scope.itemDel.quote = $scope.currQuote;
		  $scope.itemDel.users = $rootScope.user;
		  
		  if ($scope.itemDel.drawingAva == true)
			  	  $scope.itemDel.drawingAva = "1"
			  else
				  $scope.itemDel.drawingAva = "0"  
		  
		  if ($scope.itemDel.newPart == true)
			      $scope.itemDel.newPart = "1"
			  else
				  $scope.itemDel.newPart = "0"
			  
		  if ($scope.itemDel.sampleAva == true)
			  	  $scope.itemDel.sampleAva = "1";
		  	  else
		  		  $scope.itemDel.sampleAva = "0";
		  
		  if ($scope.itemDel.concernsCurr == true)
			      $scope.itemDel.concernsCurr = "1"
			  else
				  $scope.itemDel.concernsCurr = "0"
			  
		  if ($scope.itemDel.cadfileAva == true)
			      $scope.itemDel.cadfileAva = "1"
			  else
				  $scope.itemDel.cadfileAva = "0"		  
			  
		  if ($scope.itemDel.packageReq == true)
			      $scope.itemDel.packageReq = "1"
			  else
				  $scope.itemDel.packageReq = "0"
					  
					  
				        if (($scope.itemDel.itemType == null) || ($scope.itemDel.industry == null) || ($scope.itemDel.qitemType == null) 
				                || ($scope.itemDel.anualUsage == "") || ($scope.itemDel.quantity == "" ) || ($scope.itemDel.targetPrice == "") 
				                || ($scope.itemDel.suggestMat == null) || ($scope.itemDel.suggestVend == null) 
				                || ($scope.itemDel.fob == "") || ($scope.itemDel.rfqdueDate == "") || $scope.itemDel.measureUnit == null){
				             	$scope.valid = false;
				             	notificationService.error('You Must Complete all obligatory fields');
				             }
				             
				             if (($scope.itemDel.itemType.id == 1) && ($scope.itemDel.newPartName == "")){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field New Part Name'); 
				             }

				     		if (($scope.itemDel.itemType.id == 4) && ($scope.partExp == null)){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field Part Name'); 
				     		}
				     		
				     		
				     		if ($scope.itemDel.itemType.id == 4) {
				     			
				     			console.log('PART FROM EXP TO SAVE : ' + $scope.partExp.partId); 
				     			
				     			if ($scope.itemDel.part.partDesc == '')
				     				$scope.valid = false;
				     			
				     			/*$scope.itemDel.part.partNumber = $scope.partExp.partId;
				     			$scope.itemDel.part.partDesc   = $scope.partExp.partDesc;
				     			$scope.itemDel.part.partCost   = $scope.partExp.unitCost;*/
				     			
				     			
				     			
				     		}
				     		
				     		if ($scope.valid) { 
				     			
				     			
				    			$http.post(baseUrl + '/item/insert', $scope.itemDel).
				    			success(function(response){
				    				    notificationService.success('Item Sucessfully Added');
				    				    listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);
				    				    listquotenotes($http,$scope,baseUrl,$rootScope.quoActual.id);
				    				    $scope.itemDel = {};
							    	      if ($scope.itemDel.drawingAva == '1') 
						    			  	  $scope.itemDel.drawingAva = true
						    			  else
						    				  $scope.itemDel.drawingAva = false 
						    		  
						    		  if ($scope.itemDel.newPart == '1')
						    			      $scope.itemDel.newPart = true
						    			  else
						    				  $scope.itemDel.newPart = false
						    			  
						    		  if ($scope.itemDel.sampleAva == '1')
						    			  	  $scope.itemDel.sampleAva = true
						    		  	  else
						    		  		  $scope.itemDel.sampleAva = false
						    		  
						    		  if ($scope.itemDel.concernsCurr == '1')
						    			      $scope.itemDel.concernsCurr = true
						    			  else
						    				  $scope.itemDel.concernsCurr = false
						    			  
						    		  if ($scope.itemDel.cadfileAva == '1')
						    			      $scope.itemDel.cadfileAva = true
						    			  else
						    				  $scope.itemDel.cadfileAva = false		  
						    			  
						    		  if ($scope.itemDel.packageReq == '1')
						    			      $scope.itemDel.packageReq = true
						    			  else
						    				  $scope.itemDel.packageReq = false	

				    				});
     			
				     			
				     		}
				     		
				     	
				     		
		  
		  
		  
	  }	  
	  
	  
	  $scope.newItem = function(){ 
		  
		  $scope.valid = true;
		  
		  $scope.itemDel.rfqdueDate = $scope.date_lead;
		  $scope.itemDel.quote = $scope.currQuote;
		  $scope.itemDel.users = $rootScope.user;
		  
		  if ($scope.itemDel.drawingAva == true)
			  	  $scope.itemDel.drawingAva = "1"
			  else
				  $scope.itemDel.drawingAva = "0"  
		  
		  if ($scope.itemDel.newPart == true)
			      $scope.itemDel.newPart = "1"
			  else
				  $scope.itemDel.newPart = "0"
			  
		  if ($scope.itemDel.sampleAva == true)
			  	  $scope.itemDel.sampleAva = "1";
		  	  else
		  		  $scope.itemDel.sampleAva = "0";
		  
		  if ($scope.itemDel.concernsCurr == true)
			      $scope.itemDel.concernsCurr = "1"
			  else
				  $scope.itemDel.concernsCurr = "0"
			  
		  if ($scope.itemDel.cadfileAva == true)
			      $scope.itemDel.cadfileAva = "1"
			  else
				  $scope.itemDel.cadfileAva = "0"		  
			  
		  if ($scope.itemDel.packageReq == true)
			      $scope.itemDel.packageReq = "1"
			  else
				  $scope.itemDel.packageReq = "0"
					  
					  
				        if (($scope.itemDel.itemType == null) || ($scope.itemDel.industry == null) || ($scope.itemDel.qitemType == null) 
				                || ($scope.itemDel.anualUsage == "") || ($scope.itemDel.quantity == "" ) || ($scope.itemDel.targetPrice == "") 
				                || ($scope.itemDel.suggestMat == null) || ($scope.itemDel.suggestVend == null) 
				                || ($scope.itemDel.fob == "") || ($scope.itemDel.rfqdueDate == "") || $scope.itemDel.measureUnit == null){
				             	$scope.valid = false;
				             	notificationService.error('You Must Complete all obligatory fields');
				             }
				             
				             if (($scope.itemDel.itemType.id == 1) && ($scope.itemDel.newPartName == "")){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field New Part Name'); 
				             }

				     		if (($scope.itemDel.itemType.id == 4) && ($scope.partExp == null)){
				     			$scope.valid = false;
				     			notificationService.error('Please fill field Part Name'); 
				     		}
				     		
				     		
				     		if ($scope.itemDel.itemType.id == 4) {
				     			
				     			console.log('PART FROM EXP TO SAVE : ' + $scope.partExp.partId); 
				     			
				     			if ($scope.itemDel.part.partDesc == '')
				     				$scope.valid = false;
				     			
				     			/*$scope.itemDel.part.partNumber = $scope.partExp.partId;
				     			$scope.itemDel.part.partDesc   = $scope.partExp.partDesc;
				     			$scope.itemDel.part.partCost   = $scope.partExp.unitCost;*/
				     			
				     			
				     			
				     		}
				     		
				     		if ($scope.valid) {  
				     			
				     			
				    			$http.post(baseUrl + '/item/new', $scope.itemDel).
				    			success(function(response){
				    				    notificationService.success('Item Sucessfully Added');
				    				    listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);
				    				    listquotenotes($http,$scope,baseUrl,$rootScope.quoActual.id);
				    				    $scope.itemDel = {};
							    	      if ($scope.itemDel.drawingAva == '1') 
						    			  	  $scope.itemDel.drawingAva = true
						    			  else
						    				  $scope.itemDel.drawingAva = false 
						    		  
						    		  if ($scope.itemDel.newPart == '1')
						    			      $scope.itemDel.newPart = true
						    			  else
						    				  $scope.itemDel.newPart = false
						    			  
						    		  if ($scope.itemDel.sampleAva == '1')
						    			  	  $scope.itemDel.sampleAva = true
						    		  	  else
						    		  		  $scope.itemDel.sampleAva = false
						    		  
						    		  if ($scope.itemDel.concernsCurr == '1')
						    			      $scope.itemDel.concernsCurr = true
						    			  else
						    				  $scope.itemDel.concernsCurr = false
						    			  
						    		  if ($scope.itemDel.cadfileAva == '1')
						    			      $scope.itemDel.cadfileAva = true
						    			  else
						    				  $scope.itemDel.cadfileAva = false		  
						    			  
						    		  if ($scope.itemDel.packageReq == '1')
						    			      $scope.itemDel.packageReq = true
						    			  else
						    				  $scope.itemDel.packageReq = false	

				    				});
     			
				     			
				     		}
				     		
				     	
				     		
		  
		  
		  
	  }	 	  
		  
	  
	
	
	
}]);	

//-------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------


app.controller('itemController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','Upload', '$timeout','$uibModal',
	  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,Upload, $timeout,$uibModal) {

	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	$scope.itemActual = {};
	$scope.itemActivity = {};
	if ($rootScope.itemActual==null){
		$window.location.assign("./sealhome");
	}	
	
	$scope.currQuote = $rootScope.quoActual;
	$scope.itemActual = $rootScope.itemActual;
	listmeasureunits($http,$scope,baseUrl);
	listMaterials($http,$scope,baseUrl);
	listtools($http,$scope,baseUrl);
	listmachines($http,$scope,baseUrl);
	$scope.matdetail = {};
	listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
	listlabdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
	newmaterial($http,$scope,baseUrl);
	
	$scope.button    = 0;
	$scope.buttonLab    = 0;
	$scope.clearLab = 1;
	$scope.clearMat = 1;
	$scope.cavities = 0;
	//$scope.partialDownloadLink 
	$scope.matdetail.lf = null;
	$scope.matdetail.progression  = null;
	$scope.matdetail.slitWidth = null;
	$scope.matdetail.nroRolls = null;
	$scope.matdetail.yield = null;   
	$scope.matdetail.priceEach = null;
	$scope.matdetail.freight = null;
	$scope.matdetail.freightCost = null;
	$scope.matdetail.totalCost = null;
	$scope.matdetail.material = {}
	$scope.matdetail.material.partNumber = '';   
	
	$scope.matdetail.freight = '0.00';
	$scope.matdetail.freightCost = '0.00';
	
    $scope.sugCost = 0;
    $scope.newC = '1';
    $scope.matDelete = {};
    $scope.sugCost = parseFloat($scope.itemActual.smiSaleCost.toFixed(2)); 
    //$scope.matExp = {};
    $scope.smiExp = '';
    $scope.auxMatDetails = [];
    $scope.btVisible  = true;
                                      
     
    console.log('SALES COST ' + $scope.itemActual.smiSaleCost);
	console.log('MAT : ' +  $scope.totMat);
	
	
	
	 $scope.findPartExpandable = function() {
		 
		 console.log('PART TYPED : ' + $scope.matdetail.material.partNumber);
		 findpartExp($http,$scope,baseUrl, $scope.matdetail.material.partNumber);
		 console.log('PART EXp : ',  $scope.smipartExp)
		 $scope.matdetail.material.descMaterial = $scope.smipartExp.parDesc; 
		 
		 
	 }
	
	 $scope.resetMat = function() {
		    $scope.matdetail.matWidht = null;
		    $scope.matdetail.material = null;
		    $scope.matdetail.measureUnit = null;
		    $scope.matdetail.costUm = null
		    $scope.matdetail.lf = null;
			$scope.matdetail.progression  = null;
			$scope.matdetail.slitWidth = null;
			$scope.matdetail.nroRolls = null;
			$scope.matdetail.yield = null;
			$scope.matdetail.priceEach = null;
			$scope.matdetail.freight = null;
			$scope.matdetail.totalCost = null;		 
		 
	 }
	 
	 $scope.resetLab = function() {
		    $scope.matdetail.tool = null;
		    $scope.matdetail.machine = null;
		    $scope.matdetail.stepDescription = null;
	 }
	
	
     $scope.addMatDetail = function() {
        

    	
    	$scope.matdetail.user = $rootScope.user;
    	$scope.matdetail.item =  $scope.itemActual;
    	$scope.matdetail.typeDetail = '1';
    	$scope.matdetail.typeMaterial = $scope.newC; 
    	//var costStr = $scope.matdetail.costUm.replace(',', ''); 

    	
    	if ($scope.matdetail.typeMaterial == '1'){
    		$scope.matdetail.material = $scope.newMat;
    		console.log("MATERIAL NEW  : " + $scope.matdetail.material.idMaterial);
    	}
    	
    	if ($scope.matdetail.typeMaterial == '0'){   // Existing Material From Expandablae data
    		 
    		if ($rootScope.partsAutocomplete == 1)   //New Line -- 10/29/2019
    		     $scope.matdetail.material = $scope.matExp;
    		console.log("MATERIAL EXP : " + $scope.matdetail.material.partNumber);  
    	}
    	
    	//var indata = {md: $scope.matdetail, it: $scope.itemActual }
    	
    	if ($scope.matdetail.material != null) {
    			
    			console.log("Adding Material :" , $scope.matdetail);
		    	if ($scope.matdetail.totalCost != null){
			    	$http.post(baseUrl + '/matdetail', $scope.matdetail).
					success(function(response){
						
						$scope.matdetail = {};
						listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
						listMaterials($http,$scope,baseUrl);
						$scope.button    = 0;
						$scope.newC = '1';
						//$scope.getTotalMatCost();
						$scope.updateTotals();
						return $http.post(baseUrl + '/updateItem',$scope.itemActual)
				    }).then(function(response){
			    		notificationService.success('Record Sucessfull saved');	
			    		$scope.updateTotals(); 
			    		
			    		
			    		
			    		
			    	}, function errorCallback(response){
			  	      $scope.res = response.data;
			  	      //alert('Error : ' + $scope.res);
			  		     notificationService.notify({
			  		    	title: 'Error',
			  		    	title_escape: false,
			  		    	text: $scope.res.message,
			  		    	text_escape: false,
			  		    	styling: "bootstrap3",
			  		    	type: "notice",
			  		    	icon: true
			  		    });	       
			    	});	
		    	}else
		    		notificationService.error('Total Material Cost is Null');
    	
    	}  // End compare material selected different null
    	
    	
    	
    	
     }
     
     $scope.addLabDetail = function() {
         
     	//alert("Guardando Item Temporal...");
     	//alert("Guardando Detail..." + $rootScope.idSesion + ' User : ' + $rootScope.user);  
     	
     	$scope.matdetail.user = $rootScope.user;
     	$scope.matdetail.item =  $scope.itemActual;
     	$scope.matdetail.typeDetail = '2';
		

     	//var indata = {md: $scope.matdetail, it: $scope.itemActual }
     	
     	if ($scope.matdetail.laborCost != null) {  
     	
 	    	$http.post(baseUrl + '/matdetail', $scope.matdetail).
 			success(function(response){
 				
 				$scope.matdetail = {};
 				listlabdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
 				$scope.button    = 0;
 				
 				//$scope.getTotalLabCost();
 				$scope.updateTotals();
 				return $http.post(baseUrl + '/updateItem',$scope.itemActual)
 		    }).then(function(response){
 	    		notificationService.success('Record Sucessfull saved');	
 	    		
 	    		
 	    	}, function errorCallback(response){
 	  	      $scope.res = response.data;
 	  	      //alert('Error : ' + $scope.res);
 	  		     notificationService.notify({
 	  		    	title: 'Error',
 	  		    	title_escape: false,
 	  		    	text: $scope.res.message,
 	  		    	text_escape: false,
 	  		    	styling: "bootstrap3",
 	  		    	type: "notice",
 	  		    	icon: true
 	  		    });	       
 	    	});	   
     	}else
     		notificationService.error('Total Labor Cost is Null');	 
     	
     	
      }     
     
     $scope.udpateMatDetails = function() { 
    	 

     	$scope.matdetail.user = $rootScope.user;
    	$scope.matdetail.item =  $scope.itemActual;
    	$scope.matdetail.typeDetail = '1';
    	$scope.matdetail.typeMaterial = $scope.newC;
    	//var costStr = $scope.matdetail.costUm.replace(',', ''); 
		//$scope.matdetail.costUm = costStr;   
		console.log("*** MAT DETAIl TO SAVE :  *** ",  $scope.matdetail ); 
    	
    	if ($scope.matdetail.typeMaterial == '1'){ 
    		$scope.matdetail.material = $scope.newMat;
    		console.log("MATERIAL NEW  : " + $scope.matdetail.material.idMaterial); 
    	}
    	
    	if ($scope.matdetail.typeMaterial == '0'){   // Existing Material From Expandablae data
    		
    		if ($rootScope.partsAutocomplete == 1)
    		  		$scope.matdetail.material = $scope.matExp;
    		
    			   
    		console.log("MATERIAL EXP : " + $scope.matdetail.material.partNumber); 
    	}
    	     
    	
    	 
			$http.put(baseUrl + "/matdetail/update",$scope.matdetail).success(function (data) {      
				console.log("Updating Mat proccesing Details..."); 
				listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
				listMaterials($http,$scope,baseUrl);
				$scope.matdetail = {};
				$scope.newC = '1';
				$scope.button    = 0;
				console.log("MATERIAL COST UPDATE FUNCTION :", $scope.itemActual.materialCost); 
				return $http.post(baseUrl + '/updateItem',$scope.itemActual)
		    }).then(function(response){
	    		notificationService.success('Record Sucessfull Update');	
	    		$scope.updateTotals();
	    	}, function errorCallback(response){
	  	      $scope.res = response.data;
	  	      //alert('Error : ' + $scope.res);
	  		     notificationService.notify({
	  		    	title: 'Error',
	  		    	title_escape: false,
	  		    	text: $scope.res.message,
	  		    	text_escape: false,
	  		    	styling: "bootstrap3",
	  		    	type: "notice",
	  		    	icon: true
	  		    });	       				
				
			});   	 
    	 
     }
     
 
     $scope.udpateLabDetails = function() {
    	 
    	 
			$http.put(baseUrl + "/matdetail/update",$scope.matdetail).success(function (data) {      
				console.log("Updating Mat proccesing Details..."); 
				//listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
				listlabdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
				$scope.matdetail = {};
				$scope.buttonLab    = 0;
				return $http.post(baseUrl + '/updateItem',$scope.itemActual)
		    }).then(function(response){
	    		notificationService.success('Record Sucessfull Update');
	    		$scope.updateTotals();
	    	}, function errorCallback(response){
	  	      $scope.res = response.data;
	  	      //alert('Error : ' + $scope.res);
	  		     notificationService.notify({
	  		    	title: 'Error',
	  		    	title_escape: false,
	  		    	text: $scope.res.message,
	  		    	text_escape: false,
	  		    	styling: "bootstrap3",
	  		    	type: "notice",
	  		    	icon: true
	  		    });	       				
				
			});   	 
 	 
  }
     
     $scope.udpateCavities = function() {
    	 
    	   
     }
     
     $scope.backtoQuote = function() {
   		
    	 //getQuoteDetails($http,$rootScope, $scope,baseUrl,$rootScope.quoActual);
    	 $window.location.assign(baseUrl + '/sealhome#/quote-detail');  
    	 
    }
     
     
     $scope.completeEstimate = function() {
    	 

  	 
	    $scope.itemActivity.user = $rootScope.user; 
		$scope.itemActivity.username = $rootScope.userName;   	 
		$scope.itemActivity.descActivity  = 'Item has been Estimated';
		$scope.editOrd = 'N';
		
		
		$http.post(baseUrl + "/updateitemStatus?newStat=2",$scope.itemActual).success(function (data) {      
			console.log("Updating Status to Completed..."); 
			
			return $http.post(baseUrl + '/item/newActivity?idItem='+$scope.itemActual.idItem,$scope.itemActivity)
	    }).then(function(response){
	    	notificationService.success('Item was Completed');	
	    	getQuoteDetails($http,$rootScope, $scope,baseUrl,$rootScope.quoActual.id);
	    	listquotenotes($http,$scope,baseUrl,$rootScope.quoActual.id); 
	    	$scope.btVisible = false; 
	    	//$scope.backtoQuote(); 
    	}, function errorCallback(response){
  	      $scope.res = response.data;
  	      //alert('Error : ' + $scope.res);
  		     notificationService.notify({
  		    	title: 'Error',
  		    	title_escape: false,
  		    	text: $scope.res.message,
  		    	text_escape: false,
  		    	styling: "bootstrap3",
  		    	type: "notice",
  		    	icon: true
  		    });	       				
			
		});   	 
		
		
    	 
     }
	
     $scope.removeMatDetail = function(index){    	
			
			
			//alert("Index Actual : " + index);
    	    $scope.matdetail.deleteUser = $rootScope.user;
			$http.put(baseUrl + "/matdetail/remove",$scope.matdetail).success(function (data) {      
				console.log("Deleting Mat proccesing Details..."); 
				$scope.totMat = $scope.totMat - 1;
				listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem); 
				
				$scope.matdetail = {};
				$scope.button    = 0;
				return $http.post(baseUrl + '/updateItem',$scope.itemActual)
		    }).then(function(response){
	    		notificationService.success('Record Sucessfull Delete');	
	    	}, function errorCallback(response){
	  	      $scope.res = response.data;
	  	      //alert('Error : ' + $scope.res);
	  		     notificationService.notify({
	  		    	title: 'Error',
	  		    	title_escape: false,
	  		    	text: $scope.res.message,
	  		    	text_escape: false,
	  		    	styling: "bootstrap3",
	  		    	type: "notice",
	  		    	icon: true
	  		    });	       				
				
			}); 			
			$scope.modalInstance.close();
			
	}  
     
     
     $scope.removeLabDetail = function(index){    	
			
			
			//alert("Index Actual : " + index);
 	    $scope.matdetail.deleteUser = $rootScope.user;
			$http.put(baseUrl + "/matdetail/remove",$scope.matdetail).success(function (data) {      
				console.log("Deleting Lab proccesing Details..."); 
				//listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
				$scope.totLab  = $scope.totLab  - 1;
				listlabdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem); 
				$scope.matdetail = {};
				$scope.button    = 0;
				
				return $http.post(baseUrl + '/updateItem',$scope.itemActual) 
		    }).then(function(response){
	    		notificationService.success('Record Sucessfull Delete');	
	    	}, function errorCallback(response){
	  	      $scope.res = response.data;
	  	      //alert('Error : ' + $scope.res);
	  		     notificationService.notify({
	  		    	title: 'Error',
	  		    	title_escape: false,
	  		    	text: $scope.res.message,
	  		    	text_escape: false,
	  		    	styling: "bootstrap3",
	  		    	type: "notice",
	  		    	icon: true
	  		    });	       				
				
			}); 			
			$scope.modalInstance.close(); 
			
	}      
     
     
	$scope.cancel = function () {		
			$scope.modalInstance.dismiss('cancel');
	};
		
		
	$scope.remove = function (index) {

			$scope.indiceAct = index;
			$scope.matdetail = $scope.matdetails[index];
		    
		    $scope.modalInstance = $uibModal.open({
		    	ariaLabelledBy: 'modal-title',
		        ariaDescribedBy: 'modal-body',
		    	templateUrl: 'removeDetail.html',
		        scope: $scope,
		        size: 'sm'	        
		    });
	}  
	
	$scope.removeLab = function (index) {

		$scope.indiceAct = index;
		$scope.matdetail = $scope.labdetails[index];
	    
	    $scope.modalInstance = $uibModal.open({
	    	ariaLabelledBy: 'modal-title',
	        ariaDescribedBy: 'modal-body',
	    	templateUrl: 'removeDetailLab.html',
	        scope: $scope,
	        size: 'sm'	        
	    });
} 	
     
     
     
	$scope.clickeditDetails = function(index){ 
		
		
		$scope.indiceAct = index;
		$scope.auxMatDetails = $scope.matdetails;
		//$scope.matdetail = $scope.matdetails[index];
		$scope.matdetail = $scope.auxMatDetails[index];
		console.log('MAT DETAILS CLICK : ' ,  $scope.matdetail); 
		$scope.button    = 1; //Update
		$scope.resetLab();
		$scope.buttonLab  = 2;
		$scope.clearLab = 0;
		$scope.clearMat = 1;
		$scope.newC = $scope.matdetails[index].typeMaterial;
		$scope.matExp = {};
		$scope.matExp.unitCost = $scope.matdetails[index].costUm;
	}    
	

	$scope.clickeditLabDetails = function(index){
		
		
		$scope.indiceAct = index;
		$scope.matdetail = $scope.labdetails[index];
		$scope.buttonLab    = 1; //Update
		$scope.resetMat();
		$scope.button = 2;
		$scope.clearMat = 0;
		$scope.clearLab = 1;
	}
	
	$scope.clickClearDetails = function(){
		
		$scope.matdetail = {};
		$scope.button    = 0; //Add
		$scope.buttonLab    = 0;
		$scope.clearLab = 1;
		$scope.clearMat = 1;
		$scope.newC = '1';
		listmatdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem);
		listlabdetailsitems($http,$scope,baseUrl,$scope.itemActual.idItem); 
	}
	
	
	$scope.changeMat = function(){
		
		//alert("Change");
		$scope.matdetail.costUm = $scope.matdetail.material.unitCost;
		//console.log(JSON.stringify($scope.matdetail.material));
		$scope.matdetail.lf = 0;
		$scope.matdetail.progression  = "0.00";
		$scope.matdetail.slitWidth = "0.00";
		$scope.matdetail.nroRolls = 0;
		$scope.matdetail.yield = "0.0000";
		$scope.matdetail.priceEach = "0.0000";
		$scope.matdetail.freight = "0.00";
		$scope.matdetail.totalCost = "0.0000"
	}
	
	$scope.getPrice = function(){
	     $scope.cavities = $scope.itemActual.cavities; 
			if ($scope.matdetail.freight == null )
				$scope.matdetail.freight =  "0.00";  
	     
	     if ($scope.newC == '0'){ 
	    	 $scope.matdetail.costUm = $scope.matExp.unitCost; 
	     }
		 //alert($scope.cavities);
	     //console.log('Cavities : ' + $scope.cavities);
	  /*   if (parseFloat($scope.matdetail.freight) > 1){
	    	 alert("Freight can't be greater than 1, Please fix that field")
	     } */
	     
		 if (($scope.cavities != "" ) && (parseInt($scope.cavities) > 0 )){
			 //console.log('Cavities up Zero');
			 
			if ($scope.matdetail.matWidht == ''){
				//console.log('Set Nulls');
				$scope.matdetail.nroRolls = null;
				$scope.matdetail.yield = null;
				$scope.matdetail.priceEach = null;
				$scope.matdetail.totalCost = null;
			}
			
			if ($scope.matdetail.matWidht == '0'){
				//console.log('Set Nulls');
				$scope.matdetail.nroRolls = null;
				$scope.matdetail.yield = null;
				$scope.matdetail.priceEach = null;
				$scope.matdetail.totalCost = null;
			}
			
			if (($scope.matdetail.slitWidth != '') && ($scope.matdetail.matWidht != '')){ 
				//console.log('Calc Rolls');
				//alert("SlitWithd : " +  $scope.matdetail.slitWidth);
				if (parseFloat($scope.matdetail.slitWidth) > 0){
					//console.log('Slit Witdh Mayor que Zero');
					$scope.matdetail.nroRolls = parseFloat($scope.matdetail.matWidht)/parseFloat($scope.matdetail.slitWidth);
					$scope.matdetail.nroRolls = Math.trunc($scope.matdetail.nroRolls);
				}
			}
			
			if (($scope.matdetail.nroRolls != '') && ($scope.matdetail.lf != '') && ($scope.matdetail.progression != '')){  
				
				if (( parseFloat($scope.matdetail.progression) > 0)  && (parseFloat($scope.matdetail.nroRolls) > 0)){    //OJO Nro_Rolls > 0  OJO ADDED 10/10/2019 -- ROLLS > 0
				    
					if (parseFloat($scope.matdetail.progression) > 0)
						$scope.matdetail.yield = (parseInt($scope.cavities)*parseFloat($scope.matdetail.lf)*parseFloat($scope.matdetail.nroRolls))/parseFloat($scope.matdetail.progression);
					
					$scope.matdetail.yield = parseFloat($scope.matdetail.yield).toFixed(6);
					//console.log("COST INIT :" + $scope.matdetail.costUm);
					//var costStr = $scope.matdetail.costUm.replace(',', '');
					//console.log("COST " + parseFloat(costStr));  
					$scope.matdetail.priceEach =  parseFloat(parseFloat($scope.matdetail.costUm)/parseFloat($scope.matdetail.yield)).toFixed(4); 
					
					if ($scope.matdetail.freight == '' )
							$scope.matdetail.freight =  "0.00";
					
					//$scope.matdetail.totalCost	=	parseFloat(parseFloat($scope.matdetail.priceEach)	+ parseFloat($scope.matdetail.freight)).toFixed(4); deteled 1/17/2020
					
					//alert("Pre Freight : " + $scope.matdetail.freight);
					if (($scope.matdetail.freight != null) && (parseFloat($scope.matdetail.freight) > 0)){   //OJO ADDED 10/10/2019 != null estaba comillas
						//alert("Freight : " + $scope.matdetail.freight);
						$scope.matdetail.freightCost =  parseFloat(parseFloat($scope.matdetail.priceEach) * parseFloat($scope.matdetail.freight));
						$scope.matdetail.totalCost =  parseFloat($scope.matdetail.priceEach) + parseFloat(parseFloat($scope.matdetail.priceEach) * parseFloat($scope.matdetail.freight));
						$scope.matdetail.totalCost = $scope.matdetail.totalCost.toFixed(4);
					}else{
						
						$scope.matdetail.totalCost =  parseFloat($scope.matdetail.priceEach);
						$scope.matdetail.totalCost = $scope.matdetail.totalCost.toFixed(4);						
					}

							
					
				}
				
			}
			
		 }else{   // Blank Cavities
				$scope.matdetail.nroRolls = null;
				$scope.matdetail.yield = null;
				$scope.matdetail.priceEach = null;
				$scope.matdetail.totalCost = null;	
				console.log('No paso cavities');
		 }		
		
	}
	
	$scope.getLaborCost = function(){
		
		if ((parseFloat($scope.matdetail.qtyHour) > 0) && (parseFloat($scope.matdetail.laborRate) > 0)){ 
			$scope.matdetail.laborCost = parseFloat($scope.matdetail.laborRate)/parseFloat($scope.matdetail.qtyHour);
		    $scope.matdetail.laborCost =  $scope.matdetail.laborCost.toFixed(4);
		}    
		
	}
	
	
	$scope.getTotalMatCost = function(){
		
		
		//alert("Getting Total Cost  : " + $scope.labdetails.length);
		var gTotMat = 0;
	    for (var j=0; j < $scope.matdetails.length; j++){
	    	
         	gTotMat = gTotMat + $scope.matdetails[j].totalCost;
	    }
	    $scope.itemActual.materialCost = gTotMat;
	    $scope.itemActual.materialCost = parseFloat($scope.itemActual.materialCost).toFixed(4);
		
	}
	
	$scope.getTotalLabCost = function(){
		
    
	    var gTotLab = 0;
	    console.log('SIZE Labor  : ' + $scope.labdetails.length);
	    for (var j=0; j < $scope.labdetails.length; j++){
    	
	    	gTotLab = gTotLab + $scope.labdetails[j].laborCost;
	    }
    
	    $scope.itemActual.laborCost = gTotLab;
	    $scope.itemActual.laborCost = parseFloat($scope.itemActual.laborCost).toFixed(4);
	    $scope.getSaleCost();
		
	}
	
	
	$scope.getSaleCost = function(){
		
		
	     if (($scope.itemActual.packagingCost == null) || ($scope.itemActual.packagingCost == ""))
	    	 $scope.itemActual.packagingCost = ".00";
	     
	     if (($scope.itemActual.scrapRate == null) || ($scope.itemActual.scrapRate == ""))
	    	 $scope.itemActual.scrapRate = ".00";
	     
	     if (($scope.itemActual.margin == null) || ($scope.itemActual.margin == ""))
	    	 $scope.itemActual.margin = "0";	
	     
	     if (($scope.itemActual.toolingCost == null) || ($scope.itemActual.toolingCost == ""))
	    	 $scope.itemActual.toolingCost = "0";
	     
	      
	     if (($scope.itemActual.margin != null) && ($scope.itemActual.margin != "")){
	    	 if (parseFloat($scope.itemActual.margin) > 99){
	    		 $scope.itemActual.margin = 99;
	  		     notificationService.notify({
		  		    	title: 'Error',
		  		    	title_escape: false,
		  		    	text: 'Margin cannot be equal or greater than 100%, Input has been changed to 99%',
		  		    	text_escape: false,
		  		    	styling: "bootstrap3", 
		  		    	type: "notice",
		  		    	icon: true
		  		 });	    		 
	    		 
	    	 }
	     }
	     
    	 $scope.itemActual.smiTotalCost = parseFloat($scope.itemActual.materialCost) + parseFloat($scope.itemActual.laborCost);  
    	 //$scope.sugCost = $scope.itemActual.smiTotalCost;
    	 
    	 //$scope.costTot = parseFloat($scope.itemActual.materialCost) + parseFloat($scope.itemActual.laborCost);

    	 
	     if (parseFloat("0" + $scope.itemActual.packagingCost) > 0){
	    		 $scope.itemActual.smiTotalCost =   parseFloat($scope.itemActual.smiTotalCost) + (parseFloat($scope.itemActual.smiTotalCost) * parseFloat("0" + $scope.itemActual.packagingCost));  
	     }
	     
	     if (parseFloat("0" + $scope.itemActual.scrapRate) > 0){
	    	     $scope.itemActual.smiTotalCost = parseFloat($scope.itemActual.smiTotalCost) + (parseFloat($scope.itemActual.smiTotalCost) * parseFloat("0" + $scope.itemActual.scrapRate))
	     } 
	     
	     //$scope.itemActual.smiTotalCost = (1+parseFloat("0" + $scope.itemActual.scrapRate))
	     
	     //This new formula to test tomorrow
	    //$scope.itemActual.smiTotalCost = parseFloat(((1+(parseFloat("0" + $scope.itemActual.scrapRate)))*parseFloat($scope.costTot))) * (1+parseFloat("0" + $scope.itemActual.packagingCost));
	    $scope.itemActual.smiTotalCost =  parseFloat(parseFloat((1+parseFloat("0" + parseFloat($scope.itemActual.scrapRate))) * parseFloat($scope.itemActual.materialCost)) + parseFloat($scope.itemActual.laborCost)) * parseFloat(1 + parseFloat("0" + parseFloat($scope.itemActual.packagingCost)));  
	    $scope.itemActual.smiTotalCost = parseFloat($scope.itemActual.smiTotalCost).toFixed(4); 
	    
	    console.log("MATERIAL COST :", $scope.itemActual.materialCost); 
	    console.log("SMI PRICE :", $scope.itemActual.smiTotalCost);
	   //  $scope.itemActual.smiTotalCost = parseFloat(1+(Scrap Rate));
	     
	     //$scope.itemActual.smiSaleCost = parseFloat($scope.itemActual.smiTotalCost) + (parseFloat($scope.itemActual.smiTotalCost) * (parseFloat($scope.itemActual.margin)/100));
	     $scope.itemActual.smiSaleCost = parseFloat($scope.itemActual.smiTotalCost)/((100 - parseFloat($scope.itemActual.margin))/100 );
	     $scope.itemActual.smiSaleCost = parseFloat($scope.itemActual.smiSaleCost).toFixed(3);
	     
	     console.log("SALES COST :", $scope.itemActual.smiSaleCost); 
	     
		 
		
	}   // END SALE COST
		
	$scope.updatePack = function(){
		$scope.getSaleCost();
		$scope.updateItem(); 
	}
	
	$scope.updateScrap = function(){
		$scope.getSaleCost();
		$scope.updateItem();
	}
	
	$scope.updateMargin = function(){
		$scope.getSaleCost();
		$scope.updateItem(); 
		
	}
	
	$scope.pasteClick = function(){ 
		
		console.log("Actual Copy Item" , $rootScope.currCopyItem ); 
		console.log("ACTUAL ITEM" , $scope.itemActual ); 
		if ($rootScope.currCopyItem != null){
		  
				//alert("Click Paste", $rootScope.currCopyItem.idItem);
				
				$http.post(baseUrl + "/copyMatdetail?itCopy="+$rootScope.currCopyItem.idItem, $scope.itemActual).success(function (data) {      
					//console.log("Updating Item..."); 
					
					$scope.matdetail = {};
					$scope.itemActual = data;

					return $http.get(baseUrl +"/getItem?id="+$scope.itemActual.idItem)

				}).then(function (response){
					alert("Paste, Yeeehaaaaaaa..!");
					$scope.newItem = response.data; 
					console.log("NEW ITEM ACTUAL", $scope.newItem);
					$rootScope.itemActual = $scope.newItem;

					listmatdetailsitems($http,$scope,baseUrl,$scope.newItem.idItem);
					listlabdetailsitems($http,$scope,baseUrl,$scope.newItem.idItem); 
			
			
					$window.location.assign(baseUrl + '/sealhome#/item-sheetcost');
					
					$scope.button    = 0;
					$scope.buttonLab    = 0;
					$scope.clearLab = 1;
					$scope.clearMat = 1;

					//$scope.partialDownloadLink 
					$scope.matdetail.lf = null;
					$scope.matdetail.progression  = null;
					$scope.matdetail.slitWidth = null;
					$scope.matdetail.nroRolls = null;
					$scope.matdetail.yield = null;   
					$scope.matdetail.priceEach = null;
					$scope.matdetail.freight = null;
					$scope.matdetail.freightCost = null;
					$scope.matdetail.totalCost = null;
					$scope.matdetail.material = {}
					$scope.matdetail.material.partNumber = '';   
					
					$scope.matdetail.freight = '0.00';
					$scope.matdetail.freightCost = '0.00';	
				});	
				
				$rootScope.currCopyItem = {}; 

				
		}			

	}
	
	
	$scope.refreshPaste = function() { 
		
		listmatdetailsitems($http,$scope,baseUrl,$rootScope.itemActual.idItem);
		listlabdetailsitems($http,$scope,baseUrl,$rootScope.itemActual.idItem);
		
	}
	
	
	$scope.updateItem = function(){ 
		
		console.log("Updating Item...", $scope.itemActual); 		
		$http.post(baseUrl + "/updateItem",$scope.itemActual).success(function (data) {      
			//console.log("Updating Item..."); 
			getQuoteDetails($http,$rootScope, $scope,baseUrl,$rootScope.quoActual.id);  
		
		});
		
	}
	
	$scope.updateTotals = function(){
		$scope.getSaleCost();
		$scope.updateItem();
		  
	}
	
	$scope.updateCostsug = function() {
		
		$scope.sugCost = parseFloat($scope.itemActual.smiSaleCost).toFixed(4);
	}
	
	$scope.getsuggestMargin = function() {
		   
		$scope.errorM = '0';
		$scope.erroMessage = "";
		//$scope.updateCostsug();
		//$scope.sugCost = parseFloat($scope.itemActual.smiSaleCost);
		if (parseFloat($scope.sugCost) > parseFloat($scope.itemActual.smiTotalCost)){
			console.log('SMI COST IS GREATER : ');
		    
			$scope.errorM = '0';
			//$scope.itemActual.margin = ((parseFloat($scope.sugCost) * 100)/parseFloat($scope.itemActual.smiTotalCost)) - 100; 
			$scope.itemActual.margin = (-1*((parseFloat($scope.itemActual.smiTotalCost)*100)/parseFloat($scope.sugCost)))+100;  
			//$scope.itemActual.margin = parseFloat($scope.itemActual.margin).toFixed(4); 
			console.log('MARGIN : ', $scope.itemActual.margin );
			
			$scope.getSaleCost();
			//$scope.sugCost = parseFloat($scope.itemActual.smiSaleCost).toFixed(4);
			$scope.sugCost = $scope.itemActual.smiSaleCost;
			$scope.updateItem();
		   
		}else{
			$scope.errorM = '1';
			$scope.erroMessage = "Must enter a Value greater that SMI Total Cost , That's make sense..?"
		}
		
	}
	
	
	

     

	
}]);


app.controller('followupController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$uibModal','Upload', '$timeout','$q', '$log',
	  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,$uibModal,Upload, $timeout, $q, $log) {

	

	
	
	
	
	
}]);


app.controller('notificationsController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$uibModal','Upload', '$timeout','$q', '$log',
	  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,$uibModal,Upload, $timeout, $q, $log) {

	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	findNotificationsEstimator($http,$scope,baseUrl,$rootScope.user.username); 
	
	
	
	$scope.markRead = function(index) {
		
		
		console.log("1 -- Mark as read "); 
		$http.post(baseUrl + "/quote/note/markread?idNote="+$scope.notesEstimators[index].id+"&type="+$scope.notesEstimators[index].origin, $rootScope.user).success(function (data) {      
			//console.log("Updating Item..."); 
			console.log("Mark as read ", data);
			findNotificationsEstimator($http,$scope,baseUrl,$rootScope.user.username); 
			findTotalNotificationsEstimator($http,$scope,$rootScope,baseUrl, $rootScope.user.username); 
		
		});		
		
	}
	
	
}]);

app.controller("dashboardController", ['$scope', '$http', '$location','$window','$filter','$rootScope',
		                              function($scope, $http, $location,$window,$filter,$rootScope) {
	
	if ($rootScope.role == '1'){
		$window.location.assign("sealhome#/dashboard");
	}else if ($rootScope.role == '2'){
		$window.location.assign("sealhome#/dashboardSeller");		
	}else if ($rootScope.role == '3'){
		$window.location.assign("sealhome#/dashboardEstimator");
	}
	
	
}]);

app.controller("emailnoteController", ['$scope', '$http', '$location','$window','$filter','$rootScope',
    function($scope, $http, $location,$window,$filter,$rootScope) {
	 
	  $http.defaults.headers.post["Content-Type"] = "application/json";
	  var baseUrl = ".";
	 $scope.res = $location.search();
	 $scope.resLoc = $location.absUrl();
	 console.log("PARAMETER " , $scope.res );
	 
	 //$rootScope.quoActual = $scope.res.quoteID; 
	 //$window.location.assign(baseUrl + '/sealhome#/quote-detail'); 

     var url = '/quDetail?id='+$scope.res.quoteID


		$http.get(baseUrl + url)
		.success(function(result){
			{

    		    $scope.quoDet = result;
	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
    		    $scope.currQuote = $scope.quoDet;
	    	    $rootScope.quoActual = $scope.quoDet;
	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
	    	    //alert($rootScope.quoActual);
	    	    
				$window.location.assign(baseUrl + '/sealhome#/quote-detail');				
	        }
		});	


}]);


app.controller('dashboardQueriesController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService',
	  function($rootScope, $scope, $http, $location,$window,$filter,notificationService) {
	 
	  $http.defaults.headers.post["Content-Type"] = "application/json";
	  var baseUrl = ".";
	  var year = $filter('date')(new Date(),'yyyy');
	  
	  init_echarts($http,$rootScope,$scope,baseUrl,$filter,$window);
	  $scope.today = $filter('date')(new Date(),'yyyy-MM-dd');
	  $scope.swPrint = '1';
	  //alert($rootScope.sessionEstimator.id);
	  if ($rootScope.role == '1'){
		  var yearQuery = $rootScope.yearRoot.descYear;
		  //$scope.showTable = false;
		  listOrdereditems($http,$scope,baseUrl,yearQuery);
     	  query_quotessummary($http,$scope,baseUrl,yearQuery);
     	  //listViewQuotes($http,$scope,baseUrl,year);
     	  listViewQuotesPendingAdmin($http,$scope,baseUrl,yearQuery);
     	  
	  }
	  
	  if ($rootScope.role == '3'){
		  var yearQuery = $rootScope.yearEst.descYear;
		  listOrdereditemsEst($http,$scope,baseUrl,yearQuery); 
		  //query_quotessummary($http,$scope,baseUrl,year);
		  listPendingItemsEstimator($http,$scope,baseUrl,yearQuery);
		  query_quotessummaryEstimator($http,$scope,baseUrl,year, $rootScope.user.username);
		  //listViewQuotesEstimatorByStatus($http,$scope,baseUrl,year,$rootScope.user.username, '7'); 
		  //listViewQuotesEstimatorOnHold($http,$scope,baseUrl,year,$rootScope.user.username,'7')
	  }	  

	  if ($rootScope.role == '2'){  //Salesman
		  listOrdereditems($http,$scope,baseUrl);
		  //listViewQuotesSeller($http,$scope,baseUrl,year,$rootScope.user.username);
		  query_quotessummarySeller($http,$scope,baseUrl,year, $rootScope.user.username);
		  query_lostquotessummarySeller($http,$scope,baseUrl,year, $rootScope.user.username);
	  }	
	  
	  
	  $scope.findListQuote = function(index) {
	        
  
	        //alert($scope.quotes[index].nroRfq);
			    $rootScope.quoActual = $scope.itemsOrd[index].quote;
				$window.location.assign(baseUrl + '/sealhome#/quote-detail'); 

	  }	
	  
	  $scope.findListQuotePend = function(index) {
	        
		  
	        //alert($scope.quotes[index].nroRfq);
			    $rootScope.quoActual = $scope.itemspEst[index].quote;
				$window.location.assign(baseUrl + '/sealhome#/quote-detail'); 

	  }		  
	  
	  $scope.clickitemDetails = function(index){

			$scope.itemActual = $scope.itemsOrd[index];  
			
	  } 
	  
	  
	  $scope.clickStatus  = function(index) {

		      $scope.viewqAll = [];
		      $scope.showTable = false;
			  //var table = $('#tbVQuotesStatus').DataTable();
			  //table.clear().destroy(); 
		      $scope.filterStat = $scope.qSumm[index].stat_quote;
		     
		      //listViewQuotes($http,$scope,baseUrl,year);
		      if ($rootScope.role == '1')
		    	  listViewSumQuotesByStatus($http,$scope,baseUrl,yearQuery, $scope.filterStat);
		      
		      if ($rootScope.role == '2')
		    	  listViewQuotesSellerByStatus($http,$scope,baseUrl,year,$rootScope.user.username, $scope.filterStat);
		      
		      if ($rootScope.role == '3')
		    	  listViewQuotesEstimatorByStatus($http,$scope,baseUrl,year,$rootScope.user.username, $scope.filterStat); 
		      /*if ($scope.showTable == true){
		    	  init_buttons_table('#tbVQuotesStatus');
		      } */  

		  
			  //$scope.showTable = true;
			  
	  
	  }
	  
	  $scope.clickSubStatus  = function(index) {

	      $scope.viewqAll = [];
	      $scope.showTable = false;
		  var table = $('#tbVQuotesStatus').DataTable();
		  table.clear().destroy(); 
	      
	     // listViewQuotes($http,$scope,baseUrl,year);
		  //listViewQuotesSeller($http,$scope,baseUrl,year,$rootScope.user.username);
		  listViewQuotesSellerByStatus($http,$scope,baseUrl,year,$rootScope.user.username, "8");

		  //$scope.showTable = true;
		  $scope.filterSubStat = $scope.qSummLost[index].stat_quote;
  
      }
	  
	  $scope.refresh = function(index) {


		  init_buttons_table('#tbVQuotesStatus');
		  
		  
	  }
	  
	  
	  $scope.showquoteItems = function(index) {
		  
		  if ($rootScope.role == '1')
			  listquoteitems($http,$scope,baseUrl,$scope.viewqAll[index][0]);
		  if ($rootScope.role == '2')
			  listquoteitems($http,$scope,baseUrl,$scope.viewqAllSel[index][0]);
		  if ($rootScope.role == '3')
			  listquoteitems($http,$scope,baseUrl,$scope.viewqAllEst[index][0]);
		  
	  }
	  
	  
	  $scope.printsheetCost = function(index) {
		  console.log("PRINT SHEET COST  "  , $scope.itemsquote );
		  $scope.selectedIndex = index;
		  printSheetCost($http,$rootScope, $scope,baseUrl,$scope.itemsquote[index])
	  }
	  

	  $scope.findVListQuote = function(index) {
	            var url = ""
		        if ($rootScope.role == '1'){
		        	url = '/quDetail?id='+$scope.viewqAll[index][0]
		        }
	            
		        if ($rootScope.role == '2'){
		        	url = '/quDetail?id='+$scope.viewqAllSel[index][0]
		        }
		        
		        if ($rootScope.role == '3'){
		        	url = '/quDetail?id='+$scope.viewqAllEst[index][0] 
		        }

		  		$http.get(baseUrl + url)
		  		.success(function(result){
		  			{

		       		    $scope.quoDet = result;
		  	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
		       		    $scope.currQuote = $scope.quoDet;
		  	    	    $rootScope.quoActual = $scope.quoDet;
		  	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
		  	    	    //alert($rootScope.quoActual);
		  	    	    
		  				//$window.location.assign(baseUrl + '/sealhome#/quote-detail');	
		  				$window.open(baseUrl + '/sealhome#/quotenote-detail?quoteID='+$scope.quoDet.id, '_blank');
		  	        }
		  		});				

	   }	
	  
	  
	  
	  
	  
	  $scope.findVListPendQuote = function(index) {
          var url = ""
	        if ($rootScope.role == '1'){
	        	url = '/quDetail?id='+$scope.viewqAllPending[index][0]
	        }
          
	        if ($rootScope.role == '2'){ 
	        	url = '/quDetail?id='+$scope.viewqAllSel[index][0]
	        }

	  		$http.get(baseUrl + url)
	  		.success(function(result){
	  			{

	       		    $scope.quoDet = result;
	  	      	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
	       		    $scope.currQuote = $scope.quoDet;
	  	    	    $rootScope.quoActual = $scope.quoDet;
	  	    	    //listquoteitems($http,$scope,baseUrl,$scope.currQuote.id);
	  	    	    //alert($rootScope.quoActual);
	  	    	    
	  				$window.location.assign(baseUrl + '/sealhome#/quote-detail');				
	  	        }
	  		});				

 }
	  
	
	
}]);
	
	
app.controller("MainController", ['$scope',function($scope) {
	/* angular.element(document).ready( function () {
         dTable = $('#tbdata')
         dTable.DataTable();
     }); */
	//dashboard1();
}]);

