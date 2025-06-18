package es.ieslosmontecillos;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("unused")
public class APIManager
{
    public enum METHOD
    {
        /** GET Request */
        GET,
        /** POST Request */
        POST,
        /** PUT Request */
        PUT,
        /** DELETE Request */
        DELETE
    }

    //API data for connections, the data does not include the endpoint
    private final String HOST;
    private final int PORT;
    private final String API_PATH;

    //URL to the API constructed from the data above, does not include the endpoint
    private final String FULL_URL;

    /**
     * Creates an APIManager pointing to the default URL ("http://localhost:8080/api/v1/")
     * @since 1.0
     */
    public APIManager()
    {
        //HOST = "192.168.8.219";
        HOST = "localhost";
        PORT = 8080;
        API_PATH = "/api/v1/";
        FULL_URL = "http://" + HOST + ":" + PORT + API_PATH;
    }

    /**
     * Creates an APIManager pointing to the specified HOST, PORT and API on that host
     * @param HOST host ip or domain address
     * @param PORT port that the api is listening to
     * @param API_PATH path of the api in the host
     * @since 1.0
     */
    public APIManager(String HOST, int PORT, String API_PATH)
    {
        this.HOST = HOST;
        this.PORT = PORT;
        this.API_PATH = API_PATH;
        FULL_URL = "http://" + HOST + ":" + PORT + API_PATH;
    }

    public HttpURLConnection sendRequest(String endPoint, METHOD method) throws Exception
    {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(FULL_URL + endPoint).openConnection();
        httpURLConnection.setRequestMethod(method.toString());
        httpURLConnection.connect();

        if (httpURLConnection.getResponseCode() >= 400) throw new Exception("HTTP_RETURNED_NOT_OK");
        return httpURLConnection;
    }

}
