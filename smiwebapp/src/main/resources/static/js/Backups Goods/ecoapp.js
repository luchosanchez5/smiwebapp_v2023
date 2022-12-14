
var app = angular.module("appEco",['ngRoute','jlareau.pnotify','chart.js','ngProgress','ui.bootstrap','vsGoogleAutocomplete','ngFileUpload','directives']);


function tablaResponsive(){
	
	  //alert("Iniciando Tabla");  
	
	  angular.element(document).ready(function() {
	         var handleDataTableButtons = function() {
	           if ($("#tbQueryDet").length) {
	             $("#tbQueryDet").DataTable({
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
	  });
	
}


/// ------------------------------------------------------------------------------------------------
/// --------------------          SERVICES FOR SMIQUOTING              -----------------------------
/// ------------------------------------------------------------------------------------------------
/// ------------------------------------------------------------------------------------------------

function listquotesseller($http,$scope,baseUrl, user){
	
	$scope.quotesseller = [];
	$http.get(baseUrl + "/list-quotesseller?idUSer="+user).success(function (data) {      
		$scope.quotesseller = data;     
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

function listquoteitems($http,$scope,baseUrl,quoteId){
	
	$scope.itemsquote = [];
	$http.get(baseUrl + "/list-itemsquote?id="+quoteId).success(function (data) {      
		$scope.itemsquote = data;    
    	//iniciar_tabla('#tbItemsquote');
    	alert("Found Items :" + $scope.itemsquote.length)

	});	
}

function listquotes($http,$scope,baseUrl){
	
	$scope.quotes = [];
	$http.get(baseUrl + "/list-quotes").success(function (data) {      
		$scope.quotes = data;     
		//iniciar_tabla('#tbQitemsType');

	});	
}


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
    	iniciar_tabla('#tbItemsTemp');

	});	
}

function listMaterials($http,$scope,baseUrl){
	
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
		iniciar_tabla('#tbCustomers');

	});	
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
		iniciar_tabla('#tbParts');

	});	
}

function listitemsType($http,$scope,baseUrl){
	
	$scope.itemsType = [];
	$http.get(baseUrl + "/list-TypeItems").success(function (data) {      
		$scope.itemsType = data;        
		iniciar_tabla('#tbitemsType');

	});
	
	
}

function listTerms($http,$scope,baseUrl){
	
	$scope.terms = [];
	$http.get(baseUrl + "/list-terms").success(function (data) {      
		$scope.terms = data;        
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



function cargarFundingsTypes($http,$scope,baseUrl,prpayType){
	
	
	if (prpayType == "1"){  // Si tiene pago es Financiera el Abono es Financiera
		
	    $scope.tipoPagoF = [{
	    	idtipoPagoF: "0",
	        desctipoPagoF: 'Financial'
	      }];		
		
	}else if (prpayType == "2"){
		//alert("Typo : " + prpayType );
	    $scope.tipoPagoF = [{
	    	idtipoPagoF: "1",
	        desctipoPagoF: 'Cash or Refinancial'
	      }];
		
	}else if (prpayType == "3"){

	    $scope.tipoPagoF = [{
	    	idtipoPagoF: "0",
	        desctipoPagoF: 'Financial'
	      }, {
	    	idtipoPagoF: "1",
	    	desctipoPagoF: 'Cash'
	      }];
	}
	
	
	
	
	
	
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

function listar_funding($http,$scope,baseUrl,id){
	
	$http.get(baseUrl + "/list-profundings?idProyect="+id).success(function (data) {  
		$scope.listfundings = data;    
		$scope.totFund = $scope.listfundings.length;
		//alert($scope.totFund);
		//alert($scope.prfinancial.length);
		//iniciar_tabla('#tbInv');
	});
	
}

function listar_prfinancialsAct($http,$scope,baseUrl,id){	

	$http.get(baseUrl + "/list-profinancialsAct?idProyect="+id).success(function (data) {  
		$scope.prfinancialAct = data;    
		//alert($scope.prfinancial.length);
		//iniciar_tabla('#tbInv');
	});
	
}


function listar_prfinancials($http,$scope,baseUrl,id){	

	$http.get(baseUrl + "/list-profinancials?idProyect="+id).success(function (data) {  
		$scope.prfinancial = data;    
		//alert($scope.prfinancial.length);
		//iniciar_tabla('#tbInv');
	});
	
}

function listar_inventory($http,$scope,baseUrl){	

	$http.get(baseUrl + "/list-inventory").success(function (data) {  
		$scope.invent = data;        
		iniciar_tabla('#tbInv');
	});
	
}

function query_proyects($http,$scope,baseUrl,id){	
	$scope.totExpPr = 0;                      
	$http.get(baseUrl + "/totalExpPr?idProyect="+id).success(function (data) {  
		$scope.totExpPr = data;        
	});
	
}


function listSellersProyect($http,$scope,baseUrl,s1,s2,s3){	
	$scope.sellersPr = [];
	$http.get(baseUrl + "/list-sellersPr?idSeller1="+s1+"&idSeller2="+s2+"&idSeller3="+s3).success(function (data) {      
		$scope.sellersPr = data;    

	});
	
}

function listar_subcontrators($http,$scope,baseUrl){	

	$http.get(baseUrl + "/list-subContrator").success(function (data) {  
		$scope.subCont = data;        
		iniciar_tabla('#tbSub');
	});
	
}

function listar_subcont($http,$scope,baseUrl){	

	$http.get(baseUrl + "/list-typeSubCont").success(function (data) {  
		$scope.typeCont = data;        
		//iniciar_tabla('#tbActivity')
	});
	
}

function findActualSeller($http,$scope,baseUrl,user){
	
	$http.get(baseUrl + '/findOneSellerUser?username='+user).
	success(function(response){
		
		$scope.actSel = response;

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


function listAddress($http,$scope,baseUrl){	
	$scope.address = [];
	$http.get(baseUrl + "/list-address").success(function (data) {      
		$scope.address = data;        
		iniciar_tabla('#tbAdd')

	});
	
}

function listar_expenses($http,$scope,baseUrl){	
	$scope.expenses = [];
	$http.get(baseUrl + "/listExpenses").success(function (data) {  
		$scope.expenses = data;        
		iniciar_tabla('#tbExp')

	});
	
}

function listar_expensesPr($http,$scope,baseUrl,pr){	
	$scope.expensesPr = [];
	$http.get(baseUrl + "/listExpensesPr?idProyect="+pr).success(function (data) {  
		$scope.expensesPr = data;  
		return "0"
	}).then(function (response){	
		iniciar_tabla('#tbExp')

	});
	
}

function listar_appoiments($http,$scope,baseUrl){	
	$scope.appoiments = [];
	$http.get(baseUrl + "/list-appoiments").success(function (data) {  
		$scope.appoiments = data;        
		iniciar_tabla('#tbAppoiment')

	});
	
}

function listar_activitys($http,$scope,baseUrl){	
	$scope.activitys = [];
	$http.get(baseUrl + "/listarActivitys").success(function (data) {  
		$scope.activitys = data;        
		iniciar_tabla('#tbActivity')

	});
	
}

function listar_activitysPr($http,$scope,baseUrl,pr){	
	$scope.activitysPr = [];
	$http.get(baseUrl + "/listarActivitysPr?idProyect="+pr).success(function (data) {  
		$scope.activitysPr = data;        
		//iniciar_tabla('#tbActivity')
		//alert("List Activitys : " + $scope.activitysPr);

	});
	
}

function listar_files($http,$scope,baseUrl,pr){	
	$scope.activitys = [];
	$http.get(baseUrl + "/listFiles?idProyect="+pr).success(function (data) {  
		$scope.prFiles = data;        


	});
	
}

function listar_proyectos($http,$scope,baseUrl){	
	$scope.productos = [];
	$http.get(baseUrl + "/listarproyects").success(function (data) {      
		$scope.proyects = data;        
		iniciar_tabla('#tbProy')

	});
	
}


function listar_proyectosFinished($http,$scope,baseUrl){	
	$scope.productos = [];
	$http.get(baseUrl + "/listarproyectsFinished").success(function (data) {      
		$scope.proyectsFinis = data;        
		iniciar_tabla('#tbProy')

	});
	
}


function listar_proyectosuser($http,$scope,baseUrl,user){	
	$scope.productos = [];
	$http.get(baseUrl + "/listarproyectsuser?username="+user).success(function (data) {      
		$scope.proyects = data;        
		iniciar_tabla('#tbProy')

	});
	
}

function listar_actualseller($http,$scope,baseUrl,user){	

	$http.get(baseUrl + "/actualseller?username="+user).success(function (data) {      
		$scope.actSel = data;        


	});
	
}


function listar_productos($http,$scope,baseUrl){	
	$scope.productos = [];
	$http.get(baseUrl + "/listar-productos").success(function (data) {      
		$scope.productos = data;        
		iniciar_tabla('#tbprod')

	});
	
}

function listCustomers($http,$scope,baseUrl){	
	$scope.customers = [];
	$http.get(baseUrl + "/list-customers").success(function (data) {      
		$scope.customers = data;        
		iniciar_tabla('#tbCust')

	});
	
}

function listSellers($http,$scope,baseUrl){	
	$scope.sellers = [];
	$scope.sellersFil = [];
	$http.get(baseUrl + "/list-sellers").success(function (data) {      
		$scope.sellers = data;    
		$scope.sellersFil = data;
		iniciar_tabla('#tbSell')

	});
	
}

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




function listLeadsSeller($http,$scope,baseUrl,user){	
	$scope.sellers = [];
	$scope.leads = [];
	$scope.leadSellers = '';
	
	$http.get(baseUrl + "/list-leadsSellerPr?user="+user).success(function (data) {
		$scope.leads = data;
	}).then(function (response) {      
		
		iniciar_tabla('#tbLead');
	});
	
}


function actLeads($http,$scope,baseUrl,role,user){	

    if ( role == 'SELLER'){
 	   listLeadsSeller($http,$scope,baseUrl,user);}
    else{
 	   listLeads($http,$scope,baseUrl);}
    
    
}

function actLeadsFilter($http,$scope,baseUrl,user,id){
	    $scope.leads = [];
    	$http.get(baseUrl + "/list-leadsFilterSeller?idSeller="+id).success(function (data) {      
    		$scope.leads = data;  
    		return "0"
    	}).then(function (response){
    		//alert("Promise Leads Ready");
    		iniciar_tabla('#tbLead');
    		
    		//alert("Prueba " + $scope.leads.sellers);
    		
    		

    	});
	
}

function listOrigin($http,$scope,baseUrl){	
	$scope.origins = [];
	$http.get(baseUrl + "/list-origins").success(function (data) {      
		$scope.origins = data;        
		iniciar_tabla('#tbOri');

	});
	
}

function listReferral($http,$scope,baseUrl){	
	$scope.referrals = [];
	$http.get(baseUrl + "/list-referrals").success(function (data) {      
		$scope.referrals = data;        
		iniciar_tabla('#tbRef');

	});
	
}

function listContrator($http,$scope,baseUrl){	
	$scope.contrators = [];
	$http.get(baseUrl + "/list-contrator").success(function (data) {      
		$scope.contrators = data;        
		iniciar_tabla('#tbContra');

	});
	
}

function listRoof($http,$scope,baseUrl){	
	$scope.roofs = [];
	$http.get(baseUrl + "/list-roofTypes").success(function (data) {      
		$scope.roofs = data;        
		iniciar_tabla('#tbRoof');

	});
	
}





function listar_tags($http,$scope,baseUrl,id){

	$http.get(baseUrl +'/listar-tags?idDispositivo='+id).success(function (data) {
		
       
		{ 
		  $scope.tagsdispo = data;
        }
		
	angular.element(document).ready( function () {
        dTable = $('#tbdata')
        dTable.DataTable();
        
		});
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

function dashboard1($http,$scope,baseUrl,$filter){
	
	
	$scope.graphicMain = [1,2,3];
	$http.get(baseUrl + "/lead/indicadores").success(function (data) {      
		$scope.indicadores = data;        
	});
	
	$http.get(baseUrl + "/lead/topSellers").success(function (data) {      
		$scope.topSellers = data;
	});	
	
	$http.get(baseUrl + "/lead/graphicLeadsMain").success(function (data) {      
		$scope.graphicMain = data;
	});		
	
	
	
	
	angular.element(document).ready(function() {
        //define chart clolors ( you maybe add more colors if you want or flot will add it automatic )
	    /*var appElement = document.querySelector('[ng-app=appEco]');
	    var scope = angular.element(appElement).scope();
	    var dataGr = $('[ng-controller="MainController"]').scope().graphicMain; */
	    //var dataGr = angular.element(document).scope().graphicMain;
	    var dataGr = [];
	    var d1 = [];
	    
	    //alert("Data Grafico: " + $scope.graphicMain);
	    var dataGr = $scope.graphicMain;
	    //alert("Long : " + dataGr.length);
	   
	    
	   
	    for (var j=0; j < dataGr.length; j++){
	    	
	    	var inicio = (new Date(dataGr[j].leadDate).getTime());
	    	d1.push([inicio, dataGr[j].tot]);
	    	
	    }
	    
	    //alert("VALOR : " + d1);
    
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
          label: "Leads",
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
      });

      angular.element(document).ready(function() {
        var canvasDoughnut,
            options = {
              legend: false,
              responsive: false
            };

        new Chart(document.getElementById("canvas1i"), {
          type: 'doughnut',
          tooltipFillColor: "rgba(51, 51, 51, 0.55)",
          data: {
            labels: [
              "Ygrene",
              "Hero",
              "California Firts",
              "E3",
              "Others"
            ],
            datasets: [{
              data: [15, 20, 30, 10, 30],
              backgroundColor: [
                "#BDC3C7",
                "#9B59B6",
                "#E74C3C",
                "#26B99A",
                "#3498DB"
              ],
              hoverBackgroundColor: [
                "#CFD4D8",
                "#B370CF",
                "#E95E4F",
                "#36CAAB",
                "#49A9EA"
              ]

            }]
          },
          options: options
        });

        new Chart(document.getElementById("canvas1i2"), {
          type: 'doughnut',
          tooltipFillColor: "rgba(51, 51, 51, 0.55)",
          data: {
            labels: [
              "Advertising",
              "SwaatMeet",
              "Tele-Marketing",
              "Knock knock",
              "Other"
            ],
            datasets: [{
              data: [15, 20, 30, 10, 30],
              backgroundColor: [
                "#BDC3C7",
                "#9B59B6",
                "#E74C3C",
                "#26B99A",
                "#3498DB"
              ],
              hoverBackgroundColor: [
                "#CFD4D8",
                "#B370CF",
                "#E95E4F",
                "#36CAAB",
                "#49A9EA"
              ]

            }]
          },
          options: options
        });

        new Chart(document.getElementById("canvas1i3"), {
          type: 'doughnut',
          tooltipFillColor: "rgba(51, 51, 51, 0.55)",
          data: {
            labels: [
              "Nelson",
              "Maria",
              "Other",
              "Other",
              "Other"
            ],
            datasets: [{
              data: [15, 20, 30, 10, 30],
              backgroundColor: [
                "#BDC3C7",
                "#9B59B6",
                "#E74C3C",
                "#26B99A",
                "#3498DB"
              ],
              hoverBackgroundColor: [
                "#CFD4D8",
                "#B370CF",
                "#E95E4F",
                "#36CAAB",
                "#49A9EA"
              ]

            }]
          },
          options: options
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

function dashboardSeller($http,$scope,baseUrl,$filter,user){
	
	findActualSeller($http,$scope,baseUrl,user);
	$scope.graphicMain = [1,2,3];
	$http.get(baseUrl + "/lead/indicadoresSel?username="+user).success(function (data) {      
		$scope.indicadores = data;        
	});
	
	
	$http.get(baseUrl + "/lead/topLeads?username="+user).success(function (data) {      
		$scope.topSellers = data;
	});	
	
	$http.get(baseUrl + "/lead/graphicLeadsMainSel?username="+user).success(function (data) {      
		$scope.graphicMain = data;
	});		
	
	
	
	
	angular.element(document).ready(function() {
        //define chart clolors ( you maybe add more colors if you want or flot will add it automatic )
	    /*var appElement = document.querySelector('[ng-app=appEco]');
	    var scope = angular.element(appElement).scope();
	    var dataGr = $('[ng-controller="MainController"]').scope().graphicMain; */
	    //var dataGr = angular.element(document).scope().graphicMain;
	    var dataGr = [];
	    var d1 = [];
	    
	    //alert("Data Grafico: " + $scope.graphicMain);
	    var dataGr = $scope.graphicMain;
	    //alert("Long : " + dataGr.length);
	   
	    
	   
	    for (var j=0; j < dataGr.length; j++){
	    	
	    	var inicio = (new Date(dataGr[j].leadDate).getTime());
	    	d1.push([inicio, dataGr[j].tot]);
	    	
	    }
	    
	    //alert("VALOR : " + d1);
    
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
          label: "Leads",
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
      });

      angular.element(document).ready(function() {
        var canvasDoughnut,
            options = {
              legend: false,
              responsive: false
            };

        new Chart(document.getElementById("canvas1i"), {
          type: 'doughnut',
          tooltipFillColor: "rgba(51, 51, 51, 0.55)",
          data: {
            labels: [
              "Ygrene",
              "Hero",
              "California Firts",
              "E3",
              "Others"
            ],
            datasets: [{
              data: [15, 20, 30, 10, 30],
              backgroundColor: [
                "#BDC3C7",
                "#9B59B6",
                "#E74C3C",
                "#26B99A",
                "#3498DB"
              ],
              hoverBackgroundColor: [
                "#CFD4D8",
                "#B370CF",
                "#E95E4F",
                "#36CAAB",
                "#49A9EA"
              ]

            }]
          },
          options: options
        });

        new Chart(document.getElementById("canvas1i2"), {
          type: 'doughnut',
          tooltipFillColor: "rgba(51, 51, 51, 0.55)",
          data: {
            labels: [
              "Advertising",
              "SwaatMeet",
              "Tele-Marketing",
              "Knock knock",
              "Other"
            ],
            datasets: [{
              data: [15, 20, 30, 10, 30],
              backgroundColor: [
                "#BDC3C7",
                "#9B59B6",
                "#E74C3C",
                "#26B99A",
                "#3498DB"
              ],
              hoverBackgroundColor: [
                "#CFD4D8",
                "#B370CF",
                "#E95E4F",
                "#36CAAB",
                "#49A9EA"
              ]

            }]
          },
          options: options
        });

        new Chart(document.getElementById("canvas1i3"), {
          type: 'doughnut',
          tooltipFillColor: "rgba(51, 51, 51, 0.55)",
          data: {
            labels: [
              "Nelson",
              "Maria",
              "Other",
              "Other",
              "Other"
            ],
            datasets: [{
              data: [15, 20, 30, 10, 30],
              backgroundColor: [
                "#BDC3C7",
                "#9B59B6",
                "#E74C3C",
                "#26B99A",
                "#3498DB"
              ],
              hoverBackgroundColor: [
                "#CFD4D8",
                "#B370CF",
                "#E95E4F",
                "#36CAAB",
                "#49A9EA"
              ]

            }]
          },
          options: options
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
	           if ($("#tbQueryDet").length) {
	             $("#tbQueryDet").DataTable({
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


app.provider("remoteResource", RemoteResourceProvider);

app.config(['$routeProvider',function($routeProvider) {
	  
	
	  $routeProvider.when('/', {
	    templateUrl: 'dashboard.html',
	    controller: 'homeController'
	  });
	  
	  
	  $routeProvider.when('/add-qlead', {
		  templateUrl : 'leadwizard.html',
		  controller : 'quoteleadController'
	  });	
	  
	  $routeProvider.when('/find-item', {
		  templateUrl : 'items.html',
		  controller : 'quoteleadController'
	  });	
	  
	  $routeProvider.when('/quote-detail', {
		  templateUrl : 'quoteDetail.html',
		  controller : 'detQuotesController'
	  });
	  
	  $routeProvider.when('/quote-leads', {
		  templateUrl : 'quotes.html',
		  controller : 'quoteleadController'
	  });
	  
	  $routeProvider.when('/seller-leads', {
		  templateUrl : 'quotesseller.html',
		  controller : 'quoteleadController'
	  });
	  
	  
	  $routeProvider.when('/scontrators', {
		  templateUrl : 'scontrator.html',
    	  controller : 'subcontController'
	  });

	  $routeProvider.when('/customers', {
		  templateUrl : 'customers.html',
		  controller : 'customersController'
	  });
	  
	  $routeProvider.when('/sealhome', {
		  templateUrl : 'principal.html',
		  controller : 'homeController'
	  });
	  
	  $routeProvider.when('/sellers', {
		  templateUrl : 'sellers.html',
		  controller : 'sellersController'
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
	   'https://demo.docusign.net/Signing/**']);
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





//------------------------------------------------------------------------------------------------------------
// ----------------------                    CUSTOMERS    -------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("customersController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listCustomers($http,$scope,baseUrl);
		$scope.client = {};
		//dashboard1($http,$scope,baseUrl);

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
//----------------------                    SELLERS    ----------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("sellersController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listSellers($http,$scope,baseUrl);
		$scope.seller = {};
		$scope.userAct = {};
		$scope.user  = {};
		
		
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
	    
		if ($scope.seller != null){
			$scope.userAct.email    = $scope.user.username;
			$scope.userAct.password = $scope.user.password;
			$scope.userAct.name     = $scope.seller.sureName;
			$scope.userAct.lastName = $scope.seller.lastName;
			
		}	
			
 		$http.post(baseUrl + '/seller', $scope.seller ).
 		success(function(response){
 			$('#addSeller').modal('hide');
 			$scope.sel = response;
 			notificationService.success('Seller Sucessfully registered :' + $scope.sel.idSeller);
 			
 			$http.post(baseUrl + "/registration?idSeller="+$scope.sel.idSeller, $scope.userAct).success(function (data) {      
				$scope.userResp = data;
				//alert("Regitre el Usuario");
			});
 			
  			
 			$http.get(baseUrl + "/list-sellers").success(function (data) {      
					$scope.seller = data;
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
		
		
		$scope.removeSeller = function(index){    	
				
			
			$http.put(baseUrl + '/seller/' +$scope.sellers[index].idSeller, $scope.sellers[index]).
			success(function(response){
				    notificationService.success('Seller Sucessfully Removed');
					$http.get(baseUrl + "/list-sellers").success(function (data) {      
					$scope.sellers = data; 

				});

				});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editCurrSeller = function(index){
				$http.get(baseUrl + "/findOneSeller?id=" + $scope.sellers[index].idSeller).success(function (data) {      
					$scope.seller = data; 

				});
		}
		
		$scope.editSeller = function(){   	
	    	
 		$http.put(baseUrl + '/seller/edit/' + $scope.seller.idSeller, $scope.seller ).
 		success(function(response){
 			$('#editSeller').modal('hide');
 			$http.get(baseUrl + "/list-sellers").success(function (data) {      
					$scope.sellers = data;
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
			alert("Email : " + $scope.credentials.username + " -- PAssword : " + $scope.credentials.password);  
		    $http.post(baseUrl + '/loginini', $.param($scope.credentials),  {
		        headers : {
		            "content-type" : "application/x-www-form-urlencoded"
		          }
		        }).success(function(data) {
		        	alert("I Passed Auth");
		    //alert("error la cagste pajuo");	
		      authenticate(function() {
		        if ($rootScope.authenticated) {
		        	$window.location.assign("./sealhome");
		        	//$location.path("/ecohome").replace();
		          $scope.error = false;
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
		      alert("Error");
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
    
    $http.get(baseUrl + '/welcome').success(function(data) {
      
      $scope.greeting = data;
      $rootScope.userName = data.userName;
      $rootScope.idSesion = data.idSesion;
      $rootScope.role = $scope.greeting.role;
      $rootScope.user = data.users;
      
      //alert($scope.greeting.role);
      //alert("Home Controller User : " + $rootScope.userName);
      
      
      $scope.dirimageUrl = 'images/' + $scope.greeting.imageUrl;
      //$scope.dirimageUrl = $scope.greeting.imageUrl;

    });	
    
    //alert("Image :"  + $scope.greeting.imageUrl);
    
    //dashboard1($http,$scope,baseUrl,$filter);
    if ($rootScope.role == 'ADMIN' || $rootScope.role == 'CEO' ){
    	dashboard1($http,$scope,baseUrl,$filter);
    }else{    
    	if ($rootScope.userName != null){
    		//alert($rootScope.userName);
    		dashboardSeller($http,$scope,baseUrl,$filter,$rootScope.userName);
    	}
    }	
    
    
    //dashboard1($http,$scope,baseUrl,$filter);
    
    
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
		
		
		$scope.removeLeadAct = function(index){    	
			
			//alert("Remove");
			
			//alert("Index Actual : " + index);
			$http.put(baseUrl + "/leadAct/delete?id="+$scope.leadActivitys[index].idLeadActivity + "&stat=1&user="+$rootScope.userName).
			success(function(response){
				    
				 if (response == "1"){    
				    //notificationService.success('Lead Activity Sucessfully Removed');
				    listar_leadsActivitys($http,$scope,baseUrl, $scope.leads[$scope.indiceAct].idLead);

				 }else
					 notificationService.error('Error : Just the Owner can Delete this Lead'); 

				});
			
			
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

app.controller('querysController',['$rootScope', '$scope', '$http', '$location','$window','$filter',
                 		  function($rootScope, $scope, $http, $location,$window,$filter) {
	
  $http.defaults.headers.post["Content-Type"] = "application/json";
  var baseUrl = ".";
  cargarValores($http,$scope,baseUrl);
  listSellers($http,$scope,baseUrl);
  $scope.proyfilter = {};
  //tablaResponsive();
  
    
 	$http.get(baseUrl + '/findOneSellerUser?username='+$rootScope.userName).
	success(function(response){
		
		$scope.actSel = response;
	});
 	
 	
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
	 
	 $scope.uploadFiles = function(file, errFiles) {
	        $scope.f = file;
	        $scope.errFile = errFiles && errFiles[0];
	        //alert("Entre a Files : ");
	        if (file) {
	            file.upload = Upload.upload({
	                url: baseUrl + '/upload',
	                data: {file: file, idProyect: $scope.currProyect.idProyect, username : $rootScope.userName}
	                
	            });
                
	            $scope.prFiles = {};
	            $scope.activitysPr = {};
	            file.upload.then(function (response) {
	                $timeout(function () {
	                    file.result = response.data;
	                });
	            }, function (response) {
	                if (response.status > 0)
	                    $scope.errorMsg = response.status + ': ' + response.data;
	            }, function (evt) {
	                file.progress = Math.min(100, parseInt(100.0 * 
	                                         evt.loaded / evt.total));
	                listar_files($http,$scope,baseUrl,$scope.currProyect.idProyect);
	                listar_activitysPr($http,$scope,baseUrl,$scope.currProyect.idProyect);
	                
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
		    		alert("Amount Total Invoices must be equal than Amount Total Expense");
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



app.controller('profileController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$sce',
                                		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService, $sce) {


       	$http.defaults.headers.post["Content-Type"] = "application/json";
       	var baseUrl = ".";
       	$scope.currProyect = {};
       	$scope.currProyect = $rootScope.proyActual;
       	$scope.newPassword = null;

    	$http.get(baseUrl + '/findOneSellerUser?username='+$rootScope.userName).
		success(function(response){
			
			$scope.profileSel = response;

 		});
    	
		$scope.updateProfile = function(index){    
			
			//alert("Entre Actualizar fdsfds  " + $scope.profileSel.newPassword);
	    	
			if ($scope.changePassword == "1"){
				
				//alert("Si Cambiare Password");
				$http.get(baseUrl + '/seller/update?changeP=1&username='+$rootScope.userName+'&email='+$scope.profileSel.email+'&phone='+$scope.profileSel.mobilPhone+'&password='+$scope.profileSel.newPassword).
				success(function(response){
					
					notificationService.success('Update Profile Sucessfully');
					$scope.changePassword = "0";
					$scope.newpassword = "";
					$scope.repnewpassword = "";
					
				});				
				
			}else{
				//alert("No Cambiare");
				$http.get(baseUrl + '/seller/update?changeP=0&username='+$rootScope.userName+'&email='+$scope.profileSel.email+'&phone='+$scope.profileSel.mobilPhone+'&password=no').
				success(function(response){
					
					notificationService.success('Update Profile Sucessfully');
					$scope.changePassword = "0";
					$scope.newpassword = "";
					$scope.repnewpassword = "";
					
				});
			}
			

			$scope.modalInstance.close();
			
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

//------------------------------------------------------------------------------------------------------------
//----------------------                    SUB-CONTRATORS    ----------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("subcontController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listar_subcont($http,$scope,baseUrl);
		listar_subcontrators($http,$scope,baseUrl);

		//alert("ENTRE");
		
		$scope.addSubContrator = function(){   	
	    
	    	$http.post(baseUrl + '/subcontrator?idType='+$scope.typesub, $scope.subContrator ).
			success(function(response){
				$('#addSubContrator').modal('hide');
				//alert("Tipo : " + $scope.typesub);
				notificationService.success('SubContrator Sucessfully registered');
				$scope.subContrator = {};
				listar_subcontrators($http,$scope,baseUrl);
				
	
	
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
		
		
		$scope.removeSubContrator = function(index){    	
				
			
			$http.put(baseUrl + '/subcontrator/' +$scope.subCont[index].idSubContrator, $scope.subCont[index]).
			success(function(response){
				    notificationService.success('SubContrator Sucessfully Removed');
				    listar_subcontrators($http,$scope,baseUrl);
			});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editClick = function(index){
				$http.get(baseUrl + "/findOneSubContractor?id=" + $scope.subCont[index].idSubContrator).success(function (data) {      
					$scope.subContrator = data; 

				});
		}
		
		$scope.editSubContrator = function(){   	
	    	
		$http.put(baseUrl + '/subcontrator/edit/' + $scope.subContrator.idSubContrator, $scope.subContrator ).
		success(function(response){
			$('#editSubCont').modal('hide');
			notificationService.success('SubContrator Sucessfully Update');
			listar_subcontrators($http,$scope,baseUrl);

		});
       }	
		
		
		
}]);


//------------------------------------------------------------------------------------------------------------
//----------------------                    INVENTORY    ----------------------------------------------------- //
//------------------------------------------------------------------------------------------------------------

app.controller("inventoryController", ['$scope','$http','$filter','$uibModal','notificationService',function($scope, $http,$filter,$uibModal,notificationService) {
		
		$http.defaults.headers.post["Content-Type"] = "application/json";
		var baseUrl = ".";
		listar_inventory($http,$scope,baseUrl);
		$scope.inventory = {};

		//alert("ENTRE");
		
		$scope.addInventory = function(){   	
	    
	    	$http.post(baseUrl + '/inventory', $scope.inventory ).
			success(function(response){
				$('#addInventory').modal('hide');
				//alert("Tipo : " + $scope.typesub);
				notificationService.success('Item Inventory was Sucessfully registered');
				$scope.inventory = {};
				listar_inventory($http,$scope,baseUrl);
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
		
		
		$scope.removeInventory = function(index){    	
				
			
			$http.put(baseUrl + '/inventory/' +$scope.invent[index].idInventory, $scope.invent[index]).
			success(function(response){
				    notificationService.success('Inventory Sucessfully Removed');
				    listar_inventory($http,$scope,baseUrl);
			});
			$scope.modalInstance.close();
			
		}	
		
		$scope.editClick = function(index){
				$http.get(baseUrl + "/findOneInventory?id=" + $scope.invent[index].idInventory).success(function (data) {      
					$scope.inventory = data; 

				});
		}
		
		$scope.editInventory = function(){   	
	    	
		$http.put(baseUrl + '/inventory/edit/' + $scope.inventory.idInventory, $scope.inventory ).
		success(function(response){
			$('#editSubCont').modal('hide');
			notificationService.success('Inventory Sucessfully Update');
			listar_inventory($http,$scope,baseUrl);
			
			$scope.inventory = {};

		});
     }	
		
		
		
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







//-------------------------------------------------------------------------------------------
//----------------      Wizard Add New QUOATE LEAD   -----------------------------------------
//-------------------------------------------------------------------------------------------


app.controller('quoteleadController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','$uibModal','Upload', '$timeout',
		  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,$uibModal,Upload, $timeout) {

	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	$scope.newNo = 0;
	$scope.newYes = 0;
	$scope.quote = {};
	$scope.item = {};
	$scope.item.drawingAva = '0';
	$scope.item.newPart = '0';
	$scope.item.sampleAva = '0';
	$scope.item.concernsCurr = '0';
	$scope.item.cadfileAva = '0';
	$scope.item.packageReq = '0';
	$scope.item.partKissCut = '0';
	$scope.totItems = 1;
	$scope.userActual = $rootScope.userName;
	init_icheck();
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
	listquotes($http,$scope,baseUrl);
	
	  $scope.client = {
			    primary_address: {},
			    countries: [{
			      name: 'Australia'
			    }, {
			      name: 'United States'
			    }, {
			      name: 'United Kingdom'
			    }]
			  };
	
	if ($rootScope.role == '2')
	      listquotesseller($http,$scope,baseUrl, $rootScope.user.username);
	
	//listtempitems($http,$scope,baseUrl);
	
    $scope.validateCheck = function()
    {
    	
    	//alert("Click Checkbox");
    	if ($scope.newYes == "1"){
    		$scope.newNo = "0";
    		//init_icheck();
    		$scope.quote = {};
    	}
    	
    	if ($scope.newNo == "1"){
    		$scope.newYes = "0";
    		$scope.quote = {};
    	}
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
            	
    	    	/*if (($scope.proyect.typeConstruction == 0) && ($scope.proyect.typeRoof == 0) && ($scope.proyect.typeWinDoors == 0) && ($scope.proyect.typeSolarPanels == 0) && ($scope.proyect.typeAc == 0) && ($scope.proyect.typeConcrete == 0)){
    	    		notificationService.error('You Must Select at Least one Proyect Type');
    	    		$('#wizard').smartWizard('setError',{stepnum:1,iserror:true});
    	    		isStepValid = false;
    	    		
    	    	}  

    	    	if (($scope.proyect.contrator == null) || ($scope.proyect.paymentMethod == null)){
    	    		notificationService.error('You Must Complete all fields');
    	    		isStepValid = false;
    	    	} */
            	console.log("Step 1");
    	    	
            }
            
            if (stepnumber == 2){
            	
            	/* if ($scope.proyect.numOwners == 0){
    	    		notificationService.error('You Must include at least One Owner');
    	    		isStepValid = false;            		
            	} */
            	
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
            	
            	console.log("Step 3");
            	
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

          
            alert("Starting to Save" + $rootScope.user);
        
            
           /* $http.post(baseUrl + '/proyect?idAddress='+$rootScope.address.idAddress, $scope.proyect ).
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
    			       
    		});	 */
        	
            $scope.quote.user = $rootScope.user;
            
            if ($scope.newYes == "1")
            		$scope.quote.newCustomer = "1"
            	else
            		$scope.quote.newCustomer = "0";
            
        	$http.post(baseUrl + '/quote?sessionId='+$rootScope.idSesion, $scope.quote ).
    		success(function(response){
    			   notificationService.success('Quote Sucessfull Added');	
	 		       //$location.path("./Proyect-Detail");
      			   $scope.quote= response;
      	    return $http.get(baseUrl + '/qoDetail?id='+$scope.quote.idQuote)
    		}).then(function(response){
    					
    		    		   $scope.quoDet = response.data;
    				   	   //alert("SCOPE DETALLE PROY " + $scope.proyDet.companyId);
    		    		   
    		    		   $rootScope.quoActual = $scope.quoDet;
    		    		   $window.location.assign(baseUrl + '/sealhome#/quote-detail');	
    			       
    		});	       	
        	

	    	
        }); 
      });
	
	  
      $scope.addTempItem = function() {
        
    	//alert("Guardando Item Temporal...");
    	alert($rootScope.idSesion + ' User : ' + $rootScope.user);  
    	$scope.item.sessionId =  $rootScope.idSesion;
    	$scope.item.users = $rootScope.user;
	    	$http.post(baseUrl + '/tempitem', $scope.item ).
			success(function(response){
				notificationService.success('Item Sucessfull Added');	
				listtempitems($http,$scope,baseUrl,$rootScope.user.username,$rootScope.idSesion);
				$scope.item = {};
				
	   });	    	
    	
    	
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
  	    	    alert($rootScope.quoActual);
  	    	    
  				$window.location.assign(baseUrl + '/sealhome#/quote-detail');
  				
  	        }
  		});		    	
      		    	
       }      
      
	
}]);


app.controller('detQuotesController',['$rootScope', '$scope', '$http', '$location','$window','$filter','notificationService','Upload', '$timeout','$uibModal',
	  function($rootScope, $scope, $http, $location,$window,$filter,notificationService,Upload, $timeout,$uibModal) {

	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	$scope.currQuote = {};
	$scope.currQuote = $rootScope.quoActual;
	listquoteitems($http,$scope,baseUrl,$rootScope.quoActual.id);
	listquotenotes($http,$scope,baseUrl,$rootScope.quoActual.id);
	$scope.itemActivity  = {};
	
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
	
	$scope.buttLeadAct = function(index){
		 $scope.showFields = "1";
	}
	
	
	
	
	$scope.clickitemActivity = function(index){
		
		$scope.indiceAct = index;
		alert($scope.itemsquote[index].idItem);
		listitemact($http,$scope,baseUrl,$scope.itemsquote[index].idItem);
		$scope.showFields = "0";
		$scope.itemActual = $scope.itemsquote[index].idItem;
		//listquotenotes($http,$scope,baseUrl,2);

		
		/*$scope.leadAct = $scope.leads[index].idLead;
		
		$scope.actL = $scope.leads[index];
		$scope.nameCustomer = $scope.actL.contactName;
		$scope.LeadActivity.descActivity = " ";*/
		
	}
		
	

	
	
}]);	
//-------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------

	
app.controller("MainController", ['$scope',function($scope) {
	/* angular.element(document).ready( function () {
         dTable = $('#tbdata')
         dTable.DataTable();
     }); */
	//dashboard1();
}]);

