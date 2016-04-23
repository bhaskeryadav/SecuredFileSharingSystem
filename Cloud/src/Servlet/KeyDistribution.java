package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.DiffeHelmanKeyDistribution;
import utils.JavaPropertyFileOperations;

/**
 * Servlet implementation class KeyDistribution
 */
public class KeyDistribution extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeyDistribution() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			String key=request.getParameter("B");
			HttpSession ses=request.getSession();
			String user= ses.getAttribute("uname").toString();
			//JavaPropertyFileOperations.storeSharedKey(user,key);
			JavaPropertyFileOperations.storePrivateKey(user, DiffeHelmanKeyDistribution.generateKey(),key);
			
			request.setAttribute("msg", "<span style=\"color: #8ca379;\">Registration is completed.</span>");
			request.getRequestDispatcher("/Error.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
