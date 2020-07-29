package my.fallacy.retrofitmoshiexample.auth;

import com.squareup.moshi.Json;

public class ResAuth{

	@Json(name = "Auth")
	private String auth;

	@Json(name = "message")
	private String message;

	@Json(name = "status")
	private int status;

	public String getAuth(){
		return auth;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "ResAuth{" +
				"auth='" + auth + '\'' +
				", message='" + message + '\'' +
				", status=" + status +
				'}';
	}
}