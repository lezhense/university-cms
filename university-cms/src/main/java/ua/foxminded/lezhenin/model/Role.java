package ua.foxminded.lezhenin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "roles", schema = "cms")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms.roles_id_seq")
	@SequenceGenerator(name = "cms.roles_id_seq", sequenceName = "cms.roles_id_seq", allocationSize = 1)
	private int id;
	@Column(name = "name", nullable = false)
	@NonNull
	private String name;

	@Override
	public String toString() {
		return this.name;
	}
}
