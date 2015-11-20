// JavaScript Document


var hover = (function($){
	var defaultOptions = {
		ul_arr:".title2>ul",
		s_div:"ul",
		adclass_n:"active"

	}
	var hover = function(options){
		this.options = $.extend({},defaultOptions,options);
		this.$uls = $(this.options.ul_arr);
		this.sDiv = this.options.s_div;
		this.adclass_n = this.options.adclass_n;
		//this._$fBtn = $(this.options.fBtn);
		this.init();
	}
	hover.prototype.init = function(){
		//alert(this.adclass_n);
		var self = this;
		this.$uls.each(function(){
			var self_ul = this;
			var $disDiv = $(this).parent().siblings();
			var $lis = $(self_ul).children("li");
			if($lis.length != $disDiv.length){
				$disDiv = $disDiv.children(self.sDiv);
			}
			$lis.each(function(i){
				var $licell = $(this);
				$licell.on("mouseenter",function(){
					//console.log($disDiv.length);
					$licell.addClass(self.adclass_n)
						   .siblings().removeClass(self.adclass_n);
					$disDiv.css("display","none")
						   .eq(i).css("display","block");		   
					//$licell.parent().parent().siblings().css("display","none");
					//$licell.parent().parent().siblings().eq(i).css("display","block");													      	
				});
				
			})
		});
		
		
	}
	return hover
})(jQuery)
$(function(){
	var hover_fr = new hover({ul_arr:".hd>ul",adclass_n:"cur"});
	var hover_top = new hover({ul_arr:".newpic_m>ul",adclass_n:"newpic_m_dw1"});
	var hover_tab = new hover({ul_arr:".newpic_m>ul",adclass_n:"newpic_m_dw1"});
});