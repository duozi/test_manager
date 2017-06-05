var jmeterChart = jmeterChart || {};

jmeterChart.chartColors = [
    'rgb(255,192,203)',  //pink（粉红）
    'rgb(255,127,80)',   //coral（珊瑚）
    'rgb(188,143,143)',  //rosybrown（玫瑰棕）
    'rgb(255,0,0)',      //red（红）
    'rgb(220,20,60)',    //crimson（腥红）
    'rgb(199,21,133)',   //mediumvioletred （适中的紫罗兰红）
    'rgb(255,0,255)',    //fuchsia（紫红）
    'rgb(128,0,128)',    //purple（紫）
    'rgb(75,0,130)',     //indigo（靓青）
    'rgb(138,43,226)',   //blueviolet（蓝紫罗兰）
    'rgb(106,90,205)',   //slateblue（板岩蓝）
    'rgb(0,0,255)',      //blue（蓝）
    'rgb(25,25,112)',    //midnightblue（午夜蓝）
    'rgb(100,149,237)',  //cornflowerblue（矢车菊蓝）
    'rgb(119,136,153)',  //lightslategray（浅石板灰）
    'rgb(70,130,180)',   //steelblue（钢蓝）
    'rgb(0,191,255)',    //deepskyblue（深天蓝）
    'rgb(95,158,160)',   //cadetblue（军校蓝）
    'rgb(0,255,255)',    //cyan（青）
    'rgb(0,206,209)',    //darkturquoise （深宝石绿）
    'rgb(47,79,79)',     //darkslategray （深石板灰）
    'rgb(0,128,128)',    //tea （水鸭色）
    'rgb(32,178,170)',   //lightseagreen（浅海洋绿）
    'rgb(0,255,127)',    //springgreen（春天绿）
    'rgb(46,139,87)',    //seagreen（海洋绿）
    'rgb(107,142,35)',   //olivedrab（橄榄褐）
    'rgb(255,255,0)',    //yellow（黄）
    'rgb(189,183,107)',  //darkkhaki（深卡其布）
    'rgb(255,215,0)',    //gold（金）
    'rgb(218,165,32)',   //goldenrod（秋）
    'rgb(255,165,0)',    //orange（橙）
    'rgb(210,180,140)',  //tan（晒）
    'rgb(205,133,63)',   //peru（秘鲁）
    'rgb(192,14,235)',   //chocolatesaddlebrown（马鞍棕）
    'rgb(160,82,45)',    //sienna（土黄赭）
    'rgb(128,0,0)',      //maroon（粟色）
    'rgb(105,105,105)',  //dimgray（暗灰）
    'rgb(0,0,0)'        //black（黑）
];

jmeterChart.requestSqliteChartData1 = function (id) {
    //jQuery.ajax
    $.ajax({
        url: "/performance/report/get_chart",
        type: 'POST',
        async: true,    //或false,是否异步 true
        //traditional: true,  //设置这个会将json按照list类型发送
        data: {
            id: id,
        },
        timeout: 20000,    //超时时间
        dataType: 'text', //返回的数据格式：json/xml/html/script/jsonp/text

        success: function (data, status, xhr) { // 回调函数，成功后调用
            //alert('request success<br>'+data);
            jmeterChart.createCanvas(data);

        },
        error: function (xhr, textStatus) {
            alert('request error!');
            alert(xhr);
            console.log(xhr)
            alert(textStatus);
            console.log(textStatus)
        }
    });
}

// jmeterChart.createCanvas = function(chardatajson){
// 	//var chardatajson = jmeterChart.requestSqliteChartData1(sqlitdb);
// 	chardatajson = eval("("+chardatajson+")");
// 	for(var key in chardatajson.map){
// 		var canvas = document.createElement('canvas');
// 		canvas.id     = "JmeterSqliteChart"+key;
// 		canvas.width  = 400;
// 		canvas.height = 230;
// 		canvas.style.zIndex   = 8;
// 		//canvas.style.position = "absolute";
// 		canvas.style.border   = "1px solid";
// 		document.body.appendChild(canvas);
//
// 		charttitle = key;
// 		data_x_list = chardatajson.map[key].map["time"].myArrayList;
// 		line_name_list = [];
// 		data_y_list_list = [];
// 		for(var v in chardatajson.map[key].map){
// 			if (v == "time"){
// 				continue;
// 			}
// 			if (v == "value"){
// 				line_name_list.push(charttitle);
// 			}else{
// 				line_name_list.push(v);
// 			}
// 			data_y_list_list.push(chardatajson.map[key].map[v].myArrayList);
// 		}
// 		jmeterChart.newChartLine(canvas.id,charttitle,data_x_list,line_name_list,data_y_list_list);
// 	}
//
// }
jmeterChart.createCanvas = function (chardatajson) {
    //var chardatajson = jmeterChart.requestSqliteChartData1(sqlitdb);
    chardatajson = eval("(" + chardatajson + ")");
    jmeterChart.generateCanvas(chardatajson,"users","#chart-1");
    jmeterChart.generateCanvas(chardatajson,"pct90","#chart-2");
    jmeterChart.generateCanvas(chardatajson,"tps_ok","#chart-1");
    jmeterChart.generateCanvas(chardatajson,"avg","#chart-2");
    jmeterChart.generateCanvas(chardatajson,"tps_all","#chart-1");
    jmeterChart.generateCanvas(chardatajson,"swap/G","#chart-2");
    jmeterChart.generateCanvas(chardatajson,"cpu-total","#chart-1");
    jmeterChart.generateCanvas(chardatajson,"mem/G","#chart-2");
    jmeterChart.generateCanvas(chardatajson,"load","#chart-1");
    jmeterChart.generateCanvas(chardatajson,"diskio","#chart-2");


}
jmeterChart.generateCanvas = function (chardatajson,key ,id) {
    var canvas = document.createElement('canvas');
    canvas.id = "JmeterSqliteChart" + key;
    // canvas.width = 200;
    // canvas.height = 100;
    canvas.style.zIndex = 8;
    //canvas.style.position = "absolute";
    // canvas.style.border   = "1px solid";//边框
    var div=document.createElement("div");
    // div.setAttribute("class","form-group");
    div.appendChild(canvas);
    $(id).append(div);

    // document.body.appendChild(canvas);

    charttitle = key;
    data_x_list = chardatajson[key]["time"];
    line_name_list = [];
    data_y_list_list = [];
    for (var v in chardatajson[key]) {
        if (v == "time") {
            continue;
        }
        if (v == "value") {
            line_name_list.push(charttitle);
        } else {
            line_name_list.push(v);
        }
        data_y_list_list.push(chardatajson[key][v]);
    }
    jmeterChart.newChartLine(canvas.id, charttitle, data_x_list, line_name_list, data_y_list_list);
}

/**
 * chartid canvas控件id用户绘图
 * charttitle 图表的title
 * data_x_list x轴横坐标list
 * line_name_list 每条线的名称
 * data_y_list_list y轴坐标list的list，对应多个线条在同一个图
 */
jmeterChart.newChartLine = function (chartid, charttitle, data_x_list, line_name_list, data_y_list_list) {
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
        lineChartData.datasets.push(linetmp);
    }

    var ctx = document.getElementById(chartid).getContext("2d");
    window.myLine = Chart.Line(ctx, {
        data: lineChartData,
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

jmeterChart.newChartLineTest = function (chartid, data_x_list, data_line_list) {
    if (data_x_list == null || data_line_list == null) {
        data_x_list = ["January", "February", "March", "April", "May", "June", "July"];
        data_line_list = [
            [65, 59, 90, 81, 56, 55, 40],
            [35, 29, 50, 51, 26, 25, 30],
            [25, 49, 40, 61, 16, 65, 10]
        ]
    }
    /*
     // 绘图的线条
     var lineChartData = {
     // X 轴横坐标
     labels: data_x, //["January", "February", "March", "April", "May", "June", "July"],
     datasets: [{
     label: "My First dataset", //第一条曲线标题
     borderColor: window.chartColors.red, //曲线颜色
     backgroundColor: window.chartColors.yellow, //曲线上的点的颜色
     fill: false, //是否涂满曲线到x轴
     data: [65,59,90,81,56,55,40], //y轴坐标
     yAxisID: "y-axis-1",
     }, {
     label: "My Second dataset",
     borderColor: window.chartColors.blue,
     backgroundColor: window.chartColors.blue,
     fill: false,
     data:[35,29,50,51,26,25,30],
     yAxisID: "y-axis-1"
     //yAxisID: "y-axis-2"
     }]
     };
     */
    var lineChartData = {};
    lineChartData.labels = data_x_list; // X 轴横坐标
    lineChartData.datasets = [];
    for (var i = 0; i < data_line_list.length; i++) {
        var linetmp = {};
        linetmp.label = "My Line " + (i + 1);
        linetmp.borderColor = jmeterChart.chartColors[i];
        linetmp.backgroundColor = jmeterChart.chartColors[i];
        linetmp.fill = false;
        linetmp.yAxisID = "y-axis-1";
        linetmp.data = data_line_list[i];
        lineChartData.datasets.push(linetmp);
    }

    var ctx = document.getElementById(chartid).getContext("2d");
    window.myLine = Chart.Line(ctx, {
        data: lineChartData,
        options: {
            responsive: true,
            hoverMode: 'index',
            stacked: false,
            title: {
                display: true,
                text: 'Chart.js Line Chart - Multi Axis'
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