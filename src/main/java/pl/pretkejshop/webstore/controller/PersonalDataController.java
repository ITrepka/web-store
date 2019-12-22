package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.PersonalDataDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.PersonalDataService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personal-data")
public class PersonalDataController {
    @Autowired
    private PersonalDataService personalDataService;

    @GetMapping
    public List<PersonalDataDto> getAllPersonalData() {
        return personalDataService.getAllPersonalData();
    }

    @GetMapping("/{id}")
    public PersonalDataDto getPersonalDataById(@PathVariable int id) throws NotFoundException {
        return personalDataService.getPersonalDataById(id);
    }

//    @PostMapping
//    public PersonalDataDto addPersonalData(@RequestBody CreateUpdatePersonalDataDto createPersonalDataDto) throws PersonalDataInvalidDataException {
//        return personalDataService.addPersonalData(createPersonalDataDto);
//    }
//
//    @PutMapping("/{id}")
//    public PersonalDataDto updatePersonalDataById(@PathVariable int id, @RequestBody CreateUpdatePersonalDataDto updatePersonalDataDto) throws PersonalDataNotFoundException, PersonalDataInvalidDataException {
//        return personalDataService.updatePersonalData(id, updatePersonalDataDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public PersonalDataDto deletePersonalDataById(@PathVariable int id) throws PersonalDataNotFoundException {
//        return personalDataService.deletePersonalData(id);
//    }
}
