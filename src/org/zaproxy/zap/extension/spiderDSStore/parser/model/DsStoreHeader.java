package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.FileSystemException;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class DsStoreHeader {

    // Default Header in every .DS_Store File
    // 00 00 00 01                  -> 4 Byte
    private byte[] magic1;

    // Default Header
    // 42 75 64 31                  -> 4 byte (4 Byte Offset)
    private byte[] magic2;

    // Start Offset for the searching for the TOC in current File
    // 00 00 02 00                  -> 4 byte -> int Interpretation
    private byte[] offset1;

    // Root Block Size where the TOC is located
    //
    private byte[] rootBlockSize;           // 4 byte (12 Byte Offset)

    // Should be the same Value as offset 1
    // 00 00 02 00
    private byte[] offset2;        // 4 byte (16 Byte Offset)

    // the unknown Block is not yet reverse engineered
    private byte[] unknown1;       // 16 byte (20 Byte Offset

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
                this.magic1 = Arrays.copyOfRange(fileAsByteArray,0,3);
                this.magic2 = Arrays.copyOfRange(fileAsByteArray,4, 7);
                this.offset1 = Arrays.copyOfRange(fileAsByteArray, 8, 11);
                this.rootBlockSize = Arrays.copyOfRange(fileAsByteArray, 12, 15);
                this.offset2 = Arrays.copyOfRange(fileAsByteArray, 16, 19);
                this.unknown1 = Arrays.copyOfRange(fileAsByteArray, 20 ,36);


            } else {
                throw new FileNotFoundException("File not found Path: " + dsStoreFile.getAbsolutePath());
            }
        }else {
            throw new FileNotFoundException("File should not be Null");
        }
    }

    public boolean checkHeaderIntegrity(){
        boolean isValid = true;

        int magic1Encoded = this.convertByteArrayToInt(this.magic1);
        String magic2Encoded = this.convertByteArrayToString(this.magic2);
        // magic1 has to be 00 00 00 01 -> 1
        // magic2 has to be 42 75 64 31 -> Bud1
        if(magic2Encoded != "Bud1" || magic1Encoded != 1){
            isValid = false;
            logger.info("Buddy is not valid");
            logger.info("Magic1 which has to be 1 was " + magic1Encoded);
            logger.info("Magic2 which has to be Bud1 was " + magic2Encoded);
        }

        // Offset1 has to be Equal to Offset2
        if(this.offset1 != this.offset2){
            isValid = false;
            logger.info("Buddy is not valid");
            logger.info("Offset1 differs from Offset2");
            logger.info("Offset1 " + this.offset1);
            logger.info("Offset2 " + this.offset2);
        }

        return isValid;
    }

    public String convertByteArrayToString(byte[] hexInput){
        String output=null;
        if (hexInput != null  || hexInput.length>0 ){
            output = new String(hexInput);
        }else{
            // Todo: Logging
        }
        return output;
    }

    public int convertByteArrayToInt(byte[] hexInput) {
        int output = -1;


        if (hexInput.length==4){
            // Type is Big Endian
            ByteBuffer byteBuffer = ByteBuffer.wrap(hexInput);
            output = byteBuffer.getInt();
        }

        return output;
    }




}
