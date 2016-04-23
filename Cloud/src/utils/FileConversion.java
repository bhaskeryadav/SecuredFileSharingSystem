package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileConversion {

	/**
	 * @param args
	 */
	
	public static String homeDir=System.getProperty("user.home")+File.separator+"temp"+File.separator;
	public static String prefix=".txt";
	
	public static String convertToHexadecimal(String path) {
		// TODO Auto-generated method stub

		try {
			File f = new File(path);//new File(args[0]);//new File("C:\\Users\\b.pandhari.yadav\\Desktop\\tryitimg.gif");
			FileInputStream fis = new FileInputStream(f);
			byte b[] = new byte[(int) f.length()];
			fis.read(b);
			fis.close();
			System.out.println("file length : "+f.length());
			String name=f.getName();
			

			/*
			 * byte ba[]=str.getBytes();
			 * System.out.println("ba.length   "+ba.length);
			 */
			int rem = b.length % 16;
			byte ba[] = new byte[rem];
			for (int i = 0; i < ba.length; i++) {
				
				ba[i]=0;
			}
			
			/*System.out.println("b.lenghth "+b.length);
			System.out.println("ba.lenghth "+ba.length);
			*/
		/*	for(byte a:b)
			{
				System.out.print(a+" ");
			}*/

			StringBuilder strb = new StringBuilder();
			f = new File(homeDir+name+prefix);
			// FileWriter fw=new FileWriter(f);
			PrintWriter fw = new PrintWriter(f);
			for (int i = 0; i < ba.length+b.length; i++) {
				if(i<b.length)
				strb.append(String.format("%02X", b[i]));
				else
					strb.append(String.format("%02X", ba[i-b.length]));

				if ((i + 1) % 16 == 0) {

					//System.out.println(" "+i +"  "+ strb.toString().trim().length());
					fw.println(strb.toString().trim());
					strb = strb.delete(0, strb.length());
					//System.out.println("insdie if");

				}

			}

			fw.flush();
			fw.close();
			
			return f.getAbsolutePath();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String createKeyFile(String str) throws Exception {
		// TODO Auto-generated method stub
	/*String folderPath = System.getProperty("user.home")
				+ "\\temp\\";*/
		File f1 = new File(homeDir + "key.txt");
		if (!f1.exists()) {
            f1.delete();
        }




            StringBuffer sb1=new StringBuffer();
            byte b[]=str.getBytes();
                    for (int i = 0; i < b.length; i++) {
                     //   System.out.print(b[i]+" ");
                        sb1.append(b[i]);
                    }


            str=sb1.toString();

                if(str.length()<64)
                {
                    StringBuffer sb=new StringBuffer(str);
                    int len=str.length();

                    for(int i=0;i<(64-len);i++)
                    {
                        sb.append("0");
                    }

                    str=sb.toString();

                }
                else
                     if (str.length()>64)
                        {
                         str=str.substring(0, 64);

                        }
			f1.createNewFile();
			FileOutputStream fos1 = new FileOutputStream(f1);
			//str = "66666666666666666666666666666666";
			// DataOutputStream dos=new DataOutputStream(fos1);
			// dos.writeUTF(str);
			// dos.flush();
			fos1.write(str.getBytes());
			fos1.flush();
			// dos.close();
			fos1.close();
			
			return f1.getAbsolutePath();

	}

}
