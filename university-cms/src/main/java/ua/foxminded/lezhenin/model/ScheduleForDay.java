package ua.foxminded.lezhenin.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "schedules_for_day", schema = "cms")
public class ScheduleForDay {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms.schedules_for_day_id_seq")
	@SequenceGenerator(name = "cms.schedules_for_day_id_seq", sequenceName = "cms.schedules_for_day_id_seq", allocationSize = 1)
	private int id;

	@Column(name = "schedule_date")
	@NonNull
	private Date scheduleDate;

	@OneToMany(mappedBy = "scheduleForDay", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Lesson> lessons;

	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
		lesson.setScheduleForDay(this);
	}

	public void removeLesson(int lessonId) {
		Lesson lesson = this.lessons.stream().filter(c -> c.getId() == lessonId).findFirst().orElse(null);
		if (lesson != null) {
			this.lessons.remove(lesson);
			lesson.setScheduleForDay(null);
		}
	}
}
