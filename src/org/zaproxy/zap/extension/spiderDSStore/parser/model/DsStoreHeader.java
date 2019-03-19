package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.hsqldb.persist.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class DsStoreHeader {

    // Error Messages
    final static String INVALID_HEADER_ERROR_MESSAGE="The .DS_Store Header is not valid\r\n Error: %s";
    final static String INVALID_INPUTSTREAM_ERROR_MESSAGE="The Inputstream is not valid: %s";
    final static String ERROR_WHILE_READING_INPUTSTREAM_MESSAGE="Error while reading the Inputstream";

    // Default Header in every .DS_Store File
    // 00 00 00 01                  -> 4 Byte
    final static byte[] REQUIRED_INITIALISATION_INTEGER = new byte[] {0x00, 0x00, 0x00, 0x01};
    private byte[] initialisation;

    // Default Header
    // 42 75 64 31                  -> 4 byte (4 Byte Offset)
    final static byte[] REQUIRED_BUDDYALLOCATOR_STRING = new byte[] {0x42,0x75,0x64,0x31};
    private byte[] buddyAllocation;

    // Start Offset for the searching for the TOC in current File
    // 00 00 02 00                  -> 4 byte -> int Interpretation
    private byte[] offset1;

    // Root DsStoreRootBlock Size where the TOC is located
    // 00 00 08 00
    private byte[] rootBlockSize;           // 4 byte (12 Byte Offset)

    // Should be the same Value as offset 1
    // 00 00 02 00
    private byte[] offset2;        // 4 byte (16 Byte Offset)

    // the unknown DsStoreRootBlock is not yet reverse engineered
    private byte[] unknown1;       // 16 byte (20 Byte Offset

    protected Logger logger = Logger.getLogger(DsStoreHeader.class);

    public DsStoreHeader(InputStream inputStream) throws IOException{
        byte[] byteHeader;

        // Read Inputstream
        if(inputStream == null){
            throw new InvalidArgumentException(String.format(INVALID_INPUTSTREAM_ERROR_MESSAGE,"Inputstream is null"));
        }else{
            try {
                byteHeader = inputStream.readNBytes(36);
            }catch (Exception e){
                throw new IOException(ERROR_WHILE_READING_INPUTSTREAM_MESSAGE,e);
            }
            // validate Inputstream

            if(!this.validateDsStoreHeader(byteHeader)){
                throw new InvalidArgumentException(String.format(INVALID_INPUTSTREAM_ERROR_MESSAGE, "Header of Inputstream is not valid"));
            }

            // set values
            Object[] allArrays=getArrayBlocks(byteHeader);
            this.initialisation = (byte[]) allArrays[0];
            this.buddyAllocation = (byte[]) allArrays[1];
            this.offset1 = (byte[]) allArrays[2];
            this.rootBlockSize = (byte[]) allArrays[3];
            this.offset2 = (byte[]) allArrays[4];
            this.unknown1= (byte[]) allArrays[5];

        }
    }

    public static Object[] getArrayBlocks(byte[] fullByteHeader){
        byte[] initialisation = null;
        byte[] buddyAllocation = null;
        byte[] offset1 = null;
        byte[] rootBlockSize = null;
        byte[] offset2 = null;
        byte[] unknown1= null;

        if (fullByteHeader.length == 36) {
            initialisation = ArrayUtils.subarray(fullByteHeader, 0, 4);
            buddyAllocation = ArrayUtils.subarray(fullByteHeader, 4, 8);
            offset1 = ArrayUtils.subarray(fullByteHeader, 8, 12);
            rootBlockSize = ArrayUtils.subarray(fullByteHeader, 12, 16);
            offset2 = ArrayUtils.subarray(fullByteHeader, 16, 20);
            unknown1 = ArrayUtils.subarray(fullByteHeader, 20, 36);
        }else{
            throw new IllegalArgumentException();
        }

        return new Object[]{initialisation,buddyAllocation,offset1,rootBlockSize,offset2,unknown1};
    }


    public static boolean validateDsStoreHeader(byte[] headerInBytes){
        boolean isValid = true;

        // Header has to be exact 36 Byte Long
        if(headerInBytes != null && headerInBytes.length == 36 && !Arrays.asList(headerInBytes).contains(null)) {


            Object[] allArrays=getArrayBlocks(headerInBytes);
            byte[] initialisation = (byte[]) allArrays[0];
            byte[] buddyAllocation = (byte[]) allArrays[1];
            byte[] offset1 = (byte[]) allArrays[2];
            byte[] rootBlockSize = (byte[]) allArrays[3];
            byte[] offset2 = (byte[]) allArrays[4];
            byte[] unknown1= (byte[]) allArrays[5];

            if(ArrayUtils.isEmpty(initialisation)||ArrayUtils.isEmpty(buddyAllocation)) {
                isValid = false;
            }

            if(!validateBlocksize(rootBlockSize)){
                isValid=false;
            }

            if(!validateOffset(offset1,offset2)){
                isValid=false;
            }

            // magic1 has to be 00 00 00 01 -> 1
            // magic2 has to be 42 75 64 31 -> Bud1
            if(!ArrayUtils.isEquals(initialisation,REQUIRED_INITIALISATION_INTEGER) || !ArrayUtils.isEquals(buddyAllocation,REQUIRED_BUDDYALLOCATOR_STRING)) {
                isValid = false;
            }

            // Todo validate Blocksize -> should be 2^x

        } else {
            isValid = false;
        }

        return isValid;


    }


    public long getRootBlockOffset() {
        long output=0;
        if(validateOffset(this.getOffset1(),this.getOffset2())){
            output = ByteUtil.parseByteArrayWithKomplementValuesToLong(this.getOffset1());
            output = output+4;
        }else{
            // Todo Logging
        }
        return output;
    }

    public long getRootBlockSizeAsInteger() {
        long output=0;
        if(validateBlocksize(this.getRootBlockSize())){
            output = ByteUtil.parseByteArrayWithKomplementValuesToLong(this.getRootBlockSize());
        }else{
            // Todo: Logging
        }
        return output;
    }

    private static boolean validateOffset(byte[] offset1, byte[] offset2){
        boolean isValid = true;

        if(offset1 == null || offset2 == null){
            isValid=false;
        }else{
            if(Arrays.asList(offset1).contains(null) || Arrays.asList(offset2).contains(null)){
                isValid = false;
            }else {
                if (ArrayUtils.isEmpty(offset1) || ArrayUtils.isEmpty(offset2)) {
                    isValid = false;
                } else {
                    // Offset1 has to be Equal to Offset2
                    if (!ArrayUtils.isEquals(offset1, offset2)) {
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }
    private static boolean validateBlocksize(byte[] rootBlockSize){
        boolean output=true;

        // Todo: implement validation
        if(rootBlockSize.length !=4){
            output=false;
        }

        return output;
    }

    public byte[] getOffset1() {
        return offset1;
    }

    public byte[] getOffset2() {
        return offset2;
    }

    public byte[] getUnknown1() {
        return unknown1;
    }

    public byte[] getRootBlockSize(){
        return rootBlockSize;
    }

    public long[] getRootBlockBoundries() {
        long[] output = new long[2];

        if(validateOffset(this.getOffset1(),this.getOffset2())){
            if(validateBlocksize(this.getRootBlockSize())){

                output[0]=this.getRootBlockOffset();
                output[1]=this.getRootBlockOffset()+getRootBlockSizeAsInteger();

            }else{
                // Todo: Logging
            }
        }else{
            // Todo: Logging
        }

        return output;
    }
}
