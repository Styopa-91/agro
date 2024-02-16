package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.RegisteringRequest;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.RegisteringRequestRepository;
import com.abi.agro_back.service.RegisteringRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteringRequestServiceImpl implements RegisteringRequestService {

    @Autowired
    private RegisteringRequestRepository registeringRequestRepository;

    @Override
    public RegisteringRequest createRegisteringRequest(RegisteringRequest registeringRequest) {

        return registeringRequestRepository.save(registeringRequest);
    }

    @Override
    public RegisteringRequest getRegisteringRequestById(String registeringRequestId) {

        return registeringRequestRepository.findById(registeringRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RegisteringRequest is not exists with given id : " + registeringRequestId));
    }

    @Override
    public List<RegisteringRequest> getAllRegisteringRequests() {

        return registeringRequestRepository.findAll();
    }

    @Override
    public RegisteringRequest updateRegisteringRequest(String registeringRequestId, RegisteringRequest updatedRegisteringRequest) {

        RegisteringRequest registeringRequest = registeringRequestRepository.findById(registeringRequestId).orElseThrow(
                () -> new ResourceNotFoundException("RegisteringRequest is not exists with given id: " + registeringRequestId)
        );

        registeringRequest.setFirstName(updatedRegisteringRequest.getFirstName());
        registeringRequest.setLastName(updatedRegisteringRequest.getLastName());
        registeringRequest.setPhone(updatedRegisteringRequest.getPhone());
        registeringRequest.setEmail(updatedRegisteringRequest.getEmail());

        return registeringRequestRepository.save(registeringRequest);
    }

    @Override
    public void deleteRegisteringRequest(String registeringRequestId) {

       RegisteringRequest registeringRequest = registeringRequestRepository.findById(registeringRequestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RegisteringRequest is not exists with given id : " + registeringRequestId));

       registeringRequestRepository.deleteById(registeringRequestId);
    }
}
