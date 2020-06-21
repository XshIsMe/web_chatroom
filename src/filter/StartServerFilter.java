package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.java_websocket.WebSocketImpl;

import websocket.WebSocket;

public class StartServerFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		Object o = session.getAttribute("userName");
		if (o == null) {
			// request.getRequestDispatcher("Login.html").forward(req, resp);
			response.sendRedirect("Login.html");
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("开始启动WebSocket");
		WebSocketImpl.DEBUG = false;
		int port = 8888; // 端口随便设置，只要不跟现有端口重复就可以
		WebSocket s = null;
		s = new WebSocket(port);
		s.start();
		System.out.println("启动WebSocket成功");
	}

}
