import apis.MeetUpApiLoaders;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.io.CharStreams;
import sheet.CategoryDto;
import sheet.SheetUploader;
import sheet.Status;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoaderFromApis {
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static GenericUrl getCategoryEndpoint = new GenericUrl("http://54.37.10.104:8080/api/category/list");

    public static void main(String... args) throws IOException, GeneralSecurityException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        File file = new File("err.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);

        List<Status> importResult = new ArrayList<>();

        //Loading data from api
        importResult.addAll(new MeetUpApiLoaders().loadFromApi());

        //Upload these data to google sheet
        new SheetUploader().sendDataToServerAndUpdateSheet(importResult);
    }


    private static List<CategoryDto> getAllCategories() throws IOException {
        List<CategoryDto> result;

        HttpRequestFactory requestFactory
                = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                });

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        HttpRequest request = requestFactory.buildGetRequest(getCategoryEndpoint);
        HttpResponse response = request.execute();
        try (final Reader reader = new InputStreamReader(response.getContent())) {
            result = Arrays.asList(objectMapper.readValue(CharStreams.toString(reader), CategoryDto[].class));
        }
        System.out.println("Got categories. Size: " + result.size());
        return result;
    }

}