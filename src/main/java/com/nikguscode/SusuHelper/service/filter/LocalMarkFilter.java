package com.nikguscode.SusuHelper.service.filter;

import com.nikguscode.SusuHelper.dao.discipline.DisciplineDao;
import com.nikguscode.SusuHelper.dao.student.StudentDao;
import com.nikguscode.SusuHelper.model.entity.Discipline;
import com.nikguscode.SusuHelper.model.entity.Student;
import com.nikguscode.SusuHelper.model.entity.StudentGroup;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LocalMarkFilter implements MarkFilter {

  @Qualifier("jdbcDisciplineDao")
  private final DisciplineDao disciplineDao;

  @Qualifier("jdbcUserDao")
  private final StudentDao studentDao;

  public LocalMarkFilter(DisciplineDao disciplineDao, StudentDao studentDao) {
    this.disciplineDao = disciplineDao;
    this.studentDao = studentDao;
  }

  @Override
  public List<Discipline> filterByCurrentDayOfWeekAndTime(List<Discipline> unfilteredDisciplines) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalTime currentTime = currentDateTime.toLocalTime();
    DayOfWeek currentDayOfWeek = currentDateTime.getDayOfWeek();

    return unfilteredDisciplines.stream()
        .filter(discipline -> isCurrentTimeInRange(discipline, currentTime, currentDayOfWeek))
        .collect(Collectors.toList());
  }

  @Override
  public Set<Student> filterByDisciplineStudentGroup(
      Discipline discipline, List<StudentGroup> groups, List<Student> unfilteredStudents) {
    List<StudentGroup> actualGroups = getActualGroups(discipline, groups);
    Set<Student> filteredStudents = new HashSet<>();

    for (Student student : unfilteredStudents) {
      for (StudentGroup actualGroup : actualGroups) {
        if (student.getSusuGroup().equals(actualGroup.getGroupCode())) {
          filteredStudents.add(student);
        }
      }
    }

    return filteredStudents;
  }

  private boolean isCurrentTimeInRange(
      Discipline discipline, LocalTime currentTime, DayOfWeek currentDayOfWeek) {
    LocalTime disciplineStartTime = discipline.getStartTime();
    LocalTime disciplineEndTime = discipline.getEndTime();
    List<String> scheduledDays = discipline.getScheduledDays();

    if (currentTime.isAfter(disciplineStartTime) && currentTime.isBefore(disciplineEndTime)) {
      for (String currentScheduledDay : scheduledDays) {
        if (currentScheduledDay.equals(currentDayOfWeek.toString())) {
          return true;
        }
      }
    }

    return false;
  }

  private List<StudentGroup> getActualGroups(Discipline discipline, List<StudentGroup> groups) {
//    List<StudentGroup> actualGroups = new ArrayList<>();
//    for (StudentGroup group : groups) {
//      if (discipline.getStudentGroupId().equals(group.getDisciplineId())) {
//        actualGroups.add(group);
//      }
//    }

    return groups.stream()
        .filter(group -> discipline.getStudentGroupId().equals(group.getDisciplineId()))
        .collect(Collectors.toList());

//    return actualGroups;
  }
}