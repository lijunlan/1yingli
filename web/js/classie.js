$(document).ready(function(){
  $(".left").click(function(){
    $.fn.buttonpanel = function(_param){
      var param = {
        "direction" : "in"
      };
      param = $.extend(param,_param);
      var dh = document.documentElement.left1;
      switch (param.direction){
        case"in":
          this.css("buttom",-dh).show().animate({"buttom":"0px"},500,"swing");
          break;
        case"out":
          this.animate({"buttom":"0px"},500,"swing");
          break;
      }
    }
  })
})