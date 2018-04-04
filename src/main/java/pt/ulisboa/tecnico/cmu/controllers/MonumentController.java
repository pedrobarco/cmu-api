package pt.ulisboa.tecnico.cmu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.cmu.models.Monument;
import pt.ulisboa.tecnico.cmu.repositories.MonumentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monuments")
public class MonumentController {

    @Autowired
    private MonumentRepository monumentRepository;

    @GetMapping
    public Optional<Monument> getBySsid(@RequestParam("ssid") String ssid) {
        return monumentRepository.findBySsid(ssid);
    }

    @PostMapping
    public Monument create(@RequestBody Monument monument){
        monumentRepository.save(monument);
        return monument;
    }

    @PutMapping
    public Monument update(@RequestBody Monument monument){
        monumentRepository.save(monument);
        return monument;
    }

    @GetMapping("/all")
    public List<Monument> getAll() {
        return monumentRepository.findAll();
    }

    @DeleteMapping("/all")
    public String deleteAll(){
        monumentRepository.deleteAll();
        return "All monuments deleted successfully!";
    }

    @GetMapping("/{id}")
    public Optional<Monument> getById(@PathVariable("id") String id){
        return monumentRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") String id){
        monumentRepository.deleteById(id);
        return "Monument deleted successfully!";
    }
}
