package com.use;

import java.io.FileInputStream;
import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;


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


public class Cloud {


	static Gson GSON = new GsonBuilder().create();
	
	public static void main(String ar[])
	{
		String path="C:/Demo/testBlue.png";//"C:\\Users\\USER\\Pictures\\imgs\\blue.PNG";
		String id="fMTk5MzIzNTkxMTc=";
		//String ret=uploadFile(path);
		//String ret2=downloadFile(id,path);
		delete("fMTk5NDcyMTQ0NjU=");
		
	}

	public static String downloadFile(String id,String path) {
		// TODO Auto-generated method stub
		try{
			System.out.println("------------------------------Starting Doenload--------------------------------------");
			HashMap<String, Object> params = new HashMap<String, Object>();
			HashMap<String, Object> metadata = new HashMap<String, Object>();
			// metadata.put("name", "testtesttest.txt");
			metadata.put("parent_id", "root");
			params.put("metadata", GSON.toJson(metadata));

			KloudlessResponse response = File.contents(id, "15590", params);
			//FileConversion.homeDir+name
			// For Binary Data
			// String path = "c:/Demo/ttww.png";
			ByteArrayOutputStream outputStream = response.getResponseStream();
			FileOutputStream out = new FileOutputStream(path);
			outputStream.writeTo(out);
			out.close();
			System.out.println("------------------------------End Doenload--------------------------------------");
			return path;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String uploadFile(String path) {
		// TODO Auto-generated method stub
		try{
			
			System.out.println("------------------------------Starting Upload--------------------------------------");
			
			 //path = "C:\\Users\\USER\\Pictures\\imgs\\blue.PNG";
			java.io.File f=new java.io.File(path);
			FileInputStream fis=new FileInputStream(f);
			byte b[]=new byte[(int) f.length()];
			fis.read(b);
			fis.close();

			HashMap<String, Object> params = new HashMap<String, Object>();
			HashMap<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("name", f.getName());
			metadata.put("parent_id", "root");
			params.put("metadata", GSON.toJson(metadata));
			params.put("file", b);//contents.getBytes());

			//System.out.println(params);

			File fileInfo = File.create("15590", params);
			
			System.out.println("The id : "+fileInfo.id);
			
			System.out.println("------------------------------End Upload--------------------------------------");
			
			return fileInfo.id;
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean delete(String path)
	{
		try{
			HashMap<String, Object> params = new HashMap<String, Object>();
			HashMap<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("parent_id", "root");
			params.put("metadata", GSON.toJson(metadata));
			File.delete(path, "15590", params);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
