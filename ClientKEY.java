

import java.io.*;
import java.util.*;
import java.net.*;
import java.math.*;
/**
 *
 * @author Ruchir
 */
public class ClientKEY
 {
	public static double pk1;                         // private key
	public static double pbkey;
	public static int i=0;
	public static int [] val=new int[1000];           // to strore integer values
	public static byte[] keyfortr=new byte[16];		  // Final key
    public static void main(String srgs[])throws IOException
    {
		Socket s=null;
        BufferedInputStream get=null;
        PrintWriter put=null;
		String f;
		int [] s1=new int[10];
		//double pbkey;
		double[] key=new double[50];
        try
        { 
            s=new Socket("127.0.0.1",8085);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
	    int i2;
        PrintWriter pw= new PrintWriter(s.getOutputStream( ));
		BufferedReader br= new BufferedReader(new InputStreamReader(s.getInputStream( )));
		int len=0;
		try
		{
		while(true)
			{			
				s1=randomNumberBBS();           //random generated bits
				pk1=generatekey(s1);            //Private key
				pbkey=publickey(pk1);           //Public key
				String number=Double.toString(pbkey);
				System.out.println("GEnretatred publickey:"+number);
				pw.println(number);
				pw.flush();
				String s3;
				s3=br.readLine();
				System.out.println("received public key:"+s3);
				double n=Double.parseDouble(s3);
				
				key[i]=calkey(n);
				System.out.println("Sharedkey:"+key[i]);
				len=len+keylength(key[i]);
				System.out.println("Length:"+len);
				i++;
				if(len >= 128)
				{
					break;
				}
			}
		}
		catch(Exception e)
        { 
            System.out.println("Accept failed");
            System.exit(1);
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
		s.close();	
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
	
		public static double generatekey(int s[])
		{
			int last=0,i=0;
			int [] key= new int[150];
			for(int i1=0;i1<6;i1++)
			{     
				key[last]=s[i];
				i++;
				last++;
			}
			double pk=0;
			int j1=6;
            for(int i4=0;i4<6;i4++)
            {
				double zxc=Math.pow(2,j1);
				
		        pk=pk+zxc*key[i4];
				j1--;
            }
			return pk;
		}
		public static double publickey(double x)
		{
			double y; 
			int P=1223,A=5;
			System.out.println("XA here:"+x);
    		y=(Math.pow(A,x))%P;
			return y;
		}
		public static double calkey(double keyrec)
		{
			int p=1223;
			double keyf=Math.pow(keyrec,pk1)%p;
			//double keyf=Math.pow(pbkey,keyrec)%p;
			return keyf;
		}
		public static int keylength(double keyans)
		{
			int cnt=0;
			int keyans1=(int)keyans;
			while(keyans1>0)
			{
				cnt++;
				keyans1=keyans1/2;
			}
			return cnt;
		}
		public static int[] appandkey(double[] ckey)
		{
			int fi=0;	
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
			return val;
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
}
     

