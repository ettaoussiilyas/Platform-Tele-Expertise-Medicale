-- Test user with properly hashed password (password: "test123")
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, role_id, created_at, updated_at)
VALUES 
('Test', 'User', 'test@example.com', '$2a$10$lU0eZqMbGT46n7cwPp4eveaNPnH0I5Cnw0OkShwTgQ1wW6z5ugylK', '0611223344', 1, NOW(), NOW());

-- Password for all test users is "test123"
UPDATE utilisateurs SET mot_de_passe = '$2a$10$lU0eZqMbGT46n7cwPp4eveaNPnH0I5Cnw0OkShwTgQ1wW6z5ugylK' WHERE email IN ('marie.dupont@hospital.com', 'pierre.martin@hospital.com', 'sophie.dubois@hospital.com');