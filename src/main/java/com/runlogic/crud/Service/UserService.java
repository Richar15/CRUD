package com.runlogic.crud.Service;


import com.runlogic.crud.dto.UserDTO;
import com.runlogic.crud.entity.User;
import com.runlogic.crud.mappers.UserMapper;
import com.runlogic.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> findAll() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::toDTO).orElse(null);
    }

    public List<UserDTO> findByName(String name) {
        return userMapper.toDTOList(userRepository.findByNameContaining(name));
    }

    @Transactional
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setId(null); // Ensure it's a new entity
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            return null;
        }

        userDTO.setId(id);
        User user = userMapper.toEntity(userDTO);
        User updated = userRepository.save(user);
        return userMapper.toDTO(updated);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
