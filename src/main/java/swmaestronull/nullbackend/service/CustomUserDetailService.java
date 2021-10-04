package swmaestronull.nullbackend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swmaestronull.nullbackend.domain.user.PaintUser;
import swmaestronull.nullbackend.domain.user.PaintUserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final PaintUserRepository paintUserRepository;

    public CustomUserDetailService(PaintUserRepository paintUserRepository) {
        this.paintUserRepository = paintUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PaintUser paintUser = paintUserRepository.findOneWithRoleByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 사용자를 찾을 수 없습니다."));
        if (!paintUser.isActivated()) {
            throw new RuntimeException(username + " -> 활성화 되어있지 않습니다.");
        }
        return paintUser;
    }
}
