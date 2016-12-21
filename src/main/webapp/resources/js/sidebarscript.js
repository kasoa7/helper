$(document).ready(function () {
	$('#menu-toggle').click(function(){
		if($('#menu2').hasClass('open')){
			$('#menu2').removeClass('open');
			$('#menu-toggle').removeClass('open');
		}else{
			$('#menu2').addClass('open');
			$('#menu-toggle').addClass('open');
		}
	});
});