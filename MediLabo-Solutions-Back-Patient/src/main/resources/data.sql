DELETE FROM patient;
INSERT INTO patient (prenom, nom, date_de_naissance, genre, adresse_postale, telephone, patient_id)
VALUES
('TestNone', 'Test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333', 1),
('TestBorderline', 'Test', '1945-06-24', 'M', '2 High St', '200-333-4444', 2),
('TestInDanger', 'Test', '2004-06-18', 'M', '3 Club Road', '300-444-5555', 3),
('TestEarlyOnset', 'Test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666', 4);
