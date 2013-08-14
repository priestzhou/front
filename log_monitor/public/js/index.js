(function($){
    $(document).ready(function(){
		$('#TimeRangePicker_0_1_0').click(function(){
			$('div.timeRangeMenu').show();
			return false;
		});
		$("body").click(function(event){
			$('div.timeRangeMenu').hide();
		});
		$('div.timeRangeMenu > ul > div.innerMenuWrapper > li div.outerMenuWrapper li').mousemove(function(){
			$(this).addClass('splMenuMoseOver');
		});
		$('div.timeRangeMenu > ul > div.innerMenuWrapper > li').mousemove(function(){
			$('div.timeRangeMenu > ul > div.innerMenuWrapper > li.hasSubMenu').find('div').hide();
			$(this).addClass('splMenuMoseOver');
			if($(this).hasClass('hasSubMenu')) {
				$(this).find('div').show();
			}
		});

		$('div.timeRangeMenu > ul .innerMenuWrapper > li').mouseout(function(){
			$(this).removeClass('splMenuMoseOver');
		});
		$('div.timeRangeMenu > ul .innerMenuWrapper > li.hasSubMenu > a.menuItemLink').click(function(){
			return false;
		});
		//时间选择
		$('div.timeRangeMenu > ul .innerMenuWrapper > li.timeRangePreset a.menuItemLink').click(function(){
			$('#TimeRangePicker_0_1_0 span.timeRangeActivator').text($(this).text());
			//alert($(this).attr('latest_time') + '||' + $(this).attr('earliest_time'));
			$('input[name=earliest_time]').val($(this).attr('earliest_time'));
            log_monitor.core.request_search();
			return true;
		});
		//查询按钮
		$('input.searchButton').click(function(){
			//$('div.graphArea').mask('Loading...')
			//alert($('#SearchBar_0_0_0_id').val());
            log_monitor.core.request_search();
			return true;
		});

		//表格和图标切换
		$('div.ButtonSwitcher>ul>li').click(function(){
			var type = $(this).attr('_type');
			$('div.ButtonSwitcher>ul>li').removeClass('selected');
			$(this).addClass('selected');
			buttonSwitcher();
		});
		
		function buttonSwitcher(){
			var type = $('div.ButtonSwitcher>ul>li.selected').attr('_type');
			$('div.divContent > div').hide();
			if(type == 'list') {
				$('#divContentList').show();
			} else if(type == 'table') {
				$('#divContentTable').show();
			} else {
				$('#divContentChart').show();
                log_monitor.core.draw_request_chart();
			}
		}

		buttonSwitcher()
        log_monitor.core.refresh();
 });
})(jQuery);

var request_chart_tooltip = function() {
    return this.point.category + " 总计" + this.point.y + "次"
}
