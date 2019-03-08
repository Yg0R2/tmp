package com.yg0r2.factorio.machine.service;

import com.yg0r2.factorio.machine.domain.Machine;
import com.yg0r2.factorio.machine.domain.MachineType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MachineService {

    @Autowired
    private Map<String, Machine> machinesMap;

    public Machine getByName(String name) {
        return machinesMap.get(name);
    }

    public List<Machine> getByMachineType(MachineType machineType) {
        return machinesMap.values().stream()
            .filter(machine -> machine.getMachineType() == machineType)
            .collect(Collectors.toList());
    }

}
