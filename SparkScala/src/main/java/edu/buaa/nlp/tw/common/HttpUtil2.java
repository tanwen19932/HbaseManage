package edu.buaa.nlp.tw.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class HttpUtil2 {
    public static final Logger LOG = LoggerFactory.getLogger( HttpUtil2.class );

    public static void config(HttpURLConnection con)
            throws Exception {

        con.setRequestMethod( "GET" );

        //        con.setInstanceFollowRedirects(true);

        //        con.setDoInput( true );
        //        con.setDoOutput( false );

        con.setConnectTimeout( 10 * 60 * 1000 );
        con.setReadTimeout( 10 * 60 * 1000 );
        //        con.setUseCaches(false);
        //        con.setRequestProperty("User-Agent","Mozilla/3.5.7 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //        con.setRequestProperty("Connection","keep-alive");
        //        con.setRequestProperty("Accept-Encoding", "identity");

    }

    public static String getHttpContentNio(String URL)
            throws MalformedURLException {
        URL url = new URL( URL );
        HttpURLConnection con = null;
        InputStream is = null;
        int contentLength = 0;
        try {
            int code;
            for (int redirect = 0; redirect <= 5; redirect++) {
                con = (HttpURLConnection) url.openConnection();
                config( con );
                con.setRequestProperty( "Accept-Encoding", "gzip" );
                //				con.connect();
                code = con.getResponseCode();
                contentLength = con.getContentLength();
                LOG.info( " ResponseCode " + code + " 文件大小" + contentLength + " " + URL );
                /* 只记录第一次返回的code */
                boolean needBreak = false;
                switch (code) {
                    case HttpURLConnection.HTTP_MOVED_PERM:
                    case HttpURLConnection.HTTP_MOVED_TEMP:
                        if (redirect == 5) {
                            throw new Exception( "redirect to much time" );
                        }
                        String location = con.getHeaderField( "Location" );
                        if (location == null) {
                            throw new Exception( "redirect with no location" );
                        }
                        url = new URL( url, location );
                        continue;
                    default:
                        needBreak = true;
                        break;
                }
                if (needBreak) {
                    break;
                }

            }

            is = con.getInputStream();
            String contentEncoding = con.getContentEncoding();
            if (contentEncoding != null && contentEncoding.equals( "gzip" )) {
                is = new GZIPInputStream( is );
                System.out.println( "is gzip" );
            }
            byte[] buf = new byte[10 * 1024];
            int read;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int retry = 0;
            int readAlready = 0;
            int k = 1;
            while (true) {
                if (retry > 1) {
                    break;
                }
                try {
                    if ((read = is.read( buf )) != -1) {
                        bos.write( buf, 0, read );
                        readAlready += read;
                        if (readAlready > k * 1024 * 1000) {
                            k++;
                            System.out.print( "已下载" + (readAlready / 1000) + "K.." + URL );
                            System.out.println();
                        }
                        //						System.out.println(new String (buf));
                    } else {
                        System.out.println();
                        System.out.println( "下载完成" + contentLength / 1000 + "K.." );
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println( "读取错误！ 重试" + retry++ + URL );
                    System.out.print( "已下载 " + (readAlready / 1000) + "K.." + URL );
                    Thread.sleep( 5 * 1000 );
                    if (bos.size() == contentLength) {
                        break;
                    }
                }
            }
            return new String( bos.toByteArray() );

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error( e.getMessage() );
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return null;

    }


    public static String getHttpContent(String URL)
            throws MalformedURLException {
        URL url = new URL( URL );
        HttpURLConnection con = null;
        InputStream is = null;
        int contentLength = 0;
        try {
            int code;
            for (int redirect = 0; redirect <= 5; redirect++) {
                con = (HttpURLConnection) url.openConnection();
                config( con );
                con.setRequestProperty( "Accept-Encoding", "gzip" );
                //				con.connect();
                code = con.getResponseCode();
                contentLength = con.getContentLength();
                LOG.debug( " ResponseCode " + code + " 文件大小" + contentLength + " " + URL );
                /* 只记录第一次返回的code */
                boolean needBreak = false;
                switch (code) {
                    case HttpURLConnection.HTTP_MOVED_PERM:
                    case HttpURLConnection.HTTP_MOVED_TEMP:
                        if (redirect == 5) {
                            throw new Exception( "redirect to much time" );
                        }
                        String location = con.getHeaderField( "Location" );
                        if (location == null) {
                            throw new Exception( "redirect with no location" );
                        }
                        url = new URL( url, location );
                        continue;
                    default:
                        needBreak = true;
                        break;
                }
                if (needBreak) {
                    break;
                }

            }

            is = con.getInputStream();
            String contentEncoding = con.getContentEncoding();
            if (contentEncoding != null && contentEncoding.equals( "gzip" )) {
                is = new GZIPInputStream( is );
                System.out.println( "is gzip" );
            }
            byte[] buf = new byte[10 * 1024];
            int read;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int retry = 0;
            int readAlready = 0;
            int k = 1;
            while (true) {
                if (retry > 1) {
                    break;
                }
                try {
                    if ((read = is.read( buf )) != -1) {
                        bos.write( buf, 0, read );
                        readAlready += read;
                        if (readAlready > k * 1024 * 1000) {
                            k++;
                            LOG.debug( "已下载" + (readAlready / 1000) + "K.." + URL );
                        }
                        //						System.out.println(new String (buf));
                    } else {
                        LOG.debug( "下载完成" + contentLength / 1000 + "K.." );
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.debug( "读取错误！ 重试" + retry++ + URL );
                    LOG.debug( "已下载 " + (readAlready / 1000) + "K.." + URL );
                    Thread.sleep( 5 * 1000 );
                    if (bos.size() == contentLength) {
                        break;
                    }
                }
            }
            return new String( bos.toByteArray() );

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error( e.getMessage() );
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return null;

    }


}
