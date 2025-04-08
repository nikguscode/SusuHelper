package com.nikguscode.SusuHelper.service.filter;

import com.nikguscode.SusuHelper.model.entity.Discipline;
import com.nikguscode.SusuHelper.model.entity.Student;
import com.nikguscode.SusuHelper.model.entity.StudentGroup;
import java.util.List;
import java.util.Set;

public interface MarkFilter {

  List<Discipline> filterByCurrentDayOfWeekAndTime(List<Discipline> disciplines);

  Set<Student> filterByDisciplineStudentGroup(
      Discipline discipline, List<StudentGroup> groups, List<Student> students);
}