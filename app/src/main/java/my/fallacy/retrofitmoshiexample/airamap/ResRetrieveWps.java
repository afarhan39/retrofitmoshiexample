package my.fallacy.retrofitmoshiexample.airamap;

import com.squareup.moshi.Json;

import java.util.Map;

public class ResRetrieveWps {

	@Json(name = "mission")
	private Map<String, ResMission> resMissionSortedMap;

	@Json(name = "message")
	private String message;

	@Json(name = "status")
	private int status;

	public Map<String, ResMission> getResMissionSortedMap() {
		return resMissionSortedMap;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}

	@Override
	public String toString() {
		StringBuilder map = new StringBuilder();
		for (String key : resMissionSortedMap.keySet()) {
			map.append(key).append("=").append(resMissionSortedMap.get(key));
		}

		return "ResRetrieveWps{" +
				"resMissionSortedMap=" + map.toString() +
				", message='" + message + '\'' +
				", status=" + status +
				'}';
	}
}