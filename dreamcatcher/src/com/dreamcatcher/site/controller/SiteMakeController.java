package com.dreamcatcher.site.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.site.model.SiteDetailDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/siteMake")
public class SiteMakeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String realPath;
	private int maxPostSize;
	private String encoding;
	String[] allow_file = { "jpg", "png", "bmp", "gif" , "pdf" };
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		realPath = config.getServletContext().getRealPath("/upload");
		maxPostSize = CommonConstant.UPLOAD_IMAGE_SIZE;
		encoding = CharacterConstant.DEFAULT_CHAR;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(CharacterConstant.DEFAULT_CHAR);
		
		String root = request.getContextPath();
		
		String saveDirectory = realPath;
		DateFormat df = new SimpleDateFormat("yyyy");
		String sfolder = df.format(new Date());
		saveDirectory += File.separator + sfolder;
		df = new SimpleDateFormat("MMdd");
		String sfolder2 = df.format(new Date());
		saveDirectory += File.separator + sfolder2;
		System.out.println("저장 경로 : " + saveDirectory);
		
		File file = new File(saveDirectory);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		File file2 = new File(saveDirectory+ File.separator + "thumb");
		if(!file2.exists()) {
			file2.mkdirs();
		}
		
		MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new MyFileRenamePolicy());
		
		String originalFileName = saveDirectory + File.separator + multi.getFilesystemName("siteImageFile");
		String thumbFileName = saveDirectory + File.separator + "thumb"+ File.separator+multi.getFilesystemName("siteImageFile");
		int type = ThumbnailImage.createImage(originalFileName, thumbFileName, CommonConstant.THUMBNAIL_IMAGE_SIZE);	// 썸네일 이미지 저장
		
		
		//int pg = NumberCheck.nullToOne(multi.getParameter("pg"));
		//String key = StringCheck.nullToBlank(multi.getParameter("key"));
		//String word = Encoder.serverCharToDefaultChar(multi.getParameter("word"));
		
	//	String queryString = "bcode=" + bcode + "&pg=" + pg + "&key=" + key + "&word=" + UrlEncoder.encode(word);
		//System.out.println("QS = " + queryString);
		
		Double latitude = NumberCheck.nullToDoubleZero(multi.getParameter("latitude"));
		Double longitude = NumberCheck.nullToDoubleZero(multi.getParameter("longitude"));
		String nation_code = StringCheck.nullToBlank(multi.getParameter("nation_code"));
		String address = StringCheck.nullToBlank(multi.getParameter("address"));
		String site_name = StringCheck.nullToBlank(multi.getParameter("site_name"));
		String brief_info = StringCheck.nullToBlank(multi.getParameter("brief_info"));
		String detail_info = StringCheck.nullToBlank(multi.getParameter("content"));
		String loc_name = StringCheck.nullToBlank(multi.getParameter("loc_name"));
		SiteDetailDto siteDetailDto = new SiteDetailDto();

		HttpSession session = request.getSession();

		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");

		//System.out.println("latitude : " +latitude+" & longitude : "+longitude);
		siteDetailDto.setLatitude(latitude);
		siteDetailDto.setLongitude(longitude);
		siteDetailDto.setNation_code(nation_code);
		siteDetailDto.setAddress(address);
		siteDetailDto.setSite_name(site_name);
		siteDetailDto.setBrief_info(brief_info);
		siteDetailDto.setDetail_info(detail_info);
		siteDetailDto.setLoc_name(loc_name);
		siteDetailDto.setId(memberDto.getId());
		//siteDetailDto.setId("dreamcatchul@gmail.com");

		siteDetailDto.setType(type);
		
		siteDetailDto.setOrigin_picture(multi.getOriginalFileName("siteImageFile"));
		siteDetailDto.setSaved_picture(multi.getFilesystemName("siteImageFile"));
		siteDetailDto.setSavefolder(sfolder+"/"+sfolder2);
		
		int cnt = SiteServiceImpl.getInstance().siteMake(siteDetailDto);
		PageMove.redirect(response, root+"/plan/planMapView.jsp");
	}

}