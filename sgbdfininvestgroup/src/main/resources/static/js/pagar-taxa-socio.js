/**
 * 
 */
$(document).ready(function(){
	
	$('.table .eBtn').on('click',function(event){
		event.preventDefault();
		var href=$(this).attr('href');
		var text=$(this).text();
		if(text=='Editar'){
			jQuery.get(href,function(taxa,status){
					
			$('.myForm #id').val(taxa.id);
			$('.myForm #recebido').val(taxa.recebido);
			$('.myForm #t_estapaga').val(taxa.t_estapaga);
			$('.myForm #multado').val(taxa.multado);
			
		});
			
			$('.myForm #exampleModal').modal();
		
		}else {
			//jQuery.get(href,function(user,status);
			$('.myForm #id').val('0');
			$('.myForm #recebido').val('');
			$('.myForm #t_estapaga').val('');
			$('.myForm #multado').val('');
			
			$('.myForm #exampleModal').modal();
		}
	});
	
	
	
});
  





