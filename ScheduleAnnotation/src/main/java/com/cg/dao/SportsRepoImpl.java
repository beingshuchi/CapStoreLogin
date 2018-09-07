package com.cg.dao;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cg.bean.Day;
import com.cg.bean.Games;
@Repository
public class SportsRepoImpl implements SportsRepo{
	@Resource(name="maps")
	private Map<String,Day> data;
	

	public SportsRepoImpl(Map<String, Day> data) {
		super();
		this.data = data;
	}

	@Override
	public Day save(Day d) {
	// TODO Auto-generated method stub
	data.put(d.getName(), d);
	 
	return d;
	}

	@Override
	public Day findByDayName(String name) {
	// TODO Auto-generated method stub
	 
	Day d= data.get(name);
	return d;
	 
	}

	//for loop 1- to get the values of the day
	//for loop 2- to get the values of the game
	//if the name equals the given name then add the name in the list
	@Override
	public List<Day> findByGameName(String name) {
	// TODO Auto-generated method stub
	List<Day> l= new ArrayList<Day>();
	 
	for(Day day:data.values()) {
	for(Games game:day.getGames()) {
	 
	if(name.equals(game.getName())) {
	l.add(day);
	}
	 
	}
	}
	 
	return l;
	}


}
