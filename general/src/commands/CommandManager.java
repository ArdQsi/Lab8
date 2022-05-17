package commands;

import connection.AnswerMsg;
import connection.CommandMsg;
import connection.Request;
import connection.Response;
import exceptions.*;
import io.ConsoleInputManager;
import io.FileInputManager;
import io.InputManager;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static io.OutputManager.print;

public abstract class CommandManager implements Commandable, Closeable {
    private Map<String, Command> map;
    private InputManager inputManager;
    private boolean isRunning;
    private String currentScriptFileName;
    private static Stack<String> callStack = new Stack<>();

    public Stack<String> getStack() {
        return callStack;
    }

    public String getCurrentScriptFileName() {
        return currentScriptFileName;
    }

    public CommandManager() {
        isRunning = false;
        currentScriptFileName = "";
        map = new HashMap<String,Command>();
    }

    public void addCommand(Command c) {
        map.put(c.getName(),c);
    }


    public void addCommand(String key, Command c) {
        map.put(key,c);
    }

    public Command getCommand(String s) {
        if (!hasCommand(s)) throw new NoSuchCommandException();
        return map.get(s);
    }

    public boolean hasCommand(String s) {
        return map.containsKey(s);
    }


    public void consoleMode() {
        inputManager = new ConsoleInputManager();
        isRunning = true;
        while(isRunning) {
            Response answerMsg;
            print("введите команду(Команда \"help\" выведет список команд)");
            try {
                CommandMsg commandMsg = inputManager.readCommand();
                answerMsg = runCommand(commandMsg);
            } catch (NoSuchCommandException e) {
                close();
                print("ввод для пользователя закрыт");
                break;
            }
            if(answerMsg.getStatus() == Response.Status.EXIT) {
                close();
            }
        }
    }

    public void fileMode(String path1) throws FileException {
        inputManager = new FileInputManager(path1);
        isRunning = true;
        while(isRunning && inputManager.getScanner().hasNextLine()) {
            CommandMsg commandMsg = inputManager.readCommand();
            Response answerMsg = runCommand(commandMsg);
            if(answerMsg.getStatus() == Response.Status.EXIT) {
                close();
            }
        }
    }

    public Response runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        try {
            Command cmd = getCommand(msg);
            cmd.setArgument(msg);
            res =(AnswerMsg) cmd.run();
        } catch (ExitException e) {
            res.setStatus(Response.Status.EXIT);
        } catch (CommandException | InvalidDataException | ConnectionException | FileException | CollectionException e) {
            res.error(e.getMessage());
        }
        return res;
    }

    public void setInputManager(InputManager in) {
        inputManager = in;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public static String getHelp() {
        return "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n"+
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "filter_less_than_manufacture_cost manufactureCost : вывести элементы, значение поля manufactureCost которых меньше заданного\n" +
                "print_unique_owner : вывести уникальные значения поля owner всех элементов в коллекции\n";
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void close() {
        setRunning(false);
    }
}

