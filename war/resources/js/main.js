var thiswidth = 0;
var loadsuccess = false;

var progress = function() {

    if (thiswidth>=400) {
        if (!loadsuccess) {
        	$('#errormessage').html('Was unable to load. Try refreshing.');
        	$.unblockUI();
        	$('#ajaxerror').fadeIn();
        }
    } else {
        thiswidth+=4;
    	setTimeout(progress,500);
    }
};

blockUI = function(msg) {
	$.blockUI({ message:msg, css: { 
        border: 'none', 
        padding: '15px', 
        backgroundColor: '#000', 
        '-webkit-border-radius': '18px',
        '-moz-border-radius': '18px',
        'border-radius': '18px', 
        opacity: .5, 
        color: '#fff' 
    } });
};

fmOn = function(msg) {
	if(!msg) msg='<h4>Please wait....</h4>';
	thiswidth = 0;
	loadsuccess = false;
	setTimeout(progress,0);
	 blockUI(msg);
};

showMainContainer = function() {
	$('#maincontainer').show();
};

stopProgressBar = function() {
	thiswidth=400;
	loadsuccess = true;
	showMainContainer();
};


fmOff = function(msg, timeout) {
	if (!timeout) timeout=1000;
	if(msg) blockUI(msg);
	stopProgressBar();
	setTimeout($.unblockUI, timeout);
};

fmError = function(msg) {
	fmOff();
	$('#errormessage').html(msg.responseText);
	$('#ajaxerror').fadeIn();
};

fmStay = function(msg) {
	$f.html(msg);
	$f.fadeIn();
};

reloadPage = function() {
	fmOn('Reloading....');
	location.reload();
};

whosgoing = function(btn) {
	var b = $(btn);
	b.parent().find('.whosgoing').slideToggle('fast');
};


$(document).ready(function() {
	
	$('#progressbar').hide();
	$('#ajaxerror').fadeOut();
	
	$('.toggleme').live('click', (function() {
		$('#'+$(this).attr('toggleid')).slideToggle();
	}));
	
	$('#errorbutton').live('click', function() {
		$('#ajaxerror').hide();
	});
	
	$('.clearEmail').live('click', function() {
		var pa = $( this ).parent().parent();
		pa.find('.contact_email_full').val('');
		pa.find('.contact_email').val( '' );
		pa.find('.contact_fullname').val( '' );
	});
	
	
	$('.contact_email_full').live('focus', function() {
		$(this).autocomplete({
	        minLength: 0,
	        source: my_contacts,
	        focus: function( event, ui ) {
	            $( this ).val( ui.item.label );
	            return false;
	        },
	        select: function( event, ui ) {
	            $( this ).val( ui.item.label );
				var pa = $( this ).parent().parent();
	            pa.find('.contact_email').val( ui.item.email );
	            pa.find('.contact_fullname').val( ui.item.fullname );

	            return false;
	        }
	    });
		$(this).blur(function() {
			$( this ).parent().parent().find('.contact_email').change();
			$( this ).parent().parent().find('.contact_fullname').change();
		});
	});
	
	enableDatePicker = function() {
		$('.dateField').datepicker({ 
				dateFormat: 'yy-mm-dd',
		        showOn: "button",
		        buttonText:'Edit',
		        firstDay: 1,
		        autoSize: true,
		        constrainInput: true,
		        minDate: new Date(),
		        onClose: function( selectedDate ) {
		            if ($(this).data('dateid')=='from') {
		            	// fieldset -> div
		            	$p = $(this).parent().parent();
		            	selector = $p.find('input[data-dateid="to"]');
			            selector.datepicker( "option", "minDate", selectedDate );
			            selector.val(selectedDate);
			            selector.trigger('change');
		            }
		        }
		});
		$('.dateField').attr("disabled", true);
		$('.ui-datepicker-trigger').addClass('btn');
		$('select[data-timeid="fromTime"]').change(function() {
			$('select[data-timeid="toTime"]').val($(this).val());
		});
	};

	
	$('#login').click(function() {
		location.href=("login");
	});
	$('#logout').click(function() {
		location.href=("logout");
	});
	$('#authorise').click(function() {
		location.href=("authoriseGoogle");
	});
	
	
});


/*
 * Extensions to jQuery made available via the jQuery object. 
 * 
 * Author D. Hampton
 */

/**
 * Returns the hidden value field of the current selection
 */
jQuery.fn.selectedVal = function() {
	return $(this).selectedOption().val();
};

/**
 * For check boxes. 
 */ 
jQuery.fn.isChecked = function() {
	return $(this).prop('checked');
};

/**
 * Returns the entire selected option
 */
jQuery.fn.selectedOption = function() {
	return $("#"+$(this).attr("id")+" :selected");
};

/**
 * Returns the visible value field of the current selection
 */
jQuery.fn.selectedText = function() {
	return $(this).selectedOption().text();
};

/**
 * Returns an array of all available options
 */
jQuery.fn.getOptions = function() {
	return $("#"+$(this).attr("id")+" option");
};

/**
 * Will set the option who's value is passed in to this function as
 * selected. All other options are not selected.
 */
jQuery.fn.setSelected = function(value) {
	var options = $(this).getOptions();
	$.each(options, function() {
		if ($(this).val()==value) {$(this).attr("selected", "selected");}
		else {$(this).attr("selected", false);}
	});
};

jQuery.fn.setMultipleSelected = function(value) {
	var options = $(this).getOptions();
	$.each(options, function() {
		if ($(this).val()==value) {$(this).attr("selected", "selected");}
	});
};	

/**
 * Add a new option to the end of a select box
 */
jQuery.fn.addOption = function(value, text) {
	var id = "#"+$(this).attr("id");
	var option = document.createElement("option");
	option.value = value;
	option.text = text; 
	$(id).get(0)[$(id+' option').length] = option;
};

/**
 * As it says on the tin...
 */
jQuery.fn.selectAll = function() {
	$("#"+$(this).attr("id")+" *").attr("selected","selected");
};

/**
 * Give this a URL, as long as the URL returns <name>:<value> pairs
 * in JSON format the these values will be added to the select box
 */
jQuery.fn.loadJsonOptions = function(url) {
	id = "#"+$(this).attr("id");
	$.getJSON(url,
    function(data){
    	$.each(data, function(i, item){
			$(id).addOption(item.value, item.name);
      	});
    });
};

/**
 * Empties the select box
 */
jQuery.fn.clear = function() {
	$("#"+$(this).attr("id")).children().remove();
};

/**
 * Will move the selected item to the receiver, removing it from the initial
 * select box.
 */
jQuery.fn.moveSelectedTo = function(receiver) {
	var id = "#"+$(this).attr("id");
	var o = $(id).selectedOption();
	$(o).remove();
	o.appendTo(receiver);
};

jQuery.fn.removeSelected = function() {
	var id = "#"+$(this).attr("id");
	var o = $(id).selectedOption();
	$(o).remove();
};