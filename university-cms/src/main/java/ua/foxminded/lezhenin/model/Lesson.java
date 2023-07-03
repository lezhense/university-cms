package ua.foxminded.lezhenin.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "lessons", schema = "cms")
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms.lessons_id_seq")
	@SequenceGenerator(name = "cms.lessons_id_seq", sequenceName = "cms.lessons_id_seq", allocationSize = 1)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", nullable = true)
	private Course course;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id", nullable = true)
	private Teacher teacher;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "schedule_for_day_id", nullable = true)
	private ScheduleForDay scheduleForDay;

	@Column(name = "number", nullable = true)
	private int number;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "groups_lessons", schema = "cms", joinColumns = {
			@JoinColumn(name = "lesson_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
	private Set<Group> groups = new HashSet<>();

	@Override
	public String toString() {
		return this.course + " " + this.teacher + " " + this.groups;
	}

	public void addGroup(Group group) {
		this.groups.add(group);
		group.getLessons().add(this);
	}

	public void removeGroup(int groupId) {
		Group group = this.groups.stream().filter(c -> c.getId() == groupId).findFirst().orElse(null);
		if (group != null) {
			this.groups.remove(group);
			group.getLessons().remove(this);
		}
	}
}
