package com.medilabo.MediLabo_Solutions_Back_Note;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MediLaboSolutionsBackNoteApplication {

	@Autowired
	private MongoDBDateInit mongoDBDateInit;

	public static void main(String[] args) {
		SpringApplication.run(MediLaboSolutionsBackNoteApplication.class, args);
	}

	@PostConstruct
	public void intitDatabase(){
		mongoDBDateInit.InitMongoDB();
	}

}
