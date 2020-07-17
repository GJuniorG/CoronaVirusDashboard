package de.java.application.Covid19_Tracker.Controllers;

import de.java.application.Covid19_Tracker.Database.GlobalEntity;
import de.java.application.Covid19_Tracker.Database.GlobalEntityRepository;
import de.java.application.Covid19_Tracker.Services.VirusDataExtractOnLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    VirusDataExtractOnLoadService virusDataExtractOnLoadService;
    @Autowired
    GlobalEntityRepository globalEntityRepository;

    @GetMapping("/")
    public String home(Model model) {

        List<GlobalEntity> dbValue = globalEntityRepository.findAll();

        int totalReportedCases = dbValue.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = dbValue.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

        model.addAttribute("locationStats", dbValue);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);


        return "home";
    }
}
