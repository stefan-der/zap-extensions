package org.zaproxy.zap.extension.spiderDSStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ByteUtils {

    public static Byte[] mergeByteArrays(Byte[] ... byteArrays){
        List<Byte> validHeaderAsList = new ArrayList<>();
        for (Byte[] byteArray : byteArrays) {
            Collections.addAll(validHeaderAsList,byteArray);
        }
        return (Byte[]) validHeaderAsList.toArray();
    }

}
