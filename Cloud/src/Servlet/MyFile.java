package Servlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pojos.FilesInDB;
import utils.JavaPropertyFileOperations;

/**
 * Servlet implementation class MyFile
 */
public class MyFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try	{
			String op=request.getParameter("op");
			if(op.equals("1"))
			{
				HttpSession ses=request.getSession(false);
				String name=(String) ses.getAttribute("uname");
				Set<FilesInDB> set=JavaPropertyFileOperations.getFileList( name);//new HashSet<FilesInDB>();
				request.setAttribute("list", set);
				request.getRequestDispatcher("/MyFile.jsp").forward(request, response);
			}else
				if(op.equals("0"))
				{
					String name=request.getParameter("select");
					boolean b=JavaPropertyFileOperations.delete(name);
					if(b)
					{
						request.setAttribute("mess", "yes");
						request.setAttribute("msg", "File is deleted.");
						request.getRequestDispatcher("/Error.jsp").forward(request, response);
					}
					else
					{
						request.setAttribute("msg", "File was not deleted.");
						request.getRequestDispatcher("/Error.jsp").forward(request, response);
					}
				}
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
