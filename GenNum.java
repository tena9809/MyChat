
package gostr341094;

import java.math.BigInteger;
import java.util.*;
import static gostr341094.MD4.*;
import java.io.*;

public class GenNum {
    private Random rand;
    private static final BigInteger minus = new BigInteger("-1", 10);
    private static final BigInteger two = new BigInteger("2");
    private static final BigInteger four = new BigInteger("4");
    private static final BigInteger eight = new BigInteger("8");
    private static final BigInteger three = new BigInteger("3");
    private static final BigInteger five = new BigInteger("5");
    private static final BigInteger seven = new BigInteger("7");
    private int len;
    private BigInteger p, q, a, x, y, k, r, h, s, h1, u1, u2, v;
    public GenNum(byte[] bytes, int len) {
        rand = new Random();
        this.len = len;
        this.q = getRandom(len);
        this.p = getRandom(q);
        this.a = getRandom(p,q);
        this.x = getXbyRandom(q);
        this.y = powMod(a,x,p);
        this.k = getXbyRandom(q);
       this.r = getR(a,k,p,q);
        this.h = getHash(bytes);
       this.s = getS(k, h, x, r, q);
        
    }
    
    public GenNum(File f) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(f));
       String line;
       List<String> lines = new ArrayList<String>();
       while((line = in.readLine()) != null) {
           lines.add(line);
       }
        String[] numbers = lines.toArray(new String[lines.size()]);
        this.p = new BigInteger(numbers[0]);
        this.q = new BigInteger(numbers[1]);
        this.a = new BigInteger(numbers[2]);
        this.y = new BigInteger(numbers[3]);
        this.k = new BigInteger(numbers[4]);
        this.s = new BigInteger(numbers[5]);
        this.r = new BigInteger(numbers[6]);
        this.h = new BigInteger(numbers[7]);
        this.x = new BigInteger(numbers[8]);
       
    }
    
    public GenNum(byte[] bytes, File f) throws Exception{
       BufferedReader in = new BufferedReader(new FileReader(f));
       String line;
       List<String> lines = new ArrayList<String>();
       while((line = in.readLine()) != null) {
           lines.add(line);
       }
        String[] numbers = lines.toArray(new String[lines.size()]);
        this.p = new BigInteger(numbers[0]);
        this.q = new BigInteger(numbers[1]);
        this.a = new BigInteger(numbers[2]);
        this.y = new BigInteger(numbers[3]);
        this.k = new BigInteger(numbers[4]);
        this.s = new BigInteger(numbers[5]);
        this.r = new BigInteger(numbers[6]);
        this.h1 = getHash(bytes);
        this.u1 = (s.multiply(mulInv(h1,q))).mod(q);
        this.u2 = ((r.negate()).multiply(mulInv(h1,q))).mod(q);
        getCode();
        

    }
    
    public void getCode() {
        if(s.compareTo(q) < 0 && s.compareTo(BigInteger.ZERO) > 0 && r.compareTo(q) < 0 && r.compareTo(BigInteger.ZERO) > 0) {
            this.v = ((powMod(a,u1,p).multiply(powMod(y,u2,p))).mod(p)).mod(q);
        } else { 
            System.out.println("s or r is invalid");
        }
        if(v.equals(r)) System.out.println("Подпись можно считать подлинной.");
        else System.out.println("Данные были изменены, либо подпись неверна");
    }
    
    public String getStr(){
        StringBuffer sb = new StringBuffer();
        String lineseparator = System.clearProperty("line.separator");
            sb.append(p.toString());
            sb.append(lineseparator);
            sb.append(q.toString());
            sb.append(lineseparator);
            sb.append(a.toString());
            sb.append(lineseparator);
            sb.append(y.toString());
            sb.append(lineseparator);
            sb.append(k.toString());
            sb.append(lineseparator);
            sb.append(s.toString());
            sb.append(lineseparator);
            sb.append(r.toString());
            sb.append(lineseparator);
            sb.append(h.toString());
            sb.append(lineseparator);
            sb.append(x.toString());
            sb.append(lineseparator);
       
        return sb.toString();
    }
    
   
    public void goToFile(File f) throws Exception {
        BufferedWriter out = new BufferedWriter(new FileWriter(f));
        
        out.write(getStr());
        
        out.flush();
        out.close();
        
    }
    
    public BigInteger getV(){
        return this.v;
    }
    
    public BigInteger getP(){
        return this.p;
    }
    
     public BigInteger getA(){
        return this.a;
    }
    
   
    public BigInteger getQ(){
        return this.q;
    }
    
    public BigInteger getX(){
        return this.x;
    }
    
    public BigInteger getY(){
        return this.y;
    }
    
    public BigInteger getK(){
        return this.k;
    }
    
     public BigInteger getS(){
        return this.s;
    }
     public BigInteger getH(){
        return this.h;
    }
    
    
    public boolean isEven(BigInteger num){
        if((num.mod(two)).compareTo(BigInteger.ZERO) == 0) return true;
        else return false;
    }
    
    public boolean isDiv(BigInteger num) {
        BigInteger p1 = p.subtract(BigInteger.ONE);
        if(p1.mod(num).compareTo(BigInteger.ZERO) == 0) return true;
        else return false;
    }
    
    public boolean isZero(BigInteger num){
        if(num.compareTo(BigInteger.ZERO) == 0) return true;
        else return false;
    }
    
     public boolean isOne(BigInteger num){
        if(num.compareTo(BigInteger.ONE) == 0) return true;
        else return false;
    }
    
     public  BigInteger getRandom(int len) {
        BigInteger bigint;
        byte[] arr = new byte[len];
        rand.nextBytes(arr);
        if(((arr[0]) ^ 0) == 0)  arr[0] = 1;
        bigint = new BigInteger(1,arr);
         while(!isPrime(bigint)) {
            bigint = getRandom(len);
        }
       return bigint.abs();
    }
     
     public BigInteger gcd(BigInteger a, BigInteger b) {
         while(!isZero(a) && !isZero(b)) {
            if(a.compareTo(b) == 1) {
                a = a.mod(b);
            } else {
                b = b.mod(a);
            }
         }
         return a.add(b);
     }
     
     
    private boolean MillerRabin(BigInteger n) {
        BigInteger tmp = BigInteger.ZERO;
        do {
            tmp = new BigInteger(n.bitLength()-1, rand); //случайное значение не больше, чем n-2
        //} while (tmp.compareTo(BigInteger.ONE) <= 0);
        } while (tmp.compareTo(two) <= 0 || tmp.compareTo(n.subtract(two)) >=0);
        if (!gcd(n,tmp).equals(BigInteger.ONE)) return false; // если Нод не 1, непростое

        BigInteger num = n.subtract(BigInteger.ONE);
        int s=0;
        while ( (num.mod(two)).equals(BigInteger.ZERO)) {
            num = num.divide(two); //выписываем (2 в степени s )*r
            s++;
        }
        BigInteger y = powMod(tmp,num,n); //Вычисляем у = а в степени r mod n
        if (y.equals(BigInteger.ONE)) //Если 1, то простое
            return true;
        for (int i = 0; i < s; i++) {
            if (y.equals(n.subtract(BigInteger.ONE))) return true; //если у равно н -1, простое
            else y = powMod(y,two, n);
        }
        return false;
    }
      
    public boolean isPrime(BigInteger n, int numTimes) {
        for (int i=0; i<numTimes; i++) 
            if (!MillerRabin(n)) return false;
        return true;
    }
      public boolean isPrime(BigInteger n) {
        return isPrime(n,100);
    }
   
   
      public BigInteger powMod(BigInteger a, BigInteger x, BigInteger n) {
        
        if(x.equals(BigInteger.ZERO)) { //если степень 0, то 1
            return BigInteger.ONE;
        }
        BigInteger z = powMod(a, x.divide(two), n); 
        if(isEven(x)) return z.pow(2).mod(n); //если степень четная, то z в 2
        else return a.multiply(z.pow(2)).mod(n); //иначе домножаем еще на а
      }
    
     public  BigInteger getRandom(BigInteger q) {
        BigInteger big = BigInteger.ZERO;
        int tmp = 1;
        boolean flag = true;
        while(flag) {
            BigInteger b = new BigInteger(String.valueOf(tmp));
            big = b.multiply(q).add(BigInteger.ONE);
            if(isPrime(big)) flag = false;
            tmp++;
        }
        return big;
    }
     
    public  BigInteger getRandom(BigInteger p, BigInteger q) {
        BigInteger big = BigInteger.ZERO;
        int tmp = 2;
        boolean flag = true;
        while(flag){
            big = new BigInteger(String.valueOf(tmp));
            if(powMod(big,q,p).equals(BigInteger.ONE)) flag = false;
            tmp++;
        }
        return big;
    }
    
    public BigInteger getXbyRandom(BigInteger q) {
        BigInteger big = new BigInteger(q.bitLength(),rand);
        boolean flag = true;
        while(flag) {
            if (big.compareTo(q) <= 0 && big.compareTo(BigInteger.ZERO) >= 0) flag = false;
            else big = new BigInteger(q.bitLength(),rand);
        }
        return big;
    }
    
    public void setK(BigInteger key) {
        this.k = key;
    }
    
    public void setR(BigInteger rey) {
        this.r = rey;
    }
    
    public BigInteger getR(){
        return this.r;
    }
    
    public BigInteger getR(BigInteger a, BigInteger k, BigInteger p, BigInteger q){
        BigInteger r = powMod(a,k,p).mod(q);
        if(r.equals(BigInteger.ZERO)) {
            setK(getXbyRandom(q));
            k = getK();
            r = getR(a,k,p,q);
        }
        return r;
    }
    
    public BigInteger getHash(byte[] bytes){
        
        BigInteger h = new BigInteger(new String(toStr(getMyHash(bytes))),16);
        return h;
    }
    
    public BigInteger getS(BigInteger k, BigInteger h, BigInteger x, BigInteger r, BigInteger q){
        BigInteger s = ((k.multiply(h)).add((x.multiply(r)))).mod(q);
        if(s.equals(BigInteger.ZERO)) {
            setK(getXbyRandom(q));
            k = getK();
            setR(getR(a,k,p,q));
            r = getR();
            s = getS(k,h,x,r,q);
        }
        return s;
    }
    
    public BigInteger mulInv(BigInteger b, BigInteger a) { //расширенный алгоритм евклида
        
       BigInteger d, x, y, q, r, x1, x2, y1, y2, m = a;
       if(b.equals(BigInteger.ZERO)) {
           return BigInteger.ONE;
       }
       x1 = BigInteger.ZERO;
       x2 = BigInteger.ONE;
       y1 = BigInteger.ONE;
       y2 = BigInteger.ZERO;
       while(b.compareTo(BigInteger.ZERO) > 0) {
           q = a.divide(b);
           r = a.subtract(q.multiply(b));
           x = x2.subtract(q.multiply(x1));
           y = y2.subtract(q.multiply(y1));
           a = b; b = r;
           x2 = x1; x1 = x; y2 = y1; y1 = y;
       }
       d = a; x = x2; y = y2;
       if(y.compareTo(BigInteger.ZERO) < 0 ) y = y.add(m);
       return y;
        
    }
     
}
        
    
    

