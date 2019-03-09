package com.yg0r2.factorio.machine.service;

import com.yg0r2.factorio.machine.domain.Machine;
import com.yg0r2.factorio.machine.service.exceptions.MachineNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MachineService {

    @Autowired
    private Map<String, Machine> machinesMap;

    public Machine getByName(String machineName) {
        return Optional.of(machineName)
            .map(machinesMap::get)
            .orElseThrow(() -> new MachineNotFoundException("Machine doesn't exist with name: " + machineName));
    }

}
