package com.toby.spring;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.toby.spring.contoller.HelloController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Application {

	public static void main(String[] args) {
		// Spring Container
		// resource, evnet, ...
		final GenericApplicationContext applicationContext = new GenericApplicationContext();
		// bean 등록 후 초기화하기
		applicationContext.registerBean(HelloController.class);
		applicationContext.refresh();

		// Tomcat 외 다른 서버도 구현할 수 있도록 interface로 명시되어 있음.
		final ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		final WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("frontcontroller", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws
					ServletException,
					IOException {
					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						final String name = req.getParameter("name");

						// 빈 불러오기
						final HelloController helloController = applicationContext.getBean(HelloController.class);
						// hello 호출에 대해 맵핑과 바인딩
						final String ret = helloController.hello(name);

						// 웹 응답의 3가지 요소
						// 1. status line에서 status code
						// 2. create header, especially contentType header
						// 3. body
						resp.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
						resp.getWriter().println(ret);
					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}
				}
			}).addMapping("/*");
		});
		webServer.start();
		// ----- Success WebServer Start -----
	}

}
