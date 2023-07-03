package ua.foxminded.lezhenin.model;

import java.util.HashSet;
import java.util.List;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "groups", schema = "cms")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms.groups_id_seq")
	@SequenceGenerator(name = "cms.groups_id_seq", sequenceName = "cms.groups_id_seq", allocationSize = 1)
	private int id;
	
	@Column(name = "name", unique = true, nullable = false)
	@NonNull
	private String name;
	
	@Column(name = "speciality", nullable = true)
	private String speciality;
	
	@Column(name = "year", nullable = true)
	private int year;
	
	@OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
	private List<Student> students;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "groups_courses", schema = "cms", joinColumns = {
			@JoinColumn(name = "group_id") }, inverseJoinColumns = { @JoinColumn(name = "course_id") })
	private Set<Course> courses = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, 
			    mappedBy = "groups")
	private Set<Lesson> lessons = new HashSet<>();
	
	@Override
	public String toString() {
		return this.name;
	}

	public void addCourse(Course course) {
		this.courses.add(course);
		course.getGroups().add(this);
	}

	public void removeCourse(int courseId) {
		Course course = this.courses.stream().filter(c -> c.getId() == courseId).findFirst().orElse(null);
		if (course != null) {
			this.courses.remove(course);
			course.getGroups().remove(this);
		}
	}
}
