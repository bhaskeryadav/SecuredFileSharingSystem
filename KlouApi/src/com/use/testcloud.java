package com.use;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kloudless.Kloudless;
import com.kloudless.exception.KloudlessException;
import com.kloudless.model.Account;
import com.kloudless.model.AccountCollection;
import com.kloudless.model.File;
import com.kloudless.model.Folder;
import com.kloudless.model.Link;
import com.kloudless.model.LinkCollection;
import com.kloudless.model.MetadataCollection;
import com.kloudless.net.KloudlessResponse;


public class testcloud {

	static Gson GSON = new GsonBuilder().create();
	
	public static void main(String ar[])
	{
		//Kloudless.apiKey = "INSERT API KEY HERE";
		try{
			
			AccountCollection accounts = Account.all(null);
			System.out.println(accounts);
			MetadataCollection contents = Folder.contents("root", "15590", null);
			System.out.println(contents);
		createFolder();	
		//uploadFile();
		downloadFile();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void downloadFile() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("------------------------------Starting Doenload--------------------------------------");
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("name", "testtesttest.txt");
		metadata.put("parent_id", "root");
		params.put("metadata", GSON.toJson(metadata));
		
		KloudlessResponse response = File.contents("fMTk5MzE2NjUzODE=", "15590", params);

		// For Binary Data
		String path = "c:/Demo/ttww.png";
		ByteArrayOutputStream outputStream = response.getResponseStream();
	FileOutputStream out = new FileOutputStream(path);
	outputStream.writeTo(out);
		out.close();

		// For String Data
	/*String path = "SOME OUTPUT PATH";
		String contents = response.getResponseBody();
	PrintWriter writer = new PrintWriter(path);
	writer.print(contents);
	writer.close();*/
		//System.out.println(contents);
		System.out.println("------------------------------End Doenload--------------------------------------");
	}

	private static void createFolder() {
		// TODO Auto-generated method stub
		
	}

	private static void uploadFile() throws Exception {
		// TODO Auto-generated method stub
		
		/*HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name", "C:\\Users\\USER\\Desktop\\g.txt");
		File fileInfo = File.save("test","15590", params);
		System.out.println(fileInfo);*/
		
		System.out.println("------------------------------Starting Upload--------------------------------------");
		String text = "Hello,World!";
		String path = "C:\\Users\\USER\\Pictures\\imgs\\blue.PNG";
		/*PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.println(text);
		writer.close();
		java.io.File f = new java.io.File(path);
		Scanner scanner = new Scanner(f);
		String contents = scanner.next();
		scanner.close();*/
		
		java.io.File f=new java.io.File(path);
		FileInputStream fis=new FileInputStream(f);
		byte b[]=new byte[(int) f.length()];
		fis.read(b);
		fis.close();

		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("name", "img1.png");
		metadata.put("parent_id", "root");
		params.put("metadata", GSON.toJson(metadata));
		params.put("file", b);//contents.getBytes());

		//System.out.println(params);

		File fileInfo = File.create("15590", params);
		System.out.println("The id : "+fileInfo.id);
		
		System.out.println("------------------------------End Upload--------------------------------------");
	}
}
