package org.zaproxy.zap.extension.spiderDSStore;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.zaproxy.zap.extension.spiderDSStore.parser.model.DsStoreHeader;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class DsStoreHeaderUnitTest {

    //@Rule
    //public ResourceFileRule validDSStoreFile = new ResourceFileRule("DS_Store");

    @Mock
    DsStoreHeader mockedDsStoreHeader = mock(DsStoreHeader.class);

    // Valid Byte Streams
    final Byte[] resourceHeaderInitialisation = {0x00,0x00,0x00,0x01};
    final Byte[] resourceHeaderMagicByteSequence1 = {0x42,0x75,0x64,0x31};
    final Byte[] resourceHeaderOffset = {0x00, 0x00, 0x02, 0x00};
    final Byte[] rootBlockSize = {0x00, 0x00, 0x08, 0x00};
    final Byte[] resourceHeaderUnknownBlocks ={0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5};

    // Full Valid Stream              | Init              | Bud1              | RootBlock Offset  | Root Block size   | Offset            | unknown Blocks                |
    final Byte[] fullResourceHeader = {0x00,0x00,0x00,0x01,0x42,0x75,0x64,0x31,0x00,0x00,0x02,0x00,0x00,0x00,0x08,0x00,0x00,0x00,0x02,0x00,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5};
    final int offsetSizeOfFullResourceHeader = 0x0200;
    final int rootBlockSizeOfFullResourceHeader = 0x0800;


    // Offset for InvalidOffsetTest
    final Byte[] invalidResourceHeaderOffset = {0x00, 0x00, 0x04, 0x00};

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
            fullInvalidHeader = ByteUtils.mergeByteArrays(resourceHeaderInitialisation,resourceHeaderMagicByteSequence1, null, rootBlockSize, null, resourceHeaderUnknownBlocks);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        // then
        Assert.assertFalse(DsStoreHeader.validateHeader(fullInvalidHeader));
    }

    @Test
    public void validateHeaderWithNullRootBlock_Unittest(){
        // given
        Byte[] fullResourceHeader=this.fullResourceHeader;
        fullResourceHeader[(int) Math.floor(fullResourceHeader.length/2)]=null;
        fullResourceHeader[(int) Math.floor(fullResourceHeader.length/3)]=null;
        // when
        // then
        Assert.assertFalse(DsStoreHeader.validateHeader(fullResourceHeader));
    }

    @Test
    public void getRootBlockOffset(){
        // given
        DsStoreHeader dsStoreHeader = new DsStoreHeader(this.fullResourceHeader);
        // when
        int offsetFromFunction = dsStoreHeader.getRootBlockOffset();
        // then
        Assert.assertEquals(this.offsetSizeOfFullResourceHeader,offsetFromFunction);
    }

    @Test
    public void getRootBlockSize(){
        // given
        DsStoreHeader dsStoreHeader = new DsStoreHeader(this.fullResourceHeader);
        // when
        int rootBlockFromFunction = dsStoreHeader.getRootBlockSize();
        // then
        Assert.assertEquals(this.rootBlockSizeOfFullResourceHeader, rootBlockFromFunction);
    }
}
