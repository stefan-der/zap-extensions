package org.zaproxy.zap.extension.spiderDSStore.parser.model;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.zaproxy.zap.extension.spiderDSStore.JunitRules.ResourceFileRule;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DsStoreRootBlockUnitTests {

    @Rule
    public ResourceFileRule validDSStoreFile = new ResourceFileRule("DS_Store");

    @Mock
    DsStoreHeader mockDsStoreHeader = null;


    final byte[] correctDsStoreRootBlock = new byte[]{
            0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x0B, 0x00, 0x00, 0x00, 0x45,
            0x00, 0x00, 0x10, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x04, 0x44, 0x53, 0x44,
            0x42, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00,
            0x20, 0x00, 0x00, 0x00, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00,
            (byte) 0x80, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x02,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x08,
            0x00, 0x00, 0x00, 0x28, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x30, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00,(byte)  0x80,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x02, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x08, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x20, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, (byte) 0x80, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x02, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x08, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x20, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x09, 0x09, 0x09,
            0x08, 0x09, 0x5F, 0x10, 0x19, 0x7B, 0x7B, 0x31, 0x33, 0x35, 0x2C, 0x20, 0x33, 0x32, 0x32, 0x7D,
            0x2C, 0x20, 0x7B, 0x31, 0x35, 0x35, 0x36, 0x2C, 0x20, 0x36, 0x38, 0x36, 0x7D, 0x7D, 0x09, 0x08,
            0x17, 0x25, 0x31, 0x3D, 0x49, 0x60, 0x6D, 0x79, 0x7A, 0x7B, 0x7C, 0x7D, 0x7E, (byte) 0x9A, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x9B, 0x00, 0x00,
            0x00, 0x06, 0x00, 0x76, 0x00, 0x65, 0x00, 0x6E, 0x00, 0x64, 0x00, 0x6F, 0x00, 0x72, 0x64, 0x73,
            0x63, 0x6C, 0x62, 0x6F, 0x6F, 0x6C, 0x01, 0x00, 0x00, 0x00, 0x06, 0x00, 0x76, 0x00, 0x65, 0x00,
            0x6E, 0x00, 0x64, 0x00, 0x6F, 0x00, 0x72, 0x6C, 0x67, 0x31, 0x53, 0x63, 0x6F, 0x6D, 0x70, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x02, 0x0D, 0x79, 0x00, 0x00, 0x00, 0x06, 0x00, 0x76, 0x00, 0x65, 0x00,
            0x6E, 0x00, 0x64, 0x00, 0x6F, 0x00, 0x72, 0x6C, 0x73, 0x76, 0x43, 0x62, 0x6C, 0x6F, 0x62, 0x00,
            0x00, 0x02, 0x72, 0x62, 0x70, 0x6C, 0x69, 0x73, 0x74, 0x30, 0x30, (byte) 0xD8, 0x01, 0x02, 0x03, 0x04,
            0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0A, 0x46, 0x11, 0x47, 0x48, 0x5F, 0x10, 0x10, 0x75,
            0x73, 0x65, 0x52, 0x65, 0x6C, 0x61, 0x74, 0x69, 0x76, 0x65, 0x44, 0x61, 0x74, 0x65, 0x73, 0x5F,
            0x10, 0x0F, 0x73, 0x68, 0x6F, 0x77, 0x49, 0x63, 0x6F, 0x6E, 0x50, 0x72, 0x65, 0x76, 0x69, 0x65,
            0x77, 0x57, 0x63, 0x6F, 0x6C, 0x75, 0x6D, 0x6E, 0x73, 0x5F, 0x10, 0x11, 0x63, 0x61, 0x6C, 0x63,
            0x75, 0x6C, 0x61, 0x74, 0x65, 0x41, 0x6C, 0x6C, 0x53, 0x69, 0x7A, 0x65, 0x73, 0x58, 0x74, 0x65,
            0x78, 0x74, 0x53, 0x69, 0x7A, 0x65, 0x5A, 0x73, 0x6F, 0x72, 0x74, 0x43, 0x6F, 0x6C, 0x75, 0x6D,
            0x6E, 0x58, 0x69, 0x63, 0x6F, 0x6E, 0x53, 0x69, 0x7A, 0x65, 0x5F, 0x10, 0x12, 0x76, 0x69, 0x65,
            0x77, 0x4F, 0x70, 0x74, 0x69, 0x6F, 0x6E, 0x73, 0x56, 0x65, 0x72, 0x73, 0x69, 0x6F, 0x6E, 0x09,
            0x08, (byte) 0xAB, 0x0C, 0x15, 0x1A, 0x1F, 0x23, 0x28, 0x2D, 0x32, 0x37, 0x3C, 0x41, (byte) 0xD4, 0x0D, 0x0E,
            0x0F, 0x10, 0x11, 0x12, 0x09, 0x09, 0x5A, 0x69, 0x64, 0x65, 0x6E, 0x74, 0x69, 0x66, 0x69, 0x65,
            0x72, 0x55, 0x77, 0x69, 0x64, 0x74, 0x68, 0x59, 0x61, 0x73, 0x63, 0x65, 0x6E, 0x64, 0x69, 0x6E,
            0x67, 0x57, 0x76, 0x69, 0x73, 0x69, 0x62, 0x6C, 0x65, 0x54, 0x6E, 0x61, 0x6D, 0x65, 0x11, 0x04,
            0x0E, 0x09, 0x09, (byte) 0xD4, 0x0D, 0x0E, 0x0F, 0x10, 0x16, 0x17, 0x0A, 0x0A, 0x58, 0x75, 0x62, 0x69,
            0x71, 0x75, 0x69, 0x74, 0x79, 0x10, 0x23, 0x08, 0x08, (byte) 0xD4, 0x0D, 0x0E, 0x0F, 0x10, 0x1B, 0x1C,
            0x0A, 0x09, 0x5C, 0x64, 0x61, 0x74, 0x65, 0x4D, 0x6F, 0x64, 0x69, 0x66, 0x69, 0x65, 0x64, 0x10,
            (byte) 0xB5, 0x08, 0x09, (byte) 0xD4, 0x0D, 0x0E, 0x0F, 0x10, 0x20, 0x1C, 0x0A, 0x0A, 0x5B, 0x64, 0x61, 0x74,
            0x65, 0x43, 0x72, 0x65, 0x61, 0x74, 0x65, 0x64, 0x08, 0x08, (byte) 0xD4, 0x0D, 0x0E, 0x0F, 0x10, 0x24,
            0x25, 0x0A, 0x09, 0x54, 0x73, 0x69, 0x7A, 0x65, 0x10, 0x61, 0x08, 0x09, (byte) 0xD4, 0x0D, 0x0E, 0x0F,
            0x10, 0x29, 0x2A, 0x09, 0x09, 0x54, 0x6B, 0x69, 0x6E, 0x64, 0x10, 0x73, 0x09, 0x09, (byte) 0xD4, 0x0D,
            0x0E, 0x0F, 0x10, 0x2E, 0x2F, 0x09, 0x0A, 0x55, 0x6C, 0x61, 0x62, 0x65, 0x6C, 0x10, 0x64, 0x09,
            0x08, (byte) 0xD4, 0x0D, 0x0E, 0x0F, 0x10, 0x33, 0x34, 0x09, 0x0A, 0x57, 0x76, 0x65, 0x72, 0x73, 0x69,
            0x6F, 0x6E, 0x10, 0x4B, 0x09, 0x08, (byte) 0xD4, 0x0D, 0x0E, 0x0F, 0x10, 0x38, 0x39, 0x09, 0x0A, 0x58,
            0x63, 0x6F, 0x6D, 0x6D, 0x65, 0x6E, 0x74, 0x73, 0x11, 0x01, 0x2C, 0x09, 0x08, (byte) 0xD4, 0x0D, 0x0E,
            0x0F, 0x10, 0x3D, 0x3E, 0x0A, 0x0A, 0x5E, 0x64, 0x61, 0x74, 0x65, 0x4C, 0x61, 0x73, 0x74, 0x4F,
            0x70, 0x65, 0x6E, 0x65, 0x64, 0x10, (byte) 0xC8, 0x08, 0x08, (byte) 0xD4, 0x10, 0x0E, 0x0F, 0x0D, 0x0A, 0x1C,
            0x0A, 0x44, 0x08, 0x08, 0x59, 0x64, 0x61, 0x74, 0x65, 0x41, 0x64, 0x64, 0x65, 0x64, 0x08, 0x23,
            0x40, 0x28, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x23, 0x40, 0x30, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x10, 0x01, 0x00, 0x08, 0x00, 0x19, 0x00, 0x2C, 0x00, 0x3E, 0x00, 0x46, 0x00, 0x5A, 0x00,
            0x63, 0x00, 0x6E, 0x00, 0x77, 0x00, (byte) 0x8C, 0x00, (byte) 0x8D, 0x00, (byte) 0x8E, 0x00, (byte) 0x9A, 0x00, (byte) 0xA3, 0x00,
            (byte) 0xAE, 0x00, (byte) 0xB4, 0x00, (byte) 0xBE, 0x00, (byte) 0xC6, 0x00, (byte) 0xCB, 0x00, (byte)  0xCE, 0x00,(byte)  0xCF, 0x00, (byte) 0xD0, 0x00,
            (byte) 0xD9, 0x00, (byte) 0xE2, 0x00, (byte) 0xE4, 0x00, (byte) 0xE5, 0x00, (byte) 0xE6, 0x00, (byte) 0xEF, 0x00, (byte) 0xFC, 0x00, (byte) 0xFE, 0x00,
            (byte) 0xFF, 0x01, 0x00, 0x01, 0x09, 0x01, 0x15, 0x01, 0x16, 0x01, 0x17, 0x01, 0x20, 0x01, 0x25, 0x01,
            0x27, 0x01, 0x28, 0x01, 0x29, 0x01, 0x32, 0x01, 0x37, 0x01, 0x39, 0x01, 0x3A, 0x01, 0x3B, 0x01,
            0x44, 0x01, 0x4A, 0x01, 0x4C, 0x01, 0x4D, 0x01, 0x4E, 0x01, 0x57, 0x01, 0x5F, 0x01, 0x61, 0x01,
            0x62, 0x01, 0x63, 0x01, 0x6C, 0x01, 0x75, 0x01, 0x78, 0x01, 0x79, 0x01, 0x7A, 0x01, (byte) 0x83, 0x01,
            (byte) 0x92, 0x01, (byte) 0x94, 0x01, (byte) 0x95, 0x01, (byte) 0x96, 0x01, (byte) 0x9F, 0x01, (byte) 0xA0, 0x01, (byte)  0xA1, 0x01, (byte)  0xAB, 0x01,
            (byte) 0xAC, 0x01, (byte) 0xB5, 0x01,(byte)  0xBE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x01, 0x00, 0x00, 0x00,
    };
    // because RootBlock starts at 0x00
    final long correctDsStoreRootBlockOffset = 0x00;
    final long correctDsStoreRootBlockSize=0x0800;
    final long[] mockBoundriesForWorkingFile= {correctDsStoreRootBlockOffset,correctDsStoreRootBlockSize};

    @Before
    public void before(){
        this.mockDsStoreHeader = mock(DsStoreHeader.class);
    }

    @Test
    public void createRootBlockFromHeader_Unittest() throws IOException {
        // given
        when(mockDsStoreHeader.getRootBlockBoundries()).thenReturn(mockBoundriesForWorkingFile);
        // when
        DsStoreRootBlock dsStoreRootBlock= DsStoreRootBlock.getRootBlockFromInputStream(new ByteArrayInputStream(correctDsStoreRootBlock),mockDsStoreHeader);
        // then
        Assert.assertNotNull(dsStoreRootBlock);
    }

    @Test
    public void validateDsStoreRootBlock(){
        // given
        when(mockDsStoreHeader.getRootBlockBoundries()).thenReturn(mockBoundriesForWorkingFile);
        // when
        boolean outputFromFunction = DsStoreRootBlock.validateDsStoreRootBlock(correctDsStoreRootBlock, mockDsStoreHeader);
        // then
        Assert.assertTrue(outputFromFunction);
    }


}
