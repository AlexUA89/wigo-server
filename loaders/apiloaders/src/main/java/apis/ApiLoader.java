package apis;

import dome.SearchParameters;
import dome.Status;

import java.util.List;

public abstract class ApiLoader {

    protected abstract List<Status> loadFromApi(SearchParameters parameters);

    protected abstract String getApiName();

    public List<Status> loadStatuses(SearchParameters parameters) {
        List<Status> result = loadFromApi(parameters);
        result.forEach(s -> s.setLocationDescription(parameters.getDescRiption()));
        System.out.println("Statuses loaded " + result.size() + " statuses from " + getApiName() + " for location " + parameters.getDescRiption());
        return result;
    }

}
