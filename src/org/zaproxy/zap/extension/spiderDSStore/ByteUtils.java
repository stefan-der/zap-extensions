package org.zaproxy.zap.extension.spiderDSStore;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ByteUtils {


    public static Byte[] mergeByteArrays(Byte[] ... byteArrays) throws OutOfMemoryError{
        Byte[] output = null;
        int size=0;

        // size calculation
        for (Byte[] byteArray : byteArrays){
            long calculatedSize = byteArray.length + size;
            if(calculatedSize > Integer.MAX_VALUE){
                // Todo: Search for better matching Exception Type
                throw new OutOfMemoryError();
            }else{
                size=size+byteArray.length;
            }
        }

        // concatinating
        output = new Byte[size];
        int i=0;
        for (Byte[] byteArray : byteArrays) {
            for (Byte currentByte : byteArray) {
                output[i]=currentByte;
                i++;
            }
        }
        return output;
    }
    public static String convertByteArrayToString(Byte[] hexInput){
        String output=null;
        if (hexInput != null  || hexInput.length>0 ){
            output = new String(ByteUtils.convertByteObjectArrayToPrimitiveByteArray(hexInput));
        }else{
            // Todo: Logging
        }
        return output;
    }

    public static int convertByteArrayToInt(Byte[] hexInput) {
        // Type is Big Endian
        int output = -1;

        byte[] hexArrayAsPrimitiveType = convertByteObjectArrayToPrimitiveByteArray(hexInput);

        if (hexInput.length==4){
            ByteBuffer byteBuffer = ByteBuffer.wrap(hexArrayAsPrimitiveType);
            output = byteBuffer.getInt();
        }

        return output;
    }
    public static byte[] convertByteObjectArrayToPrimitiveByteArray(Byte[] byteObject){
        byte[] byteArrayPrimitiveType=null;

        if(byteObject!= null) {
            byteArrayPrimitiveType = new byte[byteObject.length];

            for(int i = 0; i< byteObject.length; i++){
                if(byteObject[i] == null){
                    byteObject[i]=0x00;
                }
                byteArrayPrimitiveType[i] = byteObject[i];
            }
        }
        return byteArrayPrimitiveType;
    }
    public static Byte[] convertByteObjectArrayToPrimitiveByteArray(byte[] primitiveByteArray){
        Byte[] byteObjectArray=null;

        if(primitiveByteArray != null){
            byteObjectArray = new Byte[primitiveByteArray.length];

            int i = 0;
            for (Byte currentByte : primitiveByteArray){
                byteObjectArray[i] = currentByte;
                i++;
            }
        }

        return byteObjectArray;
    }

}
