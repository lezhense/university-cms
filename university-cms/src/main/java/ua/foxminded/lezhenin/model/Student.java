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
@Table(name = "students", schema = "cms")
public class Student extends User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms.students_id_seq")
	@SequenceGenerator(name = "cms.students_id_seq", sequenceName = "cms.students_id_seq", allocationSize = 1)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id", nullable = true)
	private Group group;
	
	@Column(name = "first_name", nullable = false)
	@NonNull
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	@NonNull
	private String lastName;
	
	@Column(name = "username", nullable = false)
	@NonNull
	private String username;
	
	@Column(name = "password", nullable = false)
	@NonNull
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "students_roles", schema = "cms", 
	           joinColumns = { @JoinColumn(name = "student_id") }, 
	           inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {
		this.roles.add(role);
	}
}
