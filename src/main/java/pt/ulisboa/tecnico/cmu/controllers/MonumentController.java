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

    private final MonumentRepository monumentRepository;

    @Autowired
    public MonumentController(MonumentRepository monumentRepository) {
        this.monumentRepository = monumentRepository;
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

    @GetMapping("/{ssid}")
    public Optional<Monument> getById(@PathVariable("ssid") String ssid){
        Optional<Monument> monument = monumentRepository.findById(ssid);
        monument.ifPresent(m ->
            m.getQuiz().setSolution(null)
        );
        return monument;
    }

    @DeleteMapping("/{ssid}")
    public String deleteById(@PathVariable("ssid") String ssid){
        monumentRepository.deleteById(ssid);
        return "Monument deleted successfully!";
    }
}
