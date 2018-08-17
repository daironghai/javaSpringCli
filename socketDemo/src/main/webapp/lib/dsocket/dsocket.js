/**
 * dsocket连接
 * @param {Object} options 配置对象
 */
var dsocket = function(options){
	//默认配置
	var defaults = {
		url : "ws://localhost:8082/socketDemo/main", //主链接地址
		src : "http://localhost:8082/socketDemo" //资源访问前缀
	}
	//最终配置
	var settings = $.extend({}, defaults, options);
	//连接对象
	var wsObj = null;
	//接收处理方法map
	var receiveMethodArr = [];
	//系统日志输出标记
	var sysLogTip = "dsocket.js log output ---- "
	
	/**************************************************/
	/**
	 * 测试方法
	 */
	this.test = function(){
		console.log(settings)
	}
	/**
	 * 获取资源连接
	 */
	this.getSrc = function(url){
		return settings.src + "/" + url
	}
	/**
	 * 内部日志打印
	 */
	this.log = function(txt){
		console.log(sysLogTip + txt)
	}
	/**
	 * 获取接收处理方法map集合
	 */
	this.getReceiveMethodMap = function(){
		return receiveMethodArr;
	}
	/**
	 * 添加接收处理方法
	 */
	this.addReceiveMethod = function(cmd, callback){
		if(null!=cmd && ""!=cmd){
			receiveMethodArr[cmd] = callback;
		}
	}
	/**
	 * 连接方法
	 */
	this.connect = function(success, error){
		this.log("socket init")
		var url = settings.url;
		if(null==url || ""==url){
			url = defualtUrl
		}
		wsObj = new WebSocket(url);
		if(null==wsObj || wsObj.readyState==3){
			this.log("socket init error")
			error()
			return
		}
		this.log("socket init success")
		//挂载接收处理方法
		wsObj.onmessage = function(evnt){
			var data = evnt.data;
			var jsonData = JSON.parse(data)
			if(null!=jsonData.cmd && ""!=jsonData.cmd){
				var callbackFunc = receiveMethodArr[jsonData.cmd]
				if(null!=callbackFunc){
					delete jsonData.cmd
					callbackFunc(jsonData);
				}
			}
		}
		console.log(wsObj)
		success()
		//监听页面刷新、关闭事件
		window.onbeforeunload = function(){
			if(wsObj.readyState==1){
				wsObj.close()
			}
		}
	}
	/**
	 * 发送文本命令
	 */
	this.sendTextCmd = function(cmd, data, success, error){
		if(null==cmd || ""==cmd
			|| null==data || ""==data){
			error("参数不得为空")
			return
		}
		if(wsObj.readyState==1){
			var obj = $.extend({}, data, {cmd : cmd});
			var sendData = JSON.stringify(obj);
			//console.log(sendData)
			if(null==receiveMethodArr[cmd]){
				receiveMethodArr[cmd] = success;
			}
			wsObj.send(sendData);
		}else{
			error("未连接至服务器，请求无效");
		}
	}
	
	
	return this;
}
