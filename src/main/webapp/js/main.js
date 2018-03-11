/*
file:///C:/Users/m/git/smurfshop/src/main/webapp/index.html
In Windows, paste this command in run window
chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security
this will open a new chrome browser which allow access to no 'access-control-allow-origin' header request.

*/
var rootURL = "http://localhost:8080/SmurfShop/rest/smurfs";


// Helper function to serialize all the form fields into a JSON string
var formToJSON=function () {
	
	return JSON.stringify({
		"id": $('#smurf_id').val(),		"name": $('#smurf_name').val(),		"instock": $('#smurf_instock').val(),		"price": $('#smurf_price').val(),		"size": $('#smurf_size').val(),		"material": $('#smurf_material').val(),		"supplier": $('#smurf_supplier').val(),		"image": $('#smurf_image').val(),		"description": $('#smurf_description').val()		
	});
};



//------------------------------------------------------------------------------------------------------
//products clicked
var showAllSmurfs=function () {	
	$.ajax({
		type: 'GET',	url: rootURL,	data: { get_param: 'value' }, dataType: "json", 	
		success: function(data){
			$.each(data, function(index,element){
				$('#productList .row').append('<div class="col-sm-6 col-md-4 col-lg-3"> <div class="card"><img src="' +element.image+ '" ><p>Name: ' + element.name +'</p><p>Price: '+element.price+'</p></div></div>');				
			});
		}			
	});
};
//Admin clicked
//https://datatables.net/examples/ajax/custom_data_flat.html
//https://datatables.net/examples/ajax/null_data_source.html
var admin_table = $('#adminTable').DataTable();
var showDataTable=function () {	
	admin_table.destroy();
    admin_table = $('#adminTable').DataTable( {				
		destroy: true,
        "ajax": {
			"type":"GET",
            "url": rootURL,
            "dataSrc": ""
        },
        "columns": [
            { "data": "id" },			{ "data": "name" },            { "data": "instock" },            { "data": "price" },                       { "data": "size" },			{ "data": "material" },            { "data": "supplier" },
			//edit button to open modal
			{"defaultContent": "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Edit</button>"}
        ]
    } );
};
//delete clicked
var deleteSmurf=function () {	
	$.ajax({
		type: 'DELETE',	url: rootURL+'/'+$('#smurf_id').val(),	
		success: function(data, textStatus, jqXHR){
			alert('smurf deleted successfully');             
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteSmurf error: ' + textStatus);
		}
	});

};
//create clicked
var createSmurf=function () {	
	$.ajax({
		type: 'POST',contentType: 'application/json',url: rootURL,dataType: "json",	data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('smurf created successfully');             
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('createSmurf error: ' + textStatus);
		}
	});
};
//update clicked
var updateSmurf=function () {	
	$.ajax({
		type: 'PUT', contentType: 'application/json', url: rootURL+'/'+$('#smurf_id').val(),dataType: "json",data: formToJSON(),	
		success: function(data, textStatus, jqXHR){
			alert('smurf updated successfully');             
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateSmurf error: ' + textStatus);
		}
	});
};

//--------
	
//--------------------------------------------------------------------------------------------------------------------
//When the DOM is ready.
$(document).ready(function(){
	$("#homePage").click(function(){
		
	});
	
	$("#productsPage").click(function(){		
		$('#productList .row').html('');
		showAllSmurfs();
		
	});	
		
	$("#adminPage").click(function(){		
		showDataTable();
		$('#saveButton').hide();
		
	});
	
	$('#newButton').click(function() {
		$('#newButton').prop('disabled', true);		$('#deleteButton').prop('disabled', true);		$('#updateButton').hide();		$('#saveButton').show();		$('#smurf_id').val("");		$('#smurf_id').prop('disabled', true);		$('#smurf_name').val("");		$('#smurf_instock').val("");		$('#smurf_price').val("");		$('#smurf_material').val("");		$('#smurf_supplier').val("");		$('#smurf_image').val("");		$('#smurf_description').val("");		$('#smurf_size').val("");		
		
	});
	$('#saveButton').click(function() {
		createSmurf();
		showDataTable();
		
	});

	$('#updateButton').click(function() {
		updateSmurf();
		showDataTable();
		
	});
	$('#deleteButton').click(function() {
		deleteSmurf();
		showDataTable();
		
	});
	
	//click edit button
	//https://jqueryui.com/dialog/#modal-form modal popup.
	$('#adminTable tbody').on( 'click', 'button', function () {
		$('#deleteButton').prop('disabled', false);		$('#newButton').prop('disabled', false);		$('#saveButton').hide();		$('#updateButton').show();        
		var element = admin_table.row( $(this).parents('tr') ).data();		
		$('#smurf_id').val(element.id);		$('#smurf_id').prop('disabled', true);		$('#smurf_name').val(element.name);		$('#smurf_instock').val(element.instock);		$('#smurf_price').val(element.price);		$('#smurf_material').val(element.material);		$('#smurf_supplier').val(element.supplier);		$('#smurf_image').val(element.image);		$('#smurf_description').val(element.description);		$('#smurf_size').val(element.size);		
    } );
	
	
});

