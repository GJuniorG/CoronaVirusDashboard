package de.java.application.Covid19_Tracker.Services;


import de.java.application.Covid19_Tracker.Database.GlobalEntity;
import de.java.application.Covid19_Tracker.Database.GlobalEntityRepository;
import de.java.application.Covid19_Tracker.Models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class VirusDataExtractOnLoadService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String VIRUS_TOTAL_DEATHS = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static String VIRUS_TOTAL_RECOVERED = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";


    @Autowired
    GlobalEntityRepository globalEntityRepository;

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request_total = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse_total = client.send(request_total, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader_total = new StringReader(httpResponse_total.body());
        Iterable<CSVRecord> records_total = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader_total);

        HttpRequest request_deaths = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_TOTAL_DEATHS))
                .build();

        HttpResponse<String> httpResponse_death = client.send(request_deaths, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader_death = new StringReader(httpResponse_death.body());
        Iterable<CSVRecord> records_death = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader_death);

        HttpRequest request_recovered = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_TOTAL_RECOVERED))
                .build();

        HttpResponse<String> httpResponse_recovered = client.send(request_recovered, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader_recovered = new StringReader(httpResponse_recovered.body());
        Iterable<CSVRecord> records_recovered = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader_recovered);

        for (CSVRecord record : records_total) {
            GlobalEntity locationStat = new GlobalEntity();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);

            globalEntityRepository.save(locationStat);
        }
        for (CSVRecord record : records_death) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            int latestDeaths = Integer.parseInt(record.get(record.size() - 1));
            int prevDayDeaths = Integer.parseInt(record.get(record.size() - 2));
            int diffFromPreviousDay = latestDeaths - prevDayDeaths;

            if (!country.isEmpty()) {
                GlobalEntity gb = globalEntityRepository.findByCountryAndState(country, state);
                if (gb != null) {
                    gb.setTotalDeaths(latestDeaths);
                    gb.setDeathFromPreviousDay(diffFromPreviousDay);
                    globalEntityRepository.save(gb);
                }
            }
        }

        for (CSVRecord record : records_recovered) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            int latestRecovered = Integer.parseInt(record.get(record.size() - 1));
            int prevDayRecovery = Integer.parseInt(record.get(record.size() - 2));
            int diffFromPreviousDay = latestRecovered - prevDayRecovery;

            if (!country.isEmpty()) {
                GlobalEntity gb = globalEntityRepository.findByCountryAndState(country, state);
                if (gb != null) {
                    gb.setTotalRecovered(latestRecovered);
                    gb.setRecoveredFromPreviousDay(diffFromPreviousDay);
                    globalEntityRepository.save(gb);
                }
            }
        }
    }
}
