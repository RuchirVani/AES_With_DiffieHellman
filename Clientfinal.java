import java.io.*;
import java.util.*;
import java.net.*;
import java.math.*;
/**
 *
 * @author Ruchir
 */
public class Clientfinal {
	public static File  f1,f3;
    public static void main(String srgs[])throws IOException
    {
		byte[] tkey;     // Key for Encryption 
        Socket s=null;
        BufferedInputStream get=null;
        PrintWriter put=null;
		String f;
		String path;
		int count1=0;
		int size;
	//File  f1,f3;
		f3=new File("key.txt");
		String msgk= new Scanner(f3).useDelimiter("\\Z").next();
		byte[] msgb2=msgk.getBytes();
		tkey=msgb2;
		//System.out.println(tkey.length);
		try
        { 
            s=new Socket("127.0.0.1",8085);
            get=new BufferedInputStream(s.getInputStream());
            put=new PrintWriter(s.getOutputStream(),true);
			int u;
            String str = "C:/Users/Ruchir/Desktop/tp/";                      
			path=str+"numbers.txt";									
            f1=new File("numbers.txt");
			FileOutputStream  fs=new FileOutputStream(new File(str,f1.toString()));
            String output;
			int chunksize=16;                          // read byte size ( as AES deal with 16 bytes data)
			byte jj[]=new byte[chunksize];
			AES test=new AES(tkey);                    //AES class have methods for Encryption and Decryption
			while((u=get.read(jj,0,16))!=-1)
            { 
				//System.out.println(new String(jj));
                fs.write(jj,0,u);
				count1++;
            } 
            fs.close();
			System.out.println("File received");
        }
		catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
	    s.close();		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(f1))));
		int[] abc= new int[1000000]; 			// Numbers of bits of files
		String[] line = new String[1000];       // Numbers of line
		int t=0;
		int n=0;         
		System.out.println("Recieved Binary file:");
		while ((line[t] = reader.readLine()) != null)
		{
			System.out.println(line[t]);
			//=String.suline[t];
			String t1=line[t].substring(0,line[t].length()-1);
			if(t1.length()<8)
			{
				//System.out.println("here");
				abc[n]=Integer.parseInt(t1, 2);
				//System.out.println(abc[n]);
			}
			else
			{
				String t2=t1.substring(line[t].length()-8,line[t].length()-1);
				int xyz=Integer.parseInt(t2, 2);
				abc[n]=(128-xyz)*-1;
				//System.out.println(abc[n]);
			}
			n++;
		}
		int ni=n/16;
		int chunksize=16;
		int nid=0;
		//System.out.println(abc.length);
		byte[][] enc = new byte[ni][chunksize];       // To strore the Encryted byte recevied from file
		byte[][] dec = new byte[ni][chunksize];       // To store the Decrupted byte generated from input.
		
		for(int i=0;i<ni;i++)
		{
			for(int j=0;j<16;j++)
			{		
				enc[i][j]=(byte)abc[nid];
				nid++;
				//System.out.println(enc[i][j]);
			}
		}
		/*for(int i=0;i<ni;i++)
		{
			for(int j=0;j<16;j++)
			{
				System.out.println(enc[i][j]);
			}		
		}*/
		byte[] msgb=new byte[20];
		//System.out.println("len:"+enc.length);
		String Decryptedfile="";                         // To store the Decryption in string for storing it in file
		AES test=new AES(tkey);
		for(int i = 0; i < enc.length ; i++)
		{
			//System.out.println(msgb.length);
			msgb=enc[i];
			//System.out.println("Encrypted:"+new String(msgb));
			dec[i]=test.decrypt(msgb);
			//System.out.println("Decrpted:" + new String(dec[i]));
			Decryptedfile=Decryptedfile + new String(dec[i]);
		}
		System.out.println("your message after Decryption:");
		System.out.println(Decryptedfile);
		try
		{
 			File file = new File("C:/Users/Ruchir/Desktop/tp/numbers.txt"); // Enter the file name as you want to store
			FileOutputStream fop = new FileOutputStream(file);
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			byte[] contentInBytes = Decryptedfile.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			//System.out.println("Done");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}			
}