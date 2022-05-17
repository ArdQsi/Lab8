package file;

import com.opencsv.CSVWriter;
import data.Coordinates;
import data.Person;
import data.Product;
import data.UnitOfMeasure;
import exceptions.EmptyPathException;
import exceptions.FileException;
import exceptions.FileNotExistsException;
import exceptions.FileWrongPermissionException;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import static io.OutputManager.printErr;

/**
 * Operates the main collection file for saving/loading, reads a script file
 */

public class FileManager{
    private String path;
    private String path1;

    /**
     * Constructor, just save variable
     */
    public FileManager() {path = System.getenv().get("Lab6");}

    /**
     * Constructor, just save the path to the script file
     * @param path1
     */
    public FileManager(String path1) {
        this.path1 = path1;
    }

    /**
     * Read commands from a file
     * @return string of commands
     */
    public String read() throws FileException {
        String str = "";
        try{
            if (path1 == null) throw new EmptyPathException();
            File file = new File(path1);
            if(!file.exists()) throw new FileNotExistsException();
            if(!file.canRead()) throw new FileWrongPermissionException("cannot read file");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                str+=line+"\n";
                line = bufferedReader.readLine();
            }
        } catch (IOException e){
            throw new FileException("cannot read file");
        }
        return str;
    }

    /**
     * Write data in csv to file
     * @param arrayList
     */
    public void writeToCSV(ArrayList<String[]> arrayList){
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            CSVWriter csvWriter = new CSVWriter(bufferedWriter, ',','\0' ,'\0' ,"\n");
            for (String[] out : arrayList) {
                csvWriter.writeNext(out);
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the element's data and puts it into a ArrayList<String[]>
     * @param linkedList
     * @return element in ArrayList<String[]>
     */
    public ArrayList<String[]> getElement(LinkedList<Product> linkedList) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        linkedList.forEach(x -> arrayList.add(x.getDataCollection()));
        return arrayList;
    }

//    /**
//     * Read file and add product to collection
//     * @return collection of elements
//     */
//    public LinkedList<Product> readCSVS() throws FileException{
//        LinkedList<Product> collection = new LinkedList<>();
//        if (path != null) {
//            try {
//                FileReader fileReader = new FileReader(path);
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
//                String splitBy = ",";
//
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    try {
//                        Product product = new Product();
//                        Coordinates coordinates = new Coordinates();
//                        Person owner = new Person();
//                        int f = 0;
//                        String[] s = line.split(splitBy);
//                        if (s[0].matches("[0-9]{1,}")) product.setId(Long.valueOf(s[0]));
//                        else f = 1;
//                        if (s[1].matches("[А-Яа-яЁёa-zA-Z]{1,}")) product.setName(s[1]);
//                        else f = 2;
//                        if (Long.valueOf(s[2]) >= 0 && Long.valueOf(s[2]) < 658 && s[2].matches("[0-9]{1,}"))
//                            coordinates.setX(Long.valueOf(s[2]));
//                        else f = 3;
//                        if (Long.valueOf(s[3]) >= 0 && Long.valueOf(s[3]) < 211 && s[3].matches("[0-9]{1,}"))
//                            coordinates.setY(Integer.valueOf(s[3]));
//                        else f = 4;
//                        if (s[4].matches("[0-9-]{4}-[0-9]{2}-[0-9]{2}")) product.setCreationDate(LocalDate.parse(s[4]));
//                        else f = 5;
//                        if (Integer.valueOf(s[5]) >= 0 && s[5].matches("(\\d){1,}"))
//                            product.setPrice(Integer.valueOf(s[5]));
//                        else f = 6;
//                        if (s[6].matches("[0-9.]{1,}")) product.setManufactureCost(Float.valueOf(s[6]));
//                        else f = 7;
//                        if (s[7].matches("KILOGRAMS") || s[7].matches("METERS") || s[7].matches("SQUARE_METERS")
//                                || s[7].matches("MILLILITERS")) product.setUnitOfMeasure(UnitOfMeasure.valueOf(s[7]));
//                        else f = 8;
//                        if (s[8].matches("[А-Яа-яЁёa-zA-Z]*")) owner.setPersonName(s[8]);
//                        else f = 9;
//                        if (s[9].matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}")) owner.setBirthday(LocalDateTime.parse(s[9]));
//                        else f = 10;
//                        if (Float.valueOf(s[10]) > 0 || s[10].matches("[0-9.]{1,}"))
//                            owner.setWeight(Float.valueOf(s[10]));
//                        else f = 11;
//                        if (s[11].matches("\\w{7,}")) {
//                            owner.setPassportID(s[11]);
//                        } else f = 12;
//
//                        if (f == 0) {
//                            product.setCoordinates(coordinates);
//                            product.setOwner(owner);
//                            collection.add(product);
//                        } else {
//                            if (f == 1) printErr("Id changed in the file");
//                            if (f == 2) printErr("Product name changed in the file");
//                            if (f == 3) printErr("Changed the X coordinate in the file");
//                            if (f == 4) printErr("Changed the Y coordinate in the file");
//                            if (f == 5) printErr("Creation date changed in the file");
//                            if (f == 6) printErr("Price changed in the file");
//                            if (f == 7) printErr("Manufacture cost changed in the file");
//                            if (f == 8) printErr("Unit of measure changed in the file");
//                            if (f == 9) printErr("Birthday changed in the file");
//                            if (f == 10) printErr("Birthday changed in the file");
//                            if (f == 11) printErr("Weight changed in the file");
//                            if (f == 12) printErr("Passport id changed in the file");
//                        }
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        printErr("Вы не ввели паспорт");
//                    } catch (NumberFormatException e) {
//
//                    } catch (DateTimeParseException e) {}
//                    line = bufferedReader.readLine();
//                }
//            } catch (FileNotFoundException e) {
//                printErr("Загрузочный файл не найден!");
//            } catch (NoSuchFileException e) {
//                printErr("Загрузочный файл пуст!");
//            } catch (IllegalStateException e) {
//                printErr("Непредвиденная ошибка!");
//            } catch (IOException e) {}
//        }else printErr("Системная переменная с загрузочным файлом не найдена!");
//        return collection;
//    }
}

