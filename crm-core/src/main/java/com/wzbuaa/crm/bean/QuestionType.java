package com.wzbuaa.crm.bean;

public enum QuestionType {
	YourBirthPlace("你的出生地方"),
	WhatIsYourFavoriteSong("你最喜欢的歌"),
	WhatIsYourFavoriteBook("你最喜欢的书"),
	YouAreTheMostUnforgettablePerson("你最难忘的人"),
	YouAreTheMostMemorableDay("你最难忘的日子"),
	TheNameOfYourSpouse("你配偶的名字"),
	YourStudentId("你的学号"),
	YourBirthday("你的生日"),
	WhatIsReallyWantTo("最想重来的是什么");
	private String name;

	private QuestionType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
