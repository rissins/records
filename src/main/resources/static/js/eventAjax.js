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
        'context': $("#context").val()
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