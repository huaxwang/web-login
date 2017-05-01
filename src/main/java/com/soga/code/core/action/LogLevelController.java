package com.soga.code.core.action;

import com.soga.code.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/${sysPath}/log")
public class LogLevelController {
	
	@Value("${codePath}")
	private String codePath;
	
	@RequestMapping(value="/level", method=RequestMethod.GET)
	public void level(HttpServletRequest request,ModelMap model){
		String level = request.getParameter("level");
		String codePath = request.getParameter("codepath");
		if(StringUtil.isEmpty(codePath)){
			codePath = this.codePath;
		}
		if(level!=null){
			LogbackLoggingSystem logbackLoggingSystem = new LogbackLoggingSystem(this.getClass().getClassLoader());
	   	 	//TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF
			if(level.equals("trace")){
	   	 		logbackLoggingSystem.setLogLevel(codePath, LogLevel.TRACE);
	   	 	}else if(level.equals("debug")){
	   	 		logbackLoggingSystem.setLogLevel(codePath, LogLevel.DEBUG);
	   	 	}else if(level.equals("info")){
	   	 		logbackLoggingSystem.setLogLevel(codePath, LogLevel.INFO);
	   	 	}else if(level.equals("warn")){
	   	 		logbackLoggingSystem.setLogLevel(codePath, LogLevel.WARN);
	   	 	}else if(level.equals("error")){
	   	 		logbackLoggingSystem.setLogLevel(codePath, LogLevel.ERROR);
	   	 	}else if(level.equals("fatal")){
	   	 		logbackLoggingSystem.setLogLevel(codePath, LogLevel.FATAL);
	   	 	}else if(level.equals("off")){
	   	 		logbackLoggingSystem.setLogLevel(codePath, LogLevel.OFF);
	   	 	}
			System.out.println("[LogLevelController][level]切换日志输出级别："+level);
		}
	}
}
