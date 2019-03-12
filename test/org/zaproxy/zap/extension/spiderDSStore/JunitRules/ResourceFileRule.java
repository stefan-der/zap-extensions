package org.zaproxy.zap.extension.spiderDSStore.JunitRules;

import org.apache.commons.io.IOUtils;
import org.junit.rules.ExternalResource;

import java.io.*;
import java.nio.charset.Charset;

public class ResourceFileRule extends ExternalResource {

    // https://stackoverflow.com/questions/2597271/easy-way-to-get-a-test-file-into-junit

    String res;
    File file = null;
    InputStream stream;

    public ResourceFileRule(String res)
        {
            this.res = res;
        }

        public File getFile() throws IOException
        {
            if (file == null)
            {
                createFile();
            }
            return file;
        }

        public InputStream getInputStream()
        {
            return stream;
        }

        public InputStream createInputStream()
        {
            return getClass().getResourceAsStream(res);
        }

        public String getContent() throws IOException
        {
            return getContent("utf-8");
        }

        public String getContent(String charSet) throws IOException
        {
            InputStreamReader reader = new InputStreamReader(createInputStream(),
                    Charset.forName(charSet));
            char[] tmp = new char[4096];
            StringBuilder b = new StringBuilder();
            try
            {
                while (true)
                {
                    int len = reader.read(tmp);
                    if (len < 0)
                    {
                        break;
                    }
                    b.append(tmp, 0, len);
                }
                reader.close();
            }
            finally
            {
                reader.close();
            }
            return b.toString();
        }

        public byte[] getContentAsByteArray() throws IOException{
            byte[] output = null;

            InputStream is = createInputStream();
            output = IOUtils.toByteArray(is);

            return output;
        }


        @Override
        protected void before() throws Throwable
        {
            super.before();
            stream = getClass().getResourceAsStream(res);
        }

        @Override
        protected void after()
        {
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
                // ignore
            }
            if (file != null)
            {
                file.delete();
            }
            super.after();
        }

        private void createFile() throws IOException
        {
            file = new File(".",res);
            InputStream stream = getClass().getResourceAsStream(res);
            try
            {
                file.createNewFile();
                FileOutputStream ostream = null;
                try
                {
                    ostream = new FileOutputStream(file);
                    byte[] buffer = new byte[4096];
                    while (true)
                    {
                        int len = stream.read(buffer);
                        if (len < 0)
                        {
                            break;
                        }
                        ostream.write(buffer, 0, len);
                    }
                }
                finally
                {
                    if (ostream != null)
                    {
                        ostream.close();
                    }
                }
            }
            finally
            {
                stream.close();
            }
        }

}
