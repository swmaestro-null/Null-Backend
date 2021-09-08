package swmaestronull.nullbackend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swmaestronull.nullbackend.domain.user.User;
import swmaestronull.nullbackend.domain.user.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneWithRoleByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 사용자를 찾을 수 없습니다."));
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화 되어있지 않습니다.");
        }
        return user;
    }
}
