var jmeterChart2 = jmeterChart2 || {};

jmeterChart2.requestInfluxdbChartData1 = function(id,isone) {
	//jQuery.ajax
	$.post('/performance/report/influxdbchart', {

		id: id,
	}, function (data) {
		if (data.code == 0) {
			if (isone == 0) {
				//alert('new');
				jmeterChart2.paintingCreateCanva(data.data);//第一次绘图
			} else {
				//alert('update');
				jmeterChart2.paintingUpdateCanva(data.data);//更新图表
			}
		}
		else if (data.code == 1) {
			alert("查询失败");
		} else if (data.code == 2) {
			alert(data.message);
		}
	});
}


jmeterChart2.paintingInfluxdb = function(chardatajson,key){
	chardatajson = eval("("+chardatajson+")");
	var canvas = document.createElement('canvas'); 
	canvas.id     = "Chart_"+key; 
	// canvas.width  = 400;
	// canvas.height = 230;
	canvas.style.zIndex   = 8; 
	//canvas.style.position = "absolute"; 
	canvas.style.border   = "1px solid";
	 var div=document.createElement("div");
	 div.appendChild(canvas);
	$("#chart").append(div);
	//alert('paintingInfluxdb :<br>'+key);
	charttitle = key;
	data_x_list = chardatajson[key]["time"];
	line_name_list = [];
	data_y_list_list = [];
	for(var v in chardatajson[key]){
		//alert('paintingInfluxdb :<br>'+v);
		if (v == "time"){continue;}
		if (v == "value"){
			line_name_list.push(charttitle);
		}else{
			line_name_list.push(v);
		}
		data_y_list_list.push(chardatajson[key][v]);
	}
	console.log("canvas.id: " + canvas.id + "  |  charttitle: "+charttitle);
	console.log("x:" + data_x_list);
	console.log("y:" + data_y_list_list);
	jmeterChart2.newChartLine(canvas.id,charttitle,data_x_list,line_name_list,data_y_list_list);
	jmeterChart2[canvas.id+"lineChartData"] = jmeterChart.lineChartData; //获取画图曲线数据，用于更新操作
	jmeterChart2[canvas.id] = window.myLine;
}
/**
 * chartid canvas控件id用户绘图
 * charttitle 图表的title
 * data_x_list x轴横坐标list
 * line_name_list 每条线的名称
 * data_y_list_list y轴坐标list的list，对应多个线条在同一个图
 */
jmeterChart2.newChartLine = function (chartid, charttitle, data_x_list, line_name_list, data_y_list_list) {
	if (data_x_list == null) {
		data_x_list = ["January", "February", "March", "April", "May", "June", "July"];
		data_y_list_list = [
			[65, 59, 90, 81, 56, 55, 40],
			[35, 29, 50, 51, 26, 25, 30],
			[25, 49, 40, 61, 16, 65, 10]
		];
		line_name_list = ["My Line 1", "My Line 2", "My Line 3"];
	}

	jmeterChart.lineChartData = {};
	jmeterChart.lineChartData.labels = data_x_list; // X 轴横坐标
	jmeterChart.lineChartData.datasets = [];
	for (var i = 0; i < data_y_list_list.length; i++) {
		var linetmp = {};
		linetmp.label = line_name_list[i];
		linetmp.borderColor = jmeterChart.chartColors[i];
		linetmp.backgroundColor = jmeterChart.chartColors[i];
		linetmp.fill = false;
		linetmp.yAxisID = "y-axis-1";
		linetmp.data = data_y_list_list[i];
		jmeterChart.lineChartData.datasets.push(linetmp);
	}
	var chart = document.getElementById(chartid);
	var ctx = chart.getContext("2d");
	window.myLine = Chart.Line(ctx, {
		data: jmeterChart.lineChartData,
		options: {
			responsive: true,
			hoverMode: 'index',
			stacked: false,
			title: {
				display: true,
				text: 'Chart - ' + charttitle
			},
			scales: {
				yAxes: [{
					type: "linear", // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
					display: true,
					position: "left",
					id: "y-axis-1",
					// grid line settings
					gridLines: {
						drawOnChartArea: false, // only want the grid lines for one axis to show up
					}
				}]
			}
		}
	});
}
jmeterChart2.paintingInfluxdbIO = function(chardatajson){
	chardatajson = eval("("+chardatajson+")");
	for(i=0;i<chardatajson['diskio'].length; i++){
		var canvas = document.createElement('canvas');
		host_key = "";
		for (var hostkey in chardatajson['diskio'][i]){
			host_key = hostkey;
	    }
		canvas.id = "DiskIO_"+host_key; 
		canvas.width  = 400; 
		canvas.height = 230; 
		canvas.style.zIndex   = 8; 
		canvas.style.border   = "1px solid";
		document.body.appendChild(canvas);
		charttitle = "DiskIO_"+host_key;
		data_x_list = chardatajson['diskio'][i][host_key].time;
		line_name_list = ["write_Kb/s","read_Kb/s"];
		data_y_list_list = [];
		data_y_list_list.push(chardatajson['diskio'][i][host_key]["write_Kb/s"]);
		data_y_list_list.push(chardatajson['diskio'][i][host_key]["read_Kb/s"]);
		//console.log("canvas.id: " + canvas.id + "  |  charttitle: "+charttitle);
		jmeterChart.newChartLine(canvas.id,charttitle,data_x_list,line_name_list,data_y_list_list);
		jmeterChart2[canvas.id+"lineChartData"] = jmeterChart.lineChartData; //获取画图曲线数据，用于更新操作
		jmeterChart2[canvas.id] = window.myLine;
	}
}

jmeterChart2.paintingCreateCanva = function(chardatajson){
	//chardatajson = eval("("+chardatajson+")");
	jmeterChart2.paintingInfluxdb(chardatajson, "tps_ok");
	jmeterChart2.paintingInfluxdb(chardatajson, "avg");
	jmeterChart2.paintingInfluxdb(chardatajson, "pct90");
	jmeterChart2.paintingInfluxdb(chardatajson, "users");
	jmeterChart2.paintingInfluxdb(chardatajson, "cpu-total");
	jmeterChart2.paintingInfluxdb(chardatajson, "load");
	jmeterChart2.paintingInfluxdb(chardatajson, "mem/G");
	jmeterChart2.paintingInfluxdb(chardatajson, "swap/G");
	jmeterChart2.paintingInfluxdbIO(chardatajson);
}

jmeterChart2.paintingUpdateCanvaIO = function(chardatajson){
	chardatajson = eval("("+chardatajson+")");
	for(i=0;i<chardatajson['diskio'].length; i++){
		host_key = "";
		for (var hostkey in chardatajson['diskio'][i]){
			host_key = hostkey;
	    }
		canvasid = "DiskIO_"+host_key;
		// 更新x轴
		jmeterChart2[canvasid+"lineChartData"].labels = chardatajson['diskio'][i][host_key].time;
		jmeterChart2[canvasid+"lineChartData"].datasets.forEach(function(dataset) {
			var line_name = dataset.label; // "write_Kb/s","read_Kb/s"
			dataset.data =chardatajson['diskio'][i][host_key][line_name];
		});
		jmeterChart2[canvasid].update();
	}
}
jmeterChart2.paintingUpdateCanva = function(chardatajsonstring){
	chardatajson = eval("("+chardatajsonstring+")");
	keys=["tps_ok","avg", "pct90", "users", "cpu-total", "load", "mem/G", "swap/G"];
	for(var key_i =0; key_i < keys.length; key_i++){
		key = keys[key_i];
		canvasid = "Chart_"+key;
		jmeterChart2[canvasid+"lineChartData"].labels = chardatajson[key]["time"]; //更新x轴
		// foreach 循环更新所有曲线
		jmeterChart2[canvasid+"lineChartData"].datasets.forEach(function(dataset) {
			line_name_list = [];
			data_y_list_list = [];
			for(var v in chardatajson[key]){
				if (v == "time"){continue;}
				charttitle = "";
				if (v == "value"){
					charttitle = key;
				}else{
					charttitle = v;
				}
				console.log(charttitle + "|" + dataset.label + ": 相等则更新曲线")
				// 当前线条名称 == 待更新线条
				if(charttitle == dataset.label){
					dataset.data = chardatajson[key][v];//["1","2","3","4","5","6"];
				}
			}
	    });
		jmeterChart2[canvasid].update();
	}
	jmeterChart2.paintingUpdateCanvaIO(chardatajsonstring);
}

