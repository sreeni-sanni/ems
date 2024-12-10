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
