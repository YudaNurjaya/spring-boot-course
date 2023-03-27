package com.example.demospringboot.service;
import com.example.demospringboot.exception.DataEmptyException;
import com.example.demospringboot.exception.FailedToRunException;
import com.example.demospringboot.model.User;
import com.example.demospringboot.repository.IUserRepository;
import com.example.demospringboot.utils.validation.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    JwtUtil jwtUtil;
    public User save(User user){
        try {
            return userRepository.save(user);
        }catch (FailedToRunException e){
            throw new FailedToRunException();
        }
    }
    public Iterable<User> findAll(Pageable pageable){
        try {
            return userRepository.findAll(pageable);
        }catch (DataEmptyException e){
            throw new DataEmptyException();
        }
    }
    public String login(User user){
        try{
            User getUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            String token = jwtUtil.generateToken("enigma");
            if(user.getEmail().equals(getUser.getEmail()) && user.getPassword().equals(getUser.getPassword())){
                return token;
            }else{
                throw new RuntimeException("Email or password incorrect");
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
}
