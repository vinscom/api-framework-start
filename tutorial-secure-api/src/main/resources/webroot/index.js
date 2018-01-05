function callPostAPI(e) {
    e.preventDefault();

    var data = {};
    $("#sessionForm").serializeArray().map(function (x) {
        data[x.name] = x.value;
    });

    $.ajax({
        type: "POST",
        url: 'http://localhost:8888/v1/session',
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        data: JSON.stringify(data)
    });
}

function callGetAPI(e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: 'http://localhost:8888/v1/session',
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            $("#sessionData").empty();
            for (i = 0; i < data.length; ++i) {
                $("#sessionData").append($("<li>").text(data[i]));
            }
        }
    });
}