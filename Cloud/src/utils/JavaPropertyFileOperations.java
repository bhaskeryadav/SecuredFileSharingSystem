package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.use.Cloud;

import pojos.FilesInDB;
import sun.security.util.BigInt;

public class JavaPropertyFileOperations {

	public static Properties prop = new Properties();
	private static final String propFileLoc = "EncData";
	private static final String userDetails = "UserData";
	private static final String keyDir="KeyDir";
	public static File dbFilesDir;
	public static File dbUserDir;
	public static File keyDirF;
	public static String homeDir=System.getProperty("user.home");
	public static String blowfishDatFilePath;

	public static void intializeResources() {
		try {
			// os=new FileOutputStream(propFileLoc);
			 dbFilesDir = new File(propFileLoc);
			if (!dbFilesDir.exists()) {
				dbFilesDir.mkdir();
			}
			
			dbUserDir = new File(userDetails);
			if (!dbUserDir.exists()) {
				dbUserDir.mkdir();
			}
			
			keyDirF = new File(keyDir);
			if (!keyDirF.exists()) {
				keyDirF.mkdir();
			}
			
			File blowfishDatFile=new File(homeDir+File.separator+"BLOWFISH.DAT");
			
			if(!blowfishDatFile.exists())
			{
			File blowfishDat=new File("/resource/BLOWFISH.DAT");
			blowfishDat.renameTo(blowfishDatFile);
			}
			System.out.print(blowfishDatFile.exists());
			blowfishDatFilePath=blowfishDatFile.getAbsolutePath();

		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public static void writeData(String... ar) {
		FileOutputStream fos = null;
		try {
			String path = propFileLoc + File.separator + ar[0] + ".prop";
			File fileTemp = new File(path);
			if (!fileTemp.exists()) {
				fileTemp.createNewFile();
			}
			System.out.println("tttttttttttt  : "+fileTemp.getAbsolutePath());
			fos = new FileOutputStream(fileTemp);
			String name = ar[0];

			prop.setProperty("name", ar[0]);
			prop.setProperty("size", ar[1]);
			prop.setProperty("uploadedon", ar[2]);
			prop.setProperty("location", ar[3]);
			prop.setProperty("key", ar[4]);
			prop.setProperty("uploadedby", ar[5]);
			prop.setProperty("enc_type", ar[6]);
			
			prop.store(fos, "data stored for file " + name);
		} catch (Exception errException) {
			errException.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int registerData(String... ar)
	{
		int ret=0;
		try	{
			FileOutputStream fos = null;
			try {
				String path = userDetails + File.separator +"user_"+ ar[3] + ".prop";
				File fileTemp = new File(path);
				if (!fileTemp.exists()) {
					fileTemp.createNewFile();
				}
				else
				{
					// 1 means username already exists.
					return 1;
				}
				System.out.println("tttttttttttt  : "+fileTemp.getAbsolutePath());
				fos = new FileOutputStream(fileTemp);
				
				
				
				prop.setProperty("fname", ar[0]);
				prop.setProperty("lname", ar[1]);
				prop.setProperty("email", ar[2]);
				prop.setProperty("uname", ar[3]);
				prop.setProperty("password", ar[4]);
				prop.setProperty("dob", ar[5]);
				prop.setProperty("gender", ar[6]);

				prop.store(fos, "Data registered for user " + ar[3]);
			} catch (Exception errException) {
				errException.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		intializeResources();
		writeData("as1.mp3", "dd", "ff", "gg", "hh", "uu");
	}

	public static boolean validate(String username, String key) {
		// TODO Auto-generated method stub
		try{
			//System.out.println("pppppppppppp  : "+(dbFilesDir.getAbsolutePath()+File.separator+filename+".prop"));
			FileInputStream fis=new FileInputStream(keyDirF.getAbsolutePath()+File.separator+"user_"+username+".prop");
			Properties prop=new Properties();
			prop.load(fis);
			String serverPrivateKey=prop.getProperty("serverPrivateKey");
			String sharedkey=prop.getProperty("sharedkey");
			String pubKey=DiffeHelmanKeyDistribution.getPublicKey(new BigInteger(serverPrivateKey),new BigInteger(sharedkey)).toString();
			fis.close();
			if(pubKey.equals(key))
				return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static String getFilePath(String filename) {
		// TODO Auto-generated method stub
		try{
			FileInputStream fis=new FileInputStream(dbFilesDir.getAbsolutePath()+File.separator+filename+".prop");
			Properties prop=new Properties();
			prop.load(fis);
			String filePath=prop.getProperty("location");
			fis.close();
			return filePath;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static boolean authenticate(String uname, String pwd) {
		// TODO Auto-generated method stub
		try{
			FileInputStream fis=new FileInputStream(dbUserDir.getAbsolutePath()+File.separator+"user_"+uname+".prop");
			Properties prop=new Properties();
			prop.load(fis);
			String storedPwd=prop.getProperty("password");
			fis.close();
			if(storedPwd.equals(pwd))
				return true;
		}
		catch(FileNotFoundException fne)
		{
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static Set<FilesInDB> getFileList() {
		// TODO Auto-generated method stub
		Set<FilesInDB> ret=new HashSet<FilesInDB>();
		try{
			File []files=dbFilesDir.listFiles();
			FileInputStream fis=null;
			Properties prop=null;
			
			FilesInDB fid=null;
			for (File t : files) {
				fis = new FileInputStream(t);
				prop = new Properties();
				prop.load(fis);
				fid=new FilesInDB();
				fid.setFileName(prop.getProperty("name"));
				fid.setSize(Long.parseLong(prop.getProperty("size")));
				fid.setUploadedOn(new Date(Long.parseLong(prop.getProperty("uploadedon"))));
				
				ret.add(fid);
				
				fis.close();
			}

		}catch(Exception e)
		{
			//e.printStackTrace();
		}
		return ret;
	}

	public static String getEnc_type(String filename) {
		// TODO Auto-generated method stub
		try{
			FileInputStream fis=new FileInputStream(dbFilesDir.getAbsolutePath()+File.separator+filename+".prop");
			Properties prop=new Properties();
			prop.load(fis);
			String enc_type=prop.getProperty("enc_type");
			fis.close();
			return enc_type;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Set<FilesInDB> getFileList(String name) {
		// TODO Auto-generated method stub
		Set<FilesInDB> ret=new HashSet<FilesInDB>();
		try{
			File []files=dbFilesDir.listFiles();
			FileInputStream fis=null;
			Properties prop=null;
			
			FilesInDB fid=null;
			for (File t : files) {
				fis = new FileInputStream(t);
				prop = new Properties();
				prop.load(fis);
				fid=new FilesInDB();
				fid.setFileName(prop.getProperty("name"));
				fid.setSize(Long.parseLong(prop.getProperty("size")));
				fid.setUploadedOn(new Date(Long.parseLong(prop.getProperty("uploadedon"))));
				if(name.equals(prop.getProperty("uploadedby")))
				{
				 ret.add(fid);
				}
				fis.close();
			}

		}catch(Exception e)
		{
			//e.printStackTrace();
		}
		return ret;
	}

	public static boolean delete(String filename) {
		// TODO Auto-generated method stub
		try{
			String path=dbFilesDir.getAbsolutePath()+File.separator+filename+".prop";
			Cloud.delete(getFilePath(filename));
			File f=new File(path);
			return f.delete();
			//FileInputStream fis=new FileInputStream(dbFilesDir.getAbsolutePath()+File.separator+filename+".prop");
			//Properties prop=new Properties();
			//prop.load(fis);
			//String enc_type=prop.getProperty("enc_type");
			//return enc_type;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		//return false;
	}

	public static void storePrivateKey(String user, String key, String pubKey) {
		// TODO Auto-generated method stub
		try{
			File f=new File(keyDirF.getAbsolutePath()+File.separator+"user_"+user+".prop");
			if(!f.exists())
				f.createNewFile();
			File fileTemp=f;
			FileInputStream fis=new FileInputStream(fileTemp);
			Properties prop=new Properties();
			prop.load(fis);
			FileOutputStream fos = new FileOutputStream(fileTemp);
			prop.setProperty("serverPrivateKey", key);
			prop.setProperty("sharedkey", pubKey);
			prop.store(fos, "key stored for file ");
			fos.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getSharedKey(String user) {
		// TODO Auto-generated method stub
		try{
			File f=new File(keyDirF.getAbsolutePath()+File.separator+"user_"+user+".prop");
			if(!f.exists())
				f.createNewFile();
			File fileTemp=f;
			FileInputStream fis=new FileInputStream(fileTemp);
			Properties prop=new Properties();
			prop.load(fis);
			String privateKey=prop.getProperty("serverPrivateKey");
			String sharedkey=DiffeHelmanKeyDistribution.getSharedKeyFromServer(new BigInteger(privateKey)).toString();
			fis.close();
			return sharedkey;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String getUserForFile(String filename) {
		// TODO Auto-generated method stub
		try{
			File f=new File(dbFilesDir.getAbsolutePath()+File.separator+filename+".prop");
			if(!f.exists())
				f.createNewFile();
			File fileTemp=f;
			FileInputStream fis=new FileInputStream(fileTemp);
			Properties prop=new Properties();
			prop.load(fis);
			String privateKey=prop.getProperty("uname");
			return privateKey;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String getFileKey(String filename) {
		// TODO Auto-generated method stub
		try{
			File f=new File(dbFilesDir.getAbsolutePath()+File.separator+filename+".prop");
			if(!f.exists())
				f.createNewFile();
			File fileTemp=f;
			FileInputStream fis=new FileInputStream(fileTemp);
			Properties prop=new Properties();
			prop.load(fis);
			String privateKey=prop.getProperty("key");
			return privateKey;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	
	

}
