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
				// requestChart();
                log_monitor.core.draw_request_chart();
			}
		}

		var requestChart = function() {
			var data = {
				'data1' : {'test1':10,'test2':12,'test3':15,'test4':5,'test5':12,'test6':12,'test7':2,'test8':9},
				'data2' : {'test1':5,'test2':11,'test3':10,'test4':11,'test5':13,'test6':10,'test7':21,'test8':19},
				'data3' : {'test1':2,'test2':15,'test3':6,'test4':9,'test5':23,'test6':5,'test7':17,'test8':7},
			};
			var categories = ['test1','test2','test3','test4','test5','test6','test7','test8'];
			var config = ChartConfig.getConfig('area');
			var dataTmp = [],dataValue;
			var finData = [];
			for (var x in data) {
				var dataSeries = new Array;
				for(var index in data[x]) {
					dataSeries.push(Number(data[x][index]));
				}
				finData.push({name:x,data:dataSeries});
			}
			//config.legend = false;
			config.title.text = '';
			config.series = finData;
			config.xAxis.categories = categories;
			//config.xAxis.min = 1;
			config.xAxis.title.text = '';
			//config.xAxis.type = 'logarithmic';
			//config.yAxis.min = 1;
			//config.yAxis.title.text = '';
			config.tooltip.headerFormat = '';
			config.tooltip.formatter = function() {
				return (this.point.category) + '总数' + this.point.y+'次';
			};
			//config.plotOptions.column.pointWidth = 15;
			$("#divContentChart").highcharts(config);
		};

		buttonSwitcher()
        log_monitor.core.refresh();
 });
})(jQuery);

var request_chart_tooltip = function() {
    return this.point.category + " 总计" + this.point.y + "次"
}
