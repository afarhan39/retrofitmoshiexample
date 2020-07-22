package my.fallacy.retrofitmoshiexample.userdata;

import com.squareup.moshi.Json;

public class UserData{

	@Json(name = "profession")
	private String profession;

	@Json(name = "name")
	private String name;

	@Json(name = "age")
	private int age;

	public String getProfession(){
		return profession;
	}

	public String getName(){
		return name;
	}

	public int getAge(){
		return age;
	}

	@Override
 	public String toString(){
		return 
			"UserData{" + 
			"profession = '" + profession + '\'' + 
			",name = '" + name + '\'' + 
			",age = '" + age + '\'' + 
			"}";
		}
}