package com.seuprojeto.service;

import java.util.ArrayList;
import java.util.List;

import com.seuprojeto.model.Employee;
import com.seuprojeto.model.OutsourcedEmployee;

public class EmployeeService {

	    private List<Employee> employees = new ArrayList<>();

	    // Retorna todos os funcionários armazenados (Employee ou OutsourcedEmployee)
	    public List<Employee> findAll() {
	        return employees;
	    }

	    // Adiciona um novo Employee na lista
	    public void addEmployee(Employee emp) {
	        employees.add(emp);
	    }

	    // Calcula a soma dos pagamentos de todos
	    public double getTotalPayments() {
	        double sum = 0.0;
	        for (Employee e : employees) {
	            sum += e.payment();
	        }
	        return sum;
	    }
}
