// 全屏代码
function fullScreen() {
	var elem = document.documentElement;
	if (elem.webkitRequestFullScreen) {
		elem.webkitRequestFullScreen();
	} else if (elem.mozRequestFullScreen) {
		elem.mozRequestFullScreen();
	} else if (elem.msRequestFullscreen) {
		elem.msRequestFullscreen();
	} else if (elem.requestFullScreen) {
		elem.requestFullscreen();
	} else {
		notice.notice_show("浏览器不支持全屏API或已被禁用", null, null, null, true, true);
	}
}

//退出全屏
function exitFullScreen() {
	var elem = document;
	if (elem.webkitCancelFullScreen) {
		elem.webkitCancelFullScreen();
	} else if (elem.mozCancelFullScreen) {
		elem.mozCancelFullScreen();
	} else if (elem.cancelFullScreen) {
		elem.cancelFullScreen();
	} else if (document.msExitFullscreen) {
		elem.msExitFullscreen();
	} else if (elem.exitFullscreen) {
		elem.exitFullscreen();
	} else {
		notice.notice_show("浏览器不支持全屏API或已被禁用", null, null, null, true, true);
	}
}
//定时刷新方法
//说明：
//		定时发送请求到后端获取数据并动态加载
function TimeRefresh() {
console.log("刷新");
	console.log($("#workOrderNumber").val());
	
	$.ajax({
		url:"PackingProductionData/findAll",
		async:true,
		success:function(d) {
			
			if(d.code===200) {
				var data = d.data;
				if(data.length>0){
					var html = "";
					var i = 0;
					$(data).each(function(i, v) {
						console.log("打印");
						console.log(v.workOrderNumber);
						html += "<tr baseInfoid='"+v.id+"' id='"+i+"' >" + 
						"<td>" + v.machineName + "</td>" +//机器名称
						"<td>" + v.machineState + "</td>" +//机器状态
						"<td><textarea name='workOrderNumber' id='workOrderNumber"+i+"' value='"+v.workOrderNumber+"'>"+v.workOrderNumber+"</textarea></td>" +//工单单号
						"<td><textarea name='version' id='version"+i+"' value='"+ v.version +"'>"+v.version+"</textarea></td>" +//产品单号
						"<td><textarea name='customerNumber' id='customerNumber"+i+"' value='"+ v.customerNumber +"'>"+v.customerNumber+"</textarea></td>" +//客户单号
						"<td><textarea name='scheduledProduction' id='scheduledProduction"+i+"' value='"+ v.scheduledProduction +"'>"+v.scheduledProduction+"</textarea></td>" +//计划产量
						"<td>" + v.ok + "</td>" +//合格数量
						"<td>" + v.ng + "</td>" +//不良数
						"<td id='total"+i+"'>" + v.unfinishedAmount + "</td>" +//未完成量
						"<td id='total"+i+"'>" + v.ok + "</td>" +//合格数想当于已完成量
						"<td>" + v.startTime + "</td>" +//开机时间
						"<td>" + v.endTime + "</td>"+//关机时间
						"<td><input type='button'  onclick='saveBaseInfo("+i+")' value='保存'/>" +
						"<input type='button'  onclick='submitOrder("+v.id+","+i+")' value='提交订单'/></td>";//关机时间
					});
					
					var realdata_table = document.getElementById('realdata_table'); 
					var rowNum = realdata_table.rows.length; 
					for (i = 1; i < rowNum; i++) { 
						realdata_table.deleteRow(i); 
                        rowNum = rowNum - 1; 
                        i = i - 1; 
                    }
						
					html += "<tr></tr>" +
					"<tr></tr>" +
					"<tr></tr>" +
					"<tr></tr>" +
					"<tr></tr>" +
					"<tr></tr>" +
					"<tr></tr>" +
					"<tr></tr>" +
					"<tr></tr>";
					$("#realdata_table tbody").append(html);
				}
			} else if(d.code === 401){
				showNote(d.message);
			}
		}
	});
	
}
//提交订单事件
function submitOrder(id,i) {
	if($("#scheduledProduction"+i).val() == "" || $("#scheduledProduction"+i).val() == null) {
		layer.msg("计划产量不能为空！");
		return;
	}
	if($("#workOrderNumber"+i).val() == "" || $("#workOrderNumber"+i).val() == null) {
		layer.msg("工单单号不能为空！");
		return;
	}
	if($("#version"+i).val() == "" || $("#version"+i).val() == null) {
		layer.msg("产品型号不能为空！");
		return;
	}
	if($("#customerNumber"+i).val() == "" || $("#customerNumber"+i).val() == null) {
		layer.msg("客户单号不能为空！");
		return;
	}
	var data = {
			"id" : id,
			"ok" : $("#ok"+i).html(),
			"workOrderNumber" : $("#workOrderNumber"+i).val(),
			"version" : $("#version"+i).val(),
			"customerNumber" : $("#customerNumber"+i).val(),
			"scheduledProduction" : $("#scheduledProduction"+i).val()
	};
	$.ajax({
		  type: 'POST',
		  url: "PackingProductionData/submit_order",
		  data: data,
		  success: function(result) {
			  if(result.code == 200) {
				  layer.msg(result.message, {icon:6});
				  return;
			  }
			  layer.msg(result.message, {icon: 5});
		  }
	});
}
//填写基本信息
function saveBaseInfo(i) {
	if($("#scheduledProduction"+i).val() == "" || $("#scheduledProduction"+i).val() == null) {
		layer.msg("计划产量不能为空！");
		return;
	}
	if($("#workOrderNumber"+i).val() == "" || $("#workOrderNumber"+i).val() == null) {
		layer.msg("工单单号不能为空！");
		return;
	}
	if($("#version"+i).val() == "" || $("#version"+i).val() == null) {
		layer.msg("产品型号不能为空！");
		return;
	}
	if($("#customerNumber"+i).val() == "" || $("#customerNumber"+i).val() == null) {
		layer.msg("客户单号不能为空！");
		return;
	}
	var data = {
			"id" : $("#"+i).attr("baseInfoid"),
			"ok" : $("#ok"+i).html(),
			"workOrderNumber" : $("#workOrderNumber"+i).val(),
			"version" : $("#version"+i).val(),
			"customerNumber" : $("#customerNumber"+i).val(),
			"scheduledProduction" : $("#scheduledProduction"+i).val()
	};
	$.ajax({
		  type: 'POST',
		  url: "PackingProductionData/saveBaseInfo",
		  data: data,
		  success: function(result) {
			  if(result.code == 200) {
				  layer.msg("保存成功！", {icon:6});
				  return;
			  }
			  layer.msg(result.message, {icon: 5});
		  }
	});
}

//进入主页面
function entryToMainPage(){
	$(".header").removeClass("hide");
	$(".main").removeClass("hide");
	$("#container").hide().remove();
}

function showNote(msg){
	$(".note_dialog").html(msg).show();
	setTimeout(function(){
		$(".note_dialog").hide();
	},2000);
}

//--------------------事件-------------------------

//监控键盘F11按键事件
document.addEventListener("keydown", function(e) {
	var currKey = 0
	//在FireFox或Opera中，隐藏的变量e是存在的，那么e||event返回e，如果在IE中，隐藏变量e是不存在，则返回event。
	var e = e || event;
	//IE中，只有keyCode属性，而FireFox中有which和charCode属性，Opera中有keyCode和which属性
	var currKey = e.keyCode || e.which || e.charCode;
	// 阻止F11键默认事件，用HTML5全屏API代替
	if (e.keyCode===122) {
		e.preventDefault()
		var isfull = document.isFullScreen || document.mozIsFullScreen || document.webkitIsFullScreen;
		if(isfull) {
			exitFullScreen();
		} else {
			fullScreen();
		}
	} 
}, false);

//双击指示板事件
$("#showPadIcon").on("dblclick", function(e){
	var isfull = document.isFullScreen || document.mozIsFullScreen || document.webkitIsFullScreen;
	if(isfull) {
		exitFullScreen();
	} else {
		fullScreen();
	}
});

//欢迎页进入按钮事件
$("#entry_btn").on("click",function(){
	entryToMainPage();
	fullScreen();
});

//打开历史记录窗体按钮事件
$("#history").on("click",function(){
	$.get("/PackingProductionData/findByPageHistory",function(d){
		if(d.code === 200){
			$("#devlog_table tbody").empty();
			var data = d.data;
			var html = "";
			$(data).each(function(i, v) {
				console.log(v);
				html += "<tr baseInfoid='"+v.id+"' id='"+i+"' >" + 
				"<td>" + v.machineName + "</td>" +//机器名称
				"<td>" + v.machineState + "</td>" +//机器状态
				"<td>"+v.workOrderNumber+"</textarea></td>" +//工单单号
				"<td>"+v.version+"</td>" +//产品型号
				"<td>"+v.customerNumber+"</td>" +//客户单号
				"<td>"+v.scheduledProduction+"</td>" +//计划产量
				"<td>" + v.ok + "</td>" +//合格数量
				"<td>" + v.ng + "</td>" +//不良数
				"<td id='total"+i+"'>" + v.unfinishedAmount + "</td>" +//未完成量
				"<td id='total"+i+"'>" + v.ok + "</td>" +//总数等于已完成量
				"<td>" + v.startTime + "</td>" +//开机时间
				"<td>" + v.endTime + "</td>";//关机时间
			});
			$("#devlog_table tbody").append(html);
		}
		$(".div_shade").show();
		$(".devlog_window").show();
		laydate.render({
			  elem: '#time' //指定元素
		});
	});
	
});


//打开登陆窗体的按钮事件
$("#login_win").on("click",function(){
	$(".div_shade").show();
	$(".login_window").show();
});

//登录按钮事件
$("#login").on("click",function(e){
	var p = {};
	p.userName = $("#userName").val();
	p.password = $("#password").val();
	$.ajax({
		url:"/login",
		type:"POST",
		data:p,
		success:function(d){
			if(d.code == 200){
				$(".div_shade").hide();
				$(".login_window").hide();
				$("#username").attr("disabled",true);
				$("#password").attr("disabled",true);
				$("#login").attr("disabled",true);
				$("#logout").attr("disabled",false);
				layer.msg(d.message, {icon: 6});
			} else {
				layer.msg(d.message, {icon: 5});
			}
		}
	});
});

//注销按钮事件
$("#logout").on("click",function(e){
	if (confirm("您确定要注销登录吗？")){
		$.get("/logout",function(d){
			$(".div_shade").hide();
			$(".login_window").hide();
			if(d.code === 200){
				$("#username").attr("disabled",false);
				$("#password").attr("disabled",false).val("");
				$("#login").attr("disabled",false);
				$("#logout").attr("disabled",true);
				showNote("成功注销登录!");
			}
		});
	}
});

//打开修改密码窗体的按钮事件
$("#modifyPwd_win").on("click",function(){
	$(".div_shade").show();
	$(".modifyPwd_window").show();
});

//修改密码按钮事件
$("#modifyPwd").on("click",function(){
	var p = {};
	p.psw = $("#current_PSW").val();
	p.newpsw = $("#new_PSW").val();
	var repeat = $("#repeat_PSW").val();
	if(repeat != p.newpsw){
		alert("新密码和确认密码不匹配，新重新填写！");
		return;
	}
	$.post("/modifypsw",p,function(d){
		if(d.message){
			if(d.code===200){
				$("#current_PSW").val("");
				$("#new_PSW").val("");
				$("#repeat_PSW").val("");
				$(".div_shade").hide();
				$(".modifyPwd_window").hide();
				layer.msg(d.message, {icon: 6});
				return;
			} else {
				layer.msg(d.message, {icon: 5});
			}
		}
	});
});

$("#time_query").on("click",function(){
	var time = $("input[id='time']").val();
	$.get("/PackingProductionData/findByPageHistory","time="+time,function(d){
		if(d.code === 200){
			$("#devlog_table tbody").empty();
			var data = d.data;
			var html = "";
			$(data).each(function(i, v) {
				html += "<tr baseInfoid='"+v.id+"' id='"+i+"' >" + 
				"<td>" + v.machineName + "</td>" +//机器名称
				"<td>" + v.machineState + "</td>" +//机器状态
				"<td>"+v.workOrderNumber+"</textarea></td>" +//工单单号
				"<td>"+v.version+"</td>" +//产品型号
				"<td>"+v.customerNumber+"</td>" +//客户单号
				"<td>"+v.scheduledProduction+"</td>" +//计划产量
				"<td>" + v.ok + "</td>" +//合格数量
				"<td>" + v.ng + "</td>" +//不良数
				"<td id='total"+i+"'>" + v.unfinishedAmount + "</td>" +//未完成量
				"<td id='total"+i+"'>" + v.total + "</td>" +//总数等于已完成量
				"<td>" + v.startTime + "</td>" +//开机时间
				"<td>" + v.endTime + "</td>";//关机时间
			});
			$("#devlog_table tbody").append(html);
		}
	})
});

//打开参数配置窗体的按钮事件
$("#setting_win").on("click",function(){
	$.ajax({
		url:"/config/list",
		async:true,
		success:function(d) {
			if(d.code===200) {
				$(".div_shade").show();
				$(".setting_window").show();
				var data = d.data;
				$("#setting_table >tbody").empty();
				if(data.length>0){
					$("#setting_table").data("setting", data);
					var html = "";
					$(data).each(function(i, v) {
						html += "<tr line_id='" + v.id + "' >" + 
						"<td><input class='server_name'readonly='readonly' type='text' value='" + v.name + "' /></td>" +
						"<td><input class='server_ip' type='text' value='" + v.ip + "' /></td>" +
						"<td><input class='server_port' type='text' value='" + v.port + "' /></td>" +
						"</tr>";
					});
					$("#setting_table >tbody").append(html);
				}
			} else if(d.code === 401){
				showNote(d.message);
			}
		}
	});
});

//配置保存的按钮事件
$("#setting_save").on("click",function(e){
	var list = [];
	var modifySuccess = 0;
	var callback = [];
	$("#setting_table >tbody >tr").each(function(i, v){
		var postData = {};
		console.log($(v).find("input.server_port").val());
		postData.id = $(v).attr("line_id");
		postData.name = $(v).find("server_name").val();
		postData.port = $(v).find("input.server_port").val();
		postData.ip = $(v).find("input.server_ip").val();
		
		$.ajax({
			url: "/config/modify",
            type: "post",
            data: JSON.stringify(postData),
            contentType: "application/json",
            success: function (d) {
            	list.push(d.data);
				modifySuccess++;
				callback.push(1);
				if(callback.length == $("#setting_table >tbody >tr").length){
					$("#setting_table").data("setting", list);
					$(".div_shade").hide();
					$(".setting_window").hide();
					showNote("保存成功");
				}
            },
		});
	});
	return;
});

//窗体通用的“取消”按钮事件
$(".window_footer button.cancel").on("click",function(e){
	$(".div_shade").hide();
	$(e.target).closest(".window").hide();
});

//窗体通用的“关闭”按钮事件
$(".close_window").on("click",function(e){
	$(".div_shade").hide();
	$(e.target).closest(".window").hide();
});

//退出页面的按钮事件
$("#exit").on("click",function(){
	if (confirm("您确定要关闭本页吗？")){
		$.get("/logout",function(e){});
		window.opener=null;
		window.open('','_self');
		window.close();
		window.location.href="about:blank";
		window.close();
	}
});

//打印表格
var idTmr;

function getExplorer() {
    var explorer = window.navigator.userAgent;
    //ie  
    if(explorer.indexOf("MSIE") >= 0) {
        return 'ie';
    }
    //firefox  
    else if(explorer.indexOf("Firefox") >= 0) {
        return 'Firefox';
    }
    //Chrome  
    else if(explorer.indexOf("Chrome") >= 0) {
        return 'Chrome';
    }
    //Opera  
    else if(explorer.indexOf("Opera") >= 0) {
        return 'Opera';
    }
    //Safari  
    else if(explorer.indexOf("Safari") >= 0) {
        return 'Safari';
    }
}

function method5(tableid) {
    if(getExplorer() == 'ie') {
        var curTbl = document.getElementById(tableid);
        var oXL = new ActiveXObject("Excel.Application");
        var oWB = oXL.Workbooks.Add();
        var xlsheet = oWB.Worksheets(1);
        var sel = document.body.createTextRange();
        sel.moveToElementText(curTbl);
        sel.select();
        sel.execCommand("Copy");
        xlsheet.Paste();
        oXL.Visible = true;

        try {
            var fname = oXL.Application.GetSaveAsFilename("Excel.xls",
                "Excel Spreadsheets (*.xls), *.xls");
        } catch(e) {
            print("Nested catch caught " + e);
        } finally {
            oWB.SaveAs(fname);
            oWB.Close(savechanges = false);
            oXL.Quit();
            oXL = null;
            idTmr = window.setInterval("Cleanup();", 1);
        }

    } else {
        tableToExcel(tableid)
    }
}

function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
}
var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html><head><meta charset="UTF-8"></head><body><table  border="1">{table}</table></body></html>',
        base64 = function(
            s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        },
        format = function(s, c) {
            return s.replace(/{(\w+)}/g, function(m, p) {
                return c[p];
            })
        }
    return function(table, name) {
        if(!table.nodeType)
            table = document.getElementById(table)
        var ctx = {
            worksheet: name || 'Worksheet',
            table: table.innerHTML
        }
        window.location.href = uri + base64(format(template, ctx))
    }
})()

//获取当前日期
function CurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
   
    var clock = year + "/";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "/";
   
    if(day < 10)
        clock += "0";
       
    clock += day;
    return(clock); 
} 

//日期选择插件
    
