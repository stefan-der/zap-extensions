package org.zaproxy.zap.extension.spiderDSStore.testutils;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.util.ServerRunner;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DsStoreServer extends NanoHTTPD {

    final String RELATIVE_URL_RESPOND_PATH = "/DsStoreServerStructure";
    final String VALID_DSSTORE_PATH = RELATIVE_URL_RESPOND_PATH + "/.DS_Store";
    final String VALID_PATH_TO_FILE = new File("test/org/zaproxy/zap/extension/spiderDSStore/testutils/DsStoreServerStructure/DS_Store").getAbsolutePath();


    private Logger logger  = Logger.getLogger(DsStoreServer.class);

    public DsStoreServer(int port) {
        super(port);
    }

    public DsStoreServer(){
        super(8080);

    }


    @Override
    public Response serve(IHTTPSession session){
        Response output=null;

        Map<String, List<String>> decodedQueryParameters =
                decodeParameters(session.getQueryParameterString());


        // returns a relative Path
        String sessionUri=session.getUri();

        if(sessionUri.contains(this.RELATIVE_URL_RESPOND_PATH)){
            logger.info(String.format("URI contains DsStoreServerStructure URI: %s",sessionUri));
            switch (sessionUri){
                case VALID_DSSTORE_PATH:
                    output = this.serveFiles(new File(VALID_PATH_TO_FILE));
                    break;
                default:
                    output = newFixedLengthResponse(Response.Status.NOT_FOUND,"text/html","Not Found");
            }


        }else{
            logger.info(String.format("URI doesnt contains DssStoreServerStructure in URI: %s",sessionUri));

            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("<head><title>Debug Server</title></head>");
            sb.append("<body>");
            sb.append("<h1>Debug Server</h1>");

            sb.append("<p><blockquote><b>URI</b> = ").append(
                    String.valueOf(session.getUri())).append("<br />");

            sb.append("<b>Method</b> = ").append(
                    String.valueOf(session.getMethod())).append("</blockquote></p>");

            sb.append("<h3>Headers</h3><p><blockquote>").
                    append(toString(session.getHeaders())).append("</blockquote></p>");

            sb.append("<h3>Parms</h3><p><blockquote>").
                    append(toString(session.getParms())).append("</blockquote></p>");

            sb.append("<h3>Parms (multi values?)</h3><p><blockquote>").
                    append(toString(decodedQueryParameters)).append("</blockquote></p>");

            try {
                Map<String, String> files = new HashMap<String, String>();
                session.parseBody(files);
                sb.append("<h3>Files</h3><p><blockquote>").
                        append(toString(files)).append("</blockquote></p>");
            } catch (Exception e) {
                e.printStackTrace();
            }

            sb.append("</body>");
            sb.append("</html>");
            output = newFixedLengthResponse(sb.toString());
        }


        return output;
    }

    private String toString(Map<String, ? extends Object> map) {
        if (map.size() == 0) {
            return "";
        }
        return unsortedList(map);

    }


    public static void main(String[] args) {
        ServerRunner.run(DsStoreServer.class);
    }


    public Response serveFiles(File fileToServe){
        FileInputStream fis = null;
        String mimeType = null;
        Long bytesToServe = null;

        try{
            fis = new FileInputStream(fileToServe);
            mimeType = getContentType(fileToServe);
            bytesToServe = fileToServe.length();

        }catch (IOException e){
            e.printStackTrace();
        }


        Response output = newChunkedResponse(Response.Status.OK, mimeType, fis);

        return output;
    }

    // use external lib Tika for determining Content Type
    public String getContentType(File file) throws IOException {
        String mimeType=null;
        if(file != null) {
            Tika tika = new Tika();
            mimeType = tika.detect(file);
        }else{
            throw new FileNotFoundException("File is null.");
        }
        return mimeType;
    }

    // use of URL getContentType
    // without external lib
    public String getContentTypeByUrl(File file) throws IOException{
        String mimeType=null;
        if(file !=null){
            URLConnection connection = file.toURI().toURL().openConnection();
            mimeType = connection.getContentType();
        }else{
            throw new FileNotFoundException("File is null.");
        }
        return mimeType;
    }



    private String unsortedList(Map<String, ? extends Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (Map.Entry entry : map.entrySet()) {
            listItem(sb, entry);
        }
        sb.append("</ul>");
        return sb.toString();
    }

    private void listItem(StringBuilder sb, Map.Entry entry) {
        sb.append("<li><code><b>").append(entry.getKey()).
                append("</b> = ").append(entry.getValue()).append("</code></li>");
    }

}
