package org.home.gg.domain.common;


public class Validators {

    private Validators() {
    }

    public static void assertArgIsNotNull(Object object){
        if(object == null)
            throw new NullPointerException();
    }

    public static void assertArgIsNotBlank(String str){
        if(str == null)
            throw new NullPointerException();
        if(str.trim().length() == 0)
            throw new IllegalArgumentException("string is blank");

    }

}
