package com.uk.spring_security_phase1;

import com.uk.spring_security_phase1.models.MyUserDetails;
import com.uk.spring_security_phase1.models.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    public final UserRepository userRepository;

    public MyUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntityIn = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UserEntity not found"));
        return new MyUserDetails(userEntityIn);
    }
}
