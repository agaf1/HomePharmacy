package pl.aga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aga.service.domain.Home;
import pl.aga.service.service.HomeService;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;


    @PostMapping(path = "/homes")
    public void createHome(@NonNull String familyName) {
        homeService.createHome(new Home(familyName));
    }

}
