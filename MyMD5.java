
package gostr341094;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyMD5{
    
  private static final int A = 0x67452301;
  private static final int B = (int)0xEFCDAB89L;
  private static final int C = (int)0x98BADCFEL;
  private static final int D = 0x10325476;
 
  private static final int[] t = new int[64];
  static {
    for (int i = 0; i < 64; i++)
      t[i] = (int)(long)((1L << 32) * Math.abs(Math.sin(i + 1)));
  }
 
  public static byte[] getMyHash(byte[] str){
        int strLen = str.length;
        int numBlocks = ((strLen + 8) >>> 6) + 1;
        int totalLen = numBlocks << 6;
        byte[] zeroBytes = new byte[totalLen - strLen];
        zeroBytes[0] = (byte)0x80;
        long strLenBits = (long)strLen << 3;
        for (int i = 0; i < 8; i++) {
          zeroBytes[zeroBytes.length - 8 + i] = (byte)strLenBits;
          strLenBits >>>= 8;
        }
        int a = A;
        int b = B;
        int c = C;
        int d = D;
        int[] buffer = new int[16];
        for (int k = 0; k < numBlocks;k++){
          int index = k << 6;
          for (int j = 0; j < 64; j++, index++)
            buffer[j >>> 2] = ((int)((index < strLen) ? str[index] : zeroBytes[index - strLen]) << 24) | (buffer[j >>> 2] >>> 8);
          int constA = a;
          int constB = b;
          int constC = c;
          int constD = d;
          int i = 0;
            a = FF(a,b,c,d,buffer[0],7,t[i++]);
            d = FF(d,a,b,c,buffer[1],12,t[i++]);
            c = FF(c,d,a,b,buffer[2],17,t[i++]);
            b = FF(b,c,d,a,buffer[3],22,t[i++]);
            a = FF(a,b,c,d,buffer[4],7,t[i++]);
            d = FF(d,a,b,c,buffer[5],12,t[i++]);
            c = FF(c,d,a,b,buffer[6],17,t[i++]);
            b = FF(b,c,d,a,buffer[7],22,t[i++]);
            a = FF(a,b,c,d,buffer[8],7,t[i++]);
            d = FF(d,a,b,c,buffer[9],12,t[i++]);
            c = FF(c,d,a,b,buffer[10],17,t[i++]);
            b = FF(b,c,d,a,buffer[11],22,t[i++]);
            a = FF(a,b,c,d,buffer[12],7,t[i++]);
            d = FF(d,a,b,c,buffer[13],12,t[i++]);
            c = FF(c,d,a,b,buffer[14],17,t[i++]);
            b = FF(b,c,d,a,buffer[15],22,t[i++]); 

           //2 round
            a = GG(a,b,c,d,buffer[1],5,t[i++]);
            d = GG(d,a,b,c,buffer[6],9,t[i++]);
            c = GG(c,d,a,b,buffer[11],14,t[i++]);
            b = GG(b,c,d,a,buffer[0],20,t[i++]);
            a = GG(a,b,c,d,buffer[5],5,t[i++]);
            d = GG(d,a,b,c,buffer[10],9,t[i++]);
            c = GG(c,d,a,b,buffer[15],14,t[i++]);
            b = GG(b,c,d,a,buffer[4] ,20,t[i++]);
            a = GG(a,b,c,d,buffer[9],5,t[i++]);
            d = GG(d,a,b,c,buffer[14],9,t[i++]);
            c = GG(c,d,a,b,buffer[3],14,t[i++]);
            b = GG(b,c,d,a,buffer[8],20,t[i++]);
            a = GG(a,b,c,d,buffer[13],5,t[i++]);
            d = GG(d,a,b,c,buffer[2],9,t[i++]);
            c = GG(c,d,a,b,buffer[7],14,t[i++]);
            b = GG(b,c,d,a,buffer[12],20,t[i++]); 

            //3 round
            a = HH(a,b,c,d,buffer[4],4,t[i++]);
            d = HH(d,a,b,c,buffer[8],11,t[i++]);
            c = HH(c,d,a,b,buffer[11],16,t[i++]);
            b = HH(b,c,d,a,buffer[14],23,t[i++]);
            a = HH(a,b,c,d,buffer[1],4,t[i++]);
            d = HH(d,a,b,c,buffer[4],11,t[i++]);
            c = HH(c,d,a,b,buffer[7],16,t[i++]);
            b = HH(b,c,d,a,buffer[10],23,t[i++]);
            a = HH(a,b,c,d,buffer[13],4,t[i++]);
            d = HH(d,a,b,c,buffer[0],11,t[i++]);
            c = HH(c,d,a,b,buffer[3],16,t[i++]);
            b = HH(b,c,d,a,buffer[6],23,t[i++]);
            a = HH(a,b,c,d,buffer[9],4,t[i++]);
            d = HH(d,a,b,c,buffer[12],11,t[i++]);
            c = HH(c,d,a,b,buffer[15],16,t[i++]);
            b = HH(b,c,d,a,buffer[2],23,t[i++]); 

            //4 round
            a = II(a,b,c,d,buffer[0],6,t[i++]);
            d = II(d,a,b,c,buffer[7],10,t[i++]);
            c = II(c,d,a,b,buffer[14],15,t[i++]);
            b = II(b,c,d,a,buffer[5],21,t[i++]);
            a = II(a,b,c,d,buffer[12],6,t[i++]);
            d = II(d,a,b,c,buffer[3],10,t[i++]);
            c = II(c,d,a,b,buffer[10],15,t[i++]);
            b = II(b,c,d,a,buffer[1],21,t[i++]);
            a = II(a,b,c,d,buffer[8],6,t[i++]);
            d = II(d,a,b,c,buffer[15],10,t[i++]);
            c = II(c,d,a,b,buffer[6],15,t[i++]);
            b = II(b,c,d,a,buffer[13],21,t[i++]);
            a = II(a,b,c,d,buffer[4],6,t[i++]);
            d = II(d,a,b,c,buffer[11],10,t[i++]);
            c = II(c,d,a,b,buffer[2],15,t[i++]);
            b = II(b,c,d,a,buffer[9],21,t[i++]); 

          a += constA;
          b += constB;
          c += constC;
          d += constD;}
        byte[] md5 = new byte[16];
        int count = 0;
        for (int l = 0; l < 4; l++){
            int n = 0;
            if(l == 0){
                n = a;
            }
            else if(l == 1){
                n = b;
            }
            else if(l == 2){
                n = c;
            }
            else
                n = d;
            for (int j = 0; j < 4; j++){
                md5[count++] = (byte)n;
                n >>>= 8;
            }
        }
        return md5;   
    }
    
    private static int functionF(int x, int y, int z){
        return ((x & y) | (~x & z));
    }
    private static int functionG(int x, int y, int z){
        return ((x & z) | (y & ~z));
    }
    private static int functionH(int x, int y, int z){
        return (x ^ y ^ z);
    }
    private static int functionI(int x, int y, int z){
        return (y ^ (x | ~z));
    } 
        
    private static int FF(int a, int b, int c, int d, int buffer, int s, int t){
        a = a + functionF(b,c,d) + buffer + t;
        a = (a << s) | (a >>> (32 - s));
        return (a + b);
    }
    
    private static int GG(int a, int b, int c, int d, int buffer, int s, int t){
        a = a + functionG(b,c,d) + buffer + t;
        a = (a << s) | (a >>> (32 - s));
        return (a + b);
    }
    
    private static int HH(int a, int b, int c, int d, int buffer, int s, int t){
        a = a + functionH(b,c,d) + buffer + t;
        a = (a << s) | (a >>> (32 - s));
        return (a + b);
    }
    
    private static int II(int a, int b, int c, int d, int buffer, int s, int t){
        a = a + functionI(b,c,d) + buffer + t;
        a = (a << s) | (a >>> (32 - s));
        return (a + b);
    }
 
  public static String toHexString(byte[] b)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < b.length; i++)
    {
      sb.append(String.format("%02X", b[i] & 0xFF));
    }
    return sb.toString();
  }
  
  public static byte[] getBytesFromFile(File in) throws IOException {
    FileInputStream inputStream = new FileInputStream(in);
    int len = (int)in.length();
    byte[] block = new byte[len];
    for (int i = 0; i < len; i++){
        block[i] = (byte) inputStream.read();   
    } 
    return block;
  }
}
