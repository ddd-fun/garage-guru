package org.home.gg.domain.garage;

import org.home.gg.domain.common.Reject;

import java.util.HashMap;
import java.util.Map;


public class NumberOfFreeLots {

    private final Map<String, Long> groupCounter;
    private final Long totalLots;

    public NumberOfFreeLots(Map<String, Long> groups) {
        Reject.ifNull(groups);
        this.groupCounter = new HashMap(groups);
        this.totalLots = this.groupCounter.values().stream().mapToLong(Long::new).sum();
    }

    public boolean isNoLots(){
      return this.groupCounter.isEmpty();
    }


    public long totalLots(){
      return totalLots;
    }


    @Override
    public String toString() {
       return String.format("Total available parking lots - %s. %s", this.totalLots, this.groupCounter);
    }
}
