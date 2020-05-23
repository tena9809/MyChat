
package gostr341094;

import java.io.File;
import java.math.BigInteger;

public class GOSTr341094 {

    public static void main(String[] args) throws Exception {
        String s1 = "GOSTR341094MD4";
        File test = new File("C:\\Java\\test.bmp");
        File test1 = new File("C:\\Java\\test1.bmp");
        MD4 md = new MD4();
        File f = new File("C:\\Java\\param.txt");
        File f1 = new File("C:\\Java\\text1.txt");
        GenNum gn = new GenNum(md.getBytesFromFile(test),16);
        gn.goToFile(f);
        GenNum gf = new GenNum(f);
        GenNum gj = new GenNum(md.getBytesFromFile(test1), f);
       // BigInteger big = gn.getQ();
        //System.out.println(big.toString(2));
        /*System.out.println("q " + gn.getQ());
        System.out.println("p " + gn.getP());
        System.out.println("a " + gn.getA());
        System.out.println("x " + gn.getX());
        System.out.println("y " + gn.getY());
        System.out.println("k " + gn.getK());
        System.out.println("r " + gn.getR());
        System.out.println("s " + gn.getS());
        System.out.println("h " + gn.getH()); 
        
        gn.getCode(s1.getBytes());
        System.out.println("v " + gn.getV());
*/
     
    }
    
}
