package org.zaproxy.zap.extension.spiderDSStore;

import org.apache.log4j.Logger;
import org.junit.*;
import org.zaproxy.zap.extension.spiderDSStore.JunitRules.ResourceFileRule;
import org.zaproxy.zap.extension.spiderDSStore.parser.model.DsStoreHeader;

public class DsStoreHeaderUnitTest {

    //@Rule
    //public ResourceFileRule validDSStoreFile = new ResourceFileRule("DS_Store");

    // Valid Byte Streams
    Byte[] resourceHeaderInitialisation = {0x00,0x00,0x00,0x01};
    Byte[] resourceHeaderMagicByteSequence1 = {0x42,0x75,0x64,0x31};
    Byte[] resourceHeaderOffset = {0x00, 0x00, 0x02, 0x00};
    Byte[] rootBlockSize = {0x00, 0x00, 0x08, 0x00};
    Byte[] resourceHeaderUnknownBlocks ={0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};


    // variables used in prepared
    Byte[] fullvalidHeader=null;


    Logger logger = Logger.getLogger(DsStoreHeaderUnitTest.class);

    @Before
    public void prepareHeaders() throws Exception{
        fullvalidHeader=null;

    }

    @Test
    public void testCorrect_HeaderValidation() throws Exception{
        // given

        // when
        try{
            fullvalidHeader = ByteUtils.mergeByteArrays(resourceHeaderInitialisation,resourceHeaderMagicByteSequence1, resourceHeaderOffset, rootBlockSize, resourceHeaderOffset, resourceHeaderUnknownBlocks);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        // then
        Assert.assertTrue(DsStoreHeader.validateHeader(fullvalidHeader));
    }



}
