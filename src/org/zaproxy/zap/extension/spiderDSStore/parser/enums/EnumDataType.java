package org.zaproxy.zap.extension.spiderDSStore.parser.enums;

public enum EnumDataType {

    ab("a");

    private String RecordDataTypeString;

    EnumDataType(String RecordTypeString){
        this.RecordDataTypeString = RecordTypeString;
    }

    public String getRecordTypeString(){
        return this.RecordDataTypeString;
    }

}
