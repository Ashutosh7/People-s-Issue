package com.peoplesissues.activities;

public class Listdata {
	public String imgUrl,title,subtext,location;
	public int upvotes,downvotes;
	public Listdata(String _title,String _subtext){
		this.title = _title;
		this.subtext = _subtext;
		upvotes = downvotes=0;
		imgUrl = "";
	}
	public Listdata(String _title,String _subtext, String _location, int _upvotes, int _downvotes, String _img){
		this.title = _title;
		this.subtext = _subtext;
		this.upvotes = _upvotes;
		this.location = _location;
		this.downvotes = _downvotes;
		imgUrl = _img;
	}
	public Listdata(){};
}
