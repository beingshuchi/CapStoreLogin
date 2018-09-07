package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bean.Day;
import com.cg.dao.SportsRepo;

@Service(value="service")
public class SportsServiceImpl implements SportsService {
@Autowired
private SportsRepo repo;
 



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