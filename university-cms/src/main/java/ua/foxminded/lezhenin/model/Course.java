package ua.foxminded.lezhenin.model;

import java.util.ArrayList;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "courses", schema = "cms")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms.courses_id_seq")
	@SequenceGenerator(name = "cms.courses_id_seq", sequenceName = "cms.courses_id_seq", allocationSize = 1)
	private int id;	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id", nullable = true)
	private Teacher teacher;

	@Column(name = "name", unique = true, nullable = false)
	@NonNull
	private String name;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Lesson> lessons = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "courses")
	private Set<Group> groups = new HashSet<>();

	@Override
	public String toString() {
		return this.name;
	}

	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
		lesson.setCourse(this);
	}

	public void removeLesson(int lessonId) {
		Lesson lesson = this.lessons.stream().filter(c -> c.getId() == lessonId).findFirst().orElse(null);
		if (lesson != null) {
			this.lessons.remove(lesson);
			lesson.setCourse(null);	
		}
	}

}
