CREATE TABLE IF NOT EXISTS Role (
                                    id INT PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL UNIQUE
    );

CREATE TABLE  IF NOT EXISTS Employee (
                                         id INT  PRIMARY KEY,
                                         firstName VARCHAR(50) NOT NULL,
    surName VARCHAR(50) NOT NULL,
    roleid INT NOT NULL,
    FOREIGN KEY (roleid) REFERENCES Role(id)
    );

CREATE TABLE  IF NOT EXISTS Project (
                                        id INT  PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES Employee(id)
    );

CREATE INDEX surName_index ON Employee (surName);
--insert some values
INSERT INTO Role (id,name)VALUES (1,'ADMIN'), (2,'USER'), (3,'MANAGER');
INSERT INTO Employee (id,first_name,sur_name,role_id)VALUES (100,'ADMIN','ADMIN',1);
INSERT INTO Employee (id,first_name,sur_name,role_id)VALUES (102,'USER','USER',2);
INSERT INTO Employee (id,first_name,sur_name,role_id)VALUES (103,'MANAGER','MANAGER',3);
INSERT INTO Employee (id,first_name,sur_name,role_id)VALUES (1300,'MANAGER','MANAGER',3);
INSERT INTO Project (id,name,employee_id)VALUES (11,'SampleProject',100);
INSERT INTO Project (id,name,employee_id)VALUES (12,'SampleProject',101);
INSERT INTO Project (id,name,employee_id)VALUES (13,'SampleProject',102);
INSERT INTO Project (id,name,employee_id)VALUES (14,'DummyProject',200);

--Stored Procedure
CREATE PROCEDURE DeleteRoleWithEmployeesAndReassignProjects(IN roleId INT, IN defaultEmployeeId INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error occurred during role deletion';
END;

    -- Begin transaction
START TRANSACTION;

-- Step 1: Reassign projects from employees of the specified role to the default employee
UPDATE Project
SET employee_id = defaultEmployeeId
WHERE employee_id IN (
    SELECT id
    FROM Employee
    WHERE role_id = roleId
);

-- Step 2: Delete employees associated with the specified role
DELETE FROM Employee
WHERE role_id = roleId;

-- Step 3: Delete the specified role
DELETE FROM Role
WHERE id = roleId;

-- Commit transaction
COMMIT;
