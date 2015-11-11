/**
 * 
 */
$(document).ready(function() {
	$(".MenuItem").bind("mouseenter", function(event) {
		// var $dropDownMenu = $(this).children(".dropDown");
		// $dropDownMenu.css("display", "block");
		var src = $(this).children("img").attr("src");
		var length = src.length;
		// src=src.substring(0,length-4)+"_active.png";
		src = src.substring(0, length - 4) + "_active.png";
		$(this).children("img").attr("src", src);
		$(this).children("a").css("color", "#e95e59");
		event.stopPropagation();
	});
	$(".MenuItem").bind("mouseleave", function(event) {
		// var $dropDownMenu = $(this).children(".dropDown");
		// $dropDownMenu.css("display", "none");
		var src = $(this).children("img").attr("src");
		var length = src.length;
		src = src.substring(0, length - 11) + ".png";
		$(this).children("img").attr("src", src);
		$(this).children("a").css("color", "white");
		event.stopPropagation();

	});
	setUpLoginLink();
});
