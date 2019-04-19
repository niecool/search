package com.nie.inout;

public class SearchContent implements java.io.Serializable {

    SearchContent(){
        doCheck();
    }

    protected boolean doCheck() {
        throw new UnsupportedOperationException(" you just implements this operation  ");
    }
}
