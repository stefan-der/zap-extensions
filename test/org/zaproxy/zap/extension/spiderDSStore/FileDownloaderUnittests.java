package org.zaproxy.zap.extension.spiderDSStore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.spiderDSStore.testutils.DsStoreServer;

public class FileDownloaderUnittests {

    DsStoreServer dsStoreServer;

    @Before
    public void before(){
        dsStoreServer = new DsStoreServer(33444);
    }

    @Test
    public void downloadFileFromHttp_UrlIsNull_Unittest(){
        Assert.fail();
    }

    @Test
    public void downloadFileFromHttp_UrlIsNotValid_Unittest(){
        Assert.fail();
    }

    @Test
    public void downloadFileFromHttp_fileIsAvailable_Unittest(){
        Assert.fail();
    }


    @Test
    public void downloadFileFromHttp_fileAlreadyExists_Unittest(){
        Assert.fail();
    }

    @Test
    public void downloadFileFromHttp_fileIsNotAvailable_Unittest(){
        Assert.fail();
    }

    @Test
    public void downloadFileFromHttp_downloadInterruption_Unittest(){
        Assert.fail();
    }

    @Test
    public void downloadFileFromHttp_withWrongContentType_Unittest(){
        Assert.fail();
    }

    @Test
    public void moveDownloadedFileToTempDirectory_fileIsAvailable_Unittest(){
        Assert.fail();
    }

    @Test
    public void moveFileToTempDirectory_fileIsNotAvailable_Unittest(){
        Assert.fail();
    }

    @Test
    public void moveFileToTempDirectory_fileIsNull_Unittest(){
        Assert.fail();
    }


}
