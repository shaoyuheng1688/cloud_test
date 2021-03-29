package com.raymon.frame.utils;

import com.raymon.frame.web.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpClientUtils {
    private static final Log log = LogFactory.getLog(HttpClientUtils.class);
    private PoolingClientConnectionManager cm = null;
    private IdleConnectionMonitorThread idleMonitor = null;
    private static volatile HttpClientUtils clientUtils = null;
    private final int DEF_CONN_TIMEOUT = 5000;
    private final int DEF_READ_TIMEOUT = 15000;
    private int connTimeout;
    private int readTimeout;

    private HttpClientUtils() {
    }

    private void initUtils() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
                .getSocketFactory()));

        cm = new PoolingClientConnectionManager(schemeRegistry);
        try {
            int maxTotal = 100;
            cm.setMaxTotal(maxTotal);
        } catch (NumberFormatException e) {
            log.error(
                    "Key[httpclient.max_total] Not Found in systemConfig.properties",
                    e);
        }
        // 每条通道的并发连接数设置（连接池）
        try {
            int defaultMaxConnection = 10;
            cm.setDefaultMaxPerRoute(defaultMaxConnection);
        } catch (NumberFormatException e) {
            log.error(
                    "Key[httpclient.default_max_connection] Not Found in systemConfig.properties",
                    e);
        }

        if (cm != null) {
            idleMonitor = new IdleConnectionMonitorThread(cm);
        }
    }

    public static class IdleConnectionMonitorThread extends Thread {

        private final ClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(ClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 8 hours
                        connMgr.closeIdleConnections(8, TimeUnit.HOURS);
                    }
                }
            } catch (InterruptedException ex) {
                log.error(ex);
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }

    public static HttpClientUtils getHttpClientUtils() {
        if (clientUtils == null) {
            synchronized (HttpClientUtils.class) {
                if (clientUtils == null) {
                    HttpClientUtils cu = new HttpClientUtils();
                    cu.initUtils();
                    clientUtils = cu;
                }
            }
        }
        return clientUtils;
    }

    public HttpClient getHttpClient() {
        return getHttpClient(DEF_CONN_TIMEOUT, DEF_READ_TIMEOUT);
    }

    public HttpClient getHttpClient(int connTimeout, int readTimeout) {
        this.connTimeout = connTimeout;
        this.readTimeout = readTimeout;
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                HttpVersion.HTTP_1_1);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeout);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout); // readtimeout
//		params.setParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 0);
//		params.setParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 100000000);
        return new DefaultHttpClient(cm, params);
    }

    public void release() {
        if (idleMonitor != null) {
            idleMonitor.shutdown();
            idleMonitor = null;
        }
        if (cm != null) {
            cm.shutdown();
            cm = null;
        }
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }


    public static String doPost(String url, Map<String, String> paramsMap) {
        HttpClient httpClient = HttpClientUtils.getHttpClientUtils().getHttpClient();
        HttpPost httpPost = new HttpPost(url.replace(" ", "%20"));
        HttpEntity httpentity;
        HttpResponse response = null;
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (null != paramsMap) {
                for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpentity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(httpentity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("连接失败url:" + url);
                throw new ApplicationException("连接失败，错误码：" + response.getStatusLine().getStatusCode());
            }
            HttpEntity entity = response.getEntity();
            String retData = EntityUtils.toString(response.getEntity(), "UTF-8");
            //retData = new String(retData.getBytes("ISO-8859-1"),"UTF-8");
            return retData;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(response);
        }
        return "";

    }
}
