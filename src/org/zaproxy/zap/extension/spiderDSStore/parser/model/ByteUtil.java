package org.zaproxy.zap.extension.spiderDSStore.parser.model;

public class ByteUtil {

    public static long parseByteArrayWithKomplementValuesToLong(byte[] byteArrayWithComplementValues){
        long output=0;

        for(int i=0; i<byteArrayWithComplementValues.length; i++){
            int shiftTimes=(byteArrayWithComplementValues.length-1)-i;

            long valueToAdd=0;
            if(byteArrayWithComplementValues[i] < 0){
                valueToAdd = byteArrayWithComplementValues[i] & 0xFF;
                valueToAdd = valueToAdd << (8*shiftTimes);
            }else{
                valueToAdd = byteArrayWithComplementValues[i]  << (8*shiftTimes);
            }
            output=output+valueToAdd;
        }

        return output;
    }
}
