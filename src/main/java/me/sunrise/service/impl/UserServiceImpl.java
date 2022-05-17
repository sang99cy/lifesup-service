package me.sunrise.service.impl;

import me.sunrise.entity.Cart;
import me.sunrise.entity.UserEntity;
import me.sunrise.entity.UserEntity;
import me.sunrise.enums.ResultEnum;
import me.sunrise.exception.MyException;
import me.sunrise.repository.CartRepository;
import me.sunrise.repository.UserRepository;
import me.sunrise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    @Override
    public UserEntity findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Collection<UserEntity> findByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity UserEntity) {
        //register
        UserEntity.setPassword(passwordEncoder.encode(UserEntity.getPassword()));
        try {
            UserEntity savedUser = userRepository.save(UserEntity);

            // initial Cart
            Cart savedCart = cartRepository.save(new Cart(savedUser));
            savedUser.setCart(savedCart);
            return userRepository.save(savedUser);

        } catch (Exception e) {
            throw new MyException(ResultEnum.VALID_ERROR);
        }
    }

    @Override
    @Transactional
    public UserEntity update(UserEntity UserEntity) {
        UserEntity oldUser = userRepository.findByEmail(UserEntity.getEmail());
        oldUser.setPassword(passwordEncoder.encode(UserEntity.getPassword()));
        oldUser.setName(UserEntity.getName());
        oldUser.setPhone(UserEntity.getPhone());
        oldUser.setAddress(UserEntity.getAddress());
        return userRepository.save(oldUser);
    }

    @Override
    public void delete(Long id) {
        UserEntity UserEntity = findUserById(id);
        if (UserEntity == null) throw new MyException(ResultEnum.USER_NOT_EXIST);
        userRepository.delete(UserEntity);
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserEntity UserEntity) {
        UserEntity oldUser = userRepository.findById(UserEntity.getId());
        oldUser.setPassword(passwordEncoder.encode(UserEntity.getPassword()));
        oldUser.setName(UserEntity.getName());
        oldUser.setPhone(UserEntity.getPhone());
        oldUser.setAddress(UserEntity.getAddress());
        return userRepository.save(oldUser);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
