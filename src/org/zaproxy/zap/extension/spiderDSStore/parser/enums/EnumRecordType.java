package org.zaproxy.zap.extension.spiderDSStore.parser;

// https://www.mkyong.com/java/java-enum-example/

public enum EnumRecordType {

    BKGD("BKGD"), // 12-byte blob, directories only.  Three possible subtypes
    ICVO("ICVO"), // bool, directories only
    I1oc("I1oc"), // 16-byte blob - icon location
    LSVO("LSVO"),
    bwsp("bwsp"),
    cmmt("cmmt"),
    dilc("dilc"),
    dscl("dscl"),
    extn("extn"),
    fwi0("fwi0"),
    fwsw("fwsw"),
    fwvh("fwvh"),
    GRP0("GRP0"), // ustr.  unknown.
    icnv("icnv"), // Unknown, seen in LOVE"s DS_Store
    icgo("icgo"), // 8-byte blob, directories (and files?). Unknown. Probably two integers, and often the value 00 00 00 00 00 00 00 04.
    icsp("icsp"), // 8-byte blob, directories only. Unknown, usually all but the last two bytes are zeroes.
    icvo("icvo"), //
    icvp("icvp"),
    icvt("icvt"),
    info("info"),
    logS("logS"), // comp, logical size of directory"s contents.  Appeared in 10.7
    lg1S("lg1S"), // comp, logical size of directory"s contents.  Appeared in 10.8, replacing logS
    lssp("lssp"), // 8-byte blob, directories only.  Possibly the scroll position in list view mode?
    lsvo("lsvo"),
    lsvt("lsvt"),
    lsvp("lsvp"),
    lsvP("lsvP"),
    modD("modD"), // dutc, directories only - modification date
    moDD("moDD"), // dutc, directories only - modification date
    pBB0("pBB0"), // blob
    pBBk("pBBk"), // blob, something to do with bookmarks
    phyS("phyS"),
    ph1S("ph1S"),
    pict("pict"),
    vSrn("vSrn"),
    vstl("vstl");


    private String RecordTypeString;

    EnumRecordType(String RecordTypeString){
        this.RecordTypeString = RecordTypeString;
    }

    public String getRecordTypeString(){
        return this.RecordTypeString;
    }


}
