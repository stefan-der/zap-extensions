package org.zaproxy.zap.extension.spiderDSStore;


import org.junit.Assert;
import org.junit.Test;

public class ByteUtilsTest {


    Byte[] validObjectDataSourceWithNullValues = {0x21,0x25,null,0x21,null};
    byte[] validPrimitiveDataSourceWithNullValues = {0x21,0x25,0x00,0x21,0x00};


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
