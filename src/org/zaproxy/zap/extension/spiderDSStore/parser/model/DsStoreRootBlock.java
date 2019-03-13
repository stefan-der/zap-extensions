package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import org.zaproxy.zap.extension.spiderDSStore.parser.Allocator;

public class DsStoreRootBlock {

    private Allocator allocator;
    private long offset;
    private long size;
    private byte[] value;
    private long position;
    private boolean dirty = false;

    public DsStoreRootBlock(Allocator allocator, long offset, long size) throws Exception{
        this.allocator=allocator;
        this.offset=offset;
        this.size=size;
        try {
            this.value = allocator.read(offset,size);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    


    public Allocator getAllocator() {
        return allocator;
    }

    public void setAllocator(Allocator allocator) {
        this.allocator = allocator;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
