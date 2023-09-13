package java2_project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadAndWriteEmployee {
    //read employees information from csv file and count total employees.
    private List<Employee> list;
    private int id;
    private int countEmployees = 0;
    
    public int getCountEmployees() {
        return countEmployees;
    }

    public List<Employee> getList() {
        return list;
    }
    public int getId() {
        return id;
    }

    public List<Employee> readFromFile(String fileName) {
        File file = new File(fileName);
        if(!file.exists()) {       
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
    
        list = new ArrayList<>();
        try(Scanner reader = new Scanner(file)) {
            while(reader.hasNext()) {
            String line = reader.nextLine();
            String[] field = line.split(",");
            list.add(new Employee(Integer.parseInt(
                field[0]), field[1], field[2], Boolean.valueOf(field[3]), field[4]));
            id = Integer.parseInt(field[0]);
            countEmployees++;
            } 
        } catch(IOException e) {
            return null;
            }
        return list;
    }

    public void overWriteEmployeeToFile(String filename, String name, String position, String gender, List<Employee> newList) {
        if(name == null || position == null || gender == null) {
            
          }
            File file = new File(filename);
            try(PrintWriter writer = new PrintWriter(file)) {
                for(Employee employee: newList) {
                    writer.println(employee);
                }
               
            } catch(IOException e) {
                System.out.println("Writing to file failed.");
            }
        
    }

}
