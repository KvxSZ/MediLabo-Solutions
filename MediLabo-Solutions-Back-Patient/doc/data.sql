CREATE TABLE Patient (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,  -- Correspond à @Id et @GeneratedValue
    prenom VARCHAR(255) NOT NULL,               -- Correspond à @Column(name = "prenom", nullable = false)
    nom VARCHAR(255) NOT NULL,                  -- Correspond à @Column(name = "nom", nullable = false)
    date_de_naissance DATE NOT NULL,              -- Correspond à @Column(name = "dateDeNaissance", nullable = false)
    genre VARCHAR(50) NOT NULL,                 -- Correspond à @Column(name = "genre", nullable = false)
    adresse_postale VARCHAR(255),                -- Correspond à @Column(name = "adressePostale", nullable = true)
    telephone VARCHAR(20)                       -- Correspond à @Column(name = "telephone", nullable = true)
);
