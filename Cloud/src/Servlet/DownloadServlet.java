package Servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FileDecodeTest;
import com.FileEncodeTest;
import com.use.Cloud;

import aes256.AES;

import pojos.FilesInDB;
import utils.DiffeHelmanKeyDistribution;
import utils.JavaPropertyFileOperations;
import utils.FileConversion;
/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String folderName="C:/temp/"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
try{
			
			String op=request.getParameter("op");
			
			if(op!=null&&op.equals("1"))
			{
				
				Set<FilesInDB> set=JavaPropertyFileOperations.getFileList();
				request.setAttribute("list", set);
				request.setAttribute("publicKey", JavaPropertyFileOperations.getSharedKey(request.getSession().getAttribute("uname")+""));
				request.setAttribute("N", DiffeHelmanKeyDistribution.N);
				request.setAttribute("G", DiffeHelmanKeyDistribution.G);
				request.getRequestDispatcher("/Download.jsp").forward(request, response);
			}
			else{
				String key=request.getParameter("B");
				String filename=request.getParameter("select");
				boolean bool=JavaPropertyFileOperations.validate(request.getSession().getAttribute("uname")+"",key);
				String enc_type=JavaPropertyFileOperations.getEnc_type(filename);
				System.out.println("boooooooooool : "+bool);
				if(bool)
				{
				System.out.println("asasasas "+filename);
				
				key=JavaPropertyFileOperations.getFileKey(filename);
				String keypath=FileConversion.createKeyFile(key);
				
				//String path=CloudClient.downloadStoredFile(JavaPropertyFileOperations.getFilePath(filename));//FileConversion.homeDir+filename+FileConversion.prefix+".enc.ebf";
				String id=JavaPropertyFileOperations.getFilePath(filename);
				String path=FileConversion.homeDir+filename+FileConversion.prefix+".enc.ebf";
				 path=Cloud.downloadFile(id, path);
				
				if(enc_type.equals("AESandBlowfish"))
				{
					long l3=System.currentTimeMillis();
					FileDecodeTest.main(new String[]{path,key,JavaPropertyFileOperations.blowfishDatFilePath});
					long l4=System.currentTimeMillis();
					path=FileConversion.homeDir+filename+FileConversion.prefix+".enc.ebf.dbf";
				}
				else
				{
					path=FileConversion.homeDir+filename+FileConversion.prefix+".enc";
				}
				
				String args[]=new String[7];
		    	args[0]="d";
		    	args[1]="-length";
		    	args[2]="256";
		    	args[3]="-mode";
		    	args[4]="ecb";
		    	args[5]=keypath;//"c:/temp/ke.txt";
		    	args[6]=path;//"c:/temp/key.txt";
				
		    	
				AES.main(args);
				
				String decFilePath=FileConversion.homeDir+filename;
				
				
				
				String filePath=decFilePath;//JavaPropertyFileOperations.getFilePath(filename);//"C:\\Data\\apache.rar";
		        File file = new File(filePath);
		        int length   = 0;
		        ServletOutputStream outStream = response.getOutputStream();
		        ServletContext context  = getServletConfig().getServletContext();
		        String mimetype = context.getMimeType(filePath);
		       
		        // sets response content type
		        if (mimetype == null) {
		            mimetype = "application/octet-stream";
		        }
		        response.setContentType(mimetype);
		        response.setContentLength((int)file.length());
		        String fileName = (new File(filePath)).getName();
		       
		        // sets HTTP header
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		       
		        byte[] byteBuffer = new byte[4096];
		        DataInputStream in = new DataInputStream(new FileInputStream(file));
		       
		        // reads the file's bytes and writes them to the response stream
		        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
		        {
		            outStream.write(byteBuffer,0,length);
		        }
		       
		        in.close();
		        outStream.close();
		        AES.reset();
				}
				else{
					request.setAttribute("msg", "The private key is <span style=\"color:red;\">Invalid</span>.<br>Please try again.");
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
		try{
			
			String op=request.getParameter("op");
			
			if(op!=null&&op.equals("1"))
			{
				Set<FilesInDB> set=new HashSet<FilesInDB>();
				File f=new File(folderName);
				File files[]=f.listFiles();
				FilesInDB fid=new FilesInDB();
				for(File temp:files)
				{
					fid.setFileName(temp.getName());
					fid.setSize(temp.length());
					fid.setUploadedOn(new Date(temp.lastModified()));
					set.add(fid);
				}
				
				request.setAttribute("list", set);
				request.getRequestDispatcher("/Download.jsp").forward(request, response);
			}
			else{
				String f=request.getParameter("select");
				System.out.println("asdsadsdasd "+f);
				String filePath="C:\\Data\\apache.rar";
		        File file = new File(filePath);
		        int length   = 0;
		        ServletOutputStream outStream = response.getOutputStream();
		        ServletContext context  = getServletConfig().getServletContext();
		        String mimetype = context.getMimeType(filePath);
		       
		        // sets response content type
		        if (mimetype == null) {
		            mimetype = "application/octet-stream";
		        }
		        response.setContentType(mimetype);
		        response.setContentLength((int)file.length());
		        String fileName = (new File(filePath)).getName();
		       
		        // sets HTTP header
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		       
		        byte[] byteBuffer = new byte[4096];
		        DataInputStream in = new DataInputStream(new FileInputStream(file));
		       
		        // reads the file's bytes and writes them to the response stream
		        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
		        {
		            outStream.write(byteBuffer,0,length);
		        }
		       
		        in.close();
		        outStream.close();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
