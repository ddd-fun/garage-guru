package org.home.gg.domain.garage;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AvailableLots {

    private final Map<VehicleSpec, Long> groupCounter;
    private final Long totalLots;

    public AvailableLots(Map<VehicleSpec, Long> groups) {
        this.groupCounter = new HashMap(groups);
        this.totalLots = this.groupCounter.values().stream().mapToLong(Long::new).sum();
    }

    public boolean isEmpty(){
      return this.groupCounter.isEmpty();
    }


    public long totalLots(){
      return totalLots;
    }

    public Map<VehicleSpec, Long> getGrouped(){
      return Collections.unmodifiableMap(this.groupCounter);
    }


}
