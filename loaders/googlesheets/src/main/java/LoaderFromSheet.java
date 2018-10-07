import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.io.CharStreams;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LoaderFromSheet {
    private static final String APPLICATION_NAME = "Wigo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static GenericUrl pushStatusEndpoint = new GenericUrl("http://54.37.10.104:8080/api/status");
    private static GenericUrl getCategoryEndpoint = new GenericUrl("http://localhost:8080/api/category/list");
    private static final String spreadsheetId = "18x15fG4BXVrdFdNA3S0WVLJmV1pVJaDkmHMDVRzP_Og";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    private static Map<String, CategoryDto> allCategories;

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = LoaderFromSheet.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        File file = new File("err.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);

        allCategories = getAllCategories().stream().collect(Collectors.toMap(CategoryDto::getName, c -> c));

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        final String range = "Form Responses 1";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<StatusDto> statuses = parseToStatuses(response.getValues());
        sendDataToServerAndUpdateSheet(statuses, service.spreadsheets().values());
        System.out.println("Load completed...");
    }

    private static List<StatusDto> parseToStatuses(List<List<Object>> values) throws MalformedURLException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        List<StatusDto> result = new ArrayList<>();
        for (List<Object> row : values.subList(1, values.size())) {
            UUID id = row.size() > 12 ? UUID.fromString((String) row.get(12)) : null;
            ArrayList<URL> urls = new ArrayList<>();
            for (String url : Arrays.asList(((String) row.get(9)).split(";"))) {
                urls.add(new URL(url));
            }
            String categoryName = row.get(10) != null ? (String) row.get(10) : (String) row.get(11);
            CategoryDto categoryDto = allCategories.get(categoryName);
            if (categoryDto == null)
                throw new IllegalArgumentException("There is no category with name: " + categoryName);
            result.add(new StatusDto(id, null,
                    Double.parseDouble((String) row.get(5)),
                    Double.parseDouble((String) row.get(6)),
                    (String) row.get(1),
                    (String) row.get(8),
                    new URL((String) row.get(7)),
                    row.get(3) != null ? Instant.from(formatter.parse((String) row.get(3))) : null,
                    row.get(4) != null ? Instant.from(formatter.parse((String) row.get(4))) : null,
                    new HashSet<>(),
                    categoryDto.getId(),
                    urls));
        }
        return result;
    }


    private static void sendDataToServerAndUpdateSheet(List<StatusDto> statuses, Sheets.Spreadsheets.Values values) throws IOException {
        HttpRequestFactory requestFactory
                = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                });

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        for (int i = 0; i < statuses.size(); i++) {
            StatusDto s = statuses.get(i);
            if (s.getId() != null) continue;
            HttpRequest request = requestFactory.buildPostRequest(pushStatusEndpoint, ByteArrayContent.fromString(null, objectMapper.writeValueAsString(s)));
            request.getHeaders().setContentType("application/json");
            request.getHeaders().setAuthorization("bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ3aWdvLmNvbSIsInN1YiI6ImY5MDg4NTRiLTkzZjUtNDhiYy05MjEzLTdhYmNiMTE2OWQ0OCIsImlhdCI6MTUzODU5MjQ4OSwiZXhwIjoxNTQxMTg0NDg5fQ.0uzlm9pHgTOSNZvvDBZEuM7crruRcLdTLV3ZJs_em9NlQbc2gQkA32cNu1Fk4qOt2dKDgQrwca5uMPbmXxFK_A");
            try {
                HttpResponse response = request.execute();
                UUID newId;
                try (final Reader reader = new InputStreamReader(response.getContent())) {
                    newId = UUID.fromString(CharStreams.toString(reader).replace("\"", ""));
                }
                if (newId != null) {
                    ValueRange value = new ValueRange();
                    value.setMajorDimension("ROWS");
                    value.setValues(Arrays.asList(Arrays.asList(newId.toString())));
                    values.update(spreadsheetId, "K" + (i + 2), value).setValueInputOption("USER_ENTERED").execute();
                    value.setValues(Arrays.asList(Arrays.asList("ADDED")));
                    values.update(spreadsheetId, "L" + (i + 2), value).setValueInputOption("USER_ENTERED").execute();
                }
            } catch (HttpResponseException e) {
                String error = "Status code:" + e.getStatusCode() + " Event " + s.getName() + " was not published, error:" + e.getContent();
                ValueRange value = new ValueRange();
                value.setMajorDimension("ROWS");
                value.setValues(Arrays.asList(Arrays.asList(error)));
                values.update(spreadsheetId, "L" + (i + 2), value).setValueInputOption("USER_ENTERED").execute();
                System.out.println(error);
            }
        }
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