package web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.WriteFile;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("write".equals(req.getParameter("action"))) {
			// 将聊天记录写入文件
			String message = req.getParameter("message");
			WriteFile wf = new WriteFile();
			wf.write(message);
		}
		// 文件名
		String fileName = "E:/message.txt";
		// 设置响应头
		resp.setContentType(getServletContext().getMimeType(fileName));
		resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		// 输出到浏览器
		InputStream in = new BufferedInputStream(new FileInputStream(fileName));
		OutputStream out = resp.getOutputStream();
		int temp;
		while ((temp = in.read()) != -1) {
			out.write(temp);
		}
		out.close();
		in.close();
	}

}
