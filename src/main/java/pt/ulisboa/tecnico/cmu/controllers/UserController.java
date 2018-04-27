package pt.ulisboa.tecnico.cmu.controllers;

import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.cmu.models.Code;
import pt.ulisboa.tecnico.cmu.models.User;
import pt.ulisboa.tecnico.cmu.repositories.CodeRepository;
import pt.ulisboa.tecnico.cmu.repositories.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;

    private final CodeRepository codeRepository;

    @Autowired
    public UserController(UserRepository userRepository, CodeRepository codeRepository) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user){
        StringBuilder msg = new StringBuilder();
        ResponseEntity err = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        ResponseEntity success = ResponseEntity.ok(user);
        ResponseEntity response;
        Optional<Code> code = codeRepository.findBySecret(user.getPassword());
        Optional<User> anotherUser = userRepository.findByUsername(user.getUsername());
        if(anotherUser.isPresent()) {
            msg.append("Username already exists.");
            response = err;
        } else if(code.isPresent() && code.get().getUserId() != null) {
            Code c = code.get();
            User newUser = userRepository.save(user);
            c.setUserId(newUser.getId());
            codeRepository.save(c);
            response = success;
        } else {
            msg.append("Invalid password.");
            response = err;
        }
        return response;
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
