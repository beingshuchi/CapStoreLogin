package com.cg.service;

import java.util.List;

import com.cg.bean.Day;
import com.cg.dao.SportsRepo;

public interface SportsService {
Day save(Day d);
Day findByDayName(String name);
List<Day> findByGameName(String name);
}