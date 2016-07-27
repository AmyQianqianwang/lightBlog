package org.jiucheng.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

/** HTTP请求对象 */
public class HttpRequest {

    /** 响应对象 */
    public class HttpResponse {
        private String         urlString;
        private int            defaultPort;
        private String         file;
        private String         host;
        private String         path;
        private int            port;
        private String         protocol;
        private String         query;
        private String         ref;
        private String         userInfo;
        private String         contentEncoding;
        private String         content;
        private String         contentType;
        private int            code;
        private String         message;
        private String         method;
        private int            connectTimeout;
        private int            readTimeout;
        private Vector<String> contentCollection;

        public String getContent() {
            return content;
        }

        public String getContentType() {
            return contentType;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public Vector<String> getContentCollection() {
            return contentCollection;
        }

        public String getContentEncoding() {
            return contentEncoding;
        }

        public String getMethod() {
            return method;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public String getUrlString() {
            return urlString;
        }

        public int getDefaultPort() {
            return defaultPort;
        }

        public String getFile() {
            return file;
        }

        public String getHost() {
            return host;
        }

        public String getPath() {
            return path;
        }

        public int getPort() {
            return port;
        }

        public String getProtocol() {
            return protocol;
        }

        public String getQuery() {
            return query;
        }

        public String getRef() {
            return ref;
        }

        public String getUserInfo() {
            return userInfo;
        }

    }

    private String defaultContentEncoding;

    public HttpRequest() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    /** 发送GET请求
     * 
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException */
    public HttpResponse sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", null, null);
    }

    /** 发送GET请求
     * 
     * @param urlString URL地址
     * @param params 参数集合
     * @return 响应对象
     * @throws IOException */
    public HttpResponse sendGet(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "GET", params, null);
    }

    /** 发送GET请求
     * 
     * @param urlString URL地址
     * @param params 参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException */
    public HttpResponse sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
            throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    /** 发送POST请求
     * 
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException */
    public HttpResponse sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", null, null);
    }

    /** 发送POST请求
     * 
     * @param urlString URL地址
     * @param params 参数集合
     * @return 响应对象
     * @throws IOException */
    public HttpResponse sendPost(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "POST", params, null);
    }

    /** 发送POST请求
     * 
     * @param urlString URL地址
     * @param params 参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException */
    public HttpResponse sendPost(String urlString, Map<String, String> params, Map<String, String> propertys)
            throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    /** 发送HTTP请求
     * 
     * @param urlString
     * @return 响映对象
     * @throws IOException */
    private HttpResponse send(String urlString, String method, Map<String, String> parameters,
            Map<String, String> propertys) throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        if(connectTimeout > 0) {
            urlConnection.setConnectTimeout(connectTimeout);
        }
        if(readTimeout > 0) {
            urlConnection.setReadTimeout(readTimeout);
        }
        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

        if (propertys != null)
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    /** 得到响应对象
     * 
     * @param urlConnection
     * @return 响应对象
     * @throws IOException */
    private HttpResponse makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        HttpResponse httpResponser = new HttpResponse();
        try {
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null)
                ecod = this.defaultContentEncoding;
            
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,ecod));
            httpResponser.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            httpResponser.urlString = urlString;

            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();

            httpResponser.content = temp.toString();//new String(temp.toString().getBytes(), ecod);
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();

            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    /** 默认的响应字符集 */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /** 设置默认的响应字符集 */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }
    
    private int connectTimeout = -1;
    private int readTimeout = -1;
    
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
