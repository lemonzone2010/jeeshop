package net.jeeshop.core.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class Uploadify extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyyMMdd");
	private static final Logger logger = Logger.getLogger(Uploadify.class);

	private String getTomcatRootPath() {
		return this.getServletConfig().getServletContext().getRealPath("/");
	}

	private String getPathByDate() {
		return "/upload/" + DATEFORMAT.format(new Date());
	}

	private void prepare(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
	}

	private List<FileItem> getFileItemFromRequest(HttpServletRequest request) throws FileUploadException {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		return upload.parseRequest(request);
	}

	private List<String> copyFiles2Path(List<FileItem> fileList) throws IOException {
		List<String> relPathList = new ArrayList<String>();
		for (FileItem fileItem : fileList) {
			String fileRelPath = getPathByDate() + "/" + fileItem.getName(); // 文件相对路径(WEB级别)
			String fileAbsPath = getTomcatRootPath() + fileRelPath; // 文件输出路径(OS级别)
			
			prepareSaveFileTargetPath(getTomcatRootPath() + getPathByDate());
			
			BufferedInputStream inputStream = new BufferedInputStream(fileItem.getInputStream());
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(fileAbsPath)));
			Streams.copy(inputStream, outStream, true);
			
			relPathList.add(fileRelPath);
		}
		return relPathList;
	}

	private void prepareSaveFileTargetPath(String filepath) {
		File file = new File(filepath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
	}

	/**
	 * 实现多文件的同时上传
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 预处理request和response
			prepare(request, response);

			// 从Request中获得文件对象
			List<FileItem> fileList = getFileItemFromRequest(request);

			// 遍历上传文件写入磁盘
			List<String> copyFiles2Path = copyFiles2Path(fileList);

			response.getOutputStream().print(JSON.toJSONString(copyFiles2Path));
		} catch (Exception e) {
			logger.error("文件上传失败", e);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
