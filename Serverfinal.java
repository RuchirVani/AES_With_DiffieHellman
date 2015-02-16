

import java.io.*;
import java.util.*;
import java.net.*;
import java.math.*;

/**
 *
 * @author Ruchir
 */
public class Serverfinal
         { 
		 public static void main(String args[])throws IOException
             { 
				 String str = "C:/Users/Ruchir/Desktop/";
                 String path = str + "numbers.txt";                                   // File you want to Encrypt
				 File f=new File(path);
				 String msg= new Scanner(f).useDelimiter("\\Z").next();
				 System.out.println("Message from File:"+msg);
				 byte[] msgb=new byte[20];
				 byte[] msgb1=msg.getBytes();
				 byte [] tkey;
				 File f1=new File("key.txt");
				 String msgk= new Scanner(f1).useDelimiter("\\Z").next();
				 byte[] msgb2=msgk.getBytes();
				 tkey=msgb2;
				 AES test=new AES(tkey);                                               // Call to AES Class
				 int chunksize=16;								// Byte size (as we are using AES it's 16)
				 byte[][] ret = new byte[(int)Math.ceil(msgb1.length / (double)chunksize)][chunksize]; // to store data in byte for reading from file
				 byte[][] enc = new byte[(int)Math.ceil(msgb1.length / (double)chunksize)][chunksize]; // to store the data to byte fromat after encryption
				 int start = 0;
				 String str1 = Integer.toString(ret.length);
				 int x=(msgb1.length/chunksize);
				 //put1.println(str1);
				 for(int i = 0; i <ret.length ; i++) 
				{
					if(start>chunksize)
					{
						break;
					}
					ret[i] = Arrays.copyOfRange(msgb1,start, chunksize);
					start = chunksize ;
					chunksize=chunksize+16;
				}
				String Encryptedfile="";                              // String to store Encrypted text
				for(int i = 0; i < ret.length; i++)
				{
					msgb=ret[i];
					//System.out.println("msg:"+msgb);
					enc[i]=test.encrypt(msgb);
					System.out.println("Encrpted text:" + new String(enc[i]));
					Encryptedfile=Encryptedfile + new String(enc[i]);
				}
				System.out.println("your message after Encryption:");
				System.out.println(Encryptedfile);
				int [][] xa=new int[ret.length][16];     // to conver into binary
				int cv=ret.length*16;
				String [] binaryfile= new String[cv*8];   // binary string
				int k=0;
				System.out.println("Binary File:");
				for(int i = 0; i < ret.length; i++)
				{
					for(int j=0;j<16;j++)
					{
						xa[i][j]=enc[i][j];
						String bin = Integer.toBinaryString(xa[i][j]);
						binaryfile[k]=bin;
						System.out.println(binaryfile[k]);
						k++;
						//System.out.println(xa[i][j]);
					}
				}
				try
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter(path));
					for ( int i = 0; i < k; i++)
					{	      
						writer.write(binaryfile[i]+ " \n");                             /// Storing data in form of binay
					}
						writer.close();
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				// Sending file to client
				
                ServerSocket ss=null;
                try
                {  
                     ss=new ServerSocket(8085);
                }
                catch(IOException e)
                { 
                     System.out.println("couldn't listen");
                     System.exit(0);
                }
                Socket cs=null;
                 try
                 { 
                     cs=ss.accept();
                     System.out.println("Connection established"+cs);
                 }
                 catch(Exception e)
                 { 
                     System.out.println("Accept failed");
                     System.exit(1);
                 } 
                 BufferedOutputStream put=new BufferedOutputStream(cs.getOutputStream());
                 BufferedReader st=new BufferedReader(new InputStreamReader(cs.getInputStream()));
				 PrintWriter put1=new PrintWriter(cs.getOutputStream(),true);
				 
			     if(f.isFile())
                 { 
					FileInputStream fis=new FileInputStream(f);
                     byte []buf=new byte[16];
                     int read;
                     while((read=fis.read(buf,0,16))!=-1)
					 {
                         put.write(buf,0,read);
                         put.flush();
                     }
				 } 
			     //System.out.println(ret.length);
				 System.out.println("File transfered");					 
				 cs.close();
                 ss.close();
			}
		
		} 
    