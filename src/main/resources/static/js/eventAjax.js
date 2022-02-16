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
    if (param.loginUser === "") {
        return;
    }

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


function searchData() {
    $.ajax({
        url: "/event/" + 28,
        type: "GET",
        dataType: "json",
    }).done(function (data) {
        // $("#resultDiv").text(JSON.stringify(data));
        console.log("성공");
        console.log($("#resultDiv").text(JSON.stringify(data)))

    }).fail(function () {
        console.log("실패");
    })
}
