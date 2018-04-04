package pt.ulisboa.tecnico.cmu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.cmu.models.Code;
import pt.ulisboa.tecnico.cmu.repositories.CodeRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/codes")
public class CodeController {

    @Autowired
    private CodeRepository codeRepository;

    @PostMapping
    public Code create(@RequestBody Code code){
        codeRepository.save(code);
        return code;
    }

    @GetMapping("/all")
    public List<Code> getAll() {
        return codeRepository.findAll();
    }

    @DeleteMapping("/all")
    public String deleteAll(){
        codeRepository.deleteAll();
        return "All codes deleted successfully!";
    }

    @GetMapping("/{id}")
    public Optional<Code> getById(@PathVariable("id") String id){
        return codeRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") String id){
        codeRepository.deleteById(id);
        return "Code deleted successfully!";
    }
}
