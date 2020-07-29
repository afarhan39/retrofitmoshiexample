package my.fallacy.retrofitmoshiexample.retrievewp;

import com.squareup.moshi.Json;

import java.util.List;
import java.util.Map;

public class ResMission {

	@Json(name = "missionStatus")
	private int missionStatus;

	@Json(name = "id")
	private String id;

	@Json(name = "waypoints")
	private Map<String, List<String>> waypointMap;

	@Json(name = "updatedAt")
	private double updatedAt;

	public int getMissionStatus(){
		return missionStatus;
	}

	public String getId(){
		return id;
	}

	public Map<String, List<String>> getWaypointMap() {
		return waypointMap;
	}

	public double getUpdatedAt(){
		return updatedAt;
	}

	@Override
	public String toString() {
		StringBuilder map = new StringBuilder();
		for (String key : waypointMap.keySet()) {
			map.append(key).append("=").append(waypointMap.get(key));
		}

		return "ResMission{" +
				"missionStatus=" + missionStatus +
				", id='" + id + '\'' +
				", waypointMap=" + map.toString() +
				", updatedAt=" + updatedAt +
				'}';
	}
}