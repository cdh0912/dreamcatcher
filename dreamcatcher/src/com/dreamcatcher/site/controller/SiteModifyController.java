package com.dreamcatcher.site.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.site.model.SiteDetailDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/siteModify")
public class SiteModifyController extends HttpServlet {
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
		//System.out.println("저장 경로 : " + saveDirectory);
		
		File file = new File(saveDirectory);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		File file2 = new File(saveDirectory+ File.separator + "thumb");
		if(!file2.exists()) {
			file2.mkdirs();
		}
		
		MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new MyFileRenamePolicy());
		
		String siteImageFile = StringCheck.nullToBlank(multi.getFilesystemName("siteImageFile"));
		
		int site_id = NumberCheck.nullToZero(multi.getParameter("site_id"));
		String savefolder =  StringCheck.nullToBlank(multi.getParameter("savefolder"));	// 기존 저장 폴더
		String saved_picture = StringCheck.nullToBlank(multi.getParameter("saved_picture"));	// 기존 저장 이미지
		String origin_picture = StringCheck.nullToBlank(multi.getParameter("origin_picture"));	// 기존 원본 이미지
		int type = NumberCheck.nullToZero(multi.getParameter("type"));
		
		// 새로운 이미지가 첨부되었을 경우
		if( !siteImageFile.isEmpty()){
			saved_picture = StringCheck.nullToBlank(multi.getParameter("saved_picture"));	// 기존 저장 이미지
			
			// 기존 이미지 파일 삭제
			String imagePath = realPath + File.separator + savefolder + File.separator;
			if(FileManager.deleteFile(imagePath + saved_picture))
				FileManager.deleteEmptyDirectory(imagePath);
	
			// 기존 썸네일 파일 삭제
			String thumbPath = realPath + File.separator + savefolder + File.separator + "thumb";
			if(FileManager.deleteFile(thumbPath + saved_picture))
				FileManager.deleteEmptyDirectory(thumbPath);
			
			// 새 썸네일 파일 저장
			String originalFileName = saveDirectory + File.separator + siteImageFile;
			String thumbFileName = saveDirectory + File.separator + "thumb"+ File.separator+siteImageFile;
			type = ThumbnailImage.createImage(originalFileName, thumbFileName, CommonConstant.THUMBNAIL_IMAGE_SIZE);	// 썸네일 이미지 저장

			origin_picture = multi.getOriginalFileName("siteImageFile");
			saved_picture =  multi.getFilesystemName("siteImageFile");
			savefolder = sfolder+"/"+sfolder2;
			
		}
		


		
		
		String site_name = StringCheck.nullToBlank(multi.getParameter("site_name"));
		String brief_info = StringCheck.nullToBlank(multi.getParameter("brief_info"));
		String detail_info = StringCheck.nullToBlank(multi.getParameter("content"));
		//System.out.println(brief_info + " "+detail_info);
		SiteDetailDto siteDetailDto = new SiteDetailDto();

		siteDetailDto.setSite_id(site_id);
		siteDetailDto.setSite_name(site_name);
		siteDetailDto.setBrief_info(brief_info);
		siteDetailDto.setDetail_info(detail_info);

		siteDetailDto.setOrigin_picture(origin_picture);
		siteDetailDto.setSaved_picture(saved_picture);
		siteDetailDto.setSavefolder(savefolder);
		siteDetailDto.setType(type);
		
		System.out.println("Site 수정 완료");
		int cnt = SiteServiceImpl.getInstance().siteModify(siteDetailDto);
		
		int page = NumberCheck.nullToOne(multi.getParameter("page"));
		String categoryMode = StringCheck.nullToBlank(multi.getParameter("searchMode"));
		String searchWord = StringCheck.nullToBlank(multi.getParameter("searchWord"));
		String searchMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(multi.getParameter("searchMode")));
		String viewMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(multi.getParameter("viewMode")));

		//System.out.println( "page=" +page+" categoryMode=" +categoryMode+"searchMode ="+searchMode + "searchWord  = "+searchWord+"viewMode = "+viewMode);
		
		HttpRequestWithModifiableParameters param = new HttpRequestWithModifiableParameters(request); 
		param.setParameter("page", ""+page); 
		param.setParameter("categoryMode", categoryMode); 
		param.setParameter("searchWord", searchWord); 
		param.setParameter("searchMode", searchMode); 
		param.setParameter("viewMode", viewMode); 

		request = (HttpServletRequest)param;
		
		siteDetailDto = SiteServiceImpl.getInstance().siteView(site_id);
		request.setAttribute("siteInfo", siteDetailDto);

		PageMove.forward(request, response, "/site/siteView.tiles");
	}

}