package file;

import com.opencsv.CSVWriter;
import data.Product;
import exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Operates the main collection file for saving/loading, reads a script file
 */

public class FileManager{
    private String path;
    private File file;

    public FileManager(String pth) {
        path = pth;
    }

    public FileManager(File f) {
        file = f;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFile(File f) {
        file = f;
    }

    public FileManager() {
        path = null;
    }

    public String read() throws FileException {
        if (file==null) {
            if (path == null) throw new EmptyPathException();
            InputStreamReader reader = null;

            File f = new File(path);
            return readFile(f);
        } else {
            return readFile(file);
        }
    }

    private String readFile(File f) throws FileException {
        String str = "";
        try {
            InputStreamReader reader = null;
            if (!f.exists()) throw new FileNotExistsException();
            if (!f.canRead()) throw new FileWrongPermissionException("");
            InputStream inputStream = new FileInputStream(f);
            reader = new InputStreamReader(inputStream);
            int currectSymbol;
            while ((currectSymbol = reader.read())!=-1) {
                str += ((char) currectSymbol);
            }
            reader.close();
        } catch (IOException e) {
            throw new FileException("cannot read file");
        }
        return str;
    }

    private void create(File file) throws CannotCreateFileException {
        try {
            boolean success = file.createNewFile();
            if (!success) throw new CannotCreateFileException();
        } catch (IOException e) {
            throw new CannotCreateFileException();
        }
    }

    public void write(String str) throws FileException {
        try {
            if (path == null) throw  new EmptyPathException();
            File file = new  File(path);

            if (!file.exists()) {
                create(file);
            }
            if (!file.canWrite()) throw new FileWrongPermissionException("cannot write in file");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(str);
            writer.close();

        } catch (IOException e) {
            throw new FileException("unable to read file");
        }
    }

//    /**
//     * Constructor, just save variable
//     * @param file
//     */
//    public FileManager(File file) {path = System.getenv().get("Lab6");}
//
//    /**
//     * Constructor, just save the path to the script file
//     * @param path1
//     */
//    public FileManager(String path1) {
//        this.path1 = path1;
//    }
//
//    /**
//     * Read commands from a file
//     * @return string of commands
//     */
//    public String read() throws FileException {
//        String str = "";
//        try{
//            if (path1 == null) throw new EmptyPathException();
//            File file = new File(path1);
//            if(!file.exists()) throw new FileNotExistsException();
//            if(!file.canRead()) throw new FileWrongPermissionException("cannot read file");
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line = bufferedReader.readLine();
//            while (line != null) {
//                str+=line+"\n";
//                line = bufferedReader.readLine();
//            }
//        } catch (IOException e){
//            throw new FileException("cannot read file");
//        }
//        return str;
//    }
//
//    /**
//     * Write data in csv to file
//     * @param arrayList
//     */
//    public void writeToCSV(ArrayList<String[]> arrayList){
//        try {
//            FileWriter fileWriter = new FileWriter(path);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            CSVWriter csvWriter = new CSVWriter(bufferedWriter, ',','\0' ,'\0' ,"\n");
//            for (String[] out : arrayList) {
//                csvWriter.writeNext(out);
//            }
//            csvWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Reads the element's data and puts it into a ArrayList<String[]>
//     * @param linkedList
//     * @return element in ArrayList<String[]>
//     */
//    public ArrayList<String[]> getElement(LinkedList<Product> linkedList) {
//        ArrayList<String[]> arrayList = new ArrayList<>();
//        linkedList.forEach(x -> arrayList.add(x.getDataCollection()));
//        return arrayList;
//    }

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

