package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class DsStoreRecord {
    /**
     * Record describes an Entry inside the balanced Tree of the DSStore File
     */

    // Filename/Foldername length, in characters                                    -> 4 Byte -> int Interpretation
    private byte[] fileNameLength;
    // Filename/Foldername as String                                                -> Maximum Name Length 4 Byte int_Max :)
    private byte[] fileName;
    // DSSToreEntryType -> Set of Entry Types                                       -> 4 Byte -> char Interpretation
    private byte[] dsStoreEntryType;
    // DSStoreDataType -> Set of allowed Data Types which are stored in .DS_STORE DB -> 4 Byte -> char Interpretation
    private byte[] dsSoreDataType;

    public DsStoreRecord(byte[] fileNameLength, byte[] fileName, byte[] dsStoreEntryType, byte[] dsStoreDataType) {
        this.fileNameLength=fileNameLength;
        this.fileName= fileName;
        this.dsStoreEntryType=dsStoreEntryType;
        this.dsSoreDataType = dsStoreDataType;
    }

    public byte[] getFileNameLength() {
        return fileNameLength;
    }

    public void setFileNameLength(byte[] fileNameLength) {
        this.fileNameLength = fileNameLength;
    }

    public byte[] getFileName() {
        return fileName;
    }

    public void setFileName(byte[] fileName) {
        this.fileName = fileName;
    }

    public byte[] getDsStoreEntryType() {
        return dsStoreEntryType;
    }

    public void setDsStoreEntryType(byte[] dsStoreEntryType) {
        this.dsStoreEntryType = dsStoreEntryType;
    }

    public byte[] getDsSoreDataType() {
        return dsSoreDataType;
    }

    public void setDsSoreDataType(byte[] dsSoreDataType) {
        this.dsSoreDataType = dsSoreDataType;
    }

    public static boolean checkRecordIntegrity(DsStoreRecord dsStoreRecord){
        boolean output = true;
        // TODO: integrate

        return output;
    }


    public LinkedList<DsStoreRecord> getDsStoreFrom(DsStoreRootBlock dsStoreRootBlock){
        LinkedList<DsStoreRecord> dsStoreRecordLinkedList = new LinkedList<>();




        return dsStoreRecordLinkedList;
    }

}
