package com.group7.goodongroceries.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;

public class NetworkUtils {

    private static final String HOSTNAME = "https://ndb.nal.usda.gov/ndb/doc/apilist/";

    // Add custom cert hashes, didn't work
    private static final OkHttpClient mHTTPClient = new OkHttpClient().newBuilder()
            .certificatePinner(new CertificatePinner.Builder()
                .add(HOSTNAME, "sha256/53a74836e0905a7e1dda58b7a780da05c32bd775411e98c546efcdcf875c5027") //first cert
                    .add(HOSTNAME, "sha256/60bb95e636e98c969709a68473ede5c1fbb9f9735e523720129b221e0a63ff60") //second
                    .add(HOSTNAME, "sha256/635a3d6f0697afab33ebc6be55e20be8cbf37b3fd2b8fe2374b2aec2f506c272") // third
                    .add(HOSTNAME, "sha256/76f2de2b91e0100961fe25943e339364332311c1681a3fbac642dd99a96b7ccf") // fourth
                .build()).build();

//    private static final OkHttpClient mHTTPClient = new OkHttpClient();

    public static String doHTTPGet(String inputUrl) throws IOException {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    public java.security.cert.X509Certificate[] getAcceptedIssuers()
                    {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                    {
                        //No need to implement.
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                    {
                        //No need to implement.
                    }
                }
        };

        // Install the all-trusting trust manager
        try
        {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        // Now you can access an https URL without having the certificate in the truststore
        try {
            StringBuilder content = new StringBuilder();

            URL url = new URL(inputUrl);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line);
            }
            bufferedReader.close();

            return content.toString();

        } catch (MalformedURLException e) {
            throw e;
        }
    }

}