package apis;

import dome.SearchParameters;
import dome.Status;

import java.util.ArrayList;
import java.util.List;

public class MeetUpApiLoaders extends ApiLoader {

    @Override
    protected String getApiName() {
        return "MeetUp";
    }

    @Override
    protected List<Status> loadFromApi(SearchParameters parameters) {
        // TODO load statuses from API
        return new ArrayList<>();
    }
}
