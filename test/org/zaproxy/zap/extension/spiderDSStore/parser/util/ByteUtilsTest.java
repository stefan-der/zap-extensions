package org.zaproxy.zap.extension.spiderDSStore.parser.util;


import org.junit.Assert;
import org.junit.Test;
import org.zaproxy.zap.extension.spiderDSStore.parser.model.ByteUtil;

import java.util.ArrayList;

public class ByteUtilsTest {

    byte[] validPositiveValues = {0x6a,0x71,0x75,0x72};
    long valueOfValidPositiveValues = 0x6a717572;

    //                           |FF,FF,FF,FF|
    byte[] validNegativeValues = {-1,-1,-1,-1};
    long valueOfValidNegativeValues = 0xffffffffl;

    // Value to big for the size of long
    byte[] notAllowedArraySize = new byte[17];

    @Test
    public void parseByteArrayWithKomplementValuesToLong_withPositiveArray_Unittest(){
        // Given
        // When
        long longValueFromFunction = ByteUtil.parseByteArrayWithKomplementValuesToLong(validPositiveValues);
        // Then
        Assert.assertEquals(valueOfValidPositiveValues,longValueFromFunction);
    }

    @Test
    public void parseByteArrayWithKomplementValuesToLong_withNegativeArray_Unittest(){
        // Given
        // When
        long longValueFromFunction = ByteUtil.parseByteArrayWithKomplementValuesToLong(validNegativeValues);
        // Then
        Assert.assertEquals(valueOfValidNegativeValues,longValueFromFunction);

    }

    @Test(expected = IllegalArgumentException.class)
    public void parseByteArrayWithKomplementValuesToLong_withToBigArray_Unittest(){
        long longvalueFromFunction = ByteUtil.parseByteArrayWithKomplementValuesToLong(notAllowedArraySize);
    }

}
