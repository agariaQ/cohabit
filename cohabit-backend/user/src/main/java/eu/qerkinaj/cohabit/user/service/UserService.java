package eu.qerkinaj.cohabit.user.service;

import eu.qerkinaj.cohabit.user.domain.User;
import eu.qerkinaj.cohabit.user.dto.UserDTO;
import eu.qerkinaj.cohabit.user.mapper.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserMapper userMapper;

    public UserDTO getUser(UUID uuid) {
        return userMapper.toDto(User.findById(uuid));
    }

    public String getUserEmail(UUID uuid) {
        return User.findById(uuid).email;
    }
}
