package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import jdk.dynalink.Operation;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class DsStoreRootBlock {

    private Byte[] RootBlockEntriesInByte;      // Integer
    private Byte[] RootBlockIndex;              // Integer
    private List<Byte[]> rootBlockEntriesList;


    public DsStoreRootBlock(File inputFile, DsStoreHeader dsStoreHeader){
        throw new UnsupportedOperationException();
    }
    public DsStoreRootBlock(Byte[] rootBlock, DsStoreHeader dsStoreHeader){
        throw new UnsupportedOperationException();
    }

    public static Byte[] validateDsStoreRootBlock(){
        throw new UnsupportedOperationException();
    }


}
