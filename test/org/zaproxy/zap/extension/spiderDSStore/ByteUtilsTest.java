package org.zaproxy.zap.extension.spiderDSStore;


import groovy.grape.GrapeIvy;
import net.bytebuddy.NamingStrategy;
import org.junit.Assert;
import org.junit.Test;

public class ByteUtilsTest {


    Byte[] validObjectDataSourceWithNullValues = {0x21,0x25,null,0x21,null};
    byte[] validPrimitiveDataSourceWithNullValues = {0x21,0x25,0x00,0x21,0x00};


    @Test
    public void ByteObjectToPrimititiveType_WithNullValue_Unittest(){
        Assert.assertNull(ByteUtils.convertByteObjectArrayToPrimitiveByteArray((Byte[]) null));
    }

    @Test
    public void ByteObjectToPrimititiveType_WithSomeNullElements_Unittest(){
        // Given
        // When
        byte[] primitiveObjectArrayFromFunction = ByteUtils.convertByteObjectArrayToPrimitiveByteArray(this.validObjectDataSourceWithNullValues);
        // Then
        Assert.assertArrayEquals(this.validPrimitiveDataSourceWithNullValues, primitiveObjectArrayFromFunction);
    }

    @Test
    public void ByteObjectToPrimititiveType_WithOneElement_Unittest(){
        // Given
        Byte testByte = 0x21;
        Byte[] byteObjectArray = {testByte};
        byte[] primitiveObjectArray = {testByte};
        // When
        byte[] primitiveObjectArrayFromFunction = ByteUtils.convertByteObjectArrayToPrimitiveByteArray(byteObjectArray);
        // Then
        Assert.assertArrayEquals(primitiveObjectArray, primitiveObjectArrayFromFunction);
    }


    @Test
    public void MergeByteArrays_WithNullValue_Unittest(){
        Assert.assertNull(ByteUtils.mergeByteArrays(null, null));
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
