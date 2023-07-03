package ua.foxminded.lezhenin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import ua.foxminded.lezhenin.model.ScheduleForDay;
import ua.foxminded.lezhenin.service.impl.ScheduleForDayServiceImpl;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	private final ScheduleForDayServiceImpl scheduleForDayServiceImpl;

	@Autowired
	public ScheduleController(ScheduleForDayServiceImpl scheduleForDayServiceImpl) {
		this.scheduleForDayServiceImpl = scheduleForDayServiceImpl;
	}

	@GetMapping("/all")
	public String getAllSchedules(Model model) {
		List<ScheduleForDay> schedules = scheduleForDayServiceImpl.getAll();
		model.addAttribute("schedules", schedules);
		return "schedules_all";
	}

	/*
	 * @GetMapping("/students_in_group/{id}") public String
	 * showAllStudentsInGroup(@PathVariable("id") Integer id, Model model) { Group
	 * selectedGroup = groupServiceImpl.get(id); List<Student> studentsInGroup =
	 * studentServiceImpl.getAllByGroup(selectedGroup);
	 * model.addAttribute("studentsInGroup", studentsInGroup); return
	 * "group/students_in_group"; }
	 */

	/*
	 * @GetMapping("/create") public String showGroupCreationForm(Model model) {
	 * GroupDto groupDto = new GroupDto(); model.addAttribute("groupDto", groupDto);
	 * return "group/group_create_form"; }
	 */

	/*
	 * @GetMapping("/creation_success") public String showGroupCreationSuccess() {
	 * return "done_success"; }
	 */

	/*
	 * @PostMapping("/group_creation") public String createGroup(final @Valid
	 * GroupDto groupDto, final BindingResult bindingResult, final Model model) { if
	 * (bindingResult.hasErrors()) { model.addAttribute("groupDto", groupDto);
	 * return "group/group_create_form"; } groupServiceImpl.createGroup(groupDto);
	 * return "redirect:/group/creation_success"; }
	 */

	/*
	 * @GetMapping("/delete/{id}") public String deleteGroup(@PathVariable("id")
	 * Integer id, Model model) { groupServiceImpl.delete(id); return
	 * "redirect:/group/all"; }
	 */

	/*
	 * @GetMapping("/edit/{id}") public String editGroup(@PathVariable("id") Integer
	 * id, Model model) { Group group = groupServiceImpl.get(id); Set<Course>
	 * setCourses = new HashSet<>(courseServiceImpl.getAll());
	 * model.addAttribute("group", group); model.addAttribute("setCourses",
	 * setCourses); return "group/group_form"; }
	 */

	/*
	 * @PostMapping("/save") public String saveGroup(Group group) {
	 * groupServiceImpl.save(group); return "redirect:/group/all"; }
	 */

}
