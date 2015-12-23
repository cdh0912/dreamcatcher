package com.dreamcatcher.common.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.factory.*;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.PageMove;

@WebServlet("/common")
public class CommonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(CharacterConstant.DEFAULT_CHAR);
		execute(request, response);
	}
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String root = request.getContextPath();
		String act = request.getParameter("act");

		String path = "/main";

		if("autoComplete".equals(act)) {
			CommonActionFactory.getCommonAutoCompleteAction().execute(request, response);
			
		} else if("locationCategory".equals(act)) {
			System.out.println("action = " + act);
			CommonActionFactory.getCommonLocationCategoryAction().execute(request, response);
		} else if("".equals(act)) {

		} else if("".equals(act)) {
			
		} else {
			PageMove.redirect(response, root + path);
		}
	}
}