package com.LocationLibrary.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	
	public final static String EMPTY = "";
	public static final String BLANK = " ";

	public static boolean isEmpty(String str) {
		return (str == null || EMPTY.equals(str));
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String md5(String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	 
	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            String h = Integer.toHexString(0xFF & messageDigest[i]);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();
	 
	    } catch (NoSuchAlgorithmException shouldNotHappen) {
	    	shouldNotHappen.printStackTrace();
	    }
	    return "";
	}

    public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator i = s.iterator();
        while (i.hasNext()) {
            buffer.append(i.next());
            if (i.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    
    public static String removeBlanks(String string) {
        return string.replace(BLANK, EMPTY);
    }
    
    public static String safe(String str) {
        return isEmpty(str) ? EMPTY : str;
    }
}
