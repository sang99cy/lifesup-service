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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    public final static String url = System.getProperty("user.dir");
    private final String UPLOAD_CONFIG = "upload-config.properties";

    @Override
    public UserEntity findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new MyException(ResultEnum.PRODUCT_NOT_EXIST));
        return user;
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
    public UserEntity update(UserEntity user) {
        UserEntity oldUser = userRepository.findByEmail(user.getEmail());
        //oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        oldUser.setAddress(user.getAddress());
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
        UserEntity oldUser = userRepository.findById(UserEntity.getId())
                .orElseThrow(() -> new MyException(ResultEnum.USER_NOT_EXIST));
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

    @Override
    public UserEntity updatePassword(Long userId, String newPassword, String oldPassword) {
        UserEntity userNew;
        UserEntity oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new MyException(ResultEnum.USER_NOT_EXIST));
        if (passwordEncoder.matches(oldPassword, oldUser.getPassword())) {
            System.out.println("true");
            oldUser.setPassword(passwordEncoder.encode(newPassword));
            userNew = userRepository.save(oldUser);
            return userNew;
        } else {
            System.out.println("false");
        }
        return null;
    }

    @Override
    public UserEntity updaLoadAvatar(MultipartFile image, Long userId) {
        UserEntity response = null;
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(UPLOAD_CONFIG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //String pathFile = properties.getProperty("fileImage");
        String pathFile = properties.getProperty("uploadServer");
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new MyException(ResultEnum.USER_NOT_EXIST));
        if (user != null) {
            // upload anh
            String fileName = image.getOriginalFilename();
            File mkDir = new File(pathFile);
            if (!mkDir.exists()) {
                mkDir.mkdirs();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File newFile = new File(pathFile, fileName);
                inputStream = image.getInputStream();
                outputStream = new FileOutputStream(newFile);

                int read;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes, 0, 1024)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // save ten anh
            user.setAvatar(fileName);
            response = userRepository.save(user);
            return response;
        }
        return null;
    }

}
