package org.zaproxy.zap.extension.spiderDSStore;

import org.apache.commons.io.FileUtils;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.validator.routines.UrlValidator;

public class FileDownloader {

    static final String TEMP_FILE_PREFIX ="DSStore_";
    static final String TEMP_FILE_SUFFIX ="_FileContainer";
    static final int CONNECT_TIMEOUT = 30;                  // Milliseconds
    static final int READ_TIMEOUT = 30;                     // Milliseconds

    public static File downloadFileToTempDirectory (URL url){
        Path downloadedFile = null;
        File outputFile = null;

        try{
            downloadedFile = Files.createTempFile(TEMP_FILE_PREFIX,TEMP_FILE_SUFFIX);
        }catch (Exception e){
            // Todo: Logging
        }

        try {
            FileUtils.copyURLToFile(url,downloadedFile.toFile(),CONNECT_TIMEOUT,READ_TIMEOUT);
        }catch (Exception e){
            // Todo: Logging
        }

        if(downloadedFile != null){
            outputFile=downloadedFile.toFile();
        }

        return outputFile;
    }

    public static boolean testUrl(){
        UrlValidator urlValidator=null;
        throw new UnsupportedOperationException();
    }
    public static void validateContentType(){
        throw new UnsupportedOperationException();
    }

}
