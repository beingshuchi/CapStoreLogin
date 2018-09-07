package com.cg.dao;

import java.util.List;


import com.cg.bean.Day;

public interface SportsRepo {
Day save(Day d);
Day findByDayName(String name);
List<Day> findByGameName(String name);

}