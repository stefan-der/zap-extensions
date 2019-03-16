package org.zaproxy.zap.extension.spiderDSStore.parser;

import org.apache.commons.lang.ArrayUtils;
import org.zaproxy.zap.extension.spiderDSStore.ByteUtils;

import javax.naming.OperationNotSupportedException;

public class DsStoreOffsetCalculator {

    public static final int REDUCTION_FOR_DSSTORE_OFFSET = 4;
    public static final String ERRORMESSAGE_INPUT_LOWER_THAN_OFFSET="The Input is to low to calculate a correct Offset";
    public static final String ERRORMESSAGE_INPUT_IS_OR_CONTAINS_NULL="The Input is null or contains NULL Elements";
    public static final String ERRORMESSAGE_INPUT_HAS_NOT_THE_EXPECTED_SIZE = "The Input has to be a length of Byte[4]";

    public static int calculateOffset(int originalOffset) throws IllegalArgumentException{
        if (originalOffset < REDUCTION_FOR_DSSTORE_OFFSET){
            throw new IllegalArgumentException(ERRORMESSAGE_INPUT_LOWER_THAN_OFFSET);
        }
        return (originalOffset - REDUCTION_FOR_DSSTORE_OFFSET);
    }


    // Todo: check if really is required !!!!
    public static int calculateOffset(Byte[] originalOffset){
        int output = -1;
        if(originalOffset != null && !ArrayUtils.contains(originalOffset,null)){
            if(originalOffset.length == 4){
                int originalOffsetAsInteger = ByteUtils.convertByteArrayToInt(originalOffset);
                output = calculateOffset(originalOffsetAsInteger);
            }else{
                throw new IllegalArgumentException(ERRORMESSAGE_INPUT_HAS_NOT_THE_EXPECTED_SIZE);
            }
        }else{
            throw new IllegalArgumentException(ERRORMESSAGE_INPUT_IS_OR_CONTAINS_NULL);
        }
        return output;
    }

    public static int[] calculateRootBlock(int offsetInput, int rootBlocksize) {
        int[] output = new int[2];

        output[0] = calculateOffset(offsetInput);
        output[1] = output[0]+rootBlocksize;

        return output;
    }
}
