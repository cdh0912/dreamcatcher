package com.dreamcatcher.site.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.catalina.ssi.SSICommand;

import com.dreamcatcher.common.model.service.CommonServiceImpl;
import com.dreamcatcher.factory.SiteActionFactory;
import com.dreamcatcher.site.model.NationDto;
import com.dreamcatcher.site.model.SiteImageDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;

/**
 * Servlet implementation class SiteDeleteController
 */
@WebServlet("/siteDelete")
public class SiteDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String realPath;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		realPath = config.getServletContext().getRealPath("/upload");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int site_id = NumberCheck.nullToZero(request.getParameter("site_id"));

		SiteImageDto siteImageDto = SiteServiceImpl.getInstance().siteImage(site_id);

		if(siteImageDto != null){
			String savefolder = siteImageDto.getSavefolder();
			String saved_picture = siteImageDto.getSaved_picture();
	
			String imagePath = realPath + File.separator + savefolder + File.separator;
			if(FileManager.deleteFile(imagePath + saved_picture))
				FileManager.deleteEmptyDirectory(imagePath);
	
	
			String thumbPath = realPath + File.separator + "thumb" + File.separator + savefolder + File.separator;
			if(FileManager.deleteFile(thumbPath + saved_picture))
				FileManager.deleteEmptyDirectory(thumbPath);

			int cnt = SiteServiceImpl.getInstance().deleteSite(site_id);
		}
		
		
		int page = NumberCheck.nullToOne(request.getParameter("page"));
		String searchMod = StringCheck.nullToBlank(request.getParameter("searchMode"));
		String searchWord = null;
		if(request.getMethod().equals("GET"))
			searchWord = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchWord")));
		else
			searchWord = StringCheck.nullToBlank(request.getParameter("searchWord"));
		String queryString = "page=" + page + "&searchMod=" + searchMod + "&searchWord=" + UrlEncoder.encode(searchWord);

		String root = request.getContextPath();
		String path = SiteActionFactory.getSiteArticleListAction().execute(request, response);

		PageMove.forward(request, response, path + "?" + queryString);
	}

}
