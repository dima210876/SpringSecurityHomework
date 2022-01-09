package my.studlabs.project.SpringSecurityHomework.service;

import my.studlabs.project.SpringSecurityHomework.model.User;
import my.studlabs.project.SpringSecurityHomework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements SimpleUserService, UserDetailsService
{
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User register(User user)
    {
        if (repository.findByEmail(user.getEmail()).isPresent())
            throw new IllegalStateException("Email is already taken");
        else
        {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return repository.save(user);
        }
    }

    public Optional<User> login(User user)
    {
        Optional <User> currentUser = repository.findByEmail(user.getEmail());
        if (currentUser.isPresent() &&
                bCryptPasswordEncoder.matches(user.getPassword(), currentUser.get().getPassword()))
            return currentUser;
        else return Optional.empty();
    }

    public Optional <User> getById(int id) { return repository.findById(id); }

    public List<User> getAll() { return repository.findAll(); }

    public User update(int id, User user)
    {
        User currentUser = getById(id).get();
        currentUser.setEmail(user.getEmail());
        currentUser.setFirstname(user.getFirstname());
        return currentUser;
    }

    public User changePassword(int id, String newPassword)
    {
        User currentUser = getById(id).get();
        currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return currentUser;
    }

    public void delete(int id)
    {
        Optional<User> user = getById(id);
        user.ifPresent(repository::delete);
        return;
    }

    public boolean isEmailUnique(String email)
    {
        return repository.findByEmail(email).isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }
}

