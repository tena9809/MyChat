package gostr341094;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MD4 {
        private static final int A = 0x67452301;
        private static final int B = (int)0xEFCDAB89L;
        private static final int C = (int)0x98BADCFEL;
        private static final int D = 0x10325476;

        public static byte[] getMyHash(byte[] str){
            int length = str.length;
            int numBlocks = ((length + 8) >>> 6) + 1;
            int absLen = numBlocks << 6;
            byte[] zeroBytes = new byte[absLen - length];
            zeroBytes[0] = (byte)0x80;
            long lengthBits = (long)length << 3;
            for (int i = 0; i < 8; i++) {
                zeroBytes[zeroBytes.length - 8 + i] = (byte)lengthBits;
                lengthBits >>>= 8;
            }
            int a = A;
            int b = B;
            int c = C;
            int d = D;
            int[] buffer = new int[16];
            for (int k = 0; k < numBlocks;k++){
                int i = k << 6;
                for (int j = 0; j < 64; j++, i++)
                    buffer[j >>> 2] = ((int)((i < length) ? str[i] : zeroBytes[i - length]) << 24) | (buffer[j >>> 2] >>> 8);
                int AA = a;
                int BB = b;
                int CC = c;
                int DD = d;

                //1st round
                a = FF(a, b, c, d, buffer[0], 3);
                d = FF(d, a, b, c, buffer[ 1], 7);
                c = FF(c, d, a, b, buffer[ 2], 11);
                b = FF(b, c, d, a, buffer[ 3], 19);
                a = FF(a, b, c, d, buffer[ 4], 3);
                d = FF(d, a, b, c, buffer[ 5],  7);
                c = FF(c, d, a, b, buffer[ 6], 11);
                b = FF(b, c, d, a, buffer[ 7], 19);
                a = FF(a, b, c, d, buffer[ 8], 3);
                d = FF(d, a, b, c, buffer[ 9], 7);
                c = FF(c, d, a, b, buffer[10], 11);
                b = FF(b, c, d, a, buffer[11], 19);
                a = FF(a, b, c, d, buffer[12], 3);
                d = FF(d, a, b, c, buffer[13], 7);
                c = FF(c, d, a, b, buffer[14], 11);
                b = FF(b, c, d, a, buffer[15], 19);

                //2nd round
                a = GG(a,b,c,d,buffer[0],3);
                d = GG(d,a,b,c,buffer[4],5);
                c = GG(c,d,a,b,buffer[8],9);
                b = GG(b,c,d,a,buffer[12],13);
                a = GG(a,b,c,d,buffer[1],3);
                d = GG(d,a,b,c,buffer[5],5);
                c = GG(c,d,a,b,buffer[9],9);
                b = GG(b,c,d,a,buffer[13] ,13);
                a = GG(a,b,c,d,buffer[2],3);
                d = GG(d,a,b,c,buffer[6],5);
                c = GG(c,d,a,b,buffer[10],9);
                b = GG(b,c,d,a,buffer[14],13);
                a = GG(a,b,c,d,buffer[3],3);
                d = GG(d,a,b,c,buffer[7],5);
                c = GG(c,d,a,b,buffer[11],9);
                b = GG(b,c,d,a,buffer[15],13);

                //3th round
                a = HH(a,b,c,d,buffer[0],3);
                d = HH(d,a,b,c,buffer[8],9);
                c = HH(c,d,a,b,buffer[4],11);
                b = HH(b,c,d,a,buffer[12],15);
                a = HH(a,b,c,d,buffer[2],3);
                d = HH(d,a,b,c,buffer[10],9);
                c = HH(c,d,a,b,buffer[6],11);
                b = HH(b,c,d,a,buffer[14],15);
                a = HH(a,b,c,d,buffer[1],3);
                d = HH(d,a,b,c,buffer[9],9);
                c = HH(c,d,a,b,buffer[5],11);
                b = HH(b,c,d,a,buffer[13],15);
                a = HH(a,b,c,d,buffer[3],3);
                d = HH(d,a,b,c,buffer[11],9);
                c = HH(c,d,a,b,buffer[7],11);
                b = HH(b,c,d,a,buffer[15],15);

                a += AA;
                b += BB;
                c += CC;
                d += DD;
            }
            byte[] md4 = new byte[16];
            int count = 0;
            for (int j = 0; j < 4; j++) {
                md4[count++] = (byte) a;
                md4[count++] = (byte) b;
                md4[count++] = (byte) c;
                md4[count++] = (byte) d;
                a >>>= 8;
                b >>>= 8;
                c >>>= 8;
                d >>>= 8;
            }
            return md4;
        }

        private static int FF (int a, int b, int c, int d, int x, int s) {
            a = a + ((b & c) | (~b & d)) + x;
            return (a << s) | (a >>> (32 - s));
        }
        private static int GG (int a, int b, int c, int d, int x, int s) {
            a = a + ((b & (c | d)) | (c & d)) + x + 0x5A827999;
            return (a << s) | (a >>> (32 - s));
        }
        private static int HH (int a, int b, int c, int d, int x, int s) {
            a = a + (b ^ c ^ d) + x + 0x6ED9EBA1;
            return (a << s) | (a >>> (32 - s));
        }

        public static String toStr(byte[] b)
        {
            StringBuilder sb = new StringBuilder();
            //System.out.println(b.length);
            int j = 0;
            while(j < 4) {
                for (int i = 0 + j; i < b.length; ) {
                    sb.append(String.format("%02X", b[i] & 0xFF));
                    i = i + 4;
                }
                j++;
            }
            return sb.toString();
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
