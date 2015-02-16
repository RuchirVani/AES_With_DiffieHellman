
import java.io.*;
import java.util.*;
import java.net.*;
import java.math.*;

/*
 *
 * @author Ruchir
 */
public class ServerKEY
       { 
			public static int [] val=new int[500];               // Key values in integer
			public static int fi;								// counter
			public static byte[] keyfortr=new byte[16];			// Final key 
			public static double pk2;							// generated privte key in each-round
			public static int i=0;
			public static double pbkey2;
			public static void main(String args[])throws IOException
            {
				 int [] s2=new int[10];                         // binaryfile for number of strings
				 //double pbkey2;
             	 ServerSocket ss=null;
				 try
                 {  
                     ss=new ServerSocket(8085);
                 }
                 catch(IOException e)
                 { 
                     System.out.println("couldn't listen");
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
                 } 
				 PrintWriter pw= new PrintWriter(cs.getOutputStream( ));
				 BufferedReader br= new BufferedReader(new InputStreamReader(cs.getInputStream( )));
				 int len=0;
				 String s;
				 double[] key=new double[50];
				 try
				 {
					while(true)
					{	
						s2=randomNumberBBS();                         //random number
						pk2=generatekey(s2);                          // private key
						pbkey2=publickey(pk2);                        // public key
						String number=Double.toString(pbkey2);          
						System.out.println("GEnretatred publickey:"+number);
						pw.println(number);
						pw.flush();
						s=br.readLine();
						System.out.println("received public key:"+s);
						double n=Double.parseDouble(s);
						key[i]=calkey(n);
						System.out.println("Sharedkey:"+key[i]);
						i++;
					}
				}
				catch(Exception e)
                { 
                    System.out.println("Accept failed");
                }
				int[] xvr=new int[1000];
				appandkey(key);
				for(int g=0;g<200;g++)
				{
					xvr[g]=val[g];
				}
				createByte(xvr);
				for(int j2=0;j2<16; j2++)
				{
					System.out.println(keyfortr[j2]);
				}
				
				cs.close();
                ss.close();		
				try 
				{
					File file = new File("key.txt");
					FileOutputStream fop = new FileOutputStream(file);
 					if (!file.exists())
					{
						file.createNewFile();
					}
				fop.write(keyfortr);
				fop.flush();
				fop.close();
				System.out.println("Done");
 
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
		public static int[] randomNumberBBS()
		{
			int seed=3,p=11,q=19,x;
            int number,randomNumber,it=0;
            int [] b= new int[10];    
            x= seed;
			for(int i=0;i<6;i++)
			{
				it++;
				randomNumber = (x*x)%(p*q);
				b[i]= randomNumber%2;
				x = randomNumber;
			}
			return b;
		}   
		public static double generatekey(int s2[])
		{
			int last=0,i=0;
			int [] key2= new int[150];
			for(int i1=0;i1<6;i1++)
                {
                    key2[last]=s2[i];
                    i++;
					last++;
		        }
			double pk=0;
			int j1=6;
            for(int i4=0;i4<6;i4++)
            {
				double zxc=Math.pow(2,j1);
				
		        pk=pk+zxc*key2[i4];
				j1--;
            }
			return pk;
		}
		public static double publickey(double x)
		{
			double y; 
			int P=1223,A=5;
			System.out.println("XB here:"+x);
    		y=(Math.pow(A,x))%P;
			return y;
		}
		public static double calkey(double keyrec)
		{
			int p=1223;
			double keyf=(Math.pow(keyrec,pk2))%p;
			//double keyf=Math.pow(pbkey2,keyrec)%p;
			System.out.println(Math.pow(pbkey2,keyrec));
			return keyf;
		}
		public static int keylength(double keyans)
		{
			int cnt=0;
			int keyans1=(int)keyans;
			while(keyans1>0)
			{
				cnt++;
				keyans=keyans1/2;
			}
			return cnt;
		}
		public static void appandkey(double[] ckey)
		{
			fi=0;	
			for(int j=0;j<i;j++)
			{
				int keya=(int) ckey[j];
				while(keya>0)
				{
					val[fi]=keya%2;
					fi++;
					keya=keya/2;
				}
			}
			System.out.println("Fianl key of 128-bit:");
			for(int j=0;j<128;j++)
				{
					System.out.println(val[j]);
				} 
		}
		public static void createByte(int a[])
		{
			int cntb=0;
			int[][] n= new int[20][10];
			for(int j=0;j<16;j++)
			{
				for(int k=0;k<8;k++)
				{
					n[j][k]=a[cntb];
					cntb++;
				}
			}
			int asdf=0;
			for(int j3=0;j3<16;j3++)
			{
				double x=0;
				for(int k3=8;k3>0;k3--)
				{
					x=x+Math.pow(2,k3)*n[j3][k3];
				}
				int y=(int)x;
				keyfortr[j3]=(byte)x;
			}
		}
		public byte[] returnkey()
		{
			return keyfortr;
		}
	
	} 
    