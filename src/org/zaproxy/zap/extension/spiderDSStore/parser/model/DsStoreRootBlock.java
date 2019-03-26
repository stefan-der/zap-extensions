package org.zaproxy.zap.extension.spiderDSStore.parser.model;


import groovy.json.internal.IO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class DsStoreRootBlock {

    // |   RootBlockEntries   | RootBlockIndex        | RootBlockEntry 0      | RootBlockEntry 1      | RootBlockEntry 2      |
    // {0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x0B, 0x00, 0x00, 0x00, 0x45, 0x00, 0x00, 0x10, 0x0C}

    private byte[] rootBlockEntriesInByte;      // 4 Byte -> Integer
    private byte[] rootBlockIndex;              // 4 Byte -> Integer
    private List<byte[]> rootBlockEntriesList;  // Offset 8


    private DsStoreRootBlock(byte[] rootBlockEntriesInByte, byte[] rootBlockIndex, LinkedList<byte[]> rootBlockEntriesList){
        this.rootBlockEntriesInByte = rootBlockEntriesInByte;
        this.rootBlockIndex = rootBlockIndex;
        this.rootBlockEntriesList=rootBlockEntriesList;
    }

    public static boolean validateDsStoreRootBlock(byte[] rootBlock, DsStoreHeader dsStoreHeader){
        boolean isValid = true;

        try {
            // Parameters are not null
            if (dsStoreHeader == null || rootBlock == null) {
                throw new IllegalArgumentException();
            }

            // length of rootBlockBoundries should be 2
            long[] rootBlockBoundries = dsStoreHeader.getRootBlockBoundries();
            if (rootBlockBoundries.length != 2) {
                throw new IllegalArgumentException();
            }

            // check if Size equals
            long expectedRootBlockSize =rootBlockBoundries[0] - rootBlockBoundries[1];

            // if rootBlockBoundries[0] is 0 -> calculation is negative
            if(expectedRootBlockSize <0){
                expectedRootBlockSize=expectedRootBlockSize*-1;
            }

            if(rootBlock.length != expectedRootBlockSize){
                throw new IllegalArgumentException();
            }

        }catch (IllegalArgumentException e){
            isValid=false;
        }


        return isValid;
    }
    public static DsStoreRootBlock getRootBlockFromInputStream(InputStream inputStream, DsStoreHeader dsStoreHeader) throws IOException {

        if (inputStream == null || dsStoreHeader == null){
            throw new IllegalArgumentException();
        }

        long[] rootBlockBoundries = dsStoreHeader.getRootBlockBoundries();
        if(rootBlockBoundries.length !=2){
            throw new IllegalArgumentException();
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readAllBytes());



        // Todo: check Lenght of Inpustream
        // start at beginning of the Stream
        byteArrayInputStream.reset();
        inputStream.skip(rootBlockBoundries[0]);

        // read RootBlockEntrySize
        byte[] rootBlockEntriesInByte = inputStream.readNBytes(4);
        long numberOfElementsInRootBlockIndexList = ByteUtil.parseByteArrayWithKomplementValuesToLong(rootBlockEntriesInByte);

        // read RootBlockIndex
        byte[] rootBlockIndexInByte = inputStream.readNBytes(4);


        LinkedList<byte[]> rootBlockEntriesList = new LinkedList<>();
        for(int i = 0; i<numberOfElementsInRootBlockIndexList; i++){
            byte[] byteListForRootBlockEntry = inputStream.readNBytes(4);
            rootBlockEntriesList.add(byteListForRootBlockEntry);
        }

        // Todo: validate

        return new DsStoreRootBlock(rootBlockEntriesInByte,rootBlockIndexInByte,rootBlockEntriesList);
    }

}
