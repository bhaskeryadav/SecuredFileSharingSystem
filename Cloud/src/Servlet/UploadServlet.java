package Servlet;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import utils.FileUpload;

public class UploadServlet extends HttpServlet {
   
	//private String filePath;
   /*private boolean isMultipart;
   private int maxFileSize = 50 * 1024;
   private int maxMemSize = 4 * 1024;
   private File file ;*/
   private final String UPLOAD_DIRECTORY="c:/temp";

   public void init( ){
      // Get the file location where it would be stored.
      //filePath =  getServletContext().getInitParameter("file-upload"); 
   }
   public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException {
	   String name="",path="";
	   File file=null;
	   //process only if its multipart content
       if(ServletFileUpload.isMultipartContent(request)){
           try {
              /* List<FileItem> multiparts = new ServletFileUpload(
                                        new DiskFileItemFactory()).parseRequest(request);
             
               for(FileItem item : multiparts){
                   if(!item.isFormField()){
                	   file=new File(item.getName());
                        name = file.getName();
                        file=new File(UPLOAD_DIRECTORY + File.separator + name);
                       item.write( file);//new File(UPLOAD_DIRECTORY + File.separator + name));
                   }
                   HttpSession ses=request.getSession();
                   ses.setAttribute("FilePath", file.getAbsolutePath());
                   ses.setAttribute("filename", file.getName());
                   ses.setAttribute("filesize", file.length());
               }
          */
        	   
        	   Map<String, String> ret=FileUpload.getFile(request);
        	   HttpSession ses=request.getSession();
               ses.setAttribute("FileDataMap", ret);
        	   System.out.println(ret);
              //File uploaded successfully
        	   ses.setAttribute("isStarted", null);
              request.setAttribute("message", "File Uploaded Successfully");
           } catch (Exception ex) {
              request.setAttribute("message", "File Upload Failed due to " + ex);
           }          
        
       }else{
           request.setAttribute("message",
                                "Sorry this Servlet only handles file upload request");
       }
       
      
       request.getRequestDispatcher("/Client.jsp").forward(request, response);
    
	   
       }
   public void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        
        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
   } 
}