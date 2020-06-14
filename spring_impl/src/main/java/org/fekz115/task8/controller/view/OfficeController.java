package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.Office;
import org.fekz115.task8.service.CityService;
import org.fekz115.task8.service.OfficeService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/office")
public class OfficeController {

    private final OfficeService officeService;
    private final CityService cityService;

    public OfficeController(OfficeService officeService, CityService cityService) {
        this.officeService = officeService;
        this.cityService = cityService;
    }

    @GetMapping
    public String all(Model model) {
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("page", "offices");
        return "common_page";
    }

    @GetMapping({"/edit/{id}", "/create"})
    public String createGet(@PathVariable Optional<Integer> id, Model model) {
        Office office;
        if(id.isEmpty()) {
            office = new Office();
        } else {
            office = officeService.getOfficeById(id.get()).orElse(new Office());
        }
        model.addAttribute("office", office);
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("page", "create_office");
        return "common_page";
    }

    @PostMapping
    public String createPost(Office office, int cityId, Model model) {
        try {
            office.setCity(cityService.getCityById(cityId).get());
            officeService.save(office);
            model.addAttribute("Office in " + office.getCity() + " with address " + office.getAddress() + " successfully created");
        } catch (ServiceException e) {
            model.addAttribute("exception", e.getMessage());
        }
        return all(model);
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, Model model){
        try {
            Office office = new Office();
            office.setId(id);
            officeService.remove(office);
            model.addAttribute("message", "Successfully removed");
        } catch (ServiceException e) {
            model.addAttribute("exception", e);
        }
        return all(model);
    }

}
