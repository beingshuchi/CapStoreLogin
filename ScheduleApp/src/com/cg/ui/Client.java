package com.cg.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.bean.Day;
import com.cg.bean.Games;
import com.cg.dao.SportsRepo;
import com.cg.dao.SportsRepoImpl;
import com.cg.service.SportsService;
import com.cg.service.SportsServiceImpl;

public class Client {
 

public static void main(String[] args) {
// TODO Auto-generated method stub
 
 
Map<String,Day> data=new HashMap<String,Day>();
SportsRepo repo=new SportsRepoImpl(data);
SportsService service = new SportsServiceImpl();
service.setRepo(repo);
Day d1= new Day();
List<Games> l1= new ArrayList<Games>();
d1.setName("Day 1");
Games g1= new Games();
g1.setName("Gymnastic");
l1.add(g1);
Games g2= new Games();
g2.setName("FootBall");
l1.add(g2);
Games g3= new Games();
g3.setName("Kabadi");
l1.add(g3);
d1.setGames(l1);
Day day1=service.save(d1);
System.out.println("Data with "+day1.getName()+" has been added successfully");
Day d2= new Day();
List<Games> l2= new ArrayList<Games>();
d2.setName("Day 2");
Games g4= new Games();
g4.setName("Khokho");
l2.add(g4);
Games g5= new Games();
g5.setName("Tennis");
l2.add(g5);
l2.add(g1);
d2.setGames(l2);
Day day2=service.save(d2);
System.out.println("Data with "+day2.getName()+" has been added successfully");
Day d3= new Day();
List<Games> l3= new ArrayList<Games>();
d3.setName("Day 3");
l3.add(g2);
l3.add(g3);
Games g6= new Games();
g6.setName("Cricket");
l3.add(g6);
d3.setGames(l3);
Day day3=service.save(d3);
System.out.println("Data with "+day3.getName()+" has been added successfully");
Day d4= new Day();
List<Games> l4= new ArrayList<Games>();
d4.setName("Day 4");
l4.add(g4);
l4.add(g5);
l4.add(g1);
d4.setGames(l4);
Day day4=service.save(d4);
System.out.println("Data with "+day4.getName()+" has been added successfully");
Day d5= new Day();
List<Games> l5= new ArrayList<Games>();
d5.setName("Day 5");
l5.add(g4);
l5.add(g6);
l5.add(g5);
d5.setGames(l5);
Day day5=service.save(d5);
System.out.println("Data with "+day5.getName()+" has been added successfully");
System.out.println("================================================================");
 
String dname="Day 4";
Day d=service.findByDayName(dname);
for (Games l : d.getGames()) {
System.out.println(l.getName());
}
System.out.println("================================================================");

String gname="Tennis";
List<Day>l=service.findByGameName(gname);
for (Day day : l) {
System.out.println(day.getName());
}
 
/*Client c= new Client();
String option="";
Scanner sc= new Scanner(System.in);
while(true) {
System.out.println("1. Save data");
System.out.println("2. Retrieve Game information");
System.out.println("3. Retrive Day Information");
System.out.println("4. Exit");
System.out.println();
System.out.println("Enter your choice: ");
option=sc.nextLine();
switch (option) {
case "1":
c.save();
break;
case "2":
c.findByDayName();
break;
case "3":
c.findByGameName();
break;
case "4":
sc.close();
System.exit(0);
default:
System.err.println("Enter between options 1 and 4");
}
}*/

}

/*private void save() {
Map<String,Day> data=new HashMap<String,Day>();
SportsRepo repo=new SportsRepoImpl();
SportsService service = new SportsServiceImpl(data, repo);
Day d1= new Day();
List<Games> l1= new ArrayList<Games>();
d1.setName("Day 1");
Games g1= new Games();
g1.setName("Gymnastic");
l1.add(g1);
Games g2= new Games();
g2.setName("FootBall");
l1.add(g2);
Games g3= new Games();
g3.setName("Kabadi");
l1.add(g3);
d1.setGame(l1);
Day day1=service.save(d1);
System.out.println("Data with "+day1.getName()+" has been added successfully");
Day d2= new Day();
List<Games> l2= new ArrayList<Games>();
d2.setName("Day 2");
Games g4= new Games();
g4.setName("Khokho");
l2.add(g4);
Games g5= new Games();
g5.setName("Tennis");
l2.add(g5);
l2.add(g1);
d2.setGame(l2);
Day day2=service.save(d2);
System.out.println("Data with "+day2.getName()+" has been added successfully");
Day d3= new Day();
List<Games> l3= new ArrayList<Games>();
d3.setName("Day 3");
l3.add(g2);
l3.add(g3);
Games g6= new Games();
g6.setName("Cricket");
l3.add(g6);
d3.setGame(l3);
Day day3=service.save(d3);
System.out.println("Data with "+day3.getName()+" has been added successfully");
Day d4= new Day();
List<Games> l4= new ArrayList<Games>();
d4.setName("Day 4");
l4.add(g4);
l4.add(g5);
l4.add(g1);
d4.setGame(l4);
Day day4=service.save(d4);
System.out.println("Data with "+day4.getName()+" has been added successfully");
Day d5= new Day();
List<Games> l5= new ArrayList<Games>();
d5.setName("Day 5");
l5.add(g4);
l5.add(g6);
l5.add(g5);
d5.setGame(l5);
Day day5=service.save(d5);
System.out.println("Data with "+day5.getName()+" has been added successfully");
}
 
private void findByDayName() {
Map<String,Day> data=new HashMap<String,Day>();
SportsRepo repo=new SportsRepoImpl();
SportsService service = new SportsServiceImpl(data, repo);
//SportsService service = new SportsServiceImpl();
//Scanner sc= new Scanner(System.in);
//System.out.println("Enter day name: ");
//String name=sc.nextLine();
String name="Day 4";
Day d=service.findByDayName(name);
for (Games l : d.getGame()) {
System.out.println(l.getName());
}
//sc.close();
}
 
private void findByGameName() {
Map<String,Day> data=new HashMap<String,Day>();
SportsRepo repo=new SportsRepoImpl();
SportsService service = new SportsServiceImpl(data, repo);
//SportsService service = new SportsServiceImpl();
//Scanner sc= new Scanner(System.in);
//System.out.println("Enter day name: ");
//String name=sc.nextLine();
String name="Tennis";
List<Day>l=service.findByGameName(name);
for (Day day : l) {
System.out.println(day.getName());
}
//sc.close();
}*/
}