package pt.ulisboa.tecnico.cmu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.cmu.models.Code;
import pt.ulisboa.tecnico.cmu.models.User;
import pt.ulisboa.tecnico.cmu.repositories.CodeRepository;
import pt.ulisboa.tecnico.cmu.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeRepository codeRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody User user){
        Optional<Code> code = codeRepository.findById(user.getCode().getSecret());
        ResponseEntity err = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        ResponseEntity success = ResponseEntity.ok(user);
        if (code.isPresent() && !code.get().isInUse()) {
            Code c = code.get();
            c.setInUse(true);
            user.setCode(c);
            codeRepository.save(c);
            userRepository.save(user);
            return success;
        } else {
            return  err;
        }
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @DeleteMapping("/all")
    public String deleteAll(){
        userRepository.deleteAll();
        return "All users deleted successfully!";
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id") String id){
        return userRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") String id){
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }
}
