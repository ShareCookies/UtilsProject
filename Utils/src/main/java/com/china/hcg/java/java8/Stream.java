package com.china.hcg.java.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @autor hecaigui
 * @date 2021-5-11
 * @description
 */
public class Stream {
	public static void main(String[] args) {
		List<User> userList = new ArrayList<>();
		userList.add(new User("1","a"));
		userList.add(new User("2","b"));
		userList.stream().collect(Collectors.toMap(User::getId, User::getName));
	}
	static class User {
		private String id;
		private String name;

		public User(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
