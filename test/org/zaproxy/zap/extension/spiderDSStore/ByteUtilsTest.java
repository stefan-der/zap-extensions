package org.zaproxy.zap.extension.spiderDSStore;


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

    @Test
    public void parseByteArrayWithKomplementValuesToLong_withPositiveArray(){
        // Given

        // When
        long longValueFromFunction = ByteUtil.parseByteArrayWithKomplementValuesToLong(validPositiveValues);
        // Then
        Assert.assertEquals(valueOfValidPositiveValues,longValueFromFunction);

    }

    @Test
    public void parseByteArrayWithKomplementValuesToLong_withNegativeArray(){
        // Given
        // When
        long longValueFromFunction = ByteUtil.parseByteArrayWithKomplementValuesToLong(validNegativeValues);
        // Then
        Assert.assertEquals(valueOfValidNegativeValues,longValueFromFunction);

    }

}
