package my.fallacy.retrofitmoshiexample.userdata;

import com.squareup.moshi.Json;

public class Res{

	@Json(name = "userData")
	private UserData userData;

	@Json(name = "status")
	private int status;

	public UserData getUserData(){
		return userData;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Res{" + 
			"userData = '" + userData + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}