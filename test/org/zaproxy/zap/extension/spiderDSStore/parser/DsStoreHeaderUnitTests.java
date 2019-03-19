package org.zaproxy.zap.extension.spiderDSStore.parser;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.zaproxy.zap.extension.spiderDSStore.JunitRules.ResourceFileRule;
import org.zaproxy.zap.extension.spiderDSStore.parser.model.DsStoreHeader;

import java.io.*;
import java.nio.ByteBuffer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DsStoreHeaderUnitTests {

    @Rule
    public ResourceFileRule inValidDSStoreFile = new ResourceFileRule("DS_Store_with_wrongOffset");

    @Rule
    public ResourceFileRule validDSStoreFile = new ResourceFileRule("DS_Store");

    @Mock
    DsStoreHeader mockedDsStoreHeader =null;

    @Before
    public void before(){
        this.mockedDsStoreHeader= mock(DsStoreHeader.class);
    }

    // Valid Byte
    final byte[] resourceHeaderOffset = {0x00, 0x00, 0x20, 0x00};
    final long expectedResourceHeaderOffset = 0x2000 + 4;

    final byte[] rootBlockSize = {0x00, 0x00, 0x08, 0x00};
    final long expectedRootBlockSize = 0x0800;

    final long[] expectedBoundries = new long[]{expectedResourceHeaderOffset,expectedResourceHeaderOffset+expectedRootBlockSize};

    // Full Valid Stream                   | Init              | Bud1              | RootBlock Offset  | Root Block size   | Offset            | unknown Blocks                |
    final byte[] fullValidResourceHeader = {0x00,0x00,0x00,0x01,0x42,0x75,0x64,0x31,0x00,0x00,0x02,0x00,0x00,0x00,0x08,0x00,0x00,0x00,0x02,0x00,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5};


    // destructiveValues                                                                             |0xFF,0xFF,0xFF,0xFF|                   |0xFF,0xFF,0xFF,0xFF|
    final byte[] fullInvalidResourceHeaderWithMaxedOffsets = {0x00,0x00,0x00,0x01,0x42,0x75,0x64,0x31,  -1,  -1,  -1,  -1,0x00,0x00,0x08,0x00,  -1,  -1,  -1,  -1,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5};



    Logger logger = Logger.getLogger(DsStoreHeaderUnitTests.class);

    @Test
    public void initialiseDsStoreHeader_withCorrectByteArrayAsInputStream_Unittest() throws IOException {
        // given
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.fullValidResourceHeader);
        // when
        DsStoreHeader dsStoreHeader = new DsStoreHeader(byteArrayInputStream);
        // then
        Assert.assertNotNull(dsStoreHeader);
    }

    @Test
    public void initialiseDsStoreHeader_witchCorrectFileAsInputStream_Unittest() throws IOException{
        // given
        InputStream inputStream = validDSStoreFile.createInputStream();
        // when
        DsStoreHeader dsStoreHeader = new DsStoreHeader(inputStream);
        // then
        Assert.assertNotNull(dsStoreHeader);
    }


    @Test
    public void validateHeaderWithCorrectHeader_Unittest(){
        // given
        byte[] validByteHeader = fullValidResourceHeader;
        // when
        boolean validationFromFunction = DsStoreHeader.validateDsStoreHeader(validByteHeader);
        // then
        Assert.assertTrue(validationFromFunction);
    }

    @Test
    public void validateHeader_WithInvalidOffset1_Unittest(){
        // given
        byte[] inValidByteHeader = fullValidResourceHeader;
        inValidByteHeader[9]=0x20;
        // when
        boolean validationFromFunction = DsStoreHeader.validateDsStoreHeader(inValidByteHeader);
        // then
        Assert.assertFalse(validationFromFunction);
    }

    @Test
    public void validateHeader_WithInvalidOffset2_Unittest(){
        // given
        byte[] inValidByteHeader = fullValidResourceHeader;
        inValidByteHeader[18]=0x20;
        // when
        boolean validationFromFunction = DsStoreHeader.validateDsStoreHeader(inValidByteHeader);
        // then
        Assert.assertFalse(validationFromFunction);
    }

    @Test
    public void getRootBlockOffset_Unittest() throws IOException{
        // given
        when(mockedDsStoreHeader.getOffset1()).thenReturn(this.resourceHeaderOffset);
        when(mockedDsStoreHeader.getOffset2()).thenReturn(this.resourceHeaderOffset);
        when(mockedDsStoreHeader.getRootBlockOffset()).thenCallRealMethod();
        // when
        long rootBlockOffsetFromFunction = mockedDsStoreHeader.getRootBlockOffset();
        // then
        Assert.assertEquals(expectedResourceHeaderOffset, rootBlockOffsetFromFunction);
    }

    @Test
    public void getRootBlockSizeBoundries_withValidOffsetAndValidHeader_Unittest(){

        // given
        when(mockedDsStoreHeader.getOffset1()).thenReturn(this.resourceHeaderOffset);
        when(mockedDsStoreHeader.getOffset2()).thenReturn(this.resourceHeaderOffset);
        when(mockedDsStoreHeader.getRootBlockOffset()).thenReturn(this.expectedResourceHeaderOffset);
        when(mockedDsStoreHeader.getRootBlockSize()).thenReturn(this.rootBlockSize);
        when(mockedDsStoreHeader.getRootBlockSizeAsInteger()).thenReturn(this.expectedRootBlockSize);
        when(mockedDsStoreHeader.getRootBlockBoundries()).thenCallRealMethod();

        // when
        long[] rootBlockBoundriesFromFunction = mockedDsStoreHeader.getRootBlockBoundries();

        // then
        Assert.assertArrayEquals(expectedBoundries,rootBlockBoundriesFromFunction);

    }

    @Test
    public void getRootBlockSizeBoundries_WithMaximumOffsetBlockSize_Unittest() throws IOException{
        // given
        InputStream inputStream = new ByteArrayInputStream(this.fullInvalidResourceHeaderWithMaxedOffsets);
        DsStoreHeader dsStoreHeader = new DsStoreHeader(inputStream);
        // when
        Long a = dsStoreHeader.getRootBlockOffset();
        // then

    }

}
