package org.zaproxy.zap.extension.spiderDSStore;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ByteUtilsTest {


    Byte[] validObjectDataSourceWithNullValues = {0x6a,0x71,null,0x21,null};
    byte[] validPrimitiveDataSourceWithNullValues = {0x6a,0x71,0x75,0x72,0x79};

    @Test
    public void ByteObjectToPrimititiveType_WithNullValue_Unittest(){
        Assert.assertNull(ByteUtils.convertObjectByteArrayToPrimitiveByteArray((Byte[]) null));
    }

    @Test
    public void ByteObjectToPrimititiveType_WithSomeNullElements_Unittest(){
        // Given
        // When
        byte[] primitiveObjectArrayFromFunction = ByteUtils.convertObjectByteArrayToPrimitiveByteArray(this.validObjectDataSourceWithNullValues);
        // Then
        Assert.assertArrayEquals(this.validPrimitiveDataSourceWithNullValues, primitiveObjectArrayFromFunction);
    }

    @Test
    public void ByteObjectToPrimititiveType_WithOneElement_Unittest(){
        // Given
        Byte testByte = 0x21;
        Byte[] byteObjectArray = {testByte};
        byte[] primitiveByteArray = {testByte};
        // When
        byte[] primitiveObjectArrayFromFunction = ByteUtils.convertObjectByteArrayToPrimitiveByteArray(byteObjectArray);
        // Then
        Assert.assertArrayEquals(primitiveByteArray, primitiveObjectArrayFromFunction);
    }

    @Test
    public void PrimitveTypeToByteObject_WithOneElement_Unittest(){
        // Given
        Byte testByte = 0x21;
        Byte[] byteObjectArray = {testByte};
        byte[] primitiveByteArray = {testByte};
        // When
        Byte[] byteObjectArrayFromFunction = ByteUtils.convertPrimitveByteArrayToObjectByteArray(primitiveByteArray);
        // Then
        Assert.assertArrayEquals(byteObjectArray, byteObjectArrayFromFunction);
    }

    @Test
    public void MergeByteArrays_WithNullValue_Unittest(){
        Assert.assertArrayEquals(new Byte[0], ByteUtils.mergeByteArrays(null, null));
    }

    @Test
    public void ConvertByteArrayToString_WithOneElement_Unittest(){
        // given
        String testString = "blub121";
        Byte[] testStringByteArray = new Byte[testString.length()];
        for (int i=0; i < testString.length(); i++){
            testStringByteArray[i] = testString.getBytes()[i];
        }

        // When
        String testStringFromFunction = ByteUtils.convertByteArrayToString(testStringByteArray);

        // Then
        Assert.assertEquals(testString,testStringFromFunction);
    }

    @Test
    public void ConvertByteArrayToString_WithNullValues_Unittest(){
        // given
        String validObjectDataSourceAsString = "jur";
        Byte[] validPrimitiveDataSourceWithNullValues = {0x6a,null,0x75,null,0x79};
        // When
        String stringFromFunction = ByteUtils.convertByteArrayToString(validPrimitiveDataSourceWithNullValues);
        // Then
        Assert.assertEquals(validObjectDataSourceAsString,stringFromFunction);
    }

    @Test
    public void ConvertByteArrayToString_WithUTF16String(){
        Assert.fail();
    }

    @Test
    public void ConvertByteArrayToString_WithInvalidUTF16String(){Assert.fail();}

    @Test
    public void MergeByteArrays_WithSomeNullElements_Unittest(){
        Assert.assertNull(ByteUtils.mergeByteArrays(validObjectDataSourceWithNullValues,validObjectDataSourceWithNullValues));
    }

    @Test
    public void MergeByteArrays_WithWorkingElements_Unittest(){
        Assert.fail();
    }

    @Test
    public void MergeByteArrays_WithLongInitializedArraySizes_Unittest(){Assert.fail();}





}
