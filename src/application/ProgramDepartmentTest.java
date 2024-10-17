package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class ProgramDepartmentTest {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("===== TEST 1: department findById =====");
		Department dep = departmentDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n===== TEST 2: department findAll =====");
		List<Department> listDep = departmentDao.findAll();
		for(Department dp : listDep) {
			System.out.println(dp);
		}
		
		System.out.println("\n===== TEST 3: department insert =====");
		Department newDep = new Department(null, "New Department");
		departmentDao.insert(newDep);
		System.out.println("Inserted! New Id = " + newDep.getId());
		
		System.out.println("\n===== TEST 4: department update =====");
		newDep.setName("Updated Department");
		departmentDao.update(newDep);
		System.out.println("Updated completed!");
		
		System.out.println("\n===== TEST 5: department update =====");
		departmentDao.deleteById(newDep.getId() - 1);
		System.out.println("Delete completes!");
		
		sc.close();
		
	}

}
