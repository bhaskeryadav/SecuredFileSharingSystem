package utils;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class FileUpload {

	private static String filePath = "c:/temp";
	public static SortedMap<String, String> getFile(HttpServletRequest request)
			throws Exception {
		//HttpSession ses = request.getSession();
		//Users usr = (Users) ses.getAttribute("user");

		SortedMap<String, String> key_values = new TreeMap<String, String>();

		File file;
		//Constants.homedir + "/"+ Constants.filePreviewPath ;
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") >= 0)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File(filePath));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxFileSize);
			try {
				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> i = fileItems.iterator();
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (!fi.isFormField()) {
						String fileName = fi.getName();
						
						//String g[]=fileName.split("\\.");
						//fileName=b_id+"."+g[g.length-1];
						
						
						if (fileName.lastIndexOf("\\") >= 0) {
							file = new File(filePath + "/" + fileName);
							if (!file.exists()) {
								file.createNewFile();
							}
						} else {
							file = new File(filePath + "/" + fileName);
						}
						fi.write(file);
						
						key_values.put("filename", file.getName());
						key_values.put("filesize", file.length()+"");
						key_values.put("filepath", file.getAbsolutePath());
					} else {
						key_values.put(fi.getFieldName(), fi.getString());
					}

				}

				for (Entry<String, String> entry : key_values.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();

					System.out.println(key + " = " + value);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return key_values;
	}
	
	/*public static byte[] getImageBytes(String path) throws Exception
	{
		File file=new File(path);
		FileOutputStream fos=new FileOutputStream(file);
		byte b[]=new byte[1024];
		fos.write(b);
		fos.flush();
		 
		return b;
		
	}
	
	public static String getFileName(int id,String path)throws Exception
	{
		String name=id+"_";
		File f=new File(path);
		name+=f.getName();
		return name;
	}*/

}
