package Model;

import com.sun.net.httpserver.Headers;

import javax.net.ssl.HttpsURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

//class to store Functions to be executed by WebsiteChecker

public class Functions {

    public static String getCertificateFromURL(String webURL) {
        try {
            //Create URL from user input
            URL newURL = new URL(webURL);
            //System.out.println("Valid url format");
            try {
                //Secure connection, unsecure will not connect, add unsecure (HttpURLConnection) as well later on.

                //Create connection with URL
                HttpsURLConnection connectionURL = (HttpsURLConnection) newURL.openConnection();
                connectionURL.setRequestMethod("GET");
                connectionURL.connect();
                int code = connectionURL.getResponseCode();
                //System.out.println("Connection opened, http response code:" + code);
                //System.out.println("Getting SSL certificate information...");
                try {
                    //Get certificate, print information
                    Certificate sslCerts[] = connectionURL.getServerCertificates();
                    //System.out.println(sslCerts.length);
                    for (int certCount = 0; certCount < sslCerts.length; certCount++) {
                        //System.out.println(sslCerts[certCount].toString());
                        X509Certificate certInfo = (X509Certificate) sslCerts[certCount];
                        //Return expiration date for SSL certificates
                        //System.out.println(certInfo.getNotAfter());
                        return certInfo.getNotAfter().toString();
                    }
                    //return (X509Certificate)sslCerts[0];

                } catch (Exception e) {
                    //Cannot get certificate or other exception
                    System.out.println("Could not get SSL certificate, expired?"+e.toString());
                }
            } catch (Exception e) {
                System.out.println("Connection could not be opened, invalid location "+e.toString());
            }
        } catch (Exception e) {
            System.out.println("Invalid url format " + e.toString());
        }
        return null;
    }

    public static String getIPFromURL(String webURL) throws Exception{

        InetAddress address = InetAddress.getByName(new URL(webURL).getHost());
        String ip = address.getHostAddress();
        return ip;

    }

    public static String getServerFromURL(String webURL) throws Exception{
        try{
            URL newURL = new URL(webURL);
            HttpsURLConnection connectionURL = (HttpsURLConnection) newURL.openConnection();
            connectionURL.setRequestMethod("HEAD");
            connectionURL.connect();
            String server = connectionURL.getHeaderField("server");
            System.out.println(connectionURL.getHeaderFields());
            return server;


        }catch(Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public static String getHostNamesFromUrL(String webURL) throws Exception{

        InetAddress addr = InetAddress.getByName(new URL(webURL).getHost());
        String host = addr.getHostName();
        String cHost = addr.getCanonicalHostName();
        return host+", "+cHost;




    }




}
