package dev.aarow.regions.utility.general;

import dev.aarow.regions.Regions;
import dev.aarow.regions.data.region.flag.RegionFlag;
import dev.aarow.regions.data.region.flag.RegionFlagState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneralSerializer {

    public static String serializeUUIDs(List<UUID> uuids){
        String output = "";

        for(UUID uuid : uuids){
            output = output + uuid.toString() + ";";

        }

        return output;
    }

    public static String serializeFlagStates(Map<RegionFlag, RegionFlagState> regionFlagStateMap){
        String output = "";

        for(RegionFlag regionFlag : regionFlagStateMap.keySet()){
            RegionFlagState regionFlagState = regionFlagStateMap.get(regionFlag);

            output = output + regionFlag.getName() + "@" + regionFlagState.name() + ";";
        }

        return output;
    }


    public static List<UUID> deserializeUUIDs(String input){
        String[] args = input.split(";");

        return Stream.of(args).filter(string -> !string.isEmpty()).map(UUID::fromString).collect(Collectors.toList());
    }

    public static Map<RegionFlag, RegionFlagState> deserializeFlags(String input){
        Map<RegionFlag, RegionFlagState> regionFlagStateMap = new HashMap<>();

        String[] regionFlags = input.split(";");

        for(String attribute : regionFlags){
            if(attribute.isEmpty()) continue;

            RegionFlag regionFlag = Regions.getInstance().getRegionManager().getFlagByName(attribute.split("@")[0]);

            if(regionFlag == null) continue;

            RegionFlagState regionFlagState = Regions.getInstance().getRegionManager().getFlagStateByName(attribute.split("@")[1]);

            regionFlagStateMap.put(regionFlag, regionFlagState);
        }

        return regionFlagStateMap;
    }
}
