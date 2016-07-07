$(document).ready(function() {
	
	// Calcolo il numero di oggetti in istituto
	$('div[class^="istituto_"]').each(function( index ) {
		var counter = 0;
		$('.collections', this).find('.numItems').each(function() {
		    counter += Number($(this).text());
		});
		$(this).prev('h1, h2, h3, h4, h5, h6').children('span').html(' ('+counter+')');
	});
	
    // TORNA SU
    $('.back-top a').click(function () {
        $('body,html').animate({
                scrollTop: 0
        }, 800);

        return false;
    });

    // ACCORDION PANEL HOME
    $(function(){
        $(".accordion-panel-home .expanded").not(".open").hide();
        $(".accordion-panel-home .opening").click(function(){
        	var clicked = $(this);
        	if(!clicked.hasClass("active")){
	            $(".accordion-panel-home .expanded.open").slideToggle('fast', function(){
	                $(this).prev(".opening").toggleClass("active");
	                $(this).toggleClass("open");
	            });
	            clicked.next().slideToggle('fast', function(){
	                $(this).prev(".opening").toggleClass("active");
	                $(this).toggleClass("open");
	            });
        	}
            return false;
        });
    });

    $(function() {
    	$( "#accordion" ).accordion({
    		heightStyle: "content"
    	});
    });
    
    // ACCORDION PANEL LISTA FILTRI RISULTATI RICERCA
    $(function(){
        $(".accordion-panel-advsearch .expanded").not(".open").hide();
        $(".accordion-panel-advsearch .opening").click(function(){
        	var clicked = $(this);
            clicked.next().next().slideToggle('fast', function(){
                $(this).prev().prev(".opening").toggleClass("active");
                $(this).toggleClass("open");
            });
            return false;
        });
    });
    
	//FORM DATEPICKER DEMO
    /*
	$(function () {
		$('#inputDataUno').datetimepicker({
			language: 'it',
			pickDate: 'dd/mm/yyyy',
			pickTime: false
		});
		$('#inputDataDue').datetimepicker({
			language: 'it',
			pickDate: 'dd/mm/yyyy',
			pickTime: false
		});
		$('#inputDataTre').datetimepicker({
			language: 'it',
			pickDate: 'dd/mm/yyyy',
			pickTime: false
		});
		$('#inputDataQuattro').datetimepicker({
			language: 'it',
			pickDate: 'dd/mm/yyyy',
			pickTime: false
		});
	});
	*/
    
    // ICHECK
    $('.custom-check').iCheck({
        checkboxClass: 'icheckbox_polaris',
        radioClass: 'iradio_polaris'
        //increaseArea: '-10%' // optional
    });    
    
    //CAROUSEL
    $('.sky-carousel').carousel({
        itemWidth: 170,
        itemHeight: 240,
        distance: 15,
        selectedItemDistance: 50,
        selectedItemZoomFactor: 1,
        unselectedItemZoomFactor: 0.67,
        unselectedItemAlpha: 0.6,
        motionStartDistance: 170,
        topMargin: 100,
        gradientStartPoint: 0.35,
        gradientOverlayColor: "#f5f5f5",
        gradientOverlaySize: 190,
        reflectionDistance: 1,
        reflectionAlpha: 0.35,
        reflectionVisible: true,
        reflectionSize: 70,
        selectByClick: false,
        autoSlideshow: true,
        loop: true,
        preload: true
    });
    
    $('#advancedSearchAddRow').click(function (){
    	
    	advancedSearchRowCount = $("#advancedSearchCompositionCnt").children().length-2;
    	
    	$clone = $('#advancedSearchFakefilter').clone();
    	
    	$clone.attr('id','wewe'+advancedSearchRowCount);
    	
    	$('#logicalOperator-XX',$clone)
    		.attr('name','compositionFilters['+advancedSearchRowCount+'].logicalOperator')
    		.attr('id','logicalOperator-'+advancedSearchRowCount);
    	$('#filterType-XX',$clone)
    		.attr('name','compositionFilters['+advancedSearchRowCount+'].filterType')
    		.attr('id','filterType-'+advancedSearchRowCount);
    	$('#fakeFilter-XX',$clone)
    		.attr('name','compositionFilters['+advancedSearchRowCount+'].comparisonValue')
    		.attr('id','comparisonValue-'+advancedSearchRowCount);
    	
    	$clone.insertBefore('#advancedSearchFakefilter').removeClass('hidden');
    });

    $('.changeViewTypeList').click(function(){
    	$(this).siblings('.changeViewType').val('list');
    	$(this).closest("form").submit();
    });
    $('.changeViewTypePreview').click(function(){
    	$(this).siblings('.changeViewType').val('preview');
    	$(this).closest("form").submit();
    });
    $('.changeOrderBy').change(function(){
    	$(this).closest("form").submit();
    });
    $('.changeResPP').change(function(){
    	$(this).closest("form").submit();
    });
    
    $('.semantic-buttons button').click(function(){
    	$(this).siblings().removeClass('active');
    	$(this).addClass('active');
    });
    
    $('#search-semantic-form').submit(function(){
		if($("#search-semantic-field").val().trim()==''){
			$('#dialog-message p').removeClass('hidden').html('Inserisci una chiave di ricerca.');
			$("#dialog-message").dialog();
			return false;
		}
		var typeToSearchNum = "0";
    	var typeToSearch = $('.semantic-buttons .active').attr('id');
		if(typeToSearch!='semantic-button-all'){
			typeToSearchNum = typeToSearch.replace('semantic-button-', ''); 
		}
		$("#search-semantic-form-title input[name=cdItemType]").val(typeToSearchNum);
		$("#search-semantic-form-title input[name=descrTitle]").val($("#search-semantic-field").val());
		$("#search-semantic-form-title").submit();
    	return false;
    });
    
    $.widget( "custom.catcomplete", $.ui.autocomplete, {
    	_create: function() {
	    	this._super();
	    	this.widget().menu( "option", "items", "> :not(.ui-autocomplete-category)" );
    	},
    	_renderMenu: function( ul, items ) {
	    	var that = this,
	    	currentCategory = "";
	    	$.each( items, function( index, item ) {
		    	var li;
		    	if ( item.category != currentCategory ) {
		    		ul.append( "<li class='ui-autocomplete-category'>" + item.category + "</li>" );
		    		currentCategory = item.category;
		    	}
		    	li = that._renderItemData( ul, item );
		    	if ( item.category ) {
		    		li.attr( "aria-label", item.category + " : " + item.name );
		    	}
	    	});
    	},
    	_renderItem :function( ul, item ) {
    		return $( "<li>" ).append( "<a>").text(item.name).appendTo( ul );
    	}
    });
    $( "#search-semantic-field" ).catcomplete({
    	minLength: 3,
    	delay: 500,
    	source: function( request, response ) {
    		var typeToSearchNum = "0";
    		var typeToSearch = $('.semantic-buttons .active').attr('id');
    		if(typeToSearch!='semantic-button-all'){
    			typeToSearchNum = typeToSearch.replace('semantic-button-', ''); 
    		}
    		
    		$.ajax({
    			type: "POST",
    			url: $('#linkToSearchSemantic').val(),
    			dataType: "json",
    			data: {
    				textToSearch: $("#search-semantic-field").val(),
    				typeToSearch: typeToSearchNum
    			},
    			success: function( data ) {
    				response( data );
    			}
    		});
    	},
    	select: function( event, ui ) {
    		 /*console.log( ui.item.id+" "+ui.item.name+" "+ui.item.category );*/
    		 if(ui.item.category=='Oggetti Digitali'){
    			 window.location = $('#linkToItem').val()+ui.item.id;
    		 }else if(ui.item.category=='Autori'){
    			 $("#search-semantic-form-author input[name=cdAuthor]").val(ui.item.id);
    			 $("#search-semantic-form-author input[name=descrAuthor]").val(ui.item.name);
    			 $("#search-semantic-form-author").submit();
    		 }else if(ui.item.category=='Soggetti'){
    			 $("#search-semantic-form-subject input[name=cdSubject]").val(ui.item.id);
    			 $("#search-semantic-form-subject input[name=descrSubject]").val(ui.item.name);
    			 $("#search-semantic-form-subject").submit();
    		 }
    		 return false;
    	}
    });
    
    // IMPLEMENTO IL FILTRO "AMBITO DISCIPLINARE" SULLE COLLEZIONI
    $('.areas').click(function() {
    	console.log('filtro');
    	$('.collections').not('.'+$(this).attr('id')).hide().removeClass('daContare');
    	$('.collections').filter($('.'+$(this).attr('id'))).show().addClass('daContare');;
    	
    	$('div[class^="istituto_"]').each(function( index ) {
		 var counter = 0;
		 $('.daContare', this).find('.numItems').each(function() {
  			counter += Number(this.innerText);
  		  });
		 $(this).prev('h2').children('span').html(' ('+counter+')');
		});

	});
    $('#area_all').click(function() {
    	$('.collections').show();
    	
    	$('div[class^="istituto_"]').each(function( index ) {
    		 var counter = 0;
  		 $('.collections', this).find('.numItems').each(function() {
  			counter += Number(this.innerText);
  			
  		  });
  		$(this).prev('h2').children('span').html(' ('+counter+')');
  		});
	});
    
    // GESTIONE DEL POPUP PER I CONTENUTI AUDIO
    $('.audioLogo').click(function(event) {
    	event.preventDefault();
    	window.open($(this).attr('href'), 'BDLAUDIO', 'menubar=no,location=no,resizable=yes,scrollbars=yes,status=no,width=600,height=350');
    	return false;
    });
    $('.audioLink').click(function(event) {
    	event.preventDefault();
    	window.open($(this).attr('href'), 'BDLAUDIO', 'menubar=no,location=no,resizable=yes,scrollbars=yes,status=no,width=600,height=350');
    	return false;
    });
    
    

    $('#filterDateOption').change(function(){
    	toggleDateFields($(this).val());    	
    });
    $('#filterDateOption').each(function(){
    	toggleDateFields($(this).val());    	
    });
    
});

//Gestisco mouseover/onclick su tutto il div/box contenitore
$(document).delegate(".toclick", "click", function() {
	   window.location = $(this).find("a").attr("href");
});

window.onload = function() {
	// GESTISCO IL LINK A NUOVA FINESTRA
	if (!document.getElementsByTagName)
		return;
	l = document.getElementsByTagName("a");
	for (var i = 0; i < l.length; i++) {
		if (l[i].className.indexOf("targetblank") != -1) {
			l[i].onclick = function() {
				window.open(this.href);
				return (false);
			};
		}
	}
};


function toggleDateFields(dateOptionValue){

	$('#inputDataUno').hide();
	$('#inputDataDue').hide();
	
	if(dateOptionValue=='RANGE'){
		$('#inputDataUno').show();
		$('#inputDataDue').show();
	}else if(dateOptionValue=='SECOLO'){
		$('#inputDataUno').show();
	}else if(dateOptionValue=='DATACERTA'){
		$('#inputDataUno').show();
	}else if(dateOptionValue=='CIRCA'){
		$('#inputDataUno').show();
	} 	
}