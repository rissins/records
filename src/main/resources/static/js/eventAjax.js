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
        'userId' : $("#userId").val(),
        'allDay' : $("#allDay").val(),
        // 'file' : $("#file").val()

    }
    var form = $('#uploadForm')[0];
    var formData = new FormData(form);
    formData.append('key', new Blob([ JSON.stringify(param) ], {type : "application/json"}));
    // console.log(param.allDay);
    // if (param.loginUser === "") {
    //     return;
    // }

    $.ajax({
        url: '/event',
        type: 'POST',
        enctype: 'multipart/form-data',
        contentType : false,
        processData : false,
        data: formData,
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
    let returnValue;

    $.ajax({
        url: "/event/" + $("#userId").val(),
        type: "GET",
        dataType: "json",
        async:false,

    }).done(function (data) {
        // $("#resultDiv").text(JSON.stringify(data));
        // console.log(JSON.stringify(data));
        console.log("성공");
        returnValue = data;
        // console.log(returnValue);
        // return JSON.stringify(data);

    }).fail(function () {
        console.log("실패");
    });
    return returnValue;
}

function detailViewData(eventId) {

    $.ajax({
        url: "/event/" + eventId,
        type: "GET",
        dataType: "json",
    }).done(function (data) {
        console.log(data);
        document.getElementById('viewTitle').innerText = data.title;
        document.getElementById('viewContext').innerText = data.context;
        document.getElementById('viewFile').src  = data.file;
        document.getElementById('uploadDate').innerText = data.start.substring(0, 10) +"\u00a0\u00a0"+ data.start.substring(11, 19);
    }).fail(function () {
        console.log("실패");
    });
}

function formatDate(date) {

    var d = new Date(date),

        month = '' + (d.getMonth() + 1) ,
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');

}

