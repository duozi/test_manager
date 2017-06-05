var jmeterChart2 = jmeterChart2 || {};

jmeterChart2.requestInfluxdbChartData1 = function(id,isone){
	//jQuery.ajax
	$.ajax({ 
        url: '/performance/report/influxdbchart',
        type: 'POST',
        async:true,    //或false,是否异步 true
        //traditional: true,  //设置这个会将json按照list类型发送
        data: {
			id: id,
        },
        timeout:20000,    //超时时间
        dataType: 'text', //返回的数据格式：json/xml/html/script/jsonp/text

        success: function(data, status, xhr) { // 回调函数，成功后调用
              //alert('request success<br>'+data);
        	if(isone==0){
        		//alert('new');
        		jmeterChart2.paintingCreateCanva(data.data);//第一次绘图
        	}else{
        		//alert('update');
        		jmeterChart2.paintingUpdateCanva(data.data);//更新图表
        	}
        },
        error:function(xhr,textStatus){
        	alert('request error!');
            alert(xhr);
            console.log(xhr);
            alert(textStatus);
            console.log(textStatus);
        }
     });
}

jmeterChart2.paintingInfluxdb = function(chardatajson,key){
	chardatajson = eval("("+chardatajson+")");
	var canvas = document.createElement('canvas'); 
	canvas.id     = "Chart_"+key; 
	canvas.width  = 400; 
	canvas.height = 230; 
	canvas.style.zIndex   = 8; 
	//canvas.style.position = "absolute"; 
	canvas.style.border   = "1px solid";
	document.body.appendChild(canvas);
	//alert('paintingInfluxdb :<br>'+key);
	charttitle = key;
	data_x_list = chardatajson.map[key].map["time"].myArrayList;
	line_name_list = [];
	data_y_list_list = [];
	for(var v in chardatajson.map[key].map){
		//alert('paintingInfluxdb :<br>'+v);
		if (v == "time"){continue;}
		if (v == "value"){
			line_name_list.push(charttitle);
		}else{
			line_name_list.push(v);
		}
		data_y_list_list.push(chardatajson.map[key].map[v].myArrayList);
	}
	console.log("canvas.id: " + canvas.id + "  |  charttitle: "+charttitle);
	console.log("x:" + data_x_list);
	console.log("y:" + data_y_list_list);
	jmeterChart.newChartLine(canvas.id,charttitle,data_x_list,line_name_list,data_y_list_list);
	jmeterChart2[canvas.id+"lineChartData"] = jmeterChart.lineChartData; //获取画图曲线数据，用于更新操作
	jmeterChart2[canvas.id] = window.myLine;
}
jmeterChart2.paintingInfluxdbIO = function(chardatajson){
	chardatajson = eval("("+chardatajson+")");
	for(i=0;i<chardatajson.map['diskio'].myArrayList.length; i++){
		var canvas = document.createElement('canvas');
		host_key = "";
		for (var hostkey in chardatajson.map['diskio'].myArrayList[i].map){
			host_key = hostkey;
	    }
		canvas.id = "DiskIO_"+host_key; 
		canvas.width  = 400; 
		canvas.height = 230; 
		canvas.style.zIndex   = 8; 
		canvas.style.border   = "1px solid";
		document.body.appendChild(canvas);
		charttitle = "DiskIO_"+host_key;
		data_x_list = chardatajson.map['diskio'].myArrayList[i].map[host_key].map.time.myArrayList;
		line_name_list = ["write_Kb/s","read_Kb/s"];
		data_y_list_list = [];
		data_y_list_list.push(chardatajson.map['diskio'].myArrayList[i].map[host_key].map["write_Kb/s"].myArrayList);
		data_y_list_list.push(chardatajson.map['diskio'].myArrayList[i].map[host_key].map["read_Kb/s"].myArrayList);
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
	for(i=0;i<chardatajson.map['diskio'].myArrayList.length; i++){
		host_key = "";
		for (var hostkey in chardatajson.map['diskio'].myArrayList[i].map){
			host_key = hostkey;
	    }
		canvasid = "DiskIO_"+host_key;
		// 更新x轴
		jmeterChart2[canvasid+"lineChartData"].labels = chardatajson.map['diskio'].myArrayList[i].map[host_key].map.time.myArrayList;
		jmeterChart2[canvasid+"lineChartData"].datasets.forEach(function(dataset) {
			var line_name = dataset.label; // "write_Kb/s","read_Kb/s"
			dataset.data =chardatajson.map['diskio'].myArrayList[i].map[host_key].map[line_name].myArrayList;
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
		jmeterChart2[canvasid+"lineChartData"].labels = chardatajson.map[key].map["time"].myArrayList; //更新x轴
		// foreach 循环更新所有曲线
		jmeterChart2[canvasid+"lineChartData"].datasets.forEach(function(dataset) {
			line_name_list = [];
			data_y_list_list = [];
			for(var v in chardatajson.map[key].map){
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
					dataset.data = chardatajson.map[key].map[v].myArrayList;//["1","2","3","4","5","6"];
				}
			}
	    });
		jmeterChart2[canvasid].update();
	}
	jmeterChart2.paintingUpdateCanvaIO(chardatajsonstring);
}

