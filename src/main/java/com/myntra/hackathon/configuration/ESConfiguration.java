package com.myntra.hackathon.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESConfiguration {

    private List<String> hosts;
    private List<Integer> ports;
    private List<String> schemes;
}
