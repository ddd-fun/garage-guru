package org.home.gg.domain.common;


public class Reject {

    private Reject() {
    }

    public static void ifNull(Object object){
        if(object == null)
            throw new NullPointerException();
    }

    public static void ifBlank(String str){
        if(str == null)
            throw new NullPointerException();
        if(str.trim().length() == 0)
            throw new IllegalArgumentException("string is blank");

    }

}
