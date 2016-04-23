package utils;

import java.math.BigInteger;

import sun.security.util.BigInt;

public class DiffeHelmanKeyDistribution {

	/**
	 * @param args
	 */
	
	
	public static final BigInteger N=new BigInteger("997");
	public static final BigInteger G=new BigInteger("2");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*BigInteger b=new BigInteger("7421");
		BigInteger a=new BigInteger("897");
		BigInteger n=new BigInteger("997");
		BigInteger g=new BigInteger("2");
		BigInteger bi=a.modPow(b, n);
		System.out.println(bi);*/
		//long b=7421,a=897,n=997,g=2;
		//System.out.println(Math.pow(897,7421)%n);
		System.out.println(generateKey());
	}

	public static String generateKey()
	{
		double d=Math.random();
		d*=1000;
		d=Math.floor(d);
		long l=(long)d;
		return l+"";
	}

	public static BigInteger getSharedKeyFromServer(BigInteger bigInt) {
		// TODO Auto-generated method stub
		return G.modPow(bigInt, N);
	}

	public static BigInteger getPublicKey(BigInteger privateKey,
			BigInteger sharedKey) {
		// TODO Auto-generated method stub
		
		return sharedKey.modPow(privateKey, N);
	}
}
