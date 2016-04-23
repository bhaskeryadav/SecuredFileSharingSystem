package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JavaPropertyFileOperations;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        JavaPropertyFileOperations.intializeResources();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    }
    
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	super.destroy();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			String uname=request.getParameter("u_name");
			String pwd=request.getParameter("password");
			PrintWriter out=response.getWriter();
			if(JavaPropertyFileOperations.authenticate(uname,pwd))
			{
				HttpSession ses=request.getSession();
				ses.setAttribute("uname", uname);
				request.getRequestDispatcher("/HomeFrame.jsp").forward(request, response);
			}
			else 
			{
				request.setAttribute("msg", "The username and/or password is invalid.<br>Please try again.<br>Thanks.");
				request.getRequestDispatcher("/Error.jsp").forward(request, response);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
