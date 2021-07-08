<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <title>Document</title>
</head>
<body>
<div class="col-3 mx-auto">
    <h4 class="mx-auto mt-5 text-center">Редактировать задание</h4>
    <form class="my-form mt-4" action="/task/update?id=${id}" method="post">
        <div class="form-group">
            <label for="exampleInputHeading">Заголовок</label>
            <input type="text" name="heading" class="form-control" id="exampleInputHeading" required value="${heading}">
        </div>
        <div class="form-group">
            <label for="exampleInputName">Имя исполнителя</label>
            <input type="text" name="executorName" class="form-control" id="exampleInputName" required value="${executorName}">
        </div>
        <div class="form-group">
            <label for="exampleFormControlTextarea1">Описание</label>
            <textarea class="form-control" name="taskDescription" id="exampleFormControlTextarea1" rows="3">${description}</textarea>
        </div>
        <button type="submit" class="btn btn-primary">Сохранить</button>
        <a class="btn btn-primary" href="/task-info?id=${id!}">Сохранить без изменения</a>
    </form>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</html>