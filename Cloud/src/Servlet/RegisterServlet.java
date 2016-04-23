package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.naming.java.javaURLContextFactory;

import utils.DiffeHelmanKeyDistribution;
import utils.JavaPropertyFileOperations;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        JavaPropertyFileOperations.intializeResources();
        // TODO Auto-generated constructor stub
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
			String data[]=new String[7];
			data[0]=request.getParameter("Name");
			data[1]=request.getParameter("LastName");
			data[2]=request.getParameter("Email");
			data[3]=request.getParameter("username");
			data[4]=request.getParameter("Password");
			data[5]=request.getParameter("birthday_day")+"/"+request.getParameter("birthday_month")+"/"+request.getParameter("birthday_year");
			data[6]=request.getParameter("gender");
			/*for	(String t: data)
			{
				System.out.println(t);
			}
			*/
			int r=JavaPropertyFileOperations.registerData(data);
			
			if(r==1)
			{
				request.setAttribute("msg", "The username "+data[3]+" is unavailable.<br>Please try something else.<br>Thank you.");
				request.getRequestDispatcher("/Error.jsp").forward(request, response);
			}
			if(r==0)
			{
				HttpSession ses=request.getSession();
				ses.setAttribute("uname", data[3]);
				request.setAttribute("N", DiffeHelmanKeyDistribution.N);
				request.setAttribute("G", DiffeHelmanKeyDistribution.G);
				request.setAttribute("msg", "<span style=\"color: #8ca379;\">Registration was successful.</span>");
				request.getRequestDispatcher("/KeyDistribution.jsp").forward(request, response);
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
