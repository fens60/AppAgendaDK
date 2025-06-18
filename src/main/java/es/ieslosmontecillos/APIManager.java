package es.ieslosmontecillos;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class for managing and simplifying API requests such as CRUD operations or queries
 * @author Francisco Manuel Gonzalez Martin
 * @version 2.1
 * @since 11/2/2025
 */
@SuppressWarnings("unused")
public class APIManager
{
    /**
     * Request methods available in the API
     */
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

    /**
     * Sends a request without body to the API
     * @param endPoint Endpoint of the API to send the request
     * @param method Method that the request will use (GET, POST, PUT, DELETE)
     * @return A http connection with the data provided by the API endpoint
     * @throws Exception Couldn't connect to the API or there was an error reading the response
     * @since 2.1
     */
    public HttpURLConnection sendRequest(String endPoint, METHOD method) throws Exception
    {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(FULL_URL + endPoint).openConnection();
        httpURLConnection.setRequestMethod(method.toString());
        httpURLConnection.connect();

        if (httpURLConnection.getResponseCode() >= 400) throw new Exception("HTTP_RETURNED_NOT_OK");
        return httpURLConnection;
    }

    /**
     * Sends a request WITH a body to the API
     * @param endPoint Endpoint of the API to send the request
     * @param method Method that the request will use (GET, POST, PUT, DELETE)
     * @param body Body that the request will send to the API
     * @return A http connection with the data provided by the API endpoint
     * @throws Exception Couldn't connect to the API or there was an error reading the response
     * @since 2.1
     */
    public HttpURLConnection sendRequest(String endPoint, METHOD method, String body) throws Exception
    {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(FULL_URL + endPoint).openConnection();
        httpURLConnection.setRequestMethod(method.toString());
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.getOutputStream().write(body.getBytes());

        if (httpURLConnection.getResponseCode() >= 400) throw new Exception("HTTP_RETURNED_NOT_OK");
        return httpURLConnection;
    }

    /**
     * Sends a request WITHOUT body to the API and process the response and converts it into a {@code JsonStructure}
     * @param endPoint Endpoint of the API to send the request
     * @param method Method that the request will use (GET, POST, PUT, DELETE)
     * @return Json with the data provided by the API endpoint
     * @throws Exception Couldn't connect to the API or there was an error reading the response
     * @since 2.0
     */
    public JsonStructure sendJsonRequest(String endPoint, METHOD method) throws Exception
    {
        return GetJsonFromInputStream(sendRequest(endPoint,method).getInputStream());
    }

    /**
     * Sends a request WITH a body to the API and process the response and converts it into a {@code JsonStructure}
     * @param endPoint Endpoint of the API to send the request
     * @param method Method that the request will use (GET, POST, PUT, DELETE)
     * @param body Body that the request will send to the API
     * @return Json with the data provided by the API endpoint
     * @throws Exception Couldn't connect to the API or there was an error reading the response
     * @since 2.0
     */
    public JsonStructure sendJsonRequest(String endPoint, METHOD method, String body) throws Exception
    {
        return GetJsonFromInputStream(sendRequest(endPoint,method,body).getInputStream());
    }

    /**
     * Reads an InputStream and creates a Json from it
     * @param inputStream InputStream of the request
     * @return JsonObject with the data provided by the InputStream
     * @throws IOException Couldn't read the bufferedReader of the InputStream
     * @since 2.0
     */
    private JsonStructure GetJsonFromInputStream(InputStream inputStream) throws IOException
    {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null)
            {
                response.append(inputLine);
            }

            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonStructure json = jsonReader.read();
            jsonReader.close();

            return json;
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            throw e;
        }

    }

}
