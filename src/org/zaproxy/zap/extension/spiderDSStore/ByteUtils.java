package org.zaproxy.zap.extension.spiderDSStore;

import java.nio.ByteBuffer;

public class ByteUtils {

    public static Short[] mergeByteArrays(Short[] ... allByteArrays) throws IllegalArgumentException{
        Short[] output = null;
        int size=0;

        // size calculation
        for (Short[] currentByteArray : allByteArrays){
            if(currentByteArray == null){
                // Skipping null Array
                continue;
            } else {
                long calculatedSize = currentByteArray.length + size;
                if (calculatedSize > Integer.MAX_VALUE) {
                    throw new IllegalArgumentException();
                } else {
                    size = size + currentByteArray.length;
                }
            }
        }

        // concatinating
        output = new Short[size];
        int i=0;
        for (Short[] currentByteArray : allByteArrays) {
            if (currentByteArray == null) {
                continue;
            }else{
                for (Short currentByte : currentByteArray) {
                    if(currentByte == null){
                        output[i] = 0x00;
                    }else {
                        output[i] = currentByte;
                    }
                    i++;
                }
            }
        }
        return output;
    }
    public static String convertByteArrayToString(Byte[] hexInput){
        String output=null;
        if (hexInput != null  || hexInput.length>0 ){
            output = new String(ByteUtils.convertObjectByteArrayToPrimitiveByteArray(hexInput));
        }else{
            // Todo: Logging
        }
        return output;
    }
    public static long convertShortArrayToLong(Short[] input) {
        // Type is Big Endian
        int output = -1;

        byte[] hexArrayAsPrimitiveType = convertObjectByteArrayToPrimitiveByteArray(input);

        if (hexInput.length==4){
            ByteBuffer byteBuffer = ByteBuffer.wrap(hexArrayAsPrimitiveType);
            output = byteBuffer.getInt();
        }

        return output;
    }
    public static byte[] convertObjectByteArrayToPrimitiveByteArray(Byte[] byteObject){
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
    public static Byte[] convertPrimitveByteArrayToObjectByteArray(byte[] primitiveByteArray){
        Byte[] byteObjectArray=null;

        if(primitiveByteArray != null){
            byteObjectArray = new Byte[primitiveByteArray.length];

            int i = 0;
            for (byte currentByte : primitiveByteArray){

                byteObjectArray[i] = currentByte;
                i++;
            }
        }

        return byteObjectArray;
    }


    public static short[] convertPrimitveByteArrayToShortArray(byte[] byteArray){
        short[] output = null;
        if(byteArray != null){
            for(int i =0; i<byteArray.length; i++){
                output[i] = Short.valueOf(byteArray[i]);
            }
        }

        return output;
    }

}