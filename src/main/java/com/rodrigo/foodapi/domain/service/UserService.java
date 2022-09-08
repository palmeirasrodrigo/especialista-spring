package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.BusinessException;
import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.UserNotFoundException;
import com.rodrigo.foodapi.domain.model.Group;
import com.rodrigo.foodapi.domain.model.User;
import com.rodrigo.foodapi.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public static final String MSG_USER_IN_USE = "Usuário de código %d não pode ser removida, pois está em uso ";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User find(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)
        );
    }

    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = find(userId);

        if (user.validPassword(currentPassword)) {
            throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
        }

        user.setPassword(newPassword);
    }

    @Transactional
    public User create(User user) {
        userRepository.detach(user);
        Optional<User> existUser = userRepository.findByEmail(user.getEmail());
        if (existUser.isPresent() && !existUser.get().equals(user)) {
            throw new BusinessException(
                    String.format("Já existe um usuário cadastrado com o e-mail %", user.getEmail())
            );
        }

        return userRepository.save(user);
    }

    @Transactional
    public void remove(Long userId) {
        try {
            userRepository.deleteById(userId);
            userRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_USER_IN_USE, userId)
            );
        }
    }

    @Transactional
    public void associateGroup(Long userId, Long groupId) {
        User user = find(userId);
        Group group = groupService.find(groupId);

        user.addGroup(group);
    }

    @Transactional
    public void disassociateGroup(Long userId, Long groupId) {
        User user = find(userId);
        Group group = groupService.find(groupId);

        user.removeGroup(group);
    }
}
