package com.example.batchprocessing;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

@Entity
@Table(name = "people")
public record Person(
		@Column(name="first_name")
		String firstName, 
		@Column(name="last_name")
		String lastName) {

}
