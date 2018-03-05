package com.jiesoft.framework.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public interface JettyServiceInterface {

	
	public void invoke(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
