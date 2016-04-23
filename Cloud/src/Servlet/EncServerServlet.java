package Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FileEncodeTest;
import com.use.Cloud;

import aes256.AES;

import utils.JavaPropertyFileOperations;
import utils.FileConversion;



/**
 * Servlet implementation class EncServerServlet
 */
public class EncServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StringBuffer sb=new StringBuffer();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EncServerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			
			PrintWriter out=response.getWriter();
			
			String op=request.getParameter("op");
			if(op!=null&&op.equals("1"))
			{
				try{
					sb.delete(0, sb.toString().length());
				HttpSession ses=request.getSession(false);
				Map<String, String> ret=(Map<String, String>) ses.getAttribute("FileDataMap");
				String path=ret.get("filepath");
				String enc_type=ret.get("enc_type");
				System.out.println("asasadad ::::  "+path);
				String key=ret.get("key");
				long l1=System.currentTimeMillis();
				long m11=Runtime.getRuntime().freeMemory();
				sb.append("Converting file to hexadecimal format.<br>");
				path=FileConversion.convertToHexadecimal(path);
				sb.append("File is converted to hexadecimal format.<br>");
				sb.append("AES encryption started.<br>");
				
				String keypath=FileConversion.createKeyFile(key);
				
				String args[]=new String[7];
		    	args[0]="e";
		    	args[1]="-length";
		    	args[2]="256";
		    	args[3]="-mode";
		    	args[4]="ecb";
		    	args[5]=keypath;//"c:/temp/ke.txt";
		    	args[6]=path;//"c:/temp/key.txt";
				File tmp=new File(path);
		    	path=tmp.getParent()+File.separator+tmp.getName()+".enc";
				AES.main(args);
				long l2=System.currentTimeMillis();
				long m22=Runtime.getRuntime().freeMemory();
				if(enc_type.equals("AESandBlowfish"))
				{
					sb.append("Blowfish encryption starting..<br>");
					long l3=System.currentTimeMillis();
					long m1=Runtime.getRuntime().freeMemory();
					FileEncodeTest.main(new String[]{path,key,JavaPropertyFileOperations.blowfishDatFilePath});
					long m2=Runtime.getRuntime().freeMemory();
					long l4=System.currentTimeMillis();
					sb.append("Blowfish encryption finished.<br>");
					sb.append("Total time taken for blowfish is : "+((l4-l3)<0?(l4-l3)*-1:(l4-l3))+" milliseconds.<br>");
					sb.append("Total memory taken for blowfish is : "+((m1-m2)<0?(m1-m2)*-1:(m1-m2))+" bytes.<br>");
				}
				//AES.startEncryption(path, 1,key );
				sb.append("AES encryption finished.<br>");
				sb.append("Total time taken for AES is : "+((l2-l1)<0?(l2-l1)*-1:(l2-l1))+" milliseconds.<br>");
				sb.append("Total memory taken for AES is : "+((m11-m22)<0?(m11-m22)*-1:(m11-m22))+" bytes.<br>");
				ses.setAttribute("isStarted", "yes");
				//String pathOfEnc=CloudClient.sendToCloud(System.getProperty("user.home")+File.separator+"temp"+File.separator+ret.get("filename")+".txt.enc.ebf",(String) ses.getAttribute("uname"));
				String pathOfEnc=Cloud.uploadFile(System.getProperty("user.home")+File.separator+"temp"+File.separator+ret.get("filename")+".txt.enc.ebf");
				System.out.println("starting to store data ");
				JavaPropertyFileOperations.writeData(
						(String) ret.get("filename")
						,ret.get("filesize")+""
						, System.currentTimeMillis()+""
						,pathOfEnc
						,(String) ret.get("key")
						,(String) ses.getAttribute("uname")
						,enc_type
						);
				//saveData(request);
				AES.reset();
				//new File(path).delete();
				request.getRequestDispatcher("/Client.jsp").forward(request, response);
				}catch(Exception e)
				{
					sb.append(e.toString());
					e.printStackTrace();
				}
			}else
			{
				out.print("<b>Console : </b><br>");
				out.print(sb);
				
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		//sb=new StringBuffer();
	}

	private void saveData(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try{
			
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
