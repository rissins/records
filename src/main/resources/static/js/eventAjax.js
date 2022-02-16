// function eventSave() {
//     $.ajax({
//         url: "/bbs",
//         type: "GET",
//         dataType: "json",
//     }).done(function (data) {
//         $("#resultDiv").text(JSON.stringify(data));
//         console.log("성공");
//
//     }).fail(function () {
//         console.log("실패");
//     })
// }

function eventSave() {
    var param = {
        'title': $("#title").val(),
        'context': $("#context").val(),
        'textColor': $("#textColor").val(),
        'backgroundColor': $("#backgroundColor").val(),
        'loginUser' : $("#loginUser").val()
    };

    $.ajax({
        url: '/event',
        type: 'POST',
        data: param,
    }).done(function () {
        alert("완료");
        // window.location.replace("/");
    }).fail(function () {
            alert("실패");
            // history.back();
        }
    );

}


function subtractTwoIntegers(a,b){
    var calcInstance = new com.rissins.records.service.EventService();
    return {
        result : calcInstance.findUi
    };
}

function init() {
    $.ajax({
        type : "GET",
        url : "http://localhost:10004/호출할ControllerMapping값",
        contentType: "application/json",
        dataType: "json",
        success : function (data, status) {
            alert(status);
        },
        error : function (status) {
            alert(status + "error!");
        }
    });
}