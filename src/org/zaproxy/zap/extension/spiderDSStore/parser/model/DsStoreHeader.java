package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.FileSystemException;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.zaproxy.zap.extension.spiderDSStore.ByteUtils;

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
    // 00 00 08 00
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

    public static boolean validateHeader(Byte[] byteHeader) {
        boolean isValid = true;

        // Header has to be exact 36 Byte Long
        if(byteHeader != null || byteHeader.length != 36) {


            Byte[] initialisation = (Byte[]) ArrayUtils.subarray(byteHeader, 0, 3);
            Byte[] buddyAllocation = (Byte[]) ArrayUtils.subarray(byteHeader, 4, 7);
            Byte[] offset1 = (Byte[]) ArrayUtils.subarray(byteHeader, 8, 11);
            Byte[] rootBlockSize = (Byte[]) ArrayUtils.subarray(byteHeader, 12, 15);
            Byte[] offset2 = (Byte[]) ArrayUtils.subarray(byteHeader, 16, 19);


            int  initialisationAsInt = ByteUtils.convertByteArrayToInt(initialisation);
            String buddyAllocationAsString = ByteUtils.convertByteArrayToString(buddyAllocation);
            // magic1 has to be 00 00 00 01 -> 1
            // magic2 has to be 42 75 64 31 -> Bud1
            if (buddyAllocationAsString != "Bud1" || initialisationAsInt != 1) {
                isValid = false;
            }

            int offset1AsInteger=ByteUtils.convertByteArrayToInt(offset1);
            int offset2AsInteger=ByteUtils.convertByteArrayToInt(offset2);

            // Offset1 has to be Equal to Offset2
            if (offset1AsInteger != offset2AsInteger) {
                isValid = false;
            }
        }else {
            isValid = false;
        }

        return isValid;
    }
}
