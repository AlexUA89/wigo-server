import apis.MeetUpApiLoaders;
import dome.SearchParameters;
import dome.Status;
import sheet.SheetUploader;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class LoaderFromApis {

    private static final String PARAMETERS_FILE = "parameters.txt";


    public static void main(String... args) throws IOException, GeneralSecurityException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        File file = new File("err.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);

        //Loading data from api
        List<Status> importResult = triggerLoadProcess(getSearchParameters());

        //Upload these data to google sheet
        new SheetUploader().sendDataToServerAndUpdateSheet(importResult);
    }

    private static List<SearchParameters> getSearchParameters() throws IOException {
        List<SearchParameters> result = new ArrayList<>();
        try (BufferedReader file = new BufferedReader(new FileReader(new File(PARAMETERS_FILE)))) {
            String row = file.readLine();
            while ((row = file.readLine()) != null) {
                String[] args = row.replace(" ", "").split(";");
                result.add(new SearchParameters(
                        Double.parseDouble(args[0]),
                        Double.parseDouble(args[1]),
                        Double.parseDouble(args[2]),
                        args[3]
                ));
            }
        }
        return result;
    }

    private static List<Status> triggerLoadProcess(List<SearchParameters> listOfParams) {
        List<Status> importResult = new ArrayList<>();
        for (SearchParameters params : listOfParams) {
            importResult.addAll(new MeetUpApiLoaders().loadStatuses(params));
            //TODO new loaders should be putted here
        }
        return importResult;
    }

}