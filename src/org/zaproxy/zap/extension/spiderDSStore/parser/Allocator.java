package org.zaproxy.zap.extension.spiderDSStore.parser;

import java.io.File;

public class Allocator {

    private File dsStoreFile = null;
    private boolean dirty = false;

    public Allocator(File dsStoreFile){
        this.dsStoreFile=dsStoreFile;
    }



    public byte[] read(long size, long offset){
        byte[] output=null;
        throw new UnsupportedOperationException();
    }

}
