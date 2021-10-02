package com.jacobarau.smoothptz;

public class HelloJNI {
    static {
        System.loadLibrary("native-lib");
    }

    public native int getFoo();
}
