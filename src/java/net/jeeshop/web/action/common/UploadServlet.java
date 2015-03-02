package net.jeeshop.web.action.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断提交过来的表单是否为文件上传菜单
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 构造一个文件上传处理对象
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			Iterator items;
			try {
				// 解析表单中提交的所有文件内容
				items = upload.parseRequest(request).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					if (!item.isFormField()) {
						// 取出上传文件的文件名称
						String name = item.getName();
						// 取得上传文件以后的存储路径
						String fileName = name.substring(name.lastIndexOf('\\') + 1, name.length());
						// 上传文件以后的存储路径
						String path = request.getRealPath("file") + File.separatorChar + fileName;

						// 上传文件
						File uploaderFile = new File(path);
						item.write(uploaderFile);
						// 打印上传成功信息
						response.setContentType("text/html");
						response.setCharacterEncoding("GB2312");
						PrintWriter out = response.getWriter();
						out.print("<font size='2'>上传文件为:" + name + "<br>保存的地址为" + path + "</font>");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
