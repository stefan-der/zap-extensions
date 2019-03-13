package org.zaproxy.zap.extension.spiderDSStore;

import org.apache.log4j.Logger;
import org.junit.*;
import org.zaproxy.zap.extension.spiderDSStore.parser.model.DsStoreHeader;

public class DsStoreHeaderUnitTest {

    //@Rule
    //public ResourceFileRule validDSStoreFile = new ResourceFileRule("DS_Store");

    // Valid Byte Streams
    Byte[] resourceHeaderInitialisation = {0x00,0x00,0x00,0x01};
    Byte[] resourceHeaderMagicByteSequence1 = {0x42,0x75,0x64,0x31};
    Byte[] resourceHeaderOffset = {0x00, 0x00, 0x02, 0x00};
    Byte[] rootBlockSize = {0x00, 0x00, 0x08, 0x00};
    Byte[] resourceHeaderUnknownBlocks ={0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5};

    // Offset for InvalidOffsetTest
    Byte[] invalidResourceHeaderOffset = {0x00, 0x00, 0x04, 0x00};

    Logger logger = Logger.getLogger(DsStoreHeaderUnitTest.class);

    @Test
    public void vaidateHeaderWithCorrectHeader_Unittest(){
        // given
        Byte[] fullvalidHeader=null;
        // when
        try{
            fullvalidHeader = ByteUtils.mergeByteArrays(resourceHeaderInitialisation,resourceHeaderMagicByteSequence1, resourceHeaderOffset, rootBlockSize, resourceHeaderOffset, resourceHeaderUnknownBlocks);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        // then
        Assert.assertTrue(DsStoreHeader.validateHeader(fullvalidHeader));
    }

    @Test
    public void validateHeaderWithInvalidOffset_Unittest(){
        // given
        Byte[] fullInvalidHeader=null;
        // when
        try{
            fullInvalidHeader = ByteUtils.mergeByteArrays(resourceHeaderInitialisation,resourceHeaderMagicByteSequence1, resourceHeaderOffset, rootBlockSize, invalidResourceHeaderOffset, resourceHeaderUnknownBlocks);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        // then
        Assert.assertFalse(DsStoreHeader.validateHeader(fullInvalidHeader));
    }

    @Test
    public void validateHeaderWithNullRootBlock_Unittest(){
        // given
        Byte[] fullInvalidHeader=null;
        // when
        Byte[] nullBytes = new Byte[4];
        try{
            fullInvalidHeader = ByteUtils.mergeByteArrays(resourceHeaderInitialisation,resourceHeaderMagicByteSequence1, resourceHeaderOffset, nullBytes, resourceHeaderOffset, resourceHeaderUnknownBlocks);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        // then
        Assert.assertFalse(DsStoreHeader.validateHeader(fullInvalidHeader));
    }

    @Test
    public void validateHeaderWithNullOffsetBlocks_Unittest(){
        // given
        Byte[] fullInvalidHeader=null;
        // when
        Byte[] nullBytes = new Byte[4];
        try{
            fullInvalidHeader = ByteUtils.mergeByteArrays(resourceHeaderInitialisation,resourceHeaderMagicByteSequence1, nullBytes, rootBlockSize, nullBytes, resourceHeaderUnknownBlocks);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        // then
        Assert.assertFalse(DsStoreHeader.validateHeader(fullInvalidHeader));
    }

}
