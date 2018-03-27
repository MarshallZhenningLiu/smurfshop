var smurfs;
var smurfsView;
var smurfsTableView;
var smurfModalView;
var smurf;
var selectRowData;

var Smurf = Backbone.Model.extend({
	initialize: function(){ console.log("--------line 9: creating a new smurf model");},
	urlRoot:"http://localhost:8080/SmurfShop/rest/smurfs"
});
var Smurfs = Backbone.Collection.extend({
	model: Smurf,
	url:"http://localhost:8080/SmurfShop/rest/smurfs"
});

var SmurfView = Backbone.View.extend({
		tagName: "div",
		className: "col-sm-6 col-md-4 col-lg-3 card",			
		render:function(){
			var template = _.template( $("#smurfTemplate").html() );
			var html = template( this.model.toJSON() );
			this.$el.html( html );
			console.log("--------line 19");
			return this;
		}
});

var SmurfsView = Backbone.View.extend({
		render:function(){
			console.log("--------line 26");
			this.collection.each(function(smurf){
				var smurfView = new SmurfView( {model: smurf } );
				this.$el.append( smurfView.render().el );
			}, this );
		}	
});

var SmurfsTableView = Backbone.View.extend({
		initialize: function(){
			//$('#adminTable').DataTable().destroy();
		},
		render:function(){
			$('#adminTable').DataTable().destroy();
			var self = this;
			smurfs = new Smurfs();
			smurfs.fetch({
				success: function(smurfs){
					console.log("----------line 41");
					$('#adminTable').DataTable({
						data: smurfs.toJSON(),
						columns:[
							{ "data": "id" },{ "data": "name" },{ "data": "instock" },{ "data": "price" },{ "data": "size" },{ "data": "material" },{ "data": "supplier" },
							{"defaultContent": "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Edit</button>"}
						]						
					});	

				}
			});
		}
});

var SmurfModalView = Backbone.View.extend({
		render:function(){
			var template = _.template( $("#smurfModalTemplate").html() );
			var html = template( this.model.toJSON() );
			this.$el.html( html );
			console.log("--------line 60");
			return this;
		}	
});

//---------------------------------------------------------DOM
$(document).ready(function(){
	
	$("#productsPage").click(function(){
		$('#productList').html('');
		smurfs = new Smurfs();
		smurfs.fetch({
			success: function(data){ 	console.log( "--------line 43: success fetched" ); 									
										smurfsView = new SmurfsView({ el:"#productList", collection: smurfs });									
										smurfsView.render();		
			}
		});		
	});	
	
	$("#adminPage").click(function(){	
		console.log("----------line 72");
		smurfsTableView = new SmurfsTableView( );
		smurfsTableView.render();		
	});
	
	$('#adminTable').on( 'click', '.btn-primary', function () {
		console.log("----------line 80");
		selectRowData = $('#adminTable').DataTable().row( $(this).parents('tr') ).data();
		smurf = new Smurf( selectRowData );
		smurfModalView = new SmurfModalView( { el:"#modalBody", model:smurf  } );
		smurfModalView.render();
		$('#deleteButton').prop('disabled', false);		
		$('#newButton').prop('disabled', false);		
		$('#saveButton').hide();		
		$('#updateButton').show(); 
	});	

	$('#newButton').click(function() {
		console.log("--------lin 106");
		$('#newButton').prop('disabled', true);		
		$('#deleteButton').prop('disabled', true);		
		$('#updateButton').hide();		
		$('#saveButton').show();		
		smurf = new Smurf( {id:"",name:"",instock:0,price:0,size:"",material:"",supplier:"",image:"Default.jpg",description:""} );
		smurfModalView = new SmurfModalView( { el:"#modalBody", model:smurf  } );
		smurfModalView.render();
	});
	
	$('#saveButton').click(function() {
		console.log("--------lin 116");
		smurf = new Smurf( {name:$('#smurf_name').val(),instock:$('#smurf_instock').val(),price:$('#smurf_price').val(),size:$('#smurf_size').val(),material:$('#smurf_material').val(),supplier:$('#smurf_supplier').val(),image:$('#smurf_image').val(),description:$('#smurf_description').val()} );
		smurf.save({},{
			type:'POST',
			success: function(){ 	alert("new smurf created successfully");
									smurfsTableView.render();},
			error: function(){ alert("error with creating new smurf") }
		});	
	});

	$('#updateButton').click(function() {
		console.log("--------lin 130");
		smurf = new Smurf( {id:$('#smurf_id').val().trim(),name:$('#smurf_name').val(),instock:$('#smurf_instock').val(),price:$('#smurf_price').val(),size:$('#smurf_size').val(),material:$('#smurf_material').val(),supplier:$('#smurf_supplier').val(),image:$('#smurf_image').val(),description:$('#smurf_description').val()} );
		smurf.save({},{
			success: function(){ 	alert("smurf updated successfully");
									smurfsTableView.render();},
			error: function(){ alert("error with updating smurf") }
		});		
	});
	
	$('#deleteButton').click(function() {
		console.log("--------lin 129");
		smurf = new Smurf( {id:$('#smurf_id').val().trim()} );
		smurf.destroy({
			success: function(){ 	alert("smurf deleted successfully");
									smurfsTableView.render();},
			error: function(){ alert("error with deleting smurf") }
		});		
	});
	
	

});