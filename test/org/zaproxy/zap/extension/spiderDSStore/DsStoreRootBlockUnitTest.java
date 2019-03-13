package org.zaproxy.zap.extension.spiderDSStore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.zaproxy.zap.extension.spiderDSStore.parser.model.DsStoreHeader;
import org.zaproxy.zap.extension.spiderDSStore.parser.model.DsStoreRootBlock;

import javax.naming.OperationNotSupportedException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DsStoreRootBlockUnitTest {

    @Mock
    DsStoreHeader mockDsStoreHeader = mock(DsStoreHeader.class);

    final int mockDsStoreHeaderOffset=0x0200;
    final int mockDsStoreHeaderRootBlockSize=0x0800;

    @Before
    public void before(){
        // adds a valid Offset
        when(this.mockDsStoreHeader.getRootBlockOffset()).thenReturn(this.mockDsStoreHeaderOffset);
        // adds a valid Blocksize
        when(this.mockDsStoreHeader.getRootBlockSize()).thenReturn(this.mockDsStoreHeaderRootBlockSize);
        // sets the default return value of validate Header
        when(this.mockDsStoreHeader.validateHeader()).thenReturn(true);
    }



    @Test
    public void createRootBlockFromHeader_Unittest(){
        // given
        // when
        DsStoreRootBlock dsStoreRootBlock = new DsStoreRootBlock(mockDsStoreHeader);

        // then
        verify(this.mockDsStoreHeader,times(1)).validateHeader();
        verify(this.mockDsStoreHeader,times(1)).getRootBlockOffset();
        verify(this.mockDsStoreHeader,times(1)).getRootBlockSize();


        Assert.assertEquals(mockDsStoreHeader, dsStoreRootBlock);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createRootBlockFromHeader_withNullHeader_Unittest(){
        DsStoreRootBlock dsStoreRootBlock = new DsStoreRootBlock(null);
    }



}
