
function GetURLParameter(sParam){
	var sPageURL = window.location.search.substring(1);
	sPageURL = sPageURL.replace('&amp;','&');
	sPageURL = sPageURL.replace('&#38;','&');
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) 
	{
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam) 
		{
			return sParameterName[1];
		}
	}
}

var br = new BookReader();
$(document).ready(function(){

	var BDL_CD_OGGETTO = GetURLParameter('cdOggetto');
	if(typeof BDL_CD_OGGETTO == 'undefined' ){
		alert('Non rilevo il parametro cdOggetto');
	}

	var BDL_SERVICE_PATH = "private";
	if(GetURLParameter('path')=="fe") {
		BDL_SERVICE_PATH = "public";
	} else if(GetURLParameter('path')=="be") {
		BDL_SERVICE_PATH = "private";
	}
	
	/**
	 * TODO: XXX
	 */
	br.logoURL = 'TODO: XXX';
	br.imagesBaseURL = './BookReader/images/';
	br.digital_object_base_path  = "../"+BDL_SERVICE_PATH+"/rest/srv/item/" + BDL_CD_OGGETTO+"/images/reader/";
	br.digital_object_zoom_path  = "../"+BDL_SERVICE_PATH+"/rest/srv/item/" + BDL_CD_OGGETTO+"/images/zoom/";
	br.digital_object_pdf_path   = "../"+BDL_SERVICE_PATH+"/rest/srv/item/" + BDL_CD_OGGETTO+"/pdf/";
	br.digital_object_item_path  = "../"+BDL_SERVICE_PATH+"/rest/json/item/" + BDL_CD_OGGETTO;
	br.digital_object_pages_path = "../"+BDL_SERVICE_PATH+"/rest/json/item/" + BDL_CD_OGGETTO+"/bookreader/pages";
	br.digital_object_toc_path   = "../"+BDL_SERVICE_PATH+"/rest/json/item/" + BDL_CD_OGGETTO+"/bookreader/toc";
	br.search_url                = "../"+BDL_SERVICE_PATH+"/rest/json/item/" + BDL_CD_OGGETTO+"/bookreader/ocr"; 

	br.getPageWidth = function(index) {
		//console.log("this.bdlPages["+index+"]="+this.bdlPages[index]);
		if(isNaN(index) || index<0)
			return;
		if(typeof this.bdlPages[index]=='undefined'){
			return br.getPageWidth(index-1);
		}else{
			return this.bdlPages[index].readerWidth;
		}
	};
	br.getPageHeight = function(index) {
		//console.log("this.bdlPages["+index+"]="+this.bdlPages[index]);
		if(isNaN(index) || index<0)
			return;
		if(typeof this.bdlPages[index]=='undefined'){
			return br.getPageHeight(index-1);
		}else{
			return this.bdlPages[index].readerHeight;
		}
	};

	br.getPageURI = function(index, reduce, rotate) {
		return br.digital_object_base_path + br.bdlPages[index].id+"";
	};

	br.getPageURIZoom = function(index, reduce, rotate) {
		pageId = br.bdlPages[index].id;
		pageId = pageId.replace("DLX","");
		pageId = pageId.replace("DRX","");
		return br.digital_object_zoom_path + pageId+"";
	};
	
	br.getPageURIPdf = function(index) {
		pageId = br.bdlPages[index].id;
		pageId = pageId.replace("DLX","");
		pageId = pageId.replace("DRX","");
		return br.digital_object_pdf_path + pageId+"";
	};

	br.getPageSide = function(index) {
		if (0 == (index & 0x1)) {
			return 'R';
		} else {
			return 'L';
		}
	};
	
	br.getIndexByPageId = function(pageId){
		for(i=0;i<br.bdlPages.length;i++){
			if(br.bdlPages[i].id == pageId){
				return i;
			} 
		}
		return false;
	};
	
	br.getSpreadIndices = function(pindex) {   
		var spreadIndices = [null, null]; 
		if ('rl' == this.pageProgression) {
			// Right to Left
			if (this.getPageSide(pindex) == 'R') {
				spreadIndices[1] = pindex;
				spreadIndices[0] = pindex + 1;
			} else {
				// Given index was LHS
				spreadIndices[0] = pindex;
				spreadIndices[1] = pindex - 1;
			}
		} else {
			// Left to right
			if (this.getPageSide(pindex) == 'L') {
				spreadIndices[0] = pindex;
				spreadIndices[1] = pindex + 1;
			} else {
				// Given index was RHS
				spreadIndices[1] = pindex;
				spreadIndices[0] = pindex - 1;
			}
		}

		return spreadIndices;
	};

	br.getPageName = function(index) {
		return 'Pagina ' + this.getPageNum(index);
	};

	br.getPageNum = function(index) {
		var pageNum = this.bdlPages[index];
		if (pageNum) {
			return index+1;
		} else {
			return "n" + index;
		}
	};

	br.getEmbedCode = function(frameWidth, frameHeight, viewParams) {
		return "Embed code not supported in bookreader demo.";
	};

	br.initUIStrings = function(){
		var titles = { '.logo': 'Vai al sito di BDL', 
				'.zoom_in': 'Aumenta Zoom',
				'.zoom_out': 'Diminuisci Zoom',
				'.onepg': 'Vista a una pagina',
				'.twopg': 'Vista a due pagine',
				'.thumb': 'Vista anteprime',
				'.print': 'Stampa pagina',
				'.embed': 'Embed BookReader',
				'.link': 'Collega a questa pagina',
				'.bookmark': 'Salva nei preferiti',
				'.read': 'Leggi pagina',
				'.share': 'Condividi libro',
				'.info': 'Informazioni',
				'.full': 'Visualizza a schermo intero',
				'.book_left': 'Precedente',
				'.book_right': 'Successiva',
				'.book_up': 'Pagina in su',
				'.book_down': 'Pagina in giù',
				'.play': 'Play',
				'.pause': 'Pause',
				'.BRdn': 'Visualizza/Nascondi', 
				'.BRup': 'Visualizza/Nascondi',
				'.book_top': 'Prima Pagina',
				'.book_bottom': 'Ultima Pagina'
		};
		if ('rl' == this.pageProgression) {
			titles['.book_leftmost'] = 'Ultima Pagina';
			titles['.book_rightmost'] = 'Prima Pagina';
		} else { // LTR
			titles['.book_leftmost'] = 'Prima Pagina';
			titles['.book_rightmost'] = 'Ultima Pagina';
		}

		for (var icon in titles) {
			if (titles.hasOwnProperty(icon)) {
				$('#BookReader').find(icon).attr('title', titles[icon]);
			}
		}
	};
	$.ajaxSetup({
		"error":function(data) { alert("Si verifica un errore "+data.status+" nel reperimento della risorsa");console.log( 'ERROR: ', data );  }
	});


	$.getJSON(br.digital_object_item_path, function(json){
		br.bookTitle                = json.title;
		br.bookUrl                  = "";
		
		br.mode                     = 2;
		if(json.digitalReader=="singleimage"){
			br.mode                 = 1;			
		}
		
		
		br.reductionFactors         = [
		                               {reduce: 0.50,  autofit: null},
		                               {reduce: 1,     autofit: null},
		                               {reduce: 2,     autofit: null},
		                               {reduce: 3,     autofit: null},
		                               {reduce: 4,     autofit: null}
		                               ];
		br.pageProgression          = "lr";

		/* Imposto isPdfMultipagina */
        br.isPdfMultipagina = false;
		if(json.isPdfMultipagina=='T'){
			br.isPdfMultipagina = true;
		}
		
		/* Imposto isRicercaOcr */
        br.isRicercaOcr = false;
		if(json.isRicercaOcr=='T'){
			br.isRicercaOcr = true;
		}

		$.getJSON(br.digital_object_pages_path, function(json){

			br.bdlPages = json;
			br.numLeafs = json.length;
			//br.cleanupMetadata();

			br.init();

			$("#textSrch").attr("placeholder","Ricerca testuale");
			$("#btnSrch").text("Vai");

			$("<button class='BRicon pdf'></button>").insertBefore(".BRicon.zoom_in");
			$("<button class='BRicon magnify'></button>").insertBefore(".BRicon.zoom_in");


			var downloadSinglePdf = function(index) {
				window.location = br.getPageURIPdf(index);
			};
			var downloadSinglePdfRight = function() {
				downloadSinglePdf(br.currentIndex()+1);
			};
			var downloadSinglePdfLeft = function() {
				downloadSinglePdf(br.currentIndex());
			};
			
			var enterPdfDownload = function() {
				console.log("br.mode="+br.mode);
				var nl = br.numLeafs - 1;
				mode = br.mode;
				if(br.bdlPages[br.currentIndex()].doublePage){
					mode = 1;
				}
				switch (mode)
				{
				case 1:
					downloadSinglePdf(br.currentIndex());
					break;
				case 2:
					if(nl%2==1 && nl == br.currentIndex()){
						if(br.pageProgression == 'rl'){
							downloadSinglePdfLeft();
						}else{
							downloadSinglePdfRight();
						}
					}else if(br.currentIndex()==0){
						if(br.pageProgression == 'rl'){
							downloadSinglePdfRight();
						}else{
							downloadSinglePdfLeft();
						}
					}else{
						//console.log("In the middle");
						var down_opts = '<div style="text-align:center"><a href="#" id="downloadPdfPageLeft" class="downloadPdfPageSelect">Download SX</a><a href="#" id="downloadPdfPageRight" class="downloadPdfPageSelect">Download DX</a></div>';
						$('.pdf').bt(down_opts,{
							trigger: 'none',
							positions: 'top',
							width: 150
						});
						$('.pdf').btOn(); 
					}
					break;
				case 3:
					// do nothing...?!
					alert("Impossibile procedere nel download!");
					break;

				default:
					break;
				}		
			};
			$(".BRicon.pdf").click(function() {
				console.log("Downloading PDF");
			    if(br.isPdfMultipagina){
				    enterPdfDownload();
				}else{
				    window.location = br.digital_object_pdf_path;
				}
			});
			$('#downloadPdfPageRight').live('click', downloadSinglePdfRight);
			$('#downloadPdfPageLeft').live('click', downloadSinglePdfLeft);	

			var magnifyZoom = function(index) {
				console.log("Zooming "+index);
				url = br.getPageURIZoom(index, null, null);
				console.log("url "+url);

				$("#magnifyImage").hide();

				$("#magnifyLightbox").fadeIn( "slow" );
				$("#magnifyContainer").fadeIn( "slow" );

				$('#magnifyContainer').css("width", "90%");
				$('#magnifyContainer').css("height", "90%");

				$('#magnifyViewer').css("width", "95%");
				$('#magnifyViewer').css("height", "95%");
				$('#magnifyViewer').css("overflow", "hidden");

				var zW = br.bdlPages[index].zoomWidth;
				var zH = br.bdlPages[index].zoomHeight;
				
				$("#magnifyViewer").append( "<p class='loader' style='text-align:center;font-size:18px;font-family:arial'>Caricamento in corso...</p>" );
				var doneOnce = false;
				$("#magnifyImage").attr("src",url).load(function (){
					if(doneOnce){
						// Attenzione: questo codice è  necessario: 
						// senza questo accorgimento il sistema continua a loopare
						return;
					}
					doneOnce = true;
					containerWidth=$('#magnifyViewer').width();
					containerHeight=$('#magnifyViewer').height();
					
					console.log("Resizing: zW="+zW+" zH="+zH)
					
					$("#magnifyViewer .loader").remove();
					$("#magnifyImage").show();
					
					if(zW>containerWidth){
						zH = Math.round(zH*(containerWidth/zW));
						zW = containerWidth;
						console.log("Resized height to ="+zH);
					}
					if(zH>containerHeight){
						zW = Math.round(zW*(containerHeight/zH));
						zH = containerHeight;
						console.log("Resized width to ="+zW);
					}
					$('#magnifyImage').css('width',zW);
					$('#magnifyImage').css('height',zH);
										
					$('#magnifyImage').wheelzoom();
					
				});


			};
			function closeModal() {
				$("#magnifyLightbox").fadeOut( "slow" );
				$("#magnifyContainer").fadeOut( "slow" );
			}

			$(".closeModal").live( "click", closeModal );
			$("#magnifyLightbox").live( "click", closeModal );


			var magnifyZoomRight = function() {
				magnifyZoom(br.currentIndex()+1);
			};
			var magnifyZoomLeft = function() {
				magnifyZoom(br.currentIndex());
			};
			var enterMagnify = function() {
				//console.log("br.mode = "+br.mode);
				var nl = br.numLeafs - 1;
				mode = br.mode;
				if(br.bdlPages[br.currentIndex()].doublePage){
					mode = 1;
				}
				switch (mode)
				{
				case 1:
					magnifyZoom(br.currentIndex());
					break;
				case 2:

					if(nl%2==1 && nl == br.currentIndex()){
						//console.log("at the end... zoom on left since right is empty!");
						if(br.pageProgression == 'rl'){
							magnifyZoomLeft();
						}else{
							magnifyZoomRight();
						}
					}else if(br.currentIndex()==0){
						//console.log("at the beginning... zoom on right since left is empty!");
						if(br.pageProgression == 'rl'){
							magnifyZoomRight();
						}else{
							magnifyZoomLeft();
						}
					}else{
						//console.log("In the middle");
						var zoom_opts = '<div style="text-align:center"><a href="#" id="magnifyPageLeft" class="magnifyPageSelect">Zoom SX</a><a href="#" id="magnifyPageRight" class="magnifyPageSelect">Zoom DX</a></div>';
						$('.magnify').bt(zoom_opts,{
							trigger: 'none',
							positions: 'top',
							width: 115
						});
						$('.magnify').btOn(); 
					}
					break;
				case 3:
					// do nothing
					alert("Impossibile procedere in modalita' anteprima");
					break;

				default:
					break;
				}		
			};
			$('.magnify').live('click', enterMagnify);
			$('#magnifyPageRight').live('click', magnifyZoomRight);
			$('#magnifyPageLeft').live('click', magnifyZoomLeft);	


			$.getJSON(br.digital_object_toc_path, function(tocs){
				if(tocs.length>0){

					br.tocs = tocs;

					br.tocsLinearized = new Array();

					$('.BRnavCntl').click(
							function(){
								if ($('#BRnavCntlBtm').hasClass('BRdn')) {
									$('#BDLtocContent').animate({top:42});
									$('#BDLnavCntlLeft').animate({top:42});
								} else {
									$('#BDLtocContent').animate({top:2});
									$('#BDLnavCntlLeft').animate({top:2});
								};
							}
					);

					$("#BookReader").append(
							"<div id='BDLtoc'>"
							+   "<div id=\"BDLtocContent\"></div>"
							+   "<div class=\"BDLnavCntl BDLleft\" id=\"BDLnavCntlLeft\" title=\"Visualizza/Nascondi\"></div>"
							+ "</div>"
					);

					$('#BDLnavCntlLeft').click(
							function(){
								if ($('#BDLnavCntlLeft').hasClass('BDLright')) {
									$('#BDLnavCntlLeft').animate({left:194}).animate({opacity:1});
									$('#BDLnavCntlLeft').addClass('BDLleft').removeClass('BDLright');
									$('#BDLtocContent').animate({left:0});                      
								} else {
									$('#BDLnavCntlLeft').animate({left:0}).animate({opacity:.25},1000);
									$('#BDLnavCntlLeft').addClass('BDLright').removeClass('BDLleft');
									$('#BDLtocContent').animate({left:-194});                      
								};
							}
					);


					function recursiveFunction(key, toc, father) {
						br.tocsLinearized[br.tocsLinearized.length] = toc;
						strTitle = toc.label;
						
						pNum = null;
						if(toc.page!=null && toc.page!='null'){
							idx = br.getIndexByPageId(toc.page);
							pNum = br.getPageNum(idx);
							strTitle += " - Pag. "+pNum;
						}
						li = $("<li/>").attr('id', "idx"+toc.id).attr("rel",pNum).append($("<a>").attr("href","#null").attr("title",strTitle).text(toc.label)).appendTo(father);
						if(toc.children!=null){
							var ul = $("<ul/>").appendTo(li);
							$.each(toc.children, function(key, subtoc){ 
								recursiveFunction(key, subtoc, ul);
							});
						}
					}
					var ulToc = $("<ul/>");
					var liToc = $("<li/>").attr('id', 'tocRoot').append($("<a>").attr("href","#null").text("Indice")).appendTo(ulToc);
					var ulCnt = $("<ul/>").appendTo(liToc);
					$.each(tocs, function(key, subtoc){ recursiveFunction(key, subtoc, ulCnt); });


					$("#BDLtocContent").jstree({ // start jstree configuration with configuration object
						"plugins"            : [ "themes", "ui", "html_data"],
						"core"               : {
							"initially_open"   : ["tocRoot"]
						},
						"ui"                 : {
							"selected_parent_close" : "select_parent",
							"selected_parent_open" : true,
							"select_limit" : 1,
							"initially_select" : ["tocRoot"]
						},
						"html_data" : {
							"data" : ulToc.html()
						},
						"themes" : {
							"theme" : "bdl",
							"dots" : true,
							"icons" : false
						}
					}).bind("select_node.jstree", function (event, data) {
						// `data.rslt.obj` is the jquery extended node that was clicked
						// console.log("data.rslt.obj = "+data.rslt.obj.html());
						
						relVal = data.rslt.obj.attr("rel");
						if(typeof relVal != 'undefined' && relVal!==null && relVal!=='null'){
							console.log("Jumping to page "+relVal)
							br.jumpToPage(relVal);
						}
						$("#BDLtocContent").jstree("open_node", data.rslt.obj);

					});


					br.willChangeToIndex = function(index){
						// Update navbar position icon - leads page change animation

						this.updateNavIndex(index);

						/**
						 * The next code aims to select nodes in the toc depending on what 
						 * is currently shown on the reader.
						 * It is not possible to use it since we can have multiple toc entries 
						 * with the same page number

				            pageNum = index+1;
				            var idx;
				            $.each( br.tocsLinearized, function(ind,obj){
				              if ( this.page == pageNum ) {
				                idx = this.id;
				                return false;
				              }
				            });
				            if(!$.jstree._focused().is_selected($.jstree._focused()._get_node("#idx"+idx))){
				               $.jstree._focused().deselect_all();
				               $.jstree._focused().select_node("#idx"+idx);
				            }
						 */
					};

				}
			});
		});
	});

	br.search = function(term) {
//		console.log('entro in search... ');
		
		/* Posso effettuare una Ricerca OCR? */
		if(br.isRicercaOcr) {
//			console.log('search called with term=' + term);

			$('#textSrch').blur(); //cause mobile safari to hide the keyboard

			if(term.trim()==''){
				br.removeSearchResults();
//				alert('Immettere un testo da ricercare');
				this.showPopup('Inserire un testo da ricercare! ');
				var timeout = 2000;
				setTimeout(function(){
					$(br.popup).fadeOut('slow', function() {
						br.removePopup();
					});
				},timeout);
				
				return;
			}

			term = term.replace(/\//g, ' '); // strip slashes, since this goes in the url
			this.searchTerm = term;

			this.removeSearchResults();
			this.showProgressPopup('<img id="searchmarker" src="./marker_srch-off.png'+'"> Ricerca in corso...');
			$.ajax({ 
				type: "POST",
				dataType: "json",
				url: br.search_url,
				data: { textToSearch: escape(term)},
				success: function(results) {
					//console.log('got ' + results.matches.length + ' results');
					br.removeSearchResults();
					//br.searchResults = results;

					if (0 == results.length) {
						var errStr  = 'Nessun risultato trovato per la ricerca "'+term+'".';
						var timeout = 2000;
						$(br.popup).html(errStr);
						setTimeout(function(){
							$(br.popup).fadeOut('slow', function() {
								br.removeProgressPopup();
							});
						},timeout);
						return;
					}

					var i;
					for (i=0; i<results.length; i++) {
						br.addSearchResult("", results[i].id-1);
					}
					//br.updateSearchHilites();
					br.removeProgressPopup();
				}
			});
		} else {
			br.removeSearchResults();
//			alert('Attenzione: La Ricerca OCR non e\' disponibile per l\'oggetto corrente. ');
			this.showPopup('La Ricerca OCR non e\' disponibile per l\'oggetto digitale! ');
			var timeout = 2000;
			setTimeout(function(){
				$(br.popup).fadeOut('slow', function() {
					br.removePopup();
				});
			},timeout);
			
			return;
		}
	};

	br.addSearchResult = function(queryString, pageId) {

		pageIndex = br.getIndexByPageId(pageId);
		var pageNumber = this.getPageNum(pageIndex);
		var uiStringSearch = "Risultato ricerca";
		var uiStringPage = "Pagina";

		var percentThrough = BookReader.util.cssPercentage(pageIndex, this.numLeafs - 1);
		if (pageNumber) {
			pageDisplayString = uiStringPage + ' ' + pageNumber;
		}

		var re = new RegExp('{{{(.+?)}}}', 'g');
		queryString = queryString.replace(re, '<a href="#" onclick="br.jumpToIndex('+pageIndex+'); return false;">$1</a>');

		var marker = $('<div class="search" style="top:'+(-$('#BRcontainer').height())+'px; left:' + percentThrough + ';" title="' + uiStringSearch + '"><div class="query">'
				+ queryString + '<span>' + uiStringPage + ' ' + pageNumber + '</span></div>')
				.data({'self': this, 'pageIndex': pageIndex })
				.appendTo('#BRnavline')
				.bind('click', function() {
					$(this).data('self').jumpToIndex($(this).data('pageIndex'));
				});

		$(marker).animate({top:'-25px'}, 'slow');

	};
});