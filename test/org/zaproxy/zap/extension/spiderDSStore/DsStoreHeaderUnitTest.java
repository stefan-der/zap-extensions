package org.zaproxy.zap.extension.spiderDSStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.zaproxy.zap.extension.spiderDSStore.parser.model.DsStoreHeader;

import java.io.File;
import java.net.URL;

public class DsStoreHeaderUnitTest {

    File validFile;


    @Before
    public void before(){
        URL validFileUrl = getClass().getResource(".DS_Store");
        this.validFile = new File(validFileUrl.getPath());
    }

    @Test
    public void test(){
        // given
        try {
            DsStoreHeader dsStoreHeader = new DsStoreHeader(this.validFile);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        // when

        // then

    }

}
