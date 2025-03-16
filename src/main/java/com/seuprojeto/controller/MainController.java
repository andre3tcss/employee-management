package com.seuprojeto.controller;

import com.seuprojeto.model.Employee;
import com.seuprojeto.model.OutsourcedEmployee;
import com.seuprojeto.service.EmployeeService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

    // Mesmos nomes de fx:id do FXML
    @FXML
    private TableView<Employee> tableEmployees;
    @FXML
    private TableColumn<Employee, String> colName;
    @FXML
    private TableColumn<Employee, Integer> colHours;
    @FXML
    private TableColumn<Employee, Double> colValuePerHour;
    @FXML
    private TableColumn<Employee, Double> colPayment;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtHours;
    @FXML
    private TextField txtValuePerHour;
    @FXML
    private TextField txtAdditionalCharge;

    @FXML
    private Label lblTotal;

    // Nosso serviço que gerencia a lista de funcionários
    private EmployeeService service = new EmployeeService();

    // É chamado automaticamente após o FXML ser carregado
    @FXML
    public void initialize() {
        // Configurar as colunas para ler as propriedades de cada Employee
        colName.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        colHours.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHours()));
        colValuePerHour.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getValuePerHour()));
        colPayment.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().payment()));

        // Iniciar a tabela vazia
        tableEmployees.getItems().setAll(service.findAll());

        // Atualizar o label inicial
        updateTotalLabel();
    }

    // Método chamado pelo botão "Add Employee"
    @FXML
    public void onAddEmployee() {
        try {
            // Ler os valores dos TextFields
            String name = txtName.getText();
            int hours = Integer.parseInt(txtHours.getText());
            double valuePerHour = Double.parseDouble(txtValuePerHour.getText());

            String additionalText = txtAdditionalCharge.getText();

            Employee emp;
            if (additionalText == null || additionalText.isEmpty()) {
                // Se "Additional?" está vazio, é um Employee normal
                emp = new Employee(name, hours, valuePerHour);
            } else {
                // Se tem algo em "Additional?", é um OutsourcedEmployee
                double additionalCharge = Double.parseDouble(additionalText);
                emp = new OutsourcedEmployee(name, hours, valuePerHour, additionalCharge);
            }

            // Adicionar ao serviço
            service.addEmployee(emp);

            // Atualizar a tabela
            tableEmployees.getItems().setAll(service.findAll());

            // Atualizar o total
            updateTotalLabel();

            // Limpar campos
            txtName.clear();
            txtHours.clear();
            txtValuePerHour.clear();
            txtAdditionalCharge.clear();

        } catch (NumberFormatException e) {
            // Se digitar texto inválido em Hours/ValuePerHour/Additional?
            System.err.println("Erro ao converter valor numérico: " + e.getMessage());
        }
    }

    private void updateTotalLabel() {
        double total = service.getTotalPayments();
        lblTotal.setText(String.format("Total Payment: $ %.2f", total));
    }
}
