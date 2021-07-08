package com.company.exam;

import com.company.less44.LessonServer;
import com.company.server.RouteHandler;
import com.company.server.Utils;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ExamServer extends LessonServer {
    TaskCollection taskCollection;
    File file;

    public ExamServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/task", this::taskGet);
        registerGet("/task/filter", this::taskGet);
        registerGet("/task-info", this::getInfo);
        registerPost("/add", this::addTask);
        registerPost("/task", this::doneTaskFromTable);
        registerPost("/task-info",this::doneTask);
        registerGet("/task/delete",this::deleteTaskFromTable);
        registerGet("/task/update",this::getUpdateTask);
        registerPost("/task/update", this::updateTask);
        registerGet("/task-info/delete", this::getDeleteTask);
        registerGet("statistic", this::getStatistic);
        this.file = getFilePath();
        this.taskCollection = getTaskCollection(this.file);
    }




    public File getFilePath() {
        String path = "tasks.json";
        return new File(path);
    }

    public TaskCollection getTaskCollection(File file) {
        if (file == null) {
            return new TaskCollection();
        } else
            return readTasksFromJson();
    }

    public TaskCollection readTasksFromJson() {
        if (file.length() > 0) {
            try {
                Gson gson = new Gson();
                FileReader fileReader = new FileReader("tasks.json");
                TaskCollection users = gson.fromJson(fileReader, TaskCollection.class);
                return users;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new TaskCollection();
    }

        public void JsonWriter() {
        try {
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter("tasks.json");
            String collectionJson = gson.toJson(taskCollection);
            fileWriter.write(collectionJson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void registerPost(String route, RouteHandler handler) {
        registerGeneralHandler("POST", route, handler);
    }

    private void taskGet(HttpExchange exchange) {
        String queryString = exchange.getRequestURI().getQuery();
        if (queryString != null) {
            List<Task> result;
            String action = Utils.parseUrlEncoded(queryString, "&")
                    .getOrDefault("filter", "0");

            result = doFilter(action);
            TaskCollection filtered = new TaskCollection();
            filtered.tasks.addAll(result);
            renderTemplate(exchange, "task.html", filtered);
            return;
        }
        renderTemplate(exchange, "task.html", taskCollection);
    }

    private List<Task> doFilter(String action) {
        List<Task> tasks = new ArrayList<>();
        if(action.equals("done")){
            tasks = taskCollection.tasks.stream().filter(task -> task.getStatus().equals("done")).collect(Collectors.toList());
            return tasks;
        }if(action.equals("new")){
            tasks = taskCollection.tasks.stream().filter(task -> task.getStatus().equals("new")).collect(Collectors.toList());
            return tasks;
        }else {
        return taskCollection.getTasks();
        }
    }

    private void getInfo(HttpExchange exchange) {
        String queryString = exchange.getRequestURI().getQuery();
        String id = Utils.parseUrlEncoded(queryString, "&").getOrDefault("id", "0");
        Task task = taskCollection.tasks.stream()
                .filter(task1 -> task1.getId().equals(id))
                .findAny()
                .orElse(null);
        renderTemplate(exchange, "task-info.ftl", task);
    }

    private void addTask(HttpExchange exchange) {
        String body = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(body, "&");
        taskCollection.tasks.add(new Task(parsed.get("heading"),parsed.get("executorName"),parsed.get("taskDescription")));
        JsonWriter();
        renderTemplate(exchange, "task.html",taskCollection);
    }

    private void doneTaskFromTable(HttpExchange exchange){
        String queryString = exchange.getRequestURI().getQuery();
        String id = Utils.parseUrlEncoded(queryString, "&").getOrDefault("id", "0");
        taskCollection.tasks.stream().filter(task -> task.getId().equals(id)).findAny()
                .ifPresent(task -> task.setStatus("done"));
        taskCollection.tasks.stream().filter(task -> task.getId().equals(id)).findAny()
                .ifPresent(task -> task.setDateOfFinish(LocalDate.now()));
        JsonWriter();
        renderTemplate(exchange, "task.html",taskCollection);
    }

    private void doneTask(HttpExchange exchange){
        String queryString = exchange.getRequestURI().getQuery();
        String id = Utils.parseUrlEncoded(queryString, "&").getOrDefault("id", "0");
        Task ans = taskCollection.tasks.stream()
                .filter(task1 -> task1.getId().equals(id))
                .findAny()
                .orElse(null);
        ans.setStatus("done");
        ans.setDateOfFinish(LocalDate.now());
        taskCollection.tasks.stream().filter(task1 -> task1.getId().equals(id)).findAny()
                .ifPresent(task1 -> task1.setStatus("done"));
        JsonWriter();
        renderTemplate(exchange, "task-info.ftl",ans);
    }

    private void deleteTaskFromTable(HttpExchange exchange){
        String queryStr = exchange.getRequestURI().getQuery();
        String id = Utils.parseUrlEncoded(queryStr, "&").getOrDefault("id", "0");
        taskCollection.tasks.removeIf(task -> task.getId().equals(id));
        JsonWriter();
        renderTemplate(exchange, "task.html",taskCollection);
    }

    private void getUpdateTask(HttpExchange exchange){
        String queryString = exchange.getRequestURI().getQuery();
        String id = Utils.parseUrlEncoded(queryString, "&").getOrDefault("id", "0");
        Task task = taskCollection.tasks.stream()
                .filter(task1 -> task1.getId().equals(id))
                .findAny()
                .orElse(null);
        renderTemplate(exchange, "task-update.ftl", task);
    }

        private void updateTask(HttpExchange exchange){
        String queryString = exchange.getRequestURI().getQuery();
        String id = Utils.parseUrlEncoded(queryString, "&").getOrDefault("id", "0");
        String body = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(body, "&");
            for (Task task : taskCollection.tasks) {
                if(task.getId().equals(id)){
                    task.setHeading(parsed.get("heading"));
                    task.setExecutorName(parsed.get("executorName"));
                    task.setDescription(parsed.get("taskDescription"));
                }
            }
        JsonWriter();

            Task task = taskCollection.tasks.stream()
                    .filter(task1 -> task1.getId().equals(id))
                    .findAny()
                    .orElse(null);

        renderTemplate(exchange, "task-info.ftl",task);
    }

    private void getDeleteTask(HttpExchange exchange){
        String queryStr = exchange.getRequestURI().getQuery();
        String id = Utils.parseUrlEncoded(queryStr, "&").getOrDefault("id", "0");
        Task task = taskCollection.tasks.stream()
                .filter(task1 -> task1.getId().equals(id))
                .findAny()
                .orElse(null);
        renderTemplate(exchange, "task-info-delete.ftl",task);
    }

    private void getStatistic(HttpExchange exchange) {
        int allTask = taskCollection.tasks.size();
        List<Task> tasks = taskCollection.tasks.stream().filter(task -> task.getStatus().equals("new")).collect(Collectors.toList());
        int newTask = tasks.size();
        int newTaskQuantity = allTask==0 ? 0 : (newTask*100)/allTask;
        int doneTaskQuantity = allTask ==0 ? 0 : 100 - newTaskQuantity;

        renderTemplate(exchange,"statistic.ftl", new Statistic(newTaskQuantity, doneTaskQuantity));
    }


    protected String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader reader = new InputStreamReader(input, utf8);
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
