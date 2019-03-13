package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.zaproxy.zap.extension.spiderDSStore.ByteUtils;

public class DsStoreHeader {

    static String INVALID_HEADER_MESSAGE="The .DS_Store Header is not valid.";

    // Default Header in every .DS_Store File
    // 00 00 00 01                  -> 4 Byte
    private Byte[] initialisation;

    // Default Header
    // 42 75 64 31                  -> 4 byte (4 Byte Offset)
    private Byte[] buddyAllocation;

    // Start Offset for the searching for the TOC in current File
    // 00 00 02 00                  -> 4 byte -> int Interpretation
    private Byte[] offset1;

    // Root DsStoreRootBlock Size where the TOC is located
    // 00 00 08 00
    private Byte[] rootBlockSize;           // 4 byte (12 Byte Offset)

    // Should be the same Value as offset 1
    // 00 00 02 00
    private Byte[] offset2;        // 4 byte (16 Byte Offset)

    // the unknown DsStoreRootBlock is not yet reverse engineered
    private Byte[] unknown1;       // 16 byte (20 Byte Offset

    private Logger logger = Logger.getLogger(DsStoreHeader.class);

    public DsStoreHeader(File dsStoreFile) throws FileNotFoundException, FileSystemException {
        if (dsStoreFile != null) {
            if (dsStoreFile.exists()) {
                FileInputStream fileInputStream;

                int fileSize = (int) dsStoreFile.length();
                byte[] fileAsByteArray= new byte[fileSize];

                try{
                    fileInputStream = new FileInputStream(dsStoreFile);
                    fileInputStream.read(fileAsByteArray);
                }catch (IOException e){
                    logger.error("Error while reading File: " +  dsStoreFile.getAbsolutePath());
                    logger.error(e);
                    throw new FileSystemException(e.getMessage());
                }

                // Todo: Abort criteria for Empty File
                this.initialisation = ByteUtils.convertPrimitveByteArrayToObjectByteArray(Arrays.copyOfRange(fileAsByteArray,0,3));
                this.buddyAllocation= ByteUtils.convertPrimitveByteArrayToObjectByteArray(Arrays.copyOfRange(fileAsByteArray,4, 7));
                this.offset1 = ByteUtils.convertPrimitveByteArrayToObjectByteArray(Arrays.copyOfRange(fileAsByteArray, 8, 11));
                this.rootBlockSize = ByteUtils.convertPrimitveByteArrayToObjectByteArray(Arrays.copyOfRange(fileAsByteArray, 12, 15));
                this.offset2 = ByteUtils.convertPrimitveByteArrayToObjectByteArray(Arrays.copyOfRange(fileAsByteArray, 16, 19));
                this.unknown1 = ByteUtils.convertPrimitveByteArrayToObjectByteArray(Arrays.copyOfRange(fileAsByteArray, 20 ,36));

            } else {
                throw new FileNotFoundException("File not found Path: " + dsStoreFile.getAbsolutePath());
            }
        }else {
            throw new FileNotFoundException("File should not be Null");
        }
    }

    public DsStoreHeader(Byte[] byteHeader) throws IllegalArgumentException{
        if(validateHeader(byteHeader)){
            this.initialisation = (Byte[]) ArrayUtils.subarray(byteHeader, 0, 4);
            this.buddyAllocation = (Byte[]) ArrayUtils.subarray(byteHeader, 4, 8);
            this.offset1 = (Byte[]) ArrayUtils.subarray(byteHeader, 8, 12);
            this.rootBlockSize = (Byte[]) ArrayUtils.subarray(byteHeader, 12, 16);
            this.offset2 = (Byte[]) ArrayUtils.subarray(byteHeader, 16, 20);
            this.unknown1 = (Byte[]) ArrayUtils.subarray(byteHeader, 20, 36);
        }else{
            throw new IllegalArgumentException(INVALID_HEADER_MESSAGE);
        }
    }

    public static boolean validateHeader(Byte[] byteHeader) {
        boolean isValid = true;

        // Header has to be exact 36 Byte Long
        if(byteHeader != null && byteHeader.length == 36 && !Arrays.asList(byteHeader).contains(null)) {

            Byte[] initialisation = (Byte[]) ArrayUtils.subarray(byteHeader, 0, 4);
            Byte[] buddyAllocation = (Byte[]) ArrayUtils.subarray(byteHeader, 4, 8);
            Byte[] offset1 = (Byte[]) ArrayUtils.subarray(byteHeader, 8, 12);
            Byte[] rootBlockSize = (Byte[]) ArrayUtils.subarray(byteHeader, 12, 16);
            Byte[] offset2 = (Byte[]) ArrayUtils.subarray(byteHeader, 16, 20);


            int  initialisationAsInt = ByteUtils.convertByteArrayToInt(initialisation);
            String buddyAllocationAsString = ByteUtils.convertByteArrayToString(buddyAllocation);

            // magic1 has to be 00 00 00 01 -> 1
            // magic2 has to be 42 75 64 31 -> Bud1
            if (!buddyAllocationAsString.contentEquals("Bud1")
                    || initialisationAsInt != 1) {
                isValid = false;
            }


            if(offset1 == null || offset2 == null){
                isValid = false;
            }else {
                int offset1AsInteger = ByteUtils.convertByteArrayToInt(offset1);
                int offset2AsInteger = ByteUtils.convertByteArrayToInt(offset2);

                // Offset1 has to be Equal to Offset2
                if (offset1AsInteger != offset2AsInteger) {
                    isValid = false;
                }
            }

        }else {
            isValid = false;
        }

        return isValid;
    }

    public int getRootBlockSize(){
        return ByteUtils.convertByteArrayToInt(this.rootBlockSize);
    }
    public int getRootBlockOffset(){
        return ByteUtils.convertByteArrayToInt(this.offset1);
    }
    public boolean validateHeader(){
        Byte[] fullHeader = ByteUtils.mergeByteArrays(this.initialisation, this.buddyAllocation, this.offset1, this.rootBlockSize, this.offset2, this.unknown1);
        return DsStoreHeader.validateHeader(fullHeader);
    }
}
