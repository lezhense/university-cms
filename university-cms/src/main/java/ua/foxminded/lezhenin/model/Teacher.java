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
@Table(name = "teachers", schema = "cms")
public class Teacher extends User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms.teachers_id_seq")
	@SequenceGenerator(name = "cms.teachers_id_seq", sequenceName = "cms.teachers_id_seq", allocationSize = 1)
	private int id;

	@Column(name = "first_name", nullable = false)
	@NonNull
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@NonNull
	private String lastName;

	@Column(name = "degree", nullable = true)
	private String degree;

	@Column(name = "username", nullable = false)
	@NonNull
	private String username;

	@Column(name = "password", nullable = false)
	@NonNull
	private String password;

	@OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
	private Set<Course> courses;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "teachers_roles", schema = "cms", joinColumns = {
			@JoinColumn(name = "teacher_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return this.username;
	}
}
