package com.cg.service;

import java.util.List;

import com.cg.bean.Day;
import com.cg.dao.SportsRepo;

public class SportsServiceImpl implements SportsService {

private SportsRepo repo;
 

@Override
public void setRepo(SportsRepo repo) {
	this.repo = repo;
}

@Override
public Day save(Day d) {
// TODO Auto-generated method stub

return repo.save(d);
}
 
public SportsServiceImpl() {
 
}

@Override
public Day findByDayName(String name) {
// TODO Auto-generated method stub
return repo.findByDayName(name);
}

@Override
public List<Day> findByGameName(String name) {
// TODO Auto-generated method stub
return repo.findByGameName(name);
}

}