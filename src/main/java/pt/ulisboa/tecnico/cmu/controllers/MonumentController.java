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


    @GetMapping
    public Optional<Monument> getBySsid(@RequestParam String ssid){
        Optional<Monument> monument = monumentRepository.findBySsid(ssid);
        monument.ifPresent(m ->
                m.getQuiz().setSolution(null)
        );
        return monument;
    }

    @PostMapping
    public Monument create(@RequestBody Monument monument){
        return monumentRepository.save(monument);
    }

    @PutMapping
    public Monument update(@RequestBody Monument monument){
        return monumentRepository.save(monument);
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
