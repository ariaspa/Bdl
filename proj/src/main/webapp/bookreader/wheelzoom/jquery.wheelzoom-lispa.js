/*!
  This piece of code is released by LISPA and is based on
	Wheelzoom 2.0.0
	(c) 2014 Jack Moore - http://www.jacklmoore.com/wheelzoom
	license: http://www.opensource.org/licenses/mit-license.php
	dependencies: jQuery 1.9+ or 2.0+
	supports: modern browsers, and IE9 and up
*/
(function($){
	var defaults = {
		zoom: 0.10
	};
	var wheel;

	function setSrcToBackground(img) {
		var transparentPNG = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAACklEQVR4nGMAAQAABQABDQottAAAAABJRU5ErkJggg==";

		// Explicitly set the size to the current dimensions,
		// as the src is about to be changed to a 1x1 transparent png.
		img.width = img.width;
		img.height = img.height;

		$(img).parent().css("background-image", "url("+img.src+")");
		$(img).parent().css("background-repeat", "no-repeat");
		img.src = transparentPNG;
    $(img).width($(img).parent().width());
    $(img).height($(img).parent().height());
	}

	if ( document.onmousewheel !== undefined ) { // Webkit/Opera/IE
		wheel = 'onmousewheel';
	}
	else if ( document.onwheel !== undefined) { // FireFox 17+
		wheel = 'onwheel';
	}

	$.fn.wheelzoom = function(options){
		var settings = $.extend({}, defaults, options);

		if (!this[0] || !wheel || !('backgroundSize' in this[0].style)) { // do nothing in IE8 and lower
			return this;
		}

		return this.each(function(){
			var img = this,
				$img = $(img);

			function loaded() {
				var width = $img.width(),
					height = $img.height(),
					bgWidth = width,
					bgHeight = height,
					bgPosX = $img.parent().width()/2-width/2,
					bgPosY = 0;
          
				function reset() {
					bgWidth = width;
					bgHeight = height;
          bgPosX = $img.parent().width()/2-width/2;
          bgPosY = 0;
					updateBgStyle();
				}
        
        
        
				function updateBgStyle() {
          pW = $img.parent().width();
          console.log("bgPosX="+bgPosX+" bgPosY="+bgPosY+" width="+width+" bgWidth="+bgWidth+" pW="+pW);
					//if (bgPosX > 0) {
				  //bgPosX = 0;
					//} else 
          if (bgPosX < width - bgWidth) {
						bgPosX = width - bgWidth;
					}

					if (bgPosY > 0) {
						bgPosY = 0;
					} else 
          if (bgPosY < height - bgHeight) {
						bgPosY = height - bgHeight;
					}
          $img.parent().css("background-size", bgWidth+'px '+bgHeight+'px');
          $img.parent().css("background-position", bgPosX+'px '+bgPosY+'px');
				}

				setSrcToBackground(img);

				$img.parent().css({
					backgroundSize: width+'px '+height+'px',
					backgroundPosition: bgPosX+'px '+bgPosY+'px'
				}).bind('wheelzoom.reset', reset);

				img[wheel] = function (e) {
					var deltaY = 0;

					e.preventDefault();

					if (e.deltaY) { // FireFox 17+ (IE9+, Chrome 31+?)
						deltaY = e.deltaY;
					} else if (e.wheelDelta) {
						deltaY = -e.wheelDelta;
					}

					// As far as I know, there is no good cross-browser way to get the cursor position relative to the event target.
					// We have to calculate the target element's position relative to the document, and subtrack that from the
					// cursor's position relative to the document.
					var offsetParent = $img.parent().offset();
					var offsetX = e.pageX - offsetParent.left;
					var offsetY = e.pageY - offsetParent.top;

					// Record the offset between the bg edge and cursor:
					var bgCursorX = offsetX - bgPosX;
					var bgCursorY = offsetY - bgPosY;
					
					// Use the previous offset to get the percent offset between the bg edge and cursor:
					var bgRatioX = bgCursorX/bgWidth;
					var bgRatioY = bgCursorY/bgHeight;

					// Update the bg size:
					if (deltaY < 0) {
						bgWidth += bgWidth*settings.zoom;
						bgHeight += bgHeight*settings.zoom;
					} else {
						bgWidth -= bgWidth*settings.zoom;
						bgHeight -= bgHeight*settings.zoom;
					}

					// Take the percent offset and apply it to the new size:
					bgPosX = offsetX - (bgWidth * bgRatioX);
					bgPosY = offsetY - (bgHeight * bgRatioY);

					// Prevent zooming out beyond the starting size
					if (bgWidth <= width || bgHeight <= height) {
						reset();
					} else {
						updateBgStyle();
					}
				};

				// Make the background draggable
				img.onmousedown = function(e){
					var last = e;

					e.preventDefault();

					function drag(e) {
						e.preventDefault();
						bgPosX += (e.pageX - last.pageX);
						bgPosY += (e.pageY - last.pageY);
						last = e;
						updateBgStyle();
					}

					$(document).bind('mousemove', drag).one('mouseup', function () {
						$(document).unbind('mousemove', drag);
					});
				};
			}

			if (img.complete) {
				loaded();
			} else {
				$img.one('load', loaded);
			}

		});
	};

	$.fn.wheelzoom.defaults = defaults;

}(window.jQuery));