package org.zaproxy.zap.extension.spiderDSStore;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DsStoreHeaderUnitTest {

    @Rule
    public ResourceFileRule validDSStoreFile = new ResourceFileRule("DS_Store");

    byte[] resourceHeaderInitialisation = {0x00,0x00,0x00,0x01};
    byte[] resourceHeaderMagicByteSequence1 = {0x42,0x75,0x64,0x31};
    byte[] resourceHeaderOffset = {0x00, 0x00, 0x02, 0x00};
    byte[] rootBlockSize = {0x00, 0x00, 0x08, 0x00};
    byte[] resourceHeaderUnknownBlocks ={0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
    byte[] fullvalidHeader=null;


    Logger logger = Logger.getLogger(DsStoreHeaderUnitTest.class);

    @Before
    public void prepareHeaders() throws Exception{
        ArrayList<Byte> validHeader = new ArrayList<>();


    }


    public static byte[] merge(Byte[] ... byteArrays){
        List<Byte> validHeaderAsList = new ArrayList<>();
        for (Byte[] byteArray : byteArrays) {
            Collections.addAll(validHeaderAsList,byteArray);
        }
    }

    @Test
    public void testHeaderValidation() throws Exception{
        // given Header

        // when

        // then

    }

}
